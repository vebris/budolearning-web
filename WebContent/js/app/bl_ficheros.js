function verFicheros() {
	if(!borrarContenido()) {logout(); return;}
	$("#contenido").append(
		"<ol id='migas' class='breadcrumb'>"+
		"	<li><span class='badge badge-warning' id='headerPuntos' style='font-size:1.25em;'>0</span></li>" +
		"	<li id='m_disciplina'></li>" +
		"	<li id='m_grado'></li>"+
		"   <li id='m_recurso'></li>"+
		"   <li id='m_fichero' class='active'></li>"+
		"</ol>"+		
		"<div class='banner-grids'>"+
		"	<div id='ficheros'>"+		
		"	</div> "+
		"</div> " +
		"<div id='dialog'></div>"
	);
	
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	
	$("#m_disciplina").append("<a href='javascript:verDisciplinas()'>DISCIPLINAS </a>");
	$("#m_grado").append("<a href='javascript:verGrados()'>"+ "" + sessionStorage.getItem("disciplinaNombre") + " </a>");
	$("#m_recurso").append("<a href='javascript:verRecursos()'>"+"" + sessionStorage.getItem("gradoNombre") + " </a>");
	$("#m_fichero").append(sessionStorage.getItem("recursoNombre"));
	
	
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
        			$("#ficheros").append(
    					"<div id='fichero_"+ficheros[i].id+"'class='col-md-12 banner-grid'> " + 
        				"	<div class='banner-left-grid blue'> " + 
        				"		<div class='banner-grids'> "+
       					"			<div> "+
       					"				<div class='col-md-2 banner-grid'>" +
       					"					<span class='badge lala' style='font-size:1.5em;'>"+formattedSize(ficheros[i].tamano)+ "</span><br/><br/>" +
       					"					<span class='badge' style='font-size:1.5em;'>"+formattedTime(ficheros[i].segundos)+ "</span>" +
        				"				</div>" +
           				"				<div class='col-md-8 banner-grid' style='text-align:left;color:#FFF'> " +
        				"					<p id='nombreFichero"+ficheros[i].id+"'>"+
        										ficheros[i].descripcion +
        				"					</p> " +
        				"				</div>" +
        				"				<div class='col-md-2 banner-grid' style='text-align:left;color:#FFF'> " +
        				"					<p>"+ formattedDateCorto(new Date(ficheros[i].fecha)) + "</p>" +
        				"					<p><span class='badge badge-warning' id='coste_"+ficheros[i].id+"' style='font-size:1.25em;display:none'>"+ficheros[i].coste+"</span></p>"+
        				"				</div>" +
        				"			</div>" +
        				"		</div> " + 
        				"		<div class='clearfix'> </div> " + 
        				"	</div> " + 
        				"</div> "
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