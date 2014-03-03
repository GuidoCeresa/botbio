import it.algos.algoslogo.Evento
import it.algos.algoslogo.EventoService

class LogoBootStrap {

    //--metodo invocato direttamente da Grails
    def init = { servletContext ->

        //--creazione dei records base della tavola Evento
        //--li crea SOLO se non esistono già
        Evento.findOrCreateByNome(EventoService.NUOVO).save(failOnError: true)
        Evento.findOrCreateByNome(EventoService.MODIFICA).save(failOnError: true)
        Evento.findOrCreateByNome(EventoService.CANCELLA).save(failOnError: true)
    }// fine della closure

    //--metodo invocato direttamente da Grails
    def destroy = {
    }// fine della closure

}// fine della classe di tipo BootStrap
