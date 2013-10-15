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


    def parsesso() {
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
        lista = BioWiki.findAllWhere([sesso: ''])

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
//                logService.info(avviso)
            }// fine del blocco if-else
        }// fine del blocco if

        redirect(action: 'index')
    } // fine del metodo

    def test() {
        String titoloA
        String titoloB
        WrapBio wrapA
        WrapBio wrapB
        BioWiki bioWikiA
        BioWiki bioWikiB
        def registrata

        titoloA = 'Pietro Barillà'
        titoloB = 'Pietro Barilla'

        wrapA = new WrapBio(titoloA)
        wrapB = new WrapBio(titoloB)

        wrapA.registraBioWiki()
        wrapB.registraBioWiki()

        bioWikiA = wrapA.getBioOriginale()
        bioWikiB = wrapB.getBioOriginale()

        bioWikiA.attivita3 = 'pippoz'
        bioWikiB.attivita3 = 'plutox'

        registrata = bioWikiA.save(failOnError: true)
        registrata = bioWikiB.save(failOnError: true)

        render(view: 'index')
    } // fine del metodo

} // fine della controller classe
