/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telascontroller;


import DAO.PessoaDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Busca;
import model.Endereco;
import model.Pessoa;
import org.controlsfx.control.Notifications;
import service.EnderecoService;
import service.PessoaFisicaService;
import service.PessoaJuridicaService;
import service.ServiceException;
import util.ProjetoVetra;

/**
 * FXML Controller class
 *
 * @author Max Willyan
 */
public class CadastroClienteController implements Initializable {
    @FXML private TextField tfNome, tfRg, tfCpfCnpj, tfEmail, tfTelefone, tfCelular, tfRazaoSocial;
    @FXML private TextField tfRua, tfCep, tfBairro, tfCidade, tfNumero, tfComplemento;
    @FXML private ComboBox cbTipoPessoa, cbEstado;
    @FXML private TextArea taAvisos;
    @FXML private Label lbAvisos, lbCpfCnpj, lbNome, lbRg, lbEmail, lbRazaoSocial;
    @FXML private Button btnSalvar, btnCancelar;
    
    
    boolean valida = false;
    private Pessoa verifica;
    PessoaDAOImpl pesDao = new PessoaDAOImpl();
    Pessoa pessoa = new Pessoa();
    Endereco endereco;
    public void salvarPessoaFisica(){
       
        PessoaFisicaService pesfService = new PessoaFisicaService();
       
            
                endereco = new Endereco();
                endereco.setRua(tfRua.getText());
                endereco.setBairro(tfBairro.getText());
                endereco.setCep(tfCep.getText());
                endereco.setCidade(tfCidade.getText());
                endereco.setComplemento(tfComplemento.getText());
                if(tfNumero.getText().isEmpty()){
                    Notifications.create().title("Erro").position(Pos.CENTER).text("Preencha o campo Nº").showError();
                    
                }else{
                     endereco.setNumero(Integer.parseInt(tfNumero.getText()));
                }
               
                endereco.setEstado(cbEstado.getValue().toString());
           
           
            
            
            
            
            EnderecoService endService = new EnderecoService();
            
            
        
        
        
        
            
            pessoa.setNomerazaoSocial(tfNome.getText());
            pessoa.setTipoPessoa(cbTipoPessoa.getValue().toString());
            pessoa.setRg(tfRg.getText());
            pessoa.setEmail(tfEmail.getText());
            pessoa.setTelefone(tfTelefone.getText());
            pessoa.setCelular(tfCelular.getText()); 
            pessoa.setCpfCnpj(tfCpfCnpj.getText());
            pessoa.setEndereco(endereco);
                try {
                    verifica = pesDao.verificaPessoa(tfCpfCnpj.getText());
                    if(verifica == null){
                        endService.salvar(endereco);
                        pesfService.salvar(pessoa);
                    
                        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmacao.setTitle("Confirmação");
                        confirmacao.setHeaderText("Cadastro Realizado com sucesso!");
                        confirmacao.setContentText("Deseja cadastrar um veiculo para o cliente?");

                        ButtonType sim = new ButtonType("Sim");
                        ButtonType nao = new ButtonType("Não");
                        confirmacao.getButtonTypes().setAll(sim, nao);

                        Optional<ButtonType> resultado = confirmacao.showAndWait();
                        if(resultado.get() == sim){


                    //int dialogResult = JOptionPane.showConfirmDialog (null, "Pessoa cadastrada com sucesso! Deseja Cadastrar um novo veiculo?","Warning",JOptionPane.YES_NO_OPTION);
                    //if(dialogResult == JOptionPane.YES_OPTION){

                            try {
                                chamarTelaVeiculo(pessoa);

                            } catch (Exception e) {

                            }
                        }       
                    }else{
                    
                    Notifications.create().title("Erro").position(Pos.CENTER).text("Pessoa ja cadastrada com o CPF/CNPJ"+tfCpfCnpj.getText()).showError();
                    //pessoa = pesDao.buscarPorId(tfCpfCnpj.getText());
                    populaTela(verifica);
                            
                    }
                
            } catch (ServiceException ex) {
                Notifications.create().title("Erro").position(Pos.CENTER).text(ex.getMessage()).showError();
                    
                //lbAvisos.setText(ex.getMessage());
                Logger.getLogger(CadastroClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }      
                
                        
              
                //lbAvisos.setText("Salvo com Sucesso!");
                //lbAvisos.setVisible(true);
                
                
      
    }
    
    public void salvarPessoaJuridica(){
        
        PessoaJuridicaService pesjService = new PessoaJuridicaService();
        if(valida == false){
            endereco = new Endereco();            
            endereco.setRua(tfRua.getText());
            endereco.setBairro(tfBairro.getText());
            endereco.setCep(tfCep.getText());
            endereco.setCidade(tfCidade.getText());
            endereco.setComplemento(tfComplemento.getText());            
            endereco.setEstado(cbEstado.getValue().toString());
            
            if(tfNumero.getText().isEmpty()){
                    Notifications.create().title("Erro").position(Pos.CENTER).text("Preencha o campo Nº").showError();
                    
                }else{
                     endereco.setNumero(Integer.parseInt(tfNumero.getText()));
                }
            
        }
        
        EnderecoService endService = new EnderecoService();
            try {
               verifica = pesDao.verificaPessoa(tfCpfCnpj.getText());
               
                if(verifica == null){
                    endService.salvar(endereco);
                    valida = true;
                }else{
                    Notifications.create().title("Erro").position(Pos.CENTER).text("Pessoa ja cadastrada com o CPF/CNPJ"+tfCpfCnpj.getText()).showError();
                    //pessoa = pesDao.buscarPorId(tfCpfCnpj.getText());
                    populaTela(verifica);
                }
            } catch (ServiceException ex) {
                Notifications.create().title("Erro").position(Pos.CENTER).text(ex.getMessage()).showError();
                valida = false;  
                //lbAvisos.setText(ex.getMessage());
                Logger.getLogger(CadastroClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        if(valida == true){
           pessoa.setCpfCnpj(tfCpfCnpj.getText());
            pessoa.setNomerazaoSocial(tfRazaoSocial.getText());
            pessoa.setNomeMotorista(tfNome.getText());
            pessoa.setTipoPessoa(cbTipoPessoa.getValue().toString());
            pessoa.setRg(tfRg.getText());
            pessoa.setEmail(tfEmail.getText());
            pessoa.setTelefone(tfTelefone.getText());
            pessoa.setCelular(tfCelular.getText()); 
            pessoa.setCpfCnpj(tfCpfCnpj.getText());
            pessoa.setEndereco(endereco);
        
                try {            
                    pesjService.salvar(pessoa);
                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacao.setTitle("Confirmação");
                    confirmacao.setHeaderText("Cadastro Realizado com sucesso!");
                    confirmacao.setContentText("Deseja cadastrar um veiculo para o cliente?");

                    ButtonType sim = new ButtonType("Sim");
                    ButtonType nao = new ButtonType("Não");
                    confirmacao.getButtonTypes().setAll(sim, nao);  
                
                Optional<ButtonType> resultado = confirmacao.showAndWait();
                if(resultado.get() == sim){
                    
                        try {
                            chamarTelaVeiculo(pessoa);
                        
                        } catch (Exception e) {
                          
                        }
                        
                    }
                        //valida = false;
                }catch (ServiceException ex) {
                        Notifications.create().title("Erro").position(Pos.CENTER).text(ex.getMessage()).showError();
                        //lbAvisos.setText(ex.getMessage());
                        //lbAvisos.setVisible(true);
                        //valida = false;
                        Logger.getLogger(CadastroClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
                    
        }
    }
    
    @FXML
    public void salvar(ActionEvent event) throws IOException{
        Pessoa pessoa = new Pessoa();
        Endereco endereco = new Endereco();
        PessoaFisicaService pesfService = new PessoaFisicaService();
        PessoaJuridicaService pesjService = new PessoaJuridicaService();     
        
        
        
        //pegando da tela os dados da pessoa
        
        
           
        if(cbTipoPessoa.getValue().toString().equals("Juridica")){
            salvarPessoaJuridica();
        }else if(cbTipoPessoa.getValue().toString().equals("Fisica")){
            salvarPessoaFisica();
        }    
            
    }
    
    @FXML
    private void cancelar(ActionEvent event){
      Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Cancelar!");
        confirmacao.setContentText("Tem certeza que deseja cancelar?");
                
        ButtonType sim = new ButtonType("Sim");
        ButtonType nao = new ButtonType("Não");
        confirmacao.getButtonTypes().setAll(sim, nao);
                
        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if(resultado.get() == sim){
           
            cbTipoPessoa.setValue("Selecione");
            tfCpfCnpj.setText("");       
            tfRazaoSocial.setText("");        
            tfNome.setText("");      
            tfRg.setText("");       
            tfEmail.setText("");        
            tfTelefone.setText("");        
            tfCelular.setText("");
            tfCep.setText("");       
            tfRua.setText("");      
            tfComplemento.setText("");
            cbEstado.setValue("Selecione");
            tfCidade.setText("");
            tfBairro.setText("");   
            tfNumero.setText("");
                    
        }
      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipoPessoa.setValue("Selecione");
        cbEstado.setValue("Selecione");
        btnCancelar.setId("#meubotao");
        cbTipoPessoa.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(cbTipoPessoa.getValue().toString().equals("Fisica")){
                    lbCpfCnpj.setText("CPF*");
                    lbCpfCnpj.setLayoutX(519);
                    lbNome.setText("Nome*");
                    tfNome.setLayoutX(243);
                    lbRg.setLayoutX(519);
                    lbRg.setLayoutY(212);
                    tfRg.setLayoutX(581);
                    tfRg.setLayoutY(208);
                    //lbEmail.setLayoutX(779);
                   // tfEmail.setLayoutX(212);
                    tfRazaoSocial.setEditable(false);
                    tfRazaoSocial.setVisible(false);
                    lbRazaoSocial.setVisible(false);
                }else if (cbTipoPessoa.getValue().toString().equals("Juridica")){
                    lbCpfCnpj.setText("CNPJ*");
                    lbCpfCnpj.setLayoutX(519);
                    lbNome.setText("Motorista*");
                    tfNome.setLayoutX(243);
                    tfRazaoSocial.setEditable(true);
                    tfRazaoSocial.setVisible(true);
                    lbRazaoSocial.setVisible(true);
                    /*
                    tfNome.setLayoutX(243);
                    lbRg.setLayoutX(416);
                    tfRg.setLayoutX(444);
                    lbEmail.setLayoutX(621);
                    tfEmail.setLayoutX(668);
                    tfRazaoSocial.setEditable(true);
                            */
                }
            }
        });
        
        
       
        
        
    }    

    private void populaTela(Pessoa pessoa) {
        tfCpfCnpj.setText(pessoa.getCpfCnpj());
        tfEmail.setText(pessoa.getEmail());
        tfRg.setText(pessoa.getRg());
        tfTelefone.setText(pessoa.getTelefone());
        tfCelular.setText(pessoa.getCelular());
        cbTipoPessoa.setValue(pessoa.getTipoPessoa());
        tfRua.setText(pessoa.getEndereco().getRua());
        tfBairro.setText(pessoa.getEndereco().getBairro());
        tfNumero.setText(Integer.toString(pessoa.getEndereco().getNumero()));
        tfCidade.setText(pessoa.getEndereco().getCidade());
        tfCep.setText(pessoa.getEndereco().getCep());
        cbEstado.setValue(pessoa.getEndereco().getEstado());
        if(pessoa.getTipoPessoa().equals("Fisica")){
            tfNome.setText(pessoa.getNomerazaoSocial());
            tfRazaoSocial.setEditable(false);
        }else if(pessoa.getTipoPessoa().equals("Juridica")){
            tfRazaoSocial.setText(pessoa.getNomerazaoSocial());
            tfNome.setText(pessoa.getNomeMotorista());
        }
        
    }

    private void chamarTelaVeiculo(Pessoa pessoa) {
        try {
                Parent root;
                
                root = FXMLLoader.load(getClass().getResource("/telas/CadastroVeiculoView.fxml"));
                
                
                Stage stage = new Stage();
                stage.setTitle("Cadastro Veiculo");
                
                Scene scene = new Scene(root);
                
                stage.setScene(scene);
                scene.getStylesheets().add(ProjetoVetra.class.getResource("/layout/cadastroClienteController.css").toExternalForm());
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
