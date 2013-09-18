













<%@ page import="it.algos.botbio.Nazionalita" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'nazionalita.label', default: 'Nazionalita')}" />
    <title><g:message code="nazionalita.show.label" args="[entityName]" default="Mostra"/></title>
</head>
<body>
<a href="#show-nazionalita" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="nazionalita.list.label" args="[entityName]" default="Elenco"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="nazionalita.new.label" args="[entityName]" default="Nuovo"/></g:link></li>
    </ul>
</div>
<div id="show-nazionalita" class="content scaffold-show" role="main">
    <h1><g:message code="nazionalita.show.label" args="[entityName]" default="Mostra"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list nazionalita">
        
        <g:if test="${nazionalitaInstance?.singolare}">
            <li class="fieldcontain">
                <span id="singolare-label" class="property-label"><g:message code="nazionalita.singolare.label" default="Singolare" /></span>
                
                <span class="property-value" aria-labelledby="singolare-label"><g:fieldValue bean="${nazionalitaInstance}" field="singolare"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${nazionalitaInstance?.plurale}">
            <li class="fieldcontain">
                <span id="plurale-label" class="property-label"><g:message code="nazionalita.plurale.label" default="Plurale" /></span>
                
                <span class="property-value" aria-labelledby="plurale-label"><g:fieldValue bean="${nazionalitaInstance}" field="plurale"/></span>
                
            </li>
        </g:if>
        
    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${nazionalitaInstance?.id}" />
            <g:link class="edit" action="edit" id="${nazionalitaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
