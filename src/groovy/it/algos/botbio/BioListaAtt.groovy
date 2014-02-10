package it.algos.botbio

/**
 * Created by IntelliJ IDEA.
 * User: Gac
 * Date: 13/02/11
 * Time: 14.46
 */
class BioListaAtt extends BioListaAttNaz {

    public static String ATT_NAZ = 'Attività'
    private static Ordinamento ORDINAMENTO = Ordinamento.nazionalitaAlfabetico


    public BioListaAtt(String plurale, ArrayList listaDidascalie) {
        // rimanda al costruttore della superclasse
        this(plurale, listaDidascalie, ORDINAMENTO)
    }// fine del metodo costruttore completo


    public BioListaAtt(String plurale, ArrayList listaDidascalie, Ordinamento ordinamento) {
        // rimanda al costruttore della superclasse
        super(plurale, listaDidascalie, ordinamento)
    }// fine del metodo costruttore completo

    /**
     * Regola i tag
     */

    protected regolaTag() {
        super.path = PATH + ATT_NAZ + '/'
        super.attNaz = ATT_NAZ
        super.setCampoParagrafo('nazionalitaPlurale')
    }// fine del metodo

} // fine della classe
