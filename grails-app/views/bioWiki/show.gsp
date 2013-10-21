













<%@ page import="it.algos.botbio.BioWiki" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'bioWiki.label', default: 'BioWiki')}" />
    <title><g:message code="bioWiki.show.label" args="[entityName]" default="Mostra"/></title>
</head>
<body>
<a href="#show-bioWiki" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="bioWiki.list.label" args="[entityName]" default="Elenco"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="bioWiki.new.label" args="[entityName]" default="Nuovo"/></g:link></li>
    </ul>
</div>
<div id="show-bioWiki" class="content scaffold-show" role="main">
    <h1><g:message code="bioWiki.show.label" args="[entityName]" default="Mostra"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list bioWiki">
        
        <g:if test="${bioWikiInstance?.wikiUrl}">
            <li class="fieldcontain">
                <span id="wikiUrl-label" class="property-label"><g:message code="bioWiki.wikiUrl.label" default="Wiki Url" /></span>
                
                <span class="property-value" aria-labelledby="wikiUrl-label"><g:fieldValue bean="${bioWikiInstance}" field="wikiUrl"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.testoTemplate}">
            <li class="fieldcontain">
                <span id="testoTemplate-label" class="property-label"><g:message code="bioWiki.testoTemplate.label" default="Testo Template" /></span>
                
                <span class="property-value" aria-labelledby="testoTemplate-label"><g:fieldValue bean="${bioWikiInstance}" field="testoTemplate"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.pageid}">
            <li class="fieldcontain">
                <span id="pageid-label" class="property-label"><g:message code="bioWiki.pageid.label" default="Pageid" /></span>
                
                <span class="property-value" aria-labelledby="pageid-label"><g:fieldValue bean="${bioWikiInstance}" field="pageid"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.title}">
            <li class="fieldcontain">
                <span id="title-label" class="property-label"><g:message code="bioWiki.title.label" default="Title" /></span>
                
                <span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${bioWikiInstance}" field="title"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.ns}">
            <li class="fieldcontain">
                <span id="ns-label" class="property-label"><g:message code="bioWiki.ns.label" default="Ns" /></span>
                
                <span class="property-value" aria-labelledby="ns-label"><g:fieldValue bean="${bioWikiInstance}" field="ns"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.touched}">
            <li class="fieldcontain">
                <span id="touched-label" class="property-label"><g:message code="bioWiki.touched.label" default="Touched" /></span>
                
                <span class="property-value" aria-labelledby="touched-label"><g:formatDate date="${bioWikiInstance?.touched}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.revid}">
            <li class="fieldcontain">
                <span id="revid-label" class="property-label"><g:message code="bioWiki.revid.label" default="Revid" /></span>
                
                <span class="property-value" aria-labelledby="revid-label"><g:fieldValue bean="${bioWikiInstance}" field="revid"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.size}">
            <li class="fieldcontain">
                <span id="size-label" class="property-label"><g:message code="bioWiki.size.label" default="Size" /></span>
                
                <span class="property-value" aria-labelledby="size-label"><g:fieldValue bean="${bioWikiInstance}" field="size"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.user}">
            <li class="fieldcontain">
                <span id="user-label" class="property-label"><g:message code="bioWiki.user.label" default="User" /></span>
                
                <span class="property-value" aria-labelledby="user-label"><g:fieldValue bean="${bioWikiInstance}" field="user"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.timestamp}">
            <li class="fieldcontain">
                <span id="timestamp-label" class="property-label"><g:message code="bioWiki.timestamp.label" default="Timestamp" /></span>
                
                <span class="property-value" aria-labelledby="timestamp-label"><g:fieldValue bean="${bioWikiInstance}" field="timestamp"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.comment}">
            <li class="fieldcontain">
                <span id="comment-label" class="property-label"><g:message code="bioWiki.comment.label" default="Comment" /></span>
                
                <span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${bioWikiInstance}" field="comment"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.logNote}">
            <li class="fieldcontain">
                <span id="logNote-label" class="property-label"><g:message code="bioWiki.logNote.label" default="Log Note" /></span>
                
                <span class="property-value" aria-labelledby="logNote-label"><g:fieldValue bean="${bioWikiInstance}" field="logNote"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.logErr}">
            <li class="fieldcontain">
                <span id="logErr-label" class="property-label"><g:message code="bioWiki.logErr.label" default="Log Err" /></span>
                
                <span class="property-value" aria-labelledby="logErr-label"><g:fieldValue bean="${bioWikiInstance}" field="logErr"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.langlinks}">
            <li class="fieldcontain">
                <span id="langlinks-label" class="property-label"><g:message code="bioWiki.langlinks.label" default="Langlinks" /></span>
                
                <span class="property-value" aria-labelledby="langlinks-label"><g:fieldValue bean="${bioWikiInstance}" field="langlinks"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.nome}">
            <li class="fieldcontain">
                <span id="nome-label" class="property-label"><g:message code="bioWiki.nome.label" default="Nome" /></span>
                
                <span class="property-value" aria-labelledby="nome-label"><g:fieldValue bean="${bioWikiInstance}" field="nome"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.cognome}">
            <li class="fieldcontain">
                <span id="cognome-label" class="property-label"><g:message code="bioWiki.cognome.label" default="Cognome" /></span>
                
                <span class="property-value" aria-labelledby="cognome-label"><g:fieldValue bean="${bioWikiInstance}" field="cognome"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.forzaOrdinamento}">
            <li class="fieldcontain">
                <span id="forzaOrdinamento-label" class="property-label"><g:message code="bioWiki.forzaOrdinamento.label" default="Forza Ordinamento" /></span>
                
                <span class="property-value" aria-labelledby="forzaOrdinamento-label"><g:fieldValue bean="${bioWikiInstance}" field="forzaOrdinamento"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.sesso}">
            <li class="fieldcontain">
                <span id="sesso-label" class="property-label"><g:message code="bioWiki.sesso.label" default="Sesso" /></span>
                
                <span class="property-value" aria-labelledby="sesso-label"><g:fieldValue bean="${bioWikiInstance}" field="sesso"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.attivita}">
            <li class="fieldcontain">
                <span id="attivita-label" class="property-label"><g:message code="bioWiki.attivita.label" default="Attivita" /></span>
                
                <span class="property-value" aria-labelledby="attivita-label"><g:fieldValue bean="${bioWikiInstance}" field="attivita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.attivita2}">
            <li class="fieldcontain">
                <span id="attivita2-label" class="property-label"><g:message code="bioWiki.attivita2.label" default="Attivita2" /></span>
                
                <span class="property-value" aria-labelledby="attivita2-label"><g:fieldValue bean="${bioWikiInstance}" field="attivita2"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.attivita3}">
            <li class="fieldcontain">
                <span id="attivita3-label" class="property-label"><g:message code="bioWiki.attivita3.label" default="Attivita3" /></span>
                
                <span class="property-value" aria-labelledby="attivita3-label"><g:fieldValue bean="${bioWikiInstance}" field="attivita3"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.nazionalita}">
            <li class="fieldcontain">
                <span id="nazionalita-label" class="property-label"><g:message code="bioWiki.nazionalita.label" default="Nazionalita" /></span>
                
                <span class="property-value" aria-labelledby="nazionalita-label"><g:fieldValue bean="${bioWikiInstance}" field="nazionalita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.titolo}">
            <li class="fieldcontain">
                <span id="titolo-label" class="property-label"><g:message code="bioWiki.titolo.label" default="Titolo" /></span>
                
                <span class="property-value" aria-labelledby="titolo-label"><g:fieldValue bean="${bioWikiInstance}" field="titolo"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.postCognome}">
            <li class="fieldcontain">
                <span id="postCognome-label" class="property-label"><g:message code="bioWiki.postCognome.label" default="Post Cognome" /></span>
                
                <span class="property-value" aria-labelledby="postCognome-label"><g:fieldValue bean="${bioWikiInstance}" field="postCognome"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.postCognomeVirgola}">
            <li class="fieldcontain">
                <span id="postCognomeVirgola-label" class="property-label"><g:message code="bioWiki.postCognomeVirgola.label" default="Post Cognome Virgola" /></span>
                
                <span class="property-value" aria-labelledby="postCognomeVirgola-label"><g:fieldValue bean="${bioWikiInstance}" field="postCognomeVirgola"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.preData}">
            <li class="fieldcontain">
                <span id="preData-label" class="property-label"><g:message code="bioWiki.preData.label" default="Pre Data" /></span>
                
                <span class="property-value" aria-labelledby="preData-label"><g:fieldValue bean="${bioWikiInstance}" field="preData"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.luogoNascita}">
            <li class="fieldcontain">
                <span id="luogoNascita-label" class="property-label"><g:message code="bioWiki.luogoNascita.label" default="Luogo Nascita" /></span>
                
                <span class="property-value" aria-labelledby="luogoNascita-label"><g:fieldValue bean="${bioWikiInstance}" field="luogoNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.luogoNascitaLink}">
            <li class="fieldcontain">
                <span id="luogoNascitaLink-label" class="property-label"><g:message code="bioWiki.luogoNascitaLink.label" default="Luogo Nascita Link" /></span>
                
                <span class="property-value" aria-labelledby="luogoNascitaLink-label"><g:fieldValue bean="${bioWikiInstance}" field="luogoNascitaLink"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.luogoNascitaAlt}">
            <li class="fieldcontain">
                <span id="luogoNascitaAlt-label" class="property-label"><g:message code="bioWiki.luogoNascitaAlt.label" default="Luogo Nascita Alt" /></span>
                
                <span class="property-value" aria-labelledby="luogoNascitaAlt-label"><g:fieldValue bean="${bioWikiInstance}" field="luogoNascitaAlt"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.giornoMeseNascita}">
            <li class="fieldcontain">
                <span id="giornoMeseNascita-label" class="property-label"><g:message code="bioWiki.giornoMeseNascita.label" default="Giorno Mese Nascita" /></span>
                
                <span class="property-value" aria-labelledby="giornoMeseNascita-label"><g:fieldValue bean="${bioWikiInstance}" field="giornoMeseNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.annoNascita}">
            <li class="fieldcontain">
                <span id="annoNascita-label" class="property-label"><g:message code="bioWiki.annoNascita.label" default="Anno Nascita" /></span>
                
                <span class="property-value" aria-labelledby="annoNascita-label"><g:fieldValue bean="${bioWikiInstance}" field="annoNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.noteNascita}">
            <li class="fieldcontain">
                <span id="noteNascita-label" class="property-label"><g:message code="bioWiki.noteNascita.label" default="Note Nascita" /></span>
                
                <span class="property-value" aria-labelledby="noteNascita-label"><g:fieldValue bean="${bioWikiInstance}" field="noteNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.luogoMorte}">
            <li class="fieldcontain">
                <span id="luogoMorte-label" class="property-label"><g:message code="bioWiki.luogoMorte.label" default="Luogo Morte" /></span>
                
                <span class="property-value" aria-labelledby="luogoMorte-label"><g:fieldValue bean="${bioWikiInstance}" field="luogoMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.luogoMorteLink}">
            <li class="fieldcontain">
                <span id="luogoMorteLink-label" class="property-label"><g:message code="bioWiki.luogoMorteLink.label" default="Luogo Morte Link" /></span>
                
                <span class="property-value" aria-labelledby="luogoMorteLink-label"><g:fieldValue bean="${bioWikiInstance}" field="luogoMorteLink"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.luogoMorteAlt}">
            <li class="fieldcontain">
                <span id="luogoMorteAlt-label" class="property-label"><g:message code="bioWiki.luogoMorteAlt.label" default="Luogo Morte Alt" /></span>
                
                <span class="property-value" aria-labelledby="luogoMorteAlt-label"><g:fieldValue bean="${bioWikiInstance}" field="luogoMorteAlt"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.giornoMeseMorte}">
            <li class="fieldcontain">
                <span id="giornoMeseMorte-label" class="property-label"><g:message code="bioWiki.giornoMeseMorte.label" default="Giorno Mese Morte" /></span>
                
                <span class="property-value" aria-labelledby="giornoMeseMorte-label"><g:fieldValue bean="${bioWikiInstance}" field="giornoMeseMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.annoMorte}">
            <li class="fieldcontain">
                <span id="annoMorte-label" class="property-label"><g:message code="bioWiki.annoMorte.label" default="Anno Morte" /></span>
                
                <span class="property-value" aria-labelledby="annoMorte-label"><g:fieldValue bean="${bioWikiInstance}" field="annoMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.noteMorte}">
            <li class="fieldcontain">
                <span id="noteMorte-label" class="property-label"><g:message code="bioWiki.noteMorte.label" default="Note Morte" /></span>
                
                <span class="property-value" aria-labelledby="noteMorte-label"><g:fieldValue bean="${bioWikiInstance}" field="noteMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.preAttivita}">
            <li class="fieldcontain">
                <span id="preAttivita-label" class="property-label"><g:message code="bioWiki.preAttivita.label" default="Pre Attivita" /></span>
                
                <span class="property-value" aria-labelledby="preAttivita-label"><g:fieldValue bean="${bioWikiInstance}" field="preAttivita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.epoca}">
            <li class="fieldcontain">
                <span id="epoca-label" class="property-label"><g:message code="bioWiki.epoca.label" default="Epoca" /></span>
                
                <span class="property-value" aria-labelledby="epoca-label"><g:fieldValue bean="${bioWikiInstance}" field="epoca"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.epoca2}">
            <li class="fieldcontain">
                <span id="epoca2-label" class="property-label"><g:message code="bioWiki.epoca2.label" default="Epoca2" /></span>
                
                <span class="property-value" aria-labelledby="epoca2-label"><g:fieldValue bean="${bioWikiInstance}" field="epoca2"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.cittadinanza}">
            <li class="fieldcontain">
                <span id="cittadinanza-label" class="property-label"><g:message code="bioWiki.cittadinanza.label" default="Cittadinanza" /></span>
                
                <span class="property-value" aria-labelledby="cittadinanza-label"><g:fieldValue bean="${bioWikiInstance}" field="cittadinanza"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.attivitaAltre}">
            <li class="fieldcontain">
                <span id="attivitaAltre-label" class="property-label"><g:message code="bioWiki.attivitaAltre.label" default="Attivita Altre" /></span>
                
                <span class="property-value" aria-labelledby="attivitaAltre-label"><g:fieldValue bean="${bioWikiInstance}" field="attivitaAltre"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.nazionalitaNaturalizzato}">
            <li class="fieldcontain">
                <span id="nazionalitaNaturalizzato-label" class="property-label"><g:message code="bioWiki.nazionalitaNaturalizzato.label" default="Nazionalita Naturalizzato" /></span>
                
                <span class="property-value" aria-labelledby="nazionalitaNaturalizzato-label"><g:fieldValue bean="${bioWikiInstance}" field="nazionalitaNaturalizzato"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.postNazionalita}">
            <li class="fieldcontain">
                <span id="postNazionalita-label" class="property-label"><g:message code="bioWiki.postNazionalita.label" default="Post Nazionalita" /></span>
                
                <span class="property-value" aria-labelledby="postNazionalita-label"><g:fieldValue bean="${bioWikiInstance}" field="postNazionalita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.categorie}">
            <li class="fieldcontain">
                <span id="categorie-label" class="property-label"><g:message code="bioWiki.categorie.label" default="Categorie" /></span>
                
                <span class="property-value" aria-labelledby="categorie-label"><g:fieldValue bean="${bioWikiInstance}" field="categorie"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.fineIncipit}">
            <li class="fieldcontain">
                <span id="fineIncipit-label" class="property-label"><g:message code="bioWiki.fineIncipit.label" default="Fine Incipit" /></span>
                
                <span class="property-value" aria-labelledby="fineIncipit-label"><g:fieldValue bean="${bioWikiInstance}" field="fineIncipit"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.punto}">
            <li class="fieldcontain">
                <span id="punto-label" class="property-label"><g:message code="bioWiki.punto.label" default="Punto" /></span>
                
                <span class="property-value" aria-labelledby="punto-label"><g:fieldValue bean="${bioWikiInstance}" field="punto"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.immagine}">
            <li class="fieldcontain">
                <span id="immagine-label" class="property-label"><g:message code="bioWiki.immagine.label" default="Immagine" /></span>
                
                <span class="property-value" aria-labelledby="immagine-label"><g:fieldValue bean="${bioWikiInstance}" field="immagine"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.didascalia}">
            <li class="fieldcontain">
                <span id="didascalia-label" class="property-label"><g:message code="bioWiki.didascalia.label" default="Didascalia" /></span>
                
                <span class="property-value" aria-labelledby="didascalia-label"><g:fieldValue bean="${bioWikiInstance}" field="didascalia"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.didascalia2}">
            <li class="fieldcontain">
                <span id="didascalia2-label" class="property-label"><g:message code="bioWiki.didascalia2.label" default="Didascalia2" /></span>
                
                <span class="property-value" aria-labelledby="didascalia2-label"><g:fieldValue bean="${bioWikiInstance}" field="didascalia2"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.dimImmagine}">
            <li class="fieldcontain">
                <span id="dimImmagine-label" class="property-label"><g:message code="bioWiki.dimImmagine.label" default="Dim Immagine" /></span>
                
                <span class="property-value" aria-labelledby="dimImmagine-label"><g:fieldValue bean="${bioWikiInstance}" field="dimImmagine"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.ultimaLettura}">
            <li class="fieldcontain">
                <span id="ultimaLettura-label" class="property-label"><g:message code="bioWiki.ultimaLettura.label" default="Ultima Lettura" /></span>
                
                <span class="property-value" aria-labelledby="ultimaLettura-label"><g:fieldValue bean="${bioWikiInstance}" field="ultimaLettura"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.ultimaScrittura}">
            <li class="fieldcontain">
                <span id="ultimaScrittura-label" class="property-label"><g:message code="bioWiki.ultimaScrittura.label" default="Ultima Scrittura" /></span>
                
                <span class="property-value" aria-labelledby="ultimaScrittura-label"><g:fieldValue bean="${bioWikiInstance}" field="ultimaScrittura"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.extra}">
            <li class="fieldcontain">
                <span id="extra-label" class="property-label"><g:message code="bioWiki.extra.label" default="Extra" /></span>
                
                <span class="property-value" aria-labelledby="extra-label"><g:formatBoolean boolean="${bioWikiInstance?.extra}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.extraLista}">
            <li class="fieldcontain">
                <span id="extraLista-label" class="property-label"><g:message code="bioWiki.extraLista.label" default="Extra Lista" /></span>
                
                <span class="property-value" aria-labelledby="extraLista-label"><g:fieldValue bean="${bioWikiInstance}" field="extraLista"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.graffe}">
            <li class="fieldcontain">
                <span id="graffe-label" class="property-label"><g:message code="bioWiki.graffe.label" default="Graffe" /></span>
                
                <span class="property-value" aria-labelledby="graffe-label"><g:formatBoolean boolean="${bioWikiInstance?.graffe}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.note}">
            <li class="fieldcontain">
                <span id="note-label" class="property-label"><g:message code="bioWiki.note.label" default="Note" /></span>
                
                <span class="property-value" aria-labelledby="note-label"><g:formatBoolean boolean="${bioWikiInstance?.note}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.nascosto}">
            <li class="fieldcontain">
                <span id="nascosto-label" class="property-label"><g:message code="bioWiki.nascosto.label" default="Nascosto" /></span>
                
                <span class="property-value" aria-labelledby="nascosto-label"><g:formatBoolean boolean="${bioWikiInstance?.nascosto}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.errori}">
            <li class="fieldcontain">
                <span id="errori-label" class="property-label"><g:message code="bioWiki.errori.label" default="Errori" /></span>
                
                <span class="property-value" aria-labelledby="errori-label"><g:fieldValue bean="${bioWikiInstance}" field="errori"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.sizeBio}">
            <li class="fieldcontain">
                <span id="sizeBio-label" class="property-label"><g:message code="bioWiki.sizeBio.label" default="Size Bio" /></span>
                
                <span class="property-value" aria-labelledby="sizeBio-label"><g:fieldValue bean="${bioWikiInstance}" field="sizeBio"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.modificaWiki}">
            <li class="fieldcontain">
                <span id="modificaWiki-label" class="property-label"><g:message code="bioWiki.modificaWiki.label" default="Modifica Wiki" /></span>
                
                <span class="property-value" aria-labelledby="modificaWiki-label"><g:formatDate date="${bioWikiInstance?.modificaWiki}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.letturaWiki}">
            <li class="fieldcontain">
                <span id="letturaWiki-label" class="property-label"><g:message code="bioWiki.letturaWiki.label" default="Lettura Wiki" /></span>
                
                <span class="property-value" aria-labelledby="letturaWiki-label"><g:formatDate date="${bioWikiInstance?.letturaWiki}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.incompleta}">
            <li class="fieldcontain">
                <span id="incompleta-label" class="property-label"><g:message code="bioWiki.incompleta.label" default="Incompleta" /></span>
                
                <span class="property-value" aria-labelledby="incompleta-label"><g:formatBoolean boolean="${bioWikiInstance?.incompleta}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.allineata}">
            <li class="fieldcontain">
                <span id="allineata-label" class="property-label"><g:message code="bioWiki.allineata.label" default="Allineata" /></span>
                
                <span class="property-value" aria-labelledby="allineata-label"><g:formatBoolean boolean="${bioWikiInstance?.allineata}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.controllato}">
            <li class="fieldcontain">
                <span id="controllato-label" class="property-label"><g:message code="bioWiki.controllato.label" default="Controllato" /></span>
                
                <span class="property-value" aria-labelledby="controllato-label"><g:formatBoolean boolean="${bioWikiInstance?.controllato}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioWikiInstance?.elaborata}">
            <li class="fieldcontain">
                <span id="elaborata-label" class="property-label"><g:message code="bioWiki.elaborata.label" default="Elaborata" /></span>
                
                <span class="property-value" aria-labelledby="elaborata-label"><g:formatBoolean boolean="${bioWikiInstance?.elaborata}" /></span>
                
            </li>
        </g:if>
        
    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${bioWikiInstance?.id}" />
            <g:link class="edit" action="edit" id="${bioWikiInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
