function carrega_tabela_mstp_mobile(usuario){
	
	if(usuario=='todos'){
		$(".janelas").hide();
		document.getElementById('mstp_mobile_report_diario').style.display = "block";
		g5('category','grafico_container_mstp_mobile_report');
	}
	if ( ! $.fn.DataTable.isDataTable( '#tabela_registro_mstp_mobile' ) ) {
	$('#tabela_registro_mstp_mobile').DataTable( {
		"processing": true,
		"scrollY": "200px",
        "ajax": "./UserMgmt?opt=10&usuario="+usuario,
        "lengthMenu": [ 5, 10, 20, 50]
	});
	}else{
		var table = $('#tabela_registro_mstp_mobile').DataTable();
		table.ajax.url( "./UserMgmt?opt=10&usuario="+usuario ).load(); 
		table.ajax.reload( null, false );
	}
	
}