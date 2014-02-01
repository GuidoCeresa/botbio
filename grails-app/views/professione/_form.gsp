












<%@ page import="it.algos.botbio.Professione" %>



<div class="fieldcontain ${hasErrors(bean: professioneInstance, field: 'singolare', 'error')} ">
	<label for="singolare">
		<g:message code="professione.singolare.label" default="Singolare" />
		
	</label>
	










<g:textField name="singolare" value="${professioneInstance?.singolare}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: professioneInstance, field: 'voce', 'error')} ">
	<label for="voce">
		<g:message code="professione.voce.label" default="Voce" />
		
	</label>
	










<g:textField name="voce" value="${professioneInstance?.voce}"/>
</div>

