package classes;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.apache.commons.mail.EmailException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class RelatorioOperacionalDiario implements Job{
	public void execute(JobExecutionContext context) {
	    ConexaoMongo mongo = new ConexaoMongo();
	    Conexao mysql = new Conexao();
	    Locale brasil = new Locale("pt", "BR");
	    DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, brasil);
	    Calendar calendar = Calendar.getInstance();
	    Timestamp time = new Timestamp(System.currentTimeMillis());
	    ResultSet rs,rs2 = null,rs3 = null;
	    System.out.println("Iniciando relatório Operacional");
	    String email_html_base="";
	    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		DecimalFormat numberFormat = (DecimalFormat)nf;
		numberFormat.applyPattern("#0.00");
	    email_html_base="<!DOCTYPE html>\n" + 
	    		"<html lang=\"en\">\n" + 
	    		"  <head>\n" + 
	    		"    <meta charset=\"iso-8859-1\">\n" + 
	    		"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" + 
	    		"    <title>MSTP Relatório Operacional Simples - Empresa</title>\n" + 
	    		"    <!-- plugins:css -->\n" + 
	    		"    <link rel=\"stylesheet\" href=\"https://www.mstp.com.br/emailTemplate/assets/vendors/iconfonts/mdi/css/materialdesignicons.css\">\n" + 
	    		"    <!-- endinject -->\n" + 
	    		"    <!-- vendor css for this page -->\n" + 
	    		"    <!-- End vendor css for this page -->\n" + 
	    		"    <!-- inject:css -->\n" + 
	    		"    <link rel=\"stylesheet\" href=\"https://www.mstp.com.br/emailTemplate/assets/css/shared/style.css\">\n" + 
	    		"    <!-- endinject -->\n" + 
	    		"    <!-- Layout style -->\n" + 
	    		"    <link rel=\"stylesheet\" href=\"https://www.mstp.com.br/emailTemplate/assets/css/demo_1/style.css\">\n" + 
	    		"    <!-- Layout style -->\n" + 
	    		"    <link rel=\"shortcut icon\" href=\"https://www.mstp.com.br/emailTemplate/asssets/images/favicon.ico\" />\n" + 
	    		"      <style>\n" + 
	    		"          table.tableSection {\n" + 
	    		"        display: table;\n" + 
	    		"        width: 100%;\n" + 
	    				"    	padding: 5px;\n" + 
	    	    		"    	text-align: left;"+
	    	    		"		font-size:10px;"+
	    		"    }\n" + 
	    		"    table.tableSection thead, table.tableSection tbody {\n" + 
	    		"        float: left;\n" + 
	    		"        width: 100%;\n" + 
	    		"    }\n" + 
	    		"    table.tableSection tbody {\n" + 
	    		"        overflow: auto;\n" + 
	    		"		 overflow-y: auto;"+
	    		"        height: 150px;\n" + 
	    		"		 scrollbar-color: dark;"+
	    		"    }\n" + 
	    		"    table.tableSection tr {\n" + 
	    		"        width: 100%;\n" + 
	    		"        display: table;\n" + 
	    		"        text-align: left;\n" + 
	    		"    }\n" + 
	    		"    table.tableSection th, table.tableSection td {\n" + 
	    		"        width: 50%;\n" + 
	    		" 		padding: 5px;\n" + 
	    		"    	text-align: left;"+
	    		"		font-size:10px;"+
	    		"    }\n" + 
	    		"      </style>\n" + 
	    		"  </head>\n" + 
	    		"  <body class=\"header-fixed\">\n" + 
	    		"   \n" + 
	    		"<div>Relatorio processado em:"+f2.format(time)+"</div>"+
	    		"    <div class=\"page-body\">\n" + 
	    		"      <!-- partial:partials/_sidebar.html -->\n" + 
	    		"      <div class=\"sidebar\">\n" + 
	    		"        <div class=\"user-profile\">\n" + 
	    		"          <div class=\"display-avatar animated-avatar\">\n" + 
	    		"            <img class=\"profile-img img-lg rounded-circle\" src=\"https://www.mstp.com.br/emailTemplate/assets/images/image_1emplogo.png\" alt=\"profile image\">\n" + 
	    		"          </div>\n" + 
	    		"          <div class=\"info-wrapper\">\n" + 
	    		"            <p class=\"user-name\">Empresa</p>\n" + 
	    		"            \n" + 
	    		"          </div>\n" + 
	    		"        </div>\n" + 
	    		"        <ul class=\"navigation-menu\">\n" + 
	    		"          <li class=\"nav-category-divider\">MSTP RELATORIO OPERACIONAL SIMPLES</li>\n" + 
	    		"          <li>\n" + 
	    		"            <a href=\"index.html\">\n" + 
	    		"              <span class=\"link-title\">Dashboard</span>\n" + 
	    		"              <i class=\"mdi mdi-gauge link-icon\"></i>\n" + 
	    		"            </a>\n" + 
	    		"          </li>\n" + 
	    		"         \n" + 
	    		"        </ul>\n" + 
	    		"       \n" + 
	    		"      </div>\n" + 
	    		"      <!-- partial -->\n" + 
	    		"      <div class=\"page-content-wrapper\" style=\"background: #d5d5d5;\">\n" + 
	    		"        <div class=\"page-content-wrapper-inner\" style=\"background: #d5d5d5;\">\n" + 
	    		"          <div class=\"content-viewport\">\n" + 
	    		"            <div class=\"row\">\n" + 
	    		"              <div class=\"col-12 py-5\">\n" + 
	    		"                <h4>Dashboard</h4>\n" + 
	    		"                <p class=\"text-gray\">MSTP RELATORIO OPERACIONAL SIMPLES</p>\n" + 
	    		"              </div>\n" + 
	    		"            </div>\n" + 
	    		"            <div class=\"row\">\n" + 
	    		"              <div class=\"col-md-4 col-sm-6 col-6 equel-grid\">\n" + 
	    		"                <div class=\"grid\">\n" + 
	    		"                  <div class=\"grid-body text-gray\">\n" + 
	    		"                    <div class=\"d-flex justify-content-between\">\n" + 
	    		"                      \n" + 
	    		"                    </div>\n" + 
	    		"                    <p class=\"text-black\">Usuarios Online (total_online_1)</p>\n" + 
	    		"                    <div class=\"wrapper w-50 mt-4\">\n" + 
	    		"                     <table class=\"tableSection\">\n" + 
	    		"                        <thead>\n" + 
	    		"                            <tr>\n" + 
	    		"                                <th><span class=\"text\">Usuario</span></th>\n" + 
	    		"                            </tr>\n" + 
	    		"                        </thead>\n" + 
	    		"                         <tbody>\n" + 
	    		"                             corpo_tabela_1 \n" + 
	    		"                         </tbody>\n" + 
	    		"                    </table>\n" + 
	    		"                    </div>\n" + 
	    		"                  </div>\n" + 
	    		"                </div>\n" + 
	    		"              </div>\n" + 
	    		"              <div class=\"col-md-4 col-sm-6 col-6 equel-grid\">\n" + 
	    		"                <div class=\"grid\">\n" + 
	    		"                  <div class=\"grid-body text-gray\">\n" + 
	    		"                    <div class=\"d-flex justify-content-between\">\n" + 
	    		"                      \n" + 
	    		"                    </div>\n" + 
	    		"                    <p class=\"text-black\">Sites com Equipe (total_online_2) </p>\n" + 
	    		"                    <div class=\"wrapper w-50 mt-4\">\n" + 
	    		"                      <table class=\"tableSection\">\n" + 
	    		"                        <thead>\n" + 
	    		"                            <tr>\n" + 
	    		"                                <th><span class=\"text\">Site</span></th>\n" + 
	    		"                            </tr>\n" + 
	    		"                        </thead>\n" + 
	    		"                         <tbody>\n" + 
	    		"                             corpo_tabela_2 \n" + 
	    		"                         </tbody>\n" + 
	    		"                    </table>\n" + 
	    		"                    </div>\n" + 
	    		"                  </div>\n" + 
	    		"                </div>\n" + 
	    		"              </div>\n" + 
	    		"              </div>\n" + 
	    		"            <div class=\"row\">\n" + 
	    		"              <div class=\"col-md-4 col-sm-6 col-6 equel-grid\">\n" + 
	    		"                <div class=\"grid\">\n" + 
	    		"                  <div class=\"grid-body text-gray\">\n" + 
	    		"                    <div class=\"d-flex justify-content-between\">\n" + 
	    		"                     \n" + 
	    		"                    </div>\n" + 
	    		"                    <p class=\"text-black\">Rollout Status</p>\n" + 
	    		"                    <div class=\"wrapper w-50 mt-4\">\n" + 
	    		"                      <table class=\"tableSection\" style=\"width:260px\">\n" + 
	    		"                        <thead style=\"width:260px\">\n" + 
	    		"                            <tr>\n" + 
	    		"                                <th style=\"width:180px\"><span class=\"text\">Milestone</span></th>\n" + 
	    		"                                <th style=\"width:40px\"><span class=\"text\">%</span></th>\n" + 
	    		"                                <th style=\"width:40px\"><span class=\"text\">Avanço</span></th>\n" +
	    		"                            </tr>\n" + 
	    		"                        </thead>\n" + 
	    		"                         <tbody style=\"width:260px\">\n" + 
	    		"                             corpo_tabela_4 \n" + 
	    		"                         </tbody>\n" + 
	    		"                    </table>\n" + 
	    		"                    </div>\n" + 
	    		"                  </div>\n" + 
	    		"                </div>\n" + 
	    		"              </div>\n" + 
	    		"              <div class=\"col-md-4 col-sm-6 col-6 equel-grid\">\n" + 
	    		"                <div class=\"grid\">\n" + 
	    		"                  <div class=\"grid-body text-gray\">\n" + 
	    		"                    <div class=\"d-flex justify-content-between\">\n" + 
	    		"                      \n" + 
	    		"                    </div>\n" + 
	    		"                    <p class=\"text-black\">Valor Finalizado</p>\n" + 
	    		"                    <div class=\"wrapper w-50 mt-4\">\n" + 
	    		"                      <table class=\"tableSection\">\n" + 
	    		"                        <thead>\n" + 
	    		"                            <tr>\n" + 
	    		"                                <th><span class=\"text\">Site</span></th>\n" + 
	    		"                                <th><span class=\"text\">R$</span></th>\n" + 
	    		"                            </tr>\n" + 
	    		"                        </thead>\n" + 
	    		"                         <tbody>\n" + 
	    		"                             corpo_tabela_5\n" + 
	    		"                         </tbody>\n" + 
	    		"                    </table>\n" + 
	    		"                    </div>\n" + 
	    		"                  </div>\n" + 
	    		"                </div>\n" + 
	    		"              </div>\n" + 
	    		"              \n" + 
	    		"            </div>\n" + 
	    		"          </div>\n" + 
	    		"        </div>\n" + 
	    		"        <!-- content viewport ends -->\n" + 
	    		"        <!-- partial:partials/_footer.html -->\n" + 
	    		"        <footer class=\"footer\">\n" + 
	    		"          <div class=\"row\">\n" + 
	    		"            <div class=\"col-sm-6 text-center text-sm-right order-sm-1\">\n" + 
	    		"             \n" + 
	    		"            </div>\n" + 
	    		"            <div class=\"col-sm-6 text-center text-sm-left mt-3 mt-sm-0\">\n" + 
	    		"              <small class=\"text-muted d-block\">Copyright © 2019 <a href=\"http://www.inovare-ti.com\" target=\"_blank\">INOVARE TI</a>. All rights reserved</small>\n" + 
	    		"             \n" + 
	    		"            </div>\n" + 
	    		"          </div>\n" + 
	    		"        </footer>\n" + 
	    		"        <!-- partial -->\n" + 
	    		"      </div>\n" + 
	    		"      <!-- page content ends -->\n" + 
	    		"    </div>\n" + 
	    		"    <!--page body ends -->\n" + 
	    		"    <!-- SCRIPT LOADING START FORM HERE /////////////-->\n" + 
	    		"    <!-- plugins:js -->\n" + 
	    		"    <script src=\"https://www.mstp.com.br/emailTemplate/assets/vendors/js/core.js\"></script>\n" + 
	    		"    <!-- endinject -->\n" + 
	    		"    <!-- Vendor Js For This Page Ends-->\n" + 
	    		"    <script src=\"https://www.mstp.com.br/emailTemplate/assets/vendors/apexcharts/apexcharts.min.js\"></script>\n" + 
	    		"    <script src=\"https://www.mstp.com.br/emailTemplate/assets/vendors/chartjs/Chart.min.js\"></script>\n" + 
	    		"    <script src=\"https://www.mstp.com.br/emailTemplate/assets/js/charts/chartjs.addon.js\"></script>\n" + 
	    		"    <!-- Vendor Js For This Page Ends-->\n" + 
	    		"    <!-- build:js -->\n" + 
	    		"    <script src=\"https://www.mstp.com.br/emailTemplate/assets/js/template.js\"></script>\n" + 
	    		"    <script src=\"https://www.mstp.com.br/emailTemplate/assets/js/dashboard.js\"></script>\n" + 
	    		"    <!-- endbuild -->\n" + 
	    		"  </body>\n" + 
	    		"</html>";
	    rs=mysql.Consulta("select * from empresas where others='Y'");
	    try {
	    	 Semail  emailhtml = new Semail();
		    if(rs.next()) {
		    	rs.beforeFirst();
		    	while(rs.next()) {
		    		
						String email_html=email_html_base.replaceAll("Empresa",rs.getString("nome_fantasia"));
						String tabela1="";
						String tabela2="";
						String tabela3="";
						Integer contador=0;
						rs2=mysql.Consulta("select * from registros where data_dia='"+f2.format(time)+"' and empresa='"+rs.getInt(1)+"' and tipo_registro in ('Entrada','Saída','Fim_intervalo','Inicio_intervalo')"); 
						if(rs2.next()) {
							rs2.beforeFirst();
							while(rs2.next()) {
								contador=contador+1;
								tabela1=tabela1+"<tr><td>"+rs2.getString("usuario")+"</td></tr>";
								
							}
						}
						email_html=email_html.replace("image_1emplogo", Integer.toString(rs.getInt(1)));
						email_html=email_html.replace("total_online_1", contador.toString());
						email_html=email_html.replace("corpo_tabela_1", tabela1);
						contador=0;
						
						rs2=mysql.Consulta("select distinct local_registro from registros where data_dia='"+f2.format(time)+"' and empresa='"+rs.getInt(1)+"' and tipo_local_registro='Site' and tipo_registro in ('Entrada','Saída','Fim_intervalo','Inicio_intervalo')");
						if(rs2.next()) {
							rs2.beforeFirst();
							while(rs2.next()) {
								contador=contador+1;
								tabela2=tabela2+"<tr><td>"+rs2.getString("local_registro")+"</td></tr>";
							}
						}
						email_html=email_html.replace("total_online_2", contador.toString());
						email_html=email_html.replace("corpo_tabela_2", tabela2);
						Long total_linhas_rollout;
						
						
						Bson filtro;
						Bson filtro_aux;
						
						rs2=mysql.Consulta("select distinct field_name,rollout_nome from rollout_campos where empresa="+rs.getInt(1)+" and tipo='Milestone' order by ordenacao,rollout_nome asc");
						if(rs2.next()) {
							rs2.beforeFirst();
							while(rs2.next()) {
								String cor="red";
								Double avanco_atual=0.0;
								Double diferenca=0.0;
								List<Bson> lista = new ArrayList<>();
								filtro=Filters.eq("Empresa",rs.getInt(1));
								lista.add(filtro);
								filtro=Filters.eq("Linha_ativa","Y");
								lista.add(filtro);
								filtro=Filters.eq("rolloutId",rs2.getString("rollout_nome"));
								lista.add(filtro);
								total_linhas_rollout=mongo.ConsultaCountComplexa("rollout", lista);
								//System.out.println("total de linhas no rollout..."+total_linhas_rollout);
								filtro_aux=Filters.eq("status_"+rs2.getString("field_name"),"Finalizada");
								filtro=Filters.elemMatch("Milestone", filtro_aux);
								lista.add(filtro);
								Long consulta=mongo.ConsultaCountComplexa("rollout", lista);
								if(total_linhas_rollout>0) {
								avanco_atual=(consulta.doubleValue() / total_linhas_rollout.doubleValue())*100.0;
								System.out.println("Empresa:"+rs.getString("nome_fantasia"));
								System.out.println("RolloutID:"+rs2.getString("rollout_nome"));
								System.out.println("Atividade:"+rs2.getString("field_name"));
								System.out.println("Total de Atividades finalizadas:"+consulta.doubleValue());
								System.out.println("Total de linhas no Rollout:"+total_linhas_rollout.doubleValue());
								System.out.println("percent:"+avanco_atual+"%");
								avanco_atual=Double.parseDouble(numberFormat.format(avanco_atual));
								System.out.println("----------------------------------------------------------------");
								}else {
									avanco_atual=0.00;
									diferenca=0.00;
								}
								contador=contador+1;
								
								rs3=mysql.Consulta("select percent from rollout_kpi_rel_operacional where empresa="+rs.getInt(1)+" and milestone='"+rs2.getString("field_name")+"' and rollout_id='"+rs2.getString("rollout_nome")+"'");
								if(rs3.next()) {
									if(avanco_atual > rs3.getDouble(1) ) {
										diferenca=avanco_atual - rs3.getDouble(1);
										cor="green";
									}
								}
								tabela3=tabela3+"<tr><td style=\"width:180px;text-align:left;\">"+rs2.getString("field_name")+"</td><td style=\"width:40px;text-align:right;\">"+avanco_atual+"%"+"</td><td style=\"width:40px;text-align:right;color:"+cor+"\">"+numberFormat.format(diferenca)+"%</td></tr>";
								mysql.Alterar("update rollout_kpi_rel_operacional set milestone='"+rs2.getString("field_name")+"',empresa="+rs.getInt(1)+",dt='"+f2.format(time)+"',percent="+avanco_atual+",rollout_id='"+rs2.getString("rollout_nome")+"' where empresa="+rs.getInt(1)+" and milestone='"+rs2.getString("field_name")+"' and rollout_id='"+rs2.getString("rollout_nome")+"'");
							}
						}
						email_html=email_html.replace("corpo_tabela_4", tabela3);
						rs2=mysql.Consulta("select distinct email from usuarios where empresa='"+rs.getInt(1)+"' and ativo='Y' and usuariosreloperacional='Y'");
						if(rs2.next()) {
							rs2.beforeFirst();
							while(rs2.next()) {
								emailhtml.enviaEmailHtml(rs2.getString("email"), "MSTP - Relatorio Operacional " + rs.getString("nome_fantasia") + " - " + f2.format(time), email_html);
							}
						}
						
		    	}
		    }
		    rs.close();
		    rs2.close();
		    rs3.close();
	    } catch (SQLException e) {
	    	mongo.fecharConexao();
			 mysql.fecharConexao();
			e.printStackTrace();
		} catch (MalformedURLException e) {
			mongo.fecharConexao();
			 mysql.fecharConexao();
			e.printStackTrace();
		} catch (EmailException e) {
			mongo.fecharConexao();
			 mysql.fecharConexao();
			e.printStackTrace();
		}catch (Exception e) {
			 mongo.fecharConexao();
			 mysql.fecharConexao();
			e.printStackTrace();
		}
	   
	    mongo.fecharConexao();
	    mysql.fecharConexao();
	    
	}
}
