import it.algos.algospref.Preferenze
import it.algos.botbio.LibBio

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 25-9-13
 * Time: 14:38
 */
class DownloadJob {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def bioWikiService

    //--codifica dell'orario di attivazione
    private static String cronExpressionDowload = "1 0 0 * * ?"   //tutti i giorni a mezzanotte

    static triggers = {
        cron name: 'download', cronExpression: cronExpressionDowload
    }// fine del metodo statico

//    def group = "MyGroup"

    def execute() {
        //--flag di attivazione
        if (Preferenze.getBool(LibBio.USA_CRONO_DOWNLOAD)) {
            if (bioWikiService) {
                bioWikiService.aggiungeWiki()
            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo execute

} // end of Job Class
