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
    protected int numPersone


    public BioListaAttNaz(String plurale, ArrayList listaDidascalie, Ordinamento ordinamento) {
        // rimanda al costruttore della superclasse
        super(plurale, listaDidascalie, ordinamento)
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
            this.numPersone = listaWrapper.size()
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
                    this.numPersone = numero
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
        String testo = ''
        BioListaAttNaz bioLista = null
        ArrayList lista = new ArrayList()
        Map newMappa = new HashMap()
        ArrayList listaDidascalie
        String attivita
        String sottoAttivita
        String nazionalita
        int livello

        if (mappa) {
            listaDidascalie = (ArrayList) mappa[LibBio.MAPPA_LISTA]

            attivita = mappa[LibBio.MAPPA_ATTIVITA]
            nazionalita = mappa[LibBio.MAPPA_NAZIONALITA]
            livello = (int) mappa[LibBio.MAPPA_LIVELLO]
            sottoAttivita = attivita + '/' + nazionalita
            def sottit = mappa[LibBio.MAPPA_SOTTO_TITOLO]

            bioLista = new BioListaAtt(sottoAttivita, creaListaSottoMappe(listaDidascalie, attivita, nazionalita, livello), Ordinamento.prestabilitoInMappa)
            bioLista.setCategoria(attivita)

            bioLista.registra()
        }// fine del blocco if
    }// fine del metodo

    protected ArrayList<Map> creaListaSottoMappe(ArrayList listaDidascalie, String attivita, String nazionalita, int livello) {
        ArrayList<Map> listaSottoMappe = new ArrayList()
        ArrayList<Map> listaMappeDidascalie
        ArrayList keyList
        Map mappaDidascalia
        Map mappa
        String didascalia
        String primaLettera
        String titoloSottopagina = this.getTitoloPagina() + '/' + nazionalita


        if (listaDidascalie) {

            keyList = listaDidascalie.collect { it[LibBio.MAPPA_PRIMA_LETTERA] }.unique()
            keyList.sort()


            keyList.each {
                listaMappeDidascalie = new ArrayList<Map>()
                def alfa = it

                primaLettera = it
                listaDidascalie.each {
                    if (it[LibBio.MAPPA_PRIMA_LETTERA].equals(primaLettera)) {
                        didascalia = it[LibBio.MAPPA_DIDASCALIA]
                        mappaDidascalia = new HashMap()
//                        mappaDidascalia.put(LibBio.MAPPA_PRIMA_LETTERA, primaLettera)
                        mappaDidascalia.put(LibBio.MAPPA_DIDASCALIA, didascalia)
                        listaMappeDidascalie.add(mappaDidascalia)
                    }// fine del blocco if
                } // fine del ciclo each
                mappa = new HashMap()
                mappa.put(LibBio.MAPPA_TITOLO_PARAGRAFO, primaLettera)
                mappa.put(LibBio.MAPPA_SOTTO_TITOLO, titoloSottopagina + '/' + primaLettera)
                mappa.put(LibBio.MAPPA_LISTA, listaMappeDidascalie)
                mappa.put(LibBio.MAPPA_NUMERO, listaMappeDidascalie.size())
                mappa.put(LibBio.MAPPA_ORDINE, Ordinamento.prestabilitoInMappa.toString())
                if (livello < 2) {
                    mappa.put(LibBio.MAPPA_SOTTOPAGINA, listaMappeDidascalie.size() > NUM_RIGHE_PER_CARATTERE_SOTTOPAGINA)
                } else {
                    mappa.put(LibBio.MAPPA_SOTTOPAGINA, false)
                }// fine del blocco if-else
                mappa.put(LibBio.MAPPA_ATTIVITA, attivita)
                mappa.put(LibBio.MAPPA_NAZIONALITA, nazionalita + '/' + primaLettera)
                mappa.put(LibBio.MAPPA_LIVELLO, livello + 1)
                listaSottoMappe.add(mappa)
            } // fine del ciclo each
        }// fine del blocco if

        return listaSottoMappe
    }// fine del metodo

    /**
     * Contenuto
     */
    protected String getContenuto() {
        // variabili e costanti locali di lavoro
        String testo = ''
        String aCapo = '\n'
        int numRec = 0

        numRec = this.numPersone

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

} // fine della classe
