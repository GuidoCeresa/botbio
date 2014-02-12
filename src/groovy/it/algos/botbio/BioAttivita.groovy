package it.algos.botbio

import org.apache.commons.logging.LogFactory

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 9-2-14
 * Time: 08:48
 */
class BioAttivita extends BioAttNaz {

    private static def log = LogFactory.getLog(this)

    private static boolean USA_ANCHE_ATTIVITA_DUE = true
    private static boolean USA_ANCHE_ATTIVITA_TRE = true

    public BioAttivita(String plurale) {
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
        this.bioLista = new BioListaAtt(getPlurale(), getListaDidascalie())
    } // fine del metodo

    /**
     * Crea una lista di records di attività utilizzati
     * Per ogni plurale, ci possono essere diversi 'singolari' richiamati dalle voci di BioGrails
     */
    protected creaListaSingolariId() {
        String attivitaPlurale = this.getPlurale()
        ArrayList<Long> listaSingolariID
        String query
        String tag = "'"

        if (attivitaPlurale) {
            if (!attivitaPlurale.contains(tag)) {
                query = "select id from Attivita where plurale='$attivitaPlurale'"
                listaSingolariID = (ArrayList<Long>) Attivita.executeQuery(query)
                this.setListaSingolariID(listaSingolariID)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo


    /**
     * Crea una lista di voci biografiche per un singolo record di attività attività
     *
     * @param attivitaId (grails id)
     * @return listaVociId (grails id)
     */
    protected  ArrayList<Long> creaListaVociIdSingolare(long attivitaId) {
        ArrayList<Long> listaVociId = null
        String query

        // controllo di congruità
        if (attivitaId && attivitaId > 0) {
            try { // prova ad eseguire il codice
                query = getQueryParziale(1) + "${attivitaId}"
                listaVociId = (ArrayList<Long>) BioGrails.executeQuery(query)

                if (USA_ANCHE_ATTIVITA_DUE) {
                    query = getQueryParziale(2) + "${attivitaId}"
                    listaVociId += (ArrayList<Long>) BioGrails.executeQuery(query)
                }// fine del blocco if

                if (USA_ANCHE_ATTIVITA_TRE) {
                    query = getQueryParziale(3) + "${attivitaId}"
                    listaVociId += (ArrayList<Long>) BioGrails.executeQuery(query)
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                log.error "Query fallita (creaListaVociIdSingolare): ${attivitaId}"
            }// fine del blocco try-catch
        }// fine del blocco if

        // valore di ritorno
        return listaVociId
    } // fine del metodo

    /**
     * Crea una query (parziale) col nome del campo
     *
     * @param num di attività (principale, secondaria o terziaria)
     * @return query
     */
    private static String getQueryParziale(int num) {
        // variabili e costanti locali di lavoro
        String query = ''
        String tag = "select id from BioGrails where "
        String attivitaUno = 'attivita_link_id'
        String attivitaDue = 'attivita2link_id'
        String attivitaTre = 'attivita3link_id'

        // controllo di congruità
        if (num && num > 0 && num <= 3) {
            switch (num) {
                case 1:
                    query = tag + "${attivitaUno}="
                    break
                case 2:
                    query = tag + "${attivitaDue}="
                    break
                case 3:
                    query = tag + "${attivitaTre}="
                    break
                default: // caso non definito
                    break
            } // fine del blocco switch
        }// fine del blocco if

        // valore di ritorno
        return query
    } // fine del metodo


} // fine della classe
