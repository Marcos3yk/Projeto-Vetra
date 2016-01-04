/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package telascontroller;

import DAO.ContaDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Conta;
import org.controlsfx.control.Notifications;
import service.ContaService;
import service.ServiceException;
import static telascontroller.AlterarVeiculoController.toLocalDate;

/**
 * FXML Controller class
 *
 * @author MarcosVinicius
 */
public class ContasController implements Initializable {
    @FXML
    private TextField tfConta, tfValor, tfBanco,tfContaBusca, tfValorBusca, tfBancoBusca;
    @FXML
    private DatePicker dpInicio, dpFinal, dpVencimento, dpVencimentoBusca;
    @FXML
    private ComboBox cbSituacao, cbSituacaoBusca, cbSituacaoResultado;
    @FXML
    private TableView<Conta> tabelaConta;
    @FXML
    private TableColumn cConta, cVencimento, cBanco, cStatus, cValor;
    @FXML
    private ImageView imgBuscar;
    @FXML
    private Button btnFecharTabela;
    @FXML
    private AnchorPane anchorTabela;
    
    public List<Conta> resultado;
    
    Date dataVencimento;
    Conta conta = new Conta();
    Conta linhaBuscada = new Conta();
    ContaService contaService = new ContaService();
    ContaDAOImpl conDao = new ContaDAOImpl();
    Integer idConta;
    @FXML
    private void salvar(ActionEvent event){
        if(dpVencimento.getValue()!= null){
            dataVencimento = Date.from(dpVencimento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        conta.setBanco(tfBanco.getText());
        conta.setConta(tfConta.getText());
        conta.setValor(Double.parseDouble(tfValor.getText()));
        conta.setVencimento(dataVencimento);
        conta.setSituacao(cbSituacao.getValue().toString());
        
        try {
            contaService.salvar(conta);
            Notifications.create().title("Sucesso").position(Pos.CENTER).text("Conta salva com sucesso!").showInformation();
        } catch (ServiceException ex) {
           Notifications.create().title("Erro").position(Pos.CENTER).text(ex.getMessage()).showInformation();
        
        }
    }
    
    @FXML
    private void editar(ActionEvent event){
        Date vencimento = Date.from(dpVencimentoBusca.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                
        conta.setIdConta(idConta);
        conta.setConta(tfContaBusca.getText());
        conta.setBanco(tfBancoBusca.getText());
        conta.setSituacao(cbSituacaoResultado.getValue().toString());
        conta.setVencimento(vencimento);
        conta.setValor(Double.parseDouble(tfValorBusca.getText()));
        
        try{
            contaService.editar(conta);
            Notifications.create().title("Sucesso").position(Pos.CENTER).text("Conta salva com sucesso!").showInformation();
                        
            
        }catch(ServiceException ex){
            Notifications.create().title("Erro").position(Pos.CENTER).text(ex.getMessage()).showInformation();
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbSituacao.setValue("Selecione");
        cbSituacaoBusca.setValue("Selecione");        
        
        
        imgBuscar.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event){
           if(!cbSituacaoBusca.getValue().toString().equals("Selecione") && dpInicio.getValue() == null && dpFinal.getValue() == null){
               try {
                  resultado = conDao.buscaPorSituacao(cbSituacaoBusca.getValue().toString());
                  populaTabela(resultado);
                  cbSituacaoBusca.setValue("Selecione");
               } catch (Exception e) {
                   
               }
  
           }
        }

           
        });
    }    
    private void populaTabela(List<Conta> resultado) {
        cConta.setCellValueFactory(new PropertyValueFactory<>("conta"));           
        cBanco.setCellValueFactory(new PropertyValueFactory<>("banco"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("situacao"));                
        cValor.setCellValueFactory(new PropertyValueFactory<>("valor"));                
        cVencimento.setCellValueFactory(new PropertyValueFactory<>("vencimento"));
        tabelaConta.getItems().setAll(resultado);
        //mostrando a tabela
        anchorTabela.setVisible(true);
        tabelaConta.setVisible(true);
        btnFecharTabela.setVisible(true);
        
                
        tabelaConta.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
            linhaBuscada =  tabelaConta.getSelectionModel().getSelectedItem();                            
            tfBancoBusca.setText(linhaBuscada.getBanco());      
            idConta = linhaBuscada.getIdConta();
            tfContaBusca.setText(linhaBuscada.getConta());
            tfValorBusca.setText(Double.toString(linhaBuscada.getValor()));
            cbSituacaoResultado.setValue(linhaBuscada.getSituacao());            
            LocalDate de = toLocalDate(linhaBuscada.getVencimento());           
            dpVencimentoBusca.setValue(de);

                                
            }
                        
        });
    }
    @FXML
    private void fecharTabela(ActionEvent event){
        btnFecharTabela.setVisible(false);
        tabelaConta.getItems().clear();
        tabelaConta.setVisible(false);
        anchorTabela.setVisible(false);
    }
    
    
    @FXML
    private void cancelar(ActionEvent event){
        limparCampos();
    }
    
    @FXML
    private void cancelarEditar(ActionEvent event){
        limparCampos();
    }
    
    public void limparCampos(){
        tfConta.setText("");
        tfValor.setText("");
        tfBanco.setText("");
        tfContaBusca.setText("");
        tfValorBusca.setText("");
        tfBancoBusca.setText("");
        dpVencimento.setValue(null);
        cbSituacao.setValue("Selecione");
        cbSituacaoBusca.setValue("Selecione");
        dpInicio.setValue(null);
        dpFinal.setValue(null);
        dpVencimentoBusca.setValue(null);
        cbSituacaoResultado.setValue("Selecione");
        
    }
    
    
}
