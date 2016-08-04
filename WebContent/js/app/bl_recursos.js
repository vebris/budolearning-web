function verRecursos() {
	if(!borrarContenido(true)) {logout(); return;}
	
	$("#contenido").toggleClass("wrapper row3");
	/*
	$("#contenido").append(
		"<nav id='' class='clear' style='display: initial;margin:0;'>" + 
		"	<ul class='clear' style='display:inline-flex'>"+
		"		<li style='margin:0.25em;' ><span class='badge badge-warning' id='headerPuntos' style='font-size:1.25em;width:3.5em'>0</span></li>"+
		"		<li style='margin:0.25em;' id='m_disciplina'></li>"+
		"		<li style='margin:0.25em;' id='m_grado'></li>"+
		"   	<li style='margin:0.25em;' id='m_recurso' class='active'></li>"+
		"	</ul>"+
		"</nav>"+
		"<section id='contenido_div' class='hoc container clear'>"+
		"	<ul id='recursos_list' class='nospace group'>" +
		"	</ul>" +
		"</section> "
	);
	*/
	
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	
	
	$("#list").children().remove();
	$("#contenido nav ul li").removeClass("active");
	$("#m_disciplina").children().remove();
	$("#m_grado").children().remove();
	$("#m_recurso").children().remove();
	$("#m_fichero").children().remove();
	
	$("#m_disciplina").append("<a href='javascript:verDisciplinas()' style='padding-top: 0em'><span class='badge badge-primary' id='headerPuntos' style='font-size:1.25em;'>DISCIPLINAS</span></a>");
	$("#m_grado").append("<a href='javascript:verGrados()' style='padding-top: 0em'><span class='badge badge-primary' id='headerPuntos' style='font-size:1.25em;'>" + sessionStorage.getItem("disciplinaNombre") + "</span></a>");
	$("#m_recurso").append("<span class='badge badge-neutral' id='headerPuntos' style='font-size:1.25em;'>" + sessionStorage.getItem("gradoNombre") + "</span>");
	$("#m_recurso").addClass("active");
					
	var disciplina = {id: sessionStorage.getItem("disciplinaId")}
	var grado = {id: sessionStorage.getItem("gradoId")}
	var user = {id: usuario.id};
	var request = {disciplina: disciplina, grado: grado, user: user};
	
	$.ajax({
        type: "POST",
        url: "/rest/Recurso/list",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	gestionPeticion(datos);
        	
        	var recursos = datos.data;
        	sessionStorage.setItem("fecha", new Date());
        	sessionStorage.setItem("recursos", JSON.stringify(recursos));
        	
        	var anterior = "";
        	var cont = 0;
        	
        	for(i=0;i<recursos.length;i++) {
        		if(anterior != recursos[i].tipo.nombre) {
        			cont++;
        			$("#list").append("<div id='div_"+cont+"' class='clear typography-info'><h2 style='text-align:left' class='type'>"+recursos[i].tipo.nombre+"</h1></div>");
        		}
        		
        		if(cont%4==0) primero='first'; else primero='';
        		$("#div_"+cont).append(
   					"<li class='one_quarter "+primero+" post radius' style='text-align:center;margin-bottom:1em'>" +
   					"	<article class='group' id='recurso_"+recursos[i].id+"'>" +
   					"		<figure style='width:100%;margin-top:2.5em'>" +
   					"			<span class='badge' style='font-size:2.5em;font-weight:bold'>"+recursos[i].numVideos+ "</span>"+
       	            "		</figure>" +
       	            "		<div class='txtwrap' style='width:100%;padding-bottom:0'>" +
       	            " 			<h6 class='heading font-x09 font-bold' id='nombreRecurso"+recursos[i].id+"'>"+ recursos[i].nombre +"</h6>" +
       	            "		</div>" +
       	          	"	</article>" +
   					"</li>"
       			);
        		
        		if(recursos[i].numVideos == 0) {
        			$("#recurso_"+recursos[i].id + ">td>span").addClass("badge-warning");
        		} else {
        			$("#recurso_"+recursos[i].id + ">td>span").addClass("badge-success");
        		}
	        		
        		$("#recurso_"+recursos[i].id).on( "click", function() {
					sessionStorage.setItem("recursoId", this.id.substring(8));
					sessionStorage.setItem("recursoNombre", $('#nombreRecurso'+this.id.substring(8)).text());
					verFicheros();
				});
        		
        		anterior = recursos[i].tipo.nombre;
			}
        	
        	$("#recursos").append("</div>");
      	}
	});
}