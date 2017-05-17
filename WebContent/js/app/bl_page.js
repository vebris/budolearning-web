function page(id){
	
	$("#contenido").children().remove();
	
	$('#contenido').removeClass('bgded'); 
	$('#contenido').removeClass('overlay');
	
	
	var request = {data: {id:id}};
	$.ajax({
        type: "POST",
        url: "/rest/Pagina/select",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	//seleccionarMenu(datos.data.id);
        	cargarContenido(datos.data.tipoPagina, datos.data.id, datos.data.parent);
        }
	});
	
	finCargaContenido();
}
function subpage(id){
	
	$("#contenido_der").remove();
	
	$('#submenu_select').val(id);
	$('[id*=subm_]').removeClass("active");
	$('#subm_'+id).addClass("active");
	
	
	var request = {data: {id:id}};
	$.ajax({
        type: "POST",
        url: "/rest/Pagina/contenido",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	cargarContenidoDer(datos);
        }
	});
	
	finCargaContenido();
}

function cargarContenido (tipo, id, parent) {
	var request = {data: {id:id}};
	$.ajax({
        type: "POST",
        url: "/rest/Pagina/contenido",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	if(tipo==1){
        		cargarContenido1(datos);
        	}
        	if(tipo==2){
        		cargarContenido2(datos);
        	}
        	if(tipo==3){
        		cargarContenido3(datos);
        	}
        	if(tipo==4){
        		if(parent == null) {
        			cargarContenido4(datos, id);
        		} else {
        			cargarContenido4(datos, parent.id, id);
        		}
        	}
        }
	});
}

function cargarContenido1 (datos){
	$("#contenido").addClass("wrapper row3");
	//$("#contenido").append("<div id='contenido_div' class='hoc container clear'></div>");
	var id=0;
	while (datos.data[id] != null) {
		mostrarParrafo(datos.data[id],'contenido');
		id++;
	}
}

function cargarContenido2 (datos){
	$("#contenido").addClass("wrapper row3");
	$("#contenido").append("<div id='contenido_div' class='hoc container clear'></div>");
	var id=0;
	while (datos.data[id] != null) {
		mostrarParrafo(datos.data[id],'contenido_div');
		id++;
	}
}

function cargarContenido3 (datos){
	$("#contenido").toggleClass("wrapper bgded overlay");
	$("#contenido").append("<div id='contenido_div' class='hoc container clear'></div>");
	var id=0;
	while (datos.data[id] != null) {
		mostrarParrafo(datos.data[id], 'contenido_div');
		id++;
	}
}

function cargarContenido4 (datos, id, this_id){
	$("#contenido").addClass("wrapper row3");
	$("#contenido").append("<div id='contenido_div' class='hoc container clear'></div>");
	
	$("#contenido_div").append("<div id='mainav2' class='sidebar one_fifth first container'>"+
							"  <nav class='sdb_holder'>" +
							"    <ul id='submenu_izq'>" +
							" 	 <h6><a href='"+window.location.hash+"' onclick='subpage("+id+")' style='border-bottom-width: 0.5em;'>MENU</a></h6>" +
							"    </ul>" +
							"  	 <form action='#'>" +
							"	  <select id='submenu_select' onchange='javascript:subpage(document.getElementById(\"submenu_select\").value)'>"+
							'		<option value="'+id+'"> SUBMENU: </option>' +
							"	  </select>"+
							"    </form>" +
							"  </nav>" +
							"</div>");
	
	var request = {data: {id: id}};
	$.ajax({
        type: "POST",
        url: "/rest/Pagina/list",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	var i=0;
        	while (datos.data[i] != null) {
        		$("#submenu_izq").append('<li id="subm_'+datos.data[i].id+'"><a href="'+window.location.hash+'" onclick="subpage('+datos.data[i].id+')">'+datos.data[i].titulo+'</a></li>');
        		$("#submenu_select").append('<option value="'+datos.data[i].id+'"> - '+datos.data[i].titulo+'</option>');
        		i++;
        	}
        }
	});
	
	cargarContenidoDer(datos);
}

function cargarContenidoDer(datos){
	$("#contenido_div").append("<div id='contenido_der' class='four_fifth container'></div>");
	var id=0;
	while (datos.data[id] != null) {
		mostrarParrafo(datos.data[id], 'contenido_der');
		id++;
	}
}

function mostrarParrafo(elemento, contenedor){
	
	if(elemento.imagen) {
		$("#"+contenedor).append('<div style="text-align: center;"><img class="imgl inspace-5" src="/rest/Pagina/contenido/downloadFile/' + elemento.id + '" alt="" style="max-width:200px;max-height:200px"></div>');
	}
	
	// CONTENIDO
	if (elemento.titulo=='Titulo') {
		var elementos = jQuery.parseJSON(elemento.texto);
		$("#"+contenedor).append('<h3 class="font-x1">' + elementos.texto + '</h3>');
	} else if (elemento.titulo=='Text') {
		var elementos = jQuery.parseJSON(elemento.texto);
		$("#"+contenedor).append('<p>'+elementos.texto+'</p>');
	} else if (elemento.titulo=='List') {
		var elementos = jQuery.parseJSON(elemento.texto);
		var txt = '<ul style="padding-left:1em">';
		for(count=0;count<elementos.length;count++){
			txt+='<li>' + elementos[count].texto + '</li>';
		}
		txt+='</ul>';
		$("#"+contenedor).append(txt);
	} else if (elemento.titulo=='Kata_M') {
		var elementos = jQuery.parseJSON(elemento.texto);
		var txt = '<div id="comments2">'+
			'<ul style="margin-bottom: 1em;">'+
			'	<li class="post radius" style="padding:1em">'+
            '		<article>'+
            '			<header>'+
            '			    <address>'+elementos.titulo+'</address>'+
            '			</header>'+
            '		  <div class="comcont">';
		for(count=0;count<elementos.parrafos.length;count++){
			txt+='<p>' + elementos.parrafos[count].texto + '</p>';
		}
		txt+='		  </div> '+
            '		</article>' +
            '	</li>' +
            '</ul>'+
			'</div>'
		;
		$("#"+contenedor).append(txt);
	} else if (elemento.titulo=='Kata_O') {
		var elementos = jQuery.parseJSON(elemento.texto);
		var txt = '<div id="comments">'+
			'<ul style="margin-bottom: 1em;">'+
			'	<li class="post radius" style="padding:1em">'+
            '		<article>'+
            '			<header>'+
            '			    <address>'+elementos.titulo+'</address>'+
            '			</header>'+
            '		  <div class="comcont">';
		for(count=0;count<elementos.parrafos.length;count++){
			txt+='<p>' + elementos.parrafos[count].texto + '</p>';
		}
		txt+='		  </div> '+
            '		</article>' +
            '	</li>' +
            '</ul>'+
			'</div>'
		;
		$("#"+contenedor).append(txt);
	} else if (elemento.titulo=='Escuela') {
		var elementos = jQuery.parseJSON(elemento.texto);
		$("#"+contenedor).append('<h1 class="heading">'+elementos.titulo+'</h1>');
		for(count=0;count<elementos.parrafos.length;count++){
			$("#"+contenedor).append('<p>' + elementos.parrafos[count].texto + '</p>');
		}
	} else if (elemento.titulo=='Article') {
		var elementos = jQuery.parseJSON(elemento.texto);
		$("#"+contenedor).append('<article id="articulo_'+elemento.id+'" class="element btmspace-30"><i class="fa '+elementos.icon+'"></i>' +
				'<h3 class="heading"><a href="javascript:void();">'+elementos.titulo+'</a></h3>' +
				'</article>'
		);
		for(count=0;count<elementos.parrafos.length;count++){
			$("#articulo_"+elemento.id).append('<p>' + elementos.parrafos[count].texto + '</p>');
		}
	} else if (elemento.titulo=='Article2') {
		var elementos = jQuery.parseJSON(elemento.texto);
		
		var numero = elementos.columnas.length;
		if(numero == 1) {
			numero = 'one_half';
		} else if (numero == 2) {
			numero = 'one_third';
		} else if (numero == 3) {
			numero = 'one_quarter';
		} else if (numero == 4) {
			numero = 'one_fifth';
			/*
			$("#"+contenedor).append('	<article class="' + numero + ' first post">' + 'Grado' + '</article>');
			$("#"+contenedor).append('	<article class="' + numero + ' post">' + 'Hojoundo' + '</article>');
			$("#"+contenedor).append('	<article class="' + numero + ' post">' + 'Kata' + '</article>');
			$("#"+contenedor).append('	<article class="' + numero + ' post">' + 'Aplicaci&oacute;n' + '</article>');
			$("#"+contenedor).append('	<article class="' + numero + ' post">' + 'Tiempo' + '</article>');
			*/
		}
		
		if($("#kyu_cabecera")[0] == null){
			$("#"+contenedor).append('<div id="kyu_cabecera" class="clear post radius font-x2 font-bold" style="margin-bottom:0.2em;"></div>');
			$("#kyu_cabecera").append('	<article class="' + numero + ' first" >GRADO</article>');
			$("#kyu_cabecera").append('	<article class="' + numero + ' " >HOJOUNDO</article>');
			$("#kyu_cabecera").append('	<article class="' + numero + ' " >KATA</article>');
			$("#kyu_cabecera").append('	<article class="' + numero + ' " >APLICACION</article>');
			$("#kyu_cabecera").append('	<article class="' + numero + ' " >TIEMPO</article>');
		}
		
		$("#"+contenedor).append('<div id="fila_'+elemento.id+'" class="clear post radius table" style="margin-bottom:0.1em;"></div>');
		$("#fila_"+elemento.id).append('	<article class="' + numero + ' first" >' +
				'		<div id="kyu_cabecera_min">GRADO: '+elementos.titulo+'</div>'+
				'		<div id="kyu_cabecera">'+elementos.titulo+'</div>'+
				'	</article>'
		);
		for(count=0;count<elementos.columnas.length;count++){
			if(count==0){text='HOJOUNDO';} else if(count==1){text='KATA'; }else if(count==2){text='APLICACION';}else if(count==3){text='TIEMPO';}
			$("#fila_"+elemento.id).append('	<article class="' + numero + '" >' +
					'		<div id="kyu_cabecera_min"> '+ text+': '+elementos.columnas[count].texto+'</div>'+
					'		<div id="kyu_cabecera">'+elementos.columnas[count].texto+'</div>'+
					'	</article>'
			);
		}
	} else if (elemento.titulo=='Article3') {
		var elementos = jQuery.parseJSON(elemento.texto);
		
		var numero = elementos.columnas.length;
		if(numero == 1) {
			numero = 'one_half';
		} else if (numero == 2) {
			numero = 'one_third';
		} else if (numero == 3) {
			numero = 'one_quarter';
		} else if (numero == 4) {
			numero = 'one_fifth';
		}
		
		if($("#dan_cabecera")[0] == null){
			$("#"+contenedor).append('<div id="dan_cabecera" class="clear post radius font-x2 font-bold" style="margin-bottom:0.2em;"></div>');
			$("#dan_cabecera").append('	<article class="' + numero + ' first" >GRADO</article>');
			$("#dan_cabecera").append('	<article class="' + numero + ' " >HOJOUNDO</article>');
			$("#dan_cabecera").append('	<article class="' + numero + ' " >KATA</article>');
			$("#dan_cabecera").append('	<article class="' + numero + ' " >APLICACION</article>');
			$("#dan_cabecera").append('	<article class="' + numero + ' " >TIEMPO</article>');
		}
		
		$("#"+contenedor).append('<div id="fila_'+elemento.id+'" class="clear post radius table" style="margin-bottom:0.1em;"></div>');
		$("#fila_"+elemento.id).append('	<article class="' + numero + ' first" >' +
				'		<div id="dan_cabecera_min">GRADO: '+elementos.titulo+'</div>'+
				'		<div id="dan_cabecera">'+elementos.titulo+'</div>'+
				'	</article>'
		);
		for(count=0;count<elementos.columnas.length;count++){
			if(count==0){text='HOJOUNDO';} else if(count==1){text='KATA'; }else if(count==2){text='APLICACION';}else if(count==3){text='TIEMPO';}
			$("#fila_"+elemento.id).append('	<article class="' + numero + '" >' +
					'		<div id="dan_cabecera_min" style="font-size: small;"> '+ text+': '+elementos.columnas[count].texto+'</div>'+
					'		<div id="dan_cabecera" style="font-size: small;">'+elementos.columnas[count].texto+'</div>'+
					'	</article>'
			);
		}
				
	} else {
		$("#"+contenedor).append('<p>'+elemento.texto+'</p>');
	}
	
	if(elemento.imagen && elemento.titulo != 'Titulo') {
		$("#"+contenedor).append('<div class="clear"></div>');
	}
}