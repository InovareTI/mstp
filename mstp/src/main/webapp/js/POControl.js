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
			        	 { name: 'PO NUMBER', type: 'string' },
			             { name: 'ITEM', type: 'string' },
			             { name: 'VALOR UNITARIO' , type: 'string'},
			             { name: 'QTDE', type: 'string' },
			             { name: 'VALOR TOTAL', type: 'string' }
			             
			         ],
		         localdata: POrecords,
		         id: 'id',
		     };
			 var dataAdapter = new $.jqx.dataAdapter(source);
			 
			 $("#div_tabela_po").jqxGrid(
		             {
		            	 width: '100%',
		            	 theme:'light',
		                 source: dataAdapter,
		                 showtoolbar: true,
		                 columnsresize: true,
			             filterable: true,
			             autoshowfiltericon: true,
		                 rowsheight: 55,
		                 rendertoolbar: function (toolbar) {
		                	 var me = this;
			                 toolbar.empty();
			                 container = $("<div style='overflow: hidden; position: relative; margin: 1px;'></div>");
			                 addPO_button = $("<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#PO_upload_Modal\">Carregar PO</button>");
			                 rmPO_button = $("<button id=\"apagar_po\" type=\"button\" class=\"btn btn-danger\">Desabilitar PO</button>");
			                 container.append(addPO_button);
			                 container.append(rmPO_button);
			                 toolbar.append(container);
		                 },
		                 columns: [
		                       { text: 'PO', datafield: 'PO NUMBER',cellsalign: "center",width: 100,filtertype: 'textbox'},
		                       { text: 'Item', datafield: 'ITEM',cellsalign: "center", width: 130,filtertype: 'checkedlist' },
		                       { text: 'Valor Unti', datafield: 'VALOR UNITARIO', width: 100,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Quantidade', datafield: 'QTDE', width: 100,cellsalign: "center", filtertype: 'checkedlist' },
		                       { text: 'Valor Total', datafield: 'VALOR TOTAL', width: 100,cellsalign: "center", filtertype: 'checkedlist' }
		                       
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