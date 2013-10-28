package it.algos.botbio

import it.algos.algoslib.LibTesto
import it.algos.algoswiki.QueryPag
import it.algos.algoswiki.WikiLib

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 11-9-13
 * Time: 08:42
 */
class LibBioTest extends GroovyTestCase {

    // Setup logic here
    void setUp() {
    } // fine del metodo iniziale

    // Tear down logic here
    void tearDown() {
    } // fine del metodo iniziale

    void testVoceOrdinaMappa() {
        LinkedHashMap mappaBio
        LinkedHashMap ottenuto

        mappaBio = new LinkedHashMap()
        mappaBio.put('AnnoMorte', '2145')
        mappaBio.put('Attività', 'pescatore')
        mappaBio.put('Nome', 'Mario')

        ottenuto = LibBio.riordinaMappa(mappaBio)
    } // fine del test


    void testVoceErrata() {
        LinkedHashMap mappaReali = null
        String titoloVoce
        String testoVoce
        String testoTemplate
        WrapBio wrap
        int pageid

        titoloVoce = 'Jean-Mohammed Abd-el-Jalil'
        testoVoce = QueryPag.getTesto(titoloVoce)
        testoTemplate = WikiLib.estraeTmpTesto(testoVoce)
        mappaReali = LibBio.getMappaRealiBio(testoTemplate, titoloVoce)
        assert mappaReali != null
        assert mappaReali['Nome'] == 'Jean Mohamed'
        assert mappaReali['Attività'] == 'orientalista'
        assert mappaReali['Nazionalità'] == 'marocchino'

        titoloVoce = 'Imed Ben Younes'
        testoVoce = QueryPag.getTesto(titoloVoce)
        testoTemplate = WikiLib.estraeTmpTesto(testoVoce)
        mappaReali = LibBio.getMappaRealiBio(testoTemplate, titoloVoce)
        assert mappaReali != null
        assert mappaReali['Nome'] == 'Imed'
        assert mappaReali['Attività'] == 'ex calciatore'
        assert mappaReali['Nazionalità'] == 'tunisino'
    } // fine del test

    void testPercentuale() {
        String percentuale
        int vociTotali
        int vociCat = 245667

        vociTotali = 255
        percentuale = LibTesto.formatPercentuale(vociTotali, vociCat)
        println('Voci ' + vociTotali + ' : percentuale ' + percentuale)

        vociTotali = 1255
        percentuale = LibTesto.formatPercentuale(vociTotali, vociCat)
        println('Voci ' + vociTotali + ' : percentuale ' + percentuale)

        vociTotali = 1755
        percentuale = LibTesto.formatPercentuale(vociTotali, vociCat)
        println('Voci ' + vociTotali + ' : percentuale ' + percentuale)

        vociTotali = 2255
        percentuale = LibTesto.formatPercentuale(vociTotali, vociCat)
        println('Voci ' + vociTotali + ' : percentuale ' + percentuale)

        vociTotali = 3255
        percentuale = LibTesto.formatPercentuale(vociTotali, vociCat)
        println('Voci ' + vociTotali + ' : percentuale ' + percentuale)

        vociTotali = 4205
        percentuale = LibTesto.formatPercentuale(vociTotali, vociCat)
        println('Voci ' + vociTotali + ' : percentuale ' + percentuale)

        vociTotali = 4505
        percentuale = LibTesto.formatPercentuale(vociTotali, vociCat)
        println('Voci ' + vociTotali + ' : percentuale ' + percentuale)
    } // fine del test

    void testLogVoci() {
        String avviso
        boolean debug = true
        int vociTotali
        int numVoci
        long durata

        numVoci = 100
        durata = 395000
        vociTotali = 255
        avviso = LibBio.gestVoci(null, debug, numVoci, durata, vociTotali)
        println(avviso)

        numVoci = 1000
        durata = 2263000
        vociTotali = 1255
        avviso = LibBio.gestVoci(null, debug, numVoci, durata, vociTotali)
        println(avviso)

        numVoci = 500
        durata = 576000
        vociTotali = 1755
        avviso = LibBio.gestVoci(null, debug, numVoci, durata, vociTotali)
        println(avviso)

        numVoci = 500
        durata = 680000
        vociTotali = 2255
        avviso = LibBio.gestVoci(null, debug, numVoci, durata, vociTotali)
        println(avviso)

        numVoci = 500
        durata = 828000
        vociTotali = 3255
        avviso = LibBio.gestVoci(null, debug, numVoci, durata, vociTotali)
        println(avviso)

        numVoci = 950
        durata = 902000
        vociTotali = 4205
        avviso = LibBio.gestVoci(null, debug, numVoci, durata, vociTotali)
        println(avviso)

        numVoci = 300
        durata = 678000
        vociTotali = 4505
        avviso = LibBio.gestVoci(null, debug, numVoci, durata, vociTotali)
        println(avviso)
    } // fine del test

} // fine della classe di test
