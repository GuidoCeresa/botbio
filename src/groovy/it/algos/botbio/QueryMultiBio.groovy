package it.algos.botbio

import it.algos.algoslib.LibArray
import it.algos.algoswiki.Login
import it.algos.algoswiki.*

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 8-9-13
 * Time: 08:01
 */
// Query per leggere il contenuto di molte pagine tramite una lista di ID
// Legge solamente
// Non necessita di Login (anche se sarebbe meglio averlo per query con molte pagine)
// Legge molte pagine
// Memorizza il solo contenuto del template Bio e NON tutto il testo della voce
class QueryMultiBio extends QueryMultiId {


    public QueryMultiBio(String listaPageId, Login login) {
        super(listaPageId, login)
    }// fine del metodo costruttore

    public QueryMultiBio(String listaPageId) {
        super(listaPageId)
    }// fine del metodo costruttore

    public QueryMultiBio(ArrayList listaPageId) {
        super(LibArray.creaStringaPipe(listaPageId))
    }// fine del metodo costruttore

    /**
     * Informazioni, contenuto e validita della risposta
     * Controllo del contenuto (testo) ricevuto
     * Estrae i valori e costruisce una mappa
     */
    protected void regolaRisultato() {
        ArrayList listaMappe
        HashMap unaMappa
        String testoVoce
        String testoTemplateBio
        StatoPagina statoPagina

        super.regolaRisultato()
        listaMappe = this.getListaMappe()

        listaMappe.each {
            unaMappa = (HashMap) it

            statoPagina = WikiLib.getStato(unaMappa)
            unaMappa[Const.TAG_STATO_PAGINA] = statoPagina

            testoVoce = unaMappa.testo
            testoTemplateBio = WikiLib.estraeTmpTesto(testoVoce)
            unaMappa[Const.TAG_TESTO] = testoTemplateBio
        } // fine del ciclo each
    } // fine del metodo

} // fine della classe
