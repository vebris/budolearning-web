function verVideoEspecial() {
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").append(
			"<ol id='migas' class='breadcrumb'>"+
			"	<li id='xx'></li>" +
			"</ol>"
		);
	$("#xx").append("<a href='javascript:verEspeciales()'>ATRAS</a>");
	
	/*
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	var URL = 'http://www.budolearning.es/downloadNew?id='+sessionStorage.getItem("ficheroId")+'&idUser='+usuario.id+'&extension=.mp4';
	
	$("#contenido").append(
			"<div class='container videoContainer'>"+
			"	<video autoplay controls>" +
			"   	 <source src='"+ URL +"' type='video/mp4; codecs='avc1.42E01E, mp4a.40.2'>" +
			"    	Video not supported." +
			"	</video>" +
			"</div>"
	);
	if(screen.width < 800){
		$(".videoContainer video").css("width", "100%");
		$(".videoContainer video").css("height","auto");
	} else {
		$(".videoContainer video").css("width", "800px");
		$(".videoContainer video").css("height","auto");
	}
	*/
	
	$("#contenido").append(
			"<div class='container videoContainer'>"+
			"	<div id='jp_container_1' class='jp-video' role='application' aria-label='media player'>"+
			"		<div class='jp-type-single'>"+
			"			<div id='jquery_jplayer_1' class='jp-jplayer'></div>"+
			"			<div class='jp-gui'>"+
			"				<div class='jp-video-play'>"+
			"					<button class='jp-video-play-icon' role='button' tabindex='0'>play</button>"+
			"				</div>"+
			"				<div class='jp-interface'>"+
			"					<div class='jp-progress'>"+
			"						<div class='jp-seek-bar'>"+
			"							<div class='jp-play-bar'></div>"+
			"						</div>"+
			"					</div>"+
			"					<div class='jp-current-time' role='timer' aria-label='time'>&nbsp;</div>"+
			"					<div class='jp-duration' role='timer' aria-label='duration'>&nbsp;</div>"+
			"					<div class='jp-details'>"+
			"						<div class='jp-title' aria-label='title'>&nbsp;</div>"+
			"					</div>"+
			"					<div class='jp-controls-holder'>"+
			"						<div class='jp-volume-controls'>"+
			"							<button class='jp-mute' role='button' tabindex='0'>mute</button>"+
			"							<button class='jp-volume-max' role='button' tabindex='0'>max volume</button>"+
			"							<div class='jp-volume-bar'>"+
			"								<div class='jp-volume-bar-value'></div>"+
			"							</div>"+
			"						</div>"+
			"						<div class='jp-controls'>"+
			"							<button class='jp-play' role='button' tabindex='0'>play</button>"+
			"							<button class='jp-stop' role='button' tabindex='0'>stop</button>"+
			"						</div>"+
			"						<div class='jp-toggles'>"+
			"							<button class='jp-full-screen' role='button' tabindex='0'>full screen</button>"+
			"						</div>"+
			"					</div>"+
			"				</div>"+
			"			</div>"+
			"			<div class='jp-no-solution'>"+
			"				<span>Update Required</span>"+
			"				To play the media you will need to either update your browser to a recent version or update your <a href='http://get.adobe.com/flashplayer/' target='_blank'>Flash plugin</a>."+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"</div>"
		);
	
		$(".jp-toggles").css("position","relative");
	
		var usuario = JSON.parse(sessionStorage.getItem("usuario"));
		var URL = 'http://www.budolearning.es/downloadNew?id='+sessionStorage.getItem("ficheroId")+'&idUser='+usuario.id+'&extension=.mp4';
		
		if(screen.width < 800){
			$(".videoContainer video").css("width", "100%");
			$(".videoContainer video").css("height","auto");
		} else {
			$(".videoContainer video").css("width", "800px");
			$(".videoContainer video").css("height","auto");
		}
		
		width = "100%";
		height= "auto";
		clss="";
		
		$("#jquery_jplayer_1").jPlayer({
			ready: function () {
				$(this).jPlayer("setMedia", {
					m4v: URL
				});
			},
			swfPath: "http://jplayer.org/latest/dist/jplayer",
			supplied: "m4v",
			size: {
				width: width,
				height: height,
				cssClass: clss
			},
			useStateClassSkin: true,
			autoBlur: false,
			smoothPlayBar: true,
			keyEnabled: true,
			remainingDuration: true,
			toggleDuration: true
		});
		
}

function verVideo() {
	if(!borrarContenido()) {logout(); return;}
	
	$("#contenido").append(
		"<ol id='migas' class='breadcrumb'>"+
		"	<li id='m_disciplina'></li>" +
		"	<li id='m_grado'></li>"+
		"   <li id='m_recurso'></li>"+
		"   <li id='m_fichero' class='active'></li>"+
		"</ol>"
	);
	var usuario = JSON.parse(sessionStorage.getItem("usuario"));
	
	$("#m_disciplina").append("<a href='javascript:verDisciplinas()'>DISCIPLINAS </a>");
	$("#m_grado").append("<a href='javascript:verGrados()'>"+ "" + sessionStorage.getItem("disciplinaNombre") + " </a>");
	$("#m_recurso").append("<a href='javascript:verRecursos()'>"+"" + sessionStorage.getItem("gradoNombre") + " </a>");
	$("#m_fichero").append("<a href='javascript:verFicheros()'>"+"" + sessionStorage.getItem("recursoNombre") + " </a>");
	
	$("#contenido").append(
			"<div class='container videoContainer'>"+
			"	<div id='jp_container_1' class='jp-video' role='application' aria-label='media player'>"+
			"		<div class='jp-type-single'>"+
			"			<div id='jquery_jplayer_1' class='jp-jplayer'></div>"+
			"			<div class='jp-gui'>"+
			"				<div class='jp-video-play'>"+
			"					<button class='jp-video-play-icon' role='button' tabindex='0'>play</button>"+
			"				</div>"+
			"				<div class='jp-interface'>"+
			"					<div class='jp-progress'>"+
			"						<div class='jp-seek-bar'>"+
			"							<div class='jp-play-bar'></div>"+
			"						</div>"+
			"					</div>"+
			"					<div class='jp-current-time' role='timer' aria-label='time'>&nbsp;</div>"+
			"					<div class='jp-duration' role='timer' aria-label='duration'>&nbsp;</div>"+
			"					<div class='jp-details'>"+
			"						<div class='jp-title' aria-label='title'>&nbsp;</div>"+
			"					</div>"+
			"					<div class='jp-controls-holder'>"+
			"						<div class='jp-volume-controls'>"+
			"							<button class='jp-mute' role='button' tabindex='0'>mute</button>"+
			"							<button class='jp-volume-max' role='button' tabindex='0'>max volume</button>"+
			"							<div class='jp-volume-bar'>"+
			"								<div class='jp-volume-bar-value'></div>"+
			"							</div>"+
			"						</div>"+
			"						<div class='jp-controls'>"+
			"							<button class='jp-play' role='button' tabindex='0'>play</button>"+
			"							<button class='jp-stop' role='button' tabindex='0'>stop</button>"+
			"						</div>"+
			"						<div class='jp-toggles'>"+
			"							<button class='jp-full-screen' role='button' tabindex='0'>full screen</button>"+
			"						</div>"+
			"					</div>"+
			"				</div>"+
			"			</div>"+
			"			<div class='jp-no-solution'>"+
			"				<span>Update Required</span>"+
			"				To play the media you will need to either update your browser to a recent version or update your <a href='http://get.adobe.com/flashplayer/' target='_blank'>Flash plugin</a>."+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"</div>"
		);
	
		$(".jp-toggles").css("position","relative");
	
		if(screen.width < 800){
			$(".videoContainer video").css("width", "100%");
			$(".videoContainer video").css("height","auto");
		} else {
			$(".videoContainer video").css("width", "800px");
			$(".videoContainer video").css("height","auto");
		}
		
		width = "100%";
		height= "auto";
		clss="";
		
		var usuario = JSON.parse(sessionStorage.getItem("usuario"));
		var URL = 'http://www.budolearning.es/downloadNew?id='+sessionStorage.getItem("ficheroId")+'&idUser='+usuario.id+'&extension=.mp4';
		
		$("#jquery_jplayer_1").jPlayer({
			ready: function () {
				$(this).jPlayer("setMedia", {
					m4v: URL
				});
			},
			swfPath: "http://jplayer.org/latest/dist/jplayer",
			supplied: "m4v",
			size: {
				width: width,
				height: height,
				cssClass: clss
			},
			useStateClassSkin: true,
			autoBlur: false,
			smoothPlayBar: true,
			keyEnabled: true,
			remainingDuration: true,
			toggleDuration: true
		});
	
}