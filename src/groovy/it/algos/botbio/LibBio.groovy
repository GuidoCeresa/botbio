package it.algos.botbio

import groovy.util.logging.Log4j
import it.algos.algoslib.Lib
import it.algos.algoslib.LibTesto
import it.algos.algoslib.LibTime
import it.algos.algospref.LibPref
import it.algos.algoswiki.Pagina
import it.algos.algoswiki.QueryInfoCat
import it.algos.algoswiki.Risultato
import it.algos.algoswiki.WikiLib

import java.sql.Timestamp
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 12-8-13
 * Time: 18:00
 */
@Log4j
class LibBio {

    public static final String DEBUG = 'debug'
    public static final String ANNO_DEBUG = 'annoDebug'
    public static final String CAT_DEBUG = 'catDebug'
    public static final String USA_PAGINE_SINGOLE = 'usaPagineSingole'
    public static final String USA_LIMITE_DOWNLOAD = 'usaLimiteDownload'
    public static final String USA_LIMITE_ELABORA = 'usaLimiteElabora'
    public static final String MAX_DOWNLOAD = 'maxDownload'
    public static final String MAX_ELABORA = 'maxElabora'
    public static final String USA_CRONO_DOWNLOAD = 'usaCronoDownload'
    public static final String USA_CRONO_UPLOAD = 'usaCronoUpload'
    public static final String USA_CRONO_ELABORA = 'usaCronoElabora'
    public static final String USA_CRONO_ANTROPONIMI = 'usaCronoAntroponimi'
    public static final String USA_CRONO_ATTIVITA = 'usaCronoAttivita'
    public static final String USA_CRONO_NAZIONALITA = 'usaCronoNazionalita'
    public static final String USA_CASSETTO = 'usaCassetto'
    public static final String MAX_RIGHE_CASSETTO = 'maxRigheCassetto'
    public static final String USA_COLONNE = 'usaColonne'
    public static final String MAX_RIGHE_COLONNE = 'maxRigheColonne'
    public static final String REGISTRA_SOLO_MODIFICHE_SOSTANZIALI = 'registraSoloModificheSostanziali'
    public static final String EVENTO_TESTO_TROPPO_LUNGO = 'DataTooLong'
    public static final String TAGLIO_ANTROPONIMI = 'taglioAntroponimi'
    public static final String SOGLIA_ANTROPONIMI = 'sogliaAntroponimi'
    public static final String USA_OCCORRENZE_ANTROPONIMI = 'usaOccorrenzeAntroponimi'
    public static final String CONFRONTA_SOLO_PRIMO_NOME_ANTROPONIMI = 'confrontaSoloPrimoNomeAntroponimi'
    public static final String SUMMARY = 'summary'
    public static final String VOCI = 'numeroVociGestite'
    public static final String GIORNI = 'numeroGiorniGestiti'
    public static final String ANNI = 'numeroAnniGestiti'
    public static final String ATTIVITA = 'numeroAttivitaGestite'
    public static final String NAZIONALITA = 'numeroNazionalitaGestite'
    public static final String ATTESA = 'giorniAttesa'
    public static final String ULTIMA_SINTESI = 'ultimaSintesi'
    public static final String AGGIUNTI = 'recordsAggiunti'
    public static final String MODIFICATI = 'recordsModificati'
    public static final String CANCELLATI = 'recordsCancellati'

    private static String TAG_BIO = '\\{\\{ ?([Tt]emplate:)? ?[Bb]io[ \\|\n\r\t]'

    /**
     * Recupera la mappa dalla pagina wiki
     * Ordina alfabeticamente la mappa
     * Riscrive la pagina wiki in ordine alfabetico (sul plurale)
     *
     * @param wikiService
     * @param titolo della pagina di servizio
     * @return mappa
     */
    public static getMappa = { wikiService, titolo ->
        // variabili e costanti locali di lavoro
        def mappa = null
        boolean continua = false

        // controllo di congruità
        if (wikiService && titolo) {
            continua = true
        }// fine del blocco if

        if (continua) {
            // legge la pagina di servizio
            mappa = wikiService.leggeSwitchMappa(titolo)

            // forza come minuscola la prima lettera iniziale dell'attività/nazionalità plurale
            mappa = Libreria.valoreMappaMinuscolo(mappa)

            // Ordina alfabeticamente la mappa
            mappa = Libreria.ordinaValore(mappa)

            // Riscrive la pagina wiki in ordine alfabetico (sul plurale)
            LibAttNaz.uploadMappa(wikiService, titolo, mappa)
        }// fine del blocco if

        // valore di ritorno
        return mappa
    }// fine della closure

    /**
     * Estrae una mappa chiave valore dal testo del template Bio
     * Presuppone che le righe siano separate da pipe e return
     * Controllo della parità delle graffe interne (nel metodo estraeTemplate)
     * Gestisce l'eccezione delle graffe interne (nel metodo getMappaReali)
     * Elimina un eventuale pipe iniziale in tutte le chiavi della mappa
     *
     * @param testoTemplate del template Bio
     * @param titoloVoce sulla wiki
     * @return mappa di TUTTI i parametri esistenti nel testo
     */
    public static LinkedHashMap getMappaRealiBio(String testoTemplate, String titoloVoce) {
        // variabili e costanti locali di lavoro
        LinkedHashMap mappa = null
        boolean continua = false

        // controllo di congruita
        if (testoTemplate) {
            mappa = WikiLib.estraeTmpMappa(testoTemplate)
            mappa = WikiLib.regolaMappaPipe(mappa)
            mappa = WikiLib.regolaMappaGraffe(mappa)
        }// fine del blocco if

        //--avviso di errore
        if (!mappa) {
            log.warn "getMappaRealiBio - La pagina ${titoloVoce}, non contiene ritorni a capo"
        }// fine del blocco if

        // valore di ritorno
        return mappa
    } // fine del metodo

    /**
     * Estrae una mappa chiave valore per un fix di parametri, dal testo di una biografia
     *
     * E impossibile sperare in uno schema fisso
     * Occorre considerare le {{ graffe annidate, i | (pipe) annidati
     * i mancati ritorni a capo, ecc., ecc.
     *
     * Uso la lista dei parametri che può riconoscere
     * (è meno flessibile, ma più sicuro)
     * Cerco il primo parametro nel testo e poi spazzolo il testo per cercare
     * il primo parametro noto e così via
     *
     * @param testoTemplate del template Bio
     * @return mappa dei parametri esistenti nella enumeration e presenti nel testo
     */
    public static LinkedHashMap getMappaTabBio(String testoTemplate) {
        // variabili e costanti locali di lavoro
        LinkedHashMap mappa = new LinkedHashMap()
        def lista
        HashMap mappaTmp = new HashMap()
        def chiave
        String sep = '|'
        String sep2 = '| '
        String spazio = ' '
        String uguale = '='
        String tab = '\t'
        String valore
        int pos
        int posUgu
        def listaTag

        if (testoTemplate) {
            ParBio.values().each {
                try { // prova ad eseguire il codice
                    valore = it.getTag()
                } catch (Exception unErrore) { // intercetta l'errore
                    if (it instanceof String) {
                        valore = it
                    }// fine del blocco if
                }// fine del blocco try-catch

                listaTag = new ArrayList()
                listaTag.add(sep + valore + spazio)
                listaTag.add(sep + valore + uguale)
                listaTag.add(sep + valore + tab)
                listaTag.add(sep + valore + spazio + uguale)
                listaTag.add(sep2 + valore + spazio)
                listaTag.add(sep2 + valore + uguale)
                listaTag.add(sep2 + valore + tab)
                listaTag.add(sep2 + valore + spazio + uguale)

                try { // prova ad eseguire il codice
                    pos = Lib.Txt.getPos(testoTemplate, listaTag)
                } catch (Exception unErrore) { // intercetta l'errore
//                    log.error testoTemplate
                }// fine del blocco try-catch
                if (pos > -1) {
                    mappaTmp.put(pos, valore)
                }// fine del blocco if
            }// fine di each

            lista = mappaTmp.keySet().sort { a, b -> (a < b) ? -1 : 1 }
            if (lista) {
                lista.add(testoTemplate.length())

                for (int k = 1; k < lista.size(); k++) {
                    chiave = mappaTmp.get(lista.get(k - 1))
                    valore = testoTemplate.substring((int) lista.get(k - 1), (int) lista.get(k))
                    if (valore) {
                        valore = valore.trim()
                        posUgu = valore.indexOf(uguale)
                        if (posUgu != -1) {
                            posUgu += uguale.length()
                            valore = valore.substring(posUgu).trim()
                        }// fine del blocco if
                        valore = regValore(valore)
                        valore = regACapo(valore)
                        valore = regBreakSpace(valore)
                        valore = valore.trim()
                        mappa.put(chiave, valore)
                    }// fine del blocco if

                } // fine del ciclo for
            }// fine del blocco if
        }// fine del blocco if

        mappa = WikiLib.regolaMappaPipe(mappa)
        mappa = WikiLib.regolaMappaGraffe(mappa)

        // valore di ritorno
        return mappa
    } // fine del metodo

    /**
     * Elimina il pipe iniziale
     *
     */
    public static String regValore(String valoreIn) {
        // variabili e costanti locali di lavoro
        String valoreOut = valoreIn
        String pipe = '|'

        if (valoreIn.startsWith(pipe)) {
            valoreOut = ''
        }// fine del blocco if

        // valore di ritorno
        return valoreOut.trim()
    } // fine del metodo

    /**
     * Controlla il primo aCapo che trova:
     * - se è all'interno di doppie graffe, non leva niente
     * - se non ci sono dopppie graffe, leva dopo l' aCapo
     *
     */
    public static String regACapo(String valoreIn) {
        // variabili e costanti locali di lavoro
        String valoreOut = valoreIn
        String aCapo = '\n'
        String doppioACapo = aCapo + aCapo
        String pipeACapo = aCapo + '|'
        int pos
        def mappaGraffe

        if (valoreIn && valoreIn.contains(doppioACapo)) {
            valoreOut = valoreOut.replace(doppioACapo, aCapo)
        }// fine del blocco if

        if (valoreIn && valoreIn.contains(pipeACapo)) {
            mappaGraffe = WikiLib.checkGraffe(valoreIn)

            if (mappaGraffe.isGraffe) {
            } else {
                pos = valoreIn.indexOf(pipeACapo)
                valoreOut = valoreIn.substring(0, pos)
            }// fine del blocco if-else
        }// fine del blocco if

        // valore di ritorno
        return valoreOut.trim()
    } // fine del metodo

    /**
     * Elimina un valore strano trovato (ed invisibile)
     * ATTENZIONE: non è uno spazio vuoto !
     * Trattasi del carattere: C2 A0 ovvero U+00A0 ovvero NO-BREAK SPACE
     * Non viene intercettato dal comando Java TRIM()
     */
    public static String regBreakSpace(String valoreIn) {
        // variabili e costanti locali di lavoro
        String valoreOut = valoreIn
        String strano = ' '   //NON cancellare: sembra uno spazio, ma è un carattere invisibile

        if (valoreIn.startsWith(strano)) {
            valoreOut = valoreIn.substring(1)
        }// fine del blocco if

        if (valoreIn.endsWith(strano)) {
            valoreOut = valoreIn.substring(0, valoreIn.length() - 1)
        }// fine del blocco if

        // valore di ritorno
        return valoreOut.trim()
    } // fine del metodo

    /**
     * Clona i campi/parametri di biografia
     * NON copia i campi id e version
     *
     * @param bioOriginale
     * @return bioCopia
     */
    public static BioWiki clonaBio(BioWiki bioOriginale) {
        // variabili e costanti locali di lavoro
        BioWiki bioCopia = null
        String nomeCampo
        def valCampo
        int id

        if (bioOriginale) {
            if (bioOriginale.id) {
                id = bioOriginale.id
            }// fine del blocco if

            if (id) {
                bioCopia = BioWiki.findById(id)
            }// fine del blocco if

            if (!bioCopia) {
                bioCopia = new BioWiki()
            }// fine del blocco if

            bioCopia.properties.each {
                nomeCampo = it.key
                valCampo = bioOriginale."${nomeCampo}"

                try { // prova ad eseguire il codice
                    // if (!nomeCampo.equals('id') && !nomeCampo.equals('version')) {
                    bioCopia."${nomeCampo}" = valCampo
                    //}// fine del blocco if
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch

            }// fine di each
        }// fine del blocco if

        // valore di ritorno
        return bioCopia
    } // fine del metodo

    /**
     * Corregge il parametro DidascaliaTipo
     * Se il parametro Immagine è vuoto, vuota anche la didascalia
     */
    public static String correggeParametroDidascalia(BioWiki bio, String oldValue) {
        String newValue = oldValue
        String immagine = ','

        if (!bio?.immagine) {
            newValue = ''
        }// fine del blocco if

        // valore di ritorno
        return newValue
    } // fine della closure

    /**
     * Controlla l'esistenza del template bio nel testo della voce
     *
     * @testo da controllare
     * @return vero se esiste il template
     */
    public static isBio(String testoVoce) {
        return Lib.Txt.hasTag(testoVoce, TAG_BIO)
    }// fine del metodo

    /**
     * Registra le voci gestite
     *
     * @param debug log di controllo
     */
    public static void gestVoci(def logService, boolean debug) {
        def numVoci
        int vociCat
        String percentuale
        String avviso = ''
        QueryInfoCat query

        query = new QueryInfoCat('BioBot')
        vociCat = query.getSize()
        numVoci = BioWiki.count()
        percentuale = LibTesto.formatPercentuale(numVoci, vociCat)
        numVoci = LibTesto.formatNum(numVoci)
        avviso += "[[Utente:Biobot|<span style=\"color:green\">'''Biobot'''</span>]]"
        avviso += " gestisce ${numVoci} voci pari al '''${percentuale}'''"
        avviso += " della categoria [[:Categoria:BioBot|'''BioBot''']]"
        if (debug) {
            log.info(avviso)
        } else {
            if (logService) {
                logService.info(avviso)
            }// fine del blocco if
        }// fine del blocco if-else
    }// fine del metodo

    /**
     * Registra le voci gestite
     *
     * @param debug log di controllo
     */
    public static String gestVoci(
            def logService, boolean debug, long durata, int aggiunte, int cancellate, int modificate) {
        return gestVoci(logService, debug, durata, aggiunte, cancellate, modificate, BioWiki.count())
    }// fine del metodo

    /**
     * Registra le voci gestite
     *
     * @param debug log di controllo
     */
    public static String gestVoci(
            def logService, boolean debug, long durata, int aggiunte, int cancellate, int modificate, int vociTotali) {
        String avviso = ''
        int vociCat
        String aggiunteTxt
        String cancellateTxt
        String modificateTxt
        String numVociTotaliTxt
        String percentuale
        QueryInfoCat query
        String durataSecondiTxt
        String durataMinutiTxt
        long tempo
        String tempoTxt = ''
        int durataSec
        int durataMin
        int tempoInt

        query = new QueryInfoCat('BioBot')
        vociCat = query.getSize()
        percentuale = LibTesto.formatPercentuale(vociTotali, vociCat)
        aggiunteTxt = LibTesto.formatNum(aggiunte)
        cancellateTxt = LibTesto.formatNum(cancellate)
        modificateTxt = LibTesto.formatNum(modificate)
        numVociTotaliTxt = LibTesto.formatNum(vociTotali)
        if (aggiunte > 0) {
            tempo = durata / aggiunte
            tempo = tempo / 100
            tempoInt = tempo.intValue()
            tempoTxt = LibTesto.formatNum(tempoInt)
            tempoTxt = tempoTxt.substring(0, tempoTxt.length() - 1) + ',' + tempoTxt.substring(tempoTxt.length() - 1)
            if (tempoTxt.startsWith(',')) {
                tempoTxt = '0' + tempoTxt
            }// fine del blocco if
        }// fine del blocco if
        durata = durata / 1000
        durataSec = durata.intValue()
        durataMin = durataSec / 60
        durataSecondiTxt = LibTesto.formatNum(durataSec)
        durataMinutiTxt = LibTesto.formatNum(durataMin)
        avviso += "Ciclo di ${durataMinutiTxt} min. "
        avviso += "Aggiunte: ${aggiunteTxt}. "
        avviso += "Cancellate: ${cancellateTxt}. "
        avviso += "Modificate: ${modificateTxt}. "
        avviso += "[[Utente:Biobot|<span style=\"color:green\">'''Biobot'''</span>]]"
        avviso += " gestisce ${numVociTotaliTxt} voci pari al '''${percentuale}'''"
        avviso += " della categoria [[:Categoria:BioBot|'''BioBot''']]"

        if (debug) {
            log.info(avviso)
        } else {
            if (logService) {
                logService.info(avviso)
            }// fine del blocco if
        }// fine del blocco if-else

        return avviso
    }// fine del metodo

    /**
     * Riordina la mappa di parametri Bio
     * Li ordina secondo la Enumeration
     * Aggiunge quelli ''semplici'', anche se vuoti
     *
     * @param mappaIn ingresso
     * @return mappaOut uscita
     */
    public static LinkedHashMap riordinaMappa(LinkedHashMap mappaIn) {
        LinkedHashMap mappaOut = mappaIn
        String chiave

        if (mappaOut) {
            mappaOut = new LinkedHashMap()
            ParBio?.each {
                chiave = it.tag
                if (mappaIn[chiave] && !mappaIn[chiave].equals('null')) {
                    mappaOut.put(chiave, mappaIn[chiave])
                } else {
                    if (it.semplice) {
                        mappaOut.put(chiave, '')
                    }// fine del blocco if
                }// fine del blocco if-else
            } // fine del ciclo each
        }// fine del blocco if

        return mappaOut
    }// fine del metodo

    public static String getListaRec(ArrayList<Integer> listaRecordsModificati) {
        String stringa = ''
        String tagIni = '('
        String tagEnd = ')'
        String tagSep = ','

        if (listaRecordsModificati) {
            stringa += tagIni
            listaRecordsModificati.each {
                stringa += it
                stringa += tagSep
            } // fine del ciclo each
            stringa = LibTesto.levaCoda(stringa, tagSep)
            stringa += tagEnd
        }// fine del blocco if

        return stringa
    } // fine del metodo

    /**
     * Restituisce il summary dal parametro summary e dal parametro version
     */
    public static getSummary() {
        return LibPref.getString(SUMMARY)
    }// fine della closure

    public static boolean contiene(ArrayList lista, def elemento) {
        // variabili e costanti locali di lavoro
        boolean contiene = false

        if (lista && elemento) {
            lista.each {
                if (it == elemento) {
                    contiene = true
                }// fine del blocco if
            } // fine del ciclo each
        }// fine del blocco if

        // valore di ritorno
        return contiene
    }// fine del metodo

    public static String voceAggiornataVecchia() {
        String messaggio
        String oldDataTxt = ''
        ArrayList listaTimestamp
        Timestamp oldStamp
        def oldData

        listaTimestamp = BioWiki.executeQuery('select letturaWiki from BioWiki order by letturaWiki asc')
        if (listaTimestamp && listaTimestamp.size() > 0) {
            oldStamp = (Timestamp) listaTimestamp[0]
            oldData = LibTime.creaData(oldStamp)
            oldDataTxt = LibTime.getGioMeseAnno(oldData)
        }// fine del blocco if
        messaggio = "La voce più vecchia non aggiornata è del ${oldDataTxt}"

        return messaggio
    }// fine del metodo

    public static String voceElaborataVecchia() {
        String messaggio
        String oldDataTxt = ''
        ArrayList listaTimestamp
        Timestamp oldStamp
        def oldData
        def nonElaborate

        nonElaborate = BioWiki.executeQuery('SELECT count(*) FROM BioWiki where elaborata=false')
        if (nonElaborate && nonElaborate instanceof ArrayList && nonElaborate.size() > 0) {
            nonElaborate = nonElaborate[0]
            nonElaborate = LibTesto.formatNum(nonElaborate)
        }// fine del blocco if

        listaTimestamp = BioWiki.executeQuery('select letturaWiki from BioWiki where elaborata=false order by letturaWiki asc')
        if (listaTimestamp && listaTimestamp.size() > 0) {
            oldStamp = (Timestamp) listaTimestamp[0]
            oldData = LibTime.creaData(oldStamp)
            oldDataTxt = LibTime.getGioMeseAnno(oldData)
        }// fine del blocco if

        messaggio = "Ci sono ${nonElaborate} voci non elaborate. La più vecchia è del ${oldDataTxt}"

        return messaggio
    }// fine del metodo

    /**
     * Aggiunge il tag ref in testa e coda della stringa.
     * Aggiunge SOLO se gia non esistono
     *
     * @param stringaIn testo da elaborare
     * @return stringa con ref iniziale e finale aggiunto
     */
    public static String setRef(String stringaIn) {
        String stringaOut = ""
        String tagIni = "<ref>"
        String tagEnd = "</ref>"

        try { // prova ad eseguire il codice
            stringaOut = stringaIn
            if (!stringaIn.startsWith(tagIni)) {
                stringaOut = tagIni + stringaIn
            }// fine del blocco if

            if (!stringaIn.endsWith(tagEnd)) {
                stringaOut += tagEnd
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringaOut
    }// fine del metodo

    /**
     * Divide le liste attività/nazionalità per paragrafi di gruppi nazionalità/attività
     * Crea una lista di wrapper
     *
     * @param bioLista
     * @return lista di liste di wrapper
     */
    public static divideParagrafi(BioLista bioLista) {
        // variabili e costanti locali di lavoro
        ArrayList liste = new ArrayList()
        ArrayList listaNomiParagrafi = new ArrayList()
        ArrayList listaWrapper = bioLista.getListaWrapper()
        ArrayList listaParagrafo
        String nomeParagrafo
        BioLista bioListaPar

        // crea una lista di attività/nazionalità utilizzate
        listaNomiParagrafi = getListaChiavi(bioLista)

        // spazzola la lista di attività/nazionalità utilizzate
        listaNomiParagrafi?.each {
            nomeParagrafo = it
            nomeParagrafo = nomeParagrafo.trim()

            listaParagrafo = getListaDidascalieParagrafo(bioLista, nomeParagrafo, listaWrapper)

            if (listaParagrafo && listaParagrafo.size() > 0) {
                if (nomeParagrafo == '') {
                    nomeParagrafo = BioLista.PUNTI
                }// fine del blocco if

                bioListaPar = new BioListaPar(nomeParagrafo, listaParagrafo, bioLista)
                liste.add(bioListaPar)
            }// fine del blocco if
        }// fine di each

        // valore di ritorno
        return liste
    }// fine del metodo

    /**
     * Ordina una lista di oggetti secondo il campo indicato
     *
     * @param lista non ordinata
     * @param nomeCampo di ordinamento
     * @return lista ordinata
     */
    public static ordinaLista(ArrayList lista, String nomeCampo) {
        // variabili e costanti locali di lavoro
        ArrayList listaOrdinata = null
        ArrayList listaChiavi
        def chiave

        if (lista && lista.size() > 0 && nomeCampo) {
            listaOrdinata = new ArrayList()
            listaChiavi = LibBio.listaChiavi(lista, nomeCampo)

            // prima gli anni zero (se ce ne sono)
            listaChiavi.each {
                chiave = it
                lista.each {
                    if (chiave == it."${nomeCampo}") {
                        if (!listaOrdinata.contains(it)) {
                            listaOrdinata.add(it)
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine di each
            }// fine di each
        }// fine del blocco if

        // valore di ritorno
        return listaOrdinata
    }// fine del metodo

    /**
     * Recupera una lista di nomi/chiave per i paragrafi
     *
     * @param bioLista
     * @return lista di nomi di paragrafi
     */
    public static getListaChiavi(BioLista bioLista) {
        // variabili e costanti locali di lavoro
        ArrayList listaNomiParagrafi = new ArrayList()
        ArrayList listaWrapper
        String paragrafo
        ArrayList paragrafi

        // crea una lista di nazionalità utilizzate
        if (bioLista) {

            listaWrapper = bioLista.getListaWrapper()
            paragrafo = bioLista.campoParagrafo
            paragrafi = bioLista.getCampiParagrafi()

            if (paragrafi && BioLista.TRIPLA_ATTIVITA) {
                listaNomiParagrafi = listaChiavi(listaWrapper, paragrafi)
            } else {
                listaNomiParagrafi = listaChiavi(listaWrapper, paragrafo)
            }// fine del blocco if-else
        }// fine del blocco if

        // valore di ritorno
        return listaNomiParagrafi
    }// fine del metodo

    /**
     * Crea una lista di attività/nazionalità per ogni paragrafi
     *
     * @param nomeParagrafo
     * @return listaWrapper completa
     */
    public static getListaDidascalieParagrafo(bioLista, nomeParagrafo, listaWrapper) {
        // variabili e costanti locali di lavoro
        ArrayList listaParagrafo = null

        // controllo di congruità
        if (bioLista && listaWrapper && listaWrapper.size() > 0) {
            if (nomeParagrafo == '') {
                listaParagrafo = getListaDidascalieParagrafoVuoto(bioLista, listaWrapper)
            } else {
                listaParagrafo = getListaDidascalieParagrafoPieno(bioLista, nomeParagrafo, listaWrapper)
            }// fine del blocco if-else
        }// fine del blocco if

        // valore di ritorno
        return listaParagrafo
    }// fine del metodo

    /**
     * Crea una lista di attività/nazionalità per ogni paragrafi vuoto
     *
     * @return listaWrapper completa
     */
    public static getListaDidascalieParagrafoVuoto(bioLista, listaWrapper) {
        // variabili e costanti locali di lavoro
        ArrayList listaParagrafo = new ArrayList()
        String paragrafo = bioLista.campoParagrafo
        ArrayList paragrafi = bioLista.getCampiParagrafi()
        String nomeWrapper = ''
        String nome2Wrapper = ''
        String nome3Wrapper = ''

        // controllo di congruità
        if (bioLista && listaWrapper && listaWrapper.size() > 0) {
            listaWrapper.each {
                nomeWrapper = ''
                nome2Wrapper = ''
                nome3Wrapper = ''
                try { // prova ad eseguire il codice
                    if (paragrafi) {
                        nomeWrapper = it."${paragrafi[0]}"
                        nome2Wrapper = it."${paragrafi[1]}"
                        nome3Wrapper = it."${paragrafi[2]}"
                    } else {
                        nomeWrapper = it."${paragrafo}"
                        nome2Wrapper = nomeWrapper
                        nome3Wrapper = nomeWrapper
                    }// fine del blocco if-else
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch
                nomeWrapper = nomeWrapper.trim()
                nome2Wrapper = nome2Wrapper.trim()
                nome3Wrapper = nome3Wrapper.trim()

                if (nomeWrapper.equals('') && nome2Wrapper.equals('') && nome3Wrapper.equals('')) {
                    if (!listaParagrafo.contains(it)) {
                        listaParagrafo.add(it)
                    }// fine del blocco if
                }// fine del blocco if-else
            }// fine di each
        }// fine del blocco if

        // valore di ritorno
        return listaParagrafo
    } // fine della closure

    /**
     * Crea una lista di attività/nazionalità per ogni paragrafi
     *
     * @param nomeParagrafo
     * @return listaWrapper completa
     */
    public static getListaDidascalieParagrafoPieno(bioLista, nomeParagrafo, listaWrapper) {
        // variabili e costanti locali di lavoro
        ArrayList listaParagrafo = new ArrayList()
        ArrayList liste = new ArrayList()
        ArrayList listaNomi = new ArrayList()
        String paragrafo = bioLista.campoParagrafo
        ArrayList paragrafi = bioLista.getCampiParagrafi()
        ArrayList listaTmp
        String nomeWrapper = ''
        String nome2Wrapper = ''
        String nome3Wrapper = ''
        BioLista bioListaPar
        boolean attivitaVuota
        boolean daInserire

        // controllo di congruità
        if (bioLista && nomeParagrafo && listaWrapper && listaWrapper.size() > 0) {
            listaWrapper.each {
                daInserire = false
                nomeWrapper = ''
                nome2Wrapper = ''
                nome3Wrapper = ''
                try { // prova ad eseguire il codice
                    if (paragrafi) {
                        nomeWrapper = it."${paragrafi[0]}"
                        nome2Wrapper = it."${paragrafi[1]}"
                        nome3Wrapper = it."${paragrafi[2]}"
                    } else {
                        nomeWrapper = it."${paragrafo}"
                        nome2Wrapper = nomeWrapper
                        nome3Wrapper = nomeWrapper
                    }// fine del blocco if-else
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch
                nomeWrapper = nomeWrapper.trim()
                nome2Wrapper = nome2Wrapper.trim()
                nome3Wrapper = nome3Wrapper.trim()

                if (BioLista.TRIPLA_ATTIVITA) {
                    if (nomeWrapper.equals(nomeParagrafo) || nome2Wrapper.equals(nomeParagrafo) || nome3Wrapper.equals(nomeParagrafo)) {
                        daInserire = true
                    }// fine del blocco if-else
                } else {
                    if (nomeWrapper.equals(nomeParagrafo)) {
                        daInserire = true
                    }// fine del blocco if-else
                }// fine del blocco if-else

                if (daInserire) {
                    if (!listaParagrafo.contains(it)) {
                        listaParagrafo.add(it)
                    }// fine del blocco if
                }// fine del blocco if-else
            }// fine di each

        }// fine del blocco if

        // valore di ritorno
        return listaParagrafo
    } // fine della closure

    /**
     * Recupera una lista di oggetti del campo indicato
     *
     * @param lista
     * @param nomeCampo da recuperare
     * @return lista ordinata
     */
    public static listaChiavi(ArrayList lista, String nomeCampo) {
        // variabili e costanti locali di lavoro
        ArrayList listaChiavi = null
        boolean continua = false
        def chiave

        // controllo di congruità
        if (lista && lista.size() > 0 && nomeCampo) {
            continua = true
        }// fine del blocco if

        if (continua) {
            listaChiavi = new ArrayList()

            // lista delle chiavi
            try { // prova ad eseguire il codice
                lista.each {
                    chiave = it."${nomeCampo}"
                    if (!listaChiavi.contains(chiave)) {
                        listaChiavi.add(chiave)
                    }// fine del blocco if
                }// fine di each
            } catch (Exception unErrore) { // intercetta l'errore
                log.error "listaChiavi: non esiste il campo $nomeCampo"
                continua = false
            }// fine del blocco try-catch
        }// fine del blocco if

        // ordina
        if (continua) {
            listaChiavi.sort()
        }// fine del blocco if

        // valore di ritorno
        return listaChiavi
    }// fine del metodo

    /**
     * Recupera una lista di oggetti dei campi indicato
     *
     * @param lista
     * @param nomiCampo da recuperare
     * @return lista ordinata
     */
    public static listaChiavi(ArrayList lista, ArrayList nomiCampo) {
        // variabili e costanti locali di lavoro
        ArrayList listaChiavi = null
        boolean continua = false
        ArrayList listaTmp

        // controllo di congruità
        if (lista && lista.size() > 0 && nomiCampo && nomiCampo.size() > 0) {
            continua = true
        }// fine del blocco if

        if (continua) {
            listaChiavi = new ArrayList()
            if (nomiCampo.size() == 3) {
                listaTmp = LibBio.listaChiavi(lista, nomiCampo.get(0))
                listaTmp.each {
                    if (!listaChiavi.contains(it)) {
                        listaChiavi.add(it)
                    }// fine del blocco if
                }// fine di each
                listaTmp = LibBio.listaChiavi(lista, nomiCampo.get(1))
                listaTmp.each {
                    if (listaChiavi.contains(it) || it.equals('')) {
                    } else {
                        listaChiavi.add(it)
                    }// fine del blocco if-else
                }// fine di each
                listaTmp = LibBio.listaChiavi(lista, nomiCampo.get(2))
                listaTmp.each {
                    if (listaChiavi.contains(it) || it.equals('')) {
                    } else {
                        listaChiavi.add(it)
                    }// fine del blocco if-else
                }// fine di each
            } else {
                nomiCampo.each {
                    listaTmp = LibBio.listaChiavi(lista, it)
                    listaTmp.each {
                        if (!listaChiavi.contains(it)) {
                            listaChiavi.add(it)
                        }// fine del blocco if
                    }// fine di each
                }// fine di each
            }// fine del blocco if-else
        }// fine del blocco if

        // ordina
        if (continua) {
            listaChiavi.sort()
        }// fine del blocco if

        // valore di ritorno
        return listaChiavi
    }// fine del metodo

    /**
     * Divide le liste attività/nazionalità per paragrafi sul primo carattere
     * Crea una lista di wrapper
     *
     * @param classe per il costruttore di BioListaPar
     * @param nomeCampo di ordinamento
     * @return lista di liste di wrapper
     */
    public static divideCarattere(BioLista bioLista) {
        // variabili e costanti locali di lavoro
        ArrayList liste = new ArrayList()
        ArrayList listaParagrafi = new ArrayList()
        ArrayList listaWrapper = bioLista.getListaWrapper()
        String paragrafo = bioLista.campoParagrafo
        ArrayList listaTmp
        String nomeLista
        String ordine
        BioLista bioListaPar

        // crea una lista di paragrafi
        listaParagrafi = getListaParagrafi(listaWrapper, 'ordineAlfabetico')

        // spazzola la lista dei paragrafi
        if (listaParagrafi) {
            listaParagrafi.each {
                nomeLista = it
                listaTmp = new ArrayList()
                listaWrapper.each {
                    ordine = it.ordineAlfabetico
                    if (ordine) {
                        ordine = ordine.substring(0, 1)
                        if (ordine == nomeLista) {
                            if (!listaTmp.contains(it)) {
                                listaTmp.add(it)
                            }// fine del blocco if
                        }// fine del blocco if
                    } else {
                        log.warn "divideCarattere: ${it}"
                    }// fine del blocco if-else
                }// fine di each

                bioListaPar = new BioListaFin(nomeLista, listaTmp, bioLista)
                if (bioListaPar && bioListaPar.getListaWrapper() && bioListaPar.getListaWrapper().size() > 0) {
                    liste.add(bioListaPar)
                }// fine del blocco if
            }// fine di each
        }// fine del blocco if

        // valore di ritorno
        return liste
    }// fine del metodo

    /**
     * Crea una lista di paragrafi (prima lettera delle voci) usati
     *
     * @param listaWrapper
     * @return lista di nomi paragrafi
     */
    public static getListaParagrafi(ArrayList listaWrapper, String nomeCampo) {
        // variabili e costanti locali di lavoro
        ArrayList listaParagrafi = new ArrayList()
        String ordine
        String carIniziale

        // crea una lista di lettere utilizzate
        if (listaWrapper) {
            listaWrapper.each {
                ordine = it."${nomeCampo}"
                if (ordine) {
                    carIniziale = ordine.substring(0, 1)
                    carIniziale = carIniziale.toUpperCase()
                    if (!listaParagrafi.contains(carIniziale)) {
                        listaParagrafi.add(carIniziale)
                    }// fine del blocco if
                } else {
                    log.warn "getListaParagrafi: ${it}"
                }// fine del blocco if-else
            }// fine di each
        }// fine del blocco if

        // ordina
        listaParagrafi.sort()

        // valore di ritorno
        return listaParagrafi
    }// fine del metodo

    /**
     * Aggiorna la pagina solo se è significativamente diversa,
     * al di la della prima riga con il richiamo al template e che contiene la data
     *
     * @param titolo della pagina da controllare
     * @param testoNew eventualmente da registrare
     * @param summary eventualmente da registrare
     */
    public static caricaPaginaDiversa(String titolo, String testoNew, String summary, boolean biografia) {
        // variabili e costanti locali di lavoro
        Risultato risultato = Risultato.nonElaborata
        boolean continua = false
        Pagina pagina
        String testoOld = ''
        long adesso
        long attesa

        // controllo di congruita
        if (titolo && testoNew) {
            continua = true
        }// fine del blocco if

        if (continua) {
            try { // prova ad eseguire il codice
                pagina = new Pagina(titolo)
                testoOld = pagina.getContenuto()
                if (LibBio.isDiversa(testoOld, testoNew, biografia)) {
                    if (attesa > 0) {
                        Thread.currentThread().sleep(attesa)
                    }// fine del blocco if
                    risultato = pagina.scrive(testoNew, summary)
                } else {
                    risultato = Risultato.allineata
                }// fine del blocco if-else
            } catch (Exception unErrore) { // intercetta l'errore
                // log.error unErrore
            }// fine del blocco try-catch
        }// fine del blocco if

        // valore di ritorno
        return risultato
    }// fine del metodo

    /**
     * Controlla che la voce sia effettivamente diversa,
     * al di la della prima riga con il richiamo al template e che contiene la data
     *
     * @param testoOld esistente sul server wiki
     * @param testoNew eventualmente da registrare
     * @return vero se i testi sono differenti (al la del primo template)
     */
    public static isDiversa(String testoOld, String testoNew, boolean biografia) {
        // variabili e costanti locali di lavoro
        boolean diversa = true
        boolean continua = false
        String tag = '\\{\\{.+\\}\\}'
        Pattern pattern = Pattern.compile(tag)
        Matcher matcher
        int pos

        // controllo di congruita
        if (testoOld && testoNew) {
            continua = true
        }// fine del blocco if

        if (continua) {
            if (biografia) {
                diversa == (!testoNew.equals(testoOld))
                continua = false
            }// fine del blocco if
        }// fine del blocco if

        if (continua) {
            matcher = pattern.matcher(testoOld)
            if (matcher.find()) {
                pos = matcher.end()
                testoOld = testoOld.substring(pos)
            } else {
                continua = false
            }// fine del blocco if-else

            matcher = pattern.matcher(testoNew)
            if (matcher.find()) {
                pos = matcher.end()
                testoNew = testoNew.substring(pos)
            } else {
                continua = false
            }// fine del blocco if-else
        }// fine del blocco if

        if (continua) {
            if (testoNew.equals(testoOld)) {
                diversa = false
            } else {
                diversa = true
            }// fine del blocco if-else
        }// fine del blocco if

        // valore di ritorno
        return diversa
    } // fine della closure

} // fine della classe
