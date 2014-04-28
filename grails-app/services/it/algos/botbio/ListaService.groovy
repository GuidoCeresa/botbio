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
        long inizio = System.currentTimeMillis()
        long parziale
        long durataParziale = 0
        long durataProgressiva = 0
        long durataProgressivaOld = 0
        int num
        int k = 0
        String attivita

        // Recupera tutte le attività esistenti (circa 500)
        listaAttivitaPlurali = AttivitaService.getListaPlurali()
        num = listaAttivitaPlurali.size()

        // Ciclo per ognuna delle attività esistenti (circa 500)
        listaAttivitaPlurali?.each {
            attivita = it
            attivita = LibTesto.primaMaiuscola(attivita)
            wrapAttivita = new BioAttivita(attivita)
            wrapAttivita.registraPagina()
            parziale = System.currentTimeMillis()
            durataProgressiva = parziale - inizio
            durataProgressiva = durataProgressiva / 1000
            durataParziale = durataProgressiva - durataProgressivaOld
            durataProgressivaOld = durataProgressiva
            k++
            println(k + '/' + num + " - ${attivita}" + ' in ' + durataParziale + ' sec. - totale ' + durataProgressiva + ' sec.')
        }// fine di each
    } // fine del metodo

    public pippo() {
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
            nazionalita = it
            nazionalita = LibTesto.primaMaiuscola(nazionalita)
            wrapNazionalita = new BioNazionalita(nazionalita)
            wrapNazionalita.registraPagina()
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
