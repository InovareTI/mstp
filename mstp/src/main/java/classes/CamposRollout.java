package classes;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class CamposRollout {
	private String campo_nome;
	private String campo_tipo;
	private String campo_data_tipo;
	
	public CamposRollout() {
		campo_nome="";
		campo_tipo="";
		campo_data_tipo="";
	}
	
	
	
	public String getCampo_nome() {
		return campo_nome;
	}
	public void getCampo_nome (String campo_nome) {
		this.campo_nome = campo_nome;
	}
	public JSONObject getCampos_tipo(Conexao c, Pessoa p) {
		ResultSet rs;
		JSONArray dados= new JSONArray() ;
		JSONObject jsonObject = new JSONObject(); 
		String query="select field_name,field_type,tipo from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id();
		rs=c.Consulta(query);
		try {
			if(rs.next()) {
				//campo_tipo=rs.getString(1);
				rs.beforeFirst();
				while(rs.next()) {
					dados= new JSONArray() ;
					dados.put(rs.getString(2));
					dados.put(rs.getString(3));
					jsonObject.put(rs.getString(1),dados);
					
				}
			}else {
				campo_tipo="Campo nao encontrado";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	public void setCampo_tipo(String campo_tipo) {
		this.campo_tipo = campo_tipo;
	}
	public String getCampo_data_tipo() {
		return campo_data_tipo;
	}
	public void setCampo_data_tipo(String campo_data_tipo) {
		this.campo_data_tipo = campo_data_tipo;
	}
	
	
}
