package it.algos.botbio

import it.algos.algoswiki.QueryRev
import it.algos.algoswiki.WikiLib
/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 28-8-13
 * Time: 14:51
 */
class QueryBio extends QueryRev {

    private String testoBio


    public QueryBio(int pageid) {
        super(pageid)
    }// fine del metodo costruttore

    /**
     * Informazioni, contenuto e validita della risposta
     * Controllo del contenuto (testo) ricevuto
     * Estrae i valori e costruisce una mappa
     *
     * Richiama il metodo della superclasse
     * Aggiunge la regolazione del testo specifico del template Bio
     */
    protected void regolaRisultato() {
        String testoBio
        String testoVoce

        super.regolaRisultato()

        testoVoce = this.getTesto()
        if (testoVoce) {
            testoBio = WikiLib.estraeTmpTesto(testoVoce)
            if (testoBio) {
                this.setTestoBio(testoBio)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    /**
     * Crea un'istanza
     * Restituisce il contenuto del template Bio
     *
     * @return testo della pagina
     */
    public static String leggeTestoBio(int pageid) {
        // variabili e costanti locali di lavoro
        String testoBio = ''
        QueryBio query

        if (pageid) {
            query = new QueryBio(pageid)
            if (query) {
                testoBio = query.getTestoBio()
            }// fine del blocco if
        }// fine del blocco if

        // valore di ritorno
        return testoBio
    } // fine del metodo

    /**
     * Crea un'istanza
     * Controlla se esiste il template Bio
     *
     * @return vero se esiste la voce col template Bio
     */
    public static boolean isBio(int pageid) {
        // variabili e costanti locali di lavoro
        boolean isBioValido = false
        String testoBio = leggeTestoBio(pageid)

        if (testoBio && testoBio.length() > 0) {
            isBioValido = true
        }// fine del blocco if

        // valore di ritorno
        return isBioValido
    } // fine del metodo

    String getTestoBio() {
        return testoBio
    }

    void setTestoBio(String testoBio) {
        this.testoBio = testoBio
    }
} // fine della classe
