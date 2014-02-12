import groovy.sql.Sql
import it.algos.botbio.BioGrails

import javax.sql.DataSource

class VersioneBootStrap {

    // utilizzo di un service con la businessLogic per l'elaborazione dei dati
    // il service viene iniettato automaticamente
    def versioneService

    DataSource dataSource

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

        //--aggiunte alcune preferenze
        if (versioneService && versioneService.installaVersione(7)) {
            versioneService.newVersione('Preferenze', 'Aggiunto taglio per antroponimi')
        }// fine del blocco if

        if (versioneService && versioneService.installaVersione(8)) {
            versioneService.newVersione('Applicazione', 'Aggiunta tavola Professione')
        }// fine del blocco if

        if (versioneService && versioneService.installaVersione(9)) {
            versioneService.newVersione('Applicazione', 'Riattivate liste di nomi')
        }// fine del blocco if

        //--aggiunte alcune preferenze
        if (versioneService && versioneService.installaVersione(10)) {
            versioneService.newVersione('Preferenze', 'Aggiunto usaOccorrenzeAntroponimi')
        }// fine del blocco if

        //--aggiunte alcune preferenze
        if (versioneService && versioneService.installaVersione(11)) {
            versioneService.newVersione('Preferenze', 'Aggiunto confrontaSoloPrimoNomeAntroponimi')
        }// fine del blocco if

        //--aggiunte alcune preferenze
        if (versioneService && versioneService.installaVersione(12)) {
            versioneService.newVersione('Antroponimi', 'Modificato titolo delle pagine')
        }// fine del blocco if

        //--aggiunte alcuni parametri
        if (versioneService && versioneService.installaVersione(13)) {
            versioneService.newVersione('BioGrails', 'Nuovo parametro didascaliaListe')
        }// fine del blocco if

        //--aggiunte alcune preferenze
        if (versioneService && versioneService.installaVersione(14)) {
            versioneService.newVersione('Preferenze', 'Aggiunto summary')
        }// fine del blocco if
    }// fine della closure

    def allungaCampo(Sql sql, String nomeCampo) {
        String query = "alter table wiki.bio_grails modify column `${nomeCampo}` varchar(765)"
        sql.execute(query)
        println('Allungato (longtext) il campo ${nomeCampo}')
    }// fine del metodo

    def destroy = {
    }// fine della closure

}// fine della classe
