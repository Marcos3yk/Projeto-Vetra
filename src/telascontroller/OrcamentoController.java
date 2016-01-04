/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package telascontroller;

import DAO.PessoaDAOImpl;
import DAO.VeiculoDAOImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import model.Orcamento;
import model.Pessoa;
import model.Veiculo;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author MarcosVinicius
 */
public class OrcamentoController implements Initializable {
@FXML 
private TextField tfNome, tfModelo, tfPlaca, tfEmail, tfCidade, tfNumero, tfCpfCnpj, tfComplemento;
@FXML
private TextField tfBairro, tfValor, tfRua, tfFone, tfValidade;

@FXML TextArea taServico;

@FXML
private ComboBox cbEstado, cbMarca;

@FXML DatePicker dpData;

@FXML ImageView imgRelatorio;
@FXML Label lbAviso;
boolean valida = true;
PessoaDAOImpl pesDao;
Pessoa pessoa;
VeiculoDAOImpl veiDao;
Veiculo veiculo;

private String getDateTime() { 
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
            Date date = new Date(); 
            return dateFormat.format(date);
}
Orcamento orc = new Orcamento();
    @FXML
    private void gerarOrcamento(ActionEvent event){
        orc.setNome(tfNome.getText());
            orc.setCpfCnpj(tfCpfCnpj.getText());
            orc.setEmail(tfEmail.getText());
            orc.setRua(tfRua.getText());
            orc.setCidade(tfCidade.getText());
            orc.setComplemento(tfComplemento.getText());
            orc.setNumero(tfNumero.getText());
            orc.setBairro(tfBairro.getText());
            orc.setDescrição(taServico.getText());
            orc.setPlaca(tfPlaca.getText());
            orc.setModelo(tfModelo.getText());
            orc.setMarca(cbMarca.getValue().toString());
            orc.setEstado(cbEstado.getValue().toString());
            orc.setData(dpData.getValue().toString());
            orc.setValor(Double.parseDouble(tfValor.getText()));
            orc.setFone(tfFone.getText());
            orc.setValidade(tfValidade.getText());
            try {
                gerarPDF(orc);
                Notifications.create().title("Sucesso").position(Pos.CENTER).text("Orçamento gerado com sucesso!").showInformation();
            
                //lbAviso.setVisible(true);
            } catch (DocumentException ex) {
                Logger.getLogger(OrcamentoController.class.getName()).log(Level.SEVERE, null, ex);
                lbAviso.setText("Deu erro");
                lbAviso.setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(OrcamentoController.class.getName()).log(Level.SEVERE, null, ex);
                  lbAviso.setText("Deu erro 2");
                  lbAviso.setVisible(true);
            }
    }
    
    public  void gerarPDF(Orcamento orc) throws FileNotFoundException, DocumentException, IOException{
        Document doc = null;

        OutputStream os = null;
        

     

        try {
            Date data = new Date();
            //cria o documento tamanho A4, margens de 2,54cm

            doc = new Document(PageSize.A4, 72, 72, 72, 72);

       

            //cria a stream de saída

            os = new FileOutputStream("Orçamento "+orc.getNome()+".pdf");

       

            //associa a stream de saída ao

            PdfWriter.getInstance(doc, os);

       

            //abre o documento

            doc.open();

 
            
            //adiciona o texto ao PDF
            Image img = Image.getInstance("C:\\Users\\MarcosVinicius\\Desktop\\TCC\\ProjetoVetra\\src\\icones\\vetra2.jpg");
            img.setAlignment(Element.ALIGN_LEFT);
            doc.add(img);   
            
            Font f = new Font(FontFamily.TIMES_ROMAN, 22, Font.BOLD);
            
            Paragraph p1 = new Paragraph("Vetra Veiculos Transformados.", f);
            doc.add(p1);
            p1.setSpacingAfter(20);
                    
            Paragraph p3 = new Paragraph("Endereço: Rua Coronel Camisão, 17 A");
            doc.add(p3);
            p3.setSpacingBefore(20);
            
            Paragraph p4 = new Paragraph("Bairro: Bairro Amambaí - Campo Grande-MS.");
            doc.add(p4);
            p4.setSpacingBefore(20);         
            
            Paragraph p5 = new Paragraph("Fone: (67)3044 6541 Email: vetra.veiculostransformados@gmail.com ");
            doc.add(p5);
                      
            Paragraph p6 = new Paragraph("___________________________________________________________________");
            doc.add(p6);
            
            //Paragraph p2 = new Paragraph("Data: "+getDateTime());
            //doc.add(p2);
            
            //Paragraph p2_1 = new Paragraph("CPF/CNPJ: "+orc.getCpfCnpj());
            //p2_1.setAlignment(Element.ALIGN_CENTER);
            
            Paragraph p6_1 = new Paragraph("Proposta de Orçamento");
            p6_1.setAlignment(Element.ALIGN_CENTER);
            doc.add(p6_1);
            
            Paragraph p6_3 = new Paragraph("Dados do Cliente");
            doc.add(p6_3);
            
            Paragraph p6_2 = new Paragraph("Data: "+getDateTime()+ "                                                 CPF/CNPJ: "+orc.getCpfCnpj()+"\n"
                    + "Nome: "+orc.getNome()+"                             Email: "+orc.getEmail()+" \n"
                    + "Endereço: "+orc.getRua()+"            Complemento: "+orc.getComplemento());
            p6_2.setSpacingBefore(20);
            p6_2.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
            doc.add(p6_2);
            
            Paragraph p7 = new Paragraph("Bairro: "+orc.getBairro()+"                 Numero: "+orc.getNumero()+"\n"
                    + " Cidade: "+orc.getCidade()+"                         Estado: "+orc.getEstado());
                    
            p7.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
            doc.add(p7);
            
            Paragraph p7_1 = new Paragraph("Telefone: "+orc.getFone());
            doc.add(p7_1);
            
            Paragraph p8 = new Paragraph("___________________________________________________________________");
            doc.add(p8);
            
            Paragraph p9 = new Paragraph("Dados do Serviço");
            p9.setSpacingBefore(10);
            p9.setSpacingAfter(20);
            doc.add(p9);
            
            Paragraph p9_1 = new Paragraph("Veiculo: "+orc.getModelo()+ "                 Marca: "+orc.getMarca());
            p9_1.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
            doc.add(p9_1);
            
            Paragraph p9_2 = new Paragraph("Placa: "+orc.getPlaca());
            doc.add(p9_2);
            
            Paragraph p9_3 = new Paragraph("Descrição do Serviço: "+orc.getDescrição());
            doc.add(p9_3);
            
            Paragraph p10 = new Paragraph("Validade deste Orçamento: "+orc.getValidade()+"   Valor do Serviço: R$ "+orc.getValor());
            p10.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
            doc.add(p10);
            
            Paragraph p10_1 = new Paragraph("Previsão de Término: "+orc.getData());
            p10_1.setSpacingAfter(80);
            doc.add(p10_1);
            
            Paragraph p11 = new Paragraph("______________________________");
            p11.setAlignment(Element.ALIGN_CENTER);
            doc.add(p11);
            
            Paragraph p12 = new Paragraph("Vetra Veiculos Transformados");
            p12.setAlignment(Element.ALIGN_CENTER);
            doc.add(p12);
            
           
                    
        } finally {

            if (doc != null) {

                //fechamento do documento

                doc.close();

            }

            if (os != null) {

               //fechamento da stream de saída

               os.close();

            }

        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        valida = false;
        cbEstado.setValue("Selecione");
        cbMarca.setValue("Selecione");
        
            tfCpfCnpj.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                   pesDao = new PessoaDAOImpl();
                   pessoa = new Pessoa();
                    try {
                        pessoa = pesDao.buscarPorId(tfCpfCnpj.getText());
                    } catch (Exception e) {
                    }
                    if(pessoa != null){
                        //Notifications.create().title("Aviso").position(Pos.CENTER).text("CPF/CNPJ ja cadastrado no sistema!").showInformation();
                        
                        populaDadosCliente(pessoa);
                    }

                }             
            }); 
        
        
        
                tfNome.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                   pesDao = new PessoaDAOImpl();
                   pessoa = new Pessoa();
                    try {
                        pessoa = pesDao.buscarPorId(tfCpfCnpj.getText());
                    } catch (Exception e) {
                    }
                    if(pessoa != null){
                        //Notifications.create().title("Aviso").position(Pos.CENTER).text("CPF/CNPJ ja cadastrado no sistema!").showInformation();
                        
                        populaDadosCliente(pessoa);
                    }

               

                }


            });
            
            tfPlaca.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                   veiDao = new VeiculoDAOImpl();
                   veiculo = new Veiculo();
                    try {
                        veiculo = veiDao.buscarPorPlaca(tfPlaca.getText());
                    } catch (Exception e) {
                    }
                    if(veiculo != null){
                        //Notifications.create().title("Aviso").position(Pos.CENTER).text("CPF/CNPJ ja cadastrado no sistema!").showInformation();
                        
                        populaDadosVeiculo(veiculo);
                    }

                }             
            }); 
            
            cbMarca.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                   veiDao = new VeiculoDAOImpl();
                   veiculo = new Veiculo();
                    try {
                        veiculo = veiDao.buscarPorPlaca(tfPlaca.getText());
                    } catch (Exception e) {
                    }
                    if(veiculo != null){
                        //Notifications.create().title("Aviso").position(Pos.CENTER).text("CPF/CNPJ ja cadastrado no sistema!").showInformation();
                        
                        populaDadosVeiculo(veiculo);
                    }

               

                }


            });
        
        
        
        
        
    }
    
    public void populaDadosCliente(Pessoa pessoa){
        tfNome.setText(pessoa.getNomerazaoSocial());
        tfEmail.setText(pessoa.getEmail());
        if(pessoa.getTelefone() == null){
            tfFone.setText(pessoa.getCelular());
        }else{
            tfFone.setText(pessoa.getTelefone());
        }
        tfRua.setText(pessoa.getEndereco().getRua());
        tfCidade.setText(pessoa.getEndereco().getCidade());
        tfBairro.setText(pessoa.getEndereco().getBairro());
        tfNumero.setText(Integer.toString(pessoa.getEndereco().getNumero()));
        cbEstado.setValue(pessoa.getEndereco().getEstado());
        
        if(pessoa.getEndereco().getComplemento() != null){
            tfComplemento.setText(pessoa.getEndereco().getComplemento());
        }
    }
    
    public void populaDadosVeiculo(Veiculo veiculo){
        tfPlaca.setText(veiculo.getPlaca());
        tfModelo.setText(veiculo.getNomeVeiculo());
        cbMarca.setValue(veiculo.getMarca());
    }
    
}
