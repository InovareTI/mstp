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
           
            carrega_portal();
            var Meusdockings = function (tab) {
                switch (tab) {
                    case 1:
                    	$('#docking_rollout').jqxDocking({  orientation: 'horizontal',width:1200, mode: 'docked',theme: 'material' });
                    	carrega_portalRollout();
                    	break;
                    case 2:
                    	$('#docking_PO').jqxDocking({  orientation: 'horizontal',width:1200, mode: 'docked',theme: 'material' });
                        break;
                }
            }
            $('#jqxtabs').jqxTabs({height:800,theme: 'light',initTabContent:Meusdockings});
            
            $('#range_espelho').jqxDateTimeInput({formatString:'dd/MM/yyyy',width:250,height:30,culture: 'pt-BR',theme: 'bootstrap',selectionMode: 'range'});
            $('#range_espelho_analise').jqxDateTimeInput({formatString:'dd/MM/yyyy',width:250,height:30,culture: 'pt-BR',theme: 'bootstrap',selectionMode: 'range'});
            $('#range_consulta_horas_banco').jqxDateTimeInput({formatString:'dd/MM/yyyy',width:250,height:30,culture: 'pt-BR',theme: 'bootstrap',selectionMode: 'range'});
            $('#range_despesas').jqxDateTimeInput({formatString:'dd/MM/yyyy',width:250,height:30,culture: 'pt-BR',theme: 'bootstrap',selectionMode: 'range'});
            $('#range_despesas').on('valueChanged', function (event) {
            	CarregaDespesas();
            });
            $('#range_consulta_horas_banco').on('valueChanged', function (event) {
            	carrega_extrato_he();
            });
            $('#jqxtabs_usuario').jqxTabs({height:800,theme: 'light'});
           // carrega_tabela_site();
            $("#TicketToolBar").jqxToolBar(
            		{ 
            		  width: "100%", 
            		  height: 45, 
            		  theme:'light',
            		  tools: 'button button button | button button button | button button | button',
            	     initTools: function (type, index, tool, menuToolIninitialization) {
                     var icon = $("<div class='jqx-editor-toolbar-icon jqx-editor-toolbar-icon-light buttonIcon'></div>");
                     switch (index) {
                         case 0:
                             icon.addClass("jqx-editor-toolbar-icon-bold jqx-editor-toolbar-icon-bold-light");
                             icon.attr("title", "Bold");
                             tool.append(icon);
                             break;
                         case 1:
                             icon.addClass("jqx-editor-toolbar-icon-italic jqx-editor-toolbar-icon-italic-light");
                             icon.attr("title", "Italic");
                             tool.append(icon);
                             break;
                         case 2:
                             icon.addClass("jqx-editor-toolbar-icon-underline jqx-editor-toolbar-icon-underline-light");
                             icon.attr("title", "Underline");
                             tool.append(icon);
                             break;
                         case 3:
                             icon.addClass("jqx-editor-toolbar-icon-justifyleft jqx-editor-toolbar-icon-justifyleft-light");
                             icon.attr("title", "Align Text Left");
                             tool.append(icon);
                             break;
                         case 4:
                             icon.addClass("jqx-editor-toolbar-icon-justifycenter jqx-editor-toolbar-icon-justifycenter-light");
                             icon.attr("title", "Center");
                             tool.append(icon);
                             break;
                         case 5:
                             icon.addClass("jqx-editor-toolbar-icon-justifyright jqx-editor-toolbar-icon-justifyright-light");
                             icon.attr("title", "Align Text Right");
                             tool.append(icon);
                             break;
                         case 6:
                             icon.addClass("jqx-editor-toolbar-icon-outdent jqx-editor-toolbar-icon-outdent-light");
                             icon.attr("title", "Decrease Indent");
                             tool.append(icon);
                             break;
                         case 7:
                             icon.addClass("jqx-editor-toolbar-icon-indent jqx-editor-toolbar-icon-indent-light");
                             icon.attr("title", "Increase Indent");
                             tool.append(icon);
                             break;
                         case 8:
                             icon.addClass("jqx-editor-toolbar-icon-insertimage jqx-editor-toolbar-icon-insertimage-light");
                             icon.attr("title", "Insert Image");
                             tool.append(icon);
                             break;
                     }
                 }
             });
         
            carrega_tree_rollout();
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
            $("#excelExport").jqxButton({disabled: true});
           
            $('#mainSplitter').jqxSplitter({ 
            	width: 1200,
            	height: 800,
            	orientation: 'horizontal',
            	panels: [{ size: 400 }, { size: 400 }] 
            });
           
            $("#excelExport").click(function () {
            	exportar_analise_ponto();         
            });
           
            $("#input_usuario1_arq").fileinput({
            	language: "pt-BR",
			    uploadUrl: "./UserMgmt?opt=17", // server upload action
			    uploadAsync: false,
			    allowedFileExtensions: ['xlsx'],
			    maxFileCount: 5
			});
           
            $("#input_PO_file").fileinput({
			    uploadUrl: "./POControl_Servlet?opt=12", 
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
            $("#input_usuario2_arq").fileinput({
            	language: "pt-BR",
			    uploadUrl: "./UserMgmt?opt=21", // server upload action
			    uploadAsync: false,
			    allowedFileExtensions: ['xlsx'],
			    maxFileCount: 5
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
            //if(geral.perfil.search("RolloutView")>=0 || geral.perfil.search("RolloutManager")>=0){
            	
            	carrega_gant();
            
            carrega_cliente_table();
            load_site_markers();
            carrega_select_func_ponto();
            carrega_usuarios();
            
            $('#PO_upload_Modal').on('show.bs.modal', function (event) {
            	atualiza_select_cliente();
            });
            $('#modal_project_add').on('show.bs.modal', function (event) {
            	atualiza_select_cliente();
            });
            $('#modal_upload_rollout').on('show.bs.modal', function (event) {
            	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
	           	 var rolloutid;
	           	 if (item) {
	           		 rolloutid=item.value;
	           	 }else{
	           		 rolloutid="Rollout1";
	           	 }
            	$("#input_usuario3_arq").fileinput({
                	language: "pt-BR",
    			    uploadUrl: "./RolloutServlet?opt=5&rolloutid="+rolloutid, // server upload action
    			    uploadAsync: false,
    			    allowedFileExtensions: ['xlsx'],
    			    maxFileCount: 1
    			}).on('filebatchuploadsuccess', function(event, data) {
    				$.alert("Acompanhe o progresso do carregamento em \"Minhas Importações\"");
    				//carrega_gant();
    			});
          });
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
          		//$.alert("Nenhum linha selecionada. Utilize os campos de Filtros.(Filtro pelo campo de tempo ainda em Desenvolvimento, demais campos ja estão OK para uso!)");
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
                        { name: 'Data da Atualização', type: 'date' }
                    ],
                    id: 'id',
                    localdata: '[]',
                    sortcolumn: 'Data da Atualização',
                    sortdirection: 'desc'
                };
          		var dataAdapter = new $.jqx.dataAdapter(source);
          		$("#grid_historico_rollout").jqxGrid(
          	            {
          	            	width: 850,
          	                source: dataAdapter,
          	                columnsresize: true,
          	                filterable: true,
          	                sortable: true,
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
          	                    { text: 'Data da Atualização', datafield: 'Data da Atualização',filtertype: 'checkedlist',cellsformat:"dd/MM/yyyy HH:mm:ss", width: 170}
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
	                        { name: 'Data da Atualização', type: 'date' }
	                    ],
	                    id: 'id',
	                    localdata: dataaux,
	                    sortcolumn: 'Data da Atualização',
	                    sortdirection: 'desc'
	                };
	          		var dataAdapter = new $.jqx.dataAdapter(source);
	          		$("#grid_historico_rollout").jqxGrid(
	          	            {
	          	            	width: 850,
	          	                source: dataAdapter,
	          	                columnsresize: true,
	          	                filterable: true,
	          	                sortable: true,
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
	          	                    { text: 'Data da Atualização', datafield: 'Data da Atualização',filtertype: 'checkedlist',cellsformat:"dd/MM/yyyy HH:mm:ss", width: 170}
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
	 if(geral.perfil.search("AssinaturaManager")>=0){
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
			$("#tabela_div_assinaturas").html(data);
			$("#tabela_modulo_mstp").bootstrapTable();
			
		}
	 }
}
function inicia_assinatura(opt){
	if(opt==1){
	$.confirm({
	    title: 'Termos de Uso',
	    content: 'url:termos.txt',
	    columnClass: 'medium',
	    type: 'red',
	    buttons: {
	    	Aceitar: function () {
	    		if(geral.perfil.search("AssinaturaManager")>=0){
	    			$.ajax({
	    				  type: "POST",
	    				  data: {"opt":"39"},		  
	    				  //url: "http://localhost:8080/DashTM/D_Servlet",	  
	    				  url: "./POControl_Servlet",
	    				  cache: false,
	    				  dataType: "text",
	    				  success: onSuccessinicia_aceite_termo
	    				});
	    			function onSuccessinicia_aceite_termo(data){
	    				$.alert(data.toString());
	    				inicia_assinatura2();
	    				
	    			}
	    		}
	    	},
	    	Cancela:function(){
	    		$.alert("Operação abortada!");
	    		window.close();
	    		}
	    	}
	});
	}
	
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
		 geral.empresa_id=data.empresa_id;
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
	 if(opt=="ItemPO" || opt=="portal" || opt=="senha" || opt=="usuarios_ponto" || opt=="usuarios_banco_hh" || opt=="mstp_mobile_report_diario" || opt=="rel_ponto_kpi" || opt=="usuarios_ponto_analise"){
		 $(".janelas").hide();
	     document.getElementById(opt).style.display = "block";
	}else if(opt=="ticketWindow"){
		$(".janelas").hide();
	     document.getElementById(opt).style.display = "block";
	     ticketStarter();
	}else if(opt=="importacoes"){
		 $(".janelas").hide();
	     document.getElementById(opt).style.display = "block";
		carrega_importacoes();
	}else if(opt=="clientes" && geral.perfil.search("ClienteManager")>=0){
		$(".janelas").hide();
    	document.getElementById(opt).style.display = "block";
	}else if(opt=="equipe_diarias" && geral.perfil.search("DiariaManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		CarregaDespesas();
	}else if(opt=="rollout" && geral.perfil.search("RolloutView")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="checklist_review" && geral.perfil.search("RolloutManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_checklist();
	}else if(opt=="PO" && geral.perfil.search("POManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_PO(0);
	}else if(opt=="campos_rollout" && geral.perfil.search("RolloutManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="arvore_rollout_conf" && geral.perfil.search("RolloutManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_tree_rollout_conf();
	}else if(opt=="rollout" && geral.perfil.search("RolloutManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="permits" && geral.perfil.search("PermitsManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carregaTabelaMasterPermits();
	}else if(opt=="permitsCampos" && geral.perfil.search("PermitsManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_tabela_campos_permits();
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
	}else if(opt=="justificativas" && geral.perfil.search("Usuarios Manager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_justificativas();
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
		
	}else if(opt=="usuarios_extra_hh" && geral.perfil.search("BancoHHApprover")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		
	}else if(opt=="projetos" && geral.perfil.search("ProjectManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_project_table();
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