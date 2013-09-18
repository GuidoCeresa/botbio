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

class AnnoService {

    boolean transactional = false
    def sessionFactory

    //--usato nell'ordinamento delle categorie
    public static int ANNO_INIZIALE = 2000

    /**
     * Crea tutti i records
     * Cancella prima quelli esistenti
     *
     * Ante cristo dal 1000
     * Dopo cristo fino al 2030
     */
    public creazioneIniziale() {
        // variabili e costanti locali di lavoro
        def anteCristo = 1000..1
        def dopoCristo = 1..2030
        def annoIniziale = ANNO_INIZIALE
        String tag = ' a.C.'
        int numCat
        String titolo

        //--cancella tutti i records
        Anno.executeUpdate('delete Anno')

        //costruisce gli anni prima di cristo dal 1000
        anteCristo.each {
            numCat = annoIniziale - it
            titolo = it + tag

            new Anno(progressivoCategoria: numCat, titolo: titolo).save()
        } // fine del ciclo each

        //costruisce gli anni dopo cristo fino al 2030
        dopoCristo.each {
            numCat = it + annoIniziale
            titolo = it

            new Anno(progressivoCategoria: numCat, titolo: titolo).save()
        } // fine del ciclo each

        log.info 'Operazione effettuata. Sono stati creati tutti gli anni necessari per il bot'
    }// fine del metodo

    /**
     * Sporca o pulisce tutti i records
     */
    public regolaSporco(boolean sporca) {
        def records = Anno.getAll()

        if (records) {
            records.each {
                it.sporcoNato = sporca
                it.sporcoMorto = sporca
                it.save(flush: true)
            }//fine di each
        }// fine del blocco if

        if (sporca) {
            log.info 'Operazione effettuata. Sono stati sporcati tutti gli anni.'
        } else {
            log.info 'Operazione effettuata. Sono stati puliti tutti gli anni.'
        }// fine del blocco if-else
    }// fine del metodo

    /**
     * Sporca il record
     *
     * Se non esiste non fa nulla
     */
    public static sporcoNato(def annoDaSporcare) {
        // variabili e costanti locali di lavoro
        Anno anno = null

        if (annoDaSporcare instanceof Long) {
            anno = Anno.findById(annoDaSporcare)
        }// fine del blocco if
        if (annoDaSporcare instanceof Integer) {
            anno = Anno.findById(annoDaSporcare)
        }// fine del blocco if
        if (annoDaSporcare instanceof String) {
            anno = Anno.findByTitolo(annoDaSporcare)
        }// fine del blocco if

        if (anno) {
            anno.sporcoNato = true
            anno.save(flush: true)
        }// fine del blocco if

        // valore di ritorno
        return anno
    } // fine della closure

    /**
     * Sporca il record
     *
     * Se non esiste non fa nulla
     */
    public static sporcoMorto(def annoDaSporcare) {
        // variabili e costanti locali di lavoro
        Anno anno = null

        if (annoDaSporcare instanceof Long) {
            anno = Anno.findById(annoDaSporcare)
        }// fine del blocco if
        if (annoDaSporcare instanceof Integer) {
            anno = Anno.findById(annoDaSporcare)
        }// fine del blocco if
        if (annoDaSporcare instanceof String) {
            anno = Anno.findByTitolo(annoDaSporcare)
        }// fine del blocco if

        if (anno) {
            anno.sporcoMorto = true
            anno.save(flush: true)
        }// fine del blocco if

        // valore di ritorno
        return anno
    } // fine della closure

} // fine della service classe
