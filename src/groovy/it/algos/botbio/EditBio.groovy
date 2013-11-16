package it.algos.botbio

import it.algos.algospref.Preferenze
import it.algos.algoswiki.Edit

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 15-11-13
 * Time: 08:22
 */
class EditBio extends Edit {

    private boolean paginaDaRegistrare

    //--usa il titolo della pagina
    public EditBio(String titolo, String testoNew, String summary) {
        super(titolo, testoNew, summary)
    }// fine del metodo costruttore

    //--recupera il testo ricevuto dalla prima Request
    //--controlla che esistano modifiche sostanziali (non solo la data)
    protected void regolaTesto() {
        paginaDaRegistrare = true
        boolean registraSoloModificheSostanziali = Preferenze.getBool(LibBio.REGISTRA_SOLO_MODIFICHE_SOSTANZIALI)
        String testoOld = super.getTestoPrimaRequest()
        String tagInizioVoceSignificativa = '</noinclude>'
        String testoOldSignificativo = ''
        String testoNew = super.getTestoNew()
        String testoNewSignificativo

        if (registraSoloModificheSostanziali) {
            if (testoOld) {
                testoOldSignificativo = testoOld.substring(testoOld.indexOf(tagInizioVoceSignificativa))
            }// fine del blocco if
            if (testoNew) {
                testoNewSignificativo = testoNew.substring(testoNew.indexOf(tagInizioVoceSignificativa))
            }// fine del blocco if
            if (testoOldSignificativo && testoNewSignificativo) {
                if (testoNewSignificativo.equals(testoOldSignificativo)) {
                    paginaDaRegistrare = false
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    //--controllo prima di eseguire la seconda request
    protected boolean eseguiSecondaRequest() {
        return paginaDaRegistrare
    } // fine del metodo

} // fine della classe
