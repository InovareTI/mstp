    var text = '{ "atualizacoes":0, "campos" : []}';
	var g_changes = JSON.parse(text);
	var g_changes_site = JSON.parse(text);
	var text = '{ "rows":[]}';
	var deletar=[];
	var geral={};
	var deletar_grid=[];
	var row = JSON.parse(text);
	var milestone_pg;
	var map;
	var dados_rollout;
	var myDoughnut;
	var myDoughnut2;
	var timer;
	var optionsKanban=JSON.parse("{}");
	//var mymap;
$(document).ready(function () {
	        geral.perfil="";
	        geral.banco="0";
	        carrega_perfil();
	        mapboxgl.accessToken = 'pk.eyJ1IjoiZmFiaW9zYWxidXF1ZXJxdWUiLCJhIjoiY2tnamJldTI5MDg0djJ0bXNyZGVyenozeCJ9.qmVVng-6k8FdjiJRUde_iw';
	        carrega_portal();
            carrega_cliente_table();
            carregaModulosPortal();
            var Meusdockings = function (tab) {
                switch (tab) {
                case 1:
                	var text = $('#jqxtabs').jqxTabs('getTitleAt', tab); 
                	$('#docking').jqxDocking({  orientation: 'horizontal', mode: 'docked',theme: 'material' });
                	$.ajax({
              		  type: "POST",
              		  data: {"opt":"21"},		  
              		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
              		  url: "./POControl_Servlet",
              		  cache: false,
              		  dataType: "text",
              		  success: onSuccess21
              		});
              	 function onSuccess21(data){
              		$('#docking').jqxDocking('importLayout', data);
              	 }
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
                	 g5(0,'grafico_container3');
            		 g6('grafico_container1');
            		 g7('grafico_container2');
            		 g8('grafico_container4');
                	break;    
                case 2:
                    	$('#docking_rollout').jqxDocking({  orientation: 'horizontal', mode: 'docked',theme: 'material' });
                    	
                    	carrega_portalRollout();
                    	break;
                case 3:
                    	$('#docking_PO').jqxDocking({  orientation: 'horizontal', mode: 'docked',theme: 'material' });
                    	g4(0,'grafico_container1_PO');
                    	g3(0,'grafico_container2_PO');
                    	g15(0,'grafico_container3_PO');
                    	g16(0,'grafico_container4_PO');
                        break;
                }
            }
            $('#jqxtabs').jqxTabs({height:800,theme: 'light',initTabContent:Meusdockings});
            $('#div_dt_site_sign').jqxDateTimeInput({formatString:'dd/MM/yyyy HH:mm:ss',width:250,height:30,culture: 'pt-BR',theme: 'bootstrap',showTimeButton: true});
            $('#div_dt_site_sign').on('valueChanged', function (event) {
                //console.log(event);
            	var date = event.args.date;
            	var datecompouse = moment(date).format('L') + " " + moment(date).format('LTS');
            	var ticket = sessionStorage.getItem("ItemMovedId");
            	$.ajax({
           		  type: "POST",
           		  data: {"opt":"54","tipo":"SiteSign","date":datecompouse,"ticket":ticket
           			 },	  
           		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
           		  url: "./POControl_Servlet",
           		  cache: false,
           		  dataType: "text",
           		  success: registra_data_SS
           		});
            	function registra_data_SS(data){
            		var detalhes = JSON.parse("{}");
                    detalhes.ticketnum=ticket;
                    detalhes.tipo="SiteSign";
                    detalhes.desc = "Site Sign registrado na data "+datecompouse+" por "+geral.usuario;
            		registraEventosTicket(detalhes);
            	}
            });
            var condicao = sessionStorage.getItem("condicao");
            if(condicao){
            	sessionStorage.removeItem("condicao");
            }
            $('#div_dt_site_verify').jqxDateTimeInput({formatString:'dd/MM/yyyy HH:mm:ss',width:250,height:30,culture: 'pt-BR',theme: 'bootstrap',showTimeButton: true});
            $('#div_dt_site_verify').on('valueChanged', function (event) {
                //console.log(event);
            	var date = event.args.date;
            	var datecompouse = moment(date).format('L') + " " + moment(date).format('LTS');
            	var ticket = sessionStorage.getItem("ItemMovedId");
            	$.ajax({
           		  type: "POST",
           		  data: {"opt":"54","tipo":"SiteVerify","date":datecompouse,"ticket":ticket
           			 },	  
           		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
           		  url: "./POControl_Servlet",
           		  cache: false,
           		  dataType: "text",
           		  success: registra_data_SV
           		});
            	function registra_data_SV(data){
            		var detalhes = JSON.parse("{}");
                    detalhes.ticketnum=ticket;
                    detalhes.tipo="SiteVerify";
                    detalhes.desc = "Site Verify registrado na data "+datecompouse+" por "+geral.usuario;
            		registraEventosTicket(detalhes);
            	}
            });
            $('#div_dt_mos').jqxDateTimeInput({formatString:'dd/MM/yyyy HH:mm:ss',width:250,height:30,culture: 'pt-BR',theme: 'bootstrap',showTimeButton: true});
            $('#div_dt_mos').on('valueChanged', function (event) {
                //console.log(event);
            	var date = event.args.date;
            	var datecompouse = moment(date).format('L') + " " + moment(date).format('LTS');
            	var ticket = sessionStorage.getItem("ItemMovedId");
            	$.ajax({
           		  type: "POST",
           		  data: {"opt":"54","tipo":"MOS_Date","date":datecompouse,"ticket":ticket
           			 },	  
           		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
           		  url: "./POControl_Servlet",
           		  cache: false,
           		  dataType: "text",
           		  success: registra_data_mos
           		});
            	function registra_data_mos(data){
            		var detalhes = JSON.parse("{}");
                    detalhes.ticketnum=ticket;
                    detalhes.tipo="MOS_Date";
                    
            		detalhes.desc = "MOS registrado em "+datecompouse+" por "+geral.usuario;
            		
            		registraEventosTicket(detalhes);
            		
            	}
            });
            $('#div_dt_agendamento_qc').jqxDateTimeInput({formatString:'dd/MM/yyyy HH:mm:ss',width:250,height:30,culture: 'pt-BR',theme: 'bootstrap',showTimeButton: true});
            $('#div_dt_agendamento_qc').on('valueChanged', function (event) {
                //console.log(event);
            	var date = event.args.date;
            	var datecompouse = moment(date).format('L') + " " + moment(date).format('LTS');
            	var ticket = sessionStorage.getItem("ItemMovedId");
            	$.ajax({
           		  type: "POST",
           		  data: {"opt":"54","tipo":"QCAgendamento","date":datecompouse,"ticket":ticket
           			 },	  
           		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
           		  url: "./POControl_Servlet",
           		  cache: false,
           		  dataType: "text",
           		  success: registra_data_qc
           		});
            	function registra_data_qc(data){
            		var detalhes = JSON.parse("{}");
                    detalhes.ticketnum=ticket;
                    detalhes.tipo="AgendamentoQC";
                    
            		detalhes.desc = "QC agendado para "+datecompouse+" por "+geral.usuario;
            		
            		registraEventosTicket(detalhes);
            		
            	}
            });
            $('#jqxCheckBoxSite_').jqxCheckBox({ width: 260, height: 25});
            $("#jqxCheckBoxSite_").on('change', function (event) {
                var checked = event.args.checked;
                var ticket = sessionStorage.getItem("ItemMovedId");
               
                	$.ajax({
                 		  type: "POST",
                 		  data: {"opt":"54","tipo":"Site_OK","ticket":ticket,"valor":checked
                 			 },	  
                 		  url: "./POControl_Servlet",
                 		  cache: false,
                 		  dataType: "text",
                 		  success: registra_OK_Site
                 		});
                  	function registra_OK_Site(data){
                  		
                  		var detalhes = JSON.parse("{}");
                          detalhes.ticketnum=ticket;
                          detalhes.tipo="Site_OK";
                          
                  		detalhes.desc = "Material Entregue no Site em  "+moment().format('L')+" por "+geral.usuario;
                  		registraEventosTicket(detalhes);
                  		
                  	}
                
            });
            $('#jqxCheckBoxWH_').jqxCheckBox({ width: 260, height: 25});
            $("#jqxCheckBoxWH_").on('change', function (event) {
                var checked = event.args.checked;
                var ticket = sessionStorage.getItem("ItemMovedId");
               
                	$.ajax({
                 		  type: "POST",
                 		  data: {"opt":"54","tipo":"WH_OK","ticket":ticket,"valor":checked
                 			 },	  
                 		  url: "./POControl_Servlet",
                 		  cache: false,
                 		  dataType: "text",
                 		  success: registra_OK_WH
                 		});
                  	function registra_OK_WH(data){
                  		
                  		var detalhes = JSON.parse("{}");
                          detalhes.ticketnum=ticket;
                          detalhes.tipo="WH_OK";
                          
                  		detalhes.desc = "Material Entregue no WhareHouse em  "+moment().format('L')+" por "+geral.usuario;
                  		registraEventosTicket(detalhes);
                  		
                  	}
                
            });
            $('#jqxCheckBoxEHS_').jqxCheckBox({ width: 260, height: 25});
            $("#jqxCheckBoxEHS_").on('change', function (event) {
                var checked = event.args.checked;
                var ticket = sessionStorage.getItem("ItemMovedId");
               
                	$.ajax({
                 		  type: "POST",
                 		  data: {"opt":"54","tipo":"EHS_OK","ticket":ticket,"valor":checked
                 			 },	  
                 		  url: "./POControl_Servlet",
                 		  cache: false,
                 		  dataType: "text",
                 		  success: registra_OK_EHS
                 		});
                  	function registra_OK_EHS(data){
                  		
                  		var detalhes = JSON.parse("{}");
                          detalhes.ticketnum=ticket;
                          detalhes.tipo="EHS_OK";
                          
                  		detalhes.desc = "EHS registrado como OK em  "+moment().format('L')+" por "+geral.usuario;
                  		registraEventosTicket(detalhes);
                  		
                  	}
                
            });
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
          
            $("#TicketToolBar").jqxToolBar(
            		{ 
            		  width: "100%", 
            		  height: 45, 
            		  theme:'light',
            		  tools: 'toggleButton dropdownlist dropdownlist | toggleButton toggleButton toggleButton',
            	      initTools: function (type, index, tool, menuToolIninitialization) {
                     
                     switch (index) {
                         case 0:
                             tool.text("Meus Tickets");
                             tool.on("click", function () {
                                 var toggled = tool.jqxToggleButton("toggled");
                               
                                 if (toggled) {
                                	 optionsKanban.usuario=geral.usuario;
                                	
                                	 ticketStarter(optionsKanban);
                                 } else {
                                	 optionsKanban.usuario="ALL";
                                	 
                                	 ticketStarter(optionsKanban);
                                 }
                             });
                             break;
                         case 1:
                        	 $.ajax({
                          		  type: "POST",
                          		  data: {"opt":"51"
                          			 },	  
                          		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
                          		  url: "./POControl_Servlet",
                          		  cache: false,
                          		  dataType: "text",
                          		  success: aaaaa
                          		});
                           	 function aaaaa(data){
                           		 var aux = JSON.parse(data);
                           		 tool.jqxDropDownList({ width: 220,height:32,theme:"light", source: aux });
                           	 } 
	                        	 tool.on('select', function (event) {
	                        		 var args = event.args;
	                        		 var item = tool.jqxDropDownList('getItem', args.index);
	                        		 optionsKanban.projeto=item.label;
	                        		
	                        		 ticketStarter(optionsKanban);
	                        	 });
                           	 
                             break;
                         case 2:
                        	 $.ajax({
                         		  type: "POST",
                         		  data: {"opt":"52"
                         			 },	  
                         		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
                         		  url: "./POControl_Servlet",
                         		  cache: false,
                         		  dataType: "text",
                         		  success: bbbb
                         		});
                          	 function bbbb(data){
                          		 var aux = JSON.parse(data);
                          		var source =
                                {
                                    datatype: "json",
                                    datafields: [
                                        { name: 'usuario' },
                                        { name: 'nome' }
                                    ],
                                    localData: aux
                                   
                                };
                                var dataAdapter = new $.jqx.dataAdapter(source);
	                        	 tool.jqxDropDownList({ width: 220,height:32,theme:"light", source: dataAdapter,displayMember: "nome", valueMember: "usuario" });
                          	 }
	                        	 tool.on('select', function (event) {
	                        		 var args = event.args;
	                        		 var item = tool.jqxDropDownList('getItem', args.index);
	                        		 optionsKanban.usuario=item.value;
	                        		 
	                        		 ticketStarter(optionsKanban);
	                        	 });
                          	 
                             break;
                         case 3:
                        	 tool.text("Hoje");
                        	 tool.on("click", function () {
                                 var toggled = tool.jqxToggleButton("toggled");
                                 optionsKanban.range="hoje";
                                 
                                 ticketStarter(optionsKanban);
                        	 });
                             break;
                         case 4:
                        	 tool.text("Semana Atual");
                        	 tool.on("click", function () {
                        		 optionsKanban.range="semanaCorrente";
                        		 
                        		 ticketStarter(optionsKanban);
                        	 });
                             break;
                         case 5:
                        	 tool.text("Mês Atual");
                        	 tool.on("click", function () {
                        		 optionsKanban.range="MesCorrente";
                        		
                        		 ticketStarter(optionsKanban);
                        	 });
                             break;
                        
                     }
                 }
             });
         
            
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
				//console.log(response);
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
            $("#input_5_arq").fileinput({
            	language: "pt-BR",
			    uploadUrl: "./RelatoriosServlet?opt=8", // server upload action
			    uploadAsync: false,
			    allowedFileExtensions: ['xlsx'],
			    maxFileCount: 5,
			    uploadExtraData:function (previewId, index) {
			        var obj = {rel_id:$("#temp_id_txt").val()};
			        //obj[0]=$("#cliente_carrega_po").val();
			        //obj[1]=$("#projeto_carrega_po").val()
			        return obj;
			    }
			});
            $("#input_6_arq").fileinput({
            	language: "pt-BR",
			    uploadUrl: "./RelatoriosServlet?opt=10", // server upload action
			    uploadAsync: false,
			    allowedFileExtensions: ['jpeg','jpg','png'],
			    maxFileCount: 5,
			    uploadExtraData:function (previewId, index) {
			        var obj = {item_id:$("#temp_id_txt").val()};
			        //obj[0]=$("#cliente_carrega_po").val();
			        //obj[1]=$("#projeto_carrega_po").val()
			        return obj;
			    }
			});
            
            $("#input_LOGOCUSTUMER").fileinput({
			    uploadUrl: "./POControl_Servlet?opt=9", 
			    uploadAsync: false,
			    allowedFileExtensions: ['png', 'jpg'],
			    maxFileCount: 1,
			    uploadExtraData:function (previewId, index) {
			        var obj = {
			        		nomeCompleto:$("#cliente_nome_completo").val(),
			        		nome:$("#cliente_nome_resumido").val(),
			        		cnpj:$("#cliente_cnpj").val(),
			        		inscEstadual:$("#cliente_ie").val(),
			        		endereco:$("#cliente_endereco").val(),
			        		contato:$("#cliente_telefone").val()
			        		};
			        //obj[0]=$("#cliente_carrega_po").val();
			        //obj[1]=$("#projeto_carrega_po").val()
			        return obj;
			    }
			}).on('filebatchpreupload', function(event, data) {
				
		        if($("#cliente_nome_completo").val()=="" || $("#cliente_nome_completo").val()==null || $("#cliente_nome_resumido").val()=="" || $("#cliente_nome_resumido").val()==null || $("#cliente_cnpj").val()=="" || $("#cliente_cnpj").val()==null){
		        	return {
		                message: "Dados do cliente incompletos", // upload error message
		                data:{} // any other data to send that can be referred in `filecustomerror`
		            };
		        }
		        
		    });
            
            $("#input_PO_file").fileinput({
			    uploadUrl: "./POControl_Servlet?opt=12", 
			    uploadAsync: false,
			    allowedFileExtensions: ['pdf', 'xls', 'xlsx'],
			    maxFileCount: 5,
			    uploadExtraData:function (previewId, index) {
			        var obj = {cliente:$("#cliente_carrega_po").val(),projeto:$("#projeto_carrega_po").val(),rolloutid:$("#select_rollout_carrega_po").val()};
			        //obj[0]=$("#cliente_carrega_po").val();
			        //obj[1]=$("#projeto_carrega_po").val()
			        return obj;
			    }
			}).on('filebatchpreupload', function(event, data) {
				
		        if($("#cliente_carrega_po").val()=="" || $("#cliente_carrega_po").val()==null || $("#projeto_carrega_po").val()=="" || $("#projeto_carrega_po").val()==null || $("#select_rollout_carrega_po").val()=="" || $("#select_rollout_carrega_po").val()==null){
		        	return {
		                message: "Selecione corretamente os dados.", // upload error message
		                data:{} // any other data to send that can be referred in `filecustomerror`
		            };
		        }
		        var rollout_aux=$('#select_rollout_carrega_po').find("option:selected").text();
		        if (!window.confirm("Confirmar Rollout selecionado : " + rollout_aux + "?")) {
		            return {
		                message: "Carregamento Cancelado!", // upload error message
		                data:{} // any other data to send that can be referred in `filecustomerror`
		            };
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
            $("#input_lpu_file").fileinput({
            	language: "pt-BR",
			    uploadUrl: "./POControl_Servlet?opt=56", // server upload action
			    uploadAsync: false,
			    allowedFileExtensions: ['xlsx'],
			    maxFileCount: 1
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
           
            
            
            load_site_markers();
            carrega_select_func_ponto();
            carrega_usuarios();
            carregaSelectOperadora();
            carregaSelectOperadoraExport();
            $('#PO_upload_Modal').on('show.bs.modal', function (event) {
            	atualiza_select_cliente();
            });
            $('#Modal_addTickectEvento').on('show.bs.modal', function (event) {
            	$("#span_ticket_num").html(sessionStorage.getItem("ticket"));
            	 $.ajax({
           		  type: "POST",
           		  data: {"opt":"50",
           			  "ticketnum":sessionStorage.getItem("ticket")
           			 },	  
           		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
           		  url: "./POControl_Servlet",
           		  cache: false,
           		  dataType: "text",
           		  success: exibe_historico
           		});
            	 function exibe_historico(data){
            		 $("#historico_ticket_itens").html(data);
            	 }
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
            
            $('#modal_new_Ticket').on('show.bs.modal', function (event) {
            	$.ajax({
			 		  type: "POST",
			 		  data: {
			 			  		"opt":"42"
			 				},		  
			 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			 		  url: "./POControl_Servlet",
			 		  cache: false,
			 		  dataType: "text",
			 		 success: atualiza_select_PO_ticket
			 		});
            	function atualiza_select_PO_ticket(data){
            		//alert(data);
            		$("#po_select_ticket").html(data);
            		$('#po_select_ticket').selectpicker();
            		$('#po_select_ticket').selectpicker('refresh');
            	}
            });
            $("#from_ticket").jqxDateTimeInput({selectionMode: 'range'});
            $('#modal_cria_orcamento').on('show.bs.modal', function (event) {
            	inicializaTabelaOrcamento();
            	atualiza_select_cliente();
            	CarregaSelectCategorias();
            });
            $('#Modal_AddItemLPU').on('show.bs.modal', function (event) {
            	
            	CarregaSelectCategorias();
            });
            $('#modal_atualiza_tickets').on('show.bs.modal', function (event) {
            	$("#replanTickect").jqxDateTimeInput({formatString:'dd/MM/yyyy',width:250,height:30,culture: 'pt-BR',theme: 'bootstrap',selectionMode: 'range'});
            	$("#jqxCheckBox_select_func_novo_tickect_id").jqxCheckBox({ width: 200, height: 25});
            	$("#jqxCheckBox_replanTickect").jqxCheckBox({ width: 200, height: 25});
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
		 $('#tabela_assinaturas').DataTable({
				"searching": true,
			    "ordering": false,
			    "lengthChange": false,
			    "processing": true,
		        "ajax": './POControl_Servlet?opt=37',
		        "lengthMenu": [10,20]
			});
		
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
	    				$('#tabela_assinaturas').DataTable().ajax.reload();
	    				//inicia_assinatura2();
	    				
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
		 busca_rollouts();
		 if(geral.perfil.search("AssinaturaManager")>=0){
			 document.getElementById('assinatura_menu_div').style.display = "block";
		 }
		 
		 
	 });
}
function menu(opt){
	 if(opt=="ItemPO" || opt=="portal" || opt=="senha" || opt=="usuarios_ponto" || opt=="usuarios_banco_hh" || opt=="mstp_mobile_report_diario" || opt=="rel_ponto_kpi" || opt=="usuarios_ponto_analise"){
		 $(".janelas").hide();
	     document.getElementById(opt).style.display = "block";
	}else if(opt=="ticketWindow"){
		$(".janelas").hide();
	     document.getElementById(opt).style.display = "block";
	     var ready = $('#TicketKanban').jqxKanban('ready');
			if(ready){
			}else{
				optionsKanban=JSON.parse("{}");
				optionsKanban.usuario="ALL";
				ticketStarter(optionsKanban);
			}
	     
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
	}else if(opt=="orcamento" && geral.perfil.search("OrcamentoManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carregaTabelaOrcamentos();
	}else if(opt=="orcamento" && geral.perfil.search("OrcamentoView")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="lpu" && geral.perfil.search("LPUManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_LPU();
	}else if(opt=="lpu" && geral.perfil.search("LPUView")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="rollout" && geral.perfil.search("RolloutView")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
	}else if(opt=="checklist_review" && geral.perfil.search("RolloutManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_checklist();
	}else if(opt=="regras_rollout" && geral.perfil.search("RolloutManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carregaRegras();
	}else if(opt=="PO" && geral.perfil.search("POManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_PO(0);
	}else if(opt=="campos_rollout" && geral.perfil.search("RolloutManager")>=0){
		$(".janelas").hide();
		document.getElementById(opt).style.display = "block";
		carrega_tabela_campos_rollout();
	}else if(opt=="importa_rollout" && geral.perfil.search("RolloutManager")>=0){
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