/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 16-11-13
 * Time: 17:43
 */
import it.algos.algospref.Preferenze
import it.algos.botbio.LibBio

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 25-9-13
 * Time: 14:38
 */
class UploadJob {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def bioGrailsService
    def statisticheService

    //--codifica dell'orario di attivazione
    private static String cronExpressionUpload = "0 0 7 ? * SUN-FRI"   //tutti i giorni alle 7, sabato escluso

    static triggers = {
        cron name: 'upload', cronExpression: cronExpressionUpload
    }// fine del metodo statico

    def execute() {
        //--flag di attivazione
        if (Preferenze.getBool(LibBio.USA_CRONO_UPLOAD)) {
            if (bioGrailsService) {
                bioGrailsService.uploadAll()
            }// fine del blocco if
            if (statisticheService) {
                statisticheService.paginaSintesi()
            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo execute

} // end of Job Class
