function inverter_coordenadas(){
	var operadora =  $('#select_operadora_site').val();
	var valor;
	if(operadora==''){
		$.alert("Selecione uma Operadora");
		return;
	}
	var rowindexes = $('#tabela_detalhe_sites').jqxGrid('getselectedrowindexes');
	filtros={"filtros":[]};
	for(var v=0;v<rowindexes.length;v++){
		    valor=$('#tabela_detalhe_sites').jqxGrid('getrowid', rowindexes[v]);
		    if(valor!=null && valor!=""){
		    	filtros.filtros.push(valor);
		    }
		}
	var timestamp =Date.now();
	$.ajax({
		  type: "POST",
		  data: {"opt":"12",
			  "_": timestamp,
			  "operadora":operadora,
			  "filtros":JSON.stringify(filtros)
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./SiteMgmt",
		  cache: false,
		  dataType: "text",
		 success: sucesso_inversao_coordenadas
		});
	function sucesso_inversao_coordenadas(data){
		$.alert(data.toString());
	}
}

function append_site_novo_aprovacoes(){
	var $table = $('#tabela_aprovacoes');
	 $.getJSON('./SiteMgmt?opt=7', function(data) {	
		 
		 $table.bootstrapTable('append', data);
         //$table.bootstrapTable('scrollTo', 'bottom');
	 });
}

function Add_site(){
var siteid,nome,lat,lng,cidade,bairro,endereco,pais,operadora;
	//alert("Adicionando site");
	
	siteid= document.getElementById("site_siteid").value;
	nome=document.getElementById("site_sitename").value;
	lat= document.getElementById("site_lat").value;
	lng= document.getElementById("site_lng").value;
	cidade= document.getElementById("site_municipio").value;
	bairro= document.getElementById("site_bairro").value;
    endereco= document.getElementById("site_endereco").value;
    pais= document.getElementById("site_UF").value;
    operadora= document.getElementById("site_operadora").value;
    $.ajax({
		  type: "POST",
		  data: {"opt":"1",
			"siteid":siteid,  
			"nome":nome,
			"lat":lat, 
			"lng":lng, 
			"municipio":cidade, 
			"bairro":bairro,
            "endereco":endereco,
            "uf":pais,
            "operadora":operadora
			
		  },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./SiteMgmt",
		  cache: false,
		  dataType: "text",
		  success: onSuccessSite1
		});
	
	function onSuccessSite1(data)
	{
		
		document.getElementById("site_siteid").value="";
		document.getElementById("site_sitename").value="";
		document.getElementById("site_lat").value="";
		document.getElementById("site_lng").value="";
		document.getElementById("site_municipio").value="";
		document.getElementById("site_bairro").value="";
	    document.getElementById("site_endereco").value="";
	    document.getElementById("site_UF").value="";
	    document.getElementById("site_operadora").value="";
	}
}
function carrega_tabela_site(){
	 
	  var timestamp = Date.now();	
	  
	  
		var operadora =  $('#select_operadora_site').val();
		if(operadora==''){
			$.alert("Selecione uma Operadora")
		}else{
			 var source =
		     {
					 datatype: "json",
			         datafields: [
			        	 { name: 'site_id' , type: 'string'},
			        	 { name: 'site_operadora', type: 'string' },
			             { name: 'site_uf' , type: 'string'},
			             { name: 'site_municipio', type: 'string' },
			             { name: 'site_endereco', type: 'string' },
			             { name: 'site_bairro', type: 'string' },
			             { name: 'site_latitude', type: 'string' },
			             { name: 'site_longitude', type: 'string' }
			             
			         ],
		        
		         url: './SiteMgmt',
		         id: 'site_id',
		         filter: function() {
		                // update the grid and send a request to the server.
		                $("#tabela_detalhe_sites").jqxGrid('updatebounddata', 'filter');
		            },
		            sort: function() {
		                // update the grid and send a request to the server.
		                $("#tabela_detalhe_sites").jqxGrid('updatebounddata', 'sort');
		            },
		            
		         beforeprocessing: function(data) {
		                if (data != null && data.length > 0) {
		                    source.totalrecords = data[0].totalRecords;
		                }}
		     };
			 var dataAdapter = new $.jqx.dataAdapter(source,{
				 formatData: function(data) {
		            	data.opt=2;
		            	data.operadora=operadora;
		            	data._=timestamp;
						//alert(JSON.stringify(data));
		                return data;
		            },
		            downloadComplete: function(data, status, xhr) {
		                if (!source.totalRecords) {
		                    source.totalRecords = data.length;
		                }
		            },
		            loadError: function(xhr, status, error) {
		                throw new Error(error);
		            }
			 });
			 
		     $("#tabela_detalhe_sites").jqxGrid(
		             {
		                 width: getWidth('div_tabela_Sites') - 50,
		                 height: 450,
		                 theme:'light',
		                 source: dataAdapter,
		                 columnsresize: true,
		                 pageable: true,
		                 editable: true,
			             filterable: true,
			             autoshowfiltericon: true,
			             altrows: true,
			             selectionmode: 'checkbox',
			             pagesize: 20,
			             editmode: 'click',
			             
			             virtualmode: true,
			             rendergridrows: function(obj) {
			                    return obj.data;
			             },
			             ready: function () {
			                    
			                    var localizationObject = {
			                        filterstringcomparisonoperators: ['contem', 'não contem'],
			                        // filter numeric comparison operators.
			                        filternumericcomparisonoperators: ['less than', 'greater than'],
			                        // filter date comparison operators.
			                        filterdatecomparisonoperators: ['menor que', 'maior que','maior ou igual a','menor ou igual a','igual a'],
			                        // filter bool comparison operators.
			                        filterbooleancomparisonoperators: ['equal', 'not equal']
			                    }
			                    $("#jqxgrid").jqxGrid('localizestrings', localizationObject);
			                },
			                updatefilterconditions: function (type, defaultconditions) {
			                    var stringcomparisonoperators = ['CONTAINS', 'DOES_NOT_CONTAIN'];
			                    var numericcomparisonoperators = ['LESS_THAN', 'GREATER_THAN'];
			                    var datecomparisonoperators = ['LESS_THAN', 'GREATER_THAN','GREATER_THAN_OR_EQUAL','LESS_THAN_OR_EQUAL','EQUAL'];
			                    var booleancomparisonoperators = ['EQUAL', 'NOT_EQUAL'];
			                    switch (type) {
			                        case 'stringfilter':
			                            return stringcomparisonoperators;
			                        case 'numericfilter':
			                            return numericcomparisonoperators;
			                        case 'datefilter':
			                            return datecomparisonoperators;
			                        case 'booleanfilter':
			                            return booleancomparisonoperators;
			                    }
			                },
			             pagesizeoptions: ['20', '50', '100'],
		                 columns: [
		                       
		                       { text: 'Site', datafield: 'site_id', width: 150, columntype: 'textbox' },
		                       { text: 'Operadora', datafield: 'site_operadora', width: 100, columntype: 'textbox' },
		                       { text: 'UF', datafield: 'site_uf', width: 150, columntype: 'textbox' },
		                       { text: 'Municipio', datafield: 'site_municipio', width: 150, columntype: 'textbox' },
		                       { text: 'Logradouro', datafield: 'site_endereco', width: 150, columntype: 'textbox' },
		                       { text: 'Bairro', datafield: 'site_bairro', width: 150, columntype: 'textbox' },
		                       { text: 'Latitude', datafield: 'site_latitude', width: 150, columntype: 'textbox' },
		                       { text: 'Longitude', datafield: 'site_longitude', width: 150, columntype: 'textbox' }
		                   ]
		             });
		    
			
			
		     $("#tabela_detalhe_sites").on('cellendedit', function (event) {
		         var args = event.args;
		         console.log(args);
		         //alert(args.datafield);
		         //alert(args.row.recid);
		         //alert(args.row);
		         registra_mudanca_campos_site(args.rowindex,args.datafield,args.value,args.columntype,args.oldvalue);
		     });
		
		
	}
	
}
function registra_mudanca_campos_site(index,campo,valor,tipo,oldvalue){
	var site=$("#tabela_detalhe_sites").jqxGrid('getrowid', index);
	console.log(site);
	console.log(campo);
	console.log(valor);
	console.log(oldvalue);
	if(site){
	
	localizacao={id:site,colum:campo,value:valor,tipoc:'textbox',oldvalue:oldvalue};
	
	g_changes_site.atualizacoes=g_changes_site.atualizacoes+1;
	g_changes_site.campos.push(localizacao);
	}
	console.log(g_changes_site);
}
function registra_mudancas_sites_bd(){
	
	if(g_changes_site.atualizacoes>0){
		$('#btn_salvarSite').addClass( "disabled" );
		$('#btn_salvarSite').text('Aguarde a Finalização...');
		$('#btn_salvarSite').prop('disabled', true);
	$.ajax({
		  type: "POST",
		  data: {"opt":"14",
			"mudancas":JSON.stringify(g_changes_site) 
			},		  
		  url: "./SiteMgmt",
		  cache: false,
		  dataType: "text",
		  success: SalvaEdicaoSite
		});
	
	function SalvaEdicaoSite(data)
	{
		
	$.alert(data.toString());
	g_changes_site.atualizacoes=0;
	g_changes_site.campos=[];
	 $('#btn_salvarSite').removeClass( "disabled" );
	 $('#btn_salvarSite').prop('disabled', false);
	 $('#btn_salvarSite').text('Salvar');
	}
	}else{
		$.alert("Sem Mudanças para registrar!");
		
	}
}
function carregaSelectOperadora(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"15"
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./SiteMgmt",
		  cache: false,
		  dataType: "text",
		  success: SucessocarregaSelectOperadora
		});
	 function SucessocarregaSelectOperadora(data){
		 $('#select_operadora_site').html(data);
		 $('#select_operadora_site').selectpicker('refresh');
	 }
}
function carregaSelectOperadoraExport(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"16"
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./SiteMgmt",
		  cache: false,
		  dataType: "text",
		  success: SucessocarregaSelectOperadora
		});
	 function SucessocarregaSelectOperadora(data){
		 $('#exportSiteListaOperadora').html(data);
		
	 }
}
function sync_rollout(){
	if(geral.usuario=='masteradmin'){
	$('#btnSync_rolloutsites').addClass( "disabled" );
	$('#btnSync_rolloutsites').text('Aguarde a Finalização...');
	$('#btnSync_rolloutsites').prop('disabled', true);
	$.ajax({
 		  type: "POST",
 		  data: {"opt":"12"
 			 },		  
 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
 		  url: "./SiteMgmt",
 		  cache: false,
 		  dataType: "text",
 		  success: SyncRolloutSites
 		});
	 function SyncRolloutSites(data){
		 $.alert(data.toString());
	 }
	 $('#btnSync_rolloutsites').removeClass( "disabled" );
	 $('#btnSync_rolloutsites').prop('disabled', false);
	 $('#btnSync_rolloutsites').text('Sync Rollout');
	}else{
		$.alert("Sem Privilégio para essa Operação")
	}
}
function atualiza_flag(){
	
	$.confirm({
		icon: 'fas fa-exclamation-triangle',
	    title: 'Subistuicao completa',
	    content: 'Esta ação irá desabilitar os sites atuais e inserir os novos. Deseja continuar?',
	    type:'red',
	    columnClass:'medium',
	    buttons: {
	        Sim: function () {
	        	document.getElementById('flag_substituicao').value='S';
	        	//$("#input_sites_arq").fileinput('destroy');
	        	//$("#input_sites_arq").fileinput({
	            //	language: "pt-BR",
				//    uploadUrl: "./Upload_servlet", // server upload action
				//    uploadAsync: false,
				//    allowedFileExtensions: ['xlsx'],
				//    maxFileCount: 5,
				//    uploadExtraData: {
			    //        flag: document.getElementById('flag_substituicao').value
			    //        
			    //    }
				//});
	        	$('#modal_carrega_sites').modal('show');
	        },
	        Não: function () {
	            
	        }
	        
	    }
	});
	
	 
}
function exportarTodosSites(Operadora){
	var timestamp = Date.now();	
	$('#btnGroupDropExport').addClass( "disabled" );
	$('#btnGroupDropExport').text('Aguarde a Finalização...');
	$('#btnGroupDropExport').prop('disabled', true);
	 $.ajax({
	        url: './SiteMgmt?opt=11',
	        method: 'GET',
	        data:{"operadora":Operadora},
	        xhrFields: {
	            responseType: 'blob'
	        },
	        success: function (data) {
	        	//console.log(data);
	            var a = document.createElement('a');
	            var url = window.URL.createObjectURL(data);
	            a.href = url;
	            a.download = Operadora+'_SitesMSTP_'+timestamp+'.xlsx';
	            a.click();
	            window.URL.revokeObjectURL(url);
	            $('#btnGroupDropExport').removeClass( "disabled" );
	    		$('#btnGroupDropExport').prop('disabled', false);
	    		$('#btnGroupDropExport').text('Exportar');
	    	
	        }
	    });
	 
}
function deleta1_site(ids){
	//alert(ids);
	var aux=ids.split(";");
	//alert(aux[0]);
	//alert(aux[1]);
	$.ajax({
		  type: "POST",
		  data: {"opt":"4",
			  "site":aux[0]},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./SiteMgmt",
		  cache: false,
		  dataType: "text",
		  success: Desabilita_site
		});
	function Desabilita_site(data){
		var $table = $('#tabela_sites');
		$table.bootstrapTable('removeByUniqueId', aux[1]);
		alert(data);
	}
	
}
