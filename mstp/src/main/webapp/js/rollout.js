function buscaCamposRolloutById_Select(Rolloutid) {
	var campos_rollout = sessionStorage.getItem("CamposSelect_" + Rolloutid);
	if (campos_rollout) {
		$('#select_item_vistoria_campo_rollout').html(campos_rollout);
		$('#select_item_vistoria_campo_rollout').selectpicker();
		$('#select_item_vistoria_campo_rollout').selectpicker('refresh');
	} else {
		$.ajax({
			type: "POST",
			data: { "opt": "7.1", "rolloutid": Rolloutid },
			//url: "http://localhost:8080/DashTM/D_Servlet",	  
			url: "./RolloutServlet",
			cache: false,
			dataType: "text",
			success: salvaCamposRolloutSelect
		});
		function salvaCamposRolloutSelect(data) {

			sessionStorage.setItem("CamposSelect_" + Rolloutid, data);
			$('#select_item_vistoria_campo_rollout').html(data);
			$('#select_item_vistoria_campo_rollout').selectpicker();
			$('#select_item_vistoria_campo_rollout').selectpicker('refresh');

		}
	}
}
function exibe_detalheEquipe(usuario) {
	$('#Modal_DetalheEquipeRollout').modal('show');
	$("#fotos_registros").html("");
	$("#div_detalhe_usuario").html("");
	$.ajax({
		type: "POST",
		data: {
			"opt": "47",
			"usuario": usuario
		},
		url: "./UserMgmt",
		cache: false,
		dataType: "text",
		success: exibe_foto_Equipe
	});
	function exibe_foto_Equipe(data) {

		aux = JSON.parse(data);
		if (aux.fotos.length > 0) {
			for (var i = 0; i < aux.fotos.length; i++) {
				$("#fotos_registros").append("<img src='./UserMgmt?opt=40&foto=" + aux.fotos[i] + "'>");
			}
		} else {
			$("#fotos_registros").append("<label>Sem Fotos de Registros!</label>");
		}
		$("#div_detalhe_usuario").append("<h3 style='color:black'>" + aux.nome + "</h3>");
		$("#div_detalhe_usuario").append("<table>" +
			"<tr style='border-color: white';>" +
			"<td style='border-color: white;background-color:#E7E6E5'>" +
			"<label style='color:black'>Ultimo Login:</td><td><label style='color:black'>" + aux.ultimologin + "</label></td></tr>" +
			"<tr style='border-color: white'>" +
			"<td style='border-color: white;background-color:#E7E6E5'><label style='color:black'>Entrada:</label></td><td><label style='color:black'> " + aux.Entrada + "</label></td></tr>" +
			"<tr style='border-color: white'>" +
			"<td style='border-color: white;background-color:#E7E6E5'><label style='color:black'>Inicio Intervalo:</label></td><td><label style='color:black'>" + aux.Inicio_intervalo + "</label></td></tr>" +
			"<tr style='border-color: white'>" +
			"<td style='border-color: white;background-color:#E7E6E5'><label style='color:black'>Fim Intervalo:</label></td><td><label style='color:black'>" + aux.Fim_intervalo + "</label></td></tr>" +
			"<tr style='border-color: white'>" +
			"<td style='border-color: white;background-color:#E7E6E5'><label style='color:black'>Saída:</label></td><td><label style='color:black'>" + aux.Saída + "</label></td></tr></table>");


	}
}
function carregaRegras() {
	$('#tabela_regras_rollout').DataTable({
		"searching": true,
		"ordering": false,
		"lengthChange": false,
		"processing": true,
		"ajax": './RolloutServlet?opt=38',
		"lengthMenu": [10, 20]
	});
}
function executaRegra(id) {
	$.ajax({
		type: "POST",
		data: { "opt": "40", "id": id },
		url: "./RolloutServlet",
		cache: false,
		dataType: "text",
		success: RegraExecutada
	});
	function RegraExecutada(data) {
		$.alert(data.toString());
		$('#tabela_regras_rollout').DataTable().ajax.reload();
	}
}
function apagaRegra(id) {
	$.ajax({
		type: "POST",
		data: { "opt": "39", "id": id },
		url: "./RolloutServlet",
		cache: false,
		dataType: "text",
		success: RegraExcluida
	});
	function RegraExcluida(data) {
		$('#tabela_regras_rollout').DataTable().ajax.reload();
	}
}
function atualiza_dash_rollout() {

	filtros = { "filtros": [] };
	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
	var rolloutid;
	if (item != null) {
		rolloutid = item.value;
		if (rolloutid == "Rollout") {
			return;
		}
	} else {
		rolloutid = "Rollout1";
	}
	var aux = {};
	var filtersinfo = $('#jqxgrid').jqxGrid('getfilterinformation');
	if (filtersinfo) {
		for (var j = 0; j < filtersinfo.length; j++) {
			for (var i = 0; i < filtersinfo[j].filter.getfilters().length; i++) {
				aux.filtersvalue = filtersinfo[j].filter.getfilters()[i].value;
				aux.filtercondition = filtersinfo[j].filter.getfilters()[i].condition;
				aux.datafield = filtersinfo[j].filtercolumn;

				filtros.filtros.push(aux);
				aux = {};
			}

		}
	}
	if (geral.empresa_id == 1) {
		$.ajax({
			type: "POST",
			data: {
				"opt": "29",
				"filtros": JSON.stringify(filtros), "rolloutid": rolloutid
			},
			//url: "http://localhost:8080/DashTM/D_Servlet",	  
			url: "./RolloutServlet",
			cache: false,
			dataType: "text",
			success: atualiza_dash
		});
		function atualiza_dash(data) {
			//alert(data);
			aux = JSON.parse(data);
			if (geral.empresa_id == 1) {
				$('#quadro_1_1').html("Total Work Date");
			} else {
				$('#quadro_1_1').html("Total Atividades");
			}
			$('#quadro_1_2').html(aux.quadro_1_2);
			$('#quadro_2_2').html(aux.quadro_2_2);
			$('#quadro_3_2').html(aux.quadro_3_2);
			$('#quadro_4_2').html(aux.quadro_4_2);
			$('#quadro_5_2').html(aux.quadro_5_2);
		}
	} else {
		$('#quadro_1_2').html("0");
		$('#quadro_2_2').html("0");
		$('#quadro_3_2').html("0");
		$('#quadro_4_2').html("0");
		$('#quadro_5_2').html("0");
	}
}
function carrega_tree_rollout() {
	var timestamp = Date.now();
	$("#splitter").jqxSplitter({ width: '100%', theme: 'light', height: '90%', panels: [{ size: 250 }] });
	$.getJSON('./RolloutServlet?opt=27&_=' + timestamp, function(data) {
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
		var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{ name: 'text', map: 'label' }]);
		$('#jqxTree_rollout').jqxTree({ theme: 'light', source: records, hasThreeStates: true, checkboxes: true, height: '100%', width: '100%' });
		$('#jqxTree_rollout').jqxTree('expandAll');
		//alert(sessionStorage.getItem("rolloutsid_"+geral.empresa_id));
		var aux1 = sessionStorage.getItem("rolloutsid_" + geral.empresa_id);
		var aux;
		if (aux1) {
			aux = aux1.substr(aux1.indexOf("value='") + 7);
			aux = aux.substr(0, aux.indexOf("'"));
			$("#jqxTree_rollout").jqxTree('selectItem', $("#" + aux)[0]);
		}

		$('#jqxTree_rollout').on('select', function(event) {
			$('#jqxgrid').jqxGrid('destroy');
			$('#container_rollout').html('<div id="jqxgrid"></div>')
			carrega_gant();
		});

		var contextMenu = $("#jqxMenu_tree_rollout").jqxMenu({ theme: 'light', width: '120px', height: '56px', autoOpenPopup: false, mode: 'popup' });
		var clickedItem = null;
		var attachContextMenu = function() {
			// open the context menu when the user presses the mouse right button.
			$("#jqxTree_rollout li").on('mousedown', function(event) {
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
		$("#jqxMenu_tree_rollout").on('itemclick', function(event) {
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
		$(document).on('contextmenu', function(e) {
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
	carrega_gant();
}
function atualiza_nome_rollout() {
	var nome_rollout = document.getElementById("novo_nome_rollout").value;

	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
	var rolloutid;
	if (item != null) {
		rolloutid = item.value;
		if (nome_rollout == "") { $.alert("Nome do rollout nao pode ser vazio!") } else {
			$.ajax({
				type: "POST",
				data: { "opt": "30", "rolloutid": rolloutid, "novo_nome": nome_rollout },
				//url: "http://localhost:8080/DashTM/D_Servlet",	  
				url: "./RolloutServlet",
				cache: false,
				dataType: "text",
				success: onSuccess30_noatualizado
			});
			function onSuccess30_noatualizado(data) {
				carrega_tree_rollout();
				$.alert({ type: "green", content: "Rollout nome atualzado" });
			}
		}
	} else {
		$.alert("Não identificamos o rollout selecionado!")
	}

}
function carrega_tree_rollout_conf() {
	var timestamp = Date.now();

	$.getJSON('./RolloutServlet?opt=27&_=' + timestamp, function(data) {
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
		var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{ name: 'text', map: 'label' }]);
		$('#jqxTree_rollout_conf').jqxTree({ source: records, hasThreeStates: true, checkboxes: true, height: '450px', width: '300px' });

		//$('#jqxTree_rollout_conf').jqxTree({height: '450px', width: '300px'});
		$('#AddBefore').jqxButton({ height: '30px', width: '120px' });
		$('#AddAfter').jqxButton({ height: '30px', width: '120px' });
		$('#Remove').jqxButton({ height: '30px', width: '120px' });
		$('#Update').jqxButton({ height: '30px', width: '120px' });
		$('#SaveTree').jqxButton({ height: '30px', width: '370px' });
		$('#AddBefore').click(function() {
			var selectedItem = $('#jqxTree_rollout_conf').jqxTree('selectedItem');
			if (selectedItem != null) {
				$('#jqxTree_rollout_conf').jqxTree('addBefore', { label: 'Item' }, selectedItem.element, false);
				// update the tree.
				$('#jqxTree_rollout_conf').jqxTree('render');
			}
		});
		$('#AddAfter').click(function() {
			var selectedItem = $('#jqxTree_rollout_conf').jqxTree('selectedItem');
			if (selectedItem != null) {
				$('#jqxTree_rollout_conf').jqxTree('addAfter', { label: 'Item' }, selectedItem.element, false);
				// update the tree.
				$('#jqxTree_rollout_conf').jqxTree('render');
			}
		});
		$("#input_item_tree_conf").jqxInput({ placeHolder: "Insira novo nome", height: 30, width: 250, minLength: 1 });
		$('#Update').click(function() {
			var selectedItem = $('#jqxTree_rollout_conf').jqxTree('selectedItem');
			if (selectedItem != null) {
				$('#jqxTree_rollout_conf').jqxTree('updateItem', { label: $('#input_item_tree_conf').jqxInput('val') }, selectedItem.element);
				// update the tree.
				$('#jqxTree_rollout_conf').jqxTree('render');
			}
		});
		$('#Remove').click(function() {
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
		data: { "opt": "28" },
		//url: "http://localhost:8080/DashTM/D_Servlet",	  
		url: "./RolloutServlet",
		cache: false,
		dataType: "text",
		success: onSuccess28_spazio
	});
	function onSuccess28_spazio(data) {
		$.alert("ajuste finalizado")
	}
}
function busca_rollouts() {
	var rolloutid = sessionStorage.getItem("rolloutsid_" + geral.empresa_id);
	if (rolloutid) {
		console.log(rolloutid);
		var aux = rolloutid;
		$('#select_rollout_campos').html(aux);
		$('#select_rollout_carrega_po').html(aux);
		$('#select_rollout_carrega_po').selectpicker();
		$('#select_rollout_carrega_po').selectpicker('refresh');
		$('#select_rollout_campos').selectpicker();
		$('#select_rollout_campos').selectpicker('refresh');
		$('#select_dashboard_rollout').html(aux);
		aux = rolloutid.substr(rolloutid.indexOf("value='") + 7);
		aux = aux.substr(0, aux.indexOf("'"));
		$('#select_dashboard_rollout').selectpicker('val', aux);
		$('#select_dashboard_rollout').selectpicker('refresh');
		carrega_tree_rollout();
	} else {
		//alert("buscando rollouts no banco");
		$.ajax({
			type: "POST",
			data: { "opt": "31" },
			//url: "http://localhost:8080/DashTM/D_Servlet",	  
			url: "./RolloutServlet",
			cache: false,
			dataType: "text",
			success: atualiza_select_rollout
		});
		function atualiza_select_rollout(data) {
			var nome = "rolloutsid_" + geral.empresa_id;
			console.log(data);
			sessionStorage.setItem(nome, data);
			$('#select_rollout_campos').html(data);
			$('#select_rollout_regra_rollout').html(data);
			$('#select_dashboard_rollout').html(data);
			$('#select_rollout_carrega_po').html(data);
			$('#select_rollout_campos').selectpicker();
			$('#select_rollout_campos').selectpicker('refresh');
			$('#select_rollout_regra_rollout').selectpicker('refresh');
			$('#select_dashboard_rollout').selectpicker('refresh');
			$('#select_rollout_carrega_po').selectpicker('refresh');

			aux = data.substr(data.indexOf("value='") + 7);
			aux = aux.substr(0, aux.indexOf("'"));
			$('#select_dashboard_rollout').selectpicker('val', aux);
			$('#select_dashboard_rollout').selectpicker('refresh');
			carrega_tree_rollout();
		}
	}
	//alert("sem erros no busca rollout");
}
function carrega_tabela_campos_rollout(rollout) {

	document.getElementById("rolloutid_campos_conf").value = rollout;

	if (rollout) {

	} else {
		var aux1 = sessionStorage.getItem("rolloutsid_" + geral.empresa_id);
		var aux;
		if (aux1) {
			aux = aux1.substr(aux1.indexOf("value='") + 7);
			aux = aux.substr(0, aux.indexOf("'"));
			rollout = aux;
			document.getElementById("rolloutid_campos_conf").value = aux;
		} else {
			alert("ERRO: Rollout 001");
			return;
		}
	}
	if (!$.fn.DataTable.isDataTable('#tabela_campos_rollout')) {

		var tabelaCmpos = $('#tabela_campos_rollout').DataTable({

			"scrollY": 500,
			"paging": false,
			"ajax": "./RolloutServlet?opt=2&rollout=" + rollout,
			"columns": [
				{ data: 'Ordem' },
				{ data: 'Campo' },
				{ data: 'Tipo' },
				{ data: 'Valor' },
				{ data: 'Status' }

			],
			"rowReorder": { dataSrc: 'Ordem' },
		});
		tabelaCmpos.draw();

		tabelaCmpos.on('row-reordered', function(e, diff, edit) {

			var dadosTabela = tabelaCmpos.data().toArray();
			$.ajax({
				type: "POST",
				data: { "opt": "15", "ordem": JSON.stringify(dadosTabela), "rollout": document.getElementById("rolloutid_campos_conf").value },
				//url: "http://localhost:8080/DashTM/D_Servlet",	  
				url: "./POControl_Servlet",
				cache: false,
				dataType: "text",
				success: onSuccess15
			});
			function onSuccess15(data) {
				$.alert(data.toString());
				carrega_gant();
			}

		});
		$('#tabela_campos_rollout tbody').on('click', 'tr', function() {
			if ($(this).hasClass('selected')) {
				$(this).removeClass('selected');
			}
			else {
				tabelaCmpos.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
			}
		});

	} else {

		$('#tabela_campos_rollout').DataTable().ajax.url("./RolloutServlet?opt=2&rollout=" + rollout).load();


	}
}
function stateFormatter(value, row, index) {
	if (row.campo2 === "Site ID") {
		//console.log(row);
		return {
			disabled: true,
			reorder: false
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
function edita_campos_rollout() {

	var rollout = $('#select_rollout_campos').val();
	if (rollout) {

	} else {
		$.alert("Selecione o rollout antes de iniciar a edição de um campo.");
		return;
	}
	var table = $('#tabela_campos_rollout').DataTable();
	var campos;

	table.rows('.selected').every(function(rowIdx, tableLoop, rowLoop) {
		//console.log(this.data());
		campos = this.data()['IDCAMPO'];
	});

	$.ajax({
		type: "POST",
		data: {
			"opt": "26",
			"campos": campos, "rollout": rollout
		},
		//url: "http://localhost:8080/DashTM/D_Servlet",	  
		url: "./POControl_Servlet",
		cache: false,
		dataType: "text",
		success: onSuccess26
	});
	function onSuccess26(data) {
		//info=[];
		//alert(data);
		info = JSON.parse(data);
		//alert(info['nome_campo']);

		var e, tipoc, tipoa, nome, desc, lista;

		document.getElementById("tipo_campo_rollout").value = info['tipo_Campo'];

		mostra_div(info['tipo_Campo']);

		//tipoc=e.options[e.selectedIndex].text;
		document.getElementById("lista_atributo").value = info['atributo'];
		document.getElementById("nome_campo_rollout").value = info['nome_campo'];
		document.getElementById("desc_campo_rollout").value = info['nome_campo'];
		document.getElementById("tipo_atributo_rollout").value = info['sub_tipo'];
		document.getElementById("trigger_pagamento").checked = info['trigger'];
		document.getElementById('percent_pagamento').value = info['percent'];
		document.getElementById("id_aux_campo").value = info['id_campo'];
		mostra_div_lista(info['sub_tipo']);

		$('#Modal_campos_rollout').modal('show');

	}

}

function atualiza_status() {
	if (geral.usuario.includes('masteradmin')) {
		$.ajax({
			type: "POST",
			data: { "opt": "26" },
			//url: "http://localhost:8080/DashTM/D_Servlet",	  
			url: "./RolloutServlet",
			cache: false,
			dataType: "text",
			success: atualizaStatusReposta
		});
		function atualizaStatusReposta(data) {

			$.alert(data.toString())
		}
	} else {
		$.alert("Sem permissao para essa função!")
	}
}

function deleta_campos_rollout(campos) {

	$.ajax({
		type: "POST",
		data: {
			"opt": "17",
			"campos": campos, "rollout": document.getElementById("rolloutid_campos_conf").value
		},
		//url: "http://localhost:8080/DashTM/D_Servlet",	  
		url: "./POControl_Servlet",
		cache: false,
		dataType: "text",
		success: onSuccess17
	});
	function onSuccess17(data) {

		//alert(data);
		carrega_gant();
	}

}

function add_campo_rollout() {
	//alert("adicionando campos");
	$('#btn_novo_campo_rollout').addClass("disabled");
	$('#btn_novo_campo_rollout').text('Aguarde a Finalização...');
	$('#btn_novo_campo_rollout').prop('disabled', true);
	$('#btn_edita_campo_rollout').addClass("disabled");
	$('#btn_edita_campo_rollout').text('Aguarde a Finalização...');
	$('#btn_edita_campo_rollout').prop('disabled', true);
	var e, tipoc, tipoa, nome, desc, lista, chk, percent;
	var rollout = document.getElementById("rolloutid_campos_conf").value;
	e = document.getElementById("tipo_campo_rollout");
	tipoc = e.options[e.selectedIndex].text;
	lista = document.getElementById("lista_atributo").value;
	nome = document.getElementById("nome_campo_rollout").value;
	desc = document.getElementById("desc_campo_rollout").value;
	chk = document.getElementById("trigger_pagamento").checked;
	percent = document.getElementById('percent_pagamento').value;

	if (tipoc == "Atributo") {
		e = document.getElementById("tipo_atributo_rollout");
		tipoa = e.options[e.selectedIndex].text;
	}

	$.ajax({
		type: "POST",
		data: {
			"opt": "14",
			"nome": nome,
			"tipo": tipoc,
			"desc": desc,
			"tipoatrb": tipoa,
			"lista": lista,
			"trigger": chk,
			"percent": percent,
			"rollout": rollout,
			"id_campo": document.getElementById("id_aux_campo").value
		},
		//url: "http://localhost:8080/DashTM/D_Servlet",	  
		url: "./POControl_Servlet",
		cache: false,
		dataType: "text",
		success: onSuccess14
	});

	function onSuccess14(data) {

		document.getElementById("id_aux_campo").value = "";
		//ordem_tabela();
		carrega_tabela_campos_rollout(rollout);
		//carrega_gant();
		$('#btn_novo_campo_rollout').removeClass("disabled");
		$('#btn_novo_campo_rollout').text('Novo Campo');
		$('#btn_novo_campo_rollout').prop('disabled', false);
		$('#btn_edita_campo_rollout').removeClass("disabled");
		$('#btn_edita_campo_rollout').text('Editar Campo');
		$('#btn_edita_campo_rollout').prop('disabled', false);
		$.alert(data.toString());
	}
}
function carrega_campos_rollout() {
	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
	var rolloutid;
	if (item) {
		rolloutid = item.value;
	} else {
		rolloutid = "Rollout1";
	}
	$.ajax({
		type: "POST",
		data: {
			"opt": "7.1", "rolloutid": rolloutid


		},
		//url: "http://localhost:8080/DashTM/D_Servlet",	  
		url: "./RolloutServlet",
		cache: false,
		dataType: "text",
		success: onSuccessCamposRollout
	});
	function onSuccessCamposRollout(data) {

		$("#select_campos_update_lote").html(data)
		$("#select_campos_update_lote").selectpicker('refresh');
	}
}
function carrega_campos_rollout2(rollout) {

	if (rollout) {
		$.ajax({
			type: "POST",
			data: {
				"opt": "7", "rolloutid": rollout


			},
			//url: "http://localhost:8080/DashTM/D_Servlet",	  
			url: "./RolloutServlet",
			cache: false,
			dataType: "text",
			success: onSuccessCamposRollout
		});
		function onSuccessCamposRollout(data) {
			$("#select_campo_regra_rollout").prop('disabled', false);
			$("#select_campo_regra_rollout").html(data)
			$("#select_campo_regra_rollout").selectpicker('refresh');

			$("#select_campo_acao_rollout").html(data)
			$("#select_campo_acao_rollout").selectpicker('refresh');
		}
	} else {
		$.alert("Informe o Rollout");
		return;
	}

}
function habilitaDesabilitaCondicaoRegra(opt) {

	if (opt) {
		$("#select_condicao_regra_rollout").prop('disabled', false);
		$("#select_condicao_regra_rollout").selectpicker('refresh');
	} else {
		$("#select_condicao_regra_rollout").prop('disabled', true);
		$("#select_condicao_regra_rollout").selectpicker('refresh');
	}
}
function habilitaDesabilitaCondicaoValorRegra(opt) {

	if (opt == 'contem') {
		$("#inputValorCondicaoRegra").prop('disabled', false);

	} else {
		$("#inputValorCondicaoRegra").prop('disabled', true);

	}
}
function montarCondicao(operador) {
	var condicao = sessionStorage.getItem("condicao");
	if (condicao) {
		condicao = JSON.parse(condicao);
		condicao.condicao.push({ rollout: $('#select_rollout_regra_rollout').val(), campo: $('#select_campo_regra_rollout').val(), condicao: $('#select_condicao_regra_rollout').val(), valor: $('#inputValorCondicaoRegra').val(), operador: operador });
	} else {
		condicao = JSON.parse('{"condicao":[],"acao":[]}');
		condicao.condicao.push({ rollout: $('#select_rollout_regra_rollout').val(), campo: $('#select_campo_regra_rollout').val(), condicao: $('#select_condicao_regra_rollout').val(), valor: $('#inputValorCondicaoRegra').val(), operador: operador });
	}

	sessionStorage.setItem("condicao", JSON.stringify(condicao));
	$('#tabela_condicao').find('tbody').append('<tr><td>' + $('#select_rollout_regra_rollout').val() + '</td><td>' + $('#select_campo_regra_rollout').val() + '</td><td>' + $('#select_condicao_regra_rollout').val() + '</td><td>' + $('#inputValorCondicaoRegra').val() + '</td><td>' + operador + '</td></tr>');
	$("#select_acao_regra").prop('disabled', false);
	$("#select_acao_regra").selectpicker('refresh');
}
function montarAcao() {
	var condicao = sessionStorage.getItem("condicao");
	if (condicao) {
		condicao = JSON.parse(condicao);
		condicao.acao.push({ acao: $('#select_acao_regra').val(), campo: $('#select_campo_acao_rollout').val(), valor: $('#inputValorAcaoRegra').val() });
	} else {
		condicao = JSON.parse('{"condicao":[],"acao":[]}');
		condicao.acao.push({ acao: $('#select_acao_regra').val(), campo: $('#select_campo_acao_rollout').val(), valor: $('#inputValorAcaoRegra').val() });
	}

	sessionStorage.setItem("condicao", JSON.stringify(condicao));
	$('#tabela_acao').find('tbody').append('<tr><td>' + $('#select_acao_regra').val() + '</td><td>' + $('#select_campo_acao_rollout').val() + '</td><td>' + $('#inputValorAcaoRegra').val() + '</td></tr>');

}
function salvarRegra(opt) {
	var timestamp = Date.now();
	var condicao = sessionStorage.getItem("condicao");
	condicao = JSON.parse(condicao);
	condicao.nome = $('#inputNomeRegra').val()
	if (condicao) {
		$.ajax({
			type: "POST",
			data: { "opt": "37", "regra": JSON.stringify(condicao), "id": timestamp, "executar": opt },
			url: "./RolloutServlet",
			cache: false,
			dataType: "text",
			success: RegraCriada
		});
		function RegraCriada(data) {
			$.alert("Regra criada com sucesso!");
			sessionStorage.removeItem("condicao");
			$("#inputValorAcaoRegra").val('');
			$("#inputValorAcaoRegra").prop('disabled', true);
			$('#select_acao_regra').selectpicker('deselectAll');
			$('#select_campo_acao_rollout').selectpicker('deselectAll');
			$('#select_rollout_regra_rollout').selectpicker('deselectAll');
			$('#select_campo_regra_rollout').selectpicker('deselectAll');
			$('#select_condicao_regra_rollout').selectpicker('deselectAll');
			$("#inputValorCondicaoRegra").val('');
			$("#inputValorCondicaoRegra").prop('disabled', true);
			$("#inputNomeRegra").val('');
			$('#tabela_acao').find('tbody').html('');
			$('#tabela_condicao').find('tbody').html('');
			$('#tabela_regras_rollout').DataTable().ajax.reload();
		}
	} else {
		$.alert("Regra não esta configurada corretamente");


	}
}
function preparaAcaoRegra(opt) {
	$("#inputValorAcaoRegra").prop('disabled', true);
	if (opt) {
		if (opt == "EnviarEmail") {
			$("#inputValorAcaoRegra").prop('disabled', false);
		} else if (opt == "AtualizaCampoRollout") {
			$("#select_campo_acao_rollout").prop('disabled', false);
			$("#select_campo_acao_rollout").selectpicker('refresh');
			$("#inputValorAcaoRegra").prop('disabled', false);
		}
	}
}
function verifica_campo_tipo(campo, origem) {
	//alert(campo);
	$.ajax({
		type: "POST",
		data: {
			"opt": "6",
			"campo": campo

		},
		//url: "http://localhost:8080/DashTM/D_Servlet",	  
		url: "./RolloutServlet",
		cache: false,
		dataType: "text",
		success: onSuccessCampoTipo
	});
	function onSuccessCampoTipo(data) {
		if (origem == "rollout") {
			document.getElementById("tipo_campo_lote").value = data;
			if (data == "Data") {
				$("#tipo_campos_update_lote").html("<br><hr><br><p><table data-toggle='table' style='width:80%'><tr><td>Valor</td><td><input id='data_atbr_lote' type='text' class='form-control' value=''></td></tr></table></p>")
				$("#data_atbr_lote").jqxDateTimeInput({ width: '300px', height: '25px', showWeekNumbers: true, allowNullDate: true, showFooter: true, clearString: 'Limpar', formatString: 'dd/MM/yyyy', culture: 'pt-BR' });
				$("#data_atbr_lote").jqxDateTimeInput('val', '');
			}
			else if (data == "Milestone") {
				$("#tipo_campos_update_lote").html("<br><hr><br><p><table data-toggle='table' style='width:80%'><tr><td>Inicio Planejado</td><td><input id='inicio_planejado_lote' type='text' class='form-control' value=''></td></tr><tr><td>Fim Planejado</td><td><input id='fim_planejado_lote' type='text' class='form-control' value=''></td></tr><tr><td>Inicio Real</td><td><input type='text' id='inicio_real_lote' class='form-control' value=''></td></tr><tr><td>Fim Real</td><td><input type='text' id='fim_real_lote' class='form-control' value=''></td></tr><tr><td>Anotações</td><td><input id='anot_lote' type='text' class='form-control' value=''></td></tr><tr><td>Responsável</td><td><input id='resp_lote' type='text'class=' form-control' value=''></td></tr></table><p>")
				$("#inicio_planejado_lote").jqxDateTimeInput({ width: '300px', height: '25px', showWeekNumbers: true, allowNullDate: true, showFooter: true, clearString: 'Limpar', formatString: 'dd/MM/yyyy', culture: 'pt-BR' });
				$("#inicio_planejado_lote").jqxDateTimeInput('val', '');
				$("#fim_planejado_lote").jqxDateTimeInput({ width: '300px', height: '25px', showWeekNumbers: true, allowNullDate: true, showFooter: true, clearString: 'Limpar', formatString: 'dd/MM/yyyy', culture: 'pt-BR' });
				$("#fim_planejado_lote").jqxDateTimeInput('val', '');
				$("#inicio_real_lote").jqxDateTimeInput({ width: '300px', height: '25px', showWeekNumbers: true, allowNullDate: true, showFooter: true, clearString: 'Limpar', formatString: 'dd/MM/yyyy', culture: 'pt-BR' });
				$("#inicio_real_lote").jqxDateTimeInput('val', '');
				$("#fim_real_lote").jqxDateTimeInput({ width: '300px', height: '25px', showWeekNumbers: true, allowNullDate: true, showFooter: true, clearString: 'Limpar', formatString: 'dd/MM/yyyy', culture: 'pt-BR' });
				$("#fim_real_lote").jqxDateTimeInput('val', '');
			} else {
				$("#tipo_campos_update_lote").html("<br><hr><br><p><table data-toggle='table' style='width:80%'><tr><td>Valor</td><td><input id='attbr_lote' type='text' class='form-control' value=''></td></tr></table></p>")

			}
		}
		if (origem == "filtro_operacional") {
			if (data == "Data") {
				$("#tipo_campos_filtros_mapaOperacional").html("<br><hr><label style='color:black'>Selecione o período de tempo para filtro:</label><br><p><table data-toggle='table' style='width:80%'><tr><td style='color:black'>Valor</td><td><input id='data_atbr_filtro_mapaOperacional_data' type='text' class='form-control' value=''></td></tr></table></p><br><br><button class='btn btn-primary' onclick=registra_filtro_operacional('data')>Adicionar Filtro</button>")
				$("#data_atbr_filtro_mapaOperacional_data").jqxDateTimeInput({ width: '300px', height: '25px', selectionMode: 'range', showWeekNumbers: true, allowNullDate: true, showFooter: true, clearString: 'Limpar', formatString: 'dd/MM/yyyy', culture: 'pt-BR' });
				$("#data_atbr_filtro_mapaOperacional_data").jqxDateTimeInput('val', '');
			} else {
				$("#tipo_campos_filtros_mapaOperacional").html("<br><hr><label style='color:black'>Selecione a condição e informe o valor:</label><br><p><table data-toggle='table' style='width:80%'><tr style='padding:5px'><td style='padding:5px'><select id='select_condicao_filtro_mapa_operacional' class='selectpicker' data-live-search='true'><option value='equal'>Igual a</option><option value='contem'>Contém</option></select></td></tr><tr style='padding:5px'><td style='padding:5px'><input type='text' id='data_atbr_filtro_mapaOperacional_string' value='' class='form-control'></td></tr></table></p><br><br><button class='btn btn-primary' onclick=registra_filtro_operacional('texto')>Adicionar Filtro</button>")
				$('#select_condicao_filtro_mapa_operacional').selectpicker('refresh');
			}
		}
	}
}
function registra_filtro_operacional(tipo) {
	var opt = document.getElementById('opt_mapa_operacinal').value;

	filtros = JSON.parse(sessionStorage.getItem("filtrosOperacional_" + opt));
	if (filtros == null) {
		filtros = { "filtros": [] };
	}
	var aux = {};
	if (tipo == 'texto') {
		aux.filtersvalue = document.getElementById("data_atbr_filtro_mapaOperacional_string").value;
		document.getElementById("data_atbr_filtro_mapaOperacional_string").value = '';
		aux.filtercondition = $('#select_condicao_filtro_mapa_operacional').selectpicker('val');
		$('#select_condicao_filtro_mapa_operacional').selectpicker('val', '');
		aux.datafield = $('#select_campo_mapa_operacinal').selectpicker('val');
		$('#select_campo_mapa_operacinal').selectpicker('val', '');
		filtros.filtros.push(aux);
		aux = {};
	}
	if (tipo == 'data') {
		var selection = $('#data_atbr_filtro_mapaOperacional_data').jqxDateTimeInput('getRange');
		aux.datafield = $('#select_campo_mapa_operacinal').selectpicker('val');
		aux.filtercondition = 'GREATHER EQUAL THAN';
		aux.filtersvalue = moment(selection.from).format('L')
		filtros.filtros.push(aux);
		aux = {};
		aux.datafield = $('#select_campo_mapa_operacinal').selectpicker('val');
		aux.filtercondition = 'LESS EQUAL THAN';
		aux.filtersvalue = moment(selection.to).format('L')
		$('#select_campo_mapa_operacinal').selectpicker('val', '');
		filtros.filtros.push(aux);
		aux = {};
	}

	$("#resumo_tipo_campos_filtros_mapaOperacional").html(JSON.stringify(filtros));
	sessionStorage.setItem("filtrosOperacional_" + opt, JSON.stringify(filtros))

}

function atualiza_lote() {

	var rows_indexes = $('#jqxgrid').jqxGrid('getselectedrowindexes');
	var tipo = document.getElementById("tipo_campo_lote").value;
	if (tipo == 'Milestone') {
		for (var i = 0; i < rows_indexes.length; i++) {
			if ($("#inicio_planejado_lote").jqxDateTimeInput('val') != '') {
				registra_mudança_campos(rows_indexes[i], "sdate_pre_" + $('#select_campos_update_lote').val(), $("#inicio_planejado_lote").jqxDateTimeInput('getDate'), 'datetimeinput', $('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "sdate_pre_" + $('#select_campos_update_lote').val()));
			}
			if ($("#fim_planejado_lote").jqxDateTimeInput('val') != '') {
				registra_mudança_campos(rows_indexes[i], "edate_pre_" + $('#select_campos_update_lote').val(), $("#fim_planejado_lote").jqxDateTimeInput('getDate'), 'datetimeinput', $('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "edate_pre_" + $('#select_campos_update_lote').val()));
			}
			if ($("#inicio_real_lote").jqxDateTimeInput('val') != '') {
				registra_mudança_campos(rows_indexes[i], "sdate_" + $('#select_campos_update_lote').val(), $("#inicio_real_lote").jqxDateTimeInput('getDate'), 'datetimeinput', $('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "sdate_" + $('#select_campos_update_lote').val()));
			}
			if ($("#fim_real_lote").jqxDateTimeInput('val') != '') {
				registra_mudança_campos(rows_indexes[i], "edate_" + $('#select_campos_update_lote').val(), $("#fim_real_lote").jqxDateTimeInput('getDate'), 'datetimeinput', $('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "edate_" + $('#select_campos_update_lote').val()));
			}
			if (document.getElementById("anot_lote").value != '') {
				registra_mudança_campos(rows_indexes[i], "udate_" + $('#select_campos_update_lote').val(), document.getElementById("anot_lote").value, 'textbox', $('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "udate_" + $('#select_campos_update_lote').val()));
			}
			if (document.getElementById("resp_lote").value != '') {
				registra_mudança_campos(rows_indexes[i], "resp_" + $('#select_campos_update_lote').val(), document.getElementById("resp_lote").value, 'textbox', $('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], "resp_" + $('#select_campos_update_lote').val()));
			}
		}
	} else if (tipo == 'Data') {
		for (var i = 0; i < rows_indexes.length; i++) {
			registra_mudança_campos(rows_indexes[i], $('#select_campos_update_lote').val(), $("#data_atbr_lote").jqxDateTimeInput('getDate'), 'datetimeinput', $('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], $('#select_campos_update_lote').val()));
		}
	} else {
		for (var i = 0; i < rows_indexes.length; i++) {
			if (document.getElementById("attbr_lote").value != '') {
				registra_mudança_campos(rows_indexes[i], $('#select_campos_update_lote').val(), document.getElementById("attbr_lote").value, 'textbox', $('#jqxgrid').jqxGrid('getcellvalue', rows_indexes[i], $('#select_campos_update_lote').val()));
			}
		}
	}
	registra_mudancas_bd(g_changes);
	$("#tipo_campos_update_lote").html('');
	$('#modal_update_lotes').modal('hide');

}
function carrega_gant() {
	var timestamp = Date.now();
	var sites = [];
	var me, container, save_button, addButton, cFilterButton, deleteButton = "";

	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');

	var rolloutid;
	if (item != null) {
		rolloutid = item.value;
		if (rolloutid == "Rollout") {
			return;
		}
	} else {

		var aux1 = sessionStorage.getItem("rolloutsid_" + geral.empresa_id);
		var aux;
		if (aux1) {
			aux = aux1.substr(aux1.indexOf("value='") + 7);
			aux = aux.substr(0, aux.indexOf("'"));
			rolloutid = aux;
		} else {
			rolloutid = "";
		}
	}
	$.getJSON('./RolloutServlet?opt=1&rolloutid=' + rolloutid + '&_=' + timestamp, function(data) {

		var sites_aux = [];
		sites_aux = data['sites'];
		//console.log(sites_aux);
		var validaDataActual = function(cell, value) {
			if (value == '') {
				return true;
			}
			var d1 = new Date();
			if (value > d1.getTime()) {
				return { result: false, message: "Inicio/Fim real não podem ser maior que data atual." };
			}
			return true;
		}
		var d = new Date();
		//alert(moment(d).format("L"));
		var cellclassInicioReal = function(row, columnfield, value) {
			var coluna = columnfield.substr(columnfield.indexOf("_") + 1);
			var data = $("#jqxgrid").jqxGrid('getrowdata', row);
			if (value == "") {
				return 'nada';
			} else if (moment(value).format("L") == moment(d).format("L") && data["edate_" + coluna] == "") {
				return 'yellow';
			} else if (moment(value).format("L") < moment(d).format("L") && data["edate_" + coluna] == "") {
				return 'red';
			} else if (moment(value).format("L") == moment(d).format("L") && data["edate_" + coluna] != "") {
				return 'green';
			} else {
				return 'nada';
			}
		};
		var cellclassCampoRegra = function(row, columnfield, value) {

			return 'orange';

		};
		var cellclassFimReal = function(row, columnfield, value) {
			if (value == "") {
				return 'nada';
			} else if (moment(value).format("L") == moment(d).format("L")) {
				return 'green';
			}
		};
		var siteMarcadoIntegrado = function(row, columnfield, value, defaulthtml, columnproperties, rowdata) {
			if (sites_aux.indexOf(value) > 0) {
				return '<span style="margin: 4px; margin-top:8px; float: ' + columnproperties.cellsalign + '; color: #008000;">' + value + '</span>';
			}
			else {
				return '<span style="margin: 4px; margin-top:8px; float: ' + columnproperties.cellsalign + '; color: #ff0000;">' + value + '</span>';
			}
		}
		for (var i = 0; i < data['campos2'].length; i++) {
			texto_a = (data['campos2'][i].text).toString();

			if (texto_a == "Site ID") {
				data['campos2'][i].cellsrenderer = siteMarcadoIntegrado;
			}
			if (texto_a == "Inicio Real") {
				data['campos2'][i].validation = validaDataActual;
				data['campos2'][i].cellclassname = cellclassInicioReal;
			}
			if (texto_a == "Fim Real") {
				data['campos2'][i].validation = validaDataActual;
				data['campos2'][i].cellclassname = cellclassFimReal;
			}

			if (data['campos2'][i].cellclassname == "marcar") {
				console.log(data['campos2'][i].cellclassname);
				data['campos2'][i].cellclassname = cellclassCampoRegra;
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
		for (var j = 0; j < data['campos2'].length; j++) {
			if (data['campos2'][j].datafield.search("resp_") >= 0) {
				data['campos2'][j]['createeditor'] = function(row, column, editor) {
					editor.jqxComboBox({ source: PeopleAdapter, promptText: 'Responsável' });
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
				}
			}

		};

		var filterChanged = false;
		//var dataAdapter = new $.jqx.dataAdapter(source);
		var dataadapter = new $.jqx.dataAdapter(source, {

			formatData: function(data) {
				var checked;
				if ($("#jqxcheckbox_EquipesOnSite").length) {
					checked = $("#jqxcheckbox_EquipesOnSite").jqxCheckBox('checked');
					data.equipesnosite = checked;
				} else {
					data.equipesnosite = "false";
				}

				data.opt = 9;
				data.rolloutid = rolloutid;
				data._ = timestamp;
				if ($("#AtividadeHoje").length) {
					data.atividadehoje = $("#AtividadeHoje").jqxCheckBox('checked');
				} else {
					data.atividadehoje = "false";
				}
				if ($("#AtividadeSemana").length) {
					data.atividadesemana = $("#AtividadeSemana").jqxCheckBox('checked');
				} else {
					data.atividadesemana = "false";
				}
				if ($("#AtividadeMes").length) {
					data.atividademes = $("#AtividadeMes").jqxCheckBox('checked');
				} else {
					data.atividademes = "false";
				}
				if ($("#inputBusca").length) {
					data.buscaSite = $("#inputBusca").jqxInput('val');
				} else {
					data.buscaSite = "";
				}


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
				theme: 'light',
				source: dataadapter,
				columnsresize: true,
				pageable: true,
				editable: true,
				//showfilterrow: true,
				showtoolbar: true,
				filterable: true,
				pagesize: 40,
				rowsheight: 40,
				autoshowfiltericon: true,
				ready: function() {

					var localizationObject = {
						filterstringcomparisonoperators: ['contem', 'não contem', 'vazio', 'não vazio'],
						// filter numeric comparison operators.
						filternumericcomparisonoperators: ['less than', 'greater than', 'Empty', 'not Empty'],
						// filter date comparison operators.
						filterdatecomparisonoperators: ['menor que', 'maior que', 'maior ou igual a', 'menor ou igual a', 'igual a', 'vazio', 'não vazio'],
						// filter bool comparison operators.
						filterbooleancomparisonoperators: ['equal', 'not equal'],
						currencysymbol: "R$",
						decimalseparator: ",",
						thousandsseparator: "."
					}
					$("#jqxgrid").jqxGrid('localizestrings', localizationObject);
				},
				updatefilterconditions: function(type, defaultconditions) {
					var stringcomparisonoperators = ['CONTAINS', 'DOES_NOT_CONTAIN', 'EMPTY', 'NOT_EMPTY'];
					var numericcomparisonoperators = ['LESS_THAN', 'GREATER_THAN', 'EMPTY', 'NOT_EMPTY'];
					var datecomparisonoperators = ['LESS_THAN', 'GREATER_THAN', 'GREATER_THAN_OR_EQUAL', 'LESS_THAN_OR_EQUAL', 'EQUAL', 'EMPTY', 'NOT_EMPTY'];
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
				rendertoolbar: function(toolbar) {
					var me = this;
					toolbar.empty();
					container = $("<div style='overflow: hidden; position: relative; margin: 1px;'></div>");
					save_button = $("<input type=\"button\" class=\"btn btn-primary\" style='float: left; margin-left: 5px' value=\"Salvar\" id='jqxButton_salvar'>");
					drop_button = $("<div style='float: left;' id='dropDownButton_op_rollout'><div style='border: none;' id='jqxTree_bt_rollout'><ul><li><a style='float: left; margin-left: 5px;' href='#' onclick='SyncRollout();'>Atualizar</a></li><li><a style='float: left; margin-left: 5px;' href='#' onclick=\"preparaAddnewLine()\">Nova Atividade</a></li><li><a style='float: left; margin-left: 5px;' href=\"#\" data-toggle=\"modal\" data-target=\"#modal_upload_rollout\">Importar</a></li><li><a style='float: left; margin-left: 5px;' href='#' onclick='downloadFunc()'>Exportar</a></li><li><a style='float: left; margin-left: 5px;' href=\"#\" data-toggle=\"modal\" data-target=\"#modal_rollout_history\">Histórico de Mudanças</a></li><li><a style='float: left; margin-left: 5px;' href=\"#\" onclick=menu('checklist_review')>Checklists</a></li><li><a style='float: left; margin-left: 5px;' href=\"#\" onclick='atualizaperiodo()' >Atualiza Status</a></li><li><a style='float: left; margin-left: 5px;' href='#' onclick=\"preparaAtualizacaoLotes()\">Atualização em Lotes</a></li><li><a style='float: left; margin-left: 5px;' href='#' data-toggle=\"modal\" data-target=\"#Modal_EnviaMengagem\">Nova Mensagem</a></li></ul></div></div>");
					drop_button_filtro = $("<div style='float: left;' id='dropDownButton_filtro_rollout'><div style='border: none;height:150px' id='jqxTree_bt_rollout_filtro'><ul><li> <div id='AtividadeHoje'>Atividades para hoje</div></li><li><div id='AtividadeSemana'>Atividades para semana</div></li><li><div id='AtividadeMes'>Atividades para o mes</div></li></ul></div></div>");
					divtextoBusca = $("<div style=\"float:right\"></div>");
					textoBusca = $("<input type=\"text\" id=\"inputBusca\"/>");

					cFilterButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Remover Filtros</a>");
					deleteButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Remover Atividade</a>");
					checkEquipeOnSite = $("<div id='jqxcheckbox_EquipesOnSite' style='margin-top:10px;float:left'>Exibir Apenas Sites com Equipe</div>");
					//syncButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Sync</a>");
					//downloadButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href='#'>Exportar</a>");
					//uploadButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href=\"#\" data-toggle=\"modal\" data-target=\"#modal_upload_rollout\">Importar</a>");
					//syncMongoButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href=\"#\" >Sync TO Mongo</a>");
					//RolloutMapButton = $("<a class=\"btn btn-primary\" style='float: left; margin-left: 5px;' href=\"#\" >Mapa(Beta)</a>");
					container.append(save_button);
					container.append(drop_button);
					container.append(drop_button_filtro);
					container.append(cFilterButton);
					//container.append(downloadButton);
					//container.append(uploadButton);
					//container.append(syncButton);
					container.append(deleteButton);
					//container.append(RolloutMapButton);
					container.append(checkEquipeOnSite);
					container.append(divtextoBusca);
					toolbar.append(container);
					save_button.jqxButton({ theme: 'light' });
					divtextoBusca.append(textoBusca);

					checkEquipeOnSite.jqxCheckBox({ theme: 'light' });
					textoBusca.jqxInput({ placeHolder: "Busca por Site", width: 250, minLength: 1 });

					drop_button.jqxDropDownButton({ width: 150, height: 30, theme: 'light' });
					drop_button_filtro.jqxDropDownButton({ width: 230, height: 30, theme: 'light' });
					$('#dropDownButton_op_rollout').jqxDropDownButton('setContent', '<div style="position: relative; margin-left: 3px; margin-top: 5px;">Operações</div>');
					$('#dropDownButton_filtro_rollout').jqxDropDownButton('setContent', '<div style="position: relative; margin-left: 3px; margin-top: 5px;">Filtros de Atividades</div>');
					$('#inputBusca').on('change', function(event) {
						carrega_gant();
					});
					$("#jqxcheckbox_EquipesOnSite").on('change', function(event) {
						carrega_gant();
					});

					$('#jqxTree_bt_rollout').on('select', function(event) {

						$("#dropDownButton_op_rollout").jqxDropDownButton('close');
					});
					$('#jqxTree_bt_rollout_filtro').on('select', function(event) {

						$("#dropDownButton_filtro_rollout").jqxDropDownButton('close');
					});

					$("#jqxTree_bt_rollout").jqxTree({ width: 200 });
					$("#jqxTree_bt_rollout_filtro").jqxTree({ width: 220 });
					$("#AtividadeHoje").jqxCheckBox({ width: 220, height: 25 });
					$("#AtividadeSemana").jqxCheckBox({ width: 220, height: 25 });
					$("#AtividadeMes").jqxCheckBox({ width: 220, height: 25 });
					$("#AtividadeHoje").on('change', function(event) {
						carrega_gant();
					});
					$("#AtividadeSemana").on('change', function(event) {
						carrega_gant();
					});
					$("#AtividadeMes").on('change', function(event) {
						carrega_gant();
					});

					cFilterButton.jqxButton({ theme: 'light' });
					//downloadButton.jqxButton();
					//RolloutMapButton.jqxButton({theme:'light'});
					//uploadButton.jqxButton();
					//syncButton.jqxButton();
					//syncMongoButton.jqxButton();
					deleteButton.jqxButton({ template: "danger" });

					// RolloutMapButton.click(function(event){
					// 	$.alert("Função em Manutenção. Utilize o Mapa Operacional no Menu.");
					// 	return;
					// 	filtros={"filtros":[]};
					// 	var aux={};
					// 	var filtersinfo = $('#jqxgrid').jqxGrid('getfilterinformation');
					//console.log(filtersinfo);
					// 	for(var j=0;j<filtersinfo.length;j++){
					//     	for (var i = 0; i < filtersinfo[j].filter.getfilters().length; i++) {
					//         	aux.filtersvalue=filtersinfo[j].filter.getfilters()[i].value;
					//         	aux.filtercondition=filtersinfo[j].filter.getfilters()[i].condition;
					//         	aux.datafield = filtersinfo[j].filtercolumn;

					//         	filtros.filtros.push(aux);
					//         	aux={};
					//     	}

					//     }
					// 	sessionStorage.setItem("rollout_map_filtro",JSON.stringify(filtros));
					// 	$(".janelas").hide();
					//	document.getElementById("mapa_central").style.display = "block";
					//	inicializa_mapa_full(1);


					// });

					cFilterButton.click(function(event) {
						$("#jqxgrid").jqxGrid('clearfilters');
					});
					save_button.click(function(event) {
						$('#jqxButton_salvar').jqxButton('val', 'Aguarde...');
						$('#jqxButton_salvar').jqxButton({
							disabled: true
						});
						$('#jqxButton_salvar').jqxButton('render');
						registra_mudancas_bd(g_changes);

					});
					deleteButton.click(function(event) {
						rm_row_rollout();
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
	$("#jqxgrid").on('cellendedit', function(event) {
		var args = event.args;
		
		registra_mudança_campos(args.rowindex, args.datafield, args.value, args.columntype, args.oldvalue);
	});
	$('#jqxLoader_rolout').jqxLoader('close');


}
function preparaAddnewLine() {
	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');

	var rolloutid;
	if (item != null) {
		rolloutid = item.value;
		if (rolloutid == "Rollout") {
			return;
		}
	} else {
		alert("Selecione um rollout para adicionar uma nova atividade");
		return;
	}
	$.ajax({
		type: "POST",
		data: {
			"opt": "23", "rolloutid": rolloutid

		},
		//url: "http://localhost:8080/DashTM/D_Servlet",	  
		url: "./POControl_Servlet",
		cache: false,
		dataType: "text",
		success: onSuccess23
	});
	function onSuccess23(data) {
		sessionStorage.setItem("camposRollout", data);
		var campos = JSON.parse(data);
		var htmlaux;
		$("#row_add_fields").html("");
		for (var indice = 0; indice < campos.length; indice++) {
			if (campos[indice].tipo == "Data") {
				htmlaux = " <div class='row'  style='margin-top:5px;color:black;'>" +
					"<div class='col-md-3'><label style='color:gray'>" + campos[indice].nome + "</label></div>" +
					"<div class='col-md-3'><div class='addLinhaRoloutdt row_rollout' nome='" + campos[indice].nome + "' tipo='" + campos[indice].tipo + "' id='_" + campos[indice].nome2 + "'></div></div>" +
					"</div>";
			} else if (campos[indice].tipo == "Milestone") {
				htmlaux = " <div class='row'  style='margin-top:5px;color:black;'>" +
					"<div class='col-md-3'><label style='color:gray'>" + campos[indice].nome + "</label></div>" +
					"<div class='col-md-3'><div class='addLinhaRoloutMilestone row_rollout' nome='" + campos[indice].nome + "' tipo='" + campos[indice].tipo + "' id='_" + campos[indice].nome2 + "'></div></div>" +
					"</div>";
			} else {
				htmlaux = " <div class='row'  style='margin-top:5px;color:black;'>" +
					"<div class='col-md-3'><label style='color:gray'>" + campos[indice].nome + "</label></div>" +
					"<div class='col-md-3'><input class='form-control row_rollout' nome='" + campos[indice].nome + "' tipo='" + campos[indice].tipo + "' id='_" + campos[indice].nome2 + "'/></div>" +
					"</div>";
			}
			$("#row_add_fields").append(htmlaux);

		}
		$('.addLinhaRoloutdt').jqxDateTimeInput({ formatString: 'dd/MM/yyyy', width: 250, height: 30, culture: 'pt-BR', theme: 'bootstrap' });
		$('.addLinhaRoloutMilestone').jqxDateTimeInput({ formatString: 'dd/MM/yyyy', width: 250, height: 30, culture: 'pt-BR', theme: 'bootstrap', selectionMode: 'range' });
		//$("#table_row_fields").bootstrapTable();
		//$('input[type=data_row]').w2field('date', { format: 'dd/mm/yyyy'});

		$('#modal_row_add').modal('show');
	}

}
function preparaAtualizacaoLotes() {

	if (g_changes.atualizacoes > 0) {
		$.confirm({
			title: '!',
			type: 'red',
			content: 'Dectamos mudanças não salvas. Essas mudanças serão descartadas caso deseje continuar.',
			buttons: {
				Continuar: function() {
					g_changes.atualizacoes = 0;
					g_changes.campos = [];
					document.getElementById("linhas_selecionadas_lote").value = $('#jqxgrid').jqxGrid('getselectedrowindexes').length + " Linhas Selecionadas";
					carrega_campos_rollout();
					$('#modal_update_lotes').modal('show');
				},
				Cancelar: function() {

				}

			}
		});
	} else {
		document.getElementById("linhas_selecionadas_lote").value = $('#jqxgrid').jqxGrid('getselectedrowindexes').length + " Linhas Selecionadas";
		carrega_campos_rollout();
		$('#modal_update_lotes').modal('show');
	}
}
function envia_mensagem_rollout() {
	var usuarios = $("#func_mensagem").selectpicker('val');
	var mensagem = document.getElementById("mensagem_input").value;

	if (!mensagem) {
		$.alert("Sem Mensagem para enviar");
		return;
	}
	if (!usuarios) {
		$.alert("Destinatário não selecionados");
		return;
	}
	$.ajax({
		type: "POST",
		data: { "opt": "16", "usuario": usuarios.toString(), "mensagem": mensagem },
		//url: "http://localhost:8080/DashTM/D_Servlet",	  
		url: "./POControl_Servlet",
		cache: false,
		dataType: "text"

	});
}
function SyncToMongoDB() {
	if (geral.usuario.includes('masteradmin')) {
		$.ajax({
			type: "POST",
			data: {
				"opt": "14"

			},
			//url: "http://localhost:8080/DashTM/D_Servlet",	  
			url: "./RolloutServlet",
			cache: false,
			dataType: "text"

		});
	} else {
		$.alert("Sem privilégio para essa operação!");
	}
}
function SyncRollout() {

	//carrega_gant();
	$("#jqxgrid").jqxGrid("updatebounddata");
}
function downloadFunc() {
	//$.alert("Função Temporariamente desabilitada, devido migracao do banco de dados! estimativa de retorno da função 22/02/2019 as 18:00hs!");
	//return;
	filtros = { "filtros": [] };
	var rows = $('#jqxgrid').jqxGrid('getdisplayrows');
	var rowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes');
	$.confirm({
		title: 'Download do Rollout',
		content: 'Selecione abaixo o método de downlaod.',
		type: 'orange',
		typeAnimated: true,
		columnClass: 'col-md-6 col-md-offset-3',
		buttons: {
			PorLinhas: {
				text: "Linhas Selecionadas(" + rowindexes.length + ")",
				action: function() {
					for (var v = 0; v < rowindexes.length; v++) {
						filtros.filtros.push($('#jqxgrid').jqxGrid('getrowid', rowindexes[v]));
					}
					var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
					var rolloutid;
					if (item != null) {
						rolloutid = item.value;
					} else {
						rolloutid = "Rollout1";
					}
					$.ajax({
						url: './RolloutServlet?opt=3&tipo=selecionadas&rolloutid=' + rolloutid,
						method: 'GET',
						data: { "filtros": JSON.stringify(filtros), "rolloutid": rolloutid },
						xhrFields: {
							responseType: 'blob'
						},
						success: function(data) {
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
			PorFiltro: {
				text: "Filtro corrente (" + rows.length + " Linhas)",
				action: function() {

					filtros = { "filtros": [] };
					var aux = {};
					var filtersinfo = $('#jqxgrid').jqxGrid('getfilterinformation');
					//console.log(filtersinfo);
					for (var j = 0; j < filtersinfo.length; j++) {
						for (var i = 0; i < filtersinfo[j].filter.getfilters().length; i++) {
							aux.filtersvalue = filtersinfo[j].filter.getfilters()[i].value;
							aux.filtercondition = filtersinfo[j].filter.getfilters()[i].condition;
							aux.datafield = filtersinfo[j].filtercolumn;

							filtros.filtros.push(aux);
							aux = {};
						}

					}
					//alert(JSON.stringify(filtros));
					var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
					var rolloutid;
					if (item != null) {
						rolloutid = item.value;
					} else {
						rolloutid = "Rollout1";
					}
					$.ajax({
						url: './RolloutServlet?opt=3&tipo=filtro&rolloutid=' + rolloutid,
						method: 'GET',
						data: { "filtros": JSON.stringify(filtros), "rolloutid": rolloutid },
						xhrFields: {
							responseType: 'blob'
						},
						success: function(data) {
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
			Completo: {
				text: "Completo",

				action: function() {
					var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
					var rolloutid;
					if (item) {
						rolloutid = item.value;
					} else {
						rolloutid = "Rollout1";
					}
					$.ajax({
						url: './RolloutServlet?opt=3&tipo=completo&rolloutid=' + rolloutid,
						method: 'GET',
						data: { "filtros": JSON.stringify(filtros), "rolloutid": rolloutid },
						xhrFields: {
							responseType: 'blob'
						},
						success: function(data) {
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
			Cancelar: function() { }

		}
	});
}
function registra_mudança_campos(index, campo, valor, tipo, oldvalue) {
	var recid = $("#jqxgrid").jqxGrid('getrowid', index);
	if (recid) {
		if (campo == "check_linha") {
			$("#jqxgrid").jqxGrid('selectrow', index);
			aux = deletar.indexOf($("#jqxgrid").jqxGrid('getrowid', index));
			//alert("rowid: "+$("#jqxgrid").jqxGrid('getrowid', index));
			//alert("index: "+ index);
			//alert("recid: "+ linha);

			//console.log(deletar);
		} else {
			if (tipo) {
				if (tipo == 'datetimeinput') {
					//alert(valor);
					//var d = new Date(valor);
					var localdata = moment(valor).format('L');
					//alert(localdata);
					if (localdata != 'Invalid date') {

						localizacao = { id: recid, colum: campo, value: localdata, tipoc: tipo, oldvalue: oldvalue };
					} else {

						localizacao = { id: recid, colum: campo, value: '', tipoc: 'textbox', oldvalue: oldvalue };
					}
				} else {
					localizacao = { id: recid, colum: campo, value: valor, tipoc: tipo, oldvalue: oldvalue }
				}
				g_changes.atualizacoes = g_changes.atualizacoes + 1;
				g_changes.campos.push(localizacao);
			} else {
				localizacao = { id: recid, colum: campo, value: valor, tipoc: 'textbox', oldvalue: oldvalue }
				g_changes.atualizacoes = g_changes.atualizacoes + 1;
				g_changes.campos.push(localizacao);
			}
		}
	}
	$('#jqxButton_salvar').jqxButton('val', 'Salvar(' + g_changes.atualizacoes + ')');
	$('#jqxButton_salvar').jqxButton('render');
	localStorage.setItem('updatesRollout', JSON.stringify(g_changes));
}
function busca_historico_rollout_filtros() {
	var siteid, autor, periodo, campos, i, f;
	campos = $('#select_campo_historico_filtro').selectpicker('val');
	autor = $('#select_autor_historico_filtro').selectpicker('val');
	siteid = document.getElementById('filtro_historico_siteid').value;
	var selection = $("#periodo_hitorico_mudanca").jqxDateTimeInput('getRange');
	i = moment(selection.from).format('L');
	f = moment(selection.to).format('L');
	$.ajax({
		type: "POST",
		data: {
			"opt": "20",
			"siteid": siteid,
			"campos": campos.toString(),
			"autor": autor.toString(),
			"inicio": i,
			"fim": f
		},
		//url: "http://localhost:8080/DashTM/D_Servlet",	  
		url: "./RolloutServlet",
		cache: false,
		dataType: "text",
		success: CriaGridHistorico
	});
	function CriaGridHistorico(data) {
		dataaux = JSON.parse(data);
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
					{ text: 'ID', datafield: 'recid', filtertype: 'checkedlist' },
					{ text: 'Site', datafield: 'Site ID', filtertype: 'checkedlist', width: 80 },
					{ text: 'Tipo Campo', datafield: 'Tipo Campo', filtertype: 'checkedlist', width: 100 },
					{ text: 'Milestone', datafield: 'Milestone', filtertype: 'checkedlist', width: 100 },
					{ text: 'Campo', datafield: 'Campo', filtertype: 'checkedlist', width: 110 },
					{ text: 'Valor Anterior', datafield: 'Valor Anterior', filtertype: 'checkedlist', width: 100 },
					{ text: 'Novo Valor', datafield: 'Novo Valor', filtertype: 'checkedlist', width: 100 },
					{ text: 'Atualizado Por', datafield: 'Atualizado Por', filtertype: 'checkedlist', width: 110 },
					{ text: 'Data da Atualização', datafield: 'Data da Atualização', filtertype: 'checkedlist', cellsformat: "dd/MM/yyyy HH:mm:ss", width: 170 }
				]
			});


	}
}

function atualizaperiodo() {
	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
	var rolloutid;
	if (item != null) {
		rolloutid = item.value;
		if (rolloutid == "Rollout") {
			return;
		}
	} else {
		rolloutid = "Rollout1";
	}
	$.ajax({
		type: "POST",
		data: { "opt": "41", "rolloutid": rolloutid },
		//url: "http://localhost:8080/DashTM/D_Servlet",	  
		url: "./RolloutServlet",
		cache: false,
		dataType: "text",
		success: atualizaperiodook
	});
	function atualizaperiodook(data) {
		$.alert("terminou");
	}
}
function rm_row_rollout() {

	var rowindexes = $('#jqxgrid').jqxGrid('getselectedrowindexes');
	$.confirm({
		title: 'Remoção de Atividade do Rollout',
		content: 'ATENÇÃO! Deseja remover as linhas selecionadas do rollout?',
		type: 'red',
		typeAnimated: true,
		buttons: {
			Confirma: {
				text: 'Confirma(' + rowindexes.length + ' linhas)',

				action: function() {


					for (var v = 0; v < rowindexes.length; v++) {
						deletar.push($('#jqxgrid').jqxGrid('getrowid', rowindexes[v]));
					}
					if (deletar.length > 0) {
						$.ajax({
							type: "POST",
							data: {
								"opt": "8",
								"linha": deletar.toString()
							},
							//url: "http://localhost:8080/DashTM/D_Servlet",	  
							url: "./RolloutServlet",
							cache: false,
							dataType: "text",
							success: SucessLinhaRemovida
						});
						function SucessLinhaRemovida(data) {

							$("#jqxgrid").jqxGrid('deleterow', deletar);
							$('#jqxgrid').jqxGrid('refreshdata');
							$('#jqxgrid').jqxGrid('clearselection');
							deletar = [];
							deletar_grid = [];
							carrega_gant();

						}
					}
				}
			},
			Cancela: function() { }
		}
	});



}

function add_new_row_rollout() {
	var item = $('#jqxTree_rollout').jqxTree('getSelectedItem');
	var rolloutid;
	if (item != null) {
		rolloutid = item.value;
		if (rolloutid == "Rollout") {
			return;
		}
	} else {
		$.alert("Selecione o rollout na árvore de rollout");
		return;
	}
	var linha;

	row.rows = [];
	var campos = JSON.parse(sessionStorage.getItem("camposRollout"));
	console.log(campos);
	for (var indice = 0; indice < campos.length; indice++) {
		linha = {
			nome: campos[indice].nome,
			valor_campo: $('#_' + campos[indice].nome2).val(),
			tipo_campo: campos[indice].tipo
		};
		row.rows.push(linha);
	}

	console.log(row);
	$.ajax({
		type: "POST",
		data: {
			"opt": "4",
			"linha": JSON.stringify(row), "rolloutid": rolloutid
		},
		//url: "http://localhost:8080/DashTM/D_Servlet",	  
		url: "./RolloutServlet",
		cache: false,
		dataType: "text",
		success: onSuccess24
	});
	function onSuccess24(data) {
		carrega_gant();
		row.rows = [];
	}
}
function registra_mudancas_bd(changes) {
	var mudancas=JSON.parse(localStorage.getItem('updatesRollout'));
	if (mudancas.atualizacoes > 0) {
		$.ajax({
			type: "POST",
			data: {
				"opt": "15",
				"mudancas": JSON.stringify(mudancas)
			},
			url: "./RolloutServlet",
			cache: false,
			dataType: "text",
			success: onSuccess_registra_mudancas_bd
		});

		function onSuccess_registra_mudancas_bd(data) {
			console.log('funcao onSuccess_registra_mudancas_bd');
			//$.alert(data.toString());
			console.log('funcao onSuccess_registra_mudancas_bd');
			g_changes.atualizacoes = 0;
			g_changes.campos = [];
			console.log('funcao onSuccess_registra_mudancas_bd');
			localStorage.setItem('updatesRollout', JSON.stringify(g_changes));
			$('#jqxButton_salvar').jqxButton({
				disabled: false
			});
			console.log('funcao onSuccess_registra_mudancas_bd');
			$('#jqxButton_salvar').jqxButton('val', 'Salvar');
			console.log('funcao onSuccess_registra_mudancas_bd');
			$('#jqxButton_salvar').jqxButton('render');
			console.log('funcao onSuccess_registra_mudancas_bd');
			//if (geral.perfil.search("RolloutManager") >= 0) {
			//	console.log('Atuaizando grafico');
			//	g1(0, 'grafico_container1_rollout');
			//	console.log('atualizado grafico');
			//}
		}
	} else {
		$.alert("Sem Mudanças para registrar!");
		$('#jqxButton_salvar').jqxButton({
			disabled: false
		});
		$('#jqxButton_salvar').jqxButton('val', 'Salvar');
		$('#jqxButton_salvar').jqxButton('render');
	}
}