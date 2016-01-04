/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package telascontroller;

import DAO.EstoqueDAOImpl;
import DAO.FornecedorDAOImpl;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import model.Estoque;
import model.Fornecedor;
import org.controlsfx.control.Notifications;
import service.EstoqueService;
import service.FornecedorService;
import static telascontroller.AlterarVeiculoController.toLocalDate;

/**
 * FXML Controller class
 *
 * @author MarcosVinicius
 */
public class EstoqueController implements Initializable {
    @FXML private TextField tfPeca, tfFornecedor, tfQtde, tfQtdeSai,tfQtdeEntra, tfFornecedor2, tfCnpj, tfEmail, tfNomeFornecedor;
    @FXML private TextField tfNomeBusca, tfCnpjBusca, tfPecaBusca, tfQtdeInicial, tfQtdeFinal;
    @FXML private TableView <Estoque> tabelaEstoque;
    @FXML private TableView <Fornecedor> tabelaFornecedor;
    @FXML private TableColumn cPeca, cValor, cQtde, cFornecedor, cFornecedor1, cEmail, cCnpj;
    @FXML private ComboBox<Fornecedor> cbFornecedor, cbFornecedorBusca;
    @FXML private Label lbAdiciona, lbRemove; 
    @FXML private Button btnSalvar, btnSalvar2, btnAlterar, btnAlterar2, btnSalvarAlteracao, btnSalvarAlteracao2 ,canFornecedor, canEstoque;
    
    Estoque estoque = new Estoque();
    EstoqueDAOImpl estDAO = new EstoqueDAOImpl();
    EstoqueService estService = new EstoqueService();
    Estoque linhaBuscada = new Estoque();
    Fornecedor linhaBuscadaFornecedor = new Fornecedor();
    private ObservableList<Fornecedor> listaDeFornecedor = FXCollections.observableArrayList();
    public List<Estoque> resultado;
    public List<Fornecedor> resultFornecedor;
    Fornecedor fornecedor;
    FornecedorDAOImpl forDAO;
    private final FornecedorService forService = new FornecedorService();
    
    Integer idFornecedor, idEstoque;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        forDAO = new FornecedorDAOImpl();
        
        populaTabela();
        populaTabelaFornecedor();
        populaComboForbecedor();  
        populaComboForbecedorBusca(); 
    }
    
    @FXML
    private void cancelarBuscaPecas(ActionEvent event){
        limparCamposEstoque();
        
    }
    
    @FXML
    private void buscarTodosFornecedores(ActionEvent event){
        populaTabelaFornecedor();
        limparCampos();
        limparCamposEstoque();
        
    }
    
    @FXML
    private void buscarTodosEstoque(ActionEvent event){
        populaTabela();
        limparCampos();
        limparCamposEstoque();
    }
    
    @FXML
    private void salvarFornecedor(ActionEvent event){
        fornecedor = new Fornecedor();
        forDAO = new FornecedorDAOImpl();
        
        fornecedor.setCnpj(tfCnpj.getText());
        fornecedor.setEmail(tfEmail.getText());
        fornecedor.setNome(tfNomeFornecedor.getText());
        
        try {
            forDAO.create(fornecedor);
            Notifications.create().title("Sucesso").position(Pos.CENTER).text("Salvo com sucesso!").showInformation();
            populaTabelaFornecedor();
            populaComboForbecedor();
            limparCampos();
        } catch (Exception e) {
            Notifications.create().title("Erro").position(Pos.CENTER).text(e.getMessage()).showInformation();
        }
        
        
        
    }
    
    @FXML
    private void salvarEstoque(ActionEvent event){
        
        estoque = new Estoque();
        //estoque.setFornecedor(tfFornecedor.getText());
        estoque.setPeca(tfPeca.getText());
        estoque.setQtde(Integer.parseInt(tfQtde.getText()));
        estoque.setFornecedor(cbFornecedor.getValue());
        
        
        
        try {
            estService.salvar(estoque);
            Notifications.create().title("Sucesso").position(Pos.CENTER).text("Salvo com sucesso!").showInformation();
            limparCamposEstoque();
            populaTabela();
        } catch (Exception e) {
            Notifications.create().title("Erro").position(Pos.CENTER).text(e.getMessage()).showInformation();
            
        }
        
    }
    
    @FXML
    private void alterarFornecedor(ActionEvent event){
        tfNomeFornecedor.setEditable(true);
        tfEmail.setEditable(true);
        tfCnpj.setEditable(true);
        btnAlterar.setVisible(false);
        btnSalvar.setVisible(false);
        btnSalvarAlteracao.setVisible(true);
        canFornecedor.setVisible(true);
        
        
        
    }
    
    @FXML
    private void buscarFornecedor(ActionEvent event){
        forDAO = new FornecedorDAOImpl();
        if(!tfNomeBusca.getText().isEmpty() && tfCnpjBusca.getText().isEmpty()){
            resultFornecedor = forDAO.buscarPorNome(tfNomeBusca.getText());
            populaTabelaFornecedorBusca(resultFornecedor);
        }else if(!tfCnpjBusca.getText().isEmpty() && tfNomeBusca.getText().isEmpty()){
            resultFornecedor = forDAO.buscarPorCnpj(tfCnpjBusca.getText());
            populaTabelaFornecedorBusca(resultFornecedor);
        }
    }
    
    
     @FXML
    private void buscarPeca(ActionEvent event){
        estDAO = new EstoqueDAOImpl();
        if(!tfPecaBusca.getText().isEmpty() && tfQtdeInicial.getText().isEmpty() && tfQtdeFinal.getText().isEmpty()){
            resultado = estDAO.buscarPorPeca(tfPecaBusca.getText());
            populaTabelaEstoqueBusca(resultado);
        }else if(!tfQtdeInicial.getText().isEmpty() && !tfQtdeFinal.getText().isEmpty()){
            Integer qtdeIni, qtdeFim;
            qtdeIni = Integer.parseInt(tfQtdeInicial.getText());
            qtdeFim = Integer.parseInt(tfQtdeFinal.getText());
            resultado = estDAO.buscarPorQtde(qtdeIni, qtdeFim);
            populaTabelaEstoqueBusca(resultado);
        }else if(cbFornecedorBusca.getValue()!= null && tfPecaBusca.getText().isEmpty()
                && tfQtdeInicial.getText().isEmpty() && tfQtdeFinal.getText().isEmpty()){
            resultado = estDAO.buscarPorFornecedor(cbFornecedorBusca.getValue().getIdFornecedor());
            populaTabelaEstoqueBusca(resultado);
            cbFornecedorBusca.setValue(null);
            populaComboForbecedorBusca();
        }
    }
    
    
    @FXML
    private void alterarEstoque(ActionEvent event){
        
        tfQtdeEntra.setText("0");
        tfQtdeSai.setText("0");
        lbAdiciona.setVisible(true);
        lbRemove.setVisible(true);
        tfQtdeEntra.setVisible(true);
        tfQtdeSai.setVisible(true);
        tfPeca.setEditable(true);
        tfQtde.setEditable(true);
        btnAlterar2.setVisible(false);
        btnSalvar2.setVisible(false);
        btnSalvarAlteracao2.setVisible(true);
        canEstoque.setVisible(true);
        
        /*
        //Tratando a qtde das peças
        
        
        */

    }
    
    @FXML
    private void salvarAlteracaoFornecedor(ActionEvent event){
        fornecedor = new Fornecedor();
        fornecedor.setCnpj(tfCnpj.getText());
        fornecedor.setEmail(tfEmail.getText());
        fornecedor.setNome(tfNomeFornecedor.getText());
        fornecedor.setIdFornecedor(idFornecedor);
        try {
            forService.alterar(fornecedor);
            Notifications.create().title("Sucesso").position(Pos.CENTER).text("Alterado com sucesso!").showInformation();
            populaTabelaFornecedor();
            populaComboForbecedor();
            btnSalvarAlteracao.setVisible(false);
            btnAlterar.setVisible(true);
            btnSalvar.setVisible(true);
            limparCampos();
        } catch (Exception e) {
            Notifications.create().title("Erro").position(Pos.CENTER).text(e.getMessage()).showInformation();
        }
    }
    
    @FXML
    private void salvarAlteracaoEstoque(ActionEvent event){
        fornecedor = new Fornecedor();
        estoque = new Estoque();
        int qtdeEntra, qtdeSai, qtde,qtdeTotal;
        qtdeEntra = Integer.parseInt(tfQtdeEntra.getText());
        qtdeSai = Integer.parseInt(tfQtdeSai.getText());
        qtde = Integer.parseInt(tfQtde.getText());
        qtdeTotal = qtde - qtdeSai + qtdeEntra;
        
        estoque.setPeca(tfPeca.getText());
        estoque.setQtde(qtdeTotal);
        estoque.setIdEstoque(idEstoque);
        
        fornecedor.setIdFornecedor(cbFornecedor.getValue().getIdFornecedor());
        fornecedor.setCnpj(cbFornecedor.getValue().getCnpj());
        fornecedor.setEmail(cbFornecedor.getValue().getEmail());
        fornecedor.setNome(cbFornecedor.getValue().getNome());
        estoque.setFornecedor(fornecedor);
        try {
            estService.alterar(estoque);
            Notifications.create().title("Sucesso").position(Pos.CENTER).text("Alterado com sucesso!").showInformation();
            resultado = estDAO.findAll();
            populaTabela();
            btnSalvarAlteracao2.setVisible(false);
            btnAlterar2.setVisible(true);
            btnSalvar2.setVisible(true);
            tfQtdeEntra.setVisible(false);
            tfQtdeSai.setVisible(false);
            lbAdiciona.setVisible(false);
            lbRemove.setVisible(false);
            limparCamposEstoque();
        } catch (Exception e) {
            Notifications.create().title("Erro").position(Pos.CENTER).text(e.getMessage()).showInformation();
        }
    }
    
    
    @FXML
    private void cancelarFornecedor(ActionEvent event){
            btnSalvarAlteracao.setVisible(false);
            canFornecedor.setVisible(false);
            btnAlterar.setVisible(true);
            btnSalvar.setVisible(true);
            limparCampos();
    }
    
    @FXML
    private void cancelarEstoque(){
            btnSalvarAlteracao2.setVisible(false);
            btnAlterar2.setVisible(true);
            btnSalvar2.setVisible(true);
            tfQtdeEntra.setVisible(false);
            tfQtdeSai.setVisible(false);
            lbAdiciona.setVisible(false);
            lbRemove.setVisible(false);
            canEstoque.setVisible(false);
            limparCamposEstoque();
    }

    private void populaTabela() {
        
        try {
            estDAO = new EstoqueDAOImpl();
            resultado = estDAO.findAll();
            tabelaEstoque.getItems().setAll(resultado);
        } catch (Exception e) {
        }
        cPeca.setCellValueFactory(new PropertyValueFactory<>("peca"));           
        cFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));                      
        cQtde.setCellValueFactory(new PropertyValueFactory<>("qtde"));
        tabelaEstoque.getItems().setAll(resultado);
        
        
                
        tabelaEstoque.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
            linhaBuscada =  tabelaEstoque.getSelectionModel().getSelectedItem();                            
            //tfFornecedor2.setText(linhaBuscada.getFornecedor());      
            tfPeca.setText(linhaBuscada.getPeca());
            tfQtde.setText(Integer.toString(linhaBuscada.getQtde()));
            cbFornecedor.setValue(linhaBuscada.getFornecedor());
            idEstoque = linhaBuscada.getIdEstoque();
            tfPeca.setEditable(false);
            tfQtde.setEditable(false);
            cbFornecedor.setEditable(false);
            

                                
            }
                        
        });
    }
    
    
    
    

    private void populaTabelaFornecedor() {
        cCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));           
        cFornecedor1.setCellValueFactory(new PropertyValueFactory<>("nome"));                      
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        try {
            forDAO = new FornecedorDAOImpl();
            resultFornecedor = forDAO.findAll();
            tabelaFornecedor.getItems().setAll(resultFornecedor);
        } catch (Exception e) {
        }
        
        
        
                
        tabelaFornecedor.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
            linhaBuscadaFornecedor =  tabelaFornecedor.getSelectionModel().getSelectedItem();                            
            //tfFornecedor2.setText(linhaBuscada.getFornecedor());      
            
            tfNomeFornecedor.setText(linhaBuscadaFornecedor.getNome());
            tfEmail.setText(linhaBuscadaFornecedor.getEmail());
            tfCnpj.setText(linhaBuscadaFornecedor.getCnpj());
            idFornecedor = linhaBuscadaFornecedor.getIdFornecedor();
            tfNomeFornecedor.setEditable(false);
            tfEmail.setEditable(false);
            tfCnpj.setEditable(false);
            
            

                                
            }
                        
        });
    }   
  

    private void populaComboForbecedor() {
        try {
            listaDeFornecedor.setAll(forDAO.findAll());
            cbFornecedor.setItems(listaDeFornecedor);

        } catch (Exception e) {
        }
    }
     private void populaComboForbecedorBusca() {
        try {
            listaDeFornecedor.setAll(forDAO.findAll());
            cbFornecedorBusca.setItems(listaDeFornecedor);

        } catch (Exception e) {
        }
    }
    
    public void limparCampos(){
        tfNomeFornecedor.setText("");
        tfEmail.setText("");
        tfCnpj.setText("");
    }
    
    public void limparCamposEstoque(){
        tfPeca.setText("");
        tfQtde.setText("");
        tfPecaBusca.setText("");
        tfQtdeInicial.setText("");
        tfQtdeFinal.setText("");
        cbFornecedorBusca.setValue(null);
        populaComboForbecedorBusca();
        
        
    }
    
  

    private void populaTabelaFornecedorBusca(List<Fornecedor> resultFornecedor) {
       cCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));           
        cFornecedor1.setCellValueFactory(new PropertyValueFactory<>("nome"));                      
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));        
        tabelaFornecedor.getItems().setAll(resultFornecedor);
                
        tabelaFornecedor.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
            linhaBuscadaFornecedor =  tabelaFornecedor.getSelectionModel().getSelectedItem();                            
            //tfFornecedor2.setText(linhaBuscada.getFornecedor());      
            
            tfNomeFornecedor.setText(linhaBuscadaFornecedor.getNome());
            tfEmail.setText(linhaBuscadaFornecedor.getEmail());
            tfCnpj.setText(linhaBuscadaFornecedor.getCnpj());
            idFornecedor = linhaBuscadaFornecedor.getIdFornecedor();
            tfNomeFornecedor.setEditable(false);
            tfEmail.setEditable(false);
            tfCnpj.setEditable(false);
            
            

                                
            }
                        
        });}

    private void populaTabelaEstoqueBusca(List<Estoque> resultado) {
        cPeca.setCellValueFactory(new PropertyValueFactory<>("peca"));           
        cFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));                      
        cQtde.setCellValueFactory(new PropertyValueFactory<>("qtde"));
        tabelaEstoque.getItems().setAll(resultado);
        
        
                
        tabelaEstoque.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
            linhaBuscada =  tabelaEstoque.getSelectionModel().getSelectedItem();                            
            //tfFornecedor2.setText(linhaBuscada.getFornecedor());      
            tfPeca.setText(linhaBuscada.getPeca());
            tfQtde.setText(Integer.toString(linhaBuscada.getQtde()));
            cbFornecedor.setValue(linhaBuscada.getFornecedor());
            idEstoque = linhaBuscada.getIdEstoque();
            tfPeca.setEditable(false);
            tfQtde.setEditable(false);
            cbFornecedor.setEditable(false);
            

                                
            }
                        
        });
    }
    
    
}
