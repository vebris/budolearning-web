function verGrados() {
	if(!borrarContenido()) {logout(); return;}
	$("#contenido").append(
		"<ol id='migas' class='breadcrumb'>"+
		"	<li><span class='badge badge-warning' id='headerPuntos' style='font-size:1.25em;'>0</span></li>" +
		"	<li id='m_disciplina'></li>" +
		"	<li id='m_grado' class='active'></li>"+
		"</ol>"+		
		"<div class='banner-grids'>"+
		"	<div id='grados'>"+		
		"	</div> "+
		"</div> "
	);
	
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	
	$("#m_disciplina").append("<a href='javascript:verDisciplinas()'>DISCIPLINAS</a>");
	$("#m_grado").append(sessionStorage.getItem("disciplinaNombre"));
	
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
				$("#grados").append(
				"<div id='grado_"+grados[i].id+"'class='col-md-3 banner-grid'> " + 
				"	<div class='banner-left-grid blue'> " + 
				"		<div class='banner-grid-info'> " +
				"			<img src='http://www.budolearning.es/rest/Grado/downloadFile/" + usuario.id + "/"+grados[i].id +"' style='height: 150px;width: 150px;'>" +
				"			<p id='nombreGrado"+grados[i].id+"'>"+grados[i].nombre+"</p> " + 
				"			" + 
				"		</div> " + 
				"		<div class='clearfix'> </div> " + 
				"	</div> " + 
				"</div> " 
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