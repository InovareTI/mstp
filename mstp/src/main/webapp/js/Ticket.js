function ticketStarter(){
	var fields = [
	        { name: "id", type: "string" },
	        { name: "status", map: "state", type: "string" },
	        { name: "text", map: "label", type: "string" },
	        { name: "tags", type: "string" },
	        { name: "color", map: "hex", type: "string" },
	        { name: "resourceId", type: "number" }
        ];
	var source =
    {
        localData: [
                 { id: "1161", state: "new", label: "Make a new Dashboard", tags: "dashboard", hex: "#36c7d0", resourceId: 3 },
                 { id: "1645", state: "work", label: "Prepare new release", tags: "release", hex: "#ff7878", resourceId: 1 },
                 { id: "9213", state: "new", label: "One item added to the cart", tags: "cart", hex: "#96c443", resourceId: 3 },
                 { id: "6546", state: "done", label: "Edit Item Price", tags: "price, edit", hex: "#ff7878", resourceId: 4 },
                 { id: "9034", state: "new", label: "Login 404 issue", tags: "issue, login", hex: "#96c443" }
        ],
        dataType: "array",
        dataFields: fields
    };
	var dataAdapter = new $.jqx.dataAdapter(source);
	 var resourcesAdapterFunc = function () {
         var resourcesSource =
         {
             localData: [
                   { id: 0, name: "No name", image: "./img/started.png", common: true },
                   { id: 1, name: "Andrew Fuller", image: "./img/started.png" },
                   { id: 2, name: "Janet Leverling", image: "./img/started.png" },
                   { id: 3, name: "Steven Buchanan", image: "./img/started.png" },
                   { id: 4, name: "Nancy Davolio", image: "./img/started.png" },
                   { id: 5, name: "Michael Buchanan", image: "./img/started.png" },
                   { id: 6, name: "Margaret Buchanan", image: "./img/started.png" },
                   { id: 7, name: "Robert Buchanan", image: "./img/started.png" },
                   { id: 8, name: "Laura Buchanan", image: "./img/started.png" },
                   { id: 9, name: "Laura Buchanan", image: "./img/started.png" }
             ],
             dataType: "array",
             dataFields: [
                  { name: "id", type: "number" },
                  { name: "name", type: "string" },
                  { name: "image", type: "string" },
                  { name: "common", type: "boolean" }
             ]
         };
         var resourcesDataAdapter = new $.jqx.dataAdapter(resourcesSource);
         return resourcesDataAdapter;
     }
	 $('#TicketKanban').jqxKanban({
         width: '100%',
         height: '100%',
         theme:'light',
         resources: resourcesAdapterFunc(),
         source: dataAdapter,
         columns: [
             { text: "Backlog", dataField: "new", maxItems: 5 },
             { text: "In Progress", dataField: "work", maxItems: 5 },
             { text: "Done", dataField: "done", maxItems: 5 }
         ],
         // render column headers.
         columnRenderer: function (element, collapsedElement, column) {
             var columnItems = $("#TicketKanban").jqxKanban('getColumnItems', column.dataField).length;
             // update header's status.
             element.find(".jqx-kanban-column-header-status").html(" (" + columnItems + "/" + column.maxItems + ")");
             // update collapsed header's status.
             collapsedElement.find(".jqx-kanban-column-header-status").html("<label style='color:white'> (" + columnItems + "/" + column.maxItems + ")</label>");
         }
     });
}