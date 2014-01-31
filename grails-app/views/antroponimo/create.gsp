












<%@ page import="it.algos.botbio.Antroponimo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'antroponimo.label', default: 'Antroponimo')}" />
    <title><g:message code="antroponimo.create.label" args="[entityName]" default="Nuovo"/></title>
</head>
<body>
<a href="#create-antroponimo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="antroponimo.list.label" args="[entityName]" default="Elenco"/></g:link></li>
    </ul>
</div>
<div id="create-antroponimo" class="content scaffold-create" role="main">
    <h1><g:message code="antroponimo.create.label" args="[entityName]" default="Nuovo"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${antroponimoInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${antroponimoInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="save" >
    <fieldset class="form">
        <g:render template="form"/>
    </fieldset>
    <fieldset class="buttons">
        <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
    </fieldset>
    </g:form>
</div>
</body>
</html>
