import grails.util.Holder
import grails.util.Holders

class WikiBootStrap {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def grailsApplication

    //--metodo invocato direttamente da Grails
    def init = { servletContext ->

        //--inietta una property a livello globale
        //--login normale del collegamento
        grailsApplication.config.login = null

        //--inietta una property a livello globale
        //--login del collegamento come bot
        grailsApplication.config.loginBot = null

        //--inietta una property a livello globale
        //--login del collegamento come admin
        grailsApplication.config.loginAdmin = null
    }// fine della closure

    //--metodo invocato direttamente da Grails
    def destroy = {
    }// fine della closure

}// fine della classe di tipo BootStrap
