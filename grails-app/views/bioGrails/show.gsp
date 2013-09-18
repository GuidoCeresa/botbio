













<%@ page import="it.algos.botbio.BioGrails" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'bioGrails.label', default: 'BioGrails')}" />
    <title><g:message code="bioGrails.show.label" args="[entityName]" default="Mostra"/></title>
</head>
<body>
<a href="#show-bioGrails" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="bioGrails.list.label" args="[entityName]" default="Elenco"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="bioGrails.new.label" args="[entityName]" default="Nuovo"/></g:link></li>
    </ul>
</div>
<div id="show-bioGrails" class="content scaffold-show" role="main">
    <h1><g:message code="bioGrails.show.label" args="[entityName]" default="Mostra"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list bioGrails">
        
        <g:if test="${bioGrailsInstance?.pageid}">
            <li class="fieldcontain">
                <span id="pageid-label" class="property-label"><g:message code="bioGrails.pageid.label" default="Pageid" /></span>
                
                <span class="property-value" aria-labelledby="pageid-label"><g:fieldValue bean="${bioGrailsInstance}" field="pageid"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.titolo}">
            <li class="fieldcontain">
                <span id="titolo-label" class="property-label"><g:message code="bioGrails.titolo.label" default="Titolo" /></span>
                
                <span class="property-value" aria-labelledby="titolo-label"><g:fieldValue bean="${bioGrailsInstance}" field="titolo"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.nome}">
            <li class="fieldcontain">
                <span id="nome-label" class="property-label"><g:message code="bioGrails.nome.label" default="Nome" /></span>
                
                <span class="property-value" aria-labelledby="nome-label"><g:fieldValue bean="${bioGrailsInstance}" field="nome"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.cognome}">
            <li class="fieldcontain">
                <span id="cognome-label" class="property-label"><g:message code="bioGrails.cognome.label" default="Cognome" /></span>
                
                <span class="property-value" aria-labelledby="cognome-label"><g:fieldValue bean="${bioGrailsInstance}" field="cognome"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.postCognome}">
            <li class="fieldcontain">
                <span id="postCognome-label" class="property-label"><g:message code="bioGrails.postCognome.label" default="Post Cognome" /></span>
                
                <span class="property-value" aria-labelledby="postCognome-label"><g:fieldValue bean="${bioGrailsInstance}" field="postCognome"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.postCognomeVirgola}">
            <li class="fieldcontain">
                <span id="postCognomeVirgola-label" class="property-label"><g:message code="bioGrails.postCognomeVirgola.label" default="Post Cognome Virgola" /></span>
                
                <span class="property-value" aria-labelledby="postCognomeVirgola-label"><g:fieldValue bean="${bioGrailsInstance}" field="postCognomeVirgola"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.forzaOrdinamento}">
            <li class="fieldcontain">
                <span id="forzaOrdinamento-label" class="property-label"><g:message code="bioGrails.forzaOrdinamento.label" default="Forza Ordinamento" /></span>
                
                <span class="property-value" aria-labelledby="forzaOrdinamento-label"><g:fieldValue bean="${bioGrailsInstance}" field="forzaOrdinamento"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.preData}">
            <li class="fieldcontain">
                <span id="preData-label" class="property-label"><g:message code="bioGrails.preData.label" default="Pre Data" /></span>
                
                <span class="property-value" aria-labelledby="preData-label"><g:fieldValue bean="${bioGrailsInstance}" field="preData"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.sesso}">
            <li class="fieldcontain">
                <span id="sesso-label" class="property-label"><g:message code="bioGrails.sesso.label" default="Sesso" /></span>
                
                <span class="property-value" aria-labelledby="sesso-label"><g:fieldValue bean="${bioGrailsInstance}" field="sesso"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.luogoNascita}">
            <li class="fieldcontain">
                <span id="luogoNascita-label" class="property-label"><g:message code="bioGrails.luogoNascita.label" default="Luogo Nascita" /></span>
                
                <span class="property-value" aria-labelledby="luogoNascita-label"><g:fieldValue bean="${bioGrailsInstance}" field="luogoNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.luogoNascitaLink}">
            <li class="fieldcontain">
                <span id="luogoNascitaLink-label" class="property-label"><g:message code="bioGrails.luogoNascitaLink.label" default="Luogo Nascita Link" /></span>
                
                <span class="property-value" aria-labelledby="luogoNascitaLink-label"><g:fieldValue bean="${bioGrailsInstance}" field="luogoNascitaLink"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.luogoNascitaAlt}">
            <li class="fieldcontain">
                <span id="luogoNascitaAlt-label" class="property-label"><g:message code="bioGrails.luogoNascitaAlt.label" default="Luogo Nascita Alt" /></span>
                
                <span class="property-value" aria-labelledby="luogoNascitaAlt-label"><g:fieldValue bean="${bioGrailsInstance}" field="luogoNascitaAlt"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.giornoMeseNascita}">
            <li class="fieldcontain">
                <span id="giornoMeseNascita-label" class="property-label"><g:message code="bioGrails.giornoMeseNascita.label" default="Giorno Mese Nascita" /></span>
                
                <span class="property-value" aria-labelledby="giornoMeseNascita-label"><g:fieldValue bean="${bioGrailsInstance}" field="giornoMeseNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.annoNascita}">
            <li class="fieldcontain">
                <span id="annoNascita-label" class="property-label"><g:message code="bioGrails.annoNascita.label" default="Anno Nascita" /></span>
                
                <span class="property-value" aria-labelledby="annoNascita-label"><g:fieldValue bean="${bioGrailsInstance}" field="annoNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.noteNascita}">
            <li class="fieldcontain">
                <span id="noteNascita-label" class="property-label"><g:message code="bioGrails.noteNascita.label" default="Note Nascita" /></span>
                
                <span class="property-value" aria-labelledby="noteNascita-label"><g:fieldValue bean="${bioGrailsInstance}" field="noteNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.luogoMorte}">
            <li class="fieldcontain">
                <span id="luogoMorte-label" class="property-label"><g:message code="bioGrails.luogoMorte.label" default="Luogo Morte" /></span>
                
                <span class="property-value" aria-labelledby="luogoMorte-label"><g:fieldValue bean="${bioGrailsInstance}" field="luogoMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.luogoMorteLink}">
            <li class="fieldcontain">
                <span id="luogoMorteLink-label" class="property-label"><g:message code="bioGrails.luogoMorteLink.label" default="Luogo Morte Link" /></span>
                
                <span class="property-value" aria-labelledby="luogoMorteLink-label"><g:fieldValue bean="${bioGrailsInstance}" field="luogoMorteLink"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.luogoMorteAlt}">
            <li class="fieldcontain">
                <span id="luogoMorteAlt-label" class="property-label"><g:message code="bioGrails.luogoMorteAlt.label" default="Luogo Morte Alt" /></span>
                
                <span class="property-value" aria-labelledby="luogoMorteAlt-label"><g:fieldValue bean="${bioGrailsInstance}" field="luogoMorteAlt"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.giornoMeseMorte}">
            <li class="fieldcontain">
                <span id="giornoMeseMorte-label" class="property-label"><g:message code="bioGrails.giornoMeseMorte.label" default="Giorno Mese Morte" /></span>
                
                <span class="property-value" aria-labelledby="giornoMeseMorte-label"><g:fieldValue bean="${bioGrailsInstance}" field="giornoMeseMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.annoMorte}">
            <li class="fieldcontain">
                <span id="annoMorte-label" class="property-label"><g:message code="bioGrails.annoMorte.label" default="Anno Morte" /></span>
                
                <span class="property-value" aria-labelledby="annoMorte-label"><g:fieldValue bean="${bioGrailsInstance}" field="annoMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.noteMorte}">
            <li class="fieldcontain">
                <span id="noteMorte-label" class="property-label"><g:message code="bioGrails.noteMorte.label" default="Note Morte" /></span>
                
                <span class="property-value" aria-labelledby="noteMorte-label"><g:fieldValue bean="${bioGrailsInstance}" field="noteMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.preAttivita}">
            <li class="fieldcontain">
                <span id="preAttivita-label" class="property-label"><g:message code="bioGrails.preAttivita.label" default="Pre Attivita" /></span>
                
                <span class="property-value" aria-labelledby="preAttivita-label"><g:fieldValue bean="${bioGrailsInstance}" field="preAttivita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivita}">
            <li class="fieldcontain">
                <span id="attivita-label" class="property-label"><g:message code="bioGrails.attivita.label" default="Attivita" /></span>
                
                <span class="property-value" aria-labelledby="attivita-label"><g:fieldValue bean="${bioGrailsInstance}" field="attivita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.epoca}">
            <li class="fieldcontain">
                <span id="epoca-label" class="property-label"><g:message code="bioGrails.epoca.label" default="Epoca" /></span>
                
                <span class="property-value" aria-labelledby="epoca-label"><g:fieldValue bean="${bioGrailsInstance}" field="epoca"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.epoca2}">
            <li class="fieldcontain">
                <span id="epoca2-label" class="property-label"><g:message code="bioGrails.epoca2.label" default="Epoca2" /></span>
                
                <span class="property-value" aria-labelledby="epoca2-label"><g:fieldValue bean="${bioGrailsInstance}" field="epoca2"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.cittadinanza}">
            <li class="fieldcontain">
                <span id="cittadinanza-label" class="property-label"><g:message code="bioGrails.cittadinanza.label" default="Cittadinanza" /></span>
                
                <span class="property-value" aria-labelledby="cittadinanza-label"><g:fieldValue bean="${bioGrailsInstance}" field="cittadinanza"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivita2}">
            <li class="fieldcontain">
                <span id="attivita2-label" class="property-label"><g:message code="bioGrails.attivita2.label" default="Attivita2" /></span>
                
                <span class="property-value" aria-labelledby="attivita2-label"><g:fieldValue bean="${bioGrailsInstance}" field="attivita2"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivita3}">
            <li class="fieldcontain">
                <span id="attivita3-label" class="property-label"><g:message code="bioGrails.attivita3.label" default="Attivita3" /></span>
                
                <span class="property-value" aria-labelledby="attivita3-label"><g:fieldValue bean="${bioGrailsInstance}" field="attivita3"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivitaAltre}">
            <li class="fieldcontain">
                <span id="attivitaAltre-label" class="property-label"><g:message code="bioGrails.attivitaAltre.label" default="Attivita Altre" /></span>
                
                <span class="property-value" aria-labelledby="attivitaAltre-label"><g:fieldValue bean="${bioGrailsInstance}" field="attivitaAltre"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.nazionalita}">
            <li class="fieldcontain">
                <span id="nazionalita-label" class="property-label"><g:message code="bioGrails.nazionalita.label" default="Nazionalita" /></span>
                
                <span class="property-value" aria-labelledby="nazionalita-label"><g:fieldValue bean="${bioGrailsInstance}" field="nazionalita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.nazionalitaNaturalizzato}">
            <li class="fieldcontain">
                <span id="nazionalitaNaturalizzato-label" class="property-label"><g:message code="bioGrails.nazionalitaNaturalizzato.label" default="Nazionalita Naturalizzato" /></span>
                
                <span class="property-value" aria-labelledby="nazionalitaNaturalizzato-label"><g:fieldValue bean="${bioGrailsInstance}" field="nazionalitaNaturalizzato"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.postNazionalita}">
            <li class="fieldcontain">
                <span id="postNazionalita-label" class="property-label"><g:message code="bioGrails.postNazionalita.label" default="Post Nazionalita" /></span>
                
                <span class="property-value" aria-labelledby="postNazionalita-label"><g:fieldValue bean="${bioGrailsInstance}" field="postNazionalita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.categorie}">
            <li class="fieldcontain">
                <span id="categorie-label" class="property-label"><g:message code="bioGrails.categorie.label" default="Categorie" /></span>
                
                <span class="property-value" aria-labelledby="categorie-label"><g:fieldValue bean="${bioGrailsInstance}" field="categorie"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.fineIncipit}">
            <li class="fieldcontain">
                <span id="fineIncipit-label" class="property-label"><g:message code="bioGrails.fineIncipit.label" default="Fine Incipit" /></span>
                
                <span class="property-value" aria-labelledby="fineIncipit-label"><g:fieldValue bean="${bioGrailsInstance}" field="fineIncipit"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.punto}">
            <li class="fieldcontain">
                <span id="punto-label" class="property-label"><g:message code="bioGrails.punto.label" default="Punto" /></span>
                
                <span class="property-value" aria-labelledby="punto-label"><g:fieldValue bean="${bioGrailsInstance}" field="punto"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.immagine}">
            <li class="fieldcontain">
                <span id="immagine-label" class="property-label"><g:message code="bioGrails.immagine.label" default="Immagine" /></span>
                
                <span class="property-value" aria-labelledby="immagine-label"><g:fieldValue bean="${bioGrailsInstance}" field="immagine"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.didascalia}">
            <li class="fieldcontain">
                <span id="didascalia-label" class="property-label"><g:message code="bioGrails.didascalia.label" default="Didascalia" /></span>
                
                <span class="property-value" aria-labelledby="didascalia-label"><g:fieldValue bean="${bioGrailsInstance}" field="didascalia"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.didascalia2}">
            <li class="fieldcontain">
                <span id="didascalia2-label" class="property-label"><g:message code="bioGrails.didascalia2.label" default="Didascalia2" /></span>
                
                <span class="property-value" aria-labelledby="didascalia2-label"><g:fieldValue bean="${bioGrailsInstance}" field="didascalia2"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.dimImmagine}">
            <li class="fieldcontain">
                <span id="dimImmagine-label" class="property-label"><g:message code="bioGrails.dimImmagine.label" default="Dim Immagine" /></span>
                
                <span class="property-value" aria-labelledby="dimImmagine-label"><g:fieldValue bean="${bioGrailsInstance}" field="dimImmagine"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.giornoMeseNascitaLink}">
            <li class="fieldcontain">
                <span id="giornoMeseNascitaLink-label" class="property-label"><g:message code="bioGrails.giornoMeseNascitaLink.label" default="Giorno Mese Nascita Link" /></span>
                
                <span class="property-value" aria-labelledby="giornoMeseNascitaLink-label"><g:link controller="giorno" action="show" id="${bioGrailsInstance?.giornoMeseNascitaLink?.id}">${bioGrailsInstance?.giornoMeseNascitaLink?.encodeAsHTML()}</g:link></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.giornoMeseMorteLink}">
            <li class="fieldcontain">
                <span id="giornoMeseMorteLink-label" class="property-label"><g:message code="bioGrails.giornoMeseMorteLink.label" default="Giorno Mese Morte Link" /></span>
                
                <span class="property-value" aria-labelledby="giornoMeseMorteLink-label"><g:link controller="giorno" action="show" id="${bioGrailsInstance?.giornoMeseMorteLink?.id}">${bioGrailsInstance?.giornoMeseMorteLink?.encodeAsHTML()}</g:link></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.annoNascitaLink}">
            <li class="fieldcontain">
                <span id="annoNascitaLink-label" class="property-label"><g:message code="bioGrails.annoNascitaLink.label" default="Anno Nascita Link" /></span>
                
                <span class="property-value" aria-labelledby="annoNascitaLink-label"><g:link controller="anno" action="show" id="${bioGrailsInstance?.annoNascitaLink?.id}">${bioGrailsInstance?.annoNascitaLink?.encodeAsHTML()}</g:link></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.annoMorteLink}">
            <li class="fieldcontain">
                <span id="annoMorteLink-label" class="property-label"><g:message code="bioGrails.annoMorteLink.label" default="Anno Morte Link" /></span>
                
                <span class="property-value" aria-labelledby="annoMorteLink-label"><g:link controller="anno" action="show" id="${bioGrailsInstance?.annoMorteLink?.id}">${bioGrailsInstance?.annoMorteLink?.encodeAsHTML()}</g:link></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivitaLink}">
            <li class="fieldcontain">
                <span id="attivitaLink-label" class="property-label"><g:message code="bioGrails.attivitaLink.label" default="Attivita Link" /></span>
                
                <span class="property-value" aria-labelledby="attivitaLink-label"><g:link controller="attivita" action="show" id="${bioGrailsInstance?.attivitaLink?.id}">${bioGrailsInstance?.attivitaLink?.encodeAsHTML()}</g:link></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivita2Link}">
            <li class="fieldcontain">
                <span id="attivita2Link-label" class="property-label"><g:message code="bioGrails.attivita2Link.label" default="Attivita2 Link" /></span>
                
                <span class="property-value" aria-labelledby="attivita2Link-label"><g:link controller="attivita" action="show" id="${bioGrailsInstance?.attivita2Link?.id}">${bioGrailsInstance?.attivita2Link?.encodeAsHTML()}</g:link></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivita3Link}">
            <li class="fieldcontain">
                <span id="attivita3Link-label" class="property-label"><g:message code="bioGrails.attivita3Link.label" default="Attivita3 Link" /></span>
                
                <span class="property-value" aria-labelledby="attivita3Link-label"><g:link controller="attivita" action="show" id="${bioGrailsInstance?.attivita3Link?.id}">${bioGrailsInstance?.attivita3Link?.encodeAsHTML()}</g:link></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.nazionalitaLink}">
            <li class="fieldcontain">
                <span id="nazionalitaLink-label" class="property-label"><g:message code="bioGrails.nazionalitaLink.label" default="Nazionalita Link" /></span>
                
                <span class="property-value" aria-labelledby="nazionalitaLink-label"><g:link controller="nazionalita" action="show" id="${bioGrailsInstance?.nazionalitaLink?.id}">${bioGrailsInstance?.nazionalitaLink?.encodeAsHTML()}</g:link></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.allineata}">
            <li class="fieldcontain">
                <span id="allineata-label" class="property-label"><g:message code="bioGrails.allineata.label" default="Allineata" /></span>
                
                <span class="property-value" aria-labelledby="allineata-label"><g:formatBoolean boolean="${bioGrailsInstance?.allineata}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.annoMorteErrato}">
            <li class="fieldcontain">
                <span id="annoMorteErrato-label" class="property-label"><g:message code="bioGrails.annoMorteErrato.label" default="Anno Morte Errato" /></span>
                
                <span class="property-value" aria-labelledby="annoMorteErrato-label"><g:formatBoolean boolean="${bioGrailsInstance?.annoMorteErrato}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.annoMorteValido}">
            <li class="fieldcontain">
                <span id="annoMorteValido-label" class="property-label"><g:message code="bioGrails.annoMorteValido.label" default="Anno Morte Valido" /></span>
                
                <span class="property-value" aria-labelledby="annoMorteValido-label"><g:formatBoolean boolean="${bioGrailsInstance?.annoMorteValido}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.annoNascitaErrato}">
            <li class="fieldcontain">
                <span id="annoNascitaErrato-label" class="property-label"><g:message code="bioGrails.annoNascitaErrato.label" default="Anno Nascita Errato" /></span>
                
                <span class="property-value" aria-labelledby="annoNascitaErrato-label"><g:formatBoolean boolean="${bioGrailsInstance?.annoNascitaErrato}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.annoNascitaValido}">
            <li class="fieldcontain">
                <span id="annoNascitaValido-label" class="property-label"><g:message code="bioGrails.annoNascitaValido.label" default="Anno Nascita Valido" /></span>
                
                <span class="property-value" aria-labelledby="annoNascitaValido-label"><g:formatBoolean boolean="${bioGrailsInstance?.annoNascitaValido}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivita2Errato}">
            <li class="fieldcontain">
                <span id="attivita2Errato-label" class="property-label"><g:message code="bioGrails.attivita2Errato.label" default="Attivita2 Errato" /></span>
                
                <span class="property-value" aria-labelledby="attivita2Errato-label"><g:formatBoolean boolean="${bioGrailsInstance?.attivita2Errato}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivita2Valida}">
            <li class="fieldcontain">
                <span id="attivita2Valida-label" class="property-label"><g:message code="bioGrails.attivita2Valida.label" default="Attivita2 Valida" /></span>
                
                <span class="property-value" aria-labelledby="attivita2Valida-label"><g:formatBoolean boolean="${bioGrailsInstance?.attivita2Valida}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivita3Errato}">
            <li class="fieldcontain">
                <span id="attivita3Errato-label" class="property-label"><g:message code="bioGrails.attivita3Errato.label" default="Attivita3 Errato" /></span>
                
                <span class="property-value" aria-labelledby="attivita3Errato-label"><g:formatBoolean boolean="${bioGrailsInstance?.attivita3Errato}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivita3Valida}">
            <li class="fieldcontain">
                <span id="attivita3Valida-label" class="property-label"><g:message code="bioGrails.attivita3Valida.label" default="Attivita3 Valida" /></span>
                
                <span class="property-value" aria-labelledby="attivita3Valida-label"><g:formatBoolean boolean="${bioGrailsInstance?.attivita3Valida}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivitaErrato}">
            <li class="fieldcontain">
                <span id="attivitaErrato-label" class="property-label"><g:message code="bioGrails.attivitaErrato.label" default="Attivita Errato" /></span>
                
                <span class="property-value" aria-labelledby="attivitaErrato-label"><g:formatBoolean boolean="${bioGrailsInstance?.attivitaErrato}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivitaValida}">
            <li class="fieldcontain">
                <span id="attivitaValida-label" class="property-label"><g:message code="bioGrails.attivitaValida.label" default="Attivita Valida" /></span>
                
                <span class="property-value" aria-labelledby="attivitaValida-label"><g:formatBoolean boolean="${bioGrailsInstance?.attivitaValida}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.controllato}">
            <li class="fieldcontain">
                <span id="controllato-label" class="property-label"><g:message code="bioGrails.controllato.label" default="Controllato" /></span>
                
                <span class="property-value" aria-labelledby="controllato-label"><g:formatBoolean boolean="${bioGrailsInstance?.controllato}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.meseMorteErrato}">
            <li class="fieldcontain">
                <span id="meseMorteErrato-label" class="property-label"><g:message code="bioGrails.meseMorteErrato.label" default="Mese Morte Errato" /></span>
                
                <span class="property-value" aria-labelledby="meseMorteErrato-label"><g:formatBoolean boolean="${bioGrailsInstance?.meseMorteErrato}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.meseMorteValido}">
            <li class="fieldcontain">
                <span id="meseMorteValido-label" class="property-label"><g:message code="bioGrails.meseMorteValido.label" default="Mese Morte Valido" /></span>
                
                <span class="property-value" aria-labelledby="meseMorteValido-label"><g:formatBoolean boolean="${bioGrailsInstance?.meseMorteValido}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.meseNascitaErrato}">
            <li class="fieldcontain">
                <span id="meseNascitaErrato-label" class="property-label"><g:message code="bioGrails.meseNascitaErrato.label" default="Mese Nascita Errato" /></span>
                
                <span class="property-value" aria-labelledby="meseNascitaErrato-label"><g:formatBoolean boolean="${bioGrailsInstance?.meseNascitaErrato}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.meseNascitaValido}">
            <li class="fieldcontain">
                <span id="meseNascitaValido-label" class="property-label"><g:message code="bioGrails.meseNascitaValido.label" default="Mese Nascita Valido" /></span>
                
                <span class="property-value" aria-labelledby="meseNascitaValido-label"><g:formatBoolean boolean="${bioGrailsInstance?.meseNascitaValido}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.nazionalitaErrato}">
            <li class="fieldcontain">
                <span id="nazionalitaErrato-label" class="property-label"><g:message code="bioGrails.nazionalitaErrato.label" default="Nazionalita Errato" /></span>
                
                <span class="property-value" aria-labelledby="nazionalitaErrato-label"><g:formatBoolean boolean="${bioGrailsInstance?.nazionalitaErrato}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.nazionalitaValida}">
            <li class="fieldcontain">
                <span id="nazionalitaValida-label" class="property-label"><g:message code="bioGrails.nazionalitaValida.label" default="Nazionalita Valida" /></span>
                
                <span class="property-value" aria-labelledby="nazionalitaValida-label"><g:formatBoolean boolean="${bioGrailsInstance?.nazionalitaValida}" /></span>
                
            </li>
        </g:if>
        
    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${bioGrailsInstance?.id}" />
            <g:link class="edit" action="edit" id="${bioGrailsInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
