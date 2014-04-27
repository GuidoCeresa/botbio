package it.algos.botbio

import it.algos.algoslib.LibTesto

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 12-2-14
 * Time: 07:50
 */
abstract class BioAttNaz {

    protected static int NUM_RIGHE_PER_SOTTOPAGINA = 50
    protected static int NUM_RIGHE_PER_CARATTERE_SOTTOPAGINA = 50

    private String plurale
    private ArrayList<Long> listaID
    private ArrayList<Long> listaVociId
    private ArrayList listaDidascalie
    private BioLista bioLista
    private ArrayList listaMappaGrails
    private int numPersoneUnivoche

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

        // Crea una lista di id di attività o nazionalità utilizzate
        this.creaListaId()

        // Crea una lista di id delle voci biografiche che utilizzano questa attività
        this.creaListaVociId()

        // Crea una lista di mappe che utilizzano questa attività
        this.creaListaMappe()

        // Crea una lista di didascalie
        this.creaListaDidascalie()
    } // fine del metodo

    /**
     * Crea una lista di id di attività o nazionalità utilizzate
     * Per ogni plurale, ci possono essere diversi 'singolari' richiamati dalle voci di BioGrails
     */
    protected creaListaId() {
    } // fine del metodo

    /**
     * Crea una lista di id di voci biografiche che utilizzano questa attività
     */
    private creaListaVociId() {
        ArrayList<Long> listaVociId = new ArrayList<Long>()
        ArrayList<Long> listaSingolariID

        // recupera la lista dei singolari
        listaSingolariID = this.getListaID()

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
     * Crea una lista di mappe che utilizzano questa attività
     */
    protected creaListaMappe() {
    } // fine del metodo

    /**
     * Crea una lista di id (del DB Grails) per un singolo record di attività
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
        boolean perAdessoFalso = true //cambiato in true il 8-3-14

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

    // controlla che non ci siano didascalie mancanti
    // nel caso le crea al volo
    protected ArrayList<Map> creaListaDidascalie(ArrayList<Long> listaSingolariID, String nazionalitaPlurale) {
        ArrayList<Map> listaMappe = new ArrayList<Map>()
        Map mappa
        ArrayList lista
        ArrayList<Long> listaNazId = attNazId(nazionalitaPlurale)
        String query
        long idGrails
        BioGrails bio = null
        String didascalia
        String cognome
        String nome
        String primaLettera

        query = "select id,didascaliaListe,cognome,nome from BioGrails where "
        query += queryWhereUno(listaSingolariID)
        query += ' and '
        query += queryWhereDue(listaNazId)
        query += ' order by cognome,title'

        lista = (ArrayList<String>) BioGrails.executeQuery(query)

        lista?.each {
            if (it[1]) {
                didascalia = (String) it[1]
                if (it[2]) {
                    cognome = (String) it[2]
                    primaLettera = cognome.substring(0, 1)
                } else {
                    if (it[3]) {
                        nome = (String) it[3]
                        primaLettera = nome.substring(0, 1)
                    } else {
                        primaLettera = '.'
                    }// fine del blocco if-else
                }// fine del blocco if-else
            } else {
                idGrails = (Long) it[0]
                bio = BioGrails.findById(idGrails)
                cognome = bio.cognome
                nome = bio.nome
                if (cognome) {
                    primaLettera = cognome.substring(0, 1)
                } else {
                    if (nome) {
                        primaLettera = nome.substring(0, 1)
                    } else {
                        primaLettera = '.'
                    }// fine del blocco if-else
                }// fine del blocco if-else
                didascalia = creaTestoDidascaliaAlVolo(bio)
            }// fine del blocco if-else
            mappa = new HashMap()
            primaLettera = primaLettera.toUpperCase()
            mappa.put(LibBio.MAPPA_PRIMA_LETTERA, primaLettera)
            mappa.put(LibBio.MAPPA_DIDASCALIA, didascalia)
            listaMappe.add(mappa)
        } // fine del ciclo each

        return listaMappe
    } // fine del metodo

    protected int calcolaNumeroPersoneUnivoche(ArrayList<Long> listaSingolariID, ArrayList<String> nazionalitaPlurale) {
        int personeUnivoche = 0
        ArrayList<Long> listaNazId = new ArrayList<Long>()
        String query
        def risultato

        nazionalitaPlurale?.each {
            listaNazId += attNazId(it)
        } // fine del ciclo each

        query = "select distinct id from BioGrails where "
        query += queryWhereUno(listaSingolariID)
        query += ' and '
        query += queryWhereDue(listaNazId)

        risultato = BioGrails.executeQuery(query)
        if (risultato) {
            personeUnivoche = risultato.size()
        }// fine del blocco if

        return personeUnivoche
    } // fine del metodo

    protected ArrayList<Long> attNazId(String nazionalitaPlurale) {
        return null
    } // fine del metodo

    protected String queryWhereUno(ArrayList<Long> id) {
        return null
    } // fine del metodo

    protected String queryWhereDue(ArrayList<Long> listaId) {
        return null
    } // fine del metodo

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

    ArrayList<Long> getListaID() {
        return listaID
    }

    void setListaID(ArrayList<Long> listaID) {
        this.listaID = listaID
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

    ArrayList getListaMappaGrails() {
        return listaMappaGrails
    }

    void setListaMappaGrails(ArrayList listaMappaGrails) {
        this.listaMappaGrails = listaMappaGrails
    }

    int getNumPersoneUnivoche() {
        return numPersoneUnivoche
    }

    void setNumPersoneUnivoche(int numPersoneUnivoche) {
        this.numPersoneUnivoche = numPersoneUnivoche
    }
} // fine della classe
