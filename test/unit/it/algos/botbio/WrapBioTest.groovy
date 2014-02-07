package it.algos.botbio

import grails.test.GrailsUnitTestCase
import grails.test.mixin.TestFor
import it.algos.algoswiki.*
import org.junit.Before
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 29-8-13
 * Time: 07:55
 */
@TestFor(LogWikiService)
class WrapBioTest extends GrailsUnitTestCase {

    LogWikiService logWikiService = new LogWikiService()
    private Login loginDelTest
    private static boolean SCRIVE_SU_UTENTE_BIOBOT_LOG = false //attenzione a rimetterlo che scrive sulla pagina di log

    private static final int PAGE_ID = 4163337
    private static String MULTI_PAGE = '4163337|3427955|581968'

    // Setup logic here
    @Before
    void setUp() {
        loginDelTest = new Login('biobot', 'fulvia')
    } // fine del metodo iniziale

    // Tear down logic here
    void tearDown() {
    } // fine del metodo iniziale

    void testLunedi() {
        WrapBio wrap
        String titolo
        String templateOriginale
        String templateFinale
        def mappaReali
        def mappaBio

        titolo = 'Robert Bice'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        mappaReali = wrap.getMappaReali()
        mappaBio = wrap.getMappaBio()
        templateOriginale = wrap.getTestoTemplateOriginale()
        templateFinale = wrap.getTestoTemplateFinale()
        def stop3

        titolo = 'Serhij Baškyrov'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        mappaReali = wrap.getMappaReali()
        mappaBio = wrap.getMappaBio()
        templateOriginale = wrap.getTestoTemplateOriginale()
        templateFinale = wrap.getTestoTemplateFinale()
        def stop

        titolo = 'James Baskett'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        mappaReali = wrap.getMappaReali()
        mappaBio = wrap.getMappaBio()
        templateOriginale = wrap.getTestoTemplateOriginale()
        templateFinale = wrap.getTestoTemplateFinale()
        def stop2

        titolo = 'Mario Bergara'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assertFalse(wrap.isValida())
        mappaReali = wrap.getMappaReali()
        mappaBio = wrap.getMappaBio()
        templateOriginale = wrap.getTestoTemplateOriginale()
        templateFinale = wrap.getTestoTemplateFinale()
        def stop55

        titolo = 'Ioan Neculaie'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        mappaReali = wrap.getMappaReali()
        mappaBio = wrap.getMappaBio()
        templateOriginale = wrap.getTestoTemplateOriginale()
        templateFinale = wrap.getTestoTemplateFinale()
        def stop555
    } // fine del test

    void testDidascalia() {
        WrapBio wrap
        String titolo = 'Robert T. Bakker'
        String testoTemplate
        def mappa
        titolo = 'Dmitrij Baškevič'
//        titolo = 'Utente:Gac/Sandbox8'

        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()

        testoTemplate = wrap.getTestoTemplateOriginale()
        mappa = wrap.getMappaBio()

        def stop
    } // fine del test

    void estUtf8() {
        WrapBio wrap
        String titolo = 'Dušan Uškovič'
        titolo = 'Pietro Barillà'

        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo
        def a = new EditAdd(loginDelTest, titolo, 'provaBot', 'test')
    } // fine del test

    void testErrore3() {
        WrapBio wrap
        String titolo
        BioWiki bioWiki
        BioGrails bioGrails

        titolo = 'CJ Adams'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo
        bioWiki = wrap.getBioOriginale()
        bioGrails = wrap.getBioGrails()
        def stop
    } // fine del test


    void testScrittura() {
        WrapBio wrap
        String titolo
        String template
        BioWiki bioWiki
        BioGrails bioGrails
        int pageid
        def unGiorno

        titolo = 'Federico Baccomo'
        pageid = QueryVoce.leggePageid(titolo)

//        unGiorno = new Giorno(normale: 17, bisestile: 51, nome: '13 agosto', titolo: '13 agosto')
//        mockDomain(Giorno, [unGiorno])
//        nonServe.save(flush: true)

        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo
        template = wrap.getTestoTemplateOriginale()

        bioWiki = wrap.getBioOriginale()
        bioGrails = wrap.getBioGrails()
        def stop
    } // fine del test


    void testTmplErrato() {
        String titolo
        WrapBio wrap
        String testoVoce
        String testoTemplate
        StatoBio voceBioOttenuta
        StatoBio voceBioRichiesta

        titolo = 'Utente:Biobot/2'  //vuota
        voceBioRichiesta = StatoBio.vuota
        wrap = new WrapBio(titolo)
        assert wrap
        testoVoce = wrap.getTestoVoce()
        assert testoVoce == ''
        testoTemplate = wrap.getTestoTemplateOriginale()
        assert testoTemplate == ''
        assert testoTemplate == testoVoce
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta == voceBioRichiesta

        titolo = 'Utente:Biobot/3'  //redirect
        voceBioRichiesta = StatoBio.redirect
        wrap = new WrapBio(titolo)
        assert wrap
        testoVoce = wrap.getTestoVoce()
        assert testoVoce
        testoTemplate = wrap.getTestoTemplateOriginale()
        assert testoTemplate == ''
        assert testoTemplate != testoVoce
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta == voceBioRichiesta

        titolo = 'Utente:Biobot/4'  //template errato
        voceBioRichiesta = StatoBio.bioErrato
        wrap = new WrapBio(titolo)
        assert wrap
        testoVoce = wrap.getTestoVoce()
        assert testoVoce
        testoTemplate = wrap.getTestoTemplateOriginale()
        assert testoTemplate
        assert testoTemplate == testoVoce
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta == voceBioRichiesta

        titolo = 'Utente:Biobot/5'  //template incompleto
        voceBioRichiesta = StatoBio.bioIncompleto
        wrap = new WrapBio(titolo)
        assert wrap
        testoVoce = wrap.getTestoVoce()
        assert testoVoce
        testoTemplate = wrap.getTestoTemplateOriginale()
        assert testoTemplate
        assert testoTemplate == testoVoce
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta == voceBioRichiesta

        titolo = 'Utente:Biobot/6'  //solo template
        voceBioRichiesta = StatoBio.bioNormale
        wrap = new WrapBio(titolo)
        assert wrap
        testoVoce = wrap.getTestoVoce()
        assert testoVoce
        testoTemplate = wrap.getTestoTemplateOriginale()
        assert testoTemplate
        assert testoTemplate == testoVoce
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta == voceBioRichiesta

        titolo = 'Utente:Biobot/7'  //testo più template
        voceBioRichiesta = StatoBio.bioNormale
        wrap = new WrapBio(titolo)
        assert wrap
        testoVoce = wrap.getTestoVoce()
        assert testoVoce
        testoTemplate = wrap.getTestoTemplateOriginale()
        assert testoTemplate
        assert testoTemplate != testoVoce
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta == voceBioRichiesta

        titolo = 'Artesa'  //disambigua
        voceBioRichiesta = StatoBio.disambigua
        wrap = new WrapBio(titolo)
        assert wrap
        testoVoce = wrap.getTestoVoce()
        assert testoVoce
        testoTemplate = wrap.getTestoTemplateOriginale()
        assert testoTemplate == ''
        assert testoTemplate != testoVoce
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta == voceBioRichiesta

        titolo = 'Coripe'  //testo normale senza template
        voceBioRichiesta = StatoBio.senzaBio
        wrap = new WrapBio(titolo)
        assert wrap
        testoVoce = wrap.getTestoVoce()
        assert testoVoce
        testoTemplate = wrap.getTestoTemplateOriginale()
        assert testoTemplate == ''
        assert testoTemplate != testoVoce
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta == voceBioRichiesta
    } // fine del test

    void testCompletaVoci() {
        String titoloBase = 'Utente:Biobot/'
        String titolo
        int num = 6
        int pageid
        WrapBio wrap
        StatoBio voceBioOttenuta
        StatoBio voceBioRichiesta

        titolo = 'Utente:Biobot/paginamaiscritta' //pagina inesistente
        voceBioRichiesta = StatoBio.maiEsistita
        wrap = new WrapBio(titolo)
        assert wrap
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta
        assert voceBioOttenuta == voceBioRichiesta

        pageid = 4689129 //pagina cancellata
        voceBioRichiesta = StatoBio.maiEsistita  //@todo dovrebbe diventare StatoBio.cancellata
        wrap = new WrapBio(pageid)
        assert wrap
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta
        assert voceBioOttenuta == voceBioRichiesta

        titolo = titoloBase + 2 //pagina vuota
        voceBioRichiesta = StatoBio.vuota
        wrap = new WrapBio(titolo)
        assert wrap
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta
        assert voceBioOttenuta == voceBioRichiesta

        titolo = titoloBase + 3 //pagina redirect
        voceBioRichiesta = StatoBio.redirect
        wrap = new WrapBio(titolo)
        assert wrap
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta
        assert voceBioOttenuta == voceBioRichiesta

        titolo = titoloBase + 4 //template errato
        voceBioRichiesta = StatoBio.bioErrato
        wrap = new WrapBio(titolo)
        assert wrap
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta
        assert voceBioOttenuta == voceBioRichiesta

        titolo = titoloBase + 5 //template errato
        voceBioRichiesta = StatoBio.bioIncompleto
        wrap = new WrapBio(titolo)
        assert wrap
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta
        assert voceBioOttenuta == voceBioRichiesta

        titolo = titoloBase + 6 //template normale e completo
        voceBioRichiesta = StatoBio.bioNormale
        wrap = new WrapBio(titolo)
        assert wrap
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta
        assert voceBioOttenuta == voceBioRichiesta

        titolo = 'Artesa' //pagina redirect
        voceBioRichiesta = StatoBio.disambigua
        wrap = new WrapBio(titolo)
        assert wrap
        voceBioOttenuta = wrap.getStatoBio()
        assert voceBioOttenuta
        assert voceBioOttenuta == voceBioRichiesta
    } // fine del test

    //--costruzione da array di pageid
    @Test
    void testArrayDebug() {
        ArrayList listaPageids
        Query query
        ArrayList listaMappe
        WrapBio wrapBio
        String title
        boolean debug = false
        HashMap mappa
        StatoBio statoBio
        boolean registrata
        int pageid

        listaPageids = [0, 4689129, 4689130, 4689133, 160011, 4689135, 4689136, 4689137, 142459]
        query = new QueryMultiBio(listaPageids)
        listaMappe = query.getListaMappe()

        if (listaMappe && listaMappe.size() > 1) {
            listaMappe.each {
                mappa = it
                wrapBio = new WrapBio(mappa)
                title = wrapBio.getTitoloVoce()
                pageid = wrapBio.getPageid()
                statoBio = wrapBio.getStatoBio()
                switch (statoBio) {
                    case StatoBio.bioNormale:
                        if (!debug) {
//                            wrapBio.registraBioWiki()
                            registrata = true
                        }// fine del blocco if
                        break
                    case StatoBio.bioIncompleto:
                        scriveLog(LogTipo.warn, "Alla voce [[${title}]] mancano alcuni campi indispensabili per il funzionamento del tmpl Bio")
                        registrata = false
                        break
                    case StatoBio.bioErrato:
                        scriveLog(LogTipo.warn, "Il tmpl Bio della voce [[${title}]] è errato")
                        registrata = false
                        break
                    case StatoBio.senzaBio:
                        scriveLog(LogTipo.warn, "Nella voce [[${title}]] manca completamente il tmpl Bio")
                        registrata = false
                        break
                    case StatoBio.vuota:
                        scriveLog(LogTipo.warn, "La voce [[${title}]] non ha nessun contenuto di testo")
                        registrata = false
                        break
                    case StatoBio.redirect:
                        scriveLog(LogTipo.warn, "La voce [[${title}]] non è una voce biografica, ma un redirect")
                        registrata = false
                        break
                    case StatoBio.disambigua:
                        scriveLog(LogTipo.warn, "La voce [[${title}]] non è una voce biografica, ma una disambigua")
                        registrata = false
                        break
                    case StatoBio.maiEsistita:
                        if (title) {
                            scriveLog(LogTipo.warn, "La voce [[${title}]] non esiste")
                        } else {
                            if (pageid) {
                                scriveLog(LogTipo.warn, "Non esiste la voce col pageid = ${pageid}")
                            } else {
                                scriveLog(LogTipo.warn, "Non esiste una pagina")
                            }// fine del blocco if-else
                        }// fine del blocco if-else
                        registrata = false
                        break
                    default: // caso non definito
                        break
                } // fine del blocco switch

                if (!registrata) {
                    println("regolaBloccoNuovoModificato - La voce ${title}, non è stata registrata")
                }// fine del blocco if
            }// fine del ciclo each
        }// fine del blocco if
    } // fine del test

    void scriveLog(LogTipo logTipo, String testo) {
        Login login = loginDelTest

        if (SCRIVE_SU_UTENTE_BIOBOT_LOG && login) {
            logWikiService.scriveLogin(login, testo, logTipo)
        } else {
            println(testo)
        }// fine del blocco if-else
    } // fine del metodo di supporto

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
        assert mappa.size() == 15

        assert mappa[Const.TAG_STATO_PAGINA] == StatoPagina.normale
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
        assert wrap.isValida()
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

        titolo = 'Luigi Belli'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo

        titolo = 'Hassan Rouhani'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo

        titolo = 'Gregorius Bar-Hebraeus'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo

        titolo = 'Ibn al-Awwam'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo

        titolo = 'Manaf Abushgeer'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo

        titolo = 'Alon Abutbul'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo

        titolo = 'Sayf al-Dawla'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo

        titolo = 'Falco Accame'
        wrap = new WrapBio(titolo)
        assert wrap != null
        assert wrap.isValida()
        assert wrap.getTitoloVoce() == titolo
    } // fine del test

} // fine della classe di test
