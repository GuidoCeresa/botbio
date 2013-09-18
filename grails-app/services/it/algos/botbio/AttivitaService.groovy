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

class AttivitaService {

    // utilizzo di un service con la businessLogic
    // il service NON viene iniettato automaticamente (perché è nel plugin)
    WikiService wikiService = new WikiService()

    private static String TITOLO = 'Template:Bio/plurale_attività'

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
            log.info 'Aggiornati sul DB i records di attività (plurale)'
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
        mappa = wikiService.leggeSwitchMappa(TITOLO)

        if (!mappa) {
            log.warn 'Non sono riuscito a leggere la pagina plurale attività dal server wiki'
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
        String plurale
        Attivita attivita

        if (record) {
            singolare = record.key
            plurale = record.value
            if (plurale) {
                attivita = Attivita.findBySingolare(singolare)
                if (!attivita) {
                    attivita = new Attivita()
                }// fine del blocco if
                attivita.singolare = singolare
                attivita.plurale = plurale
                attivita.save(flush: true)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    /**
     * Ritorna l'attività dal nome al singolare
     * Se non esiste, ritorna false
     */
    public static getAttivita(nomeAttivita) {
        // variabili e costanti locali di lavoro
        Attivita attivita = null

        if (nomeAttivita) {
            try { // prova ad eseguire il codice
                attivita = Attivita.findBySingolare(nomeAttivita)
            } catch (Exception unErrore) { // intercetta l'errore
                try { // prova ad eseguire il codice
//                    log.error Risultato.erroreGenerico.getDescrizione()
                } catch (Exception unAltroErrore) { // intercetta l'errore
                }// fine del blocco if
            }// fine del blocco try-catch
        }// fine del blocco if

        // valore di ritorno
        return attivita
    } // fine del metodo

} // fine della service classe
