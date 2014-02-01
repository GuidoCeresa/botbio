import it.algos.algoslib.LibTesto
import it.algos.algospref.Preferenze
import it.algos.botbio.BioGrails
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
    def bioWikiService
    def logWikiService
    def antroponimoService

    //--delay iniziale
    // execute job 5 minute after start
    public static int DELAY = 1000 * 60 * 5

    //--codifica della frequenza
    // execute job once in 60 minutes
    public static int FREQUENZA = 1000 * 60 * 60

    //--codifica dell'orario di attivazione
//    private static String cronExpressionCiclo = "0 0 0 * * ?"   //tutti i giorni a mezzanotte
//    private static String cronExpressionCiclo = "0 0 1 * * ?"   //tutti i giorni all'una
//    private static String cronExpressionCiclo = "0 0 * * * ?"   //tutti i giorni a tutte le ore
//    private static String cronExpressionCiclo = "0 0 2-23 * * ?"   //tutti i giorni a tutte le ore meno mezzanotte e l'una
//        private static String cronExpressionCiclo = "0 0 0,2,4,6,8,10,12,14,16,18,20,22 * * ?"   //tutti i giorni ogni quattro ore
    static triggers = {
//        simple startDelay: DELAY, repeatInterval: FREQUENZA
//        cron name: 'ciclo', cronExpression: cronExpressionCiclo
    }// fine del metodo statico

    def execute() {
        ArrayList<String> listaNomiParziale
        ArrayList<String> listaNomiUniciDiversiPerAccento
        String query = "select nome from BioGrails where nome <>'' order by nome asc"
        int delta = 10000
        int totaleVoci

        //--flag di attivazione
        if (Preferenze.getBool(LibBio.USA_CRONO_DOWNLOAD)) {
            log.info 'Inizio costruzione antroponimi'
            totaleVoci = BioGrails.count()

            if (antroponimoService) {
                antroponimoService.cancellaTutto()

                //ciclo
                for (int k = 0; k < totaleVoci; k += delta) {
                    listaNomiParziale = (ArrayList<String>) BioGrails.executeQuery(query, [max: delta, offset: k])
                    listaNomiUniciDiversiPerAccento = antroponimoService.elaboraNomiUnici(listaNomiParziale)
                    antroponimoService.spazzolaPacchetto(listaNomiUniciDiversiPerAccento)
                    log.info 'Elaborate ' + LibTesto.formatNum(k + delta) + ' voci'
                } // fine del ciclo for

                log.info 'Fine costruzione antroponimi'
            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo execute

} // end of Job Class
