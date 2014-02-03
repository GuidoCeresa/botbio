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
import it.algos.algospref.LibPref
import it.algos.algospref.Preferenze
import it.algos.algoswiki.Edit
import it.algos.algoswiki.TipoAllineamento
import it.algos.algoswiki.WikiLib

class AntroponimoService {

    private tagTitolo = 'Persone di nome '
    private static String aCapo = '\n'
    private tagPunti = 'Altre...'
    private boolean titoloParagrafoConLink = true
    private String progetto = 'Progetto:Antroponimi/'
    private String templateIncipit = 'incipit lista nomi'

    public void costruisce() {
        ArrayList<String> listaNomiCompleta
        ArrayList<String> listaNomiUniciDiversiPerAccento

        //--recupera una lista 'grezza' di tutti i nomi
        listaNomiCompleta = creaListaNomiCompleta()

        //--elimina tutto ciò che compare oltre al nome
        listaNomiUniciDiversiPerAccento = elaboraNomiUnici(listaNomiCompleta)

        //--ricostruisce i records di antroponimi
        spazzolaPacchetto(listaNomiUniciDiversiPerAccento)
    }// fine del metodo

    //--cancella i records di antroponimi
    public static void cancellaTutto() {
        def recs = Antroponimo.findAll()

        recs?.each {
            it.delete(flush: true)
        } // fine del ciclo each
    }// fine del metodo

    //--recupera una lista 'grezza' di tutti i nomi
    private static ArrayList<String> creaListaNomiCompleta() {
        ArrayList<String> listaNomiCompleta
        String query = "select nome from BioGrails where nome <>'' order by nome asc"

        listaNomiCompleta = (ArrayList<String>) BioGrails.executeQuery(query, [max: 10000])

        return listaNomiCompleta
    }// fine del metodo

    //--elimina tutto ciò che compare oltre al nome
    public static ArrayList<String> elaboraNomiUnici(ArrayList listaNomiCompleta) {
        ArrayList<String> listaNomiUniciDiversiPerAccento = null
        String nomeDaControllare
        String nomeValido = ' '
        int k = 0

        if (listaNomiCompleta && listaNomiCompleta.size() > 0) {
            listaNomiUniciDiversiPerAccento = new ArrayList<String>()

            //--costruisce una lista di nomi 'unici'
            //--i nomi sono differenziati in base all'accento
            listaNomiCompleta.each {
                nomeDaControllare = (String) it
                nomeValido = check(nomeDaControllare)
                if (nomeValido) {
                    if (!listaNomiUniciDiversiPerAccento.contains(nomeValido)) {
                        listaNomiUniciDiversiPerAccento.add(nomeValido)
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del ciclo each
        }// fine del blocco if

        return listaNomiUniciDiversiPerAccento
    }// fine del metodo

    private static String check(String nomeIn) {
        String nomeOut = ''
        ArrayList listaTagContenuto = new ArrayList()
        ArrayList listaTagIniziali = new ArrayList()
        int pos
        String tagSpazio = ' '
        boolean usaNomeSingolo = Preferenze.getBool('usaNomeSingolo')

        listaTagContenuto.add('<ref')
        listaTagContenuto.add('-')
        listaTagContenuto.add('"')
        listaTagContenuto.add("'")
        listaTagContenuto.add('(')

        listaTagIniziali.add('"')
        listaTagIniziali.add("'")//apice
        listaTagIniziali.add('ʿ')//apostrofo
        listaTagIniziali.add('‘')//altro tipo di apostrofo
        listaTagIniziali.add('‛')//altro tipo di apostrofo
        listaTagIniziali.add('[')
        listaTagIniziali.add('(')
        listaTagIniziali.add('.')
        listaTagIniziali.add('<!--')
        listaTagIniziali.add('{')
        listaTagIniziali.add('&')

        String tag = ''

        if (nomeIn.length() < 100) {
            nomeOut = nomeIn

            if (usaNomeSingolo) {
                // @todo Maria e Maria Cristina sono uguali
                if (nomeOut.contains(tagSpazio)) {
                    pos = nomeOut.indexOf(tagSpazio)
                    nomeOut = nomeOut.substring(0, pos)
                    nomeOut = nomeOut.trim()
                }// fine del blocco if
            }// fine del blocco if

            listaTagContenuto?.each {
                tag = (String) it
                if (nomeOut.contains(tag)) {
                    pos = nomeOut.indexOf((String) it)
                    nomeOut = nomeOut.substring(0, pos)
                    nomeOut = nomeOut.trim()
                    def stip
                }// fine del blocco if
                def stop
            } // fine del ciclo each

            listaTagIniziali?.each {
                tag = (String) it

                if (nomeOut.startsWith(tag)) {
                    nomeOut = ''
                }// fine del blocco if
            } // fine del ciclo each

            //nomeOut = nomeOut.toLowerCase()       //@todo va in errore per GianCarlo
            nomeOut = LibTesto.primaMaiuscola(nomeOut)
        }// fine del blocco if-else

        return nomeOut
    }// fine del metodo

    //--ricostruisce i records di antroponimi
    public static void spazzolaPacchetto(ArrayList<String> listaNomiUniciDiversiPerAccento) {
        String nome
        int totaleNomi
        int k = 0

        if (listaNomiUniciDiversiPerAccento && listaNomiUniciDiversiPerAccento.size() > 0) {
            totaleNomi = listaNomiUniciDiversiPerAccento.size()
            listaNomiUniciDiversiPerAccento?.each {
                nome = it
                spazzolaNome(nome, ++k, totaleNomi)
            }// fine del ciclo each
        }// fine del blocco if
    }// fine del metodo

    private static void spazzolaNome(String nome, int pos, int totaleNomi) {
        int lunghezza
        int numVoci
        Antroponimo nuovoAntroponimo

        if (nome) {
            numVoci = numeroVociCheUsanoNome(nome)
            if (numVoci > 0) {
                lunghezza = nome.length()
                nuovoAntroponimo = Antroponimo.findByNome(nome)
                if (!nuovoAntroponimo) {
                    nuovoAntroponimo = new Antroponimo(nome: nome)
                }// fine del blocco if
                nuovoAntroponimo.voci = numVoci
                nuovoAntroponimo.lunghezza = lunghezza
                nuovoAntroponimo.save()
//                nuovoAntroponimo = new Antroponimo(nome: nome, voci: numVoci, lunghezza: lunghezza).save(flush: true)
            } else {
                def stop // c'è qualcosa che non va
            }// fine del blocco if-else
//            if (nuovoAntroponimo) {
//                println(pos + '/' + totaleNomi + ' - ' + nome)
//            } else {
//                println('Non sono riuscito a registrare il nome: ' + nome)
//            }// fine del blocco if-else
        }// fine del blocco if
    }// fine del metodo

    private static int numeroVociCheUsanoNome(String nome) {
        int numVoci = 0
        ArrayList risultato
        String query = "select count(*) from BioGrails where nome='${nome}'"

        risultato = BioGrails.executeQuery(query)
        if (risultato && risultato.size() == 1) {
            numVoci = (int) risultato.get(0)
        }// fine del blocco if

        return numVoci
    }// fine del metodo

    // Elabora tutte le pagine
    def elabora() {
        ArrayList<String> listaNomi

        //esegue la query
        listaNomi = getListaNomi()

        //crea le pagine dei singoli nomi
        listaNomi?.each {
            elaboraSingoloNome((String) it)
        }// fine del ciclo each

        //crea la pagina riepilogativa
        if (listaNomi) {
            creaPaginaRiepilogativa(listaNomi)
        }// fine del blocco if

        //crea le pagine di riepilogo di tutti i nomi
        elencoNomi()

        //crea la pagina di controllo didascalie
        this.creaPaginaDidascalie()
    }// fine del metodo

    def elencoNomi() {
        int taglio = Preferenze.getInt(LibBio.TAGLIO_ANTROPONIMI)
        int soglia = Preferenze.getInt(LibBio.SOGLIA_ANTROPONIMI)
        String testo = ''
        String titolo = progetto + 'Liste'
        String summary = BioService.summarySetting()
        int k = 0
        def listaNomi
        Antroponimo antro
        ArrayList lista = new ArrayList()
        String nome
        int voci
        String vociTxt

        listaNomi = Antroponimo.findAllByVociGreaterThan(soglia - 1, [sort: 'voci', order: 'desc'])
        lista.add(['#', 'Nome', 'Voci'])
        listaNomi?.each {
            vociTxt = ''
            antro = (Antroponimo) it
            nome = antro.nome
            voci = antro.voci
            if (voci > taglio) {
                nome = "'''[[Lista di persone di nome " + nome + "|" + nome + "]]'''"
            }// fine del blocco if-else
            k++
            vociTxt = LibTesto.formatNum((String) voci)
            lista.add([k, nome, voci])
        } // fine del ciclo each

        testo += getElencoHead(k)
        testo += getElencoBody(lista)
        testo += getElencoFooter()

        new Edit(titolo, testo, summary)
    }// fine del metodo

    private static String getElencoHead(int numNomi) {
        String testoTitolo = ''
        String dataCorrente = LibTime.getGioMeseAnnoLungo(new Date())
        int soglia = Preferenze.getInt(LibBio.SOGLIA_ANTROPONIMI)
        int numBio = BioGrails.count()

        testoTitolo += '<noinclude>'
        testoTitolo += "{{StatBio|data=$dataCorrente}}"
        testoTitolo += '</noinclude>'
        testoTitolo += aCapo
        testoTitolo += '==Nomi=='
        testoTitolo += aCapo
        testoTitolo += "Elenco dei '''"
        testoTitolo += LibTesto.formatNum(numNomi)
        testoTitolo += "''' nomi '''differenti''' "
        testoTitolo += "<ref>Gli apostrofi vengono rispettati. Pertanto: '''María, Marià, Maria, Mária, Marìa, Mariâ''' sono nomi diversi</ref>"
        testoTitolo += "<ref>I nomi ''doppi'' ('''Maria Cristina'''), vengono considerati nella loro completezza</ref>"
        testoTitolo += "<ref>Per motivi tecnici, non vengono riportati nomi che iniziano con '''apici''' od '''apostrofi'''</ref>"
        testoTitolo += "<ref>Non vengono riportati nomi che iniziano con '''['''</ref>"
        testoTitolo += "<ref>Non vengono riportati nomi che iniziano con '''{'''</ref>"
        testoTitolo += "<ref>Non vengono riportati nomi che iniziano con '''('''</ref>"
        testoTitolo += "<ref>Non vengono riportati nomi che iniziano con '''.'''</ref>"
        testoTitolo += "<ref>Non vengono riportati nomi che iniziano con '''&'''</ref>"
        testoTitolo += "<ref>Non vengono riportati nomi che iniziano con '''<'''</ref>"
        testoTitolo += " utilizzati nelle '''"
        testoTitolo += LibTesto.formatNum(numBio)
        testoTitolo += "''' voci biografiche con occorrenze maggiori od uguali a '''"
        testoTitolo += soglia
        testoTitolo += "'''"
        testoTitolo += aCapo
        testoTitolo += aCapo

        return testoTitolo
    }// fine del metodo

    //costruisce il testo della tabella
    private static String getElencoBody(ArrayList listaVoci) {
        String testoTabella
        Map mappa = new HashMap()

        mappa.put('lista', listaVoci)
        mappa.put('width', '160')
        mappa.put('align', TipoAllineamento.secondaSinistra)
        testoTabella = WikiLib.creaTabellaSortable(mappa)

        return testoTabella
    }// fine del metodo

    private static String getElencoFooter() {
        String testoFooter = ''

        testoFooter += aCapo
        testoFooter += '==Note=='
        testoFooter += aCapo
        testoFooter += '<references/>'
        testoFooter += aCapo
        testoFooter += aCapo
        testoFooter += '==Voci correlate=='
        testoFooter += aCapo
        testoFooter += '*[[Progetto:Antroponimi]]'
        testoFooter += aCapo
        testoFooter += '*[[Progetto:Antroponimi/Nomi]]'
        testoFooter += aCapo
        testoFooter += '*[[Progetto:Antroponimi/Didascalie]]'
        testoFooter += aCapo
        testoFooter += aCapo
        testoFooter += '<noinclude>'
        testoFooter += '[[Categoria:Liste di persone per nome| ]]'
        testoFooter += '</noinclude>'

        return testoFooter
    }// fine del metodo

    /**
     * Elabora la pagina per un singolo nome
     */
    public void elaboraSingoloNome(String nome) {
        String titolo
        String testo = ''
        String summary = 'BioBot'
        ArrayList<BioGrails> listaBiografie

        titolo = tagTitolo + nome
        listaBiografie = getListaBiografie(nome)

        //header
        testo += this.getNomeHead(nome,listaBiografie.size())

        //body
        testo += this.getNomeBody(listaBiografie, nome)

        //footer
        testo += this.getNomeFooter(nome)

        //registra la pagina
        new Edit(titolo, testo, summary)
    }// fine del metodo

    //--costruisce una lista di nomi
    private static ArrayList<String> getListaNomi() {
        ArrayList<String> listaNomi
        int taglio = Preferenze.getInt(LibBio.TAGLIO_ANTROPONIMI)
        String query = "select nome from Antroponimo where voci>'${taglio}' order by nome asc"

        //esegue la query
        listaNomi = (ArrayList<String>) Antroponimo.executeQuery(query)

        return listaNomi
    }// fine del metodo

    //--costruisce una lista di biografie che 'usano' il nome
    //--se il flag usaNomeSingolo è vero, il nome della voce deve coincidere esattamente col parametro in ingresso
    //--se il flag usaNomeSingolo è falso, il nome della voce deve iniziare col parametro
    private static ArrayList<BioGrails> getListaBiografie(String nome) {
        ArrayList<BioGrails> listaBiografie = new ArrayList()
        BioGrails bio
        String nomeBio
        boolean confrontaSoloPrimo = Preferenze.getBool(LibBio.CONFRONTA_SOLO_PRIMO_NOME_ANTROPONIMI)
        ArrayList<BioGrails> listaGrezza

        //--recupera una lista 'grezza' di tutti i nomi
        if (confrontaSoloPrimo) {
            def criterio = BioGrails.createCriteria()
            listaBiografie = criterio.list() {
                or {
                    like("nome", "${nome}")
                    like("nome", "${nome} %")
                }
                order("title", "asc")
            }
        } else {
            def criterio = BioGrails.createCriteria()
            listaGrezza = criterio.list() {
                like("nome", "${nome}")
                order("title", "asc")
            }
            //--i nomi sono differenziati in base all'accento
            listaGrezza?.each {
                bio = (BioGrails) it
                nomeBio = bio.nome
                //nomeBio = nomeBio.toLowerCase()       //@todo va in errore per GianCarlo
                if (nomeBio.equalsIgnoreCase(nome)) {
                    listaBiografie.add(bio)
                }// fine del blocco if
            } // fine del ciclo each
        }// fine del blocco if-else

        return listaBiografie
    }// fine del metodo

    public String getNomeSingolo(String nomeIn) {
        String nomeOut = nomeIn
        boolean confrontaSoloPrimo = Preferenze.getBool(LibBio.CONFRONTA_SOLO_PRIMO_NOME_ANTROPONIMI)
        String tagSpazio = ' '
        int pos

        // per i confronti solo il primo nome viene considerato
        // @todo Maria e Maria Cristina sono uguali
        if (nomeIn && confrontaSoloPrimo) {
            if (nomeOut.contains(tagSpazio)) {
                pos = nomeOut.indexOf(tagSpazio)
                nomeOut = nomeOut.substring(0, pos)
                nomeOut = nomeOut.trim()
            }// fine del blocco if
        }// fine del blocco if

        return nomeOut
    }// fine del metodo

    public String getNomeHead(String nome, int num) {
        String testo = ''
        String dataCorrente = LibTime.getGioMeseAnnoLungo(new Date())
        String numero = ''
        boolean eliminaIndice = false
        String template = templateIncipit

        if (num) {
            numero = LibTesto.formatNum(num)
        }// fine del blocco if

        if (eliminaIndice) {
            testo += '__NOTOC__'
        }// fine del blocco if
        testo += '<noinclude>'
        testo += aCapo
        testo += "{{StatBio"
        if (numero) {
            testo += "|bio=$numero"
        }// fine del blocco if
        testo += "|data=$dataCorrente}}"
        testo += aCapo

        testo += "{{${template}|nome=${nome}}}"
        testo += aCapo
        testo += aCapo

        return testo
    }// fine del metodo


    public String getNomeBody(ArrayList listaVoci, String nome) {
        String testo = ''
        String tagMaschio = 'M'
        String tagFemmina = 'F'
        ArrayList listaVociMaschili
        ArrayList listaVociFemminili
        boolean usaTerzoLivello = false

        listaVociMaschili = this.selezionaGenere(listaVoci, tagMaschio)
        listaVociFemminili = this.selezionaGenere(listaVoci, tagFemmina)

        if (listaVociMaschili && listaVociFemminili) {
            usaTerzoLivello = true
            testo += '\n==Uomini==\n'
            testo += this.getNomeBodyBase(listaVociMaschili, usaTerzoLivello)
            testo += '\n==Donne==\n'
            testo += this.getNomeBodyBase(listaVociFemminili, usaTerzoLivello)
        } else {
            if (listaVociMaschili) {
                testo += this.getNomeBodyBase(listaVociMaschili, usaTerzoLivello)
            }// fine del blocco if
            if (listaVociFemminili) {
                testo += this.getNomeBodyBase(listaVociFemminili, usaTerzoLivello)
            }// fine del blocco if
        }// fine del blocco if-else

        return testo
    }// fine del metodo

    public String getNomeBodyBase(ArrayList listaVoci, boolean usaTerzoLivello) {
        String testo = ''
        Map mappa
        String aCapo = '\n'
        String chiave
        ArrayList<String> lista
        int num = 0
        String tagIni = '=='
        String tagEnd = '=='

        if (usaTerzoLivello) {
            tagIni = '==='
            tagEnd = '===\n----\n'
        }// fine del blocco if-else

        mappa = this.getMappaAttività(listaVoci)
        mappa = this.ordinaMappa(mappa)
        if (mappa) {
            mappa?.each {
                chiave = it.key
                lista = (ArrayList<String>) mappa.get(chiave)
                num += lista.size()
                testo += tagIni
                testo += chiave
                testo += tagEnd
                testo += aCapo
                testo += getParagrafoDidascalia(lista)
                testo += aCapo
                testo += aCapo
            }// fine del ciclo each
        }// fine del blocco if

        return testo
    }// fine del metodo

    public ArrayList selezionaGenere(ArrayList<BioGrails> listaVoci, String tag) {
        ArrayList lista = null
        BioGrails bio

        if (listaVoci && listaVoci.size() > 0 && tag) {
            lista = new ArrayList()
            listaVoci?.each {
                bio = it
                if (bio.sesso.equals(tag)) {
                    lista.add(bio)
                }// fine del blocco if
            } // fine del ciclo each
        }// fine del blocco if

        return lista
    }// fine del metodo

    // raggruppa per attività una lista di biografie
    // costruisce una mappa con:
    // una chiave per ogni attività
    // una lista di didascalie
    // inserisce solo le attività utilizzate
    public Map getMappaAttività(ArrayList<BioGrails> listaVoci) {
        LinkedHashMap<String, ArrayList<String>> mappa = null
        String didascalia = ''
        String chiaveOld = ''
        String chiave = ''
        ArrayList<String> lista
        BioGrails bio
        String attivita

        if (listaVoci) {
            mappa = new LinkedHashMap<String, ArrayList<String>>()
            listaVoci?.each {
                bio = it
                try { // prova ad eseguire il codice
                    if (bio.didascaliaListe) {
                        didascalia = bio.didascaliaListe
                    }// fine del blocco if
                } catch (Exception unErrore) { // intercetta l'errore
                    didascalia = creaDidascaliaAlVolo(bio)
                }// fine del blocco try-catch
//                didascalia = bio.didascaliaBase
                attivita = bio.attivita
                if (attivita) {
                    chiave = this.getAttivita(bio)
                    if (!chiave) {
                        chiave = tagPunti
                    }// fine del blocco if
                } else {
                    chiave = tagPunti
                }// fine del blocco if-else

                if (chiave.equals(chiaveOld)) {
                    lista = mappa.get(chiave)
                    lista.add(didascalia)
                } else {
                    if (mappa.get(chiave)) {
                        lista = mappa.get(chiave)
                        lista.add(didascalia)
                    } else {
                        lista = new ArrayList<String>()
                        lista.add(didascalia)
                        mappa.put(chiave, lista)
                        chiaveOld = chiave
                    }// fine del blocco if-else
                }// fine del blocco if-else
            }// fine del ciclo each
        }// fine del blocco if

        return mappa
    }// fine del metodo

    // se manca la didascalia, la crea al volo
    public String creaDidascaliaAlVolo(BioGrails bio) {
        String didascaliaTxt = ''
        long grailsId
        DidascaliaBio didascaliaObj

        if (bio) {
            grailsId = bio.id
            didascaliaObj = new DidascaliaBio(grailsId)
            didascaliaObj.setInizializza()
            didascaliaTxt = didascaliaObj.getTestoEstesaSimboli()
        }// fine del blocco if

        return didascaliaTxt
    }// fine del metodo

    // restituisce il nome dell'attività
    // restituisce il plurale
    // restituisce il primo carattere maiuscolo
    // aggiunge un link alla voce di riferimento
    public String getAttivita(BioGrails bio) {
        String attivitaLinkata = ''
        String attivita = ''
        String singolare
        String plurale
        Attivita attivitaRecord
        boolean link = this.titoloParagrafoConLink

        if (bio) {
            singolare = bio.attivita
            if (singolare) {
                attivitaRecord = Attivita.findBySingolare(singolare)
                if (attivitaRecord) {
                    plurale = attivitaRecord.plurale
                    if (plurale) {
                        attivita = LibTesto.primaMaiuscola(plurale)
                        if (attivita) {
                            attivita = attivita.trim()
                            if (link) {
                                def professione = Professione.findBySingolare(singolare)
                                attivitaLinkata = '[['
                                if (professione) {
                                    attivitaLinkata += LibTesto.primaMaiuscola(professione.voce)
                                } else {
                                    attivitaLinkata += LibTesto.primaMaiuscola(singolare)
                                }// fine del blocco if-else
                                attivitaLinkata += '|'
                                attivitaLinkata += attivita
                                attivitaLinkata += ']]'
                            } else {
                                attivitaLinkata = attivita
                            }// fine del blocco if-else
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        return attivitaLinkata
    }// fine del metodo

    /**
     * Ordina una mappa
     *
     * @param mappa non ordinata
     * @return mappa ordinata
     */
    public Map ordinaMappa(Map mappaIn) {
        // variabili e costanti locali di lavoro
        Map mappaOut = mappaIn
        ArrayList<String> listaChiavi
        String chiave
        def valore

        if (mappaIn && mappaIn.size() > 1) {
            listaChiavi = mappaIn.keySet()
            listaChiavi.remove(tagPunti) //elimino l'asterisco (per metterlo in fondo)
            listaChiavi.sort()
            if (listaChiavi) {
                mappaOut = new LinkedHashMap()
                listaChiavi?.each {
                    chiave = it
                    valore = mappaIn.get(chiave)
                    mappaOut.put(chiave, valore)
                }// fine del blocco if

                // aggiungo (in fondo) l'asterisco. Se c'è.
                valore = mappaIn.get(tagPunti)
                if (valore) {
                    mappaOut.put(tagPunti, valore)
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        // valore di ritorno
        return mappaOut
    }// fine della closure

    public String getParagrafoDidascalia(ArrayList<String> nomi) {
        String testo = ''
        String nome
        String tag = ''

        if (nomi) {
            nomi?.each {
                nome = it
                testo += '*'
                testo += nome
                testo += '\n'
            }// fine del ciclo each
        }// fine del blocco if

        return testo.trim()
    }// fine del metodo


    public String getNomeFooter(String nome) {
        String testo = ''
        String aCapo = '\n'

//        testo += '==Voci correlate=='
//        testo += aCapo
//        testo += aCapo
//        testo += '*[[Progetto:Antroponimi/Nomi]]'
//        testo += aCapo
//        testo += '*[[Progetto:Antroponimi/Didascalie]]'
//        testo += aCapo
//        testo += aCapo
        testo += '<noinclude>'
        testo += "[[Categoria:Liste di persone per nome|${nome}]]"
        testo += '</noinclude>'
        testo += aCapo

        return testo
    }// fine del metodo

    // pagina di controllo/servizio
    private creaPaginaDidascalie() {
        String titolo = progetto + 'Didascalie'
        String testo = ''
        String summary = 'Biobot'

        testo += getDidascalieHeader()
        testo += getDidascalieBody()
        testo += getDidascalieFooter()

        //registra la pagina
        new Edit(titolo, testo, summary)
    }// fine della closure


    private static String getDidascalieHeader() {
        String testo = ''
        String dataCorrente = LibTime.getGioMeseAnnoLungo(new Date())
        String aCapo = '\n'
        String bot = getBotLink()

        testo += '__NOTOC__'
        testo += '<noinclude>'
        testo += "{{StatBio"
        testo += "|data=$dataCorrente}}"
        testo += aCapo

        testo += "Pagina di servizio per il '''controllo'''<ref>Attualmente il ${bot} usa il tipo '''8''' (estesa con simboli)</ref> delle didascalie utilizzate nelle ''Liste di persone di nome''..."
        testo += aCapo
        testo += 'Le didascalie possono essere di diversi tipi:'
        testo += aCapo

        return testo
    }// fine della closure

    private static String getDidascalieBody() {
        String testo = ''
        String titoloEsempio = 'Silvio Spaventa'
        WrapBio bio = new WrapBio(titoloEsempio)
        BioGrails bioGrails = BioGrails.findByTitle(titoloEsempio)
        long grailsId = 0

        if (bioGrails) {
            grailsId = bioGrails.id
        }// fine del blocco if

        if (grailsId) {
            DidascaliaTipo.values().each {
                if (it.stampaTest) {
                    testo += rigaDidascalia(it)
                    testo += rigaEsempio(it, grailsId)
                }// fine del blocco if
            }// fine di each
        }// fine del blocco if

        return testo
    }// fine della closure

    private static String rigaDidascalia(DidascaliaTipo tipo) {
        String testo = ''
        String tag = ': '
        boolean usaRef = true
        String ref;
        ref = tipo.getRef();

        testo += '#'
        // testo += "'''"
        testo += LibTesto.primaMaiuscola(tipo.getSigla())
        // testo += "'''"
        testo += tag
        testo += tipo.getDescrizione()
        if (usaRef) {
            if (ref != null) {
                if (!ref.equals("")) {
                    testo += ref
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if

        return testo
    }// fine della closure

    private static String rigaEsempio(DidascaliaTipo tipo, long grailsId) {
        String testo = ''
        String aCapo = '\n'
        String testoDidascalia = ''
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(grailsId)
        didascalia.setInizializza()

        switch (tipo) {
            case DidascaliaTipo.base:
                testoDidascalia = didascalia.getTestoBase()
                break
            case DidascaliaTipo.crono:
                testoDidascalia = didascalia.getTestoCrono()
                break
            case DidascaliaTipo.cronoSimboli:
                testoDidascalia = didascalia.getTestoCrono()
                break
            case DidascaliaTipo.semplice:
                testoDidascalia = didascalia.getTestoSemplice()
                break
            case DidascaliaTipo.completa:
                testoDidascalia = didascalia.getTestoCompleta()
                break
            case DidascaliaTipo.completaSimboli:
                testoDidascalia = didascalia.getTestoCompletaSimboli()
                break
            case DidascaliaTipo.estesa:
                testoDidascalia = didascalia.getTestoEstesa()
                break
            case DidascaliaTipo.estesaSimboli:
                testoDidascalia = didascalia.getTestoEstesaSimboli()
                break
            default: // caso non definito
                break
        } // fine del blocco switch

        testo += '<BR>'
        testo += "'''"
        testo += testoDidascalia
        testo += "'''"
        testo += aCapo

        return testo
    }// fine della closure

    private static String getDidascalieFooter() {
        String testoFooter = ''
        String aCapo = '\n'

        testoFooter += '==Note=='
        testoFooter += aCapo
        testoFooter += '<references/>'
        testoFooter += aCapo
        testoFooter += '==Voci correlate=='
        testoFooter += aCapo
        testoFooter += '*[[Progetto:Antroponimi]]'
        testoFooter += aCapo
        testoFooter += '*[[Progetto:Antroponimi/Nomi]]'
        testoFooter += aCapo
        testoFooter += '*[[Progetto:Antroponimi/Liste]]'
        testoFooter += aCapo
        testoFooter += aCapo
        testoFooter += '<noinclude>'
        testoFooter += '[[Categoria:Liste di persone per nome| ]]'
        testoFooter += '</noinclude>'

        return testoFooter
    }// fine della closure

    private static String getBotLink() {
        String testo = ''

        testo += "'''"
        testo += '[[Utente:Biobot|<span style="color:green;">bot</span>]]'
        testo += "'''"

        return testo
    }// fine della closure

    /**
     * Crea la pagina riepilogativa
     */
    public creaPaginaRiepilogativa(ArrayList<String> listaVoci) {
        String testo = ''
        String titolo = progetto + 'Nomi'
        String summary = 'Biobot'

        testo += getRiepilogoHead()
        testo += getRiepilogoBody(listaVoci)
        testo += getRiepilogoFooter()

        new Edit(titolo, testo, summary)
    }// fine del metodo


    public String getRiepilogoHead() {
        String testo = ''
        String dataCorrente = LibTime.getGioMeseAnnoLungo(new Date())
        String aCapo = '\n'

        testo += '__NOTOC__'
        testo += '<noinclude>'
        testo += "{{StatBio|data=$dataCorrente}}"
        testo += '</noinclude>'
        testo += aCapo

        return testo
    }// fine del metodo


    public String getRiepilogoBody(ArrayList<String> listaVoci) {
        String testo = ''
        int taglio = Preferenze.getInt(LibBio.TAGLIO_ANTROPONIMI)
        LinkedHashMap mappa = null
        String chiave
        String nome
        def lista
        def ricorrenze = LibTesto.formatNum(taglio)
        String aCapo = '\n'

        testo += '==Nomi=='
        testo += aCapo
        testo += 'Elenco dei '
        testo += "''' "
        testo += LibTesto.formatNum(listaVoci.size())
        testo += "'''"
        testo += ' nomi che hanno più di '
        testo += "'''"
        testo += ricorrenze
        testo += "'''"
        testo += ' ricorrenze nelle voci biografiche'
        testo += aCapo

        testo += aCapo
        testo += '{{Div col|cols=3}}'
        if (listaVoci) {
            listaVoci.each {
                nome = it
                testo += aCapo
                testo += this.getRiga(nome)
            }// fine del ciclo each
        }// fine del blocco if
        testo += aCapo
        testo += '{{Div col end}}'
        testo += aCapo

        return testo
    }// fine del metodo

    public String getRiga(String nome) {
        String testo = ''
        String tag
        String aCapo = '\n'
        String numVoci

        if (nome) {
            tag = tagTitolo + nome
            testo += '*'
            testo += '[['
            testo += tag
            testo += '|'
            testo += nome
            testo += ']]'
            if (LibPref.getBool('usaOccorrenzeAntroponimi')) {
                numVoci = numeroVociCheUsanoNome(nome)
                testo += ' ('
                testo += "'''"
                testo += LibTesto.formatNum(numVoci)
                testo += "'''"
                testo += ' )'
            }// fine del blocco if
            testo += aCapo
        }// fine del blocco if

        return testo.trim()
    }// fine del metodo

    public String getRiepilogoFooter() {
        String testo = ''
        String aCapo = '\n'

        testo += aCapo
        testo += '==Voci correlate=='
        testo += aCapo
        testo += '*[[Progetto:Antroponimi]]'
        testo += aCapo
        testo += '*[[Progetto:Antroponimi/Liste]]'
        testo += aCapo
        testo += '*[[Progetto:Antroponimi/Didascalie]]'
        testo += aCapo
        testo += aCapo
        testo += '<noinclude>'
        testo += '[[Categoria:Liste di persone per nome| ]]'
        testo += '</noinclude>'

        return testo
    }// fine del metodo

} // fine della service classe
