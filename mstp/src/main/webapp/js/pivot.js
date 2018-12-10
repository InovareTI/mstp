var myPivot_design;
function init_pivot(){
	var data1 = new Array();
	 var localization = getLocalization('pt-BR');
    $.getJSON('./RolloutServlet?opt=10', function(data) {
    	myPivot_design = new dhx.Pivot("divPivotGrid", {
    	    data: data.records,                 
    	    fields: {
    	        rows: [],
    	        columns: [],
    	        values: [],
    	    },
    	    fieldList: data.campos2
    	});
    });
    $button = $('#salvar_pivot_btn');
    $button.click(function () {
    	var fields = myPivot_design.getFields();
    	console.log(fields);
    });
}
function salvar_pivot(){
	var nome;
	nome=document.getElementById("report_pivot_nome").value;
	if(nome==""){
		$.alert("Nome do report é Obrigatório");
		return;
	}""
	var myPivotGridInstance2="";
	
	var tamanho_filtros=$('#divPivotGrid').jqxPivotGrid('getInstance').source.filters.length;
	//console.log("tamanho:"+tamanho_filtros);
	for(var i=0;i<tamanho_filtros;i++){
		myPivotGridInstance2 = myPivotGridInstance2 + i +" : " + $('#divPivotGrid').jqxPivotGrid('getInstance').source.filters[i].filterFunction.toString();
	}
	var myPivotGridInstance = $('#divPivotGrid').jqxPivotGrid('getInstance').source._pivot;
	//console.log(myPivotGridInstance2);
	$.ajax({
		  type: "POST",
		  data: {"opt":"11",
			  "pivot":JSON.stringify(myPivotGridInstance),
			  "filtros":JSON.stringify(myPivotGridInstance2),
			  "nome":nome
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		  success: SuccessSalvoPivotx
		});
	function SuccessSalvoPivotx(data){
		$.alert(data.toString());
	}
}
function carrega_select_pivot(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"12"
			 
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RolloutServlet",
		  cache: false,
		  dataType: "text",
		  success: SuccessCarrega_PivotSelect
		});
	function SuccessCarrega_PivotSelect(data){
		$("#select_pivot_view").html(data);
		$("#select_pivot_view").selectpicker('refresh');
	}
}
function carrega_pivot(id){
	
	  $.getJSON('./RolloutServlet?opt=13&id='+id, function(data) {	
		  var source =
		    {
		        localdata: data.records,
		        datatype: "array",
		        datafields:data.campos
		    };
		  var dataAdapter = new $.jqx.dataAdapter(source);
		    dataAdapter.dataBind();
		    
		    var pivotDataSource = new $.jqx.pivot(
		            dataAdapter,
		            {
		                customAggregationFunctions: {
		                    'var': function (values) {
		                        if (values.length <= 1)
		                            return 0;
		                        // sample's mean
		                        var mean = 0;
		                        for (var i = 0; i < values.length; i++)
		                            mean += values[i];
		                        mean /= values.length;
		                        // calc squared sum
		                        var ssum = 0;
		                        for (var i = 0; i < values.length; i++)
		                            ssum += Math.pow(values[i] - mean, 2)
		                        // calc the variance
		                        var variance = ssum / values.length;
		                        return variance;
		                    }
		                },
		                pivotValuesOnRows: false,
		                fields: data.campos2,
		                rows: data.rows,
		                columns: data.columns,
		                filters: data.filters,
		                values: data.values
		            });
		    var localization = { 'var': 'Variance' };
		    $('#divPivotGridView').jqxPivotGrid(
		    	    {
		    	        localization: localization,
		    	        source: pivotDataSource,
		    	        treeStyleRows: false,
		    	        autoResize: false,
		    	        multipleSelectionEnabled: true
		    	    });
		    
	  });
	  $('#divPivotGridView').jqxPivotGrid('getPivotRows').items[0].setHeight(100);
      $('#divPivotGridView').jqxPivotGrid('refresh');
}