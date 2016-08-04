function verEspeciales(){
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").toggleClass("wrapper row3");
	
	$("#contenido").append(
		"<section id='contenido_div' class='hoc container clear'>"+
		"	<ul id='ficheros_list' class='nospace group'>" +
		"	</ul>" +
		"</section> "
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
        		
        		if(i%2==0) { clear = 'first'; } else { clear = ''; }
        		
        		$("#ficheros_list").append(
       					"<li class='"+clear+" one_half post radius' style='text-align:center;margin-bottom:1em'>" +
       					"	<article class='group' id='fichero_"+ficheros[i].fichero.id+"'>" +
       					"		<figure style='padding-top:5em'>" +
       					"			<span class='badge badge-neutral' style='font-size:1.5em;color: #444444;'>"+formattedSize(ficheros[i].fichero.tamano)+ "</span><br/><br/>" +
       					"			<span class='badge badge-neutral' style='font-size:1.5em;color: #444444;'>"+formattedTime(ficheros[i].fichero.segundos)+ "</span>" +
           	            "		</figure>" +
           	            "		<div class='txtwrap' style='text-align:left;font-size:large'>" +
           	            " 			<p id='nombreFichero"+ficheros[i].fichero.id+"'>"+
    										ficheros[i].fichero.descripcion +
    					"					</p> " +
    					"					<p>"+ ficheros[i].club.nombre + "</p>" +
    									usuario +
    					"					<p>"+ formattedDateCorto(new Date(ficheros[i].inicio)) + " - " + formattedDateCorto(new Date(ficheros[i].fin)) + "</p>" +
           	            "		</div>" +
           	          	"	</article>" +
       					"</li>"
           			);
        		
    			$("#fichero_"+ficheros[i].fichero.id).on( "click", function() {
					sessionStorage.setItem("ficheroId", this.id.substring(8));
					verVideoEspecial();
				});
			}
      	}
	});
}