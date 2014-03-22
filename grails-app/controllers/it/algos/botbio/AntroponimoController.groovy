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
import it.algos.algospref.Preferenze
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.springframework.dao.DataIntegrityViolationException

class AntroponimoController {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def exportService
    def logoService
    def eventoService
    def antroponimoService

    def index() {
        redirect(action: 'list', params: params)
    } // fine del metodo

    //--importa (usa il nome-metodo create, perchè è il primo ed unico della lista standard)
    //--mostra un avviso di spiegazione per l'operazione da compiere
    //--passa al metodo effettivo
    def create() {
        params.tipo = TipoDialogo.conferma
        params.titolo = 'Creazione dalle voci'
        params.avviso = []
        params.avviso.add('Vengono cancellati tutti i precedenti records di antroponimi')
        params.avviso.add('Vengono creati tutti i records coi nomi presenti nelle voci (bioGrails)')
        params.avviso.add('Tempo indicativo: quattro ore')
        params.returnController = 'antroponimo'
        params.returnAction = 'costruisceDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--ritorno dal dialogo di conferma
    //--a seconda del valore ritornato come parametro, esegue o meno l'operazione
    //--cancella i precedenti records
    //--crea i records estraendoli dalle voci esistenti (bioGrails)
    def costruisceDopoConferma() {
        String valore
        flash.message = 'Operazione annullata. Antroponimi non modificati.'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    if (antroponimoService) {
                        antroponimoService.costruisce()
                    }// fine del blocco if
                    flash.message = 'Operazione effettuata. Sono stati creati gli antroponimi'
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        redirect(action: 'list')
    } // fine del metodo

    //--mostra un dialogo di conferma per l'operazione da compiere
    //--passa al metodo effettivo
    def elabora() {
        params.tipo = TipoDialogo.conferma
        params.titolo = 'Ciclo'
        params.avviso = []
        params.avviso.add('Upload dalle pagina antroponimi. Vengono create/aggiornate tutte le voci.')
        params.returnController = 'antroponimo'
        params.returnAction = 'elaboraDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--ritorno dal dialogo di conferma
    //--a seconda del valore ritornato come parametro, esegue o meno l'operazione
    //--crea/aggiorna le pagine antroponimi
    def elaboraDopoConferma() {
        String valore
        boolean debug = Preferenze.getBool((String) grailsApplication.config.debug)
        flash.message = 'Operazione annullata. Pagine antroponimi non modificate.'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    if (grailsApplication && grailsApplication.config.login) {
                        antroponimoService.elabora()
                        flash.message = 'Operazione effettuata. Sono stati creati/aggiornate le pagine antroponimi'
                    } else {
                        if (debug) {
                            antroponimoService.elabora()
                            flash.message = 'Operazione effettuata. Sono stati creati/aggiornate le pagine antroponimi'
                        } else {
                            flash.error = 'Devi essere loggato per poter caricare gli antroponimi'
                        }// fine del blocco if-else
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        redirect(action: 'list')
    } // fine del metodo


    def list(Integer max) {
        params.max = Math.min(max ?: 1000, 1000)
        ArrayList menuExtra
        ArrayList campiLista
        def lista
        def campoSort
        String titoloLista

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        menuExtra = [
                [cont: 'antroponimo', action: 'elabora', icon: 'frecciasu', title: 'Upload antroponimi'],
        ]
        // fine della definizione

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', title:'titoloVisibile', sort:'ordinamento']
        //--se vuoto, mostra i primi n (stabilito nel templates:scaffoldinf:list)
        //--    nell'ordine stabilito nella constraints della DomainClass
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
        lista = Antroponimo.findAll(params)

        //--calcola il numero di record
        titoloLista = 'Elenco di ' + lista.size() + ' records di antroponimi'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'list', model: [
                antroponimoInstanceList: lista,
                antroponimoInstanceTotal: lista.size(),
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
                    records = Antroponimo.list(params)
                }// fine del blocco if
                if (!fields) {
                    fields = new DefaultGrailsDomainClass(Antroponimo.class).persistentProperties*.name
                }// fine del blocco if
                parameters = [title: titleReport]
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename=Antroponimo.${params.extension}")
                exportService.export((String) params.format, response.outputStream, records, fields, [:], [:], parameters)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo


    def save() {
        def antroponimoInstance = new Antroponimo(params)

        if (!antroponimoInstance.save(flush: true)) {
            render(view: 'create', model: [antroponimoInstance: antroponimoInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        //--log della registrazione
        if (logoService && eventoService) {
            logoService.setInfo(request, eventoService.getNuovo(), 'Gac')
        }// fine del blocco if

        flash.message = message(code: 'default.created.message', args: [message(code: 'antroponimo.label', default: 'Antroponimo'), antroponimoInstance.id])
        redirect(action: 'show', id: antroponimoInstance.id)
    } // fine del metodo

    def show(Long id) {
        def antroponimoInstance = Antroponimo.get(id)

        if (!antroponimoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'antroponimo.label', default: 'Antroponimo'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [antroponimoInstance: antroponimoInstance]
    } // fine del metodo

    def edit(Long id) {
        def antroponimoInstance = Antroponimo.get(id)

        if (!antroponimoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'antroponimo.label', default: 'Antroponimo'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [antroponimoInstance: antroponimoInstance]
    } // fine del metodo

    def update(Long id, Long version) {
        def antroponimoInstance = Antroponimo.get(id)

        if (!antroponimoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'antroponimo.label', default: 'Antroponimo'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        if (version != null) {
            if (antroponimoInstance.version > version) {
                antroponimoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'antroponimo.label', default: 'Antroponimo')] as Object[],
                        "Another user has updated this Antroponimo while you were editing")
                render(view: 'edit', model: [antroponimoInstance: antroponimoInstance])
                return
            }// fine del blocco if e fine anticipata del metodo
        }// fine del blocco if

        antroponimoInstance.properties = params

        if (!antroponimoInstance.save(flush: true)) {
            render(view: 'edit', model: [antroponimoInstance: antroponimoInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        flash.message = message(code: 'default.updated.message', args: [message(code: 'antroponimo.label', default: 'Antroponimo'), antroponimoInstance.id])
        redirect(action: 'show', id: antroponimoInstance.id)
    } // fine del metodo

    def delete(Long id) {
        def antroponimoInstance = Antroponimo.get(id)
        if (!antroponimoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'antroponimo.label', default: 'Antroponimo'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        try {
            antroponimoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'antroponimo.label', default: 'Antroponimo'), id])
            redirect(action: 'list')
        }// fine del blocco try
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'antroponimo.label', default: 'Antroponimo'), id])
            redirect(action: 'show', id: id)
        }// fine del blocco catch
    } // fine del metodo

} // fine della controller classe
