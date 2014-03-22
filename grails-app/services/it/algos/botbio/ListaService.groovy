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

class ListaService {

    /**
     * Costruisce tutte le liste delle attività e delle naziuonalità
     *
     * Recupera la lista delle singole attività
     * Per ogni attività recupera una lista di records di attività utilizzati
     * Per ogni attività crea una lista di tutte le biografie che utilizzano quei records di attività
     * Crea una lista di didascalie
     * Crea la pagina e la registra su wiki
     *
     * Recupera la lista delle singole nazionalità
     * Per ogni attività recupera una lista di records di nazionalità utilizzati
     * Per ogni attività crea una lista di tutte le biografie che utilizzano quei records di nazionalità
     * Crea una lista di didascalie
     * Crea la pagina e la registra su wiki
     */
    public uploadAll() {
        this.uploadAttivita()
        this.uploadNazionalita()
    } // fine del metodo

    /**
     * Costruisce tutte le liste delle attività
     *
     * Recupera la lista delle singole attività
     * Per ogni attività recupera una lista di records di attività utilizzati
     * Per ogni attività crea una lista di tutte le biografie che utilizzano quei records di attività
     * Crea una lista di didascalie
     * Crea la pagina e la registra su wiki
     */
    public uploadAttivita() {
        // variabili e costanti locali di lavoro
        ArrayList<String> listaAttivitaPlurali
        BioAttivita wrapAttivita

        // Recupera tutte le attività esistenti (circa 500)
        listaAttivitaPlurali = AttivitaService.getListaPlurali()

        // Ciclo per ognuna delle attività esistenti (circa 500)
        listaAttivitaPlurali.each {
            wrapAttivita = new BioAttivita(it)
            wrapAttivita.registraPagina()
        }// fine di each
    } // fine del metodo

    /**
     * Costruisce tutte le liste delle nazionalità
     *
     * Recupera la lista delle singole nazionalità
     * Per ogni attività recupera una lista di records di nazionalità utilizzati
     * Per ogni attività crea una lista di tutte le biografie che utilizzano quei records di nazionalità
     * Crea una lista di didascalie
     * Crea la pagina e la registra su wiki
     */
    public uploadNazionalita() {
        // variabili e costanti locali di lavoro
        ArrayList<String> listaNazionalitaPlurali
        BioNazionalita wrapNazionalita

        // Recupera tutte le nazionalità esistenti (circa 275)
        listaNazionalitaPlurali = NazionalitaService.getListaPlurali()

        // Ciclo per ognuna delle nazionalità esistenti (circa 275)
        listaNazionalitaPlurali.each {
            wrapNazionalita = new BioNazionalita(it)
            wrapNazionalita.registraPagina()
        }// fine di each
    } // fine del metodo

} // fine della service classe
