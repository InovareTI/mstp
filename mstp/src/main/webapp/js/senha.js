function inicializa_controle_senha(){

$("#nova_senha").jqxPasswordInput({placeHolder: "Nova Senha:", showStrength: true, showStrengthPosition: "top",
		                passwordStrength: function (password, characters, defaultStrength) {
		                    var length = password.length;
		                    var letters = characters.letters;
		                    var numbers = characters.numbers;
		                    var specialKeys = characters.specialKeys;
		                    var strengthCoefficient = letters + numbers + 2 * specialKeys + letters * numbers * specialKeys;
		                    var strengthValue;
		                    if (length < 4) {
		                        strengthValue = "Muito Curta";
		                    } else if (strengthCoefficient < 10) {
		                        strengthValue = "Fraca";
		                    } else if (strengthCoefficient < 20) {
		                        strengthValue = "Justa";
		                    } else if (strengthCoefficient < 30) {
		                        strengthValue = "Boa";
		                    } else {
		                        strengthValue = "Forte";
		                    };
		                    return strengthValue;
		                }
		            });
					$("#senha_atual").jqxPasswordInput({placeHolder: "Senha Atual:"});
					$("#confirma_senha").jqxPasswordInput({placeHolder: "Confirmar Senha:"});
                    
}
function trocadeSenha(){
	 var atual = $("#senha_atual").jqxPasswordInput('val');
     var nova = $("#nova_senha").jqxPasswordInput('val');
     var confirma = $("#confirma_senha").jqxPasswordInput('val');
    
     if(atual==""){
    	 alert("Senha Atual é Obrigatório!");
    	 return;
     }else if(nova!=confirma){
    	 alert("Confirmação da Senha não confere com nova Senha!");
    	 return;
     }
     
     $.ajax({
		  type: "POST",
		  data: {"opt":"20",
			"atual":atual,  
			"nova":nova, 
			"confirma":confirma
			},		  
		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
		  url: "./POControl_Servlet",
		  cache: false,
		  dataType: "text",
		  success: onSuccess20
		});
	
	function onSuccess20(data)
	{
		
		alert(data);
		$("#senha_atual").jqxPasswordInput('val','');
		$("#nova_senha").jqxPasswordInput('val','');
		$("#confirma_senha").jqxPasswordInput('val','');
	}
     
 }