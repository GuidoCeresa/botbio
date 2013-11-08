package it.algos.botbio

import groovy.util.logging.Log4j
import it.algos.algoslib.Lib
import it.algos.algoslib.LibTesto
import it.algos.algospref.Preferenze
import it.algos.algoswiki.QueryInfoCat
import it.algos.algoswiki.WikiLib

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
    public static final String MAX_DOWNLOAD = 'maxDownload'
    public static final String USA_CRONO_DOWNLOAD = 'usaCronoDownload'

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
    public static String gestVoci(def logService, boolean debug, long durata, int aggiunte, int modificate) {
        return gestVoci(logService, debug, durata, aggiunte, modificate, BioWiki.count())
    }// fine del metodo

    /**
     * Registra le voci gestite
     *
     * @param debug log di controllo
     */
    public static String gestVoci(def logService, boolean debug, long durata, int aggiunte, int modificate, int vociTotali) {
        String avviso = ''
        int vociCat
        String aggiunteTxt
        String modificateTxt
        String numVociTotaliTxt
        String percentuale
        QueryInfoCat query
        String durataSecondiTxt
        String durataMinutiTxt
        long tempo
        String tempoTxt
        int durataSec
        int durataMin
        int tempoInt

        query = new QueryInfoCat('BioBot')
        vociCat = query.getSize()
        percentuale = LibTesto.formatPercentuale(vociTotali, vociCat)
        aggiunteTxt = LibTesto.formatNum(aggiunte)
        modificateTxt = LibTesto.formatNum(modificate)
        numVociTotaliTxt = LibTesto.formatNum(vociTotali)
        tempo = durata / aggiunte
        durata = durata / 1000
        durataSec = durata.intValue()
        durataMin=durataSec/60
        durataSecondiTxt = LibTesto.formatNum(durataSec)
        durataMinutiTxt = LibTesto.formatNum(durataMin)
        tempo = tempo / 100
        tempoInt = tempo.intValue()
        tempoTxt = LibTesto.formatNum(tempoInt)
        tempoTxt = tempoTxt.substring(0, tempoTxt.length() - 1) + ',' + tempoTxt.substring(tempoTxt.length() - 1)
        if (tempoTxt.startsWith(',')) {
            tempoTxt = '0' + tempoTxt
        }// fine del blocco if
        avviso += "Ciclo di ${durataMinutiTxt} min (${tempoTxt} sec/voce). "
        avviso += "Aggiunte: ${aggiunteTxt}. Modificate: ${modificateTxt}. "
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
        // variabili e costanti locali di lavoro
        String ritorno
        String summary = '[[Utente:Biobot#9|Biobot '
        String versioneCorrente = Preferenze.getStr('version')
        String ultimaVersione = '9'

        if (!versioneCorrente) {
            versioneCorrente = ultimaVersione
        }// fine del blocco if
        ritorno = summary + versioneCorrente + ']]'

        // valore di ritorno
        return ritorno
    }// fine della closure

} // fine della classe
