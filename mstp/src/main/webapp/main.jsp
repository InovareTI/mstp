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
		<link type="text/css" rel="stylesheet" href="css/mfb/mfb.min.css"/>
		
		 <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
         <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
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
	
	<link href="js/plugins/photoviewer/photoviewer.css" rel="stylesheet">
	<link href="js/viewerjs/viewer.css" rel="stylesheet">
	
	<script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
	<script src="js/jquery-confirm.min.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.dataTables.min.css"/>
 <link rel="stylesheet" type="text/css" href=""/>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/rowreorder/1.2.6/js/dataTables.rowReorder.min.js"></script>
	
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/dataTables.buttons.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.colVis.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.flash.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.html5.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.print.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/rowgroup/1.1.0/css/rowGroup.dataTables.min.css" />
	<script src="https://cdn.datatables.net/rowgroup/1.1.0/js/dataTables.rowGroup.min.js" type="text/javascript"></script>
	
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
  	<script src="js/bootstrap-datetimepicker.min.js"></script>
   	<script src="js/locale/pt-br.js"></script>
   	<script src="js/easytimer/easytimer.min.js"></script>
  	<script type="text/javascript" src="js/w2ui-1/w2ui-1.4.3.min.js"></script>
  	<script type="text/javascript" src="js/jqwidgets/jqxcore.js"></script>
  	<script type="text/javascript" src="js/jqwidgets/jqxloader.js"></script>
  	<script type="text/javascript" src="js/jqwidgets/jqxdatetimeinput.js"></script>
  	<script type="text/javascript" src="js/jqwidgets/jqxdropdownbutton.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxmenu.js"></script>
    
    <script defer src="https://use.fontawesome.com/releases/v5.13.1/js/all.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.filter.js"></script>
     <script type="text/javascript" src="js/jqwidgets/jqxtreegrid.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxtreemap.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxtree.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdata.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.sort.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.columnsresize.js"></script> 
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.grouping.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdata.export.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.export.js"></script>
   <script type="text/javascript" src="js/mfb/mfb.min.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdate.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxribbon.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxlayout.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.pager.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.edit.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.aggregates.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpanel.js"></script>
     <script type="text/javascript" src="js/jqwidgets/jqxsplitter.js"></script>
     <script type="text/javascript" src="js/jqwidgets/jqxtabs.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdropdownlist.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxcombobox.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxcalendar.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxnumberinput.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpasswordinput.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxtooltip.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxwindow.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxtoolbar.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdocking.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxexpander.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxscheduler.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxscheduler.api.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxinput.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdragdrop.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxsortable.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxkanban.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpivot.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpivotdesigner.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpivotgrid.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxradiobutton.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxform.js"></script>
    
    <script type="text/javascript" src="js/jqwidgets/globalization/globalize.js"></script>
    <script type="text/javascript" src="js/jqwidgets/localization.js"></script>
    <link rel="stylesheet" type="text/css" href="js/dhtmlx/pivot.css" />
    <script type="text/javascript" src="js/dhtmlx/pivot.js"></script>
    <script src="js/plugins/photoviewer/photoviewer.js"></script>
   
    
    <script src="js/viewerjs/viewer.js"></script>
    <script src="js/viewerjs/jquery-viewer.js"></script>
    
    <script src="js/Chart.min.js"></script>
  <script src="js/utils.js"></script>
	<script type="text/javascript" src="js/starter.js"></script>
	<script type="text/javascript" src="js/portal.js"></script>
	<script type="text/javascript" src="js/rollout.js"></script>
	<script type="text/javascript" src="js/senha.js"></script>
	<script type="text/javascript" src="js/project.js"></script>
	<script type="text/javascript" src="js/clientes.js"></script>
	<script type="text/javascript" src="js/usuarios.js"></script>
	<script type="text/javascript" src="js/POControl.js"></script>
	<script type="text/javascript" src="js/mapa_controle.js"></script>
	<script type="text/javascript" src="js/Site.js"></script>
	<script type="text/javascript" src="js/banco_horas.js"></script>
	<script type="text/javascript" src="js/permits.js"></script>
	<script type="text/javascript" src="js/Relatorios.js"></script>
	<script type="text/javascript" src="js/Ponto.js"></script>
	<script type="text/javascript" src="js/TemplateVistoria.js"></script>
	<script type="text/javascript" src="js/scheduler.js"></script>
	<script type="text/javascript" src="js/importacoes.js"></script>
	<script type="text/javascript" src="js/pivot.js"></script>
	<script type="text/javascript" src="js/checklist.js"></script>
	<script type="text/javascript" src="js/lpu.js"></script>
	<script type="text/javascript" src="js/despesas.js"></script>
	<script type="text/javascript" src="js/orcamento.js"></script>
	<script type="text/javascript" src="js/Ticket.js"></script>
	
<script src='https://api.mapbox.com/mapbox-gl-js/v1.12.0/mapbox-gl.js'></script>
<link href='https://api.mapbox.com/mapbox-gl-js/v1.12.0/mapbox-gl.css' rel='stylesheet' />


	<link rel="manifest" href="/manifest.json" />
	
<script src="https://cdn.onesignal.com/sdks/OneSignalSDK.js" async=""></script>
<script>
  var OneSignal = window.OneSignal || [];
  OneSignal.push(function() {
    OneSignal.init({
      appId: "ae9ad50e-520d-436a-b0b0-23aaddedee7b",
      autoResubscribe: true,
      notifyButton: {
        enable: true,
      },
    });
  });
</script>

	
     <style>
     .bulletshistorico {
  list-style: none;
}

  .bulletshistorico li::before {
  content: "\2022";
  color: red;
  font-weight: bold;
  display: inline-block; 
  width: 1em;
  margin-left: -1em;
}
        .jqx-kanban-item-color-status {
            width: 100%;
            height: 25px;
            border-top-left-radius: 3px;
            border-top-right-radius: 3px;
            position:relative;
            margin-top:0px;
            top: 0px;
        }
        .jqx-kanban-item {
            padding-top: 0px;
        }
        .jqx-kanban-item-text {
            padding-top: 6px;
        }
        .jqx-kanban-item-avatar {
            top: 9px;
        }
        .jqx-kanban-template-icon {
            position: absolute;
            right: 3px;
            top:12px;
        }
    </style>
	<style>
	
	
	
	.imgRollout {
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 1px;
 
}

.imgRollout:hover {
  box-shadow: 0 0 2px 1px rgba(0, 140, 186, 0.5);
}
	
	.green {
  color: black !important;
  background-color: #b6ff00 !important;
}

.yellow {
  color: black !important;
  background-color: yellow !important;
}

.orange {
  color: black !important;
  background-color: #FF7F50 !important;
}

.red {
  color: black !important;
  background-color: #e83636 !important;
}
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
	#tabela_detalhe_sites{
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
		#map_full { position:fixed;height:100%; width:550px; }
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
							 <li class="icon icon-arrow-left">
								<a  href="#"><span style="padding:5px"><i class="fas fa-hand-holding-usd"></i></span>Financeiro</a>
								<div class="mp-level">
									<h2 ><span style="padding:5px"><i class="fas fa-hand-holding-usd"></i></span>Financeiro</h2>
									<ul>
										<li><a href="#" onclick="menu('PO')"><span style="padding:5px"><i class="fas fa-hand-holding-usd fa-sm"></i></span><span>Gestão de PO's</span></a></li>
										<li><a href="#" onclick="menu('orcamento')"><span style="padding:5px"><i class="fas fa-hand-holding-usd fa-sm"></i></span><span>Orçamentos</span></a></li>
										<li><a href="#" onclick="menu('lpu')"><span style="padding:5px"><i class="fas fa-hand-holding-usd fa-sm"></i></span><span>LPU</span></a></li>
										</ul>
								</div>
							</li>
							 <li class="icon icon-arrow-left">
								<a class="icon icon-settings" href="#">RH</a>
								<div class="mp-level">
									<h2 class="icon icon-settings">Recursos Humanos</h2>
									<ul>
										<li><a class="icon icon-calendar" href="#" onclick="menu('usuarios_ponto')">Folha de Ponto</a></li>
										<li><a class="icon icon-calendar" href="#" onclick="menu('usuarios_ponto_analise')">Análise de Ponto</a></li>
										<li><a class="icon icon-calendar" href="#" onclick="menu('justificativas')">Justificativas de Ponto</a></li>
										<li><a class="icon icon-calendar" href="#" onclick="menu('faltas_relatorio')">Relatório de Faltas</a></li>
										<li><a class="icon icon-calendar" href="#" onclick="menu('calendariofff')">Calendário de Folgas,Feriados e Férias</a></li>
										<li><a class="icon icon-calendar" href="#" onclick="menu('usuarios_banco_hh')">Banco de Horas</a></li>
										<li><a class="icon icon-calendar" href="#" onclick="menu('usuarios_extra_hh')">Horas Extras</a></li>
										<li><a class="icon icon-user" href="#" onclick="menu('feriados')">Feriados</a></li>
										<li><a class="icon icon-user" href="#" onclick="menu('...')">Calendário de Férias</a></li>
										<li><a class="icon icon-user" href="#" onclick="menu('usuarios')">Gestão de Usuários</a></li>
									</ul>
								</div>
							</li>
							
							<li><a class="icon icon-display" href="#" onclick="menu('permits')">Aquisições(PERMITS)</a></li>
							<li><a href="#" onclick="menu('aprovacoes');carrega_aprovacoes_hh();"><i class="fas fa-list-ul"></i>&nbsp;&nbsp;Aprovações</a></li>
							
							<li><a class="icon icon-paperplane" href="#" onclick="menu('rollout')">Rollout</a></li>
							<li><a class="icon icon-paperplane" href="#" onclick="menu('ticketWindow')">Tickets</a></li>
							<li><a class="icon icon-world" href="#" onclick="menu('mapa_Operacional')">Mapa Operacional</a></li>
							 <li class="icon icon-arrow-left">
								<a class="icon icon-settings" href="#">Gestão de Custos</a>
								<div class="mp-level">
									<h2 class="icon icon-settings">Gestão de Custos</h2>
									<ul>
										<li><a class="icon icon-calendar" href="#" onclick="menu('equipe_diarias')">Díárias de Equipes</a></li>
									</ul>
								</div>
							</li>
							<li class="icon icon-arrow-left">
								<a class="icon icon-note" href="#">Relatórios</a>
								<div class="mp-level">
									<h2 class="icon icon-settings">Relatórios</h2>
									<ul>
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
							
                            <li class="icon icon-arrow-left">
								<a class="icon icon-settings" href="#">Configurações</a>
								<div class="mp-level">
									<h2 class="icon icon-settings">Configurações</h2>
									<ul>
										<li><a class="icon icon-key" href="#" onclick="menu('senha')">Senha</a></li>
										<li><a class="icon icon-user" href="#" onclick="menu('permitsCampos')">Aquisições&Permissões</a></li>
										<li><a class="icon icon-user" href="#">Portal</a></li>
										<li class="icon icon-arrow-left"><a class="icon icon-male" href="#" onclick="menu('clientes')">Clientes</a></li>
										<li class="icon icon-arrow-left"><a class="icon icon-shop" href="#" onclick="menu('projetos')">Projetos</a></li>
										<li class="icon icon-arrow-left">
											<a class="icon icon-note" href="#">Rollout</a>
											<div class="mp-level">
												<h2 class="icon icon-settings">Configurações rollout</h2>
												<ul>
													<li><a class="icon icon-user" href="#" onclick="menu('campos_rollout')">Campos do Rollout</a></li>
													<li><a class="icon icon-user" href="#" onclick="menu('regras_rollout')">Regras de Automação</a></li>
													<li><a class="icon icon-key" href="#" onclick="menu('arvore_rollout_conf')">Árvore de Rollout</a></li>
													<li><a class="icon icon-user" href="#" onclick="menu('vistoria_campos')">Checklist</a></li>
													<li><a class="icon icon-user" href="#" onclick="menu('importa_rollout')">Importação do Rollout</a></li>
												</ul></div></li>
										
										
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
				            <li>Tickets</li>
				            <li>Usuários</li>
				            <li>Rollout</li>
				            <li>Financeiro</li>
	        			</ul>
	        			<div>
	        			<div style="display:block;width:100%">
	        			<div style="padding:15px;width:24%;float:left">
							<div class="card" style="border-radius: 25px;box-shadow: 5px 5px 5px rgba(0,0,0,0.5);">
		    					<div class="container" style="text-align: center;align-content:center;background: #808080;height: 150px; border-radius: 25px;">
		    		  				<div id="card_po_sem_ticket"><h3><b>num_po_sem_ticket</b></h3></div>
		    		    			<br><p>Itens de PO sem Ticket</p>
		    		  			</div>
		    				</div>
		    			</div>	
		    			<div style="padding:15px;width:24%;float:left">
		    				<div class="card" style="border-radius: 25px;box-shadow: 5px 5px 5px rgba(0,0,0,0.5);">
		    					<div class="container" style="text-align: center;align-content:center;background: #808080;height: 150px; border-radius: 25px;">
		    		  				<div id="card_ticket_abertos"><h3><b>num_tickets_Abertos</b></h3></div>
		    		    			<br><p>Tickets Abertos</p>
		    		  			</div>
		    				</div>
						</div>
						<div style="padding:15px;width:24%;float:left;border-radius: 25px">
		    				<div class="card" style="border-radius: 25px;box-shadow: 5px 5px 5px rgba(0,0,0,0.5);">
		    					<div class="container" style="text-align: center;align-content:center;background: #808080;height: 150px; border-radius: 25px;">
		    		  				<div id="card_ticket_andamento"><h3><b>num_tickets_Abertos</b></h3></div>
		    		    			<br><p>Tickets em Execução</p>
		    		  			</div>
		    				</div>
						</div>
						<div style="padding:15px;width:24%;float:left;">
		    				<div class="card" style="height: 150px; border-radius: 25px;box-shadow: 5px 5px 5px rgba(0,0,0,0.5);">
		    					<div class="container" style="text-align: center;align-content:center;background: #73AD21;height: 150px; border-radius: 25px;">
		    		  				<div id="card_ticket_finalizados"><h3><b>num_tickets_Abertos</b></h3></div>
		    		    			<br><p>Tickets Finalizados</p>
		    		  			</div>
		    				</div>
						</div>
						</div>
						<br>
						<div style="display:block;width:100%">
							<div id="canvas-holder" style="float:left;width:50%">
								<canvas id="chart-area" style="width:100px"></canvas>
							</div>
							<div id="canvas-holder2" style="float:left;width:40%">
								<canvas id="chart-area2"></canvas>
							</div>
							
						</div>
						<br>
						<div style="text-align:center;width:100%">
						<div id="grid_ticket_content" style="margin:auto;display: inline-block;">
								<div id="grid_ticket_chart"></div>
						</div>
						</div>
						
						</div>
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
					            <div style="overflow: hidden;width:400px">
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
					    </div><!-- fim da  -->
					    <div>
							<div id='jqxWidget_rollout' style="width:100%;margin: auto;float: left">
					        <div id="docking_rollout" style="width:90%">
					            <div style="overflow: hidden;width:50%">
					                <div id="window5" style="height: 350px">
					                    <div><div>
						                    	<div style="float:left" id='from'></div>
						                    	<div style="float:right" >
						                    		<button class="btn" data-toggle="modal" data-target="#seleciona_rollout_modal">Selecionar Rollout</button>
						                    	</div>
					                    	</div>
					                    </div>
					                    <div id='grafico_container1_rollout'><div class="loader"></div></div>
					                </div>
					                <div id="window6" style="height: 350px">
					                    <div><div id='from2'></div></div>
					                   
					                    <div id='grafico_container2_rollout'><div class="loader"></div></div>
					                </div>
					            </div>
					            <div style="overflow: hidden;width:50%">
					            <div id="window9" style="height: 350px">
					                    <div>Evolução de Milestone</div>
					                   
					                    <div id='grafico_container3_rollout'><div class="loader"></div></div>
					                </div>
					                <div id="window10" style="height: 350px">
					                    <div>Planejamento por Período
					                    <div> 
						                    <div style='float:left' id='jqxRadioButton_rollout'>Diário</div>
						                    <div style='float:left' id='jqxRadioButton2_rollout'><span>Semanal</span></div>
	        								<div style='float:left' id='jqxRadioButton3_rollout'><span>Mensal</span></div>
        								</div>
        								</div>
					                   
					                    <div id='grafico_container4_rollout'><div class="loader"></div></div>
					                </div>
					            </div>
					         </div>
					         </div>
						</div><!-- Fim da Tab Rollout -->
						<div>
						<div id='jqxWidget_PO' style="width:100%;margin: auto;float: left">
					        <div id="docking_PO" style="width:90%">
					            <div style="overflow: hidden;width:50%">
					                <div id="window7" style="height: 350px">
					                    <div>Itens de PO</div>
					                   
					                    <div id='grafico_container1_PO'><div class="loader"></div></div>
					                </div>
					                <div id="window8" style="height: 350px">
					                    <div>Valor Acumulado por Status de Item de PO</div>
					                   
					                    <div id='grafico_container2_PO'><div class="loader"></div></div>
					                </div>
					            </div>
					            <div style="overflow: hidden;width:50%">
					                <div id="window11" style="height: 350px">
					                    <div>Previsao de Faturamento</div>
					                   
					                    <div id='grafico_container3_PO'><div class="loader"></div></div>
					                </div>
					                <div id="window12" style="height: 350px">
					                    <div>Faturado x Realizado</div>
					                   
					                    <div id='grafico_container4_PO'><div class="loader"></div></div>
					                </div>
					            </div>
					         </div>
					         </div>
						</div><!-- Fim da Tab PO -->
					   </div>
					</div> <!-- /portal -->
					<div class="janelas" id="mapa_Operacional" style="display:none;">
						<div id='jqxtabs_mapaOperacional'>
							<ul style='margin-left: 20px;'>
				            	<li>Sites & Atividades</li>
				            	
	        				</ul>
	        				<div>
			        			<div id='jqxWidget_operacional' style="width:100%;margin: auto;float: left">
							        <div id="docking_operacional" style="width:1200px">
							            <div style="overflow: hidden;width:400px">
							                <div id="op_window0" style="height: 40%">
							                    <div style="width:100%;text-align:center" >
								                    <div style="float:left;width:190px;">Alocação de recursos em Projeto</div>
								                    <div style="display: inline-block;margin:0 auto;width:300px;"></div>
								                    <div style="float:right;width:20px;cursor: pointer;"><i class="fas fa-filter" data-toggle="modal" data-target="#modal_filtros_mapa_operacional" onclick="atualiza_opt_filtro(3)"></i></div>
							                    </div>
							                   
							                    <div id="grid_func_mapaOperacional_AtividadeProjetosUsuario"><div id="jqxLoader_grid_func_mapaOperacional_AtividadeProjetosUsuario"></div></div>
							                </div>
							                <div id="op_window1" style="height: 40%">
							                    <div style="width:100%;text-align:center" >
								                    <div style="float:left;width:190px;">Atividade Projeto</div>
								                    <div style="display: inline-block;margin:0 auto;width:300px;"></div>
								                    <div style="float:right;width:20px;cursor: pointer;"><i class="fas fa-filter" data-toggle="modal" data-target="#modal_filtros_mapa_operacional" onclick="atualiza_opt_filtro(4)"></i></div>
							                    </div>
							                    <div id="grid_func_mapaOperacional_AtividadeProjetos"><div id="jqxLoader_grid_func_mapaOperacional_AtividadeProjetos"></div></div>
							                </div>
							            </div>
							            <div style="overflow: hidden;width:400px">
							                
							                <div id="op_window3" style="height: 40%; ">
							                    <div style="width:100%;text-align:center" >
								                    <div style="float:left;width:190px;">Atividade por Equipe</div>
								                    <div style="display: inline-block;margin:0 auto;width:300px;"></div>
								                    <div style="float:right;width:20px;cursor: pointer;"><i class="fas fa-filter" data-toggle="modal" data-target="#modal_filtros_mapa_operacional" onclick="atualiza_opt_filtro(1)"></i></div>
							                    </div>
							                    <div>
							                   		<div id="jqxLoader_grid_func_mapaOperacional"></div>
							                    	<div id="grid_func_mapaOperacional"></div>
							                   </div>
							                </div>
							                <div id="op_window2" style="height: 40%; ">
							                    <div style="width:100%;text-align:center" >
								                    <div style="float:left;width:190px;">Escopo de Projeto</div>
								                    <div style="display: inline-block;margin:0 auto;width:300px;"></div>
								                    <div style="float:right;width:20px;cursor: pointer;"><i class="fas fa-filter" data-toggle="modal" data-target="#modal_filtros_mapa_operacional" onclick="atualiza_opt_filtro(2)"></i></div>
							                    </div>
							                    <div id="grid_func_mapaOperacional_SitesProjetos"><div id="jqxLoader_grid_func_mapaOperacional_SitesProjetos"></div></div>
							                </div>
							            </div>
							            
							            <div style="overflow: hidden;height:800px;width:400px">
							                <div id="op_window4" style="height: 800px; width:600px">
							                	<div>Rollout Sites e MSTP Mobile tracker</div>
							                    <div>
								                    <div id="map_full"><div class="loader"></div></div>
													
												</div>
							                
							                </div>
							            </div>
							        </div><!--fecha docking-->
							    </div><!--fecha docking-->
	        				</div><!-- /fim da primeira tabMapaOperacinal -->
	        				
						</div><!-- /tabMapaOperacinal -->
					</div><!-- /mapaOperacinal -->
					<div class="janelas" id="permitsCampos" style="display:none;">
						<div id="div_tabela_campos_permits"></div>
					</div>
					<div class="janelas" id="orcamento" style="display:none;width:100%;height: 100%">
						<div class="panel panel-primary" style="width:90%;margin:0 auto">
			        				<div class="panel-heading">Controle de Orçamentos</div>
				        				<div class="panel-body">
					        				<button class="btn btn-primary" data-toggle="modal" data-target="#modal_cria_orcamento">+ Orçamento</button>
					        				<button class="btn btn-primary" >Download(emDesenvolvimento)</button>
					        				<button class="btn btn-primary" >Edição(emDesenvolvimento)</button>
					        				<button class="btn btn-primary" onclick="GerarDocumentoOrcamento()" >Gerar Documento</button>
					        				<button class="btn btn-danger" >Remoção de Orçamento(emDesenvolvimento)</button>
				        				</div>
			        		</div>
			        		<div class="panel panel-primary" style="width:90%;height:75%;margin:0 auto">
		        				<div class="panel-heading">Tabela de Orçamentos</div>
			        				<div class="panel-body">
				        				<div id="div_grid_orcamento" style="height:100%;"></div>
			        				</div>
		        		</div>
					</div>
					<div class="janelas" id="lpu" style="display:none;width:100%;height: 100%">
					<div class="panel panel-primary" style="width:90%;margin:0 auto">
		        				<div class="panel-heading">Controles LPU</div>
			        				<div class="panel-body">
				        				<button class="btn btn-primary" data-toggle="modal" data-target="#lpu_upload_Modal">Upload de LPU</button>
				        				<button class="btn btn-primary" id="btnDownloadLPU" onclick="DownloadLPU()">Download de LPU</button>
				        				<button class="btn btn-primary" data-toggle="modal" data-target="#Modal_AddItemLPU">Novo Item</button>
				        				<button class="btn btn-danger" id="btnRemoveItemLpu" onclick="RemoveItemLPU()">Remoção de Item</button>
				        				<a id="template_importar_site" style="color:blue" href="templates/templateLPUImportacao.xlsx">Template(importação)</a>
			        				</div>
		        		</div>
		        		<div class="panel panel-primary" style="width:90%;height:75%;margin:0 auto">
		        				<div class="panel-heading">LPU</div>
			        				<div class="panel-body">
				        				<div id="div_grid_lpu" style="height:100%;"></div>
			        				</div>
		        		</div>
					</div>
					
					
					<div class="janelas" id="mapa_central" style="display:none;">
					
            
											
					
						<script>
						
						function inicializa_mapa_full(opt){
							$('#map_full').html('');
							mapboxgl.accessToken = 'pk.eyJ1IjoiZmFiaW9zYWxidXF1ZXJxdWUiLCJhIjoiY2tnamJldTI5MDg0djJ0bXNyZGVyenozeCJ9.qmVVng-6k8FdjiJRUde_iw';
						map = new mapboxgl.Map({
						    container: 'map_full',
                            style: 'mapbox://styles/mapbox/streets-v11', 
						    zoom: 3,
						    center: [-51.549, -11.811]
						});
						map.on('load', function () {
							if(opt==1)				{load_site_markers_mapa_central_rollout();}else{load_site_markers_mapa_central();}
							
						});
						
						map.addControl(new mapboxgl.FullscreenControl());
						map.addControl(new mapboxgl.NavigationControl());
						
						
						}
						</script>
					</div>
					<div class="janelas" id="importa_rollout" style="display:none;width:100%;height:100%;">
					<button class="btn btn-success" data-toggle="modal" data-target="#modal_upload_rollout">Importar Rollout</button>
					</div>
					
					<div class="janelas" id="equipe_diarias" style="display:none;width:100%;height:100%;">
					
						<div class="panel panel-primary" style="width:90%;margin:0 auto">
		        				<div class="panel-heading">Espelho de Diárias</div>
			        				<div class="panel-body">
				        				<div style="float: left;">
				        					<select id="select_func_diaria" class="selectpicker" data-live-search="true" title="Escolha o Funcionário" onchange="CarregaDespesas()"></select>
				        			  	</div>
				        			  	<div style="float: left;" id="range_despesas"></div>
				        			  	<div style="float: right">
				        			  			<button class="btn btn-primary" onclick="relatorioDespesa()">Novo Espelho de diária</button>
				        			  	</div>
			        				</div>
		        		</div>
		        		<div class="panel panel-primary" id="divcontentdespesa" style="width:90%;margin:0 auto">
		        				<div class="panel-heading">Resumo de Despesas</div>
			        				<div class="panel-body">
			        					<div id="grid_resumo_despesas"></div>
			        				</div>
		        		</div>
					
					</div>
					
					<div class="janelas" id="ticketWindow" style="display:none;width:100%;height:100%;background:lightgray;">
								
								 <div id="TicketToolBar"></div>
								 <div id="TicketKanban"></div> 
								
								<ul class="mfb-component--br mfb-slidein" data-mfb-toggle="hover" data-mfb-state="closed">
								  <li class="mfb-component__wrap">
								    <!-- the main menu button -->
								    <a data-mfb-label="Operações" class="mfb-component__button--main">
								      <!-- the main button icon visibile by default -->
								      
								      <i class="mfb-component__main-icon--resting"><i class="fas fa-plus"></i></i>
								      <!-- the main button icon visibile when the user is hovering/interacting with the menu -->
								      <i class="mfb-component__main-icon--active"><i class="fas fa-asterisk"></i></i>
								    </a>
								    <ul class="mfb-component__list">
								      <!-- a child button, repeat as many times as needed -->
								      <li>
								        <a href="#" data-toggle="modal" data-target="#modal_new_Ticket" data-mfb-label="Novo Ticket" class="mfb-component__button--child">
								          <i class="mfb-component__child-icon"><i class="fas fa-tasks"></i></i>
								        </a>
								      </li>
								      <li>
								        <a href="#" data-toggle="modal" data-target="#modal_user_add" class="mfb-component__button--child" data-mfb-label="Novo Usuário">
								          <i class="mfb-component__child-icon"><i class="fas fa-user-plus "></i></i>
								        </a>
								      </li>
								    </ul>
								  </li>
								</ul>
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
									                <div id="divPivotGridDesigner" style="height: 400px; width: 250px;">
									                </div>
									            </td>
									            <td>
									                <div id="divPivotGrid" style="height: 400px; width: 550px;">
									                </div>
									            </td>
									        </tr>
									    </table>
								    </div>
		        				</div>
	        		</div>
					</div>
					<div class="janelas" id="permits" style="display:none;width:100%;height:100%;">
						<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">Gestão de Aquisições e Licenças</div>
		        				<div class="panel-body">
		        				 	<div id="mainSplitter">
								            <div class="splitter-panel">
								                <div id="gridMaster_permits"></div>
								            </div>
								            <div class="splitter-panel">
								            	<div id='tabs_permits'>
										            <ul>
										                <li style="margin-left: 30px;">Cadastro</li>
										                <li>Aquisição</li>
										                <li>Licenciamento</li>
										                <li>Financeiro</li>
										                <li>Histórico</li>
										            </ul>
									            	<div>
									            		<div id="divCamposCadastro"></div>
									            		<div class="modal-footer">
        														<button id="add_project_btn" type="button" class="btn btn-info" onclick="add_cadastroPermits()">Salvar Novo Cadastro</button>
      													</div>
									            	</div>
									            	<div><div id="divCamposAquisicao"></div></div>
									            	<div><div id="divCamposLicenciamento"></div></div>
									            	<div><div id="divCamposFinanceiro"></div></div>
									            	<div><div id="divCamposHistórico"></div></div>
									            </div>
								            
								            </div>
        							</div>
		        				</div>
	        			</div>
					</div>
					<div class="janelas" id="justificativas" style="display:none;width:100%;height:100%;">
						<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">Justificativas de Ponto</div>
		        				<div class="panel-body">
		        				<div id='justificativa_div_inserir'><button type="button" class="btn btn-info" data-toggle="modal" data-target="#modal_add_justificativa">Nova Justificativa</button><button type="button" class="btn btn-danger" onclick="remover_justificativa()">Remover Justificativa</button></div>
		        				</div>
	        			</div>
	        			<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">Tabela de Justificativas</div>
		        			<div class="panel-body" id="container_tabela_justificativas">
		        				<div id='grid_justificativas'>
			        				
			        			</div>
		        			</div>
	        		</div>
					</div>
					
					<div class="janelas" id="vistoria_campos" style="display:none;width:100%;height:100%;">
					<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">Definir novo Relatório de CheckList</div>
		        				<div class="panel-body">
		        				<div id='novo_rel_vistoria'><button type="button" class="btn btn-info" data-toggle="modal" data-target="#modal_campos_vistoria">Novo Relatório</button></div>
		        				</div>
	        		</div>
					<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">Definições de relatórios de checklist</div>
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
	        		<div class="janelas" id="PO" style="display:none;width:100%;height:100%;"	>
	        	
        			<div id="div_tabela_po"><button class="btn btn-lg btn-warning"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> Carregando...</button></div>
        	
       
	        		</div>
	        		<div class="janelas" id="ItemPO" style="display:none;width:100%;height:100%;"	>
	        	
        			<div id="div_tabela_Itempo"><button class="btn btn-lg btn-warning"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> Carregando...</button></div>
        	
       
	        		</div>
	        		<div class="janelas" id="arvore_rollout_conf" style="display:none;width:100%;height:100%;"	>
	        					 <div id='jqxWidget'>
        						<div style='float: left;'>
	        					<div id='jqxTree_rollout_conf' style='float: left; margin-left: 20px;'>
                
            						</div>
             				<div style='margin-left: 60px; float: left;'>
				             <div style='margin-top: 10px;'>
				                    <input type="button" id='AddAfter' value="Adcionar Depois" />
				                </div>
				                <div style='margin-top: 10px;'>
				                    <input type="button" id='AddBefore' value="Adicionar Antes" />
				                </div>
				                 <div style='margin-top: 10px;'>
				                    <input type="button" id='Remove' value="Remover" />
				                </div>
				                 <div style='margin-top: 10px;'>
				                 
				                 	<input type="text" id="input_item_tree_conf"/>
                    				<input type="button" id='Update' value="Atualizar" />
                				</div>
                				<br>
                				<br>
                				<div style='margin-top: 10px;'>
				                    <input type="button" id='SaveTree' value="Salvar Alterações" />
				                </div>
				               
				             </div>
				             
				            </div></div>
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
									    				<small id="quadro_1_1" class="stat-label"><b>Total de Atividades</b></small>
									    				<br>
									    				<h4 id="quadro_1_2">0</h4>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 10px;"></div>
									    		
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
									    				<img src="img/list1.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small id="quadro_2_1" class="stat-label"><b>Qtde. PO Principal</b></small>
									    				<br>
									    				<h4 id="quadro_2_2">0</h4>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 10px;"></div>
									    		
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
									    				<img src="img/gnucash.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small id="quadro_3_1"class="stat-label" ><b>Volume Financeiro</b></small>
									    				<br>
									    				<h4 id="quadro_3_2" >0</h4>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 10px;"></div>
									    		
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
									    				<small class="stat-label" id="quadro_4_1"><b>Total de Sites</b></small>
									    				<br>
									    				<h4 id="quadro_4_2">0</h4>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		
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
									    			<div  class="col-xs-8" >
									    				<small id="quadro_5_1" class="stat-label">Total de Recursos</small>
									    				<br>
									    				<h4 id="quadro_5_2">0</h4>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    	
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
						 			 <div id="splitter" >
						 			 	<div><label style="color:gray">Selecione o Rollout desejado</label><div id='jqxTree_rollout' style='float: left;'></div>
						 			 	<div id='jqxMenu_tree_rollout'>
									            <ul>
									                <li>Renomear</li>
									                
									            </ul>
									        </div>
						 			 	</div>
	        						 	<div id="container_rollout"><div id="jqxgrid"></div></div>
	        						</div>
    							</div>
					 		</div>
        		</div><!-- /rollout -->
        		<div class="janelas" id="checklist_review" style="display:none;margin:0 auto;">
        		
        			<div id="div_checklist_dashboard" class="panel panel-default" style="width:90%;margin:0 auto;">
        				<div class="panel-heading">Filtrar & Buscar Faltas</div>
	        			<div class="panel-body">
	        				<label>Colocar resumor de numeors</label>
	        			</div>
	        		</div>
        			<div id="div_checklist_tabela" class="panel panel-primary" style="width:90%;margin:0 auto;">
        				<div class="panel-heading">Tabela de Faltas</div>
	        			<div class="panel-body">
	        			<div id='jqxWidget'>
		        			<div id="splitter_checklist" >
							 			 	<div class="splitter-panel" id="container_gridChecklist"><div id="gridChecklist"></div></div>
		        						 	<div class="splitter-panel" id="container_dados_div_review"><div id="dados_div_review" style="overflow: scroll;"></div></div>
		        			</div>
	        			 </div>
	        			</div>
	        		</div>
        		</div>
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
        		<div class="panel panel-default" style="width:90%;margin:0 auto">
	        				<div class="panel-heading">Módulos</div>
		        				<div class="panel-body">
		        			
        				<table id="tabela_assinaturas" class="display" style="color:black;width:100%">
							        <thead>
							            <tr>
							                <th>Nome do Módulo</th>
							                <th>Descrição</th>
							                <th>Data Assinatura</th>
							                <th>Valor</th>
							                <th>termo de Aceite/th>
							                <th>Data do Aceite</th>
							                <th>Assinar</th>
							            </tr>
							        </thead>
							        <tfoot>
							            <tr>
							               <th>Nome do Módulo</th>
							                <th>Descrição</th>
							                <th>Data Assinatura</th>
							                <th>Valor</th>
							                <th>termo de Aceite/th>
							                <th>Data do Aceite</th>
							                <th>Assinar</th>
							            </tr>
							        </tfoot>
							    </table>
							    	
		        				</div>
	        		</div>
        		</div>
        		</div>
        		
        		<div class="janelas" id="usuarios_banco_hh" style="display:none;margin:0 auto;"	>
        		
	        		<div id="resumo_banco de Horas" class="panel panel-default" style="width:90%;margin:0 auto;">
	        				<div class="panel-heading">Resumo Banco de Horas </div>
		        				<div class="panel-body">
		        				<div style="width:50%;float:left;"><h3 style="color:gray;float:left">Total Disponivel: &nbsp;&nbsp;&nbsp;&nbsp;</h3>
		        					<h3 style="float:left"><a id="exibe_hh12" style="color:#1925f6" href="#" onclick="carrega_extrato_he()">0</a></h3>
		        				</div>
		        				<div style="width:50%;float:left"></div>
		        				</div>
	        		</div>
	        		<div id="div_mostra_extrato_hh" style="display:none">
	        		<div class="panel panel-default" style="width:90%;margin:0 auto;">
	        			<div class="panel-heading">Extrato Horas<div style="float:right"><a style="color:gray" href="#" onclick="mostra_horas_manuais()">Voltar</a></div></div>
		        			<div class="panel-body">
		        			<select id="select_func_HH_extrato" class="selectpicker" data-live-search="true" title="Escolha o Funcionário" onchange="carrega_extrato_he()">
		        			
		        			</select>
		        			<div style="float: left;" id="range_consulta_horas_banco"></div>
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
        		<div class="janelas" id="PO" style="display:none;margin:0 auto;"	>
        			<div id="div_tabela_po"><button class="btn btn-lg btn-warning"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> Carregando...</button></div>
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
		        			<button id="btn_gerartodos_espelho" onclick="baixarTodosEspelhos()" class="btn btn-danger">Gerar todos os Espelhos de ponto do período informado</button>
		        			<!--  <button id="btn_corrigir_espelho" onclick="corrigir_ponto()" class="btn btn-danger">Corrigir Intervalos de Descanso</button>-->
	        			</div>
	        			</div>
        			</div>
        			<p></p>
        			<div id="div_tabela_usuario_ponto_folha_panel" class="panel panel-primary" style="width:90%;margin:0 auto;">
        				<div class="panel-heading"><label id="resumo_espelho_func">Espelho de Ponto</label></div>
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
	        			 <div style='margin-left: 10px; float: left;'>
			                
			                <input type="button" value="Download XLS" id='excelExport' />
			                <br>
            			</div>
        				<div id="div_tabela_usuario_ponto_folha_analise">
					       
        				
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
	        			<div id="div_tabela_sites_filtros" class="panel panel-primary" style="width:90%;margin:0 auto;">
	        				<div class="panel-heading">Filtrar & Buscar Sites</div>
		        			<div class="panel-body">
		        			<div style="float: left">
		        			<table><tr><td>
			        			<select  style="float: left" id="select_operadora_site"  onchange="carrega_tabela_site()" class="selectpicker" data-live-search="true" title="Escolha a Operadora">
			        			
			        			</select></td>
			        			<td>
			        			
			        		</td>
			        		</tr>
			        		</table>
			        		</div>
			        			
			        		<div style="float: right">
			        		<div class="btn-group" role="group">
    						    <button id="btnGroupDrop1" type="button" class="btn btn-primary"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    							     Importar
    						     <span class="caret"></span> 
    						    </button>
    					    <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
    						      <li><a class="dropdown-item" href="#" onclick='atualiza_flag()'>Substuição Completa</a></li>
    						      <li><a class="dropdown-item" href="#" data-toggle="modal" data-target="#modal_carrega_sites">Adicionar novos Sites</a></li>
    						      <li><a class="dropdown-item" href="#">Atualizar informações existentes (Em Desenvolvimento)</a></li>
    					    </ul>
    					     
    					  </div>
    					  <div class="btn-group" role="group">
    						    <button id="btnGroupDropExport" type="button" class="btn btn-primary"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    							     Exportar
    						     <span class="caret"></span> 
    						    </button>
	    					 <ul id="exportSiteListaOperadora" class="dropdown-menu" aria-labelledby="btnGroupDropExport">
	  								
	  						  	</ul>
  						  	 </div>
    					  <a id="template_importar_site" href="templates/templateSiteImportacao.xlsx" type="button" class="btn btn-primary">Template(importação)</a>
			        			<button id="btn_salvarSite"  class="btn btn-primary" onclick="registra_mudancas_sites_bd()">Salvar</button>
			        			<input id="btn_inverter_coordenadas" type="button" class="btn btn-primary" onclick="inverter_coordenadas()" value="Ajustar Coordenadas">
		        			</div>
		        			</div>
	        			</div>
	        			<p></p>
	        			<div id="div_tabela_Sites" class="panel panel-primary" style="width:90%;margin:0 auto;">
        				<div class="panel-heading"><h3 class="panel-title">Tabela de Sites</h3></div>
	        			<div class="panel-body">
        				<div id="div_tabela_detalhe_sites">
					       <div id="tabela_detalhe_sites" >
					      </div>
        				
        				</div>
        				</div>
        			</div>
	        			
        		</div><!-- /tabela_Sites -->
        		<div class="janelas" id="projetos" style="display:none">
        		
        			 <div id="div_tabela_projetos"></div>
        		
        		</div>
        		<div class="janelas" id="usuarios" style="display:none">
        			<div id='jqxtabs_usuario'>
						<ul style='margin-left: 20px;'>
				            <li>Usuários</li>
				            <li>Grupos</li>
	        			</ul>
	        			<div>
        					<div id="div_tabela_usuario">
        					<div>
        					<button  class="btn btn-info" data-toggle="modal" data-target="#modal_user_add">Novo Usuário</button>
        					<button id="desabilitar_usuario" class="btn btn-danger">Desabilitar Usuário</button>
        					<button id="btn_reset_senha" type="button" class="btn btn-danger" onclick="reset_senha()">Redefinir Senha</button>
        					<button id="btn_ferias" class="btn btn-info" onclick="iniciar_ferias()">Agendar Férias de Funcionário</button>
        					<button id="btn_tipo_usu" class="btn btn-info" onclick="definiTipoUsuario()">Alterar Tipo Usuário</button>
        					<div class="btn-group" role="group">
				    		    <button id="btnGroupDrop2" class="btn btn-secondary" aria-haspopup="true" dropdown-toggle data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    		     Importar
				    		     <span class="caret"></span>
				    		    </button>
				    		    <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
				    		      <li><a class="dropdown-item" href="#">Substuição Completa</a></li>
				    		      <li><a class="dropdown-item" href="#" data-toggle="modal" data-target="#modal_add_usuarios">Adicionar novos Usuários</a></li>
				    		      <li><a class="dropdown-item" href="#" data-toggle="modal" data-target="#modal_atualiza_usuarios">Atualizar informações existentes</a></li>
				    		    </ul>
			    		    </div>
			    		    <a href="javascript:window.location='UserMgmt?opt=16'" class="btn btn-primary">Exportar</button>
    						<a id="template_importar_usuario" href="templates/template_usuarios_mstp.xlsx" type="button" class="btn btn-link btn-info">Template(importação)</a>
    						
        					</div>
        					<table id="tabela_usuario" class="display" style="color:black;width:100%">
							        <thead>
							            <tr>
							                <th>Usuario</th>
							                <th>Nome</th>
							                <th>Email</th>
							                <th>Perfil</th>
							                <th>Tipo</th>
							                <th>Validado</th>
							                <th>Ultimo Acesso</th>
							            </tr>
							        </thead>
							        <tfoot>
							            <tr>
							               <th>Usuario</th>
							                <th>Nome</th>
							                <th>Email</th>
							                <th>Perfil</th>
							                <th>Tipo</th>
							                <th>Validado</th>
							                <th>Ultimo Acesso</th>
							            </tr>
							        </tfoot>
							    </table>
        					</div>
        				</div>
        				<div>
        				
        							<div id='jqxWidget2' style="padding-left:20px;font-size: 13px; font-family: Verdana; float: left;width: 100%;height: 100%">
						 			 
						 			 <div id="splitter2" >
						 			 	<div><label style="color:gray">Organização de Grupos</label>
							 			 	<p>
	                    						<input type="button" id='btn_grupo_time' value="Novo Grupo" />
	                    						</p>
	                    						<br>
	               							 
						 			 		<div id='jqxTree_grupos_usuarios' style='float: left;paading:10px'></div>
						 			 	 
						 			 	<div id='jqxMenu_tree_grupos_usuarios'>
									            <ul>
									                <li>Renomear</li>
									                
									            </ul>
									      </div>
						 			 	</div>
	        						 	<div id="container_grupos_usuarios"><div id="jqxgrid_grupo_usuario"></div></div>
	        						</div>
    							</div>
        				
						</div>
        			</div>
        		</div>
        		<div class="janelas" id="regras_rollout" style="display:none;">
        		   <div id="painel_operacoes_regras_rollout" class="panel panel-primary" style="width:90%;margin:0 auto;">
        					<div class="panel-heading"><h3 class="panel-title">Regras do rollout</h3></div>
		        			<div class="panel-body">
		        				<button type="button" class="btn btn-info" data-toggle="modal" data-target="#modal_nova_regra_rollout">Nova Regra</button><button type="button" class="btn btn-danger">Desabilitar Regra</button>
	        				</div>
        			</div>
        			<div id="painel_regras_rollout" class="panel panel-primary" style="width:90%;margin:0 auto;">
        					<div class="panel-heading"><h3 class="panel-title">Regras do rollout</h3></div>
		        			<div class="panel-body">
		        				<div id="div_tabela_regras_rollout">
				        				<table id="tabela_regras_rollout" class="display" style="color:black;width:100%">
							        <thead>
							            <tr>
							                <th>Operações</th>
							                <th>Nome Regra</th>
							                <th>Condição</th>
							                <th>Ação</th>
							                <th>Ultima Execução</th>
							                <th>Criado Por</th>
							                
							            </tr>
							        </thead>
							        <tfoot>
							            <tr>
							                <th>Operações</th>
							                <th>Nome Regra</th>
							                <th>Condição</th>
							                <th>Ação</th>
							                <th>Ultima Execução</th>
							                <th>Criado Por</th>
							            </tr>
							        </tfoot>
							    </table>
		        				</div>
	        				</div>
        			</div>
        		</div>
        		<div class="janelas" id="campos_rollout" style="display:none;">
        		<input type="hidden" id="rolloutid_campos_conf" value="">
				<div id="div_tabela_campos_rollout" style="height: 100%">
				<div id="painel_operacoes_regras_rollout" class="panel panel-primary" style="width:90%;margin:0 auto;">
        					<div class="panel-heading"><h3 class="panel-title">Campos dos Rollouts</h3></div>
		        			<div class="panel-body">
		        				<button class="btn btn-primary" id="btn_novo_campo_rollout" data-toggle="modal" data-target='#Modal_campos_rollout'>Novo Campo</button>
		        				<button class="btn btn-primary" id="btn_edita_campo_rollout" onclick="edita_campos_rollout()">Editar Campo</button>
		        				<select id="select_rollout_campos" class="selectpicker" data-live-search="true" title="Escolha o Rollout" onchange="carrega_tabela_campos_rollout(this.value)"></select>
		        			</div>
		        </div>
				<div id="painel_operacoes_regras_rollout" class="panel panel-primary" style="width:90%;margin:0 auto;">
        					<div class="panel-heading"><h3 class="panel-title">Campos dos Rollouts</h3></div>
		        			<div class="panel-body">
		        				 <table id="tabela_campos_rollout" class="display" style="color:black;width:100%">
					        <thead>
					            <tr>
					                <th>Ordem</th>
					                <th>Campo</th>
					                <th>Tipo</th>
					                <th>Valor</th>
					                <th>Status</th>
					                
					            </tr>
					        </thead>
					        <tfoot>
					            <tr>
					                <th>Ordem</th>
					                <th>Campo</th>
					                <th>Tipo</th>
					                <th>Valor</th>
					                <th>Status</th>
					            </tr>
					        </tfoot>
					    </table>
	        				</div>
        			</div>
					
				</div>
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
	      <input id="input_LOGOCUSTUMER" name="total_input[]" type="file" >
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
		      		<tr><td>Campo</td><td><select class="selectpicker" data-live-search="true" title="Escolha o Campo" id="select_campos_update_lote" onchange="verifica_campo_tipo(this.value,'rollout')"></select></td></tr>
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

<div id="modal_cria_orcamento" class="modal fade" role="dialog">
  <div style="width:90%" class="modal-dialog modal-lg modal-dialog-centered">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas"><label style="color:gray">Criando Novo Orçamento</label></h4>
      </div>
      <div class="modal-body" >
      <div style="height:700px;overflow: auto;">
      <div class="row"  style="margin-left:10px;color:gray;"><h4><label>Dados do Cliente</label></h4></div>
      	<div class="row"  style="color:black;">
	      	<div class="col-md-6"><select class="selectpicker" data-live-search="true" title="Selecione o Cliente" id="select_cliente_orcamento" ></select></div>
	      	<div class="col-md-6"><select class="selectpicker" data-live-search="true" title="Selecione o Contrato" id="select_cliente_contrato_orcamento" ></select></div>
	    </div>
	    <hr>
	    <div class="row"  style="margin-left:10px;color:gray;"><h4><label>Dados do Local</label></h4></div>
	    <div class="row"  style="color:black;">
	      	<div class="col-md-6"><div><input type="text" style="float:left;width:50%" class="form-control" id="site_orcamento_pesquisa" placeholder="Digite o Site"/><button onclick="PesquisaSiteOrcamento()"><i class="fas fa-search"></i></button></div></div>
	     </div>
	     <div class="row"  style="margin-top:5px;color:black;">
	      	<div class="col-md-4"><label style="color:gray">Endereço</label></div>
	      	<div class="col-md-4"><label style="color:gray">Bairro</label></div>
	      	<div class="col-md-4"><label style="color:gray">Município</label></div>
	     </div>
	      <div class="row"  style="color:black;">
	      	<div class="col-md-4"><input type="text" style="float:left;width:90%" class="form-control" id="site_orcamento_endereco" placeholder="Endereco"/></div>
	      	<div class="col-md-4"><input type="text" style="float:left;width:90%" class="form-control" id="site_orcamento_bairro" placeholder="Bairro"/></div>
	      	<div class="col-md-4"><input type="text" style="float:left;width:90%" class="form-control" id="site_orcamento_municipio" placeholder="Municipio"/></div>
	     </div>
	     <div class="row"  style="margin-top:5px;color:black;">
	      	<div class="col-md-4"><label style="color:gray">UF</label></div>
	      	<div class="col-md-4"><label style="color:gray">GRAM</label></div>
	      	<div class="col-md-4"><label style="color:gray">Sigla/Código</label></div>
	     </div>
	     <div class="row"  style="color:black;">
	      	<div class="col-md-4"><input type="text" style="float:left;width:90%" class="form-control" id="site_orcamento_uf" placeholder="UF"/></div>
	      	<div class="col-md-4"><input type="text" style="float:left;width:90%" class="form-control" id="site_orcamento_GRAM" placeholder="Digite o Site"/></div>
	      	<div class="col-md-4"><input type="text" style="float:left;width:90%" class="form-control" id="site_orcamento_SIGLA_LOCAL" placeholder="Digite o Site"/></div>
	     </div>
	     <hr>
	     <div class="row"  style="margin-left:10px;color:gray;"><h4><label>Informações Adicionais</label></h4></div>
	      <div class="row"  style="color:black;">
	      	<div class="col-md-4"><label style="color:gray">Deslocamento(km)</label></div>
	      	<div class="col-md-4"><label style="color:gray">OBS1</label></div>
	      	<div class="col-md-4"><label style="color:gray">OBS2</label></div>
	      
	     </div>
	      <div class="row"  style="color:black;">
	      	<div class="col-md-4"><input type="text" style="float:left;width:90%" class="form-control" id="site_orcamento" placeholder="Digite o Site"/></div>
	      	<div class="col-md-4"><input type="text" style="float:left;width:90%" class="form-control" id="site_orcamento_1" placeholder="Digite o Site"/></div>
	      	<div class="col-md-4"><input type="text" style="float:left;width:90%" class="form-control" id="site_orcamento_2" placeholder="Digite o Site"/></div>
	     </div>
	     <hr>
	     <div style="margin-left:10px;color:gray;" class="row"><h4><label>Dados do Serviço</label></h4></div>
	      <div class="row"  style="color:black;">
	      	<div class="col-md-4"><label style="color:gray">Descrição do Serviço</label></div>
	      	<div class="col-md-4"><label style="color:gray">Motivo do Serviço</label></div>
	     </div>
	      <div class="row"  style="color:black;">
	      	<div class="col-md-4"><input type="text" style="float:left;width:90%" class="form-control" id="site_orcamento_descricao_servico" placeholder="Descrição do Serviço"/></div>
	      	<div class="col-md-4"><input type="text" style="float:left;width:90%" class="form-control" id="site_orcamento_motivo_servico" placeholder="Motivo do Serviço"/></div>
	     </div>
	     <div class="row"  style="padding:5px;color:black;">
	      	<div class="col-md-3"><label style="color:gray">Categoria</label></div>
	      	<div class="col-md-3"><label style="color:gray">Item</label></div>
	      	<div class="col-md-2"><label style="color:gray">Unidade</label></div>
	      	<div class="col-md-2"><label style="color:gray">Valor Unit.</label></div>
	      	<div class="col-md-2"><label style="color:gray"></label></div>
	     </div>
	     <div style="color:black;" class="row">
	      	<div class="col-md-3"><select class="selectpicker" data-live-search="true" title="Selecione a Categoria do Item" onchange="CarregaSelectItens(this.value)" id="select_categorias_item_orcamento" ></select></div>
	      	<div class="col-md-3"><select class="selectpicker" data-live-search="true" title="Selecione o Item" onchange="RecuperaDadosItem(this.value)" id="select_item_orcamento" ></select></div>
	      	<div class="col-md-2"><input readonly style="font-weight: bold;" type="text"  class="form-control" id="lbl_item_unidade" /></div>
	      	<div class="col-md-2"><input readonly style="font-weight: bold;" type="text"  class="form-control" id="lbl_item_valor" /></div>
	      	<div class="col-md-2"><button class="btn" onclick="AddLinhaItemOrcamento()">Adicionar</button><button class="btn btn-danger" onclick="RemoverLinhaItemOrcamento()">Remover</button></div>
	     </div>
	     <input readonly style="font-weight: bold;" type="hidden"  class="form-control" id="lbl_item_desc" />
	     <input readonly style="font-weight: bold;" type="hidden"  class="form-control" id="lbl_item_valor2" />
	     <div id="grid_orcamento_itens"></div>
	     </div>
      </div>
      <div class="modal-footer">
        <button id="add_orcamento_btn0" type="button" class="btn btn-info" onclick="RegistraOrcamento(0)">Salvar</button><button id="add_orcamento_btn1" type="button" class="btn btn-info" onclick="RegistraOrcamento(1)">Salvar & Enviar</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>	


<div id="modal_row_add" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog modal-lg modal-dialog-centered">

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
        <button id="add_project_btn" type="button" class="btn btn-info" onclick="add_new_row_rollout()">Salvar e Adicionar outra</button><button id="add_project_btn" type="button" data-dismiss="modal" class="btn btn-info" onclick="add_new_row_rollout()">Salvar Atividade e Fechar</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
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



<div id="seleciona_rollout_modal" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Seleciona Rollout</h4>
      </div>
      <div class="modal-body">
      	<select  id="select_dashboard_rollout" class="selectpicker" data-live-search="true" title="Escolha o rollout" onchange="g1(2,'grafico_container1_rollout');g9(2,'grafico_container2_rollout')"></select>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>


<div id="modal_lanca_autorizacaoHeretro" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Autorização retro</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      	
      	<p>
			<div id="range_autRetro"></div>
			<br>
		<p>
			
	      
		</div>
      </div>
      <div class="modal-footer">
        <button id="add_autoretro_btn" type="button" class="btn btn-info">Gerar Autorizações Prévias</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>

<div id="modal_nova_regra_rollout" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog modal-lg">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Nova Regra</h4>
      </div>
      <div class="modal-body">
      	<div id="div_condicao_regra">
      		<div><h2>Condição</h2></div>
      		<div>
      		<label class="control-label">Nome da Regra:</label>
      		<input type="text" id="inputNomeRegra" class="form-control" placeholder="Digite aqui um nome de referencia"/>
      			<table style="width:100%">
      				<tr><td style="color:black;width:100px">Rollout</td><td><select  id="select_rollout_regra_rollout" class="selectpicker" data-live-search="true" title="Escolha o rollout" onchange="carrega_campos_rollout2(this.value)"></select></td></tr>
      				<tr><td style="color:black;width:100px">Campo</td><td><select  id="select_campo_regra_rollout" class="selectpicker" data-live-search="true" title="Escolha o campo" onchange="habilitaDesabilitaCondicaoRegra(this.value)" disabled></select></td></tr>
      				<tr><td style="color:black;width:100px">Condição</td><td><select  id="select_condicao_regra_rollout" class="selectpicker" data-live-search="true" title="Escolha a condição" onchange="habilitaDesabilitaCondicaoValorRegra(this.value)" disabled><option value='vazio'>É Vazio</option><option value='n_vazio'>Não é Vazio</option><option value="contem">Contem</option></select></td></tr>
      				<tr><td style="color:black;width:100px">Valor</td><td><input type="text" id="inputValorCondicaoRegra" class="form-control" placeholder="Digite aqui o valor da condição se necessário" disabled/></td></tr>
      			</table>
      			<br>
      			<button style="float:right" type="button" class="btn btn-info" onclick="montarCondicao('E')">Adicionar Condição "&"</button>
      			<button style="float:right" type="button" class="btn btn-info" onclick="montarCondicao('Ou')" disabled >Adicionar Condição "Ou"</button>
      		</div>
		</div>
		<br>
		<br>
		<p>
			<div id="div_resumo_regra">
				<table id="tabela_condicao" style="color:black;width:100%">
					<thead>
						<tr>
							<th>Rollout</th>
							<th>Campo</th>
							<th>Condição</th>
							<th>Valor</th>
							<th>Operador</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</p>
		<hr>
		<p>
		<select class="selectpicker" id="select_acao_regra" data-live-search="true" title="Escolha a Ação" onchange="preparaAcaoRegra(this.value)" disabled>
			<option value="EnviarEmail">Enviar Email(Em Desenvolvimento)</option>
			<option value="EnviarMensagem">Enviar Mensagem(Em Desenvolvimento)</option>
			<option value="CriarAlerta">Criar Alerta(Em Desenvolvimento)</option>
			<option value="AtualizaCampoRollout">Atualizar Campo</option>
		</select>
		<select  id="select_campo_acao_rollout" class="selectpicker" data-live-search="true" title="Escolha o campo" onchange="habilitaDesabilitaCondicaoRegra(this.value)" disabled></select>
		<input type="text" id="inputValorAcaoRegra" class="form-control" placeholder="Digite aqui o valor ou emails separados por virgula" disabled/>
		<button type="button" class="btn btn-primary" onclick="montarAcao()">Adicionar Ação</button>
		<br>
		<table id="tabela_acao" style="color:black;width:100%">
					<thead>
						<tr>
							<th>Ação</th>
							<th>Campo</th>
							<th>Valor</th>
							
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
		
		</p>
      </div>
      <br>
      <br>
      
      <div class="modal-footer">
        <button id="btn_SalvarAplicarRegra" onclick="salvarRegra(1)" type="button" class="btn btn-danger" data-dismiss="modal">Salvar e Aplicar Regra</button><button id="btn_SalvarRegra" onclick="salvarRegra(0)" type="button" class="btn btn-info" data-dismiss="modal">Salvar</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
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
      <input type="hidden" value="" id="id_aux_campo"/>
      <table width=100%>
      		<tr><td style="padding: 10px"><label class="control-label label_janelas">Nome do Campo</label></td></tr>
	        <tr><td style="padding: 10px"><input type="text" class="form-control" id="nome_campo_rollout"></td></tr>
	        <tr><td style="padding: 10px"><label class="control-label label_janelas">Tipo do Campo</label></td></tr>
	        <tr><td style="padding: 10px" class="label_janelas"><select class="form-control" id="tipo_campo_rollout" onchange="mostra_div(this.value)"><option>Milestone</option><option>Atributo</option></select><br><div id="chk_pgto" class="checkbox"><label class="control-label label_janelas"><input type="checkbox" id="trigger_pagamento" onclick="habilita_campo(this)">Aciona Pagamento</label><br><label class="control-label label_janelas">Percentual Pagamento</label><br><input type="text" class="form-control label_janelas" id="percent_pagamento" disabled="disabled"></div><div id="div_campo_atributo" style="display:none"><select class="form-control label_janelas" id="tipo_atributo_rollout" onchange="mostra_div_lista(this.value)"><option>-</option><option>Texto</option><option>Data</option><option>Numero</option><option>Lista</option><option>Moeda(R$)</option></select></div><br><div id="div_campo_atributo_lista" style="display:none"><input type="text" class="form-control label_janelas" id="lista_atributo">Coloque os valores separados por virgula(,).</div></td></tr>
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

<div id="Modal_campos_permits" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title label_janelas">Adicionar Novo Campo ao Módulo de Aquisições e Licenças</h4>
      </div>
      <div class="modal-body">
      <input type="hidden" value="" id="id_aux_campo_permit"/>
      <table width=100%>
      		<tr><td style="padding: 10px"><label class="control-label label_janelas">Nome do Campo</label></td></tr>
	        <tr><td style="padding: 10px"><input type="text" class="form-control" id="nome_campo_permit"></td></tr>
	        <tr><td style="padding: 10px"><label class="control-label label_janelas">Tipo do Campo</label></td></tr>
	        <tr><td style="padding: 10px" class="label_janelas"><select class="form-control" id="tipo_campo_permit"><option>Texto</option><option>Data</option><option>Numero</option><option>Lista</option></select><br></td></tr>
			<tr><td style="padding: 10px"><label class="control-label label_janelas">Grupo do Campo</label></td></tr>
	        <tr><td style="padding: 10px" class="label_janelas"><select class="form-control" id="grupo_campo_permit"><option>Cadastro</option><option>Aquisição</option><option>Licenciamento</option><option>Financeito</option></select><br></td></tr>
			<tr><td style="padding: 10px"><label class="control-label label_janelas">Descrição</label></td></tr>
	        <tr><td style="padding: 10px"><input type="text" class="form-control" id="desc_campo_permit"></td></tr>     
	        <tr><td style="padding: 10px">Exibir na Tabela Master<input type="checkbox" class="form-control" id="tabela_master_permit"></td></tr> 	
      	</table>
			<hr>
			
        
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-default" data-dismiss="modal" onclick="add_campo_permit()">Adicionar</button>
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


<div id="modal_dt_agendamento_qc" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black;">Data de Agendamento do QC</h4>
      </div>
      <div class="modal-body">
      <div style="padding:10px">
			<div class="row">
				<div class="col-sm">
			    	<div id="div_dt_agendamento_qc"></div>
			    </div>
			</div>
		</div>
	    <hr>
	   
	   
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>  

<div id="modal_work_ticket" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black;">Requisitos de Execução</h4>
      </div>
      <div class="modal-body">
      <div style="padding:10px">
      		<div class="row">
				<div class="col-sm">
			    	<label style="color:black;">MOS</label>
			    </div>
				<div class="col-sm">
			    	<div id="div_dt_mos"></div>
			    </div>
			</div>
			<br>
			<div class="row">
				<div class="col-sm">
			    	<label style="color:black;">Site Sign</label>
			    </div>
				<div class="col-sm">
			    	<div id="div_dt_site_sign"></div>
			    </div>
			</div>
			<br>
			<div class="row">
				<div class="col-sm">
			    	<label style="color:black;">Site Verify</label>
			    </div>
				<div class="col-sm">
			    	<div id="div_dt_site_verify"></div>
			    </div>
			</div>
			<br>
			<div class="row">
				<div class="col-sm" style="float:left;">
			    	<div id="jqxCheckBoxSite_">Site</div>
			    </div>
				<div class="col-sm" style="float:left;">
			    	<div id="jqxCheckBoxWH_">WhareHouse</div>
			    </div>
			    <div class="col-sm" style="float:left;">
			    	<div id="jqxCheckBoxEHS_">EHS OK</div>
			    </div>
			</div>
		</div>
	    <hr>
	   
	   
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>

<div id="modal_atualiza_tickets" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black;">Atualizar Informações do Ticket</h4>
      </div>
      <div class="modal-body">
      
		<div class="row">
			<div class="col-sm">
		    	<div id='jqxCheckBox_select_func_novo_tickect_id' style='margin-left: 10px; float: left;'>Novo Responsável</div>
		    </div>
			<div class="col-sm">
		    	<select id="select_func_novo_tickect_id" class="selectpicker" data-live-search="true" title="Escolha o Funcionário"></select>
		    </div>
		</div>
	    <hr>
	    <div class="row">
	    	<div class="col-sm">
		    	<div id='jqxCheckBox_replanTickect' style='margin-left: 10px; float: left;'>Novo Planejamento</div>
		    </div>
		    <div class="col-sm">
			    
			    <div id="replanTickect"></div>
			</div>
	    </div>
	   
      </div>
      <div class="modal-footer">
        <button type="button" onclick="UpdateTicket()" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>  

<div id="modal_atualiza_no_rollout" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Atualizar Nome do Rollout</h4>
      </div>
      <div class="modal-body">
     
        <label class="control-label" style="color:black">Atualização de nome do Rollout</label>
		<input type="text" class="form-control" value="" id="novo_nome_rollout" placeholder="Insirir aqui o nome do rollout">	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="atualiza_nome_rollout()">Atualizar</button>
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


<div id="modal_new_Ticket" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Abertura de Ticket</h4>
      </div>
      <div class="modal-body" id="grid_item_container">
     		<div style="float: left;">
     			<select id="po_select_ticket" class="selectpicker" data-selected-text-format="count"  data-live-search="true" title="Escolha a PO" onchange='carrega_grid_poItem(this.value)'></select>
     		</div>
			<div style="float: right;">
				<div id='from_ticket'></div>
			</div>
			<div style="float: left;">
				   <select id="select_func_tickect" class="selectpicker" data-live-search="true" title="Escolha o Funcionário" onchange="CarregaDespesas()"></select>
			</div>
     		<div id="grid_item_po_ticket"></div>
     		
     		
      </div>
      <div class="modal-footer">
       <button type="button" class="btn btn-success" onclick="add_Ticket()">Novo Ticket com itens selecionados</button> <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 

<div id="modal_add_justificativa" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Adicionar Nova Justificativa</h4>
      </div>
      <div class="modal-body">
     
     <table data-toggle="bootstrap-table">
     	<tr>
     		<td style="padding: 5px"><input type="text" class="form-control" id="input_justificativa" value="" placeholder="Justificativa"></td>
     	</tr>
     	<tr>
     		<td style="padding: 5px"><input type="text" class="form-control" id="input_justificativa_desc" value="" placeholder=" Descrição Justificativa"></td>
     	</tr>
     	<tr>
     		<td style="padding: 5px"><label class="control-label" style="color:black">Foto Requirida:</label><input type="checkbox" class="form-control" id="input_justificativa_foto" value="Foto Requirida"></td>
     	</tr>
     </table>
			
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" data-dismiss="modal" onclick="add_justificativas()" >Salvar</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
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

<div id="modal_upload_template_checklist" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
      <input type="hidden" value='' id='temp_id_txt'/>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Carregar Template de Relatório</h4>
      </div>
      <div class="modal-body">
     
			<hr>
        <label class="control-label" style="color:black">Selecionar Arquivo</label>
		<input id="input_5_arq" name="total_input5[]" type="file" >	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>

<div id="modal_upload_foto_modelo" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
      <input type="hidden" value='' id='temp_id_txt'/>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Carregar Foto Modelo do Item</h4>
      </div>
      <div class="modal-body">
     
			<hr>
        <label class="control-label" style="color:black">Selecionar Arquivo</label>
		<input id="input_6_arq" name="total_input6[]" type="file" >	
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
        <h4 class="modal-title" style="color:black">Criar novo Template de Checklist</h4>
      </div>
      <div class="modal-body" style="display:block;">
      <div><button type="button" id="add_grupo_arvore" class="btn btn-default">Criar Grupo</button><button type="button" class="btn btn-default"  id="add_item_arvore">Criar Item</button><button type="button" class="btn btn-default" id="Remove_item">Remover</button></div>
      <div style="display:inline-block;" id="conteudo_arvore_relatorio">
      		<div id='campos_relatorio' style='float:left;margin-left: 20px;'>
                <ul>
                    <li id='1'>Item</li>
                </ul>
            </div>
    		 <div style='float:right;margin-left: 20px;height:500px;border-style:solid;border-width:1px;border-color: gray;padding:20px'>
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
    		 		<td><select id="select_tipo_item_vistoria" onchange="verificaValor(this.value)" class="selectpicker" data-live-search="true" title="Escolha o Tipo do Item">
		        			<option value="Texto">Texto</option>
		        			<option value="Numero">Número</option>
		        			<option value="Foto">Foto</option>
		        			<option value="oneSelect">Seleção Unica</option>
		        			<option value="MultiSelect">Multiplas seleções</option>
		        			<option value="Data">Data</option>
		        			<option value="rollout_campo">Campo do Rollout</option>
						</select><div id="divItemRolloutChecklist" style="display:none">
						
						<select id="select_item_vistoria_rollout" onchange="buscaCamposRolloutById_Select(this.value)" class="selectpicker" data-live-search="true" title="Escolha o Rollout">
		        			<option value="NO_CAMPO">Sem Campo</option>
		        			
						</select>
						<br>
						<select id="select_item_vistoria_campo_rollout" class="selectpicker" data-live-search="true" title="Escolha o campo do Rollout">
		        			<option value="NO_CAMPO">Sem Campo</option>
		        			
						</select></div>
					</td>
    		 	</tr>
    		 	
    		 	<tr>
    		 		<td><label class="control-label" style="color:black">Linha</label></td>
    		 		<td><input id="item_vistoria_linha" class="form-control" type="text"></td>
    		 	</tr>
    		 	<tr>
    		 		<td><label class="control-label" style="color:black">Coluna</label></td>
    		 		<td><input id="item_vistoria_coluna" class="form-control" type="text"></td>
    		 	</tr>
    		 	<tr>
    		 		<td><label class="control-label" style="color:black">Planilha</label></td>
    		 		<td><input id="item_vistoria_planilha" class="form-control" type="text" value="0"></td>
    		 	</tr>
    		 	<tr>
    		 	 <td><a href="#" style="color:blue" onclick="uploadFotoModeloItem()">Inserir Foto Modelo</a></td>
    		 	 <td><img src="img/album.jpg" alt="Foto Modelo" width="40" height="40" onclick="visualiza_foto2()"></td>
    		   </tr>
    		   	<tr>
    		 	 <td colspan=2 align="right"></td>
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
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
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
      <input type="hidden" id="operacao_relatorio" value="">
      <input type="hidden" id="id_operacao_relatorio" value="">
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

<div id="modal_filtros_mapa_operacional" class="modal fade" role="dialog">
  <div class="modal-dialog">
	<div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Filtros Mapa Operacional</h4>
      </div>
      <div class="modal-body">
     <input type="hidden" id="opt_mapa_operacinal" value=''>
			<table>
				<tr>
					<td>
						<select id="select_campo_mapa_operacinal" class="selectpicker" data-live-search="true" onchange="verifica_campo_tipo(this.value,'filtro_operacional')" title="Selecione o Campo de filtro"></select>
					</td>
				</tr>
			</table>
			<div id="tipo_campos_filtros_mapaOperacional"></div>
			<hr>
			<label style="color:gray">Resumo de Filtros à aplicar:</label>
			
			<div id="resumo_tipo_campos_filtros_mapaOperacional" style="color:gray"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" onclick="limpar_filtros_mapa_operacional()">Limpar Filtros</button><button type="button" class="btn btn-warning" onclick="atualiza_mapa_operacional()" data-dismiss="modal">Aplicar</button><button type="button" class="btn btn-warning" data-dismiss="modal">Aplicar e Salvar</button>
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
<div id="PO_upload_Modal" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Carregar PO</h4>
      </div>
      <div class="modal-body">
      <table >
      		<tr>
      		<td style="padding: 10px"><select class="selectpicker" data-live-search="true" id="cliente_carrega_po" onchange="carrega_select_projeto(this.value)" title="Selecione o Cliente"></select></td>
	        <td style="padding: 10px"><select class="selectpicker" data-live-search="true" id="projeto_carrega_po" title="Selecione o Projeto"></select></td>
	        <td style="padding: 10px"><select class="selectpicker" data-live-search="true" id="select_rollout_carrega_po" title="Selecione o Rollout"></select></td>
	        </tr>
      	</table>
			<hr>
        <label class="control-label">Selecionar Arquivo</label>
		<input id="input_PO_file" name="total_input[]" type="file"  multiple>	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 

<div id="lpu_upload_Modal" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"><label style="color:black">Carregar LPU</label></h4>
      </div>
      <div class="modal-body">
     
			<hr>
        <label class="control-label">Selecionar Arquivo</label>
		<input id="input_lpu_file" name="total_input[]" type="file"  multiple>	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 

<div id="Modal_AddItemLPU" class="modal fade" role="dialog">
  <div style="width:90%" class="modal-dialog modal-lg">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Adcionar Item LPU</h4>
      </div>
      <div class="modal-body" >
      
       <div class="row"  style="margin-top:5px;color:black;">
	      	<div class="col-md-3"><label style="color:gray">Categoria</label></div>
	      	<div class="col-md-3"><label style="color:gray">Codigo Item</label></div>
	      	<div class="col-md-6"><label style="color:gray">Descrição do Item</label></div>
	   </div>
	      <div class="row"  style="margin-top:5px;color:black;">
	      	<div class="col-md-3"><select class="selectpicker" data-live-search="true" title="Selecione a Categoria do Item" id="select_categorias_item_lpu" ></select></div>
	      	<div class="col-md-3"><input type="text" placeholder="Código do Item" id="addcodigo_item_lpu" class="form-control" value=""/></div>
	      	<div class="col-md-6"><input type="text" placeholder="Descrição do Item" id="adddescricao_item_lpu" class="form-control" value=""/></div>
	     </div>
	     <br>
	     <div class="row"  style="margin-top:10px;color:black;">
	      	<div class="col-md-3"><label style="color:gray">Unidade</label></div>
	      	<div class="col-md-3"><label style="color:gray">UF</label></div>
	      	<div class="col-md-4"><label style="color:gray">Preço Unitário(somente numeros)</label></div>
	   </div>
	      <div class="row"  style="margin-top:5px;color:black;">
	      	<div class="col-md-3"><select class="selectpicker" data-live-search="true" title="" id="select_unidade_item_lpu" ><option value="m">Metro(m)</option><option value="kg">Kilo(kg)</option><option value="unid">Unidade(unid)</option><option value="rl">Rolo(rl)</option><option value="m3">Volume(m3)</option><option value="m2">Area(m2)<option value="h">Hora(h)</option><option value="h/h">HomemHora(h/h)</option><option value="km">Distancia(km)</option></select></div>
	      	<div class="col-md-3"><select class="selectpicker" data-live-search="true" title="" id="select_uf_item_lpu" ><option value="RJ">RJ</option><option value="SP">SP</option><option value="MG">MG</option><option value="ES">ES</option><option value="DF">DF</option><option value="PR">PR</option></select></div>
	      	<div class="col-md-3"><input type="number" id="addvalor_item_lpu" placeholder="valor" class="form-control" value="0.0"/></div>
	      	
	     </div>
			
      </div>
      <div class="modal-footer">
        <button type="button" onclick ="addItemLPU()" class="btn btn-default" data-dismiss="modal" >Adicionar</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" >Fechar</button>
      </div>
    </div>

  </div>
</div> 


<div id="Modal_DetalheEquipeRollout" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Detalhes da Equipe no Site</h4>
      </div>
      <div class="modal-body" >
      <div id="fotos_registros"></div>
      <div id="div_detalhe_usuario"></div>
			
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" >Fechar</button>
      </div>
    </div>

  </div>
</div> 

<div id="Modal_addTickectEvento" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Novo Evento do Ticket - <span id="span_ticket_num"></span></h4>
      </div>
      <div class="modal-body">
      <table >
      		<tr>
      		<td style="padding: 10px"><select class="selectpicker" data-actions-box="true" multiple data-selected-text-format="count > 2" data-live-search="true" title="Escolha o Funcionário"  id="func_ticket_evento" ></select></td>
	        </tr>
	        <tr>
      		<td style="padding: 10px"><select class="selectpicker" data-actions-box="true" data-selected-text-format="count > 2" data-live-search="true" onchange="atualiza_gap()" title="Atividade Principal"  id="atividade_eventoTicket" ><option value='PO'>PO</option><option value='Acesso'>Acesso</option><option value='Survey'>Survey</option><option value='Material'>Material</option><option value='Instalação'>Instalação</option><option value='Qualidade'>Qualidade</option><option value='Transmissão'>Transmissão</option><option value='Ativação'>Ativação</option><option value='HOF'>HandOver Físico</option><option value='HOL'>HandOver Lógivo</option></select></td>
	        </tr>
	        <tr>
      		<td style="padding: 10px"><select class="selectpicker" data-actions-box="true" multiple data-selected-text-format="count > 2" data-live-search="true" title="GAP da Atividade"  id="gap_eventoTicket" ></select></td>
	        </tr>
	        <tr>
      		<td style="padding: 10px"><select class="selectpicker" data-actions-box="true" data-selected-text-format="count > 2" data-live-search="true" title="Responsável"  id="owner_eventoTicket" ><option value='OPERADORA'>Operadora</option><option value='CLIENTE'>Cliente</option><option value='SPAZIO'>SPAZIO</option></select></td>
	        </tr>
      	</table>
			<hr>
        <label class="control-label"> Escreva sua Mensagem Abaixo. Seja Objetivo e breve</label>
		<input id="mensagem_input_ticket" class="form-control" value="" type="text" rows="5" style="color:black;width:400px; height:50px;font-size: 14px" >	
      	<div id="historico_ticket" style='height: 300px; overflow-y: scroll;'><div><h3 style="color:gray">Histórico de Eventos:</h3></div><hr><ul class="bulletshistorico" id="historico_ticket_itens"></ul></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" onclick="registraEventosTicket(null)">Enviar</button><button type="button" class="btn btn-success" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 


<div id="Modal_EnviaMengagem" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Enviar Mensagem ao Usuário</h4>
      </div>
      <div class="modal-body" style="background-image: linear-gradient(red, yellow);">
      <table >
      		<tr>
      		<td style="padding: 10px"><select class="selectpicker" data-actions-box="true" multiple data-selected-text-format="count > 2" data-live-search="true" title="Escolha o Funcionário"  id="func_mensagem" ></select></td>
	        
	        </tr>
      	</table>
			<hr>
        <label class="control-label"> Escreva sua Mensagem Abaixo</label>
		<input id="mensagem_input" class="form-control" value="" type="text" rows="5" style="color:black;width:400px; height:50px;font-size: 14px" >	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="envia_mensagem_rollout()">Enviar</button>
      </div>
    </div>

  </div>
</div> 

<div id="modal_project_add" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style="color:black">Adicionar Novo Projeto</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      	<table >
      		<tr><td style="padding: 10px"><label class="control-label" style="color:black">Selecione a Conta</label>
	        <select class="form-control" required id="projeto_nome_cliente"><option>Cliente1</option><option>Cliente2</option></select></td><td style="padding: 10px"><label class="control-label">Selecione o Produto</label>
	        <select class="form-control" id="projeto_produto"><option>Wireless</option><option>Transmissao</option><option>Microwave</option></select></td><td style="padding: 10px"><label class="control-label">Estado</label>
	        <select class="form-control" id="projeto_estado"><option>RJ</option><option>SP</option></select></td></tr>
      	</table>
			<hr>
	       <br>
			<table >
	      		<tr><td style="padding: 10px"><label class="control-label" style="color:black">Orçamento do Projeto</label></td>
		        <td style="padding: 10px"><label class="control-label" style="color:black">PM do projeto - Cliente</label></td>
		        <td style="padding: 10px"><label class="control-label" style="color:black">Email PM - Cliente</label></td></tr>
		        <tr><td style="padding: 10px"><input type="text" style="color:black" class="form-control" id="projeto_orcamento"></td>
		        <td style="padding: 10px"><input type="text" style="color:black" class="form-control" id="projeto_pm_cliente"></td>
		        <td style="padding: 10px"><input type="text" style="color:black" class="form-control" id="projeto_mail_cliente"></td></tr>
      		</table>
			<br>
			<hr>
			<br>
			<table >
	      		<tr><td style="padding: 10px"><label class="control-label" style="color:black">Nome do Projeto</label></td>
		        <td style="padding: 10px"><label class="control-label" style="color:black">Código do Projeto</label></td>
		        
		        <tr><td style="padding: 10px"><input type="text" style="color:black" class="form-control" id="projeto_nome"></td>
		        <td style="padding: 10px"><input type="text" style="color:black" class="form-control" id="projeto_codigo_cliente"></td>
		        </tr>
      		</table>
			<br>
			<hr>
			<table ><tr><td style="padding: 10px"><label class="control-label" style="color:black" >Inicio do Projeto</label></td><td style="padding: 10px"><label style="color:black" class="control-label">Encerramento Previsto</label></td></tr>
			<tr><td style="padding: 10px">
							<div class='input-group date' id='datetimepicker6'>
								                    <input type='text' class="form-control" id="projeto_inicio_dt"/>
								                    
								                    <span class="input-group-addon">
								                        <span class="glyphicon glyphicon-calendar"></span>
								                    </span>
						    </div>
						 </td>
						 <td style="padding: 10px">
								            <div class='input-group date' id='datetimepicker7'>
                <input type='text' class="form-control" id="projeto_fim_dt"/>
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div></td></tr></table>
								        
								        <script type="text/javascript">
								            $(function () {
								                $('#datetimepicker6').datetimepicker({useCurrent: true});
								                $('#datetimepicker7').datetimepicker({
								                    useCurrent: false, //Important! See issue #1075
								                    //timePicker:false,
								                    calendarWeeks:true,
								                    showTodayButton:true
								                    //disabledHours:true
								                });
								                $("#datetimepicker6").on("dp.change", function (e) {
								                    $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
								                });
								                $("#datetimepicker7").on("dp.change", function (e) {
								                    $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
								                });
								            });
								        </script>
				   
   			 
			<br>
			<hr>
		</div>
      </div>
      <div class="modal-footer">
        <button id="add_project_btn" type="button" class="btn btn-info" onclick="add_project()">Adicionar Projeto</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div> 

<div id="modal_ajuste_ponto" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog modal-lg">

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
      			<td style="padding: 5px"><div class='input-group date' id='ponto_entradax'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div></td>
      			<td style="padding: 5px"><div class='input-group date' id='ponto_saidax'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div></td>
      			<td style="padding: 5px"><select id="select_ajuste_ponto" class="selectpicker" data-live-search="true" title="Escolha o Local">
		        			
		        			</select>
		        </td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><label class="control-label label_janelas">Inicio Intervalo</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas">Fim Intervalo</label></td>
      			<td style="padding: 5px"><label class="control-label label_janelas"></label></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><div class='input-group date' id='ponto_inicio_intervalox'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div></td>
      			<td style="padding: 5px"><div class='input-group date' id='ponto_fim_intervalox'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div></td>
      			<td style="padding: 5px">
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
          L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    			attribution: 'MSTP',
    			maxZoom: 18,
    			id: 'mapbox/streets-v11',
    			tileSize: 512,
    			zoomOffset: -1,
    			accessToken: 'pk.eyJ1IjoiZmFiaW9zYWxidXF1ZXJxdWUiLCJhIjoiY2tnamJldTI5MDg0djJ0bXNyZGVyenozeCJ9.qmVVng-6k8FdjiJRUde_iw'
		}).addTo(mymap);
          
			 
		</script>
	</body>
</html>





