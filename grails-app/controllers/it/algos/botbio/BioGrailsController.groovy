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

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.springframework.dao.DataIntegrityViolationException

//--gestisce l'upload delle informazioni
class BioGrailsController {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def exportService
    def logoService
    def eventoService

    def index() {
        redirect(action: 'list', params: params)
    } // fine del metodo

    def list(Integer max) {
        params.max = Math.min(max ?: 100, 100)
        ArrayList menuExtra
        ArrayList campiLista
        def lista
        def campoSort

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        menuExtra = []
        // fine della definizione

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', titolo:'titoloVisibile', sort:'ordinamento']
        campiLista = [
                'pageid',
                'title',
                'nome',
                'cognome',
                'attivita',
                'attivita2',
                'attivita3',
                'nazionalita']
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
        lista = BioGrails.findAll(params)

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'list', model: [
                bioGrailsInstanceList: lista,
                bioGrailsInstanceTotal: lista.size(),
                menuExtra: menuExtra,
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
                    records = BioGrails.list(params)
                }// fine del blocco if
                if (!fields) {
                    fields = new DefaultGrailsDomainClass(BioGrails.class).persistentProperties*.name
                }// fine del blocco if
                parameters = [title: titleReport]
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename=BioGrails.${params.extension}")
                exportService.export((String) params.format, response.outputStream, records, fields, [:], [:], parameters)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    def create() {
        [bioGrailsInstance: new BioGrails(params)]
    } // fine del metodo

    def save() {
        def bioGrailsInstance = new BioGrails(params)

        if (!bioGrailsInstance.save(flush: true)) {
            render(view: 'create', model: [bioGrailsInstance: bioGrailsInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        //--log della registrazione
        if (logoService && eventoService) {
            logoService.setInfo(request, eventoService.getNuovo(), 'Gac')
        }// fine del blocco if

        flash.message = message(code: 'default.created.message', args: [message(code: 'bioGrails.label', default: 'BioGrails'), bioGrailsInstance.id])
        redirect(action: 'show', id: bioGrailsInstance.id)
    } // fine del metodo

    def show(Long id) {
        def bioGrailsInstance = BioGrails.get(id)

        if (!bioGrailsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bioGrails.label', default: 'BioGrails'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [bioGrailsInstance: bioGrailsInstance]
    } // fine del metodo

    def edit(Long id) {
        def bioGrailsInstance = BioGrails.get(id)

        if (!bioGrailsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bioGrails.label', default: 'BioGrails'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [bioGrailsInstance: bioGrailsInstance]
    } // fine del metodo

    def update(Long id, Long version) {
        def bioGrailsInstance = BioGrails.get(id)

        if (!bioGrailsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bioGrails.label', default: 'BioGrails'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        if (version != null) {
            if (bioGrailsInstance.version > version) {
                bioGrailsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bioGrails.label', default: 'BioGrails')] as Object[],
                        "Another user has updated this BioGrails while you were editing")
                render(view: 'edit', model: [bioGrailsInstance: bioGrailsInstance])
                return
            }// fine del blocco if e fine anticipata del metodo
        }// fine del blocco if

        bioGrailsInstance.properties = params

        if (!bioGrailsInstance.save(flush: true)) {
            render(view: 'edit', model: [bioGrailsInstance: bioGrailsInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bioGrails.label', default: 'BioGrails'), bioGrailsInstance.id])
        redirect(action: 'show', id: bioGrailsInstance.id)
    } // fine del metodo

    def delete(Long id) {
        def bioGrailsInstance = BioGrails.get(id)
        if (!bioGrailsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bioGrails.label', default: 'BioGrails'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        try {
            bioGrailsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bioGrails.label', default: 'BioGrails'), id])
            redirect(action: 'list')
        }// fine del blocco try
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bioGrails.label', default: 'BioGrails'), id])
            redirect(action: 'show', id: id)
        }// fine del blocco catch
    } // fine del metodo

} // fine della controller classe
