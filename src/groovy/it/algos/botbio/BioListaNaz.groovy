package it.algos.botbio

/**
 * Created by IntelliJ IDEA.
 * User: Gac
 * Date: 13/02/11
 * Time: 14.46
 */
class BioListaNaz extends BioListaAttNaz {

    public static String ATT_NAZ = 'Nazionalità'
    private static Ordinamento ORDINAMENTO = Ordinamento.attivitaAlfabetico


    public BioListaNaz(String plurale, int numPersoneUnivoche, ArrayList listaDidascalie) {
        // rimanda al costruttore della superclasse
        this(plurale, numPersoneUnivoche,listaDidascalie, ORDINAMENTO)
    }// fine del metodo costruttore completo


    public BioListaNaz(String plurale, int numPersoneUnivoche, ArrayList listaDidascalie, Ordinamento ordinamento) {
        // rimanda al costruttore della superclasse
        super(plurale, numPersoneUnivoche, listaDidascalie, ordinamento)
    }// fine del metodo costruttore completo

    /**
     * Regola i tag
     */

    protected regolaTag() {
        super.path = PATH + ATT_NAZ + '/'
        super.attNaz = ATT_NAZ

        if (TRIPLA_ATTIVITA) {
            super.setCampiParagrafi(['attivitaPlurale', 'attivita2Plurale', 'attivita3Plurale'])
        } else {
            super.setCampoParagrafo('attivitaPlurale')
        }// fine del blocco if-else
    }// fine del metodo

    protected String getTestoEnd(String attNaz) {
        // variabili e costanti locali di lavoro
        String testo = ''
        String aCapo = '\n'
        int numUnivoche = this.getNumPersoneUnivoche()
        int numRighe = this.getNumPersoneRighe()
        boolean dimDiversa = (numRighe > numUnivoche)

        if (TRIPLA_ATTIVITA && dimDiversa) {
            testo += aCapo
            testo += '==Note=='
            testo += aCapo
            testo += 'Alcune persone sono citate più volte perché hanno diverse attività'
            testo += aCapo
        }// fine del blocco if
        testo += super.getTestoEnd(attNaz)

        // valore di ritorno
        return testo
    }// fine del metodo

    protected creaSottoPagina(Map mappa) {
        BioListaAttNaz bioLista
        ArrayList listaDidascalie
        String attivita
        String sottoAttivita
        String nazionalita
        int livello

        if (mappa) {
            listaDidascalie = (ArrayList) mappa[LibBio.MAPPA_LISTA]

            attivita = mappa[LibBio.MAPPA_ATTIVITA]
            if (!attivita) {
                attivita = '...'
            }// fine del blocco if
            nazionalita = mappa[LibBio.MAPPA_NAZIONALITA]
            livello = (int) mappa[LibBio.MAPPA_LIVELLO]
            sottoAttivita = nazionalita + '/' + attivita

            bioLista = new BioListaNaz(sottoAttivita, listaDidascalie.size(),creaListaSottoMappe(listaDidascalie, attivita, nazionalita, livello), Ordinamento.prestabilitoInMappa)
            bioLista.setCategoria(nazionalita)
            bioLista.registra()
        }// fine del blocco if
    }// fine del metodo

    private ArrayList<Map> creaListaSottoMappe(ArrayList listaDidascalie, String attivita, String nazionalita, int livello) {
        ArrayList<Map> listaSottoMappe = new ArrayList()
        ArrayList<Map> listaMappeDidascalie
        ArrayList keyList
        Map mappaDidascalia
        Map mappa
        String didascalia
        String primaLettera
        String titoloSottopagina = this.getTitoloPagina() + '/' + attivita


        if (listaDidascalie) {

            keyList = listaDidascalie.collect { it[LibBio.MAPPA_PRIMA_LETTERA] }.unique()
            keyList.sort()

            keyList.each {
                listaMappeDidascalie = new ArrayList<Map>()

                primaLettera = it
                listaDidascalie.each {
                    if (it[LibBio.MAPPA_PRIMA_LETTERA].equals(primaLettera)) {
                        didascalia = it[LibBio.MAPPA_DIDASCALIA]
                        mappaDidascalia = new HashMap()
                        mappaDidascalia.put(LibBio.MAPPA_DIDASCALIA, didascalia)
                        listaMappeDidascalie.add(mappaDidascalia)
                    }// fine del blocco if
                } // fine del ciclo each
                mappa = new HashMap()
                mappa.put(LibBio.MAPPA_TITOLO_PARAGRAFO, primaLettera)
                mappa.put(LibBio.MAPPA_SOTTO_TITOLO, titoloSottopagina + '/' + primaLettera)
                mappa.put(LibBio.MAPPA_LISTA, listaMappeDidascalie)
                mappa.put(LibBio.MAPPA_NUMERO, listaMappeDidascalie.size())
                mappa.put(LibBio.MAPPA_ORDINE, Ordinamento.prestabilitoInMappa.toString())
                if (livello < 2) {
                    mappa.put(LibBio.MAPPA_SOTTOPAGINA, listaMappeDidascalie.size() > NUM_RIGHE_PER_CARATTERE_SOTTOPAGINA)
                } else {
                    mappa.put(LibBio.MAPPA_SOTTOPAGINA, false)
                }// fine del blocco if-else
                mappa.put(LibBio.MAPPA_ATTIVITA, attivita + '/' + primaLettera)
                mappa.put(LibBio.MAPPA_NAZIONALITA, nazionalita)
                mappa.put(LibBio.MAPPA_LIVELLO, livello + 1)
                listaSottoMappe.add(mappa)
            } // fine del ciclo each
        }// fine del blocco if

        return listaSottoMappe
    }// fine del metodo

} // fine della classe
