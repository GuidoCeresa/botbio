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

import it.algos.algoslib.Lib

class BotbioTagLib {
    static namespace = "bio" //stand for myvitaback
    static String app = 'biobot'

    /**
     * Lista dei controllers per la videata Home <br>
     *
     * @return testo del tag
     */
    def listaControllers = {
        String testoOut = ''

        testoOut += tagController('Preferenze')
        testoOut += tagController('Logo')
        testoOut += tagController('Evento')
        testoOut += tagController('Versione')
        testoOut += tagController('LoginWiki')
        testoOut += tagController('Anno')
        testoOut += tagController('Giorno')
        testoOut += tagController('Attivita')
        testoOut += tagController('Nazionalita')
        testoOut += tagController('Bio')
        testoOut += tagController('BioWiki')
        testoOut += tagController('BioGrails')

        out << testoOut
    }// fine della closure

    //--Costruisce il tag controller per il testo indicato
    public static String tagController(String controller, String titolo, String azione) {
        String testoOut = ''

        if (controller && titolo) {
            testoOut += '<li class="controller">'
            testoOut += '<a href="/botbio/'
            testoOut += Lib.Txt.primaMinuscola(controller)
            testoOut += '/'
            if (azione) {
                testoOut += azione
            } else {
                testoOut += 'index'
            }// fine del blocco if-else
            testoOut += '">'
            testoOut += titolo
            testoOut += '</a>'
            testoOut += '</li>'
        }// fine del blocco if

        return testoOut
    } // fine del metodo statico

    //--Costruisce il tag controller per il testo indicato
    public static String tagController(String controller, String titolo) {
        return tagController(controller, titolo, '')
    } // fine del metodo statico

    //--Costruisce il tag controller per il testo indicato
    public static String tagController(String controller) {
        return tagController(controller, controller)
    } // fine del metodo statico

} // fine della tag library

