import it.algos.algospref.Preferenze
import it.algos.botbio.LibBio

class PrefBootStrap {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def grailsApplication

    //--metodo invocato direttamente da Grails
    def init = { servletContext ->

        //--iniezione di una property/variabile globale
        //--la crea qui perché l'ordine di caricamento delle classi BootStrap NON è garantito
        //--e quindi potrebbe non esistere ancora, se creata in un altro BootStrap
        //--in ogni caso è successivamente disponibile a tutta l'applicazione
        //--indipendentemente dal BootStrap in cui è stata creata
        grailsApplication.config.debug = LibBio.DEBUG
        grailsApplication.config.annoDebug = LibBio.ANNO_DEBUG
        grailsApplication.config.catDebug = LibBio.CAT_DEBUG
        grailsApplication.config.usaPagineSingole = LibBio.USA_PAGINE_SINGOLE
        grailsApplication.config.usaLimiteDownload = LibBio.USA_LIMITE_DOWNLOAD
        grailsApplication.config.maxDownload = LibBio.MAX_DOWNLOAD
        grailsApplication.config.usaCronoDownload = LibBio.USA_CRONO_DOWNLOAD

        //--alcune preferenze sempre presenti
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.debug, Preferenze.TYPE_BOOL, 'false').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.annoDebug, Preferenze.TYPE_STR, '1645').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.catDebug, Preferenze.TYPE_STR, 'Scultori ellenistici').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaPagineSingole, Preferenze.TYPE_BOOL, 'false').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaLimiteDownload, Preferenze.TYPE_BOOL, 'true').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.maxDownload, Preferenze.TYPE_INT, '10000').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaCronoDownload, Preferenze.TYPE_BOOL, 'false').save(flush: true)
    }// fine della closure

    //--metodo invocato direttamente da Grails
    def destroy = {
    }// fine della closure

}// fine della classe di tipo BootStrap
