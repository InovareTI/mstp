 function carrega_tabela_faturamento(){
	//alert($('#select_conta_faturamento').val());
	$.ajax({
		  type: "POST",
		  data: {"opt":"29",
			  "conta":$('#select_conta_faturamento').val(),
			  "projeto":$('#select_projeto_faturamento').val()
			  },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess29
		});
	function onSuccess29(data)
	{
		$("#div_tabela_faturamento").html("<div id=\"toolbar_tabela_faturamento\" role=\"toolbar\" class=\"btn-toolbar\">"+
				"<button type=\"button\" class=\"btn btn-success\" data-toggle=\"modal\" data-target=\"#modal_fluxo_pagamento\">Fluxo de Pagamento</button>"+
    			"<button type=\"button\" class=\"btn btn-info\" onclick=\"buscar_dados_rollout()\">Sincronizar</button>"+
    			"<button type=\"button\" class=\"btn btn-danger\">Remover Item</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\">Dashboard</button>"+
    		    "</div>" + data);
		$('#tabela_faturamento').bootstrapTable();
	    
	}

}
 function add_item_pgfluxo(){
	    var $table = $('#table_fluxopg_item');
	    var $table_faturamento=$('#tabela_faturamento');
	    var index_aux= $table.bootstrapTable('getData').length;
	    //alert("antes da soma:"+index_aux);
	    index_aux=index_aux+1;
	   // alert("depois da soma:"+index_aux);
	    var randomId = 100 + ~~(Math.random() * 100);
	    //alert($table_faturamento.bootstrapTable('getSelections').length);
	     $table.bootstrapTable('insertRow', {
	                index: index_aux,
	                row: {
	                    id: index_aux,
	                    milestone: "<a class='milestone_pg_fluxo' href='#' id='milestone"+(index_aux)+"' name='milestone"+(index_aux)+"' data-title='Milestone' data-type='select' data-pk="+index_aux+"></a>",
	                    porcentagem: "<a class='percent_pg_fluxo' href='#' id='percent"+(index_aux)+"' name='percent"+(index_aux)+"' data-title='Percentual' data-type='text' data-pk="+index_aux+"></a>",
	                }
	            });
	     
	     $('.milestone_pg_fluxo').editable( {source: milestone_pg });
	     $('.percent_pg_fluxo').editable();
	     
	  }
 function limpa_tabelafluxo_(){
	 var $table = $('#table_fluxopg_item');
	 $table.bootstrapTable('removeAll');
 }
 function buscar_dados_rollout(){
	 $.ajax({
		  type: "POST",
		  data: {"opt":"32"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess32
		});
	function onSuccess32(data)
	{
		alert("Dados Sincronizados do Rollout!");
		carrega_tabela_faturamento();
	}
 }
 function salvar_fluxo_pg(){
	    var $table = $('#table_fluxopg_item');
	    var $table_faturamento=$('#tabela_faturamento');
	    var index_aux= $table.bootstrapTable('getData').length;
	    var index_aux2= $table_faturamento.bootstrapTable('getSelections').length;
	    var randomId = 100 + ~~(Math.random() * 100);
	    var dados_fluxo;
	    var soma;
	    var text = '{"fluxo":[]}';
		var fluxo = JSON.parse(text);
	    
	    soma=0;
	    if($table_faturamento.bootstrapTable('getSelections').length==0){
	    	alert("Nehum item Selecionado para esse fluxo!");
	    	return;
	    }
	    //alert(JSON.stringify($table_faturamento.bootstrapTable('getSelections')));
	    dados_fluxo=$table_faturamento.bootstrapTable('getSelections');
	   // alert(index_aux);
	   
	    for (i=1;i<=index_aux;i++){
	    	if($('#milestone'+i).editable('getValue',true)==null){
		    	alert("Favor definir os milestones corretamente!");
		    	return;
		    }
		    soma=soma+parseInt($('#percent'+i).editable('getValue',true));
		   	   
	    }
	   
	    if(soma!=100){
	    	alert("A soma das porcentagens precisa ser igual a 100!");
	    	return;
	    }
	    for(j=0;j<index_aux2;j++){ 
		    for (i=1;i<=index_aux;i++){
		    	  dados={id:dados_fluxo[j]['id_item_faturamento'],milestone:$('#milestone'+i).editable('getValue',true),percentual:$('#percent'+i).editable('getValue',true)};
		 		  fluxo.fluxo.push(dados);
		    }
	    }
	    //alert(JSON.stringify(fluxo));
	    $.ajax({
			  type: "POST",
			  data: {"opt":"31","fluxo":JSON.stringify(fluxo)},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./POControl_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess31
			});
		function onSuccess31(data)
		{
			carrega_tabela_faturamento();
			alert("Fluxo de Pagamento Registrado com Sucesso!");
			$('#modal_fluxo_pagamento').modal("toggle");
			limpa_tabelafluxo_();
		}
 }