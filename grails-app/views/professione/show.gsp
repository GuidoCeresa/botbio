













<%@ page import="it.algos.botbio.Professione" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'professione.label', default: 'Professione')}" />
    <title><g:message code="professione.show.label" args="[entityName]" default="Mostra"/></title>
</head>
<body>
<a href="#show-professione" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="professione.list.label" args="[entityName]" default="Elenco"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="professione.new.label" args="[entityName]" default="Nuovo"/></g:link></li>
    </ul>
</div>
<div id="show-professione" class="content scaffold-show" role="main">
    <h1><g:message code="professione.show.label" args="[entityName]" default="Mostra"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list professione">
        
        <g:if test="${professioneInstance?.singolare}">
            <li class="fieldcontain">
                <span id="singolare-label" class="property-label"><g:message code="professione.singolare.label" default="Singolare" /></span>
                
                <span class="property-value" aria-labelledby="singolare-label"><g:fieldValue bean="${professioneInstance}" field="singolare"/></span>
                
            </li>
        </g:if>
        
        <g:if test="${professioneInstance?.voce}">
            <li class="fieldcontain">
                <span id="voce-label" class="property-label"><g:message code="professione.voce.label" default="Voce" /></span>
                
                <span class="property-value" aria-labelledby="voce-label"><g:fieldValue bean="${professioneInstance}" field="voce"/></span>
                
            </li>
        </g:if>
        
    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${professioneInstance?.id}" />
            <g:link class="edit" action="edit" id="${professioneInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
