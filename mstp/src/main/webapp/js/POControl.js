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
		//alert(data);
		$("#div_tabela_po").html("<div id=\"toolbar_po_control\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#PO_upload_Modal\">Carregar PO</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"carrega_PO(1)\">Atualizar</button>"+
    			"<button id=\"apagar_po\" type=\"button\" class=\"btn btn-danger\">Apagar PO</button>"+
    			"<button id=\"validar_po\" type=\"button\" class=\"btn btn-danger\">Validar PO</button>"+
    			"</div>" + data);
		
		$('#tabela_de_po').bootstrapTable();
		
	    var $table = $('#tabela_de_po'),
		$button = $('#apagar_po');
	    $(function () {
	        $button.click(function () {
	        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
	                return row.po;
	            });
	            $table.bootstrapTable('remove', {
	                field: 'po',
	                values: ids
	            });
	            //alert(ids);
	            deleta_PO(String(ids));
	        });
	    });
	    $button = $('#validar_po');
	    $(function () {
	        $button.click(function () {
	        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
	                return row.po;
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
function deleta_PO(po_number){
	//alert(po_number);
	$.ajax({
		  type: "POST",
		  data: {"opt":"2",
			     "po":po_number},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess2
		});
	function onSuccess2(data)
	{
		
		carrega_ITEM();
		alert("Pos removidas");
	}

}
function validacao_PO(po_number){
	//alert(po_number);
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