/**
 * 
 */

function carrega_LPU(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"57"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: LPULoad
		});
	
	function LPULoad(data)
	{
		    var LPUrecords=JSON.parse(data);
			var source =
		     {
					 datatype: "json",
			         datafields: [
			        	 { name: 'ID' , type: 'number'},
			        	 { name: 'ID_LPU' , type: 'string'},
			        	 { name: 'CATEGORIA', type: 'string' },
			        	 { name: 'DESCRICAO', type: 'string' },
			             { name: 'UF', type: 'string' },
			             { name: 'UNIDADE', type: 'string' },
			             { name: 'VALOR', type: 'string' }
			             
			             
			         ],
		         localdata: LPUrecords,
		         id: 'ID',
		     };
			
			 var dataAdapter = new $.jqx.dataAdapter(source);
			 
			 $("#div_grid_lpu").jqxGrid(
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
		                 selectionmode: 'checkbox',
		                 columns: [
		                       { text: 'ID', datafield: 'ID_LPU',align: "center",cellsalign: "center",width: 80,filtertype: 'textbox'},
		                       { text: 'Descrição', align: "center",datafield: 'DESCRICAO', width: 650,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Categoria', align: "center",datafield: 'CATEGORIA', width: 170,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Unidade', align: "center",datafield: 'UNIDADE', width: 150,cellsalign: "center",filtertype: 'checkedlist' },
		                       { text: 'Valor', align: "center",datafield: 'VALOR',cellsalign: "center", width: 150,filtertype: 'textbox' },
		                       { text: 'UF', align: "center",datafield: 'UF',cellsalign: "center", width: 100,filtertype: 'checkedlist' }
		                        
		                   ]
		             });
			
		
	}
}

function RemoveItemLPU(){
	$('#btnRemoveItemLpu').addClass( "disabled" );
	$('#btnRemoveItemLpu').text('Aguarde a Finalização...');
	$('#btnRemoveItemLpu').prop('disabled', true);
	var rowindexes = $('#div_grid_lpu').jqxGrid('getselectedrowindexes');
	var ids=[];
	if(rowindexes.length<1){
		$.alert("Nenhum item selecionado");
		$('#btnRemoveItemLpu').removeClass( "disabled" );
		$('#btnRemoveItemLpu').text('Remoção de Item');
		$('#btnRemoveItemLpu').prop('disabled', false);
		return;
	}else{
		for(var v=0;v<rowindexes.length;v++){
			ids.push(parseInt($('#div_grid_lpu').jqxGrid('getrowid', rowindexes[v])));
		}
	$.ajax({
		  type: "POST",
		  data: {"opt":"58","ids":JSON.stringify(ids)},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: RemoveItemLPU_Sucesso
		});
	
	function RemoveItemLPU_Sucesso(data)
	{
		
		carrega_LPU();
		
		$('#div_grid_lpu').jqxGrid('clearselection');
		$.alert(data.toString());
		$('#btnRemoveItemLpu').removeClass( "disabled" );
		$('#btnRemoveItemLpu').text('Remoção de Item');
		$('#btnRemoveItemLpu').prop('disabled', false);
	}
	}
}

function addItemLPU(){
	var item={};
	item.codigo = $('#addcodigo_item_lpu').val();
	item.categoria = $('#select_categorias_item_lpu').val();
	item.descricao = document.getElementById("adddescricao_item_lpu").value;
	item.valor = parseFloat(document.getElementById("addvalor_item_lpu").value);
	item.uf = $('#select_uf_item_lpu').val();
	item.unidade = $('#select_unidade_item_lpu').val();
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"59","item":JSON.stringify(item)},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: addItemLPU_Sucesso
		});
	
	function addItemLPU_Sucesso(data)
	{
		$.alert(data.toString());
	}
	
}
function DownloadLPU(){
	$('#btnDownloadLPU').addClass( "disabled" );
	$('#btnDownloadLPU').text('Aguarde a Finalização...');
	$('#btnDownloadLPU').prop('disabled', true);
	$.ajax({
        url: './POControl_Servlet?opt=60',
        method: 'GET',
        data:{"opt":60},
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data) {
            var a = document.createElement('a');
            var url = window.URL.createObjectURL(data);
            a.href = url;
            a.download = 'LPU_'+geral.empresa_nome+'.xlsx';
            a.click();
            window.URL.revokeObjectURL(url);
            $('#btnDownloadLPU').removeClass( "disabled" );
    		$('#btnDownloadLPU').text('Download LPU');
    		$('#btnDownloadLPU').prop('disabled', false);
        }
    });
}