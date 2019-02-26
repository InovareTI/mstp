function testejs(){
	$.alert("Funçao em desenvolvimento");
}
function getWidth(idelemeent){
	var elmnt = document.getElementById(idelemeent);
	return elmnt.offsetWidth;
}
function carrega_mapa_operacional(){
	var timestamp =Date.now();
    $('#jqxtabs_mapaOperacional').jqxTabs({theme: 'light'});
    
	
	$('#docking_operacional').jqxDocking({  orientation: 'horizontal', mode: 'docked',theme: 'material' });
	$("#jqxLoader_grid_func_mapaOperacional").jqxLoader({ text: "Carregando Rollout",width: 100, height: 60, imagePosition: 'top' });
	$('#jqxLoader_grid_func_mapaOperacional').jqxLoader('open');
	
	
		$.ajax({
	 		  type: "POST",
	 		  data: {"opt":"21",
	 			  "_": timestamp
	 			},		  
	 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
	 		  url: "./RolloutServlet",
	 		  cache: false,
	 		  dataType: "text",
	 		 success: atualiza_grid_mapa_operacional
	 		});
		function atualiza_grid_mapa_operacional(data){
			
			dataaux=JSON.parse(data);
			
			 var source =
		     {
					 datatype: "json",
			         datafields: [
			        	 { name: 'recid' , type: 'int'},
			        	 { name: 'usuario', type: 'string' },
			             { name: 'nome', type: 'string' },
			             { name: 'site_id' , type: 'string'},
			             { name: 'h_entrada', type: 'string' },
			             { name: 'u_registro', type: 'string' }
			             
			         ],
		         localdata: dataaux,
		         id: 'recid',
		     };
			 var dataAdapter = new $.jqx.dataAdapter(source);
			 var photorenderer = function (row, column, value) {
		         var name = $('#grid_func_mapaOperacional').jqxGrid('getrowdata', row).usuario;
		         var imgurl = './UserMgmt?opt=32&usuario='+name;
		         var img = '<div style="background: white;"><img style="margin:2px; margin-left: 10px;" width="42" height="52" src="' + imgurl + '"></div>';
		         return img;
		     }
			 
		     var renderer = function (row, column, value) {
		         return '<span style="margin-left: 4px; margin-top: 9px; float: left;">' + value + '</span>';
		     }
		     $("#grid_func_mapaOperacional").jqxGrid(
		             {
		                 width: getWidth('op_window3'),
		                 height: 350,
		                 source: dataAdapter,
		                 columnsresize: true,
			             filterable: true,
			             autoshowfiltericon: true,
		                 rowsheight: 55,
		               
		                 columns: [
		                       { text: 'Photo', width: 50, cellsrenderer: photorenderer },
		                       { text: 'Nome', datafield: 'nome', width: 150, cellsrenderer: renderer,filtertype: 'checkedlist' },
		                       { text: 'Site', datafield: 'site_id', width: 100, cellsrenderer: renderer,filtertype: 'checkedlist' },
		                       { text: 'Hora Entrada', datafield: 'h_entrada', width: 150, cellsrenderer: renderer,filtertype: 'checkedlist' },
		                       { text: 'Ultimo Registro', datafield: 'u_registro', width: 150, cellsrenderer: renderer,filtertype: 'checkedlist' }
		                       
		                   ]
		             });
		    
	}
		 $('#jqxLoader_grid_func_mapaOperacional').jqxLoader('close');
		 $.ajax({
	 		  type: "POST",
	 		  data: {"opt":"22",
	 			  "_": timestamp
	 			},		  
	 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
	 		  url: "./RolloutServlet",
	 		  cache: false,
	 		  dataType: "text",
	 		 success: atualiza_grid_mapa_operacional_projetos
	 		});	
		 function atualiza_grid_mapa_operacional_projetos(data){
			 dataaux=JSON.parse(data);
				
			 var source =
		     {
					 datatype: "json",
			         datafields: [
			        	 { name: 'id' , type: 'int'},
			        	 { name: 'projeto', type: 'string' },
			             { name: 'totalsites', type: 'int' },
			             { name: 'sites_done' , type: 'int'},
			             { name: 'sites_planejados', type: 'int' },
			             { name: 'sites_planejados_done', type: 'int' }
			             
			         ],
		         localdata: dataaux,
		         id: 'id',
		     };
			 var dataAdapter = new $.jqx.dataAdapter(source);
			 
		    
		     $("#grid_func_mapaOperacional_SitesProjetos").jqxGrid(
		             {
		                 width: getWidth('op_window3'),
		                 height: 350,
		                 source: dataAdapter,
		                 columnsresize: true,
			             filterable: true,
			             autoshowfiltericon: true,
		                 rowsheight: 55,
		               
		                 columns: [
		                       { text: 'Projeto', datafield: 'projeto',columngroup:"Sites_Projeto",cellsalign: "center",width: 100,filtertype: 'textbox'},
		                       { text: 'Total Atividades', datafield: 'totalsites',columngroup:"Sites_Projeto",cellsalign: "center", width: 130,filtertype: 'checkedlist' },
		                       { text: 'Realizado', datafield: 'sites_done',columngroup:"Sites_Projeto", width: 100,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Planejado', datafield: 'sites_planejados',columngroup:"Sites_Projeto", width: 100,cellsalign: "center", filtertype: 'checkedlist' },
		                       { text: 'PlanejadoRealizado', datafield: 'sites_planejados_done',columngroup:"Sites_Projeto", width: 100,cellsalign: "center", filtertype: 'checkedlist' }
		                       
		                   ],
		                  columngroups: [
		                	  { text: "Escopo de Projetos", align: "center",name: "Sites_Projeto"},
		                   ]
		             });
		 }
		 $.ajax({
	 		  type: "POST",
	 		  data: {"opt":"23",
	 			  "_": timestamp
	 			},		  
	 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
	 		  url: "./RolloutServlet",
	 		  cache: false,
	 		  dataType: "text",
	 		 success: atualiza_grid_mapa_operacional_atividades
	 		});	
		 function atualiza_grid_mapa_operacional_atividades(data){
			 dataaux=JSON.parse(data);
				
			 var source =
		     {
					 datatype: "json",
			         datafields: [
			        	 { name: 'id' , type: 'int'},
			        	 { name: 'atividade', type: 'string' },
			             { name: 'projeto', type: 'string' },
			             { name: 'totalatividades' , type: 'int'}
			             
			             
			         ],
		         localdata: dataaux,
		         id: 'id',
		     };
			 var dataAdapter = new $.jqx.dataAdapter(source);
			 
		    
		     $("#grid_func_mapaOperacional_AtividadeProjetos").jqxGrid(
		             {
		                 width: getWidth('op_window1'),
		                 height: 350,
		                 source: dataAdapter,
		                 columnsresize: true,
			             filterable: true,
			             autoshowfiltericon: true,
		                 rowsheight: 55,
		               
		                 columns: [
		                       { text: 'Atividade', datafield: 'atividade',columngroup:"atividade_Projeto",align: "center",cellsalign: "center",width: 330,filtertype: 'textbox'},
		                       { text: 'Projeto', datafield: 'projeto',columngroup:"atividade_Projeto",align: "center",cellsalign: "center", width: 110,filtertype: 'textbox' },
		                       { text: 'Qtd.', datafield: 'totalatividades',columngroup:"atividade_Projeto",align: "center", width: 80,cellsalign: "center",filtertype: 'checkedlist' }
		                       
		                       
		                   ],
		                  columngroups: [
		                	  { text: "Atividade por Projeto", align: "center",name: "atividade_Projeto"},
		                   ]
		             });
		 }
		 $.ajax({
	 		  type: "POST",
	 		  data: {"opt":"24",
	 			  "_": timestamp
	 			},		  
	 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
	 		  url: "./RolloutServlet",
	 		  cache: false,
	 		  dataType: "text",
	 		 success: atualiza_grid_mapa_operacional_atividadesUsuario
	 		});
		 function atualiza_grid_mapa_operacional_atividadesUsuario(data){
			 dataaux=JSON.parse(data);
				
			 var source =
		     {
					 datatype: "json",
			         datafields: [
			        	 { name: 'id' , type: 'int'},
			        	 { name: 'projeto', type: 'string' },
			             { name: 'usuario', type: 'string' },
			             { name: 'totalatividades_usuario' , type: 'int'}
			             
			             
			         ],
		         localdata: dataaux,
		         id: 'id',
		     };
			 var dataAdapter = new $.jqx.dataAdapter(source);
			 var photorenderer2 = function (row, column, value) {
		         var name = $('#grid_func_mapaOperacional_AtividadeProjetosUsuario').jqxGrid('getrowdata', row).usuario;
		         var imgurl = './UserMgmt?opt=32&usuario='+name;
		         var img = '<div style="background: white;"><img style="margin:2px; margin-left: 10px;" width="52" height="52" src="' + imgurl + '"></div>';
		         return img;
		     }
		    
		     $("#grid_func_mapaOperacional_AtividadeProjetosUsuario").jqxGrid(
		             {
		                 width: getWidth('op_window0'),
		                 height: 350,
		                 source: dataAdapter,
		                 columnsresize: true,
			             filterable: true,
			             autoshowfiltericon: true,
		                 rowsheight: 55,
		               
		                 columns: [
		                       { text: 'Projeto', datafield: 'projeto',columngroup:"atividade_Projeto_Usuario",align: "center",cellsalign: "center",width: 150,filtertype: 'textbox'},
		                       { text: 'Photo', width: 100, columngroup:"atividade_Projeto_Usuario",cellsrenderer: photorenderer2 },
		                       { text: 'Usuario', datafield: 'usuario',columngroup:"atividade_Projeto_Usuario",align: "center",cellsalign: "center", width: 200,filtertype: 'textbox' },
		                       { text: 'Qtd.', datafield: 'totalatividades_usuario',columngroup:"atividade_Projeto_Usuario",align: "center", width: 80,cellsalign: "center",filtertype: 'checkedlist' }
		                       
		                       
		                   ],
		                  columngroups: [
		                	  { text: "Atividade por Usuario e Projeto", align: "center",name: "atividade_Projeto_Usuario"},
		                   ]
		             });
		 }
		 $.ajax({
	 		  type: "POST",
	 		  data: {"opt":"25",
	 			  "_": timestamp
	 			},		  
	 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
	 		  url: "./RolloutServlet",
	 		  cache: false,
	 		  dataType: "text",
	 		 success: atualiza_select_campos_mapaOperacional
	 		});
		 function atualiza_select_campos_mapaOperacional(data){
			 $('#select_campo_mapa_operacinal').html(data);
			 $('#select_campo_mapa_operacinal').selectpicker('refresh');
		 }
}
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
		 
		 
		 $('#docking').jqxDocking({  orientation: 'horizontal', mode: 'docked',theme: 'material' });
		 $('#docking_rollout').jqxDocking({  orientation: 'horizontal',width:1200, mode: 'docked',theme: 'material' });
		 $('#docking').jqxDocking('importLayout', data);
		 console.log(geral.perfil);
		 if(geral.perfil.search("RolloutManager")>=0){
			 
			 $("#from").jqxDateTimeInput({selectionMode: 'range'});
			 $("#from2").jqxDateTimeInput({selectionMode: 'range'});
			 $("#from").on('change', function (event) {
                 //var selection = $("#jqxWidget").jqxDateTimeInput('getRange');
                 g1(1,'grafico_container1_rollout');
             });
			 $("#from2").on('change', function (event) {
                 //var selection = $("#jqxWidget").jqxDateTimeInput('getRange');
                 g9(1,'grafico_container2_rollout');
             });
			 //g1(0,'grafico_container1_rollout');
			 //g9(0,'grafico_container2_rollout');
			 
		 }
		 if(geral.perfil.search("FinanceiroManager")>=0){
			 g2(0,'grafico_container2');
			 g4(0,'grafico_container4');
		 }
		 if(geral.perfil.search("POManager")>=0){
			 g3(0,'grafico_container3');
		 }
		 if(geral.perfil.search("POManager")>=0){
			 g3(0,'grafico_container3');
		 }
		 g5(0,'grafico_container3');
		 g6('grafico_container1');
		 g7('grafico_container2');
		 g8('grafico_container4');
		 
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