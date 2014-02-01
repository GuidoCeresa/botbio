import it.algos.algosvers.Versione

/* Created by Algos s.r.l. */
/* Date: mag 2013 */
/* Il plugin Algos ha inserito (solo la prima volta) questo header per controllare */
/* le successive release (tramite il flag di controllo aggiunto) */
/* Non verrà mai sovrascritto, in nessun caso, dalle successive release del plugin */
/* indipendentemente dal fatto che venga localmente modificato o meno */
/* Se si vuole reinstallarlo per una eventuale versione più aggiornata del plugin, */
/* occorre cancellarlo PRIMA di reinstallare il plugin */

class BootStrap {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def grailsApplication

    //--metodo invocato direttamente da Grails
    def init = { servletContext ->

        //--iniezione di una property/variabile globale
        grailsApplication.config.mostraControllerSpecifici = 'alcuni'
    }// fine della closure

    //--metodo invocato direttamente da Grails
    def destroy = {
    }// fine della closure

}// fine della classe di tipo BootStrap
