/* Created by Algos s.r.l. */
/* Date: mag 2013 */
/* Il plugin Algos ha creato o sovrascritto il templates che ha creato questo file. */
/* L'header del templates serve per controllare le successive release */
/* (tramite il flag di controllo aggiunto) */
/* Tipicamente VERRA sovrascritto (il template, non il file) ad ogni nuova release */
/* del plugin per rimanere aggiornato. */
/* Se vuoi che le prossime release del plugin NON sovrascrivano il template che */
/* genera questo file, perdendo tutte le modifiche precedentemente effettuate, */
/* regola a false il flag di controllo flagOverwrite© del template stesso. */
/* (non quello del singolo file) */
/* flagOverwrite = true */

package it.algos.botbio

import groovy.util.logging.Log4j
import it.algos.algoslib.Lib
import it.algos.algoslib.LibArray
import it.algos.algoslib.LibTesto
import it.algos.algospref.LibPref
import it.algos.algospref.Preferenze
import it.algos.algoswiki.Login
import it.algos.algoswiki.QueryCatPageid
import it.algos.algoswiki.QueryTimestamp
import it.algos.algoswiki.WrapTime

import java.sql.Timestamp

@Log4j
class BioWikiService {

    public static int idDebug = 734256  //todo ADG
    public static final String TITOLO_CATEGORIA = 'BioBot'
//    public static final String TITOLO_CATEGORIA = 'Pittori britannici' //@todo ASSOLUTAMENTE provvisorio
//    public static final String TITOLO_CATEGORIA = 'Nati nel 1544' //@todo ASSOLUTAMENTE provvisorio

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def grailsApplication

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def logWikiService
//    BioService bioService = new BioService()

    private static long inizio = System.currentTimeMillis()
    private static String tagAvviso = WrapBio.tagAvviso

    long ultimaRegistrazione = 0

    /**
     * Importa il database biografia
     *
     * Forza la cancellazione di tutti i records
     * Rimanda al metodo Aggiunge
     */
    public importaWiki() {
        // cancella tutti i records
        if (!Preferenze.getBool(LibBio.DEBUG)) {
            log.info 'Inizio cancellazione totale del database'
            BioWiki.executeUpdate('delete BioWiki')
            log.info 'Termine cancellazione del database'
        }// fine del blocco if

        // Aggiunge nuovi records al database biografia
        this.aggiungeWiki()
    } // fine del metodo

    /**
     * Aggiunge i nuovi records al database biografia
     *
     * Recupera la lista completa delle voci esistenti dalla categoria Biografie
     * Recupera la lista dei records esistenti nel database
     * Crea la lista delle voci nuove che non hanno ancora records (da creare)
     * Abbatte la lista se c'è un limite di download
     * Aggiunge i nuovi records:
     *      modificando o meno la voce sul server wiki a seconda del flag modificaPagine
     * Cancella i records eccedenti
     *
     * @return mappa: con:
     *              listaNuoviRecordsCreati: lista completa delle voci esistenti effettivamente create (pageid)
     *              listaRecords cancellati: voci cancellate (pageid)
     */
    public HashMap aggiungeWiki() {
        // variabili e costanti locali di lavoro
        HashMap mappa = new HashMap()
        ArrayList<Integer> listaNuoviRecordsCreati
        int vociCreate
        int maxDownload
        ArrayList<Integer> listaVociServerWiki = null
        boolean continua = true
        ArrayList<Integer> listaRecordsDatabase = null
        ArrayList<Integer> listaRecordsMancanti = null
        ArrayList<Integer> listaNuoviRecordsDaCreare = null
        ArrayList<Integer> listaRecordsForseDaCancellare = null
        ArrayList<Integer> listaRecordsDaCancellare = null
        boolean debug = Preferenze.getBool((String) grailsApplication.config.debug)
        def num

        // messaggio di log
        inizio = System.currentTimeMillis()
        log.info 'Metodo di aggiunta nuovi records (anche cancellazioni)'

        //--Recupera dal server la lista completa delle voci esistenti dalla categoria BioBot
        if (continua) {
            listaVociServerWiki = this.getListaVociServerWiki()
            continua = (listaVociServerWiki && listaVociServerWiki.size() > 0)
            tempo()
        }// fine del blocco if

        if (continua) {
            log.info 'Nella categoria ci sono ' + LibTesto.formatNum(listaVociServerWiki.size()) + ' pagine'
        }// fine del blocco if

        //--Recupera la lista dei records esistenti nel database
        if (continua) {
            listaRecordsDatabase = creaListaRecordsDatabase()
            tempo()
        }// fine del blocco if

        //--Recupera la lista delle voci nuove che non hanno ancora records (da creare)
        if (continua) {
            listaRecordsMancanti = deltaListe(listaVociServerWiki, listaRecordsDatabase)
            tempo()
        }// fine del blocco if-else

        //--Limitazione del numero di voci da considerare
        if (continua) {
            if (LibPref.getBool('usaLimiteDownload')) {
                maxDownload = LibPref.getInt('maxDownload')
                listaNuoviRecordsDaCreare = LibArray.estraArray(listaRecordsMancanti, maxDownload)
            } else {
                listaNuoviRecordsDaCreare = listaRecordsMancanti
            }// fine del blocco if-else
            if (listaNuoviRecordsDaCreare) {
                vociCreate = listaNuoviRecordsDaCreare.size()
            }// fine del blocco if
        }// fine del blocco if-else

        //--Crea i nuovi records per il database biografia
        if (continua) {
            listaNuoviRecordsCreati = this.creaNuoviRecords(listaNuoviRecordsDaCreare)
            if (!listaNuoviRecordsCreati) {
                listaNuoviRecordsCreati = listaNuoviRecordsDaCreare
            }// fine del blocco if
            mappa.put(LibBio.AGGIUNTI, listaNuoviRecordsCreati)
            tempo()
        }// fine del blocco if-else

        //--Recupera la lista dei records che non hanno più una voce sul server wiki (forse da cancellare)
        if (continua) {
            listaRecordsForseDaCancellare = deltaListe(listaRecordsDatabase, listaVociServerWiki)
            tempo()
        }// fine del blocco if-else

        //--Controlla che i records effettivamente non esistano (la lista della categoria potrebbe essere sbagliata)
        if (continua) {
            if (debug) {
                listaRecordsDaCancellare = listaRecordsForseDaCancellare
            } else {
                listaRecordsDaCancellare = controlloRecordsDaEliminare(listaRecordsForseDaCancellare)
            }// fine del blocco if-else
            tempo()
        }// fine del blocco if-else

        //--Cancella i records che non hanno più una voce sul server wiki (sicuramente da cancellare)
        if (continua) {
            this.cancellazioneEffettivaRecords(listaRecordsDaCancellare)
            mappa.put(LibBio.CANCELLATI, listaRecordsDaCancellare)
            tempo()
        }// fine del blocco if-else

        if (debug) {
            log.info 'Fine del metodo di aggiunta nuovi records ed eventuali cancellazioni di records esistenti'
        } else {
//            if (listaNuoviRecordsDaCreare) {
//                num = listaNuoviRecordsDaCreare.size()
//                num = Lib.Text.formatNum(num)
//            }// fine del blocco if

//            logWikiService.info "Sono state aggiunte ${num} nuove voci dopo l'ultimo check"
            log.info 'Fine del metodo di aggiunta nuovi records ed eventuali cancellazioni di records esistenti'
        }// fine del blocco if-else

        // valore di ritorno
        return mappa
    } // fine del metodo

    /**
     * Aggiorna il database biografia
     *
     * listaRecordsDatabase:
     *      Recupera la lista dei records esistenti nel database
     *      Ordinata secondo il timestamp più vecchio
     *
     * listaRecordsDaControllare:
     *      Limita eventualmente la lista secondo i parametri usaLimiteDownload e maxDownload
     *
     * listaRecordsModificatiWiki:
     *      Spazzola la lista a blocchi stabiliti
     *      Crea la lista delle voci effettivamente modificate sul server wikipedia dall'ultimo controllo
     *
     * listaRecordsModificatiBio:
     *      Crea la lista delle voci che hanno modificato specificatamente il template bio (e non il resto della voce)
     *
     * Crea la lista delle voci mantenute, potenzialmente uguali o modificate
     * Legge il parametro revid, lo confronta con il revid del database
     * Crea la lista delle voci modificate, che potenzialmente hanno modificato il template:Bio (da controllare)
     * A pacchetti finiti della lista, legge le pagine dal server wiki
     * Per ogni pagina controlla se è stato modificato il template:Bio (da modificare)
     * Legge prima i parametri significativi del record e li confronta coi nuovi parametri
     * Sporca le tavole Giorno e Anno
     * Registra il records modificato, col nuovo revid
     */
    public ArrayList<Integer> aggiornaWiki() {
        // variabili e costanti locali di lavoro
        ArrayList<Integer> listaRecordsModificatiBio
        ArrayList<Integer> listaRecordsModificatiWiki
        int maxDownload
        boolean debug = Preferenze.getBool((String) grailsApplication.config.debug)
        ArrayList listaRecordsDatabaseTime
        ArrayList listaRecordsDaControllareTime
        int vociControllate = 0
        int vociAggiornate = 0
        String vociControllateTxt = ''
        String vociAggiornateTxt = ''
        long inizio = System.currentTimeMillis()
        long fine
        long durata
        String oldDataTxt

        if (debug) {
            log.info 'Modalita debug'
            this.download(idDebug)
            return 1
        }// fine del blocco if

        // messaggio di log
        log.info 'Metodo di controllo voci ed aggiornamento records esistenti'

        //--Recupera la lista completa dei records presenti nel database
        listaRecordsDatabaseTime = creaListaRecordsDatabaseTime()

        //--Limita eventualmente la lista secondo i parametri usaLimiteDownload e maxDownload
        if (LibPref.getBool('usaLimiteDownload')) {
            maxDownload = LibPref.getInt('maxDownload')
            listaRecordsDaControllareTime = LibArray.estraArray(listaRecordsDatabaseTime, maxDownload)
        } else {
            listaRecordsDaControllareTime = listaRecordsDatabaseTime
        }// fine del blocco if-else
        vociControllate = listaRecordsDaControllareTime.size()

        //--Crea la lista delle voci effettivamente modificate sul server wikipedia dall'ultimo controllo
        listaRecordsModificatiWiki = getListaModificateWiki(listaRecordsDaControllareTime)

        //--Crea la lista delle voci che hanno modificato specificatamente il template bio (e non il resto della voce)
//        listaRecordsModificatiBio = getListaModificateBio(listaRecordsModificatiWiki)
        listaRecordsModificatiBio = listaRecordsModificatiWiki

        //--Crea o modifica i records corrispondenti alle voci nuove ed a quelle che hanno modificato il template bio
        this.regolaVociNuoveModificate(listaRecordsModificatiBio)
        aggiornaUltimaLettura(listaRecordsDaControllareTime)

        if (listaRecordsModificatiBio) {
            vociAggiornate = listaRecordsModificatiBio.size()
        }// fine del blocco if

        // valore di ritorno
        vociControllateTxt = LibTesto.formatNum(vociControllate)
        vociAggiornateTxt = LibTesto.formatNum(vociAggiornate)
        fine = System.currentTimeMillis()
        durata = fine - inizio
        println('Controllo spot: ' + durata)
        durata = durata / 1000
        durata = durata / 60
        log.info "BioWiki. Sono state controllate ${vociControllateTxt} voci e aggiornati ${vociAggiornateTxt} records. Tempo ${durata} min "
        oldDataTxt = LibBio.voceAggiornataVecchia()
        log.info "${oldDataTxt}"

        return listaRecordsModificatiWiki
    } // fine del metodo

    /**
     * Recupera dal server la lista completa delle voci esistenti dalla categoria BioBot
     * Non usa ne Pagina ne Categoria
     * Usa la query QueryCatPageId
     * Deve essere loggato come bot
     * La query richiede (come API) "cmprop=ids|title|sortkeyprefix"
     * per poter regolare il cmcontinue col valore di sortkeyprefix ricevuto
     * La query memorizza poi un unico array di int, grande anche più di 250.000 elementi
     * Tempo necessario: circa 400 secs
     *
     * @return lista completa delle voci esistenti dalla categoria BioBot
     */
    private ArrayList<Integer> getListaVociServerWiki() {
        // variabili e costanti locali di lavoro
        ArrayList<Integer> lista = null
        boolean continua = true
        String titoloCategoria = TITOLO_CATEGORIA
        def num = 0
        Login login = grailsApplication.config.login
        boolean debug = Preferenze.getBool((String) grailsApplication.config.debug)
        String catDebug = Preferenze.getStr((String) grailsApplication.config.catDebug)
        QueryCatPageid query = null
        long inizio = System.currentTimeMillis()
        long fine
        long durata

        if (debug) {
            log.info 'Siamo in modalità debug'
            if (debug) {
                titoloCategoria = catDebug
            } else {
                return lista
            }// fine del blocco if-else
        } else {
            if (!login || !login.isValido() || !login.isBot()) {
                return lista
            }// fine del blocco if
        }// fine del blocco if-else

        // recupera una lista di pageId
        if (debug) {
            log.info "Caricamento della categoria ${titoloCategoria} - Circa 8 minuti (siamo in debug)"
        } else {
//            log.info "Caricamento della categoria ${titoloCategoria} - Circa 6 minuti"
        }// fine del blocco if-else

        try { // prova ad eseguire il codice
            query = new QueryCatPageid(login, titoloCategoria)
        } catch (Exception unErrore) { // intercetta l'errore
            logWikiService.error "Non sono riuscito a caricare la categoria ${titoloCategoria}"
            log.error "Non sono riuscito a caricare la categoria ${titoloCategoria}"
            log.error(unErrore)
            continua = false
        }// fine del blocco try-catch

        if (continua) {
            try { // prova ad eseguire il codice
                lista = query.getListaIds()
            } catch (Exception unErrore) { // intercetta l'errore
                logWikiService.error "Non sono riuscito ad estrarre le pageids dalla categoria ${titoloCategoria}"
                log.error "Non sono riuscito ad estrarre le pageids dalla categoria ${titoloCategoria}"
                log.error(unErrore)
                continua = false
            }// fine del blocco try-catch
        }// fine del blocco if

        //--patch per la voce Pagina principale @todo non ho capito perche entra nella categoria
        if (continua) {
            long inizioLoc
            long fineLoc
            long differenza
            boolean rimossa
            int pageidPaginaPrincipale = 521472
            if (lista.contains(pageidPaginaPrincipale)) {
                inizioLoc = System.currentTimeMillis()
                try { // prova ad eseguire il codice
                    rimossa = lista.remove(lista.indexOf(pageidPaginaPrincipale))
                    fineLoc = System.currentTimeMillis()
                    differenza = fineLoc - inizioLoc
                    log.info "tempo di cancellazione di un elemento della lista: " + differenza
                } catch (Exception unErrore) { // intercetta l'errore
                    log.info "errore nella rimozione di un elemento dalla lista"
                    log.error unErrore
                }// fine del blocco try-catch
            } else {
                log.info "la lista non conteneva la pagina principale"
            }// fine del blocco if-else
            if (rimossa) {
//                    logWikiService.warn 'cancellato il pageid della Pagina principale'
                log.info 'cancellato il pageid della Pagina principale'
            }// fine del blocco if
        }// fine del blocco if

        if (lista) {
            num = lista.size()
            num = Lib.Text.formatNum(num)
            fine = System.currentTimeMillis()
            durata = fine - inizio
            durata = durata / 1000
//            log.info "La categoria contiene ${num} voci ed è stata caricata in ${durata} secondi"
        } else {
            logWikiService.warn("La categoria ${titoloCategoria} non contiene nessuna voce")
            log.warn "La categoria non contiene voci"
        }// fine del blocco if-else

        // valore di ritorno
        return lista
    } // fine del metodo

    /**
     * Recupera la lista dei records esistenti nel database
     * Ordinata secondo il timestamp più vecchio; serve per le voci ''modificate''
     * Mentre per l'aggiunta di voci ''nuove'' è indifferente
     * La lista e composta dai campi:
     *      pageid (int)
     */
    private static ArrayList creaListaRecordsDatabase() {
        // variabili e costanti locali di lavoro
        ArrayList lista

        lista = BioWiki.executeQuery("select pageid from BioWiki order by ultimaLettura asc")

        if (!lista) {
            log.warn "La lista di pageids e vuota"
        }// fine del blocco if-else

        // valore di ritorno
        return lista
    } // fine del metodo

    /**
     * Recupera la lista dei records esistenti nel database
     * Ordinata secondo il timestamp più vecchio; serve per le voci ''modificate''
     * Mentre per l'aggiunta di voci ''nuove'' è indifferente
     * La lista e composta dai campi:
     *      pageid (int)
     *      ultimaLettura (Timestamp)
     */
    private static ArrayList creaListaRecordsDatabaseTime() {
        // variabili e costanti locali di lavoro
        ArrayList lista

        lista = BioWiki.executeQuery("select pageid,ultimaLettura from BioWiki order by ultimaLettura asc")

        if (!lista) {
            log.warn "La lista di pageids/ultimaLettura e vuota"
        }// fine del blocco if-else

        // valore di ritorno
        return lista
    } // fine del metodo

    /**
     * Crea nuovi records per il database biografia
     * Sporca le tavole Giorno e Anno
     */
    def ArrayList<Integer> creaNuoviRecords(ArrayList<Integer> listaNuoviRecords) {
        // variabili e costanti locali di lavoro
        ArrayList<Integer> listaNuoviRecordsCreati = null
        def num = 0
        String numTxt
        long inizio
        long fine
        long durata
        long tempoImpiegato

        if (listaNuoviRecords) {
            num = listaNuoviRecords.size()
            numTxt = Lib.Text.formatNum(num)
            log.info "Ci sono ${num} nuovi records da creare"

            //--creo le voci nuove che non hanno ancora records
            inizio = System.currentTimeMillis()
            this.regolaVociNuoveModificate(listaNuoviRecords)
            fine = System.currentTimeMillis()
            durata = fine - inizio
            tempoImpiegato = durata / num
            log.warn "Fine dei nuovi records - Tempo impiegato = ${tempoImpiegato} millisecondi a voce"
        } else {
            log.warn "Non ci sono nuovi records da creare"
        }// fine del blocco if-else

        return listaNuoviRecordsCreati
    } // fine del metodo

    /**
     * Controlla che i records effettivamente non esistano
     *
     * Controlla che effettivamente la voce non esista oppure non abbia il tmpl:Bio
     * (potrebbe essere sbagliata per difetto la listaCatServerWiki e si corre il rischio
     * di cancellare un record ''valido'')
     * Le liste sono di pageids
     */
    private static ArrayList<Integer> controlloRecordsDaEliminare(ArrayList<Integer> listaRecordsForseEliminandi) {
        // variabili e costanti locali di lavoro
        ArrayList<Integer> listaRecordsDaCancellare = null
        def dim

        //Crea la lista dei records che non hanno più una corrispondente voce su wiki (da cancellare)
        if (!listaRecordsForseEliminandi) {
            log.warn "Non ci sono records da cancellare"
        }// fine del blocco if-else

        //controllo l'effettiva mancanza della voce (la categoria potrebbe averne saltato qualcuno)
        if (listaRecordsForseEliminandi) {
            listaRecordsDaCancellare = new ArrayList()
            listaRecordsForseEliminandi.each {

                if (!QueryBio.isBio(it)) {
                    listaRecordsDaCancellare.add(it)
                }// fine del blocco if

            }// fine di each
        }// fine del blocco if

        if (listaRecordsDaCancellare) {
            dim = listaRecordsDaCancellare.size()
            log.info "Ci sono ${dim} records da cancellare"
        }// fine del blocco if

//        //cancello quelle da cancellare
//        this.regolaVociNonPiuInCategoria(listaRecordsEliminati)
//        if (dim > 0) {
//            log.info "Sono stati cancellati ${dim} records"
//        } else {
//            log.info "Non sono stati cancellati records"
//        }// fine del blocco if-else

        return listaRecordsDaCancellare
    } // fine della closure

    /**
     * Cancella i records esistenti nel database biografia
     */
    private cancellazioneEffettivaRecords(ArrayList<Integer> listaRecordsDaCancellare) {
        if (listaRecordsDaCancellare) {
            listaRecordsDaCancellare.each {
                this.cancellaSingoloRecord(it)
            }// fine del ciclo each
        }// fine del blocco if
    } // fine del metodo

    /**
     * Cancella il singolo record
     * Regola la voce da cancellare
     *
     * Legge prima i parametri significativi del record
     * Sporca le tavole Giorno e Anno
     * Cancella la voce dal database
     */
    def cancellaSingoloRecord(int pageid) {
        // variabili e costanti locali di lavoro
        boolean cancellato = false
        BioWiki bioWiki
        BioGrails bioGrails

        if (pageid) {
            bioWiki = BioWiki.findByPageid(pageid)
            if (bioWiki) {
//                this.regolaLink(bioWiki)   //@todo manca
                try { // prova ad eseguire il codice
                    bioWiki.delete(flush: true)
                } catch (Exception unErrore) { // intercetta l'errore
                    log.error 'cancellaRecord ' + bioWiki.title + ' ' + unErrore.toString()
                }// fine del blocco try-catch
            }// fine del blocco if
            bioGrails = BioGrails.findByPageid(pageid)
            if (bioGrails) {
//                this.regolaLink(bioGrails) //@todo manca
                try { // prova ad eseguire il codice
                    bioGrails.delete(flush: true)
                } catch (Exception unErrore) { // intercetta l'errore
                    log.error 'cancellaRecord ' + bioGrails.title + ' ' + unErrore.toString()
                }// fine del blocco try-catch
            }// fine del blocco if
        }// fine del blocco if

        // valore di ritorno
        return cancellato
    } // fine del metodo

    /**
     * Crea la lista delle voci sicuramente modificate sul server
     *
     * Riceve la lista dal database
     * Spazzola la lista a blocchi stabiliti
     * Un blocco legge n pagine contemporaneamente
     * Per ogni blocco legge da wiki solo il pageid ed il lastrevid
     * Confronta il lastrevid con quello esistente nel record già presente
     * Crea la lista sicuramente modificate
     *
     * @param listaRecordsDaControllare lista di pageids
     * @return listaRecordsModificatiWiki lista di pageids
     */
    private static ArrayList<Integer> getListaModificateWiki(ArrayList listaRecordsDaControllare) {
        // variabili e costanti locali di lavoro
        ArrayList<Integer> listaRecordsModificatiWiki = null
        boolean continua = false
        ArrayList lista = null
        ArrayList listaWrap = null
        ArrayList listaErroriWrap = null
        ArrayList listaModificateTmp = null
        int dimBlocco = 500
        ArrayList listaPageids = null
        int blocchi
        int k = 0
        def mod = 0
        QueryTimestamp query
        HashMap mappa
        String elencoPageids

        // controllo di congruità
        if (listaRecordsDaControllare) {
            continua = true
        }// fine del blocco if

        if (continua) {
            listaPageids = Lib.Array.splitArray(listaRecordsDaControllare, dimBlocco)

            if (listaPageids) {
                continua = true
            } else {
                continua = false
            }// fine del blocco if-else
        }// fine del blocco if

        if (continua) {
            listaRecordsModificatiWiki = new ArrayList()
            blocchi = listaPageids.size()
            listaPageids.each {
                k++
                if (it instanceof ArrayList) {
                    lista = (ArrayList) it
                }// fine del blocco if

                if (lista) {
                    mappa = creaMappa((ArrayList) it)
                    elencoPageids = creaStringaChiavi(mappa)

                    query = new QueryTimestamp(elencoPageids)
                    if (query) {
                        listaWrap = query.getListaWrapTime()
                        if (listaWrap) {
                            listaModificateTmp = chekTimeLista(mappa, listaWrap)
                        }// fine del blocco if
                        listaErroriWrap = query.getListaErrori()
                        if (listaErroriWrap) {
                            listaErroriWrap?.each {
                                log.error 'Errore querytimestamp in ' + it
                            } // fine del ciclo each
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

                try { // prova ad eseguire il codice
                    listaModificateTmp.each {
                        listaRecordsModificatiWiki.add((Integer) it)
                    } // fine del ciclo each

                    mod = listaRecordsModificatiWiki.size()
                } catch (Exception unErrore) { // intercetta l'errore
                    log.error 'getListaModificate' + " al blocco ${k}/$blocchi + " + unErrore.toString()
                }// fine del blocco try-catch
            }// fine del ciclo each
        }// fine del blocco if

        // valore di ritorno
        return listaRecordsModificatiWiki
    } // fine del metodo

    /**
     * Crea la lista delle voci che hanno modificato specificatamente il template bio
     * (e non il resto della voce)
     *
     * @param listaRecordsModificatiWiki lista di pageids
     * @return listaRecordsModificatiBio lista di pageids
     */
    private ArrayList<Integer> getListaModificateBio(ArrayList listaRecordsModificatiWiki) {
        ArrayList<Integer> listaRecordsModificatiBio = null
        boolean usaPagineSingole = LibPref.getBool(LibBio.USA_PAGINE_SINGOLE)
        int dimBlocco = 100
        ArrayList<Integer> listaPageids

        if (listaRecordsModificatiWiki) {
            if (usaPagineSingole) {
                log.info "Controlla la modifica del template bio a singole voci per volta"
                listaRecordsModificatiWiki.each {
                    this.download((int) it, true)
                }// fine del ciclo each
            } else {
                listaPageids = Lib.Array.splitArray(listaRecordsModificatiWiki, dimBlocco)
                if (listaPageids) {
                    listaPageids.each {
                        this.regolaBloccoNuovoModificato((ArrayList) it)
                    }// fine del ciclo each
                }// fine del blocco if
            }// fine del blocco if-else
        }// fine del blocco if

        return listaRecordsModificatiBio
    } // fine del metodo

    /**
     * Creo le voci nuove che non hanno ancora records oppure modificate
     *
     * Spazzola la lista a blocchi stabiliti
     * Un blocco legge n pagine contemporaneamente
     * Per ogni pagina crea il nuovo record e sporca le tavole Giorno e Anno
     *
     * Nei test per caricare 126 pagine impiega
     * Pagina singola: 59, 67 secondi
     * Blocchi di 10 pagine: 17 circa (0,134 sec/pag)
     * Blocchi di 50 pagine: 9,3 circa (0,073 sec/pag)
     * Blocchi di 100 pagine: 9,8 circa (0,077 sec/pag)

     * Nei test per caricare 722 pagine impiega (caricate 720)
     * Blocchi di 10 pagine: 127 secondi (0,175 sec/pag)
     * Blocchi di 50 pagine: 80/102/111 secondi (0,135 sec/pag)
     * Blocchi di 100 pagine: 87 secondi (0,120 sec/pag)
     *
     * @param lista di pageids
     */
    public regolaVociNuoveModificate(ArrayList<Integer> listaVoci) {
        // variabili e costanti locali di lavoro
        boolean usaPagineSingole = Preferenze.getBool((String) grailsApplication.config.usaPagineSingole)
        int dimBlocco = 100
        ArrayList<Integer> listaPageids

        if (listaVoci) {
            if (usaPagineSingole) {
                log.info "Regola le voci nuove o modificate a singole voci per volta"
                listaVoci.each {
                    this.download((int) it, true)
                    tempo()
                }// fine del ciclo each
            } else {
                listaPageids = Lib.Array.splitArray(listaVoci, dimBlocco)
                if (listaPageids) {
                    listaPageids.each {
                        this.regolaBloccoNuovoModificato((ArrayList) it)
                    }// fine del ciclo each
                }// fine del blocco if
            }// fine del blocco if-else
        }// fine del blocco if
    } // fine del metodo

    /**
     * Spazzola la lista a blocchi stabiliti
     *
     * Un blocco legge n pagine contemporaneamente
     * Per ogni pagina crea il nuovo record o modifica quello esistente e sporca le tavole Giorno e Anno
     *
     * @param lista di pageids
     */
    def regolaBloccoNuovoModificato(ArrayList listaPageids) {
        // variabili e costanti locali di lavoro
        ArrayList listaMappe
        QueryMultiBio query
        WrapBio wrapBio
        String title
        int pageid
        boolean registrata = false
        HashMap mappa
        StatoBio statoBio

        if (listaPageids) {
            try { // prova ad eseguire il codice
                query = new QueryMultiBio(listaPageids)
                listaMappe = query.getListaMappe()
            } catch (Exception unErrore) { // intercetta l'errore
                log.error 'Non sono riuscito ad eseguire la query ' + unErrore.toString()
            }// fine del blocco try-catch

            if (listaMappe && listaMappe.size() > 0) {
                listaMappe.each {
                    mappa = (HashMap) it
                    wrapBio = new WrapBio(mappa)
                    title = wrapBio.getTitoloVoce()
                    pageid = wrapBio.getPageid()
                    statoBio = wrapBio.getStatoBio()
                    switch (statoBio) {
                        case StatoBio.bioNormale:
                            wrapBio.registraBioWiki()
                            registrata = true
                            break
                        case StatoBio.bioIncompleto:
                            wrapBio.registraBioWiki()
                            logWikiService.warn "Alla voce '''[[${title}]]''' mancano alcuni campi indispensabili per il funzionamento del '''[[Template:Bio|tmpl Bio]]'''"
                            registrata = false
                            break
                        case StatoBio.bioErrato:
                            logWikiService.error "La voce '''[[${title}]]''' presenta il '''[[Template:Bio|tmpl Bio]]''' completamente errato"
                            registrata = false
                            break
                        case StatoBio.senzaBio:
                            logWikiService.error "Nella voce '''[[${title}]]''' manca completamente il '''[[Template:Bio|tmpl Bio]]'''"
                            registrata = false
                            break
                        case StatoBio.vuota:
                            logWikiService.error "La voce '''[[${title}]]''' non ha nessun contenuto di testo"
                            registrata = false
                            break
                        case StatoBio.redirect:
                            logWikiService.error "La voce '''[[${title}]]''' non è una voce biografica, ma un '''[[Aiuto:Redirect|redirect]]'''"
                            registrata = false
                            break
                        case StatoBio.disambigua:
                            logWikiService.error "La voce '''[[${title}]]''' non è una voce biografica, ma una '''[[Aiuto:Disambigua|disambigua]]'''"
                            registrata = false
                            break
                        case StatoBio.maiEsistita:
                            if (title) {
                                logWikiService.error "La voce '''[[${title}]]''' non esiste"
                            } else {
                                if (pageid) {
                                    logWikiService.error "Non esiste la voce col pageid = '''${pageid}'''"
                                } else {
                                    logWikiService.error "Non esiste una pagina"
                                }// fine del blocco if-else
                            }// fine del blocco if-else
                            registrata = false
                            break
                        default: // caso non definito
                            break
                    } // fine del blocco switch

                    if (!registrata) {
                        log.error "regolaBloccoNuovoModificato - La voce ${title}, non è stata registrata"
                    }// fine del blocco if
                }// fine del ciclo each
            }// fine del blocco if
        }// fine del blocco if
    } // fine della closure

    // voci esaminate
    void aggiornaUltimaLettura(ArrayList listaRecordsEsaminati) {
        String tagIni = '('
        String tagEnd = ')'
        String tagSep = ','
        String query
        String range = ''
        Timestamp ultimaLettura = new Timestamp(System.currentTimeMillis())
        def letturaWiki = (new Timestamp(System.currentTimeMillis())).clearTime()

        range += tagIni
        listaRecordsEsaminati?.each {
            range += it[0]
            range += tagSep
        } // fine del ciclo each
        range = LibTesto.levaCoda(range, tagSep)
        range += tagEnd

        if (range) {
            query = "update BioWiki set ultimaLettura='${ultimaLettura}' where pageid in ${range}"
            BioWiki.executeUpdate(query)
            query = "update BioWiki set letturaWiki='${letturaWiki}' where pageid in ${range}"
            BioWiki.executeUpdate(query)
        }// fine del blocco if
    } // fine del metodo

/**
 * Regola i collegamenti (link) alle tavole:
 * Giorno
 * Anno
 * Attivita
 * Nazionalita
 * @todo obsoleto va in libreria
 * @deprecated
 * @param istanza di biografia originale wiki
 * @param istanza di biografia corretta
 */
    def regolaLink = { biografiaWiki ->
        // variabili e costanti locali di lavoro
        def biografiaTmp

        // controllo di congruità
        if (biografiaWiki) {
            biografiaTmp = this.clonaBiografia(biografiaWiki)
            biografiaTmp = this.regolaNoteErr(biografiaWiki, biografiaTmp)

            // giorno e mese di nascita
            biografiaWiki.giornoMeseNascitaLink = GiornoService.sporcoNato(biografiaWiki.giornoMeseNascita)
            if (biografiaWiki.giornoMeseNascitaLink == null) {
                biografiaWiki.giornoMeseNascitaLink = GiornoService.sporcoNato(biografiaTmp.giornoMeseNascita)
            }// fine del blocco if

            // giorno e mese di morte
            biografiaWiki.giornoMeseMorteLink = GiornoService.sporcoMorto(biografiaWiki.giornoMeseMorte)
            if (biografiaWiki.giornoMeseMorteLink == null) {
                biografiaWiki.giornoMeseMorteLink = GiornoService.sporcoMorto(biografiaTmp.giornoMeseMorte)
            }// fine del blocco if

            // anno di nascita
            biografiaWiki.annoNascitaLink = AnnoService.sporcoNato(biografiaWiki.annoNascita)
            if (biografiaWiki.annoNascitaLink == null) {
                biografiaWiki.annoNascitaLink = AnnoService.sporcoNato(biografiaTmp.annoNascita)
            }// fine del blocco if

            // anno di morte
            biografiaWiki.annoMorteLink = AnnoService.sporcoMorto(biografiaWiki.annoMorte)
            if (biografiaWiki.annoMorteLink == null) {
                biografiaWiki.annoMorteLink = AnnoService.sporcoMorto(biografiaTmp.annoMorte)
            }// fine del blocco if

            // attività principale
            biografiaWiki.attivitaLink = AttivitaService.getAttivita(biografiaWiki.attivita)
            if (biografiaWiki.attivitaLink == null) {
                biografiaWiki.attivitaLink = AttivitaService.getAttivita(biografiaTmp.attivita)
            }// fine del blocco if

            // attività secondaria
            biografiaWiki.attivita2Link = AttivitaService.getAttivita(biografiaWiki.attivita2)
            if (biografiaWiki.attivita2Link == null) {
                biografiaWiki.attivita2Link = AttivitaService.getAttivita(biografiaTmp.attivita2)
            }// fine del blocco if

            // attività terziaria
            biografiaWiki.attivita3Link = AttivitaService.getAttivita(biografiaWiki.attivita3)
            if (biografiaWiki.attivita3Link == null) {
                biografiaWiki.attivita3Link = AttivitaService.getAttivita(biografiaTmp.attivita3)
            }// fine del blocco if

            // nazionalità
            biografiaWiki.nazionalitaLink = NazionalitaService.getNazionalita(biografiaWiki.nazionalita)
            if (biografiaWiki.nazionalitaLink == null) {
                biografiaWiki.nazionalitaLink = NazionalitaService.getNazionalita(biografiaTmp.nazionalita)
            }// fine del blocco if
        }// fine del blocco if
    } // fine della closure

/**
 * Scarica da wikipedia una voce e crea/aggiorna un record sul database
 *
 * carica i parametri del template Bio, leggendolo dalla voce di wikipedia
 * la query legge la pagina col pageid
 * non sono ancora sicuro che la voce corrispondente al pageid sia una biografia
 * In alcuni casi corregge direttamente la pagina e riporta nel log //todo da sviluppare
 * controlla l'integrità dei dati dopo aver registrato
 *
 * @param pageid codice id del server wiki (# dal grailsId)
 * @param esegueUpload - per evitare il loop
 * @return record di biografia
 */
    public download(int pageid, boolean esegueUpload) {
        BioWiki biografia = null
        boolean continua = false
        WrapBio wrapBio
        int testoOld
        int testoNew
        String title

        //controllo di congruita
        if (pageid && pageid > 0) {
            continua = true
        }// fine del blocco if

        if (continua) {
            wrapBio = new WrapBio(pageid)
            if (wrapBio.getStatoBio() == StatoBio.bioNormale || wrapBio.getStatoBio() == StatoBio.bioIncompleto) {
                wrapBio.registraBioWiki()
            } else {
                title = wrapBio.getTitoloVoce()
                log.error "download - La voce ${title}, non è stata registrata"
            }// fine del blocco if-else

//            testoOld = wrapBio.getTestoVoceOriginale().length()
//            testoNew = wrapBio.getTestoVoceFinale().length()
//            if (testoOld != testoNew) {
//                if (wrapBio.registraPaginaWiki()) {
//                    wrapBio.getBioRegistrabile().controllato = true
//                }// fine del blocco if
//            } else {
//                wrapBio.getBioRegistrabile().controllato = true
//            }// fine del blocco if-else
//            wrapBio.registraRecordDbSql()
        }// fine del blocco if

        // valore di ritorno
        return biografia
    } // fine della closure

/**
 * Scarica da wikipedia una voce e crea/aggiorna un record sul database
 *
 * carica i parametri del template Bio, leggendolo dalla voce di wikipedia
 * la query legge la pagina col pageid
 * non sono ancora sicuro che la voce corrispondente al pageid sia una biografia
 * In alcuni casi corregge direttamente la pagina e riporta nel log //todo da sviluppare
 * controlla l'integrità dei dati dopo aver registrato
 *
 * @param pageid codice id del server wiki (# dal grailsId)
 * @return record di biografia
 */
    public download(int pageid) {
        return this.download(pageid, false) //@todo provvisorio deve diventare true
    } // fine della closure

/**
 * Differenza tra due liste
 */
    private static ArrayList<Integer> deltaListe(ArrayList<Integer> primaLista, ArrayList<Integer> secondaLista) {
        // variabili e costanti locali di lavoro
        ArrayList<Integer> listaDiff = null

//        log.info 'Recupera la lista delle voci nuove che non hanno ancora records (da creare)'

        if (primaLista) {
            if (secondaLista) {
                listaDiff = new ArrayList<Integer>()
                primaLista.each {
                    if (!secondaLista.contains(it)) {
                        try { // prova ad eseguire il codice
                            if (it > 0) {
                                listaDiff.add(it)
                            }// fine del blocco if
                        } catch (Exception unErrore) { // intercetta l'errore
                            log.error 'deltaListe codice non numerico?'
                        }// fine del blocco try-catch
                    }// fine del blocco if
                }// fine del ciclo each
            } else {
                listaDiff = primaLista
            }// fine del blocco if-else
        }// fine del blocco if

        // valore di ritorno
        return listaDiff
    } // fine della closure

/**
 * Controlla se le voci (su wiki) sono state modificate rispetto alla versione esistente (sul database)
 *
 * Confronta il lastrevid con quello esistente nella voce
 * Crea la lista sicuramente modificate
 *
 * @param listaForseModificate lista parziale (a blocchi) di mappe con pageids=lastrevids
 * @return lista di pageid
 */
    private static ArrayList chekTimeListaOld(ArrayList listaWrapTime) {
        // variabili e costanti locali di lavoro
        ArrayList listaPageid = null
        WrapTime wrap
        BioWiki bio
        int pageid
        Timestamp valoreWiki = null
        Timestamp valoreDatabase = null
        int lastrevid

        if (listaWrapTime) {
            listaPageid = new ArrayList()

            listaWrapTime.each {
                wrap = null
                pageid = 0
                bio = null
                if (it instanceof WrapTime) {
                    wrap = (WrapTime) it
                }// fine del blocco if

                if (wrap) {
                    pageid = wrap.getPageid()
                    valoreWiki = wrap.getTimestamp()
                }// fine del blocco if

                if (pageid) {
                    bio = BioWiki.findByPageid(pageid)
                }// fine del blocco if

                if (bio) {
                    valoreDatabase = bio.timestamp
                }// fine del blocco if

                if (valoreWiki > valoreDatabase) {
                    listaPageid.add(pageid)
                }// fine del blocco if
            }// fine del ciclo each
        }// fine del blocco if

        // valore di ritorno
        return listaPageid
    } // fine della closure

/**
 * Controlla se le voci (su wiki) sono state modificate rispetto alla versione esistente (sul database)
 *
 * Confronta il lastrevid con quello esistente nella voce
 * Crea la lista sicuramente modificate
 *
 * @param mappa
 * @param listaWrapTime
 * @return lista di pageid
 */
    private static ArrayList chekTimeLista(HashMap mappa, ArrayList listaWrapTime) {
        // variabili e costanti locali di lavoro
        ArrayList listaPageid = null
        WrapTime wrap
        int pageid
        Timestamp valoreWiki = null
        Timestamp valoreDatabase = null
        int lastrevid

        if (mappa && listaWrapTime) {
            listaPageid = new ArrayList()

            listaWrapTime.each {
                wrap = null
                pageid = 0
                if (it instanceof WrapTime) {
                    wrap = (WrapTime) it
                }// fine del blocco if

                if (wrap) {
                    pageid = wrap.getPageid()
                    valoreWiki = wrap.getTimestamp()
                }// fine del blocco if

                if (pageid) {
                    if (mappa.get(pageid)) {
                        valoreDatabase = (Timestamp) mappa.get(pageid)
                    }// fine del blocco if

                    if (valoreWiki > valoreDatabase) {
                        listaPageid.add(pageid)
                    }// fine del blocco if
                }// fine del blocco if

            }// fine del ciclo each
        }// fine del blocco if

        // valore di ritorno
        return listaPageid
    } // fine della closure

/**
 * Restituisce il valore booleano di un parametro Setting
 * @deprecated
 */
    public static boolSetting = { code ->
        // variabili e costanti locali di lavoro
        boolean ritorno = false
        boolean continua = (code && code in String)

        if (continua) {
            continua = (LibPref.getBool(code))
        }// fine del blocco if

        if (continua) {
            ritorno = (LibPref.getBool(code).equals('true'))
        }// fine del blocco if

        // valore di ritorno
        return ritorno
    }// fine della closure

/**
 * Restituisce il contrario del valore booleano di un parametro Setting
 * @deprecated
 */
    public static notBoolSetting = { code ->
        // variabili e costanti locali di lavoro
        boolean ritorno = false
        boolean continua = (code && code in String)

        if (continua) {
            def alfa = boolSetting(code)
            if (alfa) {
                ritorno = false
            } else {
                ritorno = true
            }// fine del blocco if-else
        }// fine del blocco if

        // valore di ritorno
        return ritorno
    }// fine della closure

/**
 * Controlla se il numero passato è un multiplo esatto
 * @todo obsoleto va in libreria
 * @deprecated
 */
    public isStep = { int numero, int intervallo ->
        // variabili e costanti locali di lavoro
        boolean step = false
        def delta
        def intero

        if (numero && intervallo) {
            if (numero >= intervallo) {
                delta = numero / intervallo
                intero = (int) delta

                if (intero == delta) {
                    step = true
                }// fine del blocco if

            }// fine del blocco if
        }// fine del blocco if

        // valore di ritorno
        return step
    }// fine della closure

    private static tempo() {
//       log.info LibBio.deltaMin(inizio) + ' minuti dal via'
    }// fine del metodo

    private static tempoSec() {
//        log.info LibBio.deltaSec(inizio) + ' secondi dal via'
    }// fine del metodo


    private static HashMap creaMappa(ArrayList listaPageidsTime) {
        HashMap mappa = new HashMap()

        listaPageidsTime?.each {
            mappa.put(it[0], it[1])
        } // fine del ciclo each

        return mappa
    }// fine del metodo

    private static String creaStringaChiavi(HashMap mappa) {
        String elencoPageids = ''
        def key
        String sep = '|'
        if (mappa) {
            key = mappa.keySet()
            elencoPageids = ''
            key?.each {
                elencoPageids += it
                elencoPageids += sep
            } // fine del ciclo each
            elencoPageids = LibTesto.levaCoda(elencoPageids, sep)
        }// fine del blocco if

        return elencoPageids
    }// fine del metodo

    /**
     * Crea il testo definitivo del template con i dati modificati nel record di BioWiki
     *
     * I campi/parametri sono ordinati come l'enumeration
     * Riporta sempore i campi/parametri (anche vuoti)
     * Riporta gli altri campi/parametri solo se hanno un valore
     */
    public String creaTestoTemplateBioWiki(int pageid) {
        String testoTemplate = ''
        BioWiki bioWiki = BioWiki.findByPageid(pageid)
        String tagIni = '{{Bio'
        String aCapo = '\n'
        String tagPipe = '|'
        String tagSep = ' = '
        String tagEnd = '}}'
        def campo

        //controllo di congruita
        if (pageid && bioWiki) {
            testoTemplate += tagIni
            testoTemplate += aCapo
            ParBio.each {
                campo = it.toString()
                if (it.semplice || bioWiki."${campo}") {
                    testoTemplate += tagPipe
                    testoTemplate += it.tag
                    testoTemplate += tagSep
                    if (bioWiki."${campo}") {
                        testoTemplate += bioWiki."${campo}"
                    }// fine del blocco if
                    testoTemplate += aCapo
                }// fine del blocco if
            } // fine di each
            testoTemplate += tagEnd
        }// fine del blocco if

        return testoTemplate
    } // fine della closure

} // fine della service classe
