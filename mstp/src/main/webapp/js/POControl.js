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