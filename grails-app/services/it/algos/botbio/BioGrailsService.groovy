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

import it.algos.algoslib.LibTesto
import it.algos.algoslib.LibTime
import it.algos.algospref.Preferenze
import it.algos.algoswiki.Risultato
import it.algos.algoswiki.WikiLib

class BioGrailsService {

    def grailsApplication

    public static String aCapo = '\n'
    public static String ast = '*'

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti i giorni modificati
    //--elabora e crea tutti gli anni modificati
    def uploadAll() {
        long inizio = System.currentTimeMillis()
        long fine
        long durata

        def giorniNati = Giorno.countBySporcoNato(true)
        def giorniMorti = Giorno.countBySporcoMorto(true)
        def anniNati = Anno.countBySporcoNato(true)
        def anniMorti = Anno.countBySporcoMorto(true)
        log.info("Inizio ciclo upload: giorniNati=${giorniNati}, giorniMorti=${giorniMorti}, anniNati=${anniNati}, anniMorti=${anniMorti}")

        uploadGiorni()
        uploadAnni()
        fine = System.currentTimeMillis()
        durata = fine - inizio
        durata = durata / 1000
        durata = durata / 1000
        log.info("Ciclo upload in ${durata} min")

//        uploadAttivita()
//        uploadNazionalita()
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
        ArrayList listaPersone = new ArrayList()
        ArrayList listaPersoneSenzaAnno
        ArrayList listaPersoneConAnno
        String querySenza
        String queryAnno
        long giornoId
        int numPersone
        String tagNatiMorti = 'Nati'
        String tagNateMorte = 'nate'
        boolean registrata = false

        if (giorno) {
            giornoId = giorno.id
        }// fine del blocco if

        if (giornoId) {
            querySenza = "select didascaliaGiornoNato from BioGrails where (giornoMeseNascitaLink=${giornoId} and annoNascitaLink is null) order by cognome asc"
            queryAnno = "select didascaliaGiornoNato from BioGrails where (giornoMeseNascitaLink=${giornoId} and annoNascitaLink>0) order by annoNascitaLink,cognome asc"
            listaPersoneSenzaAnno = BioGrails.executeQuery(querySenza)
            listaPersoneConAnno = BioGrails.executeQuery(queryAnno)

            listaPersoneSenzaAnno?.each {
                if (it) {
                    listaPersone.add(it)
                }// fine del blocco if
            } // fine del ciclo each
            listaPersoneConAnno?.each {
                if (it) {
                    listaPersone.add(it)
                }// fine del blocco if
            } // fine del ciclo each
            if (listaPersone) {
                numPersone = listaPersone.size()
                registrata = this.caricaPagina(giorno, listaPersone, numPersone, tagNatiMorti, tagNateMorte)
            }// fine del blocco if
        }// fine del blocco if

//        if (giorno && registrata) {
        if (giorno) {
            giorno.sporcoNato = false
            giorno.save(flush: true)
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
        ArrayList listaPersone = new ArrayList()
        ArrayList listaPersoneSenzaAnno
        ArrayList listaPersoneConAnno
        String querySenza
        String queryAnno
        long giornoId
        int numPersone
        String tagNatiMorti = 'Morti'
        String tagNateMorte = 'morte'
        boolean registrata = false

        if (giorno) {
            giornoId = giorno.id
        }// fine del blocco if

        if (giornoId) {
            querySenza = "select didascaliaGiornoMorto from BioGrails where (giornoMeseMorteLink=${giornoId} and annoMorteLink is null) order by cognome asc"
            queryAnno = "select didascaliaGiornoMorto from BioGrails where (giornoMeseMorteLink=${giornoId} and annoMorteLink>0) order by annoMorteLink, cognome asc"
            listaPersoneSenzaAnno = BioGrails.executeQuery(querySenza)
            listaPersoneConAnno = BioGrails.executeQuery(queryAnno)
            listaPersoneSenzaAnno?.each {
                if (it) {
                    listaPersone.add(it)
                }// fine del blocco if
            } // fine del ciclo each
            listaPersoneConAnno?.each {
                if (it) {
                    listaPersone.add(it)
                }// fine del blocco if
            } // fine del ciclo each
            if (listaPersone) {
                numPersone = listaPersone.size()
                registrata = this.caricaPagina(giorno, listaPersone, numPersone, tagNatiMorti, tagNateMorte)
            }// fine del blocco if
        }// fine del blocco if

//        if (giorno && registrata) {
        if (giorno) {
            giorno.sporcoMorto = false
            giorno.save(flush: true)
        }// fine del blocco if
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti gli anni modificati
    def uploadAnni() {
        uploadAnniNascita()
        uploadAnniMorte()
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti gli anni di nascita modificati
    def uploadAnniNascita() {
        ArrayList listaAnniModificati

        listaAnniModificati = Anno.findAllBySporcoNato(true)
        listaAnniModificati?.each {
            uploadAnnoNascita((Anno) it)
        } // fine del ciclo each
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea la lista dell'anno di nascita
    def uploadAnnoNascita(Anno anno) {
        ArrayList listaPersone = new ArrayList()
        ArrayList listaPersoneSenzaGiorno
        ArrayList listaPersoneConGiorno
        String querySenza
        String queryGiorno
        long annoId
        int numPersone
        String tagNatiMorti = 'Nati'
        String tagNateMorte = 'nate'

        if (anno) {
            annoId = anno.id
        }// fine del blocco if

        if (annoId) {
            querySenza = "select didascaliaAnnoNato from BioGrails where (annoNascitaLink=${annoId} and giornoMeseNascitaLink is null) order by cognome asc"
            queryGiorno = "select didascaliaAnnoNato from BioGrails where (annoNascitaLink=${annoId} and giornoMeseNascitaLink>0) order by giornoMeseNascitaLink,cognome asc"
            listaPersoneSenzaGiorno = BioGrails.executeQuery(querySenza)
            listaPersoneConGiorno = BioGrails.executeQuery(queryGiorno)
            listaPersoneSenzaGiorno?.each {
                if (it) {
                    listaPersone.add(it)
                }// fine del blocco if
            } // fine del ciclo each
            listaPersoneConGiorno?.each {
                if (it) {
                    listaPersone.add(it)
                }// fine del blocco if
            } // fine del ciclo each
            if (listaPersone) {
                numPersone = listaPersone.size()
                this.caricaPagina(anno, listaPersone, numPersone, tagNatiMorti, tagNateMorte)
            }// fine del blocco if
        }// fine del blocco if

        if (anno) {
            anno.sporcoNato = false
            anno.save(flush: true)
        }// fine del blocco if
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea tutti gli anni di morte modificati
    def uploadAnniMorte() {
        ArrayList listaAnniModificati

        listaAnniModificati = Anno.findAllBySporcoMorto(true)
        listaAnniModificati?.each {
            uploadAnnoMorte((Anno) it)
        } // fine del ciclo each
    } // fine del metodo

    //--creazione delle liste partendo da BioGrails
    //--elabora e crea la lista dell'anno di morte
    def uploadAnnoMorte(Anno anno) {
        ArrayList listaPersone = new ArrayList()
        ArrayList listaPersoneSenzaGiorno
        ArrayList listaPersoneConGiorno
        String querySenza
        String queryGiorno
        long annoId
        int numPersone
        String tagNatiMorti = 'Morti'
        String tagNateMorte = 'morte'

        if (anno) {
            annoId = anno.id
        }// fine del blocco if

        if (annoId) {
            querySenza = "select didascaliaAnnoMorto from BioGrails where (annoMorteLink=${annoId} and giornoMeseMorteLink is null) order by cognome asc"
            queryGiorno = "select didascaliaAnnoMorto from BioGrails where (annoMorteLink=${annoId} and giornoMeseMorteLink>0) order by giornoMeseMorteLink,cognome asc"
            listaPersoneSenzaGiorno = BioGrails.executeQuery(querySenza)
            listaPersoneConGiorno = BioGrails.executeQuery(queryGiorno)
            listaPersoneSenzaGiorno?.each {
                if (it) {
                    listaPersone.add(it)
                }// fine del blocco if
            } // fine del ciclo each
            listaPersoneConGiorno?.each {
                if (it) {
                    listaPersone.add(it)
                }// fine del blocco if
            } // fine del ciclo each
            if (listaPersone) {
                numPersone = listaPersone.size()
                this.caricaPagina(anno, listaPersone, numPersone, tagNatiMorti, tagNateMorte)
            }// fine del blocco if
        }// fine del blocco if

        if (anno) {
            anno.sporcoMorto = false
            anno.save(flush: true)
        }// fine del blocco if
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
        EditBio paginaModificata
        Risultato risultato
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
            paginaModificata = new EditBio(titolo, testo, summary)
            risultato = paginaModificata.getRisultato()

            if ((risultato == Risultato.modificaRegistrata) || (risultato == Risultato.allineata)) {
                registrata = true
            } else {
//                log.warn "La pagina $titolo è $risultato"
            }// fine del blocco if-else
        }// fine del blocco if

        // valore di ritorno
        return registrata
    }// fine del metodo

    /**
     * Carica su wiki la pagina
     */
    private boolean caricaPagina(Anno anno, ArrayList lista, int numPersone, String tagNatiMorti, String tagNateMorte) {
        // variabili e costanti locali di lavoro
        boolean registrata = false
        String titolo = ''
        String testo = ''
        String summary = LibBio.getSummary()
        EditBio paginaModificata
        Risultato risultato
        boolean debug = Preferenze.getBool((String) grailsApplication.config.debug)

        // controllo di congruità
        if (anno && lista && numPersone) {
            titolo = getTitolo(anno, tagNatiMorti)
            testo = getTesto(anno, lista, numPersone, tagNatiMorti, tagNateMorte)
        }// fine del blocco if

        if (titolo && testo) {
            if (debug) {
                titolo = 'Utente:Gac/Sandbox4280'
            }// fine del blocco if
            paginaModificata = new EditBio(titolo, testo, summary)
            risultato = paginaModificata.getRisultato()

            if ((risultato == Risultato.registrata) || (risultato == Risultato.allineata)) {
                registrata = true
            } else {
//                log.warn "La pagina $titolo è $risultato"
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
     * Costruisce il titolo della pagina
     */
    private static String getTitolo(Anno anno, String tagNatiMorti) {
        // variabili e costanti locali di lavoro
        String titolo = ''
        String spazio = ' '
        String articolo = 'nel'
        String articoloBis = "nell'"
        String tagAC = ' a.C.'

        // controllo di congruità
        if (anno && tagNatiMorti) {
            titolo = anno.titolo
            if (titolo == '1'
                    || titolo == '1' + tagAC
                    || titolo == '11'
                    || titolo == '11' + tagAC
                    || titolo.startsWith('8')
            ) {
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
    private
    static String getTesto(Giorno giorno, ArrayList lista, int numPersone, String tagNatiMorti, String tagNateMorte) {
        // variabili e costanti locali di lavoro
        String testo = ''

        if (giorno && lista && numPersone) {
            testo = getTestoTop(giorno, numPersone)
            testo += getTestoBodyGiorno(lista, tagNateMorte)
            testo += aCapo
            testo += getTestoBottom(giorno, tagNatiMorti)
        }// fine del blocco if

        // valore di ritorno
        return testo.trim()
    }// fine del metodo

    /**
     * Costruisce il testo della pagina
     */
    private
    static String getTesto(Anno anno, ArrayList lista, int numPersone, String tagNatiMorti, String tagNateMorte) {
        // variabili e costanti locali di lavoro
        String testo = ''

        if (anno && lista && numPersone) {
            testo = getTestoTop(anno, numPersone)
            testo += getTestoBodyAnno(lista, tagNateMorte)
            testo += aCapo
            testo += getTestoBottom(anno, tagNatiMorti)
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
        String personeTxt

        // controllo di congruità
        if (giorno && numPersone) {
            dataCorrente = LibTime.getGioMeseAnnoLungo(new Date())
            personeTxt = LibTesto.formatNum(numPersone)
            torna = giorno.titolo

            testo = "<noinclude>"
            testo += aCapo
            testo += "{{ListaBio"
            testo += "|bio="
            testo += personeTxt
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
     * Costruisce il testo iniziale della pagina
     */
    private static String getTestoTop(Anno anno, int numPersone) {
        // variabili e costanti locali di lavoro
        String testo = ''
        String torna
        String dataCorrente
        String personeTxt

        // controllo di congruità
        if (anno && numPersone) {
            dataCorrente = LibTime.getGioMeseAnnoLungo(new Date())
            personeTxt = LibTesto.formatNum(numPersone)
            torna = anno.titolo

            testo = "<noinclude>"
            testo += aCapo
            testo += "{{ListaBio"
            testo += "|bio="
            testo += personeTxt
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
    private static String getTestoBody(ArrayList lista, String tagNateMorte, boolean usaSempreCassetto) {
        // variabili e costanti locali di lavoro
        String testoBody = ''
        boolean usaDueColonne = true
        String testo = ''
        int numPersone = 0

        // testo della lista
        if (lista) {
            numPersone = lista.size()
            lista.each {
                testo += ast
                testo += it
                testo += aCapo
            }//fine di each
            testoBody = testo.trim()
        }// fine del blocco if

        // eventuale doppia colonna
        testoBody = fixTestoColonne(testoBody, numPersone)

        // eventuale cassetto
        testoBody = fixTestoCassetto(testoBody, numPersone, tagNateMorte, usaSempreCassetto)

        // valore di ritorno
        return testoBody.trim()
    }// fine del metodo

    /**
     * Costruisce il testo variabile della pagina
     * I giorni hanno sempre un numero notevole di persone
     * quindi vanno sempre col cassetto e su due colonne
     *
     * @param lista degli elementi
     * @return testo con un ritorno a capo iniziale ed uno finale
     */
    private static String getTestoBodyGiorno(ArrayList lista, String tagNateMorte) {
        return getTestoBody(lista, tagNateMorte, true)
    }// fine del metodo

    /**
     * Costruisce il testo variabile della pagina
     * I giorni hanno sempre un numero notevole di persone
     * quindi vanno sempre col cassetto e su due colonne
     *
     * @param lista degli elementi
     * @return testo con un ritorno a capo iniziale ed uno finale
     */
    private static String getTestoBodyAnno(ArrayList lista, String tagNateMorte) {
        return getTestoBody(lista, tagNateMorte, false)
    }// fine del metodo


    private static fixTestoColonne(String testoIn, int numPersone) {
        String testoOut = testoIn
        boolean usaColonne = Preferenze.getBool(LibBio.USA_COLONNE)
        int maxRigheColonne = Preferenze.getInt(LibBio.MAX_RIGHE_COLONNE)

        if (usaColonne && (numPersone > maxRigheColonne)) {
            testoOut = WikiLib.listaDueColonne(testoIn)
        }// fine del blocco if

        // valore di ritorno
        return testoOut.trim()
    }// fine del metodo

    /**
     * Inserisce l'eventuale cassetto
     */
    private
    static String fixTestoCassetto(String testoIn, int numPersone, String tagNateMorte, boolean usaSempreCassetto) {
        // variabili e costanti locali di lavoro
        String testoOut = testoIn
        String titolo
        boolean usaCassetto = Preferenze.getBool(LibBio.USA_CASSETTO)
        int maxRigheCassetto = Preferenze.getInt(LibBio.MAX_RIGHE_CASSETTO)
        String nateMorte

        if (usaSempreCassetto) {
            titolo = "Lista di persone $tagNateMorte in questo giorno"
            testoOut = WikiLib.cassettoInclude(testoIn, titolo)
        } else {
            if (usaCassetto && (numPersone > maxRigheCassetto)) {
                nateMorte = tagNateMorte.toLowerCase()
                nateMorte = nateMorte.substring(0, nateMorte.length() - 1).trim()
                nateMorte += 'e'
                titolo = "Lista di persone $nateMorte in questo anno"
                testoOut = WikiLib.cassettoInclude(testoIn, titolo)
            } else {
                testoOut = testoIn
            }// fine del blocco if-else
        }// fine del blocco if-else

        // valore di ritorno
        return testoOut.trim()
    }// fine del metodo

    /**
     * Costruisce il testo finale della pagina
     */
    private static String getTestoBottom(Giorno giorno, String tagNatiMorti) {
        // variabili e costanti locali di lavoro
        String testo = ''
        String prog
        String titolo
        String tag = tagNatiMorti.toLowerCase()

        // controllo di congruità
        if (giorno) {
            prog = getProgTre(giorno)
            titolo = getTitolo(giorno, tagNatiMorti)
            testo += "<noinclude>"
            testo += aCapo
            testo += '{{Portale|biografie}}'
            testo += aCapo
            testo += "[[Categoria:Liste di ${tag} per giorno| $prog]]"
            testo += aCapo
            testo += "[[Categoria:$titolo| ]]"
            testo += aCapo
            testo += "</noinclude>"
        }// fine del blocco if

        // valore di ritorno
        return testo.trim()
    }// fine del metodo

    /**
     * Costruisce il testo finale della pagina
     */
    private static String getTestoBottom(Anno anno, String tagNatiMorti) {
        // variabili e costanti locali di lavoro
        String testo = ''
        String prog
        String titolo
        String tag = tagNatiMorti.toLowerCase()

        // controllo di congruità
        if (anno) {
            prog = anno.progressivoCategoria
            titolo = getTitolo(anno, tagNatiMorti)
            testo += "<noinclude>"
            testo += aCapo
            testo += '{{Portale|biografie}}'
            testo += aCapo
            testo += "[[Categoria:Liste di ${tag} nell'anno| $prog]]"
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
