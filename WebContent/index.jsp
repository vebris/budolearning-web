<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width" />
		<title>BudoLearning</title>      
		<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
		<!-- css -->
		<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel='stylesheet' type='text/css' />
		<link href="${pageContext.request.contextPath}/css/style.css" rel='stylesheet' type='text/css' />
		
		
		<!-- Javascript -->
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"> </script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<link href="${pageContext.request.contextPath}/css/jquery-ui.min.css" rel='stylesheet' type='text/css' />
		<link href="${pageContext.request.contextPath}/css/jquery-ui.structure.min.css" rel='stylesheet' type='text/css' />
		<link href="${pageContext.request.contextPath}/css/jquery-ui.theme.min.css" rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
		
			
		<script src="/js/bootstrap.js"></script>
		<script type="text/javascript">
			jQuery(document).ready(function($) {
				$(".scroll").click(function(event){		
					event.preventDefault();
					$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
				});
			});
		</script>	
		
		<link href="http://jplayer.org/latest/dist/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css" />
 		<script type="text/javascript" src="http://jplayer.org/latest/dist/jplayer/jquery.jplayer.min.js"></script>
		
		<script src="http://crypto-js.googlecode.com/svn/tags/3.0.2/build/rollups/md5.js"></script>
		
	</head>   
	<body onload="inicioMenu();verCalendario();">
	
	   	<div id="menu">
	   	</div>
	
		<div id="contenido">
		</div>
   		
   		<script src="${pageContext.request.contextPath}/js/app/bl_1.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_calendario.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_login.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_disciplinas.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_grados.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_recursos.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_ficheros.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_video.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_usuario_datos.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_usuario_registro.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_usuario_resetPass.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_especiales.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_alumnos.js"></script>
		<script src="${pageContext.request.contextPath}/js/app/bl_clubes.js"></script>
		
		<script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		
		  ga('create', 'UA-74068696-1', 'auto');
		  ga('send', 'pageview');
		</script>
	</body>
</html>