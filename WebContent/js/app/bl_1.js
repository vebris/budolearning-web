function gestionPeticion(data){
	$("#headerPuntos").html(data.puntos);
	sessionStorage.setItem("puntos",data.puntos);	
}

function inicioMenu(){
	$("#menu").toggleClass("header");
	$("#menu").append(
		"<div class='container'>" + 
		"	<div class='logo' align='center'>"+
		"		<h1>" +
		"			<a href='${pageContext.request.contextPath}/budolearning-web/index.jsp'>Budo<span>Learning</span></a>" +
		"		</h1>"+
		"	</div>"+
		"	<div class='top-nav'>"+
		"		<nav class='navbar navbar-default'>"+
		"			<div class='container'>"+
		"				<button id='menuButton' type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#bs-example-navbar-collapse-1'>Menu</button>"+
		"			</div>"+
		"			<div class='collapse navbar-collapse' id='bs-example-navbar-collapse-1'>"+
		"				<ul class='nav navbar-nav' id='menu_options'>"+
		"				</ul>	"+
		"				<div class='clearfix'> </div>"+
		"			</div>	"+
		"		</nav>		"+
		"	</div>" +
		"</div>"
	);
	cargarMenuOut();
}

function cargarMenuOut(){
	$("#menu_options").children().remove();
	$("#menu_options").append("<li><a href='javascript:verCalendario();'>Calendario</a></li>");
	$("#menu_options").append("<li><a href='javascript:mostrarLogin();'>Login</a></li>");
}

function cargarMenuIn(){
	$("#menu_options").children().remove();
	
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	
	if(usuario.rol == 'ADMINISTRADOR') {
		
	}
	if(usuario.rol == 'PROFESOR' || usuario.rol == 'ADMINISTRADOR'){
		$("#menu_options").append("<li><a href='javascript:verAlumnos();'>Alumnos</a></li>");
	}
	
	$("#menu_options").append("<li><a href='javascript:verEspeciales();'>Especiales</a></li>");
	$("#menu_options").append("<li><a href='javascript:verClubes();'>Clubes</a></li>");
	$("#menu_options").append("<li><a href='javascript:verCalendario();'>Cursos</a></li>");
	$("#menu_options").append("<li><a href='javascript:verDisciplinas();'>Vista Usuario</a></li>");
	$("#menu_options").append("<li><a href='javascript:verDatosPersonales();'>Datos Personales</a></li>");
	$("#menu_options").append("<li><a href='javascript:logout();'>Salir</a></li>");
}

function logout(){
	sessionStorage.removeItem("url");
	sessionStorage.removeItem("usuario");
	sessionStorage.removeItem("fecha");
	sessionStorage.clear();
	
	cargarMenuOut();
	mostrarLogin();
}

function borrarContenido(){
	
	if(sessionStorage.getItem("fecha") != null && ((new Date().getTime()) - (new Date(sessionStorage.getItem("fecha")).getTime()))/1000 > 60 * 60 ) {
		return false;
	}
	
	$("#menuButton").removeClass()
	$("#menuButton").addClass("navbar-toggle collapsed");
	$("#menuButton").attr("aria-expanded", false);
	
	$("#bs-example-navbar-collapse-1").removeClass("in");
	$("#bs-example-navbar-collapse-1").attr("aria-expanded", false);
	
	$("#contenido").children().remove();
	$("#contenido").removeClass();
	
	return true;
}

function formattedDate(date) {
    var month = '' + (date.getMonth() + 1),
        day = '' + date.getDate(),
        year = date.getFullYear(),
        hours = '' + date.getHours(),
        minutes = '' + date.getMinutes()
        ;

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    if (hours.length < 2) hours = '0' + hours;
    if (minutes.length < 2) minutes = '0' + minutes;

    return [day, month, year].join('/') + " " + hours + ":" + minutes;
}

function formattedDateCorto(date) {
    var month = '' + (date.getMonth() + 1),
        day = '' + date.getDate(),
        year = date.getFullYear();
        ;

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [day, month, year].join('/');
}
function formattedSize(tamano){
	return parseFloat(tamano / 1024 / 1024).toFixed(2) + " MB";
}

function formattedTime(segundos){
	var hour = '' + Math.floor(segundos / 3600),
    min = '' + Math.floor((segundos%3600)/ 60),
    seconds = '' + Math.floor(segundos % 60);
    ;

	if (hour.length < 2) hour = '0' + hour;
	if (min.length < 2) min = '0' + min;
	if (seconds.length < 2) seconds = '0' + seconds;
	
	return [hour, min, seconds].join(':');
}