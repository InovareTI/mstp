function relatorioDespesa(){
	
	var selection = $("#range_despesas").jqxDateTimeInput('getRange');
	inicio = moment(selection.from).format('L');
	fim = moment(selection.to).format('L');
	var func =  $('#select_func_diaria').val();
	var rowindexes = $('#grid_resumo_despesas').jqxGrid('getselectedrowindexes');
	filtros={"filtros":[]};
	for(var v=0;v<rowindexes.length;v++){
		filtros.filtros.push($('#grid_resumo_despesas').jqxGrid('getrowid', rowindexes[v]));
	}
	$.ajax({
        url: './DiariasServlet?opt=5',
        method: 'GET',
        data:{"filtros":JSON.stringify(filtros),"inicio":inicio,"fim":fim,"func":func},
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data) {
            var a = document.createElement('a');
            var url = window.URL.createObjectURL(data);
            a.href = url;
            a.download = 'despesa.pdf';
            a.click();
            window.URL.revokeObjectURL(url);
        }
    });
	
}
function despesasStatus(id_despesa){
	
	status_despesa=id_despesa.substr(id_despesa.indexOf("*")+1,id_despesa.length);
	id_despesa=id_despesa.substr(0,id_despesa.indexOf("*"));
	alert(id_despesa);
	alert(status_despesa);
	$.ajax({
		  type: "POST",
		  data: {"opt":"4",
			"id":id_despesa,
			"status":status_despesa
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./DiariasServlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccessStatusDespesa
		});
	function onSuccessStatusDespesa(data){
		CarregaDespesas();
	}
}
function visualiza_comprovante(id_comprovante){
	new PhotoViewer([{
        src: './DiariasServlet?opt=3&despesa='+id_comprovante,
      }]);
}

function CarregaDespesas(){
	var timestamp = Date.now();
	var func =  $('#select_func_diaria').val();
	var inicio="";
	var fim="";
	var selection = $("#range_despesas").jqxDateTimeInput('getRange');
	inicio = moment(selection.from).format('L');
	fim = moment(selection.to).format('L');
	
	var source =
    {
        datatype: "json",
        datafields: [
       	 { name: 'id' , type: 'string'},
       	{ name: 'dtdespesa', type: 'string' },
       	{ name: 'funcdespesa', type: 'string' },
    	 { name: 'categoria', type: 'string' },
         { name: 'projeto', type: 'string' },
         { name: 'site', type: 'string' },
         { name: 'motivo' , type: 'string'},
         { name: 'valor' , type: 'int'},
         { name: 'statusPagamento' , type: 'string'},
         { name: 'StatusDespesa' , type: 'string'},
         { name: 'comprovante' , type: 'string'},
         { name: 'operacoes' , type: 'string'}
         ],
        cache: false,
        url: './DiariasServlet',
       	id: 'id',
        filter: function() {
            // update the grid and send a request to the server.
            $("#grid_resumo_despesas").jqxGrid('updatebounddata', 'filter');
            //alert("alert filter1");
        },
        sort: function() {
            // update the grid and send a request to the server.
            $("#grid_resumo_despesas").jqxGrid('updatebounddata', 'sort');
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
        	data.opt=1;
        	data.func=func;
        	data._=timestamp;
        	data.inicio=inicio;
        	data.fim=fim;
        	//console.log(data);
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
	$("#grid_resumo_despesas").jqxGrid(
            {
               width:getWidth("divcontentdespesa")-40,
               source: dataadapter,
               columnsresize: true,
               theme:'light',
               pageable: true,
	           filterable: true,
	           autoshowfiltericon: true,
               rowsheight: 55,
               ready: function () {
                   
                   var localizationObject = {
                       filterstringcomparisonoperators: ['contem', 'não contem','vazio','não vazio'],
                       // filter numeric comparison operators.
                       filternumericcomparisonoperators: ['less than', 'greater than','Empty','not Empty'],
                       // filter date comparison operators.
                       filterdatecomparisonoperators: ['menor que', 'maior que','maior ou igual a','menor ou igual a','igual a','vazio','não vazio'],
                       // filter bool comparison operators.
                       filterbooleancomparisonoperators: ['equal', 'not equal']
                   }
                   $("#grid_resumo_despesas").jqxGrid('localizestrings', localizationObject);
               },
               updatefilterconditions: function (type, defaultconditions) {
                   var stringcomparisonoperators = ['CONTAINS', 'DOES_NOT_CONTAIN','EMPTY','NOT_EMPTY'];
                   var numericcomparisonoperators = ['LESS_THAN', 'GREATER_THAN','EMPTY','NOT_EMPTY'];
                   var datecomparisonoperators = ['LESS_THAN', 'GREATER_THAN','GREATER_THAN_OR_EQUAL','LESS_THAN_OR_EQUAL','EQUAL','EMPTY','NOT_EMPTY'];
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
               columns: [
                      { text: 'ID', datafield: 'id',align: "center",cellsalign: "center",width: 330,filtertype: 'textbox'},
                      { text: 'Data Despesa', datafield: 'dtdespesa',align: "center",cellsalign: "center", width: 110,filtertype: 'textbox' },
                      { text: 'Responsável', datafield: 'funcdespesa',align: "center",cellsalign: "center", width: 110,filtertype: 'textbox' },
                      { text: 'Categoria', datafield: 'categoria',align: "center",cellsalign: "center", width: 110,filtertype: 'textbox' },
                      { text: 'Projeto', datafield: 'projeto',align: "center", width: 80,cellsalign: "center",filtertype: 'checkedlist' },
                      { text: 'Site', datafield: 'site',align: "center", width: 80,cellsalign: "center",filtertype: 'checkedlist' },
                      { text: 'Motivo', datafield: 'motivo',align: "center", width: 80,cellsalign: "center",filtertype: 'checkedlist' },
                      { text: 'Valor', datafield: 'valor',align: "center", width: 120,cellsalign: "center",filtertype: 'checkedlist' },
                      { text: 'Pagamento', datafield: 'statusPagamento',align: "center", width: 100,cellsalign: "center",filtertype: 'checkedlist' },
                      { text: 'Status Depesa', datafield: 'StatusDespesa',align: "center", width: 100,cellsalign: "center",filtertype: 'checkedlist' },
                      { text: 'Comprovante de Despesa', datafield: 'comprovante',align: "center", width: 80,cellsalign: "center"},
                      { text: 'Operações', datafield: 'operacoes',align: "center", width: 280,cellsalign: "center"},
               ],
               altrows: true,
               editmode: 'click',
               virtualmode: true,
               rendergridrows: function(obj) {
                   return obj.data;
               }
               
            });
}