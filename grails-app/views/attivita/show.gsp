













<%@ page import="it.algos.botbio.Attivita" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'attivita.label', default: 'Attivita')}" />
    <title><g:message code="attivita.show.label" args="[entityName]" default="Mostra"/></title>
</head>
<body>
<a href="#show-attivita" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="attivita.list.label" args="[entityName]" default="Elenco"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="attivita.new.label" args="[entityName]" default="Nuovo"/></g:link></li>
    </ul>
</div>
<div id="show-attivita" class="content scaffold-show" role="main">
    <h1><g:message code="attivita.show.label" args="[entityName]" default="Mostra"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list attivita">
        
        <g:if test="${attivitaInstance?.singolare}">
            <li class="fieldcontain">
                <span id="singolare-label" class="property-label"><g:message code="attivita.singolare.label" default="Singolare" /></span>
                
                <span class="property-value" aria-labelledby="singolare-label"><g:fieldValue bean="${attivitaInstance}" field="singolare"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${attivitaInstance?.plurale}">
            <li class="fieldcontain">
                <span id="plurale-label" class="property-label"><g:message code="attivita.plurale.label" default="Plurale" /></span>
                
                <span class="property-value" aria-labelledby="plurale-label"><g:fieldValue bean="${attivitaInstance}" field="plurale"/></span>
                
            </li>
        </g:if>
        
    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${attivitaInstance?.id}" />
            <g:link class="edit" action="edit" id="${attivitaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
