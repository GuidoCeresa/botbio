package it.algos.botbio

import it.algos.algoslib.LibTesto

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 10-2-14
 * Time: 14:08
 */
class BioListaFin extends BioLista {

    private String tagDiretto = ''
    private String tagInverso = ''
    private static Ordinamento ORDINAMENTO = Ordinamento.alfabetico


    public BioListaFin(String plurale, ArrayList listaDidascalie, def bioLista) {
        // rimanda al costruttore della superclasse
        this(plurale, listaDidascalie, bioLista, ORDINAMENTO)
    }// fine del metodo costruttore completo

    public BioListaFin(String plurale, ArrayList listaDidascalie, def bioLista, Ordinamento ordinamento) {
        // rimanda al costruttore della superclasse
        super(plurale, listaDidascalie, (BioLista) bioLista, ordinamento)
    }// fine del metodo costruttore completo

    /**
     * Regola i tag
     */

    protected regolaTag() {
        // variabili e costanti locali di lavoro
        String tag = '/'

        if (this.bioLista instanceof BioListaAtt) {
            this.tagDiretto = BioListaAttNaz.PATH + BioListaAtt.ATT_NAZ
            this.tagInverso = BioListaAttNaz.PATH + BioListaNaz.ATT_NAZ
        } else if (this.bioLista instanceof BioListaNaz) {
            this.tagDiretto = BioListaAttNaz.PATH + BioListaNaz.ATT_NAZ
            this.tagInverso = BioListaAttNaz.PATH + BioListaAtt.ATT_NAZ
        }// fine del blocco if-else

        this.tagDiretto += tag
        this.tagInverso += tag
    }// fine del metodo

    /**
     * Regola l'ordinamento
     */

    protected regolaOrdinamento() {
        // variabili e costanti locali di lavoro
        ArrayList listaDidascalie = this.getListaWrapper()

        if (listaDidascalie) {
            listaDidascalie = LibBio.ordinaLista(this.listaWrapper, 'ordineAlfabetico')
            this.setListaWrapper(listaDidascalie)
        }// fine del blocco if
    }// fine del metodo

    /**
     * Regola il titolo del paragrafo
     */

    protected creaTitolo() {
        this.titoloParagrafo = this.plurale
    }// fine del metodo

    /**
     * Regola il contenuto
     */

    protected regolaContenuto() {
        // variabili e costanti locali di lavoro
        String testo = ''
        ArrayList listaDidascalie
        String titoloSottopagina
        boolean eccessiva = listaWrapper.size() > NUM_RIGHE_PER_SOTTOPAGINA
        DidascaliaBio didascaliaObj
        String didascaliaTxt

        listaDidascalie = this.listaWrapper

        if (listaDidascalie) {
            testo = '=='
            testo += this.titoloParagrafo
            testo += '=='
            testo += A_CAPO
        }// fine del blocco if

        if (SOTTOPAGINE && eccessiva) {
            titoloSottopagina = this.getSottoTitolo()
            testo += this.getRinvioSottopagina()
            this.creaSottoPagina(listaDidascalie)
        } else {
            listaDidascalie?.each {
                didascaliaTxt = ''
                if (it instanceof DidascaliaBio) {
                    didascaliaObj = (DidascaliaBio) it
                    didascaliaTxt = didascaliaObj.getTestoEstesaSimboli()
                }// fine del blocco if
                testo += INI_RIGA
                testo += didascaliaTxt
                testo += A_CAPO
            }// fine di each
        }// fine del blocco if-else

        if (DOPPIO_SPAZIO) {
            testo += A_CAPO
        }// fine del blocco if

        if (testo) {
            this.testo = testo
        }// fine del blocco if
    }// fine del metodo

    /**
     * creaSottoPagina
     *
     * @param titoloSottopagina
     */

    private void creaSottoPagina(ArrayList listaWrapper) {
        // variabili e costanti locali di lavoro
        BioLista bioLista  =null
        String titoloSottopagina = this.getSottoTitolo()

        if (this.bioLista instanceof BioListaAtt) {
            bioLista = new BioListaAtt(titoloSottopagina, listaWrapper, Ordinamento.alfabetico)
        } else if (this.bioLista instanceof BioListaNaz) {
            bioLista = new BioListaNaz(titoloSottopagina, listaWrapper, Ordinamento.alfabetico)
        }// fine del blocco if-else

        bioLista.categoria = ''
        bioLista.registra()
    }// fine del metodo

    /**
     * Crea il rinvio testuale alla sottopagina
     *
     * @param titoloSottopagina
     * @return testo della riga di rinvio
     */

    private String getRinvioSottopagina() {
        // variabili e costanti locali di lavoro
        String testo = ''
        String rigaRinvio = this.getRinvio()

        if (rigaRinvio) {
            testo = "*{{Vedi anche|${rigaRinvio}}}"
        }// fine del blocco if

        // valore di ritorno
        return testo
    }// fine del metodo

    /**
     * Crea il rinvio testuale alla sottopagina
     *
     * @return titolo della sottoPagina
     */

    private String getSottoTitolo() {
        // variabili e costanti locali di lavoro
        String sottoTitolo
        String pagina
        String paragrafo
        String tag = '/'

        pagina = this.bioLista.plurale
        pagina = LibTesto.primaMaiuscola(pagina)
        paragrafo = this.plurale
        paragrafo = LibTesto.primaMaiuscola(paragrafo)

        sottoTitolo = pagina
        sottoTitolo += tag
        sottoTitolo += paragrafo

        // valore di ritorno
        return sottoTitolo
    }// fine del metodo

    /**
     * Crea il rinvio testuale alla sottopagina
     *
     * @param titoloSottopagina
     * @return testo della riga di rinvio
     */

    private String getRinvio() {
        // variabili e costanti locali di lavoro
        String testoRinvio

        testoRinvio = this.tagDiretto
        testoRinvio += this.getSottoTitolo()

        // valore di ritorno
        return testoRinvio
    }// fine del metodo


    /**
     * Contenuto
     */

    protected String getContenuto() {
        // variabili e costanti locali di lavoro
        String testo = ''
        String testoDidascalie = this.testo
        String aCapo = '\n'

        testo += testoDidascalie

        // valore di ritorno
        return testo
    }// fine del metodo
} // fine della classe
