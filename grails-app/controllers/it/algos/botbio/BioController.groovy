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
import it.algos.algospref.LibPref
import it.algos.algospref.Preferenze

//--gestisce operazioni aggiuntive e di controllo
class BioController {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def logService
    def grailsApplication
    def bioService

    def index() {
        render(view: 'index')
    } // fine del metodo


    def parsessoassente() {
        params.max = 1000
        ArrayList campiLista
        def lista
        def campoSort
        String titoloLista

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', title:'titoloVisibile', sort:'ordinamento']
        campiLista = [
                'pageid',
                [campo: 'wikiUrl', title: 'Wiki'],
                'nome',
                'cognome',
                'sesso'
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

        //--selezione dei records da mostrare
        //--per una lista filtrata (parziale), modificare i parametri
        //--oppure modificare il findAllByInteroGreaterThan()...
        lista = bioService.getListaSessoAssente()

        //--titolo visibile sopra la table dei dati
        titoloLista = 'Elenco di ' + Lib.Txt.formatNum(lista.size()) + ' biografie con parametro sesso assente'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'parsesso', model: [
                bioWikiInstanceList: lista,
                titoloLista: titoloLista,
                campiLista: campiLista],
                params: params)
    } // fine del metodo

    def parsessoerrato() {
        params.max = 1000
        ArrayList campiLista
        def lista
        def campoSort
        String titoloLista

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', title:'titoloVisibile', sort:'ordinamento']
        campiLista = [
                'pageid',
                [campo: 'wikiUrl', title: 'Wiki'],
                'nome',
                'cognome',
                'sesso'
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

        //--selezione dei records da mostrare
        //--per una lista filtrata (parziale), modificare i parametri
        //--oppure modificare il findAllByInteroGreaterThan()...
        lista = bioService.getListaSessoErrato()

        //--titolo visibile sopra la table dei dati
        titoloLista = 'Elenco di ' + Lib.Txt.formatNum(lista.size()) + ' biografie con parametro sesso errato'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'parsesso', model: [
                bioWikiInstanceList: lista,
                titoloLista: titoloLista,
                campiLista: campiLista],
                params: params)
    } // fine del metodo

    //--mostra un dialogo di conferma per l'operazione da compiere
    //--passa al metodo effettivo
    def uploadSesso() {
        params.tipo = TipoDialogo.conferma
        params.titolo = 'FixSesso'
        params.avviso = []
        params.avviso.add("Vengono modificate su wikipedia tutte le voci col parametro sesso errato")
        params.avviso.add("Viene aggiunto di default il parametro 'M'")
        params.returnController = 'bio'
        params.returnAction = 'uploadSessoDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--ciclo di correzione ed upload
    def uploadSessoDopoConferma() {
        String valore
        boolean continua = false
        def numVoci
        String avviso
        boolean debug = Preferenze.getBool((String) grailsApplication.config.debug)
        flash.message = 'Operazione annullata. Le voci non sono state modificate.'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    if (grailsApplication && grailsApplication.config.login) {
                        continua = true
                    } else {
                        if (debug) {
                            continua = true
                        } else {
                            flash.message = 'Devi essere loggato per poter modificare le voci.'
                        }// fine del blocco if-else
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        if (continua) {
            numVoci = bioService.uploadSesso()
            if (numVoci == 0) {
                flash.message = 'Non è stata modificata (corretta) nessuna voce'
            } else {
                numVoci = LibTesto.formatNum(numVoci)
                avviso = "Sono state modificate (corrette) ${numVoci} voci che avevano il parametro sesso errato o mancante"
                flash.message = avviso
                log.info(avviso)
            }// fine del blocco if-else
        }// fine del blocco if

        redirect(action: 'index')
    } // fine del metodo


    def pargiorno() {
        params.max = 1000
        ArrayList campiLista
        def lista
        def campoSort
        String titoloLista

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', title:'titoloVisibile', sort:'ordinamento']
        campiLista = [
                'pageid',
                [campo: 'wikiUrl', title: 'Wiki'],
                'nome',
                'cognome',
                'giornoMeseNascita',
                'giornoMeseMorte'
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

        //--selezione dei records da mostrare
        //--per una lista filtrata (parziale), modificare i parametri
        //--oppure modificare il findAllByInteroGreaterThan()...
        lista = bioService.getListaPrimiGiorniErrati()

        //--titolo visibile sopra la table dei dati
        titoloLista = 'Elenco di ' + Lib.Txt.formatNum(lista.size()) + ' biografie con primo giorno del mese errato'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'pargiorno', model: [
                bioWikiInstanceList: lista,
                titoloLista: titoloLista,
                campiLista: campiLista],
                params: params)
    } // fine del metodo

    //--mostra un dialogo di conferma per l'operazione da compiere
    //--passa al metodo effettivo
    def uploadGiorni() {
        params.tipo = TipoDialogo.conferma
        params.titolo = 'FixPrimoGiornoMese'
        params.avviso = []
        params.avviso.add("Vengono modificate su wikipedia tutte le voci col parametro giornoMeseNascita o giornoMeseMorte errato")
        params.avviso.add("Controlla il primo giorno del mese")
        params.returnController = 'bio'
        params.returnAction = 'uploadGiorniDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--ciclo di correzione ed upload
    def uploadGiorniDopoConferma() {
        String valore
        boolean continua = false
        def numVoci
        String avviso
        boolean debug = Preferenze.getBool((String) grailsApplication.config.debug)
        flash.message = 'Operazione annullata. Le voci non sono state modificate.'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    if (grailsApplication && grailsApplication.config.login) {
                        continua = true
                    } else {
                        if (debug) {
                            continua = true
                        } else {
                            flash.message = 'Devi essere loggato per poter modificare le voci.'
                        }// fine del blocco if-else
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        if (continua) {
            numVoci = bioService.uploadGiorni()
            if (numVoci == 0) {
                flash.message = 'Non è stata modificata (corretta) nessuna voce'
            } else {
                numVoci = LibTesto.formatNum(numVoci)
                avviso = "Sono state modificate (corrette) ${numVoci} voci che avevano il parametro giornoMeseNascita o giornoMeseMorte errato"
                flash.message = avviso
                log.info(avviso)
//                logWikiService.info(avviso)
            }// fine del blocco if-else
        }// fine del blocco if

        redirect(action: 'index')
    } // fine del metodo

    //--records vuoti
    def parOrdinamentoVuoti() {
        params.max = 100
        params.controller = 'BioGrails'
        ArrayList menuExtra
        ArrayList campiLista
        def lista
        def campoSort = 'forzaOrdinamento'
        String titoloLista
        int recordsTotaliVuoti
        int vociBiograficheTotali

        if (LibPref.getBool(LibBio.USA_LIMITE_ELABORA)) {
            params.max = LibPref.getInt(LibBio.MAX_ELABORA)
        }// fine del blocco if

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        menuExtra = [
                [cont: 'bio', action: 'parOrdinamentoPieni', icon: 'database', title: 'Piene'],
                [cont: 'bio', action: 'parOrdinamentoAll', icon: 'database', title: 'Tutte'],
                [cont: 'bio', action: 'fixOrdinamento', icon: 'database', title: 'Fix and elabora']
        ]
        // fine della definizione

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', title:'titoloVisibile', sort:'ordinamento']
        campiLista = [
                'pageid',
                'title',
                'forzaOrdinamento',
                'cognome',
                'nome'
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

        //--selezione dei records da mostrare
        //--per una lista filtrata (parziale), modificare i parametri
        //--oppure modificare il findAllByInteroGreaterThan()...
        lista = BioGrails.findAllByForzaOrdinamentoIsNull([sort: "title", offset: params.offset, order: "asc", max: params.max])

        //--calcola il numero di record
        recordsTotaliVuoti = BioGrails.countByForzaOrdinamentoIsNull()
        vociBiograficheTotali = BioGrails.count()

        //--titolo visibile sopra la table dei dati
        titoloLista = 'Elenco di '
        titoloLista += Lib.Txt.formatNum(params.max) + '/' + Lib.Txt.formatNum(recordsTotaliVuoti)
        titoloLista += ' records col parametro forzaOrdinamento vuoto'
        titoloLista += ' su un totale di ' + Lib.Txt.formatNum(vociBiograficheTotali) + ' voci biografiche'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'parordinamento', model: [
                bioGrailsInstanceList: lista,
                bioGrailsInstanceTotal: recordsTotaliVuoti,
                menuExtra: menuExtra,
                titoloLista: titoloLista,
                campiLista: campiLista],
                params: params)
    } // fine del metodo

    //--records vuoti
    def parOrdinamentoPieni() {
        params.max = 100
        params.controller = 'BioGrails'
        ArrayList menuExtra
        ArrayList campiLista
        def lista
        def campoSort = 'forzaOrdinamento'
        String titoloLista
        int recordsTotaliPieni
        int vociBiograficheTotali

        if (LibPref.getBool(LibBio.USA_LIMITE_ELABORA)) {
            params.max = LibPref.getInt(LibBio.MAX_ELABORA)
        }// fine del blocco if

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        menuExtra = [
                [cont: 'bio', action: 'parOrdinamentoVuoti', icon: 'database', title: 'Vuote'],
                [cont: 'bio', action: 'parOrdinamentoAll', icon: 'database', title: 'Tutte']
        ]
        // fine della definizione

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', title:'titoloVisibile', sort:'ordinamento']
        campiLista = [
                'pageid',
                'title',
                'forzaOrdinamento',
                'cognome',
                'nome'
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

        //--selezione dei records da mostrare
        //--per una lista filtrata (parziale), modificare i parametri
        //--oppure modificare il findAllByInteroGreaterThan()...
        lista = BioGrails.findAllByForzaOrdinamentoIsNotNull([sort: "forzaOrdinamento", offset: params.offset, order: "asc", max: params.max])

        //--calcola il numero di record
        recordsTotaliPieni = BioGrails.countByForzaOrdinamentoIsNotNull()
        vociBiograficheTotali = BioGrails.count()

        //--titolo visibile sopra la table dei dati
        titoloLista = 'Elenco di '
        titoloLista += Lib.Txt.formatNum(params.max) + '/' + Lib.Txt.formatNum(recordsTotaliPieni)
        titoloLista += ' records col parametro forzaOrdinamento pieno'
        titoloLista += ' su un totale di ' + Lib.Txt.formatNum(vociBiograficheTotali) + ' voci biografiche'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'parordinamento', model: [
                bioGrailsInstanceList: lista,
                bioGrailsInstanceTotal: recordsTotaliPieni,
                menuExtra: menuExtra,
                titoloLista: titoloLista,
                campiLista: campiLista],
                params: params)
    } // fine del metodo

    //-- tutti i records
    def parOrdinamentoAll() {
        params.max = Integer.MAX_VALUE
        params.controller = 'BioGrails'
        ArrayList menuExtra
        ArrayList campiLista
        def lista
        def campoSort = 'forzaOrdinamento'
        String titoloLista
        int recordsTotali

        if (LibPref.getBool(LibBio.USA_LIMITE_ELABORA)) {
            params.max = LibPref.getInt(LibBio.MAX_ELABORA)
        }// fine del blocco if

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        menuExtra = [
                [cont: 'bio', action: 'parOrdinamentoVuoti', icon: 'database', title: 'Vuote'],
                [cont: 'bio', action: 'parOrdinamentoPieni', icon: 'database', title: 'Piene']
        ]
        // fine della definizione

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', title:'titoloVisibile', sort:'ordinamento']
        campiLista = [
                'pageid',
                'title',
                'forzaOrdinamento',
                'cognome',
                'nome'
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

        //--selezione dei records da mostrare
        //--per una lista filtrata (parziale), modificare i parametri
        //--oppure modificare il findAllByInteroGreaterThan()...
//        lista = BioGrails.findAll(params)
        lista = BioGrails.findAll([sort: "forzaOrdinamento", offset: params.offset, order: "asc", max: params.max])

        //--calcola il numero di record
        recordsTotali = BioGrails.count()

        //--titolo visibile sopra la table dei dati
        titoloLista = 'Elenco di '
        titoloLista += Lib.Txt.formatNum(lista.size()) + '/' + Lib.Txt.formatNum(recordsTotali)
        titoloLista += ' biografie con evidenziato il parametro forzaOrdinamento'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'parordinamento', model: [
                bioGrailsInstanceList: lista,
                bioGrailsInstanceTotal: recordsTotali,
                menuExtra: menuExtra,
                titoloLista: titoloLista,
                campiLista: campiLista],
                params: params)
    } // fine del metodo

    //--mostra un dialogo di conferma per l'operazione da compiere
    //--passa al metodo effettivo
    def fixOrdinamento() {
        boolean usaLimiteElabora = LibPref.getBool(LibBio.USA_LIMITE_ELABORA)
        int maxElabora = LibPref.getInt(LibBio.MAX_ELABORA)
        String maxElaboraTxt
        params.tipo = TipoDialogo.conferma
        params.titolo = 'FixOrdinamento'
        params.avviso = []

        if (usaLimiteElabora && maxElabora > 0) {
            maxElaboraTxt = Lib.Txt.formatNum(maxElabora)
            params.avviso.add("Vengono elaborate ${maxElaboraTxt} voci col parametro forzaOrdinamento vuoto")
        } else {
            params.avviso.add("Vengono elaborate tutte le voci col parametro forzaOrdinamento vuoto")
            params.avviso.add("Occorrono decine e decine di ore")
        }// fine del blocco if-else
        params.returnController = 'bio'
        params.returnAction = 'fixOrdinamentoDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--ciclo di elaborazione
    def fixOrdinamentoDopoConferma() {
        String valore
        boolean continua = false
        def numVoci
        String avviso
        flash.message = 'Operazione annullata. Le voci non sono state modificate.'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    continua = true
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        if (continua) {
            numVoci = bioService.elabora()
            if (numVoci.size() == 0) {
                flash.message = 'Non è stata elaborata nessuna voce'
            } else {
                numVoci = LibTesto.formatNum(numVoci.size())
                avviso = "Sono state elaborate ${numVoci} voci che avevano il parametro forzaOrdinamento vuoto"
                flash.message = avviso
                log.info(avviso)
            }// fine del blocco if-else
        }// fine del blocco if

        redirect(action: 'parOrdinamentoVuoti')
    } // fine del metodo

    //--parametro didascaliaListe
    def didascaliaListe() {
        params.max = 100
        params.controller = 'BioGrails'
        ArrayList menuExtra
        ArrayList campiLista
        def lista
        def campoSort = 'didascaliaListe'
        String titoloLista
        int recordsTotaliVuoti
        int vociBiograficheTotali

        //--selezione dei menu extra
        //--solo azione e di default controller=questo; classe e titolo vengono uguali
        //--mappa con [cont:'controller', action:'metodo', icon:'iconaImmagine', title:'titoloVisibile']
        menuExtra = [
        ]
        // fine della definizione

        //--selezione delle colonne (campi) visibili nella lista
        //--solo nome e di default il titolo viene uguale
        //--mappa con [campo:'nomeDelCampo', title:'titoloVisibile', sort:'ordinamento']
        campiLista = [
                'pageid',
                'title',
                'didascaliaListe',
                'nome',
                'cognome'
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

        //--selezione dei records da mostrare
        //--per una lista filtrata (parziale), modificare i parametri
        //--oppure modificare il findAllByInteroGreaterThan()...
        lista = BioGrails.findAllByDidascaliaListeIsNull([sort: "title", offset: params.offset, order: "asc", max: params.max])

        //--calcola il numero di record
        recordsTotaliVuoti = BioGrails.countByDidascaliaListeIsNull()
        vociBiograficheTotali = BioGrails.count()

        //--titolo visibile sopra la table dei dati
        titoloLista = 'Elenco di '
        titoloLista += Lib.Txt.formatNum(params.max) + '/' + Lib.Txt.formatNum(recordsTotaliVuoti)
        titoloLista += ' records col parametro didascaliaListe vuoto'
        titoloLista += ' su un totale di ' + Lib.Txt.formatNum(vociBiograficheTotali) + ' voci biografiche'

        //--presentazione della view (list), secondo il modello
        //--menuExtra e campiLista possono essere nulli o vuoti
        //--se campiLista è vuoto, mostra tutti i campi (primi 8)
        render(view: 'parordinamento', model: [
                bioGrailsInstanceList: lista,
                bioGrailsInstanceTotal: recordsTotaliVuoti,
                menuExtra: menuExtra,
                titoloLista: titoloLista,
                campiLista: campiLista],
                params: params)
    } // fine del metodo

    def show(Long id) {
        def bioWikiInstance = BioWiki.get(id)

        if (!bioWikiInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bioWiki.label', default: 'BioWiki'), id])
            redirect(action: 'parsesso')
            return
        }// fine del blocco if e fine anticipata del metodo

        [bioWikiInstance: bioWikiInstance]
    } // fine del metodo

    def test() {
        String titoloA
        String titoloB
        String titoloC
        WrapBio wrapA
        WrapBio wrapB
        WrapBio wrapC
        BioWiki bioWikiA
        BioWiki bioWikiB
        BioWiki bioWikiC
        def registrata

        titoloA = 'Pietro Barillà'
        titoloB = 'Mario Bergara'
        titoloC = 'San Giorgio'

        wrapA = new WrapBio(titoloA)
        wrapB = new WrapBio(titoloB)
        wrapC = new WrapBio(titoloC)

        wrapA.registraBioWiki()
        wrapB.registraBioWiki()
        wrapC.registraBioWiki()

        bioWikiA = wrapA.getBioOriginale()
        bioWikiB = wrapB.getBioOriginale()
        bioWikiC = wrapB.getBioOriginale()

        bioWikiA?.attivita3 = 'pippoz'
        bioWikiB?.attivita3 = 'plutox'
        bioWikiC?.attivita3 = 'pap'

        registrata = bioWikiA?.save(failOnError: true)
        registrata = bioWikiB?.save(failOnError: true)
        registrata = bioWikiC?.save(failOnError: true)

        render(view: 'index')
    } // fine del metodo

} // fine della controller classe
