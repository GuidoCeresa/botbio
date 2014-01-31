













<%@ page import="it.algos.botbio.Antroponimo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'antroponimo.label', default: 'Antroponimo')}" />
    <title><g:message code="antroponimo.show.label" args="[entityName]" default="Mostra"/></title>
</head>
<body>
<a href="#show-antroponimo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="antroponimo.list.label" args="[entityName]" default="Elenco"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="antroponimo.new.label" args="[entityName]" default="Nuovo"/></g:link></li>
    </ul>
</div>
<div id="show-antroponimo" class="content scaffold-show" role="main">
    <h1><g:message code="antroponimo.show.label" args="[entityName]" default="Mostra"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list antroponimo">
        
        <g:if test="${antroponimoInstance?.nome}">
            <li class="fieldcontain">
                <span id="nome-label" class="property-label"><g:message code="antroponimo.nome.label" default="Nome" /></span>
                
                <span class="property-value" aria-labelledby="nome-label"><g:fieldValue bean="${antroponimoInstance}" field="nome"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${antroponimoInstance?.voci}">
            <li class="fieldcontain">
                <span id="voci-label" class="property-label"><g:message code="antroponimo.voci.label" default="Voci" /></span>
                
                <span class="property-value" aria-labelledby="voci-label"><g:fieldValue bean="${antroponimoInstance}" field="voci"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${antroponimoInstance?.lunghezza}">
            <li class="fieldcontain">
                <span id="lunghezza-label" class="property-label"><g:message code="antroponimo.lunghezza.label" default="Lunghezza" /></span>
                
                <span class="property-value" aria-labelledby="lunghezza-label"><g:fieldValue bean="${antroponimoInstance}" field="lunghezza"/></span>
                
            </li>
        </g:if>
        
    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${antroponimoInstance?.id}" />
            <g:link class="edit" action="edit" id="${antroponimoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
