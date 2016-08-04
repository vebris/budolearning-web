function verDisciplinas() {
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").toggleClass("wrapper row3");
	$("#contenido").append(
		"<nav id='' class='clear' style='display: initial;margin:0;'>" + 
		"	<ul class='clear' style='padding-top:0.25em'>"+
		"		<li style='margin:0.25em;display:inline-block; float:left; height:2em' ><span class='badge badge-warning' id='headerPuntos' style='font-size:1.25em;width:3.5em'>0</span></li>"+
		"		<li style='margin:0.25em;display:inline-block; float:rigth;' id='m_disciplina'></li>"+
		"		<li style='margin:0.25em;display:inline-block; float:rigth;' id='m_grado'></li>"+
		"   	<li style='margin:0.25em;display:inline-block; float:rigth;' id='m_recurso'></li>"+
		"   	<li style='margin:0.25em;display:inline-block; float:rigth;' id='m_fichero'></li>"+
		"	</ul>"+
		"</nav>"+
		"<section id='contenido_div' class='hoc container clear'>"+
		"	<ul id='list' class='nospace group'>" +
		"	</ul>" +
		"</section> "
	);
	
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	
	$("#list").children().remove();
	$("#contenido nav ul li").removeClass("active");
	$("#m_disciplina").children().remove();
	$("#m_grado").children().remove();
	$("#m_recurso").children().remove();
	$("#m_fichero").children().remove();
	
	$("#m_disciplina").append("<span class='badge badge-neutral' id='headerPuntos' style='font-size:1.25em;'>DISCIPLINAS</span>");
	$("#m_disciplina").addClass("active");
	
	
	var usuario = {
			id: usuario.id
	};
	var request = {user: usuario};
	
	$.ajax({
        type: "POST",
        url: "/rest/Disciplina/list",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	gestionPeticion(datos);
        	
        	var disciplinas = datos.data;
        	sessionStorage.setItem("fecha", new Date());
        	sessionStorage.setItem("disciplinas", JSON.stringify(disciplinas));
        	
        	for(var i=0;i<disciplinas.length;i++){
        		if(i%3==0) primero='first'; else primero='';
        		
        		$("#list").append(
   					"<li class='one_third "+primero+" post radius' style='text-align:center;margin-bottom:1em'>" +
   					"	<article class='group' id='disciplina_"+disciplinas[i].id+"'>" +
   					"		<figure style='width:100%'><img src='/rest/Disciplina/downloadFile/" + usuario.id + "/"+disciplinas[i].id + "' alt=''> "+
       	            "		</figure>" +
       	            "		<div class='txtwrap' style='width:100%;padding-bottom:0'>" +
       	            " 			<h6 class='heading' id='nombreDisciplina"+disciplinas[i].id+"'>"+ disciplinas[i].nombre +"</h6>" +
       	            "		</div>" +
       	          	"	</article>" +
   					"</li>"
       			);
        		
				$("#disciplina_"+disciplinas[i].id).on( "click", function() {
					sessionStorage.setItem("disciplinaId", this.id.substring(11));
					sessionStorage.setItem("disciplinaNombre", $('#nombreDisciplina'+this.id.substring(11)).text());
					verGrados();
				});
			}
      	}
	});
}