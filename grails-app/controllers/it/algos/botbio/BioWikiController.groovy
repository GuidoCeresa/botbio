/* Created by Gac */
/* Date: mag 2013 */
/* Il plugin  ha creato o sovrascritto il templates che ha creato questo file. */
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
import it.algos.algospref.Preferenze
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.hibernate.SessionFactory
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
    def logWikiService
    def attivitaService
    def nazionalitaService
    def professioneService
    SessionFactory sessionFactory

    def index() {
        redirect(action: 'list', params: params)
    } // fine del metodo

    //--mostra un avviso di spiegazione per l'operazione da compiere
    //--passa al metodo effettivo
    def importa() {
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
        params.avviso.add('Download dalla pagina Modulo:Bio/Plurale attività. Vengono aggiunte nuove attività e aggiornate quelle esistenti.')
        params.avviso.add('Download dalla pagina Modulo:Bio/Plurale nazionalità. Vengono aggiunte nuove nazionalità e aggiornate quelle esistenti.')
        params.avviso.add('Download dalla pagina Modulo:Bio/Link attività. Vengono aggiunte nuove professioni e aggiornate quelle esistenti.')
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
        HashMap mappa
        int aggiunti = 0
        int cancellati = 0
        int modificati
        long inizio = System.currentTimeMillis()
        long fine
        long durata

        if (dialogoConfermato()) {
            attivitaService.download()
            nazionalitaService.download()
            professioneService.download()
            mappa = esegueAggiungeWiki()
            if (mappa && mappa.get(LibBio.AGGIUNTI)) {
                aggiunti = mappa.get(LibBio.AGGIUNTI).size()
            }// fine del blocco if
            if (mappa && mappa.get(LibBio.CANCELLATI)) {
                cancellati = mappa.get(LibBio.CANCELLATI).size()
            }// fine del blocco if
            modificati = esegueAggiornaWiki()
            fine = System.currentTimeMillis()
            durata = fine - inizio
            LibBio.gestVoci(logWikiService, false, durata, aggiunti, cancellati, modificati)
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
    private HashMap esegueAggiungeWiki() {
        int aggiunti = 0
        HashMap mappa
        ArrayList<Integer> listaNuoviRecordsAggiunti
        String numVociTxt = ''
        flash.message = 'Operazione annullata. Il ciclo non è partito.'

        mappa = bioWikiService.aggiungeWiki()
        if (mappa && mappa.get(LibBio.AGGIUNTI)) {
            listaNuoviRecordsAggiunti = (ArrayList<Integer>) mappa.get(LibBio.AGGIUNTI)
        }// fine del blocco if

        if (listaNuoviRecordsAggiunti) {
            bioService.elabora(listaNuoviRecordsAggiunti)
            aggiunti = listaNuoviRecordsAggiunti.size()
        }// fine del blocco if
        if (aggiunti == 0) {
            flash.message = 'Non ci sono nuove voci nella categoria. Non sono state aggiunti nuovi records BioWiki'
        } else {
            numVociTxt = LibTesto.formatNum(aggiunti)
            flash.message = "Sono state aggiunti ed elaborati ${numVociTxt} nuovi records BioWiki e BioGrails"
        }// fine del blocco if-else

        return mappa
    } // fine del metodo

    //--ciclo di aggiornamento ed elaborazione
    //--carica i parametri del template Bio, leggendoli dalle voci della categoria
    //--aggiorna i records BioWiki esistenti
    //--elabora i records BioWiki modificati, modificando i records BioGrails
    private int esegueAggiornaWiki() {
        ArrayList<Integer> listaRecordsModificati
        int modificati = 0
        String numVociTxt = ''
        String oldDataTxt
        flash.messages = []

        listaRecordsModificati = bioWikiService.aggiornaWiki()
        if (listaRecordsModificati) {
            bioService.elabora(listaRecordsModificati)
            modificati = listaRecordsModificati.size()
        }// fine del blocco if
        if (modificati == 0) {
            flash.messages.add('Le voci presenti nel database erano già aggiornate. Non è stato modificato nulla')
        } else {
            numVociTxt = LibTesto.formatNum(modificati)
            flash.messages.add("Sono stati aggiornati ed elaborati ${numVociTxt} records BioWiki e BioGrails già presenti nel database")
        }// fine del blocco if-else
        oldDataTxt = LibBio.voceAggiornataVecchia()
        flash.messages.add(oldDataTxt)

        return listaRecordsModificati.size()
    } // fine del metodo

    //--redirect
    def elabora() {
        redirect(controller: 'bioGrails', action: 'create')
    } // fine del metodo

    def list(Integer max) {
        params.max = Math.min(max ?: 100, 100)
        ArrayList menuExtra
        ArrayList campiLista
        def lista
        def campoSort
        String titoloLista
        int recordsTotali
        def noMenuCreate = true

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        menuExtra = [
                [cont: 'bioWiki', action: 'importa', icon: 'database', title: 'Importa'],
                [cont: 'bioWiki', action: 'aggiungeWiki', icon: 'frecciagiu', title: 'AggiungeWiki'],
                [cont: 'bioWiki', action: 'aggiornaWiki', icon: 'frecciagiu', title: 'AggiornaWiki'],
                [cont: 'bioWiki', action: 'cicloWiki', icon: 'frecciagiu', title: 'CicloWiki'],
                [cont: 'bioGrails', action: 'resetElabora', icon: 'database', title: 'Reset elabora'],
                [cont: 'bioGrails', action: 'elaboraAll', icon: 'database', title: 'Elabora'],
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
        if (params.nome) {
            lista = BioWiki.findAllByNome(params.nome, params)
        } else {
            if (params.cognome) {
                lista = BioWiki.findAllByCognome(params.cognome, params)
            } else {
                lista = BioWiki.findAll(params)
            }// fine del blocco if-else
        }// fine del blocco if-else

        //--calcola il numero di record
        recordsTotali = lista.size()

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
                campiLista: campiLista,
                noMenuCreate: noMenuCreate],
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

    def download() {
        int pageid = 0
        def bioWikiInstance = null
        flash.messages = []

        if (params.id) {
            pageid = Integer.decode(params.id)
        }// fine del blocco if

        if (pageid) {
            bioWikiInstance = BioWiki.findByPageid(pageid)
        }// fine del blocco if

        if (pageid && bioWikiService) {
            bioWikiService.download(pageid)
        }// fine del blocco if

        if (bioWikiInstance) {
            flash.messages.add("Il record del database su ${bioWikiInstance.title} è stato aggiornato scaricando la voce del server wiki")
            redirect(action: 'show', id: bioWikiInstance.id)
        } else {
            flash.error = 'Non sono riuscito a scaricare la pagina dal server wiki'
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    def elaboraSingola() {
        int pageid = 0
        def bioWikiInstance = null
        flash.messages = []

        if (params.id) {
            pageid = Integer.decode(params.id)
        }// fine del blocco if

        if (pageid) {
            bioWikiInstance = BioWiki.findByPageid(pageid)
        }// fine del blocco if

        if (pageid && bioService) {
            bioService.elabora(pageid)
        }// fine del blocco if

        if (bioWikiInstance) {
            flash.messages.add("Il record di ${bioWikiInstance.title} è stato elaborato ed è aggiornato su BioGrails")
            redirect(action: 'show', id: bioWikiInstance.id)
        } else {
            flash.error = 'Non sono riuscito ad elaborare il record'
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    def fix() {
        int pageid = 0
        def bioWikiInstance = null
        flash.messages = []

        if (params.id) {
            pageid = Integer.decode(params.id)
        }// fine del blocco if

        if (pageid) {
            bioWikiInstance = BioWiki.findByPageid(pageid)
        }// fine del blocco if

        if (pageid && bioWikiService && bioWikiService) {
            bioWikiService.download(pageid)
            bioService.elabora(pageid)
        }// fine del blocco if

        if (bioWikiInstance) {
            flash.messages.add("Il record del database su ${bioWikiInstance.title} è stato aggiornato scaricando la voce del server wiki")
            flash.messages.add("Il record di ${bioWikiInstance.title} è stato elaborato ed è aggiornato su BioGrails")
            redirect(action: 'show', id: bioWikiInstance.id)
        } else {
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    def upload() {
        if (grailsApplication && grailsApplication.config.login) {
            esegueUpload()
        } else {
            flash.error = 'Devi essere loggato per poter eseguire un upload'
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    def esegueUpload() {
        int pageid = 0
        def bioWikiInstance = null
        flash.messages = []

        if (params.id) {
            pageid = Integer.decode(params.id)
        }// fine del blocco if

        if (pageid) {
            bioWikiInstance = BioWiki.findByPageid(pageid)
        }// fine del blocco if

        if (pageid && bioService) {
            bioService.uploadWiki(pageid)
            bioWikiService.download(pageid)
            bioService.elabora(pageid)
        }// fine del blocco if

        if (bioWikiInstance) {
            flash.messages.add("Il record di ${bioWikiInstance.title} è stato caricato sul server wiki aggiornando la voce")
            flash.messages.add("Il record del database su ${bioWikiInstance.title} è stato aggiornato scaricando la voce del server wiki")
            flash.messages.add("Il record di ${bioWikiInstance.title} è stato elaborato ed è aggiornato su BioGrails")
            redirect(action: 'show', id: bioWikiInstance.id)
        } else {
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    def ciclo() {
        if (grailsApplication && grailsApplication.config.login) {
            esegueCiclo()
        } else {
            flash.error = 'Devi essere loggato per poter eseguire un ciclo completo'
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    def esegueCiclo() {
        int pageid = 0
        def bioWikiInstance = null
        flash.messages = []

        if (params.id) {
            pageid = Integer.decode(params.id)
        }// fine del blocco if

        if (pageid) {
            bioWikiInstance = BioWiki.findByPageid(pageid)
        }// fine del blocco if

        if (pageid && bioService) {
            bioWikiService.download(pageid)
            bioService.uploadWiki(pageid)
            bioWikiService.download(pageid)
            bioService.elabora(pageid)
        }// fine del blocco if

        if (bioWikiInstance) {
            flash.messages.add("Il record del database su ${bioWikiInstance.title} è stato aggiornato scaricando la voce del server wiki")
            flash.messages.add("Il record di ${bioWikiInstance.title} è stato caricato sul server wiki aggiornando la voce")
            flash.messages.add("Il record del database su ${bioWikiInstance.title} è stato aggiornato scaricando la voce del server wiki")
            flash.messages.add("Il record di ${bioWikiInstance.title} è stato elaborato ed è aggiornato su BioGrails")
            redirect(action: 'show', id: bioWikiInstance.id)
        } else {
            redirect(action: 'list')
        }// fine del blocco if-else
    } // fine del metodo

    def show(Long id) {
        def bioWikiInstance = BioWiki.get(id)
        ArrayList menuExtra
        def noMenuCreate = true

        if (!bioWikiInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bioWiki.label', default: 'BioWiki'), id])
            redirect(action: 'list')
            return
        }// fine del blocco if e fine anticipata del metodo

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        int pageid = bioWikiInstance.pageid
        String query = "select id from BioGrails where pageid=" + pageid
        ArrayList ref = BioGrails.executeQuery(query)
        long idGrails = (long) ref.get(0)
        menuExtra = [
                [cont: 'bioWiki', action: "download/${pageid}", icon: 'frecciagiu', title: 'Download'],
                [cont: 'bioWiki', action: "elaboraSingola/${pageid}", icon: 'database', title: 'Elabora'],
                [cont: 'bioWiki', action: "fix/${pageid}", icon: 'frecciagiu', title: 'Fix (download + elabora)'],
                [cont: 'bioWiki', action: "upload/${pageid}", icon: 'frecciasu', title: 'Upload'],
                [cont: 'bioWiki', action: "ciclo/${pageid}", icon: 'frecciasu', title: 'Ciclo (download + elabora + upload)'],
                [cont: 'bioGrails', action: "show/${idGrails}", icon: 'scambia', title: 'BioGrails']
        ]
        // fine della definizione

        //--presentazione della view (show), secondo il modello
        //--menuExtra può essere nullo o vuoto
        render(view: 'show', model: [
                bioWikiInstance: bioWikiInstance,
                menuExtra: menuExtra,
                noMenuCreate: noMenuCreate],
                params: params)
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
