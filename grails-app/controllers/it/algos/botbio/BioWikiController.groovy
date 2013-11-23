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
import it.algos.algoslib.LibTesto
import it.algos.algoslib.LibTime
import it.algos.algospref.Preferenze
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.hibernate.SessionFactory
import org.springframework.dao.DataIntegrityViolationException

import java.sql.Timestamp

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
    def logWikiService
    SessionFactory sessionFactory

    def index() {
        redirect(action: 'list', params: params)
    } // fine del metodo

    //--importa (usa il nome-metodo create, perchè è il primo ed unico della lista standard)
    //--mostra un avviso di spiegazione per l'operazione da compiere
    //--passa al metodo effettivo
    def create() {
        params.tipo = TipoDialogo.avviso
        params.titolo = 'Importazione iniziale'
        params.avviso = []
        params.avviso.add('Vengono cancellati tutti i records di biografie')
        params.avviso.add('Vengono caricate (aggiunte) tutte le voci dalla categoria BioBot BioBot (oltre 250.000).')
        params.avviso.add('Occorrono diverse ore.')
        params.avviso.add('Nelle Preferenze può essere impostato un limite di voci da caricare (aggiungere).')
        params.avviso.add('Non aggiorna le voci esistenti')
        params.returnController = 'bioWiki'
        params.returnAction = 'importaDopoPrimoAvviso'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--controlla che esista un collegamento di Login
    //--se esiste prosegue
    //--se non esiste, mostra un messaggio flash e torna alla lista generale
    def importaDopoPrimoAvviso() {
        if (grailsApplication && grailsApplication.config.login) {
            flash.message = ''
            redirect(action: 'importaPrimoControllo')
        } else {
            flash.error = 'Devi essere loggato per poter importare le voci dalla categoria Biobot (5.000 voci per Request)'
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    //--creazione iniziale
    //--controlla se esistono già
    //--mostra un dialogo di conferma per l'operazione da compiere
    def importaPrimoControllo() {
        if (BioWiki.count() > 0) {
            params.tipo = TipoDialogo.conferma
            params.titolo = 'Importazione iniziale'
            params.avviso = []
            params.avviso.add('Esistono già delle biografie')
            params.avviso.add('Se prosegui verranno cancellate e riscritte')
            params.avviso.add('Si perdono i valori-chiave dei records')
            params.avviso.add('Occorrono diverse ore.')
            params.returnController = 'bioWiki'
            params.returnAction = 'importaSecondoControllo'
            redirect(controller: 'dialogo', action: 'box', params: params)
        } else {
            bioWikiService.importaWiki()
            flash.message = 'Operazione effettuata. Sono stati importate tutte le voci della categoria: BioBot.'
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    //--creazione iniziale
    //--mostra un secondo dialogo di conferma perché l'operazione è distruttiva
    //--mostra un dialogo di conferma per l'operazione da compiere
    def importaSecondoControllo() {
        params.tipo = TipoDialogo.conferma
        params.titolo = 'Importazione iniziale'
        params.alert = []
        params.alert.add('Se prosegui verranno cancellate e riscritte tutte le voci')
        params.alert.add('Si perdono i valori-chiave dei records')
        params.alert.add('Sei sicuro di volerl continuare?')
        params.returnController = 'bioWiki'
        params.returnAction = 'importaDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--ritorno dal dialogo di conferma
    //--a seconda del valore ritornato come parametro, esegue o meno l'operazione
    //--cancella tutti i records
    //--forza la rilettura di tutto
    //--aggiunge nuove voci
    //--aggiorna le voci esistenti
    //--carica i parametri del template Bio, leggendoli dalle voci della categoria
    def importaDopoConferma() {

        if (dialogoConfermato()) {
            bioWikiService.importaWiki()
            flash.message = 'Operazione effettuata. Sono stati importate tutte le voci della categoria: BioBot e creati i records BioWiki'
        }// fine del blocco if

        redirect(action: 'list')
    } // fine del metodo

    //--mostra un dialogo di conferma per l'operazione da compiere
    //--passa al metodo effettivo
    def aggiungeWiki() {
        params.tipo = TipoDialogo.conferma
        params.titolo = 'Aggiunta'
        params.avviso = []
        params.avviso.add('Vengono caricate (aggiunte) tutte le voci dalla categoria BioBot non ancora presenti nel database BioWiki.')
        params.avviso.add('Crea i nuovi records BioWiki')
        params.avviso.add('Occorrono diverse ore.')
        params.avviso.add('Nelle Preferenze può essere impostato un limite di voci da caricare (aggiungere).')
        params.avviso.add('Non modifica i records esistenti')
        params.avviso.add('Elabora i records BioWiki effettivamente aggiunti, creando i relativi records BioGrails')
        params.returnController = 'bioWiki'
        params.returnAction = 'aggiungeWikiDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--carica i parametri del template Bio, leggendoli dalle voci della categoria
    //--aggiunge nuovi records BioWiki
    //--non aggiorna i records BioWiki esistenti
    //--elabora i records BioWiki aggiunti, creando nuovi records BioGrails
    def aggiungeWikiDopoConferma() {

        if (dialogoConfermato()) {
            esegueAggiungeWiki()
        }// fine del blocco if

        redirect(action: 'list')
    } // fine del metodo

    //--mostra un dialogo di conferma per l'operazione da compiere
    //--passa al metodo effettivo
    def aggiornaWiki() {
        params.tipo = TipoDialogo.conferma
        params.titolo = 'Aggiornamento'
        params.avviso = []
        params.avviso.add("Vengono modificate (aggiornate) tutte le voci presenti nel database e modificate dopo l'ultimo check")
        params.avviso.add('Occorrono diverse ore.')
        params.avviso.add('Nelle Preferenze può essere impostato un limite di voci da modificare (aggiornare).')
        params.avviso.add('Non carica nuove voci, ma si basa solo su quelle già presenti nel database')
        params.avviso.add('Tipicamente (nel ciclo) viene eseguito subito dopo il metodo Aggiunge')
        params.returnController = 'bioWiki'
        params.returnAction = 'aggiornaWikiDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--carica i parametri del template Bio, leggendoli dalle voci della categoria
    //--aggiorna i records BioWiki esistenti
    def aggiornaWikiDopoConferma() {

        if (dialogoConfermato()) {
            esegueAggiornaWiki()
        }// fine del blocco if

        redirect(action: 'list')
    } // fine del metodo

    //--mostra un dialogo di conferma per l'operazione da compiere
    //--passa al metodo effettivo
    def cicloWiki() {
        params.tipo = TipoDialogo.conferma
        params.titolo = 'Ciclo'
        params.avviso = []
        params.avviso.add('Vengono caricate (aggiunte) tutte le voci dalla categoria BioBot non ancora presenti nel database.')
        params.avviso.add("Vengono modificate (aggiornate) tutte le voci presenti nel database BioWiki e modificate dopo l'ultimo check")
        params.avviso.add("Vengono elaborate (elabora) tutte le voci presenti nel database BioGrails e modificate dopo l'ultimo check")
        params.avviso.add('Nelle Preferenze può essere impostato un limite di voci da caricare (aggiungere).')
        params.returnController = 'bioWiki'
        params.returnAction = 'cicloWikiDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--ciclo di aggiunta ed aggiornamento ed elaborazione
    //--carica i parametri del template Bio, leggendoli dalle voci della categoria
    //--aggiunge nuovi records BioWiki
    //--elabora i records BioWiki aggiunti, creando nuovi records BioGrails
    //--aggiorna i records BioWiki esistenti
    //--elabora i records BioWiki modificati, modificando i records BioGrails
    def cicloWikiDopoConferma() {
        int aggiunte
        int modificate
        long inizio = System.currentTimeMillis()
        long fine
        long durata

        if (dialogoConfermato()) {
            aggiunte = esegueAggiungeWiki()
            modificate = esegueAggiornaWiki()
            fine = System.currentTimeMillis()
            durata = fine - inizio
            LibBio.gestVoci(logWikiService, false, durata, aggiunte, modificate)
        }// fine del blocco if

        redirect(action: 'list')
    } // fine del metodo

    //--controllo del dialogo
    //--controlla la risposta dei bottoni del dialogo
    //--controlla il login
    private boolean dialogoConfermato() {
        boolean isDialogoConfermato = false
        String valore
        boolean debug = Preferenze.getBool((String) grailsApplication.config.debug)
        flash.message = 'Operazione annullata. Le voci biografiche non sono state modificate.'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    if (grailsApplication && grailsApplication.config.login) {
                        isDialogoConfermato = true
                    } else {
                        if (debug) {
                            isDialogoConfermato = true
                        } else {
                            flash.message = 'Devi essere loggato per poter scaricare la categoria Biobot'
                        }// fine del blocco if-else
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        return isDialogoConfermato
    } // fine del metodo

    //--ciclo di aggiunta ed elaborazione
    //--carica i parametri del template Bio, leggendoli dalle voci della categoria
    //--aggiunge nuovi records BioWiki
    //--elabora i records BioWiki aggiunti, creando nuovi records BioGrails
    private int esegueAggiungeWiki() {
        ArrayList<Integer> listaNuoviRecordsCreati
        int aggiunte = 0
        String numVociTxt = ''
        flash.message = 'Operazione annullata. Il ciclo non è partito.'

        listaNuoviRecordsCreati = bioWikiService.aggiungeWiki()
        if (listaNuoviRecordsCreati) {
            bioService.elabora(listaNuoviRecordsCreati)
            aggiunte = listaNuoviRecordsCreati.size()
        }// fine del blocco if
        if (aggiunte == 0) {
            flash.message = 'Non ci sono nuove voci nella categoria. Non sono state aggiunti nuovi records BioWiki'
        } else {
            numVociTxt = LibTesto.formatNum(aggiunte)
            flash.message = "Sono state aggiunti ed elaborati ${numVociTxt} nuovi records BioWiki e BioGrails"
        }// fine del blocco if-else

        return listaNuoviRecordsCreati.size()
    } // fine del metodo

    //--ciclo di aggiornamento ed elaborazione
    //--carica i parametri del template Bio, leggendoli dalle voci della categoria
    //--aggiorna i records BioWiki esistenti
    //--elabora i records BioWiki modificati, modificando i records BioGrails
    private int esegueAggiornaWiki() {
        ArrayList<Integer> listaRecordsModificati
        int modificate = 0
        String numVociTxt = ''
        ArrayList listaTimestamp
        String oldDataTxt = ''
        flash.message = ''

        listaRecordsModificati = bioWikiService.aggiornaWiki()
        if (listaRecordsModificati) {
            bioService.elabora(listaRecordsModificati)
            modificate = listaRecordsModificati.size()
        }// fine del blocco if
        flash.messages = []
        if (modificate == 0) {
            flash.messages.add('Le voci presenti nel database erano già aggiornate. Non è stato modificato nulla')
        } else {
            numVociTxt = LibTesto.formatNum(modificate)
            flash.messages.add("Sono stati aggiornati ed elaborati ${numVociTxt} records BioWiki e BioGrails già presenti nel database")
        }// fine del blocco if-else
        listaTimestamp = BioWiki.executeQuery('select letturaWiki from BioWiki order by letturaWiki asc')
        if (listaTimestamp && listaTimestamp.size() > 0) {
            Timestamp oldStamp = (Timestamp) listaTimestamp[0]
            def oldData = LibTime.creaData(oldStamp)
            oldDataTxt = LibTime.getGioMeseAnno(oldData)
        }// fine del blocco if
        flash.messages.add("La voce più vecchia non aggiornata è del ${oldDataTxt}")

        return listaRecordsModificati.size()
    } // fine del metodo

    //--redirect
    def elabora() {
        redirect(controller: 'bioGrails', action: 'create')
    } // fine del metodo

    def list(Integer max) {
        params.max = Math.min(max ?: 1000, 1000)
        ArrayList menuExtra
        ArrayList campiLista
        def lista
        def campoSort
        String titoloLista
        int recordsTotali

//        <span class="menuButton"><g:link class="regola" action="regola"><g:message code="Regola"/></g:link></span>
//        <span class="menuButton"><g:link class="regola" action="formatta"><g:message code="Formatta"/></g:link></span>
//        <span class="menuButton"><g:link class="regola" action="soloListe"><g:message code="Liste attnaz"/></g:link></span>

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        menuExtra = [
                [cont: 'bioWiki', action: 'aggiungeWiki', icon: 'frecciagiu', title: 'AggiungeWiki'],
                [cont: 'bioWiki', action: 'aggiornaWiki', icon: 'frecciagiu', title: 'AggiornaWiki'],
                [cont: 'bioWiki', action: 'cicloWiki', icon: 'frecciagiu', title: 'CicloWiki'],
//                [cont: 'bioWiki', action: 'elabora', icon: 'pippo', title: 'Elabora'],
                [cont: 'bioGrails', action: 'list', icon: 'scambia', title: 'BioGrails']
        ]
        // fine della definizione

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', title:'titoloVisibile', sort:'ordinamento']
        campiLista = [
                'pageid',
                [campo: 'wikiUrl', title: 'Wiki'],
                [campo: 'modificaWiki', title: 'Modificata'],
                [campo: 'letturaWiki', title: 'Letta'],
                'size',
                [campo: 'sizeBio', title: 'Bio'],
                'extra',
                'graffe',
                'note',
                [campo: 'nascosto', title: 'Ref'],
                [campo: 'incompleta', title: 'KO'],
                [campo: 'elaborata', title: 'Grails']
        ]
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

        //--calcola il numero di record
        recordsTotali = BioWiki.count()

        //--titolo visibile sopra la table dei dati
        titoloLista = 'Elenco di ' + Lib.Txt.formatNum(recordsTotali) + ' biografie (fotocopia originali wiki)'

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
