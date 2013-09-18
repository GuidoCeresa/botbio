<%/* Created by Algos s.r.l. */%>
<%/* Date: mag 2013 */%>
<%/* Il plugin Algos ha creato o sovrascritto il templates che ha creato questo file. */%>
<%/* L'header del templates serve per controllare le successive release */%>
<%/* (tramite il flag di controllo aggiunto) */%>
<%/* Tipicamente VERRA sovrascritto (il template, non il file) ad ogni nuova release */%>
<%/* del plugin per rimanere aggiornato. */%>
<%/* Se vuoi che le prossime release del plugin NON sovrascrivano il template che */%>
<%/* genera questo file, perdendo tutte le modifiche precedentemente effettuate, */%>
<%/* regola a false il flag di controllo flagOverwrite© del template stesso. */%>
<%/* (non quello del singolo file) */%>
<%/* flagOverwrite = true */%>

<%=packageName%>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
    <title><g:message code="${domainClass.propertyName}.create.label" args="[entityName]" default="Nuovo"/></title>
</head>
<body>
<a href="#create-${domainClass.propertyName}" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="\${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="${domainClass.propertyName}.list.label" args="[entityName]" default="Elenco"/></g:link></li>
    </ul>
</div>
<div id="create-${domainClass.propertyName}" class="content scaffold-create" role="main">
    <h1><g:message code="${domainClass.propertyName}.create.label" args="[entityName]" default="Nuovo"/></h1>
    <g:if test="\${flash.message}">
        <div class="message" role="status">\${flash.message}</div>
    </g:if>
    <g:hasErrors bean="\${${propertyName}}">
        <ul class="errors" role="alert">
            <g:eachError bean="\${${propertyName}}" var="error">
                <li <g:if test="\${error in org.springframework.validation.FieldError}">data-field-id="\${error.field}"</g:if>><g:message error="\${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="save" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
    <fieldset class="form">
        <g:render template="form"/>
    </fieldset>
    <fieldset class="buttons">
        <g:submitButton name="create" class="save" value="\${message(code: 'default.button.create.label', default: 'Create')}" />
    </fieldset>
    </g:form>
</div>
</body>
</html>
