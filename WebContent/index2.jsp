<!DOCTYPE html>
<html>
	<head>
		
		<title>Okinawa Kobudo: Matayoshi Ryu, Ryukyu Kobudo y Yamanni Ryu</title>
		
		<meta charset="UTF-8">
		<meta name="description" content="Estudio de Maestros, Escuelas, Armas y Katas de Okinawa Kobudo en particular Matayoshi Kobudo, Ryukyu Kobudo y Yamanni Ryu." />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name="google" content="nositelinkssearchbox">
		<meta name="subject" content="Okinawa Kobudo">
		<meta name="Language" content="ES">
		<meta name="Revisit-after" content="15">

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/rollups/md5.js"></script>
		
		<link href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" type="text/css" media="all">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.backtotop.js"></script>    
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/modernizr.js"></script>
		<script type="text/javascript" src=".${pageContext.request.contextPath}/js/responsee.js"></script>
		
		<link href="http://jplayer.org/latest/dist/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css" />
 		<script type="text/javascript" src="http://jplayer.org/latest/dist/jplayer/jquery.jplayer.min.js"></script>
		
		<!--[if lt IE 9]>
		  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		  <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
		<![endif]-->
	</head>   
	<body id="top" >
		 
	   	<div class="wrapper row3">
			<div id="contenido">
			</div>
		</div>
		
   		<script src="${pageContext.request.contextPath}/js/app/bl_1.js"></script>
   		<script src="${pageContext.request.contextPath}/js/app/bl_menu.js"></script>
   		<script src="${pageContext.request.contextPath}/js/app/bl_login.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_disciplinas.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_grados.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_recursos.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_ficheros.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_video.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_usuario_registro.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_usuario_resetPass.js"></script>
		
   		
   		<script>
	   		$.urlParam = function(name){
	   		    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	   		    if (results==null){
	   		       return null;
	   		    }
	   		    else{
	   		       return results[1] || 0;
	   		    }
	   		}
   		
   			$(function(){
   				if(window.location.hash == ''){
   					//page(1);
   					mostrarLogin();
   				}
			});
   		</script>
   	
		<a id="backtotop" href="#top"><i class="fa fa-chevron-up"></i></a>
	</body>
	
	<style>
		body{
			background-color: transparent;
		}
	</style>
</html>