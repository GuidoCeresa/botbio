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

class AttivitaService {

    // utilizzo di un service con la businessLogic
    // il service NON viene iniettato automaticamente (perché è nel plugin)
    WikiService wikiService = new WikiService()

    private static String TITOLO = 'Modulo:Bio/Plurale attività'

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
        mappa = wikiService.leggeModuloMappa(TITOLO)

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
    public static getAttivita(String nomeAttivita) {
        // variabili e costanti locali di lavoro
        Attivita attivita = null

        if (nomeAttivita) {
            try { // prova ad eseguire il codice
                attivita = Attivita.findBySingolare(nomeAttivita)
            } catch (Exception unErrore) { // intercetta l'errore
            }// fine del blocco try-catch
        }// fine del blocco if

        // valore di ritorno
        return attivita
    } // fine del metodo

    /**
     * Ritorna una lista di tutte le attività plurali distinte
     *
     * @return lista ordinata (stringhe) di tutti i plurali delle attività
     */
    public static ArrayList<String> getListaPlurali() {
        return (ArrayList<String>) Attivita.executeQuery('select distinct plurale from Attivita order by plurale')
    } // fine del metodo

    /**
     * Ritorna una lista di una mappa per ogni attività distinta
     *
     * La mappa contiene:
     *  -plurale dell'attività
     *  -numero di voci che nel campo attivitaLink usano tutti records di attività che hanno quel plurale
     *  -numero di voci che nel campo attivita2Link usano tutti records di attività che hanno quel plurale
     *  -numero di voci che nel campo attivita3Link usano tutti records di attività che hanno quel plurale
     */
    public static getLista() {
        // variabili e costanti locali di lavoro
        def lista = new ArrayList()
        def listaPlurali
        def mappa
        def singolari
        int numAtt
        int numAtt2
        int numAtt3
        int totale

        listaPlurali = getListaPlurali()

        listaPlurali?.each {
            mappa = new LinkedHashMap()
            numAtt = 0
            numAtt2 = 0
            numAtt3 = 0
            singolari = Attivita.findAllByPlurale(it)

            singolari?.each {
                numAtt += BioGrails.countByAttivitaLink(it)
                numAtt2 += BioGrails.countByAttivita2Link(it)
                numAtt3 += BioGrails.countByAttivita3Link(it)
            }// fine di each
            totale = numAtt + numAtt2 + numAtt3

            mappa.put('plurale', it)
            mappa.put('attivita', numAtt)
            mappa.put('attivita2', numAtt2)
            mappa.put('attivita3', numAtt3)
            mappa.put('attivita3', numAtt3)
            mappa.put('totale', totale)

            if (totale > 0) {
                lista.add(mappa)
            }// fine del blocco if
        }// fine di each

        // valore di ritorno
        return lista
    } // fine del metodo

    /**
     * Ritorna una lista di una mappa per ogni attività distinta NON utilizzata
     *
     * Lista del campo ''plurale'' come stringa
     */
    public static getListaNonUsate() {
        // variabili e costanti locali di lavoro
        def lista = new ArrayList()
        def listaPlurali
        def mappa
        def singolari
        int numAtt
        int numAtt2
        int numAtt3
        int totale

        listaPlurali = getListaPlurali()

        listaPlurali?.each {
            mappa = new LinkedHashMap()
            numAtt = 0
            numAtt2 = 0
            numAtt3 = 0
            singolari = Attivita.findAllByPlurale(it)

            singolari?.each {
                numAtt += BioGrails.countByAttivitaLink(it)
                numAtt2 += BioGrails.countByAttivita2Link(it)
                numAtt3 += BioGrails.countByAttivita3Link(it)
            }// fine di each
            totale = numAtt + numAtt2 + numAtt3

            mappa.put('plurale', it)
            mappa.put('attivita', numAtt)
            mappa.put('attivita2', numAtt2)
            mappa.put('attivita3', numAtt3)
            mappa.put('attivita3', numAtt3)
            mappa.put('totale', totale)

            if (totale < 1) {
                lista.add(it)
            }// fine del blocco if
        }// fine di each

        // valore di ritorno
        return lista
    } // fine del metodo

    /**
     * Restituisce l'array delle riga del parametro per le attività
     * La mappa contiene:
     *  -plurale dell'attività
     *  -numero di voci che nel campo attivita usano tutti records di attività che hanno quel plurale
     *  -numero di voci che nel campo attivita2 usano tutti records di attività che hanno quel plurale
     *  -numero di voci che nel campo attivita3 usano tutti records di attività che hanno quel plurale
     */
    def getRigaAttivita = { num, mappa ->
        // variabili e costanti locali di lavoro
        def riga = new ArrayList()
        boolean usaListe = true
        String tagCat = ':Categoria:'
        String tagListe = StatisticheService.PATH + 'Attività/'
        String pipe = '|'
        String attivita
        int numAtt
        int numAtt2
        int numAtt3
        int numTot
        String plurale

        if (mappa) {
            plurale = mappa.plurale
            if (usaListe) {
                if (true) { // possibilità di cambiare idea da programma
                    attivita = tagListe + LibTesto.primaMaiuscola(plurale) + pipe + LibTesto.primaMinuscola(plurale)
                } else {
                    attivita = tagCat + LibTesto.primaMaiuscola(plurale) + pipe + LibTesto.primaMinuscola(plurale)
                }// fine del blocco if-else
                attivita = LibWiki.setQuadre(attivita)
            } else {
                attivita = plurale
            }// fine del blocco if-else

            numAtt = mappa.attivita
            numAtt2 = mappa.attivita2
            numAtt3 = mappa.attivita3
            numTot = numAtt + numAtt2 + numAtt3

            riga.add(num)
            riga.add(attivita)
            riga.add(numAtt)
            riga.add(numAtt2)
            riga.add(numAtt3)
            riga.add(numTot)
        }// fine del blocco if

        // valore di ritorno
        return riga
    } // fine della closure

    /**
     * Restituisce l'array delle riga del parametro per le attività NON utilizzate
     *
     *  -plurale dell'attività
     */
    public getRigaAttivitaNonUsate(num, plurale) {
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
    public static numAttivita() {
        return getLista()?.size()
    } // fine del metodo

    /**
     * Totale attività distinte e NON utilizzate
     */
    public static numAttivitaNonUsate() {
        return getListaNonUsate()?.size()
    } // fine del metodo

} // fine della service classe
