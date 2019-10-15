package classes;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class Rollout {
 private CamposRollout campos;
 
 public Rollout() {
	 campos=new CamposRollout();
 }

public CamposRollout getCampos() {
	return campos;
}

public void setCampos(CamposRollout campos) {
	this.campos = campos;
}
 
public String getUsuariosSitex(String site,Pessoa p) {
	Locale locale_ptBR = new Locale( "pt" , "BR" ); 
	Locale.setDefault(locale_ptBR);
	DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
	Calendar hoje = Calendar.getInstance();
	
	hoje.set(Calendar.HOUR_OF_DAY,00);
	hoje.set(Calendar.MINUTE,01);
	
	String equipes="";
	Bson Filtro;
	Document usuarios;
	List<Bson> filtroLista = new ArrayList<>();
	Filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
	filtroLista.add(Filtro);
	System.out.println("dia da pesquisa:"+f2.format(hoje.getTime()));
	Filtro=Filters.eq("data_dia_string",f2.format(hoje.getTime()));
	filtroLista.add(Filtro);
	Filtro=Filters.eq("local_registro",site);
	filtroLista.add(Filtro);
	ConexaoMongo mongo =  new ConexaoMongo();
	FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("Registros", filtroLista);
	MongoCursor<Document> resultado = findIterable.iterator();
	if(resultado.hasNext()) {
		System.out.println("achou algum registro!");
		while(resultado.hasNext()) {
			equipes=resultado.next().getString("Usuario")+" | ";
			//System.out.println("Site com registro hoje: "+resultado.next().getString("local_registro"));
		}
		mongo.fecharConexao();
		return equipes;
	}
	
	return equipes;
}
 
}
