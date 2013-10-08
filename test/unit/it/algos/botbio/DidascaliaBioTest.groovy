package it.algos.botbio

import grails.test.*

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 1-10-13
 * Time: 07:47
 */

class DidascaliaBioTest extends GrailsUnitTestCase {

    BioService bioService = new BioService()

    private static int PAGEID = 2553376

    protected void setUp() {
        super.setUp()
    }// fine del metodo Grails


    protected void tearDown() {
        super.tearDown()
    }// fine del metodo Grails

    // Legge una singola pagina
    void testDidascalieAaron() {
        String titolo = 'Vasile Aaron'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        DidascaliaBio didascalia

        //--creazione della didascalia
        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoNascita(1770)
        didascalia.setAnnoMorte(1822)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('poeta')
        didascalia.setNazionalita('rumeno')
        didascalia.setInizializza()

        // TipoDidascalia base (nome e cognome)
        didascaliaBaseAaron(didascalia)

        // TipoDidascalia crono (nome, cognome ed anni di nascita e morte)
        didascaliaCronoAaron(didascalia)

        // TipoDidascalia crono con simboli (nome, cognome ed anni di nascita e morte con simboli)
        didascaliaCronoSimboliAaron(didascalia)

        // TipoDidascalia semplice (nome, cognome, attività, nazionalità)
        didascaliaSempliceAaron(didascalia)

        // TipoDidascalia completa (nome, cognome, attività, nazionalità ed anni di nascita e morte)
        didascaliaCompletaAaron(didascalia)

        // TipoDidascalia completa simboli (nome, cognome, attività, nazionalità ed anni di nascita e morte con simboli)
        didascaliaCompletaSimboliAaron(didascalia)

        // TipoDidascalia estesa (nome, cognome, attività, nazionalità, città ed anni di nascita e morte)
        didascaliaEstesaAaron(didascalia)

        // TipoDidascalia estesa simboli (nome, cognome, attività, nazionalità, città ed anni di nascita e morte con simboli)
        didascaliaEstesaSimboliAaron(didascalia)

        // TipoDidascalia nati giorno (anno di nascita, nome, cognome, attività, nazionalità, anno di morte)
        didascaliaNatiGiornoAaron(didascalia)

        // TipoDidascalia nati anno (giorno di nascita, nome, cognome, attività, nazionalità, anno di morte)
        didascaliaNatiAnnoAaron(didascalia)

        // TipoDidascalia morti giorno (anno di morte, nome, cognome, attività, nazionalità, anno di nascita)
        didascaliaMortiGiornoAaron(didascalia)

        // TipoDidascalia morti anno (giorno di morte, nome, cognome, attività, nazionalità, anno di nascita)
        didascaliaMortiAnnoAaron(didascalia)
    }// fine del test

    // Legge una singola pagina
    void testDidascalieBotta() {
        String titolo = 'Emil Botta'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoNascita(1911)
        didascalia.setAnnoMorte(1977)
        // i giorni si aggiungono solo perché dal Test non accede al database linkato dei giorni
        didascalia.setGiornoMeseNascita('15 settembre')
        didascalia.setGiornoMeseMorte('24 luglio')
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('attore')
        didascalia.setAttivita2('poeta')
        didascalia.setNazionalita('rumeno')
        didascalia.setInizializza()

        // TipoDidascalia base (nome e cognome)
        didascaliaBaseBotta(didascalia)

        // TipoDidascalia crono (nome, cognome ed anni di nascita e morte)
        didascaliaCronoBotta(didascalia)

        // TipoDidascalia crono con simboli (nome, cognome ed anni di nascita e morte con simboli)
        didascaliaCronoSimboliBotta(didascalia)

        // TipoDidascalia semplice (nome, cognome, attività, nazionalità)
        didascaliaSempliceBotta(didascalia)

        // TipoDidascalia completa (nome, cognome, attività, nazionalità ed anni di nascita e morte)
        didascaliaCompletaBotta(didascalia)

        // TipoDidascalia completa simboli (nome, cognome, attività, nazionalità ed anni di nascita e morte con simboli)
        didascaliaCompletaSimboliBotta(didascalia)

        // TipoDidascalia estesa (nome, cognome, attività, nazionalità, città ed anni di nascita e morte)
        didascaliaEstesaBotta(didascalia)

        // TipoDidascalia estesa simboli (nome, cognome, attività, nazionalità, città ed anni di nascita e morte con simboli)
        didascaliaEstesaSimboliBotta(didascalia)

        // TipoDidascalia nati giorno (anno di nascita, nome, cognome, attività, nazionalità, anno di morte)
        didascaliaNatiGiornoBotta(didascalia)

        // TipoDidascalia nati anno (giorno di nascita, nome, cognome, attività, nazionalità, anno di morte)
        didascaliaNatiAnnoBotta(didascalia)

        // TipoDidascalia morti giorno (anno di morte, nome, cognome, attività, nazionalità, anno di nascita)
        didascaliaMortiGiornoBotta(didascalia)

        // TipoDidascalia morti anno (giorno di morte, nome, cognome, attività, nazionalità, anno di nascita)
        didascaliaMortiAnnoBotta(didascalia)
    }// fine del test

    // TipoDidascalia base (nome e cognome)
    private static void didascaliaBaseAaron(DidascaliaBio didascalia) {
        String previsto = '[[Vasile Aaron]]'
        String risultato

        risultato = didascalia.getTestoBase()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia crono (nome, cognome ed anni di nascita e morte)
    static void didascaliaCronoAaron(DidascaliaBio didascalia) {
        String previsto = '[[Vasile Aaron]] ([[1770]] - [[1822]])'
        String risultato

        risultato = didascalia.getTestoCrono()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia crono con simboli (nome, cognome ed anni di nascita e morte con simboli)
    static void didascaliaCronoSimboliAaron(DidascaliaBio didascalia) {
        String previsto = '[[Vasile Aaron]] (n.[[1770]] - †[[1822]])'
        String risultato

        risultato = didascalia.getTestoCronoSimboli()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia semplice (nome, cognome, attività, nazionalità)
    static void didascaliaSempliceAaron(DidascaliaBio didascalia) {
        String previsto = '[[Vasile Aaron]], poeta rumeno'
        String risultato

        risultato = didascalia.getTestoSemplice()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia completa (nome, cognome, attività, nazionalità ed anni di nascita e morte)
    static void didascaliaCompletaAaron(DidascaliaBio didascalia) {
        String previsto = '[[Vasile Aaron]], poeta rumeno ([[1770]] - [[1822]])'
        String risultato

        risultato = didascalia.getTestoCompleta()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia completa simboli (nome, cognome, attività, nazionalità ed anni di nascita e morte con simboli)
    static void didascaliaCompletaSimboliAaron(DidascaliaBio didascalia) {
        String previsto = '[[Vasile Aaron]], poeta rumeno (n.[[1770]] - †[[1822]])'
        String risultato

        risultato = didascalia.getTestoCompletaSimboli()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia estesa (nome, cognome, attività, nazionalità, città ed anni di nascita e morte)
    static void didascaliaEstesaAaron(DidascaliaBio didascalia) {
        String previsto = '[[Vasile Aaron]], poeta rumeno ([[Vladimirescu]], [[1770]] - [[Sibiu]], [[1822]])'
        String risultato

        risultato = didascalia.getTestoEstesa()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia estesa simboli (nome, cognome, attività, nazionalità, città ed anni di nascita e morte con simboli)
    static void didascaliaEstesaSimboliAaron(DidascaliaBio didascalia) {
        String previsto = '[[Vasile Aaron]], poeta rumeno ([[Vladimirescu]], n.[[1770]] - [[Sibiu]], †[[1822]])'
        String risultato

        risultato = didascalia.getTestoEstesaSimboli()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia nati giorno (anno di nascita, nome, cognome, attività, nazionalità, anno di morte)
    static void didascaliaNatiGiornoAaron(DidascaliaBio didascalia) {
        String risultato

        risultato = didascalia.getTestoNatiGiorno()
        assert risultato == ''
    }// fine del test

    // TipoDidascalia nati anno (giorno di nascita, nome, cognome, attività, nazionalità, anno di morte)
    static void didascaliaNatiAnnoAaron(DidascaliaBio didascalia) {
        String previsto = '[[Vasile Aaron]], poeta rumeno († [[1822]])'
        String risultato

        risultato = didascalia.getTestoNatiAnno()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia morti giorno (anno di morte, nome, cognome, attività, nazionalità, anno di nascita)
    static void didascaliaMortiGiornoAaron(DidascaliaBio didascalia) {
        String risultato

        risultato = didascalia.getTestoMortiGiorno()
        assert risultato == ''
    }// fine del test

    // TipoDidascalia morti anno (giorno di morte, nome, cognome, attività, nazionalità, anno di nascita)
    static void didascaliaMortiAnnoAaron(DidascaliaBio didascalia) {
        String previsto = '[[Vasile Aaron]], poeta rumeno (n. [[1770]])'
        String risultato

        risultato = didascalia.getTestoMortiAnno()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia base (nome e cognome)
    private static void didascaliaBaseBotta(DidascaliaBio didascalia) {
        String previsto = '[[Emil Botta]]'
        String risultato

        risultato = didascalia.getTestoBase()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia crono (nome, cognome ed anni di nascita e morte)
    static void didascaliaCronoBotta(DidascaliaBio didascalia) {
        String previsto = '[[Emil Botta]] ([[1911]] - [[1977]])'
        String risultato

        risultato = didascalia.getTestoCrono()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia crono con simboli (nome, cognome ed anni di nascita e morte con simboli)
    static void didascaliaCronoSimboliBotta(DidascaliaBio didascalia) {
        String previsto = '[[Emil Botta]] (n.[[1911]] - †[[1977]])'
        String risultato

        risultato = didascalia.getTestoCronoSimboli()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia semplice (nome, cognome, attività, nazionalità)
    static void didascaliaSempliceBotta(DidascaliaBio didascalia) {
        String previsto = '[[Emil Botta]], attore e poeta rumeno'
        String risultato

        risultato = didascalia.getTestoSemplice()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia completa (nome, cognome, attività, nazionalità ed anni di nascita e morte)
    static void didascaliaCompletaBotta(DidascaliaBio didascalia) {
        String previsto = '[[Emil Botta]], attore e poeta rumeno ([[1911]] - [[1977]])'
        String risultato

        risultato = didascalia.getTestoCompleta()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia completa simboli (nome, cognome, attività, nazionalità ed anni di nascita e morte con simboli)
    static void didascaliaCompletaSimboliBotta(DidascaliaBio didascalia) {
        String previsto = '[[Emil Botta]], attore e poeta rumeno (n.[[1911]] - †[[1977]])'
        String risultato

        risultato = didascalia.getTestoCompletaSimboli()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia estesa (nome, cognome, attività, nazionalità, città ed anni di nascita e morte)
    static void didascaliaEstesaBotta(DidascaliaBio didascalia) {
        String previsto = '[[Emil Botta]], attore e poeta rumeno ([[Adjud]], [[1911]] - [[Bucarest]], [[1977]])'
        String risultato

        risultato = didascalia.getTestoEstesa()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia estesa simboli (nome, cognome, attività, nazionalità, città ed anni di nascita e morte con simboli)
    static void didascaliaEstesaSimboliBotta(DidascaliaBio didascalia) {
        String previsto = '[[Emil Botta]], attore e poeta rumeno ([[Adjud]], n.[[1911]] - [[Bucarest]], †[[1977]])'
        String risultato

        risultato = didascalia.getTestoEstesaSimboli()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia nati giorno (anno di nascita, nome, cognome, attività, nazionalità, anno di morte)
    static void didascaliaNatiGiornoBotta(DidascaliaBio didascalia) {
        String previsto = '[[1911]] - [[Emil Botta]], attore e poeta rumeno († [[1977]])'
        String risultato

        risultato = didascalia.getTestoNatiGiorno()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia nati anno (giorno di nascita, nome, cognome, attività, nazionalità, anno di morte)
    static void didascaliaNatiAnnoBotta(DidascaliaBio didascalia) {
        String previsto = '[[15 settembre]] - [[Emil Botta]], attore e poeta rumeno († [[1977]])'
        String risultato

        risultato = didascalia.getTestoNatiAnno()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia morti giorno (anno di morte, nome, cognome, attività, nazionalità, anno di nascita)
    static void didascaliaMortiGiornoBotta(DidascaliaBio didascalia) {
        String previsto = '[[1977]] - [[Emil Botta]], attore e poeta rumeno (n. [[1911]])'
        String risultato

        risultato = didascalia.getTestoMortiGiorno()
        assert risultato == previsto
    }// fine del test

    // TipoDidascalia morti anno (giorno di morte, nome, cognome, attività, nazionalità, anno di nascita)
    static void didascaliaMortiAnnoBotta(DidascaliaBio didascalia) {
        String previsto = '[[24 luglio]] - [[Emil Botta]], attore e poeta rumeno (n. [[1911]])'
        String risultato

        risultato = didascalia.getTestoMortiAnno()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie2() {
        String titolo = 'Antonio Abati'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Antonio Abati]], letterato italiano (†[[1667]])'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoMorte(1667)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('letterato')
        didascalia.setNazionalita('italiano')
        didascalia.setInizializza()

        risultato = didascalia.getTestoCompleta()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie3() {
        String titolo = 'Pipin Ferreras'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Pipin Ferreras]], apneista cubano (n.[[1962]])'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoNascita(1962)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('apneista')
        didascalia.setNazionalita('cubano')
        didascalia.setInizializza()

        risultato = didascalia.getTestoCompleta()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie4() {
        String titolo = 'Antonio Abbondanti'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Antonio Abbondanti]], poeta e scrittore italiano'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('poeta e scrittore')
        didascalia.setNazionalita('italiano')
        didascalia.setInizializza()

        risultato = didascalia.getTestoCompleta()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie5() {
        String titolo = 'Carlo Martello'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Carlo Martello]] (†[[741]])'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoMorte(741)
        didascalia.setInizializza()

        risultato = didascalia.getTestoCompleta()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie6() {
        String titolo = 'Antonio Blanco'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Antonio Blanco]], calciatore argentino'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('calciatore')
        didascalia.setNazionalita('argentino')
        didascalia.setInizializza()

        risultato = didascalia.getTestoCompleta()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie7() {
        String titolo = 'Antonio Blanco'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Antonio Blanco]], calciatore argentino'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('calciatore')
        didascalia.setNazionalita('argentino')
        didascalia.setInizializza()

        risultato = didascalia.getTestoEstesa()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie8() {
        String titolo = 'Andrea Ballarin'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Andrea Ballarin]], liutaio italiano ([[Thiene]], n.[[1962]])'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoNascita(1962)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('liutaio')
        didascalia.setNazionalita('italiano')
        didascalia.setInizializza()

        risultato = didascalia.getTestoEstesaSimboli()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie9() {
        String titolo = 'Arnaud Amaury'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Arnaud Amaury]], abate francese (n... - [[Narbona]], †[[1225]])'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoMorte(1225)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('abate')
        didascalia.setNazionalita('francese')
        didascalia.setInizializza()

        risultato = didascalia.getTestoEstesaSimboli()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie10() {
        String titolo = 'Androin de la Roche'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Androin de la Roche]], abate e cardinale francese (n... - [[Viterbo]], †[[1363]])'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoMorte(1363)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('abate e cardinale')
        didascalia.setNazionalita('francese')
        didascalia.setInizializza()

        risultato = didascalia.getTestoEstesaSimboli()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie11() {
        String titolo = 'Airey Neave'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Airey Neave]], militare, politico e agente segreto britannico (n.[[1916]] - [[Londra]], †[[1979]])'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoNascita(1916)
        didascalia.setAnnoMorte(1979)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('militare, politico e agente segreto')
        didascalia.setNazionalita('britannico')
        didascalia.setInizializza()

        risultato = didascalia.getTestoEstesaSimboli()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie12() {
        String titolo = 'Lucy Percy'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Lucy Percy]], agente segreto britannica (n.[[1599]] - †[[1660]])'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoNascita(1599)
        didascalia.setAnnoMorte(1660)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('agente segreto')
        didascalia.setNazionalita('britannica')
        didascalia.setInizializza()

        risultato = didascalia.getTestoEstesaSimboli()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie13() {
        String titolo = 'Alfano di Salerno'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Alfano di Salerno]], abate, medico e letterato italiano ([[Salerno]], n... - [[Salerno]], †[[1085]])'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoMorte(1085)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('abate, medico e letterato')
        didascalia.setNazionalita('italiano')
        didascalia.setInizializza()

        risultato = didascalia.getTestoEstesaSimboli()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie14() {
        String titolo = 'Antipapa Anastasio III'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Antipapa Anastasio III]], abate e filologo italiano ([[Roma]], n... - †[[879]])'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoMorte(879)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('abate e filologo')
        didascalia.setNazionalita('italiano')
        didascalia.setInizializza()

        risultato = didascalia.getTestoEstesaSimboli()
        assert risultato == previsto
    }// fine del test

    // Legge una singola pagina
    void testDidascalie15() {
        String titolo = 'Bertario di Montecassino'
        WrapBio wrap = new WrapBio(titolo)
        int pageid = wrap.getPageid()
        BioWiki bioWiki = wrap.getBioOriginale()
        BioGrails bioGrails = bioService.elaboraGrails(bioWiki, pageid)
        String previsto = '[[Bertario di Montecassino]], abate e santo italiano (n... - [[Cassino]], †[[883]])'
        String risultato
        DidascaliaBio didascalia

        didascalia = new DidascaliaBio(bioGrails)
        // gli anni si aggiungono solo perché dal Test non accede al database linkato degli anni
        didascalia.setAnnoMorte(883)
        // le descrizioni si aggiungono solo perché dal Test non accede ai database linkati di attività e nazionalità
        didascalia.setAttivita('abate e santo')
        didascalia.setNazionalita('italiano')
        didascalia.setInizializza()

        risultato = didascalia.getTestoEstesaSimboli()
        assert risultato == previsto
    }// fine del test

} // fine della classe
