function verEspeciales(){
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").toggleClass("bs-docs-example");
	
	$("#contenido").append(
		"<div class='banner-grids'>"+
		"	<div id='ficheros'>"+		
		"	</div> "+
		"</div> "
	);
	
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	var user = {id: usuario.id};
	var club = {id: usuario.entrena.id};
	var request = {user: user, club: club};
	
	$.ajax({
        type: "POST",
        url: "/rest/VideoEspecial/list",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	gestionPeticion(datos);
        	
        	var ficheros = datos.data;
        	sessionStorage.setItem("fecha", new Date());
        	
        	for(i=0;i<ficheros.length;i++) {
        		var usuario = "";
        		if(ficheros[i].usuario != null) {
        			usuario = "					<p>"+ ficheros[i].usuario.nombre + " " + ficheros[i].usuario.apellido1 + " " + ficheros[i].usuario.apellido2 +"</p>";
        		} else {
        			usuario = "<p>&nbsp;</p>";
        		}
        		
    			$("#ficheros").append(
    				"<div id='fichero_"+ficheros[i].fichero.id+"'class='col-md-6 banner-grid'> " + 
    				"	<div class='banner-left-grid blue'> " + 
    				"		<div class='banner-grids'> "+
   					"			<div> "+
   					"				<div class='col-md-2 banner-grid'>" +
   					"					<span class='badge' style='font-size:1.5em;'>"+formattedSize(ficheros[i].fichero.tamano)+ "</span>"+
    				"				</div>" +
       				"				<div class='col-md-10 banner-grid' style='text-align:left;color:#FFF'> " +
    				"					<p id='nombreFichero"+ficheros[i].fichero.id+"'>"+
    										ficheros[i].fichero.descripcion +
    				"					</p> " +
    				"					<p>"+ ficheros[i].club.nombre + "</p>" +
    									usuario +
    				"					<p>"+ formattedDateCorto(new Date(ficheros[i].inicio)) + " - " + formattedDateCorto(new Date(ficheros[i].fin)) + "</p>" +
    				"				</div>" +
    				"			</div>" +
    				"		</div> " + 
    				"		<div class='clearfix'> </div> " + 
    				"	</div> " + 
    				"</div> "
    			);
        		
    			$("#fichero_"+ficheros[i].fichero.id).on( "click", function() {
					sessionStorage.setItem("ficheroId", this.id.substring(8));
					verVideoEspecial();
				});
			}
      	}
	});
}