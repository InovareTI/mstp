function carrega_project_table(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"6"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess6
		});
	
	function onSuccess6(data)
	{
		$("#div_tabela_projetos").html("<div id=\"toolbar_projetos\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#modal_project_add\">Novo Projeto</button>"+
    			
    			"<button id=\"validar_po\" type=\"button\" class=\"btn btn-danger\">Desabilitar Projeto</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"carrega_project_table()\">Atualizar</button>"+
    		    "</div>" + data);
		
		$('#tabela_de_projeto').bootstrapTable();
		
	    
	}
}
function add_project(){
	
	var e,cliente,projeto,pm_cliente,mail_cliente,orcamento,dt_inicio,dt_fim,produto,estado;
	
	e= document.getElementById("projeto_nome_cliente");
	//cliente=e.options[e.selectedIndex].text;
	cliente=$('#projeto_nome_cliente').val();
	codigo= document.getElementById("projeto_codigo_cliente").value;
	e= document.getElementById("projeto_estado");
	estado=e.options[e.selectedIndex].text;
	projeto= document.getElementById("projeto_nome").value;
	pm_cliente= document.getElementById("projeto_pm_cliente").value;
	mail_cliente= document.getElementById("projeto_mail_cliente").value;
	orcamento= document.getElementById("projeto_orcamento").value;
	dt_inicio=document.getElementById("projeto_inicio_dt").value;
	dt_fim=document.getElementById("projeto_fim_dt").value;
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"7",
			"projeto":projeto,  
			"cliente":cliente, 
			"codigo":codigo, 
			"estado":estado, 
			"pmcliente":pm_cliente, 
			"mailcliente":mail_cliente, 
			"orcamento":orcamento, 
			"dtinicio":dt_inicio, 
			"dtfim":dt_fim 
		  },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess7
		});
	
	function onSuccess7(data)
	{
		
	alert(data);
	}
	}
function carrega_select_projeto(cliente){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"10","cliente":cliente},		  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "html",
		  success: onSuccessselectProject
		});
	function onSuccessselectProject(data)
	{
		
		$("#projeto_carrega_po").html(data);
		
	}
}