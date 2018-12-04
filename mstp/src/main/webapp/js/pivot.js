function init_pivot(){
	var data = new Array();
    var firstNames =
    [
        "Andrew", "Nancy", "Shelley", "Regina", "Yoshi", "Antoni", "Mayumi", "Ian", "Peter", "Lars", "Petra", "Martin", "Sven", "Elio", "Beate", "Cheryl", "Michael", "Guylene"
    ];
    var lastNames =
    [
        "Fuller", "Davolio", "Burke", "Murphy", "Nagase", "Saavedra", "Ohno", "Devling", "Wilson", "Peterson", "Winkler", "Bein", "Petersen", "Rossi", "Vileid", "Saylor", "Bjorn", "Nodier"
    ];
    var productNames =
    [
        "Black Tea", "Green Tea", "Caffe Espresso", "Doubleshot Espresso", "Caffe Latte", "White Chocolate Mocha", "Cramel Latte", "Caffe Americano", "Cappuccino", "Espresso Truffle", "Espresso con Panna", "Peppermint Mocha Twist"
    ];
    var priceValues =
    [
        "2.25", "1.5", "3.0", "3.3", "4.5", "3.6", "3.8", "2.5", "5.0", "1.75", "3.25", "4.0"
    ];
    for (var i = 0; i < 500; i++) {
        var row = {};
        var productindex = Math.floor(Math.random() * productNames.length);
        var price = parseFloat(priceValues[productindex]);
        var quantity = 1 + Math.round(Math.random() * 10);
        row["firstname"] = firstNames[Math.floor(Math.random() * firstNames.length)];
        row["lastname"] = lastNames[Math.floor(Math.random() * lastNames.length)];
        row["productname"] = productNames[productindex];
        row["price"] = price;
        row["quantity"] = quantity;
        row["total"] = price * quantity;
        data[i] = row;
    }
    // create a data source and data adapter
    var source =
    {
        localdata: data,
        datatype: "array",
        datafields:
        [
            { name: 'firstname', type: 'string' },
            { name: 'lastname', type: 'string' },
            { name: 'productname', type: 'string' },
            { name: 'quantity', type: 'number' },
            { name: 'price', type: 'number' },
            { name: 'total', type: 'number' }
        ]
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
            fields: [
                 { dataField: 'firstname', text: 'First Name' },
                 { dataField: 'lastname', text: 'Last Name' },
                 { dataField: 'productname', text: 'Product Name' },
                 { dataField: 'quantity', text: 'Quantity' },
                 { dataField: 'price', text: 'Price' },
                 { dataField: 'total', text: 'Total' }
            ],
            rows: [
                   { dataField: 'firstname', text: 'First Name' },
                   { dataField: 'lastname', text: 'Last Name' }
            ],
            columns: [{ dataField: 'productname', align: 'left' }],
            filters: [
                {
                    dataField: 'productname',
                    text: 'Product name',
                    filterFunction: function (value) {
                        if (value == "Black Tea" || value == "Green Tea")
                            return true;
                        return false;
                    }
                }
            ],
            values: [
                { dataField: 'price', 'function': 'sum', text: 'Sum', align: 'left', formatSettings: { prefix: '$', decimalPlaces: 2, align: 'center' }, cellsClassName: 'myItemStyle', cellsClassNameSelected: 'myItemStyleSelected' },
                { dataField: 'price', 'function': 'count', text: 'Count', className: 'myItemStyle', classNameSelected: 'myItemStyleSelected' }
            ]
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
}