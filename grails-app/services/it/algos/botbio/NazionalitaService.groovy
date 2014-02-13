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
import it.algos.algoslib.LibWiki
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

    /**
     * Ritorna una lista di una mappa per ogni nazionalità distinta
     *
     * La mappa contiene:
     *  -plurale dell'attività
     *  -numero di voci che nel campo nazionalità usano tutti records di nazionalità che hanno quel plurale
     */
    public static getLista() {
        // variabili e costanti locali di lavoro
        def lista = new ArrayList()
        def listaPlurali
        def mappa
        def singolari
        int numNaz

        listaPlurali = getListaPlurali()

        listaPlurali?.each {
            mappa = new LinkedHashMap()
            numNaz = 0
            singolari = Nazionalita.findAllByPlurale(it)

            singolari?.each {
                numNaz += BioGrails.countByNazionalitaLink(it)
            }// fine di each

            mappa.put('plurale', it)
            mappa.put('nazionalita', numNaz)
            if (numNaz > 0) {
                lista.add(mappa)
            }// fine del blocco if
        }// fine di each

        // valore di ritorno
        return lista
    } // fine del metodo

    /**
     * Ritorna una lista di una mappa per ogni nazionalità distinta NON utilizzata
     *
     * Lista del campo ''plurale'' come stringa
     */
    public static getListaNonUsate() {
        // variabili e costanti locali di lavoro
        def lista = new ArrayList()
        def listaPlurali
        def mappa
        def singolari
        int numNaz

        listaPlurali = getListaPlurali()

        listaPlurali?.each {
            mappa = new LinkedHashMap()
            numNaz = 0
            singolari = Nazionalita.findAllByPlurale(it)

            singolari?.each {
                numNaz += BioGrails.countByNazionalitaLink(it)
            }// fine di each

            mappa.put('plurale', it)
            mappa.put('nazionalita', numNaz)
            if (numNaz < 1) {
                lista.add(it)
            }// fine del blocco if
        }// fine di each

        // valore di ritorno
        return lista
    } // fine del metodo

    /**
     * Restituisce l'array delle riga del parametro per le nazionalita
     * La mappa contiene:
     *  -plurale dell'attività
     *  -numero di voci che nel campo nazionalita usano tutti records di nazionalita che hanno quel plurale
     */
    def getRigaNazionalita = { num, mappa ->
        // variabili e costanti locali di lavoro
        def riga = new ArrayList()
        boolean usaListe = true
        String tagCat = ':Categoria:'
        String tagListe = StatisticheService.PATH + 'Nazionalità/'
        String pipe = '|'
        String nazionalita
        int numNaz
        String plurale

        if (mappa) {
            plurale = mappa.plurale
            if (usaListe) {
                if (true) { // possibilità di cambiare idea da programma
                    nazionalita = tagListe + LibTesto.primaMaiuscola(plurale) + pipe + LibTesto.primaMinuscola(plurale)
                } else {
                    nazionalita = tagCat + LibTesto.primaMinuscola(plurale) + pipe + plurale
                }// fine del blocco if-else
                nazionalita = LibWiki.setQuadre(nazionalita)
            } else {
                nazionalita = plurale
            }// fine del blocco if-else

            numNaz = mappa.nazionalita

            //riga.add(getColore(mappa))
            riga.add(num)
            riga.add(nazionalita)
            riga.add(numNaz)
        }// fine del blocco if

        // valore di ritorno
        return riga
    } // fine della closure

    /**
     * Restituisce l'array delle riga del parametro per le nazionalità NON utilizzate
     *
     *  -plurale dell'attività
     */
    public getRigaNazionalitaNonUsate(num, plurale) {
        // variabili e costanti locali di lavoro
        def riga = new ArrayList()

        if (plurale) {
            riga.add(LibTesto.formatNum(num))
            riga.add(plurale)
        }// fine del blocco if

        // valore di ritorno
        return riga
    } // fine della closure

    /**
     * Totale attività distinte
     */
    public static int numNazionalita() {
        return getLista()?.size()
    } // fine del metodo

    /**
     * Totale attività distinte e NON utilizzate
     */
    public static int numNazionalitaNonUsate() {
        return getListaNonUsate()?.size()
    } // fine del metodo

} // fine della service classe
