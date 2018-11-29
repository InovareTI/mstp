<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);
%>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"> 
    <META HTTP-EQUIV="Expires" CONTENT="-1">
    <link rel="stylesheet" type="text/css" href="css/div_graph.css" />
	
    <link rel="stylesheet" href="css/fileinput.min.css" type="text/css">
	<link rel="stylesheet" href="css/w2ui-1/w2ui-1.4.3.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-editable.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-table.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-table-reorder-rows.css" type="text/css">
	<link rel="stylesheet" href="css/bootstrap-multiselect.css" type="text/css">
	
	<link href="css/style.css"  type="text/css">
	<link href="css/default/style.min.css" type="text/css">
	<link href="css/simple-sidebar.css" >
	<link rel="stylesheet" href="js/jqwidgets/styles/jqx.base.css" type="text/css" />
	<link rel="stylesheet" href="css/diversos.css" type="text/css" />
	<script src="js/jquery-2.1.3.min.js" type="text/javascript"></script>
	<script src="js/plugins/canvas-to-blob.min.js" type="text/javascript"></script>
	<script src="js/fileinput.min.js" type="text/javascript"></script>
	<script src="js/fileinput_locale_pt-BR.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="js/bootstrap-editable.js"></script>
	<script src="js/accounting.js"></script>
	<script src="js/bootstrap-table.js"></script>
	<script src="js/bootstrap-table-toolbar.min.js"></script>
	<script src="js/bootstrap-multiselect.js"></script>
	
	<script src="js/highcharts.js"></script>
	<script src="js/modules/drilldown.js"></script>
   <script src="js/modules/data.js"></script>
	<script src="js/carregador_graficos.js"></script>
  	<script src="js/bootstrap-table-pt-BR.js"></script>
  	<script src="js/bootstrap-table-filter-control.js"></script>
  	
  	
  	<script src="js/bootstrap-table-reorder-rows.min.js"></script>
  	<script src="js/jquery.tablednd.js"></script>
  	<script src="js/moment-with-locales.min.js"></script>
   	<script src="js/locale/pt-br.js"></script>
  	<script src="js/bootstrap-datetimepicker.js"></script>
  	<script type="text/javascript" src="js/w2ui-1/w2ui-1.4.3.min.js"></script>
  	<script type="text/javascript" src="js/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxmenu.js"></script>
     <script type="text/javascript" src="js/jqwidgets/jqxcombobox.js"></script>
   
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.filter.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.sort.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.columnsresize.js"></script> 
    <script type="text/javascript" src="js/jqwidgets/jqxdata.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.pager.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxgrid.edit.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpanel.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdropdownlist.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxcalendar.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxnumberinput.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxpasswordinput.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxtooltip.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxwindow.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdocking.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxexpander.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqxdatetimeinput.js"></script>
    <script type="text/javascript" src="js/jqwidgets/globalization/globalize.js"></script>
    <script src="js/trata_fluxo_pg.js"></script>
  
  <script type="text/javascript">
        $(document).ready(function () {
            // create jqxMenu
            $("#jqxMenu").jqxMenu({ width: '100%', height: '32px', mode: 'vertical', autoSizeMainItems: true, enableHover: true , autoOpen: true});
            $("#jqxMenu").jqxMenu('minimize');
            $("#jqxMenu").css('visibility', 'visible');
        });
    </script>
  
   
  	<script>
  	
  	var text = '{ "atualizacoes":0, "campos" : []}';
	var g_changes = JSON.parse(text);
	var text = '{ "rows":[]}';
	var deletar=[];
	var deletar_grid=[];
	var row = JSON.parse(text);
	var milestone_pg;
  	$(document).ready(function() {
  		//alert("teste");
  					$('#input_2').on('filebatchpreupload', function(event, data, previewId, index) {
    						//alert($("#cliente_carrega_po").val());
    						//alert($("#projeto_carrega_po").val());
    						
  						if($("#cliente_carrega_po").val()==0){
    						alert("Selecionar Cliente");	
  							return {message:"Selecionar Cliente e Projeto"};
    						}
  						if($("#projeto_carrega_po").val()==0){
    						alert("Selecionar Projeto");	
  							return {message:"Selecionar Projeto"};
    						}
  						//data.extra={cliente:$("#cliente_carrega_po").val(),projeto:''};
  						//data.extra['cliente']=$("#cliente_carrega_po").val();
  						//alert(data.);
					});
					$("#input_2").fileinput({
					    uploadUrl: "./Upload_servlet", 
					    uploadAsync: false,
					    allowedFileExtensions: ['pdf', 'xls', 'xlsx'],
					    maxFileCount: 5,
					    uploadExtraData:function (previewId, index) {
					        var obj = {cliente:$("#cliente_carrega_po").val(),projeto:$("#projeto_carrega_po").val()};
					        //obj[0]=$("#cliente_carrega_po").val();
					        //obj[1]=$("#projeto_carrega_po").val()
					        return obj;
					    }
					}).on('fileuploaded', function(event, data, previewId, index) {
						carrega_PO(0);
				});
					$("#nova_senha").jqxPasswordInput({placeHolder: "Nova Senha:", showStrength: true, showStrengthPosition: "top",
		                passwordStrength: function (password, characters, defaultStrength) {
		                    var length = password.length;
		                    var letters = characters.letters;
		                    var numbers = characters.numbers;
		                    var specialKeys = characters.specialKeys;
		                    var strengthCoefficient = letters + numbers + 2 * specialKeys + letters * numbers * specialKeys;
		                    var strengthValue;
		                    if (length < 4) {
		                        strengthValue = "Muito Curta";
		                    } else if (strengthCoefficient < 10) {
		                        strengthValue = "Fraca";
		                    } else if (strengthCoefficient < 20) {
		                        strengthValue = "Justa";
		                    } else if (strengthCoefficient < 30) {
		                        strengthValue = "Boa";
		                    } else {
		                        strengthValue = "Forte";
		                    };
		                    return strengthValue;
		                }
		            });
					$("#senha_atual").jqxPasswordInput({placeHolder: "Senha Atual:"});
					$("#confirma_senha").jqxPasswordInput({placeHolder: "Confirmar Senha:"});
					carrega_tabela_campos_rollout();
					carrega_PO(0);
					
					$('#usuario_perfil').multiselect({nonSelectedText: 'Selecione um Perfil',allSelectedText: 'Todos'});
					carrega_ITEM();
					carrega_filtros_relatorios();
					carrega_portal();
					carrega_PO_subcon();
					carrega_gant();
					$("#jqxExpander").jqxExpander({ width: '100%'});
					carrega_project_table();
					carrega_cliente_table();
					$('input[type=us-date1]').w2field('date', { format: 'd/m/yyyy', end: $('input[type=us-date2]') });
					$('input[type=us-date2]').w2field('date', { format: 'd/m/yyyy', start: $('input[type=us-date1]') });
					carrega_ajuda();
					carrega_usuarios();
					atualiza_select_cliente();
					$('#modal_fluxo_pagamento').on('shown.bs.modal', function () {
						var $table_faturamento=$('#tabela_faturamento');
					   
						document.getElementById("itens_selecionados").innerHTML=$table_faturamento.bootstrapTable('getSelections').length + " itens selecionado(s)";
						$.ajax({
							  type: "POST",
							  data: {"opt":"30"},		  
							  url: "./POControl_Servlet",
							  cache: false,
							  dataType: "html",
							  success: onSuccess30
							});
						function onSuccess30(data)
						{
							milestone_pg=data;
							
						}
						
						
						
						});
					accounting.settings = {
							currency: {
								symbol : "R$",   // default currency symbol is '$'
								format: "%s%v", // controls output: %s = symbol, %v = value/number (can be object: see below)
								decimal : ",",  // decimal point separator
								thousand: ".",  // thousands separator
								precision : 2   // decimal places
							},
							number: {
								precision : 0,  // default precision on numbers is 0
								thousand: ".",
								decimal : ","
							}
						}
  	});
  	
	
</script>
<script>
function carrega_select_projeto_faturamento(cliente){
	$.ajax({
		  type: "POST",
		  data: {"opt":"10","cliente":cliente.value},		  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "html",
		  success: onSuccess10
		});
	function onSuccess10(data)
	{
		$("#select_projeto_faturamento").html(data);
		//$("#cliente_po_select").html(data);
		//alert (data);
		
	}
}
function carrega_select_projeto(cliente){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"10","cliente":cliente.value},		  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "html",
		  success: onSuccess10
		});
	function onSuccess10(data)
	{
		$("#projeto_carrega_po").html(data);
		//$("#cliente_po_select").html(data);
		//alert (data);
		
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
		//alert (data);
		
	}
}
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
		$("#div_tabela_po").html("<div id=\"toolbar\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#myModal\">Carregar PO</button>"+
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

  </script>

  <script>
function carrega_PO_subcon(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"5"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess5
		});
	
	function onSuccess5(data)
	{
		$("#div_emitir_tabela_po").html("<div id=\"toolbar_subcon\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#modal_subcon_po\">Criar Nova PO</button>"+
    			"<button id=\"apagar_po\" type=\"button\" class=\"btn btn-danger\">Submeter PO</button>"+
    			"<button id=\"validar_po\" type=\"button\" class=\"btn btn-danger\">Cancelar PO</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"carrega_PO(1)\">Atualizar</button>"+
    		    "</div>" + data);
		$('#tabela_de_po_subcon').bootstrapTable();
		
	    
}
}

  </script>


 <script>
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

  </script>
  <script>
function carrega_tabela_campos_rollout(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"13"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess13
		});
	
	function onSuccess13(data)
	{
		$("#div_tabela_campos_rollout").html("<div id=\"toolbar_campos_rollout\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#Modal_campos_rollout\">Novo</button>"+
    			"<button id=\"edita_campo\" type=\"button\" class=\"btn btn-info\">Editar</button>"+
    			"<button id=\"desabilita_campo\" type=\"button\" class=\"btn btn-danger\">Desabilitar Campo</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"carrega_tabela_campos_rollout()\">Atualizar</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"ordem_tabela()\">Salvar</button>"+
    		    "</div>" + data);
		
		$('#tabela_campos_rollout').bootstrapTable();
		var $table = $('#tabela_campos_rollout'),
		$button = $('#desabilita_campo');
		$(function () {
	        $button.click(function () {
	        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
	                return row.campo_id;
	            });
	            $table.bootstrapTable('remove', {
	                field: 'campo_id',
	                values: ids
	            });
	            //alert(ids);
	            deleta_campos_rollout(String(ids));
	        });
	    });
		$button2 = $('#edita_campo');
		$(function () {
	        $button2.click(function () {
	        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
	                return row.campo_id;
	            });
	            edita_campos_rollout(String(ids));
	        });
	    });
}
}

function stateFormatter(value, row, index) {
    if (row.campo2 === "Site ID") {
    	return {
            disabled: true
        };
    }
    if (row.campo2 === "PO") {
        return {
            disabled: true
        };
    }
    if (row.campo2 === "Projeto") {
        return {
            disabled: true
        };
    }
    return value;
}

function edita_campos_rollout(campos){
	$.ajax({
		  type: "POST",
		  data: {"opt":"26",
			     "campos":campos},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess26
		});
	function onSuccess26(data)
	{
		//info=[];
		//alert(data);
		info=JSON.parse(data);
		//alert(info['nome_campo']);
		
		var e,tipoc,tipoa,nome,desc,lista;
		
		document.getElementById("tipo_campo_rollout").value=info['tipo_Campo'];
		
			mostra_div(info['tipo_Campo']);
		
		//tipoc=e.options[e.selectedIndex].text;
		document.getElementById("lista_atributo").value =info['atributo'];
		document.getElementById("nome_campo_rollout").value = info['nome_campo'];
		document.getElementById("desc_campo_rollout").value = info['nome_campo'];
		document.getElementById("tipo_atributo_rollout").value=info['sub_tipo'];
		document.getElementById("trigger_pagamento").checked=info['trigger'];
		document.getElementById('percent_pagamento').value=info['percent']
			mostra_div_lista(info['sub_tipo']);
		
		$('#Modal_campos_rollout').modal('show');
		
	}

}
function ordem_tabela(){
	data_ordem=$('#tabela_campos_rollout').bootstrapTable('getData');
	var ordem_campo_padrao = '{"tamanho":0,"ordem":[]}';
	var ordem_campo = JSON.parse(ordem_campo_padrao);
	i=0;
	//alert(JSON.stringify(data_ordem));
	
	ordem_campo.tamanho=data_ordem.length;
	while(i<data_ordem.length){
		localizacao={campoid:data_ordem[i]['campo_id'],posicao:i+1};
		ordem_campo.ordem.push(localizacao);
		i++;
	}
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"15","ordem":JSON.stringify(ordem_campo)},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess15
		});
	function onSuccess15(data)
	{
		alert(data);
		carrega_gant();
	}
	
	
	
	
	//alert(JSON.stringify(ordem_campo));
    
}
  </script>
  <script>
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
function carrega_ajuda(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"27"},		  
		  	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess27
		});
	
	function onSuccess27(data)
	{
		$("#videos_ajuda").html(data);
		
		
	    
}
}
  </script>

  <script>
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

  </script>
  
  <script>
  
  function sync_rollout(){
		
		$.ajax({
			  type: "POST",
			  data: {"opt":"28"},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./POControl_Servlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess28
			});
		function onSuccess28(data)
		{
			alert(data);
		    carrega_gant();
		}

	}  
  
  
  
function carrega_usuarios(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"18"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess18
		});
	function onSuccess18(data)
	{
		$("#div_tabela_usuario").html("<div id=\"toolbar_tabela_usuario\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#modal_user_add\">Novo Usuário</button>"+
    			"<button id=\"validar_cliente\" type=\"button\" class=\"btn btn-danger\">Desabilitar Usuário</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"carrega_cliente_table()\">Grupos</button>"+
    		    "</div>" + data);
		$('#tabela_usuario').bootstrapTable();
	    
	}

}



  </script>
  
  
  <script>
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
function deleta_campos_rollout(campos){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"17",
			     "campos":campos},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess17
		});
	function onSuccess17(data)
	{
		
		alert(data);
		carrega_gant();
	}

}

  </script>
  
  <script>
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

  </script>
  <script>
  function add_item_po_subcon(){
    var $table = $('#table_po_item_subcon');
    var randomId = 100 + ~~(Math.random() * 100);
     $table.bootstrapTable('insertRow', {
                index: 1,
                row: {
                    id: randomId,
                    name: 'Item ' + randomId,
                    price: '$' + randomId
                }
            });
  }
  
</script>

<script>
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
</script>

<script>
function add_campo_rollout(){
	//alert("adicionando campos");
	var e,tipoc,tipoa,nome,desc,lista,chk,percent;
	
	e= document.getElementById("tipo_campo_rollout");
	tipoc=e.options[e.selectedIndex].text;
	lista=document.getElementById("lista_atributo").value;
	nome= document.getElementById("nome_campo_rollout").value;
	desc= document.getElementById("desc_campo_rollout").value;
	chk= document.getElementById("trigger_pagamento").checked;
	percent=document.getElementById('percent_pagamento').value
	if(tipoc=="Atributo"){
		e= document.getElementById("tipo_atributo_rollout");
		tipoa=e.options[e.selectedIndex].text;
	}
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"14",
			"nome":nome,  
			"tipo":tipoc, 
			"desc":desc,
			"tipoatrb":tipoa,
			"lista":lista,
			"trigger":chk,
			"percent":percent
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess14
		});
	
	function onSuccess14(data)
	{
		
	alert(data);
	carrega_gant();
	}
	}
</script>
<script>
function carrega_filtros_relatorios(){
	source= ["Relatório Diário",
    "Relatório Semanal",
    "Relatório Mensal",
    "Relatório Customizado"];
    
	$("#jqxWidget_select_report_type").jqxComboBox({ source: source, width: 300, height: 25 });
	$("#jqxWidget_select_report_type").on('select', function (event) {
		
         if (event.args) {
             var item = event.args.item;
             
             if (item) {
            	 
                 $("#relatorio_titulo").html("<label>"+item.value+"</label>");
                
             }
         }
     });
 
}
</script>
<script>
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
	alert("Código interno do Cliente: " + data);
	}
	}
</script>
<script>
function add_user(){
	
	var nome_usuario,usuario,email,matr,perfil;
	//alert("Adicionando usuario");
	
	nome_usuario= document.getElementById("usuario_nome_completo").value;
	
	usuario= document.getElementById("usuario_usuario").value;
	email= document.getElementById("usuario_email").value;
	matr= document.getElementById("usuario_mtr").value;
	perfil=String($('#usuario_perfil').multiselect().val());
	
	//alert(perfil);
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"19",
			"nome":nome_usuario,  
			"usuario":usuario, 
			"email":email, 
			"matricula":matr, 
			"perfil":perfil
			
		  },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess19
		});
	
	function onSuccess19(data)
	{
		
		alert(data);
	}
	}
</script>

<script type="text/javascript">
 function trocadeSenha(){
	 var atual = $("#senha_atual").jqxPasswordInput('val');
     var nova = $("#nova_senha").jqxPasswordInput('val');
     var confirma = $("#confirma_senha").jqxPasswordInput('val');
    
     if(atual==""){
    	 alert("Senha Atual é Obrigatório!");
    	 return;
     }else if(nova!=confirma){
    	 alert("Confirmação da Senha não confere com nova Senha!");
    	 return;
     }
     
     $.ajax({
		  type: "POST",
		  data: {"opt":"20",
			"atual":atual,  
			"nova":nova, 
			"confirma":confirma
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess20
		});
	
	function onSuccess20(data)
	{
		
		alert(data);
		$("#senha_atual").jqxPasswordInput('val','');
		$("#nova_senha").jqxPasswordInput('val','');
		$("#confirma_senha").jqxPasswordInput('val','');
	}
     
 }
 function carrega_portal(){
	 var cont=0;
	 var cont_aux=1;
	 $.ajax({
		  type: "POST",
		  data: {"opt":"21"
			
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess21
		});
	 function onSuccess21(data){
		 
		 
		 $('#docking').jqxDocking({  orientation: 'horizontal', width: 1400, mode: 'docked' });
		 $('#docking').jqxDocking('importLayout', data);
		 g1(0,'grafico_container1');
		 g2(0,'grafico_container2');
		 g3(0,'grafico_container3');
		 g4(0,'grafico_container4');
		 $('#docking').on('dragEnd', function (event) {
			 $.ajax({
				  type: "POST",
				  data: {"opt":"22",
					"portal":$('#docking').jqxDocking('exportLayout'),  
					
					},		  
				  //url: "http://localhost:8080/DashTM/D_Servlet",	  
				  url: "./POControl_Servlet",
				  cache: false,
				  dataType: "text"
				  
				});
		 
             
         });
	 }
 }
 function carrega_gant(){
	 
	 var me,container,save_button,addButton,cFilterButton,deleteButton = "";
	 
	$.getJSON('./POControl_Servlet?opt=12', function(data) {
			
		//alert(data['people']);
		
		var source =
        {
            datatype: "json",
            datafields: data['campos'],
           	id: 'recid',
            localdata: data['records']
        };
		var dataAdapter = new $.jqx.dataAdapter(source);
		
		$("#jqxgrid").jqxGrid(
	            {
	                width: '100%',
	                source: dataAdapter,
	                columnsresize: true,
	                pageable: true,
	                editable: true,
	                showfilterrow: true,
	                showtoolbar: true,
	                filterable: true,
	                autoshowfiltericon: true,
	                rendertoolbar: function (toolbar) {
	                    var me = this;
	                    toolbar.empty();
	                    container = $("<div style='overflow: hidden; position: relative; margin: 1px;'></div>");
	                    save_button = $("<div style='float: left; margin-left: 5px'>Salvar</div>");
	                    addButton = $("<div style='float: left; margin-left: 5px;'>Nova Atividade</div>");
	                    cFilterButton = $("<div style='float: left; margin-left: 5px;'>Remover Filtros</div>");
	                    deleteButton = $("<div style='float: left; margin-left: 5px;'>Remover Atividade</div>");
	                    container.append(save_button);
	                    container.append(addButton);
	                    container.append(cFilterButton);
	                    container.append(deleteButton);
	                    toolbar.append(container);
	                    save_button.jqxButton({ template: "success"});
	                    addButton.jqxButton({template: "info"});
	                    cFilterButton.jqxButton({template: "warning"});
	                    deleteButton.jqxButton({template: "danger"});
	                    cFilterButton.click(function (event) {
	                    	$("#jqxgrid").jqxGrid('clearfilters');
	                    });
	                    save_button.click(function (event) {
	                    	registra_mudancas_bd(g_changes);
	                    });
	                    deleteButton.click(function (event) {
	                    	rm_row_rollout();
	                    });
	                    addButton.click(function (event) {
	                    	 $.ajax({
	                   		  type: "POST",
	                   		  data: {"opt":"23"
	                   			
	                   			},		  
	                   		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
	                   		  url: "./POControl_Servlet",
	                   		  cache: false,
	                   		  dataType: "text",
	                   		  success: onSuccess23
	                   		});
	                   	 function onSuccess23(data){
	                   		 var aux=new Array();
	                   		$("#row_add_fields").html(data);
	                   		$("#table_row_fields").bootstrapTable();
	                   		$('input[type=data_row]').w2field('date', { format: 'd/m/yyyy'});
	                   		$(".row_rollout").each(function(){
	                   			if($(this).attr('type')=="lista"){
	                   				//alert($(this).attr('valores'));
	                   				aux=$(this).attr('valores').split(",");
	                   				$("#"+$(this).attr('name')).jqxComboBox({ source: aux, width: '200px', height: '25px'});
	                   			}
	                   			
	                   		});
	                   		$('#modal_row_add').modal('show');
	                   	 }
	                   	
	                        
	                    });
	                    },
	                selectionmode: 'multiplerows',
	                editmode: 'click',
	                columns: data['campos2'],
	                columngroups: data['columgroup']
	            });
		
		  	
		
	});
	 //$("#jqxgrid").jqxGrid('selectionmode', 'multiplerows');
	 $("#jqxgrid").on('cellendedit', function (event) {
         var args = event.args;
         //console.log(args);
         //alert(args.datafield);
         //alert(args.row.recid);
         //alert(args.row);
         registra_mudança_campos(args.rowindex,args.datafield,args.row.recid,args.value,args.columntype);
     });
	}
		
function registra_mudança_campos(index,campo,linha,valor,tipo){
	//alert(tipo);
	if(campo=="check_linha"){
		$("#jqxgrid").jqxGrid('selectrow', index);
		aux=deletar.indexOf(linha);
		//alert("rowid: "+$("#jqxgrid").jqxGrid('getrowid', index));
		//alert("index: "+ index);
		//alert("recid: "+ linha);
		if(aux>-1){
			deletar.splice(aux,1);
			deletar_grid.splice($("#jqxgrid").jqxGrid('getrowid', index),1);
		}else{
			deletar.push(linha);
			deletar_grid.push($("#jqxgrid").jqxGrid('getrowid', index));
		}
		//console.log(deletar);
	}else{
	if (tipo=='datetimeinput'){
		var localdata=moment(valor).format('L');
		localizacao={recid:linha,colum:campo,value:localdata,tipoc:tipo}
	}else{
		localizacao={recid:linha,colum:campo,value:valor,tipoc:tipo}
	}
	g_changes.atualizacoes=g_changes.atualizacoes+1;
	g_changes.campos.push(localizacao);
	}
	//var valor_novo=w2ui['grid'].getCellValue(record,field_change);
	//alert(valor_novo);
}
function rm_row_rollout(){
	if(confirm("ATENÇÃO! Deseja remover as linhas selecionadas do rollout?")){
	if(deletar.length>0){	
		$.ajax({
	 		  type: "POST",
	 		  data: {"opt":"25",
	 			"linha":deletar.toString()
	 			},		  
	 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
	 		  url: "./POControl_Servlet",
	 		  cache: false,
	 		  dataType: "text",
	 		  success: onSuccess25
	 		});
	 	 function onSuccess25(data){
	 		 //alert(deletar.toString());
	 		 //alert(deletar_grid.toString());
	 		 $("#jqxgrid").jqxGrid('deleterow', deletar);
	 		$('#jqxgrid').jqxGrid('refreshdata');
	 		 
	 		  deletar=[];
	 		 deletar_grid=[];
	 		 carrega_gant();
	 		 
	 	 }
	}
	}
}
function add_new_row_rollout(){
	var map = {};
	//var datainformation = $('#jqxgrid').jqxGrid('getdatainformation');
	//var rowscount = datainformation.rowscount;
	//if(rowscount > 0){
	 //id = $('#jqxgrid').jqxGrid('getrowid', rowscount-1);
	 //alert("esse é o id:"+id);
	 //alert("esse é o rowscount:"+rowscount);
	 //alert("esse é o jqxgrid funcao com menos 1:"+$('#jqxgrid').jqxGrid('getrowid', rowscount-1));
	 //alert("esse é o jqxgrid funcao sem menos 1:"+$('#jqxgrid').jqxGrid('getrowid', rowscount));
	//}else{id= 0 ;}
	//alert(rowscount+" - "+id);
	$(".row_rollout").each(function(){
		if($(this).attr('name').indexOf("_inicio")>0){
			//alert("sdate_pre_" + $(this).attr('name').replace("_inicio",""));
			map["sdate_pre_" + $(this).attr('name').replace("_inicio","")] = $(this).val();
		}else if($(this).attr('name').indexOf("_fim")>0){
			map["edate_pre_" + $(this).attr('name').replace("_fim","")] = $(this).val();
		}else{
			map[$(this).attr('name')] = $(this).val();
		}
		linha={nome_campo:$(this).attr('name'),valor_campo:$(this).val(),tipo_campo:$(this).attr('tipo_campo')}
		row.rows.push(linha)
		$(this).val('');
	});
	
	//linha={nome_campo:"recid",valor_campo:parseInt(id)+1,tipo_campo:"identificador"};
	//row.rows.push(linha)
	$.ajax({
 		  type: "POST",
 		  data: {"opt":"24",
 			"linha":JSON.stringify(row)
 			},		  
 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
 		  url: "./POControl_Servlet",
 		  cache: false,
 		  dataType: "text",
 		  success: onSuccess24
 		});
 	 function onSuccess24(data){
 		map['recid']=data;
	$("#jqxgrid").jqxGrid('addrow', null,map);
	//alert(row['rows'][1]['nome_campo']);
 	 }
}
function registra_mudancas_bd(changes){
	//console.log("posicao 2:"+changes[1]);
	//changes.colunas=w2ui['grid'].columns;
	//alert("posicao 3:"+JSON.stringify(changes));
	if(g_changes.atualizacoes>0){
	$.ajax({
		  type: "POST",
		  data: {"opt":"16",
			"mudancas":JSON.stringify(changes) 
			},		  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess16
		});
	
	function onSuccess16(data)
	{
		
	alert(data);
	g_changes.atualizacoes=0;
	g_changes.campos=[];
	carrega_portal();
	}
	}else{
		alert("Sem Mudanças para registrar!");
	}
}

    </script>

	<title>G I S - Gestão Integrada de Serviços</title>

<script>
$(document).ready(function() {
	  $("[data-toggle]").click(function() {
	    var toggle_el = $(this).data("toggle");
	    $(toggle_el).toggleClass("open-sidebar");
	  });
	});
</script>
<script>
function sair(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"99"
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  
		});
}
</script>
</head>
<body>
	<div id="header" style="width: 100%; overflow: hidden;">
	<div id='jqxMenu' style='visibility: hidden;'> <div style="width: 80%; float: left;"><h3>GIS - Ambiente de Demonstração</h3></div>
        <ul>
            <li><a href="#Home">Home</a></li>
            <li><a href="#Solutions">Solutions</a>
                <ul style='width: 250px;'>
                    <li><a href="#Education">Education</a></li>
                    <li><a href="#Financial">Financial services</a></li>
                    <li><a href="#Government">Government</a></li>
                    <li><a href="#Manufacturing">Manufacturing</a></li>
                    <li type='separator'></li>
                    <li>Software Solutions
                                <ul style='width: 220px;'>
                                    <li><a href="#ConsumerPhoto">Consumer photo and video</a></li>
                                    <li><a href="#Mobile">Mobile</a></li>
                                    <li><a href="#RIA">Rich Internet applications</a></li>
                                    <li><a href="#TechnicalCommunication">Technical communication</a></li>
                                    <li><a href="#Training">Training and eLearning</a></li>
                                    <li><a href="#WebConferencing">Web conferencing</a></li>
                                </ul>
                    </li>
                    <li><a href="#">All industries and solutions</a></li>
                </ul>
            </li>
            <li><a href="#Products">Products</a>
                <ul>
                    <li><a href="#PCProducts">PC products</a></li>
                    <li><a href="#MobileProducts">Mobile products</a></li>
                    <li><a href="#AllProducts">All products</a></li>
                </ul>
            </li>     
        </ul>
    </div>
     
     <div style="width: 20%;margin-left: auto;text-align: right;vertical-align: middle;height: 60px;display: table;"><a href="lg.html" class="btn btn-danger" onclick="sair()">Sair</a></div>
    </div>
<div class='tabs-x tabs-above tabs-krajee'>
    <ul id="myTab-1" class="nav nav-tabs" role="tablist">
    <li class="active"><a href="#portal" role="tab" data-toggle="tab">Portal</a></li>
    <li class="dropdown">
            <a href="#" id="myTabDrop-1" class="dropdown-toggle" data-toggle="dropdown">Gerenciamento de PO <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop-1">
                <li><a href="#dropdown-1-1" tabindex="-1" role="tab" data-toggle="tab">Tabela de PO</a></li>
                <li><a href="#dropdown-1-2" tabindex="-1" role="tab" data-toggle="tab">PO-Item</a></li>
                <li><a href="#dropdown-1-3" tabindex="-1" role="tab" data-toggle="tab">Emitir PO</a></li>
            </ul>
        </li>
       <li class="dropdown">
            <a href="#" id="myTabDrop-1" class="dropdown-toggle" data-toggle="dropdown">Gerenciamento de Projeto <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop-1">
                
                <li><a href="#dropdown-2-1" tabindex="-1" role="tab" data-toggle="tab">Projetos</a></li>
                <li><a href="#dropdown-2-2" tabindex="-1" role="tab" data-toggle="tab">Rollout</a></li>
            </ul>
        </li>
        <li class="dropdown"><a href="#" id="myTabDrop-5" class="dropdown-toggle"  data-toggle="dropdown">Material <span class="caret"></span></a>
        	<ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop-2">
        		<li><a href="#dropdown-5-1" tabindex="-1" role="tab" data-toggle="tab">Registro de Material</a></li>
                <li><a href="#dropdown-5-2" tabindex="-1" role="tab" data-toggle="tab">Inventário Geral</a></li>
                <li><a href="#dropdown-5-3" tabindex="-1" role="tab" data-toggle="tab">Relatório de Entrada</a></li>
                <li><a href="#dropdown-5-4" tabindex="-1" role="tab" data-toggle="tab">Relatório de Saída</a></li>
             </ul>
        </li>
        <li class="dropdown"><a href="#" id="myTabDrop-6" class="dropdown-toggle"  data-toggle="dropdown">Relatórios <span class="caret"></span></a>
        	<ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop-2">
        		<li><a href="#dropdown-6-1" tabindex="-1" role="tab" data-toggle="tab">Relatórios de Entrega</a></li>
                <li><a href="#dropdown-6-2" tabindex="-1" role="tab" data-toggle="tab">Relatórios de SLA</a></li>
                <li><a href="#dropdown-6-3" tabindex="-1" role="tab" data-toggle="tab">Relatórios de Recursos</a></li>
                <li><a href="#dropdown-6-4" tabindex="-1" role="tab" data-toggle="tab">Master Plan</a></li>
                <li><a href="#dropdown-6-5" tabindex="-1" role="tab" data-toggle="tab">Ranking</a></li>
             </ul>
        </li>
        <li class="dropdown"><a href="#" id="myTabDrop-2" class="dropdown-toggle"  data-toggle="dropdown">Financeiro <span class="caret"></span></a>
        	<ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop-2">
        		<li><a href="#dropdown-3-1" tabindex="-1" role="tab" data-toggle="tab">Registro de Clientes</a></li>
        		<li><a href="#dropdown-3-2" tabindex="-1" role="tab" data-toggle="tab">Relatório de Faturamento</a></li>
             </ul>
        </li>
        
        <li class="dropdown"><a href="#" class="dropdown-toggle" id="myTabDrop-3" data-toggle="dropdown"><i class="glyphicon glyphicon-knight"></i> Configurações<span class="caret"></span></a>
        
        <ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop-3">
        		<li><a href="#dropdown-4-1" tabindex="-1" role="tab" data-toggle="tab">Rollout</a></li>
                <li><a href="#dropdown-4-2" tabindex="-1" role="tab" data-toggle="tab">Usuários</a></li>
                <li><a href="#dropdown-4-3" tabindex="-1" role="tab" data-toggle="tab">Minha Senha</a></li>
                <li><a href="#dropdown-4-4" tabindex="-1" role="tab" data-toggle="tab">Portal</a></li>
                <li><a href="#dropdown-4-5" tabindex="-1" role="tab" data-toggle="tab">Ajuda</a></li>
             </ul>
        </li>
    </ul>
    <div id="myTabContent-1" class="tab-content" style="margin: auto;">
        <div class="tab-pane fade in active" id="portal">
        <div id='jqxWidget' style="margin: auto;">
        <div id="docking" style="margin: auto;">
            <div style="overflow: hidden;">
                <div id="window0" style="height: 300px">
                    <div> <div class="w2ui-field">
    					<label>Periodo:</label>
    					<div> <input type="us-date1" id="from"> - <input type="us-date2" id="to"> <button class="btn" name="ok" onclick="g1(1,'grafico_container1')">OK</button></div>
					</div></div>
                   
                    <div id='grafico_container1'>Content 1</div>
                </div>
                <div id="window1" style="height: 300px">
                    <div></div>
                    <div id='grafico_container2'>Content 2</div>
                </div>
            </div>
            <div style="overflow: hidden;">
                <div id="window2" style="height: 300px; ">
                    <div></div>
                    <div id='grafico_container3'>Content 3</div>
                </div>
                <div id="window3" style="height: 300px; ">
                    <div></div>
                    <div id='grafico_container4'>Content 3</div>
                </div>
            </div>
        </div>
        
    </div>
						
			<div id="graficos2" class="tudo">
				    
				</div> 	
			
       
        
        
        </div>
        <div class="tab-pane fade" id="home-1"><p>...</p></div>
        <div class="tab-pane fade" id="profile-1"><p>...</p></div>
        <div class="tab-pane fade" id="dropdown-1-1">
            <br>
            
        	
        	<div id="div_tabela_po"><button class="btn btn-lg btn-warning"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> Carregando...</button></div>
        	
        </div>
        <div class="tab-pane fade" id="dropdown-1-2"><br><div id="div_tabela_item_po"></div></div>
        <div class="tab-pane fade" id="dropdown-1-3">


            <br>
            
        	
        	<div id="div_emitir_tabela_po"><button class="btn btn-lg btn-warning"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> Carregando...</button></div>
        	
		
		
		</div>
		
        <div class="tab-pane fade" id="dropdown-2-1">
            <div id="div_tabela_projetos"></div>
        </div>
        <div class="tab-pane fade" id="dropdown-2-2">
         	<div class="container">
				<div id="sidebar">
				       <ul>
				            <li><a href="#">Home</a></li>
				            <li><a href="#">Explore</a></li>
				            <li><a href="#">Users</a></li>
				            <li><a href="#">Sign Out</a></li>
				        </ul>
    			</div>
				<div class="main-content">
					<div class="swipe-area"></div>
					<a href="#" data-toggle=".container" id="sidebar-toggle">
			            <span class="bar"></span>
			            <span class="bar"></span>
			            <span class="bar"></span>
        			</a>
        			<div class="content">
        			<div id='jqxExpander'>
        			 	<div>MiniDashboard em Desenvolvimento</div>
        			 	<div>
						
							<div class="row">
						    	<div class="col-sm-6 col-md-3">
							    	<div class="panel panel-light panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/project.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Total de Atividades</small>
									    				<h4>0</h4>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		<small class="stat-label">0% de itens executados</small>
									    		<h4>0% de itens atrasados</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
			    				<div class="col-sm-6 col-md-3">
							    	<div class="panel panel-primary panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/gnucash.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Quantidade de PO</small>
									    				<h1>0</h1>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		<small class="stat-label">0% de itens executados</small>
									    		<h4>0% de itens atrasados</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
				     			<div class="col-sm-6 col-md-3">
							    	<div class="panel panel-success panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/list1.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Itens de PO</small>
									    				<h1>0</h1>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		<small class="stat-label">0% de itens executados</small>
									    		<h4>0% de itens atrasados</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
							    <div class="col-sm-6 col-md-3">
							    	<div class="panel panel-danger panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/network.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Total de Sites</small>
									    				<h1>0</h1>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		<small class="stat-label">0% de itens executados</small>
									    		<h4>0% de itens atrasados</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
							    <div class="col-sm-6 col-md-3">
							    	<div class="panel panel-dark panel-stat">
								    	<div class="panel-heading">
									    	<div  class="stat">
									    		<div class="row">
									    			<div class="col-xs-4">
									    				<img src="img/user.png">
									    			</div>
									    			<div  class="col-xs-8">
									    				<small class="stat-label">Total de Recursos</small>
									    				<h1>22</h1>
									    			</div>
									    		</div>
									    		<div style="margin-bottom: 15px;"></div>
									    		<small class="stat-label">50% de itens executados</small>
									    		<h4>40% de itens atrasados</h4>
									    	</div>
								    	</div>
							    	</div>
							    </div>
						   </div>
						
						</div>
					</div>
					</br>
					<hr>
							<div class="row" style="width: 100%">
					 			
					 			<div id='jqxWidget' style="padding-left:20px; ;font-size: 13px; font-family: Verdana; float: left;width: 100%">
        							<div id="jqxgrid"></div>
    							</div>
					 		</div>
					 	</div>
				</div>
			</div>
	    </div>
		<div class="tab-pane fade" id="dropdown-5-1">Material 1</div>
		<div class="tab-pane fade" id="dropdown-5-2">Material 2</div>
		<div class="tab-pane fade" id="dropdown-5-3">Material 3</div>
		<div class="tab-pane fade" id="dropdown-5-4">Material 4</div>
		
		<div class="tab-pane fade" id="dropdown-6-1">
			<div style="display:table;margin: 0 auto;">
				<div id="relatorio_titulo" style="font-size: xx-large;height:60px;margin: auto;"></div>
				<div id="filtros_relatorio">
				
	            <div style='float: left;' id='jqxWidget_select_report_type'></div>
				<div style='float: left;' id='jqxWidget_select_report_milestone'></div>
				<div style='float: left;' id='jqxWidget_select_report_range'></div>
				</div>
				<div id="grafico_relatorio"></div>
				<div id="tabela_relatorio"></div>
			</div>
		</div>
		<div class="tab-pane fade" id="dropdown-6-2">relatorio 2</div>
		<div class="tab-pane fade" id="dropdown-6-3">relatorio 3</div>
		<div class="tab-pane fade" id="dropdown-6-4">relatorio 4</div>
		<div class="tab-pane fade" id="dropdown-6-5">relatorio 5</div>
		<div class="tab-pane fade" id="dropdown-3-1">
			<div id="div_tabela_clientes"></div>
		</div>
		<div class="tab-pane fade" id="dropdown-3-2">
			<div id="div_filtros_faturamento" class="form-group">
			<table width=100%>
				<tr>
					<td style="padding: 10px"><select class="form-control" id="select_conta_faturamento" onchange="carrega_select_projeto_faturamento(this)"><option>Selecione uma conta</option><option>Conta</option></select></td>
					<td style="padding: 10px"><select class="form-control" id="select_projeto_faturamento"><option value='all'>Selecione um Projeto</option></select></td>
					<td style="padding: 10px"><button class="btn btn-info" onclick="carrega_tabela_faturamento()">Pesquisar</button></td>
				</tr>
			</table>
			</div>
			<hr>
			<div id="div_tabela_faturamento"></div>
		
		</div>
        <div class="tab-pane fade" id="dropdown-4-1">
			<div id="div_tabela_campos_rollout"></div>
		</div>
        <div class="tab-pane fade" id="dropdown-4-2">
        	<div id="div_tabela_usuario"></div>
        </div>
        <div class="tab-pane fade" id="dropdown-4-3">
        	<div class="form-group">
			      <table width=100%>
			      		<tr><td style="padding: 10px"><label class="control-label">Senha Atual:</label></td></tr>
				        <tr><td style="padding: 10px"><input type="password" class="form-control" id="senha_atual"></td></tr>
				        <tr><td style="padding: 10px"><label class="control-label">Nova Senha:</label></td></tr>
				        <tr><td style="padding: 10px"><input type="password" class="form-control" id="nova_senha"></td></tr>
						<tr><td style="padding: 10px"><label class="control-label">Repita a nova Senha:</label></td></tr>
				        <tr><td style="padding: 10px"><input type="password" class="form-control" id="confirma_senha"></td></tr>    
				        <tr><td style="padding: 10px"><label class="control-label"></label></td></tr>  
				        <tr><td style="padding: 10px"><button id="troca_senha_btn" type="button" class="btn btn-info" onclick="trocadeSenha()">Trocar Senha</button></td></tr>	
			      	</table>
			
      </div>
        </div>
        <div class="tab-pane fade" id="dropdown-4-4">
        	seleciona os gráficos aqui
        </div>
        <div class="tab-pane fade" id="dropdown-4-5">
        	<div id="videos_ajuda" style="display:table;margin: 0 auto;">
        		
        	</div>
        </div>
    </div>
</div>

<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Carregar PO</h4>
      </div>
      <div class="modal-body">
      <table >
      		<tr>
      		<td style="padding: 10px"><select class="form-control" required id="cliente_carrega_po" onchange="carrega_select_projeto(this)"><option>Cliente...</option></select></td>
	        <td style="padding: 10px"><select class="form-control" required id="projeto_carrega_po"><option>Projeto...</option></select></td>
	        </tr>
      	</table>
			<hr>
        <label class="control-label">Selecionar Arquivo</label>
		<input id="input_2" name="total_input[]" type="file"  multiple>	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>   

<div id="Modal_campos_rollout" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Adicionar Novo Campo ao Rollout</h4>
      </div>
      <div class="modal-body">
      <table width=100%>
      		<tr><td style="padding: 10px"><label class="control-label">Nome do Campo</label></td></tr>
	        <tr><td style="padding: 10px"><input type="text" class="form-control" id="nome_campo_rollout"></td></tr>
	        <tr><td style="padding: 10px"><label class="control-label">Tipo do Campo</label></td></tr>
	        <tr><td style="padding: 10px"><select class="form-control" id="tipo_campo_rollout" onchange="mostra_div(this.value)"><option>Milestone</option><option>Atributo</option></select><br><div id="chk_pgto" class="checkbox"><label class="control-label"><input type="checkbox" id="trigger_pagamento" onclick="habilita_campo(this)">Aciona Pagamento</label><br><label class="control-label">Percentual Pagamento</label><br><input type="text" class="form-control" id="percent_pagamento" disabled="disabled"></div><div id="div_campo_atributo" style="display:none"><select class="form-control" id="tipo_atributo_rollout" onchange="mostra_div_lista(this.value)"><option>-</option><option>Texto</option><option>Data</option><option>Numero</option><option>Lista</option></select></div><br><div id="div_campo_atributo_lista" style="display:none"><input type="text" class="form-control" id="lista_atributo">Coloque os valores separados por virgula(,).</div></td></tr>
			<tr><td style="padding: 10px"><label class="control-label">Descrição</label></td></tr>
	        <tr><td style="padding: 10px"><input type="text" class="form-control" id="desc_campo_rollout"></td></tr>      	
      	</table>
			<hr>
			<script>function habilita_campo(chk){if(chk.checked){document.getElementById('percent_pagamento').disabled=false;}else{document.getElementById('percent_pagamento').disabled=true;}} function mostra_div(texto){if(texto=="Atributo"){document.getElementById('div_campo_atributo').style.display = 'block';document.getElementById('chk_pgto').style.display = 'none';}else{document.getElementById('div_campo_atributo').style.display = 'none';document.getElementById('chk_pgto').style.display = 'block';document.getElementById('div_campo_atributo_lista').style.display = 'none';}}function mostra_div_lista(texto){if(texto=="Lista"){document.getElementById('div_campo_atributo_lista').style.display = 'block';}else{document.getElementById('div_campo_atributo_lista').style.display = 'none';}}</script>
        
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-default" data-dismiss="modal" onclick="add_campo_rollout()">Adicionar</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>  

<div id="modal_subcon_po" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Criar PO para Subcontratada</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      	<table >
      		<tr><td style="padding: 10px"><label class="control-label">Selecione a Empresa</label>
	        <select class="form-control"><option>Empresa1</option><option>Empresa2</option></select></td><td style="padding: 10px"><label class="control-label">Selecione o Modelo da PO</label>
	        <select class="form-control"><option>1 PO - 1 Site</option><option>1 PO - n Sites - Itens iguais</option><option>1 PO - n Sites - itens diferentes</option></select></td><td style="padding: 10px"><label class="control-label">Estado</label>
	        <select class="form-control"><option>RJ</option><option>SP</option></select></td></tr>
      	</table>
			
	        
	        <hr>
	        <label class="control-label">LPU</label>
	        <select class="form-control"><option>NomedaEmpresaEmissora_NomedaEmpresaRecebedora_XXXX_Versao1</option><option>NomedaEmpresaEmissora_NomedaEmpresaRecebedora_XXXX_Versao2</option></select>
			<br>
			<label class="control-label">Sites(Insira os sites separados por ';')</label>
			<input type="text" class="form-control" id="site_subcon_po">
			<br>
			<hr>
			<button id="add_item_po_subcon_btn" type="button" class="btn btn-info" onclick="add_item_po_subcon()">Adicionar Item</button>
    		<button id="rm_item_po_subcon_btn" type="button" class="btn btn-danger">Remover Item</button>
    		<table id="table_po_item_subcon"
                               data-toggle="table"
                               data-height="299"
                               >
                            <thead>
                            <tr>
                                <th data-field="id">ID</th>
                                <th data-field="name">Item Name</th>
                                <th data-field="price">Item Price</th>
                            </tr>
                            </thead>
                        </table>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>   


<div id="modal_fluxo_pagamento" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Fluxo de Pagamento</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      		<label id="itens_selecionados" class="control-label"></label>
      		<br>
      		<du>
      			<li>1. Clique em Adicionar quantas vezes for necessário, confome o numero de parcelas que deseja</li>
      			<li>2. Somente após adicionar a quantidade de parcelas insira as informações</li>
      			<li>3. Clique na célula Milestone e selecione a opção que libera pagamento</li>
      			<li>4. Clique na célula percentagem e informe o percentual de cada parcela</li>
      		</du>	
			<hr>
			<button id="add_fluxo_pg" type="button" class="btn btn-info" onclick="add_item_pgfluxo()">Adicionar Item</button>
    		<button id="rm_fluxo_pg" type="button" class="btn btn-danger" onclick="limpa_tabelafluxo_()">Remover Item</button>
    		<table id="table_fluxopg_item" data-toggle="table" data-height="299" data-use-row-attr-func=true  data-reorderable-rows=true>
                            <thead>
                            <tr>
                                <th data-field="id">ID</th>
                                <th data-field="milestone" data-editable="true">Milestone</th>
                                <th data-field="porcentagem" data-editable="true">Porcentagem</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
		</div>
		
      </div>
      <div class="modal-footer">
       <button type="button" class="btn btn-primary"  onclick="salvar_fluxo_pg()">Salvar Fluxo</button> <a href="#" class="btn btn-default" data-dismiss="modal">Fechar</a>
      </div>
    </div>
     
  </div>
</div>   


<div id="modal_project_add" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Adicionar Novo Projeto</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      	<table >
      		<tr><td style="padding: 10px"><label class="control-label">Selecione a Conta</label>
	        <select class="form-control" required id="projeto_nome_cliente"><option>Cliente1</option><option>Cliente2</option></select></td><td style="padding: 10px"><label class="control-label">Selecione o Produto</label>
	        <select class="form-control" id="projeto_produto"><option>Wireless</option><option>Transmissao</option><option>Microwave</option></select></td><td style="padding: 10px"><label class="control-label">Estado</label>
	        <select class="form-control" id="projeto_estado"><option>RJ</option><option>SP</option></select></td></tr>
      	</table>
			<hr>
	       <br>
			<table >
	      		<tr><td style="padding: 10px"><label class="control-label">Orçamento do Projeto</label></td>
		        <td style="padding: 10px"><label class="control-label">PM do projeto - Cliente</label></td>
		        <td style="padding: 10px"><label class="control-label">Email PM - Cliente</label></td></tr>
		        <tr><td style="padding: 10px"><input type="text" class="form-control" id="projeto_orcamento"></td>
		        <td style="padding: 10px"><input type="text" class="form-control" id="projeto_pm_cliente"></td>
		        <td style="padding: 10px"><input type="text" class="form-control" id="projeto_mail_cliente"></td></tr>
      		</table>
			<br>
			<hr>
			<br>
			<table >
	      		<tr><td style="padding: 10px"><label class="control-label">Nome do Projeto</label></td>
		        <td style="padding: 10px"><label class="control-label">Código do Projeto</label></td>
		        
		        <tr><td style="padding: 10px"><input type="text" class="form-control" id="projeto_nome"></td>
		        <td style="padding: 10px"><input type="text" class="form-control" id="projeto_codigo_cliente"></td>
		        </tr>
      		</table>
			<br>
			<hr>
			<table ><tr><td style="padding: 10px"><label class="control-label">Inicio do Projeto</label></td><td style="padding: 10px"><label class="control-label">Encerramento Previsto</label></td></tr>
			<tr><td style="padding: 10px">
							<div class='input-group date' id='datetimepicker6'>
								                    <input type='text' class="form-control" id="projeto_inicio_dt"/>
								                    
								                    <span class="input-group-addon">
								                        <span class="glyphicon glyphicon-calendar"></span>
								                    </span>
						    </div>
						 </td>
						 <td style="padding: 10px">
								            <div class='input-group date' id='datetimepicker7'>
                <input type='text' class="form-control" id="projeto_fim_dt"/>
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div></td></tr></table>
								        
								        <script type="text/javascript">
								            $(function () {
								                $('#datetimepicker6').datetimepicker({useCurrent: true});
								                $('#datetimepicker7').datetimepicker({
								                    useCurrent: false, //Important! See issue #1075
								                    //timePicker:false,
								                    calendarWeeks:true,
								                    showTodayButton:true
								                    //disabledHours:true
								                });
								                $("#datetimepicker6").on("dp.change", function (e) {
								                    $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
								                });
								                $("#datetimepicker7").on("dp.change", function (e) {
								                    $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
								                });
								            });
								        </script>
				   
   			 
			<br>
			<hr>
		</div>
      </div>
      <div class="modal-footer">
        <button id="add_project_btn" type="button" class="btn btn-info" onclick="add_project()">Adicionar Projeto</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>   

<div id="modal_user_add" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Adicionar Novo Usuário</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      	<table >
      		<tr>
      			<td style="padding: 5px"><label class="control-label">Nome Completo</label></td>
      			<td style="padding: 5px"><label class="control-label">Email</label></td>
      			<td style="padding: 5px"><label class="control-label">Perfil</label></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><input type="text" class="form-control" id="usuario_nome_completo"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="usuario_email"></td>
      			<td style="padding: 5px"><select id="usuario_perfil" multiple="multiple"><option value="Administrador">Administrador</option><option value="Financeiro">Financeiro</option><option value="tecnico">Técnico</option></select></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><label class="control-label">Usuario</label></td>
      			<td style="padding: 5px"><label class="control-label">Matricula</label></td>
      			<td style="padding: 5px"></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><input type="text" class="form-control" maxlength='30' id="usuario_usuario"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="usuario_mtr"></td>
      			<td style="padding: 5px"></td>
      		</tr>
      	</table>
			<hr>
	      
		</div>
      </div>
      <div class="modal-footer">
        <button id="add_project_btn" type="button" class="btn btn-info" onclick="add_user()">Adicionar Usuário</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>

**
<div id="modal_row_add" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Adicionar Nova Atividade</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group" id="row_add_fields" style="height: 400px;overflow: auto;">
      	
	    </div>
      </div>
      <div class="modal-footer">
        <button id="add_project_btn" type="button" class="btn btn-info" onclick="add_new_row_rollout()">Salvar e Adicionar outra</button><button id="add_project_btn" type="button" class="btn btn-info" onclick="add_new_row_rollout()">Salvar Atividade e Fechar</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>	

**
	
<div id="modal_customer_add" class="modal fade subconpo" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Adicionar Novo Cliente</h4>
      </div>
      <div class="modal-body">
      	<div class="form-group">
      	<table >
      		<tr>
      			<td style="padding: 5px"><label class="control-label">Nome Empresa Completo *</label></td>
      			<td style="padding: 5px"><label class="control-label">Nome Empresa Resumido *</label></td>
      			<td style="padding: 5px"><label class="control-label">CNPJ Empresa *</label></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><input type="text" class="form-control" id="cliente_nome_completo"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="cliente_nome_resumido"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="cliente_cnpj"></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><label class="control-label">Incrição Estadual</label></td>
      			<td style="padding: 5px"><label class="control-label">Endereço</label></td>
      			<td style="padding: 5px"><label class="control-label">Telefone</label></td>
      		</tr>
      		<tr>
      			<td style="padding: 5px"><input type="text" class="form-control" id="cliente_ie"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="cliente_endereco"></td>
      			<td style="padding: 5px"><input type="text" class="form-control" id="cliente_telefone"></td>
      		</tr>
      	</table>
			<hr>
	      
		</div>
      </div>
      <div class="modal-footer">
        <button id="add_project_btn" type="button" class="btn btn-info" onclick="add_customer()">Adicionar Cliente</button><button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>

  </div>
</div>	
	<div id="footer">
    Copyright Â© Inovare TI 
    </div>
 <!-- Resource jQuery -->
</body>
</html>