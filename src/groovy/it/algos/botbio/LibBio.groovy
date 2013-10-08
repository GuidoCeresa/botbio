package it.algos.botbio

import groovy.util.logging.Log4j
import it.algos.algoslib.Lib
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

                    valore = testoTemplate.substring(lista.get(k - 1), lista.get(k))
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

                        chiave = mappaTmp.get(lista.get(k - 1))
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
    public static Bio clonaBio(Bio bioOriginale) {
        // variabili e costanti locali di lavoro
        Bio bioCopia = null
        String nomeCampo
        def valCampo
        int id

        if (bioOriginale) {
            if (bioOriginale.id) {
                id = bioOriginale.id
            }// fine del blocco if

            if (id) {
                bioCopia = Bio.findById(id)
            }// fine del blocco if

            if (!bioCopia) {
                bioCopia = new Bio()
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
    public static String correggeParametroDidascalia(Bio bio, String oldValue) {
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


} // fine della classe
