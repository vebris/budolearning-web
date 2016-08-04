function verFicheros() {
	if(!borrarContenido(true)) {logout(); return;}
	
	$("#contenido").toggleClass("wrapper row3");
	/*
	$("#contenido").append(
		"<nav id='' class='clear' style='display: initial;margin:0;'>" + 
		"	<ul class='clear' style='display:inline-flex'>"+
		"		<li style='margin:0.25em;' ><span class='badge badge-warning' id='headerPuntos' style='font-size:1.25em;width:3.5em'>0</span></li>"+
		"		<li style='margin:0.25em;' id='m_disciplina'></li>"+
		"		<li style='margin:0.25em;' id='m_grado'></li>"+
		"   	<li style='margin:0.25em;' id='m_recurso'></li>"+
		"   	<li id='m_fichero' class='active'></li>"+
		"	</ul>"+
		"</nav>"+
		"<section id='contenido_div' class='hoc container clear'>"+
		"	<ul id='ficheros_list' class='nospace group'>" +
		"	</ul>" +
		"</section> "+
		"<div id='dialog'></div>"
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
	$("#m_recurso").append("<a href='javascript:verRecursos()' style='padding-top: 0em'><span class='badge badge-primary' id='headerPuntos' style='font-size:1.25em;'>" + sessionStorage.getItem("gradoNombre") + "</span></a>");
	$("#m_fichero").append("<span class='badge badge-neutral' id='headerPuntos' style='font-size:1.25em;'>" + sessionStorage.getItem("recursoNombre") + "</span>");
	$("#m_fichero").addClass("active");
	
	var disciplina = {id: sessionStorage.getItem("disciplinaId")}
	var grado = {id: sessionStorage.getItem("gradoId")}
	var recurso = {id: sessionStorage.getItem("recursoId")}
	var user = {id: usuario.id};
	var request = {disciplina: disciplina, grado: grado, recurso: recurso, user: user};
	
	$.ajax({
        type: "POST",
        url: "/rest/Fichero/list",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	gestionPeticion(datos);
        	
        	var ficheros = datos.data;
        	sessionStorage.setItem("fecha", new Date());
        	sessionStorage.setItem("ficheros", JSON.stringify(ficheros));
        	
        	for(i=0;i<ficheros.length;i++) {
        		if(ficheros[i].extension == 'mp4'){
        			
        			if(i%2==0) primero='first'; else primero='';
            		$("#list").append(
       					"<li class='one_half "+primero+" post radius' style='text-align:center;margin-bottom:1em'>" +
       					"	<article class='group' id='fichero_"+ficheros[i].id+"'>" +
       					"		<figure style='padding-top:1em'>" +
       					"			<span class='badge badge-neutral' style='font-size:1.5em;color: #444444;'>"+formattedSize(ficheros[i].tamano)+ "</span><br/><br/>" +
       					"			<span class='badge badge-neutral' style='font-size:1.5em;color: #444444;'>"+formattedTime(ficheros[i].segundos)+ "</span>" +
           	            "		</figure>" +
           	            "		<div class='txtwrap' style=''>" +
           	            " 			<h6 class='heading font-x1' id=''nombreFichero"+ficheros[i].id+"'>"+ ficheros[i].descripcion +"</h6>" +
        				"			<p>"+ formattedDateCorto(new Date(ficheros[i].fecha)) + "</p>" +
        				"			<p><span class='badge badge-warning' id='coste_"+ficheros[i].id+"' style='font-size:1.25em;display:none'>"+ficheros[i].coste+"</span></p>"+
           	            "		</div>" +
           	          	"	</article>" +
       					"</li>"
           			);
        			
            		if(ficheros[i].coste <= 0) {
        				$("#coste_"+ficheros[i].id).css("display","none");
		        		$("#fichero_"+ficheros[i].id).on( "click", function() {
    						sessionStorage.setItem("ficheroId", this.id.substring(8));
    						verVideo();
    					});
	        		} else {
	        			/*$("#fichero_"+ficheros[i].id +">div>div>div>div>span.lala").addClass("badge-danger");*/
	        			$("#coste_"+ficheros[i].id).css("display","");
	        			$("#fichero_"+ficheros[i].id).on( "click", function() {
	        				fichero = null;
	        				for(zz=0;zz<ficheros.length;zz++){
	        					if(ficheros[zz].id==this.id.substring(8)){
	        						fichero=ficheros[zz];
	        					}
	        				}
	        				
	        				if (fichero != null){
		        				if (fichero.coste <= sessionStorage.getItem("puntos")) {
		        					$("#dialog").removeAttr("title");
			        				$("#dialog").attr("title","PUNTOS");
			        				
			        				$("#dialog").children().remove();
			        				$("#dialog").append("<p>Desea gastar " + fichero.coste + " de " + sessionStorage.getItem("puntos") +"? </p>");
			        				$("#dialog").dialog({
			        					buttons: {
			        						"Seguir": function() {
			        							sessionStorage.setItem("ficheroId", fichero.id);
			        							verVideo();
			        							$( this ).dialog( "close" );
			        						},
			        						"Cancelar": function() {
			        							$( this ).dialog( "close" );
			        						}
			        			        }
			        				});	
			        				
			        				$(".ui-dialog-buttonset>button").css("color","buttontext");
			        			} else {
			        				$("#dialog").removeAttr("title");
			        				$("#dialog").attr("title","PUNTOS");
			        				
			        				$("#dialog").children().remove();
			        				$("#dialog").append("<p>No puede ver este elemento.<br/> Necesita " + fichero.coste + " y tiene actualmente " + sessionStorage.getItem("puntos") +" .</p>");
			        				$("#dialog").dialog();
			        			}
	        				}
    					});
	        		}
        		}
			}
      	}
	});
}