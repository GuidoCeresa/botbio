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

class NazionalitaService {

    // utilizzo di un service con la businessLogic
    // il service NON viene iniettato automaticamente (perché è nel plugin)
    WikiService wikiService = new WikiService()

    private static String TITOLO = 'Modulo:Bio/Plurale nazionalità'

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
            log.info 'Aggiornati sul DB i records di nazionalità (plurale)'
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
        try { // prova ad eseguire il codice
            mappa = wikiService.leggeModuloMappa(TITOLO)
        } catch (Exception unErrore) { // intercetta l'errore
            log.error 'getMappa - ' + unErrore
        }// fine del blocco try-catch

        if (!mappa) {
            log.warn 'getMappa - Non sono riuscito a leggere la pagina ' + TITOLO + ' attività dal server wiki'
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
        Nazionalita nazionalita

        if (record) {
            singolare = record.key
            plurale = record.value
            if (plurale) {
                nazionalita = Nazionalita.findBySingolare(singolare)
                if (!nazionalita) {
                    nazionalita = new Nazionalita()
                }// fine del blocco if
                nazionalita.singolare = singolare
                nazionalita.plurale = plurale
                nazionalita.save(flush: true)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    /**
     * Ritorna la nazionalità dal nome al singolare
     * Se non esiste, ritorna false
     */
    public static getNazionalita(String nomeNazionalita) {
        // variabili e costanti locali di lavoro
        Nazionalita nazionalita = null

        if (nomeNazionalita) {
            try { // prova ad eseguire il codice
                nazionalita = Nazionalita.findBySingolare(nomeNazionalita)
            } catch (Exception unErrore) { // intercetta l'errore
                log.error unErrore
            }// fine del blocco try-catch
        }// fine del blocco if

        // valore di ritorno
        return nazionalita
    } // fine del metodo

    /**
     * Ritorna una lista di tutte le nazionalità plurali distinte
     *
     * @return lista ordinata (stringhe) di tutti i plurali delle nazionalità
     */
    public static ArrayList<String> getListaPlurali() {
        return (ArrayList<String>) Nazionalita.executeQuery('select distinct plurale from Nazionalita order by plurale')
    } // fine del metodo

} // fine della service classe
