function mostrarLogin() {
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").toggleClass("wrapper row3");
	$("#contenido").append("<div id='contenido_div' class='hoc container clear'></div>");
	$("#contenido_div").append(
		"<div class='typography-info'>"+
		"	<h2 class='type'>Login</h2>" + 
		"</div>"+
		"<div class='one_fifth first'>&nbsp;</div>"+
		"<div class='three_fifth' style='text-align:center'>"+
		"	<div style='border: 2px solid #ccc; border-radius: 4px; padding: 1em; margin: 1em;'>" +
		"		<div class='input-group'>" +
		"			<span class='input-group-addon'>Login</span>"+
		"			<input type='text' id='login' class='form-control' placeholder='login' aria-describedby='basic-addon1'>"+
		"		</div>" +
		"		<div class='input-group'>" +
		"			<span class='input-group-addon'>Password</span>"+
		"			<input type='password' id='password' class='form-control' placeholder='password' aria-describedby='basic-addon1'>"+
		"		</div>"+
		"		<h1>"+
		"			<a href='#' onclick='login()'><span class='cls_button'>&nbsp;&nbsp;&nbsp;&nbsp;Entrar&nbsp;&nbsp;&nbsp;&nbsp;</span></a>"+
		"		</h1>" +
		"		<div>"+
		"			<a href='#' onclick='verOlvidoContrasena();' class='cls_button_2' style='font-size:small'>&iquest;Olvid&oacute; su contrase&ntilde;a?</a>"+
		"			<a href='#' onclick='verRegistroUsuario();' class='cls_button_2' class='cls_button_2' style='font-size:small'>Registrarse</a>"+
		"		</div>" +
		"	</div>"+
		"	<h4 >"+
		"		<a href='http://budolearning.noip.me/rest/UtilesService/descargarUltimaVersion' target='_blank'>"+
		"		  <img width='24' height='24' src='img/android.png' style='height:24'/> Aplicaci&oacute;n Android"+
		"		</a>"+
		"	</h4>"+
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
	       			menu(15);
	       			verDisciplinas();
	       		} else {
	       			window.location.hash='#!Login';
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