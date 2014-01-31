import it.algos.algospref.Preferenze
import it.algos.botbio.LibBio

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 23-11-13
 * Time: 20:15
 */
class CicloJob {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def bioWikiService
    def logWikiService
    def bioService
    def attivitaService
    def nazionalitaService

    //--delay iniziale
    // execute job 5 minute after start
    public static int DELAY = 1000 * 60 * 5

    //--codifica della frequenza
    // execute job once in 60 minutes
    public static int FREQUENZA = 1000 * 60 * 60

    //--codifica dell'orario di attivazione
    private static String cronExpressionCiclo = "0 0 0 * * ?"   //tutti i giorni a mezzanotte
//    private static String cronExpressionCiclo = "0 0 * * * ?"   //tutti i giorni a tutte le ore
//    private static String cronExpressionCiclo = "0 0 2-23 * * ?"   //tutti i giorni a tutte le ore meno mezzanotte e l'una
//        private static String cronExpressionCiclo = "0 0 0,2,4,6,8,10,12,14,16,18,20,22 * * ?"   //tutti i giorni ogni quattro ore
    static triggers = {
//        simple startDelay: DELAY, repeatInterval: FREQUENZA
//        cron name: 'ciclo', cronExpression: cronExpressionCiclo
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
            if (attivitaService) {
                attivitaService.download()
            }// fine del blocco if
            if (nazionalitaService) {
                nazionalitaService.download()
            }// fine del blocco if

            if (bioWikiService) {
                //--aggiunge ed elabora quelli aggiunti
                listaNuoviRecordsAggiunti = bioWikiService.aggiungeWiki()
                if (listaNuoviRecordsAggiunti) {
                    bioService.elabora(listaNuoviRecordsAggiunti)
                    aggiunti = listaNuoviRecordsAggiunti.size()
                }// fine del blocco if

                //--modifica ed elabora quelli modificati
                listaRecordsModificati = bioWikiService.aggiornaWiki()
                if (listaRecordsModificati) {
                    bioService.elabora(listaRecordsModificati)
                    modificati = listaRecordsModificati.size()
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
