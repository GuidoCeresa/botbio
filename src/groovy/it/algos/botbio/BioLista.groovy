package it.algos.botbio

import it.algos.algoslib.LibTesto
import it.algos.algoslib.LibTime
import it.algos.algospref.Pref
import it.algos.algoswiki.Edit
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
    protected static String INI_RIGA = '*'
    protected static String A_CAPO = '\n'

    private ArrayList listaWrapper
    private String plurale
    private String categoria
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
        this.setPlurale(plurale)
        this.setCategoria(plurale)
        this.setListaWrapper(listaWrapper)
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
        return ''
    }// fine del metodo

    /**
     * Registra la pagina
     */

    public registra() {
        String titolo = this.getTitoloPagina()
        String testo = this.getContenuto()
        String summary = LibBio.getSummary()

        // registra la pagina solo se ci sono differenze significative
        // al di la della prima riga con il richiamo al template e che contiene la data
        if (titolo && testo && this.listaWrapper && this.listaWrapper.size() > 0) {
            try { // prova ad eseguire il codice
                Edit edit = new EditBio(titolo, testo, summary)
            } catch (Exception unErrore) { // intercetta l'errore
                log.error titolo + ' - ' + unErrore
            }// fine del blocco try-catch
        }// fine del blocco if
    }// fine del metodo


    protected static String getTestoIni(int numRec) {
        // variabili e costanti locali di lavoro
        String testo = ''
        boolean usaTavolaContenuti = Pref.getBool(LibBio.USA_TAVOLA_CONTENUTI)
        String dataCorrente = LibTime.getGioMeseAnnoLungo(new Date())
        String numero = LibTesto.formatNum(numRec)
        String tagIndice = '__FORCETOC__'
        String tagNoIndice = '__NOTOC__'
        String aCapo = '\n'

        if (usaTavolaContenuti) {
            testo += tagIndice
        } else {
            testo += tagNoIndice
        }// fine del blocco if-else
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
        String categoria = this.getCategoria()
        String plurale = this.getPlurale()
        String attNazMaiuscola
        String attNazMinuscola

        categoria = LibTesto.primaMaiuscola(categoria)
        plurale = LibTesto.primaMaiuscola(plurale)
        attNazMaiuscola = LibTesto.primaMaiuscola(attNaz)
        attNazMinuscola = LibTesto.primaMinuscola(attNaz)

        testo += aCapo
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
        return testo
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


    public ArrayList getListaWrapper() {
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

    String getTesto() {
        return testo
    }

    void setTesto(String testo) {
        this.testo = testo
    }

    String getTitoloPagina() {
        return titoloPagina
    }

    void setTitoloPagina(String titoloPagina) {
        this.titoloPagina = titoloPagina
    }

    String getCategoria() {
        return categoria
    }

    void setCategoria(String categoria) {
        this.categoria = categoria
    }

    String getPlurale() {
        return plurale
    }

    void setPlurale(String plurale) {
        this.plurale = plurale
    }

}// fine della classe
