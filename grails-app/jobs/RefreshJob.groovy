

import it.algos.algoslib.LibGrails
import it.algos.algoslib.LibTesto
import it.algos.algoslogo.Logo

class RefreshJob {

    //--delay iniziale
    // execute job 1 minute after start
    public static int DELAY = 1000 * 60

    //--codifica della frequenza
    // execute job once in 30 minutes
    public static int FREQUENZA = 1000 * 60 * 30

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def mailService

    static triggers = {
        simple startDelay: DELAY, repeatInterval: FREQUENZA
    }// fine del metodo statico

    def execute() {
        def logs
        try { // prova ad eseguire il codice
            logs = Logo.getAll()
            if (logs.size() > 0) {
                //--non fa nulla, ma ha comunque ''risvegliato'' l'applicazione
            } else {
                spedisceMail('Applicazione installata e funzionante - Non ci sono logs')
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            spedisceMail('Qualcosa non funziona')
        }// fine del blocco try-catch
    }// fine del metodo execute


    private spedisceMail(String testo) {
        String oggetto
        String adesso = new Date().toString()
        String mailTo = 'guidoceresa@me.com'
        String time = 'Report eseguito alle ' + adesso + '\n'

        testo = time + testo
        oggetto = LibGrails.getAppName()
        oggetto = LibTesto.primaMaiuscola(oggetto)

        if (mailService) {
            mailService.sendMail {
                to mailTo
                subject oggetto
                body testo
            }// fine della closure
        }// fine del blocco if

    }// fine del metodo

} // end of Job Class
