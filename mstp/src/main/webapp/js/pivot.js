function init_pivot(){
	var data1 = new Array();
    
    $.getJSON('./RolloutServlet?opt=10', function(data) {	
    var source =
    {
        localdata: data.records,
        datatype: "array",
        datafields:data.campos
    };
    var dataAdapter = new $.jqx.dataAdapter(source);
    dataAdapter.dataBind();
    // create a pivot data source from the dataAdapter
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
            rows: [],
            columns: [],
            filters: [],
            values: []
        });
    var localization = { 'var': 'Variance' };
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
}