function inicializa_scheduler_feriado(){
	
var source =
            {
                dataType: 'json',
                dataFields: [
                    { name: 'id', type: 'string' },
                    { name: 'status', type: 'string' },
                    { name: 'name', type: 'string' },
                    
                    { name: 'start', type: 'date', format: "dd/MM/yyyy" },
                    { name: 'end', type: 'date', format: "dd/MM/yyyy" }
                ],
                id: 'id',
                url: './UserMgmtRponto?opt=25'
            };
            var adapter = new $.jqx.dataAdapter(source);
            $("#scheduler_feriado").jqxScheduler({
                date: new $.jqx.date(2018, 10, 02),
                width: 1200,
                height: 800,
                source: adapter,
                showLegend: true,
                editDialog: false,
                ready: function () {
               
                },
                appointmentDataFields:
                {
                	allDay: "allDay",
                	from: "start",
                    to: "end",
                    id: "id",
                    subject: "name",
                    status: "status"
                },
                view: 'monthView',
                views:
                [
                    'dayView',
                    'weekView',
                    'monthView'
                ]
            });
           
            $('#scheduler_feriado').on('cellClick', function (event) { 
            	var args = event.args; 
            	var cell = args.cell; 
            	var date = args.date; 
            	//console.log(args);
            	//console.log(cell);
            	//console.log(date);
            	var appointment={
            			id:"",
            			allDay: "allDay",
            			start:args.cell.dataset.date,
            			end:args.cell.dataset.date,
            		    name:"Feriado",
            		    status:""
            	}
            	//console.log(appointment);
            	$('#scheduler_feriado').jqxScheduler('addAppointment', appointment);
            });
            
            $('#scheduler_feriado').on('appointmentDoubleClick', function (event) { 
            	var args = event.args; 
            	var appointment = args.appointment; 
            	
            	$.confirm({
            		title:'Remover/Atualizar Feriado?',
            		content:'Voce pode Remover o Feriado ou Atualizar o título do Feriado<br><br><input id="nome_feriado_update" name="nome_feriado_update" type="text" class="nome_feriado form-control">',
            		type : 'red',
            		buttons:{
            			Remover: function () {
            				$('#scheduler_feriado').jqxScheduler('selectAppointment', appointment.id);
            				$('#scheduler_feriado').jqxScheduler('deleteAppointment', appointment.id);
            	        },
            	        AtualizarNome: function () {
            	        	var name = this.$content.find('.nome_feriado').val();
            	        	$('#scheduler_feriado').jqxScheduler('setAppointmentProperty', appointment.id, "subject", name);
            	        	$.ajax({
            	          		  type: "POST",
            	          		  data: {"opt":"29",
            	          			  "id":appointment.id,
            	          			"nome":name,
            	          			 },		  
            	          		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
            	          		  url: "./UserMgmtRponto",
            	          		  cache: false,
            	          		  dataType: "text"
            	          		 
            	          		});
            	        },
            	        Cancelar: function () {
            	            
            	        }
            			
            		}
            			
            		
            	});
            });
            $('#scheduler_feriado').on('appointmentAdd', function (event) { 
            	var args = event.args; 
            	var appointment = args.appointment; 
            	console.log(appointment);
            	$.ajax({
          		  type: "POST",
          		  data: {"opt":"26",
          			  "id":appointment.id,
          			"nome":appointment.subject,
          			"inicio":moment(appointment.originalData.start).format('L'),
          			"fim":moment(appointment.originalData.end).format('L')
          			
          		  },		  
          		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
          		  url: "./UserMgmtRponto",
          		  cache: false,
          		  dataType: "text",
          		  success: cria_feriado
          		});
            	function cria_feriado(data){
            		var aux=data.split("-");
            		alert(aux[0]);
            		$('#scheduler_feriado').jqxScheduler('setAppointmentProperty', appointment.id, "id", aux[0]);
            		$.alert(data.toString());
            	}
            });
            $('#scheduler_feriado').on('appointmentDelete', function (event) { 
            	var args = event.args; 
            	var appointment = args.appointment; 
            	alert("event delete appointment");
            	$.ajax({
            		  type: "POST",
            		  data: {"opt":"27",
            			  "id":appointment.id
            		},		  
            		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
            		  url: "./UserMgmtRponto",
            		  cache: false,
            		  dataType: "text",
            		  success: deleta_feriado
            		});
              	function deleta_feriado(data){
              		$.alert(data.toString());
              	}
            
            });
}
