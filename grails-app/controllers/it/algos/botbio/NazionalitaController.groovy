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

class NazionalitaController {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def exportService
    def logoService
    def eventoService
    def nazionalitaService

    def index() {
        redirect(action: 'list', params: params)
    } // fine del metodo

    //--mostra un avviso di spiegazione per l'operazione da compiere
    //--passa al metodo effettivo
    def create() {
        params.tipo = TipoDialogo.conferma
        params.avviso = 'Download dalla pagina Template:Bio/plurale_nazionalità. Vengono aggiunte nuove nazionalità e aggiornate quelle esistenti. Vuoi procedere?'
        params.returnController = 'nazionalita'
        params.returnAction = 'downloadDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--ritorno dal dialogo di conferma
    //--a seconda del valore ritornato come parametro, esegue o meno l'operazione
    //--aggiorna i records leggendoli dalla pagina wiki
    //--modifica i valori esistenti
    //--aggiunge nuovi valori
    def downloadDopoConferma() {
        String valore
        flash.message = 'Operazione annullata. Download non eseguito.'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    nazionalitaService.download()
                    flash.message = 'Operazione effettuata. Sono stati aggiornati i valori delle nazionalità'
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

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
        menuExtra = []
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
        lista = Nazionalita.findAll(params)
        recordsTotali = Nazionalita.count()

        //--calcola il numero di record
        titoloLista = 'Elenco di ' + Lib.Txt.formatNum(recordsTotali) + ' nazionalità'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'list', model: [
                nazionalitaInstanceList: lista,
                nazionalitaInstanceTotal: recordsTotali,
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
                    records = Nazionalita.list(params)
                }// fine del blocco if
                if (!fields) {
                    fields = new DefaultGrailsDomainClass(Nazionalita.class).persistentProperties*.name
                }// fine del blocco if
                parameters = [title: titleReport]
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename=Nazionalita.${params.extension}")
                exportService.export((String) params.format, response.outputStream, records, fields, [:], [:], parameters)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo


    def save() {
        def nazionalitaInstance = new Nazionalita(params)

        if (!nazionalitaInstance.save(flush: true)) {
            render(view: 'create', model: [nazionalitaInstance: nazionalitaInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        //--log della registrazione
        if (logoService && eventoService) {
            logoService.setInfo(request, eventoService.getNuovo(), 'Gac')
        }// fine del blocco if

        flash.message = message(code: 'default.created.message', args: [message(code: 'nazionalita.label', default: 'Nazionalita'), nazionalitaInstance.id])
        redirect(action: 'show', id: nazionalitaInstance.id)
    } // fine del metodo

    def show(Long id) {
        def nazionalitaInstance = Nazionalita.get(id)

        if (!nazionalitaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'nazionalita.label', default: 'Nazionalita'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [nazionalitaInstance: nazionalitaInstance]
    } // fine del metodo

    def edit(Long id) {
        def nazionalitaInstance = Nazionalita.get(id)

        if (!nazionalitaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'nazionalita.label', default: 'Nazionalita'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [nazionalitaInstance: nazionalitaInstance]
    } // fine del metodo

    def update(Long id, Long version) {
        def nazionalitaInstance = Nazionalita.get(id)

        if (!nazionalitaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'nazionalita.label', default: 'Nazionalita'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        if (version != null) {
            if (nazionalitaInstance.version > version) {
                nazionalitaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'nazionalita.label', default: 'Nazionalita')] as Object[],
                        "Another user has updated this Nazionalita while you were editing")
                render(view: 'edit', model: [nazionalitaInstance: nazionalitaInstance])
                return
            }// fine del blocco if e fine anticipata del metodo
        }// fine del blocco if

        nazionalitaInstance.properties = params

        if (!nazionalitaInstance.save(flush: true)) {
            render(view: 'edit', model: [nazionalitaInstance: nazionalitaInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        flash.message = message(code: 'default.updated.message', args: [message(code: 'nazionalita.label', default: 'Nazionalita'), nazionalitaInstance.id])
        redirect(action: 'show', id: nazionalitaInstance.id)
    } // fine del metodo

    def delete(Long id) {
        def nazionalitaInstance = Nazionalita.get(id)
        if (!nazionalitaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'nazionalita.label', default: 'Nazionalita'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        try {
            nazionalitaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'nazionalita.label', default: 'Nazionalita'), id])
            redirect(action: 'list')
        }// fine del blocco try
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'nazionalita.label', default: 'Nazionalita'), id])
            redirect(action: 'show', id: id)
        }// fine del blocco catch
    } // fine del metodo

} // fine della controller classe
