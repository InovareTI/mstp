package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.Conexao;
import classes.Pessoa;

/**
 * Servlet implementation class RelatoriosServlet
 */
@WebServlet("/RelatoriosServlet")
public class RelatoriosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelatoriosServlet() {
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
		mgmt_relatorios(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		mgmt_relatorios(request, response);
	}
	public void mgmt_relatorios(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ResultSet rs ;
		ResultSet rs2 ;
		String dados_tabela;
		String opt;
		String insere;
		String param1;
		String param2;
		String param3;
		String param4;
		String param5;
		String param6;
		String query;
		Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		Locale.setDefault(locale_ptBR);
		HttpSession session = req.getSession(true);
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		Conexao conn = (Conexao) session.getAttribute("conexao");
		double money; 
		NumberFormat number_formatter = NumberFormat.getCurrencyInstance();
		String moneyString;
		Timestamp time = new Timestamp(System.currentTimeMillis());
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
		DateFormat f3 = DateFormat.getDateTimeInstance();
		opt=req.getParameter("opt");
		System.out.println(p.get_PessoaUsuario()+" Chegou no servlet de Relatório do MSTP Web - "+f3.format(time));
		try {
		if(opt.equals("1")){
			param1=req.getParameter("nome");
			param2=req.getParameter("desc");
			param3=req.getParameter("tipo");
			query="";
			query="insert into vistoria_report (relatorio_nome,descricao,tipo_relatorio,dt_generated,generated_by,empresa,obs) values ('"+param1+"','"+param2+"','"+param3+"','"+time+"','"+p.get_PessoaUsuario()+"',"+p.getEmpresa().getEmpresa_id()+",'')";
			if(conn.Inserir_simples(query)) {
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Relatório criado com sucesso!");
			}else {
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Falha na Criação do Relatório!");
			}
		}else if(opt.equals("2")){
			query="";
			query="select * from vistoria_report where empresa="+p.getEmpresa().getEmpresa_id();
			rs=conn.Consulta(query);
			int contador=0;
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela="{\"draw\": 1,\n";
					dados_tabela=dados_tabela+"\"recordsTotal\": replace1 ,\n";
					dados_tabela=dados_tabela+"\"recordsFiltered\": 15 ,\n";
					dados_tabela=dados_tabela+"\"data\":[";
					while(rs.next()) {
						contador=contador+1;
						dados_tabela=dados_tabela+"[\"<a href='#' onclick='edita_relatorio_info()'><i class='far fa-edit fa-lg' style='color:gray;padding:3px;'></i></a><a href='#' data-toggle='modal' data-target='#modal_campos_vistoria2' onclick='carrega_campos_relatorio("+rs.getInt("id")+")'><i class='fas fa-cogs fa-lg' style='color:gray;padding:3px;'></i></a><a href='#' onclick='remover_relatorio("+rs.getInt("id")+")'><i class='far fa-trash-alt fa-lg' style='color:red;padding:3px;'></i></a>\",\""+rs.getString("relatorio_nome")+"\",\""+rs.getString("descricao")+"\",\""+rs.getString("tipo_relatorio")+"\",\""+rs.getString("generated_by")+"\",\""+rs.getString("dt_generated")+"\"],\n";
					}
					dados_tabela=dados_tabela.replace("replace1", Integer.toString(contador));
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela+"]}";
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
			} else if(opt.equals("3")){
				param1=req.getParameter("nome_item");
				param2=req.getParameter("desc_item");
				param3=req.getParameter("id_item");
				param4=req.getParameter("parent_id_item");
				param5=req.getParameter("relatorio_id_item");
				query="";
				if(param4.equals("")) {
					param4="0";
				}
				query="insert into vistoria_campos (field_name,tree_id,parent_id,empresa,relatorio_id) values ('"+param1+"',"+param3+","+param4+","+p.getEmpresa().getEmpresa_id()+","+param5+")";
				if(conn.Inserir_simples(query)) {
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Item Salvo com Sucesso!");
				}
			}else if(opt.equals("4")){
				query="";
				param1=req.getParameter("rel_id");
				query="select * from vistoria_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param1;
				rs=conn.Consulta(query);
				if(rs.next()) {
					
					rs.beforeFirst();
					dados_tabela="[";
					while(rs.next()) {
						dados_tabela=dados_tabela+"{\"id\":\""+rs.getInt("tree_id")+"\",\"parentid\":\""+rs.getInt("parent_id")+"\",\"text\":\""+rs.getString("field_name")+"\"},";
			                
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
					dados_tabela=dados_tabela+"]";
					System.out.println(dados_tabela);
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}else {
					dados_tabela="[{\"id\":\"1\",\"parentid\":\"0\",\"text\":\"Item\"}]";
					
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
						
			}else if(opt.equals("5")){
				query="";
				param1=req.getParameter("rel_id");
				query="select * from vistoria_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param1;
				rs=conn.Consulta(query);
				if(rs.next()) {
					resp.setContentType("application/Text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Remoção nao permitida, pois existem campos ativos");
				}else {
					query="delete from vistoria_campos where relatorio_id="+param1;
					conn.Excluir(query);
					query="delete from vistoria_report where id="+param1;
					conn.Excluir(query);
					resp.setContentType("application/Text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Relatório e Campos removidos com sucesso. Isso não afeta documentos já gerados com esse template");
				}
			}else if(opt.equals("6")){
				query="";
				param1=req.getParameter("relatorio_id_item");
				param2=req.getParameter("id_item");
				param3=req.getParameter("parent_id_item");
				query="select * from vistoria_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param1+" and tree_id="+param2+" and parent_id="+param3;
				rs=conn.Consulta(query);
				if(rs.next()) {
					query=" select * from vistoria_dados where empresa="+p.getEmpresa().getEmpresa_id()+" and campo_id="+param2+" and parent_id="+param3+" and relatorio_id="+param1+" and relatorio_gerado='Y'";
					rs2=conn.Consulta(query);
					if(rs2.next()) {
						resp.setContentType("application/Text");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Remoção de Campo nao permita, pois existem vistoria em andamento sem documento gerado");
					}else {
						query="delete from vistoria_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param1+" and tree_id="+param2+" and parent_id="+param3;
						conn.Excluir(query);
						resp.setContentType("application/Text");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Campo Removido com sucesso");
					}
				}
			}
		}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
