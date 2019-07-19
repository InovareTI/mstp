function ordem_tabelaPermits(){
	data_ordem=$('#tabela_campos_permits').bootstrapTable('getData');
	var ordem_campo_padrao = '{"tamanho":0,"ordem":[]}';
	var ordem_campo = JSON.parse(ordem_campo_padrao);
	
	i=0;
	//alert(JSON.stringify(data_ordem));
	
	ordem_campo.tamanho=data_ordem.length;
	while(i<data_ordem.length){
		localizacao={campoid:data_ordem[i]['campo_id_permits'],posicao:i+1,campo_nome:data_ordem[i]['campo_id_permits']};
		ordem_campo.ordem.push(localizacao);
		i++;
	}
	alert(JSON.stringify(ordem_campo));
	$.ajax({
		  type: "POST",
		  data: {"opt":"3","ordem":JSON.stringify(ordem_campo)},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./PermitsServlet",
		  cache: false,
		  dataType: "text",
		  success: ReordenandoTabelaPermit
		});
	function ReordenandoTabelaPermit(data)
	{
		$.alert(data.toString());
		
	}
	
	
	
	
	//alert(JSON.stringify(ordem_campo));
    
}
function carrega_tabela_campos_permits() {
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"2"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./PermitsServlet",
		  cache: false,
		  dataType: "text",
		  success: formataTabelaCamposPermits
		});
	
	function formataTabelaCamposPermits(data)
	{
		
		$("#div_tabela_campos_permits").html("<div id=\"toolbar_campos_permits\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#Modal_campos_permits\">Novo Campo</button>"+
    			"<button id=\"edita_campo\" type=\"button\" class=\"btn btn-info\">Editar</button>"+
    			"<button id=\"desabilita_campo_permit\" type=\"button\" class=\"btn btn-danger\">Desabilitar Campo</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"carrega_tabela_campos_permits()\">Atualizar</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"ordem_tabelaPermits()\">Salvar</button>"+
    		    "</div>" + data);
		
		$('#tabela_campos_permits').bootstrapTable();
		
		$('#tabela_campos_permits').selectpicker('refresh');
		busca_rollouts();
		var $table = $('#tabela_campos_permits'),
		$button = $('#desabilita_campo_permit');
		$(function () {
	        $button.click(function () {
	        	var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
	                return row.campo_id_permits;
	            });
	            $table.bootstrapTable('remove', {
	                field: 'campo_id_permits',
	                values: ids
	            });
	            //alert(ids);
	            deleta_campos_permits(String(ids));
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
function deleta_campos_permits(campos){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"4",
			     "campos":campos},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./PermitsServlet",
		  cache: false,
		  dataType: "text",
		  success: deletaCampoPermit
		});
	function deletaCampoPermit(data)
	{
		carrega_tabela_campos_permits();
		
	}

}
function add_campo_permit(){
	var campo;
	var campo_tipo;
	var descricao;
	var grupo;
	var tabela_master;
	campo=document.getElementById("nome_campo_permit").value;
	campo_tipo=$('#tipo_campo_permit').find("option:selected").text();
	descricao=document.getElementById("desc_campo_permit").value
	grupo=$('#grupo_campo_permit').find("option:selected").text();
	tabela_master=document.getElementById("tabela_master_permit").checked;
	var data_ordem=$('#tabela_campos_permits').bootstrapTable('getData');
	var posicao=data_ordem.length;
	posicao=posicao+1;
	$.ajax({
		  type: "POST",
		  data: {"opt":"1",
			  "campo":campo,
			  "grupo":grupo,
			  "tipo":campo_tipo,
			  "desc":descricao,
			  "posicao":posicao,
			  "tabelaMaster":tabela_master},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./PermitsServlet",
		  cache: false,
		  dataType: "text",
		  success: RegistraCampoBD
		});
	function RegistraCampoBD(data){
		$.alert("Campo registrado com sucesso");
	}
}
function carrega_aquisicao(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"6",
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./PermitsServlet",
		  cache: false,
		  dataType: "text",
		  success: CarregaCamposAquisicao
		});
	function CarregaCamposAquisicao(data){
		var template=JSON.parse(data);
		 $('#divCamposAquisicao').jqxForm({
             template: template,
             padding: { left: 10, top: 10, right: 10, bottom: 10 }
         }); 
	}
}
function carrega_cadastro(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"7",
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./PermitsServlet",
		  cache: false,
		  dataType: "text",
		  success: CarregaCamposAquisicao
		});
	function CarregaCamposAquisicao(data){
		var template=JSON.parse(data);
		 $('#divCamposCadastro').jqxForm({
             template: template,
             padding: { left: 10, top: 10, right: 10, bottom: 10 }
         }); 
	}
}
function carrega_Licenciamento(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"8",
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./PermitsServlet",
		  cache: false,
		  dataType: "text",
		  success: CarregaCamposLicenciamento
		});
	function CarregaCamposLicenciamento(data){
		var template=JSON.parse(data);
		 $('#divCamposLicenciamento').jqxForm({
             template: template,
             padding: { left: 10, top: 10, right: 10, bottom: 10 }
         }); 
	}
}
function carrega_FinanceiroPermit(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"9",
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./PermitsServlet",
		  cache: false,
		  dataType: "text",
		  success: CarregaCamposFinanceiro
		});
	function CarregaCamposFinanceiro(data){
		var template=JSON.parse(data);
		 $('#divCamposFinanceiro').jqxForm({
             template: template,
             padding: { left: 10, top: 10, right: 10, bottom: 10 }
         }); 
	}
}
function carrega_HistoricoPermit(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"120",
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./PermitsServlet",
		  cache: false,
		  dataType: "text",
		  success: CarregaCamposHistorico
		});
	function CarregaCamposHistorico(data){
		var template=JSON.parse(data);
		 $('#divCamposFinanceiro').jqxForm({
             template: template,
             padding: { left: 10, top: 10, right: 10, bottom: 10 }
         }); 
	}
}
function add_cadastroPermits(){
	var cadastro=$('#divCamposCadastro').jqxForm().val();
	var aquisicao=$('#divCamposAquisicao').jqxForm().val();
	var licenciamento=$('#divCamposLicenciamento').jqxForm().val();
	var financeiro=$('#divCamposFinanceiro').jqxForm().val();
	//console.log(cadastro);
	$.ajax({
		  type: "POST",
		  data: {"opt":"10",
			  "cadastroCampos":JSON.stringify(cadastro),
			  "AquisicaoCampos":JSON.stringify(aquisicao),
			  "LicenciamentoCampos":JSON.stringify(licenciamento),
			  "FinanceiroCampos":JSON.stringify(financeiro)
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./PermitsServlet",
		  cache: false,
		  dataType: "text",
		  success: RegistraPermit
		});
	function RegistraPermit(data){
		$.alert(data.toString());
		}
}
function carregaTabelaMasterPermits(){
	var timestamp = Date.now();
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"5",
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./PermitsServlet",
		  cache: false,
		  dataType: "text",
		  success: CarregaTabela
		});
	function CarregaTabela(data){
		
		var dados = JSON.parse(data);
		var source =
	    {
	        datatype: "json",
	        datafields: dados.campos,
	        cache: false,
            url: './PermitsServlet',
	        updaterow: function (rowid, rowdata, commit) {
	            commit(true);
	        }
	    };
		 var dataAdapter = new $.jqx.dataAdapter(source,{formatData: function(data) {
         	data.opt=11;
        	data._=timestamp;
			//alert(JSON.stringify(data));
            return data;
        }});
		 $("#gridMaster_permits").jqxGrid(
		            {
		                width: '100%',
		                height: '100%',
		                source: dataAdapter,
		                pageable: true,
		                filterable: true,
		                pagesize: 40,
		                autoshowfiltericon: true,
		                pagesizeoptions: ['40', '100', '300'],
		                altrows: true,
		                editmode: 'click',
		                virtualmode: true,
		                rendergridrows: function(obj) {
		                    return obj.data;
		                },
		                columnsresize: true,
		                columns: dados.campos2
		            });
		 carrega_cadastro();
		 carrega_aquisicao();
		 carrega_Licenciamento();
		 carrega_FinanceiroPermit();
		 $('#tabs_permits').jqxTabs({ width: 1200, height: 400, position: 'top'});
	}
	 
}