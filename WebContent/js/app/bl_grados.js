function verGrados() {
	if(!borrarContenido(true)) {logout(); return;}
	
	$("#contenido").toggleClass("wrapper row3");
	/*
	$("#contenido").append(
		"<nav id='' class='clear' style='display: initial;margin:0;'>" + 
		"	<ul class='clear' style='display:inline-flex'>"+
		"		<li style='margin:0.25em;'><span class='badge badge-warning' id='headerPuntos' style='font-size:1.25em;width:3.5em'>0</span></li>"+
		"		<li style='margin:0.25em;' id='m_disciplina'></li>"+
		"		<li style='margin:0.25em;' id='m_grado' class='active'></li>"+
		"	</ul>"+
		"</nav>"+
		"<section id='contenido_div' class='hoc container clear'>"+
		"	<ul id='grados_list' class='nospace group'>" +
		"	</ul>" +
		"</section> "
	);
	*/
	
	$("#list").children().remove();
	
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	
	$("#list").children().remove();
	$("#contenido nav ul li").removeClass("active");
	$("#m_disciplina").children().remove();
	$("#m_grado").children().remove();
	$("#m_recurso").children().remove();
	$("#m_fichero").children().remove();
	
	
	$("#m_disciplina").append("<a href='javascript:verDisciplinas()' style='padding-top: 0em'><span class='badge badge-primary' id='headerPuntos' style='font-size:1.25em;'>DISCIPLINAS</span></a>");
	$("#m_grado").append("<span class='badge badge-neutral' id='headerPuntos' style='font-size:1.25em;'>" + sessionStorage.getItem("disciplinaNombre") + "</span>");
	$("#m_grado").addClass("active");
	
	
	
	var disciplina = {id: sessionStorage.getItem("disciplinaId")}
	var user = {id: usuario.id};
	var request = {disciplina: disciplina, user: user};
	
	$.ajax({
        type: "POST",
        url: "/rest/Grado/list",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	gestionPeticion(datos);
        	
        	var grados = datos.data;
        	sessionStorage.setItem("fecha", new Date());
        	sessionStorage.setItem("grados", JSON.stringify(grados));
        	
        	for(i=0;i<grados.length;i++) {
        		
        		if(i%4==0) primero='first'; else primero='';
        		
        		$("#list").append(
   					"<li class='one_quarter "+primero+" post radius' style='text-align:center;margin-bottom:1em'>" +
   					"	<article class='group' id='grado_"+grados[i].id+"'>" +
   					"		<figure style='width:100%'><img src='http://www.budolearning.es/rest/Grado/downloadFile/" + usuario.id + "/"+grados[i].id +"' alt=''> "+
       	            "		</figure>" +
       	            "		<div class='txtwrap' style='width:100%;padding-bottom:0'>" +
       	            " 			<h6 class='heading' id='nombreGrado"+grados[i].id+"'>"+ grados[i].nombre +"</h6>" +
       	            "		</div>" +
       	          	"	</article>" +
   					"</li>"
       			);
        						
				$("#grado_"+grados[i].id).on( "click", function() {
					sessionStorage.setItem("gradoId", this.id.substring(6));
					sessionStorage.setItem("gradoNombre", $('#nombreGrado'+this.id.substring(6)).text());
					verRecursos();
				});
			}
      	}
	});
}