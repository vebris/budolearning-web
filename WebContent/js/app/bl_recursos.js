function verRecursos() {
	if(!borrarContenido()) {logout(); return;}
	$("#contenido").append(
		"<ol id='migas' class='breadcrumb'>"+
		"	<li><span class='badge badge-warning' id='headerPuntos' style='font-size:1.25em;'>0</span></li>" +
		"	<li id='m_disciplina'></li>" +
		"	<li id='m_grado'></li>"+
		"   <li id='m_recurso' class='active'></li>"+
		"</ol>"+		
		"<div class='banner-grids'>"+
		"	<div id='recursos'>"+		
		"	</div> "+
		"</div> "
	);
	
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	
	$("#m_disciplina").append("<a href='javascript:verDisciplinas()'>DISCIPLINAS</a>");
	$("#m_grado").append("<a href='javascript:verGrados()'>"+ "" + sessionStorage.getItem("disciplinaNombre") + " </a>");
	$("#m_recurso").append(sessionStorage.getItem("gradoNombre"));
					
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
        	var cont = 1;
        	
        	for(i=0;i<recursos.length;i++) {
        		if(anterior != recursos[i].tipo.nombre) {
        			cont++;
        			$("#recursos").append("<div id='div_"+cont+"' class='clearfix typography-info'><h2 style='text-align:left' class='type'>"+recursos[i].tipo.nombre+"</h1></div>");
        		}
        		
    			$("#div_"+cont).append(
    				"<div id='recurso_"+recursos[i].id+"'class='col-md-4 banner-grid'> " + 
    				"	<div class='banner-left-grid blue'> " + 
    				"		<div class='banner-grids'> "+
   					"			<div> "+
   					"				<div class='col-md-2 banner-grid'>" +
   					"					<span class='badge' style='font-size:1.5em'>"+recursos[i].numVideos+ "</span>" +
    				"				</div>" +
       				"				<div class='col-md-10 banner-grid' style='text-align:left;color:#FFF'> " +
    				"					<p id='nombreRecurso"+recursos[i].id+"'>" + recursos[i].nombre + "</p> " +
    				"				</div>" +
    				"			</div>" +
    				"		</div> " + 
    				"		<div class='clearfix'> </div> " + 
    				"	</div> " + 
    				"</div> "
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