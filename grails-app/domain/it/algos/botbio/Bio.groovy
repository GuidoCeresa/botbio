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

class Bio {

    /** nomi interni dei campi (ordine non garantito) */
    //--parametri wiki
    //--sono comuni alle due sottoclassi
    int pageid
    String title

    //--parametri del template Bio presenti nel template della voce
    //--sono comuni alle due sottoclassi
    String nome = ''
    String cognome = ''
    String forzaOrdinamento = ''
    String sesso = ''

    String attivita = ''
    String attivita2 = ''
    String attivita3 = ''
    String nazionalita = ''

    //serve per le voci che sono state modificate sul server wiki rispetto alla versione sul database
    //si basa sul parametro lastrevid
    //per sicurezza è false (quindi all'inizio controllo tutto)
    boolean allineata = false

    //serve per le voci che sono state ricaricate su wiki dopo controllo e formattazione del template
    boolean controllato = false

    //serve per le voci che sono state elaborate e trasferite (creando od aggiornando il record) su BioGrails
    boolean elaborata = false

    /**
     * regolazione delle proprietà di ogni campo
     * l'ordine con cui vengono elencati qui,
     * viene rispettato nella lista e nella scheda standard
     * la possibilità di avere valori nulli, di default è false
     */
    static constraints = {

    } // end of static constraints

    /**
     * Grails support two tipe of Inheritance:
     * 1) table-per-hierarchy mapping (default)
     * 2) table-per-subclasses
     * By default GORM classes use table-per-hierarchy inheritance mapping.
     * This has the disadvantage that columns cannot have a NOT-NULL constraint applied to them at the database level.
     * If you would prefer to use a table-per-subclass inheritance strategy you can do so as follows:
     *         tablePerHierarchy false
     * The mapping of the root Xyz class specifies that it will not be using table-per-hierarchy mapping for all child classes.
     */
    static mapping = {
        tablePerHierarchy false  //standard

        // registrazione automatica delle date di creazione e update dei records
        // default del timestamping è true
        autoTimestamp true

        // numero di versione del singolo record per l'optimistic locking
        // default del versioning è true
        version true

    } // end of static mapping

    /**
     * valore di testo restituito per una istanza della classe
     */
    String toString() {
        return getNome() + ' ' + getCognome()
    } // end of toString

    /**
     * GORM supports the registration of events as methods that get fired
     * when certain events occurs such as deletes, inserts and updates
     * The following is a list of supported events:
     * beforeInsert - Executed before an object is initially persisted to the database
     * beforeUpdate - Executed before an object is updated
     * beforeDelete - Executed before an object is deleted
     * beforeValidate - Executed before an object is validated
     * afterInsert - Executed after an object is persisted to the database
     * afterUpdate - Executed after an object has been updated
     * afterDelete - Executed after an object has been deleted
     * onLoad - Executed when an object is loaded from the database
     */

    /**
     * metodo chiamato automaticamente da Grails
     * prima di creare un nuovo record
     */
    def beforeInsert = {
    } // end of def beforeInsert

    /**
     * metodo chiamato automaticamente da Grails
     * prima di registrare un record esistente
     */
    def beforeUpdate = {
    } // end of def beforeUpdate

    /**
     * metodo chiamato automaticamente da Grails
     * prima di cancellare un record
     */
    def beforeDelete = {
    } // end of def beforeDelete

    /**
     * metodo chiamato automaticamente da Grails
     * prima di convalidare un record
     */
    def beforeValidate = {
    } // end of def beforeDelete

    /**
     * metodo chiamato automaticamente da Grails
     * dopo aver creato un nuovo record
     */
    def afterInsert = {
    } // end of def beforeInsert

    /**
     * metodo chiamato automaticamente da Grails
     * dopo aver registrato un record esistente
     */
    def afterUpdate = {
    } // end of def beforeUpdate

    /**
     * metodo chiamato automaticamente da Grails
     * dopo aver cancellato un record
     */
    def afterDelete = {
    } // end of def beforeDelete

    /**
     * metodo chiamato automaticamente da Grails
     * dopo che il record è stato letto dal database e
     * le proprietà dell'oggetto sono state aggiornate
     */
    def onLoad = {
    } // end of def onLoad

} // fine della domain classe
