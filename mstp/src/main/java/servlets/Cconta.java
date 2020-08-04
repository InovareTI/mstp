package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.bson.Document;

import classes.Conexao;
import classes.ConexaoMongo;
import classes.Semail;

/**
 * Servlet implementation class Cconta
 */
@WebServlet("/Cconta")
public class Cconta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cconta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		criaconta(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		criaconta(request, response);
	}
	
	public void criaconta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String param1;
		String param2;
		String param3;
		String param4;
		String param5;
		String param6;
		String query;
		String link="";
		Integer empresa=0;
		
		MessageDigest md;
		ResultSet rs ;
		Conexao conn = new Conexao();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		DateFormat f3 = DateFormat.getDateTimeInstance();
		System.out.println("Chegou no servlet de criação de contas do MSTP WEB - "+f3.format(time));
		param1=req.getParameter("signup_username");
		param2=req.getParameter("signup_email");
		param3=req.getParameter("signup_password");
		param4=req.getParameter("signup_empresa");
		param5=req.getParameter("signup_nome");
		param6=req.getParameter("MSTP_FREE");
		query="";
		try {
			/*System.out.println(param1);
			System.out.println(param2);
			System.out.println(param3);
			System.out.println(param4);
			System.out.println(param5);
			System.out.println(param6);*/
			rs=conn.ConsultaLogin("select id_usuario from usuarios where email=?",param2);
			if(rs.next()) {
				System.out.println("Email já cadastrado");
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Email já cadastrado!");
			}else {
				rs=conn.ConsultaLogin("select id_usuario from usuarios where id_usuario=?",param1);
				if(rs.next()) {
					System.out.println("Usuário indisponivel.Escolha outro usuário!");
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Usuário indisponivel.  Escolha outro usuário!");
				}else {
					List<String> parametros = new ArrayList<>();
					if(param6.equals("true")) {
						query="insert into empresas(nome,nome_fantasia,cnpj,MSTP_FREE,dt_created) values(?,?,?,'Y','"+time+"')";
						parametros.add(param5);
						parametros.add(param5);
						parametros.add("0");
					
					if(conn.InsereUsuario(query,parametros)) {
						rs = conn.Consulta("select id_empresa from empresas order by id_empresa desc limit 1");
						if(rs.next()) {
							empresa=rs.getInt(1);
						}
					}
					}else {
						query="insert into empresas(nome,nome_fantasia,cnpj,MSTP_FREE,dt_created) values(?,?,?,'N','"+time+"')";
						parametros.add(param4);
						parametros.add(param4);
						parametros.add("0");
					
					if(conn.InsereUsuario(query,parametros)) {
						rs = conn.Consulta("select id_empresa from empresas order by id_empresa desc limit 1");
						if(rs.next()) {
							empresa=rs.getInt(1);
						}
					}
					}
					parametros = new ArrayList<>();
					md = MessageDigest.getInstance( "SHA-256" );
					md.update( param3.getBytes());     
					BigInteger hash = new BigInteger(1,md.digest() );     
					String SenhaHash = hash.toString(16);
					md = MessageDigest.getInstance( "SHA-256" );
					md.update( param1.getBytes());     
					hash = new BigInteger(1,md.digest() );     
					String codigoValida = hash.toString(16);
					query="insert into usuarios ("
							+ "id_usuario,"
							+ "nome,"
							+ "HASH,"
							+ "email,"
							+ "perfil,"
							+ "empresa,"
							+ "VALIDADO,ATIVO,CODIGO_VALIDACAO)"
							+ " values("
							+ "?,"
							+ "?,"
							+ "?,"
							+ "?,"
							+ "?,"
							+ "?,"
							+ "?,"
							+ "?,?) ";
					parametros.add(param1);
					parametros.add(param5);
					parametros.add(SenhaHash);
					parametros.add(param2);
					parametros.add("tecnico");
					parametros.add(empresa.toString());
					parametros.add("N");
					parametros.add("Y");
					parametros.add(codigoValida);
					if(conn.InsereUsuario(query,parametros)) {
						Semail email= new Semail();
						link="https://www.mstp.com.br/Reg_Servlet?validaCode="+codigoValida;
						email.enviaEmailSimples(param2, "MSTP - Link de Ativação","Prezado, \n \n Abaixo segue o link de autorização para a conta .\n \n"+param5+" - "+param1+" \n \n \n "+link);
						if(param6.equals("true")) {
							query="insert into perfil_funcoes(perfil_nome,funcao_nome,perfil_id,função_id,dt_add,ativo,empresa_id,usuario_id) values('RolloutManager','RolloutManager',1,1,'"+time+"','Y',"+empresa+",'"+param1+"')";
							conn.Inserir_simples(query);
						}else {
							query="insert into perfil_funcoes(perfil_nome,funcao_nome,perfil_id,função_id,dt_add,ativo,empresa_id,usuario_id) values('RolloutManager','RolloutManager',1,1,'"+time+"','Y',"+empresa+",'"+param1+"')";
							conn.Inserir_simples(query);
							query="insert into perfil_funcoes(perfil_nome,funcao_nome,perfil_id,função_id,dt_add,ativo,empresa_id,usuario_id) values('UsuariosManager','Usuarios Manager',1,1,'"+time+"','Y',"+empresa+",'"+param1+"')";
							conn.Inserir_simples(query);
							query="insert into perfil_funcoes(perfil_nome,funcao_nome,perfil_id,função_id,dt_add,ativo,empresa_id,usuario_id) values('SiteManager','Site Manager',1,1,'"+time+"','Y',"+empresa+",'"+param1+"')";
							conn.Inserir_simples(query);
							query="insert into perfil_funcoes(perfil_nome,funcao_nome,perfil_id,função_id,dt_add,ativo,empresa_id,usuario_id) values('MapManager','MapViewer',1,1,'"+time+"','Y',"+empresa+",'"+param1+"')";
							conn.Inserir_simples(query);
							query="insert into perfil_funcoes(perfil_nome,funcao_nome,perfil_id,função_id,dt_add,ativo,empresa_id,usuario_id) values('VistoriaManager','VistoriaManager',1,1,'"+time+"','Y',"+empresa+",'"+param1+"')";
							conn.Inserir_simples(query);
							query="insert into perfil_funcoes(perfil_nome,funcao_nome,perfil_id,função_id,dt_add,ativo,empresa_id,usuario_id) values('POManager','POManager',1,1,'"+time+"','Y',"+empresa+",'"+param1+"')";
							conn.Inserir_simples(query);
							query="insert into perfil_funcoes(perfil_nome,funcao_nome,perfil_id,função_id,dt_add,ativo,empresa_id,usuario_id) values('LPUManager','LPUManager',1,1,'"+time+"','Y',"+empresa+",'"+param1+"')";
							conn.Inserir_simples(query);
							query="insert into perfil_funcoes(perfil_nome,funcao_nome,perfil_id,função_id,dt_add,ativo,empresa_id,usuario_id) values('OrcamentoManager','OrcamentoManager',1,1,'"+time+"','Y',"+empresa+",'"+param1+"')";
							conn.Inserir_simples(query);
							query="insert into perfil_funcoes(perfil_nome,funcao_nome,perfil_id,função_id,dt_add,ativo,empresa_id,usuario_id) values('ClienteManager','ClienteManager',1,1,'"+time+"','Y',"+empresa+",'"+param1+"')";
							conn.Inserir_simples(query);
							query="insert into perfil_funcoes(perfil_nome,funcao_nome,perfil_id,função_id,dt_add,ativo,empresa_id,usuario_id) values('ProjectManager','ProjectManager',1,1,'"+time+"','Y',"+empresa+",'"+param1+"')";
							conn.Inserir_simples(query);
						}
						ConexaoMongo mongo = new ConexaoMongo();
						Document acesso =new Document();
						Document modulo = new Document();
						acesso.append("Empresa", empresa);
						acesso.append("Usuario",param1);
						List<Document> modulos = new ArrayList<>();
						modulo.append("nome", "Sites");
						modulos.add(modulo);
						modulo = new Document();
						modulo.append("nome", "Atividades");
						modulos.add(modulo);
						acesso.append("Modulos",modulos);
						mongo.InserirSimples("UsuarioModulo", acesso);
						Document rollout = new Document();
						rollout.append("Empresa", empresa);
						rollout.append("rollout_id", 0);
						rollout.append("rollout_nome", "Rollout");
						rollout.append("id", "Rollout");
						rollout.append("parentid", -1);
						if(param6.equals("true")) {
							rollout.append("text", "MEUS PROJETOS");
						}else {
							rollout.append("text", param4+" PROJETOS");
						}
						rollout.append("value", "Rollout");
						mongo.InserirSimples("rollouts_ids", rollout);
						rollout = new Document();
						rollout.append("Empresa", empresa);
						rollout.append("rollout_id", 1);
						rollout.append("rollout_nome", "Rollout1");
						rollout.append("id", "Rollout1");
						rollout.append("parentid", "Rollout");
						rollout.append("text", "Projeto1");
						rollout.append("value", "Rollout1");
						mongo.InserirSimples("rollouts_ids", rollout);
						
						query="insert into rollout_campos(field_name,field_type,field_owner,field_creation_date,fiel_creation_owner,field_status,ordenacao,tipo,empresa,rollout_id,rollout_nome)"
								+ " values('Site ID','Atributo','System','"+time+"','System','ATIVO',1,'Texto',"+empresa+",1,'Rollout1')";
						conn.Inserir_simples(query);
						query="insert into rollout_campos(field_name,field_type,field_owner,field_creation_date,fiel_creation_owner,field_status,ordenacao,tipo,empresa,rollout_id,rollout_nome)"
								+ " values('Cliente','Atributo','System','"+time+"','System','ATIVO',2,'Texto',"+empresa+",1,'Rollout1')";
						conn.Inserir_simples(query);
						query="insert into rollout_campos(field_name,field_type,field_owner,field_creation_date,fiel_creation_owner,field_status,ordenacao,tipo,empresa,rollout_id,rollout_nome)"
								+ " values('Projeto','Atributo','System','"+time+"','System','ATIVO',3,'Texto',"+empresa+",1,'Rollout1')";
						conn.Inserir_simples(query);
						query="insert into rollout_campos(field_name,field_type,field_owner,field_creation_date,fiel_creation_owner,field_status,ordenacao,tipo,empresa,rollout_id,rollout_nome)"
								+ " values('Atividade','Atributo','System','"+time+"','System','ATIVO',4,'Texto',"+empresa+",1,'Rollout1')";
						conn.Inserir_simples(query);
						query="insert into rollout_campos(field_name,field_type,field_owner,field_creation_date,fiel_creation_owner,field_status,ordenacao,tipo,empresa,rollout_id,rollout_nome)"
								+ " values('Comentario','Atributo','System','"+time+"','System','ATIVO',5,'Texto',"+empresa+",1,'Rollout1')";
						conn.Inserir_simples(query);
						query="insert into rollout_campos(field_name,field_type,field_owner,field_creation_date,fiel_creation_owner,field_status,ordenacao,tipo,empresa,rollout_id,rollout_nome)"
								+ " values('AtividadePlano','Milestone','System','"+time+"','System','ATIVO',6,'Milestone',"+empresa+",1,'Rollout1')";
						conn.Inserir_simples(query);
						query="INSERT INTO vistoria_report (relatorio_nome, descricao, tipo_relatorio,empresa,status_ativo) "
								+ "VALUES ( 'Relatório de Atividade', 'Relatório de Atividade', 'Instalação',"+empresa+",'ATIVO')";
						conn.Inserir_simples(query);
						rs=conn.Consulta("select id from vistoria_report where empresa="+empresa+" order by id desc limit 1 ");
						
						if(rs.next()) {
							query="INSERT INTO vistoria_campos (field_name, field_type, tipo,empresa,parent_id,tree_id,relatorio_id,field_desc) "
									+ "VALUES ( 'Atividade', 'Grupo','Texto',"+empresa+",0,1,"+rs.getInt(1)+",'ATIVIDADE')";
							conn.Inserir_simples(query);
							query="INSERT INTO vistoria_campos (field_name, field_type, tipo,empresa,parent_id,tree_id,relatorio_id,field_desc) "
									+ "VALUES ( 'FOTO 1', 'Item','Foto',"+empresa+",1,2,"+rs.getInt(1)+",'FOTO 1')";
							conn.Inserir_simples(query);
							query="INSERT INTO vistoria_campos (field_name, field_type, tipo,empresa,parent_id,tree_id,relatorio_id,field_desc) "
									+ "VALUES ( 'FOTO 2', 'Item','Foto',"+empresa+",1,3,"+rs.getInt(1)+",'FOTO 2')";
							conn.Inserir_simples(query);
							query="INSERT INTO vistoria_campos (field_name, field_type, tipo,empresa,parent_id,tree_id,relatorio_id,field_desc) "
									+ "VALUES ( 'FOTO 3', 'Item','Foto',"+empresa+",1,4,"+rs.getInt(1)+",'FOTO 3')";
							conn.Inserir_simples(query);
							query="INSERT INTO vistoria_campos (field_name, field_type, tipo,empresa,parent_id,tree_id,relatorio_id,field_desc) "
									+ "VALUES ( 'FOTO 4', 'Item','Foto',"+empresa+",1,5,"+rs.getInt(1)+",'FOTO 4')";
							conn.Inserir_simples(query);
						}
						
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("ContaCriada");
						conn.getConnection().commit();
						rs.close();
						conn.fecharConexao();
						mongo.fecharConexao();
					}else {
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Erro durante procedimento  de criação");
						rs.close();
						conn.fecharConexao();
					}
					
				}
		}
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.fecharConexao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.fecharConexao();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.fecharConexao();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.fecharConexao();
		}
	}

}
