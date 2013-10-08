package it.algos.botbio

import it.algos.algoswiki.Query
import it.algos.algoswiki.QueryVoce

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 4-9-13
 * Time: 07:54
 */
class QueryBioTest extends GroovyTestCase {

    private static final int PAGE_ID = 4163337

    private static final int PAGE_ID_NOT_BIO = 735901

    // Setup logic here
    void setUp() {
    } // fine del metodo iniziale

    // Tear down logic here
    void tearDown() {
    } // fine del metodo iniziale

    //--costruzione da pageid
    void testLegge() {
        int pageid = PAGE_ID
        Query query
        String testoBio
        String testoBioStatic

        query = new QueryBio(pageid)
        assert query != null
        testoBio = query.getTestoBio()
        assert testoBio != null
        assert testoBio.length() > 100

        testoBioStatic = QueryBio.leggeTestoBio(PAGE_ID)
        assert testoBioStatic == testoBio
    } // fine del test

    //--costruzione da pageid
    void testCheckBio() {
        String titoloNoBio
        boolean isBio

        isBio = QueryBio.isBio(PAGE_ID)
        assert isBio

        titoloNoBio = QueryVoce.leggeTitolo(PAGE_ID_NOT_BIO)
        assert titoloNoBio == 'Arrondissement di Verviers'
        isBio = QueryBio.isBio(PAGE_ID_NOT_BIO)
        assertFalse(isBio)
    } // fine del test

} // fine della classe di test
