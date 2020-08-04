function g11(){
	$.getJSON('./Dashboard_Servlet?opt=11', function(data) {
		
			var config = {
				type: 'doughnut',
				data: {
					datasets: [{
						data: data.dados_projetos,
						backgroundColor: [
							"red",
							"orange",
							"yellow",
							"green",
							"blue",
						],
						label: 'Tickets'
					}],
					labels: data.projetos
				},
				options: {
					responsive: true,
					legend: {
						position: 'top',
					},
					title: {
						display: true,
						text: 'Distribuição de Tickets por Projeto'
					},
					animation: {
						animateScale: true,
						animateRotate: true
					},
					'onClick' : function (evt, item) {
	                    console.log ('legend onClick', evt);
	                    console.log('legd item', item);
	                }
				}
			};
			
				var ctx = document.getElementById('chart-area').getContext('2d');
				window.myDoughnut = new Chart(ctx, config);
				 window.myDoughnut.options.circumference= 2 * Math.PI;
				 window.myDoughnut.options.rotation= -Math.PI / 2 ;
				 window.myDoughnut.update();
				
			
			
	 });
}
function g12(){
	$.getJSON('./Dashboard_Servlet?opt=11', function(data) {
		
			var config = {
				type: 'doughnut',
				data: {
					datasets: [{
						data: data.dados_projetos,
						backgroundColor: [
							"red",
							"orange",
							"yellow",
							"green",
							"blue",
						],
						label: 'Tickets'
					}],
					labels: data.projetos
				},
				options: {
					responsive: true,
					legend: {
						position: 'top',
					},
					title: {
						display: true,
						text: 'Distribuição de Tickets por Recurso'
					},
					animation: {
						animateScale: true,
						animateRotate: true
					},
					'onClick' : function (evt, item) {
	                    console.log ('legend onClick', evt);
	                    console.log('legd item', item);
	                }
				}
			};
			
				
				var ctx2 = document.getElementById('chart-area2').getContext('2d');
				window.myDoughnut2 = new Chart(ctx2, config);
				 window.myDoughnut2.options.circumference=  Math.PI;
				 window.myDoughnut2.options.rotation= -Math.PI  ;
				 window.myDoughnut2.update();
			
			
	 });
}
function g10(){
	 $.getJSON('./Dashboard_Servlet?opt=10', function(data) {
		 
		 $("#card_po_sem_ticket").html("<h2><b>"+data.item_po_sem_ticket+"</b></h2>");
		 $("#card_ticket_abertos").html("<h2><b>"+data.item_po_ticket_new+"</b></h2>");
		 $("#card_ticket_andamento").html("<h2><b>"+data.item_po_ticket_work+"</b></h2>");
		 $("#card_ticket_finalizados").html("<h2><b>"+data.item_po_ticket_done+"</b></h2>");
	 });
}
function g1 (opcao,local) {
				
			var options = {
			        chart: {
			            renderTo: local,
			            type: 'column'
			            
			        },
			        title: {
			            text: 'Atividades Planejadas no Período',
			            style:{
			            	fontSize: '14px'
			            },
			            x: -20 //center
			        },
			        xAxis:
			        {
			        categories: [],
			        },
			        plotOptions: {
			        	column: {
			                dataLabels: {
			                    enabled: true,
			                    format: '{y}',
			                    rotation: 315,
			                    x: 4,
			                    y: -10
			                    
			                },
			                enableMouseTracking: true
			            }
			        },
			        series: [{}]
			    };
			moment().locale('pt-br');
			
			  var s,p;
			  var rolloutid;
			if (opcao==0){
			     var date = new Date();
			     var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
			     var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
			     $("#from").jqxDateTimeInput('setRange', firstDay, lastDay);
			     s=moment(firstDay).format('L');
			     p=moment(lastDay).format('L');
			  }else{
				  var selection = $("#from").jqxDateTimeInput('getRange');
				  s=moment(selection.from).format('L');
				  p=moment(selection.to).format('L');
				  
			  }
			 
			 rolloutid = $('#select_dashboard_rollout').val();
			if (rolloutid){
				rolloutid = $('#select_dashboard_rollout').val();
				$('#seleciona_rollout_modal').modal('hide');
			}else{
				rolloutid=0;
			}
			
			   //$.getJSON('http://inovareti.jelasticlw.com.br/DashTM/Dashboard_Servlet?opt=1&dtfrom='+s.value+'&dtto='+p.value, function(data) {
			  $.getJSON('./Dashboard_Servlet?opt=3&dtfrom='+s+'&dtto='+p+'&rollout='+rolloutid, function(data) {	
			    	//alert(JSON.stringify(data));
			    	options.xAxis.categories = data['categorias'];
			    	options.series = data['dados'];
			    	
			       
			        var chart = new Highcharts.Chart(options);
			        //alert("fez o charts");
			    });

			}

function g2(opcao,local) {
				//alert("entrei no g2");
			var options = {
			        chart: {
			            renderTo: local,
			            type: 'line'
			        },
			        title: {
			            text: 'Valor Recebido por periodo',
			            style:{
			            	fontSize: '10px'
			            },
			            x: -20 //center
			        },
			        xAxis:
			        {
			        categories: [],
			        labels:{
			        	rotation: 45
			        	
			        }
			        },
			        plotOptions: {
			        	line: {
			                dataLabels: {
			                    enabled: true,
			                    rotation: 315,
			                    formatter: function () {
			                    	//alert(this.y);
			                        return  accounting.formatMoney(this.y);
			                    },
			                    x: 4,
			                    y: -10
			                    
			                },
			                enableMouseTracking: true
			            }
			        },
			        series: [{}]
			    };
				//var e = document.getElementById("year");
				//var strUser = e.options[e.selectedIndex].value;
				//alert(strUser);
			    //$.getJSON('http://inovareti.jelasticlw.com.br/DashTM/Dashboard_Servlet?opt=2&ano='+strUser, function(data) {
			    $.getJSON('./Dashboard_Servlet?opt=2', function(data) {	
			    	options.xAxis.categories = data[0]['data'];
			    	
			    	for(var d=0;d<data.length-1;d++){
			    		options.series[d] = data[d+1];
			    	}
			    	var chart2 = new Highcharts.Chart(options);
			    });

			}
function g3(opcao,local) {

	$.getJSON('./Dashboard_Servlet?opt=2', function(data) {
	Highcharts.chart(local, {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: 'Status(Valor) do item por mes de Emissao da PO'
	    },
	    subtitle: {
	        text: 'Fonte: PO'
	    },
	    xAxis: {
	        categories: data.mes,
	        crosshair: true
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Valor Itens de PO'
	        }
	    },
	    tooltip: {
	        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	            '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
	        footerFormat: '</table>',
	        shared: true,
	        useHTML: true
	    },
	    plotOptions: {
	        column: {
	            pointPadding: 0.2,
	            borderWidth: 0,
	            dataLabels: {
                    enabled: true,
                    rotation: 315,
                    formatter: function () {
                    	//alert(this.y);
                        return  accounting.formatMoney(this.y);
                    },
                    x: 4,
                    y: -10
                    
                },
                enableMouseTracking: true
	        },
	        
	    },
	    series: [{
	        name: 'Recebida',
	        data: data.recebida

	    }, {
	        name: 'Validada',
	        data: data.validada

	    }, {
	        name: 'Em Execução',
	        data: data.iniciadas

	    }, {
	        name: 'Finalizado',
	        data: data.finalizadas

	    }]
	});
	 });
	
}
function g16(opcao,local){
	Highcharts.chart(local, {
	    chart: {
	        type: 'line'
	    },
	    title: {
	        text: 'Realizado vs Faturado'
	    },
	    subtitle: {
	        text: 'Fonte: PO,Rollout'
	    },
	    xAxis: {
	        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	    },
	    yAxis: {
	        title: {
	            text: 'Valor(R$)'
	        }
	    },
	    plotOptions: {
	        line: {
	            dataLabels: {
	                enabled: true
	            },
	            enableMouseTracking: false
	        }
	    },
	    series: [{
	        name: 'Realizado',
	        data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
	    }, {
	        name: 'Faturado',
	        data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
	    }]
	});
}
function g15(opcao,local){
	$.getJSON('./Dashboard_Servlet?opt=13', function(data) {
		Highcharts.chart(local, {
		    chart: {
		        type: 'column'
		    },
		    title: {
		        text: 'Previsão de Faturamento de Itens de PO'
		    },
		    subtitle: {
		        text: 'Fonte: PO'
		    },
		    xAxis: {
		        categories: data.mes,
		        crosshair: true
		    },
		    yAxis: {
		        min: 0,
		        title: {
		            text: 'Previsão de Faturamento de Itens de PO'
		        }
		    },
		    tooltip: {
		        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		            '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
		        footerFormat: '</table>',
		        shared: true,
		        useHTML: true
		    },
		    plotOptions: {
		        column: {
		            pointPadding: 0.2,
		            borderWidth: 0,
		            dataLabels: {
	                    enabled: true,
	                    rotation: 315,
	                    formatter: function () {
	                    	//alert(this.y);
	                        return  accounting.formatMoney(this.y);
	                    },
	                    x: 4,
	                    y: -10
	                    
	                },
	                enableMouseTracking: true
		        },
		        
		    },
		    series: [{
		        name: 'Faturamento Previsto',
		        data: data.faturamento

		    }]
		});
		 });
		
}			
function g4 (opcao,local) {
	$.getJSON('./Dashboard_Servlet?opt=4', function(data) {
	Highcharts.chart(local, {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: 'Status do item por mes de Emissao da PO'
	    },
	    subtitle: {
	        text: 'Fonte: PO'
	    },
	    xAxis: {
	        categories: data.mes,
	        crosshair: true
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Itens de PO'
	        }
	    },
	    tooltip: {
	        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	            '<td style="padding:0"><b>{point.y:.1f} Itens</b></td></tr>',
	        footerFormat: '</table>',
	        shared: true,
	        useHTML: true
	    },
	    plotOptions: {
	        column: {
	            pointPadding: 0.2,
	            borderWidth: 0,
	            dataLabels: {
                    enabled: true,
                    rotation: 315,
                    formatter: function () {
                    	//alert(this.y);
                        return  this.y;
                    },
                    x: 4,
                    y: -10
                    
                },
                enableMouseTracking: true
	        },
	        
	    },
	    series: [{
	        name: 'Recebida',
	        data: data.recebida

	    }, {
	        name: 'Validada',
	        data: data.validada

	    }, {
	        name: 'Em Execução',
	        data: data.iniciadas

	    }, {
	        name: 'Finalizado',
	        data: data.finalizadas

	    }]
	});
	 });
	}
function g5(opcao,local) {
	
	$.getJSON('./Dashboard_Servlet?opt=5', function(data) {
		
	Highcharts.chart(local, {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: 'MSTP Mobile - Registros de usuários na semana corrente'
	    },
	   
	    xAxis: {
	        type: opcao,
	        labels: {
	            rotation: -45,
	        }
	    },
	    yAxis: {
	        title: {
	            text: 'Quantidade de Registros'
	        }

	    },
	    legend: {
	        enabled: false
	    },
	    plotOptions: {
	        series: {
	            borderWidth: 0,
	            dataLabels: {
	                enabled: true
	                
	            },
	            cursor: 'pointer',
	            events: {
	                click: function (event) {
	                    if(opcao=='category'){
	                    	carrega_tabela_mstp_mobile(event.point.name);
	                    }else{
	                    	carrega_tabela_mstp_mobile('todos');
	                    }
	                        
	                }
	            }
	        }
	    },
	    series: data
	});
	});
}
function g6(local){
	$.ajax({
		  type: "POST",
		  data: {"opt":"6"},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./Dashboard_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccessdash6
		});
	function onSuccessdash6(data){
		$("#"+local).html(data);
	}
}
function g7(local){
	
	$.getJSON('./Dashboard_Servlet?opt=7', function(data) {
		
	Highcharts.chart(local, {
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
	        type: 'pie'
	    },
	    title: {
	        text: 'Aprovações Pendentes'
	    },
	    tooltip: {
	        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	    },
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	                enabled: false
	            },
	            showInLegend: true,
	            events: {
	                click: function (event) {
	                	menu('aprovacoes');
	                	carrega_aprovacoes_hh();
	                }
	            }
	        }
	    },
	    series: data
	});
});
}
function g8(local){
	
	$.getJSON('./Dashboard_Servlet?opt=8', function(data) {
	
	Highcharts.chart(local, {
	    chart: {
	        type: 'bar'
	    },
	    title: {
	        text: 'Horas registradas pelo MSTP Mobile'
	    },
	    
	    xAxis: {
	    	categories: data['categoria'],
	        title: {
	            text: null
	        }
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Horas registradas na semana atual'
	           
	        }
	       
	    },
	    tooltip: {
	        valueSuffix: ' Horas'
	    },
	    plotOptions: {
	        bar: {
	            dataLabels: {
	                enabled: true,
	                formatter:function(){
	                	return this.y;
	                },
	                style:{
	                	fontSize:"9px"
	                }
	            }
	        }
	    },
	    legend: {
	    	enabled: false
	    },
	    credits: {
	        enabled: false
	    },
	    series: data['serie']
	});
});
}
function g9 (opcao,local) {
	
	
	moment().locale('pt-br');
	
	  var s,p;
	  
	if (opcao==0){
	     var date = new Date();
	     var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	     var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	     $("#from2").jqxDateTimeInput('setRange', firstDay, lastDay);
	     s=moment(firstDay).format('L');
	     p=moment(lastDay).format('L');
	  }else{
		  var selection = $("#from2").jqxDateTimeInput('getRange');
		  s=moment(selection.from).format('L');
		  p=moment(selection.to).format('L');
	  }
	 
	 rolloutid = $('#select_dashboard_rollout').val();
		if (rolloutid){
			rolloutid = $('#select_dashboard_rollout').val();
			$('#seleciona_rollout_modal').modal('hide');
		}else{
			rolloutid=0;
		}
	
	   //$.getJSON('http://inovareti.jelasticlw.com.br/DashTM/Dashboard_Servlet?opt=1&dtfrom='+s.value+'&dtto='+p.value, function(data) {
	  $.getJSON('./Dashboard_Servlet?opt=9&dtfrom='+s+'&dtto='+p+'&rollout='+rolloutid, function(data) {	
	    	//alert(JSON.stringify(data));
	    	
		  Highcharts.chart(local, {
			    chart: {
			        type: 'bar'
			    },
			    title: {
			        text: 'Quantidade de Atividades do Rollout por Usuário'
			    },
			    subtitle: {
			        text: 'Fonte: Rollout'
			    },
			    xAxis: {
			        categories: data.pessoas,
			        title: {
			            text: null
			        }
			    },
			    yAxis: {
			        min: 0,
			        title: {
			            text: 'Atividades',
			            align: 'high'
			        },
			        labels: {
			            overflow: 'justify'
			        }
			    },
			    tooltip: {
			        valueSuffix: ' Atividades'
			    },
			    plotOptions: {
			        bar: {
			            dataLabels: {
			                enabled: true
			            }
			        }
			    },
			    legend: {
			        layout: 'vertical',
			        align: 'right',
			        verticalAlign: 'top',
			        x: -40,
			        y: 80,
			        borderWidth: 1,
			        backgroundColor:'#FFFFFF',
			        shadow: true
			    },
			    credits: {
			        enabled: false
			    },
			    series: data.serie
			});
			
			
	        
	    });

	}
function g14(local){
	Highcharts.chart(local, {
	    chart: {
	        type: 'line'
	    },
	    title: {
	        text: 'Planejamento (Milestone.nome) por período'
	    },
	    subtitle: {
	        text: 'Fonte: Rollout'
	    },
	    xAxis: {
	        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	    },
	    yAxis: {
	        title: {
	            text: 'Atividades'
	        }
	    },
	    plotOptions: {
	        line: {
	            dataLabels: {
	                enabled: true
	            },
	            enableMouseTracking: false
	        }
	    },
	    series: [{
	        name: 'Plano',
	        data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
	    }, {
	        name: 'Realizado',
	        data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
	    }]
	});
}
function g13(local){
	rolloutid = $('#select_dashboard_rollout').val();
	if (rolloutid){
		rolloutid = $('#select_dashboard_rollout').val();
		$('#seleciona_rollout_modal').modal('hide');
	}else{
		rolloutid=0;
	}
	 $.getJSON('./Dashboard_Servlet?opt=12&rollout='+rolloutid, function(data) {	
	Highcharts.chart(local, {
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: 0,
	        plotShadow: false
	    },
	    title: {
	        text: data.nome+'<br>'+data.porcetagem + ' Concluído',
	        align: 'center',
	        verticalAlign: 'middle',
	        y: 30
	    },
	    tooltip: {
	        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	    },
	    accessibility: {
	        point: {
	            valueSuffix: '%'
	        }
	    },
	    plotOptions: {
	        pie: {
	            dataLabels: {
	                enabled: true,
	                distance: -50,
	                style: {
	                    fontWeight: 'bold',
	                    color: 'white'
	                }
	            },
	            startAngle: 0,
	            endAngle: 360,
	            center: ['50%', '50%'],
	            size: '100%'
	        }
	    },
	    series: [{
	        type: 'pie',
	        name: 'Milestone',
	        innerSize: '80%',
	        data: data.dados
	    }]
	});

	 });
	
}