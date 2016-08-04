function irAPagina(element){
	
	if(element == '#') return;
	if(element.indexOf(')')>0) eval(element);
	if(element.indexOf(')')<0) page(element);

	window.location.hash = $("#menu_select option:selected").attr("name");
	$('[id*=menu_]').removeClass("active");
	$('#menu_'+$("#menu_select option:selected").attr("id").substr(7)).addClass("active");
	$('#menu_'+$("#menu_select option:selected").attr("id").substr(7)).parent().parent().addClass("active");
}

function seleccionarMenu(id){
	$('[id*=menu_]').removeClass("active");
	$('#menu_'+id).addClass("active");
	$('#menu_'+id).parent().parent().addClass("active");
	
	$("#menu_select").val($("#option_"+id).val());
}

function menu(idMenu){
	
	$("#menu").children().remove();
	
	var request = {};
	$.ajax({
        type: "POST",
        url: "/rest/Menu/list",
        dataType: 'json',
        contentType : "application/json",
        data: JSON.stringify(request),
        success: function(datos){
        	$("#menu").append(
        			'<nav id="mainav" class="hoc clear">'+
        			'<ul id="menu_ul" class="clear">'+
        			'</ul>'+
        			'</nav>'
        			);
        	$("#mainav").append(
        			'<form action="#">' +
        			'	<select id="menu_select" onchange="irAPagina(document.getElementById(\'menu_select\').value)">'+
        			'	</select>'+
        			'</form>');
        	$("#menu_select").append('<option value="page(1);seleccionarMenu(0);"> MENU </option>');
        	
        	var id=0;
        	while (datos.data[id] != null) {
        		var cargar = false;
        		
        		if(JSON.parse(sessionStorage.getItem("usuario")) == null){
        			if(datos.data[id].logado == 0){
        				cargar = true;
        			}
        		} else {
        			if(datos.data[id].logado == 1){
        				cargar = true;
        			}
        		}
        		
        		if(datos.data[id].logado == 2) cargar = true;
        		
        		if(cargar) {
        			if(datos.data[id].parent != null){
	        			if($("#menu_ul_"+datos.data[id].parent.id) != null){
	        				$("#menu_"+datos.data[id].parent.id+">a").addClass("drop"); 
	        				$("#menu_"+datos.data[id].parent.id).append(
	        						'<ul id="menu_ul_'+datos.data[id].parent.id+'" class="clear">'+
	        						'</ul>');
	        			}
	        			
	        			if(datos.data[id].pagina != null){
	        				$("#menu_ul_"+datos.data[id].parent.id).append('<li id="menu_'+datos.data[id].id+'"><a href="#!'+datos.data[id].titulo+'" onclick="page('+datos.data[id].pagina.id+');seleccionarMenu('+datos.data[id].id+');">'+datos.data[id].titulo+'</a></li>');
	        				$("#menu_select").append('<option id="option_'+datos.data[id].id+'" value="'+datos.data[id].pagina.id+'" name="#!'+datos.data[id].titulo+'"> - '+datos.data[id].titulo+'</option>');
	        			} else {
	        				$("#menu_ul_"+datos.data[id].parent.id).append('<li id="menu_'+datos.data[id].id+'"><a href="#">'+datos.data[id].titulo+'</a></li>');
	        				$("#menu_select").append('<option id="option_'+datos.data[id].id+'" value="#" > - '+datos.data[id].titulo+'</option>');
	        			}
	        			
	        		} else {
	        			if(datos.data[id].pagina && datos.data[id].pagina.tipoPagina == 5) {
	        				$("#menu_ul").append('<li id="menu_'+datos.data[id].id+'"><a href="#!'+datos.data[id].titulo+'" onclick="'+datos.data[id].pagina.titulo+';seleccionarMenu('+datos.data[id].id+');">'+datos.data[id].titulo+'</a></li>');
		        			$("#menu_select").append('<option id="option_'+datos.data[id].id+'" value="'+datos.data[id].pagina.titulo+'" name="#!'+datos.data[id].titulo+'">'+datos.data[id].titulo+'</option>');
	        			} else {
		        			if(datos.data[id].pagina != null){
			        			$("#menu_ul").append('<li id="menu_'+datos.data[id].id+'"><a href="#!'+datos.data[id].titulo+'" onclick="page('+datos.data[id].pagina.id+');seleccionarMenu('+datos.data[id].id+');">'+datos.data[id].titulo+'</a></li>');
			        			$("#menu_select").append('<option id="option_'+datos.data[id].id+'" value="'+datos.data[id].id+'" name="#!'+datos.data[id].titulo+'">'+datos.data[id].titulo+'</option>');
		        			} else {
		        				$("#menu_ul").append('<li id="menu_'+datos.data[id].id+'"><a href="#">'+datos.data[id].titulo+'</a></li>');
			        			$("#menu_select").append('<option id="option_'+datos.data[id].id+'" value="#" >'+datos.data[id].titulo+'</option>');
		        			}
	        			}
	        		}
        		}
        		id++;
        	}
        	
        	if(window.location.hash != ""){
        		$('[href*='+window.location.hash.replace('!','\\!').replace(' ','\\ ')+']').click();
        	}
        	
        	if(idMenu != null) {
        		seleccionarMenu(idMenu);
        		window.location.hash = $('#menu_'+idMenu+'>a').attr('href');
        	}
        }
	});
}