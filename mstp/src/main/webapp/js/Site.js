function append_site_novo_aprovacoes(){
	var $table = $('#tabela_aprovacoes');
	 $.getJSON('./SiteMgmt?opt=7', function(data) {	
		 
		 $table.bootstrapTable('append', data);
         //$table.bootstrapTable('scrollTo', 'bottom');
	 });
}

function Add_site(){
var siteid,nome,lat,lng,cidade,bairro,endereco,pais,operadora;
	//alert("Adicionando site");
	
	siteid= document.getElementById("site_siteid").value;
	nome=document.getElementById("site_sitename").value;
	lat= document.getElementById("site_lat").value;
	lng= document.getElementById("site_lng").value;
	cidade= document.getElementById("site_municipio").value;
	bairro= document.getElementById("site_bairro").value;
    endereco= document.getElementById("site_endereco").value;
    pais= document.getElementById("site_UF").value;
    operadora= document.getElementById("site_operadora").value;
    $.ajax({
		  type: "POST",
		  data: {"opt":"1",
			"siteid":siteid,  
			"nome":nome,
			"lat":lat, 
			"lng":lng, 
			"municipio":cidade, 
			"bairro":bairro,
            "endereco":endereco,
            "uf":pais,
            "operadora":operadora
			
		  },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./SiteMgmt",
		  cache: false,
		  dataType: "text",
		  success: onSuccessSite1
		});
	
	function onSuccessSite1(data)
	{
		alert(data);
		document.getElementById("site_siteid").value="";
		document.getElementById("site_sitename").value="";
		document.getElementById("site_lat").value="";
		document.getElementById("site_lng").value="";
		document.getElementById("site_municipio").value="";
		document.getElementById("site_bairro").value="";
	    document.getElementById("site_endereco").value="";
	    document.getElementById("site_UF").value="";
	    document.getElementById("site_operadora").value="";
	}
}
function carrega_tabela_site(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"2"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./SiteMgmt",
		  cache: false,
		  dataType: "text",
		  success: onSuccessSite2
		});
	function onSuccessSite2(data)
	{
		$("#div_tabela_Sites").html("<div id=\"toolbar_tabela_Sites\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#modal_site_add\">Novo Site</button>"+
    			"<button id=\"btn_deletan_sites\" type=\"button\" class=\"btn btn-danger\">Desabilitar Site</button>"+
    			"<div class=\"btn-group\" role=\"group\">"+
    		    "<button id=\"btnGroupDrop1\" type=\"button\" class=\"btn btn-secondary aria-haspopup=\"true\" dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">"+
    		     "Importar"+
    		     "<span class=\"caret\"></span> "+
    		    "</button>"+
    		    "<ul class=\"dropdown-menu\" aria-labelledby=\"btnGroupDrop1\">"+
    		    "  <li><a class=\"dropdown-item\" href=\"#\" onclick='atualiza_flag()'>Substuição Completa</a></li>"+
    		     " <li><a class=\"dropdown-item\" href=\"#\" data-toggle=\"modal\" data-target=\"#modal_carrega_sites\">Adicionar novos Sites</a></li>"+
    		     " <li><a class=\"dropdown-item\" href=\"#\">Atualizar informações existentes</a></li>"+
    		    "</ul>"+
    		  "</div>"+
    		  "<div class=\"btn-group\" role=\"group\">"+
  		    "<button id=\"btnGroupDrop2\" type=\"button\" class=\"btn btn-secondary aria-haspopup=\"true\" dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">"+
  		     "Exportar"+
  		     "<span class=\"caret\"></span> "+
  		    "</button>"+
  		    "<ul class=\"dropdown-menu\" aria-labelledby=\"btnGroupDrop1\">"+
  		    "  <li><a class=\"dropdown-item\" href=\"#\" onclick=\"exportarTodosSites('VIVO')\">VIVO</a></li>"+
  		  "  <li><a class=\"dropdown-item\" href=\"#\" onclick=\"exportarTodosSites('NEXTEL')\">NEXTEL</a></li>"+
  		"  <li><a class=\"dropdown-item\" href=\"#\" onclick=\"exportarTodosSites('CLARO')\">CLARO</a></li>"+
  		
  		"  <li><a class=\"dropdown-item\" href=\"#\" onclick=\"exportarTodosSites('TIM')\">TIM</a></li>"+
  		    "</ul>"+
  		  "</div>"+
    		 
    			"<a id=\"template_importar_site\" href=\"templates/templateSiteImportacao.xlsx\" type=\"button\" class=\"btn btn-primary\">Template(importação)</a>"+
    			
    		    "</div>" + data);
		$('#tabela_sites').bootstrapTable();
		var $table = $('#tabela_sites'),
	     $button = $('#btn_deletan_sites');
	 $(function () {
	     $button.click(function () {
	    	 
	         var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
	             return row.siteiDsys_tbl;
	         });
	         
	         $.ajax({
	   		  type: "POST",
	   		  data: {"opt":"6",
	   			  "sites":ids.toString()},		  
	   		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
	   		  url: "./SiteMgmt",
	   		  cache: false,
	   		  dataType: "text",
	   		  success: Desabilita_site_n
	   		});
	         function Desabilita_site_n(data){
	        	 alert(data);
	        	 $table.bootstrapTable('remove', {
		             field: 'siteiDsys_tbl',
		             values: ids
		         });
	         }
	         
	     });
	 });
	}

}
function atualiza_flag(){
	
	$.confirm({
		icon: 'fas fa-exclamation-triangle',
	    title: 'Subistuicao completa',
	    content: 'Esta ação irá desabilitar os sites atuais e inserir os novos. Deseja continuar?',
	    type:'red',
	    columnClass:'medium',
	    buttons: {
	        Sim: function () {
	        	document.getElementById('flag_substituicao').value='S';
	        	//$("#input_sites_arq").fileinput('destroy');
	        	//$("#input_sites_arq").fileinput({
	            //	language: "pt-BR",
				//    uploadUrl: "./Upload_servlet", // server upload action
				//    uploadAsync: false,
				//    allowedFileExtensions: ['xlsx'],
				//    maxFileCount: 5,
				//    uploadExtraData: {
			    //        flag: document.getElementById('flag_substituicao').value
			    //        
			    //    }
				//});
	        	$('#modal_carrega_sites').modal('show');
	        },
	        Não: function () {
	            
	        }
	        
	    }
	});
	
	 
}
function exportarTodosSites(Operadora){
	var timestamp = Date.now();	
	$('#btnGroupDrop2').addClass( "disabled" );
	$('#btnGroupDrop2').text('Aguarde a Finalização...');
	$('#btnGroupDrop2').prop('disabled', true);
	 $.ajax({
	        url: './SiteMgmt?opt=11',
	        method: 'GET',
	        data:{"operadora":Operadora},
	        xhrFields: {
	            responseType: 'blob'
	        },
	        success: function (data) {
	        	//console.log(data);
	            var a = document.createElement('a');
	            var url = window.URL.createObjectURL(data);
	            a.href = url;
	            a.download = Operadora+'_SitesMSTP_'+timestamp+'.xlsx';
	            a.click();
	            window.URL.revokeObjectURL(url);
	            $('#btnGroupDrop2').removeClass( "disabled" );
	    		$('#btnGroupDrop2').prop('disabled', false);
	    		$('#btnGroupDrop2').text('Exportar');
	    	
	        }
	    });
	 
}
function deleta1_site(ids){
	//alert(ids);
	var aux=ids.split(";");
	//alert(aux[0]);
	//alert(aux[1]);
	$.ajax({
		  type: "POST",
		  data: {"opt":"4",
			  "site":aux[0]},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./SiteMgmt",
		  cache: false,
		  dataType: "text",
		  success: Desabilita_site
		});
	function Desabilita_site(data){
		var $table = $('#tabela_sites');
		$table.bootstrapTable('removeByUniqueId', aux[1]);
		alert(data);
	}
	
}
