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
		
		if(geral.empresa_id==1){
			
			var POrecords=JSON.parse(data);
			var source =
		     {
					 datatype: "json",
			         datafields: [
			        	 { name: 'id' , type: 'int'},
			        	 { name: 'SITE', type: 'string' },
			        	 { name: 'PO NUMBER', type: 'string' },
			             { name: 'ITEM', type: 'string' },
			             { name: 'ITEM_CODE', type: 'string' },
			             { name: 'PO LINE', type: 'string' },
			             { name: 'SHIPMENT', type: 'string' },
			             { name: 'PUBLISHED' , type: 'string'},
			             { name: 'Carregada' , type: 'string'},
			             { name: 'VALOR UNITARIO' , type: 'string'},
			             { name: 'QTDE', type: 'string' },
			             { name: 'VALOR TOTAL', type: 'string' }
			             
			         ],
		         localdata: POrecords,
		         id: 'id',
		     };
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
			                 rmPO_button = $("<button id=\"apagar_po\" type=\"button\" class=\"btn btn-danger\">Desabilitar PO</button>");
			                 exportPO_button = $("<button id=\"export_po_button\" type=\"button\" class=\"btn btn-danger\" onclick=\"exportPOTable()\">Download Controle Master</button>");
			                 container.append(addPO_button);
			                 container.append(rmPO_button);
			                 container.append(exportPO_button);
			                 toolbar.append(container);
		                 },
		                 columns: [
		                       { text: 'PO', datafield: 'PO NUMBER',align: "center",cellsalign: "center",width: 150,filtertype: 'textbox'},
		                       { text: 'Publicada', align: "center",datafield: 'PUBLISHED', width: 150,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Carregada', align: "center",datafield: 'Carregada', width: 150,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Site', align: "center",datafield: 'SITE',cellsalign: "center", width: 330,filtertype: 'textbox' },
		                       { text: 'Descrição do Item', align: "center",datafield: 'ITEM',cellsalign: "center", width: 330,filtertype: 'checkedlist' },
		                       { text: 'Shipment NO', align: "center",datafield: 'SHIPMENT',cellsalign: "center", width: 130,filtertype: 'checkedlist' },
		                       { text: 'Valor Unitário', align: "center",datafield: 'VALOR UNITARIO', width: 100,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Quantidade', align: "center",datafield: 'QTDE', width: 100,cellsalign: "center", filtertype: 'checkedlist' },
		                       { text: 'Valor Total', align: "center",datafield: 'VALOR TOTAL', width: 100,cellsalign: "center", filtertype: 'checkedlist' }
		                       
		                   ]
		             });
			
		}else{
		$("#div_tabela_po").html("<div id=\"toolbar_po_control\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#PO_upload_Modal\">Carregar PO</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"carrega_PO(1)\">Atualizar</button>"+
    			"<button id=\"apagar_po\" type=\"button\" class=\"btn btn-danger\">Apagar PO</button>"+
    			"<button id=\"validar_po\" type=\"button\" class=\"btn btn-danger\">Validar PO</button>"+
    			"<button type=\"button\" class=\"btn btn-danger\" onclick='desenvolvimento()'>Delivery Status</button>"+
      			"<button type=\"button\" class=\"btn btn-danger\" onclick='desenvolvimento()'>Faturamento Status</button>"+
    			"</div>" + data);
		
		$('#tabela_de_po').bootstrapTable();
		
	    var $table = $('#tabela_de_po');
		
	    $(function () {
	    	$('#apagar_po').click(function () {
	        	
	        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
	                return row.po_mstp_id;
	            });
	        	
	            $table.bootstrapTable('remove', {
	                field: 'po_mstp_id',
	                values: ids
	            });
	            
	            deleta_PO(String(ids));
	        });
	    });
	    
	    $(function () {
	    	$('#validar_po').click(function () {
	        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
	                return row.po_mstp_id;
	            });
	            validacao_PO(String(ids));
	        });
	    });
	    //alert("atualizando PO's");
		}
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
function deleta_PO(po_number){
	if(po_number){
	$.ajax({
		  type: "POST",
		  data: {"opt":"2",
			     "po":po_number},		  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess2
		});
	function onSuccess2(data)
	{
		
		$.alert("PO's Desativadas");
	}
	}else{
		$.alert("seleção de PO's parece inválida.");
	}
}
function validacao_PO(po_number){
	if(po_number){
	$.ajax({
		  type: "POST",
		  data: {"opt":"4",
			     "po":po_number},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess4
		});
	function onSuccess4(data)
	{
		carrega_ITEM();
		alert("Pos Validadas");
	}
	}else{
		$.alert("seleção de PO's parece inválida.");
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