import groovy.sql.Sql
import it.algos.algospref.Pref
import it.algos.algospref.Preferenze
import it.algos.botbio.LibBio

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

        //--aggiunte alcune preferenze
        if (versioneService && versioneService.installaVersione(15)) {
            versioneService.newVersione('Preferenze', 'Aggiunti valori per le statistiche')
        }// fine del blocco if

        //--aggiunte alcune preferenze
        if (versioneService && versioneService.installaVersione(16)) {
            versioneService.newVersione('Preferenze', 'Aggiunti valori per i crono jobs')
        }// fine del blocco if

        //--completa il nuovo campo ordine delle preferenze
        if (versioneService && versioneService.installaVersione(17)) {
            //@todo occorre creare manualmente il campo con "alter table preferenze add ordine integer not null"
            def preferenze = Preferenze.list()
            Preferenze preferenza

            preferenze?.each {
                preferenza = it
                preferenza.ordine = preferenza.id
                preferenza.save(flush: true)
            } // fine del ciclo each

            versioneService.newVersione('Preferenze', 'Aggiunti i campi ordine e descrizione')
        }// fine del blocco if

        //--modificate le constraints di BioWiki
        if (versioneService && versioneService.installaVersione(18)) {
            //@todo occorre modificare manualmente il db con "alter table bio_wiki modify column extra_lista longtext set utf8 collate utf8 default null"
            versioneService.newVersione('Database', 'Modificati con default null i campi: BioWiki.extraLista, BioWiki.errori')
        }// fine del blocco if

        //--creata una nuova preferenza
        if (versioneService && versioneService.installaVersione(19)) {
            Pref pref = new Pref()
            pref.ordine = 3
            pref.code = LibBio.USA_TAVOLA_CONTENUTI
            pref.descrizione = 'Mostra il sommario in testa alle pagine'
            pref.type = Pref.Type.booleano
            pref.bool = true
            pref.save(flush: true)
            versioneService.newVersione('Preferenze', 'USA_TAVOLA_CONTENUTI di default true')
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
