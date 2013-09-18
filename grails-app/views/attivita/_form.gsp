












<%@ page import="it.algos.botbio.Attivita" %>



<div class="fieldcontain ${hasErrors(bean: attivitaInstance, field: 'singolare', 'error')} required">
	<label for="singolare">
		<g:message code="attivita.singolare.label" default="Singolare" />
		<span class="required-indicator">*</span>
	</label>
	










<g:textField name="singolare" required="" value="${attivitaInstance?.singolare}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: attivitaInstance, field: 'plurale', 'error')} required">
	<label for="plurale">
		<g:message code="attivita.plurale.label" default="Plurale" />
		<span class="required-indicator">*</span>
	</label>
	










<g:textField name="plurale" required="" value="${attivitaInstance?.plurale}"/>
</div>

