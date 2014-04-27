package it.algos.botbio

import it.algos.algoslib.LibTesto
import it.algos.algoslib.LibWiki

/**
 * Created by IntelliJ IDEA.
 * User: Gac
 * Date: 13/02/11
 * Time: 14.46
 */
class BioListaAttNaz extends BioLista {

    public static String PATH = 'Progetto:Biografie/'
    protected String path
    protected String attNaz
    private int numPersoneRighe
    private int numPersoneUnivoche


    public BioListaAttNaz(String plurale, ArrayList listaDidascalie, Ordinamento ordinamento) {
        // rimanda al costruttore della superclasse
        super(plurale, listaDidascalie, ordinamento)
    }// fine del metodo costruttore completo

    public BioListaAttNaz(String plurale, int numPersoneUnivoche, ArrayList listaDidascalie, Ordinamento ordinamento) {
        // rimanda al costruttore della superclasse
        super(plurale, listaDidascalie, ordinamento)
        this.setNumPersoneUnivoche(numPersoneUnivoche)
    }// fine del metodo costruttore completo

    /**
     * Crea il titolo del paragrafo/pagina
     */

    protected creaTitolo() {
        String titoloPagina

        if (this.getPlurale()) {
            titoloPagina = this.path + LibTesto.primaMaiuscola(this.getPlurale())
            if (titoloPagina) {
                this.setTitoloPagina(titoloPagina)
            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo

    /**
     * Regola l'ordinamento
     */
    protected regolaOrdinamento() {
        // variabili e costanti locali di lavoro
        ArrayList listaWrapper = this.getListaWrapper()

        if (listaWrapper) {
            int numPersone = listaWrapper.size()
            setNumPersoneRighe(numPersone)
            switch (ordinamento) {
                case Ordinamento.nazionalitaAlfabetico:
                    listaWrapper = LibBio.divideParagrafi(this)
                    break
                case Ordinamento.attivitaAlfabetico:
                    listaWrapper = LibBio.divideParagrafi(this)
                    break
                case Ordinamento.carattere:
                    listaWrapper = LibBio.divideCarattere(this)
                    break
                case Ordinamento.prestabilitoInMappa:
                    int numero = 0
                    listaWrapper?.each {
                        def mappa = it
                        if (mappa && mappa instanceof Map) {
                            if (mappa[LibBio.MAPPA_LISTA]) {
                                def lista = mappa[LibBio.MAPPA_LISTA]
                                if (lista && lista instanceof ArrayList) {
                                    numero += lista.size()
                                }// fine del blocco if
                            }// fine del blocco if
                        }// fine del blocco if
                    } // fine del ciclo each
                    this.setNumPersoneRighe(numero)
                    //non deve fare nulla
                    break
                default: // caso non definito
                    listaWrapper = LibBio.ordinaLista(this.listaWrapper, 'ordineAlfabetico')
                    break
            } // fine del blocco switch

            // Divide le liste di attività per paragrafi di gruppi di nazionalità
            this.setListaWrapper(listaWrapper)
        }// fine del blocco if
    }// fine del metodo

    /**
     * Regola il contenuto
     */

    protected regolaContenuto() {
        // variabili e costanti locali di lavoro
        String testo = ''
        String testoDidascalie = ''
        ArrayList lista
        BioLista bioListaObj
        DidascaliaBio didascaliaObj
        String didascaliaTxt
        boolean sottoPagina = false

        if (this.listaWrapper) {
            lista = this.getListaWrapper()

            lista?.each {
                if (it instanceof Map) {
                    sottoPagina = false
                    if (it[LibBio.MAPPA_SOTTOPAGINA]) {
                        sottoPagina = it[LibBio.MAPPA_SOTTOPAGINA]
                    }// fine del blocco if
                    if (sottoPagina) {
                        testoDidascalie += paragrafoSottoPagina(it)
                    } else {
                        testoDidascalie += espandeMappa(it)
                    }// fine del blocco if-else
                } else {
                    didascaliaTxt = ''
                    if (it instanceof DidascaliaBio) {
                        didascaliaObj = (DidascaliaBio) it
                        didascaliaTxt = didascaliaObj.getTestoEstesaSimboli()
                    }// fine del blocco if
                    if (it instanceof BioLista) {
                        bioListaObj = (BioLista) it
                        didascaliaTxt = bioListaObj.getTesto()
                    }// fine del blocco if
                    if (didascaliaTxt && !didascaliaTxt.startsWith('=')) {
                        testoDidascalie += INI_RIGA
                    }// fine del blocco if
                    testoDidascalie += didascaliaTxt
                    testoDidascalie += A_CAPO
                }// fine del blocco if-else
            }// fine di each
        }// fine del blocco if

        if (testoDidascalie) {
            testo = testoDidascalie.trim()
        }// fine del blocco if

        this.testo = testo
    }// fine del metodo

    protected String paragrafoSottoPagina(Map mappa) {
        String testo = ''
        String titoloParagrafo = ''
        String sottoTitolo

        if (mappa) {
            if (mappa[LibBio.MAPPA_TITOLO_PARAGRAFO]) {
                titoloParagrafo = mappa[LibBio.MAPPA_TITOLO_PARAGRAFO]
            }// fine del blocco if

            if (titoloParagrafo) {
                testo += '=='
                testo += titoloParagrafo
                testo += '=='
                testo += A_CAPO
            }// fine del blocco if

            testo += INI_RIGA
            sottoTitolo = mappa[LibBio.MAPPA_SOTTO_TITOLO]
            sottoTitolo = 'Vedi anche|' + sottoTitolo
            sottoTitolo = LibWiki.setGraffe(sottoTitolo)
            testo += sottoTitolo
            testo += A_CAPO
        }// fine del blocco if

        creaSottoPagina(mappa)

        return testo
    }// fine del metodo

    protected static String espandeMappa(Map mappa) {
        String testo = ''
        String titoloParagrafo = ''
        ArrayList listaDidascalie = null

        if (mappa) {
            if (mappa[LibBio.MAPPA_TITOLO_PARAGRAFO]) {
                titoloParagrafo = mappa[LibBio.MAPPA_TITOLO_PARAGRAFO]
            }// fine del blocco if

            if (titoloParagrafo) {
                testo += '=='
                testo += titoloParagrafo
                testo += '=='
                testo += A_CAPO
            }// fine del blocco if

            if (mappa[LibBio.MAPPA_LISTA] && mappa[LibBio.MAPPA_LISTA] instanceof ArrayList) {
                listaDidascalie = (ArrayList) mappa[LibBio.MAPPA_LISTA]
            }// fine del blocco if

            if (listaDidascalie && listaDidascalie.size() > 0) {
                listaDidascalie?.each {
                    testo += INI_RIGA
                    testo += it[LibBio.MAPPA_DIDASCALIA]
                    testo += A_CAPO
                } // fine del ciclo each
            }// fine del blocco if
            testo += A_CAPO
        }// fine del blocco if

        return testo
    }// fine del metodo

    protected creaSottoPagina(Map mappa) {
    }// fine del metodo

    /**
     * Contenuto
     */
    protected String getContenuto() {
        // variabili e costanti locali di lavoro
        String testo = ''
        String aCapo = '\n'
        int numRec = 0

        numRec = this.getNumPersoneUnivoche()

        testo += getTestoIni(numRec)
        testo += aCapo
        if (DOPPIO_SPAZIO) {
            testo += aCapo
        }// fine del blocco if
        testo += this.testo
        testo += aCapo
        if (DOPPIO_SPAZIO) {
            testo += aCapo
        }// fine del blocco if
        testo += this.getTestoEnd(this.attNaz)

        // valore di ritorno
        return testo
    }// fine del metodo

    int getNumPersoneRighe() {
        return numPersoneRighe
    }

    void setNumPersoneRighe(int numPersoneRighe) {
        this.numPersoneRighe = numPersoneRighe
    }

    int getNumPersoneUnivoche() {
        return numPersoneUnivoche
    }

    void setNumPersoneUnivoche(int numPersoneUnivoche) {
        this.numPersoneUnivoche = numPersoneUnivoche
    }
} // fine della classe
