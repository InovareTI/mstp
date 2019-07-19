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
			 
			     
			
			   //$.getJSON('http://inovareti.jelasticlw.com.br/DashTM/Dashboard_Servlet?opt=1&dtfrom='+s.value+'&dtto='+p.value, function(data) {
			  $.getJSON('./Dashboard_Servlet?opt=3&dtfrom='+s+'&dtto='+p, function(data) {	
			    	//alert(JSON.stringify(data));
			    	options.xAxis.categories = data[0]['data'];
			    	options.series[0] = data[1];
			    	options.series[1] = data[2];
			    	options.series[2] = data[3];
			    	options.series[3] = data[4];
			    	
			       
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
			 	
			var options = {
			        chart: {
			            renderTo: local,
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false

			        },
			        title: {
			            text: 'Empresas emissoras',
			            style:{
			            	fontSize: '14px'
			            },
			            x: -20 //center
			        },
			        tooltip: {
			            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: true,
			                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
			                    
			                },
		                    showInLegend: true
			            }
			        },
			        series: [{
			        	type: 'pie',
						name:'Empresa',		
			            data:[]
			        	}]
			    };

			        //$.getJSON('http://inovareti.jelasticlw.com.br/DashTM/Dashboard_Servlet?opt=3', function(data) {
			    	$.getJSON('./Dashboard_Servlet?opt=1', function(data) {
			    	 //alert(data);
			    		options.series[0].data = data;
			    	 var chart3 = new Highcharts.Chart(options);
			    	 
			    });

			}
			
function g4 (opcao,local) {
	//alert("tentando carregar o g4");
	 $.getJSON('./Dashboard_Servlet?opt=4', function(data) {	
	    	//alert("carregadndo grfico");
	    	$('#'+local).highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: 'Volume Financeiro por Status de Faturamento',
	                	style:{
			            	fontSize: '10px'
			            }
	            },
	            subtitle: {
	                text: 'Clque nas barras ver mais detalhes'
	            },
	            xAxis: {
	                type: 'category'
	            },
	            yAxis: {
	                title: {
	                    text: 'Volume Financeiro'
	                }
	                
	            },
	            legend: {	
	                enabled: false
	            },
	            plotOptions: {
	            	column: {
	                    borderWidth: 0,
	                    dataLabels: {
	                    	enabled: true,
	                    	formatter: function () {
		                    	//alert(this.y);
		                        return  accounting.formatMoney(this.y);
		                    }
	                    }
	                }
	            },

	            tooltip: {
	                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b><br/>'
	            },

	            series: data[0]['data'],
	            drilldown: {
	                series: data[1]['data']
	            }
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
	
	var options = {
	        chart: {
	            renderTo: local,
	            type: 'column'
	            
	        },
	        title: {
	            text: 'Quantidade de Sites por dias trabalhados no período selecionado',
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
	                    rotation: -90,
	                    color: '#FFFFFF',
	                    align: 'right',
	                    y: 10,
	                    style: {
	                        fontSize: '13px',
	                        fontFamily: 'Verdana, sans-serif'
	                    }
	                    
	                },
	                enableMouseTracking: true
	            }
	        },
	        series: [{}]
	    };
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
	 
	     
	
	   //$.getJSON('http://inovareti.jelasticlw.com.br/DashTM/Dashboard_Servlet?opt=1&dtfrom='+s.value+'&dtto='+p.value, function(data) {
	  $.getJSON('./Dashboard_Servlet?opt=9&dtfrom='+s+'&dtto='+p, function(data) {	
	    	//alert(JSON.stringify(data));
	    	
	    	options.series=data;
	    	var chart = new Highcharts.Chart(options);
	        
	    });

	}