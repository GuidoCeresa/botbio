package it.algos.botbio

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 11-11-13
 * Time: 13:07
 */
public enum ErroreGiorno {
    capitano('Capt.', 'Capitano'),
    cavaliere('Cav.', 'Cavaliere'),
    cavaliere2('Il Cavaliere', 'Il cavaliere'),
    commendatore('Comm.', 'Commendatore'),
    ingegnere('Dott. Ing.', 'Ingegnere'),
    dottore('Dr.', 'Dottore'),
    dottore2('Il Dott.', 'Il dottore'),
    dottore3('Il Dottor', 'Il dottore'),
    dottore4('Il Dr.', 'Il dottore')

    String valoreErrato
    String valoreCorretto


    ErroreGiorno(String valoreErrato, String valoreCorretto) {
        /* regola le variabili di istanza coi parametri */
        this.setNomeErrato(nomeErrato)
        this.setNomeCorretto(nomeCorretto)
    } // fine del costruttore


    public static String getNomeCorretto(String nomeErrato) {
        // variabili e costanti locali di lavoro
        String nomeCorretto = null
        String nomeCorrente

        ErroreGiorno.each {
            nomeCorrente = it.nomeErrato
            if (nomeCorrente.equals(nomeErrato)) {
                nomeCorretto = it.nomeCorretto
            }// fine del blocco if
        }// fine di each

        // valore di ritorno
        return nomeCorretto
    }// fine del metodo



}