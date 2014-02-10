package it.algos.botbio

/**
 * Created by IntelliJ IDEA.
 * User: Gac
 * Date: 13/02/11
 * Time: 14.46
 */
class BioListaNaz extends BioListaAttNaz {

    public static String ATT_NAZ = 'Nazionalità'
    private static Ordinamento ORDINAMENTO = Ordinamento.attivitaAlfabetico


    public BioListaNaz(String plurale, ArrayList listaDidascalie) {
        // rimanda al costruttore della superclasse
        this(plurale, listaDidascalie, ORDINAMENTO)
    }// fine del metodo costruttore completo


    public BioListaNaz(String plurale, ArrayList listaDidascalie, Ordinamento ordinamento) {
        // rimanda al costruttore della superclasse
        super(plurale, listaDidascalie, ordinamento)
    }// fine del metodo costruttore completo

    /**
     * Regola i tag
     */

    protected regolaTag() {
        super.path = PATH + ATT_NAZ + '/'
        super.attNaz = ATT_NAZ

        if (BioLista.TRIPLA_ATTIVITA) {
            super.setCampiParagrafi(['attivitaPlurale', 'attivita2Plurale', 'attivita3Plurale'])
        } else {
            super.setCampoParagrafo('attivitaPlurale')
        }// fine del blocco if-else
    }// fine del metodo

    protected String getTestoEnd(String attNaz) {
        // variabili e costanti locali di lavoro
        String testo = ''
        String aCapo = '\n'
        int dimPagina = this.numPersone
        int dimWrapper = this.dimWrapper()
        boolean dimDiversa = (dimWrapper > dimPagina)

        if (BioLista.TRIPLA_ATTIVITA && this.ordinamento == Ordinamento.attivitaAlfabetico && dimDiversa) {
            testo += '==Note=='
            testo += aCapo
            testo += 'Alcune persone sono citate più volte perché hanno diverse attività'
            testo += aCapo
            testo += aCapo
        }// fine del blocco if
        testo += super.getTestoEnd(attNaz)

        // valore di ritorno
        return testo.trim()
    }// fine del metodo

} // fine della classe
