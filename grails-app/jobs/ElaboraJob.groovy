import it.algos.algospref.Preferenze
import it.algos.botbio.LibBio

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 18-11-13
 * Time: 17:14
 */
class ElaboraJob {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def bioService
//    def logWikiService

    //--codifica dell'orario di attivazione
    private static String cronExpressionElabora = "0 0 2 * * ?"   //tutti i giorni alle due di notte

    static triggers = {
//        cron name: 'upload', cronExpression: cronExpressionElabora
    }// fine del metodo statico

    def execute() {
        ArrayList<Integer> listaNuoviRecordsCreati
        ArrayList<Integer> listaRecordsModificati
        long inizio = System.currentTimeMillis()
        long fine
        long durata
        int aggiunte = 0
        int modificate = 0

        //--flag di attivazione
        if (Preferenze.getBool(LibBio.USA_CRONO_DOWNLOAD)) {
            if (bioService) {
                bioService.elabora()
            }// fine del blocco if
//            if (logWikiService) {
//                fine = System.currentTimeMillis()
//                durata = fine - inizio
//                LibBio.gestVoci(logWikiService, false, durata, aggiunte, modificate)
//            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo execute

} // end of Job Class
