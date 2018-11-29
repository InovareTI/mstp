function verifica_template_nome(){
	
	if($('#select_template_vistoria').val()=='1'){
		document.getElementById('nome_template_novo').style.display = "block";
	}else{
		document.getElementById('nome_template_novo').style.display = "none";
	}
}
function edita_relatorio_info(){
	$.alert("Em desenvolvimento");
}
function remover_relatorio(relatorio_id){
	$.ajax({
		  type: "POST",
		  data: {"opt":"5",
			     "rel_id":relatorio_id},		  
		    
		  url: "./RelatoriosServlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccessRemoveRel
		});
	function onSuccessRemoveRel(data){
		$.alert(data.toString());
		tabela_relatorio();
	}
}
function carrega_campos_relatorio(relatorio_id){
	var item_tipo;
	$.ajax({
 		  type: "POST",
 		  data: {"opt":"4",
 			     "rel_id":relatorio_id},		  
 		    
 		  url: "./RelatoriosServlet",
 		  cache: false,
 		  dataType: "text",
 		  success: onSuccessCarregaItem
 		});
	function onSuccessCarregaItem(data){
		 var itens_vistoria=JSON.parse(data);
		 var source =
         {
             datatype: "json",
             datafields: [
                 { name: 'id' },
                 { name: 'parentid' },
                 { name: 'text' }
                 
             ],
             id: 'id',
             localdata: itens_vistoria
         };
		 var dataAdapter = new $.jqx.dataAdapter(source);
		 dataAdapter.dataBind();
		 var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{ name: 'text', map: 'label'}]);
		 $('#campos_relatorio').jqxTree({ source: records, height: '450px', width: '300px'});
		 $('#campos_relatorio').jqxTree('refresh');
	}
	$("#add_item_arvore").unbind('click');
	$('#add_item_arvore').click(function (event) {
		event.preventDefault();
		
		$('#campos_relatorio').jqxTree('refresh');
        var selectedItem = $('#campos_relatorio').jqxTree('getSelectedItem');
        console.log(selectedItem);
        var treeItems = $("#campos_relatorio").jqxTree('getItems');
        if (selectedItem != null) {
        	
        	
            // adds an item with label: 'item' as a child of the selected item. The last parameter determines whether to refresh the Tree or not.
            // If you want to use the 'addTo' method in a loop, set the last parameter to false and call the 'render' method after the loop.
            $('#campos_relatorio').jqxTree('addTo', { label: 'Item',id:(treeItems.length + 1) }, selectedItem.element, true);
            
            $('#campos_relatorio').jqxTree('render');
        }
        else {
        	 $('#campos_relatorio').jqxTree('addTo', { label: 'Item',id:(treeItems.length + 1),parentId:0 }, null, true);
             // update the tree.
             $('#campos_relatorio').jqxTree('render');
             
        }
    });
	$("#Remove_item").unbind('click');
	$('#Remove_item').click(function () {
        var selectedItem = $('#campos_relatorio').jqxTree('selectedItem');
        var item = $('#campos_relatorio').jqxTree('getSelectedItem');
        if (selectedItem != null) {
        	 $.ajax({
          		  type: "POST",
          		  data: {"opt":"6",
          			  
          			  "id_item":item.id,
          			  "parent_id_item":item.parentId,
          			  "relatorio_id_item":relatorio_id},		  
          		    
          		  url: "./RelatoriosServlet",
          		  cache: false,
          		  dataType: "text",
          		  success: onSuccessremoveItem
          		});
        	 function onSuccessremoveItem(data){
        		 if(data=="Campo Removido com sucesso"){
        			 $('#campos_relatorio').jqxTree('removeItem', selectedItem.element, false);
        	            // update the tree.
        	            $('#campos_relatorio').jqxTree('render');
        	            $.alert(data.toString());
        		 }else{
        			 $.alert(data.toString());
        		 }
        	 }
            // removes the selected item. The last parameter determines whether to refresh the Tree or not.
            // If you want to use the 'removeItem' method in a loop, set the last parameter to false and call the 'render' method after the loop.
           
        }
    });
	$("#Update_item").unbind('click');
	 $('#Update_item').click(function () {
		 item_tipo=$('#select_tipo_item_vistoria').find("option:selected").text();
         //var selectedItem = $('#campos_relatorio').jqxTree('selectedItem');
         var selectedItem = $('#campos_relatorio').jqxTree('getSelectedItem');
         console.log(selectedItem);
         if (selectedItem != null) {
        	 if(selectedItem.parentId==''){
        		 $('#campos_relatorio').jqxTree('updateItem', { parentId: 0 }, selectedItem.element);
        	 }
             $('#campos_relatorio').jqxTree('updateItem', { label: document.getElementById('nome_item_vistoria').value }, selectedItem.element);
             // update the tree.
             
             $('#campos_relatorio').jqxTree('render');
         }
         $('#campos_relatorio').jqxTree('refresh');
         var item = $('#campos_relatorio').jqxTree('getSelectedItem');
         
         $.ajax({
   		  type: "POST",
   		  data: {"opt":"3",
   			  "nome_item":item.label,
   			  "desc_item":item.value,
   			  "id_item":item.id,
   			  "parent_id_item":item.parentId,
   			  "relatorio_id_item":relatorio_id},		  
   		    
   		  url: "./RelatoriosServlet",
   		  cache: false,
   		  dataType: "text",
   		  success: onSuccessCriaItem
   		});
   	function onSuccessCriaItem(data){
   		$.alert(data.toString())
   	}
         
         
         document.getElementById('nome_item_vistoria').value="";
         document.getElementById('desc_item_vistoria').value="";
     });
}
function deleta_relatorio(){
	$.alert("Em desenvolvimento");
}
function tabela_relatorio(){
	if ( ! $.fn.DataTable.isDataTable( '#tabela_relatorios' ) ) {
	$('#tabela_relatorios').DataTable({
		"searching": false,
	    "ordering": false,
	    "lengthChange": false,
	    "processing": true,
        "ajax": './RelatoriosServlet?opt=2',
        "lengthMenu": [ 15, 30, 50]
	});
	}else{
		var table = $('#tabela_relatorios').DataTable();
		table.destroy();
		$('#tabela_relatorios').DataTable({
			"searching": false,
		    "ordering": false,
		    "lengthChange": false,
		    "processing": true,
	        "ajax": './RelatoriosServlet?opt=2',
	        "lengthMenu": [ 15, 30, 50]
		});
	}
}
function criar_relatorio(){
	var tipo_rel=$('#select_tipo_relatorio').find("option:selected").text();
	$.ajax({
		  type: "POST",
		  data: {"opt":"1",
			  "nome":document.getElementById('nome_relatorio').value,
			  "desc":document.getElementById('desc_relatorio').value,
			  "tipo":tipo_rel},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./RelatoriosServlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccessCriaRelatório
		});
	function onSuccessCriaRelatório(data){
		$.alert(data.toString())
	}
}