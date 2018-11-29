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