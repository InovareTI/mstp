function aprova_item(id,id_vistoria){
	var timestamp =Date.now();
	$.ajax({
		  type: "POST",
		  data: {"opt":"35",
			  "_": timestamp,
			  "idvistoria":id_vistoria,
			  "id":id
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		 success: item_aprovado
		});
	function item_aprovado(data){
		carrega_dados_checklist(id_vistoria);
	}
}
function rejeita_item(id,id_vistoria){
	var timestamp =Date.now();
	$.ajax({
		  type: "POST",
		  data: {"opt":"36",
			  "_": timestamp,
			  "idvistoria":id_vistoria,
			  "id":id
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		 success: item_rejeitado
		});
	function item_rejeitado(data){
		carrega_dados_checklist(id_vistoria);
	}
}
function visualiza_foto(idvistoria,id,titulo){
	
		
	 var aux='[{"src":"./RolloutServlet?opt=34&id_vistoria='+idvistoria+'&id='+id+'","title":"'+titulo+'"}]';
	 var auxJson=JSON.parse(aux);
	 var options = {
			   modalWidth: '600',
			   modalHeight: '650',
			    // for example:
			    index: 0 // this option means you will start at first image
			};
	 var viewer = new PhotoViewer(auxJson, options);
	 
}
function carrega_checklist(){
	$("#splitter_checklist").jqxSplitter({ width: '100%', theme:'light', height: '550', panels: [{ size: 600}] });
	var timestamp =Date.now();
	$.ajax({
		  type: "POST",
		  data: {"opt":"32",
			  "_": timestamp
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		 success: carregaChecklits
		});
	function carregaChecklits(data){
		dataaux=JSON.parse(data);
		var source =
	     {
				 datatype: "json",
		         datafields: [
		        	 { name: 'id' , type: 'string'},
		        	 { name: 'checkListNome', type: 'string' },
		             { name: 'siteID', type: 'string' },
		             { name: 'statusCheckList' , type: 'string'},
		             { name: 'owner', type: 'string' },
		             { name: 'executorCheklist', type: 'string' }
		         ],
	         localdata: dataaux,
	         id: 'id',
	     };
		var dataAdapter = new $.jqx.dataAdapter(source);
		var renderer = function (row, column, value) {
	         return '<span style="margin-left: 4px; margin-top: 9px; float: left;">' + value + '</span>';
	     }
		 
		$("#gridChecklist").jqxGrid(
	             {	
	                 width: 800,
	                 height: 550,
	                 source: dataAdapter,
	                 columnsresize: true,
		             filterable: true,
		             autoshowfiltericon: true,
		             theme:'light',
		             pageable: true,
		             pagesize: 20,
	                 columns: [
	                	   { text: 'ID Vistoria', datafield: 'id', width: 100,filtertype: 'checkedlist' },
	                       { text: 'Nome Checklist', datafield: 'checkListNome', width: 250,filtertype: 'checkedlist' },
	                       { text: 'Site ID', datafield: 'siteID', width: 150,filtertype: 'checkedlist' },
	                       { text: 'Status', datafield: 'statusCheckList', width: 150,filtertype: 'checkedlist' },
	                       { text: 'Responsável Atual', datafield: 'owner', width: 200,filtertype: 'checkedlist' },
	                       { text: 'Executor do Checklist', datafield: 'executorCheklist', width: 200,filtertype: 'checkedlist' }
	                   ],
	                 
	             });
		
		$('#gridChecklist').on('rowselect', function (event) 
				{
				    // event arguments.
				    var args = event.args;
				    //console.log(args);
				    var rowBoundIndex = args.rowindex;
				    // row's data. The row's data object or null(when all rows are being selected or unselected with a single action). If you have a datafield called "firstName", to access the row's firstName, use var firstName = rowData.firstName;
				    var rowData = args.row;
				    //console.log(rowData);
				    carrega_dados_checklist(rowData.id);
				});
		
	}
}
function carrega_dados_checklist(id_vistoria){
	var timestamp =Date.now();
	$.ajax({
 		  type: "POST",
 		  data: {"opt":"33",
 			  "_": timestamp,
 			  "idvistoria":id_vistoria
 			},		  
 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
 		  url: "./RolloutServlet",
 		  cache: false,
 		  dataType: "text",
 		 success: carregaChecklits_dados
 		});
  	 function carregaChecklits_dados(data){
  		 $("#dados_div_review").html(data);
  		 $("#dados_div_review").jqxPanel({ width: 800, height: 550});
  	 }
}