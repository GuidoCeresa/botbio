package it.algos.botbio

import org.apache.commons.logging.LogFactory

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 9-2-14
 * Time: 08:48
 */
class BioAttivita {

    private static def log = LogFactory.getLog(this)

    private static boolean USA_ANCHE_ATTIVITA_DUE = true
    private static boolean USA_ANCHE_ATTIVITA_TRE = true
    private String plurale
    private ArrayList<Long> listaSingolariID
    private ArrayList<Long> listaVociId
    private ArrayList<String> listaDidascalie
    private BioLista bioLista

    public BioAttivita(String plurale) {
        super()
        this.inizializza(plurale)
    }// fine del metodo costruttore completo

    /**
     * Metodo iniziale con il plurale dell'attività
     *
     * @param plurale
     */
    private inizializza(String plurale) {
        // regola le variabili di istanza coi parametri
        this.setPlurale(plurale)

        // Crea una lista di records di attività utilizzati da BioGrails
        this.creaListaSingolariId()

        // Crea una lista di voci biografiche che utilizzano questa attività
        this.creaListaVociId()

        // Crea una lista di didascalie
        this.creaListaDidascalie()

        // Crea paragrafo/pagina con le didascalie
//        this.bioLista = new BioListaAtt(this.plurale, this.listaDidascalie)
    } // fine del metodo

    /**
     * Crea una lista di records di attività utilizzati
     * Per ogni plurale, ci possono essere diversi 'singolari' richiamati dalle voci di BioGrails
     */
    private creaListaSingolariId() {
        String attivitaPlurale = this.getPlurale()
        ArrayList<Long> listaSingolariID
        String query

        if (attivitaPlurale) {
            query = "select id from Attivita where plurale='$attivitaPlurale'"
            listaSingolariID = (ArrayList<Long>) Attivita.executeQuery(query)
            this.setListaSingolariID(listaSingolariID)
        }// fine del blocco if
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
    private static ArrayList<Long> creaListaVociIdSingolare(long attivitaId) {
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
    private static String getQueryParziale(int num){
        // variabili e costanti locali di lavoro
        String query     =''
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

    /**
     * Crea una lista di didascalie
     */
    private creaListaDidascalie() {
        // variabili e costanti locali di lavoro
        boolean continua = false
        ArrayList<Long> listaVociId  = this.getListaVociId()
        ArrayList<String> listaDidascalie = new ArrayList<String>()
        BioGrails bioGrails

        // controllo di congruità
        if (listaVociId) {
            listaVociId?.each {
                bioGrails = BioGrails.findById(it)
                if (bioGrails) {
                    if (bioGrails.didascaliaListe) {
                        listaDidascalie.add(bioGrails.didascaliaListe)
                    } else {
                        listaDidascalie.add(creaDidascaliaAlVolo(bioGrails))
                    }// fine del blocco if-else
                    listaDidascalie.add(bioGrails.didascaliaListe)
                }// fine del blocco if
            }// fine di each

            this.setListaDidascalie(listaDidascalie)
        }// fine del blocco if
    } // fine del metodo

    // se manca la didascalia, la crea al volo
    public static String creaDidascaliaAlVolo(BioGrails bio) {
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

    /**
     * Registra il paragrafo/pagina
     */
    public registraPagina = {
        if (this.bioLista) {
            this.bioLista.registra()
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

    ArrayList<String> getListaDidascalie() {
        return listaDidascalie
    }

    void setListaDidascalie(ArrayList<String> listaDidascalie) {
        this.listaDidascalie = listaDidascalie
    }
} // fine della classe
