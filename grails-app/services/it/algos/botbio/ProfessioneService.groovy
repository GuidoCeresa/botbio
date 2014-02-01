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

import it.algos.algoswiki.WikiService

class ProfessioneService {

    // utilizzo di un service con la businessLogic
    // il service NON viene iniettato automaticamente (perché è nel plugin)
    WikiService wikiService = new WikiService()

    private static String TITOLO = 'Modulo:Bio/Link attività'

    /**
     * Aggiorna i records leggendoli dalla pagina wiki
     *
     * Recupera la mappa dalla pagina wiki
     * Ordina alfabeticamente la mappa
     * Aggiunge al database i records mancanti
     */
    public download() {
        // variabili e costanti locali di lavoro
        Map mappa = null

        // Recupera la mappa dalla pagina wiki
        mappa = this.getMappa()

        // Aggiunge i records mancanti
        if (mappa) {
            mappa?.each {
                this.aggiungeRecord(it)
            }// fine di each
            log.info 'Aggiornati sul DB i records di professione'
        }// fine del blocco if
    } // fine del metodo

    /**
     * Recupera la mappa dalla pagina wiki
     */
    private getMappa() {
        // variabili e costanti locali di lavoro
        Map mappa = null

        // Legge la pagina di servizio
        // Recupera la mappa dalla pagina wiki
        mappa = wikiService.leggeModuloMappa(TITOLO)

        if (!mappa) {
            log.warn 'Non sono riuscito a leggere la pagina link attività dal server wiki'
        }// fine del blocco if

        // valore di ritorno
        return mappa
    } // fine del metodo

    /**
     * Aggiunge il record mancante
     */
    def aggiungeRecord(record) {
        // variabili e costanti locali di lavoro
        String singolare
        String voce
        Professione professione

        if (record) {
            singolare = record.key
            voce = record.value
            if (voce) {
                professione = Professione.findBySingolare(singolare)
                if (!professione) {
                    professione = new Professione()
                }// fine del blocco if
                professione.singolare = singolare
                professione.voce = voce
                professione.save(flush: true)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    /**
     * Ritorna la voce di professione dal nome al singolare
     * Se non esiste, ritorna false
     */
    public static getProfessione(String nomeProfessione) {
        // variabili e costanti locali di lavoro
        Professione professione = null

        if (nomeProfessione) {
            try { // prova ad eseguire il codice
                professione = Professione.findBySingolare(nomeProfessione)
            } catch (Exception unErrore) { // intercetta l'errore
                try { // prova ad eseguire il codice
                } catch (Exception unAltroErrore) { // intercetta l'errore
                }// fine del blocco if
            }// fine del blocco try-catch
        }// fine del blocco if

        // valore di ritorno
        return professione
    } // fine del metodo

} // fine della service classe
