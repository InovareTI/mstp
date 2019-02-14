<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);
%>

<!DOCTYPE html>
<html lang="en" class="no-js">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />
		<title>M S T P - Managed Services Telecom Platform</title>
		<meta name="description" content="MSTP - Managed Services Telecom Platform" />
		<meta name="keywords" content="MSTP INOVARE, INOVARE TI, Servico, telecom" />
		<meta name="Inovare TI" content="MSTP - Managed Services Telecom Platform" />
		
		
		
		<link rel="shortcut icon" href="./favicon.ico">
		
		 <link rel="stylesheet" href="js/plugins/leaflet/leaflet.css" type="text/css"/>
		 <script src="js/plugins/leaflet/leaflet.js"></script>
		<link rel="stylesheet" type="text/css" href="css/div_graph.css" />
	
    
	<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-table.css" type="text/css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap-datetimepicker.min.css"/>
	<link rel="stylesheet" href="js/jqwidgets/styles/jqx.base.css" type="text/css" />
	<link rel="stylesheet" href="js/jqwidgets/styles/jqx.light.css" type="text/css" />
	<link rel="stylesheet" href="js/jqwidgets/styles/jqx.material.css" type="text/css" />
	
	<link rel="stylesheet" href="css/diversos.css" type="text/css" />
	<link rel="stylesheet" href="css/fileinput.min.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-select.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-select.min.css" type="text/css">
	<link rel="stylesheet" href="js/jquery-confirm.min.css" type="text/css" />
	<link href="css/style.css"  type="text/css">
	<link href="css/default/style.min.css" type="text/css">
	
	
	<link rel="stylesheet" type="text/css" href="css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="css/customizacoes35.css" />
	<link rel="stylesheet" type="text/css" href="css/icons.css" />
	<link rel="stylesheet" type="text/css" href="css/component.css" />
	
	
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.css">
	<script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
	<script src="js/jquery-confirm.min.js" type="text/javascript"></script>
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.js"></script>
	<link rel="stylesheet" type="text/css" href="css/DataTable/buttons.dataTables.min.css" />
	<script src="js/DataTable/dataTables.buttons.js" type="text/javascript"></script>
		<script src="js/DataTable/jszip.min.js" type="text/javascript"></script>
	<script src="js/DataTable/buttons.print.js" type="text/javascript"></script>
	<script src="js/DataTable/buttons.html5.js" type="text/javascript"></script>

	<script src="js/DataTable/pdfmake.min.js" type="text/javascript"></script>
	<script src="js/DataTable/vfs_fonts.js" type="text/javascript"></script>
	
	<script src="js/modernizr.custom.js"></script>
	<script src="js/plugins/canvas-to-blob.min.js" type="text/javascript"></script>
	<script src="js/fileinput.js" type="text/javascript"></script>
	<script src="js/fileinput_locale_pt-BR.js" type="text/javascript"></script> 
	<script src="js/moment-with-locales.min.js"></script>
	<script src="js/bootstrap-datetimepicker.js"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	
	
	
	<script src="js/accounting.js"></script>
	<script src="js/bootstrap-table.js"></script>
	<script src="js/bootstrap-table-toolbar.min.js"></script>
	<script src="js/bootstrap-table-editable.js"></script>
	<script src="js/bootstrap-multiselect.js"></script>
	<script src="js/bootstrap-select.js"></script>
	<script src="js/bootstrap-select.min.js"></script>
	<script src="js/highcharts.js"></script>
	<script src="js/modules/drilldown.js"></script>
    <script src="js/modules/data.js"></script>
	<script src="js/carregador_graficos.js"></script>
  	<script src="js/bootstrap-table-pt-BR.js"></script>
  	<script src="js/bootstrap-table-filter-control.js"></script>
  	
  	 <!-- Make sure you put this AFTER Leaflet's CSS -->

  	
  	<script src="js/bootstrap-table-reorder-rows.min.js"></script>
  	<script src="js/jquery.tablednd.js"></script>
  	
   	<script src="js/locale/pt-br.js"></script>
   	<script src="js/bootstrap-datetimepicker.min.js"></script>
  	
  	<script type="text/javascript" src="js/w2ui-1/w2ui-1.4.3.min.js"></script>
  	
  	<script type="text/javascript" src="js/jqwidgets/jqxcore.js"></script>
  	<script type="text/javascript" src="js/jqwidgets/jqxloader.js"></script>
  	<script type="text/javascript" src="js/jqwidgets/jqxdatetimeinput.js"></script>
  	<script type="text/javascript" src="js/jqwidgets/jqxdropdownbutton.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxcombobox.js"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.0.2/js/all.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.filter.js"></script>
     <script type="text/javascript" src="js/jqwidgets/jqxtreegrid.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxtreemap.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxtree.js"></script>
   
    <script type="text/javascript" src="js/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.sort.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.columnsresize.js"></script> 
    <script type="text/javascript" src="js/jqwidgets/jqxdata.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdate.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxribbon.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxlayout.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.pager.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.edit.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpanel.js"></script>
     <script type="text/javascript" src="js/jqwidgets/jqxtabs.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdropdownlist.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxcalendar.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxnumberinput.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpasswordinput.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxtooltip.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxwindow.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdocking.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxexpander.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxscheduler.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxscheduler.api.js"></script>
     <script type="text/javascript" src="js/jqwidgets/jqxinput.js"></script>
      <script type="text/javascript" src="js/jqwidgets/jqxdragdrop.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpivot.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpivotdesigner.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpivotgrid.js"></script>
    <script type="text/javascript" src="js/jqwidgets/globalization/globalize.js"></script>
    <script type="text/javascript" src="js/jqwidgets/localization.js"></script>
    <link rel="stylesheet" type="text/css" href="js/dhtmlx/pivot.css" />
  <script type="text/javascript" src="js/dhtmlx/pivot.js"></script>
    
	<script type="text/javascript" src="js/starter.js"></script>
	<script type="text/javascript" src="js/portal.js"></script>
	<script type="text/javascript" src="js/rollout.js"></script>
	<script type="text/javascript" src="js/senha.js"></script>
	<script type="text/javascript" src="js/project.js"></script>
	<script type="text/javascript" src="js/clientes.js"></script>
	<script type="text/javascript" src="js/usuarios.js"></script>
	<script type="text/javascript" src="js/mapa_controle.js"></script>
	<script type="text/javascript" src="js/Site.js"></script>
	<script type="text/javascript" src="js/banco_horas.js"></script>
	<script type="text/javascript" src="js/Relatorios.js"></script>
	<script type="text/javascript" src="js/Ponto.js"></script>
	<script type="text/javascript" src="js/TemplateVistoria.js"></script>
	<script type="text/javascript" src="js/scheduler.js"></script>
	<script type="text/javascript" src="js/importacoes.js"></script>
	<script type="text/javascript" src="js/pivot.js"></script>
	<script src='https://api.mapbox.com/mapbox-gl-js/v0.52.0/mapbox-gl.js'></script>
     <link href='https://api.mapbox.com/mapbox-gl-js/v0.52.0/mapbox-gl.css' rel='stylesheet' />

	<link rel="manifest" href="/manifest.json" />
	<script type="text/javascript" src="OneSignalSDKWorker.js"></script>
	<script type="text/javascript" src="OneSignalSDKUpdaterWorker.js"></script>
	<script>
		  var OneSignal = window.OneSignal || [];
		  OneSignal.push(function() {
		    OneSignal.init({
		      appId: "ae9ad50e-520d-436a-b0b0-23aaddedee7b",
		      autoRegister: false,
		      notifyButton: {
		        enable: true,
		      },
		    });
		  });
		</script>
    
	<style>
	.card {
    	/* Add shadows to create the "card" effect */
    		box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
    		transition: 0.3s;
    		color:white;
		}

/* On mouse-over, add a deeper shadow */
	.card:hover {
	    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
	}

/* Add some padding inside the card container */
	.container {
	    padding: 2px 16px;
	}
	#tabela_usuario_folha_ponto{
	color:black;	
	}
	#tabela_usuario_folha_ponto_analise{
	color:black;	
	}
	#tabela_usuario_analise_HH{
	color:black;	
	}
	#tabela_arquivos_importados{
	color:black;	
	}
	 #menu_mapa {
	 	color: black;
        position: absolute;
        background: #fff;
        padding: 10px;
        width: 95%;
        font-family: 'Open Sans', sans-serif;
    }
	</style>
	    <script>
	    
			function sair(){
				$.ajax({
					  type: "POST",
					  data: {"opt":"99"
						},		  
					  //url: "http://localhost:8080/DashTM/D_Servlet",	  
					  url: "./POControl_Servlet",
					  cache: false,
					  dataType: "text",
					  
					});
			}
			 
		</script>
		<style>
		#mapid { height: 180px; }
		#map_full { position:fixed;height:100%; width:100%; }
		.loader {
  border: 16px solid #f3f3f3;
  border-radius: 50%;
  border-top: 16px solid #3498db;
  width: 120px;
  height: 120px;
  -webkit-animation: spin 2s linear infinite; /* Safari */
  animation: spin 2s linear infinite;
}

/* Safari */
@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
 #menu_operadora {
        background: #fff;
        position: absolute;
        z-index: 1;
        top: 10px;
        right: 10px;
        border-radius: 3px;
        width: 120px;
        border: 1px solid rgba(0,0,0,0.4);
        font-family: 'Open Sans', sans-serif;
    }

    #menu_operadora a {
        font-size: 13px;
        color: #404040;
        display: block;
        margin: 0;
        padding: 0;
        padding: 10px;
        text-decoration: none;
        border-bottom: 1px solid rgba(0,0,0,0.25);
        text-align: center;
    }

    #menu_operadora a:last-child {
        border: none;
    }

    #menu_operadora a:hover {
        background-color: #f8f8f8;
        color: #404040;
    }

    #menu_operadora a.active {
        background-color: #3887be;
        color: #ffffff;
    }

    #menu_operadora a.active:hover {
        background: #3074a4;
    }
    .mapboxgl-popup {
        max-width: 400px;
        color:black;
        font: 12px/20px 'Helvetica Neue', Arial, Helvetica, sans-serif;
    }
		</style>
		
	</head>
	<body>
	
		<div class="container">
			<!-- Push Wrapper -->
			<div class="mp-pusher" id="mp-pusher">

				<!-- mp-menu -->
				<nav id="mp-menu" class="mp-menu">
					<div class="mp-level">
						<h4 class="icon icon-world" style="font-family: Horizon;"><%= session.getAttribute("nome_empresa")%></h4>
						<ul>
							<li><a class="icon icon-display" href="#" onclick="menu('portal')">Dashboard</a></li>
							<li><a class="icon icon-display" href="#" onclick="menu('po')">Gestão de PO's</a></li>
							<li><a href="#" onclick="menu('aprovacoes');carrega_aprovacoes_hh();"><i class="fas fa-list-ul"></i>&nbsp;&nbsp;Aprovações</a></li>
							<li class="icon icon-arrow-left">
								<a class="icon icon-male" href="#" onclick="menu('clientes')">Clientes</a>
								
							</li>
							<li class="icon icon-arrow-left"><a class="icon icon-shop" href="#">Projetos</a></li>
							<li><a class="icon icon-paperplane" href="#" onclick="menu('rollout')">Rollout</a></li>
							<li><a class="icon icon-world" href="#" onclick="menu('mapa_central')">Mapa</a></li>
							<li class="icon icon-arrow-left">
								<a class="icon icon-note" href="#">Relatórios</a>
								<div class="mp-level">
									<h2 class="icon icon-settings">Relatórios</h2>
									<ul>
										<li><a class="icon icon-calendar" href="#" onclick="menu('rel_ponto_kpi');carrega_kpi()">KPI's de Ponto</a></li>
										<li><a class="icon icon-calendar" href="#" onclick="menu('usuarios_ponto')">Folha de Ponto</a></li>
										<li><a class="icon icon-calendar" href="#" onclick="menu('usuarios_ponto_analise')">Análise de Ponto</a></li>
										<li><a class="icon icon-calendar" href="#" onclick="menu('faltas_relatorio')">Relatório de Faltas</a></li>
										<li><a class="icon icon-calendar" href="#" onclick="menu('usuarios_banco_hh')">Banco de Horas</a></li>
										<li><a class="icon icon-calendar" href="#" onclick="menu('usuarios_extra_hh')">Horas Extras</a></li>
										<li><a class="icon icon-key" href="#" onclick="menu('mstp_mobile_report_diario');g5('category','grafico_container_mstp_mobile_report')">Registros MSTP Mobile</a></li>
										<li class="icon icon-arrow-left">
											<a class="icon icon-note" href="#">Rollout</a>
											<div class="mp-level">
												<h2 class="icon icon-settings">Relatórios rollout</h2>
												<ul>
													<li><a class="icon icon-key" href="#" onclick="menu('pivot_design_div')">Construcao de Pivot</a></li>
													<li><a class="icon icon-key" href="#" onclick="menu('pivot_view_div')">Pivots</a></li>
												</ul></div></li>
									</ul>
								</div>
							</li>
							<li><a class="icon icon-wallet" href="#">Financeiro</a></li>
                            <li class="icon icon-arrow-left">
								<a class="icon icon-settings" href="#">Configurações</a>
								<div class="mp-level">
									<h2 class="icon icon-settings">Configurações</h2>
									<ul>
										<li><a class="icon icon-key" href="#" onclick="menu('senha')">Senha</a></li>
										<li><a class="icon icon-user" href="#" onclick="menu('usuarios')">Usuários</a></li>
										<li><a class="icon icon-user" href="#" onclick="menu('feriados')">Feriados</a></li>
										<li><a class="icon icon-user" href="#">Portal</a></li>
										<li><a class="icon icon-user" href="#" onclick="menu('campos_rollout')">Rollout</a></li>
										<li><a class="icon icon-user" href="#" onclick="menu('vistoria_campos')">Relatórios de Vistorias</a></li>
										<li><a class="icon icon-user" href="#" onclick="menu('Sites')">Sites</a></li>
										<div id="assinatura_menu_div" style="display:none" ><li><a class="icon icon-user" href="#" onclick="menu('assinaturas')">Assinatura</a></li></div>
									</ul>
								</div>
							</li>
							<li><a class="icon icon-upload" href="#" onclick="menu('importacoes')">Minhas Importações</a></li>
                            <li><a class="icon icon-signout" href="index.html" onclick="sair()">Sair</a></li>
						</ul>
					</div>
				</nav>
				<!-- /mp-menu -->
                <div class="block block-80 clearfix" style="display:block;">
				<p><a href="#" id="trigger" class="menu-trigger"></a><label style="font-family: Horizon;"><%= session.getAttribute("nome_empresa")%></label><label style="color:orange;font-size: 12px;">©MSTP</label></p>
								
				</div>
				<div id="principal" style="width:100%;display:table;height:100%">
				
					<div class="janelas" id="portal" >
					<div id='jqxtabs'>
						<ul style='margin-left: 20px;'>
				            <li>Usuários</li>
				            <li>Rollout</li>
	        			</ul>
	        			<div>
						<div id='jqxWidget' style="width:100%;margin: auto;float: left">
					        <div id="docking" style="width:1200px">
					            <div style="overflow: hidden;width:400px">
					                <div id="window0" style="height: 350px">
					                    <div>Minhas Horas Trabalhas</div>
					                   
					                    <div id='grafico_container1'><div class="loader"></div></div>
					                </div>
					                <div id="window1" style="height: 350px">
					                    <div>Aprovações</div>
					                    <div id='grafico_container2'><div class="loader"></div></div>
					                </div>
					            </div>
					            <div style="overflow: hidden;;width:400px">
					                <div id="window2" style="height: 350px; ">
					                    <div>Registro no App por funcionário</div>
					                    <div id='grafico_container3'><div class="loader"></div></div>
					                </div>
					                <div id="window3" style="height: 350px; ">
					                    <div>Horas Acumuladas por funcionário</div>
					                    <div id='grafico_container4'><div class="loader"></div></div>
					                </div>
					            </div>
					            
					            <div style="overflow: hidden;height:800px;width:400px">
					                <div id="window4" style="height: 800px; width:600px">
					                	<div>Sites com ponto registrado</div>
					                    <div id="mapid" style="height: 800px"></div>
					                </div>
					                
					            </div>
					        </div>
					        
					    </div><!-- fim do widget -->
					    </div>
					    <div>
							<div id='jqxWidget_rollout' style="width:100%;margin: auto;float: left">
					        <div id="docking_rollout" style="width:1200px">
					            <div style="overflow: hidden;width:400px">
					                <div id="window0" style="height: 350px">
					                    <div><div id='from'></div></div>
					                   
					                    <div id='grafico_container1_rollout'><div class="loader"></div></div>
					                </div>
					                <div id="window1" style="height: 350px">
					                    <div><div id='from2'></div></div>
					                   
					                    <div id='grafico_container2_rollout'><div class="loader"></div></div>
					                </div>
					            </div>
					         </div>
					         </div>
						</div><!-- Fim Tab Rollout -->
					   </div>
					</div> <!-- /portal -->
					<div class="janelas" id="mapa_central" style="display:none;"	>
					
            
						<div id="map_full"><div class="loader"></div></div>
						<div id='menu_mapa'><div id="mapa_info"></div></div>
					
					
						<script>
						
						function inicializa_mapa_full(opt){
							$('#map_full').html('');
							$('#menu_mapa').html('');
							
						mapboxgl.accessToken = 'pk.eyJ1IjoiZmFiaW9zYWxidXF1ZXJxdWUiLCJhIjoiY2pjMjYxYzFoMGludzJxczR1ZWp2aTBpaiJ9.KptnIvfz_61BmkgGIR_ZWA';
						map = new mapboxgl.Map({
						    container: 'map_full',
						    style: 'mapbox://styles/fabiosalbuquerque/cjl100xt72fci2ro3s9y5b1re',
						    zoom: 3,
						    center: [-51.549, -11.811]
						});
						map.on('load', function () {
							if(opt==1){load_site_markers_mapa_central_rollout();}else{load_site_markers_mapa_central();}
							
						});
						//var layerList = document.getElementById('menu_mapa');
						//var inputs = layerList.getElementsByTagName('input');
						//function switchLayer(layer) {
						//    var layerId = layer.target.id;
						//    map.setStyle('mapbox://styles/mapbox/' + layerId + '-v9');
						//}
						//for (var i = 0; i < inputs.length; i++) {
						//    inputs[i].onclick = switchLayer;
						//}
						map.addControl(new mapboxgl.FullscreenControl());
						map.addControl(new mapboxgl.NavigationControl());
						var toggleableLayerIds=[];
						if(opt==1){ 
							toggleableLayerIds = [ 'ROLLOUT' ];
						}else{
							toggleableLayerIds = [ 'VIVO', 'TIM','CLARO','NEXTEL','USUARIOS','ROLLOUT' ];
						}
						for (var i = 0; i < toggleableLayerIds.length; i++) {
						    var id = toggleableLayerIds[i];
						    var link = document.createElement('a');
						    link.href = '#';
						    link.className = 'btn btn-info active';
						    link.textContent = id;
						    link.onclick = function (e) {
						        var clickedLayer = this.textContent;
						        e.preventDefault();
						        e.stopPropagation();
						        if(clickedLayer=='ROLLOUT'){
						        	$(".janelas").hide();
						    		document.getElementById("rollout").style.display = "block";
						        }else{
						        var visibility = map.getLayoutProperty(clickedLayer, 'visibility');
						        if (visibility === 'visible') {
						        	
						            	map.setLayoutProperty(clickedLayer, 'visibility', 'none');
						        	    map.setLayoutProperty('clusters_'+clickedLayer, 'visibility', 'none');
						        	    map.setLayoutProperty('cluster-count_'+clickedLayer, 'visibility', 'none');
						            this.className = 'btn btn-danger';
						        } else {
						            this.className = 'btn btn-info active';
						            map.setLayoutProperty(clickedLayer, 'visibility', 'visible');
						            map.setLayoutProperty('clusters_'+clickedLayer, 'visibility', 'visible');
					        	    map.setLayoutProperty('cluster-count_'+clickedLayer, 'visibility', 'visible');
						        }}
						    };
						    var layers = document.getElementById('menu_mapa');
						    layers.appendChild(link);
						}
						}
						</script>
					</div>
					<div class="janelas" id="pivot_view_div" style="display:none;width:100%;height:100%;">
					<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">Pivots de rollout</div>
		        				<div class="panel-body">
		        				<div><select id="select_pivot_view" class="selectpicker" data-live-search="true" title="Escolha sua Pivot" onchange="carrega_pivot(this.value)">
		        			
		        			</select></div>
		        				<hr>
		        				<div > 
		        				<table>
								        <tr>
								            
								            <td>
								                <div id="divPivotGridView" style="height: 700px; width: 1200px;">
								                </div>
								            </td>
								        </tr>
								    </table>
								    </div>
		        				</div>
	        		</div>
					</div>
					<div class="janelas" id="pivot_design_div" style="display:none;width:100%;height:100%;">
					<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">Construção de Pivot (limitado em 100 linhas do rollout)</div>
		        				<div class="panel-body">
		        				<div><button class="btn btn-primary" id="salvar_pivot_btn">Salvar Pivot</button><input type="text" class="form-control" id="report_pivot_nome" placeholder="Informe o nome do relatório" style="width:400px;display: inline;"></div>
		        				<hr>
		        				<div > 
		        				<table>
								        <tr>
								            
								            <td>
								                <div id="divPivotGrid" style="height: 700px; width: 100%;">
								                </div>
								            </td>
								        </tr>
								    </table>
								    </div>
		        				</div>
	        		</div>
					</div>
					<div class="janelas" id="vistoria_campos" style="display:none;width:100%;height:100%;">
					<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">Definir novo Relatório de Vistoria</div>
		        				<div class="panel-body">
		        				<div id='novo_rel_vistoria'><button type="button" class="btn btn-info" data-toggle="modal" data-target="#modal_campos_vistoria">Novo Relatório</button></div>
		        				</div>
	        		</div>
					<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">Definições de relatórios de vistorias</div>
		        			<div class="panel-body" >
		        				<div id='tabela_container_vistoria_campos'>
			        				<table style="color:black;width:100%" class="display" id="tabela_relatorios">
				        				<thead style="color:black">
				        					<tr>
					        					<th>Operações</th>
					        					<th>Relatório Nome</th>
					        					<th>Relatório Descrição</th>
					        					<th>Relatório Tipo</th>
					        					<th>Criado por</th>
					        					<th>Criado em</th>
					        					
				        					</tr>
				        				</thead>
				        				
			        				</table>
			        			</div>
		        			</div>
	        		</div></div>
	        		<div class="janelas" id="po" style="display:none;width:100%;height:100%;"	>
	        	
        			<div id="div_tabela_po"><button class="btn btn-lg btn-warning"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> Carregando...</button></div>
        	
       
	        		</div>
					<div class="janelas" id="rollout" style="display:none;width:100%;height:100%;"	>
        				<div id='jqxExpander' style="width:auto;display:inline-block;">
        			 	<div>MiniDashboard</div>
        			 	<div>
						
							<div class="row">
						    	<div class="col-sm-6 col-md-3">
							    	<div class="panel panel-light panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/project.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Total de Atividades</small>
									    				<h4>0</h4>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		<small class="stat-label">0% de itens executados</small>
									    		<h4>0% de itens atrasados</h4>
									    	</div>
								    	</div>
							    	</div>
							 </div>
			    				<div class="col-sm-6 col-md-3">
							    	<div class="panel panel-primary panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/gnucash.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Quantidade de PO</small>
									    				<h1>0</h1>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		<small class="stat-label">0% de itens executados</small>
									    		<h4>0% de itens atrasados</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
				     			<div class="col-sm-6 col-md-3">
							    	<div class="panel panel-success panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/list1.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Itens de PO</small>
									    				<h1>0</h1>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		<small class="stat-label">0% de itens executados</small>
									    		<h4>0% de itens atrasados</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
							    <div class="col-sm-6 col-md-3">
							    	<div class="panel panel-danger panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/network.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Total de Sites</small>
									    				<h1>0</h1>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		<small class="stat-label">0% de itens executados</small>
									    		<h4>0% de itens atrasados</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
							    <div class="col-sm-6 col-md-3">
							    	<div class="panel panel-dark panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/user.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Total de Recursos</small>
									    				<h1>22</h1>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		<small class="stat-label">50% de itens executados</small>
									    		<h4>40% de itens atrasados</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
						   </div>
						
						</div>
					</div><!-- /expander -->
					<hr>
							<div class="row" style="width: 100%;height:95%;">
					 			
					 			<div id='jqxWidget' style="padding-left:20px; ;font-size: 13px; font-family: Verdana; float: left;width: 100%;height: 100%">
        								<div id="jqxLoader_rolout"></div>
        								<div id="jqxgrid"></div>
    							</div>
					 		</div>
        		</div><!-- /rollout -->
        		<div class="janelas" id="faltas_relatorio" style="display:none;margin:0 auto;"	>
        		<div id="div_filtro_relatorio_FALTAS" class="panel panel-default" style="width:90%;margin:0 auto;">
        				<div class="panel-heading">Filtrar & Buscar Faltas</div>
	        			<div class="panel-body">
	        				<select id="select_mes_faltas" class="selectpicker" data-live-search="true" title="Escolha o mes">
		        			<option value="1">Janeiro</option>
		        			<option value="2">Fevereiro</option>
		        			<option value="3">Março</option>
		        			<option value="4">Abril</option>
		        			<option value="5">Maio</option>
		        			<option value="6">Junho</option>
		        			<option value="7">Julho</option>
		        			<option value="8">Agosto</option>
		        			<option value="9">Setembro</option>
		        			<option value="10">Outubro</option>
		        			<option value="11">Novembro</option>
		        			<option value="12">Dezembro</option>
		        			</select>
		        			<select id="select_func_faltas" class="selectpicker" data-live-search="true" title="Escolha o Funcionário">
		        			
		        			</select>
		        			<button id="btn_gerar_analise_faltas" onclick="carrega_faltas()" class="btn btn-primary">Gerar Análise de Faltas</button>
	        			</div>
	        		</div>
	        		<div id="div_tabela_faltas" class="panel panel-primary" style="width:90%;margin:0 auto;">
        				<div class="panel-heading">Tabela de Faltas</div>
	        			<div class="panel-body">
	        			 <table id="tabela_faltas" class="display" style="color:black;width:100%">
					        <thead>
					            <tr>
					                <th>Dia</th>
					                <th>Usuário</th>
					                <th>Nome</th>
					                <th>Status</th>
					                
					            </tr>
					        </thead>
					        <tfoot>
					            <tr>
					                <th>Dia</th>
					                <th>Usuário</th>
					                <th>Nome</th>
					                <th>Status</th>
					            </tr>
					        </tfoot>
					    </table>
	        			</div>
	        		</div>
        		</div>
        		<div class="janelas" id="importacoes" style="display:none;margin:0 auto;"	>
        		
	        		<div id="div_tabela_arquivos_importados" class="panel panel-primary" style="width:90%;margin:0 auto;">
        				<div class="panel-heading">Tabela de Arquivos Importados</div>
	        			<div class="panel-body">
	        			 <table id="tabela_arquivos_importados" class="display" style="color:black;width:100%">
					        <thead>
					            <tr>
					                <th>Dia</th>
					                <th>Usuário</th>
					                <th>Nome</th>
					                <th>Arquivo</th>
					                <th>Status</th>
					            </tr>
					        </thead>
					        <tfoot>
					            <tr>
					                <th>Dia</th>
					                <th>Usuário</th>
					                <th>Nome</th>
					                <th>Arquivo</th>
					                <th>Status</th>
					            </tr>
					        </tfoot>
					    </table>
	        			</div>
	        		</div>
        		</div>
        		<div class="janelas" id="mstp_mobile_report_diario" style="display:none;margin:0 auto;"	>
        			<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">MSTP Mobile relatório de Registros </div>
		        				<div class="panel-body">
		        				<div id='grafico_container_mstp_mobile_report' style="height:200px">...</div>
		        				</div>
	        		</div>
	        		<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">Tabela - MSTP Mobile relatório de Registros </div>
		        			<div class="panel-body" >
		        				<div id='tabela_container_mstp_mobile_report'>
			        				<table style="color:black;width:100%" class="display" id="tabela_registro_mstp_mobile">
				        				<thead style="color:black">
				        					<tr>
					        					<th>Usuário</th>
					        					<th>Data & Hora</th>
					        					<th>Tipo</th>
					        					<th>Local</th>
					        					<th>Distancia do Local</th>
				        					</tr>
				        				</thead>
				        				
			        				</table>
			        			</div>
		        			</div>
	        		</div>
        		</div>
        		<div class="janelas" id="assinaturas" style="display:none;margin:0 auto;">
        		<div id="tabela_div_assinaturas" style="width:90%;margin:0 auto;">
        		<table style="width:90%;margin:0 auto;">
        		<thead>
        		<th>PayPal</th>
        		<th>Descrição</th>
        		</thead>
        		<tbody><tr><td><div id="btn_assinar1"><button type="button" class="btn btn-info" onclick="inicia_assinatura()">Aceitar & Assinar</button></div></td><td>Módulo de controle de ponto com emissao de espelho de ponto até 70 usuários.</td>
        		
        		<td>
					     <A HREF="https://www.paypal.com/cgi-bin/webscr?cmd=_subscr-find&alias=5TTCUD9752CMQ">
					<IMG SRC="https://www.paypalobjects.com/pt_BR/i/btn/btn_unsubscribe_LG.gif" BORDER="0">
					</A>
        		</td>
        		</tr>
        		</tbody>
        		
        		</table>
        		</div>
        		</div>
        		<div class="janelas" id="usuarios_banco_hh" style="display:none;margin:0 auto;"	>
        		
	        		<div id="resumo_banco de Horas" class="panel panel-default" style="width:90%;margin:0 auto;">
	        				<div class="panel-heading">Resumo Banco de Horas </div>
		        				<div class="panel-body">
		        				<div style="width:50%;float:left;"><h3 style="color:gray;float:left">Total Disponivel: &nbsp;&nbsp;&nbsp;&nbsp;</h3>
		        					<h3 style="float:left"><a id="exibe_hh12" style="color:#1925f6" href="#" onclick="carrega_extrato_he()">0</a></h3>
		        				</div>
		        				<div style="width:50%;float:left"><h3 style="color:gray">Total Compensado:</h3></div>
		        				</div>
	        		</div>
	        		<div id="div_mostra_extrato_hh" style="display:none">
	        		<div class="panel panel-default" style="width:90%;margin:0 auto;">
	        			<div class="panel-heading">Extrato Horas<div style="float:right"><a style="color:gray" href="#" onclick="mostra_horas_manuais()">Voltar</a></div></div>
		        			<div class="panel-body">
		        				<div id="div_tabela_extrato_hh"></div>
		        			</div>
		        		</div>	
	        		</div>
	        		<div id="add_banco_de_Horas" class="panel panel-default" style="width:90%;margin:0 auto;">
	        				<div class="panel-heading">Adicionar horas manualmente</div>
		        				<div class="panel-body">
		        				<table>
		        				<tr style="color:black"><th style="padding:10px">Data & Hora de Início</th><th style="padding:10px">Data & Hora fim</th><th style="padding:10px">Horas Extras Normais</th><th style="padding:10px">Horas Noturnas</th><th>Observações</th><th></th></tr>
		        				<tr><td style="padding:10px">
		        				<div class='input-group date' id='banco_entrada'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div></td>
                <td style="padding:10px">
		        				<div class='input-group date' id='banco_saida'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div></td> <td style="padding:10px"><input id="horas_normais" type='text' class="form-control" readonly></td><td style="padding:10px"><input id="horas_noturnas" type='text' class="form-control" readonly></td><td style="padding:10px"><input type="text" class="form-control" value="" id="obs_hh"></td><td><button onclick="add_hours()" class="btn btn-primary">+</button></td>
                </tr></table>
                <div id="div_tabela_resumo_hh" style="display:none">
                	<table style="color:black" id="tabela_resumo_banco" data-toggle="table"  data-height="300">
                	<thead style="color:black">
                		<tr style="color:black">
                			<th data-field="id">#</th>
                			<th data-field="H_data">Data</th>
                			<th data-field="H_Entrada">Início</th>
                			<th data-field="H_Saida">Fim</th>
                			<th data-field="H_normais">H. Normais</th>
                			<th data-field="H_noturna">H. Noturnas</th>
                			<th data-field="H_anotacao">Anotação</th>
                		</tr>
                	</thead>
                	</table>
                <button class="btn btn-primary" onclick="armazena_hh()">Enviar</button>
                </div>
		        				</div>
	        		</div>
	        	
        		</div>
        		
        		<script type="text/javascript">
        	    $(function () {
        	        $('#banco_entrada').datetimepicker({useCurrent: false,showClear: true});
        	        $('#banco_saida').datetimepicker({
        	            useCurrent: false, //Important! See issue #1075
        	            showClear: true
        	        });
        	        $("#banco_entrada").on("dp.change", function (e) {
        	            
        	           	if(e.date!=false){
        	            var check = $('#banco_entrada').data("DateTimePicker").viewDate();
        	  			var month = check.format('M');
        	            var day   = check.format('D');
        	            var year  = check.format('YYYY');
						var aux_d=day+'/'+month+'/'+year;
						//$('#banco_entrada').data("DateTimePicker").viewDate(aux_d+' 18:00:00');
						//$('#banco_saida').data("DateTimePicker").viewDate(aux_d+' 19:00:00');
						$('#banco_saida').data("DateTimePicker").maxDate(aux_d+' 23:59:59');
        	            $('#banco_saida').data("DateTimePicker").minDate(aux_d+' 00:00:00');
        	            
        	            calcula_horas($('#banco_entrada').data("DateTimePicker").viewDate(),$('#banco_saida').data("DateTimePicker").viewDate());
        	            }else{
        	            	$('#banco_saida').data("DateTimePicker").minDate(false);
        	            	$('#banco_saida').data("DateTimePicker").maxDate(false);
        	            }
        	        });
        	        $("#banco_saida").on("dp.change", function (e) {
        	        	//console.log($('#banco_entrada').data("DateTimePicker").viewDate());
        	        	if(e.date!=false){
            	            var check = $('#banco_saida').data("DateTimePicker").viewDate();
            	  			var month = check.format('M');
            	            var day   = check.format('D');
            	            var year  = check.format('YYYY');
    						var aux_d=day+'/'+month+'/'+year;
    						//$('#banco_entrada').data("DateTimePicker").viewDate(aux_d+' 18:00:00');
    						//$('#banco_saida').data("DateTimePicker").viewDate(aux_d+' 19:00:00');
            	            $('#banco_entrada').data("DateTimePicker").minDate(aux_d+' 00:00:00');
            	            $('#banco_entrada').data("DateTimePicker").maxDate(aux_d+' 23:59:59');
            	            calcula_horas($('#banco_entrada').data("DateTimePicker").viewDate(),$('#banco_saida').data("DateTimePicker").viewDate());
            	            //calcula_horas(moment(aux_d+' 22:00:00'),$('#banco_saida').data("DateTimePicker").viewDate());    
        	        	}else{
            	            	$('#banco_entrada').data("DateTimePicker").minDate(false);
            	            	$('#banco_entrada').data("DateTimePicker").maxDate(false);
            	            }
        	            
        	        });
        	    });
        	</script>
        		<div class="janelas" id="rel_ponto_kpi" style="display:none;margin:0 auto;">
        		<div id='jqxExpander' style="margin:0 auto;width: 90%;">
        			 	<div> Resumo Mensal</div>
        			 	<div>
						
							<div class="row">
						    	<div class="col-sm-6 col-md-3">
							    	<div class="panel panel-primary panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/entrada2.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Ponto</small>
									    				<h3 id="kpi_entrada_lbl">0%</h3>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		
									    		<h4>Entrada</h4>
									    	</div>
								    	</div>
							    	</div>
							 </div>
			    				<div class="col-sm-6 col-md-3">
							    	<div class="panel panel-primary panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/entrada2.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Intervalo</small>
									    				<h3 id="kpi_ii_lbl">0%</h3>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		
									    		<h4>Início de Intervalo</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
				     			<div class="col-sm-6 col-md-3">
							    	<div class="panel panel-primary panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/entrada2.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Intervalo</small>
									    				<h3 id="kpi_fi_lbl">0%</h3>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		
									    		<h4>Fim de Intervalo</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
							    <div class="col-sm-6 col-md-3">
							    	<div class="panel panel-primary panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/entrada2.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Saída</small>
									    				<h3 id="kpi_saida_lbl">0%</h3>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		
									    		<h4>Saída</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
							    <div class="col-sm-6 col-md-3">
							    	<div class="panel panel-dark panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/user.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Recursos</small>
									    				<h3 id="kpi_usuarios_lbl">0%</h3>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		
									    		<h4>Recursos Ativos</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
						   </div>
						
						</div>
					</div><!-- /expander -->
        			<div id="div_tabela_kpi_ponto" class="panel panel-primary" style="width:90%;margin:0 auto;">
        				<div class="panel-heading">KPI's de Marcação de Ponto</div>
	        			<div class="panel-body">
	        			 <table id="tabela_kpi_ponto" class="display" style="color:black;width:100%">
					        <thead>
					            <tr>
					                <th>Dia</th>
					                <th>Total Usuários</th>
					                <th>Entrada</th>
					                <th>Inicio Intervalo</th>
					                <th>Fim Intervalo</th>
					                <th>Saída</th>
					            </tr>
					        </thead>
					        <tfoot>
					            <tr>
					                <th>Dia</th>
					                <th>Total Usuários</th>
					                <th>Entrada</th>
					                <th>Inicio Intervalo</th>
					                <th>Fim Intervalo</th>
					                <th>Saída</th>
					            </tr>
					        </tfoot>
					    </table>
	        			</div>
	        		</div>
        		</div>
        		<div class="janelas" id="usuarios_extra_hh" style="display:none;margin:0 auto;">
        			<div id="div_tabela_usuario_HH_filtros" class="panel panel-default" style="width:90%;margin:0 auto;">
        				<div class="panel-heading">Filtrar & Buscar Horas Extras</div>
	        			<div class="panel-body">
	        				<select id="select_mes_folha_HH" class="selectpicker" data-live-search="true" title="Escolha o mes">
		        			<option value="1">Janeiro</option>
		        			<option value="2">Fevereiro</option>
		        			<option value="3">Março</option>
		        			<option value="4">Abril</option>
		        			<option value="5">Maio</option>
		        			<option value="6">Junho</option>
		        			<option value="7">Julho</option>
		        			<option value="8">Agosto</option>
		        			<option value="9">Setembro</option>
		        			<option value="10">Outubro</option>
		        			<option value="11">Novembro</option>
		        			<option value="12">Dezembro</option>
		        			</select>
		        			<select id="select_func_HH" class="selectpicker" data-live-search="true" title="Escolha o Funcionário">
		        			
		        			</select>
		        			<button id="btn_gerar_analise_HH" onclick="carrega_HH_usuario()" class="btn btn-primary">Gerar Análise de Horas Extras</button>
	        			</div>
	        		</div>
	        		<p></p>
	        		<div id="div_tabela_usuario_HH_panel" class="panel panel-primary" style="width:90%;margin:0 auto;">
        				<div class="panel-heading"><h3 class="panel-title">Análise de Horas Extras</h3></div>
	        			<div class="panel-body">
        				<div id="div_tabela_usuario_HH">
					       <table id="tabela_usuario_analise_HH" class="display" style="width:100%">
					        <thead>
					            <tr>
					                <th>Data</th>
					                <th>Entrada</th>
					                <th>Saída</th>
					                <th>Horas Totais</th>
					                <th>Horas Extras normais</th>
					                <th>Horas extras Noturnas</th>
					                <th>Aprovada</th>
					            </tr>
					        </thead>
					        <tfoot>
					            <tr>
					                <th>Data</th>
					                <th>Entrada</th>
					                <th>Saída</th>
					                <th>Horas Totais</th>
					                <th>Horas Extras normais</th>
					                <th>Horas extras Noturnas</th>
					                <th>Aprovada</th>
					            </tr>
					        </tfoot>
					    </table>
        				
        				</div>
        				</div>
        			</div>
        		</div>
        		<div class="janelas" id="feriados" style="display:none;margin:0 auto;"	>
        		<div id="div_tabela_feriados_panel" class="panel panel-primary" style="width:90%;margin:0 auto;">
        				<div class="panel-heading"><h3 class="panel-title">Gerenciar Feriados</h3></div>
	        			<div class="panel-body">
        				<div id="scheduler_feriado"></div>
        				</div>
        		</div>
        		
        		
        		</div>
        		<div class="janelas" id="usuarios_ponto" style="display:none;margin:0 auto;"	>
        			<div id="div_tabela_usuario_ponto_folha_filtros" class="panel panel-default" style="width:90%;margin:0 auto;">
        				<div class="panel-heading">Filtrar & Buscar Espelho de ponto</div>
	        			<div class="panel-body">
	        			<div style="float: left">
		        			<select  id="select_mes_folha_ponto" onchange="atualiza_range_espelho()" class="selectpicker" data-live-search="true" title="Escolha o mes">
		        			<option value="1">Janeiro</option>
		        			<option value="2">Fevereiro</option>
		        			<option value="3">Março</option>
		        			<option value="4">Abril</option>
		        			<option value="5">Maio</option>
		        			<option value="6">Junho</option>
		        			<option value="7">Julho</option>
		        			<option value="8">Agosto</option>
		        			<option value="9">Setembro</option>
		        			<option value="10">Outubro</option>
		        			<option value="11">Novembro</option>
		        			<option value="12">Dezembro</option>
		        			</select>
		        			<select  id="select_func_folha_ponto" class="selectpicker" data-live-search="true" title="Escolha o Funcionário">
		        			
		        			</select>
		        		</div>
		        			<div style="float: left;" id="range_espelho"></div>
		        		<div style="float: right">
		        			<button id="btn_gerar_espelho" onclick="recalcular_HH_BH()" class="btn btn-primary">Gerar Espelho de ponto</button>
		        			<button id="btn_corrigir_espelho" onclick="corrigir_ponto()" class="btn btn-danger">Corrigir Intervalos de Descanso</button>
	        			</div>
	        			</div>
        			</div>
        			<p></p>
        			<div id="div_tabela_usuario_ponto_folha_panel" class="panel panel-primary" style="width:90%;margin:0 auto;">
        				<div class="panel-heading"><h3 class="panel-title">Espelho de Ponto</h3></div>
	        			<div class="panel-body">
        				<div id="div_tabela_usuario_ponto_folha">
					       <table id="tabela_usuario_folha_ponto" class="display" style="width:100%">
					        <thead>
					            <tr>
					                <th>Data</th>
					                <th>Entrada</th>
					                <th>Início Intervalo</th>
					                <th>Fim Intervalo</th>
					                <th>Saída</th>
					                <th>Local</th>
					                <th>Status</th>
					                <th>Ajustar</th>
					                <th>Horas Extras</th>
					            </tr>
					        </thead>
					        <tfoot>
					            <tr>
					                <th>Data</th>
					                <th>Entrada</th>
					                <th>Início Intervalo</th>
					                <th>Fim Intervalo</th>
					                <th>Saída</th>
					                <th>Local</th>
					                <th>Status</th>
					                <th>Ajustar</th>
					                <th>Horas Extras</th>
					            </tr>
					        </tfoot>
					    </table>
        				
        				</div>
        				</div>
        			</div>
        				
        		</div>
        		<div class="janelas" id="usuarios_ponto_analise" style="display:none;margin:0 auto;"	>
        			<div id="div_tabela_usuario_ponto_folha_filtros" class="panel panel-default" style="width:90%;margin:0 auto;">
        				<div class="panel-heading">Filtrar & Buscar Registros de Pontos</div>
	        			<div class="panel-body">
	        			<div style="float: left">
		        			
		        			<select  id="select_func_folha_ponto_analise" class="selectpicker" data-live-search="true" title="Escolha o Funcionário">
		        			
		        			</select>
		        		</div>
		        			<div style="float: left;" id="range_espelho_analise"></div>
		        		<div style="float: right">
		        			<button id="btn_gerar_espelho_analise" onclick="carrega_ponto_analise_usuario()" class="btn btn-primary">Listar Pontos Registrados</button>
		        			
	        			</div>
	        			</div>
        			</div>
        			<p></p>
        			<div id="div_tabela_usuario_ponto_folha_panel_analise" class="panel panel-primary" style="width:90%;margin:0 auto;">
        				<div class="panel-heading"><h3 class="panel-title">Registros de Pontos</h3></div>
	        			<div class="panel-body">
        				<div id="div_tabela_usuario_ponto_folha_analise">
					       <table id="tabela_usuario_folha_ponto_analise" class="display" style="width:100%">
					        <thead>
					            <tr>
					                <th>Data</th>
					                <th>Nome</th>
					                <th>Usuários</th>
					                <th>Entrada</th>
					                <th>Início Intervalo</th>
					                <th>Fim Intervalo</th>
					                <th>Saída</th>
					                <th>Local</th>
					                <th>Status</th>
					                <th>Ajustar</th>
					                <th>Horas Extras</th>
					            </tr>
					        </thead>
					        <tfoot>
					            <tr>
					                <th>Data</th>
					                <th>Nome</th>
					                <th>Usuários</th>
					                <th>Entrada</th>
					                <th>Início Intervalo</th>
					                <th>Fim Intervalo</th>
					                <th>Saída</th>
					                <th>Local</th>
					                <th>Status</th>
					                <th>Ajustar</th>
					                <th>Horas Extras</th>
					            </tr>
					        </tfoot>
					    </table>
        				
        				</div>
        				</div>
        			</div>
        				
        		</div>
        		<div class="janelas" id="aprovacoes" style="display:none;">
        			<div  id="div_tabela_aprovacoes" ></div>
        		</div>
        		<div class="janelas" id="clientes" style="display:none;">
        			<div  id="div_tabela_clientes" ></div>
        		</div><!-- /tabela_clientes -->
        		<div class="janelas" id="Sites" style="display:none;">
        		<input type="hidden" id="flag_substituicao" value="N">
        			<div  id="div_tabela_Sites" ></div>
        		</div><!-- /tabela_Sites -->
        		<div class="janelas" id="usuarios" style="display:none">
        			<div id="div_tabela_usuario"></div>
        		</div>
        		<div class="janelas" id="campos_rollout" style="display:none;">
				<div id="div_tabela_campos_rollout"></div>
			</div>
        		 <div class="janelas" id="senha" style="display:none;">
        	<div class="form-group">
			      <table width=100%>
			      		<tr><td style="padding: 10px"><label class="control-label">Senha Atual:</label></td></tr>
				        <tr><td style="padding: 10px"><input type="password" class="form-control" id="senha_atual"></td></tr>
				        <tr><td style="padding: 10px"><label class="control-label">Nova Senha:</label></td></tr>
				        <tr><td style="padding: 10px"><input type="password" class="form-control" id="nova_senha"></td></tr>
						<tr><td style="padding: 10px"><label class="control-label">Repita a nova Senha:</label></td></tr>
				        <tr><td style="padding: 10px"><input type="password" class="form-control" id="confirma_senha"></td></tr>    
				        <tr><td style="padding: 10px"><label class="control-label"></label></td></tr>  
				        <tr><td style="padding: 10px"><button id="troca_senha_btn" type="button" class="btn btn-info" onclick="trocadeSenha()">Trocar Senha</button></td></tr>	
			      	</table>
			
      </div>
        </div><!-- /senha -->
        
				</div>

			</div><!-- /pusher -->
		</div><!-- /container -->
		<div id="modal_customer_add" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Adicionar Novo Cliente</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      	<table >
      		<tr>
      			<td style="padding: 5px"><label class="control-label label_janelas">Nome Empresa Completo *</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Nome Empresa Resumido *</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">CNPJ Empresa *</label></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><input type="text" class="form-control " id="cliente_nome_completo"></td>
      			<td style="padding: 5px"><input type="text" class="form-control " id="cliente_nome_resumido"></td>
      			<td style="padding: 5px"><input type="text" class="form-control " id="cliente_cnpj"></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><label class="control-label label_janelas">Incrição Estadual</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Endereço</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Telefone</label></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><input type="text" class="form-control" id="cliente_ie"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="cliente_endereco"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="cliente_telefone"></td>
      		</tr>
      	</table>
			<hr>
	      
		</div>
      </div>
      <div class="modal-footer">
        <button id="add_project_btn" type="button" class="btn btn-info" onclick="add_customer()">Adicionar Cliente</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>
<div id="modal_update_lotes" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Atualização de várias linhas</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group"  style="color:black;height: 400px;overflow: auto;">
      	<input id="linhas_selecionadas_lote" type="text" readonly value="" style="color:black">
      	<input id="tipo_campo_lote" type="hidden" readonly value="">
      	
      	<br>
      	<div>
	      	<div>
		      	<table>
		      		<tr><td>Campo</td><td><select class="selectpicker" data-live-search="true" title="Escolha o Campo" id="select_campos_update_lote" onchange="verifica_campo_tipo(this.value)"></select></td></tr>
		      	</table>
	      	</div>
	      	<div id="tipo_campos_update_lote">
	      	</div>
      	</div>
	    </div>
      </div>
      <div class="modal-footer">
        <button id="add_project_btn" type="button" class="btn btn-info" onclick="atualiza_lote()">Atualizar Linhas Selecionadas</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>	
<div id="modal_row_add" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Adicionar Nova Atividade</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group" id="row_add_fields" style="height: 400px;overflow: auto;">
      	
	    </div>
      </div>
      <div class="modal-footer">
        <button id="add_project_btn" type="button" class="btn btn-info" onclick="add_new_row_rollout()">Salvar e Adicionar outra</button><button id="add_project_btn" type="button" class="btn btn-info" onclick="add_new_row_rollout()">Salvar Atividade e Fechar</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>	
<div id="modal_user_add" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Adicionar Novo Usuário</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      	<table >
      		<tr>
      			<td style="padding: 5px"><label class="control-label label_janelas">Nome Completo</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Email</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Perfil</label></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><input type="text" class="form-control" id="usuario_nome_completo"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="usuario_email"></td>
      			<td style="padding: 5px"><select id="usuario_perfil" multiple="multiple"><option value="Administrador">Administrador</option><option value="Financeiro">Financeiro</option><option value="tecnico">Técnico</option></select></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><label class="control-label label_janelas">Usuario</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Matricula</label></td>
      			<td style="padding: 5px"></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><input type="text" class="form-control" maxlength='30' id="usuario_usuario"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="usuario_mtr"></td>
      			<td style="padding: 5px"></td>
      		</tr>
      	</table>
			<hr>
	      
		</div>
      </div>
      <div class="modal-footer">
        <button id="add_project_btn" type="button" class="btn btn-info" onclick="add_user()">Adicionar Usuário</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>

<div id="modal_lanca_ferias" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Lançar Férias para Funcionário - <label id="ferias_nome_usuario"></label></h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      	<p>
      		<select  id="select_ferias_ano_base" class="selectpicker" data-live-search="true" title="Escolha o ano referência das férias">
      			<option>2017</option>
      			<option>2018</option>
      		</select>
      		<br>
      	</p>
      	<p>
			<div id="range_ferias"></div>
			<br>
		<p>
			<div style="color:black;font-size:24px" id="log_dias_ferias"></div>
	      
		</div>
      </div>
      <div class="modal-footer">
        <button id="add_lanca_ferias_btn" type="button" class="btn btn-info">Lançar Férias</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>

<div id="Modal_campos_rollout" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Adicionar Novo Campo ao Rollout</h4>
      </div>
      <div class="modal-body">
      <table width=100%>
      		<tr><td style="padding: 10px"><label class="control-label label_janelas">Nome do Campo</label></td></tr>
	        <tr><td style="padding: 10px"><input type="text" class="form-control" id="nome_campo_rollout"></td></tr>
	        <tr><td style="padding: 10px"><label class="control-label label_janelas">Tipo do Campo</label></td></tr>
	        <tr><td style="padding: 10px" class="label_janelas"><select class="form-control" id="tipo_campo_rollout" onchange="mostra_div(this.value)"><option>Milestone</option><option>Atributo</option></select><br><div id="chk_pgto" class="checkbox"><label class="control-label label_janelas"><input type="checkbox" id="trigger_pagamento" onclick="habilita_campo(this)">Aciona Pagamento</label><br><label class="control-label label_janelas">Percentual Pagamento</label><br><input type="text" class="form-control label_janelas" id="percent_pagamento" disabled="disabled"></div><div id="div_campo_atributo" style="display:none"><select class="form-control label_janelas" id="tipo_atributo_rollout" onchange="mostra_div_lista(this.value)"><option>-</option><option>Texto</option><option>Data</option><option>Numero</option><option>Lista</option></select></div><br><div id="div_campo_atributo_lista" style="display:none"><input type="text" class="form-control label_janelas" id="lista_atributo">Coloque os valores separados por virgula(,).</div></td></tr>
			<tr><td style="padding: 10px"><label class="control-label label_janelas">Descrição</label></td></tr>
	        <tr><td style="padding: 10px"><input type="text" class="form-control" id="desc_campo_rollout"></td></tr>      	
      	</table>
			<hr>
			<script>function habilita_campo(chk){if(chk.checked){document.getElementById('percent_pagamento').disabled=false;}else{document.getElementById('percent_pagamento').disabled=true;}} function mostra_div(texto){if(texto=="Atributo"){document.getElementById('div_campo_atributo').style.display = 'block';document.getElementById('chk_pgto').style.display = 'none';}else{document.getElementById('div_campo_atributo').style.display = 'none';document.getElementById('chk_pgto').style.display = 'block';document.getElementById('div_campo_atributo_lista').style.display = 'none';}}function mostra_div_lista(texto){if(texto=="Lista"){document.getElementById('div_campo_atributo_lista').style.display = 'block';}else{document.getElementById('div_campo_atributo_lista').style.display = 'none';}}</script>
        
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-default" data-dismiss="modal" onclick="add_campo_rollout()">Adicionar</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>  
<div id="modal_site_add" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Adicionar Novo Site</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      	<table >
      		<tr>
      			<td style="padding: 5px"><label class="control-label label_janelas">Site ID *</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Site Name *</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Bairro </label></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><input type="text" class="form-control " id="site_siteid"></td>
      			<td style="padding: 5px"><input type="text" class="form-control " id="site_sitename"></td>
      			<td style="padding: 5px"><input type="text" class="form-control " id="site_bairro"></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><label class="control-label label_janelas">Município</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Endereço</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">UF</label></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><input type="text" class="form-control" id="site_municipio"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="site_endereco"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="site_UF"></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><label class="control-label label_janelas">Latitude *</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Longitude *</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Operadora(VIVO,TIM,OI,CLARO)*</label></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><input type="text" class="form-control" id="site_lat"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="site_lng"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="site_operadora"></td>
      		</tr>
      	</table>
			<hr>
	      
		</div>
      </div>
      <div class="modal-footer">
        <button id="add_project_btn" type="button" class="btn btn-info" onclick="Add_site()">Adicionar</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>

<!-- Modal -->
<div id="modal_carrega_sites" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Carregar Sites</h4>
      </div>
      <div class="modal-body">
      <table >
      		<tr>
      		<td style="padding: 10px"><select class="form-control" required id="cliente_carrega_po"><option>Cliente...</option></select></td>
	        <td style="padding: 10px"><select class="form-control" required id="projeto_carrega_po"><option>Projeto...</option></select></td>
	        </tr>
      	</table>
			<hr>
        <label class="control-label">Selecionar Arquivo</label>
		<input id="input_sites_arq" name="total_input[]" type="file"  multiple>	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>  

<div id="modal_atualiza_usuarios" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Atualizar informações de Usuários</h4>
      </div>
      <div class="modal-body">
     
			<hr>
        <label class="control-label" style="color:black">Selecionar Arquivo</label>
		<input id="input_usuario1_arq" name="total_input[]" type="file"  multiple>	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 
<div id="modal_upload_rollout" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Carregar Rollout</h4>
      </div>
      <div class="modal-body">
     
			<hr>
        <label class="control-label" style="color:black">Selecionar Arquivo</label>
		<input id="input_usuario3_arq" name="total_input[]" type="file"  multiple>	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>
<div id="modal_campos_vistoria2" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">

    <!-- Modal content-->
    
    <div class="modal-content" style="display:block;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Criar novo Template de Vistoria</h4>
      </div>
      <div class="modal-body" style="display:block;">
      <div><button type="button" id="add_item_arvore" class="btn btn-default">Criar Grupo</button><button type="button" class="btn btn-default" data-dismiss="modal" onclick="criar_relatorio()">Criar Item</button><button type="button" class="btn btn-default" id="Remove_item">Remover</button></div>
      <div style="display:inline-block;" id="conteudo_arvore_relatorio">
      		<div id='campos_relatorio' style='float:left;margin-left: 20px;'>
                <ul>
                    <li id='1'>Item</li>
                </ul>
            </div>
    		 <div style='float:right;margin-left: 20px;height:450px;border-style:solid;border-width:1px;border-color: gray;padding:20px'>
    		 	<table>
    		 	<tr>
    		 		<td><label class="control-label" style="color:black">Nome do Item</label></td>
    		 		<td><input id="nome_item_vistoria" class="form-control" type="text"></td>
    		 	</tr>
    		 	<tr>
    		 		<td><label class="control-label" style="color:black">Descrição do Item</label></td>
    		 		<td><input id="desc_item_vistoria" class="form-control" type="text"></td>
    		 	</tr>
    		 	<tr>
    		 		<td><label class="control-label" style="color:black">Tipo do Item</label></td>
    		 		<td><select id="select_tipo_item_vistoria" class="selectpicker" data-live-search="true" title="Escolha o Tipo do Item">
		        			<option value="1">Texto</option>
		        			<option value="1">Número</option>
		        			<option value="1">Foto</option>
		        			<option value="1">Seleção Unica</option>
		        			<option value="1">Multiplas seleções</option>
		        			<option value="1">Data</option>
		        			
						</select>
					</td>
    		 	</tr>
    		 	
    		 	<tr>
    		 	 <td colspan=2 align="right"></td>
    		   </tr>
    		 	</table>
    		 	<div class="modal-footer" style="display:block;">
        			<button type="button" class="btn btn-default" id="Update_item">Salvar</button>
      			</div>
    		 </div>
     </div>
     </div>
      <div class="modal-footer" style="display:block;">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="criar_relatorio()">Criar</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 

<div id="modal_campos_vistoria" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Criar novo Template de Vistoria</h4>
      </div>
      <div class="modal-body">
      <table>
      
      <tr><td><label class="control-label" style="color:black">1. Informe o Nome do Relatório</label></td><td><input id="nome_relatorio"type="text" class="form-control"></td></tr>
      <tr><td><label class="control-label" style="color:black">3. Informe a Descrição do Relatório</label></td><td><input id="desc_relatorio" type="text" class="form-control"></td></tr>
      <tr><td><label class="control-label" style="color:black">4. Informe o Tipo do Relatório</label></td><td><select id="select_tipo_relatorio" class="selectpicker" data-live-search="true" title="Escolha o Tipo do Campo">
		        			<option value="1">Vistoria</option>
		        			<option value="1">Aceitação</option>
		        			<option value="1">Auditoria</option>
		        			<option value="1">Outros</option>
		        			
	</select></td></tr>
	
      </table>
     
     </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="criar_relatorio()">Criar</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>  
<div id="modal_add_usuarios" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Adicionar novos Usuários</h4>
      </div>
      <div class="modal-body">
     
			<hr>
        <label class="control-label" style="color:black">Selecionar Arquivo</label>
		<input id="input_usuario2_arq" name="total_input[]" type="file"  multiple>	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 
 
<div id="modal_rollout_history" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Hitórico de Mudanças</h4>
      </div>
      <div class="modal-body">
            <div id='filtros_rollout_historico'>
            <table style="width:100%">
            <tr><td style="padding: 10px"><input style="color: black" class="form-control" type="text" id="filtro_historico_siteid" placeholder="SiteID"></td><td style="padding: 10px"><select id="select_autor_historico_filtro" class="selectpicker" data-selected-text-format="count" multiple data-live-search="true" title="Escolha o Autor"></select></td><td style="padding: 10px"><select id="select_campo_historico_filtro" data-selected-text-format="count" class="selectpicker" multiple data-live-search="true" title="Escolha o Campo"></select></td></tr>
            <tr></tr>
            </table>
            <div style="padding: 10px"><label style="color: black">Período da Mudança:</label><div id='periodo_hitorico_mudanca'></div></div>
            <div style="padding: 10px"><button class="btn btn-primary" type="button" id="filtro_historico_button" onclick="busca_historico_rollout_filtros()">Filtrar</button></div>
            
            
            </div>
            <div style="z-index: -1;"><div id='grid_historico_rollout' style="width:60%"></div></div>
			
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 

<div id="modal_ajuste_ponto" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Ajuste de registro de Ponto</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      	<table >
      		<tr>
      			<td style="padding: 5px"><label class="control-label label_janelas">Entrada</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Saida</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Local</label></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><div class='input-group date' id='ponto_entrada'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div></td>
      			<td style="padding: 5px"><div class='input-group date' id='ponto_saida'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div></td>
      			<td style="padding: 5px"><select id="select_ajuste_ponto" class="selectpicker" data-live-search="true" title="Escolha o Local">
		        			<option value="1">Spazio</option>
		        			<option value="2">Site</option>
		        			</select>
		        </td>
      		</tr>
      		<tr><td colspan=3 class="control-label label_janelas">Comentários</td></tr>
      		<tr><td colspan=3> <input type="text" id="ajuste_ponto_motivo" class="form-control"></td></tr>
      		
      		
      		
      		
      	</table>
      	<br>
      	<br>
      	<p><label class="control-label label_janelas">Voce tambem pode escolher uma opção abaixo. Nesse caso os valores acima serão descartados.</label></p>
      	<table><tr><td style="padding: 5px"><select id="select_ajuste_ponto_razao" class="selectpicker" data-live-search="true" title="Escolha um motivo">
		        			<option value="1">Folga</option>
		        			<option value="2">Compensação Banco de Horas</option>
		        			</select>
		        </td></tr></table>
      	<input type="hidden" id="aux_ajuste_ponto_dia" value=""/>
      	<script type="text/javascript">
        	    $(function () {
        	        $('#ponto_entrada').datetimepicker({useCurrent: false,showClear: true});
        	        $('#ponto_saida').datetimepicker({
        	            useCurrent: false, //Important! See issue #1075
        	            showClear: true
        	        });
        	        $("#ponto_entrada").on("dp.change", function (e) {
        	            
        	           	if(e.date!=false){
        	            var check = $('#ponto_entrada').data("DateTimePicker").viewDate();
        	  			var month = check.format('M');
        	            var day   = check.format('D');
        	            var year  = check.format('YYYY');
						var aux_d=day+'/'+month+'/'+year;
						//$('#banco_entrada').data("DateTimePicker").viewDate(aux_d+' 18:00:00');
						//$('#banco_saida').data("DateTimePicker").viewDate(aux_d+' 19:00:00');
						$('#ponto_saida').data("DateTimePicker").maxDate(aux_d+' 23:59:59');
        	            $('#ponto_saida').data("DateTimePicker").minDate(aux_d+' 00:00:00');
        	            
        	            //calcula_horas($('#ponto_entrada').data("DateTimePicker").viewDate(),$('#ponto_saida').data("DateTimePicker").viewDate());
        	            }else{
        	            	$('#ponto_saida').data("DateTimePicker").minDate(false);
        	            	$('#ponto_saida').data("DateTimePicker").maxDate(false);
        	            }
        	        });
        	        $("#ponto_saida").on("dp.change", function (e) {
        	        	//console.log($('#banco_entrada').data("DateTimePicker").viewDate());
        	        	if(e.date!=false){
            	            var check = $('#ponto_saida').data("DateTimePicker").viewDate();
            	  			var month = check.format('M');
            	            var day   = check.format('D');
            	            var year  = check.format('YYYY');
    						var aux_d=day+'/'+month+'/'+year;
    						//$('#banco_entrada').data("DateTimePicker").viewDate(aux_d+' 18:00:00');
    						//$('#banco_saida').data("DateTimePicker").viewDate(aux_d+' 19:00:00');
            	            $('#ponto_entrada').data("DateTimePicker").minDate(aux_d+' 00:00:00');
            	            $('#ponto_entrada').data("DateTimePicker").maxDate(aux_d+' 23:59:59');
            	            //calcula_horas($('#ponto_entrada').data("DateTimePicker").viewDate(),$('#ponto_saida').data("DateTimePicker").viewDate());
            	            //calcula_horas(moment(aux_d+' 22:00:00'),$('#banco_saida').data("DateTimePicker").viewDate());    
        	        	}else{
            	            	$('#ponto_entrada').data("DateTimePicker").minDate(false);
            	            	$('#ponto_entrada').data("DateTimePicker").maxDate(false);
            	            }
        	            
        	        });
        	    });
        	</script>
			<hr>
	      
		</div>
      </div>
      <div class="modal-footer">
        <button id="solicita_ajuste_ponto_btn" onclick="solicita_ajuste_ponto()" type="button" class="btn btn-info" data-dismiss="modal">Solicitar Ajuste</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>

     
		<div id="footer">
    Copyright Inovare TI 
    </div>
		<script src="js/classie.js"></script>
		<script src="js/mlpushmenu.js"></script>
		<script>
			new mlPushMenu( document.getElementById( 'mp-menu' ), document.getElementById( 'trigger' ) );
			var mymap = L.map('mapid').setView([-22.9, -43.1], 8);
			 L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoiZmFiaW9zYWxidXF1ZXJxdWUiLCJhIjoiY2pjMjYxYzFoMGludzJxczR1ZWp2aTBpaiJ9.KptnIvfz_61BmkgGIR_ZWA', {
				    attribution: 'MSTP',
				    maxZoom: 18,
				    id: 'mapbox.streets',
				    
				}).addTo(mymap);
			 
			 
			 
		</script>
	</body>
</html>