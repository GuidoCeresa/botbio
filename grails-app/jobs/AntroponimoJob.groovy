import it.algos.algospref.Preferenze
import it.algos.botbio.LibBio

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 31-1-14
 * Time: 20:45
 */
class AntroponimoJob {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def antroponimoService

    //--codifica dell'orario di attivazione
    private static String cronExpressionAntroponimoUno = "0 0 0 ? * 7#1"   //a mezzanotte, il primo sabato del mese
    private static String cronExpressionAntroponimoDue = "0 0 0 ? * 7#3"   //a mezzanotte, il terzo sabato del mese

    static triggers = {
        cron name: 'antroponimouno', cronExpression: cronExpressionAntroponimoUno
        cron name: 'antroponimodue', cronExpression: cronExpressionAntroponimoDue
    }// fine del metodo statico

    def execute() {
        //--flag di attivazione
        if (Preferenze.getBool(LibBio.USA_CRONO_ANTROPONIMI)) {
            if (antroponimoService) {
                log.info 'Inizio elaborazione antroponimi'
                antroponimoService.elabora()
                log.info 'Fine elaborazione antroponimi'
            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo execute

} // end of Job Class
