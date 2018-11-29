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
	
		<meta name="description" content="Sistema de Gerenciamento de Equipes" />
		<meta name="keywords" content="GIS, INOVARE, INOVARE TI, Servico, telecom" />
		<meta name="Inovare TI" content="GIS" />
		
	<link rel="stylesheet" type="text/css" href="css/div_graph.css" />
	
    <link rel="stylesheet" href="css/fileinput.min.css" type="text/css">
	<link rel="stylesheet" href="css/w2ui-1/w2ui-1.4.3.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-editable.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-table.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-table-reorder-rows.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-multiselect.css" type="text/css">
	
	<link href="css/style.css"  type="text/css">
	<link href="css/default/style.min.css" type="text/css">
	<link href="css/simple-sidebar.css" >
	<link rel="stylesheet" href="js/jqwidgets/styles/jqx.base.css" type="text/css" />
	<link rel="stylesheet" href="css/diversos.css" type="text/css" />
	
	<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" type="text/css" href="css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="css/demo.css" />
		<link rel="stylesheet" type="text/css" href="css/icons.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
	
	<script src="js/jquery-2.1.3.min.js" type="text/javascript"></script>
	<script src="js/plugins/canvas-to-blob.min.js" type="text/javascript"></script>
	<script src="js/fileinput.min.js" type="text/javascript"></script>
	<script src="js/fileinput_locale_pt-BR.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/bootstrap-editable.js"></script>
	<script src="js/accounting.js"></script>
	<script src="js/bootstrap-table.js"></script>
	<script src="js/bootstrap-table-toolbar.min.js"></script>
	<script src="js/bootstrap-multiselect.js"></script>
	
	<script src="js/highcharts.js"></script>
	<script src="js/modules/drilldown.js"></script>
   <script src="js/modules/data.js"></script>
	<script src="js/carregador_graficos.js"></script>
  	<script src="js/bootstrap-table-pt-BR.js"></script>
  	<script src="js/bootstrap-table-filter-control.js"></script>
  	
  	
  	<script src="js/bootstrap-table-reorder-rows.min.js"></script>
  	<script src="js/jquery.tablednd.js"></script>
  	<script src="js/moment-with-locales.min.js"></script>
   	<script src="js/locale/pt-br.js"></script>
  	<script src="js/bootstrap-datetimepicker.js"></script>
  	<script type="text/javascript" src="js/w2ui-1/w2ui-1.4.3.min.js"></script>
  	<script type="text/javascript" src="js/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxmenu.js"></script>
     <script type="text/javascript" src="js/jqwidgets/jqxcombobox.js"></script>
   
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.filter.js"></script>
    
    <script type="text/javascript" src="js/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.sort.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.columnsresize.js"></script> 
    <script type="text/javascript" src="js/jqwidgets/jqxdata.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.pager.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.edit.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpanel.js"></script>
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
    <script type="text/javascript" src="js/jqwidgets/jqxdatetimeinput.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqx_aux.js"></script>
    <script type="text/javascript" src="js/jqwidgets/globalization/globalize.js"></script>
    <script src="js/trata_fluxo_pg.js"></script>
	<script src="js/modernizr.custom.js"></script>
	<script type="text/javascript" src="js/rollout.js"></script>
	<script type="text/javascript" src="js/senha.js"></script>
	<script type="text/javascript" src="js/project.js"></script>
	
   
  	<script>
  	
  	var text = '{ "atualizacoes":0, "campos" : []}';
	var g_changes = JSON.parse(text);
	var text = '{ "rows":[]}';
	var deletar=[];
	var deletar_grid=[];
	var row = JSON.parse(text);
	var milestone_pg;
  	$(document).ready(function() {
  		//alert("teste");
  		carrega_tabela_campos_rollout();
  		carrega_portal();
  		$("#jqxExpander").jqxExpander({ width: '100%'});
  		//	document.getElementById("rollout").style.display = "none";
		//carrega_project_table();
		//carrega_cliente_table();
  		carrega_ajuda();			
					accounting.settings = {
							currency: {
								symbol : "R$",   // default currency symbol is '$'
								format: "%s%v", // controls output: %s = symbol, %v = value/number (can be object: see below)
								decimal : ",",  // decimal point separator
								thousand: ".",  // thousands separator
								precision : 2   // decimal places
							},
							number: {
								precision : 0,  // default precision on numbers is 0
								thousand: ".",
								decimal : ","
							}
						}
  	});
  	
	
</script>

 

<script type="text/javascript">
 
 function carrega_portal(){
	 var cont=0;
	 var cont_aux=1;
	 
	 $.ajax({
		  type: "POST",
		  data: {"opt":"21"
			
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess21
		});
	 function onSuccess21(data){
        
		 $('#docking').jqxDocking({ orientation: 'horizontal', width: 300, mode: 'docked' });

		 //$('#docking').jqxDocking({  orientation: 'horizontal', width: 800, mode: 'default' });
		 console.log($('#docking').jqxDocking('exportLayout'));
		 $('#docking').jqxDocking('importLayout', data);
		 
		 g1(0,'grafico_container1');
		 g2(0,'grafico_container2');
		 g3(0,'grafico_container3');
		 g4(0,'grafico_container4');
		 $('#docking').on('dragEnd', function (event) {
			 $.ajax({
				  type: "POST",
				  data: {"opt":"22",
					"portal":$('#docking').jqxDocking('exportLayout'),  
					
					},		  
				  //url: "http://localhost:8080/DashTM/D_Servlet",	  
				  url: "./POControl_Servlet",
				  cache: false,
				  dataType: "text"
				  
				});
		 
             
         });
	 }
 }

 function carrega_ajuda(){
		$.ajax({
			  type: "POST",
			  data: {"opt":"27"},		  
			  	  
			  url: "./POControl_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess27
			});
		
		function onSuccess27(data)
		{
			$("#videos_ajuda").html(data);
			
			
		    
	}
	}
 function menu(opt){
	 
	 document.getElementById("portal").style.display = "none";
	 document.getElementById(opt).style.display = "block";
	 carrega_gant();
	
 }
    </script>

	<title>G I S - Gestão Integrada de Serviços</title>


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
	</head>
	<body>
		<div class="container">
			<!-- Push Wrapper -->
			<div class="mp-pusher" id="mp-pusher">

				<!-- mp-menu -->
				<nav id="mp-menu" class="mp-menu">
					<div class="mp-level">
						<h2 class="icon icon-world">G I S</h2>
						<ul>
							<li class="icon icon-arrow-left">
								<a class="icon icon-display" href="#" onclick="carrega_portal()">Dashboard</a>
								<div class="mp-level">
									<h2 class="icon icon-display">Chamados</h2>
									<ul>
										<li class="icon icon-arrow-left">
											<a class="icon icon-phone" href="#">Projetos</a>
											<div class="mp-level">
												<h2>Mobile Phones</h2>
												<ul>
													<li><a href="#">Super Smart Phone</a></li>
													<li><a href="#">Thin Magic Mobile</a></li>
													<li><a href="#">Performance Crusher</a></li>
													<li><a href="#">Futuristic Experience</a></li>
												</ul>
											</div>
										</li>
										<li class="icon icon-arrow-left">
											<a class="icon icon-tv" href="#">Televisions</a>
											<div class="mp-level">
												<h2>Televisions</h2>
												<ul>
													<li><a href="#">Flat Superscreen</a></li>
													<li><a href="#">Gigantic LED</a></li>
													<li><a href="#">Power Eater</a></li>
													<li><a href="#">3D Experience</a></li>
													<li><a href="#">Classic Comfort</a></li>
												</ul>
											</div>
										</li>
										<li class="icon icon-arrow-left">
											<a class="icon icon-camera" href="#">Cameras</a>
											<div class="mp-level">
												<h2>Cameras</h2>
												<ul>
													<li><a href="#">Smart Shot</a></li>
													<li><a href="#">Power Shooter</a></li>
													<li><a href="#">Easy Photo Maker</a></li>
													<li><a href="#">Super Pixel</a></li>
												</ul>
											</div>
										</li>
									</ul>
								</div>
							</li>
							<li class="icon icon-arrow-left">
								<a class="icon icon-news" href="#">Clientes</a>
								<div class="mp-level">
									<h2 class="icon icon-news">Magazines</h2>
									<ul>
										<li><a href="#">National Geographic</a></li>
										<li><a href="#">Scientific American</a></li>
										<li><a href="#">The Spectator</a></li>
										<li><a href="#">The Rambler</a></li>
										<li><a href="#">Physics World</a></li>
										<li><a href="#">The New Scientist</a></li>
									</ul>
								</div>
							</li>
							<li class="icon icon-arrow-left">
								<a class="icon icon-shop" href="#">Projetos</a>
								<div class="mp-level">
									<h2 class="icon icon-shop">Store</h2>
									<ul>
										<li class="icon icon-arrow-left">
											<a class="icon icon-t-shirt" href="#">Clothes</a>
											<div class="mp-level">
												<h2 class="icon icon-t-shirt">Clothes</h2>
												<ul>
													<li class="icon icon-arrow-left">
														<a class="icon icon-female" href="#">Women's Clothing</a>
														<div class="mp-level">
															<h2>Women's Clothing</h2>
															<ul>
																<li><a href="#">Tops</a></li>
																<li><a href="#">Dresses</a></li>
																<li><a href="#">Trousers</a></li>
																<li><a href="#">Shoes</a></li>
																<li><a href="#">Sale</a></li>
															</ul>
														</div>
													</li>
													<li class="icon icon-arrow-left">
														<a class="icon icon-male" href="#">Men's Clothing</a>
														<div class="mp-level">
															<h2>Men's Clothing</h2>
															<ul>
																<li><a href="#">Shirts</a></li>
																<li><a href="#">Trousers</a></li>
																<li><a href="#">Shoes</a></li>
																<li><a href="#">Sale</a></li>
															</ul>
														</div>
													</li>
												</ul>
											</div>
										</li>
										<li>
											<a class="icon icon-diamond" href="#">Jewelry</a>
										</li>
										<li>
											<a class="icon icon-music" href="#">Music</a>
										</li>
										<li>
											<a class="icon icon-food" href="#">Grocery</a>
										</li>
									</ul>
								</div>
							</li>
							<li><a class="icon icon-photo" href="#" onclick="menu('rollout')">Rollout</a></li>
							<li><a class="icon icon-wallet" href="#">Financeiro</a></li>
                            <li><a class="icon icon-wallet" href="#">Configurações</a></li>
                             <li><a class="icon icon-wallet" href="lg.html" onclick="sair()">Sair</a></li>
						</ul>
					</div>
				</nav>
				<!-- /mp-menu -->
                <div class="block block-40 clearfix">
				<p><a href="#" id="trigger" class="menu-trigger"></a><b style="color:red">G </b><b>I S</b></p>
								
				</div>
				
				
				<div id="principal">
			
				<div id="portal">
       			   <div id='jqxWidget' style="margin: auto;">
        <div id="docking" style="margin: auto;">
            <div style="overflow: hidden;">
                <div id="window0" style="height: 300px">
                    <div> <div class="w2ui-field">
    					<label>Periodo:</label>
    					<div> <input type="us-date1" id="from"> - <input type="us-date2" id="to"> <button class="btn" name="ok" onclick="g1(1,'grafico_container1')">OK</button></div>
					</div></div>
                   
                    <div id='grafico_container1'>Content 1</div>
                </div>
                <div id="window1" style="height: 300px">
                    <div></div>
                    <div id='grafico_container2'>Content 2</div>
                </div>
            </div>
            <div style="overflow: hidden;">
                <div id="window2" style="height: 300px; ">
                    <div></div>
                    <div id='grafico_container3'>Content 3</div>
                </div>
                <div id="window3" style="height: 300px; ">
                    <div></div>
                    <div id='grafico_container4'>Content 3</div>
                </div>
            </div>
        </div>
        
    </div>
                 </div><!-- /portal -->
        		<div id="rollout" style="display:none;"	>
        		<div id='jqxExpander' style="width:auto;display:inline-block;">
        			 	<div>MiniDashboard em Desenvolvimento</div>
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
							<div class="row" style="width: 100%">
					 			
					 			<div id='jqxWidget' style="padding-left:20px; ;font-size: 13px; font-family: Verdana; float: left;width: 100%">
        								<div id="jqxgrid"></div>
    							    </div>
					 		</div>
        		</div><!-- /rollout -->
			<div id="div_tabela_campos_rollout" style="display:none;"></div>
			<div id="div_tabela_usuario" style="display:none;"></div>
			<div id="videos_ajuda" style="display:none ;margin: 0 auto;"></div>
			<div id="div_tabela_projetos" style="display:none;"></div>
        	     <div id="controle_senha" style="display:none;">
		      <table style="width: 100%">
			      		<tr><td style="padding: 10px"><label class="control-label">Senha Atual:</label></td></tr>
				        <tr><td style="padding: 10px"><input type="password" class="form-control" id="senha_atual"></td></tr>
				        <tr><td style="padding: 10px"><label class="control-label">Nova Senha:</label></td></tr>
				        <tr><td style="padding: 10px"><input type="password" class="form-control" id="nova_senha"></td></tr>
						<tr><td style="padding: 10px"><label class="control-label">Repita a nova Senha:</label></td></tr>
				        <tr><td style="padding: 10px"><input type="password" class="form-control" id="confirma_senha"></td></tr>    
				        <tr><td style="padding: 10px"><label class="control-label"></label></td></tr>  
				        <tr><td style="padding: 10px"><button id="troca_senha_btn" type="button" class="btn btn-info" onclick="trocadeSenha()">Trocar Senha</button></td></tr>	
			      </table>
			
            </div><!-- /senha -->
        
			</div><!-- /principal -->
				

	</div><!-- /pusher -->
			
</div><!-- /container -->
		<script src="js/classie.js"></script>
		<script src="js/mlpushmenu.js"></script>
		<script>
			new mlPushMenu( document.getElementById( 'mp-menu' ), document.getElementById( 'trigger' ) );
		</script>
	</body>
</html>