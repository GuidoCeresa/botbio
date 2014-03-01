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
        grailsApplication.config.usaCronoDownload = LibBio.USA_CRONO_DOWNLOAD
        grailsApplication.config.maxDownload = LibBio.MAX_DOWNLOAD
        grailsApplication.config.usaCassetto = LibBio.USA_CASSETTO
        grailsApplication.config.maxRigheCassetto = LibBio.MAX_RIGHE_CASSETTO
        grailsApplication.config.usaColonne = LibBio.USA_COLONNE
        grailsApplication.config.maxRigheColonne = LibBio.MAX_RIGHE_COLONNE
        grailsApplication.config.registraSoloModificheSostanziali = LibBio.REGISTRA_SOLO_MODIFICHE_SOSTANZIALI
        grailsApplication.config.usaCronoElabora = LibBio.USA_CRONO_ELABORA
        grailsApplication.config.usaLimiteElabora = LibBio.USA_LIMITE_ELABORA
        grailsApplication.config.maxElabora = LibBio.MAX_ELABORA
        grailsApplication.config.taglioAntroponimi = LibBio.TAGLIO_ANTROPONIMI
        grailsApplication.config.sogliaAntroponimi = LibBio.SOGLIA_ANTROPONIMI
        grailsApplication.config.usaOccorrenzeAntroponimi = LibBio.USA_OCCORRENZE_ANTROPONIMI
        grailsApplication.config.confrontaSoloPrimoNomeAntroponimi = LibBio.CONFRONTA_SOLO_PRIMO_NOME_ANTROPONIMI
        grailsApplication.config.summary = LibBio.SUMMARY
        grailsApplication.config.numeroVociGestite = LibBio.VOCI
        grailsApplication.config.numeroGiorniGestiti = LibBio.GIORNI
        grailsApplication.config.numeroAnniGestiti = LibBio.ANNI
        grailsApplication.config.numeroAttivitaGestite = LibBio.ATTIVITA
        grailsApplication.config.numeroNazionalitaGestite = LibBio.NAZIONALITA
        grailsApplication.config.giorniAttesa = LibBio.ATTESA
        grailsApplication.config.ultimaSintesi = LibBio.ULTIMA_SINTESI
        grailsApplication.config.usaCronoAntroponimi = LibBio.USA_CRONO_ANTROPONIMI
        grailsApplication.config.usaCronoAttivita = LibBio.USA_CRONO_ATTIVITA
        grailsApplication.config.usaCronoNazionalita = LibBio.USA_CRONO_NAZIONALITA
        grailsApplication.config.usaCronoUpload = LibBio.USA_CRONO_UPLOAD

        //--alcune preferenze sempre presenti
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.debug, Preferenze.TYPE_BOOL, 'false').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.annoDebug, Preferenze.TYPE_STR, '1645').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.catDebug, Preferenze.TYPE_STR, 'Pittori britannici').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaPagineSingole, Preferenze.TYPE_BOOL, 'false').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaLimiteDownload, Preferenze.TYPE_BOOL, 'true').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaCronoDownload, Preferenze.TYPE_BOOL, 'false').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.maxDownload, Preferenze.TYPE_INT, '10000').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaCassetto, Preferenze.TYPE_BOOL, 'true').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.maxRigheCassetto, Preferenze.TYPE_INT, '50').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaColonne, Preferenze.TYPE_BOOL, 'true').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.maxRigheColonne, Preferenze.TYPE_INT, '10').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.registraSoloModificheSostanziali, Preferenze.TYPE_BOOL, 'true').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaCronoElabora, Preferenze.TYPE_BOOL, 'false').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaLimiteElabora, Preferenze.TYPE_BOOL, 'true').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.maxElabora, Preferenze.TYPE_INT, '2000').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.taglioAntroponimi, Preferenze.TYPE_INT, '100').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.sogliaAntroponimi, Preferenze.TYPE_INT, '10').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaOccorrenzeAntroponimi, Preferenze.TYPE_BOOL, 'true').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.confrontaSoloPrimoNomeAntroponimi, Preferenze.TYPE_BOOL, 'true').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.summary, Preferenze.TYPE_STR, '[[Utente:Biobot#9|Biobot 9.4]]').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.numeroVociGestite, Preferenze.TYPE_INT, '250000').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.numeroGiorniGestiti, Preferenze.TYPE_INT, '365').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.numeroAnniGestiti, Preferenze.TYPE_INT, '2000').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.numeroAttivitaGestite, Preferenze.TYPE_INT, '500').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.numeroNazionalitaGestite, Preferenze.TYPE_INT, '250').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.giorniAttesa, Preferenze.TYPE_INT, '15').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.ultimaSintesi, Preferenze.TYPE_STR, '13 feb 2014').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaCronoAntroponimi, Preferenze.TYPE_BOOL, 'false').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaCronoAttivita, Preferenze.TYPE_BOOL, 'false').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaCronoNazionalita, Preferenze.TYPE_BOOL, 'false').save(flush: true)
        Preferenze.findOrCreateByCodeAndTypeAndValue((String) grailsApplication.config.usaCronoUpload, Preferenze.TYPE_BOOL, 'false').save(flush: true)
    }// fine della closure

    //--metodo invocato direttamente da Grails
    def destroy = {
    }// fine della closure

}// fine della classe di tipo BootStrap
