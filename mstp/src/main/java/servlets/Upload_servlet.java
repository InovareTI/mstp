package servlets;

import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.text.PDFTextStripperByArea;

import classes.Conexao;
import classes.Pessoa;

/**
 * Servlet implementation class Upload_servlet
 */
@WebServlet("/Upload_servlet")
public class Upload_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload_servlet() {
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
		upload_po(request,response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		upload_po(request,response);
	}

	
	public void upload_po(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		//System.out.println("entrou no servlet de upload");
		ResultSet rs;
		int last_id=-1;
		HttpSession session = req.getSession(true);
		Conexao conn = (Conexao) session.getAttribute("conexao");
		InputStream inputStream=null;
		ServletContext context = req.getSession().getServletContext();
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		DateFormat f3 = DateFormat.getDateTimeInstance();
		System.out.println(p.get_PessoaUsuario()+" Chegou no servlet de Upload - "+f3.format(time));
		try{
		if (ServletFileUpload.isMultipartContent(req)) {
			List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
			//System.out.println("Multipart size: " + multiparts.size());
			Iterator<FileItem> iter = multiparts.iterator();
			while (iter.hasNext()) {
				FileItem item = iter.next();
				if (item.isFormField()) {
					String fieldname = item.getFieldName();
			        String fieldvalue = item.getString();
			        System.out.println(fieldname+":"+fieldvalue);
			       if(fieldname.equals("flag")) {
			    	   if(fieldvalue.equals("S")) {
			    		   String query="";
			    		   System.out.println("chegou na query de mudança");
			    		   query="update sites set site_ativo='N'";
			    		   if(conn.Update_simples(query)){
			    			   System.out.println("Sites Desativados por substituição completa em "+time+" por "+p.get_PessoaUsuario());
			    		   }
			    	   }
			       }
				}
			}
			iter = multiparts.iterator();
			while (iter.hasNext()) {
				
				FileItem item = iter.next();
				if (item.isFormField()) {
			        
			        
			    } else {
			       // processUploadedFile(item);
			    	 inputStream = item.getInputStream();
			    	 String sql = "Insert into arquivos_importados (arq_dt_inserido,arq,arq_nome,arq_tipo,arq_tamanho,arq_usuario) values ('"+time+"' , ? ,'"+item.getName()+"','"+item.getContentType()+"','"+item.getSize()+"','"+p.get_PessoaUsuario()+"')";
				     //System.out.println(sql);
			    	 PreparedStatement statement;
					 statement = conn.getConnection().prepareStatement(sql);
					 statement.setBlob(1, inputStream);
					 int linha = statement.executeUpdate();
				     statement.getConnection().commit();
				     if (linha>0) {
				    	    time = new Timestamp(System.currentTimeMillis());
				    	    System.out.println(p.get_PessoaUsuario()+" Arquivo inserido no banco com sucesso - "+f3.format(time));
				        	statement.close();
				        	resp.setContentType("application/json"); 
				        	JSONObject jsonObject = new JSONObject(); 
				        	jsonObject.put("Sucesso", "Sucesso"); 
				        	PrintWriter pw = resp.getWriter();
				        	pw.print(jsonObject); 
				        	pw.close();
				        	/*rs=conn.Consulta("Select id_sys_arq from arquivos_importados order by id_sys_arq desc limit 1");
				    		if(rs.next()){
				    			last_id=rs.getInt(1);
				    		}*/
				        	time = new Timestamp(System.currentTimeMillis());
				        	System.out.println(p.get_PessoaUsuario()+" inicio de processamento do arquivo - "+f3.format(time));
				    		InputStream inputStream2= new ByteArrayInputStream(IOUtils.toByteArray(item.getInputStream()));
				    		DataFormatter dataFormatter = new DataFormatter();
				    		
				    		XSSFWorkbook wb = new XSSFWorkbook(inputStream2);
				    		Sheet sheet1 = wb.getSheet("SitesImportar");
				    		Cell cell;
				    		String cellValue;
				    		last_id=0;
				    		for (Row row : sheet1) {
				    			if(row.getRowNum()>0) {
				    			sql="insert into sites(site_sequencial,site_uf,site_id,site_nome,site_endereco,site_cep,site_latitude,site_longitude,site_altitude,site_Area_Total,site_area_tarifacao,site_municipio,site_reg_operacional,site_bairro,site_tipo_contrato,site_init_oper,site_owner,site_tipo_construcao,site_tipo_estrutura,site_link,site_operadora,site_modelo,site_antena,site_obs1,site_obs2,dt_add_site,usuario_add_site,empresa) values(\n";
				    			for (int indexCell=1;indexCell<26;indexCell++) {
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 
				    				 cellValue=cellValue.replace("'", "_");
				    				 if(indexCell==3 && cellValue.equals("")) {
				    					 cellValue="SiteNaoEncontrado";
				    				 }
				    	             sql=sql+"'"+cellValue+"',\n";
				    	            
				    			}
				    			sql=sql+"'"+time+"','"+p.get_PessoaUsuario()+"',"+p.getEmpresa().getEmpresa_id()+")";
				    			
				    			//System.out.println(sql);
				    			if(!sql.contains("SiteNaoEncontrado")) {
					    			if(conn.Inserir_simples(sql)) {
					    				last_id=last_id+1;
					    			}
				    			}
				    			}
				    		}
				    		time = new Timestamp(System.currentTimeMillis());
				    		System.out.println(p.get_PessoaUsuario()+" Fim de processamento do arquivo com "+last_id+" linhas inseridas em "+f3.format(time));
				    		wb.close();
				    		
				        	
				        }
			    }
			}
		}
		
			
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			inputStream.close();
			conn.fecharConexao();
			e.printStackTrace();
		}catch (Exception e){
			inputStream.close();
			conn.fecharConexao();
    		System.out.println(e.getMessage());
            e.printStackTrace();
    	}
		
	}
}
