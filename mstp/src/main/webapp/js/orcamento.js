/**
 * Funçoes de Orcamento
 */

function carregaTabelaOrcamentos(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"6"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./OrcamentoServlet",
		  cache: false,
		  dataType: "text",
		  success: carregaTabelaOrcamentos
		});
	
	function carregaTabelaOrcamentos(data)
	{
		   
		
		var statusvalidacor = function(row, columnfield, value) {
			 
			 
			 if (value == "") {
			    return 'nada';
			  } else if (value == "EM APROVAÇÃO") {
			    return 'yellow';
			  } else if(value == "REJEITADO"){
				  return 'red';
			  }else if (value == "APROVADO") {
				    return 'green';
			  }else{
				   return 'nada';
			  }
			};
		    var ORCAMENTOrecords=JSON.parse(data);
			var source =
		     {
					 datatype: "json",
			         datafields: [
			        	 { name: 'ID' , type: 'string'},
			        	 { name: 'ID_CLIENTE_MSTP' , type: 'string'},
			        	 { name: 'CLIENTE' , type: 'string'},
			        	 { name: 'SITE', type: 'string' },
			        	 { name: 'MUNICIPIO', type: 'string' },
			             { name: 'UF', type: 'string' },
			             { name: 'DESC_SERVICO', type: 'string' },
			             { name: 'VALOR', type: 'number' },
			             { name: 'STATUS', type: 'number' },
			             { name: 'CRIADOPOR', type: 'string' },
			             { name: 'DT_CRIADO', type: 'string' }
			             
			             
			         ],
		         localdata: ORCAMENTOrecords,
		         id: 'ID',
		     };
			
			 var dataAdapter = new $.jqx.dataAdapter(source);
			 
			 $("#div_grid_orcamento").jqxGrid(
		             {
		            	 width: '100%',
		            	 theme:'light',
		            	 autoheight: true,
		                 source: dataAdapter,
		                 columnsresize: true,
			             filterable: true,
			             autoshowfiltericon: true,
		                 pageable: true,
		                 pagesize: 15,
		                 ready: function () {
			                    
			                    var localizationObject = {
			                        filterstringcomparisonoperators: ['contem', 'não contem','vazio','não vazio'],
			                        // filter numeric comparison operators.
			                        filternumericcomparisonoperators: ['less than', 'greater than','Empty','not Empty'],
			                        // filter date comparison operators.
			                        filterdatecomparisonoperators: ['menor que', 'maior que','maior ou igual a','menor ou igual a','igual a','vazio','não vazio'],
			                        // filter bool comparison operators.
			                        filterbooleancomparisonoperators: ['equal', 'not equal'],
			                        currencysymbol : "R$",
			                        decimalseparator : ",",
			                    	thousandsseparator : "."
			                    }
			                    $("#div_grid_orcamento").jqxGrid('localizestrings', localizationObject);
			                },
		                 selectionmode: 'checkbox',
		                 columns: [
		                       { text: 'ID', datafield: 'ID_CLIENTE_MSTP',align: "center",cellsalign: "center",width: 130,filtertype: 'textbox'},
		                       { text: 'Cliente', align: "center",datafield: 'CLIENTE', width: 220,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Site', align: "center",datafield: 'SITE', width: 150,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Serviço', align: "center",datafield: 'DESC_SERVICO', width: 520,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Valor', align: "center",datafield: 'VALOR',cellsalign: "center", cellsformat: 'c2',width: 140,filtertype: 'textbox' },
		                       { text: 'Status', align: "center",datafield: 'STATUS',cellsalign: "center",cellclassname:statusvalidacor,width: 140,filtertype: 'textbox' },
		                       { text: 'Criado Por', align: "center",datafield: 'CRIADOPOR',cellsalign: "center", width: 150,filtertype: 'checkedlist' },
		                       { text: 'Criado em', align: "center",datafield: 'DT_CRIADO',cellsalign: "center", width: 150,filtertype: 'checkedlist' }
		                        
		                   ]
		             });
			
		
	}
}
function inicializaTabelaOrcamento(){
	
	var source =
     {
			 datatype: "json",
	         datafields: [
	        	 { name: 'ID' , type: 'number'},
	        	 { name: 'IDITEM' , type: 'string'},
	        	 { name: 'ITEM', type: 'string' },
	        	 { name: 'UNIDADE', type: 'string' },
	             { name: 'QTDE', type: 'number' },
	             { name: 'DESCONTO', type: 'number' },
	             { name: 'VALORUNITARIO', type: 'number' },
	             { name: 'VALORTOTAL', type: 'number' }
	             
	             
	         ],
         localdata: [],
         id: 'ID',
     };
	
	 var dataAdapter = new $.jqx.dataAdapter(source);
	 
	 $("#grid_orcamento_itens").jqxGrid(
             {
            	 width: '100%',
            	 theme:'light',
            	 autoheight: true,
                 source: dataAdapter,
                 columnsresize: true,
	             filterable: true,
	             autoshowfiltericon: true,
                 pageable: true,
                 editable: true,
                 showstatusbar: true,
                 statusbarheight: 50,
                 showaggregates: true,
                 pagesize: 15,
                 ready: function () {
	                    
	                    var localizationObject = {
	                        filterstringcomparisonoperators: ['contem', 'não contem','vazio','não vazio'],
	                        // filter numeric comparison operators.
	                        filternumericcomparisonoperators: ['less than', 'greater than','Empty','not Empty'],
	                        // filter date comparison operators.
	                        filterdatecomparisonoperators: ['menor que', 'maior que','maior ou igual a','menor ou igual a','igual a','vazio','não vazio'],
	                        // filter bool comparison operators.
	                        filterbooleancomparisonoperators: ['equal', 'not equal'],
	                        currencysymbol : "R$",
	                        decimalseparator : ",",
	                    	thousandsseparator : "."
	                    }
	                    $("#grid_orcamento_itens").jqxGrid('localizestrings', localizationObject);
	                },
                 selectionmode: 'multiplerows',
                 columns: [
                       { text: 'ID LINHA', datafield: 'ID',align: "center", editable:false, cellsalign: "center",width: 80,filtertype: 'textbox'},
                       { text: 'ID ITEM', datafield: 'IDITEM',align: "center", editable:false,cellsalign: "center",width: 80,filtertype: 'textbox'},
                       { text: 'Descrição', align: "center",datafield: 'ITEM', editable:false, width: 650,cellsalign: "center",filtertype: 'checkedlist' },
                       { text: 'Unidade', align: "center",datafield: 'UNIDADE', editable:false, width: 130,cellsalign: "center",filtertype: 'checkedlist' },
                       { text: 'Quantidade', align: "center",datafield: 'QTDE', cellsformat: 'd2',width: 110,cellsalign: "center",columntype: "numberinput",validation: function (cell, value) {
                           
                    	   
                    	   if (value < 0.1) {
                               return { result: false, message: "Quantidade deve ser maior que zero" };
                           }else{
                        	   
                        	   var calculo = $('#grid_orcamento_itens').jqxGrid('getcellvaluebyid', cell.row, "VALORUNITARIO");
                        	   var desconto = $('#grid_orcamento_itens').jqxGrid('getcellvaluebyid', cell.row, "DESCONTO");
                        	   calculo = (calculo * value)-((calculo * value)*(desconto/100));
                        	   $("#grid_orcamento_itens").jqxGrid('setcellvalue', cell.row, "VALORTOTAL", calculo);
                        	   return true;
                        	   
                           }
                           
                       },filtertype: 'checkedlist' },
                       { text: 'Desconto', align: "center",datafield: 'DESCONTO',cellsformat: 'd2',cellsalign: "center",columntype: "numberinput", width: 110,validation: function (cell, value) {
                           
                    	   
                    	   if (value > 100) {
                               return { result: false, message: "Quantidade deve ser menor que 101" };
                           }else{
                        	   
                        	   var calculo = $('#grid_orcamento_itens').jqxGrid('getcellvaluebyid', cell.row, "VALORUNITARIO");
                        	   var quantidade = $('#grid_orcamento_itens').jqxGrid('getcellvaluebyid', cell.row, "QTDE");
                        	   calculo = (calculo * quantidade)-((calculo * quantidade)*(value/100));
                        	   $("#grid_orcamento_itens").jqxGrid('setcellvalue', cell.row, "VALORTOTAL", calculo);
                        	   return true;
                        	   
                           }
                           
                       },filtertype: 'textbox' },
                       { text: 'ValorUni', align: "center",datafield: 'VALORUNITARIO', cellsformat: 'c2',editable:false, cellsalign: "center", width: 120,aggregates: ['sum'],filtertype: 'checkedlist' },
                       { text: 'ValorTotal', align: "center",datafield: 'VALORTOTAL', cellsformat: 'c2',editable:false, cellsalign: "center", width: 120,aggregates: ['sum'],filtertype: 'checkedlist' }
                        
                   ]
             });
}
function AddLinhaItemOrcamento(){
	var linha={};
	linha.IDITEM=$('#select_item_orcamento').val();
	linha.ITEM=document.getElementById("lbl_item_desc").value;
	linha.UNIDADE=document.getElementById("lbl_item_unidade").value;
	linha.QTDE=1;
	linha.DESCONTO=0;
	linha.VALORUNITARIO=parseFloat(document.getElementById("lbl_item_valor2").value);
	linha.VALORTOTAL=parseFloat(document.getElementById("lbl_item_valor2").value);
	//console.log(linha);
	$('#grid_orcamento_itens').jqxGrid('addrow', null, linha);
}
function CarregaSelectCategorias(){
	var timestamp =Date.now();
	$.ajax({
 		  type: "POST",
 		  data: {"opt":"1",
 			  "_": timestamp
 			},		  
 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
 		  url: "./OrcamentoServlet",
 		  cache: false,
 		  dataType: "text",
 		 success: CarregaSelectCategorias_Sucesso
 		});
  	 function CarregaSelectCategorias_Sucesso(data){
  		$("#select_categorias_item_orcamento").html(data);
  		$('#select_categorias_item_orcamento').selectpicker({size:5});
		$('#select_categorias_item_orcamento').selectpicker('refresh');
		
		$("#select_categorias_item_lpu").html(data);
  		$('#select_categorias_item_lpu').selectpicker({size:5});
		$('#select_categorias_item_lpu').selectpicker('refresh');
  	 }
}
function CarregaSelectItens(opt){
	var timestamp =Date.now();
	if(opt){
		opt=opt;
	}else{
		opt="vazia";
	}
	$.ajax({
 		  type: "POST",
 		  data: {"opt":"2",
 			  "categoria":opt,
 			  "_": timestamp
 			},		  
 		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
 		  url: "./OrcamentoServlet",
 		  cache: false,
 		  dataType: "text",
 		 success: CarregaSelectItens_Sucesso
 		});
  	 function CarregaSelectItens_Sucesso(data){
  		$("#select_item_orcamento").html(data);
  		$('#select_item_orcamento').selectpicker({size:5});
		$('#select_item_orcamento').selectpicker('refresh');
  	 }
}
function RecuperaDadosItem(item){
	var timestamp =Date.now();
	$.ajax({
		  type: "POST",
		  data: {"opt":"3",
			  "item":item,
			  "_": timestamp
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./OrcamentoServlet",
		  cache: false,
		  dataType: "text",
		 success: RecuperaDadosItem_Sucesso
		});
	 function RecuperaDadosItem_Sucesso(data){
		 aux=JSON.parse(data);
		 document.getElementById("lbl_item_unidade").value=aux[0];
		 document.getElementById("lbl_item_valor").value=aux[1];
		 document.getElementById("lbl_item_desc").value=aux[2];
		 document.getElementById("lbl_item_valor2").value=aux[3];
	 }
}
function PesquisaSiteOrcamento(){
	var timestamp =Date.now();
	var site = document.getElementById("site_orcamento_pesquisa").value;
	$.ajax({
		  type: "POST",
		  data: {"opt":"4",
			  "site":site,
			  "_": timestamp
			},	  
		  url: "./OrcamentoServlet",
		  cache: false,
		  dataType: "text",
		 success: PesquisaSiteOrcamento_Sucesso
		});
	 function PesquisaSiteOrcamento_Sucesso(data){
		 aux=JSON.parse(data);
		 if(aux[0]=="site nao encontrado"){
			 $.alert("Site não encontrado na base de Sites!");
		 }else{
			 document.getElementById("site_orcamento_endereco").value=aux[0];
			 document.getElementById("site_orcamento_bairro").value=aux[1];
			 document.getElementById("site_orcamento_municipio").value=aux[2];
			 document.getElementById("site_orcamento_uf").value=aux[3];
		 }
	 }
}
function RemoverLinhaItemOrcamento(){
	var rowindex = $('#grid_orcamento_itens').jqxGrid('getselectedrowindex');
	while(rowindex!=-1){
			
			var id = $('#grid_orcamento_itens').jqxGrid('getrowid', rowindex);
			$('#grid_orcamento_itens').jqxGrid('deleterow', id);
			rowindex = $('#grid_orcamento_itens').jqxGrid('getselectedrowindex');
		}
	$('#grid_orcamento_itens').jqxGrid('clearselection');
	//$('#grid_orcamento_itens').jqxGrid('updatebounddata');
	$('#grid_orcamento_itens').jqxGrid('refreshdata');
	$('#grid_orcamento_itens').jqxGrid('refresh');
	$('#grid_orcamento_itens').jqxGrid('render');
}
function RegistraOrcamento(opt){
	 
	var orcamento={};
	orcamento.cliente=$("#select_cliente_orcamento").val();
	orcamento.contratoCliente=$("#select_cliente_contrato_orcamento").val();
	orcamento.site=document.getElementById("site_orcamento_pesquisa").value;
	orcamento.siteEnderco=document.getElementById("site_orcamento_endereco").value;
	orcamento.siteBairro=document.getElementById("site_orcamento_bairro").value;
	orcamento.siteMunicipio=document.getElementById("site_orcamento_municipio").value;
	orcamento.siteUF=document.getElementById("site_orcamento_uf").value;
	orcamento.siteGRAM=document.getElementById("site_orcamento_GRAM").value;
	orcamento.siteSigla_local=document.getElementById("site_orcamento_SIGLA_LOCAL").value;
	orcamento.descServico=document.getElementById("site_orcamento_descricao_servico").value;
	orcamento.motivoServico=document.getElementById("site_orcamento_motivo_servico").value;
	orcamento.itensServico=$('#grid_orcamento_itens').jqxGrid('getrows');
	console.log(orcamento);
	
	var timestamp =Date.now();
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"5",
			  "orcamento":JSON.stringify(orcamento),
			  "_": timestamp
			},	  
		  url: "./OrcamentoServlet",
		  cache: false,
		  dataType: "text",
		 success: RegistraOrcamento_Sucesso
		});
	 function RegistraOrcamento_Sucesso(data){
		$.alert(data.toString());
		carregaTabelaOrcamentos();
	 }
	
}
function GerarDocumentoOrcamento(){
	var timestamp =Date.now();
	var rowindexes = $('#div_grid_orcamento').jqxGrid('getselectedrowindexes');
	var id;;
	for(var v=0;v<rowindexes.length;v++){
		id=$('#div_grid_orcamento').jqxGrid('getrowid', rowindexes[v]);
		
		$.ajax({
			  type: "POST",
			  data: {"opt":"7",
				  "orcamentoID":id,
				  "_": timestamp
				},	  
			  url: "./OrcamentoServlet",
			  cache: false,
			  dataType: "text",
			 success: GerarDocumentoOrcamento_Sucesso
			});
		 function GerarDocumentoOrcamento_Sucesso(data){
			 var vtot = 0;
			var orcamento = JSON.parse(data);
			console.log(orcamento);
			var logo_cliente=orcamento.logoCustomer;
			var logoEmpresa=orcamento.logoEmpresa;
			//console.log(window.btoa(orcamento.logoCustomer));
			//console.log(logo_cliente);
			var docDefinition = {
					footer:function(currentPage, pageCount) { 
						return {
							margin: [10, 0, 10, 0],
							table:{
								widths: [200, '*', 200],
								body:[
								  [{ text:'www.jtel.com.br',color:'#585858',fontSize: 10,margin:[10,0,0,0],border: [false, true, false, false]},{text:'',color:'#585858',border: [false, true, false, false]},{ text: currentPage.toString() + '/' + pageCount, color:'#585858',alignment: 'right',border: [false, true, false, false],fontSize: 10,margin:[0,0,10,0] }],
							      
							    ]
							}
							}; 
						},
					
					  content: [
						  {
							  table:{
								  widths: [100, '*', 100],
								  body:[
									  [{
									        image: 'data:image/png;base64,'+logo_cliente,
									        width:80
									  	},
									  	{text:[
												{text:'Solicitação de Orçamento para \n',alignment: 'center',fontSize: 12,color:'#585858',bold: true},
												{text:'Oi MOVEL S/A \n',alignment: 'center',color:'#585858',fontSize: 12,bold: false},
												{text:'Proposta: '+orcamento.ORCAMENTO_ID_CLIENTE+' - Revisão: 0',fontSize: 10,alignment: 'center',color:'#585858',bold: true}
											]
									  	},
										{
									        image: 'data:image/jpeg;base64,'+logoEmpresa,
									        width:100
									       
										}]
								  ]
							  },
							  layout: 'noBorders'
						  },
							{
								style: 'tabelaMargem',
							  table: {
								 widths: [100, '*',100 ,'*'],
								body: [
									[{fillColor: '#dddddd',text:'Número da Proposta',color:'#585858',bold: true,fontSize: 10}, {text:orcamento.ORCAMENTO_ID_CLIENTE,color:'#585858',bold: false,fontSize: 10},'',''],
									[{fillColor: '#dddddd',text:'Descrição do Serviço',color:'#585858',bold: true,fontSize: 10}, {text:orcamento.ORCAMENTO_SERVICO_DESCRICAO,colSpan: 3,color:'#585858',bold: false,fontSize: 10},'',''],
									[{fillColor: '#dddddd',text:'Motivo do Serviço',color:'#585858',bold: true,fontSize: 10}, {text:orcamento.ORCAMENTO_SERVICO_MOTIVO,colSpan: 3,color:'#585858',bold: false,fontSize: 10},'',''],
									[{fillColor: '#dddddd',text:'Endereço',color:'#585858',bold: true,fontSize: 10}, {text:orcamento.ORCAMENTO_SITE_ENDERECO,colSpan: 3,color:'#585858',bold: false,fontSize: 10},'',''],
									[{fillColor: '#dddddd',text:'Bairro',color:'#585858',bold: true,fontSize: 10}, {text:orcamento.ORCAMENTO_SITE_BAIRRO,colSpan: 3,color:'#585858',bold: false,fontSize: 10},'',''],
									[{fillColor: '#dddddd',text:'Cidade',color:'#585858',bold: true,fontSize: 10}, {text:orcamento.ORCAMENTO_SITE_MUNICIPIO,color:'#585858',bold: false,fontSize: 10},{fillColor: '#dddddd',text:'UF',color:'#585858',bold: true,fontSize: 10},{text:orcamento.ORCAMENTO_SITE_UF,color:'#585858',bold: false,fontSize: 10}],
									[{fillColor: '#dddddd',text:'Estação / Site',color:'#585858',bold: true,fontSize: 10}, {text:orcamento.ORCAMENTO_SITE,color:'#585858',bold: false,fontSize: 10},'',''],
									[{fillColor: '#dddddd',text:'Código Local',color:'#585858',bold: true,fontSize: 10}, {text:orcamento.ORCAMENTO_SITE_SIGLA,color:'#585858',bold: false,fontSize: 10},{fillColor: '#dddddd',text:'Sigla Local',color:'#585858',bold: true,fontSize: 10},{text:orcamento.ORCAMENTO_SITE_SIGLA,color:'#585858',bold: false,fontSize: 10}],
									[{fillColor: '#dddddd',text:'GRAM',color:'#585858',bold: true,fontSize: 10}, {text:orcamento.ORCAMENTO_SITE_SIGLA,color:'#585858',bold: false,fontSize: 10},{fillColor: '#dddddd',text:'BA',color:'#585858',bold: true,fontSize: 10},{text:orcamento.ORCAMENTO_SITE_SIGLA,color:'#585858',bold: false,fontSize: 10}],
									[{fillColor: '#dddddd',text:'Deslocamento Total',color:'#585858',bold: true,fontSize: 10}, '0',{fillColor: '#dddddd',text:'Deslocamento Devido',color:'#585858',bold: true,fontSize: 10},'0'],
									[{fillColor: '#dddddd',text:'Contrato',color:'#585858',bold: true,fontSize: 10}, {text:orcamento.ORCAMENTO_CLIENTE_CONTRATO,color:'#585858',bold: false,fontSize: 10},{fillColor: '#dddddd',text:'Data',color:'#585858',bold: true,fontSize: 10},{text:moment(orcamento.DT_ADD.$date).format('L') + ' ' + moment(orcamento.DT_ADD.$date).format('LTS'),color:'#585858',bold: false,fontSize: 10}]
								]
							},
							layout: 'lightHorizontalLines'
							},
							{
								  style: 'tabelaMargem',
								  table: {
										headerRows: 2,
									  	widths: [30,'*',40 ,25,60,60],
										body: [
											[{
												fillColor: '#dddddd',
												text:'Itens da Proposta',
												colSpan: 6,
												alignment:'center',
												color:'#585858',
												bold: true,
												fontSize: 12
												},'','','','',''],
											[
												{fillColor: '#dddddd',text:'Item',alignment:'center',color:'#585858',bold: true,fontSize: 10},
												{fillColor: '#dddddd',text:'Descrição',color:'#585858',bold: true,fontSize: 10},
												{fillColor: '#dddddd',text:'Unidade',color:'#585858',bold: true,fontSize: 10},
												{fillColor: '#dddddd',text:'Qtde',color:'#585858',bold: true,fontSize: 10},
												{fillColor: '#dddddd',text:'V.Unit.',color:'#585858',bold: true,fontSize: 10},
												{fillColor: '#dddddd',text:'V.Total',color:'#585858',bold: true,fontSize: 10},
											]
											
										]
								},
								layout: 'lightHorizontalLines'
							}
					  ],	
					  styles: {
									tabelaMargem: {
										margin: [0, 20, 0, 10]
									}
					  		}
			};
			for(var indice=0;indice<orcamento.ORCAMENTO_SERVICO_ITENS_LPU.length;indice++){
				 vtot = vtot+orcamento.ORCAMENTO_SERVICO_ITENS_LPU[indice].VALORTOTAL;
				
				docDefinition.content[2].table.body.push(
						[
							{text:orcamento.ORCAMENTO_SERVICO_ITENS_LPU[indice].IDITEM,color:'#585858',bold: false,fontSize: 10},
							{text:orcamento.ORCAMENTO_SERVICO_ITENS_LPU[indice].ITEM,color:'#585858',bold: false,alignment: 'justify',fontSize: 10},
							{text:orcamento.ORCAMENTO_SERVICO_ITENS_LPU[indice].UNIDADE,color:'#585858',bold: false,fontSize: 10},
							{text:orcamento.ORCAMENTO_SERVICO_ITENS_LPU[indice].QTDE,color:'#585858',bold: false,fontSize: 10},
							{text:accounting.formatMoney(orcamento.ORCAMENTO_SERVICO_ITENS_LPU[indice].VALORUNITARIO),color:'#585858',bold: false,fontSize: 10},
							{text:accounting.formatMoney(orcamento.ORCAMENTO_SERVICO_ITENS_LPU[indice].VALORTOTAL),color:'#585858',bold: false,fontSize: 10}
							]);
			}
			docDefinition.content[2].table.body.push(
					[
						{text:'',fillColor: '#f2d4ac',color:'#585858',bold: false,fontSize: 10},
						{text:'',fillColor: '#f2d4ac',color:'#585858',bold: false,fontSize: 10},
						{text:'',fillColor: '#f2d4ac',color:'#585858',bold: false,fontSize: 10},
						{text:'VALOR TOTAL: '+ accounting.formatMoney(vtot),fillColor: '#f2d4ac',alignment:'right',colSpan: 3,color:'#585858',bold: true,fontSize: 10},
						{text:'',fillColor: '#f2d4ac',color:'#585858',bold: true,fontSize: 10},
						{text:'',color:'#585858',fillColor: '#f2d4ac',bold: true,fontSize: 10}
						]);
			pdfMake.createPdf(docDefinition).download(orcamento.ORCAMENTO_ID_CLIENTE+' '+orcamento.ORCAMENTO_SITE+'.pdf');
		 }
		
		
	}
	
}