package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;



public class CamposRollout {
	private String campo_nome;
	private String campo_tipo;
	private String campo_data_tipo;
	
	public CamposRollout() {
		campo_nome="";
		campo_tipo="";
		campo_data_tipo="";
	}
	
	public String BuscaSitebyRecID(ConexaoMongo c,int recid) {
		
		Document resultado = new Document();
		Bson filtro;
		List<Bson> filtros = new ArrayList<>();
		filtro=Filters.eq("recid",recid);
		filtros.add(filtro);
		FindIterable<Document> findIterable= c.ConsultaCollectioncomFiltrosLista("rollout", filtros);
		MongoCursor<Document> busca= findIterable.iterator();
		if(busca.hasNext()) {
			resultado=busca.next();
			return resultado.getString("Site ID");
		}else {
			return "Site Nao encontrado";
		}
		
	}
	
	public String getCampo_nome() {
		return campo_nome;
	}
	public void getCampo_nome (String campo_nome) {
		this.campo_nome = campo_nome;
	}
	public JSONObject getCampos_tipo(Conexao c, Pessoa p,String rolloutid) {
		ResultSet rs;
		JSONArray dados= new JSONArray() ;
		JSONObject jsonObject = new JSONObject(); 
		try {
		String query="select field_name,field_type,tipo,ordenacao from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id() + " and rollout_nome='"+rolloutid+"' order by ordenacao asc";
		rs=c.Consulta(query);
		
			if(rs.next()) {
				//campo_tipo=rs.getString(1);
				rs.beforeFirst();
				while(rs.next()) {
					
					dados= new JSONArray() ;
					dados.put(rs.getString(2));
					dados.put(rs.getString(3));
					dados.put(rs.getInt(4));
					dados.put(rs.getString(1));
					jsonObject.put(rs.getString(1),dados);
					
				}
			}else {
				campo_tipo="Campo nao encontrado";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("erro com a pessoa: "+p.get_PessoaName());
			e.printStackTrace();
		}
		return jsonObject;
	}
	public String[] getCamposOrdenados(Conexao c, Pessoa p) {
		ResultSet rs;
		String[] dados=null;
		int i=0;
		
		String query="select field_name,field_type,tipo,ordencao from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id() + " order by ordenacao asc";
		rs=c.Consulta(query);
		try {
			if(rs.next()) {
				//campo_tipo=rs.getString(1);
				rs.beforeFirst();
				
				while(rs.next()) {
					dados[i]=rs.getString(1);
					i=i+1;
				}
			}else {
				campo_tipo="Campo nao encontrado";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dados;
	}
	public void setCampo_tipo(String campo_tipo) {
		this.campo_tipo = campo_tipo;
	}
	public String getCampo_data_tipo() {
		return campo_data_tipo;
	}
	public String getCampo_data_tipo(Conexao c,String nome_campo,Pessoa p) {
		ResultSet rs;
		String query="select field_name,field_type,tipo from rollout_campos where field_name='"+nome_campo+"' and empresa="+p.getEmpresa().getEmpresa_id();
		rs=c.Consulta(query);
		try {
		if(rs.next()) {
			return rs.getString("tipo");
		}else {
			return "";
		}
		}catch(SQLException e) {
			return "";
		}
	}
	public void setCampo_data_tipo(String campo_data_tipo) {
		this.campo_data_tipo = campo_data_tipo;
	}
	public int get_campos_quantidade(Conexao c, Pessoa p) {
		
		ResultSet rs;
		rs=c.Consulta("select count(field_name) from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and field_status='ATIVO'");
		try {
			if(rs.next()) {
				return rs.getInt(1);
			}else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	
	}
	
	
}
