class VersioneBootStrap {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def versioneService

    //--metodo invocato direttamente da Grails
    //--tutte le aggiunte, modifiche e patch vengono inserite con una versione
    //--l'ordine di inserimento è FONDAMENTALE
    def init = { servletContext ->
        //--controllo del flusso
        log.debug 'init'

        //--prima installazione del programma
        if (versioneService && versioneService.installaVersione(1)) {
            versioneService.newVersione('Applicazione', 'Installazione iniziale')
        }// fine del blocco if

        //--connessione tra applicazione e tomcate
        if (versioneService && versioneService.installaVersione(2)) {
            versioneService.newVersione('DataSource', 'Aggiunto autoReconnect=true')
        }// fine del blocco if

        //--aggiunte alcune preferenze
        if (versioneService && versioneService.installaVersione(3)) {
            versioneService.newVersione('Preferenze', 'Aggiunto cassetto e colonne')
        }// fine del blocco if

        //--registra solo se il contenuto (data esclusa) è modificato
        if (versioneService && versioneService.installaVersione(4)) {
            versioneService.newVersione('Upload', 'Aggiunto controllo differenze significative in fase di registrazione voce')
        }// fine del blocco if

        //--aggiunte alcune preferenze
        if (versioneService && versioneService.installaVersione(5)) {
            versioneService.newVersione('Grafica', 'Formattato il numero di persone nel template in testa pagina')
        }// fine del blocco if

        //--aggiunte alcune preferenze
        if (versioneService && versioneService.installaVersione(6)) {
            versioneService.newVersione('Upload', 'Ordinamento alfabetico nei sotogruppi di giorni e anni')
        }// fine del blocco if
    }// fine della closure

    def destroy = {
    }// fine della closure

}// fine della classe
