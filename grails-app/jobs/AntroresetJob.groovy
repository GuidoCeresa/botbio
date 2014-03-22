import it.algos.algospref.Preferenze
import it.algos.botbio.LibBio

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 31-1-14
 * Time: 20:45
 */
class AntroresetJob {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def antroponimoService

    //--codifica dell'orario di attivazione
    private static String cronExpressionAntroreset = "0 0 0 ? * 7#5"   //a mezzanotte, il quinto sabato del mese

    static triggers = {
        cron name: 'antroreset', cronExpression: cronExpressionAntroreset
    }// fine del metodo statico

    def execute() {
        //--flag di attivazione
        if (Preferenze.getBool(LibBio.USA_CRONO_ANTROPONIMI)) {
            if (antroponimoService) {
                log.info 'Inizio reset totale antroponimi'
                antroponimoService.costruisce()
                log.info 'Fine reset totale antroponimi'
            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo execute

} // end of Job Class
