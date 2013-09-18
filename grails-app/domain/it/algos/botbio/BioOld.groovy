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

class BioOld {

//    /** nomi interni dei campi (ordine non garantito) */
//    // parametri wiki
//    int pageid
//    int ns
//    String title
//    Date touched
//    int revid
//    int length
//    String user
//    Date timestamp
//    String comment
//    String logNote
//    String logErr
//    int langlinks
//
//    // nomi interni dei campi (ordine non garantito)
//    // parametri del template Bio
//    String titolo = ''
//    String nome = ''
//    String cognome = ''
//    String postCognome = ''
//    String postCognomeVirgola = ''
//    String forzaOrdinamento = ''
//    String preData = ''
//    String sesso = ''
//
//    String luogoNascita = ''
//    String luogoNascitaLink = ''
//    String luogoNascitaAlt = ''
//    String giornoMeseNascita = ''
//    String annoNascita = ''
//    String noteNascita = ''
//
//    String luogoMorte = ''
//    String luogoMorteLink = ''
//    String luogoMorteAlt = ''
//    String giornoMeseMorte = ''
//    String annoMorte = ''
//    String noteMorte = ''
//
//    String preAttivita = ''
//    String attivita = ''
//    String epoca = ''
//    String epoca2 = ''
//    String cittadinanza = ''
//    String attivita2 = ''
//    String attivita3 = ''
//    String attivitaAltre = ''
//    String nazionalita = ''
//    String nazionalitaNaturalizzato = ''
//    String postNazionalita = ''
//
//    String categorie = ''
//    String fineIncipit = ''
//    String punto = ''
//    String immagine = ''
//    String didascalia = ''
//    String didascalia2 = ''
//    String dimImmagine = ''
//
//
//    // altri campi di collegamenti alle altre tavole specializzate
//    Giorno giornoMeseNascitaLink = null
//    Giorno giornoMeseMorteLink = null
//    Anno annoNascitaLink = null
//    Anno annoMorteLink = null
//    Attivita attivitaLink = null
//    Attivita attivita2Link = null
//    Attivita attivita3Link = null
//    Nazionalita nazionalitaLink = null
//
//    // altri campi di controllo
//    boolean extra = false         //
//    String extraLista = ''         //
//    boolean graffe = false        // {{
//    boolean note = false          // <ref
//    boolean nascosto = false      // <!--
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

    //serve per le voci che sono state modificate sul server wiki rispetto alla versione sul database
    //si basa sul parametro lastrevid
    //per sicurezza è false (quindi all'inizio controllo tutto)
    boolean allineata = false

    //serve per le voci che sono state ricaricate su wiki dopo controllo e formattazione del template
    boolean controllato = false

    /**
     * regolazione delle proprietà di ogni campo
     * l'ordine con cui vengono elencati qui,
     * viene rispettato nella lista e nella scheda standard
     * la possibilità di avere valori nulli, di default è false
     */
    static constraints = {
//        pageid(unique: true)
//        ns()
//        title(nullable: true)
//        touched(nullable: true, formatoData: new SimpleDateFormat('d MMM yy'))
//        revid()
//        length()
//        user(nullable: true)
//        timestamp(nullable: true, formatoData: new SimpleDateFormat('d MMM yy'))
//        comment(nullable: true)
//        logNote(nullable: true)
//        logErr(nullable: true)
//        langlinks()
//        extra()
//
//        titolo()
//        nome()
//        cognome()
//        postCognome()
//        postCognomeVirgola()
//        forzaOrdinamento()
//        preData()
//        sesso()
//
//        luogoNascita()
//        luogoNascitaLink()
//        luogoNascitaAlt()
//        giornoMeseNascita()
//        annoNascita()
//        noteNascita()
//
//        luogoMorte()
//        luogoMorteLink()
//        luogoMorteAlt()
//        giornoMeseMorte()
//        annoMorte()
//        noteMorte()
//
//        preAttivita()
//        attivita()
//        epoca()
//        epoca2()
//        cittadinanza()
//        attivita2()
//        attivita3()
//        attivitaAltre()
//        nazionalita()
//        nazionalitaNaturalizzato()
//        postNazionalita()
//
//        categorie()
//        fineIncipit()
//        punto()
//        immagine()
//        didascalia()
//        didascalia2()
//        dimImmagine()
//
//        giornoMeseNascitaLink(nullable: true)
//        giornoMeseMorteLink(nullable: true)
//        annoNascitaLink(nullable: true)
//        annoMorteLink(nullable: true)
//        attivitaLink(nullable: true)
//        attivita2Link(nullable: true)
//        attivita3Link(nullable: true)
//        nazionalitaLink(nullable: true)
//
//        extraLista()
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
        tablePerHierarchy true  //standard

        // registrazione automatica delle date di creazione e update dei records
        // default del timestamping è true
        autoTimestamp true

        // numero di versione del singolo record per l'optimistic locking
        // default del versioning è true
        version true

//        // stringa di lunghezza variabile
//        title type: 'text'
//        user type: 'text'
//        comment type: 'text'
//        logNote type: 'text'
//        logErr type: 'text'
//
//        // stringa di lunghezza variabile
//        titolo type: 'text'
//        nome type: 'text'
//        cognome type: 'text'
//        postCognome type: 'text'
//        postCognomeVirgola type: 'text'
//        forzaOrdinamento type: 'text'
//        preData type: 'text'
//        sesso type: 'text'
//
//        luogoNascita type: 'text'
//        luogoNascitaLink type: 'text'
//        luogoNascitaAlt type: 'text'
//        giornoMeseNascita type: 'text'
//        annoNascita type: 'text'
//        noteNascita type: 'text'
//
//        luogoMorte type: 'text'
//        luogoMorteLink type: 'text'
//        luogoMorteAlt type: 'text'
//        giornoMeseMorte type: 'text'
//        annoMorte type: 'text'
//        noteMorte type: 'text'
//
//        preAttivita type: 'text'
//        attivita type: 'text'
//        epoca type: 'text'
//        epoca2 type: 'text'
//        attivita2 type: 'text'
//        attivita3 type: 'text'
//        attivitaAltre type: 'text'
//        nazionalita type: 'text'
//        nazionalitaNaturalizzato type: 'text'
//        postNazionalita type: 'text'
//
//        categorie type: 'text'
//        fineIncipit type: 'text'
//        punto type: 'text'
//        immagine type: 'text'
//        didascalia type: 'text'
//        didascalia2 type: 'text'
//        dimImmagine type: 'text'
//
//        premio1 type: 'text'
//        specialita1 type: 'text'
//        annoPremio1 type: 'text'
//        premio2 type: 'text'
//        specialita2 type: 'text'
//        annoPremio2 type: 'text'
//        premio3 type: 'text'
//        specialita3 type: 'text'
//        annoPremio3 type: 'text'
//        premio4 type: 'text'
//        specialita4 type: 'text'
//        annoPremio4 type: 'text'
//
//        extraLista type: 'text'
    } // end of static mapping

    /**
     * valore di testo restituito per una istanza della classe
     */
    String toString() {
        return  ''
//        return getNome() + ' ' + getCognome()
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
