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

import it.algos.algoslib.LibTime
import it.algos.algospref.Preferenze
import it.algos.algoswiki.Edit
import it.algos.algoswiki.Risultato
import it.algos.algoswiki.WikiLib

class BioGrailsService {

    def grailsApplication

    public static String aCapo = '\n'
    public static String ast = '*'

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti i giorni modificati
    //--elabora e crea tutti gli anni modificati
    //--elabora e crea tutte le attività
    //--elabora e crea tutte le nazionalità
    def upload() {
        uploadGiorni()
        uploadAnni()
        uploadAttivita()
        uploadNazionalita()
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti i giorni modificati
    def uploadGiorni() {
        uploadGiorniNascita()
        uploadGiorniMorte()
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti i giorni di nascita modificati
    def uploadGiorniNascita() {
        ArrayList listaGiorniModificati

        listaGiorniModificati = Giorno.findAllBySporcoNato(true)
        listaGiorniModificati?.each {
            uploadGiornoNascita((Giorno) it)
        } // fine del ciclo each
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea la lista del giorno di nascita
    def uploadGiornoNascita(Giorno giorno) {
        ArrayList listaPersone
        String query
        long giornoId
        int numPersone
        String tagNatiMorti = 'Nati'
        String tagNateMorte = 'nate'

        if (giorno) {
            giornoId = giorno.id
        }// fine del blocco if

        if (giornoId) {
            query = "select didascaliaGiornoNato from BioGrails where giornoMeseNascitaLink=${giornoId} order by annoNascitaLink"
            listaPersone = BioGrails.executeQuery(query)
            if (listaPersone) {
                numPersone = listaPersone.size()
                this.caricaPagina(giorno, listaPersone, numPersone, tagNatiMorti, tagNateMorte)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti i giorni di morte modificati
    def uploadGiorniMorte() {
        ArrayList listaGiorniModificati

        listaGiorniModificati = Giorno.findAllBySporcoMorto(true)
        listaGiorniModificati?.each {
            uploadGiornoMorte((Giorno) it)
        } // fine del ciclo each
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea la lista del giorno di morte
    def uploadGiornoMorte(Giorno giorno) {
        ArrayList listaPersone
        String query
        long giornoId
        int numPersone
        String tagNatiMorti = 'Morti'
        String tagNateMorte = 'morte'

        if (giorno) {
            giornoId = giorno.id
        }// fine del blocco if

        if (giornoId) {
            query = "select didascaliaGiornoMorto from BioGrails where giornoMeseMorteLink=${giornoId} order by annoMorteLink"
            listaPersone = BioGrails.executeQuery(query)
            if (listaPersone) {
                numPersone = listaPersone.size()
                this.caricaPagina(giorno, listaPersone, numPersone, tagNatiMorti, tagNateMorte)
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti gli anni modificati
    def uploadAnni() {
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutte le attività
    def uploadAttivita() {
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutte le nazionalità
    def uploadNazionalita() {
    } // fine del metodo

    /**
     * Carica su wiki la pagina
     */
    private boolean caricaPagina(Giorno giorno, ArrayList lista, int numPersone, String tagNatiMorti, String tagNateMorte) {
        // variabili e costanti locali di lavoro
        boolean registrata = false
        String titolo = ''
        String testo = ''
        String summary = LibBio.getSummary()
        def risultato
        boolean debug = Preferenze.getBool((String) grailsApplication.config.debug)

        // controllo di congruità
        if (giorno && lista && numPersone) {
            titolo = getTitolo(giorno, tagNatiMorti)
            testo = getTesto(giorno, lista, numPersone, tagNatiMorti, tagNateMorte)
        }// fine del blocco if

        if (titolo && testo) {
            if (debug) {
                titolo = 'Utente:Gac/Sandbox4280'
            }// fine del blocco if
            risultato = new Edit(titolo, testo, summary)

            if ((risultato == Risultato.registrata) || (risultato == Risultato.allineata)) {
                registrata = true
            } else {
                log.warn "La pagina $titolo è $risultato"
            }// fine del blocco if-else
        }// fine del blocco if

        // valore di ritorno
        return registrata
    }// fine del metodo

    /**
     * Costruisce il titolo della pagina
     */
    private static String getTitolo(Giorno giorno, String tagNatiMorti) {
        // variabili e costanti locali di lavoro
        String titolo = ''
        String spazio = ' '
        String articolo = 'il'
        String articoloBis = "l'"

        // controllo di congruità
        if (giorno && tagNatiMorti) {
            titolo = giorno.titolo
            if (titolo.startsWith('8') || titolo.startsWith('11')) {
                titolo = tagNatiMorti + spazio + articoloBis + titolo
            } else {
                titolo = tagNatiMorti + spazio + articolo + spazio + titolo
            }// fine del blocco if-else
        }// fine del blocco if

        // valore di ritorno
        return titolo
    }// fine del metodo

    /**
     * Costruisce il testo della pagina
     */
    private static String getTesto(Giorno giorno, ArrayList lista, int numPersone, String tagNatiMorti, String tagNateMorte) {
        // variabili e costanti locali di lavoro
        String testo = ''

        if (giorno && lista && numPersone) {
            testo = getTestoTop(giorno, numPersone)
            testo += getTestoBody(lista, tagNateMorte)
            testo += aCapo
            testo += getTestoBottom(giorno, tagNatiMorti)
        }// fine del blocco if

        // valore di ritorno
        return testo.trim()
    }// fine del metodo

    /**
     * Costruisce il testo iniziale della pagina
     */
    private static String getTestoTop(Giorno giorno, int numPersone) {
        // variabili e costanti locali di lavoro
        String testo = ''
        String torna
        String dataCorrente

        // controllo di congruità
        if (giorno && numPersone) {
            dataCorrente = LibTime.getGioMeseAnno(new Date())
            torna = giorno.titolo

            testo = "<noinclude>"
            testo += aCapo
            testo += "{{ListaBio"
            testo += "|bio="
            testo += numPersone
            testo += "|data="
            testo += dataCorrente.trim()
            testo += "}}"
            testo += aCapo
            testo += "{{torna a|$torna}}"
            testo += aCapo
            testo += "</noinclude>"
        }// fine del blocco if

        // valore di ritorno
        return testo.trim()
    }// fine del metodo

    /**
     * Costruisce il testo variabile della pagina
     * I giorni hanno sempre un numero notevole di persone
     * quindi vanno sempre col cassetto e su due colonne
     *
     * @param lista degli elementi
     * @return testo con un ritorno a capo iniziale ed uno finale
     */
    private static String getTestoBody(ArrayList lista, String tagNateMorte) {
        // variabili e costanti locali di lavoro
        String testoBody
        boolean usaCassetto = true
        boolean usaDueColonne = true
        String testo = ''
        String titolo

        // testo della lista
        if (lista) {
            lista.each {
                testo += ast
                testo += it
                testo += aCapo
            }//fine di each
            testo = testo.trim()
        }// fine del blocco if

        // eventuale doppia colonna
        if (usaDueColonne) {
            testo = WikiLib.listaDueColonne(testo)
        }// fine del blocco if

        // eventuale cassetto
        if (usaCassetto) {
            titolo = "Lista di persone $tagNateMorte in questo giorno"
            testoBody = WikiLib.cassettoInclude(testo, titolo)
        } else {
            testoBody = testo
        }// fine del blocco if-else

        // valore di ritorno
        return testoBody.trim()
    }// fine del metodo

    /**
     * Costruisce il testo finale della pagina
     */
    private static String getTestoBottom(Giorno giorno, String tagNatiMorti) {
        // variabili e costanti locali di lavoro
        String testo = ''
        String prog
        String titolo

        // controllo di congruità
        if (giorno) {
            prog = getProgTre(giorno)
            titolo = getTitolo(giorno, tagNatiMorti)
            testo += "<noinclude>"
            testo += aCapo
            testo += '{{Portale|biografie}}'
            testo += aCapo
            testo += "[[Categoria:Liste di nati per giorno| $prog]]"
            testo += aCapo
            testo += "[[Categoria:$titolo| ]]"
            testo += aCapo
            testo += "</noinclude>"
        }// fine del blocco if

        // valore di ritorno
        return testo.trim()
    }// fine del metodo

    /**
     * Stringa progressivo del giorno nell'anno
     * Tre cifre (per omogeneità nell'ordinamento della categoria)
     *
     * Utilizzo l'anno bisestile per essere sicuro di prenderli comunque tutti
     */
    private static String getProgTre(Giorno giorno) {
        // variabili e costanti locali di lavoro
        String progTre = ''
        int cifre = 3
        int prog
        String tagIni = '0'

        if (giorno) {
            prog = giorno.bisestile
            progTre = prog + ''
            while (progTre.length() < cifre) {
                progTre = tagIni + progTre
            }// fine di while
        }// fine del blocco if

        // valore di ritorno
        return progTre
    }// fine del metodo

} // fine della service classe
