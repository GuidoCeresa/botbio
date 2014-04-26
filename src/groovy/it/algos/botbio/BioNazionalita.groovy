package it.algos.botbio

import org.apache.commons.logging.LogFactory

/**
 * Created by IntelliJ IDEA.
 * User: Gac
 * Date: 10/02/11
 * Time: 21.25
 */
class BioNazionalita extends BioAttNaz {

    private static def log = LogFactory.getLog(this)

    public BioNazionalita(String plurale) {
        super(plurale)
    }// fine del metodo costruttore completo

    /**
     * Metodo iniziale con il plurale dell'attività
     *
     * @param plurale
     */
    protected inizializza(String plurale) {
        super.inizializza(plurale)

        // Crea paragrafo/pagina con le didascalie
        this.bioLista = new BioListaNaz(this.plurale, this.listaDidascalie)
    } // fine del metodo

    /**
     * Crea una lista di records di nazionalità utilizzati
     */
    protected creaListaSingolariId() {
        String nazionalitaPlurale = this.getPlurale()
        ArrayList<Long> listaSingolariID
        String query
        String tag = "'"

        if (nazionalitaPlurale) {
            if (!nazionalitaPlurale.contains(tag)) {
                query = "select id from Nazionalita where plurale='$nazionalitaPlurale'"
                listaSingolariID = (ArrayList<Long>) Nazionalita.executeQuery(query)
                this.setListaSingolariID(listaSingolariID)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    /**
     * Crea una lista di id (del DB Grails) per un singolo record di attività attività
     *
     * @param attivitaId (grails id)
     * @return listaVociId (grails id)
     */
    protected ArrayList<Long> creaListaVociIdSingolare(long nazionalitaId) {
        // variabili e costanti locali di lavoro
        ArrayList<Long> listaVociId = null
        String query = 'select id from BioGrails where nazionalita_link_id='

        // controllo di congruità
        if (nazionalitaId && nazionalitaId > 0) {
            try { // prova ad eseguire il codice
                query += "${nazionalitaId}"
                listaVociId = (ArrayList<Long>) BioGrails.executeQuery(query)

            } catch (Exception unErrore) { // intercetta l'errore
                log.error "Query fallita (creaListaVociIdSingolare): ${nazionalitaId}"
            }// fine del blocco try-catch
        }// fine del blocco if

        // valore di ritorno
        return listaVociId
    } // fine della closure

}// fine della classe
