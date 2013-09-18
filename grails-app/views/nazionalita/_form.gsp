












<%@ page import="it.algos.botbio.Nazionalita" %>



<div class="fieldcontain ${hasErrors(bean: nazionalitaInstance, field: 'singolare', 'error')} required">
	<label for="singolare">
		<g:message code="nazionalita.singolare.label" default="Singolare" />
		<span class="required-indicator">*</span>
	</label>
	










<g:textField name="singolare" required="" value="${nazionalitaInstance?.singolare}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: nazionalitaInstance, field: 'plurale', 'error')} required">
	<label for="plurale">
		<g:message code="nazionalita.plurale.label" default="Plurale" />
		<span class="required-indicator">*</span>
	</label>
	










<g:textField name="plurale" required="" value="${nazionalitaInstance?.plurale}"/>
</div>

