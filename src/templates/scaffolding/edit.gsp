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
    <title><g:message code="${domainClass.propertyName}.edit.label" args="[entityName]" default="Modifica"/></title>
</head>
<body>
<a href="#edit-${domainClass.propertyName}" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="\${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="${domainClass.propertyName}.list.label" args="[entityName]" default="Elenco"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="${domainClass.propertyName}.new.label" args="[entityName]" default="Nuovo"/></g:link></li>
    </ul>
</div>
<div id="edit-${domainClass.propertyName}" class="content scaffold-edit" role="main">
    <h1><g:message code="${domainClass.propertyName}.edit.label" args="[entityName]" default="Aggiungi"/></h1>
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
    <g:form method="post" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
    <g:hiddenField name="id" value="\${${propertyName}?.id}" />
    <g:hiddenField name="version" value="\${${propertyName}?.version}" />
    <fieldset class="form">
        <g:render template="form"/>
    </fieldset>
    <fieldset class="buttons">
        <g:actionSubmit class="save" action="update" value="\${message(code: 'default.button.update.label', default: 'Update')}" />
        <g:actionSubmit class="delete" action="delete" value="\${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('\${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </fieldset>
    </g:form>
</div>
</body>
</html>
