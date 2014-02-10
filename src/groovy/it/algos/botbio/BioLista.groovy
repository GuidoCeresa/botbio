package it.algos.botbio

import org.apache.commons.logging.LogFactory

/**
 * Created by IntelliJ IDEA.
 * User: Gac
 * Date: 11/02/11
 * Time: 16.41
 */
class BioLista {

    private static def log = LogFactory.getLog(this)
    protected static String PUNTI = '...'
    public static boolean TRIPLA_ATTIVITA = true //@todo eventuale preferenza
    protected static boolean DOPPIO_SPAZIO = false
    protected static boolean DIM_PARAGRAFO = false
    protected static boolean SOTTOPAGINE = true
    protected static int NUM_RIGHE_PER_SOTTOPAGINA = 50
    protected static boolean CARATTERE_SOTTOPAGINA = true
    protected static int NUM_RIGHE_PER_CARATTERE_SOTTOPAGINA = 50

    private ArrayList listaWrapper
    protected String plurale
    protected String categoria
    protected String titoloPagina
    protected String titoloParagrafo
    protected String campoParagrafo
    protected ArrayList campiParagrafi
    protected String testo
    protected Ordinamento ordinamento
    protected BioLista bioLista


    public BioLista() {
        // rimanda al costruttore della superclasse
        super()
    }// fine del metodo costruttore completo

    public BioLista(String plurale, ArrayList listaWrapper, def bioLista) {
        // rimanda al costruttore della superclasse
        this(plurale, listaWrapper, bioLista, null)
    }// fine del metodo costruttore completo

    public BioLista(String plurale, ArrayList listaDidascalie, Ordinamento ordinamento) {
        // rimanda al costruttore della superclasse
        this(plurale, listaDidascalie, (BioLista) null, ordinamento)
    }// fine del metodo costruttore completo

    public BioLista(String plurale, ArrayList listaWrapper, def bioLista, Ordinamento ordinamento) {
        // rimanda al costruttore della superclasse
        super()

        // regola le variabili di istanza coi parametri
        this.plurale = plurale
        this.categoria = plurale
        this.listaWrapper = listaWrapper
        this.bioLista = bioLista
        this.ordinamento = ordinamento

        // Metodo iniziale con il plurale dell'attività
        this.inizializza()
    }// fine del metodo costruttore completo

    /**
     * Metodo iniziale con la lista delle didascalie
     */

    private inizializza() {

        // Regola i tag
        this.regolaTag()

        // regola l'ordinamento
        this.regolaOrdinamento()

        // Crea il titolo del paragrafo/pagina
        this.creaTitolo()

        // regola il contenuto
        this.regolaContenuto()
    }// fine del metodo

    /**
     * Regola i tag
     */

    protected regolaTag() {
    }// fine del metodo

    /**
     * Regola l'ordinamento
     */

    protected regolaOrdinamento() {
    }// fine del metodo

    /**
     * Crea il titolo del paragrafo/pagina
     */

    protected creaTitolo() {
    }// fine del metodo

    /**
     * Regola il contenuto
     */

    protected regolaContenuto() {
    }// fine del metodo

    /**
     * Contenuto
     */

    protected String getContenuto() {
    }// fine del metodo

    /**
     * Registra la pagina
     */

    public registra() {
        // variabili e costanti locali di lavoro
        String titolo = this.titoloPagina
        String testo = this.getContenuto()
        String summary = BiografiaService.summarySetting()

        // registra la pagina solo se ci sono differente significative
        // al di la della prima riga con il richiamo al template e che contiene la data
        if (titolo && testo && this.listaWrapper && this.listaWrapper.size() > 0) {
            LibBio.caricaPaginaDiversa(titolo, testo, summary, false)
            def stop
        }// fine del blocco if
        def stop
    }// fine del metodo


    protected String getTestoIni(int numRec) {
        // variabili e costanti locali di lavoro
        String testo = ''
        String dataCorrente
        String numero
        String tag = '__NOTOC__'
        String aCapo = '\n'

        dataCorrente = WikiLib.getData('DMY')
        numero = WikiLib.formatNumero(numRec)

        testo += tag
        testo += aCapo
        testo += "<noinclude>"
        testo += "{{StatBio"
        testo += "|bio="
        testo += numero
        testo += "|data="
        testo += dataCorrente.trim()
        testo += "}}"
        testo += "</noinclude>"

        // valore di ritorno
        return testo.trim()
    }// fine del metodo


    protected String getTestoEnd(String attNaz) {
        // variabili e costanti locali di lavoro
        String testo = ''
        String aCapo = '\n'
        String categoria = this.categoria
        String plurale = this.plurale
        String attNazMaiuscola
        String attNazMinuscola

        categoria = WikiLib.primaMaiuscola(categoria)
        plurale = WikiLib.primaMaiuscola(plurale)
        attNazMaiuscola = WikiLib.primaMaiuscola(attNaz)
        attNazMinuscola = WikiLib.primaMinuscola(attNaz)

        testo += '==Voci correlate=='
        testo += aCapo
        if (categoria) {
            testo += "*[[:Categoria:${categoria}]]"
            testo += aCapo
        }// fine del blocco if
        testo += "*[[Progetto:Biografie/${attNazMaiuscola}]]"
        testo += aCapo
        testo += aCapo
        testo += '{{Portale|biografie}}'
        testo += aCapo
        testo += aCapo
        if (categoria) {
            testo += "<noinclude>[[Categoria:Bio ${attNazMinuscola}|${plurale}]]</noinclude>"
        } else {
            testo += "<noinclude>[[Categoria:Bio ${attNazMinuscola}]]</noinclude>"
        }// fine del blocco if-else

        // valore di ritorno
        return testo.trim()
    }// fine del metodo

    protected int dimWrapper() {
        // variabili e costanti locali di lavoro
        int dimWrapper = 0

        if (this.listaWrapper) {
            if (this.listaWrapper.size() > 0 && this.listaWrapper[0] in BioLista) {
                this.listaWrapper?.each {
                    dimWrapper += it.listaWrapper.size()
                }// fine di each
            } else {
                dimWrapper = this.listaWrapper.size()
            }// fine del blocco if-else
        }// fine del blocco if

        // valore di ritorno
        return dimWrapper
    }// fine del metodo


    protected void setListaWrapper(ArrayList listaWrapper) {
        this.listaWrapper = listaWrapper
    }


    protected ArrayList getListaWrapper() {
        return listaWrapper
    }


    protected void setCampoParagrafo(String campoParagrafo) {
        this.campoParagrafo = campoParagrafo
    }


    protected String getCampoParagrafo() {
        return campoParagrafo
    }


    protected void setCampiParagrafi(ArrayList campiParagrafi) {
        this.campiParagrafi = campiParagrafi
    }


    protected ArrayList getCampiParagrafi() {
        return campiParagrafi
    }

}// fine della classe
