package it.algos.botbio

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
        // variabili e costanti locali di lavoro
        String titoloPagina = ''

        if (this.plurale) {
            titoloPagina = this.path + WikiLib.primaMaiuscola(this.plurale)
            if (titoloPagina) {
                this.titoloPagina = titoloPagina
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
        ArrayList listaDidascalie
        String aCapo = '\n'

        if (this.listaWrapper) {
            listaDidascalie = this.listaWrapper

            listaDidascalie?.each {
                testoDidascalie += it.testo
                testoDidascalie += aCapo
            }// fine di each
        }// fine del blocco if

        if (testoDidascalie) {
            testo = testoDidascalie.trim()
        }// fine del blocco if

        this.testo = testo
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

        testo += this.getTestoIni(numRec)
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
