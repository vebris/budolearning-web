function verCalendario() {
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").toggleClass("bs-docs-example");
	$("#contenido").append(
			"<div class='banner-grids'>"+
			"	<div id='calendario'>"+
			"	</div> "+
			"</div> "
	);
	$.ajax({
        type: "POST",
        url: "/rest/Curso/list",
        success: function(datos){
        	gestionPeticion(datos);
        	
        	var cursos = datos.data;
        	
       		for(i=0;i<cursos.length;i++){
       			$("#calendario").append(
       					"<div id='curso_"+cursos[i].id+"' class='col-md-12 banner-grid'> " +
       					"	<div class='banner-left-grid blue'> " +
       					"		<div class='banner-grids'> "+
       					"			<div> "+
       					"				<div class='col-md-2 banner-grid'>" +
	       				"					<img src='/rest/Curso/downloadFile/0/"+cursos[i].id +"' style='max-height: 150px;max-width: 150px;'>" +
	       				"				</div>" +
	       				"				<div class='col-md-10 banner-grid' style='text-align:left;color:#FFF'> " +
	       				"					<p id='nombreCurso"+cursos[i].id+"'>"+cursos[i].nombre+" </p>" +
	       				"					<p>" +
       					"						Inicio: "+formattedDate(new Date(cursos[i].inicio))+"<br/>" +
       					"						Fin: "+formattedDate(new Date(cursos[i].fin))+
       					"					</p>" +
       					"					<p>"+cursos[i].profesor+" </p>" +
       					"					<p>"+cursos[i].direccion+"</p>" +
       					"				</div> " +
       					"			</div> " +
       					"		</div>" + 
       					"		<div class='clearfix'> </div> " + 
       					"	</div> " + 
       					"</div> " 
       					);
       		}
      	}
	});
}