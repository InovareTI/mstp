function carrega_tabela_campos_rollout() {
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"2"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
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
			alert(data);
		    carrega_gant();
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
		
		//alert(data);
		carrega_gant();
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
		localizacao={campoid:data_ordem[i]['campo_id'],posicao:i+1,campo_nome:data_ordem[i]['campo2']};
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
		$.alert(data.toString());
		carrega_gant();
	}
	
	
	
	
	//alert(JSON.stringify(ordem_campo));
    
}
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
		
		ordem_tabela();
		carrega_gant();
	}
	}
function carrega_campos_rollout(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"7"
			
			
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
function verifica_campo_tipo(campo){
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
		//alert(data);
		document.getElementById("tipo_campo_lote").value=data;
		if(data=="Data"){
			$("#tipo_campos_update_lote").html("<br><hr><br><p><table data-toggle='table' style='width:80%'><tr><td>Valor</td><td><input id='data_atbr_lote' type='text' class='form-control' value=''></td></tr></table></p>")
			$("#data_atbr_lote").jqxDateTimeInput({ width: '300px', height: '25px',showWeekNumbers: true,allowNullDate: true,showFooter:true,clearString: 'Limpar',culture: 'pt-BR' });
			$("#data_atbr_lote").jqxDateTimeInput('val','');
		}
		else if(data=="Milestone"){
			$("#tipo_campos_update_lote").html("<br><hr><br><p><table data-toggle='table' style='width:80%'><tr><td>Inicio Planejado</td><td><input id='inicio_planejado_lote' type='text' class='form-control' value=''></td></tr><tr><td>Fim Planejado</td><td><input id='fim_planejado_lote' type='text' class='form-control' value=''></td></tr><tr><td>Inicio Real</td><td><input type='text' id='inicio_real_lote' class='form-control' value=''></td></tr><tr><td>Fim Real</td><td><input type='text' id='fim_real_lote' class='form-control' value=''></td></tr><tr><td>Anotações</td><td><input id='anot_lote' type='text' class='form-control' value=''></td></tr><tr><td>Responsável</td><td><input id='resp_lote' type='text'class=' form-control' value=''></td></tr></table><p>")
			$("#inicio_planejado_lote").jqxDateTimeInput({ width: '300px', height: '25px',showWeekNumbers: true,allowNullDate: true,showFooter:true,clearString: 'Limpar',culture: 'pt-BR' });
			$("#inicio_planejado_lote").jqxDateTimeInput('val','');
			$("#fim_planejado_lote").jqxDateTimeInput({ width: '300px', height: '25px',showWeekNumbers: true,allowNullDate: true,showFooter:true,clearString: 'Limpar',culture: 'pt-BR' });
			$("#fim_planejado_lote").jqxDateTimeInput('val','');
			$("#inicio_real_lote").jqxDateTimeInput({ width: '300px', height: '25px',showWeekNumbers: true,allowNullDate: true,showFooter:true,clearString: 'Limpar',culture: 'pt-BR' });
			$("#inicio_real_lote").jqxDateTimeInput('val','');
			$("#fim_real_lote").jqxDateTimeInput({ width: '300px', height: '25px',showWeekNumbers: true,allowNullDate: true,showFooter:true,clearString: 'Limpar',culture: 'pt-BR' });
			$("#fim_real_lote").jqxDateTimeInput('val','');
		}else{
			 $("#tipo_campos_update_lote").html("<br><hr><br><p><table data-toggle='table' style='width:80%'><tr><td>Valor</td><td><input id='attbr_lote' type='text' class='form-control' value=''></td></tr></table></p>")
			 
		}
	}
}
function atualiza_lote(){
	
	var rows_indexes=$('#jqxgrid').jqxGrid('getselectedrowindexes');
	var tipo=document.getElementById("tipo_campo_lote").value;
	if(tipo=='Milestone'){
	for(var i=0;i<rows_indexes.length;i++){
		if($("#inicio_planejado_lote").jqxDateTimeInput('val')!=''){
			registra_mudança_campos(rows_indexes[i],"sdate_pre_"+$('#select_campos_update_lote').val(),$("#inicio_planejado_lote").jqxDateTimeInput('val'),'datetimeinput');
		}
		if($("#fim_planejado_lote").jqxDateTimeInput('val')!=''){
			registra_mudança_campos(rows_indexes[i],"edate_pre_"+$('#select_campos_update_lote').val(),$("#fim_planejado_lote").jqxDateTimeInput('val'),'datetimeinput');
		}
		if($("#inicio_real_lote").jqxDateTimeInput('val')!=''){
			registra_mudança_campos(rows_indexes[i],"sdate_"+$('#select_campos_update_lote').val(),$("#inicio_real_lote").jqxDateTimeInput('val'),'datetimeinput');
		}
		if($("#fim_real_lote").jqxDateTimeInput('val')!=''){
			registra_mudança_campos(rows_indexes[i],"edate_"+$('#select_campos_update_lote').val(),$("#fim_real_lote").jqxDateTimeInput('val'),'datetimeinput');
		}
		if(document.getElementById("anot_lote").value!=''){
			registra_mudança_campos(rows_indexes[i],"udate_"+$('#select_campos_update_lote').val(),document.getElementById("anot_lote").value,'textbox');
		}
		if(document.getElementById("resp_lote").value!=''){
			registra_mudança_campos(rows_indexes[i],"resp_"+$('#select_campos_update_lote').val(),document.getElementById("resp_lote").value,'textbox');
		}
	}
	}else if(tipo=='Data'){
		registra_mudança_campos(rows_indexes[i],$('#select_campos_update_lote').val(),$("#data_atbr_lote").jqxDateTimeInput('val'),'datetimeinput');
	}else{
		for(var i=0;i<rows_indexes.length;i++){
			if(document.getElementById("attbr_lote").value!=''){
				registra_mudança_campos(rows_indexes[i],$('#select_campos_update_lote').val(),document.getElementById("attbr_lote").value,'textbox');
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
	 $("#jqxLoader_rolout").jqxLoader({ text: "Carregando Rollout",width: 100, height: 60, imagePosition: 'top' });
	 $('#jqxLoader_rolout').jqxLoader('open');
	 
	 
	$.getJSON('./RolloutServlet?opt=1&_='+timestamp, function(data) {
			
		
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
	                    save_button = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px' href='#'>Salvar</a>");
	                    addButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Nova Atividade</a>");
	                    batchButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Atualização em Lotes</a>");
	                    cFilterButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Remover Filtros</a>");
	                    deleteButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Remover Atividade</a>");
	                    syncButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Sync</a>");
	                    downloadButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Exportar</a>");
	                    uploadButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href=\"#\" data-toggle=\"modal\" data-target=\"#modal_upload_rollout\">Importar</a>");
	                    syncMongoButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href=\"#\" >Sync TO Mongo</a>");
	                    RolloutMapButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href=\"#\" >Mapa(Beta)</a>");
	                    container.append(save_button);
	                    container.append(addButton);
	                    container.append(batchButton);
	                    container.append(cFilterButton);
	                    container.append(downloadButton);
	                    container.append(uploadButton);
	                    container.append(syncButton);
	                    container.append(deleteButton);
	                    container.append(RolloutMapButton);
	                    container.append(syncMongoButton);
	                    toolbar.append(container);
	                    save_button.jqxButton();
	                    batchButton.jqxButton();
	                    addButton.jqxButton();
	                    cFilterButton.jqxButton();
	                    downloadButton.jqxButton();
	                    RolloutMapButton.jqxButton();
	                    uploadButton.jqxButton();
	                    syncButton.jqxButton();
	                    syncMongoButton.jqxButton();
	                    deleteButton.jqxButton({template: "danger"});
	                    syncMongoButton.click(function (event) {
	                    	if(geral.usuario=='masteradmin'){
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
	                    });
	                    RolloutMapButton.click(function(event){
	                    	
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
	                		load_site_markers_mapa_central_rollout();
	                    	
	                    });
	                    downloadButton.click(function(event){
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
	                    					$.ajax({
	            	            		        url: './RolloutServlet?opt=3',
	            	            		        method: 'GET',
	            	            		        data:{"filtros":JSON.stringify(filtros)},
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
	                    					for (var i = 0; i < rows.length; i++) {
	            	                    		var rowData = rows[i];
	            	                    		filtros.filtros.push(rowData.uid);
	            	                    		
	            	                    	}
	            	                    	//alert(JSON.stringify(filtros));
	            	            		    $.ajax({
	            	            		        url: './RolloutServlet?opt=3',
	            	            		        method: 'GET',
	            	            		        data:{"filtros":JSON.stringify(filtros)},
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
	                    					 $.ajax({
		            	            		        url: './RolloutServlet?opt=3',
		            	            		        method: 'GET',
		            	            		        data:{"filtros":JSON.stringify(filtros)},
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
	                    });
	                    syncButton.click(function (event) {
	                    	carrega_gant();
	                    });
	                    cFilterButton.click(function (event) {
	                    	$("#jqxgrid").jqxGrid('clearfilters');
	                    });
	                    save_button.click(function (event) {
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
	                    downloadButton.click(function (event) {
	                    	//download_rollout();
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
	                   		$('input[type=data_row]').w2field('date', { format: 'dd/mm/yyyy'});
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
         //console.log(args);
         //alert(args.datafield);
         //alert(args.row.recid);
         //alert(args.row);
         registra_mudança_campos(args.rowindex,args.datafield,args.value,args.columntype);
     });
	 $('#jqxLoader_rolout').jqxLoader('close');
	}
		
function registra_mudança_campos(index,campo,valor,tipo){
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
		
		var localdata = moment(valor).format('L');
		
		if(localdata!='Invalid date'){
			
			localizacao={id:$("#jqxgrid").jqxGrid('getrowid', index),colum:campo,value:localdata,tipoc:tipo};
		}else{
			
			localizacao={id:$("#jqxgrid").jqxGrid('getrowid', index),colum:campo,value:'',tipoc:'textbox'};
		}
	}else{
		localizacao={id:$("#jqxgrid").jqxGrid('getrowid', index),colum:campo,value:valor,tipoc:tipo}
	}
	g_changes.atualizacoes=g_changes.atualizacoes+1;
	g_changes.campos.push(localizacao);
	}
	}
	//var valor_novo=w2ui['grid'].getCellValue(record,field_change);
	//alert(valor_novo);
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
		linha={nome_campo:$(this).attr('name'),valor_campo:$(this).val(),tipo_campo:$(this).attr('tipo_campo')}
		
		row.rows.push(linha)
		$(this).val('');
	});
	
	//linha={nome_campo:"recid",valor_campo:parseInt(id)+1,tipo_campo:"identificador"};
	//row.rows.push(linha)
	$.ajax({
 		  type: "POST",
 		  data: {"opt":"4",
 			"linha":JSON.stringify(row)
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
		
	alert(data);
	g_changes.atualizacoes=0;
	g_changes.campos=[];
	if(geral.perfil.search("RolloutManager")>=0){
		
		 g1(0,'grafico_container5');
		 
	 }
	}
	}else{
		alert("Sem Mudanças para registrar!");
	}
}