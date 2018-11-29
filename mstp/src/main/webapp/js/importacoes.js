function carrega_importacoes(){
		var timestamp=Date.now();
		if ( ! $.fn.DataTable.isDataTable( '#tabela_arquivos_importados' ) ) {
			
		$('#tabela_arquivos_importados').DataTable( {
			dom: 'Bfrtip',
			buttons: {
	        buttons: [
	        	{
	                
	        		text: 'Atualizar',
	                action: function ( e, dt, node, config ) {
	                    //alert( 'Activated!' );
	                    $('#tabela_arquivos_importados').DataTable().ajax.reload();
	                }
	                
	            }
	            
	            
	        ]},
	        "processing": true,
	        "order": [],
	        "ajax": "./POControl_Servlet?opt=38&_"+timestamp,
	        "lengthMenu": [ 15, 30, 100, 200]
	        
	    });
		}else{
			var table = $('#tabela_arquivos_importados').DataTable();
			table.destroy();
			$('#tabela_arquivos_importados').DataTable( {
				dom: 'Bfrtip',
				buttons: {
		        buttons: [
		        	{
		        		text: 'Atualizar',
		                action: function ( e, dt, node, config ) {
		                    //alert( 'Activated!' );
		                    $('#tabela_arquivos_importados').DataTable().ajax.reload();
		                }
		                
		            }
		            
		        ]},
		        "processing": true,
		        "order": [],
		        "ajax": "./POControl_Servlet?opt=38&_"+timestamp,
		        "lengthMenu": [ 15, 30, 100, 200]
		        
		    });
		}
 
}