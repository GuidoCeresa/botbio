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

import it.algos.algoslib.LibTime
import it.algos.algoswiki.Edit
import it.algos.algoswiki.WikiLib

class StatisticheService {

    def attivitaService

    public static String PATH = 'Progetto:Biografie/'
    private static String A_CAPO = '\n'

    /**
     * Aggiorna la pagina wiki di servizio delle attività
     */
    public attivitaUsate() {
        // variabili e costanti locali di lavoro
        String nota = 'uploadAttivita'
        String dataCorrente = LibTime.getGioMeseAnnoLungo(new Date())
        String titolo = PATH + 'Attività'
        String testo
        String summary = BioService.summarySetting()
        int num = AttivitaService.numAttivita()
        int numNonUsate = AttivitaService.numAttivitaNonUsate()

        testo = '<noinclude>'
        testo += "{{StatBio|data=$dataCorrente}}"
        testo += '</noinclude>'
        testo += A_CAPO
        testo += A_CAPO
        testo += '==Usate=='
        testo += A_CAPO
        testo += A_CAPO
        testo += "'''$num''' attività '''effettivamente utilizzate''' nelle voci biografiche che usano il [[template:Bio|template Bio]]."
        testo += A_CAPO
        testo += this.creaTabellaAttivita()
        testo += A_CAPO
        testo += A_CAPO
        testo += '==Non usate=='
        testo += A_CAPO
        testo += A_CAPO
        testo += "'''$numNonUsate''' attività presenti nella [[Template:Bio/plurale attività|tabella]] ma '''non utilizzate''' in nessuna voce biografica"
        testo += A_CAPO
//        testo += this.creaPrettyTableAttivitaNonUsate()
        testo += A_CAPO
        testo += A_CAPO
        testo += '==Voci correlate=='
        testo += A_CAPO
        testo += A_CAPO
        testo += '*[[Progetto:Biografie/Statistiche]]'
        testo += A_CAPO
        testo += '*[[Progetto:Biografie/Nazionalità]]'
        testo += A_CAPO
        testo += '*[[Progetto:Biografie/Parametri]]'
        testo += A_CAPO
        testo += '*[[:Categoria:Bio parametri]]'
        testo += A_CAPO
        testo += '*[[:Categoria:Bio attività]]'
        testo += A_CAPO
        testo += '*[[:Categoria:Bio nazionalità]]'
        testo += A_CAPO
        testo += '*[http://it.wikipedia.org/w/index.php?title=Template:Bio/plurale_attività&action=edit Tabella delle attività nel template (protetta)]'
        testo += A_CAPO
        testo += '*[http://it.wikipedia.org/w/index.php?title=Template:Bio/plurale_nazionalità&action=edit Tabella delle nazionalità nel template (protetta)]'
        testo += A_CAPO
        testo += A_CAPO
        testo += '<noinclude>'
        testo += '[[Categoria:Progetto Biografie|{{PAGENAME}}]]'
        testo += '</noinclude>'

        new Edit(titolo, testo, summary)
    }// fine del metodo

    /**
     * Costruisce la tabella delle attività
     *
     * @return testo
     */
    def creaTabellaAttivita() {
        // variabili e costanti locali di lavoro
        String testo
        String testoTabella
        ArrayList listaRighe = new ArrayList()
        def listaAttivita
        def mappa = new HashMap()
        int k = 0

        listaRighe.add(getRigaTitoloAttivita())
        listaAttivita = AttivitaService.getLista()
        listaAttivita?.each {
            k++
            listaRighe.add(attivitaService.getRigaAttivita(k, it))
        }// fine di each

        //costruisce il testo della tabella
        mappa.put('lista', listaRighe)
        mappa.put('width', '60')
//        mappa.putAt('align', TipoAllineamento.secondaSinistra)
        testoTabella = WikiLib.creaTabellaSortable(mappa)

        // valore di ritorno
        return testoTabella
    }// fine del metodo

    /**
     * Restituisce l'array delle riga del titolo della tabella delle attività
     */
    def getRigaTitoloAttivita() {
        // variabili e costanti locali di lavoro
        def riga

        riga = ["'''#'''", "'''attività utilizzate'''", "'''prima'''", "'''seconda'''", "'''terza'''", "'''totale'''"]

        // valore di ritorno
        return riga
    }// fine del metodo

} // fine della service classe
