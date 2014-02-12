package it.algos.botbio

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 12-2-14
 * Time: 07:50
 */
abstract class BioAttNaz {

    private String plurale
    private ArrayList<Long> listaSingolariID
    private ArrayList<Long> listaVociId
    private ArrayList listaDidascalie
    private BioLista bioLista

    public BioAttNaz(String plurale) {
        super()
        this.inizializza(plurale)
    }// fine del metodo costruttore completo

    /**
     * Metodo iniziale con il plurale dell'attività
     *
     * @param plurale
     */
    protected inizializza(String plurale) {
        // regola le variabili di istanza coi parametri
        this.setPlurale(plurale)

        // Crea una lista di records di attività utilizzati da BioGrails
        this.creaListaSingolariId()

        // Crea una lista di voci biografiche che utilizzano questa attività
        this.creaListaVociId()

        // Crea una lista di didascalie
        this.creaListaDidascalie()

        // Crea paragrafo/pagina con le didascalie
        this.bioLista = new BioListaAtt(getPlurale(), getListaDidascalie())
    } // fine del metodo

    /**
     * Crea una lista di records di attività utilizzati
     * Per ogni plurale, ci possono essere diversi 'singolari' richiamati dalle voci di BioGrails
     */
    protected creaListaSingolariId() {
    } // fine del metodo

    /**
     * Crea una lista di voci biografiche che utilizzano questa attività
     */
    private creaListaVociId() {
        ArrayList<Long> listaSingolariID
        ArrayList<Long> listaVociId = new ArrayList<Long>()

        // recupera la lista dei singolari
        listaSingolariID = this.getListaSingolariID()

        // controllo di congruità
        if (listaSingolariID) {
            listaSingolariID?.each {
                listaVociId += creaListaVociIdSingolare(it)
            }// fine di each

            //ordine numerico
            listaVociId.sort()

            this.setListaVociId(listaVociId)
        }// fine del blocco if
    } // fine del metodo

    /**
     * Crea una lista di voci biografiche per un singolo record di attività attività
     *
     * @param attivitaId (grails id)
     * @return listaVociId (grails id)
     */
    protected ArrayList<Long> creaListaVociIdSingolare(long attivitaId) {
        return null
    } // fine del metodo

    /**
     * Crea una lista di didascalie
     */
    protected creaListaDidascalie() {
        // variabili e costanti locali di lavoro
        boolean continua = false
        ArrayList<Long> listaVociId = this.getListaVociId()
        ArrayList listaDidascalie = new ArrayList()
        BioGrails bioGrails
        boolean perAdessoFalso = false

        // controllo di congruità
        if (listaVociId) {
            listaVociId?.each {
                bioGrails = BioGrails.findById(it)
                if (bioGrails) {
                    if (perAdessoFalso) {
                        if (bioGrails.didascaliaListe) {
                            listaDidascalie.add(bioGrails.didascaliaListe)
                        } else {
                            listaDidascalie.add(creaTestoDidascaliaAlVolo(bioGrails))
                        }// fine del blocco if-else
                    } else {
                        listaDidascalie.add(creaDidascaliaAlVolo(bioGrails))
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine di each

            this.setListaDidascalie(listaDidascalie)
        }// fine del blocco if
    } // fine del metodo

    // se manca la didascalia, la crea al volo
    public static String creaTestoDidascaliaAlVolo(BioGrails bio) {
        String didascaliaTxt = ''
        long grailsId
        DidascaliaBio didascaliaObj

        if (bio) {
            grailsId = bio.id
            didascaliaObj = new DidascaliaBio(grailsId)
            didascaliaObj.setInizializza()
            didascaliaTxt = didascaliaObj.getTestoEstesaSimboli()
        }// fine del blocco if

        return didascaliaTxt
    }// fine del metodo

    // se manca la didascalia, la crea al volo
    public static DidascaliaBio creaDidascaliaAlVolo(BioGrails bio) {
        DidascaliaBio didascalia = null
        long grailsId

        if (bio) {
            grailsId = bio.id
            didascalia = new DidascaliaBio(grailsId)
            didascalia.setInizializza()
        }// fine del blocco if

        return didascalia
    }// fine del metodo

    /**
     * Registra il paragrafo/pagina
     */
    public registraPagina() {
        if (getBioLista()) {
            getBioLista().registra()
        }// fine del blocco if
    }// fine della closure


    String getPlurale() {
        return plurale
    }

    void setPlurale(String plurale) {
        this.plurale = plurale
    }

    ArrayList<Long> getListaSingolariID() {
        return listaSingolariID
    }

    void setListaSingolariID(ArrayList<Long> listaSingolariID) {
        this.listaSingolariID = listaSingolariID
    }

    ArrayList<Long> getListaVociId() {
        return listaVociId
    }

    void setListaVociId(ArrayList<Long> listaVociId) {
        this.listaVociId = listaVociId
    }

    ArrayList getListaDidascalie() {
        return listaDidascalie
    }

    void setListaDidascalie(ArrayList listaDidascalie) {
        this.listaDidascalie = listaDidascalie
    }

    BioLista getBioLista() {
        return bioLista
    }

    void setBioLista(BioLista bioLista) {
        this.bioLista = bioLista
    }
} // fine della classe