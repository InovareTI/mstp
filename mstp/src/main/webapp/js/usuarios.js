function add_justificativas(){
	
	var justificativa= document.getElementById("input_justificativa").value;
	var desc_jus= document.getElementById("input_justificativa_desc").value;
	var foto= document.getElementById("input_justificativa_foto").checked;
	
	 $.ajax({
  		  type: "POST",
  		  data: {"opt":"33",
  			  "justificativa":justificativa,
  			  "desc":desc_jus,
  			  "foto":foto
  		  },		  
  		  url: "./UserMgmt",
  		  cache: false,
  		  dataType: "text",
  		  success: Add_justificatas_resultado
  		});
	function Add_justificatas_resultado(data){
		$.alert(data.toString())
	}
}

function carrega_faltas(){

	
	var mes =  $('#select_mes_faltas').val();
	var mes_aux=$('#select_mes_faltas').find("option:selected").text();
	var func =  $('#select_func_faltas').val();
	var dados_usuario={};
	
	
	if(mes==''){
		$.alert("Mes inválido");
		return;
	}else{
		if ( ! $.fn.DataTable.isDataTable( '#tabela_faltas' ) ) {
			
		$('#tabela_faltas').DataTable( {
			dom: 'Bfrtip',
	        buttons: [
	        	{
	                extend: 'print',
	                text: 'Imprimir'
	                
	            },
	            {
	        		extend: 'excelHtml5'
	        	},
	            {
	                extend: 'pdfHtml5'
	                
	            }
	            
	        ],
	        "processing": true,
	        "ajax": "./UserMgmt?opt=23&mes="+mes+"&func="+func,
	        "lengthMenu": [ 15, 30, 100, 200]
	        
	    });
		}else{
			var table = $('#tabela_faltas').DataTable();
			table.destroy();
			$('#tabela_faltas').DataTable( {
				dom: 'Bfrtip',
		        buttons: [
		        	{
		                extend: 'print',
		                text: 'Imprimir'
		                
		            },
		            {
		        		extend: 'excelHtml5'
		        	},
		            {
		                extend: 'pdfHtml5'
		                
		            }
		            
		        ],
		        "processing": true,
		        "ajax": "./UserMgmt?opt=23&mes="+mes+"&func="+func,
		        "lengthMenu": [ 15, 30, 100, 200]
		        
		    });
		}
	}
	
	
}
function carrega_usuarios(){
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"18"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess18
		});
	function onSuccess18(data)
	{
		$("#div_tabela_usuario").html("<div id=\"toolbar_tabela_usuario\" role=\"toolbar\" class=\"btn-toolbar\">"+
        		
    			"<button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#modal_user_add\">Novo Usuário</button>"+
    			"<button id=\"desabilitar_usuario\" type=\"button\" class=\"btn btn-danger\">Desabilitar Usuário</button>"+
    			"<button id=\"btn_reset_senha\" type=\"button\" class=\"btn btn-danger\" onclick=\"reset_senha()\">Redefinir Senha</button>"+
    			"<button id=\"btn_ferias\" type=\"button\" class=\"btn btn-info\" onclick=\"iniciar_ferias()\">Agendar Férias de Funcionário</button>"+
    			"<div class=\"btn-group\" role=\"group\">"+
    		    "<button id=\"btnGroupDrop2\" type=\"button\" class=\"btn btn-secondary aria-haspopup=\"true\" dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">"+
    		     "Importar"+
    		     "<span class=\"caret\"></span> "+
    		    "</button>"+
    		    "<ul class=\"dropdown-menu\" aria-labelledby=\"btnGroupDrop1\">"+
    		    "  <li><a class=\"dropdown-item\" href=\"#\">Substuição Completa</a></li>"+
    		     " <li><a class=\"dropdown-item\" href=\"#\" data-toggle=\"modal\" data-target=\"#modal_add_usuarios\">Adicionar novos Usuários</a></li>"+
    		     " <li><a class=\"dropdown-item\" href=\"#\" data-toggle=\"modal\" data-target=\"#modal_atualiza_usuarios\">Atualizar informações existentes</a></li>"+
    		    "</ul>"+
    		  "</div>"+
    			"<a href=\"javascript:window.location='UserMgmt?opt=16'\" class=\"btn btn-primary\">Exportar</button>"+
    			"<a id=\"template_importar_usuario\" href=\"templates/template_usuarios_mstp.xlsx\" type=\"button\" class=\"btn btn-primary\">Template(importação)</a>"+
    		    "</div>" + data);
		$('#tabela_usuario').bootstrapTable();
		var $table = $('#tabela_usuario'),
	     $button = $('#desabilitar_usuario');
	 $(function () {
	     $button.click(function () {
	    	 
	         var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
	             return row.user;
	         });
	         $.ajax({
		   		  type: "POST",
		   		  data: {"opt":"3",
		   			  "users":ids.toString()},		  
		   		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		   		  url: "./UserMgmt",
		   		  cache: false,
		   		  dataType: "text",
		   		  success: Desabilita_usuario_n
		   		});
	         function Desabilita_usuario_n(data){
	        	 //alert(data);
	        	 $table.bootstrapTable('remove', {
		             field: 'user',
		             values: ids
		         });
	         }
	     });
	 });
	}

}
function reset_senha(){
	var $table = $('#tabela_usuario');
	 var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
         return row.user;
     });
	 console.log(ids);
	$.confirm({
	    title: 'Redefinir senha - '+ids.length+' usuários selecionados',
	    content: '' +
	    '<form action="" class="formName">' +
	    '<div class="form-group">' +
	    '<label>Insira a nova senha</label>' +
	    '<input type="text" placeholder="Nova Senha" class="name form-control" required />' +
	    '</div>' +
	    '</form>',
	    columnClass:'col-md-6 col-md-offset-3',
	    buttons: {
	        formSubmit: {
	            text: 'Redefinir',
	            btnClass: 'btn-blue',
	            action: function () {
	                var name = this.$content.find('.name').val();
	                if(!name){
	                    $.alert('Insira uma Senha Válida');
	                    return false;
	                }else{
	                	$.ajax({
	      	      		  type: "POST",
	      	      		  data: {"opt":"24",
	      	      			  "senha":name,
	      	      			  "usuarios":ids.toString()},		  
	      	      		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
	      	      		  url: "./UserMgmt",
	      	      		  cache: false,
	      	      		  dataType: "text",
	      	      		  success: onSuccessReset
	      	      		});
	      	            function onSuccessReset(data){
	      	            	$.alert(data.toString());
	      	            	$table.bootstrapTable('uncheckAll');
	      	            }
	                }
	                
	            }
	        },
	        cancel: function () {
	            //close
	        },
	    },
	    onContentReady: function () {
	        // bind to events
	        var jc = this;
	        this.$content.find('form').on('submit', function (e) {
	            // if the user submits the form by pressing enter in the field.
	            e.preventDefault();
	            
	            jc.$$formSubmit.trigger('click'); // reference the button and click it
	        });
	    }
	});
}
function iniciar_ferias(){
	var $table = $('#tabela_usuario');
	 var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
         return row.user;
     });
	 if(ids.length==0){
		 
		 $.alert('Selecione um Usuário');
		 return;
	 }
	 var d;
	 var i;
	 var f;
	 var tudo=JSON.parse('{"i":"0","d":"0","f":"0","ano_base":"0"}');
	 $('#modal_lanca_ferias').modal('show');
	 $("#ferias_nome_usuario").html(ids[0]);
	 $("#range_ferias").jqxDateTimeInput({selectionMode: 'range'});
	 $("#range_ferias").on('change', function (event) {
		 var selection = $("#range_ferias").jqxDateTimeInput('getRange');
		  i = moment(selection.from).format('L');
		  f = moment(selection.to).format('L');
		  d = parseInt(moment.duration(moment(selection.to).diff(moment(selection.from))).asDays());
		  tudo.i=i;
		  tudo.f=f;
		  tudo.d=d;
		 //console.log(selection);
         $('#log_dias_ferias').html(d+' dias de férias.');
     });
	 $button = $('#add_lanca_ferias_btn');
	 $(function () {
	     $button.click(tudo,function () {
	    	 tudo.ano_base=$('#select_ferias_ano_base').val();
	    	 if(tudo.ano_base==""){
	    		 $.alert('Selecione o Ano Referência das Férias');
	    		 return;
	    	 }
	    	 if(tudo.d > 30){
	    		 $.confirm({
	    			  title: 'Periodo de Férias Inconsistente',
	    			  content: 'O período selecionado é maior do que 30 dias. Deseja Continua?',
	    			  buttons: {
	    			        Continuar: function () {
	    			        	$.ajax({
	    		      	      		  type: "POST",
	    		      	      		  data: {"opt":"31",
	    		      	      			  "inicio":tudo.i,
	    		      	      			  "fim":tudo.f,
	    		      	      			  "duracao":tudo.d,
	    		      	      			  "usuario":ids[0].toString(),
	    		      	      			  "ano_base":tudo.ano_base},		  
	    		      	      		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
	    		      	      		  url: "./UserMgmt",
	    		      	      		  cache: false,
	    		      	      		  dataType: "text",
	    		      	      		  success: onSuccesslancaFerias
	    		      	      		});
	    		      	            function onSuccesslancaFerias(data){
	    		      	            	$.alert(data.toString());
	    		      	            	$table.bootstrapTable('uncheckAll');
	    		      	            	$('#modal_lanca_ferias').modal('hide');
	    		      	            }
	    			        },
	    			        Cancelar: function () {
	    			            
	    			        }
	    			  }
	    		 });
	    	 }else if(tudo.d < 1){
	    		 $.alert({title:'Período Inválido!',
	    			    content: 'Período de Férias precisa ser maior ou igual a 1 dia.',
	    			    type:'red'});
	    	 }else{
	    		 $.ajax({
     	      		  type: "POST",
     	      		  data: {"opt":"31",
     	      			  "inicio":tudo.i,
     	      			  "fim":tudo.f,
     	      			  "duracao":tudo.d,
     	      			  "usuario":ids[0].toString(),
     	      			  "ano_base":tudo.ano_base},		  
     	      		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
     	      		  url: "./UserMgmt",
     	      		  cache: false,
     	      		  dataType: "text",
     	      		  success: onSuccesslancaFerias
     	      		});
     	            function onSuccesslancaFerias(data){
     	            	$.alert(data.toString());
     	            	$table.bootstrapTable('uncheckAll');
     	            	$('#modal_lanca_ferias').modal('hide');
     	            }
	    	 }
	     });
	     }); 
}
function exportar_usuarios(){
	$.ajax({
		  type: "POST",
		  data: {"opt":"16"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		  success: onSuccessExportUser
		});
}
function emDesenvolvimento(){
	alert("Em Desenvolvimento!");
}
function add_user(){
	
	var nome_usuario,usuario,email,matr,perfil;
	//alert("Adicionando usuario");
	
	nome_usuario= document.getElementById("usuario_nome_completo").value;
	
	usuario= document.getElementById("usuario_usuario").value;
	email= document.getElementById("usuario_email").value;
	matr= document.getElementById("usuario_mtr").value;
	perfil=String($('#usuario_perfil').multiselect().val());
	
	//alert(perfil);
	
	$.ajax({
		  type: "POST",
		  data: {"opt":"26",
			"nome":nome_usuario,  
			"usuario":usuario, 
			"email":email, 
			"matricula":matr, 
			"perfil":perfil
			
		  },		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		  success: onSuccess19
		});
	
	function onSuccess19(data)
	{
		
		alert(data);
	}
	}
