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
import it.algos.algoslib.LibTesto

class SetupController {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def annoService
    def giornoService
    def attivitaService
    def nazionalitaService

    def index() {
        redirect(action: 'preSetup', params: params)
    } // fine del metodo

    //--mostra un dialogo di conferma per l'operazione da compiere
    //--passa al metodo effettivo
    def preSetup() {
        params.tipo = TipoDialogo.conferma
        params.titolo = 'Setup iniziale'
        params.avviso = []
        params.avviso.add('Setup iniziale del programma.')
        params.avviso.add( 'Vengono create le tavole di anni e giorni.')
        params.avviso.add('Vengono importate le liste di attività e nazionalità.')
        params.returnController = 'Setup'
        params.returnAction = 'setupDopoConferma'
        redirect(controller: 'dialogo', action: 'box', params: params)
    } // fine del metodo

    //--ritorno dal dialogo di conferma
    //--a seconda del valore ritornato come parametro, esegue o meno l'operazione
    //--setup (usa il nome-metodo create, perchè è il primo ed unico della lista standard)
    def setupDopoConferma() {
        String valore
        flash.message = 'Operazione annullata. Il setup non è stato eseguito.'

        if (params.valore) {
            if (params.valore instanceof String) {
                valore = (String) params.valore
                if (valore.equals(DialogoController.DIALOGO_CONFERMA)) {
                    redirect(action: 'postSetup')
                } else {
                    redirect(uri: '/')
                }// fine del blocco if-else
            } else {
                redirect(uri: '/')
            }// fine del blocco if-else
        } else {
            redirect(uri: '/')
        }// fine del blocco if-else
    } // fine del metodo

    //--Vengono create le tavole di anni e giorni
    //--Vengono importate le liste di attività e nazionalità
    //--Torna al menu iniziale
    def postSetup() {
        flash.messages = []
        flash.errors = []
        def numRec

        if (Anno.count < 1) {
            annoService.creazioneIniziale()
            numRec = Anno.count()
            numRec = LibTesto.formatNum(numRec)
            flash.messages.add("È stata creata la tavola degli anni. Contiene ${numRec} anni.")
        } else {
            flash.errors.add('Non è stata creata la tavola degli anni, in quanto esistevano già.')
        }// fine del blocco if-else

        if (Giorno.count < 1) {
            giornoService.creazioneIniziale()
            numRec = Giorno.count()
            numRec = LibTesto.formatNum(numRec)
            flash.messages.add("È stata creata la tavola dei giorni. Contiene ${numRec} giorni.")
        } else {
            flash.errors.add('Non è stata creata la tavola dei giorni, in quanto esistevano già.')
        }// fine del blocco if-else

        if (Attivita.count < 1) {
            attivitaService.download()
            numRec = Attivita.count()
            numRec = LibTesto.formatNum(numRec)
            flash.messages.add("È stata importata la lista delle attività. Contiene ${numRec} attività.")
        } else {
            flash.errors.add('Non è stata importata la lista delle attività, in quanto esistevano già.')
        }// fine del blocco if-else

        if (Nazionalita.count < 1) {
            nazionalitaService.download()
            numRec = Nazionalita.count()
            numRec = LibTesto.formatNum(numRec)
            flash.messages.add("È stata importata la lista delle nazionalita. Contiene ${numRec} nazionalita.")
        } else {
            flash.errors.add('Non è stata importata la lista delle nazionalita, in quanto esistevano già.')
        }// fine del blocco if-else

        redirect(uri: '/')
    } // fine del metodo

} // fine della classe controller
