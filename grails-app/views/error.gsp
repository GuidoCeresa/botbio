<%--Created by Algos s.r.l.--%>
<%--Date: mag 2013--%>
<%--Il plugin Algos ha inserito (solo la prima volta) questo header per controllare--%>
<%--le successive release (tramite il flag di controllo aggiunto)--%>
<%--Tipicamente VERRA sovrascritto ad ogni nuova release del plugin--%>
<%--per rimanere aggiornato--%>
<%--Se vuoi che le prossime release del plugin NON sovrascrivano questo file,--%>
<%--perdendo tutte le modifiche precedentemente effettuate,--%>
<%--regola a false il flag di controllo flagOverwrite©--%>
<%--flagOverwrite = true--%>

<!DOCTYPE html>
<html>
	<head>
		<title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error</g:else></title>
		<meta name="layout" content="main">
		<g:if env="development"><link rel="stylesheet" href="${resource(dir: 'css', file: 'errors.css')}" type="text/css"></g:if>
	</head>
	<body>
		<g:if env="development">
			<g:renderException exception="${exception}" />
		</g:if>
		<g:else>
			<ul class="errors">
				<li>Si è verificato un errore</li>
			</ul>
		</g:else>
	</body>
</html>
