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

class BioGrails  {

    /** nomi interni dei campi (ordine non garantito) */
    //--parametri wiki
    int pageid
    String title

    //--parametri del template Bio presenti nel template della voce
    //--alcuni sono presenti nella superclasse
    String nome = ''
    String cognome = ''
    String forzaOrdinamento = ''
    String sesso = ''
    String localitaNato //luogoNascita oppure luogoNascitaLink se esiste in BioWiki
    String localitaMorto //luogoMorte oppure luogoMorteLink se esiste in BioWiki

    String attivita = ''
    String attivita2 = ''
    String attivita3 = ''
    String nazionalita = ''

    String didascaliaBase
    String didascaliaGiornoNato
    String didascaliaGiornoMorto
    String didascaliaAnnoNato
    String didascaliaAnnoMorto

    // altri campi di collegamenti alle altre tavole specializzate
    Giorno giornoMeseNascitaLink = null
    Giorno giornoMeseMorteLink = null
    Anno annoNascitaLink = null
    Anno annoMorteLink = null
    Attivita attivitaLink = null
    Attivita attivita2Link = null
    Attivita attivita3Link = null
    Nazionalita nazionalitaLink = null

    //--altri campi di controllo
//    boolean meseNascitaValido = false
//    boolean meseMorteValido = false
//    boolean annoNascitaValido = false
//    boolean annoMorteValido = false
//    boolean attivitaValida = false
//    boolean attivita2Valida = false
//    boolean attivita3Valida = false
//    boolean nazionalitaValida = false
//    boolean meseNascitaErrato = false
//    boolean meseMorteErrato = false
//    boolean annoNascitaErrato = false
//    boolean annoMorteErrato = false
//    boolean attivitaErrato = false
//    boolean attivita2Errato = false
//    boolean attivita3Errato = false
//    boolean nazionalitaErrato = false

    /**
     * regolazione delle proprietà di ogni campo
     * l'ordine con cui vengono elencati qui,
     * viene rispettato nella lista e nella scheda standard
     * la possibilità di avere valori nulli, di default è false
     */
    static constraints = {

        pageid(unique: true)
        title(unique: true, nullable: false, blank: false)

        nome(nullable: true, blank: true)
        cognome(nullable: true, blank: true)
        forzaOrdinamento(nullable: true, blank: true)
        sesso(nullable: true, blank: true)

        attivita(nullable: true, blank: true)
        attivita2(nullable: true, blank: true)
        attivita3(nullable: true, blank: true)
        nazionalita(nullable: true, blank: true)
        localitaNato(nullable: true, blank: true)
        localitaMorto(nullable: true, blank: true)

        didascaliaBase(nullable: true, blank: true)
        didascaliaGiornoNato(nullable: true, blank: true)
        didascaliaGiornoMorto(nullable: true, blank: true)
        didascaliaAnnoNato(nullable: true, blank: true)
        didascaliaAnnoMorto(nullable: true, blank: true)

        giornoMeseNascitaLink(nullable: true)
        giornoMeseMorteLink(nullable: true)
        annoNascitaLink(nullable: true)
        annoMorteLink(nullable: true)
        attivitaLink(nullable: true)
        attivita2Link(nullable: true)
        attivita3Link(nullable: true)
        nazionalitaLink(nullable: true)
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
    // nomi dei campi sul database, di default usa il nome interno del campo
    // la superclasse di ogni domainClass inserisce i campi dateCreated e lastUpdated
    // che vengono aggiornati automaticamente da GORM
    // per disabilitare l'automatismo, mettere a false la proprietà autoTimestamp nella classe specifica
    // Grails inserisce automaticamente la proprietà/campo 'versione' per l'optimistic locking
    // per disabilitare l'automatismo, mettere a false la proprietà version nella classe specifica
    static mapping = {
        tablePerHierarchy true  //standard
    } // end of static mapping

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
