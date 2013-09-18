












<%@ page import="it.algos.botbio.Bio" %>



<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'pageid', 'error')} required">
	<label for="pageid">
		<g:message code="bio.pageid.label" default="Pageid" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="pageid" type="number" value="${bioInstance.pageid}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'titolo', 'error')} ">
	<label for="titolo">
		<g:message code="bio.titolo.label" default="Titolo" />
		
	</label>
	










<g:textField name="titolo" value="${bioInstance?.titolo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'nome', 'error')} ">
	<label for="nome">
		<g:message code="bio.nome.label" default="Nome" />
		
	</label>
	










<g:textField name="nome" value="${bioInstance?.nome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'cognome', 'error')} ">
	<label for="cognome">
		<g:message code="bio.cognome.label" default="Cognome" />
		
	</label>
	










<g:textField name="cognome" value="${bioInstance?.cognome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'postCognome', 'error')} ">
	<label for="postCognome">
		<g:message code="bio.postCognome.label" default="Post Cognome" />
		
	</label>
	










<g:textField name="postCognome" value="${bioInstance?.postCognome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'postCognomeVirgola', 'error')} ">
	<label for="postCognomeVirgola">
		<g:message code="bio.postCognomeVirgola.label" default="Post Cognome Virgola" />
		
	</label>
	










<g:textField name="postCognomeVirgola" value="${bioInstance?.postCognomeVirgola}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'forzaOrdinamento', 'error')} ">
	<label for="forzaOrdinamento">
		<g:message code="bio.forzaOrdinamento.label" default="Forza Ordinamento" />
		
	</label>
	










<g:textField name="forzaOrdinamento" value="${bioInstance?.forzaOrdinamento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'preData', 'error')} ">
	<label for="preData">
		<g:message code="bio.preData.label" default="Pre Data" />
		
	</label>
	










<g:textField name="preData" value="${bioInstance?.preData}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'sesso', 'error')} ">
	<label for="sesso">
		<g:message code="bio.sesso.label" default="Sesso" />
		
	</label>
	










<g:textField name="sesso" value="${bioInstance?.sesso}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'luogoNascita', 'error')} ">
	<label for="luogoNascita">
		<g:message code="bio.luogoNascita.label" default="Luogo Nascita" />
		
	</label>
	










<g:textField name="luogoNascita" value="${bioInstance?.luogoNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'luogoNascitaLink', 'error')} ">
	<label for="luogoNascitaLink">
		<g:message code="bio.luogoNascitaLink.label" default="Luogo Nascita Link" />
		
	</label>
	










<g:textField name="luogoNascitaLink" value="${bioInstance?.luogoNascitaLink}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'luogoNascitaAlt', 'error')} ">
	<label for="luogoNascitaAlt">
		<g:message code="bio.luogoNascitaAlt.label" default="Luogo Nascita Alt" />
		
	</label>
	










<g:textField name="luogoNascitaAlt" value="${bioInstance?.luogoNascitaAlt}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'giornoMeseNascita', 'error')} ">
	<label for="giornoMeseNascita">
		<g:message code="bio.giornoMeseNascita.label" default="Giorno Mese Nascita" />
		
	</label>
	










<g:textField name="giornoMeseNascita" value="${bioInstance?.giornoMeseNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'annoNascita', 'error')} ">
	<label for="annoNascita">
		<g:message code="bio.annoNascita.label" default="Anno Nascita" />
		
	</label>
	










<g:textField name="annoNascita" value="${bioInstance?.annoNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'noteNascita', 'error')} ">
	<label for="noteNascita">
		<g:message code="bio.noteNascita.label" default="Note Nascita" />
		
	</label>
	










<g:textField name="noteNascita" value="${bioInstance?.noteNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'luogoMorte', 'error')} ">
	<label for="luogoMorte">
		<g:message code="bio.luogoMorte.label" default="Luogo Morte" />
		
	</label>
	










<g:textField name="luogoMorte" value="${bioInstance?.luogoMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'luogoMorteLink', 'error')} ">
	<label for="luogoMorteLink">
		<g:message code="bio.luogoMorteLink.label" default="Luogo Morte Link" />
		
	</label>
	










<g:textField name="luogoMorteLink" value="${bioInstance?.luogoMorteLink}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'luogoMorteAlt', 'error')} ">
	<label for="luogoMorteAlt">
		<g:message code="bio.luogoMorteAlt.label" default="Luogo Morte Alt" />
		
	</label>
	










<g:textField name="luogoMorteAlt" value="${bioInstance?.luogoMorteAlt}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'giornoMeseMorte', 'error')} ">
	<label for="giornoMeseMorte">
		<g:message code="bio.giornoMeseMorte.label" default="Giorno Mese Morte" />
		
	</label>
	










<g:textField name="giornoMeseMorte" value="${bioInstance?.giornoMeseMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'annoMorte', 'error')} ">
	<label for="annoMorte">
		<g:message code="bio.annoMorte.label" default="Anno Morte" />
		
	</label>
	










<g:textField name="annoMorte" value="${bioInstance?.annoMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'noteMorte', 'error')} ">
	<label for="noteMorte">
		<g:message code="bio.noteMorte.label" default="Note Morte" />
		
	</label>
	










<g:textField name="noteMorte" value="${bioInstance?.noteMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'preAttivita', 'error')} ">
	<label for="preAttivita">
		<g:message code="bio.preAttivita.label" default="Pre Attivita" />
		
	</label>
	










<g:textField name="preAttivita" value="${bioInstance?.preAttivita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'attivita', 'error')} ">
	<label for="attivita">
		<g:message code="bio.attivita.label" default="Attivita" />
		
	</label>
	










<g:textField name="attivita" value="${bioInstance?.attivita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'epoca', 'error')} ">
	<label for="epoca">
		<g:message code="bio.epoca.label" default="Epoca" />
		
	</label>
	










<g:textField name="epoca" value="${bioInstance?.epoca}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'epoca2', 'error')} ">
	<label for="epoca2">
		<g:message code="bio.epoca2.label" default="Epoca2" />
		
	</label>
	










<g:textField name="epoca2" value="${bioInstance?.epoca2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'cittadinanza', 'error')} ">
	<label for="cittadinanza">
		<g:message code="bio.cittadinanza.label" default="Cittadinanza" />
		
	</label>
	










<g:textField name="cittadinanza" value="${bioInstance?.cittadinanza}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'attivita2', 'error')} ">
	<label for="attivita2">
		<g:message code="bio.attivita2.label" default="Attivita2" />
		
	</label>
	










<g:textField name="attivita2" value="${bioInstance?.attivita2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'attivita3', 'error')} ">
	<label for="attivita3">
		<g:message code="bio.attivita3.label" default="Attivita3" />
		
	</label>
	










<g:textField name="attivita3" value="${bioInstance?.attivita3}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'attivitaAltre', 'error')} ">
	<label for="attivitaAltre">
		<g:message code="bio.attivitaAltre.label" default="Attivita Altre" />
		
	</label>
	










<g:textField name="attivitaAltre" value="${bioInstance?.attivitaAltre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'nazionalita', 'error')} ">
	<label for="nazionalita">
		<g:message code="bio.nazionalita.label" default="Nazionalita" />
		
	</label>
	










<g:textField name="nazionalita" value="${bioInstance?.nazionalita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'nazionalitaNaturalizzato', 'error')} ">
	<label for="nazionalitaNaturalizzato">
		<g:message code="bio.nazionalitaNaturalizzato.label" default="Nazionalita Naturalizzato" />
		
	</label>
	










<g:textField name="nazionalitaNaturalizzato" value="${bioInstance?.nazionalitaNaturalizzato}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'postNazionalita', 'error')} ">
	<label for="postNazionalita">
		<g:message code="bio.postNazionalita.label" default="Post Nazionalita" />
		
	</label>
	










<g:textField name="postNazionalita" value="${bioInstance?.postNazionalita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'categorie', 'error')} ">
	<label for="categorie">
		<g:message code="bio.categorie.label" default="Categorie" />
		
	</label>
	










<g:textField name="categorie" value="${bioInstance?.categorie}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'fineIncipit', 'error')} ">
	<label for="fineIncipit">
		<g:message code="bio.fineIncipit.label" default="Fine Incipit" />
		
	</label>
	










<g:textField name="fineIncipit" value="${bioInstance?.fineIncipit}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'punto', 'error')} ">
	<label for="punto">
		<g:message code="bio.punto.label" default="Punto" />
		
	</label>
	










<g:textField name="punto" value="${bioInstance?.punto}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'immagine', 'error')} ">
	<label for="immagine">
		<g:message code="bio.immagine.label" default="Immagine" />
		
	</label>
	










<g:textField name="immagine" value="${bioInstance?.immagine}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'didascalia', 'error')} ">
	<label for="didascalia">
		<g:message code="bio.didascalia.label" default="Didascalia" />
		
	</label>
	










<g:textField name="didascalia" value="${bioInstance?.didascalia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'didascalia2', 'error')} ">
	<label for="didascalia2">
		<g:message code="bio.didascalia2.label" default="Didascalia2" />
		
	</label>
	










<g:textField name="didascalia2" value="${bioInstance?.didascalia2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'dimImmagine', 'error')} ">
	<label for="dimImmagine">
		<g:message code="bio.dimImmagine.label" default="Dim Immagine" />
		
	</label>
	










<g:textField name="dimImmagine" value="${bioInstance?.dimImmagine}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'allineata', 'error')} ">
	<label for="allineata">
		<g:message code="bio.allineata.label" default="Allineata" />
		
	</label>
	










<g:checkBox name="allineata" value="${bioInstance?.allineata}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioInstance, field: 'controllato', 'error')} ">
	<label for="controllato">
		<g:message code="bio.controllato.label" default="Controllato" />
		
	</label>
	










<g:checkBox name="controllato" value="${bioInstance?.controllato}" />
</div>

