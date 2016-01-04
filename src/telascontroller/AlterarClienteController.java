/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package telascontroller;

import DAO.EnderecoDAOImpl;
import DAO.PessoaDAOImpl;
import com.sun.javafx.scene.SceneUtils;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Endereco;
import model.Pessoa;
import org.controlsfx.control.Notifications;
import service.EnderecoService;
import service.PessoaFisicaService;
import service.PessoaJuridicaService;
import service.ServiceException;

/**
 * FXML Controller class
 *
 * @author MarcosVinicius
 */
public class AlterarClienteController implements Initializable{

    
    @FXML private TextField tfNome, tfRg, tfCpfCnpj, tfEmail, tfTelefone, tfCelular, tfRazaoSocial, tfCpf;
    @FXML private TextField tfRua, tfCep, tfBairro, tfCidade, tfNumero, tfComplemento, tfCnpj, tfNomeBusca;
    @FXML private ComboBox cbTipoPessoa, cbEstado;
    @FXML private TextArea taAvisos;
    @FXML private Label lbIdPessoa, lbIdEndereco, lbCpfCnpj, lbNome, lbRg, lbEmail, lbRazaoSocial;
    public List<Pessoa> resultado;
    @FXML private TableView<Pessoa> tabelaBusca;
    @FXML private TableColumn colunaNome, colunaCpfCnpj, colunaEmail, colunaRg, colunaTelefone;
    @FXML AnchorPane anchorTabela;
    @FXML private Button btOk, btFecharTabela;
    Pessoa linhaBuscada = new Pessoa(); 
    
    Pessoa busca = new Pessoa();
    int idPessoa;
    Integer idEndereco;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipoPessoa.setValue("Selecione");
        cbEstado.setValue("Selecione");
        
        cbTipoPessoa.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(cbTipoPessoa.getValue().toString().equals("Fisica")){
                    lbCpfCnpj.setText("CPF");
                    //lbCpfCnpj.setLayoutX(519);
                    lbNome.setText("Nome");
                    //tfNome.setLayoutX(243);
                    //lbRg.setLayoutX(519);
                    //lbRg.setLayoutY(212);
                    //tfRg.setLayoutX(581);
                    //tfRg.setLayoutY(208);
                    //lbEmail.setLayoutX(779);
                   // tfEmail.setLayoutX(212);
                    tfRazaoSocial.setEditable(false);
                    tfRazaoSocial.setVisible(false);
                    lbRazaoSocial.setVisible(false);
                }else if (cbTipoPessoa.getValue().toString().equals("Juridica")){
                    lbCpfCnpj.setText("CNPJ");
                    //lbCpfCnpj.setLayoutX(519);
                    lbNome.setText("Motorista");
                    //tfNome.setLayoutX(243);
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
    
    @FXML
    private void fecharTabela(ActionEvent event){
        anchorTabela.setVisible(false);
        tabelaBusca.setVisible(false);        
        btFecharTabela.setVisible(false);
        
    }
    
    @FXML
    private void buscar(ActionEvent event){
        if(!tfCpf.getText().isEmpty() && tfNomeBusca.getText().isEmpty() && tfCnpj.getText().isEmpty()){
            
            PessoaDAOImpl bus= new PessoaDAOImpl();
            
            busca = bus.buscarPorId(tfCpf.getText());
            
            Endereco end = new Endereco();            
            EnderecoDAOImpl endDao = new EnderecoDAOImpl();
            //end = endDao.buscarPorId(busca.getEndereco().getIdEndereco());
            
            if(busca != null){
                if(busca.getTipoPessoa().equals("Fisica")){
                    tfNome.setText(busca.getNomerazaoSocial());
                    tfCpfCnpj.setText(busca.getCpfCnpj());
                    tfEmail.setText(busca.getEmail());
                    tfRg.setText(busca.getRg());
                    tfTelefone.setText(busca.getTelefone());
                    tfCelular.setText(busca.getCelular());
                    cbTipoPessoa.setValue(busca.getTipoPessoa());
                    lbIdPessoa.setText(Integer.toString(busca.getIdPessoa()));
                    //dados do endereço
                    System.out.println("Comp: "+end.getComplemento());
                    tfRua.setText(busca.getEndereco().getRua());
                    tfBairro.setText(busca.getEndereco().getBairro());
                    tfCidade.setText(busca.getEndereco().getCidade());
                    tfCep.setText(busca.getEndereco().getCep());
                    tfNumero.setText(Integer.toString(busca.getEndereco().getNumero()));
                    cbEstado.setValue(busca.getEndereco().getEstado());
                    lbIdEndereco.setText(Integer.toString(busca.getEndereco().getIdEndereco()));
                    
                }else if(busca.getTipoPessoa().equals("Juridica")){
                    tfRazaoSocial.setText(busca.getNomerazaoSocial());
                    tfCpfCnpj.setText(busca.getCpfCnpj());
                    tfEmail.setText(busca.getEmail());
                    tfRg.setText(busca.getRg());
                    tfTelefone.setText(busca.getTelefone());
                    tfCelular.setText(busca.getCelular());
                    cbTipoPessoa.setValue(busca.getTipoPessoa());
                    lbIdPessoa.setText(Long.toString(busca.getIdPessoa()));
                    //dados do endereço
                    
                    tfRua.setText(busca.getEndereco().getRua());
                    tfBairro.setText(busca.getEndereco().getBairro());
                    tfCidade.setText(busca.getEndereco().getCidade());
                    tfCep.setText(busca.getEndereco().getCep());
                    tfNumero.setText(Integer.toString(busca.getEndereco().getNumero()));
                    cbEstado.setValue(busca.getEndereco().getEstado());
                    lbIdEndereco.setText(Integer.toString(busca.getEndereco().getIdEndereco()));
                  
                    
                }
                           
            }else{
                //taAvisos.setText("Cliente não cadastrado!");
                //taAvisos.setVisible(true);
                //btOk.setVisible(true);
            }
        }else if(!tfCnpj.getText().isEmpty() && tfCpf.getText().isEmpty() && tfNomeBusca.getText().isEmpty()){
            PessoaDAOImpl bus= new PessoaDAOImpl();
            
            busca = bus.buscarPorId(tfCnpj.getText());
            
            Endereco end = new Endereco();            
            EnderecoDAOImpl endDao = new EnderecoDAOImpl();
            end = endDao.buscarPorId(busca.getEndereco().getIdEndereco());
            
            if(busca != null){
                if(busca.getTipoPessoa().equals("Fisica")){
                    tfNome.setText(busca.getNomerazaoSocial());
                    tfCpfCnpj.setText(busca.getCpfCnpj());
                    tfEmail.setText(busca.getEmail());
                    tfRg.setText(busca.getRg());
                    tfTelefone.setText(busca.getTelefone());
                    tfCelular.setText(busca.getCelular());
                    cbTipoPessoa.setValue(busca.getTipoPessoa());
                    lbIdPessoa.setText(Long.toString(busca.getIdPessoa()));
                    //dados do endereço
                    System.out.println("Comp: "+end.getComplemento());
                    tfRua.setText(end.getRua());
                    tfBairro.setText(end.getBairro());
                    tfCidade.setText(end.getCidade());
                    tfCep.setText(end.getCep());
                    tfNumero.setText(Integer.toString(end.getNumero()));
                    cbEstado.setValue(end.getEstado());
                    lbIdEndereco.setText(Long.toString(end.getIdEndereco()));
                    
                    }else if(busca.getTipoPessoa().equals("Juridica")){
                        tfRazaoSocial.setText(busca.getNomerazaoSocial());
                        tfCpfCnpj.setText(busca.getCpfCnpj());
                        tfEmail.setText(busca.getEmail());
                        tfRg.setText(busca.getRg());
                        tfTelefone.setText(busca.getTelefone());
                        tfCelular.setText(busca.getCelular());
                        cbTipoPessoa.setValue(busca.getTipoPessoa());
                        lbIdPessoa.setText(Integer.toString(busca.getIdPessoa()));
                        //dados do endereço

                        tfRua.setText(busca.getEndereco().getRua());
                        tfBairro.setText(busca.getEndereco().getBairro());
                        tfCidade.setText(busca.getEndereco().getCidade());
                        tfCep.setText(busca.getEndereco().getCep());
                        tfNumero.setText(Integer.toString(busca.getEndereco().getNumero()));
                        cbEstado.setValue(busca.getEndereco().getEstado());
                        lbIdEndereco.setText(Integer.toString(busca.getEndereco().getIdEndereco()));

                    }
                }
        }else if(!tfNomeBusca.getText().isEmpty() && tfCpf.getText().isEmpty() &&  tfCnpj.getText().isEmpty()){
                    PessoaDAOImpl bus= new PessoaDAOImpl();
                    Pessoa bus2= new Pessoa();
                    //bus2= bus.buscarPorNome(tfNomeBusca.getText());       


                    colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomerazaoSocial"));
                    colunaCpfCnpj.setCellValueFactory(new PropertyValueFactory<>("cpfCnpj"));
                    colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
                    colunaRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
                    colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));                      
                    tabelaBusca.getItems().setAll(bus.buscarPorNome(tfNomeBusca.getText()));
                    anchorTabela.setVisible(true);
                    tabelaBusca.setVisible(true);            
                    btFecharTabela.setVisible(true);
            
                    tabelaBusca.setOnMousePressed(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent event){
                            linhaBuscada = tabelaBusca.getSelectionModel().getSelectedItem();

                            if(linhaBuscada.getTipoPessoa().equals("Fisica")){
                                tfNome.setText(linhaBuscada.getNomerazaoSocial());
                                tfCpfCnpj.setText(linhaBuscada.getCpfCnpj());
                                tfEmail.setText(linhaBuscada.getEmail());
                                tfRg.setText(linhaBuscada.getRg());
                                tfTelefone.setText(linhaBuscada.getTelefone());
                                tfCelular.setText(linhaBuscada.getCelular());
                                cbTipoPessoa.setValue(linhaBuscada.getTipoPessoa());
                                lbIdPessoa.setText(Integer.toString(busca.getIdPessoa()));
                    
                                //dados do endereço
                                //System.out.println("Comp: "+linhaBuscada.getEndereco().getComplemento());
                                tfRua.setText(linhaBuscada.getEndereco().getRua());
                                tfBairro.setText(linhaBuscada.getEndereco().getBairro());
                                tfCidade.setText(linhaBuscada.getEndereco().getCidade());
                                tfCep.setText(linhaBuscada.getEndereco().getCep());
                                tfNumero.setText(Integer.toString(linhaBuscada.getEndereco().getNumero()));
                                cbEstado.setValue(linhaBuscada.getEndereco().getEstado());
                                lbIdEndereco.setText(Integer.toString(linhaBuscada.getEndereco().getIdEndereco()));
                        
                            }
                        }
                    });
                       
            }  
        
    }
    @FXML
    private void salvar(ActionEvent event){
        if(cbTipoPessoa.getValue().toString().equals("Juridica")){
            alterarPessoaJuridica();
        }else if(cbTipoPessoa.getValue().toString().equals("Fisica")){
            alterarPessoaFisica();
        } 
        
        
    }
    
    public void alterarPessoaFisica(){
        Pessoa pessoa = new Pessoa();
        PessoaFisicaService pesfService = new PessoaFisicaService();
        boolean valida = false;
            Endereco endereco = new Endereco();
            
            endereco.setRua(tfRua.getText());
            endereco.setBairro(tfBairro.getText());
            endereco.setCep(tfCep.getText());
            endereco.setCidade(tfCidade.getText());
            endereco.setComplemento(tfComplemento.getText());
            try {
                endereco.setNumero(Integer.parseInt(tfNumero.getText()));
            } catch (Exception e) {
            }
            
            endereco.setEstado(cbEstado.getValue().toString());
            endereco.setIdEndereco(Integer.parseInt(lbIdEndereco.getText()));

            EnderecoService endService = new EnderecoService();
            try {
                endService.salvar(endereco);
                valida = true;
            } catch (ServiceException ex) {
                Notifications.create().title("Erro").position(Pos.CENTER).text(ex.getMessage()).showError();

                Logger.getLogger(AlterarClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        
        
        if(valida == true){
            pessoa.setIdPessoa(Integer.parseInt(lbIdPessoa.getText()));
            pessoa.setNomerazaoSocial(tfNome.getText());
            pessoa.setTipoPessoa(cbTipoPessoa.getValue().toString());
            pessoa.setRg(tfRg.getText());
            pessoa.setEmail(tfEmail.getText());
            pessoa.setTelefone(tfTelefone.getText());
            pessoa.setCelular(tfCelular.getText()); 
            pessoa.setCpfCnpj(tfCpfCnpj.getText());
            pessoa.setEndereco(endereco);
            try {            
                pesfService.salvar(pessoa);
                Notifications.create().title("Sucesso").position(Pos.CENTER).text("Alterado com Sucesso").showInformation();

                
                }catch (ServiceException ex) {
                    Notifications.create().title("Erro").position(Pos.CENTER).text(ex.getMessage()).showError();

                    valida = false;
                    Logger.getLogger(AlterarClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
           
        }
    }
    
    public void alterarPessoaJuridica(){
        Pessoa pessoa = new Pessoa();
        PessoaFisicaService pesfService = new PessoaFisicaService();
        boolean valida = false;
            Endereco endereco = new Endereco();
            
            endereco.setRua(tfRua.getText());
            endereco.setBairro(tfBairro.getText());
            endereco.setCep(tfCep.getText());
            endereco.setCidade(tfCidade.getText());
            endereco.setComplemento(tfComplemento.getText());
            try {
                endereco.setNumero(Integer.parseInt(tfNumero.getText()));
            } catch (Exception e) {
            }
            
            endereco.setEstado(cbEstado.getValue().toString());
            endereco.setIdEndereco(Integer.parseInt(lbIdEndereco.getText()));

            EnderecoService endService = new EnderecoService();
            try {
                endService.salvar(endereco);
                valida = true;
            } catch (ServiceException ex) {
                Notifications.create().title("Erro").position(Pos.CENTER).text(ex.getMessage()).showError();

                Logger.getLogger(AlterarClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        
        
        if(valida == true){
            pessoa.setIdPessoa(Integer.parseInt(lbIdPessoa.getText()));
            pessoa.setNomerazaoSocial(tfRazaoSocial.getText());
            pessoa.setTipoPessoa(cbTipoPessoa.getValue().toString());
            pessoa.setNomeMotorista(tfNome.getText());
            pessoa.setRg(tfRg.getText());
            pessoa.setEmail(tfEmail.getText());
            pessoa.setTelefone(tfTelefone.getText());
            pessoa.setCelular(tfCelular.getText()); 
            pessoa.setCpfCnpj(tfCpfCnpj.getText());
            pessoa.setEndereco(endereco);
            try {            
                pesfService.salvar(pessoa);
                Notifications.create().title("Sucesso").position(Pos.CENTER).text("Alterado com Sucesso").showInformation();

                
                }catch (ServiceException ex) {
                    Notifications.create().title("Erro").position(Pos.CENTER).text(ex.getMessage()).showError();

                    valida = false;
                    Logger.getLogger(AlterarClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
           
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
            tfCpf.setText("");
            tfCnpj.setText("");
            tfNomeBusca.setText("");
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

   
}
