function carrega_checklist(){
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
		 var initrowdetails = function (index, parentElement, gridElement, datarecord) {
			 var tabsdiv = null;
             var information = null;
             var notes = null;
             tabsdiv = $($(parentElement).children()[0]);
             if (tabsdiv != null) {
            	 
             }
		 }
		$("#gridChecklist").jqxGrid(
	             {
	                 width: 1400,
	                 height: 650,
	                 source: dataAdapter,
	                 rowdetails: true,
	                 columnsresize: true,
		             filterable: true,
		             autoshowfiltericon: true,
		             theme:'light',
		             pageable: true,
		             rowdetailstemplate: { rowdetails: "<div style='margin: 10px;'><ul style='margin-left: 30px;'><li class='title'></li><li>Fotos</li></ul><div class='information'></div><div class='notes'></div></div>", rowdetailsheight: 200 },
		             initrowdetails: initrowdetails,
		             pagesize: 20,
	                 columns: [
	                	   { text: 'ID Vistoria', datafield: 'id', width: 250,filtertype: 'checkedlist' },
	                       { text: 'Nome Checklist', datafield: 'checkListNome', width: 250,filtertype: 'checkedlist' },
	                       { text: 'Site ID', datafield: 'siteID', width: 150,filtertype: 'checkedlist' },
	                       { text: 'Status', datafield: 'statusCheckList', width: 150,filtertype: 'checkedlist' },
	                       { text: 'Responsável Atual', datafield: 'owner', width: 200,filtertype: 'checkedlist' },
	                       { text: 'Executor do Checklist', datafield: 'executorCheklist', width: 200,filtertype: 'checkedlist' }
	                   ],
	                 
	             });
	}
}