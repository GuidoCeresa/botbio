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

class AnnoController {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def exportService
    def logoService
    def eventoService
    def annoService
    def bioGrailsService

    def index() {
        redirect(action: 'list', params: params)
    } // fine del metodo

    //--mostra un avviso di spiegazione per l'operazione da compiere
    //--passa al metodo effettivo
    def create() {
        params.tipo = TipoDialogo.avviso
        params.avviso = 'Vengono creati tutti gli anni necessari per il funzionamento del bot. Dal 1.000 a.C. al 2030.'
        params.returnController = 'anno'
        params.returnAction = 'createDopoAvviso'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--creazione iniziale
    //--controlla se esistono già
    //--mostra un dialogo di conferma per l'operazione da compiere
    //--resetta i valori della tavola
    //--cosa che provoca una perdita delle chiavi ids
    def createDopoAvviso() {
        if (Anno.count() > 0) {
            params.tipo = TipoDialogo.conferma
            params.avviso = 'Gli anni esistono già. Se prosegui vengono cancellati e riscritti. Si perdono i valori-chiave dei records. Sei sicuro di volerlo fare?'
            params.returnController = 'anno'
            params.returnAction = 'createDopoConferma'
            redirect(controller: 'dialogo', action: 'box', params: params)
        } else {
            annoService.creazioneIniziale()
            flash.message = 'Operazione effettuata. Sono stati creati tutti gli anni necessari per il bot.'
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    //--ritorno dal dialogo di conferma
    //--a seconda del valore ritornato come parametro, esegue o meno l'operazione
    //--resetta i valori della tavola
    //--cosa che provoca una perdita delle chiavi ids
    def createDopoConferma() {
        String valore
        flash.message = 'Operazione annullata. Gli anni non sono stati modificati.'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    annoService.creazioneIniziale()
                    flash.message = 'Operazione effettuata. Sono stati creati tutti gli anni necessari per il bot.'
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        redirect(action: 'list')
    } // fine del metodo

    //--mostra un avviso di conferma per l'operazione da compiere
    def sporca() {
        params.tipo = TipoDialogo.conferma
        params.avviso = 'Normalmente il bot sporca SOLO gli anni relativi alle voci bio modificate. Sei sicuro di voler sporcare tutto?'
        params.returnController = 'anno'
        params.returnAction = 'sporcaDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--ritorno dal dialogo di conferma
    //--a seconda del valore ritornato come parametro, esegue o meno l'operazione
    //--forza a true tutti i records
    def sporcaDopoConferma() {
        String valore
        flash.message = 'Operazione annullata. Gli anni non sono stati sporcati'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    annoService.regolaSporco(true)
                    flash.message = 'Operazione effettuata. Sono stati sporcati tutti gli anni.'
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        redirect(action: 'list')
    } // fine del metodo

    //--mostra un avviso di conferma per l'operazione da compiere
    def pulisce() {
        params.tipo = TipoDialogo.conferma
        params.avviso = 'Normalmente il bot pulisce tutti gli anni DOPO il ciclo di registrazione giorni/anni. Sei sicuro di volerli pulire adesso?'
        params.returnController = 'anno'
        params.returnAction = 'pulisceDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--ritorno dal dialogo di conferma
    //--a seconda del valore ritornato come parametro, esegue o meno l'operazione
    //--forza a false tutti i records
    def pulisceDopoConferma() {
        String valore
        flash.message = 'Operazione annullata. Gli anni non sono stati puliti'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    annoService.regolaSporco(false)
                    flash.message = 'Operazione effettuata. Sono stati puliti tutti gli anni.'
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        redirect(action: 'list')
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti gli anni modificati (solo nascita)
    //--passa al metodo effettivo senza nessun dialogo di conferma
    def uploadAnniNascita() {
        if (grailsApplication && grailsApplication.config.login) {
            bioGrailsService.uploadAnniNascita()
        } else {
            flash.error = 'Devi essere loggato per effettuare un upload di pagine sul server wiki'
        }// fine del blocco if-else
        redirect(action: 'list')
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti gli anni modificati (solo morte)
    //--passa al metodo effettivo senza nessun dialogo di conferma
    def uploadAnniMorte() {
        if (grailsApplication && grailsApplication.config.login) {
            bioGrailsService.uploadAnniMorte()
        } else {
            flash.error = 'Devi essere loggato per effettuare un upload di pagine sul server wiki'
        }// fine del blocco if-else
        redirect(action: 'list')
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti gli anni modificati
    //--passa al metodo effettivo senza nessun dialogo di conferma
    def uploadAllAnni() {
        if (grailsApplication && grailsApplication.config.login) {
            bioGrailsService.uploadAnniNascita()
            bioGrailsService.uploadAnniMorte()
        } else {
            flash.error = 'Devi essere loggato per effettuare un upload di pagine sul server wiki'
        }// fine del blocco if-else
        redirect(action: 'list')
    } // fine del metodo

    def list(Integer max) {
        params.max = Math.min(max ?: 100, 100)
        ArrayList menuExtra
        ArrayList campiLista
        def lista
        def campoSort
        String titoloLista
        int recordsTotali

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        menuExtra = [
                [action: 'sporca',
                        icon: 'list',
                        title: 'Sporca tutto'],
                [action: 'pulisce',
                        icon: 'list',
                        title: 'Pulisce tutto'],
                [cont: 'bioGrails', action: 'uploadAnniNascita', icon: 'frecciasu', title: 'Upload nascita'],
                [cont: 'bioGrails', action: 'uploadAnniMorte', icon: 'frecciasu', title: 'Upload morte'],
                [cont: 'bioGrails', action: 'uploadAllAnni', icon: 'frecciasu', title: 'Upload all anni']
        ]
        // fine della definizione

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', titolo:'titoloVisibile', sort:'ordinamento']
        campiLista = []
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
        lista = Anno.list(params)
        recordsTotali = Anno.count()

        //--calcola il numero di record
        titoloLista = 'Elenco di ' + Lib.Txt.formatNum(recordsTotali) + ' anni'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'list', model: [
                annoInstanceList: lista,
                annoInstanceTotal: recordsTotali,
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
                    records = Anno.list(params)
                }// fine del blocco if
                if (!fields) {
                    fields = new DefaultGrailsDomainClass(Anno.class).persistentProperties*.name
                }// fine del blocco if
                parameters = [title: titleReport]
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename=Anno.${params.extension}")
                exportService.export((String) params.format, response.outputStream, records, fields, [:], [:], parameters)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo


    def save() {
        def annoInstance = new Anno(params)

        if (!annoInstance.save(flush: true)) {
            render(view: 'create', model: [annoInstance: annoInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        //--log della registrazione
        if (logoService && eventoService) {
            logoService.setInfo(request, eventoService.getNuovo(), 'Gac')
        }// fine del blocco if

        flash.message = message(code: 'default.created.message', args: [message(code: 'anno.label', default: 'Anno'), annoInstance.id])
        redirect(action: 'show', id: annoInstance.id)
    } // fine del metodo

    def show(Long id) {
        def annoInstance = Anno.get(id)

        if (!annoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anno.label', default: 'Anno'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [annoInstance: annoInstance]
    } // fine del metodo

    def edit(Long id) {
        def annoInstance = Anno.get(id)

        if (!annoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anno.label', default: 'Anno'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [annoInstance: annoInstance]
    } // fine del metodo

    def update(Long id, Long version) {
        def annoInstance = Anno.get(id)

        if (!annoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anno.label', default: 'Anno'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        if (version != null) {
            if (annoInstance.version > version) {
                annoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'anno.label', default: 'Anno')] as Object[],
                        "Another user has updated this Anno while you were editing")
                render(view: 'edit', model: [annoInstance: annoInstance])
                return
            }// fine del blocco if e fine anticipata del metodo
        }// fine del blocco if

        annoInstance.properties = params

        if (!annoInstance.save(flush: true)) {
            render(view: 'edit', model: [annoInstance: annoInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        flash.message = message(code: 'default.updated.message', args: [message(code: 'anno.label', default: 'Anno'), annoInstance.id])
        redirect(action: 'show', id: annoInstance.id)
    } // fine del metodo

    def delete(Long id) {
        def annoInstance = Anno.get(id)
        if (!annoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anno.label', default: 'Anno'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        try {
            annoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'anno.label', default: 'Anno'), id])
            redirect(action: 'list')
        }// fine del blocco try
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'anno.label', default: 'Anno'), id])
            redirect(action: 'show', id: id)
        }// fine del blocco catch
    } // fine del metodo

} // fine della controller classe
