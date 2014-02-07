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

import java.sql.Timestamp
import java.text.SimpleDateFormat

/**
 * Classe con esattamente i dati esistenti sul server (nel momento in cui li legge)
 * Fotocopia esatta
 */
class BioWiki  {

    /** nomi interni dei campi (ordine non garantito) */
    //--parametri wiki
    int pageid
    String title
    int ns
    Date touched    //ultima visita effettuata da chicchessia sul server wiki - attualmente (27-10-13) non utilizzato
    int revid
    int size
    String user
    Timestamp timestamp //ultima modifica effettuata da chicchessia sul server wiki
    String comment
    String logNote
    String logErr
    int langlinks

    //--parametri del template Bio presenti nel template della voce
    String titolo = ''  //titolo tipo ''dottore'', non TITLE della pagina
    String nome = ''
    String cognome = ''
    String forzaOrdinamento = ''
    String sesso = ''
    String postCognome = ''
    String postCognomeVirgola = ''
    String preData = ''

    String luogoNascita = ''
    String luogoNascitaLink = ''
    String luogoNascitaAlt = ''
    String giornoMeseNascita = ''
    String annoNascita = ''
    String noteNascita = ''

    String luogoMorte = ''
    String luogoMorteLink = ''
    String luogoMorteAlt = ''
    String giornoMeseMorte = ''
    String annoMorte = ''
    String noteMorte = ''

    String preAttivita = ''
    String epoca = ''
    String epoca2 = ''
    String cittadinanza = ''
    String attivitaAltre = ''
    String nazionalitaNaturalizzato = ''
    String postNazionalita = ''

    String attivita = ''
    String attivita2 = ''
    String attivita3 = ''
    String nazionalita = ''

    String categorie = ''
    String fineIncipit = ''
    String punto = ''
    String immagine = ''
    String didascalia = ''
    String didascalia2 = ''
    String dimImmagine = ''

    //--estratto dal testo della voce
    String testoTemplate
    int sizeBio

    //--tempo di DOWNLOAD
    //--uso il formato Timestamp, per confrontarla col campo timestamp
    //--molto meglio che siano esattamente dello stesso tipo
    //--ultima lettura della voce effettuata dal programma Botbio
    //--momento in cui il record BioWiki è stato modificato in allineamento alla voce sul server wiki
    Timestamp ultimaLettura

    //--tempo di UPLOAD
    //--uso il formato Timestamp, per confrontarla col campo timestamp
    //--molto meglio che siano esattamente dello stesso tipo
    //--momento in cui la voce sul server wiki è stata modificata con il WrapBio costruito dal programma
    Timestamp ultimaScrittura

    //--ridondante, costruito con il timestamp esatto della pagina sul server wiki
    //--serve per visualizzare la data in forma ''breve'' più leggibile,
    //--mentre rimane il valore esatto del campo originario timestamp
    Date modificaWiki

    //--ridondante, costruito con il ultimaLettura esatto della pagina
    //--serve per visualizzare la data in forma ''breve'' più leggibile,
    //--mentre rimane il valore esatto del campo originario timestamp
    Date letturaWiki

    //--ridondante, costruito con il tag wikipedia + il title della pagina
    //--serve per visualizzare un campo extra di link a wikipedia, senza modificare il ''list'' standard
    String wikiUrl

    boolean extra = false         //
    String extraLista = ''        //
    boolean graffe = false        // {{
    boolean note = false          // <ref
    boolean nascosto = false      // <!--
    String errori = ''

    //serve per le voci che sono state modificate sul server wiki rispetto alla versione sul database
    //si basa sul parametro lastrevid
    //per sicurezza è false (quindi all'inizio controllo tutto)
    boolean allineata = false

    //serve per le voci che sono state ricaricate su wiki dopo controllo e formattazione del template
    boolean controllato = false

    //serve per le voci che sono state elaborate e trasferite (creando od aggiornando il record) su BioGrails
    boolean elaborata=false

    //serve per le voci a cui manca uno o più parametri fondamentali
    boolean incompleta = false

    /**
     * regolazione delle proprietà di ogni campo
     * l'ordine con cui vengono elencati qui,
     * viene rispettato nella lista e nella scheda standard
     * la possibilità di avere valori nulli, di default è false
     */
    static constraints = {
        wikiUrl(nullable: true)
        testoTemplate(nullable: true)

        pageid(unique: true)
        title(unique: true, nullable: false, blank: false)
        ns()
        touched(nullable: true, formatoData: new SimpleDateFormat('d MMM yy'))
        revid()
        size()
        user(nullable: true)
        timestamp(nullable: true, formatoData: new SimpleDateFormat('d MMM yy'))
        comment(nullable: true)
        logNote(nullable: true)
        logErr(nullable: true)
        langlinks()

        nome(nullable: true, blank: true)
        cognome(nullable: true, blank: true)
        forzaOrdinamento(nullable: true, blank: true)
        sesso(nullable: true, blank: true)

        attivita(nullable: true, blank: true)
        attivita2(nullable: true, blank: true)
        attivita3(nullable: true, blank: true)
        nazionalita(nullable: true, blank: true)

        titolo(nullable: true, blank: true)
        postCognome(nullable: true, blank: true)
        postCognomeVirgola(nullable: true, blank: true)
        preData(nullable: true, blank: true)
        luogoNascita(nullable: true, blank: true)
        luogoNascitaLink(nullable: true, blank: true)
        luogoNascitaAlt(nullable: true, blank: true)
        giornoMeseNascita(nullable: true, blank: true)
        annoNascita(nullable: true, blank: true)
        noteNascita(nullable: true, blank: true)

        luogoMorte(nullable: true, blank: true)
        luogoMorteLink(nullable: true, blank: true)
        luogoMorteAlt(nullable: true, blank: true)
        giornoMeseMorte(nullable: true, blank: true)
        annoMorte(nullable: true, blank: true)
        noteMorte(nullable: true, blank: true)

        preAttivita(nullable: true, blank: true)
        epoca(nullable: true, blank: true)
        epoca2(nullable: true, blank: true)
        cittadinanza(nullable: true, blank: true)
        attivitaAltre(nullable: true, blank: true)
        nazionalitaNaturalizzato(nullable: true, blank: true)
        postNazionalita(nullable: true, blank: true)

        categorie(nullable: true, blank: true)
        fineIncipit(nullable: true, blank: true)
        punto(nullable: true, blank: true)
        immagine(nullable: true, blank: true)
        didascalia(nullable: true, blank: true)
        didascalia2(nullable: true, blank: true)
        dimImmagine(nullable: true, blank: true)

        ultimaLettura(nullable: true)
        ultimaScrittura(nullable: true)

        extra()
        extraLista()
        graffe()
        note()
        nascosto()
        errori()
        sizeBio()
        modificaWiki(nullable: true)
        letturaWiki(nullable: true)
        incompleta()
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

        // registrazione automatica delle date di creazione e update dei records
        // default del timestamping è true
        autoTimestamp true

        // numero di versione del singolo record per l'optimistic locking
        // default del versioning è true
        version true

        // stringa di lunghezza variabile
        title type: 'text'
        user type: 'text'
        comment type: 'text'
        logNote type: 'text'
        logErr type: 'text'

        // stringa di lunghezza variabile
        nome type: 'text'
        cognome type: 'text'
        postCognome type: 'text'
        postCognomeVirgola type: 'text'
        forzaOrdinamento type: 'text'
        preData type: 'text'
        sesso type: 'text'

        luogoNascita type: 'text'
        luogoNascitaLink type: 'text'
        luogoNascitaAlt type: 'text'
        giornoMeseNascita type: 'text'
        annoNascita type: 'text'
        noteNascita type: 'text'

        luogoMorte type: 'text'
        luogoMorteLink type: 'text'
        luogoMorteAlt type: 'text'
        giornoMeseMorte type: 'text'
        annoMorte type: 'text'
        noteMorte type: 'text'

        preAttivita type: 'text'
        attivita type: 'text'
        epoca type: 'text'
        epoca2 type: 'text'
        attivita2 type: 'text'
        attivita3 type: 'text'
        attivitaAltre type: 'text'
        nazionalita type: 'text'
        nazionalitaNaturalizzato type: 'text'
        postNazionalita type: 'text'

        categorie type: 'text'
        fineIncipit type: 'text'
        punto type: 'text'
        immagine type: 'text'
        didascalia type: 'text'
        didascalia2 type: 'text'
        dimImmagine type: 'text'

        // stringa di lunghezza variabile
        testoTemplate type: 'text'
        extraLista type: 'text'
        errori type: 'text'
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
