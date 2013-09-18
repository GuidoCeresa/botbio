













<%@ page import="it.algos.botbio.Bio" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'bio.label', default: 'Bio')}" />
    <title><g:message code="bio.show.label" args="[entityName]" default="Mostra"/></title>
</head>
<body>
<a href="#show-bio" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="bio.list.label" args="[entityName]" default="Elenco"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="bio.new.label" args="[entityName]" default="Nuovo"/></g:link></li>
    </ul>
</div>
<div id="show-bio" class="content scaffold-show" role="main">
    <h1><g:message code="bio.show.label" args="[entityName]" default="Mostra"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list bio">
        
        <g:if test="${bioInstance?.pageid}">
            <li class="fieldcontain">
                <span id="pageid-label" class="property-label"><g:message code="bio.pageid.label" default="Pageid" /></span>
                
                <span class="property-value" aria-labelledby="pageid-label"><g:fieldValue bean="${bioInstance}" field="pageid"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.titolo}">
            <li class="fieldcontain">
                <span id="titolo-label" class="property-label"><g:message code="bio.titolo.label" default="Titolo" /></span>
                
                <span class="property-value" aria-labelledby="titolo-label"><g:fieldValue bean="${bioInstance}" field="titolo"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.nome}">
            <li class="fieldcontain">
                <span id="nome-label" class="property-label"><g:message code="bio.nome.label" default="Nome" /></span>
                
                <span class="property-value" aria-labelledby="nome-label"><g:fieldValue bean="${bioInstance}" field="nome"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.cognome}">
            <li class="fieldcontain">
                <span id="cognome-label" class="property-label"><g:message code="bio.cognome.label" default="Cognome" /></span>
                
                <span class="property-value" aria-labelledby="cognome-label"><g:fieldValue bean="${bioInstance}" field="cognome"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.postCognome}">
            <li class="fieldcontain">
                <span id="postCognome-label" class="property-label"><g:message code="bio.postCognome.label" default="Post Cognome" /></span>
                
                <span class="property-value" aria-labelledby="postCognome-label"><g:fieldValue bean="${bioInstance}" field="postCognome"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.postCognomeVirgola}">
            <li class="fieldcontain">
                <span id="postCognomeVirgola-label" class="property-label"><g:message code="bio.postCognomeVirgola.label" default="Post Cognome Virgola" /></span>
                
                <span class="property-value" aria-labelledby="postCognomeVirgola-label"><g:fieldValue bean="${bioInstance}" field="postCognomeVirgola"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.forzaOrdinamento}">
            <li class="fieldcontain">
                <span id="forzaOrdinamento-label" class="property-label"><g:message code="bio.forzaOrdinamento.label" default="Forza Ordinamento" /></span>
                
                <span class="property-value" aria-labelledby="forzaOrdinamento-label"><g:fieldValue bean="${bioInstance}" field="forzaOrdinamento"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.preData}">
            <li class="fieldcontain">
                <span id="preData-label" class="property-label"><g:message code="bio.preData.label" default="Pre Data" /></span>
                
                <span class="property-value" aria-labelledby="preData-label"><g:fieldValue bean="${bioInstance}" field="preData"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.sesso}">
            <li class="fieldcontain">
                <span id="sesso-label" class="property-label"><g:message code="bio.sesso.label" default="Sesso" /></span>
                
                <span class="property-value" aria-labelledby="sesso-label"><g:fieldValue bean="${bioInstance}" field="sesso"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.luogoNascita}">
            <li class="fieldcontain">
                <span id="luogoNascita-label" class="property-label"><g:message code="bio.luogoNascita.label" default="Luogo Nascita" /></span>
                
                <span class="property-value" aria-labelledby="luogoNascita-label"><g:fieldValue bean="${bioInstance}" field="luogoNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.luogoNascitaLink}">
            <li class="fieldcontain">
                <span id="luogoNascitaLink-label" class="property-label"><g:message code="bio.luogoNascitaLink.label" default="Luogo Nascita Link" /></span>
                
                <span class="property-value" aria-labelledby="luogoNascitaLink-label"><g:fieldValue bean="${bioInstance}" field="luogoNascitaLink"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.luogoNascitaAlt}">
            <li class="fieldcontain">
                <span id="luogoNascitaAlt-label" class="property-label"><g:message code="bio.luogoNascitaAlt.label" default="Luogo Nascita Alt" /></span>
                
                <span class="property-value" aria-labelledby="luogoNascitaAlt-label"><g:fieldValue bean="${bioInstance}" field="luogoNascitaAlt"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.giornoMeseNascita}">
            <li class="fieldcontain">
                <span id="giornoMeseNascita-label" class="property-label"><g:message code="bio.giornoMeseNascita.label" default="Giorno Mese Nascita" /></span>
                
                <span class="property-value" aria-labelledby="giornoMeseNascita-label"><g:fieldValue bean="${bioInstance}" field="giornoMeseNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.annoNascita}">
            <li class="fieldcontain">
                <span id="annoNascita-label" class="property-label"><g:message code="bio.annoNascita.label" default="Anno Nascita" /></span>
                
                <span class="property-value" aria-labelledby="annoNascita-label"><g:fieldValue bean="${bioInstance}" field="annoNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.noteNascita}">
            <li class="fieldcontain">
                <span id="noteNascita-label" class="property-label"><g:message code="bio.noteNascita.label" default="Note Nascita" /></span>
                
                <span class="property-value" aria-labelledby="noteNascita-label"><g:fieldValue bean="${bioInstance}" field="noteNascita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.luogoMorte}">
            <li class="fieldcontain">
                <span id="luogoMorte-label" class="property-label"><g:message code="bio.luogoMorte.label" default="Luogo Morte" /></span>
                
                <span class="property-value" aria-labelledby="luogoMorte-label"><g:fieldValue bean="${bioInstance}" field="luogoMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.luogoMorteLink}">
            <li class="fieldcontain">
                <span id="luogoMorteLink-label" class="property-label"><g:message code="bio.luogoMorteLink.label" default="Luogo Morte Link" /></span>
                
                <span class="property-value" aria-labelledby="luogoMorteLink-label"><g:fieldValue bean="${bioInstance}" field="luogoMorteLink"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.luogoMorteAlt}">
            <li class="fieldcontain">
                <span id="luogoMorteAlt-label" class="property-label"><g:message code="bio.luogoMorteAlt.label" default="Luogo Morte Alt" /></span>
                
                <span class="property-value" aria-labelledby="luogoMorteAlt-label"><g:fieldValue bean="${bioInstance}" field="luogoMorteAlt"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.giornoMeseMorte}">
            <li class="fieldcontain">
                <span id="giornoMeseMorte-label" class="property-label"><g:message code="bio.giornoMeseMorte.label" default="Giorno Mese Morte" /></span>
                
                <span class="property-value" aria-labelledby="giornoMeseMorte-label"><g:fieldValue bean="${bioInstance}" field="giornoMeseMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.annoMorte}">
            <li class="fieldcontain">
                <span id="annoMorte-label" class="property-label"><g:message code="bio.annoMorte.label" default="Anno Morte" /></span>
                
                <span class="property-value" aria-labelledby="annoMorte-label"><g:fieldValue bean="${bioInstance}" field="annoMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.noteMorte}">
            <li class="fieldcontain">
                <span id="noteMorte-label" class="property-label"><g:message code="bio.noteMorte.label" default="Note Morte" /></span>
                
                <span class="property-value" aria-labelledby="noteMorte-label"><g:fieldValue bean="${bioInstance}" field="noteMorte"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.preAttivita}">
            <li class="fieldcontain">
                <span id="preAttivita-label" class="property-label"><g:message code="bio.preAttivita.label" default="Pre Attivita" /></span>
                
                <span class="property-value" aria-labelledby="preAttivita-label"><g:fieldValue bean="${bioInstance}" field="preAttivita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.attivita}">
            <li class="fieldcontain">
                <span id="attivita-label" class="property-label"><g:message code="bio.attivita.label" default="Attivita" /></span>
                
                <span class="property-value" aria-labelledby="attivita-label"><g:fieldValue bean="${bioInstance}" field="attivita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.epoca}">
            <li class="fieldcontain">
                <span id="epoca-label" class="property-label"><g:message code="bio.epoca.label" default="Epoca" /></span>
                
                <span class="property-value" aria-labelledby="epoca-label"><g:fieldValue bean="${bioInstance}" field="epoca"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.epoca2}">
            <li class="fieldcontain">
                <span id="epoca2-label" class="property-label"><g:message code="bio.epoca2.label" default="Epoca2" /></span>
                
                <span class="property-value" aria-labelledby="epoca2-label"><g:fieldValue bean="${bioInstance}" field="epoca2"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.cittadinanza}">
            <li class="fieldcontain">
                <span id="cittadinanza-label" class="property-label"><g:message code="bio.cittadinanza.label" default="Cittadinanza" /></span>
                
                <span class="property-value" aria-labelledby="cittadinanza-label"><g:fieldValue bean="${bioInstance}" field="cittadinanza"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.attivita2}">
            <li class="fieldcontain">
                <span id="attivita2-label" class="property-label"><g:message code="bio.attivita2.label" default="Attivita2" /></span>
                
                <span class="property-value" aria-labelledby="attivita2-label"><g:fieldValue bean="${bioInstance}" field="attivita2"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.attivita3}">
            <li class="fieldcontain">
                <span id="attivita3-label" class="property-label"><g:message code="bio.attivita3.label" default="Attivita3" /></span>
                
                <span class="property-value" aria-labelledby="attivita3-label"><g:fieldValue bean="${bioInstance}" field="attivita3"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.attivitaAltre}">
            <li class="fieldcontain">
                <span id="attivitaAltre-label" class="property-label"><g:message code="bio.attivitaAltre.label" default="Attivita Altre" /></span>
                
                <span class="property-value" aria-labelledby="attivitaAltre-label"><g:fieldValue bean="${bioInstance}" field="attivitaAltre"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.nazionalita}">
            <li class="fieldcontain">
                <span id="nazionalita-label" class="property-label"><g:message code="bio.nazionalita.label" default="Nazionalita" /></span>
                
                <span class="property-value" aria-labelledby="nazionalita-label"><g:fieldValue bean="${bioInstance}" field="nazionalita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.nazionalitaNaturalizzato}">
            <li class="fieldcontain">
                <span id="nazionalitaNaturalizzato-label" class="property-label"><g:message code="bio.nazionalitaNaturalizzato.label" default="Nazionalita Naturalizzato" /></span>
                
                <span class="property-value" aria-labelledby="nazionalitaNaturalizzato-label"><g:fieldValue bean="${bioInstance}" field="nazionalitaNaturalizzato"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.postNazionalita}">
            <li class="fieldcontain">
                <span id="postNazionalita-label" class="property-label"><g:message code="bio.postNazionalita.label" default="Post Nazionalita" /></span>
                
                <span class="property-value" aria-labelledby="postNazionalita-label"><g:fieldValue bean="${bioInstance}" field="postNazionalita"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.categorie}">
            <li class="fieldcontain">
                <span id="categorie-label" class="property-label"><g:message code="bio.categorie.label" default="Categorie" /></span>
                
                <span class="property-value" aria-labelledby="categorie-label"><g:fieldValue bean="${bioInstance}" field="categorie"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.fineIncipit}">
            <li class="fieldcontain">
                <span id="fineIncipit-label" class="property-label"><g:message code="bio.fineIncipit.label" default="Fine Incipit" /></span>
                
                <span class="property-value" aria-labelledby="fineIncipit-label"><g:fieldValue bean="${bioInstance}" field="fineIncipit"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.punto}">
            <li class="fieldcontain">
                <span id="punto-label" class="property-label"><g:message code="bio.punto.label" default="Punto" /></span>
                
                <span class="property-value" aria-labelledby="punto-label"><g:fieldValue bean="${bioInstance}" field="punto"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.immagine}">
            <li class="fieldcontain">
                <span id="immagine-label" class="property-label"><g:message code="bio.immagine.label" default="Immagine" /></span>
                
                <span class="property-value" aria-labelledby="immagine-label"><g:fieldValue bean="${bioInstance}" field="immagine"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.didascalia}">
            <li class="fieldcontain">
                <span id="didascalia-label" class="property-label"><g:message code="bio.didascalia.label" default="Didascalia" /></span>
                
                <span class="property-value" aria-labelledby="didascalia-label"><g:fieldValue bean="${bioInstance}" field="didascalia"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.didascalia2}">
            <li class="fieldcontain">
                <span id="didascalia2-label" class="property-label"><g:message code="bio.didascalia2.label" default="Didascalia2" /></span>
                
                <span class="property-value" aria-labelledby="didascalia2-label"><g:fieldValue bean="${bioInstance}" field="didascalia2"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.dimImmagine}">
            <li class="fieldcontain">
                <span id="dimImmagine-label" class="property-label"><g:message code="bio.dimImmagine.label" default="Dim Immagine" /></span>
                
                <span class="property-value" aria-labelledby="dimImmagine-label"><g:fieldValue bean="${bioInstance}" field="dimImmagine"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.allineata}">
            <li class="fieldcontain">
                <span id="allineata-label" class="property-label"><g:message code="bio.allineata.label" default="Allineata" /></span>
                
                <span class="property-value" aria-labelledby="allineata-label"><g:formatBoolean boolean="${bioInstance?.allineata}" /></span>
                
            </li>
        </g:if>
        
        <g:if test="${bioInstance?.controllato}">
            <li class="fieldcontain">
                <span id="controllato-label" class="property-label"><g:message code="bio.controllato.label" default="Controllato" /></span>
                
                <span class="property-value" aria-labelledby="controllato-label"><g:formatBoolean boolean="${bioInstance?.controllato}" /></span>
                
            </li>
        </g:if>
        
    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${bioInstance?.id}" />
            <g:link class="edit" action="edit" id="${bioInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
