

function ticketStarter(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"45"},
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: carrega_kabam
		});
	function carrega_kabam(data){
		var data_kabam=JSON.parse(data);
		var fields_kanbam = [
	        { name: "id", type: "string" },
	        { name: "status", map: "state", type: "string" },
	        { name: "text", map: "label", type: "string" },
	        { name: "content", type: "string" },
	        { name: "tags", type: "string" },
	        { name: "color", map: "hex", type: "string" },
	        { name: "resourceId", type: "number" }
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
	         columns: [
	             { text: "Abertos", dataField: "NEW", maxItems: 15 },
	             { text: "Em Execução", dataField: "work", maxItems: 15 },
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
	             //console.log(element);
	             var drop_button_item=$("<div style='float: left;' id='dropDownButton_tickect_"+item.id+"'><div style='border: none;' id='jqxTree_tickect_"+item.id+"'><ul><li><a style='float: left; margin-left: 5px;' href='#' onclick=addEventosTicket('"+item.id+"')>Adicionar Evento</a></li><li><a style='float: left; margin-left: 5px;' href='#' onclick=evolucaoTicket('"+item.id+"')>Adicionar Evolução</a></li></ul></div></div>");
	             var label_progresso=$("<div style='float: right; margin-top:0px;vertical-align: top;'><h2 style='color:gray;margin-top:0px;vertical-align: top;'>0%</h2></div>");
	             var barra = $("<div id='barra_op_"+item.id+"'></div>");
	             label_progresso.appendTo(barra);
	             drop_button_item.appendTo(barra);
	             
	             $(element).find(".jqx-kanban-item-text").append(barra);
	             drop_button_item.jqxDropDownButton({ width: 150, height: 25});
	             $("#jqxTree_tickect_"+item.id).jqxTree({ width: 200});
	             $(element).find("#expanderTicket_"+item.id).jqxExpander({ width: '350px', height: '200px',expanded: false,theme:"light"});
	             
	         }
	     });
	}
	$('.jqxExpanderTicketClass').on('expanded', function () { $('.jqxExpanderTicketClass').jqxExpander('refresh'); }); 
}

function addEventosTicket(ticketId){
	sessionStorage.setItem("ticket",ticketId);
	$('#Modal_addTickectEvento').modal('show');
	
}
function registraEventosTicket(){
	var d = new Date()
	var func,atividade,gap,owner,desc,ticketnum;
	ticketnum=sessionStorage.getItem("ticket");
	func = $('#func_ticket_evento').val();
	atividade = $('#atividade_eventoTicket').val();
	gap = $('#gap_eventoTicket').val();
	owner = $('#owner_eventoTicket').val();
	desc = $('#mensagem_input_ticket').val();
	alert("ticket_num:"+sessionStorage.getItem("ticket")+"| func: "+func+"| atividade"+atividade+"|gap"+gap);
	 $.ajax({
		  type: "POST",
		  data: {"opt":"49",
			  "ticket_num":ticketnum.toString(),
			  "func":func.toString(),
			  "atividade":atividade.toString(),
			  "gap":gap.toString(),
			  "owner":owner.toString(),
			  "desc":desc.toString()},	  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: atualiza_historico
		});
	function  atualiza_historico (data){
		$("#historico_ticket_itens").prepend("<li><label style='color:gray;'>Data do Evento:"+ moment(d).format('LLL')+" <br>Categorias: "+ atividade+" | "+gap +" | "+owner+" <br>Registro: "+desc+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+moment(d).format('LLL')+" por "+geral.usuario+"</label></li>");
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
		 console.log(rowindexes);
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
			 console.log(data);
			 conteudo_ticket=conteudo_ticket+"<br>Site: "+data.SITE+"<br>Descrição:"+ data.ITEM + "<br>Quantidade: "+data.QTDE+"<br>";
			 tags=data.PROJETO;
			 quantidade_po=data.QTDE - data.QTDE_TICKET;
			 recurso = $('#select_func_tickect').val();
			 if (recurso=="" || recurso==null){
				 $.alert("Selecione o Responsável do Ticket");
				 return;
			 }
			 $.ajax({
				  type: "POST",
				  data: {"opt":"44",
					  "po_num":data.PO_NUM,
					  "ticket":ticket,
					  "item":data.ITEM,
					  "item_code":data.ITEM_CODE,
					  "po_line":data.PO_LINE,
					  "published":data.PUBLISHED,
					  "site":data.SITE,
					  "projeto":data.PROJETO,
					  "tags":tags,
					  "recurso":recurso,
					  "incio_planejado_bl":moment(selection.from).format('L'),
					  "fim_planejado_bl":moment(selection.to).format('L'),
					  "todos_itens":todas_linhas,
					  "quantidade_itens":data.QTDE_TICKET,
					  "quantidade_pos_restante":quantidade_po},		  
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