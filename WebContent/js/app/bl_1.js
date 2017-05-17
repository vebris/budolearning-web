function gestionPeticion(data){
	$("#headerPuntos").html(data.puntos);
	sessionStorage.setItem("puntos",data.puntos);	
}

function logout(){
	
	sessionStorage.removeItem("url");
	sessionStorage.removeItem("usuario");
	sessionStorage.removeItem("fecha");
	sessionStorage.clear();
	
	menu(12);
	mostrarLogin();
}

function borrarContenido(disciplinas){
	
	if(sessionStorage.getItem("fecha") != null && ((new Date().getTime()) - (new Date(sessionStorage.getItem("fecha")).getTime()))/1000 > 60 * 60 ) {
		return false;
	}
	
	/*
	$("#menuButton").removeClass()
	$("#menuButton").addClass("navbar-toggle collapsed");
	$("#menuButton").attr("aria-expanded", false);
	
	$("#bs-example-navbar-collapse-1").removeClass("in");
	$("#bs-example-navbar-collapse-1").attr("aria-expanded", false);
	*/
	if(!disciplinas){
		$("#contenido").children().remove();
	}
	$("#contenido").removeClass();
	
	return true;
}

function recargarAnuncio(){
	var e = $('#anuncio iframe');
    e.attr("src", e.attr("src"));
	var c = $(e);
    c.attr("src", c.attr("src"));
}

function finCargaContenido(){
	recargarAnuncio();
	(adsbygoogle = window.adsbygoogle || []).push({});
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

var monthNames = ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Novr", "Dic"];