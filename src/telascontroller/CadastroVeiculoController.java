/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telascontroller;

import DAO.BuscaDAO;
import DAO.PessoaDAOImpl;
import DAO.VeiculoDAOImpl;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Busca;
import model.Pessoa;
import model.Servico;
import model.Veiculo;
import org.controlsfx.control.Notifications;
import service.ServiceException;
import service.ServicoService;
import service.VeiculoService;
import static telascontroller.AlterarVeiculoController.toLocalDate;

/**
 * FXML Controller class
 *
 * @author Max Willyan
 */
public class CadastroVeiculoController implements Initializable {
    @FXML private TextField tfCpf, tfCpfCnpj, tfModelo, tfPlaca, tfNrenavam, tfNchassi, tfProprietario;
    @FXML private TextField tfMotorista, tfValorServico, tfCnpj, tfNomeBusca, tfNome, tfEmail;
    @FXML private DatePicker dpEntrada, dpTermino;
    @FXML private ComboBox cbMarca;
    @FXML private TextArea taServico, taAvisos;
    @FXML private Button btOk, btOk2, btFecharTabela;
    public List<Pessoa> resultado;
    @FXML private TableView<Pessoa> tabelaBusca;
    @FXML private TableColumn colunaNome, colunaCpfCnpj, colunaEmail, colunaRg, colunaTelefone;
    @FXML AnchorPane anchorTabela;
    @FXML GridPane gridSelect;
    @FXML CheckBox chbSelect;
    Pessoa linhaBuscada = new Pessoa();        
   
    Veiculo veiculo = new Veiculo();
    Veiculo validaPlaca = new Veiculo();
    VeiculoDAOImpl veiDAO = new VeiculoDAOImpl();
    Servico serv = new Servico();
    Pessoa buscaPessoa= new Pessoa();
    @FXML
    private void buscar(ActionEvent event){
        if(!tfCpf.getText().isEmpty() && tfNomeBusca.getText().isEmpty() && tfCnpj.getText().isEmpty()){
            PessoaDAOImpl bus = new PessoaDAOImpl();
            //Pessoa bus2= new Pessoa();
            buscaPessoa = bus.buscarPorId(tfCpf.getText());
            if(buscaPessoa != null){
                tfNome.setText(buscaPessoa.getNomerazaoSocial());
                tfCpfCnpj.setText(buscaPessoa.getCpfCnpj());
                tfEmail.setText(buscaPessoa.getEmail());            
            }else{
                Notifications.create().title("Erro").position(Pos.CENTER).text("Cliente não cadastrado!").showInformation();
                
            }
        }else if(!tfCnpj.getText().isEmpty() && tfCpf.getText().isEmpty() && tfNomeBusca.getText().isEmpty()){
            PessoaDAOImpl bus = new PessoaDAOImpl();
            
            buscaPessoa = bus.buscarPorId(tfCnpj.getText());
            if(buscaPessoa != null){
                tfNome.setText(buscaPessoa.getNomerazaoSocial());
                tfCpfCnpj.setText(buscaPessoa.getCpfCnpj());
                tfEmail.setText(buscaPessoa.getEmail());            
            }else{
                Notifications.create().title("Erro").position(Pos.CENTER).text("Cliente não cadastrado!").showInformation();
                
            }
        }else if(!tfNomeBusca.getText().isEmpty() && tfCpf.getText().isEmpty() &&  tfCnpj.getText().isEmpty()){
            PessoaDAOImpl bus = new PessoaDAOImpl();
            
            //bus2= bus.buscarPorNome(tfNomeBusca.getText()); 
       
    
            
            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomerazaoSocial"));
            colunaCpfCnpj.setCellValueFactory(new PropertyValueFactory<>("cpfCnpj"));
            colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colunaRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
            colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone")); 
            try {
                tabelaBusca.getItems().setAll(bus.buscarPorNome(tfNomeBusca.getText()));
            } catch (Exception e) {
                Notifications.create().title("Erro").position(Pos.CENTER).text("Nenhum cliente encontrado").showInformation();
                
            }
            
            anchorTabela.setVisible(true);
            tabelaBusca.setVisible(true);            
            btFecharTabela.setVisible(true);
            
            tabelaBusca.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event){
                    linhaBuscada = tabelaBusca.getSelectionModel().getSelectedItem();
        
                    tfNome.setText(linhaBuscada.getNomerazaoSocial());
                    tfCpfCnpj.setText(linhaBuscada.getCpfCnpj());
                    tfEmail.setText(linhaBuscada.getEmail());
                    buscaPessoa = new Pessoa();
                    buscaPessoa = linhaBuscada;
                }
            });
            
            /*
            if(bus2 != null){
                tfNome.setText(bus2.getNomeRazaoSocial());
                tfCpfCnpj.setText(bus2.getCpfCnpj());
                tfEmail.setText(bus2.getEmail());            
            }else{
                taAvisos.setText("Cliente não cadastrado!");
                taAvisos.setVisible(true);
                btOk.setVisible(true);
            } */
                    
        }
    }
   
    
        
   
  
    @FXML
    private void ok(ActionEvent event){
        taAvisos.setText("");
        taAvisos.setVisible(false);
        btOk.setVisible(false);
    }
    
    @FXML
    private void salvar(ActionEvent event){ 
        Pessoa pes = new Pessoa();
        
        if(dpEntrada.getValue() == null  ){
           Notifications.create().title("Erro").position(Pos.CENTER).text("O campo data de entrada  é obrigatório").showError();
                 
        }
        if(dpTermino.getValue() == null){
            Notifications.create().title("Erro").position(Pos.CENTER).text("O campo data termino é obrigatório").showError();
             
        }
         Date dataEntrada = Date.from(dpEntrada.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
         Date dataSaida = Date.from(dpTermino.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
          
        
            
         
         if(!tfCpfCnpj.getText().isEmpty()){
            pes.setCpfCnpj(tfCpfCnpj.getText());
            veiculo.setMarca(cbMarca.getValue().toString());
            veiculo.setPlaca(tfPlaca.getText());
            veiculo.setPessoa(buscaPessoa);
            veiculo.setNomeVeiculo(tfModelo.getText());
            veiculo.setNomeProprietario(tfProprietario.getText());            
            veiculo.setDataEntrada(dataEntrada);
            
            if(!tfNchassi.getText().isEmpty()){
                veiculo.setChassi(Long.parseLong(tfNchassi.getText()));
                
            }else{
                Notifications.create().title("Erro").position(Pos.CENTER).text("O campo Chassi é obrigatório").showError();
                
            }
            
            if(!tfNrenavam.getText().isEmpty()){
               veiculo.setRenavam(Long.parseLong(tfNrenavam.getText())); 
            }else{
                Notifications.create().title("Erro").position(Pos.CENTER).text("O campo Chassi é obrigatório").showError();
                
            }
            
           
            //pegando dados do serviço
            
            serv.setCpfCnpj(tfCpfCnpj.getText());
            serv.setDataEntrada(dataEntrada);
            serv.setDataTermino(dataSaida);
            serv.setDescricaoServico(taServico.getText());
            serv.setPlaca(tfPlaca.getText());
            serv.setValor(Double.parseDouble(tfValorServico.getText()));
            serv.setStatus("Em andamento");
            serv.setVeiculo(veiculo);
            serv.setNomeRazaoSocial(buscaPessoa.getNomerazaoSocial());
            ServicoService servService = new ServicoService();
            VeiculoService veicService = new VeiculoService();
            try {
               validaPlaca = veiDAO.validaVeiculo(tfPlaca.getText());
               if(validaPlaca == null){
                   veicService.salvar(veiculo);
                   servService.salvar(serv);
                   Notifications.create().title("Sucesso").position(Pos.CENTER).text("Veiculo e serviço salvos com sucesso!").showInformation();
                
               }else{
                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmacao.setTitle("Confirmação");
                    confirmacao.setHeaderText("Veiculo ja cadastrado.!");
                    confirmacao.setContentText("Deseja Cadastrar um novo serviço para o veiculo?");

                    ButtonType sim = new ButtonType("Sim");
                    ButtonType nao = new ButtonType("Não");
                    confirmacao.getButtonTypes().setAll(sim, nao);
                
                    Optional<ButtonType> resultado = confirmacao.showAndWait();
                    if(resultado.get() == sim){
                  
                        populaTela(validaPlaca);
                        try {
                            serv.setVeiculo(validaPlaca);
                            servService.salvar(serv);
                            Notifications.create().title("Sucesso").position(Pos.CENTER).text("Veiculo e serviço salvos com sucesso!").showInformation();
                                
                      } catch (Exception e) {
                          
                      }
                  }
               }
               
               
            } catch (ServiceException ex) {
               Notifications.create().title("Erro").position(Pos.CENTER).text(ex.getMessage()).showInformation();
               Logger.getLogger(CadastroVeiculoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                
        }else{
            Notifications.create().title("Erro").position(Pos.CENTER).text("Realize a busca de um cliente antes de salvar!").showError();
           
        }
            
            
    }
    
    @FXML
    private void fecharTabela(ActionEvent event){
        tabelaBusca.setVisible(false);
        
        btFecharTabela.setVisible(false);
        anchorTabela.setVisible(false);
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbMarca.setValue("Selecione");
    }    

    private void populaTela(Veiculo validaPlaca) {
        tfPlaca.setText(validaPlaca.getPlaca());
        tfModelo.setText(validaPlaca.getNomeVeiculo());
        cbMarca.setValue(validaPlaca.getMarca());
        tfNchassi.setText(Long.toString(validaPlaca.getChassi()));
        tfNrenavam.setText(Long.toString(validaPlaca.getRenavam()));
        tfProprietario.setText(validaPlaca.getNomeProprietario());
        LocalDate de = toLocalDate(validaPlaca.getDataEntrada());           
        dpEntrada.setValue(de);
        
        tfPlaca.setEditable(false);
        tfModelo.setEditable(false);
        cbMarca.setEditable(false);
        tfNchassi.setEditable(false);
        tfNrenavam.setEditable(false);
        tfProprietario.setEditable(false);
        dpEntrada.setEditable(false);
        
        
    }
    
    @FXML
    private void cancelar(ActionEvent event){
        tfPlaca.setText("");
        tfModelo.setText("");
        cbMarca.setValue("Seleione");
        tfNchassi.setText("");
        tfNrenavam.setText("");
        tfProprietario.setText("");                   
        dpEntrada.setValue(null);
        dpTermino.setValue(null);
        taServico.setText(""); 
        tfValorServico.setText("");
        tfCnpj.setText("");
        tfCpf.setText("");
        tfNome.setText("");
        tfNomeBusca.setText("");
        tfEmail.setText("");
        tfCpfCnpj.setText("");
              
        
    }
    
}
