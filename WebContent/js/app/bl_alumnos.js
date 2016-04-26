function verAlumnos() {
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").toggleClass("bs-docs-example");
	$("#contenido").append(
		"<ol id='migas' class='breadcrumb'>"+
		"	<li id='atras'></li>"+
		"</ol>"+
		"<div class='banner-grids' style='text-align: center;'>"+
		"	CLUBES: <select id='clubes'/>" + 
		"</div> " +
		"<div class='banner-grids'>"+
		"	<div id='alumnos' class='container'>"+
		"	</div> "+
		"</div> "
	);
	
	$("#atras").append("<a href='javascript:verAlumnos()'>ATRAS</a>");
	
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	
	var user = {id: usuario.id};
	var request = {user: user};
	$.ajax({
        type: "POST",
        url: "/rest/Club/list",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	gestionPeticion(datos);
        	
        	var clubes = datos.data;
        	var seleccionado = false;
        	for(i=0;i<clubes.length;i++){
        		if(clubes[i].id==usuario.profesor.id){
        			$("#clubes").append("<option id='c_"+clubes[i].id+"' value='"+clubes[i].id+"' selected>"+clubes[i].nombre+"</option>");
        			seleccionado = true;
        		} else {
        			$("#clubes").append("<option id='c_"+clubes[i].id+"' value='"+clubes[i].id+"'>"+clubes[i].nombre+"</option>");
        		}
        	}
    
        	if(seleccionado) {
        		$("#clubes").prop('disabled', true);
        	} else {
        		if(JSON.parse(sessionStorage.getItem("clubId")) != null){
        			$("#c_"+JSON.parse(sessionStorage.getItem("clubId"))).attr("selected","selected");
        		}
        	}
        	
        	$("#clubes").change(function() {
        		sessionStorage.setItem("clubId", $("#clubes>option:selected").val());
    			mostrarAlumnos();
    		});
        	
        	sessionStorage.setItem("clubId", $("#clubes>option:selected").val());
        	mostrarAlumnos();
        }
	});
}

function mostrarAlumnos(){
	$("#alumnos").children().remove();
	var request = {user: {id: JSON.parse(sessionStorage.getItem("usuario")).id} , club: {id: JSON.parse(sessionStorage.getItem("clubId"))}};
	$.ajax({
        type: "POST",
        url: "/rest/Usuario/list",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	gestionPeticion(datos);
        	
        	var alumnos = datos.data;
        	sessionStorage.setItem("fecha", new Date());
        	sessionStorage.setItem("alumnos", JSON.stringify(alumnos));
        	
        	for(var i=0;i<alumnos.length;i++){
				$("#alumnos").append(
				"<div id='alumno_"+alumnos[i].id+"' class='col-md-12 banner-grid'> " + 
				"	<div class='banner-left-grid blue'> " + 
				"		<div class='banner-grids'> "+
				"			<div class='container'> "+
				"				<div class='col-md-8 banner-grid' style='text-align:left;color:#FFF'> " +
				"					<p>" + alumnos[i].nombre + " " + alumnos[i].apellido1 + " " + alumnos[i].apellido2 + "</p> " +
				"					<p>" + alumnos[i].mail + "</p>" +
				"					<p>" + alumnos[i].telefono + "</p> " +
				"				</div>" +
				"				<div class='col-md-2 banner-grid' style='text-align:right;color:#FFF'> " +
				"					<p>" + (alumnos[i].activo?'ACTIVO':'INACTIVO') + (alumnos[i].version>0?" (V- " + (alumnos[i].version/10) + ")":"")+"</p> " +
				"					<p><span class='badge badge-warning' style='font-size:1.5em'>" + alumnos[i].puntos+"</span></p> " +
				"				</div> " +
				"			</div> " +
				"		</div> " + 
				"		<div class='clearfix'> </div> " + 
				"	</div> " + 
				"</div> " 
				);
				
				$("#alumno_"+alumnos[i].id).on( "click", function() {
					sessionStorage.setItem("alumnoId", this.id.substring(7));
					verDetalleAlumno();
				});
			}
      	}
	});
}

function verDetalleAlumno(){
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").toggleClass("bs-docs-example");
	$("#contenido").append(	
		"<div class='banner-grids' style='text-align: center;'>"+
		"	<a href='javascript:verAlumnos()'>VOLVER A ALUMNOS </a>" + 
		"</div> " +
		"	<div id='sms' class='typography-info'>"+
		"		<h2 class='type'></h2> "+
		"	</div>"+
		"<div id='tabs'> " +
			"<ul>" +
		    "	<li><a href='#tabs-1'>Datos Personales</a></li>"+
		    "	<li><a href='#tabs-2'>Disciplias/Grados</a></li>"+
		    "</ul>"+
		    "<div id='tabs-1' style='border: 1px;border-style: solid;'>" +
			"	<div class='row rowAuto'  style='display:none'>"+
			"		<div class='col-lg-6'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>ID</span>"+
			"				<input type='text' id='id' class='form-control' placeholder='id' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"	<div class='row rowAuto' style='display:none'>"+
			"		<div class='col-lg-6'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>Login</span>"+
			"				<input type='text' id='login' class='form-control' placeholder='login' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"		<div class='col-lg-6'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>Password</span>"+
			"				<input type='password' id='password' class='form-control' placeholder='password' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"	<div class='row rowAuto' style='display:none'>"+
			"		<div class='col-lg-6'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>Rol</span>"+
			"				<select id='rol' class='form-control' aria-describedby='basic-addon1'>"+
			"					<option value='USER'>Usuario</option>"+
			"				</select>"+
			"			</div>"+
			"		</div>"+
			"		<div class='col-lg-6'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>Activo</span>"+
			"				<input type='checkbox' id='activo' class='' placeholder='activo' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"	<div class='row rowAuto'>"+
			"		<div class='col-lg-12'>"+
			"			<div class='input-group rowAuto'>"+
			"				<span class='input-group-addon'>Entrena</span>"+
			"				<select id='entrena' class='form-control' aria-describedby='basic-addon1'>"+
			"				</select>"+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"	<div class='row rowAuto'>"+
			"		<div class='col-lg-4'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>Nombre</span>"+
			"				<input type='text' id='nombre' class='form-control' placeholder='nombre' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"		<div class='col-lg-4'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>Primer Apellido</span>"+
			"				<input type='text' id='apellido1' class='form-control' placeholder='apellido1' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"		<div class='col-lg-4'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>Segundo Apellido</span>"+
			"				<input type='text' id='apellido2' class='form-control' placeholder='apellido2' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"	<div class='row rowAuto'>"+
			"		<div class='col-lg-4'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>DNI</span>"+
			"				<input type='text' id='dni' class='form-control' placeholder='dni' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"		<div class='col-lg-4'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>Email</span>"+
			"				<input type='text' id='mail' class='form-control' placeholder='email' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"		<div class='col-lg-4'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>Tel&eacute;fono</span>"+
			"				<input type='text' id='telefono' class='form-control' placeholder='telefono' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"	<div class='row rowAuto'>"+
			"		<div class='col-lg-8'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>Direcci&oacute;n</span>"+
			"				<input type='text' id='direccion' class='form-control' placeholder='direccion' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"		<div class='col-lg-4'>"+
			"			<div class='input-group'>"+
			"				<span class='input-group-addon'>Localidad</span>"+
			"				<input type='text' id='localidad' class='form-control' placeholder='localidad' aria-describedby='basic-addon1'>"+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"	<div class='typography-info'>"+
			"		<h1>"+
			"			<a href='javascript:guardarAlumno()'><span class='label label-default'>Guardar</span></a>"+
			"		</h1>"+
			"	</div>" +
			"</div>"+
			"<div id='tabs-2' style='border: 1px;border-style: solid;'>" +
			"</div>" + 
			"<div id='dialog'></div>"+
		"</div>"
	);
	
	$( "#tabs" ).tabs();
	
	$(".ui-tabs.ui-widget-content").removeClass("ui-widget-content");
	
	var request = {user: {id: JSON.parse(sessionStorage.getItem("usuario")).id}};
	$.ajax({
        type: "POST",
        url: "/rest/Club/list",
        success: function(datos){
        	gestionPeticion(datos);
        	
       		for(i=0;i<datos.data.length;i++){
       			$("#entrena").append("<option value='" + datos.data[i].id + "'>" + datos.data[i].nombre + "</option>");
       		}
       		
       		refrescarTabs();
       		
      	}
	});	
}

function refrescarTabs(){
	var request = {data: {id: JSON.parse(sessionStorage.getItem("alumnoId"))}};
	$.ajax({
        type: "POST",
        url: "/rest/Usuario/select",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	gestionPeticion(datos);
        	
        	var alumno = datos.data;
        	$("#id").val(alumno.id);
        	$("#login").val(alumno.login);
        	$("#password").val("");
        	$("#rol").val(alumno.rol);
        	$("#entrena").val(alumno.entrena.id);
        	$("#activo").val(alumno.activo);
        	$("#nombre").val(alumno.nombre);
        	$("#apellido1").val(alumno.apellido1);
        	$("#apellido2").val(alumno.apellido2);
        	$("#dni").val(alumno.dni);
        	$("#mail").val(alumno.mail);
        	$("#telefono").val(alumno.telefono);
        	$("#direccion").val(alumno.direccion);
        	$("#localidad").val(alumno.localidad);
        	
        	cargarGrados(alumno);
      	}
	});
}

function cargarGrados(alumno){
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	var disciplinas = usuario.disciplinas;
	
	$("#tabs-2").children().remove();
	for(i=0;i<disciplinas.length;i++){
		var disciplinasAlumno = alumno.disciplinas;
		var gradoId = 1;
		var gradoNombre = "BLANCO";

		for(j=0;j<disciplinasAlumno.length;j++){
			if(disciplinas[i].id == disciplinasAlumno[j].id){
				var grados = disciplinasAlumno[j].grados;
				if(grados[0] != null) {
					gradoId = grados[0].id;
					gradoNombre = grados[0].nombre;
				}
			}
		}
				
		$("#tabs-2").append(
				"<div id='disciplina_"+disciplinas[i].id+"_"+gradoId+"' class='col-md-3 banner-grid'> " + 
				"	<div class='banner-left-grid blue'> " + 
				"		<div class='banner-grid-info'> " +
				"			<img src='/rest/Disciplina/downloadFile/" + usuario.id + "/"+disciplinas[i].id +"' style='max-height: 100px;max-width: 100px;'>" +
				"			<p id='nombreDisciplina_"+disciplinas[i].id+"_"+gradoId+"'>"+disciplinas[i].nombre+ " <br/>" + gradoNombre + "</p> " + 
				"		</div> " + 
				"		<div class='clearfix'> </div> " + 
				"	</div> " + 
				"</div> "
		);
		
		$("#disciplina_"+disciplinas[i].id+"_"+gradoId).on( "click", function() {
			var id = this.id.substring(11);
			$("#dialog").removeAttr("title");
			$("#dialog").children().remove();
			$("#dialog").attr("title","SUBIR GRADO");
			$("#dialog").append("<p>&iquest;Est&aacute; seguro de subir de grado?</p>");
			$("#dialog").dialog({
				resizable: false,
				modal: true,
				buttons: {
		          "Subir": function() {
		  			var disciplina = id.substring(0,id.indexOf("_"));
		  			var grado = id.substring(id.indexOf("_")+1);
		  			
		  			var request = {
		  					user: {id: JSON.parse(sessionStorage.getItem("usuario")).id}, 
		  					data: {id: JSON.parse(sessionStorage.getItem("alumnoId"))},
		  					disciplina: {id: disciplina},
		  					grado: {id: grado}
		  			};
		  			$.ajax({
		  		        type: "POST",
		  		        url: "/rest/Usuario/subirGrado",
		  		        dataType: 'json',
		  		        contentType : "application/json",
		  		        data: JSON.stringify(request),
		  		        success: function(datos){
		  		        	gestionPeticion(datos);
		  		        	
		  		        	$("#sms>.alert").remove();
		  		        	if(datos.success){
		  		       			$("#sms").append("<div class='alert alert-success'>" + datos.msg + "</div>");
		  		       		} else {
		  		       			$("#sms").append("<div class='alert alert-danger'>" + datos.msg + "</div>");
		  		       		}
		  		       		$(window).scrollTop(0);
		  		        	refrescarTabs();
		  		        	
		  		        }
		  			});
		            $( this ).dialog( "close" );
		          },
		          "Cancelar": function() {
		            $( this ).dialog( "close" );
		          }
		        }
			});
			$(".ui-dialog-buttonset>button").css("color","#000000");
		});
	}

}

function validoDatosAlumno(){
	var valido = true;
	if($("#login").val() == ""){
		$("#sms").append("<div class='alert alert-warning'>El login es un campo obligatorio</div>");
		valido = false;
	}
	if($("#nombre").val() == ""){
		$("#sms").append("<div class='alert alert-warning'>El nombre es un campo obligatorio</div>");
		valido = false;
	}
	if($("#apellido1").val() == ""){
		$("#sms").append("<div class='alert alert-warning'>El primer apellido es un campo obligatorio</div>");
		valido = false;
	}
	if($("#mail").val() == ""){
		$("#sms").append("<div class='alert alert-warning'>El E-mail es un campo obligatorio</div>");
		valido = false;
	}
	return valido;
}

function guardarAlumno(){
	$("#sms>.alert").remove();
	if(validoDatosAlumno()){
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
	        url: "/rest/Usuario/update",
	        dataType: 'json',
	        contentType : "application/json",
	        data: JSON.stringify(request),
	        success: function(datos){
	        	gestionPeticion(datos);
	        	
	       		if(datos.success){
	       			// Mensaje
	       			$("#sms").append("<div class='alert alert-success'>" + datos.msg + "</div>");
	       		} else {
	       			$("#sms").append("<div class='alert alert-danger'>" + datos.msg + "</div>");
	       		}
	       		$(window).scrollTop(0);
	      	}
		});
	} else {
		$(window).scrollTop(0);
	}
	
}