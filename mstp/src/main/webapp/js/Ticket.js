

function ticketStarter(options){
	$.ajax({
		  type: "POST",
		  data: {"opt":"45","options":JSON.stringify(options)},
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: carrega_kabam
		});
	function carrega_kabam(data){
		var data_kabam=JSON.parse(data);
		
		//console.log(options);
		//console.log(data_kabam);
		var ready = $('#TicketKanban').jqxKanban('ready');
		if(ready){
			$('#TicketKanban').unbind();
			$('#TicketKanban').jqxKanban('destroy');
			$('#ticketWindow').append($("<div id='TicketKanban'></div>"));
		}
		var fields_kanbam = [
	        { name: "id", type: "string" },
	        { name: "status", map: "state", type: "string" },
	        { name: "text", map: "label", type: "string" },
	        { name: "content", type: "string" },
	        { name: "tags", type: "string" },
	        { name: "color", map: "hex", type: "string" },
	        { name: "resourceId", type: "string" }
        ];
		var source_kanbam =
	    {
	        localData: data_kabam.source,
	        dataType: "array",
	        dataFields: fields_kanbam
	    };
		var dataAdapter_kanbam= new $.jqx.dataAdapter(source_kanbam);
		var resourcesAdapterFunc_kanbam = function () {
	         var resourcesSource_kanbam =
	         {
	             localData: data_kabam.recursos,
	             dataType: "array",
	             dataFields: [
	                  { name: "id", type: "string" },
	                  { name: "name", type: "string" }
	                 
	             ]
	         };
	         var resourcesDataAdapter_kanbam = new $.jqx.dataAdapter(resourcesSource_kanbam);
	         return resourcesDataAdapter_kanbam;
	     };
	    
	     $('#TicketKanban').jqxKanban({
			 template: "<div class='jqx-kanban-item' id=''>"
	             + "<div class='jqx-kanban-item-color-status'></div>"
	             + "<div style='display: none;' class='jqx-kanban-item-avatar'></div>"
	             + "<div class='jqx-icon jqx-icon-close-white jqx-kanban-item-template-content jqx-kanban-template-icon'></div>"
	             + "<div class='jqx-kanban-item-text'></div>"
	             + "<div class='jqx-kanban-item-content'></div>"
	             + "<div class='jqx-kanban-item-footer'></div>"
	     + "</div>",
	         width: '100%',
	         height: '90%',
	         theme:'light',
	         resources: resourcesAdapterFunc_kanbam(),
	         source: dataAdapter_kanbam,
	         ready: function(){
	        	 return true;
	         },
	         columns: [
	             { text: "Abertos", dataField: "NEW", maxItems: 15 },
	             { text: "Em Execução", dataField: "work", maxItems: 15 },
	             { text: "Agendamento de Qualidade", dataField: "precheck", maxItems: 15 },
	             { text: "Análise de Qualidade", dataField: "check", maxItems: 15 },
	             { text: "Finalizados", dataField: "done", maxItems: 15 }
	         ],
	         
	         columnRenderer: function (element, collapsedElement, column) {
	             var columnItems = $("#TicketKanban").jqxKanban('getColumnItems', column.dataField).length;
	             element.find(".jqx-kanban-column-header-status").html("(" + columnItems + "/" + column.maxItems + ")");
	             collapsedElement.find(".jqx-kanban-column-header-status").html("(" + columnItems + "/" + column.maxItems + ")");
	         },
	         itemRenderer: function(element, item, resource)
	         {
	             $(element).find(".jqx-kanban-item-color-status").html(resource.name);
	             //console.log(item);
	             var drop_button_item=$("<div style='float: left;' id='dropDownButton_tickect_"+item.id+"'><div style='border: none;' id='jqxTree_tickect_"+item.id+"'><ul><li><a style='float: left; margin-left: 5px;' href='#' onclick=addEventosTicket('"+item.id+"')>Adicionar Evento</a></li><li><a style='float: left; margin-left: 5px;' href='#' onclick=evolucaoTicket('"+item.id+"')>Adicionar Evolução</a></li><li><a style='float: left; margin-left: 5px;' href='#' onclick=registraTicketNumToUpdate('"+item.id+"') data-toggle='modal' data-target='#modal_atualiza_tickets'>Editar Ticket</a></li></ul></div></div>");
	             var label_progresso=$("<div id='div_lbl_progress_tickect_"+item.id+"' style='float: right; margin-top:0px;vertical-align: top;'><label id='lbl_progress_tickect_"+item.id+"' style='font-size:24px;color:gray;margin-top:0px;vertical-align: top;'>0%</label></div>");
	             var barra = $("<div id='barra_op_"+item.id+"'></div>");
	             label_progresso.appendTo(barra);
	             drop_button_item.appendTo(barra);
	             //console.log(item);
	             $(element).find(".jqx-kanban-item-text").append(barra);
	             drop_button_item.jqxDropDownButton({ width: 150, height: 25});
	             $("#jqxTree_tickect_"+item.id).jqxTree({ width: 200});
	             $(element).find(".jqx-kanban-item-content").html($(item.content));
	             $(element).find("#expanderTicket_"+item.id).jqxExpander({  
	            	 	initContent: function () {
	            	 		$('#expanderTicket_'+item.id).jqxExpander({height: '150px'});
	            	 		$('.jqxExpanderTicketClass').jqxExpander('refresh'); 
	            	 		$('#expanderTicket_'+item.id).on('expanding', function () { $('#expanderTicket_'+item.id).jqxExpander({height: '150px'}); }); 
	            	 		$('#expanderTicket_'+item.id).on('collapsing', function () {$('#expanderTicket_'+item.id).jqxExpander({height: '50px'}); }); 
	            	 	},
	            	 	width: '300px',
	            	 	height: '50px',
	            	 	expanded: false,
	            	 	theme:"light"
	            	 		}
	             );
	             $(element).find("#expanderTicketChecklist_"+item.id).jqxExpander({ 
	            	 initContent: function () {
	            		 $('.jqxExpanderTicketClass').jqxExpander('refresh');
	            		 if(item.status!="NEW"){
	            			 $.ajax({
	            	       		  type: "POST",
	            	       		  data: {"opt":"55","ticket":item.id},	  
	            	       		  url: "./POControl_Servlet",
	            	       		  cache: false,
	            	       		  dataType: "text",
	            	       		success: carrega_checklist
	            	       		});
	            			 function carrega_checklist(data){
	            				 var aux=JSON.parse(data);
	            				 //console.log(aux);
	            				 for(var indice=0;indice<aux.length;indice++){
	            					 if(aux[indice]['nome']=='TICKET_MOS_DATE'){
	            						 $('#jqxCheckBoxMOS_'+item.id).jqxCheckBox({ width: 260, height: 25, checked: true,disabled: true});
	            						 $('#jqxCheckBoxMOS_'+item.id).children('span')[0].innerHTML = 'MOS - ' + aux[indice]['valor'];
	            					 }else if(aux[indice]['nome']=='TICKET_SITESIGN'){
	            						 $('#jqxCheckBoxSiteSign_'+item.id).jqxCheckBox({ width: 260, height: 25, checked: true,disabled: true});
	            						 $('#jqxCheckBoxSiteSign_'+item.id).children('span')[0].innerHTML = 'Site Sign - ' + aux[indice]['valor'];
	            					 }else if(aux[indice]['nome']=='TICKET_SITEVERIFY'){
	            						 $('#jqxCheckBoxSiteVerify_'+item.id).jqxCheckBox({ width: 260, height: 25, checked: true,disabled: true});
	            						 $('#jqxCheckBoxSiteVerify_'+item.id).children('span')[0].innerHTML = 'Site Verify - ' + aux[indice]['valor'];
	            					 }else if(aux[indice]['nome']=='TICKET_MOS_WH_OK'){
	            						 $('#jqxCheckBoxMOSWH_'+item.id).jqxCheckBox({ width: 260, height: 25, checked: true,disabled: true});
	            						 $('#jqxCheckBoxMOSWH_'+item.id).children('span')[0].innerHTML = 'MOS no WhareHouse - ' + aux[indice]['valor'];
	            					 }else if(aux[indice]['nome']=='TICKET_MOS_SITE_OK'){
	            						 $('#jqxCheckBoxMOSSITE_'+item.id).jqxCheckBox({ width: 260, height: 25, checked: true,disabled: true});
	            						 $('#jqxCheckBoxMOSSITE_'+item.id).children('span')[0].innerHTML = 'MOS no Site - ' + aux[indice]['valor'];
	            					 }else if(aux[indice]['nome']=='TICKET_EHS_OK'){
	            						 $('#jqxCheckBoxEHS_'+item.id).jqxCheckBox({ width: 260, height: 25, checked: true,disabled: true});
	            						 $('#jqxCheckBoxEHS_'+item.id).children('span')[0].innerHTML = 'EHS OK - ' + aux[indice]['valor'];
	            					 }else if(aux[indice]['nome']=='TICKET_QC_DATE'){
	            						 $('#jqxCheckBoxQCagendado_'+item.id).jqxCheckBox({ width: 260, height: 25, checked: true,disabled: true});
	            						 $('#jqxCheckBoxQCagendado_'+item.id).children('span')[0].innerHTML = 'QC Agendado - ' + aux[indice]['valor'];
	            					 }
	            				 }
	            			 }
	            		 }
	            		 
	    	             
	    	            // $('#jqxCheckBox2_'+item.id).on('change', function (event) {
	    	            //	 var checked = event.args.checked;
	    	            	 //console.log(event);
	    	            //     if (checked) {
	    	            //    	 document.getElementById('lbl_progress_tickect_'+item.id).innerHTML = '30%';
	    	           //     }
	    	            //     else {
	    	           //     	 $(element).find('#lbl_progress_tickect_'+item.id).hmtl("<h2 style='color:gray;margin-top:0px;vertical-align: top;'>2%</h2>");
	    	            //     }
	    	            // });
	            		 $('#expanderTicketChecklist_'+item.id).jqxExpander({height: '150px'});
	            		 $('#expanderTicketChecklist_'+item.id).on('expanding', function () { $('#expanderTicketChecklist_'+item.id).jqxExpander({height: '150px'}); }); 
	            	 	 $('#expanderTicketChecklist_'+item.id).on('collapsing', function () {$('#expanderTicketChecklist_'+item.id).jqxExpander({height: '50px'}); }); 
	            	  },
	            	  width: '300px',
	            	  height: '50px',
	            	  expanded: false,
	            	  theme:"light"
	            		 }
	             );
	            
	             if(item.status=="NEW"){
	            	 $(element).find(".jqx-kanban-item-text").css('background', "#E7E6E5");
	            	 $(element).find(".jqx-kanban-item-content").css('background', "#E7E6E5");
	             }else if(item.status=="work"){
	            	 $(element).find(".jqx-kanban-item-text").css('background', "#F5F112");
	            	 $(element).find(".jqx-kanban-item-content").css('background', "#F5F112");
	             }else if(item.status=="check"){
	            	 $(element).find(".jqx-kanban-item-text").css('background', "#F58712");
	            	 $(element).find(".jqx-kanban-item-content").css('background', "#F58712");
	             }else if(item.status=="done"){
	            	 $(element).find(".jqx-kanban-item-text").css('background', "#82b74b");
	            	 $(element).find(".jqx-kanban-item-content").css('background', "#82b74b");
	             }
	         }
	     });
	     
	     $('#TicketKanban').on('itemMoved', function (event) {
             var args = event.args;
             var itemId = args.itemId;
             var oldParentId = args.oldParentId;
             var newParentId = args.newParentId;
             var itemData = args.itemData;
             var oldColumn = args.oldColumn;
             var newColumn = args.newColumn;
             sessionStorage.setItem("ItemMovedId",itemData.id);
             if(newColumn.dataField=="work"){
            	 $('#modal_work_ticket').modal('show')
             }
             if(newColumn.dataField=="precheck"){
            	 $('#modal_dt_agendamento_qc').modal('show')
             }

             $.ajax({
       		  type: "POST",
       		  data: {"opt":"46","id_":itemData.id,"status_":newColumn.dataField},	  
       		  url: "./POControl_Servlet",
       		  cache: false,
       		  dataType: "text"
       		});
             
             var detalhes = JSON.parse("{}");
             detalhes.ticketnum=itemData.id;
             detalhes.tipo="ItemMovidoManualmente";
             
     		detalhes.desc = "Item movido de "+oldColumn.text+" para "+newColumn.text+ " por "+geral.usuario;
     		
     		registraEventosTicket(detalhes);
         });
	}
	
}

function addEventosTicket(ticketId){
	sessionStorage.setItem("ticket",ticketId);
	$('#Modal_addTickectEvento').modal('show');
	
}
function registraEventosTicket(detalhes){
	var d = new Date();
	if(detalhes){
	}else{
		detalhes = JSON.parse("{}");
		detalhes.ticketnum=sessionStorage.getItem("ticket");
		detalhes.tipo="RegistroManualEventos";
		detalhes.func = $('#func_ticket_evento').val();
		detalhes.atividade = $('#atividade_eventoTicket').val();
		detalhes.gap = $('#gap_eventoTicket').val();
		detalhes.owner = $('#owner_eventoTicket').val();
		detalhes.desc = $('#mensagem_input_ticket').val();
	}
	//console.log(detalhes);
	$.ajax({
		  type: "POST",
		  data: {"opt":"49",
			  "dados":JSON.stringify(detalhes)
			  },	  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: atualiza_historico
		});
	function  atualiza_historico (data){
		if(detalhes.tipo=="RegistroManualEventos"){
			$("#historico_ticket_itens").prepend("<li><label style='color:gray;'>Data do Evento:"+ moment(d).format('LLL')+" <br>Categorias: "+ detalhes.atividade+" | "+detalhes.gap +" | "+detalhes.owner+" <br>Registro: "+detalhes.desc+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+moment(d).format('LLL')+" por "+geral.usuario+"</label></li>");
		}
	}
}


function registraTicketNumToUpdate(ticket){
	sessionStorage.setItem("ticketNumToUpdate",ticket);
}

function UpdateTicket(){
	var mudancas = JSON.parse("{}");
	var selection=$('#replanTickect').jqxDateTimeInput('getRange');
	mudancas.ticket = sessionStorage.getItem("ticketNumToUpdate");
	var selectnovofunc = $("#jqxCheckBox_select_func_novo_tickect_id").val();
	var selectnovoplan = $("#jqxCheckBox_replanTickect").val();
	if(selectnovofunc){
		mudancas.name = $('#select_func_novo_tickect_id').val();
	}
	if(selectnovoplan){
		mudancas.from = moment(selection.from).format('L');
		mudancas.to=moment(selection.to).format('L');
	}
	if(!selectnovofunc){
		if(!selectnovoplan){
			alert("Sem mudanças para registrar - operação abortada.");
			return;
		}
	}
	$.ajax({
		  type: "POST",
		  data: {"opt":"53","mudancas":JSON.stringify(mudancas)},	  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success:mudatickect
		});
      function mudatickect(data){
    	  if(selectnovofunc){
		      	var items = $('#TicketKanban').jqxKanban('getItems');
		      	//console.log(items);
		      	//console.log(items.length);
		      	for(var i=0;i<items.length;i++){
		      		//console.log(items[i].id + " | "+ ticket);
		      		if(items[i].id==mudancas.ticket){
		      			//console.log("verdadeiro");
		      			var newContent = { text: items[i].text, content: items[i].content, tags: items[i].tags, color: items[i].color, resourceId: mudancas.name, className: "" };
		      			//console.log(newContent);
		      			$('#TicketKanban').jqxKanban('updateItem', items[i].id, newContent); 
		      			break;
		      		}
		      	}
		      	var detalhes=JSON.parse("{}");
		      	detalhes.ticketnum=mudancas.ticket;
		      	detalhes.tipo="MudancaResponsavel";
		      	detalhes.desc="Ticket atribuído para "+mudancas.name+", por "+geral.usuario+".";
		      	registraEventosTicket(detalhes);
    	  }
    	  if(selectnovoplan){
    		  detalhes.tipo="ReplanejamentoDatas";
		      detalhes.desc="Ticket replanejado para "+mudancas.from+" até "+mudancas.to+" por "+ geral.usuario;
		      registraEventosTicket(detalhes);
    	  }
      	sessionStorage.removeItem("ticketNumToUpdate");
      }
	
}
function atualiza_gap(){
	var atividade = $('#atividade_eventoTicket').val();
	var options;
	switch (atividade){
		case"PO":
			options="<option value='atrasado'>Pedido Atrasado</option><option value='itens_incorretos'>Itens Incorretos</option><option value='val_incorretos'>Valores incorretos</option>";
			break;
		case"Acesso":
			options="<option value='atrasado'>Não Solicitado</option><option value='itens_incorretos'>Rejeitado</option><option value='val_incorretos'>Em aprovação</option>";
			break;
	default:
		options="<option>Selecione uma atividade</option>"
	}
	
	$('#gap_eventoTicket').html(options);
	$('#gap_eventoTicket').selectpicker('refresh');
}
function updateStatusTicket(itemdata){
	//console.log(itemdata);
	 $.ajax({
		  type: "POST",
		  data: {"opt":"46","id_":itemdata.id,"status_":itemdata.status},	  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text"
		});
}
function add_Ticket(){
		
		 var ticket=Date.now();
		 var rowindexes = $('#grid_item_po_ticket').jqxGrid('getselectedrowindexes');
		 var rows = $('#grid_item_po_ticket').jqxGrid('getrows');
		 var contador=$("<div id='contadorshow_"+ticket+"' style='display:block;float:right;font-size:18px'></div>");
		 var conteudo2=$("<div id='conteudo_"+ticket+"'></div>");
		 conteudo2.append(contador);
		 //console.log(rowindexes);
		 var conteudo_ticket = "<label>Ticket - " + ticket+"</label>";
		 var data,tags,recurso;
		 var selection=$('#from_ticket').jqxDateTimeInput('getRange');
		 var todas_linhas;
		 if(rows.length==rowindexes.length){
			 todas_linhas="Y";
		 }else{
			 todas_linhas="N";
		 }
		 var quantidade_po;
		 for(i=0;i<rowindexes.length;i++){
			 data = $('#grid_item_po_ticket').jqxGrid('getrowdata', rowindexes[i]);
			 //console.log(data);
			 conteudo_ticket=conteudo_ticket+"<br>Site: "+data.SITE+"<br>Descrição:"+ data.ITEM + "<br>Quantidade: "+data.QTDE+"<br>";
			 tags=data.PROJETO;
			 quantidade_po=data.QTDE - data.QTDE_TICKET;
			 recurso = $('#select_func_tickect').val();
			 if (recurso=="" || recurso==null){
				 $.alert("Selecione o Responsável do Ticket");
				 return;
			 }
			 var newticket=JSON.parse("{}");
			 newticket.po_num = data.PO_NUM;
			 newticket.ticket =ticket.toString();
			 newticket.item=data.ITEM;
			 newticket.item_code=data.ITEM_CODE;
			 newticket.po_line=data.PO_LINE;
			 newticket.published=data.PUBLISHED;
			 newticket.site = data.SITE;
			 newticket.projeto = data.PROJETO;
			 newticket.tags = tags;
			 newticket.recurso = recurso;
			 newticket.incio_planejado_bl = moment(selection.from).format('L');
			 newticket.fim_planejado_bl = moment(selection.to).format('L');
			 newticket.todos_itens=todas_linhas.toString();
			 newticket.quantidade_itens = data.QTDE_TICKET.toString();
			 newticket.quantidade_pos_restante = quantidade_po.toString();
			 alert(JSON.stringify(newticket));
			 $.ajax({
				  type: "POST",
				  data: {"opt":"44",
					  "ticket":JSON.stringify(newticket)},		  
				  //url: "http://localhost:8080/DashTM/D_Servlet",	  
				  url: "./POControl_Servlet",
				  cache: false,
				  dataType: "text",
				  success: atualiza_kabam
				});
			function atualiza_kabam(dados){
				
				 carrega_grid_poItem(data.PO_NUM);
			}
		 }
		 $('#TicketKanban').jqxKanban('addItem', { status: "NEW", text: conteudo_ticket , content:conteudo2, tags: "Novo" , color: "#5dc3f0",tags:data.PROJETO,resourceId: recurso });
		 //timer = new easytimer.Timer();
         //timer.start();
         //timer.addEventListener('secondsUpdated', function (e) {
         //    $('#contadorshow_'+ticket).html(timer.getTimeValues().toString());
         //});
		 
     
}