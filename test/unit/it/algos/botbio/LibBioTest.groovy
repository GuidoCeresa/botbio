package it.algos.botbio

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

} // fine della classe di test
