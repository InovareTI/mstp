function carrega_cliente_table(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"8"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess8
		});
	
	function onSuccess8(data)
	{
		$("#div_tabela_clientes").html("<div id=\"toolbar_cliente\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#modal_customer_add\">Novo CLiente</button>"+
    			
    			"<button id=\"validar_cliente\" type=\"button\" class=\"btn btn-danger\">Desabilitar Cliente</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"carrega_cliente_table()\">Atualizar</button>"+
    		    "</div>" + data);
		
		$('#tabela_de_clientes').bootstrapTable();
	    
}
}
function atualiza_select_cliente(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"11"},		  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "html",
		  success: onSuccess11
		});
	function onSuccess11(data)
	{
		$("#cliente_carrega_po").html(data);
		$("#projeto_nome_cliente").html(data);
		$("#select_conta_faturamento").html(data);
		$("#cliente_carrega_po").selectpicker('refresh');
		//alert (data);
		
	}
}
function add_customer(){
	
	var clientenmecompleto,clientenme,cnpj,endereco,contato,insce;
	
	
	clientenmecompleto= document.getElementById("cliente_nome_completo").value;
	
	clientenme= document.getElementById("cliente_nome_resumido").value;
	cnpj= document.getElementById("cliente_cnpj").value;
	endereco= document.getElementById("cliente_endereco").value;
	contato= document.getElementById("cliente_telefone").value;
	insce=document.getElementById("cliente_ie").value;
	
	if(clientenmecompleto.length==0){
		alert("Nome Completo do Cliente é Obrigatório");
		return;
	} 
	if(clientenme.length==0){
		alert("Nome do Cliente é Obrigatório");
		return;
	}
	if(cnpj.length==0){
		alert("CNPJ do Cliente é Obrigatório");
		return;
	} 
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"9",
			"nomeCompleto":clientenmecompleto,  
			"nome":clientenme, 
			"cnpj":cnpj, 
			"inscEstadual":insce, 
			"endereco":endereco, 
			"contato":contato
			
		  },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess9
		});
	
	function onSuccess9(data)
	{
	atualiza_select_cliente();	
	$.alert("Código interno do Cliente: " + data);
	}
	}