    var text = '{ "atualizacoes":0, "campos" : []}';
	var g_changes = JSON.parse(text);
	var text = '{ "rows":[]}';
	var deletar=[];
	var geral={};
	var deletar_grid=[];
	var row = JSON.parse(text);
	var milestone_pg;
	var map;
	//var mymap;
$(document).ready(function () {
	
			geral.perfil="";
			geral.banco="0";
            carrega_perfil();
            var initWidgets = function (tab) {
                switch (tab) {
                    case 0:
                        
                        break;
                    case 1:
                    	 if(geral.perfil.search("RolloutManager")>=0){
                			 
                			 $("#from").jqxDateTimeInput({selectionMode: 'range'});
                			 $("#from").on('change', function (event) {
                                 //var selection = $("#jqxWidget").jqxDateTimeInput('getRange');
                                 g1(1,'grafico_container1_rollout');
                             });
                			 g1(0,'grafico_container1_rollout');
                			 
                		 }
                        break;
                }
            }
            $('#jqxtabs').jqxTabs({height:800,theme: 'light',initTabContent: initWidgets});
            $('#jqxtabs').on('selected', function (event) {
            	//console.log(event);
                if(event.args.item==1){
                	g1(0,'grafico_container1_rollout');
       			    g9(0,'grafico_container2_rollout');
                }
            });
            $('#range_espelho').jqxDateTimeInput({formatString:'dd/MM/yyyy',width:250,height:30,culture: 'pt-BR',theme: 'bootstrap',selectionMode: 'range'});
            $('#range_espelho_analise').jqxDateTimeInput({formatString:'dd/MM/yyyy',width:250,height:30,culture: 'pt-BR',theme: 'bootstrap',selectionMode: 'range'});
            carrega_portal();
            carrega_usuarios();
            carrega_tabela_site();
            
            calcula_quantidade_hh_disponivel();
            $("#input_sites_arq").fileinput({
            	language: "pt-BR",
			    uploadUrl: "./Upload_servlet", // server upload action
			    uploadAsync: false,
			    allowedFileExtensions: ['xlsx'],
			    maxFileCount: 5,
			    uploadExtraData: function(previewId, index) {
			        var data = {
			        		flag: document.getElementById('flag_substituicao').value
			           
			        };
			        return data;
			    }
			    
			}).on('fileuploaded', function(event, data, previewId, index) {
				var form = data.form, files = data.files, extra = data.extra,
		        response = data.response, reader = data.reader;
				console.log(response);
		});
            $("#input_usuario1_arq").fileinput({
            	language: "pt-BR",
			    uploadUrl: "./UserMgmt?opt=17", // server upload action
			    uploadAsync: false,
			    allowedFileExtensions: ['xlsx'],
			    maxFileCount: 5
			});
            $("#input_usuario2_arq").fileinput({
            	language: "pt-BR",
			    uploadUrl: "./UserMgmt?opt=21", // server upload action
			    uploadAsync: false,
			    allowedFileExtensions: ['xlsx'],
			    maxFileCount: 5
			});
            $("#input_usuario3_arq").fileinput({
            	language: "pt-BR",
			    uploadUrl: "./RolloutServlet?opt=5", // server upload action
			    uploadAsync: false,
			    allowedFileExtensions: ['xlsx'],
			    maxFileCount: 1
			}).on('filebatchuploadsuccess', function(event, data) {
				$.alert("Acompanhe o progresso do carregamento em \"Minhas Importações\"");
				carrega_gant();
			});
            $("#jqxExpander").jqxExpander({ width: '100%'});
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
            
            carrega_tabela_campos_rollout();
            carrega_gant();
            //carrega_cliente_table();
            load_site_markers();
            carrega_select_func_ponto();
            
            $('#modal_ajuste_ponto').on('show.bs.modal', function (event) {
            	
            	  var button = $(event.relatedTarget) // Button that triggered the modal
            	  var recipient = button.data('dia') // Extract info from data-* attributes
            	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
            	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
            	  prepara_ajuste_ponto(recipient);
            });
            $('#modal_rollout_history').on('show.bs.modal', function (event) {
              filtros={"filtros":[]};	
          	  var button = $(event.relatedTarget) // Button that triggered the modal
          	  var rowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes');
          	  if(rowindexes.length==0){
          		$.alert("Nenhum linha selecionada. Utilize os campos de Filtros.(Filtro pelo campo de tempo ainda em Desenvolvimento, demais campos ja estão OK para uso!)");
          		$.ajax({
			 		  type: "POST",
			 		  data: {"opt":"19",
			 			},		  
			 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			 		  url: "./RolloutServlet",
			 		  cache: false,
			 		  dataType: "text",
			 		 success: atualiza_select_historico_campos
			 		});
          		
          		$("#periodo_hitorico_mudanca").jqxDateTimeInput({selectionMode: 'range'});
          		var source =
                {
                    datatype: "json",
                    datafields: [
                    	{ name: 'id', type: 'string' },
                        { name: 'recid', type: 'int' },
                        { name: 'Site ID', type: 'string' },
                        { name: 'Tipo Campo', type: 'string' },
                        { name: 'Milestone', type: 'string' },
                        { name: 'Campo', type: 'string' },
                        { name: 'Valor Anterior', type: 'string' },
                        { name: 'Novo Valor', type: 'string' },
                        { name: 'Atualizado Por', type: 'string' },
                        { name: 'Data da Atualização', type: 'string' }
                    ],
                    id: 'id',
                    localdata: '[]'
                };
          		var dataAdapter = new $.jqx.dataAdapter(source);
          		$("#grid_historico_rollout").jqxGrid(
          	            {
          	            	width: 850,
          	                source: dataAdapter,
          	                columnsresize: true,
          	                filterable: true,
          	                autoshowfiltericon: true,
          	                columns: [
          	                    { text: 'ID', datafield: 'recid',filtertype: 'checkedlist' },
          	                    { text: 'Site', datafield: 'Site ID',filtertype: 'checkedlist', width: 80 },
          	                    { text: 'Tipo Campo', datafield: 'Tipo Campo',filtertype: 'checkedlist', width: 100 },
          	                    { text: 'Milestone', datafield: 'Milestone',filtertype: 'checkedlist', width: 100 },
          	                    { text: 'Campo', datafield: 'Campo',filtertype: 'checkedlist', width: 110},
          	                    { text: 'Valor Anterior', datafield: 'Valor Anterior',filtertype: 'checkedlist', width: 100},
          	                    { text: 'Novo Valor', datafield: 'Novo Valor',filtertype: 'checkedlist', width: 100},
          	                    { text: 'Atualizado Por', datafield: 'Atualizado Por',filtertype: 'checkedlist', width: 110},
          	                    { text: 'Data da Atualização', datafield: 'Data da Atualização',filtertype: 'checkedlist', width: 170}
          	                ]
          	            });
          	  }else{
	          	for(var v=0;v<rowindexes.length;v++){
					filtros.filtros.push($('#jqxgrid').jqxGrid('getrowid', rowindexes[v]));
				}
	          	$("#periodo_hitorico_mudanca").jqxDateTimeInput({selectionMode: 'range'});
	          	$.ajax({
			 		  type: "POST",
			 		  data: {"opt":"19",
			 			},		  
			 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			 		  url: "./RolloutServlet",
			 		  cache: false,
			 		  dataType: "text",
			 		 success: atualiza_select_historico_campos
			 		});
	          	$.ajax({
			 		  type: "POST",
			 		  data: {"opt":"18",
			 			"filtros":JSON.stringify(filtros)
			 			},		  
			 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			 		  url: "./RolloutServlet",
			 		  cache: false,
			 		  dataType: "text",
			 		  success: mostra_historico_rollout
			 		});
	          	function mostra_historico_rollout(data){
	          		dataaux=JSON.parse(data);
	          		var source =
	                {
	                    datatype: "json",
	                    datafields: [
	                    	{ name: 'id', type: 'string' },
	                        { name: 'recid', type: 'int' },
	                        { name: 'Site ID', type: 'string' },
	                        { name: 'Tipo Campo', type: 'string' },
	                        { name: 'Milestone', type: 'string' },
	                        { name: 'Campo', type: 'string' },
	                        { name: 'Valor Anterior', type: 'string' },
	                        { name: 'Novo Valor', type: 'string' },
	                        { name: 'Atualizado Por', type: 'string' },
	                        { name: 'Data da Atualização', type: 'string' }
	                    ],
	                    id: 'id',
	                    localdata: dataaux
	                };
	          		var dataAdapter = new $.jqx.dataAdapter(source);
	          		$("#grid_historico_rollout").jqxGrid(
	          	            {
	          	            	width: 850,
	          	                source: dataAdapter,
	          	                columnsresize: true,
	          	                filterable: true,
	          	                autoshowfiltericon: true,
	          	                columns: [
	          	                    { text: 'ID', datafield: 'recid',filtertype: 'checkedlist' },
	          	                    { text: 'Site', datafield: 'Site ID',filtertype: 'checkedlist', width: 80 },
	          	                    { text: 'Tipo Campo', datafield: 'Tipo Campo',filtertype: 'checkedlist', width: 100 },
	          	                    { text: 'Milestone', datafield: 'Milestone',filtertype: 'checkedlist', width: 100 },
	          	                    { text: 'Campo', datafield: 'Campo',filtertype: 'checkedlist', width: 110},
	          	                    { text: 'Valor Anterior', datafield: 'Valor Anterior',filtertype: 'checkedlist', width: 100},
	          	                    { text: 'Novo Valor', datafield: 'Novo Valor',filtertype: 'checkedlist', width: 100},
	          	                    { text: 'Atualizado Por', datafield: 'Atualizado Por',filtertype: 'checkedlist', width: 110},
	          	                    { text: 'Data da Atualização', datafield: 'Data da Atualização',filtertype: 'checkedlist', width: 170}
	          	                ]
	          	            });
	          	}
          	  }
          	function atualiza_select_historico_campos(data){
      			$('#select_campo_historico_filtro').html(data);
      			$('#select_campo_historico_filtro').selectpicker('refresh');
      		}
          });
            $('#modal_ajuste_ponto').on('hide.bs.modal', function (event) {
            	fecha_tela_ajuste_ponto();
            });
            
            $('#modal_carrega_sites').on('hide.bs.modal', function (e) {
            	document.getElementById('flag_substituicao').value='N';
            });
            $('#usuario_perfil').multiselect({nonSelectedText: 'Selecione um Perfil',allSelectedText: 'Todos'});
            //$('#tabela_usuario_folha_ponto').DataTable();
            
			$("#senha_atual").jqxPasswordInput({placeHolder: "Senha Atual:"});
			$("#confirma_senha").jqxPasswordInput({placeHolder: "Confirmar Senha:"});
			$('input[type=us-date1]').w2field('date', { format: 'd/m/yyyy', end: $('input[type=us-date2]') });
			$('input[type=us-date2]').w2field('date', { format: 'd/m/yyyy', start: $('input[type=us-date1]') });
			
			
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
function inicia_assinatura2(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"37"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccessinicia_assinatura2
		});
	function onSuccessinicia_assinatura2(data){
		if(data=="ok"){
			$("#btn_assinar1").html("Módulo Assinado");
		}
	}
}
function inicia_assinatura(){
	$.confirm({
	    title: 'Termos de Uso',
	    content: 'url:termos.txt',
	    columnClass: 'medium',
	    type: 'red',
	    buttons: {
	    	Confirma: function () {
	    		if(geral.perfil.search("AssinaturaManager")>=0){
	    		$.dialog({
	    			title:'Pague seguro com PayPal',
	    			content:'<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">'+
	    				'<input type="hidden" name="cmd" value="_s-xclick">'+
	    		'<input type="hidden" name="hosted_button_id" value="D73EZDBFS9C22">'+
	    		'<input type="image" src="https://www.paypalobjects.com/pt_BR/BR/i/btn/btn_subscribeCC_LG.gif" border="0" name="submit" alt="PayPal - A maneira fácil e segura de enviar pagamentos online!">'+
	    		'<img alt="" border="0" src="https://www.paypalobjects.com/pt_BR/i/scr/pixel.gif" width="1" height="1">'+
	    		'</form>'
	    		
	    		});
	    		}
	    	},
	    	Cancela:function(){
	    		$.alert("Operação abortada!");
	    		window.close();
	    		}
	    	}
	});
	}
function carrega_perfil(){
	geral.perfil="";
	$.getJSON('./POControl_Servlet?opt=33', function(data) {
	
		 //alert("perfil carregado");
		 geral.perfil=data.perfil;
		 geral.usuario=data.usuario;
		 geral.nome=data.nome;
		 geral.ctps=data.ctps;
		 geral.cargo=data.cargo;
		 geral.pis=data.pis;
		 geral.matricula=data.matricula;
		 geral.admissao=data.admissao;
		 geral.empresa_nome=data.empresa_nome;
		 geral.empresa_cnpj=data.empresa_cnpj;
		 geral.empresa_cnae=data.empresa_cnae;
		 geral.empresa_bairro=data.empresa_bairro;
		 geral.empresa_uf=data.empresa_uf;
		 geral.empresa_cidade=data.empresa_cidade;
		 geral.empresa_endereco=data.empresa_endereco;
		 if(geral.perfil.search("AssinaturaManager")>=0){
			 document.getElementById('assinatura_menu_div').style.display = "block";
		 }
		 console.log(geral);
		 
	 });
}
function menu(opt){
	 if(opt=="portal" || opt=="senha" || opt=="usuarios_ponto" || opt=="usuarios_banco_hh" || opt=="mstp_mobile_report_diario" || opt=="rel_ponto_kpi" || opt=="usuarios_ponto_analise"){
		 $(".janelas").hide();
	     document.getElementById(opt).style.display = "block";
	}else if(opt=="importacoes"){
		 $(".janelas").hide();
	     document.getElementById(opt).style.display = "block";
		carrega_importacoes();
	}else if(opt=="clientes" && geral.perfil.search("ClienteManager")>=0){
		$(".janelas").hide();
    	document.getElementById(opt).style.display = "block";
	}else if(opt=="rollout" && geral.perfil.search("RolloutView")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="po" && geral.perfil.search("POManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}
	else if(opt=="campos_rollout" && geral.perfil.search("RolloutManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="rollout" && geral.perfil.search("RolloutManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="pivot_design_div" && geral.perfil.search("RolloutManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		init_pivot();
	}else if(opt=="pivot_view_div" && geral.perfil.search("RolloutManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_select_pivot();
	}
	else if(opt=="usuarios" && geral.perfil.search("Usuarios Manager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="vistoria_campos" && geral.perfil.search("VistoriaManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		tabela_relatorio();
	}
	else if(opt=="faltas_relatorio" && geral.perfil.search("Usuarios Manager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		
	}else if(opt=="feriados" && geral.perfil.search("Usuarios Manager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		inicializa_scheduler_feriado();
	}else if(opt=="Sites" && geral.perfil.search("Site Manager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="assinaturas" && geral.perfil.search("AssinaturaManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		inicia_assinatura2();
	}else if(opt=="aprovacoes" && geral.perfil.search("BancoHHApprover")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="mapa_Operacional" && geral.perfil.search("MapViewer")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_mapa_operacional();
		inicializa_mapa_full();
		
	}
	else{
		alert("Seu perfil não é compatível com essa função!");
	}
}