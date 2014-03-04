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
    def attivitaService
    def nazionalitaService
    def professioneService
    def bioService
    def bioWikiService
    def logWikiService

    //--numero di cicli
    private static int CICLI = 4

    //--codifica dell'orario di attivazione
    //--a mezzanotte, tutti i giorni sabato escluso
    private static String cronExpressionCiclo = "0 0 0 ? * SUN-FRI"

    static triggers = {
        cron name: 'ciclo', cronExpression: cronExpressionCiclo
    }// fine del metodo statico

    def execute() {
        HashMap mappa
        ArrayList<Integer> listaNuoviRecordsAggiunti
        ArrayList<Integer> listaRecordsModificati
        long inizio = System.currentTimeMillis()
        long fine
        long durata
        int aggiunti = 0
        int modificati = 0
        int cancellati = 0

        //--flag di attivazione
        if (Preferenze.getBool(LibBio.USA_CRONO_DOWNLOAD)) {
            if (attivitaService) {
                attivitaService.download()
            }// fine del blocco if
            if (nazionalitaService) {
                nazionalitaService.download()
            }// fine del blocco if
            if (professioneService) {
                professioneService.download()
            }// fine del blocco if

            if (bioService && bioWikiService) {
                //--aggiunge ed elabora quelli aggiunti
                mappa = bioWikiService.aggiungeWiki()
                if (mappa && mappa.get(LibBio.AGGIUNTI)) {
                    listaNuoviRecordsAggiunti = (ArrayList<Integer>) mappa.get(LibBio.AGGIUNTI)
                }// fine del blocco if
                if (listaNuoviRecordsAggiunti) {
                    bioService.elabora(listaNuoviRecordsAggiunti)
                    aggiunti = listaNuoviRecordsAggiunti.size()
                }// fine del blocco if
                if (mappa && mappa.get(LibBio.CANCELLATI)) {
                    cancellati = mappa.get(LibBio.CANCELLATI).size()
                }// fine del blocco if

                //--modifica ed elabora quelli modificati
                for (int k = 0; k < CICLI; k++) {
                    listaRecordsModificati = bioWikiService.aggiornaWiki()
                    if (listaRecordsModificati) {
                        bioService.elabora(listaRecordsModificati)
                        modificati = listaRecordsModificati.size()
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

            if (logWikiService) {
                fine = System.currentTimeMillis()
                durata = fine - inizio
                LibBio.gestVoci(logWikiService, false, durata, aggiunti, cancellati, modificati)
            }// fine del blocco if
        }// fine del blocco if
    }// fine del metodo execute

} // end of Job Class
