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

package ${packageName}

import it.algos.algospref.Pref
import it.algos.algos.LibAlgos
import it.algos.algoslib.LibGrails
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.grails.plugin.filterpane.FilterPaneUtils
import org.springframework.dao.DataIntegrityViolationException

class ${className}Controller {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def filterPaneService
    def exportService
    def eventoService
    def logoService

    // flag per usare il filtro/ricerca/selezione in questo controller
    private static boolean USA_FILTER = true

    // flag per usare l'export in questo controller
    private static boolean USA_EXPORT = true

    private static int MAX = 20

    def index() {
        regolaFiltro()
        regolaExport()
        redirect(action: 'list', params: params)
    } // fine del metodo

    /**
     * Regola un flag (nei params) per mostrare o meno una finestra/dialogo
     * di ricerca/selezione/filtro dei records
     * Di default il flag usaFilter, è true
     *
     * Può essere disabilitato in maniera dinamica,
     * senza avviare/riavviare l'applicazione,
     * solo per questo controller,
     * creando nelle Pref una preferenza specifica solo per questo controller
     *
     * Può essere disabilitato in maniera statica,
     * avviando/riavviando l'applicazione,
     * solo per questo controller
     * cancellando il corpo di questo metodo
     * e regolando la variabile statica della classe
     *
     * Può essere disabilitato in maniera statica,
     * avviando/riavviando l'applicazione,
     * per tutta l'applicazione,
     * modificando il parametro servletContext.usaFilter,
     * contenuto nella classe FilterBootStrap
     *
     * (queste note si possono cancellare e rileggere nel file.txt README)
     */
    def regolaFiltro() {
        def risultato = Pref.getBool("usaFilter${className}")

        if (risultato != null && risultato instanceof Boolean) {
            USA_FILTER = risultato
        } else {
            if (servletContext.usaFilter != null && servletContext.usaFilter instanceof Boolean) {
                USA_FILTER = servletContext.usaFilter
            }// fine del blocco if
        }// fine del blocco if-else
    } // fine del metodo

    /**
     * Regola un flag per mostrare o meno una finestra/dialogo
     * di ricerca/selezione/filtro dei records
     * Di default il flag usaExport, è true
     *
     * Può essere disabilitato in maniera dinamica,
     * senza avviare/riavviare l'applicazione,
     * solo per questo controller,
     * creando nelle Pref una preferenza specifica solo per questo controller
     *
     * Può essere disabilitato in maniera statica,
     * avviando/riavviando l'applicazione,
     * solo per questo controller
     * cancellando il corpo di questo metodo
     * e regolando la variabile statica della classe
     *
     * Può essere disabilitato in maniera statica,
     * avviando/riavviando l'applicazione,
     * per tutta l'applicazione,
     * modificando il parametro servletContext.usaExport,
     * contenuto nella classe ExportBootStrap
     *
     * (queste note si possono cancellare e rileggere nel file.txt README)
     */
    def regolaExport() {
        def risultato = Pref.getBool("usaExport${className}")

        if (risultato != null && risultato instanceof Boolean) {
            USA_EXPORT = risultato
        } else {
            if (servletContext.usaExport != null && servletContext.usaExport instanceof Boolean) {
                USA_EXPORT = servletContext.usaExport
            }// fine del blocco if
        }// fine del blocco if-else
    } // fine del metodo

    def list(Integer max) {
        redirect(action: 'filter', params: params)
    } // fine del metodo

    //--filtro di selezione e ricerca
    def filter ={
        if (!params.max) params.max = MAX
        ArrayList menuExtra
        ArrayList campiLista
        def campoSort
        int recordsParziali
        int recordsTotali
        String titoloLista
        String titoloListaFiltrata

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        menuExtra = []
        params.menuExtra = menuExtra
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
        if (USA_EXPORT) {
            export(params)
        }// fine del blocco if

        //--selezione dei records da mostrare
        //--per una lista filtrata (parziale), appare il dialogo
        recordsParziali = filterPaneService.count(params, ${className})
        recordsTotali = ${className}.count()

        //--calcola il numero di record
        titoloLista = "Elenco di " + params.max + "/" + recordsParziali + " records di ${domainClass.propertyName}"
        titoloListaFiltrata = "Elenco di " + params.max + "/" + recordsParziali + " records filtrati di ${domainClass.propertyName}"

        //--presentazione della view (index), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 12)
        render(view: 'index',
                model: [${propertyName}List  : filterPaneService.filter(params, ${className}),
                        ${propertyName}Count : recordsParziali,
                        ${propertyName}Total : recordsTotali,
                        filterParams       : FilterPaneUtils.extractFilterParams(params),
                        usaFilter     : USA_FILTER,
                        usaExport     : USA_EXPORT,
                        menuExtra          : menuExtra,
                        titoloLista        : titoloLista,
                        titoloListaFiltrata: titoloListaFiltrata,
                        campiLista         : campiLista,
                        params             : params])
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

        if (exportService && USA_EXPORT) {
            if (params?.format && params.format != 'html') {
                if (!records) {
                    records = ${className}.list(params)
                }// fine del blocco if
                if (!fields) {
                    fields = new DefaultGrailsDomainClass(${className}.class).persistentProperties*.name
                }// fine del blocco if
                parameters = [title: titleReport]
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename=${className}.\${params.extension}")
                exportService.export((String) params.format, response.outputStream, records, fields, [:], [:], parameters)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    def create() {
        [${propertyName}: new ${className}(params)]
    } // fine del metodo

    def save() {
        def ${propertyName} = new ${className}(params)

        if (!${propertyName}.save(flush: true)) {
            render(view: 'create', model: [${propertyName}: ${propertyName}])
            return
        }// fine del blocco if e fine anticipata del metodo

        //--log della registrazione
        if (logoService && eventoService) {
            logoService.setInfo(request, eventoService.getNuovo(), 'Gac')
        }// fine del blocco if

        flash.message = message(code: 'default.created.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])
        redirect(action: 'list')
    } // fine del metodo

    def show(Long id) {
        def ${propertyName} = ${className}.get(id)
        boolean usaSpostamento = true
        if (FilterPaneUtils.extractFilterParams(params)) {
            usaSpostamento = false
        }// fine del blocco if

        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        render(view: 'show',
                model: [${propertyName} : ${propertyName},
                        usaSpostamento: usaSpostamento,
                        params        : params])
    } // fine del metodo

    def edit(Long id) {
        def ${propertyName} = ${className}.get(id)

        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        [${propertyName}: ${propertyName}]
    } // fine del metodo

    def update(Long id, Long version) {
        def ${propertyName} = ${className}.get(id)

        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        if (version != null) {
            if (${propertyName}.version > version) {<% def lowerCaseName = grails.util.GrailsNameUtils.getPropertyName(className) %>
                    ${propertyName}.errors.rejectValue("version", "default.optimistic.locking.failure",
                            [message(code: '${domainClass.propertyName}.label', default: '${className}')] as Object[],
                            "Another user has updated this ${className} while you were editing")
                render(view: 'edit', model: [${propertyName}: ${propertyName}])
                return
            }// fine del blocco if e fine anticipata del metodo
        }// fine del blocco if

        ${propertyName}.properties = params

        if (!${propertyName}.save(flush: true)) {
            render(view: 'edit', model: [${propertyName}: ${propertyName}])
            return
        }// fine del blocco if e fine anticipata del metodo

        flash.message = message(code: 'default.updated.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])
        redirect(action: 'list')
    } // fine del metodo

    def delete(Long id) {
        def ${propertyName} = ${className}.get(id)
        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        try {
            ${propertyName}.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: 'list')
        }// fine del blocco try
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: 'show', id: id)
        }// fine del blocco catch
    } // fine del metodo

    def moveFirst() {
        params.id = LibAlgos.primo(getClasse())
        redirect(action: 'show', params: params)
    } // fine del metodo

    def movePrec() {
        long pos = params.long('id')
        params.id = LibAlgos.prec(getClasse(), pos)
        redirect(action: 'show', params: params)
    } // fine del metodo

    def moveSucc() {
        long pos = params.long('id')
        params.id = LibAlgos.suc(getClasse(), pos)
        redirect(action: 'show', params: params)
    } // fine del metodo

    def moveLast() {
        params.id = LibAlgos.ultimo(getClasse())
        redirect(action: 'show', params: params)
    } // fine del metodo

    private Class getClasse() {
        return LibGrails.getDomainClazz(grailsApplication, '${className}')
    } // fine del metodo

} // fine della controller classe
