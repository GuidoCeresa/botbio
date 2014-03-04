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

    //--codifica dell'orario di attivazione
    //tutti i giorni dalle 9 alle 11 di sera, sabato escluso
    private static String cronExpressionElabora = "0 0 9-23 ? * SUN-FRI"

    static triggers = {
        cron name: 'elabora', cronExpression: cronExpressionElabora
    }// fine del metodo statico

    def execute() {
        //--flag di attivazione
        if (Preferenze.getBool(LibBio.USA_CRONO_ELABORA)) {
            if (bioService) {
                bioService.elabora()
            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo execute

} // end of Job Class
