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
