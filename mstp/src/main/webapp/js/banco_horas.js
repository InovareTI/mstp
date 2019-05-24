function calcula_horas(dt1,dt2){
    var tot_noturna;
    var tot;
    var tot_normal;
    var min_noturnos;
    tot_noturna=0;
    tot=0;
    tot_normal=0;
    var duration = moment.duration(dt2.diff(dt1));
    tot = duration.asHours();
    tot_normal=tot;
    
    if(tot>0){
    if(dt1.hour()>=18 && dt2.hour()>=22){
    	
    	tot_noturna=tot_noturna + dt2.hour() - 22;
    	min_noturnos=dt2.minutes();
    	tot_noturna=tot_noturna+(min_noturnos / 60);
    	tot_normal=tot-tot_noturna;
    }else if(dt1.hour()<=5 && dt2.hour()<=5){
    	
    	tot_noturna=dt2.hour()-dt1.hour();
    	min_noturnos=dt2.minutes()+dt1.minutes();
    	tot_noturna=tot_noturna+(min_noturnos / 60);
    	tot_normal=tot-tot_noturna;
    	
    }else if(dt1.hour()<=5 && dt2.hour()>=22){
    	tot=tot-10;
    	
    	tot_noturna=tot_noturna + (dt2.hour() - 22);
    	tot_noturna=tot_noturna + (5 - dt1.hour());
    	min_noturnos=dt1.minutes()+dt2.minutes();
    	tot_noturna=tot_noturna+(min_noturnos/60);
    	tot_normal=tot-tot_noturna;
    }else if(dt1.hour()>=8 && dt2.hour()<=19){
    	tot_noturna=0.00;
    	tot_normal=0.00;
    }
    
    }
    
    document.getElementById("horas_normais").value=tot_normal.toFixed(2);
    document.getElementById("horas_noturnas").value=tot_noturna.toFixed(2);
    
}
function add_hours(){
	if($('#banco_entrada').data("DateTimePicker").viewDate()>moment() || $('#banco_saida').data("DateTimePicker").viewDate()>moment()){
		alert("Não é permitido datas no futuro!");
	}else{
	if(document.getElementById("horas_normais").value!="" || document.getElementById("horas_normais").value!=0 || document.getElementById("horas_noturnas").value!=0){
	document.getElementById('div_tabela_resumo_hh').style.display = "block";
	//$('#tabela_resumo_banco').bootstrapTable();
	var $table = $("#tabela_resumo_banco");
	var linhas =$table.bootstrapTable('getData').length;
    var rows = [];
	// Create an empty <tr> element and add it to the 1st position of the table:
	
    rows.push({
    	id:linhas+1,
    	H_data:$('#banco_entrada').data("DateTimePicker").viewDate().format('L'),
    	H_Entrada:$('#banco_entrada').data("DateTimePicker").viewDate().format('L LTS'),
    	H_Saida : $('#banco_saida').data("DateTimePicker").viewDate().format('L LTS'),
    	H_normais : document.getElementById("horas_normais").value,
    	H_noturna : document.getElementById("horas_noturnas").value,
    	H_anotacao: document.getElementById("obs_hh").value,
    });
    $table.bootstrapTable('append', rows);
    $table.bootstrapTable('scrollTo', 'bottom');
    
    $('#banco_entrada').data("DateTimePicker").clear();
    $('#banco_saida').data("DateTimePicker").clear();
    document.getElementById("horas_normais").value="";
    document.getElementById("horas_noturnas").value="";
    document.getElementById("obs_hh").value="";
    $('#banco_saida').data("DateTimePicker").minDate(false);
	$('#banco_saida').data("DateTimePicker").maxDate(false);
	}else{
		$.alert({
			title: 'MSTP WEB',
		    content:'Ajuste corretamente os valores',
		    type: 'red',
		});
	}
	}
}
function armazena_hh(){
	var $table = $("#tabela_resumo_banco");
	var dados =JSON.stringify($table.bootstrapTable('getData'));
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"4",
			"data":dados
			},
			url: "./UserMgmt",
			cache: false,
			dataType: "text",
			success: Add_HH_onSuccess4
			});
	function Add_HH_onSuccess4(data){
		$table.bootstrapTable('removeAll');
	}
}
function carrega_extrato_he(){
	document.getElementById('add_banco_de_Horas').style.display = "none";
	document.getElementById('div_mostra_extrato_hh').style.display = "block";
	$.ajax({
		  type: "POST",
		  data: {"opt":"5"
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		  success: onSuccessUserMgmt5
		});
	function onSuccessUserMgmt5(data)
	{
		$("#div_tabela_extrato_hh").html(data);
		$('#tabela_registro_de_HH').bootstrapTable();
	    
	}

}
function mostra_horas_manuais(){
	document.getElementById('add_banco_de_Horas').style.display = "block";
	document.getElementById('div_mostra_extrato_hh').style.display = "none";
}
function calcula_quantidade_hh_disponivel(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"6"
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		  success: onSuccessUserMgmt6
		});
	function onSuccessUserMgmt6(data)
	{
		$("#exibe_hh12").html(data);
		
	    
	}

}
function carrega_aprovacoes_hh(){

	$.ajax({
		  type: "POST",
		  data: {"opt":"7"
			 },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		  success: onSuccessUserMgmt7
		});
	function onSuccessUserMgmt7(data){
		$("#div_tabela_aprovacoes").html(data);
		$('#tabela_aprovacoes').bootstrapTable();
		append_ponto_aprovacoes();
	}
}
function aprova1_registro(tipo,id){
	var id_aux=id.substr(0,id.search("-"));
	if(tipo=="BancoHH"){
		$.ajax({
			  type: "POST",
			  data: {"opt":"8",
				  "he":id_aux},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./UserMgmt",
			  cache: false,
			  dataType: "text",
			  success: aprova1Success
			});
	}else if(tipo=="AjustePonto"){
		$.ajax({
			  type: "POST",
			  data: {"opt":"14",
				  "id":id_aux},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./UserMgmt",
			  cache: false,
			  dataType: "text",
			  success: aprova1Success
			});
	}else if(tipo=="SiteNovo"){
		if(geral.perfil.search("Site Manager")>=0){
		$.ajax({
			  type: "POST",
			  data: {"opt":"8",
				  "id":id_aux},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./SiteMgmt",
			  cache: false,
			  dataType: "text",
			  success: aprova1Success
			});
		}else{
			$.alert({
				title: 'MSTP WEB',
			    content:'Essa operação necessita de privilégios para cadastro de site.',
			    type: 'red'
			});
		}
	}		
	function aprova1Success(data){
		var $table = $('#tabela_aprovacoes');
		$table.bootstrapTable('removeByUniqueId', id);
		$.alert({
			title: 'MSTP WEB',
		    content:data.toString(),
		    type: 'green',
		});
		if(tipo=="AjustePonto"){
			calcula_quantidade_hh_disponivel();
		}
	}
}
function rejeita1_registro(tipo,id){
	var id_aux=id.substr(0,id.search("-"));
	
	if(tipo=="BancoHH"){
		$.ajax({
			  type: "POST",
			  data: {"opt":"9",
				  "he":id_aux},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./UserMgmt",
			  cache: false,
			  dataType: "text",
			  success: rejeita1Success
			});
	}else if(tipo=="AjustePonto"){
		$.ajax({
			  type: "POST",
			  data: {"opt":"13",
				  "id":id_aux},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./UserMgmt",
			  cache: false,
			  dataType: "text",
			  success: rejeita1Success
			});
	}else if(tipo=="SiteNovo"){
		$.ajax({
			  type: "POST",
			  data: {"opt":"9",
				  "id":id_aux},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./SiteMgmt",
			  cache: false,
			  dataType: "text",
			  success: rejeita1Success
			});
	}
	function rejeita1Success(data){
		var $table = $('#tabela_aprovacoes');
		$table.bootstrapTable('removeByUniqueId', id);
		$.alert({
			title: 'MSTP WEB',
		    content:data.toString(),
		    type: 'green',
		});
	}
}
function carrega_HH_usuario(){
	var mes =  $('#select_mes_folha_HH').val();
	var mes_aux=$('#select_mes_folha_HH').find("option:selected").text();
	var func =  $('#select_func_HH').val();
	if ( ! $.fn.DataTable.isDataTable( '#tabela_usuario_analise_HH' ) ) {
		
	}else{
		var table = $('#tabela_usuario_analise_HH').DataTable();
		table.ajax.url( "./UserMgmt?opt=18&mes="+mes+"&func="+func ).load(); 
		table.ajax.reload( null, false );
	}
}

function recalcular_HH_BH(){
	var mes =  $('#select_mes_folha_ponto').val();
	var mes_aux=$('#select_mes_folha_ponto').find("option:selected").text();
	var func =  $('#select_func_folha_ponto').val();
	var selection=$('#range_espelho').jqxDateTimeInput('getRange');
	$('#btn_gerar_espelho').addClass( "disabled" );
	$('#btn_gerar_espelho').text('Aguarde a Finalização...');
	$('#btn_gerar_espelho').prop('disabled', true);
	
	if(mes=="" || func=="" || !selection.from){
		$.alert({
			title: 'MSTP WEB',
		    content: 'Selecione os dados necessários',
		    type: 'red',
		});
		$('#btn_gerar_espelho').removeClass( "disabled" );
		$('#btn_gerar_espelho').prop('disabled', false);
		$('#btn_gerar_espelho').text('Gerar Espelho de ponto');
	}else{
	$.ajax({
		  type: "POST",
		  data: {"opt":"18",
			  "mes":mes,
			  "inicio":moment(selection.from).format('L'),
			  "fim":moment(selection.to).format('L'),
			  "func":func},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		  success: Success_recalcula_HH_BH
		});
	
	function Success_recalcula_HH_BH(data){
		var aux=data.split(";");
		geral.horas_sabado=aux[1].substr(aux[1].indexOf(":")+1,aux[1].length);
		geral.horas_domingo=aux[2].substr(aux[2].indexOf(":")+1,aux[2].length);
		geral.banco=aux[0].substr(aux[0].indexOf(":")+1,aux[0].length);
		
		$.alert({
			title: 'MSTP WEB',
		    content:data.toString(),
		    type: 'green',
		});
		carrega_ponto_usuario();
		$('#btn_gerar_espelho').removeClass( "disabled" );
		$('#btn_gerar_espelho').prop('disabled', false);
		$('#btn_gerar_espelho').text('Gerar Espelho de ponto');
		$('#resumo_espelho_func').html("Espelho de Ponto - Resumo de horas acumuladas => Banco:"+geral.banco+" | Sábado:"+geral.horas_sabado+" | Domingo:"+geral.horas_domingo);
		
	}
	}
}