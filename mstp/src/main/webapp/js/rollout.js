function atualiza_dash_rollout(){
	
	filtros={"filtros":[]};
	 var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
	var rolloutid;
	 if (item != null) {
		 rolloutid=item.value;
		 if(rolloutid=="Rollout"){
			 return;
		 }
	 }else{
		 rolloutid="Rollout1";
	 }
	var aux={};
	var filtersinfo = $('#jqxgrid').jqxGrid('getfilterinformation');
	if(filtersinfo){
	for(var j=0;j<filtersinfo.length;j++){
    	for (var i = 0; i < filtersinfo[j].filter.getfilters().length; i++) {
        	aux.filtersvalue=filtersinfo[j].filter.getfilters()[i].value;
        	aux.filtercondition=filtersinfo[j].filter.getfilters()[i].condition;
        	aux.datafield = filtersinfo[j].filtercolumn;
        	
        	filtros.filtros.push(aux);
        	aux={};
    	}
    	
    }
	}
	$.ajax({
		  type: "POST",
		  data: {"opt":"29",
			  "filtros":JSON.stringify(filtros),"rolloutid":rolloutid},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		  success: atualiza_dash
		});
	function atualiza_dash(data){
		//alert(data);
		aux=JSON.parse(data);
		if(geral.empresa_id==1){
			$('#quadro_1_1').html("Total Work Date");
		}else{
			$('#quadro_1_1').html("Total Atividades");
		}
		$('#quadro_1_2').html(aux.quadro_1_2);
		$('#quadro_2_2').html(aux.quadro_2_2);
		$('#quadro_3_2').html(aux.quadro_3_2);
		$('#quadro_4_2').html(aux.quadro_4_2);
		$('#quadro_5_2').html(aux.quadro_5_2);
	}
	
}
function carrega_tree_rollout(){
	var timestamp = Date.now();
	 $("#splitter").jqxSplitter({ width: '100%', theme:'light', height: '90%', panels: [{ size: 250}] });
	$.getJSON('./RolloutServlet?opt=27&_='+timestamp, function(data) {
		var source =
        {
            datatype: "json",
            datafields: [
                { name: 'id' },
                { name: 'parentid' },
                { name: 'text' },
                { name: 'value' }
            ],
            id: 'id',
            localdata: data
        };
		 var dataAdapter = new $.jqx.dataAdapter(source);
		 dataAdapter.dataBind();
		 var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{ name: 'text', map: 'label'}]);
         $('#jqxTree_rollout').jqxTree({  theme:'light',source: records, hasThreeStates: true, checkboxes: true,height: '100%', width: '100%'});
         $('#jqxTree_rollout').jqxTree('expandAll');
         $("#jqxTree_rollout").jqxTree('selectItem', $("#Rollout1")[0]);
         $('#jqxTree_rollout').on('select',function (event)
        		 {
        	       $('#jqxgrid').jqxGrid('destroy');
        	       $('#container_rollout').html('<div id="jqxgrid"></div>')
        	 		carrega_gant();
        		 });
         var contextMenu = $("#jqxMenu_tree_rollout").jqxMenu({  theme:'light',width: '120px',  height: '56px', autoOpenPopup: false, mode: 'popup' });
         var clickedItem = null;
         var attachContextMenu = function () {
             // open the context menu when the user presses the mouse right button.
             $("#jqxTree_rollout li").on('mousedown', function (event) {
                 var target = $(event.target).parents('li:first')[0];
                 var rightClick = isRightClick(event);
                 if (rightClick && target != null) {
                     $("#jqxTree_rollout").jqxTree('selectItem', target);
                     var scrollTop = $(window).scrollTop();
                     var scrollLeft = $(window).scrollLeft();
                     contextMenu.jqxMenu('open', parseInt(event.clientX) + 5 + scrollLeft, parseInt(event.clientY) + 5 + scrollTop);
                     return false;
                 }
             });
         }
         attachContextMenu();
         $("#jqxMenu_tree_rollout").on('itemclick', function (event) {
             var item = $.trim($(event.args).text());
             switch (item) {
                 case "Renomear":
                     var selectedItem = $('#jqxTree_rollout').jqxTree('selectedItem');
                     if (selectedItem != null) {
                    	 $('#modal_atualiza_no_rollout').modal('show');
                         attachContextMenu();
                     }
                     break;
                 case "Remove Item":
                     var selectedItem = $('#jqxTree_rollout').jqxTree('selectedItem');
                     if (selectedItem != null) {
                         $('#jqxTree_rollout').jqxTree('removeItem', selectedItem.element);
                         attachContextMenu();
                     }
                     break;
             }
         });
         $(document).on('contextmenu', function (e) {
             if ($(e.target).parents('.jqx-tree').length > 0) {
                 return false;
             }
             return true;
         });
         function isRightClick(event) {
             var rightclick;
             if (!event) var event = window.event;
             if (event.which) rightclick = (event.which == 3);
             else if (event.button) rightclick = (event.button == 2);
             return rightclick;
         }
	});
}
function atualiza_nome_rollout(){
	var nome_rollout = document.getElementById("novo_nome_rollout").value;
	
	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
	 var rolloutid;
	 if (item != null) {
		 rolloutid=item.value;
		 if(nome_rollout==""){$.alert("Nome do rollout nao pode ser vazio!")}else{
		 $.ajax({
			  type: "POST",
			  data: {"opt":"30","rolloutid":rolloutid,"novo_nome":nome_rollout},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./RolloutServlet",
			  cache: false,
			  dataType: "text",
			  success: onSuccess30_noatualizado
			});
		function onSuccess30_noatualizado(data){
			carrega_tree_rollout();
			$.alert({type:"green",content:"Rollout nome atualzado"});
		}
		 }
	 }else{
		 $.alert("Não identificamos o rollout selecionado!")
	 }
	
}
function carrega_tree_rollout_conf(){
	var timestamp = Date.now();
	 
	$.getJSON('./RolloutServlet?opt=27&_='+timestamp, function(data) {
		var source =
        {
            datatype: "json",
            datafields: [
                { name: 'id' },
                { name: 'parentid' },
                { name: 'text' },
                { name: 'value' }
            ],
            id: 'id',
            localdata: data
        };
		 var dataAdapter = new $.jqx.dataAdapter(source);
		 dataAdapter.dataBind();
		 var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{ name: 'text', map: 'label'}]);
         $('#jqxTree_rollout_conf').jqxTree({ source: records, hasThreeStates: true, checkboxes: true,height: '450px', width: '300px'});
         
         //$('#jqxTree_rollout_conf').jqxTree({height: '450px', width: '300px'});
 		$('#AddBefore').jqxButton({ height: '30px', width: '120px'});
         $('#AddAfter').jqxButton({ height: '30px', width: '120px'});
         $('#Remove').jqxButton({ height: '30px', width: '120px'});
         $('#Update').jqxButton({ height: '30px', width: '120px'});
         $('#SaveTree').jqxButton({ height: '30px', width: '370px'});
         $('#AddBefore').click(function () {
             var selectedItem = $('#jqxTree_rollout_conf').jqxTree('selectedItem');
             if (selectedItem != null) {
                 $('#jqxTree_rollout_conf').jqxTree('addBefore', { label: 'Item' }, selectedItem.element, false);
                 // update the tree.
                 $('#jqxTree_rollout_conf').jqxTree('render');
             }
         });
         $('#AddAfter').click(function () {
             var selectedItem = $('#jqxTree_rollout_conf').jqxTree('selectedItem');
             if (selectedItem != null) {
                 $('#jqxTree_rollout_conf').jqxTree('addAfter', { label: 'Item' }, selectedItem.element, false);
                 // update the tree.
                 $('#jqxTree_rollout_conf').jqxTree('render');
             }
         });
         $("#input_item_tree_conf").jqxInput({placeHolder: "Insira novo nome", height: 30, width: 250, minLength: 1});
         $('#Update').click(function () {
             var selectedItem = $('#jqxTree_rollout_conf').jqxTree('selectedItem');
             if (selectedItem != null) {
                 $('#jqxTree_rollout_conf').jqxTree('updateItem', { label: $('#input_item_tree_conf').jqxInput('val') }, selectedItem.element);
                 // update the tree.
                 $('#jqxTree_rollout_conf').jqxTree('render');
             }
         });
         $('#Remove').click(function () {
             var selectedItem = $('#jqxTree_rollout_conf').jqxTree('selectedItem');
             if (selectedItem != null) {
                 // removes the selected item. The last parameter determines whether to refresh the Tree or not.
                 // If you want to use the 'removeItem' method in a loop, set the last parameter to false and call the 'render' method after the loop.
                 $('#jqxTree_rollout_conf').jqxTree('removeItem', selectedItem.element, false);
                 // update the tree.
                 $('#jqxTree_rollout_conf').jqxTree('render');
             }
         });
	});
}

function corrigerolloutspazio() {
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"28"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess28_spazio
		});
	function onSuccess28_spazio(data){
		$.alert("ajuste finalizado")
	}
}
function busca_rollouts(){
	//alert("testando campos");
	if(sessionStorage.getItem("rolloutsid")){
		//alert("usando dados offline");
		var aux=sessionStorage.getItem("rolloutsid_"+geral.empresa_id);
			$('#select_rollout_campos').html(aux);
			$('#select_rollout_campos').selectpicker();
			$('#select_rollout_campos').selectpicker('refresh');
	}else{
		//alert("buscando rollouts no banco");
		$.ajax({
			  type: "POST",
			  data: {"opt":"31"},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./RolloutServlet",
			  cache: false,
			  dataType: "text",
			  success: atualiza_select_rollout
			});
		function atualiza_select_rollout(data){
			//alert(data);
			$('#select_rollout_campos').html(data);
			$('#select_rollout_campos').selectpicker();
			$('#select_rollout_campos').selectpicker('refresh');
			sessionStorage.setItem("rolloutsid_"+geral.empresa_id, data);
		}
	}
}
function carrega_tabela_campos_rollout(rollout) {
	
	document.getElementById("rolloutid_campos_conf").value=rollout;
	if(rollout==null || rollout==""){
		rollout="Rollout1";
		document.getElementById("rolloutid_campos_conf").value="Rollout1";
	}
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"2","rollout":rollout},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess13
		});
	
	function onSuccess13(data)
	{
		$("#div_tabela_campos_rollout").html("<div id=\"toolbar_campos_rollout\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#Modal_campos_rollout\">Novo Campo</button>"+
    			"<button id=\"edita_campo\" type=\"button\" class=\"btn btn-info\">Editar</button>"+
    			"<button id=\"desabilita_campo\" type=\"button\" class=\"btn btn-danger\">Desabilitar Campo</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"carrega_tabela_campos_rollout()\">Atualizar</button>"+
    			"<button type=\"button\" class=\"btn btn-primary\" onclick=\"ordem_tabela()\">Salvar</button>"+
    		    "<select id=\"select_rollout_campos\" class=\"selectpicker\" data-live-search=\"true\" title=\"Escolha o Rollout\" onchange=\"carrega_tabela_campos_rollout(this.value)\"></select>"+
    			"</div>" + data);
		
		$('#tabela_campos_rollout').bootstrapTable();
		
		$('#select_rollout_campos').selectpicker('refresh');
		busca_rollouts();
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
    	//console.log(row);
    	return {
            disabled: true,
            reorder:false
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
			//alert(data);
		    carrega_gant();
		}

	}  

function atualiza_status(){
	if(geral.usuario.includes('masteradmin')){
	$.ajax({
		  type: "POST",
		  data: {"opt":"26"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		  success: atualizaStatusReposta
		});
	function atualizaStatusReposta(data)
	{
		
		$.alert(data.toString())
	}
	}else{
		$.alert("Sem permissao para essa função!")
	}
}

function deleta_campos_rollout(campos){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"17",
			     "campos":campos,"rollout":document.getElementById("rolloutid_campos_conf").value},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess17
		});
	function onSuccess17(data)
	{
		
		//alert(data);
		carrega_gant();
	}

}
function ordem_tabela(){
	data_ordem=$('#tabela_campos_rollout').bootstrapTable('getData');
	var ordem_campo_padrao = '{"tamanho":0,"ordem":[]}';
	var ordem_campo = JSON.parse(ordem_campo_padrao);
	var rollout = document.getElementById("rolloutid_campos_conf").value;
	i=0;
	//alert(JSON.stringify(data_ordem));
	
	ordem_campo.tamanho=data_ordem.length;
	while(i<data_ordem.length){
		localizacao={campoid:data_ordem[i]['campo_id'],posicao:i+1,campo_nome:data_ordem[i]['campo2']};
		ordem_campo.ordem.push(localizacao);
		i++;
	}
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"15","ordem":JSON.stringify(ordem_campo),"rollout":rollout},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess15
		});
	function onSuccess15(data)
	{
		$.alert(data.toString());
		carrega_gant();
	}
	
	
	
	
	//alert(JSON.stringify(ordem_campo));
    
}
function add_campo_rollout(){
	//alert("adicionando campos");
	var e,tipoc,tipoa,nome,desc,lista,chk,percent;
	var rollout=document.getElementById("rolloutid_campos_conf").value;
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
			"percent":percent,
			"rollout":rollout
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess14
		});
	
	function onSuccess14(data)
	{
		
		ordem_tabela();
		carrega_gant();
	}
	}
function carrega_campos_rollout(){
	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
  	 var rolloutid;
  	 if (item) {
  		 rolloutid=item.value;
  	 }else{
  		 rolloutid="Rollout1";
  	 }
	$.ajax({
		  type: "POST",
		  data: {"opt":"7","rolloutid":rolloutid
			
			
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccessCamposRollout
		});
	function onSuccessCamposRollout(data){
		
		$("#select_campos_update_lote").html(data)
		$("#select_campos_update_lote").selectpicker('refresh');
	}
}
function verifica_campo_tipo(campo,origem){
	//alert(campo);
	$.ajax({
		  type: "POST",
		  data: {"opt":"6",
			"campo":campo
			
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccessCampoTipo
		});
	function onSuccessCampoTipo(data){
		if(origem=="rollout"){
		document.getElementById("tipo_campo_lote").value=data;
		if(data=="Data"){
			$("#tipo_campos_update_lote").html("<br><hr><br><p><table data-toggle='table' style='width:80%'><tr><td>Valor</td><td><input id='data_atbr_lote' type='text' class='form-control' value=''></td></tr></table></p>")
			$("#data_atbr_lote").jqxDateTimeInput({ width: '300px', height: '25px',showWeekNumbers: true,allowNullDate: true,showFooter:true,clearString: 'Limpar',formatString: 'dd/MM/yyyy',culture: 'pt-BR' });
			$("#data_atbr_lote").jqxDateTimeInput('val','');
		}
		else if(data=="Milestone"){
			$("#tipo_campos_update_lote").html("<br><hr><br><p><table data-toggle='table' style='width:80%'><tr><td>Inicio Planejado</td><td><input id='inicio_planejado_lote' type='text' class='form-control' value=''></td></tr><tr><td>Fim Planejado</td><td><input id='fim_planejado_lote' type='text' class='form-control' value=''></td></tr><tr><td>Inicio Real</td><td><input type='text' id='inicio_real_lote' class='form-control' value=''></td></tr><tr><td>Fim Real</td><td><input type='text' id='fim_real_lote' class='form-control' value=''></td></tr><tr><td>Anotações</td><td><input id='anot_lote' type='text' class='form-control' value=''></td></tr><tr><td>Responsável</td><td><input id='resp_lote' type='text'class=' form-control' value=''></td></tr></table><p>")
			$("#inicio_planejado_lote").jqxDateTimeInput({ width: '300px', height: '25px',showWeekNumbers: true,allowNullDate: true,showFooter:true,clearString: 'Limpar',formatString: 'dd/MM/yyyy', culture: 'pt-BR' });
			$("#inicio_planejado_lote").jqxDateTimeInput('val','');
			$("#fim_planejado_lote").jqxDateTimeInput({ width: '300px', height: '25px',showWeekNumbers: true,allowNullDate: true,showFooter:true,clearString: 'Limpar',formatString: 'dd/MM/yyyy',culture: 'pt-BR' });
			$("#fim_planejado_lote").jqxDateTimeInput('val','');
			$("#inicio_real_lote").jqxDateTimeInput({ width: '300px', height: '25px',showWeekNumbers: true,allowNullDate: true,showFooter:true,clearString: 'Limpar',formatString: 'dd/MM/yyyy',culture: 'pt-BR' });
			$("#inicio_real_lote").jqxDateTimeInput('val','');
			$("#fim_real_lote").jqxDateTimeInput({ width: '300px', height: '25px',showWeekNumbers: true,allowNullDate: true,showFooter:true,clearString: 'Limpar',formatString: 'dd/MM/yyyy',culture: 'pt-BR' });
			$("#fim_real_lote").jqxDateTimeInput('val','');
		}else{
			 $("#tipo_campos_update_lote").html("<br><hr><br><p><table data-toggle='table' style='width:80%'><tr><td>Valor</td><td><input id='attbr_lote' type='text' class='form-control' value=''></td></tr></table></p>")
			 
		}
		}
		if(origem=="filtro_operacional"){
			if(data=="Data"){
				$("#tipo_campos_filtros_mapaOperacional").html("<br><hr><label style='color:black'>Selecione o período de tempo para filtro:</label><br><p><table data-toggle='table' style='width:80%'><tr><td style='color:black'>Valor</td><td><input id='data_atbr_filtro_mapaOperacional_data' type='text' class='form-control' value=''></td></tr></table></p><br><br><button class='btn btn-primary' onclick=registra_filtro_operacional('data')>Adicionar Filtro</button>")
				$("#data_atbr_filtro_mapaOperacional_data").jqxDateTimeInput({ width: '300px', height: '25px',selectionMode: 'range',showWeekNumbers: true,allowNullDate: true,showFooter:true,clearString: 'Limpar',formatString: 'dd/MM/yyyy',culture: 'pt-BR' });
				$("#data_atbr_filtro_mapaOperacional_data").jqxDateTimeInput('val','');
			}else{
				$("#tipo_campos_filtros_mapaOperacional").html("<br><hr><label style='color:black'>Selecione a condição e informe o valor:</label><br><p><table data-toggle='table' style='width:80%'><tr style='padding:5px'><td style='padding:5px'><select id='select_condicao_filtro_mapa_operacional' class='selectpicker' data-live-search='true'><option value='equal'>Igual a</option><option value='contem'>Contém</option></select></td></tr><tr style='padding:5px'><td style='padding:5px'><input type='text' id='data_atbr_filtro_mapaOperacional_string' value='' class='form-control'></td></tr></table></p><br><br><button class='btn btn-primary' onclick=registra_filtro_operacional('texto')>Adicionar Filtro</button>")
				$('#select_condicao_filtro_mapa_operacional').selectpicker('refresh');
			}
		}
	}
}
function registra_filtro_operacional(tipo){
	 var opt=document.getElementById('opt_mapa_operacinal').value;
	
	filtros=JSON.parse(sessionStorage.getItem("filtrosOperacional_"+opt));
	if(filtros==null){
		filtros={"filtros":[]};
	}
	var aux={};
	if(tipo=='texto'){
		aux.filtersvalue=document.getElementById("data_atbr_filtro_mapaOperacional_string").value;
		document.getElementById("data_atbr_filtro_mapaOperacional_string").value='';
		aux.filtercondition=$('#select_condicao_filtro_mapa_operacional').selectpicker('val');
		$('#select_condicao_filtro_mapa_operacional').selectpicker('val','');
		aux.datafield = $('#select_campo_mapa_operacinal').selectpicker('val');
		$('#select_campo_mapa_operacinal').selectpicker('val','');
		filtros.filtros.push(aux);
    	aux={};
	}
       if(tipo=='data'){
    	   var selection=$('#data_atbr_filtro_mapaOperacional_data').jqxDateTimeInput('getRange');
    	   aux.datafield = $('#select_campo_mapa_operacinal').selectpicker('val');
    	   aux.filtercondition='GREATHER EQUAL THAN';
    	   aux.filtersvalue=moment(selection.from).format('L')
    	   filtros.filtros.push(aux);
       	   aux={};
       	   aux.datafield = $('#select_campo_mapa_operacinal').selectpicker('val');
       	   aux.filtercondition='LESS EQUAL THAN';
       	   aux.filtersvalue=moment(selection.to).format('L')
       	    $('#select_campo_mapa_operacinal').selectpicker('val','');
       	   filtros.filtros.push(aux);
    	   aux={};
   } 	
        	
        $("#resumo_tipo_campos_filtros_mapaOperacional").html(JSON.stringify(filtros));
        sessionStorage.setItem("filtrosOperacional_"+opt,JSON.stringify(filtros))
	
}

function atualiza_lote(){
	
	var rows_indexes=$('#jqxgrid').jqxGrid('getselectedrowindexes');
	var tipo=document.getElementById("tipo_campo_lote").value;
	if(tipo=='Milestone'){
	for(var i=0;i<rows_indexes.length;i++){
		if($("#inicio_planejado_lote").jqxDateTimeInput('val')!=''){
			registra_mudança_campos(rows_indexes[i],"sdate_pre_"+$('#select_campos_update_lote').val(),$("#inicio_planejado_lote").jqxDateTimeInput('getDate'),'datetimeinput',$('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "sdate_pre_"+$('#select_campos_update_lote').val()));
		}
		if($("#fim_planejado_lote").jqxDateTimeInput('val')!=''){
			registra_mudança_campos(rows_indexes[i],"edate_pre_"+$('#select_campos_update_lote').val(),$("#fim_planejado_lote").jqxDateTimeInput('getDate'),'datetimeinput',$('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "edate_pre_"+$('#select_campos_update_lote').val()));
		}
		if($("#inicio_real_lote").jqxDateTimeInput('val')!=''){
			registra_mudança_campos(rows_indexes[i],"sdate_"+$('#select_campos_update_lote').val(),$("#inicio_real_lote").jqxDateTimeInput('getDate'),'datetimeinput',$('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "sdate_"+$('#select_campos_update_lote').val()));
		}
		if($("#fim_real_lote").jqxDateTimeInput('val')!=''){
			registra_mudança_campos(rows_indexes[i],"edate_"+$('#select_campos_update_lote').val(),$("#fim_real_lote").jqxDateTimeInput('getDate'),'datetimeinput',$('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "edate_"+$('#select_campos_update_lote').val()));
		}
		if(document.getElementById("anot_lote").value!=''){
			registra_mudança_campos(rows_indexes[i],"udate_"+$('#select_campos_update_lote').val(),document.getElementById("anot_lote").value,'textbox',$('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "udate_"+$('#select_campos_update_lote').val()));
		}
		if(document.getElementById("resp_lote").value!=''){
			registra_mudança_campos(rows_indexes[i],"resp_"+$('#select_campos_update_lote').val(),document.getElementById("resp_lote").value,'textbox',$('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "resp_"+$('#select_campos_update_lote').val()));
		}
	}
	}else if(tipo=='Data'){
		for(var i=0;i<rows_indexes.length;i++){
			registra_mudança_campos(rows_indexes[i],$('#select_campos_update_lote').val(),$("#data_atbr_lote").jqxDateTimeInput('getDate'),'datetimeinput',$('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], $('#select_campos_update_lote').val()));
		}
	}else{
		for(var i=0;i<rows_indexes.length;i++){
			if(document.getElementById("attbr_lote").value!=''){
				registra_mudança_campos(rows_indexes[i],$('#select_campos_update_lote').val(),document.getElementById("attbr_lote").value,'textbox',$('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], $('#select_campos_update_lote').val()));
			}
		}
	}
	registra_mudancas_bd(g_changes);
	$("#tipo_campos_update_lote").html('');
	$('#modal_update_lotes').modal('hide');
	
}
function carrega_gant(){
	 var timestamp = Date.now();
	 var sites=[];
	 var me,container,save_button,addButton,cFilterButton,deleteButton = "";
	 
	 var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
	 var rolloutid;
	 if (item != null) {
		 rolloutid=item.value;
		 if(rolloutid=="Rollout"){
			 return;
		 }
	 }else{
		 
		 rolloutid="Rollout1";
	 }
	$.getJSON('./RolloutServlet?opt=1&rolloutid='+rolloutid+'&_='+timestamp, function(data) {
			
		var sites_aux=[];
		sites_aux=data['sites'];
		//console.log(sites_aux);
		 var validaDataActual= function (cell, value) {
			 if(value==''){
				 return true;
			 }
			 var d1 = new Date();
			 if(value > d1.getTime()){
				 return { result: false, message: "Inicio/Fim real não podem ser maior que data atual." };
			 }
             return true;
         }
		 var siteMarcadoIntegrado = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
             if (sites_aux.indexOf(value)>0) {
                 return '<span style="margin: 4px; margin-top:8px; float: ' + columnproperties.cellsalign + '; color: #008000;">' + value + '</span>';
             }
             else {
                 return '<span style="margin: 4px; margin-top:8px; float: ' + columnproperties.cellsalign + '; color: #ff0000;">' + value + '</span>';
             }
         }
		for(var i=0;i<data['campos2'].length;i++){
			texto_a=(data['campos2'][i].text).toString();
			
			if(texto_a=="Site ID"){
				data['campos2'][i].cellsrenderer=siteMarcadoIntegrado;
			}
			if(texto_a=="Inicio Real"){
				
				data['campos2'][i].validation=validaDataActual;
			}
			if(texto_a=="Fim Real"){
				
				data['campos2'][i].validation=validaDataActual;
			}
			
		}
		var PeopleSource =
        {
             datatype: "array",
             datafields: [
                 { name: 'label', type: 'string' },
                 { name: 'value', type: 'string' }
             ],
             localdata: data['people']
        };
        var PeopleAdapter = new $.jqx.dataAdapter(PeopleSource, {
            autoBind: true
        });
		for(var j=0 ;j<data['campos2'].length;j++){
			if(data['campos2'][j].datafield.search("resp_")>=0){
				data['campos2'][j]['createeditor']= function (row, column, editor) {
					editor.jqxComboBox({ source: PeopleAdapter, promptText: 'Responsável'});
							}
			}
		}
		
		var source =
        {
            datatype: "json",
            datafields: data['campos'],
            cache: false,
            url: './RolloutServlet',
           	id: 'id',
            filter: function() {
                // update the grid and send a request to the server.
                $("#jqxgrid").jqxGrid('updatebounddata', 'filter');
                //alert("alert filter1");
            },
            sort: function() {
                // update the grid and send a request to the server.
                $("#jqxgrid").jqxGrid('updatebounddata', 'sort');
            },
            beforeprocessing: function(data) {
                if (data != null && data.length > 0) {
                    source.totalrecords = data[0].totalRecords;
                }}
           
        };
		
        var filterChanged = false;
		//var dataAdapter = new $.jqx.dataAdapter(source);
		var dataadapter = new $.jqx.dataAdapter(source, {
			
            formatData: function(data) {
            	data.opt=9;
            	data.rolloutid=rolloutid;
            	data._=timestamp;
				//alert(JSON.stringify(data));
                return data;
            },
            downloadComplete: function(data, status, xhr) {
                if (!source.totalRecords) {
                    source.totalRecords = data.length;
                }
                atualiza_dash_rollout();
            },
            loadError: function(xhr, status, error) {
                throw new Error(error);
            }
        });
		$("#jqxgrid").jqxGrid(
	            {
	                width: '100%',
	                height: '70%',
	                theme:'light',
	                source: dataadapter,
	                columnsresize: true,
	                pageable: true,
	                editable: true,
	                //showfilterrow: true,
	                showtoolbar: true,
	                filterable: true,
	                pagesize: 40,
	                autoshowfiltericon: true,
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
	               
	                selectionmode: 'checkbox',
	                pagesizeoptions: ['40', '100', '300'],
	                rendertoolbar: function (toolbar) {
	                    var me = this;
	                    toolbar.empty();
	                    container = $("<div style='overflow: hidden; position: relative; margin: 1px;'></div>");
	                    save_button = $("<input type=\"button\" class=\"btn btn-primary\" style='float: left; margin-left: 5px' value=\"Salvar\" id='jqxButton_salvar'>");
	                    drop_button=$("<div style='float: left;' id='dropDownButton_op_rollout'><div style='border: none;' id='jqxTree_bt_rollout'><ul><li><a style='float: left; margin-left: 5px;' href='#' onclick='SyncRollout();'>Atualizar</a></li><li><a style='float: left; margin-left: 5px;' href=\"#\" data-toggle=\"modal\" data-target=\"#modal_upload_rollout\">Importar</a></li><li><a style='float: left; margin-left: 5px;' href='#' onclick='downloadFunc()'>Exportar</a></li><li><a style='float: left; margin-left: 5px;' href=\"#\" data-toggle=\"modal\" data-target=\"#modal_rollout_history\">Histórico de Mudanças</a></li><li><a style='float: left; margin-left: 5px;' href=\"#\" onclick='SyncToMongoDB()' >Sync TO Mongo</a></li><li><a style='float: left; margin-left: 5px;' href=\"#\" onclick='atualiza_status()' >Atualiza Status</a></li></ul></div></div>");
	                    addButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Nova Atividade</a>");
	                    batchButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Atualização em Lotes</a>");
	                    cFilterButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Remover Filtros</a>");
	                    deleteButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Remover Atividade</a>");
	                    //syncButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Sync</a>");
	                    //downloadButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Exportar</a>");
	                    //uploadButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href=\"#\" data-toggle=\"modal\" data-target=\"#modal_upload_rollout\">Importar</a>");
	                    //syncMongoButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href=\"#\" >Sync TO Mongo</a>");
	                    RolloutMapButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href=\"#\" >Mapa(Beta)</a>");
	                    container.append(save_button);
	                    container.append(addButton);
	                    container.append(batchButton);
	                    container.append(drop_button);
	                    container.append(cFilterButton);
	                    //container.append(downloadButton);
	                    //container.append(uploadButton);
	                    //container.append(syncButton);
	                    container.append(deleteButton);
	                    container.append(RolloutMapButton);
	                    //container.append(syncMongoButton);
	                    toolbar.append(container);
	                    save_button.jqxButton({theme:'light'});
	                    batchButton.jqxButton({theme:'light'});
	                    drop_button.jqxDropDownButton({ width: 150, height: 30,theme:'light'});
	                    $('#dropDownButton_op_rollout').jqxDropDownButton('setContent', '<div style="position: relative; margin-left: 3px; margin-top: 5px;">Operações</div>'); 
	                    $('#jqxTree_bt_rollout').on('select', function (event) {
	                       
	                        $("#dropDownButton_op_rollout").jqxDropDownButton('close'); 
	                    });
	                    $("#jqxTree_bt_rollout").jqxTree({ width: 200});
	                    addButton.jqxButton({theme:'light'});
	                    cFilterButton.jqxButton({theme:'light'});
	                    //downloadButton.jqxButton();
	                    RolloutMapButton.jqxButton({theme:'light'});
	                    //uploadButton.jqxButton();
	                    //syncButton.jqxButton();
	                    //syncMongoButton.jqxButton();
	                    deleteButton.jqxButton({template: "danger"});
	                    
	                    RolloutMapButton.click(function(event){
	                    	$.alert("Função em Manutenção. Utilize o Mapa Operacional no Menu.");
	                    	return;
	                    	filtros={"filtros":[]};
	                    	var aux={};
	                    	var filtersinfo = $('#jqxgrid').jqxGrid('getfilterinformation');
	                    	//console.log(filtersinfo);
	                    	for(var j=0;j<filtersinfo.length;j++){
		                    	for (var i = 0; i < filtersinfo[j].filter.getfilters().length; i++) {
			                    	aux.filtersvalue=filtersinfo[j].filter.getfilters()[i].value;
			                    	aux.filtercondition=filtersinfo[j].filter.getfilters()[i].condition;
			                    	aux.datafield = filtersinfo[j].filtercolumn;
			                    	
			                    	filtros.filtros.push(aux);
			                    	aux={};
		                    	}
		                    	
		                    }
	                    	sessionStorage.setItem("rollout_map_filtro",JSON.stringify(filtros));
	                    	$(".janelas").hide();
	                		document.getElementById("mapa_central").style.display = "block";
	                		inicializa_mapa_full(1);
	                		
	                    	
	                    });
	                    
	                   
	                    cFilterButton.click(function (event) {
	                    	$("#jqxgrid").jqxGrid('clearfilters');
	                    });
	                    save_button.click(function (event) {
	                    	$('#jqxButton_salvar').jqxButton('val', 'Aguarde...');
	                    	$('#jqxButton_salvar').jqxButton({
	                    	    disabled: true
	                    	});
	                    	$('#jqxButton_salvar').jqxButton('render');
	                    	registra_mudancas_bd(g_changes);
	                    	
	                    });
	                    deleteButton.click(function (event) {
	                    	rm_row_rollout();
	                    });
	                    batchButton.click(function (event) {
	                    	if(g_changes.atualizacoes>0){
	                    		$.confirm({
	                    		    title: '!',
	                    		    type:'red',
	                    		    content: 'Dectamos mudanças não salvas. Essas mudanças serão descartadas caso deseje continuar.',
	                    		    buttons: {
	                    		        Continuar: function () {
	                    		        	g_changes.atualizacoes=0;
	                    		        	g_changes.campos=[];
	                    		        	document.getElementById("linhas_selecionadas_lote").value=$('#jqxgrid').jqxGrid('getselectedrowindexes').length + " Linhas Selecionadas";
	            	                    	carrega_campos_rollout();
	            	                    	$('#modal_update_lotes').modal('show');
	                    		        },
	                    		        Cancelar: function () {
	                    		            
	                    		        }
	                    		        
	                    		    }
	                    		});
	                    	}else{
	                    	document.getElementById("linhas_selecionadas_lote").value=$('#jqxgrid').jqxGrid('getselectedrowindexes').length + " Linhas Selecionadas";
	                    	carrega_campos_rollout();
	                    	$('#modal_update_lotes').modal('show');
	                    	}
	                    	//console.log($('#jqxgrid').jqxGrid('getselectedrowindexes').length + " Linhas Selecionadas");
	                    });
	                   
	                    addButton.click(function (event) {
	                    	 $.ajax({
	                   		  type: "POST",
	                   		  data: {"opt":"23","rolloutid":rolloutid
	                   			
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
	                   		$('input[type=data_row]').w2field('date', { format: 'dd/mm/yyyy'});
	                   		
	                   		$('#modal_row_add').modal('show');
	                   	 }
	                   	
	                        
	                    });
	                    },
	                altrows: true,
	                editmode: 'click',
	                virtualmode: true,
	                rendergridrows: function(obj) {
	                    return obj.data;
	                },
	                columns: data['campos2'],
	                columngroups: data['columgroup']
	            });
		
		$('#jqxLoader_rolout').jqxLoader('close');
		
	});
	 //$("#jqxgrid").jqxGrid('selectionmode', 'multiplerows');
	 $("#jqxgrid").on('cellendedit', function (event) {
         var args = event.args;
        // console.log(args);
         //alert(args.datafield);
         //alert(args.row.recid);
         //alert(args.row);
         registra_mudança_campos(args.rowindex,args.datafield,args.value,args.columntype,args.oldvalue);
     });
	 $('#jqxLoader_rolout').jqxLoader('close');
	 
	 
	}
function SyncToMongoDB() {
	if(geral.usuario.includes('masteradmin')){
	$.ajax({
		  type: "POST",
		  data: {"opt":"14"
			
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text"
		  
		});
	}else{
		$.alert("Sem privilégio para essa operação!");
	}
}
function SyncRollout(){
	carrega_gant();
}
function downloadFunc(){
	//$.alert("Função Temporariamente desabilitada, devido migracao do banco de dados! estimativa de retorno da função 22/02/2019 as 18:00hs!");
	//return;
	filtros={"filtros":[]};
	var rows = $('#jqxgrid').jqxGrid('getdisplayrows');
	var rowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes');
	$.confirm({
		title:'Download do Rollout',
		content:'Selecione abaixo o método de downlaod.',
		type: 'orange',
		typeAnimated: true,
		columnClass: 'col-md-6 col-md-offset-3',
		buttons:{
			PorLinhas:{
				text:"Linhas Selecionadas("+rowindexes.length+")",
				action:function(){
					for(var v=0;v<rowindexes.length;v++){
						filtros.filtros.push($('#jqxgrid').jqxGrid('getrowid', rowindexes[v]));
					}
					 var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
					 var rolloutid;
					 if (item != null) {
						 rolloutid=item.value;
					 }else{
						 rolloutid="Rollout1";
					 }
					$.ajax({
        		        url: './RolloutServlet?opt=3&tipo=selecionadas&rolloutid='+rolloutid,
        		        method: 'GET',
        		        data:{"filtros":JSON.stringify(filtros),"rolloutid":rolloutid},
        		        xhrFields: {
        		            responseType: 'blob'
        		        },
        		        success: function (data) {
        		            var a = document.createElement('a');
        		            var url = window.URL.createObjectURL(data);
        		            a.href = url;
        		            a.download = 'rollout_mstp.xlsx';
        		            a.click();
        		            window.URL.revokeObjectURL(url);
        		        }
        		    });
				}
			},
			PorFiltro:{
				text:"Filtro corrente ("+rows.length+" Linhas)",
				action:function(){
					
					filtros={"filtros":[]};
                	var aux={};
                	var filtersinfo = $('#jqxgrid').jqxGrid('getfilterinformation');
                	//console.log(filtersinfo);
                	for(var j=0;j<filtersinfo.length;j++){
                    	for (var i = 0; i < filtersinfo[j].filter.getfilters().length; i++) {
	                    	aux.filtersvalue=filtersinfo[j].filter.getfilters()[i].value;
	                    	aux.filtercondition=filtersinfo[j].filter.getfilters()[i].condition;
	                    	aux.datafield = filtersinfo[j].filtercolumn;
	                    	
	                    	filtros.filtros.push(aux);
	                    	aux={};
                    	}
                    	
                    }
                	//alert(JSON.stringify(filtros));
                	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
					 var rolloutid;
					 if (item != null) {
						 rolloutid=item.value;
					 }else{
						 rolloutid="Rollout1";
					 }
        		    $.ajax({
        		        url: './RolloutServlet?opt=3&tipo=filtro&rolloutid='+rolloutid,
        		        method: 'GET',
        		        data:{"filtros":JSON.stringify(filtros),"rolloutid":rolloutid},
        		        xhrFields: {
        		            responseType: 'blob'
        		        },
        		        success: function (data) {
        		            var a = document.createElement('a');
        		            var url = window.URL.createObjectURL(data);
        		            a.href = url;
        		            a.download = 'rollout_mstp.xlsx';
        		            a.click();
        		            window.URL.revokeObjectURL(url);
        		        }
        		    });
				
				}
			},
			Completo:{
				text:"Completo",
				
				action:function(){
					var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
					 var rolloutid;
					 if (item) {
						 rolloutid=item.value;
					 }else{
						 rolloutid="Rollout1";
					 }
					 $.ajax({
            		        url: './RolloutServlet?opt=3&tipo=completo&rolloutid='+rolloutid,
            		        method: 'GET',
            		        data:{"filtros":JSON.stringify(filtros),"rolloutid":rolloutid},
            		        xhrFields: {
            		            responseType: 'blob'
            		        },
            		        success: function (data) {
            		            var a = document.createElement('a');
            		            var url = window.URL.createObjectURL(data);
            		            a.href = url;
            		            a.download = 'rollout_mstp.xlsx';
            		            a.click();
            		            window.URL.revokeObjectURL(url);
            		        }
            		    });
				}
			},
			Cancelar:function(){}
				
		}
	});
}		
function registra_mudança_campos(index,campo,valor,tipo,oldvalue){
	//alert(tipo);
	if(campo=="check_linha"){
		$("#jqxgrid").jqxGrid('selectrow', index);
		aux=deletar.indexOf($("#jqxgrid").jqxGrid('getrowid', index));
		//alert("rowid: "+$("#jqxgrid").jqxGrid('getrowid', index));
		//alert("index: "+ index);
		//alert("recid: "+ linha);
		
		//console.log(deletar);
	}else{
	if(tipo.length>0){
	if (tipo=='datetimeinput'){
		//alert(valor);
		//var d = new Date(valor);
		var localdata = moment(valor).format('L');
		//alert(localdata);
		if(localdata!='Invalid date'){
			
			localizacao={id:$("#jqxgrid").jqxGrid('getrowid', index),colum:campo,value:localdata,tipoc:tipo,oldvalue:oldvalue};
		}else{
			
			localizacao={id:$("#jqxgrid").jqxGrid('getrowid', index),colum:campo,value:'',tipoc:'textbox',oldvalue:oldvalue};
		}
	}else{
		localizacao={id:$("#jqxgrid").jqxGrid('getrowid', index),colum:campo,value:valor,tipoc:tipo,oldvalue:oldvalue}
	}
	g_changes.atualizacoes=g_changes.atualizacoes+1;
	g_changes.campos.push(localizacao);
	}
	}
	//var valor_novo=w2ui['grid'].getCellValue(record,field_change);
	//alert(valor_novo);
}
function busca_historico_rollout_filtros(){
	var siteid,autor,periodo,campos,i,f;
	campos=$('#select_campo_historico_filtro').selectpicker('val');
	autor=$('#select_autor_historico_filtro').selectpicker('val');
	siteid=document.getElementById('filtro_historico_siteid').value;
	var selection = $("#periodo_hitorico_mudanca").jqxDateTimeInput('getRange');
	 i = moment(selection.from).format('L');
	 f = moment(selection.to).format('L');
	$.ajax({
		  type: "POST",
		  data: {"opt":"20",
			"siteid":siteid,
			"campos":campos.toString(),
			"autor":autor.toString(),
			"inicio":i,
			"fim":f
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		 success: CriaGridHistorico
		});
	function CriaGridHistorico(data){
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
                { name: 'Data da Atualização', type: 'date	' }
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
function rm_row_rollout(){
	
	var rowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes');
	$.confirm({
		title:'Remoção de Atividade do Rollout',
		content:'ATENÇÃO! Deseja remover as linhas selecionadas do rollout?',
		type: 'red',
		typeAnimated: true,
		buttons:{
			Confirma :{
				text: 'Confirma('+rowindexes.length+' linhas)',
			
				action:function(){
			
				
				for(var v=0;v<rowindexes.length;v++){
					deletar.push($('#jqxgrid').jqxGrid('getrowid', rowindexes[v]));
				}
				if(deletar.length>0){	
					$.ajax({
				 		  type: "POST",
				 		  data: {"opt":"8",
				 			"linha":deletar.toString()
				 			},		  
				 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
				 		  url: "./RolloutServlet",
				 		  cache: false,
				 		  dataType: "text",
				 		  success: SucessLinhaRemovida
				 		});
				 	 function SucessLinhaRemovida(data){
				 		 
				 		$("#jqxgrid").jqxGrid('deleterow', deletar);
				 		$('#jqxgrid').jqxGrid('refreshdata');
				 		$('#jqxgrid').jqxGrid('clearselection');
				 		  deletar=[];
				 		 deletar_grid=[];
				 		 carrega_gant();
				 		 
				 	 }
				}
				}
			},
			Cancela:function (){}
		}
	});
		
		
	
	}

function add_new_row_rollout(){
	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
	 var rolloutid;
	 if (item != null) {
		 rolloutid=item.value;
		 if(rolloutid=="Rollout"){
			 return;
		 }
	 }else{
		 $.alert("Selecione o rollout na árvore de rollout");
		 return;
	 }
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
	row.rows=[];
	$(".row_rollout").each(function(){
		if($(this).attr('name').indexOf("_inicio")>0){
			//alert("sdate_pre_" + $(this).attr('name').replace("_inicio",""));
			map["sdate_pre_" + $(this).attr('name').replace("_inicio","")] = $(this).val();
		}else if($(this).attr('name').indexOf("_fim")>0){
			map["edate_pre_" + $(this).attr('name').replace("_fim","")] = $(this).val();
		}else{
			map[$(this).attr('name')] = $(this).val();
		}
		linha={nome_campo:$(this).attr('name'),valor_campo:$(this).val(),tipo_campo:$(this).attr('tipo_campo'),tipo_info:$(this).attr('tipo_info')}
		
		row.rows.push(linha)
		$(this).val('');
	});
	
	//linha={nome_campo:"recid",valor_campo:parseInt(id)+1,tipo_campo:"identificador"};
	//row.rows.push(linha)
	$.ajax({
 		  type: "POST",
 		  data: {"opt":"4",
 			"linha":JSON.stringify(row),"rolloutid":rolloutid
 			},		  
 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
 		  url: "./RolloutServlet",
 		  cache: false,
 		  dataType: "text",
 		  success: onSuccess24
 		});
 	 function onSuccess24(data){
 		map['recid']=data;
	$("#jqxgrid").jqxGrid('addrow', null,map);
	//alert(row['rows'][1]['nome_campo']);
	 row.rows=[];
 	 }
}
function registra_mudancas_bd(changes){
	//console.log("posicao 2:"+changes[1]);
	//changes.colunas=w2ui['grid'].columns;
	//alert("posicao 3:"+JSON.stringify(changes));
	if(g_changes.atualizacoes>0){
	$.ajax({
		  type: "POST",
		  data: {"opt":"15",
			"mudancas":JSON.stringify(changes) 
			},		  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess16
		});
	
	function onSuccess16(data)
	{
		
	$.alert(data.toString());
	g_changes.atualizacoes=0;
	g_changes.campos=[];
	$('#jqxButton_salvar').jqxButton({
	    disabled: false
	});
	$('#jqxButton_salvar').jqxButton('val', 'Salvar');
	$('#jqxButton_salvar').jqxButton('render');
	if(geral.perfil.search("RolloutManager")>=0){
		
		 g1(0,'grafico_container5');
		 
	 }
	}
	}else{
		$.alert("Sem Mudanças para registrar!");
		$('#jqxButton_salvar').jqxButton({
		    disabled: false
		});
		$('#jqxButton_salvar').jqxButton('val', 'Salvar');
		$('#jqxButton_salvar').jqxButton('render');
	}
}