












<%@ page import="it.algos.botbio.Attivita" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'attivita.label', default: 'Attivita')}" />
    <title><g:message code="attivita.edit.label" args="[entityName]" default="Modifica"/></title>
</head>
<body>
<a href="#edit-attivita" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="attivita.list.label" args="[entityName]" default="Elenco"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="attivita.new.label" args="[entityName]" default="Nuovo"/></g:link></li>
    </ul>
</div>
<div id="edit-attivita" class="content scaffold-edit" role="main">
    <h1><g:message code="attivita.edit.label" args="[entityName]" default="Aggiungi"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${attivitaInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${attivitaInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form method="post" >
    <g:hiddenField name="id" value="${attivitaInstance?.id}" />
    <g:hiddenField name="version" value="${attivitaInstance?.version}" />
    <fieldset class="form">
        <g:render template="form"/>
    </fieldset>
    <fieldset class="buttons">
        <g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </fieldset>
    </g:form>
</div>
</body>
</html>
