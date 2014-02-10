package it.algos.botbio

import it.algos.algoslib.LibWiki
import org.apache.commons.logging.LogFactory

/**
 * Created by IntelliJ IDEA.
 * User: Gac
 * Date: 11/02/11
 * Time: 09.51
 */
class DidascaliaBio {

    protected static boolean USA_SIMBOLI = true
    protected static boolean USA_WARN = false

    private static def log = LogFactory.getLog(this)
    private static String PUNTI = '...'
    private static String PUNTI_NATO = '..'
    private static String TAG_NATO_CRONO = 'n. '
    private static String TAG_MORTO_CRONO = '† '
    private static String TAG_NATO_LISTE = 'n.'
    private static String TAG_MORTO_LISTE = '†'

    private DidascaliaTipo tipoDidascalia = DidascaliaTipo.estesaSimboli

    private String nome = ''
    private String cognome = ''
    private String titolo = ''
    private String forzaOrdinamento

    private Giorno giornoMeseNascitaLink = null
    private Anno annoNascitaLink = null
    private Giorno giornoMeseMorteLink = null
    private Anno annoMorteLink = null
    private String giornoMeseNascita = ''
    private String annoNascita = ''
    private String giornoMeseMorte = ''
    private String annoMorte = ''
    private String localitaNato = ''
    private String localitaMorto = ''

    private Attivita attivitaLink = null
    private Attivita attivita2Link = null
    private Attivita attivita3Link = null
    private Nazionalita nazionalitaLink = null
    private String attivita = ''
    private String attivita2 = ''
    private String attivita3 = ''
    private String nazionalita = ''

    private String ordineAlfabetico = ''
    private String primoCarattere = ''
    private int ordineAnno = 0
    private int ordineGiorno = 0

    private String attivitaPlurale = ''
    private String attivita2Plurale = ''
    private String attivita3Plurale = ''
    private String nazionalitaPlurale = ''
    private String testo = ''

    private BioGrails biografia

    private String testoBase
    private String testoCrono
    private String testoCronoSimboli
    private String testoSemplice
    private String testoCompleta
    private String testoCompletaSimboli
    private String testoEstesa
    private String testoEstesaSimboli
    private String testoNatiGiorno
    private String testoMortiGiorno
    private String testoNatiAnno
    private String testoMortiAnno


    public DidascaliaBio(long bioId) {
        // Metodo iniziale
        this.inizializza(bioId)
    }// fine del metodo costruttore completo

    public DidascaliaBio(int pageid) {
        BioGrails bio = null

        if (pageid) {
            bio = BioGrails.findByPageid(pageid)
        }// fine del blocco if

        // Metodo iniziale
        if (bio) {
            this.setBiografia(bio)
            this.inizializza()
        }// fine del blocco if
    }// fine del metodo costruttore completo

    /**
     * @deprecated
     */
    public DidascaliaBio(long bioId, DidascaliaTipo didascaliaTipo) {
        this.setTipoDidascalia(didascaliaTipo)
        // Metodo iniziale
        this.inizializza(bioId)
    }// fine del metodo costruttore completo


    public DidascaliaBio(WrapBio wrapBio) {
        // Metodo iniziale
        this.inizializza(wrapBio)
    }// fine del metodo costruttore completo


    public DidascaliaBio(BioGrails biografia) {
        // Metodo iniziale
        this.setBiografia(biografia)
//        this.inizializza()
    }// fine del metodo costruttore completo

    /**
     * Metodo iniziale
     *
     * @param plurale
     */
    private inizializza(long biografiaId) {
        // variabili e costanti locali di lavoro
        BioGrails biografia

        if (biografiaId) {
            biografia = BioGrails.get(biografiaId)

            if (biografia) {
                this.setBiografia(biografia)
                this.inizializza()
            }// fine del blocco if
        }// fine del blocco if
    }// fine della closure

    /**
     * Metodo iniziale
     *
     * @param plurale
     */
    private inizializza(WrapBio wrapBio) {
        // variabili e costanti locali di lavoro
        BioGrails biografia

        if (wrapBio) {
            biografia = wrapBio.getBioFinale()

            if (biografia) {
                this.setBiografia(biografia)
                this.inizializza()
            }// fine del blocco if
        }// fine del blocco if
    }// fine della closure

    /**
     * Metodo iniziale
     *
     * @param plurale
     */
    private inizializza() {
        BioGrails bio = this.getBiografia()

        if (bio) {
            this.recuperaDatiAnagrafici(bio)
            this.recuperaDatiCrono(bio)
            this.recuperaDatiLocalita(bio)
            this.recuperaDatiAttNaz(bio)

            this.regolaOrdineAlfabetico()
            this.regolaOrdineCronologico()
            this.regolaDidascalia()
        }// fine del blocco if
    }// fine della closure

    /**
     * Recupera dal record di biografia i valori anagrafici
     */
    private recuperaDatiAnagrafici(BioGrails bio) {
        if (bio) {
            try { // prova ad eseguire il codice
                if (bio.nome) {
                    this.nome = bio.nome
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca nome'
                }// fine del blocco if
            }// fine del blocco try-catch

            try { // prova ad eseguire il codice
                if (bio.cognome) {
                    this.cognome = bio.cognome
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca cognome'
                }// fine del blocco if
            }// fine del blocco try-catch

            try { // prova ad eseguire il codice
                if (bio.forzaOrdinamento) {
                    this.forzaOrdinamento = bio.forzaOrdinamento
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca forzaOrdinamento'
                }// fine del blocco if
            }// fine del blocco try-catch

            try { // prova ad eseguire il codice
                if (bio.title) {
                    this.titolo = bio.title
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca titolo'
                }// fine del blocco if
            }// fine del blocco try-catch
        }// fine del blocco if
    }// fine della closure

    /**
     * Recupera dal record di biografia i valori cronografici
     */
    private recuperaDatiCrono(BioGrails bio) {
        if (bio) {
            try { // prova ad eseguire il codice
                if (bio.giornoMeseNascitaLink) {
                    this.giornoMeseNascitaLink = bio.giornoMeseNascitaLink
                    this.giornoMeseNascita = this.giornoMeseNascitaLink.titolo
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca giornoMeseNascitaLink'
                }// fine del blocco if
            }// fine del blocco try-catch

            try { // prova ad eseguire il codice
                if (bio.annoNascitaLink) {
                    this.annoNascitaLink = bio.annoNascitaLink
                    this.annoNascita = this.annoNascitaLink.titolo
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca annoNascitaLink'
                }// fine del blocco if
            }// fine del blocco try-catch

            try { // prova ad eseguire il codice
                if (bio.giornoMeseMorteLink) {
                    this.giornoMeseMorteLink = bio.giornoMeseMorteLink
                    this.giornoMeseMorte = this.giornoMeseMorteLink.titolo
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca giornoMeseMorteLink'
                }// fine del blocco if
            }// fine del blocco try-catch

            try { // prova ad eseguire il codice
                if (bio.annoMorteLink) {
                    this.annoMorteLink = bio.annoMorteLink
                    this.annoMorte = this.annoMorteLink.titolo
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca annoMorteLink'
                }// fine del blocco if
            }// fine del blocco try-catch
        }// fine del blocco if
    }// fine della closure

    /**
     * Recupera dal record di biografia i valori delle località
     */
    private recuperaDatiLocalita(BioGrails bio) {
        if (bio) {
            try { // prova ad eseguire il codice
                if (bio.localitaNato) {
                    this.localitaNato = bio.localitaNato
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca luogoNascita'
                }// fine del blocco if
            }// fine del blocco try-catch

            try { // prova ad eseguire il codice
                if (bio.localitaMorto) {
                    this.localitaMorto = bio.localitaMorto
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca luogoMorte'
                }// fine del blocco if
            }// fine del blocco try-catch
        }// fine del blocco if
    }// fine della closure

    /**
     * Recupera dal record di biografia i valori delle attività e della nazionalità
     */
    private recuperaDatiAttNaz(BioGrails bio) {
        if (bio) {
            try { // prova ad eseguire il codice
                if (bio.attivitaLink) {
                    this.attivitaLink = bio.attivitaLink
                    this.attivita = this.attivitaLink.singolare
                    this.attivitaPlurale = this.attivitaLink.plurale
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca attivitaLink'
                }// fine del blocco if
            }// fine del blocco try-catch

            try { // prova ad eseguire il codice
                if (bio.attivita2Link) {
                    this.attivita2Link = bio.attivita2Link
                    this.attivita2 = this.attivita2Link.singolare
                    this.attivita2Plurale = this.attivita2Link.plurale
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca attivita2Link'
                }// fine del blocco if
            }// fine del blocco try-catch

            try { // prova ad eseguire il codice
                if (bio.attivita3Link) {
                    this.attivita3Link = bio.attivita3Link
                    this.attivita3 = this.attivita3Link.singolare
                    this.attivita3Plurale = this.attivita3Link.plurale
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca attivita3Link'
                }// fine del blocco if
            }// fine del blocco try-catch

            try { // prova ad eseguire il codice
                if (bio.nazionalitaLink) {
                    this.nazionalitaLink = bio.nazionalitaLink
                    this.nazionalita = this.nazionalitaLink.singolare
                    this.nazionalitaPlurale = this.nazionalitaLink.plurale
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                if (USA_WARN) {
                    log.warn 'manca nazionalitaLink'
                }// fine del blocco if
            }// fine del blocco try-catch
        }// fine del blocco if
    }// fine della closure

    /**
     * Regola l'ordinamento alfabetico del nome
     */
    def regolaOrdineAlfabetico() {
        // variabili e costanti locali di lavoro
        String tagVir = ', '

        if (this.forzaOrdinamento) {
            this.ordineAlfabetico = this.forzaOrdinamento
        } else {
            if (this.nome && this.cognome) {
                this.ordineAlfabetico = this.cognome + tagVir + this.nome
            } else {
                if (this.nome) {
                    this.ordineAlfabetico = this.nome
                }// fine del blocco if
                if (this.cognome) {
                    this.ordineAlfabetico = this.cognome
                }// fine del blocco if
            }// fine del blocco if-else
        }// fine del blocco if-else

        if (this.ordineAlfabetico) {
            this.primoCarattere = this.ordineAlfabetico.substring(0, 1).toUpperCase()
        }// fine del blocco if

    } // fine della closure

    /**
     * Regola l'ordinamento cronologico
     */
    def regolaOrdineCronologico() {

        if (this.annoNascitaLink) {
            this.ordineAnno = this.annoNascitaLink.progressivoCategoria
        }// fine del blocco if

        if (this.giornoMeseNascitaLink) {
            this.ordineGiorno = this.giornoMeseNascitaLink.bisestile
        }// fine del blocco if
    } // fine della closure

    /**
     * Costruisce il testo della didascalia
     * Costruisce tutte le possibilità
     */
    public regolaDidascalia() {
        // variabili e costanti locali di lavoro
        String testoBase
        String testoCrono
        String testoCronoSimboli
        String testoSemplice
        String testoCompleta
        String testoCompletaSimboli
        String testoEstesa
        String testoEstesaSimboli
        String testoNatiGiorno
        String testoMortiGiorno
        String testoNatiAnno
        String testoMortiAnno

        //--testo base
        this.setTipoDidascalia(DidascaliaTipo.base)
        testoBase = regolaDidascaliaBase()
        if (testoBase) {
            this.setTestoBase(testoBase)
        }// fine del blocco if

        //--testo crono
        this.setTipoDidascalia(DidascaliaTipo.crono)
        testoCrono = regolaDidascaliaBase()
        if (testoCrono) {
            this.setTestoCrono(testoCrono)
        }// fine del blocco if

        //--testo crono simboli
        this.setTipoDidascalia(DidascaliaTipo.cronoSimboli)
        testoCronoSimboli = regolaDidascaliaBase()
        if (testoCronoSimboli) {
            this.setTestoCronoSimboli(testoCronoSimboli)
        }// fine del blocco if

        //--testo semplice
        this.setTipoDidascalia(DidascaliaTipo.semplice)
        testoSemplice = regolaDidascaliaBase()
        if (testoSemplice) {
            this.setTestoSemplice(testoSemplice)
        }// fine del blocco if

        //--testo completa
        this.setTipoDidascalia(DidascaliaTipo.completa)
        testoCompleta = regolaDidascaliaBase()
        if (testoCompleta) {
            this.setTestoCompleta(testoCompleta)
        }// fine del blocco if

        //--testo completa simboli
        this.setTipoDidascalia(DidascaliaTipo.completaSimboli)
        testoCompletaSimboli = regolaDidascaliaBase()
        if (testoCompletaSimboli) {
            this.setTestoCompletaSimboli(testoCompletaSimboli)
        }// fine del blocco if

        //--testo estesa
        this.setTipoDidascalia(DidascaliaTipo.estesa)
        testoEstesa = regolaDidascaliaBase()
        if (testoSemplice) {
            this.setTestoEstesa(testoEstesa)
        }// fine del blocco if

        //--testo estesa simboli
        this.setTipoDidascalia(DidascaliaTipo.estesaSimboli)
        testoEstesaSimboli = regolaDidascaliaBase()
        if (testoEstesaSimboli) {
            this.setTestoEstesaSimboli(testoEstesaSimboli)
        }// fine del blocco if

        //--testo nati nel giorno
        this.setTestoNatiGiorno('')
        if (giornoMeseNascita) {
            this.setTipoDidascalia(DidascaliaTipo.natiGiorno)
            testoNatiGiorno = regolaDidascaliaBase()
            if (testoNatiGiorno) {
                this.setTestoNatiGiorno(testoNatiGiorno)
            }// fine del blocco if
        }// fine del blocco if

        //--testo nati nel anno
        this.setTestoNatiAnno('')
        if (annoNascita) {
            this.setTipoDidascalia(DidascaliaTipo.natiAnno)
            testoNatiAnno = regolaDidascaliaBase()
            if (testoNatiAnno) {
                this.setTestoNatiAnno(testoNatiAnno)
            }// fine del blocco if
        }// fine del blocco if

        //--testo morti nel giorno
        this.setTestoMortiGiorno('')
        if (giornoMeseMorte) {
            this.setTipoDidascalia(DidascaliaTipo.mortiGiorno)
            testoMortiGiorno = regolaDidascaliaBase()
            if (testoMortiGiorno) {
                this.setTestoMortiGiorno(testoMortiGiorno)
            }// fine del blocco if
        }// fine del blocco if

        //--testo morti nel anno
        this.setTestoMortiAnno('')
        if (annoMorte) {
            this.setTipoDidascalia(DidascaliaTipo.mortiAnno)
            testoMortiAnno = regolaDidascaliaBase()
            if (testoMortiAnno) {
                this.setTestoMortiAnno(testoMortiAnno)
            }// fine del blocco if
        }// fine del blocco if
    } // fine della closure

    /**
     * Costruisce il testo della didascalia
     */
    public String regolaDidascaliaBase() {
        // variabili e costanti locali di lavoro
        String testo = ''

        // blocco iniziale (potrebbe non esserci)
        testo += getBloccoIniziale()

        // titolo e nome (obbligatori)
        testo += this.getNomeCognome()

        // attivitaNazionalita (potrebbe non esserci)
        testo += this.getAttNaz()

        // blocco finale (potrebbe non esserci)
        testo += this.getBloccoFinale()

        if (testo) {
            this.testo = testo
        }// fine del blocco if

        return testo
    } // fine della closure

    /**
     * asterisco iniziale
     */
    private static String getAsterisco() {
        // variabili e costanti locali di lavoro
        String testo = ''
        String tagAst = '*'
        String spazio = ' '

        // asterisco iniziale
        testo += tagAst
        testo += spazio

        // valore di ritorno
        return testo
    } // fine del metodo

    /**
     * Costruisce il blocco iniziale (potrebbe non esserci)
     */
    def getBloccoIniziale() {
        // variabili e costanti locali di lavoro
        String testo = ''
        boolean continua = false
//        Giorno giornoMeseNascita = null
//        Anno annoNascita = null
//        Giorno giornoMeseMorte = null
//        Anno annoMorte = null
        String tagIni = '*'
        String tagSep = ' - '

        switch (tipoDidascalia) {
            case DidascaliaTipo.base:
                break
            case DidascaliaTipo.crono:
                break
            case DidascaliaTipo.cronoSimboli:
                break
            case DidascaliaTipo.semplice:
                break
            case DidascaliaTipo.completaLista:
                break
            case DidascaliaTipo.completa:
                break
            case DidascaliaTipo.completaSimboli:
                break
            case DidascaliaTipo.estesa:
                break
            case DidascaliaTipo.estesaSimboli:
                break
            case DidascaliaTipo.natiGiorno:
                if (annoNascita) {
                    testo = LibWiki.setQuadre(annoNascita) + tagSep
                }// fine del blocco if
                break
            case DidascaliaTipo.natiAnno:
                if (giornoMeseNascita) {
                    testo = LibWiki.setQuadre(giornoMeseNascita) + tagSep
                }// fine del blocco if
                break
            case DidascaliaTipo.mortiGiorno:
                if (annoMorte) {
                    testo = LibWiki.setQuadre(annoMorte) + tagSep
                }// fine del Libreria if
                break
            case DidascaliaTipo.mortiAnno:
                if (giornoMeseMorte) {
                    testo = LibWiki.setQuadre(giornoMeseMorte) + tagSep
                }// fine del blocco if
                break
            default: // caso non definito
                break
        } // fine del blocco switch

        // asterisco iniziale della riga
//        if (testo) {
//            testo = tagIni + testo
//        }// fine del blocco if

        // valore di ritorno
        return testo
    } // fine della closure

    /**
     * Costruisce il nome e cognome (obbligatori)
     * Si usa il titolo della voce direttamente, se non contiene parentesi
     *
     * @return testo con link alla voce (doppie quadre)
     */
    def getNomeCognome() {
        // variabili e costanti locali di lavoro
        String nomeCognome = ''
        boolean continua = false
        String titoloVoce = ''
        String tagPar = '('
        String tagPipe = '|'
        String nomePersona

        // controllo di congruità
        titoloVoce = this.titolo
        if (titoloVoce) {
            continua = true
        }// fine del blocco if

        if (continua) {
            // se il titolo NON contiene la parentesi, il nome non va messo perché coincide col titolo della voce
            if (titoloVoce.contains(tagPar)) {
                nomePersona = titoloVoce.substring(0, titoloVoce.indexOf(tagPar))
                nomeCognome = titoloVoce + tagPipe + nomePersona
            } else {
                nomeCognome = titoloVoce
            }// fine del blocco if-else
            continua == (nomeCognome)
        }// fine del blocco if

        if (continua) {
            nomeCognome = nomeCognome.trim()
            nomeCognome = LibWiki.setQuadre(nomeCognome)
        }// fine del blocco if

        // valore di ritorno
        return nomeCognome
    } // fine della closure

    /**
     * Costruisce la stringa attività e nazionalità della didascalia
     *
     * I collegamenti alle tavole Attività e Nazionalità, potrebbero esistere nella biografia
     * anche se successivamente i corrispondenti records (di Attività e Nazionalità) sono stati cancellati
     * Occorre quindi proteggere il codice dall'errore (dovuto ad un NON aggiornamento dei dati della biografia)
     *
     * @return testo
     */
    def getAttNaz() {
        // variabili e costanti locali di lavoro
        String attNazDidascalia = ''
        boolean continua = true
        String attivita = null
        String attivita2 = null
        String attivita3 = null
        String nazionalita = null
        String tagAnd = ' e '
        String tagSpa = ' '
        String tagVir = ', '
        boolean virgolaDopoPrincipale = false
        boolean andDopoPrincipale = false
        boolean andDopoSecondaria = false

        attivita = this.attivita
        attivita2 = this.attivita2
        attivita3 = this.attivita3
        nazionalita = this.nazionalita

        // la virgolaDopoPrincipale c'è se è presente la seconda attività e la terza
        if (continua) {
            if (attivita2 && attivita3) {
                virgolaDopoPrincipale = true
            }// fine del blocco if
        }// fine del blocco if

        // la andDopoPrincipale c'è se è presente la seconda attività e non la terza
        if (continua) {
            if (attivita2 && !attivita3) {
                andDopoPrincipale = true
            }// fine del blocco if
        }// fine del blocco if

        // la andDopoSecondaria c'è se è presente terza attività
        if (continua) {
            if (attivita3) {
                andDopoSecondaria = true
            }// fine del blocco if
        }// fine del blocco if

        if (continua) {
            switch (tipoDidascalia) {
                case DidascaliaTipo.base:
                    break
                case DidascaliaTipo.crono:
                    break
                case DidascaliaTipo.cronoSimboli:
                    break
                case DidascaliaTipo.semplice:
                case DidascaliaTipo.completaLista:
                case DidascaliaTipo.completa:
                case DidascaliaTipo.completaSimboli:
                case DidascaliaTipo.estesa:
                case DidascaliaTipo.estesaSimboli:
                case DidascaliaTipo.natiGiorno:
                case DidascaliaTipo.natiAnno:
                case DidascaliaTipo.mortiGiorno:
                case DidascaliaTipo.mortiAnno:
                    // attività principale
                    if (attivita) {
                        attNazDidascalia += attivita
                    }// fine del blocco if

                    // virgola
                    if (virgolaDopoPrincipale) {
                        attNazDidascalia += tagVir
                    }// fine del blocco if

                    // and
                    if (andDopoPrincipale) {
                        attNazDidascalia += tagAnd
                    }// fine del blocco if

                    // attività secondaria
                    if (attivita2) {
                        attNazDidascalia += attivita2
                    }// fine del blocco if

                    // and
                    if (andDopoSecondaria) {
                        attNazDidascalia += tagAnd
                    }// fine del blocco if

                    // attività terziaria
                    if (attivita3) {
                        attNazDidascalia += attivita3
                    }// fine del blocco if

                    // nazionalità facoltativo
                    if (nazionalita) {
                        attNazDidascalia += tagSpa
                        attNazDidascalia += nazionalita
                    }// fine del blocco if

                    if (attNazDidascalia) {
                        attNazDidascalia = tagVir + attNazDidascalia + tagSpa
                    }// fine del blocco if
                    break
                default: // caso non definito
                    break
            } // fine del blocco switch
        }// fine del blocco if

        // valore di ritorno
        return attNazDidascalia.trim()
    } // fine della closure

    /**
     * Costruisce il blocco finale (potrebbe non esserci)
     *
     * @return testo
     */
    def getBloccoFinale() {
        // variabili e costanti locali di lavoro
        String testo = ''
        String annoNascita = null
        String annoMorte = null
        String luogoNascita = ''
        String luogoNascitaLink = ''
        String luogoMorte = ''
        String luogoMorteLink = ''
        String tagParIni = ' ('
        String tagParEnd = ')'
        String tagParVir = ', '
        String tagParMezzo = ' - '
        boolean parentesi = false
        boolean trattino = false
        boolean simboli = false
        boolean virgolaNato = false
        boolean virgolaMorto = false
        String tagAnnoNato = PUNTI
        String tagAnnoMorto = PUNTI

        annoNascita = this.annoNascita
        annoMorte = this.annoMorte

        luogoNascita = this.localitaNato
        luogoMorte = this.localitaMorto

        //patch
        //se il luogo di nascita (mancante) è indicato con 3 puntini (car 8230), li elimino
        if (luogoNascita.size() == 1) {
            luogoNascita = ''
        }// fine del blocco if

        //    //se non c'è ne anno ne luogo di nascita, metto i puntini
        //    //se non c'è ne anno ne luogo di morte, metto i puntini
        //    //se manca tutto non visualizzo nemmeno i puntini
        //    if (tipoDidascalia == DidascaliaTipo.estesa || tipoDidascalia == DidascaliaTipo.estesaSimboli) {
        //        if (!luogoNascita && !annoNascita) {
        //            annoNascita = tagAnnoNato
        //        }// fine del blocco if
        //        //if (!luogoMorte && !annoMorte) {
        //        //    annoMorte = tagAnnoMorto
        //        //}// fine del blocco if
        //        if (annoNascita.equals(tagAnnoNato) && annoMorte.equals(tagAnnoNato)) {
        //            annoNascita = ''
        //            annoMorte = ''
        //        }// fine del blocco if
        //    }// fine del blocco if

        // se manca l'anno di nascita
        // metto i puntini SOLO se esiste l'anno di morte
        if (tipoDidascalia == DidascaliaTipo.estesa || tipoDidascalia == DidascaliaTipo.estesaSimboli) {
            if (!annoNascita && annoMorte) {
                annoNascita = tagAnnoNato
            }// fine del blocco if
        }// fine del blocco if

        // la parentesi c'è se anche solo uno dei dati è presente
        if (luogoNascita || annoNascita || luogoMorte || annoMorte) {
            parentesi = true
        }// fine del blocco if

        // il trattino c'è se è presente un dato della nascita (anno o luogo) ed uno della morte (anno o luogo)
        if ((luogoNascita || annoNascita) && (luogoMorte || annoMorte)) {
            trattino = true
        }// fine del blocco if

        // i simboli ci sono se previsti nel DidascaliaTipo, oppure se c'è solo una data
        if (tipoDidascalia == DidascaliaTipo.cronoSimboli || tipoDidascalia == DidascaliaTipo.completaSimboli || tipoDidascalia == DidascaliaTipo.estesaSimboli) {
            simboli = true
        } else {
            if (!annoNascita || !annoMorte) {
                simboli = true
            }// fine del blocco if
        }// fine del blocco if-else

        // la virgolaNato c'è se solo se entrambi i dati di nascita sono presenti
        if (luogoNascita && annoNascita) {
            virgolaNato = true
        }// fine del blocco if

        // la virgolaMorto c'è se solo se entrambi i dati di morte sono presenti
        if (luogoMorte && annoMorte) {
            virgolaMorto = true
        }// fine del blocco if

        // costruisce il blocco finale (potrebbe non esserci)
        switch (tipoDidascalia) {
            case DidascaliaTipo.base:
                break
            case DidascaliaTipo.semplice:
                break
            case DidascaliaTipo.crono:
            case DidascaliaTipo.cronoSimboli:
            case DidascaliaTipo.completaLista:
            case DidascaliaTipo.completa:
            case DidascaliaTipo.completaSimboli:
                if (parentesi) {
                    testo = tagParIni
                }// fine del blocco if
                if (annoNascita) {
                    testo = tagParIni
                    testo += getTestoAnnoNascita(annoNascita, simboli)
                    if (annoMorte) {
                        testo += tagParMezzo
                    }// fine del blocco if
                }// fine del blocco if
                if (annoMorte) {
                    testo += getTestoAnnoMorte(annoMorte, simboli)
                }// fine del blocco if
                if (parentesi) {
                    testo += tagParEnd
                }// fine del blocco if
                break
            case DidascaliaTipo.estesa:
            case DidascaliaTipo.estesaSimboli:
                if (parentesi) {
                    testo = tagParIni
                }// fine del blocco if

                if (luogoNascita) {
                    if (luogoNascitaLink) {
                        testo += LibWiki.setQuadre(luogoNascitaLink + "|" + luogoNascita)
                    } else {
                        if (luogoNascita.contains(')')) {
                            luogoNascita+='|'
                        }// fine del blocco if
                        testo += LibWiki.setQuadre(luogoNascita)
                    }// fine del blocco if-else
                }// fine del blocco if

                if (virgolaNato) {
                    testo += tagParVir
                }// fine del blocco if

                if (annoNascita) {
                    testo += getTestoAnnoNascita(annoNascita, simboli)
                }// fine del blocco if

                if (trattino) {
                    testo += tagParMezzo
                }// fine del blocco if

                if (luogoMorte) {
                    if (luogoMorteLink) {
                        testo += LibWiki.setQuadre(luogoMorteLink + "|" + luogoMorte)
                    } else {
                        if (luogoMorte.contains(')')) {
                            luogoMorte+='|'
                        }// fine del blocco if
                        testo += LibWiki.setQuadre(luogoMorte)
                    }// fine del blocco if-else
                }// fine del blocco if

                if (virgolaMorto) {
                    testo += tagParVir
                }// fine del blocco if

                if (annoMorte) {
                    testo += getTestoAnnoMorte(annoMorte, simboli)
                }// fine del blocco if

                if (parentesi) {
                    testo += tagParEnd
                }// fine del blocco if
                break
            case DidascaliaTipo.natiGiorno:
            case DidascaliaTipo.natiAnno:
                if (annoMorte) {
                    testo = ' (' + TAG_MORTO_CRONO
                    testo += LibWiki.setQuadre(annoMorte)
                    testo += tagParEnd
                }// fine del blocco if
                break
            case DidascaliaTipo.mortiGiorno:
            case DidascaliaTipo.mortiAnno:
                if (annoNascita) {
                    testo = ' (' + TAG_NATO_CRONO
                    testo += LibWiki.setQuadre(annoNascita)
                    testo += tagParEnd
                }// fine del blocco if
                break
            default: // caso non definito
                break
        } // fine del blocco switch

        if (testo.equals(tagParIni + tagParEnd)) {
            testo = ''
        }// fine del blocco if

        // valore di ritorno
        return testo
    } // fine della closure

    /**
     * Restituisce il testo dell'anno
     * Con la formattazione stabilita
     *
     * @param anno da formattare
     * @return anno formattato
     */
    private static String getAnno(String anno, boolean simboli, String tagIni) {
        // variabili e costanti locali di lavoro
        String testoAnno = ''
        String tag = PUNTI

        if (anno) {
            if (simboli) {
                testoAnno += tagIni
            }// fine del blocco if

            if (anno.equals(tag)) {
                if (tagIni.equals(TAG_NATO_LISTE)) {
                    anno = PUNTI_NATO
                }// fine del blocco if
                testoAnno += anno
            } else {
                testoAnno += LibWiki.setQuadre(anno)
            }// fine del blocco if
        }// fine del blocco if-else

        // valore di ritorno
        return testoAnno
    } // fine del metodo

    /**
     * Restituisce il testo dell'anno di nascita
     * Con la formattazione stabilita
     */
    private static String getTestoAnnoNascita(String annoNascita, boolean simboli) {
        return getAnno(annoNascita, simboli, TAG_NATO_LISTE)
    } // fine del metodo

    /**
     * Restituisce il testo dell'anno di nascita
     * Con la formattazione stabilita
     */
    private static String getTestoAnnoMorte(String annoMorte, boolean simboli) {
        return getAnno(annoMorte, simboli, TAG_MORTO_LISTE)
    } // fine del metodo

    /**
     * Testo visibile della didascalia
     */
    def getTesto = {
        // @todo provvisorio
        String testo = '* ' + this.testo

        return testo
    } // fine della closure

    public String toString() {
        return this.getTesto()
    } // fine del metodo

    public BioGrails getBiografia() {
        return this.biografia
    } // fine del metodo

    public void setBiografia(BioGrails biografia) {
        this.biografia = biografia
    } // fine del metodo

    public String getTestoPulito() {
        String testo = ''
        String ast = '*'

        testo = this.getTesto()
        testo = testo.trim()
        if (testo.startsWith(ast)) {
            testo = testo.substring(1)
            testo = testo.trim()
        }// fine del blocco if
        if (testo.startsWith(ast)) {
            testo = testo.substring(1)
            testo = testo.trim()
        }// fine del blocco if

        return testo
    } // fine del metodo

    private static String getDidascalia(long bioId, DidascaliaTipo tipoDidascalia) {
        String testo = ''
        DidascaliaBio didascalia = null

        if (bioId) {
            didascalia = new DidascaliaBio(bioId, tipoDidascalia)
        }// fine del blocco if

        if (didascalia) {
            testo = didascalia.getTestoPulito()
        }// fine del blocco if

        return testo
    } // fine del metodo


    public static String getSemplice(long bioId) {
        return getDidascalia(bioId, DidascaliaTipo.semplice)
    } // fine del metodo


    private static String getDidascalia(WrapBio wrapBio, DidascaliaTipo tipoDidascalia) {
        String testo = ''
        DidascaliaBio didascalia = null
        BioGrails biografia

        if (wrapBio) {
            biografia = wrapBio.getBioFinale()
            if (biografia) {
                didascalia = new DidascaliaBio()
                didascalia.setBiografia(biografia)
                didascalia.tipoDidascalia = tipoDidascalia
                didascalia.inizializza()
            }// fine del blocco if

            if (didascalia) {
                testo = didascalia.getTestoPulito()
            }// fine del blocco if
        }// fine del blocco if

        return testo
    } // fine del metodo

    public static String getBase(WrapBio wrapBio) {
        return getDidascalia(wrapBio, DidascaliaTipo.base)
    } // fine del metodo


    public static String getSemplice(WrapBio wrapBio) {
        return getDidascalia(wrapBio, DidascaliaTipo.crono)
    } // fine del metodo


    public static String getCompleta(WrapBio wrapBio) {
        return getDidascalia(wrapBio, DidascaliaTipo.completaLista)
    } // fine del metodo

    DidascaliaTipo getTipoDidascalia() {
        return tipoDidascalia
    }


    void setTipoDidascalia(DidascaliaTipo tipoDidascalia) {
        this.tipoDidascalia = tipoDidascalia
    }


    String getAnnoNascita() {
        return annoNascita
    }


    void setAnnoNascita(String annoNascita) {
        this.annoNascita = annoNascita
    }


    void setAnnoNascita(int annoNascita) {
        this.setAnnoNascita(annoNascita.toString())
    }


    String getAnnoMorte() {
        return annoMorte
    }


    void setAnnoMorte(String annoMorte) {
        this.annoMorte = annoMorte
    }


    void setAnnoMorte(int annoMorte) {
        this.setAnnoMorte(annoMorte.toString())
    }

    String getGiornoMeseNascita() {
        return giornoMeseNascita
    }

    void setGiornoMeseNascita(String giornoMeseNascita) {
        this.giornoMeseNascita = giornoMeseNascita
    }

    String getGiornoMeseMorte() {
        return giornoMeseMorte
    }

    void setGiornoMeseMorte(String giornoMeseMorte) {
        this.giornoMeseMorte = giornoMeseMorte
    }

    String getAttivita() {
        return attivita
    }

    String getAttivita2() {
        return attivita2
    }

    void setAttivita2(String attivita2) {
        this.attivita2 = attivita2
    }

    String getAttivita3() {
        return attivita3
    }

    void setAttivita3(String attivita3) {
        this.attivita3 = attivita3
    }

    void setAttivita(String attivita) {
        this.attivita = attivita
    }

    Attivita getAttivita2Link() {
        return attivita2Link
    }

    void setAttivita2Link(Attivita attivita2Link) {
        this.attivita2Link = attivita2Link
    }

    Attivita getAttivita3Link() {
        return attivita3Link
    }

    void setAttivita3Link(Attivita attivita3Link) {
        this.attivita3Link = attivita3Link
    }

    String getNazionalita() {
        return nazionalita
    }


    void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita
    }


    void setInizializza() {
        this.inizializza()
    }

    String getTestoBase() {
        return testoBase
    }

    void setTestoBase(String testoBase) {
        this.testoBase = testoBase
    }

    String getTestoCrono() {
        return testoCrono
    }

    void setTestoCrono(String testoCrono) {
        this.testoCrono = testoCrono
    }

    String getTestoCronoSimboli() {
        return testoCronoSimboli
    }

    void setTestoCronoSimboli(String testoCronoSimboli) {
        this.testoCronoSimboli = testoCronoSimboli
    }

    String getTestoSemplice() {
        return testoSemplice
    }

    void setTestoSemplice(String testoSemplice) {
        this.testoSemplice = testoSemplice
    }

    String getTestoCompleta() {
        return testoCompleta
    }

    void setTestoCompleta(String testoCompleta) {
        this.testoCompleta = testoCompleta
    }

    String getTestoCompletaSimboli() {
        return testoCompletaSimboli
    }

    void setTestoCompletaSimboli(String testoCompletaSimboli) {
        this.testoCompletaSimboli = testoCompletaSimboli
    }

    String getTestoEstesa() {
        return testoEstesa
    }

    void setTestoEstesa(String testoEstesa) {
        this.testoEstesa = testoEstesa
    }

    String getTestoEstesaSimboli() {
        return testoEstesaSimboli
    }

    void setTestoEstesaSimboli(String testoEstesaSimboli) {
        this.testoEstesaSimboli = testoEstesaSimboli
    }

    String getTestoNatiGiorno() {
        return testoNatiGiorno
    }

    void setTestoNatiGiorno(String testoNatiGiorno) {
        this.testoNatiGiorno = testoNatiGiorno
    }

    String getTestoMortiGiorno() {
        return testoMortiGiorno
    }

    void setTestoMortiGiorno(String testoMortiGiorno) {
        this.testoMortiGiorno = testoMortiGiorno
    }

    String getTestoNatiAnno() {
        return testoNatiAnno
    }

    void setTestoNatiAnno(String testoNatiAnno) {
        this.testoNatiAnno = testoNatiAnno
    }

    String getTestoMortiAnno() {
        return testoMortiAnno
    }

    void setTestoMortiAnno(String testoMortiAnno) {
        this.testoMortiAnno = testoMortiAnno
    }

}// fine della classe
