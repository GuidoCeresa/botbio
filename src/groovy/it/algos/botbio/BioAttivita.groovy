package it.algos.botbio

import it.algos.algoslib.LibTesto
import it.algos.algoslib.LibWiki
import org.apache.commons.logging.LogFactory

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 9-2-14
 * Time: 08:48
 */
class BioAttivita extends BioAttNaz {

    private static def log = LogFactory.getLog(this)

    private static boolean USA_ANCHE_ATTIVITA_DUE = true
    private static boolean USA_ANCHE_ATTIVITA_TRE = true

    public BioAttivita(String plurale) {
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
        this.bioLista = new BioListaAtt(getPlurale(), getNumPersoneUnivoche(),getListaMappaGrails(), Ordinamento.prestabilitoInMappa)
    } // fine del metodo

    /**
     * Crea una lista di id di attività utilizzate
     * Per ogni plurale, ci possono essere diversi 'singolari' richiamati dalle voci di BioGrails
     */
    protected creaListaId() {
        String attivitaPlurale = this.getPlurale()
        ArrayList<Long> listaSingolariID
        String query
        String tag = "'"

        if (attivitaPlurale) {
            if (!attivitaPlurale.contains(tag)) {
                query = "select id from Attivita where plurale='$attivitaPlurale'"
                listaSingolariID = (ArrayList<Long>) Attivita.executeQuery(query)
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
    protected ArrayList<Long> creaListaVociIdSingolare(long attivitaId) {
        ArrayList<Long> listaVociId = null
        String query

        // controllo di congruità
        if (attivitaId && attivitaId > 0) {
            try { // prova ad eseguire il codice
                query = getQueryParzialeId(1) + "${attivitaId}"
                listaVociId = (ArrayList<Long>) BioGrails.executeQuery(query)

                if (USA_ANCHE_ATTIVITA_DUE) {
                    query = getQueryParzialeId(2) + "${attivitaId}"
                    listaVociId += (ArrayList<Long>) BioGrails.executeQuery(query)
                }// fine del blocco if

                if (USA_ANCHE_ATTIVITA_TRE) {
                    query = getQueryParzialeId(3) + "${attivitaId}"
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
     * Crea una lista di mappe che utilizzano questa attività
     * Ricerca tutte le nazionalita
     * Per ognuna esegue una query
     */
    protected creaListaMappe() {
        ArrayList<Map> listaMappaGrails = new ArrayList()
        Map mappa
        String attivita = this.getPlurale()
        String nazionalita
        String titoloParagrafo
        String sottoTitolo
        ArrayList<Long> listaSingolariID
        ArrayList<String> listaNazionalitaSingolari
        ArrayList<String> listaNazionalitaPlurali
        String pathTitolo = BioListaAttNaz.PATH + 'Nazionalità/'
        String pathSottoTitolo = BioListaAttNaz.PATH + 'Attività/'
        ArrayList<Map> listaDidascalie

        // recupera la lista delle attivita singolari
        listaSingolariID = this.getListaID()

        // recupera la lista delle nazionalita singolari
        listaNazionalitaSingolari = creaNazionalitaSingolari(listaSingolariID)

        // recupera la lista delle nazionalita plurali
        listaNazionalitaPlurali = creaNazionalitaPlurali(listaNazionalitaSingolari)

        // ciclo
        if (listaSingolariID) {
            listaNazionalitaPlurali?.each {
                mappa = new HashMap()
                nazionalita = it
                if (nazionalita) {
                    nazionalita = LibTesto.primaMaiuscola(it)
                    titoloParagrafo = pathTitolo + nazionalita + '|' + nazionalita
                    titoloParagrafo = LibWiki.setQuadre(titoloParagrafo)
                    attivita = LibTesto.primaMaiuscola(attivita)
                    sottoTitolo = pathSottoTitolo + attivita + '/' + nazionalita
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

            this.setListaMappaGrails(listaMappaGrails)
        }// fine del blocco if
    } // fine del metodo

    private ArrayList<String> creaNazionalitaSingolari(ArrayList<Long> listaSingolariID) {
        ArrayList<String> listaNazionalitaSingolari
        String query

        query = "select distinct nazionalita from BioGrails where "
        query += queryWhereUno(listaSingolariID)

        listaNazionalitaSingolari = (ArrayList<String>) BioGrails.executeQuery(query)

        return listaNazionalitaSingolari
    } // fine del metodo

    private static ArrayList<String> creaNazionalitaPlurali(ArrayList<String> listaNazionalitaSingolari) {
        ArrayList<String> listaNazionalitaPlurali = new ArrayList()
        Nazionalita nazionalita
        String nazionalitaPlurale

        listaNazionalitaSingolari?.each {
            if (it) {
                nazionalita = Nazionalita.findBySingolare((String) it)
                if (nazionalita) {
                    nazionalitaPlurale = nazionalita.plurale
                }// fine del blocco if
            } else {
                nazionalitaPlurale = ''
            }// fine del blocco if-else
            if (!listaNazionalitaPlurali.contains(nazionalitaPlurale)) {
                listaNazionalitaPlurali.add(nazionalitaPlurale)
            }// fine del blocco if
        } // fine del ciclo each
        listaNazionalitaPlurali?.sort()

        return listaNazionalitaPlurali
    } // fine del metodo


    protected ArrayList<Long> attNazId(String nazionalitaPlurale) {
        ArrayList<Long> listaNazId = null
        def risultato
        String query = "select id from Nazionalita where plurale='${nazionalitaPlurale}'"
        risultato = Nazionalita.executeQuery(query)

        if (risultato instanceof ArrayList<Long>) {
            if (risultato.size() > 0) {
                listaNazId = risultato
            } else {
                listaNazId = new ArrayList<Long>()
                listaNazId.add(0)
            }// fine del blocco if-else
        } else {
            if (risultato instanceof Long) {
                listaNazId = new ArrayList<Long>()
                listaNazId.add(risultato)
            }// fine del blocco if
        }// fine del blocco if-else

        return listaNazId
    } // fine del metodo


    protected String queryWhereUno(ArrayList<Long> idAttivita) {
        String query = "("
        String longId

        idAttivita?.each {
            longId = it
            query += "attivita_link_id=${longId} or attivita2link_id=${longId} or attivita3link_id=${longId} or "
        } // fine del ciclo each

        return LibTesto.levaCoda(query.trim(), 'or').trim() + ')'
    } // fine del metodo

    protected String queryWhereDue(ArrayList<Long> listaNazId) {
        String query = "("
        String naz

        if (listaNazId && listaNazId.size() == 1 && listaNazId[0] == 0) {
            query += "nazionalita_link_id is null"
        } else {
            listaNazId?.each {
                naz = it
                query += "nazionalita_link_id=${naz} or "
            } // fine del ciclo each
        }// fine del blocco if-else

        return LibTesto.levaCoda(query.trim(), 'or').trim() + ')'
    } // fine del metodo

    /**
     * Crea una query (parziale) col nome del campo
     *
     * @param num di attività (principale, secondaria o terziaria)
     * @return query
     */
    private static String getQueryParzialeId(int num) {
        // variabili e costanti locali di lavoro
        String query = ''
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

} // fine della classe
