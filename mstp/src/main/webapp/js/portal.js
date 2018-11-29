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