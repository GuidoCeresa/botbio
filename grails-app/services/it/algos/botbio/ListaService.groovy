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

import it.algos.algoslib.LibTesto
import it.algos.algospref.Pref

class ListaService {

    def attivitaService

    /**
     * Costruisce tutte le liste delle attività e delle nazionalità
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
        this.uploadAttivitaPrimaMeta()
        this.uploadAttivitaSecondaMeta()
    } // fine del metodo

    /**
     * Costruisce le pagine della prima meta delle attività
     * Il taglio è la preferenza META_ATTIVITA
     *
     * Recupera la lista delle singole attività
     * Per ogni attività recupera una lista di records di attività utilizzati
     * Per ogni attività crea una lista di tutte le biografie che utilizzano quei records di attività
     * Crea una lista di didascalie
     * Crea la pagina e la registra su wiki
     */
    public uploadAttivitaPrimaMeta() {
        int posInizio = 0
        int posFine = Pref.getInt(LibBio.META_ATTIVITA, 250)
        this.uploadAttivita(posInizio, posFine)
    } // fine del metodo

    /**
     * Costruisce le pagine della seconda meta delle attività
     * Il taglio è la preferenza META_ATTIVITA
     *
     * Recupera la lista delle singole attività
     * Per ogni attività recupera una lista di records di attività utilizzati
     * Per ogni attività crea una lista di tutte le biografie che utilizzano quei records di attività
     * Crea una lista di didascalie
     * Crea la pagina e la registra su wiki
     */
    public uploadAttivitaSecondaMeta() {
        int posInizio = Pref.getInt(LibBio.META_ATTIVITA, 250)
        int posFine = attivitaService.getNumPlurali()
        this.uploadAttivita(posInizio, posFine)
    } // fine del metodo

    /**
     * Costruisce tutte le liste delle attività
     *
     * Recupera la lista delle singole attività
     * Per ogni attività recupera una lista di records di attività utilizzati
     * Per ogni attività crea una lista di tutte le biografie che utilizzano quei records di attività
     * Crea una lista di didascalie
     * Crea la pagina e la registra su wiki
     *
     * @param posInizio : posizione della prima attività da elaborare
     * @param posInizio : posizione dell'ultima attività da elaborare
     */
    public uploadAttivita(int posInizio, int posFine) {
        // variabili e costanti locali di lavoro
        ArrayList<String> listaAttivitaPlurali
        BioAttivita wrapAttivita
        long inizio = System.currentTimeMillis()
        long parziale
        long durataParziale
        long durataProgressiva
        long durataProgressivaOld = 0
        int num
        String attivita

        // Recupera tutte le attività esistenti (circa 500)
        listaAttivitaPlurali = AttivitaService.getListaPlurali()
        num = listaAttivitaPlurali.size()

        // Ciclo per ognuna delle attività esistenti (circa 500)
        for (int k = posInizio; k < posFine; k++) {
            attivita = listaAttivitaPlurali.get(k)
            wrapAttivita = new BioAttivita(attivita)
            wrapAttivita.registraPagina()
            attivita = LibTesto.primaMaiuscola(attivita)
            parziale = System.currentTimeMillis()
            durataProgressiva = parziale - inizio
            durataProgressiva = durataProgressiva / 1000
            durataParziale = durataProgressiva - durataProgressivaOld
            durataProgressivaOld = durataProgressiva
            println(k + '/' + num + " - ${attivita}" + ' in ' + durataParziale + ' sec. - totale ' + durataProgressiva + ' sec.')
            def stop
        } // fine del ciclo for
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
        long inizio = System.currentTimeMillis()
        long parziale
        long durataParziale = 0
        long durataProgressiva = 0
        long durataProgressivaOld = 0
        int num
        int k = 0
        String nazionalita

        // Recupera tutte le nazionalità esistenti (circa 275)
        listaNazionalitaPlurali = NazionalitaService.getListaPlurali()
        num = listaNazionalitaPlurali.size()

        // Ciclo per ognuna delle nazionalità esistenti (circa 275)
        listaNazionalitaPlurali.each {
            wrapNazionalita = new BioNazionalita(it)
            wrapNazionalita.registraPagina()
            nazionalita = LibTesto.primaMaiuscola(it)
            parziale = System.currentTimeMillis()
            durataProgressiva = parziale - inizio
            durataProgressiva = durataProgressiva / 1000
            durataParziale = durataProgressiva - durataProgressivaOld
            durataProgressivaOld = durataProgressiva
            k++
            println(k + '/' + num + " - ${nazionalita}" + ' in ' + durataParziale + ' sec. - totale ' + durataProgressiva + ' sec.')
        }// fine di each
    } // fine del metodo

} // fine della service classe
