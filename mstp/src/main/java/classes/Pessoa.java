package classes;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.Document;

public class Pessoa {

	private String name;
	private String usuario;
	private int idade;
	private perfil perfil;
	private String doc_cpf;
	private String doc_rg;
	private String doc_creia;
	private String perfil_campos;
	private String ultimo_login;
	private String email;
	private String ctps;
	private String cargo;
	private String admissao;
	private String pis;
	private String ponto_registro;
	private String meu_ponto_registro;
	private String perfil_funcoes;
	private Empresa empresa;
	private String matricula;
	public Pessoa(){
		perfil=new perfil();
		empresa=new Empresa();
		name="";
		usuario="";
		doc_cpf="";
		doc_rg="";
		doc_creia="";
		perfil_campos="";
		ultimo_login="";
		perfil_funcoes="";
		matricula="";
	}
	
	public String getEstadoUsuario(String usuarioPesquisado,Conexao c) {
		String query;
		ResultSet rs;
		query="select estado from usuarios where id_usuario='"+usuarioPesquisado+"' and ativo='Y' and empresa='"+getEmpresa().getEmpresa_id()+"'";
		rs=c.Consulta(query);
		try {
			if(rs.next()) {
				//System.out.println(rs.getString("estado"));
				return rs.getString("estado");
			}else {
				return "";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public String getPonto_registro(Conexao c,String Usuario) {
		String query;
		ResultSet rs;
		query="select * from usuario_ponto where id_usuario='"+Usuario+"'";
		rs=c.Consulta(query);
		try {
			if(rs.next()) {
				query="select * from pontos where id_ponto="+rs.getString("id_ponto");
				rs=c.Consulta(query);
				if(rs.next()) {
					ponto_registro=rs.getString("nome_ponto");
				}else {
					ponto_registro="Ponto não encontrado";
				}
			}else {
				ponto_registro="Usuário sem local de ponto";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ponto_registro;
	}

	public void setPonto_registro(String ponto_registro) {
		this.ponto_registro = ponto_registro;
	}

	public String getMeu_ponto_registro() {
		return meu_ponto_registro;
	}

	public void setMeu_ponto_registro(String meu_ponto_registro) {
		this.meu_ponto_registro = meu_ponto_registro;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCtps() {
		return ctps;
	}

	public void setCtps(String ctps) {
		this.ctps = ctps;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getAdmissao() {
		return admissao;
	}

	public void setAdmissao(String admissao) {
		this.admissao = admissao;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPerfil_funcoes() {
		return perfil_funcoes;
	}

	public void setPerfil_funcoes(Conexao conn) {
		
		String query="";
		ResultSet rs ;
		query="select * from perfil_funcoes where usuario_id='"+this.usuario+"' and ativo='Y'";
		rs=conn.Consulta(query);
		try {
			if(rs.next()) {
				rs.beforeFirst();
				while(rs.next()) {
					this.perfil_funcoes = this.perfil_funcoes +rs.getString("funcao_nome")+";";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String get_ProjetoNome(Conexao c,String id_projeto){
		String nome_projeto;
		ResultSet rs;
		rs=c.Consulta("select project_name from project_table where project_id="+Integer.parseInt(id_projeto));
		try {
			if(rs.next()){
				nome_projeto=rs.getString(1);
			}else{
				nome_projeto="Projeto Não encontrado";
			}
			return nome_projeto;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR";
		}
		
	}
	public void set_PessoaUsuario(String u){
		usuario=u;
	}
	public String get_PessoaUsuario(){
		return usuario;
	}
	public void set_PessoaName(String n){
		name=n;
	}
	public void set_PessoaUltimoLogin(String ul){
		ultimo_login=ul;
	}
	public void set_PessoaPerfil_nome(String p){
		
		perfil.set_Perfil_nome(p);
	}
	public String get_PessoaPerfil_nome(){
		
		return perfil.get_perfil();
	}
	public String[] buscarPessoa(Conexao c, String u, Integer e) {
		String[] dados= new String[3];
		ResultSet rs;
		rs=c.Consulta("select * from usuarios where id_usuario='"+u+"' and empresa='"+e+"' limit 1");
		try {
			if(rs.next()) {
				dados[0]=rs.getString("nome");
				dados[1]=rs.getString("id_usuario");
				dados[2]=rs.getString("lider_usuario");
			}else {
				dados[0]="Usuário não encontrado";
				dados[1]=u;
				dados[2]="-";
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		return dados;
		
	}
	public String get_PessoaName(){
		return name;
	}
	public String get_PessoaPerfil_campos(){
		return perfil_campos;
	}
	public void set_PessoaPerfil_campos( Conexao c,String tabela){
		
		perfil_campos="";
		perfil_campos= perfil.get_campos_perfil(c, tabela);
	
	}
	public String get_PessoaUltimo_login(){
		return ultimo_login;
	}
	public void set_PessoaIdade(int i){
		idade=i;
	}
	public Document getPessoaInfo(String param1, Conexao conn) {
		String query;
	ResultSet rs;
	Document info  = new Document();
	query="select * from usuarios where id_usuario='"+param1+"' and ativo='Y' and validado='Y' and empresa='"+getEmpresa().getEmpresa_id()+"'";
	rs=conn.Consulta(query);
	try {
		if(rs.next()) {
			
			info.append("usuario", rs.getString("id_usuario"));
			info.append("nome", rs.getString("nome"));
			info.append("cargo", rs.getString("cargo"));
			info.append("ctps", rs.getString("ctps"));
			info.append("admissao", rs.getString("admissao"));
			info.append("matricula", rs.getString("matricula"));
			info.append("cpf", rs.getString("cpf"));
			info.append("pis", rs.getString("pis"));
			
			
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return info;
}
	public int get_PessoaIdade(){
		return idade;
	}
	public void set_PessoaDocCpf(String c){
		doc_cpf=c;
	}
	public String get_PessoaDocCpf(){
		return doc_cpf;
	}
	public void set_PessoaDocRG(String r){
		doc_rg=r;
	}
	public String get_PessoaDocRG(){
		return doc_rg;
	}public void set_PessoaDocCreia(String cr){
		doc_creia=cr;
	}
	public String get_PessoaDocCreia(){
		return doc_creia;
	}
	public void set_Pessoa(Pessoa p){
		this.set_PessoaName(p.get_PessoaName());
		this.set_PessoaIdade(p.get_PessoaIdade());
		this.set_PessoaDocCpf(p.get_PessoaDocCpf());
		this.set_PessoaDocRG(p.get_PessoaDocRG());
		
	}
	public Pessoa get_Pessoa(){
		return this;
	}
	
}
