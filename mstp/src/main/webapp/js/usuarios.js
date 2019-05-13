function remover_justificativa(){
	var rowindexes = $('#grid_justificativas').jqxGrid('getselectedrowindexes');
	filtros={"filtros":[]};
	for(var v=0;v<rowindexes.length;v++){
			filtros.filtros.push($('#grid_justificativas').jqxGrid('getrowid', rowindexes[v]));
		}
	var timestamp =Date.now();
	$.ajax({
		  type: "POST",
		  data: {"opt":"36",
			  "_": timestamp,
			  "filtros":JSON.stringify(filtros)
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		 success: sucesso_remove_justificativas
		});
	function sucesso_remove_justificativas(data){
		$.alert(data.toString());
		carrega_justificativas();
	}
}
function carrega_justificativas(){
	var timestamp =Date.now();
	$.ajax({
		  type: "POST",
		  data: {"opt":"35",
			  "_": timestamp
			  
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./UserMgmt",
		  cache: false,
		  dataType: "text",
		 success: sucesso_carrega_justificativas
		});	
	 function sucesso_carrega_justificativas(data){
		 dataaux=JSON.parse(data);
			
		 var source =
	     {
				 datatype: "json",
		         datafields: [
		        	 { name: 'id' , type: 'string'},
		        	 { name: 'justificativa', type: 'string' },
		             { name: 'descricao', type: 'string' },
		             { name: 'foto' , type: 'string'},
		             { name: 'owner' , type: 'string'},
		             { name: 'dtadd' , type: 'string'}
		             
		         ],
	         localdata: dataaux,
	         id: 'id',
	     };
		 var dataAdapter = new $.jqx.dataAdapter(source);
		 
	    
	     $("#grid_justificativas").jqxGrid(
	             {
	            	 width: getWidth('container_tabela_justificativas')-50,
	                 height: 350,
	                 source: dataAdapter,
	                 columnsresize: true,
		             filterable: true,
		             autoshowfiltericon: true,
	                 rowsheight: 55,
	                 selectionmode: 'checkbox',
	                 columns: [
	                       { text: 'Justificativa', datafield: 'justificativa',filtertype: 'textbox'},
	                       { text: 'Descrição', datafield: 'descricao',filtertype: 'checkedlist' },
	                       { text: 'Foto Requirida', datafield: 'foto',filtertype: 'checkedlist' },
	                       { text: 'Criado Por', datafield: 'owner',filtertype: 'checkedlist' },
	                       { text: 'Criado em', datafield: 'dtadd',filtertype: 'checkedlist' },
	                       
	                   ]
	                  
	             });
	 }

}
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
		$.alert(data.toString());
		carrega_justificativas();
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
		
		$("#splitter2").jqxSplitter({ width: '100%', theme:'light', height: '90%', panels: [{ size: 350}] });
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
	 var timestamp = Date.now();
	 
	 $.getJSON('./UserMgmt?opt=37&_='+timestamp, function(data) {
			var source =
	        {
	            datatype: "json",
	            datafields: [
	                { name: 'id' },
	                { name: 'parentid' },
	                { name: 'text' },
	                { name: 'value' }
	            ],
	            id: 'id',
	            localdata: data
	        };
			var dataAdapter = new $.jqx.dataAdapter(source);
			 dataAdapter.dataBind();
			 var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{ name: 'text', map: 'label'}]);
	         $('#jqxTree_grupos_usuarios').jqxTree({  theme:'light',source: records, hasThreeStates: true, checkboxes: true,height: '100%', width: '100%'});
	         $('#jqxTree_grupos_usuarios').jqxTree('expandAll');
	         $("#jqxTree_grupos_usuarios").jqxTree('selectItem', $("#grupo1")[0]);
	         carrega_grupo_usuario();
	         $('#jqxTree_grupos_usuarios').on('select',function (event)
	        		 {
	        	       $('#jqxgrid_grupo_usuario').jqxGrid('destroy');
	        	       $('#container_grupos_usuarios').html('<div id="jqxgrid_grupo_usuario"></div>')
	        	 		carrega_grupo_usuario();
	        		 });
	         
	         var contextMenu = $("#jqxMenu_tree_grupos_usuarios").jqxMenu({  theme:'light',width: '120px',  height: '56px', autoOpenPopup: false, mode: 'popup' });
	         var clickedItem = null;
	         var attachContextMenu = function () {
	             // open the context menu when the user presses the mouse right button.
	             $("#jqxTree_grupos_usuarios li").on('mousedown', function (event) {
	                 var target = $(event.target).parents('li:first')[0];
	                 var rightClick = isRightClick(event);
	                 if (rightClick && target != null) {
	                     $("#jqxTree_grupos_usuarios").jqxTree('selectItem', target);
	                     var scrollTop = $(window).scrollTop();
	                     var scrollLeft = $(window).scrollLeft();
	                     contextMenu.jqxMenu('open', parseInt(event.clientX) + 5 + scrollLeft, parseInt(event.clientY) + 5 + scrollTop);
	                     return false;
	                 }
	             });
	         }
	         attachContextMenu();
	         $("#jqxMenu_tree_grupos_usuarios").on('itemclick', function (event) {
	             var item = $.trim($(event.args).text());
	             switch (item) {
	                 case "Renomear":
	                     var selectedItem = $('#jqxMenu_tree_grupos_usuarios').jqxTree('selectedItem');
	                     if (selectedItem != null) {
	                    	 $('#modal_atualiza_no_rollout').modal('show');
	                         attachContextMenu();
	                     }
	                     break;
	                 case "Remove Item":
	                     var selectedItem = $('#jqxTree_grupos_usuarios').jqxTree('selectedItem');
	                     if (selectedItem != null) {
	                         $('#jqxTree_grupos_usuarios').jqxTree('removeItem', selectedItem.element);
	                         attachContextMenu();
	                     }
	                     break;
	             }
	         });
	         $(document).on('contextmenu', function (e) {
	             if ($(e.target).parents('.jqx-tree').length > 0) {
	                 return false;
	             }
	             return true;
	         });
	         function isRightClick(event) {
	             var rightclick;
	             if (!event) var event = window.event;
	             if (event.which) rightclick = (event.which == 3);
	             else if (event.button) rightclick = (event.button == 2);
	             return rightclick;
	         }
		});
	 $('#btn_grupo_time').jqxButton({ height: '25px', width: '100px'});
	 $('#btn_grupo_time').click(function () {
         var selectedItem = $('#jqxTree_grupos_usuarios').jqxTree('selectedItem');
         if (selectedItem != null) {
             $('#jqxTree_grupos_usuarios').jqxTree('addAfter', { label: 'Grupo' }, selectedItem.element, false);
             
             $('#jqxTree_grupos_usuarios').jqxTree('render');
         }
     });
	 
	 
	}

}
function carrega_grupo_usuario(){
	var item = $('#jqxTree_grupos_usuarios').jqxTree('getSelectedItem');
	 var grupoid;
	 if (item != null) {
		 grupoid=item.value;
		 
		 if(grupoid==""){
			 return;
		 }
	 }else{
		 grupoid="grupo1";
	 }
	 
	 var timestamp =Date.now();
		$.ajax({
			  type: "POST",
			  data: {"opt":"38",
				  "_": timestamp,
				  "grupoid":grupoid
				},		  
			  //url: "http://localhost:8080/DashTM/D_Servlet",	  
			  url: "./UserMgmt",
			  cache: false,
			  dataType: "text",
			 success: atualiza_grid_grupo_usuario
			});	
		 function atualiza_grid_grupo_usuario(data){
			 dataaux=JSON.parse(data);
				
			 var source =
		     {
					 datatype: "json",
			         datafields: [
			        	 { name: 'id' , type: 'int'},
			        	 { name: 'Empresa' , type: 'int'},
			        	 { name: 'usuario', type: 'string' },
			             { name: 'funcionario_nome', type: 'string' },
			             { name: 'lider' , type: 'string'}
			             
			         ],
		         localdata: dataaux,
		         id: 'id',
		     };
			 var dataAdapter = new $.jqx.dataAdapter(source);
			 $("#jqxgrid_grupo_usuario").jqxGrid(
		             {
		                 width: 500,
		                 height: 650,
		                 source: dataAdapter,
		                 columnsresize: true,
			             filterable: true,
			             autoshowfiltericon: true,
		                 rowsheight: 50,
		                 pageable: true,
		                 altrows: true,
		                 selectionmode: 'checkbox',
		                 columns: [
		                       { text: 'Funcionário', datafield: 'funcionario_nome',cellsalign: "center",width: 400,filtertype: 'textbox'},
		                       { text: 'Líder', datafield: 'lider',cellsalign: "center", width: 100,filtertype: 'checkedlist' }
		                       
		                   ]
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
