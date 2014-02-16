













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
        <g:if test="${menuExtra}">
            <algos:menuExtra menuExtra="${menuExtra}"> </algos:menuExtra>
        </g:if>
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
        
        <g:if test="${bioGrailsInstance?.title}">
            <li class="fieldcontain">
                <span id="title-label" class="property-label"><g:message code="bioGrails.title.label" default="Title" /></span>
                
                <span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${bioGrailsInstance}" field="title"/></span>
                
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
        
        <g:if test="${bioGrailsInstance?.forzaOrdinamento}">
            <li class="fieldcontain">
                <span id="forzaOrdinamento-label" class="property-label"><g:message code="bioGrails.forzaOrdinamento.label" default="Forza Ordinamento" /></span>
                
                <span class="property-value" aria-labelledby="forzaOrdinamento-label"><g:fieldValue bean="${bioGrailsInstance}" field="forzaOrdinamento"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.sesso}">
            <li class="fieldcontain">
                <span id="sesso-label" class="property-label"><g:message code="bioGrails.sesso.label" default="Sesso" /></span>
                
                <span class="property-value" aria-labelledby="sesso-label"><g:fieldValue bean="${bioGrailsInstance}" field="sesso"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.attivita}">
            <li class="fieldcontain">
                <span id="attivita-label" class="property-label"><g:message code="bioGrails.attivita.label" default="Attivita" /></span>
                
                <span class="property-value" aria-labelledby="attivita-label"><g:fieldValue bean="${bioGrailsInstance}" field="attivita"/></span>
                
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
        
        <g:if test="${bioGrailsInstance?.nazionalita}">
            <li class="fieldcontain">
                <span id="nazionalita-label" class="property-label"><g:message code="bioGrails.nazionalita.label" default="Nazionalita" /></span>
                
                <span class="property-value" aria-labelledby="nazionalita-label"><g:fieldValue bean="${bioGrailsInstance}" field="nazionalita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.localitaNato}">
            <li class="fieldcontain">
                <span id="localitaNato-label" class="property-label"><g:message code="bioGrails.localitaNato.label" default="Localita Nato" /></span>
                
                <span class="property-value" aria-labelledby="localitaNato-label"><g:fieldValue bean="${bioGrailsInstance}" field="localitaNato"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.localitaMorto}">
            <li class="fieldcontain">
                <span id="localitaMorto-label" class="property-label"><g:message code="bioGrails.localitaMorto.label" default="Localita Morto" /></span>
                
                <span class="property-value" aria-labelledby="localitaMorto-label"><g:fieldValue bean="${bioGrailsInstance}" field="localitaMorto"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.didascaliaBase}">
            <li class="fieldcontain">
                <span id="didascaliaBase-label" class="property-label"><g:message code="bioGrails.didascaliaBase.label" default="Didascalia Base" /></span>
                
                <span class="property-value" aria-labelledby="didascaliaBase-label"><g:fieldValue bean="${bioGrailsInstance}" field="didascaliaBase"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.didascaliaGiornoNato}">
            <li class="fieldcontain">
                <span id="didascaliaGiornoNato-label" class="property-label"><g:message code="bioGrails.didascaliaGiornoNato.label" default="Didascalia Giorno Nato" /></span>
                
                <span class="property-value" aria-labelledby="didascaliaGiornoNato-label"><g:fieldValue bean="${bioGrailsInstance}" field="didascaliaGiornoNato"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.didascaliaGiornoMorto}">
            <li class="fieldcontain">
                <span id="didascaliaGiornoMorto-label" class="property-label"><g:message code="bioGrails.didascaliaGiornoMorto.label" default="Didascalia Giorno Morto" /></span>
                
                <span class="property-value" aria-labelledby="didascaliaGiornoMorto-label"><g:fieldValue bean="${bioGrailsInstance}" field="didascaliaGiornoMorto"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.didascaliaAnnoNato}">
            <li class="fieldcontain">
                <span id="didascaliaAnnoNato-label" class="property-label"><g:message code="bioGrails.didascaliaAnnoNato.label" default="Didascalia Anno Nato" /></span>
                
                <span class="property-value" aria-labelledby="didascaliaAnnoNato-label"><g:fieldValue bean="${bioGrailsInstance}" field="didascaliaAnnoNato"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.didascaliaAnnoMorto}">
            <li class="fieldcontain">
                <span id="didascaliaAnnoMorto-label" class="property-label"><g:message code="bioGrails.didascaliaAnnoMorto.label" default="Didascalia Anno Morto" /></span>
                
                <span class="property-value" aria-labelledby="didascaliaAnnoMorto-label"><g:fieldValue bean="${bioGrailsInstance}" field="didascaliaAnnoMorto"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioGrailsInstance?.didascaliaListe}">
            <li class="fieldcontain">
                <span id="didascaliaListe-label" class="property-label"><g:message code="bioGrails.didascaliaListe.label" default="Didascalia Liste" /></span>
                
                <span class="property-value" aria-labelledby="didascaliaListe-label"><g:fieldValue bean="${bioGrailsInstance}" field="didascaliaListe"/></span>
                
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
