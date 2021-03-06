function atualiza_opt_filtro(opt){
	document.getElementById('opt_mapa_operacinal').value=opt;
	$("#resumo_tipo_campos_filtros_mapaOperacional").html(sessionStorage.getItem("filtrosOperacional_"+opt));
}
function getWidth(idelemeent){
	var elmnt = document.getElementById(idelemeent);
	return elmnt.offsetWidth;
}
function limpar_filtros_mapa_operacional(){
	var opt=document.getElementById('opt_mapa_operacinal').value;
	sessionStorage.removeItem("filtrosOperacional_"+opt);
	$("#resumo_tipo_campos_filtros_mapaOperacional").html('');
}
function atualiza_mapa_operacional(){
	var opt=document.getElementById('opt_mapa_operacinal').value;
	//alert(opt);
	if(opt=='1'){
		operacional1();
	}else if(opt=='2'){
		operacional2();
	}else if(opt=='4'){
		operacional4();
	}else if(opt=='3'){
		operacional3();
	}
	
}
function operacional2(){
	var timestamp =Date.now();
	$.ajax({
		  type: "POST",
		  data: {"opt":"22",
			  "_": timestamp,
			  "filtros":sessionStorage.getItem("filtrosOperacional_2")
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
	                       { text: 'Iniciada', datafield: 'sites_planejados',columngroup:"Sites_Projeto", width: 100,cellsalign: "center", filtertype: 'checkedlist' },
	                       { text: 'N Iniciada', datafield: 'sites_planejados_done',columngroup:"Sites_Projeto", width: 100,cellsalign: "center", filtertype: 'checkedlist' }
	                       
	                   ],
	                  columngroups: [
	                	  { text: "Escopo de Projetos", align: "center",name: "Sites_Projeto"},
	                   ]
	             });
	 }
}
function operacional1(){
	var timestamp =Date.now();
	$("#jqxLoader_grid_func_mapaOperacional").jqxLoader({ text: "Carregando Rollout",width: 100, height: 60, imagePosition: 'top' });
	$('#jqxLoader_grid_func_mapaOperacional').jqxLoader('open');
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"21",
			  "_": timestamp,
			  "filtros":sessionStorage.getItem("filtrosOperacional_1")
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
	 $('#grid_func_mapaOperacional').on('rowclick', function (event) 
			 {
			     var args = event.args;
			     //console.log(args);
			     $.getJSON('./SiteMgmt?opt=13&usuario='+args.row.bounddata.usuario+'&_='+timestamp, function(data) {
			    	 //console.log(data);
			    	 //map.zoomTo(3, {duration: 9000});
				     map.flyTo({center: data,zoom: 14});
			     });
			 }); 
}
function operacional4(){
	var timestamp =Date.now();
	 $.ajax({
		  type: "POST",
		  data: {"opt":"23",
			  "_": timestamp,
			  "filtros":sessionStorage.getItem("filtrosOperacional_4")
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
}
function operacional3(){
	var timestamp =Date.now();
	$.ajax({
		  type: "POST",
		  data: {"opt":"24",
			  "_": timestamp,
			  "filtros":sessionStorage.getItem("filtrosOperacional_3")
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
}
function carrega_mapa_operacional(){
	var timestamp =Date.now();
    $('#jqxtabs_mapaOperacional').jqxTabs({theme: 'light'});
    $('#docking_operacional').jqxDocking({  orientation: 'horizontal', mode: 'docked',theme: 'material' });
	
	operacional1();
	operacional2();
	operacional4();	 
	operacional3();	
		 
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
function carrega_portalRollout(){
	if(geral.perfil.search("RolloutManager")>=0){
		 $("#from").jqxDateTimeInput({selectionMode: 'range'});
         $("#from2").jqxDateTimeInput({selectionMode: 'range'});
        
		$("#from").on('change', function (event) {
	        //var selection = $("#jqxWidget").jqxDateTimeInput('getRange');
	        g1(1,'grafico_container1_rollout');
	        
	    });
		 
		 $("#from2").on('change', function (event) {
	        g9(1,'grafico_container2_rollout');
	    });
		 g1(0,'grafico_container1_rollout');
		 g9(1,'grafico_container2_rollout');
		 g13('grafico_container3_rollout');
		  $("#jqxRadioButton_rollout").jqxRadioButton({ width: 150, height: 25, checked: true});
          $("#jqxRadioButton2_rollout").jqxRadioButton({ width: 150, height: 25});
          $("#jqxRadioButton3_rollout").jqxRadioButton({ width: 150, height: 25});
		 g14('grafico_container4_rollout');
		
	}
}
function carrega_portal(){
	 var cont=0;
	 var cont_aux=1;
	 
		 
		if(geral.perfil.search("FinanceiroManager")>=0){
			 g2(0,'grafico_container2');
			 g4(0,'grafico_container4');
		 }
		 if(geral.perfil.search("POManager")>=0){
			 g3(0,'grafico_container1_PO');
			 g2(0,'grafico_container2_PO');
		 }
		 g5(0,'grafico_container3');
		 g6('grafico_container1');
		 g7('grafico_container2');
		 g8('grafico_container4');
		 g10();
		 g11();
		 g12();
		 grid_ticket_chart();
		
		//console.log(geral.perfil);
		 
	 
 }
function carregaModulosPortal(){
	var timestamp =Date.now();
	$.ajax({
		  type: "POST",
		  data: {"opt":"48",
			  "_": timestamp
			},		  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		 success: carregaModulosOK
		});
	function carregaModulosOK(data){
		//alert(data);
	}
}

function grid_ticket_chart(){
	var timestamp =Date.now();
	$.ajax({
		  type: "POST",
		  data: {"opt":"47",
			  "_": timestamp
			},		  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		 success: carrega_grid_chart_ticket
		});
	 function carrega_grid_chart_ticket(data){
		 dataaux=JSON.parse(data);
	var source =
    {
			 datatype: "json",
	         datafields: [
	        	 { name: 'id' , type: 'int'},
	        	 { name: 'projeto', type: 'string' },
	             { name: 'recurso', type: 'string' },
	             { name: 'inicio' , type: 'string'},
	             { name: 'fim', type: 'string' },
	             { name: 'status', type: 'string' }
	             
	         ],
        localdata: dataaux.dados,
        id: 'id',
    };
	 var dataAdapter = new $.jqx.dataAdapter(source);
	 
   
    $("#grid_ticket_chart").jqxGrid(
            {
            	width: 1200,
                height: 150,
                source: dataAdapter,
                columnsresize: true,
	            filterable: true,
	            autoshowfiltericon: true,
                columns: [
                      { text: 'Ticket', datafield: 'id',cellsalign: "center",width: 200,filtertype: 'textbox'},
                      { text: 'Projeto', datafield: 'projeto',cellsalign: "center", width: 330,filtertype: 'checkedlist' },
                      { text: 'Recurso', datafield: 'recurso', width: 200,cellsalign: "center",filtertype: 'checkedlist' },
                      { text: 'Início', datafield: 'inicio', width: 150,cellsalign: "center", filtertype: 'checkedlist' },
                      { text: 'Fim', datafield: 'fim', width: 150,cellsalign: "center", filtertype: 'checkedlist' },
                      { text: 'Status', datafield: 'status', width: 150,cellsalign: "center", filtertype: 'checkedlist' }
                      
                  ]
            });
	 }
}