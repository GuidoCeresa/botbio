package it.algos.botbio

import it.algos.algoslib.LibTesto
import it.algos.algoslib.LibWiki
import org.apache.commons.logging.LogFactory

/**
 * Created by IntelliJ IDEA.
 * User: Gac
 * Date: 10/02/11
 * Time: 21.25
 */
class BioNazionalita extends BioAttNaz {

    private static def log = LogFactory.getLog(this)

    public BioNazionalita(String plurale) {
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
        this.bioLista = new BioListaNaz(getPlurale(), getNumPersoneUnivoche(), getListaMappaGrails(), Ordinamento.prestabilitoInMappa)
    } // fine del metodo

    /**
     * Crea una lista di id di attività utilizzate
     * Per ogni plurale, ci possono essere diversi 'singolari' richiamati dalle voci di BioGrails
     */
    protected creaListaId() {
        String nazionalitaPlurale = this.getPlurale()
        ArrayList<Long> listaSingolariID
        String query
        String tag = "'"

        if (nazionalitaPlurale) {
            if (!nazionalitaPlurale.contains(tag)) {
                query = "select id from Nazionalita where plurale='$nazionalitaPlurale'"
                listaSingolariID = (ArrayList<Long>) Nazionalita.executeQuery(query)
                this.setListaID(listaSingolariID)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    /**
     * Crea una lista di id (del DB Grails) per un singolo record di attività attività
     *
     * @param attivitaId (grails id)
     * @return listaVociId (grails id)
     */
    protected ArrayList<Long> creaListaVociIdSingolare(long nazionalitaId) {
        // variabili e costanti locali di lavoro
        ArrayList<Long> listaVociId = null
        String query = 'select id from BioGrails where nazionalita_link_id='

        // controllo di congruità
        if (nazionalitaId && nazionalitaId > 0) {
            try { // prova ad eseguire il codice
                query += "${nazionalitaId}"
                listaVociId = (ArrayList<Long>) BioGrails.executeQuery(query)

            } catch (Exception unErrore) { // intercetta l'errore
                log.error "Query fallita (creaListaVociIdSingolare): ${nazionalitaId}"
            }// fine del blocco try-catch
        }// fine del blocco if

        // valore di ritorno
        return listaVociId
    } // fine della closure

    /**
     * Crea una lista di mappe che utilizzano questa nazionalita
     * Ricerca tutte le attività
     * Per ognuna esegue una query
     */
    protected creaListaMappe() {
        ArrayList<Map> listaMappaGrails = new ArrayList()
        Map mappa
        String nazionalita = this.getPlurale()
        String attivita
        String titoloParagrafo
        String sottoTitolo
        ArrayList<Long> listaSingolariID
        ArrayList<String> listaAttivitaSingolari
        ArrayList<String> listaAttivitaPlurali
        String pathTitolo = BioListaAttNaz.PATH + 'Attività/'
        String pathSottoTitolo = BioListaAttNaz.PATH + 'Nazionalità/'
        ArrayList<Map> listaDidascalie
        int personeUnivoche

        // recupera la lista delle nazionalità singolari
        listaSingolariID = this.getListaID()

        // recupera la lista delle attività singolari
        listaAttivitaSingolari = creaAttivitaSingolari(listaSingolariID)

        // recupera la lista delle attività plurali
        listaAttivitaPlurali = creaAttivitaPlurali(listaAttivitaSingolari)

        // ciclo
        if (listaSingolariID) {
            listaAttivitaPlurali?.each {
                mappa = new HashMap()
                attivita = it
                if (attivita) {
                    attivita = LibTesto.primaMaiuscola(it)
                    titoloParagrafo = pathTitolo + attivita + '|' + attivita
                    titoloParagrafo = LibWiki.setQuadre(titoloParagrafo)
                    nazionalita = LibTesto.primaMaiuscola(nazionalita)
                    sottoTitolo = pathSottoTitolo + nazionalita + '/' + attivita
                } else {
                    titoloParagrafo = '...'
                }// fine del blocco if-else
                listaDidascalie = creaListaDidascalie(listaSingolariID, it)
                mappa.put(LibBio.MAPPA_TITOLO_PARAGRAFO, titoloParagrafo)
                mappa.put(LibBio.MAPPA_SOTTO_TITOLO, sottoTitolo)
                mappa.put(LibBio.MAPPA_LISTA, listaDidascalie)
                mappa.put(LibBio.MAPPA_NUMERO, listaDidascalie.size())
                mappa.put(LibBio.MAPPA_ORDINE, Ordinamento.prestabilitoInMappa.toString())
                mappa.put(LibBio.MAPPA_SOTTOPAGINA, listaDidascalie.size() > NUM_RIGHE_PER_SOTTOPAGINA)
                mappa.put(LibBio.MAPPA_ATTIVITA, attivita)
                mappa.put(LibBio.MAPPA_NAZIONALITA, nazionalita)
                mappa.put(LibBio.MAPPA_LIVELLO, 1)
                listaMappaGrails.add(mappa)
            }// fine di each

            personeUnivoche = calcolaNumeroPersoneUnivoche(listaSingolariID, listaAttivitaPlurali)
            this.setNumPersoneUnivoche(personeUnivoche)

            this.setListaMappaGrails(listaMappaGrails)
        }// fine del blocco if
    } // fine del metodo

    private ArrayList<String> creaAttivitaSingolari(ArrayList<Long> listaSingolariID) {
        ArrayList<String> listaAttivitaSingolariAll = new ArrayList<String>()
        ArrayList<String> listaAttivitaSingolari
        ArrayList<String> listaAttivitaSingolari2
        ArrayList<String> listaAttivitaSingolari3
        String query
        String queryWhere

        queryWhere = queryWhereUno(listaSingolariID)

        query = "select distinct attivita from BioGrails where "
        listaAttivitaSingolari = (ArrayList<String>) BioGrails.executeQuery(query + queryWhere)
        if (listaAttivitaSingolari.size() == 1 && listaAttivitaSingolari[0].equals('')) {
            listaAttivitaSingolari = null
        }// fine del blocco if

        query = "select distinct attivita2 from BioGrails where "
        listaAttivitaSingolari2 = (ArrayList<String>) BioGrails.executeQuery(query + queryWhere)
        if (listaAttivitaSingolari2.size() == 1 && listaAttivitaSingolari2[0].equals('')) {
            listaAttivitaSingolari2 = null
        }// fine del blocco if

        query = "select distinct attivita3 from BioGrails where "
        listaAttivitaSingolari3 = (ArrayList<String>) BioGrails.executeQuery(query + queryWhere)
        if (listaAttivitaSingolari3.size() == 1 && listaAttivitaSingolari3[0].equals('')) {
            listaAttivitaSingolari3 = null
        }// fine del blocco if

        if (listaAttivitaSingolari) {
            listaAttivitaSingolariAll += listaAttivitaSingolari
        }// fine del blocco if
        if (listaAttivitaSingolari2) {
            listaAttivitaSingolariAll += listaAttivitaSingolari2
        }// fine del blocco if
        if (listaAttivitaSingolari3) {
            listaAttivitaSingolariAll += listaAttivitaSingolari3
        }// fine del blocco if

        return listaAttivitaSingolariAll
    } // fine del metodo

    private static ArrayList<String> creaAttivitaPlurali(ArrayList<String> listaAttivitaSingolari) {
        ArrayList<String> listaAttivitaPlurali = new ArrayList()
        Attivita attivita
        String attivitaPlurale

        listaAttivitaSingolari?.each {
            if (it) {
                attivita = Attivita.findBySingolare((String) it)
                if (attivita) {
                    attivitaPlurale = attivita.plurale
                }// fine del blocco if
            } else {
                attivitaPlurale = ''
            }// fine del blocco if-else
            if (!listaAttivitaPlurali.contains(attivitaPlurale)) {
                listaAttivitaPlurali.add(attivitaPlurale)
            }// fine del blocco if
        } // fine del ciclo each
        listaAttivitaPlurali?.sort()

        return listaAttivitaPlurali
    } // fine del metodo

    protected ArrayList<Long> attNazId(String attivitaPlurale) {
        ArrayList<Long> listaId = null
        def risultato
        attivitaPlurale = attivitaPlurale.replaceAll("'", "''")
        String query = "select id from Attivita where plurale='${attivitaPlurale}'"
        risultato = Attivita.executeQuery(query)

        if (risultato instanceof ArrayList<Long>) {
            if (risultato.size() > 0) {
                listaId = risultato
            } else {
                listaId = new ArrayList<Long>()
                listaId.add(0)
            }// fine del blocco if-else
        } else {
            if (risultato instanceof Long) {
                listaId = new ArrayList<Long>()
                listaId.add(risultato)
            }// fine del blocco if
        }// fine del blocco if-else

        return listaId
    } // fine del metodo

    protected String queryWhereUno(ArrayList<Long> idNazionalita) {
        String query = "("
        String longId

        idNazionalita?.each {
            longId = it
            query += "nazionalita_link_id=${longId} or "
        } // fine del ciclo each

        return LibTesto.levaCoda(query.trim(), 'or').trim() + ')'
    } // fine del metodo

    protected String queryWhereDue(ArrayList<Long> listaAttId) {
        String query = "("
        String naz

        if (listaAttId && listaAttId.size() == 1 && listaAttId[0] == 0) {
            query += "attivita_link_id is null"
        } else {
            listaAttId?.each {
                naz = it
                query += "attivita_link_id=${naz} or attivita2link_id=${naz} or attivita3link_id=${naz} or "
            } // fine del ciclo each
        }// fine del blocco if-else

        return LibTesto.levaCoda(query.trim(), 'or').trim() + ')'
    } // fine del metodo

}// fine della classe
