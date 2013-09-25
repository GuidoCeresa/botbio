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

import it.algos.algos.TipoDialogo
import it.algos.algoslib.Lib
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.springframework.dao.DataIntegrityViolationException

//--gestisce l'elaborazione dei dati
class BioController {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def exportService
    def logoService
    def eventoService
    def bioService
    def grailsApplication


    def index() {
        redirect(action: 'list', params: params)
    } // fine del metodo

    def list(Integer max) {
        params.max = Math.min(max ?: 1000, 1000)
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
        //--se vuoto, mostra i primi n (stabilito nel templates:scaffoldinf:list)
        //--    nell'ordine stabilito nella constraints della DomainClass
        campiLista = [
                'pageid',
                [campo: 'wikiUrl', title: 'Wiki'],
                'nome',
                'cognome',
                [campo: 'annoNascita', title: 'Nato'],
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
        lista = Bio.findAll(params)
        recordsTotali = Bio.count()

        //--calcola il numero di record
        titoloLista = 'Elenco di ' + Lib.Txt.formatNum(recordsTotali) + ' biografie'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'list', model: [
                bioInstanceList: lista,
                bioInstanceTotal: recordsTotali,
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
                    records = Bio.list(params)
                }// fine del blocco if
                if (!fields) {
                    fields = new DefaultGrailsDomainClass(Bio.class).persistentProperties*.name
                }// fine del blocco if
                parameters = [title: titleReport]
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename=Bio.${params.extension}")
                exportService.export((String) params.format, response.outputStream, records, fields, [:], [:], parameters)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo


    def save() {
        def bioInstance = new Bio(params)

        if (!bioInstance.save(flush: true)) {
            render(view: 'create', model: [bioInstance: bioInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        //--log della registrazione
        if (logoService && eventoService) {
            logoService.setInfo(request, eventoService.getNuovo(), 'Gac')
        }// fine del blocco if

        flash.message = message(code: 'default.created.message', args: [message(code: 'biografia.label', default: 'Bio'), bioInstance.id])
        redirect(action: 'show', id: bioInstance.id)
    } // fine del metodo

    def show(Long id) {
        def bioInstance = Bio.get(id)

        if (!bioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'biografia.label', default: 'Bio'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [bioInstance: bioInstance]
    } // fine del metodo

    def edit(Long id) {
        def bioInstance = Bio.get(id)

        if (!bioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'biografia.label', default: 'Bio'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [bioInstance: bioInstance]
    } // fine del metodo

    def update(Long id, Long version) {
        def bioInstance = Bio.get(id)

        if (!bioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'biografia.label', default: 'Bio'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        if (version != null) {
            if (bioInstance.version > version) {
                bioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'biografia.label', default: 'Bio')] as Object[],
                        "Another user has updated this Bio while you were editing")
                render(view: 'edit', model: [bioInstance: bioInstance])
                return
            }// fine del blocco if e fine anticipata del metodo
        }// fine del blocco if

        bioInstance.properties = params

        if (!bioInstance.save(flush: true)) {
            render(view: 'edit', model: [bioInstance: bioInstance])
            return
        }// fine del blocco if e fine anticipata del metodo

        flash.message = message(code: 'default.updated.message', args: [message(code: 'biografia.label', default: 'Bio'), bioInstance.id])
        redirect(action: 'show', id: bioInstance.id)
    } // fine del metodo

    def delete(Long id) {
        def bioInstance = Bio.get(id)
        if (!bioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'biografia.label', default: 'Bio'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        try {
            bioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'biografia.label', default: 'Bio'), id])
            redirect(action: 'list')
        }// fine del blocco try
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'biografia.label', default: 'Bio'), id])
            redirect(action: 'show', id: id)
        }// fine del blocco catch
    } // fine del metodo

} // fine della controller classe
