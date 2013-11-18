import it.algos.algoslogo.Evento
import it.algos.algoslogo.EventoService
import it.algos.botbio.LibBio

class LogoBootStrap {


    //--metodo invocato direttamente da Grails
    def init = { servletContext ->

        //--creazione dei records base della tavola Evento
        //--li crea SOLO se non esistono già
        Evento.findOrCreateByNome(EventoService.GENERICO).save(failOnError: true)
        Evento.findOrCreateByNome(EventoService.SETUP).save(failOnError: true)
        Evento.findOrCreateByNome(EventoService.NUOVO).save(failOnError: true)
        Evento.findOrCreateByNome(EventoService.MODIFICA).save(failOnError: true)
        Evento.findOrCreateByNome(EventoService.CANCELLA).save(failOnError: true)
        Evento.findOrCreateByNome(LibBio.EVENTO_TESTO_TROPPO_LUNGO).save(failOnError: true)
    }// fine della closure

    //--metodo invocato direttamente da Grails
    def destroy = {
    }// fine della closure

}// fine della classe di tipo BootStrap
