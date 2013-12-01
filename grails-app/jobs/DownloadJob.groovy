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
    def bioService

    //--delay iniziale
    // execute job 5 minute after start
    public static int DELAY = 1000 * 60 * 5

    //--codifica della frequenza
    // execute job once in 60 minutes
    public static int FREQUENZA = 1000 * 60 * 60

    //--codifica dell'orario di attivazione
//    private static String cronExpressionDowload = "0 0 2-23 * * ?"   //tutti i giorni a tutte le ore meno mezzanotte e l'una
    private static String cronExpressionDowload = "0 0 2,6,10,14,18,22 * * ?"   //tutti i giorni ogni quattro ore meno mezzanotte e l'una

    static triggers = {
//        simple startDelay: DELAY, repeatInterval: FREQUENZA
        cron name: 'download', cronExpression: cronExpressionDowload
    }// fine del metodo statico

    def execute() {
        ArrayList<Integer> listaRecordsModificati

        //--flag di attivazione
        if (Preferenze.getBool(LibBio.USA_CRONO_DOWNLOAD)) {
            if (bioWikiService) {
                listaRecordsModificati = bioWikiService.aggiornaWiki()
                if (listaRecordsModificati) {
                    bioService.elabora(listaRecordsModificati)
                }// fine del blocco if
            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo execute

} // end of Job Class
