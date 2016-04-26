function mostrarLogin() {
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").toggleClass("row rowAuto");
	$("#contenido").append(
		"<div class='typography-info'>"+
		"	<h2 class='type'>Login</h2>" + 
		"</div>"+
		"<div class='row rowAuto'>"+
		"	<div class='col-lg-4'></div>"+
		"	<div class='col-lg-4'>"+
		"		<div class='input-group'>" +
		"			<span class='input-group-addon'>Login</span>"+
		"			<input type='text' id='login' class='form-control' placeholder='login' aria-describedby='basic-addon1'>"+
		"		</div>" +	
		"	</div>" +
		"</div>"+
		"<div class='row rowAuto'>"+
		"	<div class='col-lg-4'></div>"+
		"	<div class='col-lg-4'>"+
		"		<div class='input-group'>" +
		"			<span class='input-group-addon'>Password</span>"+
		"			<input type='password' id='password' class='form-control' placeholder='password' aria-describedby='basic-addon1'>"+
		"		</div>"+
		"	</div>"+
		"</div>"+
		"<div class='row rowAuto'>"+
		"	<h1 align='center'>"+
		"		<a href='javascript:login()'><span class='label label-default'>&nbsp;&nbsp;&nbsp;&nbsp;Entrar&nbsp;&nbsp;&nbsp;&nbsp;</span></a>"+
		"	</h1>"+
		"</div>"+
		"<div class='row rowAuto'></div>"+
		"<div class='row rowAuto'></div>"+
		"<div class='row rowAuto'>"+
		"	<h4 align='center'>"+
		"		<a href='http://budolearning.noip.me/rest/UtilesService/descargarUltimaVersion' target='_blank'>"+
		"		  <img width='32' height='32' src='img/android.png'/> Aplicaci&oacute;n Android"+
		"		</a>"+
		"	</h4>"+
		"</div>"+
		"<div class='row rowAuto'></div>"+
		"<div class='row rowAuto' align='center'>"+
		"	<div class='typography-info'>"+
		"		<a href='javascript:verOlvidoContrasena();'>&iquest;Olvid&oacute; su contrase&ntilde;a?</a>"+
		"	</div>"+
		"</div>"+
		"<div class='row rowAuto' align='center'>"+
		"	<div class='typography-info'>"+
		"		<a href='javascript:verRegistroUsuario()'>Registrarse</a>"+
		"	</div>"+
		"</div>"
	);	
}

function validoLogin(){
	var valido = true;
	if($("#login").val() == ""){
		$("#headerTitle").append("<div class='alert alert-warning'>El login es un campo obligatorio</div>");
		valido = false;
	}
	if($("#password").val() == ""){
		$("#headerTitle").append("<div class='alert alert-warning'>La contraseña es un campo obligatorio</div>");
		valido = false;
	}
	return valido;
}


function login(){
	$("#headerTitle>.alert").remove();
	if(validoLogin()){
		var usuario = {
				login: $("#login").val(),
				password: CryptoJS.MD5($("#password").val()).toString(),
				vesrion: 0
		};
		var request = {data: usuario};
		
		$.ajax({
	        type: "POST",
	        url: "/rest/Usuario/login",
	        dataType: 'json',
	        contentType : "application/json",
	        data: JSON.stringify(request),
	        success: function(datos){
	        	gestionPeticion(datos);
	        	
	       		if(datos.success){
	       			sessionStorage.setItem("url", 'http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}');
	       			sessionStorage.setItem("usuario", JSON.stringify(datos.data));
	       			sessionStorage.setItem("fecha", new Date());
	       			cargarMenuIn();
	       			verDisciplinas();
	       		} else {
	       			$("#headerTitle").append("<div class='alert alert-danger'>" + datos.msg + "</div>");
	       			$(window).scrollTop(0);
	       		}
	      	}
		});
	} else {
		$(window).scrollTop(0);
	}
}	

function mostrarOlvidoContrasena() {
	if(!borrarContenido()) {logout(); return;}
}

function mostrarRegistrarse() {
	if(!borrarContenido()) {logout(); return;}
}