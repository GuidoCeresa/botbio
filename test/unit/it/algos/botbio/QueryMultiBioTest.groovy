package it.algos.botbio

import it.algos.algoswiki.QueryMultiId

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 8-9-13
 * Time: 08:08
 */
class QueryMultiBioTest extends GroovyTestCase {

    // Setup logic here
    void setUp() {
    } // fine del metodo iniziale

    // Tear down logic here
    void tearDown() {
    } // fine del metodo iniziale

    // Un test per ogni singola funzionalità
    // Leggo una categoria e poi leggo le pagine dagli Ids ricavati
    void testLegge() {
        String listaId = '4163337|3427955|581968'
        QueryMultiId query
        QueryMultiBio queryBio
        def listaMappe
        def listaMappeBio
        String testoVoceUno
        String testoTemplateUno

        query = new QueryMultiId(listaId)
        listaMappe = query.getListaMappe()
        assert listaMappe instanceof ArrayList<Map>
        assert listaMappe.size() == 3
        assert listaMappe[0].size() >= 13
        assert listaMappe[0].title == "Melania l'Anziana"
        assert listaMappe[0].testo != null
        assert listaMappe[0].size() >= 13
        assert listaMappe[1].title == "Abramo di Harran"
        assert listaMappe[1].testo != null
        assert listaMappe[0].size() >= 13
        assert listaMappe[2].title == "Manlio Teodoro"
        assert listaMappe[2].testo != null
        testoVoceUno = listaMappe[0].testo

        queryBio = new QueryMultiBio(listaId)
        listaMappeBio = queryBio.getListaMappe()
        assert listaMappeBio instanceof ArrayList<Map>
        assert listaMappeBio.size() == 3
        assert listaMappeBio[0].size() >= 13
        assert listaMappeBio[0].title == "Melania l'Anziana"
        assert listaMappeBio[0].testo != null
        assert listaMappeBio[0].size() >= 13
        assert listaMappeBio[1].title == "Abramo di Harran"
        assert listaMappeBio[1].testo != null
        assert listaMappeBio[0].size() >= 13
        assert listaMappeBio[2].title == "Manlio Teodoro"
        assert listaMappeBio[2].testo != null
        testoTemplateUno = listaMappeBio[0].testo

        assert testoTemplateUno != testoVoceUno
    } // fine del test

} // fine della classe di test
