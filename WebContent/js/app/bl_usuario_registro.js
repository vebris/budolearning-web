function verRegistroUsuario() {
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").toggleClass("wrapper row3");
	$("#contenido").append("<div id='contenido_div' class='hoc container clear'></div>");
	$("#contenido_div").append(	
		"<div id='headerTitle' class='typography-info'>"+
		"	<h2 class='type'>Registrar Usuario</h2> "+
		"</div>"+
		"<div style='border: 2px solid #ccc; border-radius: 4px; padding: 1em; margin: 1em; text-align:center'  >"+
		"	<div class='input-group ' style='display:none'>"+
		"		<span class='input-group-addon'>ID</span>"+
		"		<input type='text' id='id' class='form-control' placeholder='id' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<div class='input-group one_half first'>"+
		"		<span class='input-group-addon'>Login</span>"+
		"		<input type='text' id='login' class='form-control' placeholder='login' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<div class='input-group one_half'>"+
		"		<span class='input-group-addon'>Password</span>"+
		"		<input type='password' id='password' class='form-control' placeholder='password' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<div class='input-group' style='display:none'>"+
		"		<span class='input-group-addon'>Rol</span>"+
		"		<select id='rol' class='form-control' aria-describedby='basic-addon1'>"+
		"			<option value='USER'>Usuario</option>"+
		"		</select>"+
		"	</div>"+
		"	<div class='input-group' style='display:none'>"+
		"		<span class='input-group-addon'>Activo</span>"+
		"		<input type='checkbox' id='activo' class='' placeholder='activo' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<div class='input-group'>"+
		"		<span class='input-group-addon'>Entrena</span>"+
		"		<select id='entrena' class='form-control' aria-describedby='basic-addon1'>"+
		"		</select>"+
		"	</div>"+
		"	<div class='input-group one_third first'>"+
		"		<span class='input-group-addon'>Nombre</span>"+
		"		<input type='text' id='nombre' class='form-control' placeholder='nombre' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<div class='input-group one_third'>"+
		"		<span class='input-group-addon'>Primer Apellido</span>"+
		"		<input type='text' id='apellido1' class='form-control' placeholder='apellido1' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<div class='input-group one_third'>"+
		"		<span class='input-group-addon'>Segundo Apellido</span>"+
		"		<input type='text' id='apellido2' class='form-control' placeholder='apellido2' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<div class='input-group one_third first'>"+
		"		<span class='input-group-addon'>DNI</span>"+
		"		<input type='text' id='dni' class='form-control' placeholder='dni' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<div class='input-group one_third'>"+
		"		<span class='input-group-addon'>Email</span>"+
		"		<input type='text' id='mail' class='form-control' placeholder='email' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<div class='input-group one_third'>"+
		"		<span class='input-group-addon'>Tel&eacute;fono</span>"+
		"		<input type='text' id='telefono' class='form-control' placeholder='telefono' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<div class='input-group two_third first'>"+
		"		<span class='input-group-addon'>Direcci&oacute;n</span>"+
		"		<input type='text' id='direccion' class='form-control' placeholder='direccion' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<div class='input-group one_third'>"+
		"		<span class='input-group-addon'>Localidad</span>"+
		"		<input type='text' id='localidad' class='form-control' placeholder='localidad' aria-describedby='basic-addon1'>"+
		"	</div>"+
		"	<h1>"+
		"		<a href='javascript:guardarUsuario()'><span class='cls_button'>Guardar</span></a>"+
		"	</h1>"+
		"</div>"
	);
	
	$.ajax({
        type: "POST",
        url: "/rest/Club/list",
        success: function(datos){
       		for(i=0;i<datos.data.length;i++){
       			$("#entrena").append("<option value='" + datos.data[i].id + "'>" + datos.data[i].nombre + "</option>");
       		}
        }
	});
	
}

function validoRegistroUsuario(){
	var valido = true;
	if($("#login").val() == ""){
		$("#headerTitle").append("<div class='alert alert-warning'>El login es un campo obligatorio</div>");
		valido = false;
	}
	if($("#nombre").val() == ""){
		$("#headerTitle").append("<div class='alert alert-warning'>El nombre es un campo obligatorio</div>");
		valido = false;
	}
	if($("#apellido1").val() == ""){
		$("#headerTitle").append("<div class='alert alert-warning'>El primer apellido es un campo obligatorio</div>");
		valido = false;
	}
	if($("#mail").val() == ""){
		$("#headerTitle").append("<div class='alert alert-warning'>El E-mail es un campo obligatorio</div>");
		valido = false;
	}
	return valido;
}

function registrarUsuario(){
	$("#headerTitle>.alert").remove();
	if(validoRegistroUsuario()){
		var entrena = { id: $("#entrena").val() };
		var usuario = {
				id: $("#id").val(),
				login: $("#login").val(),
				password: $("#password").val().length>0?CryptoJS.MD5($("#password").val()).toString():null,
				rol: $("#rol").val(), 
				activo: $("#activo").val(),
				entrena: entrena,
				nombre: $("#nombre").val(),
				apellido1: $("#apellido1").val(),
				apellido2: $("#apellido2").val(),
				dni: $("#dni").val(),
				mail: $("#mail").val(),
				telefono: $("#telefono").val(),
				direccion: $("#direccion").val(),
				localidad: $("#localidad").val()
		};
		
		var request = {user: usuario, data: usuario};
		
		$.ajax({
	        type: "POST",
	        url: "/rest/Usuario/insert",
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