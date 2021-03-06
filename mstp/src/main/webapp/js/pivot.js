var myPivot_design;
function init_pivot(){
	var data1 = new Array();
	 var localization = getLocalization('pt-BR');
    $.getJSON('./RolloutServlet?opt=10', function(data) {
    	
    	 var source =
         {
             localdata: data.records,
             datatype: "array",
             datafields:data.campos,
             
         };
         var dataAdapter = new $.jqx.dataAdapter(source);
         dataAdapter.dataBind();
         
         var pivotDataSource = new $.jqx.pivot(
                 dataAdapter,
                 {
                     customAggregationFunctions: {
                         'weeknum': function (values) {
                        	 console.log(values);
                             if (values.length <= 1)
                                 return 'W0';
                             for (var i = 0; i < values.length; i++){
                            	 
                             }
                             var d = Date.parse(values);
                             var semana=moment().week(d);
                             console.log("semana:" +semana);
                             return "W"+semana;
                         }
                     },
                     pivotValuesOnRows: false,
                     fields: data.campos2,
                     rows: [],
                     columns: [],
                     filters: [],
                     values: []
                 });
         	var localization = { 'weeknum': 'ContaWeek' };
         // create a pivot grid
         $('#divPivotGrid').jqxPivotGrid(
         {
             localization: localization,
             source: pivotDataSource,
             treeStyleRows: false,
             autoResize: false,
             multipleSelectionEnabled: true
         });
         var pivotGridInstance = $('#divPivotGrid').jqxPivotGrid('getInstance');
         // create a pivot grid
         $('#divPivotGridDesigner').jqxPivotDesigner(
         {
             type: 'pivotGrid',
             target: pivotGridInstance
         });
    	
    });
    $button = $('#salvar_pivot_btn');
    $button.click(function () {
    	var fields = myPivot_design.getFields();
    	var fieldsFilter = myPivot_design.getFiltersValues();
    	console.log(fields);
   

	var nome;
	nome=document.getElementById("report_pivot_nome").value;
	if(nome==""){
		$.alert("Nome do report é Obrigatório");
		return;
	}""
	//var myPivotGridInstance2="";
	
	//var tamanho_filtros=$('#divPivotGrid').jqxPivotGrid('getInstance').source.filters.length;
	//console.log("tamanho:"+tamanho_filtros);
	//for(var i=0;i<tamanho_filtros;i++){
	//	myPivotGridInstance2 = myPivotGridInstance2 + i +" : " + $('#divPivotGrid').jqxPivotGrid('getInstance').source.filters[i].filterFunction.toString();
	//}
	//var myPivotGridInstance = $('#divPivotGrid').jqxPivotGrid('getInstance').source._pivot;
	//console.log(myPivotGridInstance2);
	$.ajax({
		  type: "POST",
		  data: {"opt":"11",
			  "pivot":JSON.stringify(fields),
			  "filtros":JSON.stringify(fieldsFilter),
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
    });
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
	
	$("#divPivotGridView").html("<div class='loader'></div>");
	  $.getJSON('./RolloutServlet?opt=13&id='+id, function(data) {
		  $("#divPivotGridView").html("");
		  myPivot_design = new dhx.Pivot("divPivotGridView", {
	    	    data: data.records,                 
	    	    fields: {
	    	        rows: data.rows,
	    	        columns: data.columns,
	    	        values: data.values,
	    	    },
	    	    fieldList: data.campos2,
	    	    layout: {                       
	    	    	readonly: true
	    	    }   
	    	});
		  myPivot_design.setFiltersValues(data.Filters);
	    });
	  
}