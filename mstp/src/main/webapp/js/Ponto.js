function atualiza_range_espelho(){
	var mes =  $('#select_mes_folha_ponto').val();
	mes=mes-1;
	var date = new Date();
    var firstDay = new Date(date.getFullYear(), mes, 1);
    var lastDay = new Date(date.getFullYear(), mes + 1, 0);
     $("#range_espelho").jqxDateTimeInput('setRange', firstDay, lastDay);
	
}
function visualiza_foto_justificativa(id_ajuste){
	new PhotoViewer([{
        src: './UserMgmt?opt=34&ajuste='+id_ajuste,
      }]);
}
function carrega_kpi(){
	
		$('#tabela_kpi_ponto').DataTable({
			"searching": false,
		    "ordering": false,
		    "lengthChange": false,
		    "processing": true,
	        "ajax": './UserMgmt?opt=20',
	        "lengthMenu": [ 7, 14, 21, 31]
		});
		
		$.ajax({
			  type: "POST",
			  data: {"opt":"22"
				  
				 },		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./UserMgmt",
			  cache: false,
			  dataType: "text",
			  success: Successkpimensal
			});
		function Successkpimensal(data){
			var aux=data.split(";");
			$("#kpi_entrada_lbl").html(aux[0]);
			$("#kpi_ii_lbl").html(aux[1]);
			$("#kpi_fi_lbl").html(aux[2]);
			$("#kpi_saida_lbl").html(aux[3]);
			$("#kpi_usuarios_lbl").html(aux[4]);
		}
}
function corrigir_ponto(){
	
	var mes =  $('#select_mes_folha_ponto').val();
	var mes_aux=$('#select_mes_folha_ponto').find("option:selected").text();
	var func =  $('#select_func_folha_ponto').val();
	var selection=$('#range_espelho').jqxDateTimeInput('getRange');
	$('#btn_corrigir_espelho').addClass( "disabled" );
	$('#btn_corrigir_espelho').text('Aguarde a Finalização...');
	$('#btn_corrigir_espelho').prop('disabled', true);
	if( mes =="" || func==""){
		$.alert({
			title: 'MSTP WEB',
		    content: 'Selecione os dados necessários',
		    type: 'red',
		});
		$('#btn_corrigir_espelho').removeClass( "disabled" );
		$('#btn_corrigir_espelho').prop('disabled', false);
		$('#btn_corrigir_espelho').text('Corrigir Espelho de Ponto');
	}else{
	
	$.confirm({
	    title: 'Confirmação',
	    content: 'Esta ação somente altera registros de intervalo que estejam inconsistentes.',
	    columnClass: 'medium',
	    type: 'red',
	    buttons: {
	    	Confirma: function () {
	    		$.alert('Atenção: Esta Função será desabilitada em 31/03/2019.')
	
	if(geral.perfil.search("BancoHHApprover")>=0){
		$.ajax({
			  type: "POST",
			  data: {"opt":"19",
				  "func":func,
				  "mes":mes,
				  "inicio":moment(selection.from).format('L'),
				  "fim":moment(selection.to).format('L'),
				 },		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./UserMgmt",
			  cache: false,
			  dataType: "text",
			  success: SuccessCorrigePonto
			});
		function SuccessCorrigePonto(data){
			$.alert({
				title: 'MSTP WEB',
			    content: data.toString(),
			    type: 'red',
			});
			$('#btn_corrigir_espelho').removeClass( "disabled" );
			$('#btn_corrigir_espelho').text('Corrigir Intervalos de Descanso');
			$('#btn_corrigir_espelho').prop('disabled', false);
		}
	}else{
		$.alert({
			title: 'MSTP WEB',
		    content:'Sem Permissão para essa operação',
		    type: 'red',
		});
		$('#btn_corrigir_espelho').removeClass( "disabled" );
		$('#btn_corrigir_espelho').text('Corrigir Espelho de Ponto');
		$('#btn_corrigir_espelho').prop('disabled', false);
	}
	    	 },
	         Cancelar: function () {
	        	$('#btn_corrigir_espelho').removeClass( "disabled" );
	 			$('#btn_corrigir_espelho').text('Corrigir Espelho de Ponto');
	 			$('#btn_corrigir_espelho').prop('disabled', false);
	         }
	 	}});
	}
}
function prepara_ajuste_ponto(dia){
	//alert("chegou no ajuste de ponto para "+dia);
    $('#ponto_entrada').data("DateTimePicker").minDate(dia+' 00:00:00');
    $('#ponto_entrada').data("DateTimePicker").maxDate(dia+' 23:59:59');
    $('#ponto_saida').data("DateTimePicker").maxDate(dia+' 23:59:59');
    $('#ponto_saida').data("DateTimePicker").minDate(dia+' 00:00:00');
    document.getElementById("aux_ajuste_ponto_dia").value=dia;
}
function fecha_tela_ajuste_ponto(){
	
    $('#ponto_saida').data("DateTimePicker").minDate(false);
    $('#ponto_saida').data("DateTimePicker").maxDate(false);
    $('#ponto_entrada').data("DateTimePicker").minDate(false);
    $('#ponto_entrada').data("DateTimePicker").maxDate(false);
    $('#ponto_entrada').data("DateTimePicker").clear();
    $('#ponto_saida').data("DateTimePicker").clear();
}
function solicita_ajuste_ponto(){
	var entrada = $('#ponto_entrada').data("DateTimePicker").viewDate().format('L LTS');
	var saida = $('#ponto_saida	').data("DateTimePicker").viewDate().format('L LTS');
	var local = $('#select_ajuste_ponto').val();
	var motivo = document.getElementById("ajuste_ponto_motivo").value;
	var dia_folga_compesacao=document.getElementById("aux_ajuste_ponto_dia").value;
	var select_folga_compensacao = $('#select_ajuste_ponto_razao').val();
	$.ajax({
		  type: "POST",
		  data: {"opt":"11",
			  "entrada":entrada,
			  "saida":saida,
			  "local":local,
			  "motivo":motivo,
			  "data_folga_compensacao":dia_folga_compesacao,
			  "select_folga_compensacao":select_folga_compensacao},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		  success: SuccessSolicatAjustePonto
		});
	function SuccessSolicatAjustePonto(data){
		$.alert({
			title: 'MSTP WEB',
		    content:data.toString(),
		    type: 'green',
		});
	}
}
function append_ponto_aprovacoes(){
	var $table = $('#tabela_aprovacoes');
	 $.getJSON('./UserMgmt?opt=12', function(data) {	
		 
		 $table.bootstrapTable('append', data);
         //$table.bootstrapTable('scrollTo', 'bottom');
	 });
	 append_site_novo_aprovacoes();
}
function coleta_dados_usuario(handleData){
	var timestamp = new Date().getUTCMilliseconds();
	$.ajax({
		  type: "POST",
		  data: {"opt":"15",
			  "usuario":$('#select_func_folha_ponto').val(),
			  "_":timestamp},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		  success:function(data) {
		      handleData(data); 
		    }
		});
	
}
function carrega_ponto_usuario(){
	var part1;
	var part2;
	var part3={};
	var part4={};
	var mes =  $('#select_mes_folha_ponto').val();
	var mes_aux=$('#select_mes_folha_ponto').find("option:selected").text();
	var func =  $('#select_func_folha_ponto').val();
	var selection=$('#range_espelho').jqxDateTimeInput('getRange');
	var dados_usuario={};
	coleta_dados_usuario(function(output){
		
		dados_usuario=JSON.parse(output);
		
	
	//console.log(dados_usuario);
	part1="<div class=\"panel panel-default\" style=\"width:99%;margin:0 auto;\">"+
        				"<div class=\"panel-heading\">"+geral.empresa_nome+"</div>"+
	        			"<div class=\"panel-body\"><table style=\"width:100%\">" +
	        			"<tr><th>Endereço:</th><td colspan=\"5\">"+geral.empresa_endereco+"</td></tr>" +
	        			"<tr><th>Bairro:</th><td>"+geral.empresa_bairro+"</td><th>Cidade:</th><td>"+geral.empresa_cidade+"</td><th>Estado:</th><td>"+geral.empresa_uf+"</td></tr>" +
	        			"<tr><th>CNPJ:</th><td>"+geral.empresa_cnpj+"</td><th>CNAE:</th><td>"+geral.empresa_cnae+"</td><th>Visto Fiscal:</th><td></td></tr>" +
	        			"</table> </div>"+
	        			"<div class=\"panel-heading\">"+dados_usuario.matricula+" - "+dados_usuario.nome+"</div>"+
	        			"<div class=\"panel-body\"><table style=\"width:100%\">" +
	        			"<tr><th>CTPS:</th><td colspan=\"3\">"+dados_usuario.ctps+"</td><th>Matrícula:</th><td>"+dados_usuario.matricula+"</td></tr>" +
	        			"<tr><th>Cargo:</th><td>"+dados_usuario.cargo+"</td><th></th><td></td><th>Admissão:</th><td>"+dados_usuario.admissao+"</td></tr>" +
	        			"<tr><th>Horário:</th><td>08:30h às 18:30 de 2ª a 6ª </td><th>Intervalo:</th><td>1:12h de intervalo de 2ª a 6ª</td><th>PIS:</th><td>"+dados_usuario.pis+"</td></tr>" +
	        			"</table>"+
	        			"</div>"+
	        			"</div>";
	part2="<div><p><br><br><table style=\"width:100%\">" +
			"<tr><td>OBS: Bando de Horas do Mes:"+geral.banco+" | Horas Extras (50%):"+geral.horas_sabado + " | Horas Extras (100%):"+geral.horas_domingo+"</td><td></td></tr>"+
			"<tr><td>&nbsp</td><td>&nbsp</td><td>&nbsp</td</tr>"+
			"<tr><td>____________________________________________________</td><td></td><td>____________________________________________________</td</tr>"+
			"<tr><td>Ass. do Empregador</td><td></td><td>Empregado - "+dados_usuario.nome+"</td></tr>"+
			"<tr><td>&nbsp</td><td></td><td></td></tr>"+
			"<tr><td></td><td></td><td></td></tr>"+
			"<tr><td colspan='3' style='text-align: right;'>www.mstp.com.br</td></tr>"+
			"</table></p></div>";
	part3= [
		
				{
					
					table: {
						widths: [45,180, 40, 90,50,50],
						body: [
							[{text:geral.empresa_nome,style: 'tableHeader2',colSpan: 6}, {}, {},{},{},{}],
							[{text:'Endereço:',style: 'tableHeader2'},{text: geral.empresa_endereco}, {},{},{},{}],
							[{text:'Bairro:',style: 'tableHeader2'},{text: geral.empresa_bairro}, {text:'Cidade:',style: 'tableHeader2'},{text:geral.empresa_cidade},{text:'Estado:',style: 'tableHeader2'},{text:geral.empresa_uf}],
							[{text:'CNPJ:',style: 'tableHeader2'},{text: geral.empresa_cnpj}, {text:'CNAE:',style: 'tableHeader2'},{text:geral.empresa_cnae},{text:'Visto Fiscal:',style: 'tableHeader2'},{}]
						]
					},
					layout: 'headerLineOnly'
				},
				{
					
					table: {
						widths: [45,150, 40, 90,50,50],
						body: [
							[{text:dados_usuario.matricula+" - "+dados_usuario.nome,style: 'tableHeader2',colSpan: 6}, {}, {},{},{},{}],
							[{text:'CTPS:',style: 'tableHeader2'},{text: dados_usuario.ctps}, {},{},{text:'Matrícula:',style: 'tableHeader2'},{text: dados_usuario.matricula}],
							[{text:'Cargo:',style: 'tableHeader2'},{text: dados_usuario.cargo}, {},{},{text:'Admissão:',style: 'tableHeader2'},{text:dados_usuario.admissao}],
							[{text:'Horário:',style: 'tableHeader2'},{text: '08:30 às 18:30 de 2ª a 6ª' }, {text:'Intervalo:',style: 'tableHeader2'},{text:'1h:12m por dia'},{text:'PIS:',style: 'tableHeader2'},{text:dados_usuario.pis}]
						]
					},
					layout: 'headerLineOnly'
				}
				];
	part4= [
		{
			
			table: {
				widths: [250,50,200],
				body: [
					[{text:'Reconhecemos a exatidão das informações aqui descritas'}, {}, {}],
					[{text:'____________________________________________________'},{}, {text:'____________________________________________________'}],
					[{text:'Ass. Empregador',style: 'tableHeader2'},{}, {text:'Ass. '+dados_usuario.nome,style: 'tableHeader2'}],
					[{},{}, {}],
					[{},{}, {}],
					[{},{}, {}],
					[{},{}, {text:'www.mstp.com.br',alignment: 'right'}]
					
				]
			},
			layout: 'headerLineOnly'
		}];
	if(mes=='' || func==''){
		alert("Parametros de busca incorretos");
		return;
	}else{
		if ( ! $.fn.DataTable.isDataTable( '#tabela_usuario_folha_ponto' ) ) {
			
		$('#tabela_usuario_folha_ponto').DataTable( {
			dom: 'Bfrtip',
	        buttons: [
	        	{
	                extend: 'print',
	                text: 'Imprimir',
	                title:'Folha de Ponto - período '+ mes_aux+'/2019',
	                messageTop: part1,
	                //messageTop: 'Funcionário: '+geral.nome+'<br>Usuário de registro: '+geral.usuario+'<br><b>Folha de Ponto Periodo relativo '+ mes_aux+'/2018</b>',
	                messageBottom:part2,
	                exportOptions: {
	                columns: [0,1,2,3,4,5,6]
	                },
	                customize: function ( win ) {
	                	console.log(win.document.body);
	                	//ddconsole.log(win.document);
	                    $(win.document.body)
	                        .css( 'font-size', '7pt' );
	                    $(win.document.body).find( 'h1' )
                        .css( 'font-size', '10pt' );    
	 
	                    $(win.document.body).find( 'table' )
	                        .addClass( 'compact' )
	                        .css( 'font-size', 'inherit' );
	                }
	            },
	            {
	        		extend: 'excelHtml5'
	        	},
	            {
	                extend: 'pdfHtml5',
	                title:'Folha de Ponto - período '+ mes_aux+'/2019',
	                messageBottom:'\nObservações: \n Bando de Horas do Mes:'+geral.banco+' | Horas Extras (50%):'+geral.horas_sabado + ' | Horas Extras (100%):'+geral.horas_domingo,
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                customize: function ( doc ) {
	                	
	                	doc.content.splice(1,0,part3);
	                	doc.content.push(part4);
	                	doc.pageMargins= [ 30, 40, 20, 30 ];
	                	//console.log(doc);
	                	//console.log(doc.content);
	                	
	                }
	            }
	            
	        ],
	        "processing": true,
	        "order": [],
	        "ajax": "./UserMgmt?opt=1&mes="+mes+"&func="+func+"&inicio="+moment(selection.from).format('L')+"&fim="+moment(selection.to).format('L'),
	        "lengthMenu": [ 7, 14, 21, 31]
	        
	    });
		}else{
			var table = $('#tabela_usuario_folha_ponto').DataTable();
			table.destroy();
			$('#tabela_usuario_folha_ponto').DataTable( {
				dom: 'Bfrtip',
		        buttons: [
		        	{
		                extend: 'print',
		                text: 'Imprimir',
		                title:'Folha de Ponto - período '+ mes_aux+'/2019',
		                messageTop: part1,
		                //messageTop: 'Funcionário: '+geral.nome+'<br>Usuário de registro: '+geral.usuario+'<br><b>Folha de Ponto Periodo relativo '+ mes_aux+'/2018</b>',
		                messageBottom:part2,
		                exportOptions: {
		                columns: [0,1,2,3,4,5,6]
		                },
		                customize: function ( win ) {
		                	console.log(win.document.body);
		                	//ddconsole.log(win.document);
		                    $(win.document.body)
		                        .css( 'font-size', '7pt' );
		                    $(win.document.body).find( 'h1' )
	                        .css( 'font-size', '10pt' );    
		 
		                    $(win.document.body).find( 'table' )
		                        .addClass( 'compact' )
		                        .css( 'font-size', 'inherit' );
		                }
		            },
		            {
		        		extend: 'excelHtml5'
		        	},
		            {
		                extend: 'pdfHtml5',
		                title:'Folha de Ponto - período '+ mes_aux+'/2019',
		                messageBottom:'\nObservações: \n Bando de Horas do Mes:'+geral.banco+' | Horas Extras (50%):'+geral.horas_sabado + ' | Horas Extras (100%):'+geral.horas_domingo,
		                exportOptions: {
		                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
		                },
		                customize: function ( doc ) {
		                	
		                	doc.content.splice(1,0,part3);
		                	doc.content.push(part4);
		                	doc.pageMargins= [ 30, 40, 20, 30 ];
		                	//console.log(doc);
		                	//console.log(doc.content);
		                	
		                }
		            }
		            
		        ],
		        "processing": true,
		        "order": [],
		        "ajax": "./UserMgmt?opt=1&mes="+mes+"&func="+func+"&inicio="+moment(selection.from).format('L')+"&fim="+moment(selection.to).format('L'),
		        "lengthMenu": [ 7, 14, 21, 31]
		        
		    });
		}
	}
	});
}
function carrega_ponto_analise_usuario(){
	
	
	var func =  $('#select_func_folha_ponto_analise').val();
	var selection=$('#range_espelho_analise').jqxDateTimeInput('getRange');
	
	
		
	
	//console.log(dados_usuario);
	
	if(func==''){
		func="TODOS";
		
	}
		if ( ! $.fn.DataTable.isDataTable( '#tabela_usuario_folha_ponto_analise' ) ) {
			
		$('#tabela_usuario_folha_ponto_analise').DataTable( {
			dom: 'Bfrtip',
	        buttons: [
	        	{
	                extend: 'print',
	                text: 'Imprimir',
	                
	                
	                customize: function ( win ) {
	                	
	                    $(win.document.body)
	                        .css( 'font-size', '7pt' );
	                    $(win.document.body).find( 'h1' )
                        .css( 'font-size', '10pt' );    
	 
	                    $(win.document.body).find( 'table' )
	                        .addClass( 'compact' )
	                        .css( 'font-size', 'inherit' );
	                }
	            },
	            {
	        		extend: 'excelHtml5'
	        	}
	            
	            
	        ],
	        "processing": true,
	        "order": [],
	        "ajax": "./UserMgmt?opt=30&func="+func+"&inicio="+moment(selection.from).format('L')+"&fim="+moment(selection.to).format('L'),
	        "lengthMenu": [ 15, 30]
	        
	    });
		}else{
			var table = $('#tabela_usuario_folha_ponto_analise').DataTable();
			table.destroy();
			$('#tabela_usuario_folha_ponto_analise').DataTable( {
				dom: 'Bfrtip',
		        buttons: [
		        	{
		                extend: 'print',
		                text: 'Imprimir',
		                
		                customize: function ( win ) {
		                	console.log(win.document.body);
		                	//ddconsole.log(win.document);
		                    $(win.document.body)
		                        .css( 'font-size', '7pt' );
		                    $(win.document.body).find( 'h1' )
	                        .css( 'font-size', '10pt' );    
		 
		                    $(win.document.body).find( 'table' )
		                        .addClass( 'compact' )
		                        .css( 'font-size', 'inherit' );
		                }
		            },
		            {
		        		extend: 'excelHtml5'
		        	}
		            
		            
		        ],
		        "processing": true,
		        "order": [],
		        "ajax": "./UserMgmt?opt=30&func="+func+"&inicio="+moment(selection.from).format('L')+"&fim="+moment(selection.to).format('L'),
		        "lengthMenu": [ 15, 30]
		        
		    });
		}
	
	
}
function carrega_select_func_ponto(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"2"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		  success: onSuccessUserMgmt2
		});
	function onSuccessUserMgmt2(data){
		$("#select_func_folha_ponto").html(data);
		$("#select_func_folha_ponto_analise").html(data);
		$("#select_func_faltas").html(data);
		$("#select_func_diaria").html(data);
		$("#select_autor_historico_filtro").html(data);
		$('#select_func_folha_ponto').selectpicker('refresh');
		$('#select_func_folha_ponto_analise').selectpicker('refresh');
		$('#select_func_faltas').selectpicker('refresh');
		$("#select_func_diaria").selectpicker('refresh');
		$("#select_autor_historico_filtro").selectpicker('refresh');
	}
}
