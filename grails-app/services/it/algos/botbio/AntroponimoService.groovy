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
import it.algos.algoswiki.Edit

class AntroponimoService {

    private tagTitolo = 'Lista di persone di nome '
    private static String aCapo = '\n'

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
        int taglio = Preferenze.getInt(LibBio.TAGLIO_ANTROPONIMI)
        ArrayList listaNomi
        String query = 'select nome from Antroponimo where voci>'
        query += taglio
        query += ' order by nome'

        //esegue la query
        listaNomi = Antroponimo.executeQuery(query)

        //crea le pagine dei singoli nomi
        listaNomi?.each {
            elaboraSingoloNome((String) it)
        }// fine del ciclo each

        //crea la pagina di controllo didascalie
//        this.creaPaginaDidascalie()

        //crea la pagina riepilogativa
//        if (listaNomi) {
//            creaPaginaRiepilogativa(listaNomi)
//        }// fine del blocco if

    }// fine del metodo

    /**
     * Elabora la pagina per un singolo nome
     */
    public void elaboraSingoloNome(String nome) {
        String titolo
        String testo = ''
        String summary = 'BioBot'
        ArrayList<BioGrails> listaBiografieDiversePerAccento

        titolo = tagTitolo + nome
        listaBiografieDiversePerAccento = biografieDiversePerAccento(nome)

        //header
        testo += this.getNomeHead(listaBiografieDiversePerAccento.size())

        //body
        testo += this.getNomeBody(listaBiografieDiversePerAccento, nome)

        //footer
        testo += this.getNomeFooter(nome)

        //registra la pagina
        new Edit(titolo, testo, summary)
    }// fine del metodo

    //--costruisce una lista di biografie che 'usano' il nome
    //--se il flag usaNomeSingolo è vero, il nome della voce deve coincidere esattamente col parametro in ingresso
    //--se il flag usaNomeSingolo è falso, il nome della voce deve iniziare col parametro
    private static ArrayList<BioGrails> biografieDiversePerAccento(String nome) {
        ArrayList<BioGrails> listaBiografieDiversePerAccento = new ArrayList()
        BioGrails bio
        String nomeBio
        boolean usaNomeSingolo = Preferenze.getBool('usaNomeSingolo')
        ArrayList<BioGrails> listaGrezza = null

        //--recupera una lista 'grezza' di tutti i nomi
        //@todo non sono riuscito a sviluppare la differenza (per ora)
        if (usaNomeSingolo) {
            listaGrezza = BioGrails.findAllByNome(nome, [order: 'nome'])
        } else {
            listaGrezza = BioGrails.findAllByNome(nome, [order: 'nome'])
        }// fine del blocco if-else

        //--i nomi sono differenziati in base all'accento
        listaGrezza?.each {
            bio = (BioGrails) it
            nomeBio = bio.nome
            //nomeBio = nomeBio.toLowerCase()       //@todo va in errore per GianCarlo
            if (nomeBio.equalsIgnoreCase(nome)) {
                listaBiografieDiversePerAccento.add(bio)
            }// fine del blocco if
        } // fine del ciclo each

        return listaBiografieDiversePerAccento
    }// fine del metodo


    public String getNomeHead(int num) {
        String testo = ''
        String dataCorrente = LibTime.getGioMeseAnnoLungo(new Date())
        String numero = ''
        boolean eliminaIndice = false

        if (num) {
            numero = LibTesto.formatNum(num)
        }// fine del blocco if

        if (eliminaIndice) {
            testo += '__NOTOC__'
        }// fine del blocco if
        testo += '<noinclude>'
        testo += "{{StatBio"
        if (numero) {
            testo += "|bio=$numero"
        }// fine del blocco if
        testo += "|data=$dataCorrente}}"
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
        def lista
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
                lista = mappa.get(chiave)
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
                didascalia = bio.didascaliaBase
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

} // fine della service classe