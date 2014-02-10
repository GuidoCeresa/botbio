package it.algos.botbio

import org.apache.commons.logging.LogFactory

/**
 * Created by IntelliJ IDEA.
 * User: Gac
 * Date: 10/02/11
 * Time: 21.25
 */
class BioNazionalita {

    private static def log = LogFactory.getLog(this)

    private static String PATH = 'Progetto:Biografie/Nazionalità/'

    private String plurale
    private ArrayList listaSingolariID
    private ArrayList listaVociId
    private ArrayList listaDidascalie
    private String titoloPrincipale
    private BioLista bioLista


    public BioNazionalita(String plurale) {
        // rimanda al costruttore della superclasse
        super()

        // Metodo iniziale con il plurale dell'attività
        this.inizializza(plurale)
    }// fine del metodo costruttore completo

    /**
     * Metodo iniziale con il plurale della nazionalità
     *
     * @param plurale
     */
    private inizializza = {String plurale ->
        // regola le variabili di istanza coi parametri
        this.setPlurale(plurale)

        // Crea una lista di records di attività utilizzati
        this.creaListaSingolariId()

        // Crea una lista di voci biografiche che utilizzano questa nazionalità
        this.creaListaVociId()

        // Crea una lista di didascalie
        this.creaListaDidascalie()

        // Crea paragrafo/pagina con le didascalie
        this.bioLista = new BioListaNaz(this.plurale, this.listaDidascalie)
        def stop
    }// fine della closure


    /**
     * Crea una lista di records di nazionalità utilizzati
     */
    private creaListaSingolariId = {
        // variabili e costanti locali di lavoro
        boolean continua = false
        String nazionalitaPlurale
        ArrayList listaSingolariID = null
        String query
        def records

        // recupera il plurale
        nazionalitaPlurale = this.getPlurale()

        // controllo di congruità
        if (nazionalitaPlurale) {
            continua = true
        }// fine del blocco if

        if (continua) {
            query = "select id from Nazionalita where plurale="
            query += "'"
            query += nazionalitaPlurale
            query += "'"
        }// fine del blocco if

        if (continua) {
            try { // prova ad eseguire il codice
                listaSingolariID = (ArrayList) Nazionalita.executeQuery(query)
            } catch (Exception unErrore) { // intercetta l'errore
                records = Nazionalita.findAllByPlurale(nazionalitaPlurale)
                if (records) {
                    listaSingolariID = new ArrayList()
                    records?.each {
                        listaSingolariID.add(it.id)
                    }// fine di each
                    log.error "Query alternativa (creaListaSingolariId): ${nazionalitaPlurale}"
                } else {
                    log.error "Query fallita (creaListaSingolariId): ${nazionalitaPlurale}"
                }// fine del blocco if-else
            }// fine del blocco try-catch
        }// fine del blocco if

        if (continua) {
            this.setListaSingolariID(listaSingolariID)
        }// fine del blocco if

    } // fine della closure

    /**
     * Crea una lista di voci biografiche che utilizzano questa nazionalità
     */
    private creaListaVociId = {
        // variabili e costanti locali di lavoro
        boolean continua = false
        ArrayList listaSingolariID
        ArrayList listaVociId = new ArrayList()

        // recupera la lista dei singolari
        listaSingolariID = this.getListaSingolariID()

        // controllo di congruità
        if (listaSingolariID) {
            continua = true
        }// fine del blocco if

        if (continua) {
            listaSingolariID?.each {
                listaVociId += this.creaListaVociIdSingolare(it)
            }// fine di each
        }// fine del blocco if

        //ordine numerico
        if (continua) {
            listaVociId.sort()
        }// fine del blocco if

        if (continua) {
            this.setListaVociId(listaVociId)
        }// fine del blocco if
    } // fine della closure

    /**
     * Crea una lista di voci biografiche per un singolo record di nazionalità
     *
     * @param attivitaId (grails id)
     * @return listaVociId (grails id)
     */
    private creaListaVociIdSingolare = {long nazionalitaId ->
        // variabili e costanti locali di lavoro
        ArrayList listaVociId
        String query  ='select id from Biografia where nazionalita_link_id='

        // controllo di congruità
        if (nazionalitaId && nazionalitaId > 0) {
            try { // prova ad eseguire il codice
                query += "${nazionalitaId}"
                listaVociId = (ArrayList) Biografia.executeQuery(query)

            } catch (Exception unErrore) { // intercetta l'errore
                log.error "Query fallita (creaListaVociIdSingolare): ${nazionalitaId}"
            }// fine del blocco try-catch
        }// fine del blocco if

        // valore di ritorno
        return listaVociId
    } // fine della closure


    /**
     * Crea una lista di didascalie
     */
    private creaListaDidascalie = {
        // variabili e costanti locali di lavoro
        boolean continua = false
        ArrayList listaVociId
        ArrayList listaDidascalie = new ArrayList()
        DidascaliaBio didascalia

        // recupera la lista delle voci biografiche
        listaVociId = this.getListaVociId()

        // controllo di congruità
        if (listaVociId) {
            continua = true
        }// fine del blocco if

        if (continua) {
            listaVociId?.each {
                didascalia = new DidascaliaBio(it)
                listaDidascalie.add(didascalia)
            }// fine di each
            continua = (listaDidascalie && listaDidascalie.size() > 0)
        }// fine del blocco if

        if (continua) {
            this.setListaDidascalie(listaDidascalie)
        }// fine del blocco if
    } // fine della closure

    /**
     * Registra il paragrafo/pagina
     */
    public registraPagina = {
        if (this.bioLista) {
            this.bioLista.registra()
        }// fine del blocco if
    }// fine della closure


    private void setPlurale(String plurale) {
        this.plurale = plurale
    }


    private String getPlurale() {
        return plurale
    }


    private void setListaSingolariID(ArrayList listaSingolariID) {
        this.listaSingolariID = listaSingolariID
    }


    private ArrayList getListaSingolariID() {
        return listaSingolariID
    }


    private void setListaVociId(ArrayList listaVociId) {
        this.listaVociId = listaVociId
    }


    private ArrayList getListaVociId() {
        return listaVociId
    }


    private void setListaDidascalie(ArrayList listaDidascalie) {
        this.listaDidascalie = listaDidascalie
    }


    private ArrayList getListaDidascalie() {
        return listaDidascalie
    }

}// fine della classe
