function verDisciplinas() {
	if(!borrarContenido()) {logout(); return;}
	$("#contenido").append(
		"<ol id='migas' class='breadcrumb'>"+
		"	<li><span class='badge badge-warning' id='headerPuntos' style='font-size:1.25em;'>0</span></li>" +
		"	<li id='m_disciplina' class='active'>DISCIPLINAS</li>"+
		"</ol>"+		
		"<div class='banner-grids'>"+
		"	<div id='disciplinas'>"+
		"	</div> "+
		"</div> "
	);
	
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	
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
				$("#disciplinas").append(
				"<div id='disciplina_"+disciplinas[i].id+"' class='col-md-4 banner-grid'> " + 
				"	<div class='banner-left-grid blue'> " + 
				"		<div class='banner-grid-info'> " +
				"			<img src='/rest/Disciplina/downloadFile/" + usuario.id + "/"+disciplinas[i].id +"' style='height: 150px;width: auto;'>" +
				"			<p id='nombreDisciplina"+disciplinas[i].id+"'>"+disciplinas[i].nombre+"</p> " + 
				"			" + 
				"		</div> " + 
				"		<div class='clearfix'> </div> " + 
				"	</div> " + 
				"</div> " 
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