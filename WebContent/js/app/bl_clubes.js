function verClubes() {
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").toggleClass("bs-docs-example");
	$("#contenido").append(
		"<div class='banner-grids'>"+
		"	<div id='clubes'>"+
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
        url: "/rest/Club/list",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	gestionPeticion(datos);
        	
        	var clubes = datos.data;
        	sessionStorage.setItem("fecha", new Date());
        	sessionStorage.setItem("clubes", JSON.stringify(clubes));
        	
        	for(var i=0;i<clubes.length;i++){
				$("#clubes").append(
				"<div id='club_"+clubes[i].id+"' class='col-md-6 banner-grid'> " + 
				"	<div class='banner-left-grid blue'> " + 
				"		<div class='banner-grids'> "+
				"			<div> "+
				"				<div class='col-md-2 banner-grid'>" +
				"					<img src='/rest/Club/downloadFile/" + usuario.id + "/"+clubes[i].id +"' style='max-height: 150px;max-width: 150px;'>" +
				"				</div>" +
				"				<div class='col-md-1 banner-grid'></div>" +
				"				<div class='col-md-8 banner-grid' style='text-align:left;color:#FFF'> " +
				"					<p>&nbsp;" + clubes[i].nombre + "</p> " +
				"					<p>&nbsp;" + clubes[i].descripcion + "</p> " +
				"					<p>&nbsp;" + clubes[i].direccion + " " + clubes[i].localidad + "</p> " +
				"					<p>&nbsp;" + clubes[i].email + "</p>" +
				"					<p>&nbsp;" + clubes[i].telefono + "</p> " +
				"					<p>&nbsp;" + clubes[i].web + "</p> " +
				"				</div>" +
				"			</div> " +
				"		</div> " + 
				"		<div class='clearfix'> </div> " + 
				"	</div> " + 
				"</div> " 
				);
			}
      	}
	});
}