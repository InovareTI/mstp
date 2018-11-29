<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);
%>

<!DOCTYPE html>
<html>
    <head>
    	<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>M S T P - Managed Services Telecom Platform</title>
		<meta name="description" content="MSTP - Managed Services Telecom Platform" />
		<meta name="keywords" content="MSTP INOVARE, INOVARE TI, Servico, telecom" />
		<meta name="Inovare TI" content="MSTP - Managed Services Telecom Platform" />
		<script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <script>
    function insere_mensagem(){
    	$.ajax({
  		  type: "POST",
  		  data: {"opt":"34",
  			  "titulo":document.getElementById("titulo_message").value,"mensagem":document.getElementById("message").value},		  
  		  //url: "http://localhost:8080/DashTM/D_Servlet",	  
  		  url: "./POControl_Servlet",
  		  cache: false,
  		  dataType: "text",
  		  success: onSuccess34
  		});
    	function onSuccess34(data){
    		alert(data);
    	}
    }
    </script>
    </head>
    <body>
        <h1>Insere mensagem para usuÃ¡rios</h1>
        <div>
            <table>
                <tr><td>Titulo:</td><td><input type="text" id="titulo_message"></td></tr>
                <tr><td>Mensagem:</td><td><textarea name="message" id="message" cols="40" rows="5"></textarea></td></tr>
                <tr><td><button id="btn_insere_mensagem" onclick="insere_mensagem()">Inserir</button></td></tr>
            
            </table>
        
        </div>
    </body>
</html>