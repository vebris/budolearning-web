function verClubes() {
	if(!borrarContenido()) {logout(); return;}
	
	
	$("#contenido").toggleClass("wrapper row3");
	
	$("#contenido").toggleClass("bs-docs-example");
	$("#contenido").append(
		"<section id='contenido_div' class='hoc container clear'>"+
		"	<ul id='clubes' class='nospace group'>" +
		"	</ul>" +
		"</section> "
	);
	
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	
	if(usuario != undefined){
		var usuario = { id: usuario.id };
	} else {
		var usuario = {id: 0};	
	}
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
        		
        		if(i%1==0) primero='first'; else primero='';
        		
        		nombre = '';
        		direccion = '';
        		email = '';
        		telefono = '';
        		web = '';
        		profesor = '';
        		
        		
        		if(clubes[i].nombre!=null && clubes[i].nombre.length>0) nombre = "<p>&nbsp;" + clubes[i].nombre + "&nbsp;("+clubes[i].descripcion + ")</p> ";
        		if(clubes[i].direccion!=null && clubes[i].direccion.length>0) direccion = "<p>&nbsp;" + clubes[i].direccion + " " + clubes[i].localidad + "</p> ";
        		if(clubes[i].email!=null && clubes[i].email.length>0) email = "<p>&nbsp;<a href='mailto:"+clubes[i].email+"'>" + clubes[i].email + "</a></p>";
        		if(clubes[i].telefono!=null && clubes[i].telefono.length>0) telefono = "<p>&nbsp; "+ clubes[i].telefono + "</p>";
        		if(clubes[i].web!=null && clubes[i].web.length>0) web = "<p>&nbsp;<a href='http\://"+clubes[i].web +"' target='_blank'>" + clubes[i].web + "</a></p> ";
        		if(clubes[i].profesor!=null && clubes[i].profesor.length>0) profesor = "<p>&nbsp;" + clubes[i].profesor + "</p> ";
        		$("#clubes").append(
       					"<li class='one_one "+primero+" post radius' style='text-align:center;margin-bottom:1em'>" +
       					"	<article class='group' id='club_"+clubes[i].id+"'>" +
       					"		<figure style='padding-top:1em'>" +
       					"			<img src='/rest/Club/downloadFile/0/" + clubes[i].id + "' alt=''> "+
           	            "		</figure>" +
           	            "		<div class='txtwrap' style='text-align:left'>" +
           	            nombre +
           	            direccion +
           	            email + 
           	            telefono +
           	            web + 
           	            profesor + 
           	            "		</div>" +
           	          	"	</article>" +
       					"</li>"
           			);
        		
        		/*
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
				*/
			}
      	}
	});
	
	finCargaContenido();
}