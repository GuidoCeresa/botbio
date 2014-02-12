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
import it.algos.algospref.Preferenze
import it.algos.algoswiki.Edit
import it.algos.algoswiki.WikiLib

class StatisticheService {

    def attivitaService
    def nazionalitaService

    public static String PATH = 'Progetto:Biografie/'
    private static String A_CAPO = '\n'

    /**
     * Aggiorna la pagina wiki di servizio delle attività
     */
    public attivitaUsate() {
        // variabili e costanti locali di lavoro
        String titolo = PATH + 'Attività'
        String testo = ''
        String summary = Preferenze.getStr(LibBio.SUMMARY)
        int numUsate = AttivitaService.numAttivita()
        int numNonUsate = AttivitaService.numAttivitaNonUsate()

        testo += getTestoTop()
        testo += getTestoBody(AttivitaNazionalita.attivita, numUsate, numNonUsate)
        testo += getTestoBottom(AttivitaNazionalita.attivita)

        if (titolo && testo && summary) {
            new Edit(titolo, testo, summary)
        }// fine del blocco if
    }// fine del metodo

    /**
     * Aggiorna la pagina wiki di servizio delle nazionalità
     */
    public nazionalitaUsate() {
        String titolo = PATH + 'Nazionalità'
        String testo = ''
        String summary = Preferenze.getStr(LibBio.SUMMARY)
        int numUsate = NazionalitaService.numNazionalita()
        int numNonUsate = NazionalitaService.numNazionalitaNonUsate()

        testo += getTestoTop()
        testo += getTestoBody(AttivitaNazionalita.nazionalita, numUsate, numNonUsate)
        testo += getTestoBottom(AttivitaNazionalita.nazionalita)

        if (titolo && testo && summary) {
            new Edit(titolo, testo, summary)
        }// fine del blocco if
    }// fine del metodo

    /**
     * Costruisce il testo iniziale della pagina
     */
    private static String getTestoTop() {
        // variabili e costanti locali di lavoro
        String testo = ''
        String dataCorrente = LibTime.getGioMeseAnnoLungo(new Date())

        // controllo di congruità
        if (dataCorrente) {
            testo += '<noinclude>'
            testo += "{{StatBio|data=$dataCorrente}}"
            testo += '</noinclude>'
            testo += A_CAPO
            testo += A_CAPO
        }// fine del blocco if

        // valore di ritorno
        return testo
    }// fine del metodo

    /**
     * Costruisce il testo variabile della pagina
     */
    private String getTestoBody(AttivitaNazionalita tipoAttNaz, int numUsate, int numNonUsate) {
        // variabili e costanti locali di lavoro
        String testo = ''

        testo += '==Usate=='
        testo += A_CAPO
        testo += A_CAPO
        switch (tipoAttNaz) {
            case AttivitaNazionalita.attivita:
                testo += "'''$numUsate''' attività '''effettivamente utilizzate''' nelle voci biografiche che usano il [[template:Bio|template Bio]]."
                testo += A_CAPO
                testo += this.creaTabellaAttivita()
                break
            case AttivitaNazionalita.nazionalita:
                testo += "'''$numUsate''' nazionalità '''effettivamente utilizzate''' nelle voci biografiche che usano il [[template:Bio|template Bio]]."
                testo += A_CAPO
                testo += this.creaTabellaNazionalita()
                break
            default: // caso non definito
                break
        } // fine del blocco switch
        testo += A_CAPO
        testo += A_CAPO
        testo += '==Non usate=='
        testo += A_CAPO
        testo += A_CAPO
        switch (tipoAttNaz) {
            case AttivitaNazionalita.attivita:
                testo += "'''$numNonUsate''' attività presenti nella [[Template:Bio/plurale attività|tabella]] ma '''non utilizzate''' in nessuna voce biografica"
                testo += A_CAPO
                testo += this.creaTabellaAttivitaNonUsate()
                break
            case AttivitaNazionalita.nazionalita:
                testo += "'''$numNonUsate''' nazionalità presenti nella [[Template:Bio/plurale nazionalità|tabella]] ma '''non utilizzate''' in nessuna voce biografica"
                testo += A_CAPO
                testo += this.creaTabellaNazionalitaNonUsate()
                break
            default: // caso non definito
                break
        } // fine del blocco switch
        testo += A_CAPO
        testo += A_CAPO
    }// fine del metodo

    /**
     * Costruisce il testo finale della pagina
     */
    private String getTestoBottom(AttivitaNazionalita tipoAttNaz) {
        String testo = ''
        boolean loMettoPerchéFunziona = false

        testo += '==Voci correlate=='
        testo += A_CAPO
        if (loMettoPerchéFunziona) {
            testo += A_CAPO
            testo += '*[[Progetto:Biografie/Statistiche]]'
        }// fine del blocco if
        testo += A_CAPO
        switch (tipoAttNaz) {
            case AttivitaNazionalita.attivita:
                testo += '*[[Progetto:Biografie/Nazionalità]]'
                break
            case AttivitaNazionalita.nazionalita:
                testo += '*[[Progetto:Biografie/Attività]]'
                break
            default: // caso non definito
                break
        } // fine del blocco switch
        if (loMettoPerchéFunziona) {
            testo += A_CAPO
            testo += '*[[Progetto:Biografie/Parametri]]'
        }// fine del blocco if
        testo += A_CAPO
        testo += '*[[:Categoria:Bio parametri]]'
        testo += A_CAPO
        testo += '*[[:Categoria:Bio attività]]'
        testo += A_CAPO
        testo += '*[[:Categoria:Bio nazionalità]]'
        testo += A_CAPO
        testo += '*[https://it.wikipedia.org/w/index.php?title=Modulo:Bio/Plurale_attività&action=edit Lista delle attività nel modulo (protetto)]'
        testo += A_CAPO
        testo += '*[https://it.wikipedia.org/w/index.php?title=Modulo:Bio/Plurale_nazionalità&action=edit Lista delle nazionalità nel modulo (protetto)]'
        testo += A_CAPO
        testo += A_CAPO
        testo += '<noinclude>'
        testo += '[[Categoria:Progetto Biografie|{{PAGENAME}}]]'
        testo += '</noinclude>'

        // valore di ritorno
        return testo
    }// fine del metodo

    /**
     * Costruisce la tabella delle attività
     *
     * @return testo
     */
    def creaTabellaAttivita() {
        // variabili e costanti locali di lavoro
        String testoTabella
        ArrayList listaRighe = new ArrayList()
        def listaAttivita
        def mappa = new HashMap()
        int k = 0

        listaRighe.add(getRigaTitolo(AttivitaNazionalita.attivita))
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
     * Costruisce la tabella delle attività
     *
     * @return testo
     */
    def creaTabellaNazionalita() {
        // variabili e costanti locali di lavoro
        String testoTabella
        ArrayList listaRighe = new ArrayList()
        def listaNazionalita
        def mappa = new HashMap()
        int k = 0

        listaRighe.add(getRigaTitolo(AttivitaNazionalita.nazionalita))
        listaNazionalita = NazionalitaService.getLista()
        listaNazionalita?.each {
            k++
            listaRighe.add(nazionalitaService.getRigaNazionalita(k, it))
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
     * Costruisce la tabella delle attività NON utilizzate
     *
     * @return testo
     */
    def creaTabellaAttivitaNonUsate() {
        // variabili e costanti locali di lavoro
        String testoTabella
        def listaRighe = new ArrayList()
        def listaAttivita
        def mappa = new HashMap()
        int k = 0

        listaRighe.add(getRigaTitoloNonUsate(AttivitaNazionalita.attivita))
        listaAttivita = AttivitaService.getListaNonUsate()
        listaAttivita?.each {
            k++
            listaRighe.add(attivitaService.getRigaAttivitaNonUsate(k, it))
        }// fine di each

        //costruisce il testo della tabella
        mappa.put('lista', listaRighe)
        mappa.put('width', '60')
//        mappa.putAt('align', TipoAllineamento.secondaSinistra)
        testoTabella = WikiLib.creaTabellaSortable(mappa)

        // valore di ritorno
        return testoTabella
    } // fine della closure

    /**
     * Costruisce la tabella delle nazionalità NON utilizzate
     *
     * @return testo
     */
    def creaTabellaNazionalitaNonUsate() {
        // variabili e costanti locali di lavoro
        String testoTabella
        def listaRighe = new ArrayList()
        def listaNazionalita
        def mappa = new HashMap()
        int k = 0

        listaRighe.add(getRigaTitoloNonUsate(AttivitaNazionalita.attivita))
        listaNazionalita = NazionalitaService.getListaNonUsate()
        listaNazionalita?.each {
            k++
            listaRighe.add(nazionalitaService.getRigaNazionalitaNonUsate(k, it))
        }// fine di each

        //costruisce il testo della tabella
        mappa.put('lista', listaRighe)
        mappa.put('width', '60')
//        mappa.putAt('align', TipoAllineamento.secondaSinistra)
        testoTabella = WikiLib.creaTabellaSortable(mappa)

        // valore di ritorno
        return testoTabella
    } // fine della closure

    /**
     * Restituisce l'array delle riga del titolo della tabella delle attività
     */
    def getRigaTitolo(AttivitaNazionalita tipoAttNaz) {
        // variabili e costanti locali di lavoro
        def riga = ''

        switch (tipoAttNaz) {
            case AttivitaNazionalita.attivita:
                riga = ["'''#'''", "'''attività utilizzate'''", "'''prima'''", "'''seconda'''", "'''terza'''", "'''totale'''"]
                break
            case AttivitaNazionalita.nazionalita:
                riga = ["'''#'''", "'''nazionalità utilizzate'''", "'''num'''"]
                break
            default: // caso non definito
                break
        } // fine del blocco switch

        // valore di ritorno
        return riga
    }// fine del metodo

    /**
     * Restituisce l'array delle riga del titolo della tabella delle attività NON utilizzate
     */
    def getRigaTitoloNonUsate(AttivitaNazionalita tipoAttNaz) {
        // variabili e costanti locali di lavoro
        def riga = ''

        switch (tipoAttNaz) {
            case AttivitaNazionalita.attivita:
                riga = ["'''#'''", "'''attività non utilizzate'''"]
                break
            case AttivitaNazionalita.nazionalita:
                riga = ["'''#'''", "'''nazionalità non utilizzate'''"]
                break
            default: // caso non definito
                break
        } // fine del blocco switch

        // valore di ritorno
        return riga
    } // fine della closure

    public enum AttivitaNazionalita {
        attivita, nazionalita
    } // fine della Enumeration

} // fine della service classe
