function verCalendario() {
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").addClass("wrapper row3");
	$("#contenido").append("<section id='contenido_div' class='hoc container clear'>" +
			//"	<div class='center btmspace-80'>" +
			//" 		<h2 class='font-x3'>Calendario</h2> "+
			//"		<p></p>" +
			//"	</div>" +
			"	<ul id='calendar_list' class='nospace group'>" +
			"	</ul>" +
			"</section>");
	$.ajax({
        type: "POST",
        url: "/rest/Curso/list",
        success: function(datos){
        	gestionPeticion(datos);
        	
        	var cursos = datos.data;
        	
       		for(i=0;i<cursos.length;i++){
       			if(i%2==0) primero='first'; else primero='';
       			inicio = new Date(cursos[i].inicio);
       			fin = new Date(cursos[i].fin);
       			$("#calendar_list").append(
       					"<li class='one_half "+primero+" post radius' style='text-align:center;margin-bottom:1em'>" +
       					"	<article class='group'>" +
       					"		<figure><img src='/rest/Curso/downloadFile/0/" + cursos[i].id + "' alt=''> "+
	       	            "			<figcaption>" +
	       	            "				<time datetime='" + inicio+"'><strong>"+inicio.getUTCDate()+"</strong> <em>"+monthNames[inicio.getMonth()]+"</em></time>" +
	       	            "			</figcaption>" +
	       	            "		</figure>" +
	       	            "		<div class='txtwrap' style='text-align:left'>" +
	       	            " 			<h6 class='heading'>"+ cursos[i].nombre +"</h6>" +
	       	            " 			<p>Desde: " + formattedDate(inicio) + "<br/>" + "Hasta: " + formattedDate (fin)+ "</p>" +
	       	            " 			<p>" + cursos[i].profesor  + "</p>" +
	       	            " 			<p>" + cursos[i].direccion + "</p>" +
	       	            "			<!--<footer><a href='#'>Read More</a></footer>-->" +
	       	            "		</div>" +
	       	          	"	</article>" +
       					"</li>"
       			);
       		}
      	}
	});
	
	finCargaContenido();
}