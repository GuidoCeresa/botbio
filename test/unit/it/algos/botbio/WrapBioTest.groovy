package it.algos.botbio

import it.algos.algoswiki.QueryPag
import it.algos.algoswiki.WikiLib

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 29-8-13
 * Time: 07:55
 */

class WrapBioTest extends GroovyTestCase {

    private static final int PAGE_ID = 4163337
    private static String MULTI_PAGE = '4163337|3427955|581968'

    // Setup logic here
    void setUp() {
    } // fine del metodo iniziale

    // Tear down logic here
    void tearDown() {
    } // fine del metodo iniziale

    //--costruzione da pageid
    void testSingolaVoce() {
        WrapBio wrap
        int pageid = PAGE_ID
        String titolo = 'Abramo di Harran'
        Map mappa

        wrap = new WrapBio(pageid)
        assert wrap != null
        mappa = wrap.getMappaPar()

        assert mappa != null
        assert mappa.size() == 14

        assert mappa['pageid'] != null
        assert mappa['pageid'] instanceof Integer
        assert mappa['pageid'] == PAGE_ID
        assert mappa['ns'] != null
        assert mappa['ns'] instanceof Integer
        assert mappa['ns'] == 0
        assert mappa['title'] != null
        assert mappa['title'] instanceof String
        assert mappa['title'] == titolo
        assert mappa['revid'] != null
        assert mappa['revid'] instanceof Integer
        assert mappa['parentid'] != null
        assert mappa['parentid'] instanceof Integer
        assert mappa['minor'] != null
        assert mappa['user'] != null
        assert mappa['user'] instanceof String
        assert mappa['userid'] != null
        assert mappa['userid'] instanceof Integer
        assert mappa['timestamp'] != null
        assert mappa['size'] != null
        assert mappa['size'] instanceof Integer
        assert mappa['size'] > 200
        assert mappa['comment'] != null
        assert mappa['comment'] instanceof String
        assert mappa['contentformat'] != null
        assert mappa['contentformat'] instanceof String
        assert mappa['contentmodel'] != null
        assert mappa['contentmodel'] instanceof String
        assert mappa['testo'] != null
        assert mappa['testo'] instanceof String
    } // fine del test

    //--mappa reali
    void testBio() {
        WrapBio wrap
        String titolo
        String testoVoce
        String testoTemplate
        Map mappa

        titolo = 'Ion C. Brătianu'
        testoVoce = QueryPag.getTesto(titolo)
        testoTemplate = WikiLib.estraeTmpTesto(testoVoce)
        mappa = WikiLib.estraeTmpMappa(testoTemplate)

        assert mappa != null
        assert mappa['Attività'] == 'politico'
        assert mappa['Immagine'] == 'IC Bratianu.jpg'
        def stop
    } // fine del test

    //--costruzione da mappa query
    void testVociMultiple() {
        QueryMultiBio query
        String pageids = MULTI_PAGE
        ArrayList listaMappe
        Map mappa

        query = new QueryMultiBio(pageids)
        assert query != null
        listaMappe = query.getListaMappe()
        assert listaMappe != null
        assert listaMappe.size() == 3
        mappa = listaMappe[0]
        assert mappa != null
        assert mappa.size() >= 13
        assert mappa['pageid'] != null
        assert mappa['pageid'] instanceof Integer
        assert mappa['pageid'] != null
        assert mappa['ns'] != null
        assert mappa['ns'] instanceof Integer
        assert mappa['ns'] == 0
        assert mappa['title'] != null
        assert mappa['title'] instanceof String
        assert mappa['title'] != null
        assert mappa['revid'] != null
        assert mappa['revid'] instanceof Integer
        assert mappa['parentid'] != null
        assert mappa['parentid'] instanceof Integer
        assert mappa['minor'] != null
        assert mappa['user'] != null
        assert mappa['user'] instanceof String
        assert mappa['userid'] != null
        assert mappa['userid'] instanceof Integer
        assert mappa['timestamp'] != null
        assert mappa['size'] != null
        assert mappa['size'] instanceof Integer
        assert mappa['size'] > 200
        assert mappa['comment'] != null
        assert mappa['comment'] instanceof String
        assert mappa['contentformat'] != null
        assert mappa['contentformat'] instanceof String
        assert mappa['contentmodel'] != null
        assert mappa['contentmodel'] instanceof String
        assert mappa['testo'] != null
        assert mappa['testo'] instanceof String
        assert mappa['testo'].startsWith('{{Bio')
    } // fine del test

    //--costruzione da mappa query
    void testVociMultiple2() {
        QueryMultiBio query
        WrapBio wrap
        String pageids = MULTI_PAGE
        ArrayList listaMappe
        HashMap mappa
        HashMap mappaPar
        String titoloVoce
        int pageid
        String testoVoce
        String testoTemplateOriginale  // testo del template originale presente sul server

        query = new QueryMultiBio(pageids)
        assert query != null
        listaMappe = query.getListaMappe()
        assert listaMappe != null
        assert listaMappe.size() == 3
        listaMappe.each {
            mappa = (HashMap) it
            wrap = new WrapBio(mappa)
            mappaPar = wrap.getMappaPar()
            assert mappaPar != null

            titoloVoce = wrap.getTitoloVoce()
            assert titoloVoce != null
            assert titoloVoce instanceof String

            pageid = wrap.getPageid()
            assert pageid != null
            assert pageid instanceof Integer

            testoVoce = wrap.getTestoVoce()
            assert testoVoce != null
            assert testoVoce instanceof String

            testoTemplateOriginale = wrap.getTestoTemplateOriginale()
            assert testoTemplateOriginale != null
            assert testoTemplateOriginale instanceof String
        } // fine del ciclo each

    } // fine del test

    void testValidita() {
        WrapBio wrap
        int pageid = 2880857
        String titolo = 'Luigi Belli'

        wrap = new WrapBio(PAGE_ID)
        assert wrap != null
        assert wrap.isValida()

        wrap = new WrapBio(pageid)
        assert wrap != null
        assert wrap.getTitoloVoce() == titolo
        assertFalse(wrap.isValida())
    } // fine del test

    void testErrore() {
        WrapBio wrap
        int pageid
        String titolo

        pageid = 4557769
        wrap = new WrapBio(pageid)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == 'Omri Ben Harush'

        pageid = 2384715
        wrap = new WrapBio(pageid)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == 'Mustafa Ben Halim'

        pageid = 3051359
        wrap = new WrapBio(pageid)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == 'Imed Ben Younes'

        pageid = 4595848
        wrap = new WrapBio(pageid)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == 'Jean-Mohammed Abd-el-Jalil'

        titolo = 'Peter Beardsley'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()

    } // fine del test

    void testErrore2() {
        WrapBio wrap
        String titolo

        titolo = 'Acrotato (figlio di Cleomene II)'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo

//        titolo = 'Ferdinando Bernini'
//        wrap = new WrapBio(titolo)
//        assert wrap != null
//        assert wrap.isValida()
//        assert wrap.getTitoloVoce() == titolo

        titolo='Luigi Belli'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo

    } // fine del test

} // fine della classe di test
