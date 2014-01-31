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
import it.algos.algospref.Preferenze

class AntroponimoService {

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
        if (usaNomeSingolo) {
            // query = "select id from Biografia where nome='${nome}' order by nome desc"
            listaGrezza = BioGrails.findAllByNome(nome, [order: 'nome'])
//            def c = Biografia.createCriteria()
//            listaGrezza = c.list {
//                like("nome", "${nome} %")
//                order("nome", "desc")
//            }
        } else {
            //query = "select id from Biografia where nome like '${nome} %' order by nome desc"
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

} // fine della service classe
