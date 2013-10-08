package it.algos.botbio

import it.algos.algoslib.LibWiki

public enum DidascaliaTipo {

    base("base", "nome e cognome", true) {
        public String refText() {
            String testo = "";
            testo += "Nome" + parametro + fixBold("Nome") + tempBio + pseudonimi;
            testo += tagRef;
            testo += "Cognome" + parametro + fixBold("Cognome") + tempBio + pseudonimi;
            return testo;
        }
    },
    crono("crono", "nome, cognome, anno di nascita ed anno di morte", true) {
        public String refText() {
            String testo = "";
            testo += "Anno valido di nascita" + parametro + fixBold("AnnoNascita") + tempBio + periodi;
            testo += tagRef;
            testo += "Anno valido di morte" + parametro + fixBold("AnnoMorte") + tempBio + periodi;
            return testo;
        }
    },
    cronoSimboli("cronoSimboli", "nome, cognome, anno di nascita ed anno di morte con simboli", true) {
        public String refText() {
            String testo = "";
            testo += "I simboli utilizzati sono '''n.''' per l'anno di nascita e '''†''' per l'anno di morte";
            return testo;
        }
    },
    semplice("semplice", "nome, cognome, attività e nazionalità", true) {
        public String refText() {
            String testo = "";
            testo += "Attività " + parametri + fixBold("Attività") + vir + fixBold("Attività2") + vir + fixBold("Attività3") + tempBio + temp + fixBold("[[Template:Bio/plurale attività|attività]]");
            testo += ". Il valore del parametro " + fixBold("AttivitàAltre") + tempBio + " non viene preso in considerazione.";
            testo += tagRef;
            testo += "Nazionalità" + parametro + fixBold("Nazionalità") + tempBio + temp + fixBold("[[Template:Bio/plurale nazionalità|nazionalità]]");
            return testo;
        }
    },
    completa("completa", "nome, cognome, attività, nazionalità, anno di nascita ed anno di morte", true),
    completaSimboli("completaSimboli", "nome, cognome, attività, nazionalità, anno di nascita ed anno di morte con simboli", true),
    estesa("estesa", "nome, cognome, attività, nazionalità, località di nascita, località di morte, anno di nascita ed anno di morte", true) {
        public String refText() {
            String testo = "";
            testo += "Località di nascita " + parametro + fixBold("LuogoNascita") + tempBio + rimando + fixBold("LuogoNascitaLink");
            testo += tagRef;
            testo += "Località di morte " + parametro + fixBold("LuogoMorte") + tempBio + rimando + fixBold("LuogoMorteLink");
            return testo;
        }
    },
    estesaSimboli("estesaSimboli", "nome, cognome, attività, nazionalità, località di nascita, località di morte, anno di nascita ed anno di morte con simboli", true),
    completaLista("completaLista", "", false),
    natiGiorno("natiGiorno", "", false),
    natiAnno("natiAnno", "", false),
    mortiGiorno("mortiGiorno", "", false),
    mortiAnno("mortiAnno", "", false);

    String sigla;
    String descrizione;
    String ref;        //reference per le note sulla pagina wiki di controllo
    boolean stampaTest;
    private final static String parametro = " come indicato nella voce biografica al parametro ";
    private final static String parametri = " come indicate nella voce biografica ai parametri ";
    private final static String tempBio = " del template '''[[template:Bio|Bio]]'''";
    private final static String pseudonimi = ", indipendentemente dal titolo della voce stessa e da eventuali pseudonimi.";
    private final static String tagRef = "</ref><ref>";   // rende doppia la preferenza
    private final static String periodi = ". Anni o periodi non validi (circa, ..., ?), non vengono considerati";
    private final static String temp = ". Il valore deve essere previsto nel template ";
    private final static String rimando = ". Con eventuale rimando alla voce indicata dal parametro ";
    private final static String vir = ", ";

    DidascaliaTipo(String sigla, String descrizione, boolean stampa) {
        /* regola le variabili di istanza coi parametri */
        this.setSigla(sigla);
        this.setDescrizione(descrizione);
        this.setReferences();
        this.setStampaTest(stampa);
    }// fine del costruttore


    public void setReferences() {
        String testo = this.refText();
        if (!testo.equals("")) {
            this.setRef(LibWiki.setRef(testo));
        }// fine del blocco if
    }


    public String refText() {
        return "";
    }


    public String getSigla() {
        return sigla;
    }


    private void setSigla(String sigla) {
        this.sigla = sigla;
    }


    public String getDescrizione() {
        return descrizione;
    }


    private void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    public boolean getStampaTest() {
        return stampaTest;
    }


    private void setStampaTest(boolean stampaTest) {
        this.stampaTest = stampaTest;
    }


    public String getRef() {
        return ref;
    }


    private void setRef(String ref) {
        this.ref = ref;
    }


    public static String fixBold(String testo) {
        String tagBold = "'''";

        return tagBold + testo + tagBold;
    }

}// fine della Enumeration
