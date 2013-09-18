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

class GiornoService {

    /**
     * Crea tutti i records
     *
     * Crea 366 records per tutti i giorni dell'anno
     * La colonna n, è il progressivo del giorno negli anni normali
     * La colonna b, è il progressivo del giorno negli anni bisestili
     */
    public creazioneIniziale() {
        // variabili e costanti locali di lavoro
        def lista
        Map mappa
        int normale
        int bisestile
        String nome
        String titolo

        //--cancella tutti i records
        Giorno.executeUpdate('delete Giorno')

        //costruisce i 366 records
        lista = LibTime.getAllGiorni()
        lista.each {
            mappa = (Map) it
            normale = 0
            bisestile = 0
            nome = ''
            titolo = ''
            if (mappa.normale) {
                normale = mappa.normale
            }// fine del blocco if
            if (mappa.bisestile) {
                bisestile = mappa.bisestile
            }// fine del blocco if
            if (mappa.nome) {
                nome = mappa.nome
            }// fine del blocco if
            if (mappa.titolo) {
                titolo = mappa.titolo
            }// fine del blocco if

            new Giorno(normale: normale, bisestile: bisestile, nome: nome, titolo: titolo).save()
        }//fine di each

        log.info 'Operazione effettuata. Sono stati creati tutti i giorni necessari per il bot'
    } // fine del metodo

    /**
     * Sporca o pulisce tutti i records
     */
    public regolaSporco(boolean sporca) {
        def records = Giorno.getAll()

        if (records) {
            records.each {
                it.sporcoNato = sporca
                it.sporcoMorto = sporca
                it.save(flush: true)
            }//fine di each
        }// fine del blocco if

        if (sporca) {
            log.info 'Operazione effettuata. Sono stati sporcati tutti i giorni.'
        } else {
            log.info 'Operazione effettuata. Sono stati puliti tutti i giorni.'
        }// fine del blocco if-else
    }// fine del metodo

    /**
     * Sporca il record
     * Se non esiste non fa nulla
     */
    public static sporcoNato(def giornoDaSporcare) {
        // variabili e costanti locali di lavoro
        Giorno giorno = null

        if (giornoDaSporcare) {
            if (giornoDaSporcare instanceof Long) {
                giorno = Giorno.findById(giornoDaSporcare)
            }// fine del blocco if
            if (giornoDaSporcare instanceof Integer) {
                giorno = Giorno.findById(giornoDaSporcare)
            }// fine del blocco if
            if (giornoDaSporcare instanceof String) {
                giorno = Giorno.findByNome(giornoDaSporcare)
            }// fine del blocco if

            if (giorno) {
                giorno.sporcoNato = true
                giorno.save(flush: true)
            }// fine del blocco if
        }// fine del blocco if

        // valore di ritorno
        return giorno
    } // fine della closure

    /**
     * Sporca il record
     *
     * Se non esiste non fa nulla
     */
    public static sporcoMorto(def giornoDaSporcare) {
        // variabili e costanti locali di lavoro
        Giorno giorno = null

        if (giornoDaSporcare instanceof Long) {
            giorno = Giorno.findById(giornoDaSporcare)
        }// fine del blocco if
        if (giornoDaSporcare instanceof Integer) {
            giorno = Giorno.findById(giornoDaSporcare)
        }// fine del blocco if
        if (giornoDaSporcare instanceof String) {
            giorno = Giorno.findByNome(giornoDaSporcare)
        }// fine del blocco if

        if (giorno) {
            giorno.sporcoMorto = true
            giorno.save(flush: true)
        }// fine del blocco if

        // valore di ritorno
        return giorno
    } // fine della closure

} // fine della service classe
