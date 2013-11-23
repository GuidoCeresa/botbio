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
    def logWikiService

    //--delay iniziale
    // execute job 5 minute after start
    public static int DELAY = 1000 * 60 * 5

    //--codifica della frequenza
    // execute job once in 60 minutes
    public static int FREQUENZA = 1000 * 60 * 60

    //--codifica dell'orario di attivazione
    private static String cronExpressionDowload = "1 0 0 * * ?"   //tutti i giorni a mezzanotte e 1 secondo

    static triggers = {
//        simple startDelay: DELAY, repeatInterval: FREQUENZA
        cron name: 'download', cronExpression: cronExpressionDowload
    }// fine del metodo statico

//    def group = "MyGroup"

    def execute() {
        ArrayList<Integer> listaNuoviRecordsAggiunti
        ArrayList<Integer> listaRecordsModificati
        long inizio = System.currentTimeMillis()
        long fine
        long durata
        int aggiunti = 0
        int modificati = 0

        //--flag di attivazione
        if (Preferenze.getBool(LibBio.USA_CRONO_DOWNLOAD)) {
            if (bioWikiService) {
                listaNuoviRecordsAggiunti = bioWikiService.aggiungeWiki()
                if (listaNuoviRecordsAggiunti) {
                    aggiunti = listaNuoviRecordsAggiunti.size()
                }// fine del blocco if
                listaRecordsModificati = bioWikiService.aggiornaWiki()
                if (listaRecordsModificati) {
                    modificati = listaRecordsModificati.size()
                }// fine del blocco if
                if (bioService) {
                    bioService.elabora((ArrayList<Integer>) listaNuoviRecordsAggiunti + listaRecordsModificati)
                }// fine del blocco if
            }// fine del blocco if
            if (logWikiService) {
                fine = System.currentTimeMillis()
                durata = fine - inizio
                LibBio.gestVoci(logWikiService, false, durata, aggiunti, modificati)
            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo execute

} // end of Job Class
