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
import java.util.Arrays;
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
import org.bson.Document;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.text.PDFTextStripperByArea;

import classes.Conexao;
import classes.ConexaoMongo;
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
		
		int last_id=-1;
		HttpSession session = req.getSession(true);
		Conexao conn = (Conexao) session.getAttribute("conexao");
		InputStream inputStream=null;
		ConexaoMongo c = new ConexaoMongo();
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
			    		   //c.RemoverMuitosSemFiltro("sites", p.getEmpresa().getEmpresa_id());
			    		   Document filtro = new Document();
			    		   Document update = new Document();
			    		   Document update1 = new Document();
			    		   update.append("site_ativo", "N");
			    		   filtro.append("Empresa", p.getEmpresa().getEmpresa_id());
			    		   update1.append("$set", update);
			    		   c.AtualizaMuitos("sites", filtro, update1);
			    		   System.out.println("Sites Desativados por substituição completa em "+time+" por "+p.get_PessoaUsuario());
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
				    		
				    		Document site = new Document();
				    		String site_latitude="";
				    		String site_longitude="";
				    		String site_id_encontrado="";
				    		Document geo = new Document();
				    		Document geometry = new Document();
				    		Document properties = new Document();
				    		XSSFWorkbook wb = new XSSFWorkbook(inputStream2);
				    		Sheet sheet1 = wb.getSheet("SitesImportar");
				    		Cell cell;
				    		String cellValue;
				    		last_id=0;
				    		for (Row row : sheet1) {
				    			site_id_encontrado="";
				    			if(row.getRowNum()>0) {
				    			//sql="insert into sites(dt_add_site,usuario_add_site,empresa) values(\n";
				    			int indexCell=1;
				    				 site.append("Empresa",p.getEmpresa().getEmpresa_id());
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_sequencial", cellValue);
				    	             
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_uf", cellValue);
				    	             properties.append("UF", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_id", cellValue);
				    	             properties.append("SiteID", cellValue);
				    	             if(cellValue.equals("")) {
				    	            	 site_id_encontrado="nao encontrado";
				    	             }
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_nome", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_endereco", cellValue);
				    	             site.append("site_ativo", "Y");
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_cep", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site_latitude=cellValue;
				    	             site.append("site_latitude", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site_longitude = cellValue;
				    	             site.append("site_longitude", cellValue);
				    	             geometry.append("type", "Point");
									 geometry.append("coordinates", verfica_coordenadas(site_longitude,site_latitude));
									 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_altitude", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_Area_Total", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_area_tarifacao", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_municipio", cellValue);
				    	             properties.append("Municipio", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_reg_operacional", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_bairro", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_tipo_contrato", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_init_oper", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_owner", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_tipo_construcao", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_tipo_estrutura", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_link", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_operadora", cellValue);
				    	             properties.append("Operadora", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_modelo", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_antena", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_obs1", cellValue);
				    	             indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             cellValue=cellValue.replace("'", "_");
				    	             site.append("site_obs2", cellValue);
				    	             site.append("dt_add_site", f3.format(time));
				    	             geo.append("type","Feature");
				    	             geo.append("geometry", geometry);
				    	             geo.append("properties", properties);
				    	             site.append("GEO", geo);
				    	             site.append("Update_by", p.get_PessoaUsuario());
				    	             site.append("Update_time", time);
				    	             
				    			
				    			
				    			//System.out.println(sql);
				    			if(!site_id_encontrado.equals("nao encontrado")) {
					    			c.InserirSimpels("sites", site);
					    		}
				    			
				    			}
				    			site.clear();
				    			geo.clear();
				    			geometry.clear();
				    			properties.clear();
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
			System.out.println(e.getMessage());
            e.printStackTrace();
			inputStream.close();
			conn.fecharConexao();
    		
    	}
		
	}
	 public List<Double> verfica_coordenadas(String lng,String lat) {
		 try {
			 Double f_lat=Double.parseDouble(lat.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
			 Double f_lng=Double.parseDouble(lng.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
			 return Arrays.asList(f_lat,f_lng);
		 }catch (NumberFormatException e) {
				
				
				return Arrays.asList(-10.00,-10.00);
		}
	 }
}
