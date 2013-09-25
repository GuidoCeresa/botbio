/* Created by Algos s.r.l. */
/* Date: mag 2013 */
/* Il plugin Algos ha creato o sovrascritto il templates che ha creato questo file. */
/* L'header del templates serve per controllare le successive release */
/* (tramite il flag di controllo aggiunto) */
/* Tipicamente VERRA sovrascritto (il template, non il file) ad ogni nuova release */
/* del plugin per rimanere aggiornato. */
/* Se vuoi che le prossime release del plugin NON sovrascrivano il template che */
/* genera questo file, perdendo tutte le modifiche precedentemente effettuate, */
/* regola a false il flag di controllo flagOverwrite© del template stesso. */
/* (non quello del singolo file) */
/* flagOverwrite = true */

package it.algos.botbio

import it.algos.algos.DialogoController
import it.algos.algos.TipoDialogo
import it.algos.algoslib.Lib
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.springframework.dao.DataIntegrityViolationException

//--gestisce il download delle informazioni
class BioWikiController {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def exportService
    def logoService
    def eventoService
    def bioWikiService
    def grailsApplication
    def bioService

    def index() {
        redirect(action: 'list', params: params)
    } // fine del metodo

    //--mostra un avviso di spiegazione per l'operazione da compiere
    //--passa al metodo effettivo
    def create() {
        params.tipo = TipoDialogo.avviso
        params.avviso = 'Vengono cancellati tutti i records di biografie. Vengono importate tutte le voci dalla categoria: BioBot (oltre 250.000). Occorrono circa 4 minuti (per caricare la Categoria).'
        params.returnController = 'bioWiki'
        params.returnAction = 'createDopoPrimoAvviso'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--controlla che esista un collegamento di Login
    //--se esiste prosegue
    //--se non esiste, mostra un messaggio flash e torna alla lista generale
    def createDopoPrimoAvviso() {
        if (grailsApplication && grailsApplication.config.login) {
            flash.message = ''
            redirect(action: 'createDopoAvviso')
        } else {
            flash.error = 'Devi essere loggato per poter importare le voci dalla categoria Biobot (5.000 voci per Request)'
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    //--creazione iniziale
    //--controlla se esistono già
    //--mostra un dialogo di conferma per l'operazione da compiere
    //--importa (usa il nome-metodo create, perchè è il primo ed unico della lista standard)
    //--cancella tutti i records
    //--forza la rilettura di tutto
    //--aggiunge nuove voci
    //--aggiorna le voci esistenti
    //--carica i parametri del template Bio, leggendoli dalle voci della categoria
    def createDopoAvviso() {
        if (BioWiki.count() > 0) {
            params.tipo = TipoDialogo.conferma
            params.avviso = 'Esistono già delle biografie. Se prosegui verranno cancellate e riscritte. Si perdono i valori-chiave dei records. Ci vogliono parecchie ore. Sei sicuro di volerlo fare?'
            params.returnController = 'bioWiki'
            params.returnAction = 'createDopoConferma'
            redirect(controller: 'dialogo', action: 'box', params: params)
        } else {
            bioWikiService.importaWiki()
            flash.message = 'Operazione effettuata. Sono stati importate tutte le voci della categoria: BioBot.'
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    //--ritorno dal dialogo di conferma
    //--a seconda del valore ritornato come parametro, esegue o meno l'operazione
    //--importa (usa il nome-metodo create, perchè è il primo ed unico della lista standard)
    //--cancella tutti i records
    //--forza la rilettura di tutto
    //--aggiunge nuove voci
    //--aggiorna le voci esistenti
    //--carica i parametri del template Bio, leggendoli dalle voci della categoria
    def createDopoConferma() {
        String valore
        flash.message = 'Operazione annullata. Le voci biografiche non sono state importate.'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    bioWikiService.importaWiki()
                    flash.message = 'Operazione effettuata. Sono stati importate tutte le voci della categoria: BioBot.'
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        redirect(action: 'list')
    } // fine del metodo

    //--mostra un dialogo di conferma per l'operazione da compiere
    //--passa al metodo effettivo
    def elabora() {
        if (BioWiki.count() > 0) {
            params.tipo = TipoDialogo.conferma
            params.avviso = 'Elaborazione di tutte le biografie. Da BioWiki a BioGrails. Ci vogliono parecchie ore. Sei sicuro di volerlo fare?'
            params.returnController = 'bioWiki'
            params.returnAction = 'elaboraDopoConferma'
            redirect(controller: 'dialogo', action: 'box', params: params)
        } else {
            params.tipo = TipoDialogo.avviso
            params.avviso = 'Sorry, non ci sono voci biografiche da elaborare !'
            params.returnController = 'bio'
            redirect(controller: 'dialogo', action: 'box', params: params)
        }// fine del blocco if-else
    } // fine del metodo

    //--elaborazione dei dati da BioWiki a BioGrails
    def elaboraDopoConferma() {
        bioService.elabora()
        redirect(uri: '/')
    } // fine del metodo

    def list(Integer max) {
        params.max = Math.min(max ?: 1000, 1000)
        ArrayList menuExtra
        ArrayList campiLista
        def lista
        def campoSort
        String titoloLista
        int recordsTotali

//        <span class="menuButton"><g:link class="create" action="importa"><g:message code="Import"/></g:link></span>
//        <span class="menuButton"><g:link class="frecciagiu" action="aggiunge"><g:message code="Aggiunge"/></g:link></span>
//        <span class="menuButton"><g:link class="frecciagiu" action="aggiorna"><g:message code="Aggiorna"/></g:link></span>
//        <span class="menuButton"><g:link class="frecciagiu" action="cicloRidotto"><g:message code="Ciclo ridotto"/></g:link></span>
//        <span class="menuButton"><g:link class="frecciagiu" action="cicloCompleto"><g:message code="Ciclo completo"/></g:link></span>
//        <span class="menuButton"><g:link class="regola" action="regola"><g:message code="Regola"/></g:link></span>
//        <span class="menuButton"><g:link class="regola" action="formatta"><g:message code="Formatta"/></g:link></span>
//        <span class="menuButton"><g:link class="regola" action="soloListe"><g:message code="Liste attnaz"/></g:link></span>
//        <span class="menuButton"><g:link class="regola" action="cicloNuovoIniziale"><g:message code="Ciclo nuovo iniziale"/></g:link></span>
//        <span class="menuButton"><g:link class="regola" action="cicloNuovoContinua"><g:message code="Ciclo nuovo continua"/></g:link></span>

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        menuExtra = [
                [cont: 'bioWiki', action: 'aggiungeWiki', icon: 'frecciagiu', title: 'AggiungeWiki'],
                [cont: 'bioWiki', action: 'aggiornaWiki', icon: 'frecciagiu', title: 'AggiornaWiki'],
                [cont: 'bioWiki', action: 'elabora', icon: 'pippo', title: 'Elabora']
        ]
        // fine della definizione

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', titolo:'titoloVisibile', sort:'ordinamento']
        campiLista = [
                'pageid',
                [campo: 'wikiUrl', title: 'Wiki'],
                [campo: 'modificaWiki', title: 'Modificata'],
                [campo: 'letturaWiki', title: 'Letta'],
                'user',
                'size',
                [campo: 'sizeBio', title: 'Bio'],
                'extra',
                'graffe',
                'note',
                [campo: 'nascosto', title: 'Ref']]
        // fine della definizione

        //--regolazione dei campo di ordinamento
        //--regolazione dei parametri di ordinamento
        if (!params.sort) {
            if (campoSort) {
                params.sort = campoSort
            }// fine del blocco if
        }// fine del blocco if-else
        if (params.order) {
            if (params.order == 'asc') {
                params.order = 'desc'
            } else {
                params.order = 'asc'
            }// fine del blocco if-else
        } else {
            params.order = 'asc'
        }// fine del blocco if-else

        //--metodo di esportazione dei dati (eventuale)
        export(params)

        //--selezione dei records da mostrare
        //--per una lista filtrata (parziale), modificare i parametri
        //--oppure modificare il findAllByInteroGreaterThan()...
        lista = BioWiki.findAll(params)
        recordsTotali = BioWiki.count()

        //--calcola il numero di record
        titoloLista = 'Elenco di ' + Lib.Txt.formatNum(recordsTotali) + ' biografie'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'list', model: [
                bioWikiInstanceList: lista,
                bioWikiInstanceTotal: recordsTotali,
                menuExtra: menuExtra,
                titoloLista: titoloLista,
                campiLista: campiLista],
                params: params)
    } // fine del metodo

    //--metodo di esportazione dei dati
    //--funziona SOLO se il flag -usaExport- è true (iniettato e regolato in ExportBootStrap)
    //--se non si regola la variabile -titleReport- non mette nessun titolo al report
    //--se non si regola la variabile -records- esporta tutti i records
    //--se non si regola la variabile -fields- esporta tutti i campi
    def export = {
        String titleReport = new Date()
        def records = null
        List fields = null
        Map parameters

        if (exportService && servletContext.usaExport) {
            if (params?.format && params.format != 'html') {
                if (!records) {
                    records = BioWiki.list(params)
                }// fine del blocco if
                if (!fields) {
                    fields = new DefaultGrailsDomainClass(BioWiki.class).persistentProperties*.name
                }// fine del blocco if
                parameters = [title: titleReport]
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename=BioWiki.${params.extension}")
                exportService.export((String) params.format, response.outputStream, records, fields, [:], [:], parameters)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    //--mostra un dialogo di conferma per l'operazione da compiere
    //--passa al metodo effettivo
    def aggiungeWiki() {
        params.tipo = TipoDialogo.conferma
        params.avviso = 'Vengono aggiunte tutte le voci dalla categoria BioBot non ancora presenti nel database. Occorrono diverse ore. Nelle Preferenze può essere impostato un limite di voci da caricare.'
        params.returnController = 'bioWiki'
        params.returnAction = 'aggiungeWikiDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--aggiunge nuove voci
    //--aggiorna le voci esistenti
    //--carica i parametri del template Bio, leggendoli dalle voci della categoria
    def aggiungeWikiDopoConferma() {
        String valore
        flash.message = 'Operazione annullata. Le voci biografiche non sono state aggiunte.'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    if (grailsApplication && grailsApplication.config.login) {
                        bioWikiService.aggiungeWiki()
                    } else {
                        if (grailsApplication && grailsApplication.config.debug) {
                            bioWikiService.aggiungeWiki()
                            flash.message = 'Operazione effettuata. Sono stati importate tutte le voci della categoria: BioBot.'
                        } else {
                            flash.message = 'Devi essere loggato per poter scaricare la categoria Biobot'
                        }// fine del blocco if-else
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        redirect(action: 'list')
    } // fine del metodo

    //--aggiorna le voci esistenti
    //--carica i parametri del template Bio, leggendoli dalle voci della categoria
    def aggiornaWiki() {
        flash.message = "Inizio dell'aggiornamento delle voci; occorrono alcuni minuti"
        if (grailsApplication && grailsApplication.config.login) {
            bioWikiService.aggiornaWiki()
        } else {
            if (grailsApplication && grailsApplication.config.debug) {
                bioWikiService.aggiornaWiki()
            } else {
                flash.message = 'Devi essere loggato per poter scaricare la categoria Biobot'
            }// fine del blocco if-else
        }// fine del blocco if-else

        redirect(action: 'list')
    } // fine del metodo


    def save() {
        def bioWikiInstance = new BioWiki(params)

        if (!bioWikiInstance.save(flush: true)) {
            render(view: 'create', model: [bioWikiInstance: bioWikiInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        //--log della registrazione
        if (logoService && eventoService) {
            logoService.setInfo(request, eventoService.getNuovo(), 'Gac')
        }// fine del blocco if

        flash.message = message(code: 'default.created.message', args: [message(code: 'bioWiki.label', default: 'BioWiki'), bioWikiInstance.id])
        redirect(action: 'show', id: bioWikiInstance.id)
    } // fine del metodo

    def show(Long id) {
        def bioWikiInstance = BioWiki.get(id)

        if (!bioWikiInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bioWiki.label', default: 'BioWiki'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [bioWikiInstance: bioWikiInstance]
    } // fine del metodo

    def edit(Long id) {
        def bioWikiInstance = BioWiki.get(id)

        if (!bioWikiInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bioWiki.label', default: 'BioWiki'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [bioWikiInstance: bioWikiInstance]
    } // fine del metodo

    def update(Long id, Long version) {
        def bioWikiInstance = BioWiki.get(id)

        if (!bioWikiInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bioWiki.label', default: 'BioWiki'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        if (version != null) {
            if (bioWikiInstance.version > version) {
                bioWikiInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bioWiki.label', default: 'BioWiki')] as Object[],
                        "Another user has updated this BioWiki while you were editing")
                render(view: 'edit', model: [bioWikiInstance: bioWikiInstance])
                return
            }// fine del blocco if e fine anticipata del metodo
        }// fine del blocco if

        bioWikiInstance.properties = params

        if (!bioWikiInstance.save(flush: true)) {
            render(view: 'edit', model: [bioWikiInstance: bioWikiInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bioWiki.label', default: 'BioWiki'), bioWikiInstance.id])
        redirect(action: 'show', id: bioWikiInstance.id)
    } // fine del metodo

    def delete(Long id) {
        def bioWikiInstance = BioWiki.get(id)
        if (!bioWikiInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bioWiki.label', default: 'BioWiki'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        try {
            bioWikiInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bioWiki.label', default: 'BioWiki'), id])
            redirect(action: 'list')
        }// fine del blocco try
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bioWiki.label', default: 'BioWiki'), id])
            redirect(action: 'show', id: id)
        }// fine del blocco catch
    } // fine del metodo

} // fine della controller classe
