import it.algos.algospref.Preferenze
import it.algos.botbio.LibBio

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 31-1-14
 * Time: 20:45
 */
class AttivitaJob {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def listaService
    def statisticheService

    //--codifica dell'orario di attivazione
    private static String cronExpressionAttivita = "0 0 0 ? * 7#2"   //a mezzanotte, il secondo sabato del mese

    static triggers = {
        cron name: 'attivita', cronExpression: cronExpressionAttivita
    }// fine del metodo statico

    def execute() {
        //--flag di attivazione
        if (Preferenze.getBool(LibBio.USA_CRONO_ATTIVITA)) {
            if (listaService) {
                listaService.uploadAttivita()
            }// fine del blocco if
            if (statisticheService) {
                statisticheService.attivitaUsate()
            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo execute

} // end of Job Class
