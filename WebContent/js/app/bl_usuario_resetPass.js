function verOlvidoContrasena() {
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").append(	
		"<div id='headerTitle' class='typography-info'>"+
		"	<h2 class='type'>Generar Contrase&ntilde;a</h2> "+
		"</div>"+
		"<div class='row rowAuto'>"+
		"	<div class='col-lg-4'></div>"+
		"	<div class='col-lg-4'>"+
		"		<div class='input-group'>"+
		"			<span class='input-group-addon'>Email</span>"+
		"			<input type='text' id='mail' class='form-control' placeholder='email' aria-describedby='basic-addon1'>"+
		"		</div>"+
		"	</div>"+
		"</div>"+
		"<div class='typography-info'>"+
		"	<h1>"+
		"		<a href='javascript:resetearContrasena()'><span class='label label-default'>Resetear Contrase&ntilde;a</span></a>"+
		"	</h1>"+
		"</div>"
	);
	
}

function validoRegistroUsuario(){
	var valido = true;
	if($("#email").val() == ""){
		$("#headerTitle").append("<div class='alert alert-warning'>Debe rellenar el campo 'email'.</div>");
		valido = false;
	}
	return valido;
}

function resetearContrasena(){
	$("#headerTitle>.alert").remove();
	if(validoRegistroUsuario()){
		var entrena = { id: $("#entrena").val() };
		var usuario = {
				mail: $("#mail").val()
		};
		
		var request = {user: usuario, data: usuario};
		
		$.ajax({
	        type: "POST",
	        url: "/rest/Usuario/cambioContrasena",
	        dataType: 'json',
	        contentType : "application/json",
	        data: JSON.stringify(request),
	        success: function(datos){
	       		if(datos.success){
	       			// Mensaje
	       			$("#headerTitle").append("<div class='alert alert-success'>" + datos.msg + "</div>");
	       		} else {
	       			$("#headerTitle").append("<div class='alert alert-danger'>" + datos.msg + "</div>");
	       		}
	       		$(window).scrollTop(0);
	      	}
		});
	} else {
		$(window).scrollTop(0);
	}
}