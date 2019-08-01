package classes;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.bson.Document;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.line.LineStyle;

public class EspelhoPonto {
	public EspelhoPonto() {
		
	}
	public void TabelaPontos(String nome,String usuarioid,float altura,float largura,PDDocument document,PDPage page,String mes,String inicio,String fim,Document empresa,Document usuario,Pessoa p,Conexao mysql,PDType0Font font2) {
		String padrao_data_br = "dd/MM/yyyy";
		final String padrao_data_hora_br = "dd/MM/yyyy HH:mm:ss";
		Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		Locale.setDefault(locale_ptBR);
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
		float margin = 30;
        // starting y position is whole page height subtracted by top and bottom margin
        float yStartNewPage = altura - (2 * margin);
        // we want table across whole page width (subtracted by left and right margin ofcourse)
        float tableWidth = largura - (2 * margin);
        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;
        // y position is your coordinate of top left corner of the table
        float yPosition = 620;
        PDFont fontBold = PDType1Font.TIMES_BOLD;
        PDFont font = PDType1Font.TIMES_ROMAN;
        
        Feriado feriado= new Feriado();
        SimpleDateFormat format = new SimpleDateFormat(padrao_data_br);
        if(p.getPerfil_funcoes().contains("Usuarios Manager")) {
        try {
        	//PDType0Font font2 = PDType0Font.load(document, new File("https://fonts.googleapis.com/css?family=Playfair+Display&display=swap"));
        	BaseTable table = new BaseTable(yPosition, yStartNewPage,
			        bottomMargin, tableWidth, margin, document, page, true, drawContent);
        	Row<PDPage> headerRow = table.createRow(10);
        	
        	 Cell<PDPage> cell = headerRow.createCell(10, "Data");
		        cell.setFont(fontBold);
		        cell.setFillColor(Color.DARK_GRAY);
		        cell.setTextColor(Color.WHITE);
		        cell.setFontSize(10);
		        // vertical alignment
		        cell.setValign(VerticalAlignment.MIDDLE);
		        cell.setAlign(HorizontalAlignment.CENTER);
		        cell = headerRow.createCell(15, "Entrada");
		        cell.setFont(fontBold);
		        cell.setFillColor(Color.DARK_GRAY);
		        cell.setTextColor(Color.WHITE);
		        cell.setFontSize(10);
		        // vertical alignment
		        cell.setValign(VerticalAlignment.MIDDLE);
		        cell.setAlign(HorizontalAlignment.CENTER);
		        cell = headerRow.createCell(15, "Inicio Intervalo");
		        cell.setFont(fontBold);
		        cell.setFillColor(Color.DARK_GRAY);
		        cell.setTextColor(Color.WHITE);
		        cell.setFontSize(10);
		        // vertical alignment
		        cell.setValign(VerticalAlignment.MIDDLE);
		        cell.setAlign(HorizontalAlignment.CENTER);
		        cell = headerRow.createCell(15, "Fim Intervalo");
		        cell.setFont(fontBold);
		        cell.setFillColor(Color.DARK_GRAY);
		        cell.setTextColor(Color.WHITE);
		        cell.setFontSize(10);
		        // vertical alignment
		        cell.setValign(VerticalAlignment.MIDDLE);
		        cell.setAlign(HorizontalAlignment.CENTER);
		        cell = headerRow.createCell(15, "Saida");
		        
		        cell.setFont(fontBold);
		        cell.setFillColor(Color.DARK_GRAY);
		        cell.setTextColor(Color.WHITE);
		        cell.setFontSize(10);
		        // vertical alignment
		        cell.setValign(VerticalAlignment.MIDDLE);
		        cell.setAlign(HorizontalAlignment.CENTER);
		        cell = headerRow.createCell(20, "Local");
		        cell.setFont(fontBold);
		        cell.setFillColor(Color.DARK_GRAY);
		        cell.setTextColor(Color.WHITE);
		        cell.setFontSize(10);
		        // vertical alignment
		        cell.setValign(VerticalAlignment.MIDDLE);
		        cell.setAlign(HorizontalAlignment.CENTER);
		        cell = headerRow.createCell(10, "Status");
		        cell.setFont(fontBold);
		        cell.setFillColor(Color.DARK_GRAY);
		        cell.setTextColor(Color.WHITE);
		        cell.setFontSize(10);
		        // vertical alignment
		        cell.setValign(VerticalAlignment.MIDDLE);
		        cell.setAlign(HorizontalAlignment.CENTER);
		        // border style
		        
		        table.addHeaderRow(headerRow);
		        
				Calendar inicioCalendar= Calendar.getInstance();
				Calendar fimCalendar= Calendar.getInstance();
				Calendar inicio_autorizacao= Calendar.getInstance();
				ResultSet rs,rs2;
				Date dt_inicio=format.parse(inicio);
				Date dt_fim=format.parse(fim);
				inicioCalendar.setTime(dt_inicio);
				fimCalendar.setTime(dt_fim);
				String local;
				String encontrado="n";
				String tipo_registro="Normal";
				String tipo_ajustar=" - ";
				String entrada="00:00:00";
				String saida="00:00:00";
				String [] HH = new String[4];
				int contador=1;
				local="Sem Localização";
				HH[0] = "Caculo Indisponivel";
				HH[1]="0.0";
				HH[2]="0.0";
				HH[3]="0.0";
				String query="";
				String param3="";
				String param4="";
				Double total_horas_acumuladas=0.0;
				Double total_horas_acumuladas_sabado=0.0;
				Double total_horas_acumuladas_domingo=0.0;
				query="select distinct data_dia from registros where usuario='"+usuarioid+"' and str_to_date(datetime_servlet,'%Y-%m-%d') >= str_to_date('"+f2.format(inicioCalendar.getTime())+"','%d/%m/%Y') and str_to_date(datetime_servlet,'%Y-%m-%d') <= str_to_date('"+f2.format(fimCalendar.getTime())+"','%d/%m/%Y') order by datetime_servlet asc" ;
				rs=mysql.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					fimCalendar.add(Calendar.DAY_OF_MONTH, 1);
					while(inicioCalendar.before(fimCalendar)) {
						HH[0] = "Caculo Indisponivel";
						HH[1]="0.0";
						HH[2]="0.0";
						HH[3]="0.0";
						while(rs.next()) {
							param3="";
							param4="";
							if(f2.format(inicioCalendar.getTime()).equals(rs.getString("data_dia"))) {
								Row<PDPage> row = table.createRow(4);
								if(inicioCalendar.get(Calendar.DAY_OF_WEEK)==1) {
									tipo_registro="Domingo";
									tipo_ajustar=" - ";
								}else if(inicioCalendar.get(Calendar.DAY_OF_WEEK)==7) {
									tipo_registro="Sábado";
									tipo_ajustar=" - ";
								}else if(feriado.verifica_feriado(f2.format(inicioCalendar.getTime()),p.getEstadoUsuario(usuarioid,mysql), mysql, p.getEmpresa().getEmpresa_id())) {
									tipo_registro="Feriado";
									tipo_ajustar=" - ";
								}else {
									tipo_registro="Normal";
									tipo_ajustar=" - ";
								}
								
						        cell = row.createCell(10, rs.getString("data_dia"));
						        cell.setTopPadding(1);
						        cell.setBottomPadding(1);
						        cell.setFontSize(8);
								
						        query="SELECT * FROM registros where usuario='"+usuarioid+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro in ('Folga','Compensação','Licença Médica','Férias') order by datetime_servlet asc limit 1";
								rs2=mysql.Consulta(query);
								if(rs2.next()) {
									cell = row.createCell(15, "-:-");
							        cell.setTopPadding(1);
							        cell.setBottomPadding(1);
							        cell.setFontSize(8);
							        
							        cell = row.createCell(15, "-:-");
							        cell.setTopPadding(1);
							        cell.setBottomPadding(1);
							        cell.setFontSize(8);
							        
							        cell = row.createCell(15, "-:-");
							        cell.setTopPadding(1);
							        cell.setBottomPadding(1);
							        cell.setFontSize(8);
							        
							        cell = row.createCell(15, "-:-");
							        cell.setTopPadding(1);
							        cell.setBottomPadding(1);
							        cell.setFontSize(8);
							        
							        cell = row.createCell(20, "-:-");
							        cell.setTopPadding(1);
							        cell.setBottomPadding(1);
							        cell.setFontSize(8);
							        
							        cell = row.createCell(10, rs2.getString("tipo_registro"));
							        cell.setTopPadding(1);
							        cell.setBottomPadding(1);
							        cell.setFontSize(8);
								}else {
									//busca entrada
									query="SELECT * FROM registros where usuario='"+usuarioid+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
									rs2=mysql.Consulta(query);
									if(rs2.next()) {
										cell = row.createCell(15, rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5));
								        cell.setTopPadding(1);
								        cell.setBottomPadding(1);
								        cell.setFontSize(8);
								        if(rs2.getString("tipo_local_registro").equals("Site")) {
								        	local=rs2.getString("tipo_local_registro")+"|"+rs2.getString("site_operadora_registro")+"|"+rs2.getString("local_registro");
								        }else {
								        	local=rs2.getString("tipo_local_registro");
								        }
								        param3=rs2.getString("datetime_servlet");
									}else {
										cell = row.createCell(15, "");
								        cell.setTopPadding(1);
								        cell.setBottomPadding(1);
								        cell.setFontSize(8);
									}
									//busca inicio intervalo
									query="SELECT * FROM registros where usuario='"+usuarioid+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Inicio_intervalo' order by datetime_servlet asc limit 1";
									rs2=mysql.Consulta(query);
									if(rs2.next()) {
										cell = row.createCell(15, rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5));
								        cell.setTopPadding(1);
								        cell.setBottomPadding(1);
								        cell.setFontSize(8);
								        if(rs2.getString("tipo_local_registro").equals("Site")) {
								        	local=rs2.getString("tipo_local_registro")+"|"+rs2.getString("site_operadora_registro")+"|"+rs2.getString("local_registro");
								        }else {
								        	local=rs2.getString("tipo_local_registro");
								        }
									}else {
										cell = row.createCell(15, "");
								        cell.setTopPadding(1);
								        cell.setBottomPadding(1);
								        cell.setFontSize(8);
									}
									//busca fim intervalo
									query="SELECT * FROM registros where usuario='"+usuarioid+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Fim_intervalo' order by datetime_servlet asc limit 1";
									rs2=mysql.Consulta(query);
									if(rs2.next()) {
										cell = row.createCell(15, rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5));
								        cell.setTopPadding(1);
								        cell.setBottomPadding(1);
								        cell.setFontSize(8);
								        if(rs2.getString("tipo_local_registro").equals("Site")) {
								        	local=rs2.getString("tipo_local_registro")+"|"+rs2.getString("site_operadora_registro")+"|"+rs2.getString("local_registro");
								        }else {
								        	local=rs2.getString("tipo_local_registro");
								        }
									}else {
										cell = row.createCell(15, "");
								        cell.setTopPadding(1);
								        cell.setBottomPadding(1);
								        cell.setFontSize(8);
									}
									//busca saída
									query="SELECT * FROM registros where usuario='"+usuarioid+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Saída' order by datetime_servlet asc limit 1";
									rs2=mysql.Consulta(query);
									if(rs2.next()) {
										cell = row.createCell(15, rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5));
								        cell.setTopPadding(1);
								        cell.setBottomPadding(1);
								        cell.setFontSize(8);
								        if(rs2.getString("tipo_local_registro").equals("Site")) {
								        	local=rs2.getString("tipo_local_registro")+"|"+rs2.getString("site_operadora_registro")+"|"+rs2.getString("local_registro");
								        }else {
								        	local=rs2.getString("tipo_local_registro");
								        }
								        param4=rs2.getString("datetime_servlet");
									}else {
										cell = row.createCell(15, "");
								        cell.setTopPadding(1);
								        cell.setBottomPadding(1);
								        cell.setFontSize(8);
									}
									cell = row.createCell(20, local);
							        cell.setTopPadding(1);
							        cell.setBottomPadding(1);
							        cell.setFontSize(8);
							        cell = row.createCell(10, tipo_registro);
							        cell.setTopPadding(1);
							        cell.setBottomPadding(1);
							        cell.setFontSize(8);
									
							        if(!param3.equals("") && !param4.equals("")) {
										
										HH=calcula_hh(param3,param4,feriado,mysql,p,usuarioid);
										
										if(Double.parseDouble(HH[1])>0.0) {
											if(HH[3].equals("Sábado")){
												total_horas_acumuladas_sabado=total_horas_acumuladas_sabado+Double.parseDouble(HH[1]);
											}else if(HH[3].equals("Domingo")) {
												total_horas_acumuladas_domingo=total_horas_acumuladas_domingo+Double.parseDouble(HH[1]);
											}else if(HH[3].equals("Feriado")) {
												total_horas_acumuladas_domingo=total_horas_acumuladas_domingo+Double.parseDouble(HH[1]);
											}else {
												total_horas_acumuladas=total_horas_acumuladas+Double.parseDouble(HH[1]);
											}
										}
									}else if(!param3.equals("") && param4.equals("")) {
										if(p.getEmpresa().getEmpresa_id()==1) {
										HH=calcula_hh(param3,param3.substring(0, 10)+" 18:30:00",feriado,mysql,p,usuarioid);
										}else if(p.getEmpresa().getEmpresa_id()==5){
											if(inicioCalendar.get(Calendar.DAY_OF_WEEK)==6) {
												HH=calcula_hh(param3,param3.substring(0, 10)+" 16:00:00",feriado,mysql,p,usuarioid);
											}else {
												HH=calcula_hh(param3,param3.substring(0, 10)+" 17:00:00",feriado,mysql,p,usuarioid);
											}
											//HH_aux=calcula_hh(param3,param3.substring(0, 10)+" 17:00:00",feriado,conn,p,param2);
										}else {
											HH=calcula_hh(param3,param3.substring(0, 10)+" 18:00:00",feriado,mysql,p,usuarioid);
										}
										if(HH[3].equals("Sábado")){
											total_horas_acumuladas_sabado=total_horas_acumuladas_sabado+Double.parseDouble(HH[1]);
										}else if(HH[3].equals("Domingo")) {
											total_horas_acumuladas_domingo=total_horas_acumuladas_domingo+Double.parseDouble(HH[1]);
										}else if(HH[3].equals("Feriado")) {
											total_horas_acumuladas_domingo=total_horas_acumuladas_domingo+Double.parseDouble(HH[1]);
										}else {
											total_horas_acumuladas=total_horas_acumuladas+Double.parseDouble(HH[1]);
										}
									}
							        
								}
								encontrado="s";
							}
						}
						if(encontrado.equals("n")) {
							Row<PDPage> row = table.createRow(4);
						if(inicioCalendar.get(Calendar.DAY_OF_WEEK)==1) {
							tipo_registro="Domingo";
							
							}else if(inicioCalendar.get(Calendar.DAY_OF_WEEK)==7) {
								tipo_registro="Sábado";
								
							}else if(feriado.verifica_feriado(f2.format(inicioCalendar.getTime()),p.getEstadoUsuario(usuarioid,mysql), mysql, p.getEmpresa().getEmpresa_id())) {
								tipo_registro="Feriado";
								
							}else {
								tipo_registro="Falta";
								
							}
							
						cell = row.createCell(10, f2.format(inicioCalendar.getTime()));
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
						cell = row.createCell(15, "-:-");
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
				        cell = row.createCell(15, "-:-");
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
				        cell = row.createCell(15, "-:-");
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
				        cell = row.createCell(15, "-:-");
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
				        cell = row.createCell(20, "-:-");
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
				        cell = row.createCell(10, tipo_registro);
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
						}
						encontrado="n";
						rs.beforeFirst();
						inicioCalendar.add(Calendar.DAY_OF_MONTH, 1);
					}
				}else {
					fimCalendar.add(Calendar.DAY_OF_MONTH, 1);
					while(inicioCalendar.before(fimCalendar)) {
						Row<PDPage> row = table.createRow(4);
						if(inicioCalendar.get(Calendar.DAY_OF_WEEK)==1) {
							tipo_registro="Domingo";
							
							}else if(inicioCalendar.get(Calendar.DAY_OF_WEEK)==7) {
								tipo_registro="Sábado";
								
							}else if(feriado.verifica_feriado(f2.format(inicioCalendar.getTime()),p.getEstadoUsuario(usuarioid,mysql), mysql, p.getEmpresa().getEmpresa_id())) {
								tipo_registro="Feriado";
								
							}else {
								tipo_registro="Falta";
								
							}
							
						cell = row.createCell(10, f2.format(inicioCalendar.getTime()));
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
						cell = row.createCell(15, "-:-");
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
				        cell = row.createCell(15, "-:-");
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
				        cell = row.createCell(15, "-:-");
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
				        cell = row.createCell(15, "-:-");
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
				        cell = row.createCell(20, "-:-");
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        
				        cell = row.createCell(10, tipo_registro);
				        cell.setTopPadding(1);
				        cell.setBottomPadding(1);
				        cell.setFontSize(8);
				        inicioCalendar.add(Calendar.DAY_OF_MONTH, 1);
					}
				}
        	 table.draw();
        	 Integer horas_acumuladas=total_horas_acumuladas.intValue();
				Double minutos_acumuladosDouble=(total_horas_acumuladas- Math.floor(total_horas_acumuladas))*60;
				int minutos_acumuladosint=minutos_acumuladosDouble.intValue();
				String total_horas_acumuladastring="";
				String total_horas_acumulada_sabadostring="";
				String total_horas_acumulada_domingostring="";
				if(horas_acumuladas<10) {
					total_horas_acumuladastring="0"+horas_acumuladas;
				}else {
					total_horas_acumuladastring=horas_acumuladas.toString();
				}
				if(minutos_acumuladosint<10) {
					total_horas_acumuladastring=total_horas_acumuladastring+":0"+minutos_acumuladosint;
				}else {
					total_horas_acumuladastring=total_horas_acumuladastring+":"+minutos_acumuladosint;
				}
				horas_acumuladas=total_horas_acumuladas_sabado.intValue();
				minutos_acumuladosDouble=(total_horas_acumuladas_sabado- Math.floor(total_horas_acumuladas_sabado))*60;
				minutos_acumuladosint=minutos_acumuladosDouble.intValue();
				if(horas_acumuladas<10) {
					total_horas_acumulada_sabadostring="0"+horas_acumuladas;
				}else {
					total_horas_acumulada_sabadostring=horas_acumuladas.toString();
				}
				if(minutos_acumuladosint<10) {
					total_horas_acumulada_sabadostring=total_horas_acumulada_sabadostring+":0"+minutos_acumuladosint;
				}else {
					total_horas_acumulada_sabadostring=total_horas_acumulada_sabadostring+":"+minutos_acumuladosint;
				}
				horas_acumuladas=total_horas_acumuladas_domingo.intValue();
				minutos_acumuladosDouble=(total_horas_acumuladas_domingo- Math.floor(total_horas_acumuladas_domingo))*60;
				minutos_acumuladosint=minutos_acumuladosDouble.intValue();
				if(horas_acumuladas<10) {
					total_horas_acumulada_domingostring="0"+horas_acumuladas;
				}else {
					total_horas_acumulada_domingostring=horas_acumuladas.toString();
				}
				if(minutos_acumuladosint<10) {
					total_horas_acumulada_domingostring=total_horas_acumulada_domingostring+":0"+minutos_acumuladosint;
				}else {
					total_horas_acumulada_domingostring=total_horas_acumulada_domingostring+":"+minutos_acumuladosint;
				}
        	 yPosition=150;
        	 BaseTable tablerodape = new BaseTable(yPosition, yStartNewPage,
 			        bottomMargin, tableWidth, margin, document, page, true, drawContent);
        	 Row<PDPage> row = tablerodape.createRow(4);
        	
        	 cell = row.createCell(100, "Observações:");
        	 cell.setBorderStyle(new LineStyle(Color.white,1));
        	 cell.setFont(font2);
		     cell.setTopPadding(1);
		     cell.setBottomPadding(1);
		     cell.setFontSize(8);
		     row = tablerodape.createRow(4);
		     cell = row.createCell(100, "Horas Extras (50%) "+total_horas_acumulada_sabadostring+" | Horas Extras (100%) "+total_horas_acumulada_domingostring);
		     cell.setFont(font2);
		     cell.setBorderStyle(new LineStyle(Color.white,1));
		     cell.setTopPadding(1);
		     cell.setBottomPadding(1);
		     cell.setFontSize(8);
		     row = tablerodape.createRow(4);
		     cell = row.createCell(100, "Reconhecemos a exatidão das informações aqui descritas");
		     cell.setFont(font2);
        	 cell.setBorderStyle(new LineStyle(Color.white,1));
		     cell.setFontSize(8);
		     row = tablerodape.createRow(4);
		     cell = row.createCell(30, "");
        	 cell.setBorderStyle(new LineStyle(Color.white,1));
        	 cell.setBottomBorderStyle(new LineStyle(Color.BLACK,1));
		     cell.setFontSize(8);
		     cell = row.createCell(40, "");
        	 cell.setBorderStyle(new LineStyle(Color.white,1));
        	 cell.setFontSize(8);
        	 cell = row.createCell(30, "");
        	 cell.setBorderStyle(new LineStyle(Color.white,1));
        	 cell.setBottomBorderStyle(new LineStyle(Color.BLACK,1));
		     cell.setFontSize(8);
		     row = tablerodape.createRow(4);
		     cell = row.createCell(30, "Ass. Empregador");
        	 cell.setBorderStyle(new LineStyle(Color.white,1));
        	 cell.setFont(fontBold);
        	 cell.setTopPadding(1);
		     cell.setBottomPadding(1);
		     cell.setFontSize(8);
		     cell = row.createCell(40, "");
        	 cell.setBorderStyle(new LineStyle(Color.white,1));
        	 cell.setTopPadding(1);
		     cell.setBottomPadding(1);
        	 cell.setFontSize(8);
        	 cell = row.createCell(30, nome);
        	 cell.setTopPadding(1);
		     cell.setBottomPadding(1);
        	 cell.setBorderStyle(new LineStyle(Color.white,1));
        	 cell.setFont(fontBold);
		     cell.setFontSize(8);
		     tablerodape.draw();  
        	 
        }catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
       
	}
	public BaseTable TabelaCabecalho(String nomeUsuario,float altura,float largura,PDDocument document,PDPage page,String mes,String inicio,String fim,Document empresa,Document usuario) {
		float margin = 30;
        // starting y position is whole page height subtracted by top and bottom margin
        float yStartNewPage = altura - (2 * margin);
        // we want table across whole page width (subtracted by left and right margin ofcourse)
        float tableWidth = largura - (2 * margin);
        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;
        // y position is your coordinate of top left corner of the table
        float yPosition = 780;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
       
        try {
			BaseTable table = new BaseTable(yPosition, yStartNewPage,
			        bottomMargin, tableWidth, margin, document, page, true, drawContent);
			 Row<PDPage> headerRow = table.createRow(50);
		        // the first parameter is the cell width
		        Cell<PDPage> cell = headerRow.createCell(100, "Folha de Ponto - periodo "+mes);
		        cell.setFont(fontBold);
		        cell.setFontSize(16);
		        // vertical alignment
		        cell.setValign(VerticalAlignment.MIDDLE);
		        cell.setAlign(HorizontalAlignment.CENTER);
		        // border style
		        cell.setTopBorderStyle(new LineStyle(Color.white, 10));
		        cell.setLeftBorderStyle(new LineStyle(Color.white, 10));
		        cell.setRightBorderStyle(new LineStyle(Color.white, 10));
		        table.addHeaderRow(headerRow);
		        Row<PDPage> row = table.createRow(4);
		        cell = row.createCell(100, empresa.getString("NomeEmpresa"));
		        cell.setFont(fontBold);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setFontSize(8);
		        row = table.createRow(4);
		        cell = row.createCell(10, "Endereco:");
		        cell.setTopPadding(1);
		        cell.setBottomPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(90, empresa.getString("EnderecoEmpresa"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        row = table.createRow(4);
		        cell = row.createCell(10, "Bairro:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(30, empresa.getString("BairroEmpresa"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(10, "Cidade:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(30, empresa.getString("CidadeEmpresa"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(10, "Estado:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(10, empresa.getString("EstadoEmpresa"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        
		        row = table.createRow(4);
		        cell = row.createCell(10, "CNPJ:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(30, empresa.getString("CnpjEmpresa"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(10, "CNAE:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(30, empresa.getString("CnaeEmpresa"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(10, "VistoFiscal:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(10, "");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        
		        row = table.createRow(4);
		        cell = row.createCell(100, usuario.getString("usuarioNome"));
		        cell.setLeftBorderStyle(new LineStyle(Color.white,1));
		        cell.setRightBorderStyle(new LineStyle(Color.white,1));
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        row = table.createRow(8);
		        cell = row.createCell(10, "CTPS:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(30, usuario.getString("CTPS"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(10, "");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(30, "");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(10, "Matricula:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(10, usuario.getString("matricula"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        
		        row = table.createRow(4);
		        cell = row.createCell(10, "Cargo:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(30, usuario.getString("Cargo"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(10, "");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(30, "");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(10, "Admissao:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(10, usuario.getString("adm"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        
		        row = table.createRow(4);
		        cell = row.createCell(10, "Horario:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(30, usuario.getString("horario"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(10, "Intervalo:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(30, usuario.getString("intervalo"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        cell = row.createCell(10, "PIS:");
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFont(fontBold);
		        cell.setFontSize(8);
		        cell = row.createCell(10, usuario.getString("pis"));
		        cell.setTopPadding(1);
		        cell.setBorderStyle(new LineStyle(Color.white,1));
		        cell.setBottomPadding(1);
		        cell.setFontSize(8);
		        
			return table;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		}
	}
	public String[] calcula_hh(String entrada,String saida,Feriado f,Conexao c, Pessoa p,String usuarioPesquisado) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		DecimalFormat twoDForm = (DecimalFormat)nf;
		twoDForm.applyPattern("#0.00");
		Date d1 = null;
		Date d2 = null;
		//String msg="";
		String[] retorno=new String[4];
		try {
			d1 = format.parse(entrada);
			d2= format.parse(saida);
			Calendar hora_saida=Calendar.getInstance();
		    hora_saida.setTime(d2);
		    Calendar hora_entrada=Calendar.getInstance();
		    hora_entrada.setTime(d1);
		    int aux_hora_entrada=hora_entrada.get(Calendar.HOUR_OF_DAY);
			int aux_min_entrada=hora_entrada.get(Calendar.MINUTE);
			double total_hora_entrada=aux_hora_entrada + (aux_min_entrada /60.0);
			int aux_hora_saida=hora_saida.get(Calendar.HOUR_OF_DAY);
			int aux_min_saida=hora_saida.get(Calendar.MINUTE);
			double total_hora_saida=aux_hora_saida + (aux_min_saida / 60.0);
			double total_horas=total_hora_saida-total_hora_entrada;
			double horas_extras=0.0;
			double horas_extras_noturnas=0.0;
			if(p.getEmpresa().getEmpresa_id()==1) {
			if(hora_entrada.get(Calendar.DAY_OF_WEEK)!=1 && hora_entrada.get(Calendar.DAY_OF_WEEK)!=7) {
				if(f.verifica_feriado(f2.format(hora_entrada.getTime()),p.getEstadoUsuario(usuarioPesquisado, c), c, p.getEmpresa().getEmpresa_id())) {
					horas_extras=total_horas-1.2;
					horas_extras_noturnas=0.0;
					if(total_hora_saida>22.0 && total_hora_saida<23.59) {
						horas_extras_noturnas=total_hora_saida-22.0;
						horas_extras=horas_extras-horas_extras_noturnas;
					}
				
				retorno[0]="Hora Extra";
				retorno[1]=String.valueOf(twoDForm.format(horas_extras));
				retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
				retorno[3]="Feriado";
				
				//msg="BH:" + String.valueOf(twoDForm.format(horas_extras+horas_extras_noturnas));
				
				}else {
				if(total_horas>10.0) {
					horas_extras=total_horas-10.0;
					horas_extras_noturnas=0.0;
					if(total_hora_saida>22.0 && total_hora_saida<23.59) {
						horas_extras_noturnas=total_hora_saida-22.0;
						horas_extras=horas_extras-horas_extras_noturnas;
					}
				}else {
					horas_extras=total_horas-10.0;
					horas_extras_noturnas=0.0;
				}
				retorno[0]="Banco";
				retorno[1]=String.valueOf(twoDForm.format(horas_extras));
				retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
				retorno[3]="";
				//msg="HE:" + String.valueOf(twoDForm.format(horas_extras)) + " | HEN:" + String.valueOf(twoDForm.format(horas_extras_noturnas));
				}}else {
				 
					horas_extras=total_horas-1.2;
					horas_extras_noturnas=0.0;
					if(total_hora_saida>22.0 && total_hora_saida<23.59) {
						horas_extras_noturnas=total_hora_saida-22.0;
						horas_extras=horas_extras-horas_extras_noturnas;
					}
				
				retorno[0]="Hora Extra";
				retorno[1]=String.valueOf(twoDForm.format(horas_extras));
				retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
				if(hora_entrada.get(Calendar.DAY_OF_WEEK)==7) {
					retorno[3]="Sábado";
				}else {
					retorno[3]="Domingo";
				}
				//msg="BH:" + String.valueOf(twoDForm.format(horas_extras+horas_extras_noturnas));
				 }
			
			
			}else if(p.getEmpresa().getEmpresa_id()==5) {
				Double total_horas_dia=0.0;
				if(hora_entrada.get(Calendar.DAY_OF_WEEK)==6) {
					total_horas_dia=9.0;
				}else {
					total_horas_dia=10.0;
				}
				
					if(f.verifica_feriado(f2.format(hora_entrada.getTime()),p.getEstadoUsuario(usuarioPesquisado, c), c, p.getEmpresa().getEmpresa_id())) {

						horas_extras=total_horas-1.0;
						horas_extras_noturnas=0.0;
						if(total_hora_saida>22.0 && total_hora_saida<23.59) {
							horas_extras_noturnas=total_hora_saida-22.0;
							horas_extras=horas_extras-horas_extras_noturnas;
						}
					
						retorno[0]="Hora Extra";
						retorno[1]=String.valueOf(twoDForm.format(horas_extras));
						retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
						retorno[3]="Feriado";
					
					//msg="BH:" + String.valueOf(twoDForm.format(horas_extras+horas_extras_noturnas));
					
					}else {
						if(hora_entrada.get(Calendar.DAY_OF_WEEK)!=1 && hora_entrada.get(Calendar.DAY_OF_WEEK)!=7) {
					if(total_horas>total_horas_dia) {
						horas_extras=total_horas-total_horas_dia;
						horas_extras_noturnas=0.0;
						if(total_hora_saida>22.0 && total_hora_saida<23.59) {
							horas_extras_noturnas=total_hora_saida-22.0;
							horas_extras=horas_extras-horas_extras_noturnas;
						}
					}else {
						horas_extras=total_horas-total_horas_dia;
						horas_extras_noturnas=0.0;
					}
					retorno[0]="Hora Extra";
					retorno[1]=String.valueOf(twoDForm.format(horas_extras));
					retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
					retorno[3]="Sábado";
					//msg="HE:" + String.valueOf(twoDForm.format(horas_extras)) + " | HEN:" + String.valueOf(twoDForm.format(horas_extras_noturnas));
					}else if(hora_entrada.get(Calendar.DAY_OF_WEEK)==7) {
						horas_extras=total_horas-1.0;
						
						horas_extras_noturnas=0.0;
						if(total_hora_saida>22.0 && total_hora_saida<23.59) {
							horas_extras_noturnas=total_hora_saida-22.0;
							horas_extras=horas_extras-horas_extras_noturnas;
						}
					
					retorno[0]="Hora Extra";
					retorno[1]=String.valueOf(twoDForm.format(horas_extras));
					retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
					retorno[3]="Sábado";
					}else {
					 
							horas_extras=total_horas-1.0;
						
						horas_extras_noturnas=0.0;
						if(total_hora_saida>22.0 && total_hora_saida<23.59) {
							horas_extras_noturnas=total_hora_saida-22.0;
							horas_extras=horas_extras-horas_extras_noturnas;
						}
					
					retorno[0]="Hora Extra";
					retorno[1]=String.valueOf(twoDForm.format(horas_extras));
					retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
					retorno[3]="Domingo";
					
					//msg="BH:" + String.valueOf(twoDForm.format(horas_extras+horas_extras_noturnas));
					}}
				
				
				
			}
			return retorno;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
