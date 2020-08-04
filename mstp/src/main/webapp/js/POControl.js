/**
 * 
 */





function carrega_PO(opc){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"1"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess1
		});
	
	function onSuccess1(data)
	{
		    var POrecords=JSON.parse(data);
			var source =
		     {
					 datatype: "json",
			         datafields: [
			        	 { name: 'ID' , type: 'string'},
			        	 { name: 'SITE', type: 'string' },
			        	 { name: 'PO NUMBER', type: 'string' },
			             { name: 'ITEM', type: 'string' },
			             { name: 'ITEM_CODE', type: 'string' },
			             { name: 'PO LINE', type: 'string' },
			             { name: 'SHIPMENT', type: 'string' },
			             { name: 'PUBLISHED' , type: 'string'},
			             { name: 'Carregada' , type: 'string'},
			             { name: 'Validada' , type: 'string'},
			             { name: 'VALOR UNITARIO' , type: 'string'},
			             { name: 'QTDE', type: 'string' },
			             { name: 'VALOR TOTAL', type: 'string' }
			             
			         ],
		         localdata: POrecords,
		         id: 'ID',
		     };
			var cellclass = function(row,columnfield,value){
				if(value=='PO NAO VALIDADA'){
					return 'yellow';
				}
				if(value == 'REJEITADA'){
					return 'red';
				}
				return 'green';
			}

			var initrowdetails = function (index, parentElement, gridElement, datarecord) {
				 tabsdiv = $($(parentElement).children()[0]);
				 if (tabsdiv != null) {
					 information = tabsdiv.find('.information');
					 var container = $('<div style="margin: 5px;"></div>')
	                 container.appendTo($(information));
					 //console.log(datarecord);
					 $.ajax({
						  type: "POST",
						  data: {"opt":"41","pono":datarecord['PO NUMBER'],"itemcode":datarecord['ITEM_CODE'],"poline":datarecord['PO LINE'],"publishdate":datarecord['PUBLISHED']},		  
						  //url: "http://localhost:8080/DashTM/D_Servlet",	  
						  url: "./POControl_Servlet",
						  cache: false,
						  dataType: "text",
						  success: getPOdetalhe
						});
					 function getPOdetalhe(data){
						 var aux=JSON.parse(data);
						 
						 container.append("<pre>"+JSON.stringify(aux,['ID','PO NO','PR NO','Publish Date','Item Code','Item Description','PO Line NO','Unit Price','Requested Qty','Line Amount'],'\t')+"</pre>");
					 }
				 }
			
				 $(tabsdiv).jqxTabs({ width: 850, height: 470, theme:'light',});
			 }
			 var dataAdapter = new $.jqx.dataAdapter(source);
			 
			 $("#div_tabela_po").jqxGrid(
		             {
		            	 width: '100%',
		            	 height: '90%',
		            	 theme:'light',
		                 source: dataAdapter,
		                 showtoolbar: true,
		                 columnsresize: true,
			             filterable: true,
			             autoshowfiltericon: true,
		                 rowsheight: 55,
		                 pageable: true,
		                 pagesize: 20,
		                 selectionmode: 'checkbox',
		                 rowdetails: true,
		                 rowdetailstemplate: { rowdetails: "<div style='margin: 10px;'><ul style='margin-left: 30px;'><li class='title'>Detalhe</li><li>Tickets</li></ul><div class='information'></div><div class='notes'></div></div>", rowdetailsheight: 500 },
		                 initrowdetails: initrowdetails,
		                 rendertoolbar: function (toolbar) {
		                	 var me = this;
			                 toolbar.empty();
			                 container = $("<div style='overflow: hidden; position: relative; margin: 1px;'></div>");
			                 addPO_button = $("<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#PO_upload_Modal\">Carregar PO</button>");
			                 ValidaPO_button = $("<button id=\"btn_valida_po\" onclick=\"validacao_PO()\" type=\"button\" class=\"btn btn-info\" >Validar Item de PO</button>");
			                 rmPO_button = $("<button id=\"apagar_po\" onclick=\"deleta_PO()\" type=\"button\" class=\"btn btn-danger\">Desabilitar PO</button>");
			                 exportPO_button = $("<button id=\"export_po_button\" type=\"button\" class=\"btn btn-danger\" onclick=\"exportPOTable()\">Download Controle Master</button>");
			                 container.append(addPO_button);
			                 container.append(ValidaPO_button);
			                 container.append(rmPO_button);
			                 container.append(exportPO_button);
			                 toolbar.append(container);
		                 },
		                 columns: [
		                       { text: 'PO', datafield: 'PO NUMBER',align: "center",cellsalign: "center",width: 140,filtertype: 'textbox'},
		                       { text: 'Publicada', align: "center",datafield: 'PUBLISHED', width: 150,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Carregada', align: "center",datafield: 'Carregada', width: 150,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Validada', align: "center",datafield: 'Validada', width: 150,cellclassname:cellclass,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Site', align: "center",datafield: 'SITE',cellsalign: "center", width: 330,filtertype: 'textbox' },
		                       { text: 'Descrição do Item', align: "center",datafield: 'ITEM',cellsalign: "center", width: 330,filtertype: 'checkedlist' },
		                       { text: 'Shipment NO', align: "center",datafield: 'SHIPMENT',cellsalign: "center", width: 110,filtertype: 'checkedlist' },
		                       { text: 'V.Unitário', align: "center",datafield: 'VALOR UNITARIO', width: 90,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Qty', align: "center",datafield: 'QTDE', width: 80,cellsalign: "center", filtertype: 'checkedlist' },
		                       { text: 'V.Total', align: "center",datafield: 'VALOR TOTAL', width: 100,cellsalign: "center", filtertype: 'checkedlist' }
		                       
		                   ]
		             });
			
		
			    $('#apagar_po').removeClass( "disabled" );
				$('#apagar_po').text('Remover PO');
				$('#apagar_po').prop('disabled', false);
				
				$('#btn_valida_po').removeClass( "disabled" );
				$('#btn_valida_po').text('Valida Item de PO');
				$('#btn_valida_po').prop('disabled', false);
	            
	     
	           // validacao_PO();
	     
	    //alert("atualizando PO's");
		
	}
	if(opc==1){
		//alert("atualizando itens!");
		carrega_ITEM();
	}
}
function exportPOTable(){
	var timestamp = Date.now();	
	$('#export_po_button').addClass( "disabled" );
	$('#export_po_button').text('Aguarde a Finalização...');
	$('#export_po_button').prop('disabled', true);
	$.ajax({
        url: './POControl_Servlet?opt=48',
        method: 'GET',
        data:{"opt":"48"},
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data) {
        	//console.log(data);
            var a = document.createElement('a');
            var url = window.URL.createObjectURL(data);
            a.href = url;
            a.download = 'MSTP_MasterControl_'+timestamp+'.xlsx';
            a.click();
            window.URL.revokeObjectURL(url);
            $('#export_po_button').removeClass( "disabled" );
    		$('#export_po_button').prop('disabled', false);
    		$('#export_po_button').text("Exportar PO's");
    	
        }
    });
}
function carrega_grid_poItem(po){
	console.log(po);
	$.ajax({
		  type: "POST",
		  data: {"opt":"43","po":po},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: monda_grid_po_Item
		});
	function monda_grid_po_Item(data){
		
		 var POrecords= JSON.parse(data);
		 
			var source =
		    {
					 datatype: "json",
			         datafields: [
			        	 { name: 'id' , type: 'int'},
			        	 { name: 'PO_NUM', type: 'string',editable:false },
			        	 { name: 'ITEM_CODE', type: 'string',editable:false },
			             { name: 'ITEM', type: 'string',editable:false },
			             { name: 'PO_LINE', type: 'string',editable:false },
			             { name: 'PUBLISHED' , type: 'string',editable:false},
			             { name: 'QTDE', type: 'int',editable:false },
			             { name: 'QTDE_TICKET', type: 'int' },
			             { name: 'PROJETO', type: 'string',editable:false },
			             { name: 'SITE', type: 'string',editable:false },
			         ],
		        localdata: POrecords,
		        id: 'id',
		    };
			var dataAdapter = new $.jqx.dataAdapter(source);
			$('#grid_item_po_ticket').jqxGrid('destroy');
 	        $('#grid_item_container').append('<div id="grid_item_po_ticket"></div>')
 	 		
			 $("#grid_item_po_ticket").jqxGrid(
		             {
		            	 width: '100%',
		            	 height: '90%',
		            	 theme:'light',
		                 source: dataAdapter,
		                 editable: true,
		                 altrows: true,
		                 rowsheight: 55,
		                 pageable: true,
		                 pagesize: 20,
		                 selectionmode: 'checkbox',
		                 columns: [
		                	   { text: 'Site', align: "center",datafield: 'SITE', width: 150,cellsalign: "center",filtertype: 'checkedlist',editable:false },
		                       { text: 'Publicada', align: "center",datafield: 'PUBLISHED', width: 150,cellsalign: "center",filtertype: 'checkedlist',editable:false },
		                       { text: 'Descrição do Item', align: "center",datafield: 'ITEM',cellsalign: "center", width: 330,filtertype: 'checkedlist',editable:false },
		                       { text: 'Quantidade Disponível', align: "center",datafield: 'QTDE', width: 100,cellsalign: "center", filtertype: 'checkedlist',editable:false },
		                       { text: 'Quantidade Tickect', align: "center",datafield: 'QTDE_TICKET', width: 100,cellsalign: "center", filtertype: 'checkedlist' }
		                   ]
		             });
	}
}
function downloadPO(idarquivo){
	//alert(idarquivo);
	
	$.ajax({
        url: './POControl_Servlet?opt=13&id_arquivo='+idarquivo,
        method: 'GET',
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data) {
            var a = document.createElement('a');
            var url = window.URL.createObjectURL(data);
            a.href = url;
            a.type = 'application/pdf';
            a.download = idarquivo+'.pdf';
            a.click();
            window.URL.revokeObjectURL(url);
        }
    });
}
function desenvolvimento(){
	$.alert("Em Desenvolvimento");
}
function MostraItemPO(po){
	$.ajax({
		  type: "POST",
		  data: {"opt":"3","po":po},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccessItemPO
		});
	
	function onSuccessItemPO(data)
	{
		menu('ItemPO');
		$("#div_tabela_Itempo").html("<div id=\"toolbar_po_control\" role=\"toolbar\" class=\"btn-toolbar\">"+
      		"<button type=\"button\" class=\"btn btn-info\" onclick=\"menu('PO')\">Voltar</button>"+
  			"<button type=\"button\" class=\"btn btn-info\">Atualizar</button>"+
  			"<button type=\"button\" class=\"btn btn-danger\" onclick='desenvolvimento()'>Sincronizar com Rollout</button>"+
  			"<button type=\"button\" class=\"btn btn-danger\" onclick='desenvolvimento()'>Delivery Status</button>"+
  			"<button type=\"button\" class=\"btn btn-danger\" onclick='desenvolvimento()'>Faturamento Status</button>"+
  			"</div>" + data);
		$('#tabela_de_item_po').bootstrapTable();
	}
}
function deleta_PO(){
	$('#apagar_po').addClass( "disabled" );
	$('#apagar_po').text('Aguarde a Finalização...');
	$('#apagar_po').prop('disabled', true);
	var rows = $('#div_tabela_po').jqxGrid('getdisplayrows');
	var rowindexes = $('#div_tabela_po').jqxGrid('getselectedrowindexes');
	for(var v=0;v<rowindexes.length;v++){
		filtros.filtros.push($('#div_tabela_po').jqxGrid('getrowid', rowindexes[v]));
	}
	$.ajax({
		  type: "POST",
		  data: {"opt":"2",
			     "po":JSON.stringify(filtros)},		  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess2
		});
	function onSuccess2(data)
	{
		carrega_PO(0);
		$.alert("PO's Desativadas");
		$("#div_tabela_po").jqxGrid('clearselection');
		
	}
	
}
function validacao_PO(po_number){
	$('#btn_valida_po').addClass( "disabled" );
	$('#btn_valida_po').text('Aguarde a Finalização...');
	$('#btn_valida_po').prop('disabled', true);
	var rows = $('#div_tabela_po').jqxGrid('getdisplayrows');
	var rowindexes = $('#div_tabela_po').jqxGrid('getselectedrowindexes');
	for(var v=0;v<rowindexes.length;v++){
		filtros.filtros.push($('#div_tabela_po').jqxGrid('getrowid', rowindexes[v]));
	}
	$.ajax({
		  type: "POST",
		  data: {"opt":"4",
			     "po":JSON.stringify(filtros)},		  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccessValidaItem
		});
	function onSuccessValidaItem(data)
	{
		carrega_PO(0);
		$.alert("PO Itens Validados");
		$("#div_tabela_po").jqxGrid('clearselection');
		g15(0,'grafico_container3_PO');
	}
}
function carrega_ITEM(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"3"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess3
		});
	function onSuccess3(data)
	{
		
                $("#div_tabela_item_po").html("<div id=\"toolbar_tabela_item\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"sync_rollout()\">Sincronizar itens no Rollout</button>"+
    			
    			"</div>" + data);
		
		//$("#div_tabela_item_po").html(data);
		$('#tabela_de_item_po').bootstrapTable();
	    
	}

}