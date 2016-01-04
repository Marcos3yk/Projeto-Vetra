/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telascontroller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.ProjetoVetra;

/**
 *
 * @author Max Willyan
 */
public class TelaPrincipalController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private ImageView imgCliente, imgNovoVeiculo, imgOrcamento, imgServico, imgAlterarCliente, imgAlterarVeiculo;
    @FXML
    private ImageView imgConta, imgEstoque;
    @FXML AnchorPane anchorVeiculo;
   
    
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgNovoVeiculo.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event){
            try {
                Parent root;
                
                root = FXMLLoader.load(getClass().getResource("/telas/CadastroVeiculoView.fxml"));
                
                
                Stage stage = new Stage();
                stage.setTitle("Cadastro Veículo/Serviço");
                
                Scene scene = new Scene(root, 1000, 600);
                
                stage.setScene(scene);
                scene.getStylesheets().add(ProjetoVetra.class.getResource("/layout/cadastroClienteController.css").toExternalForm());
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
        
        imgCliente.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event){
            try {
                Parent root;
                
                root = FXMLLoader.load(getClass().getResource("/telas/CadastroClienteView.fxml"));
                
                
                Stage stage = new Stage();
                stage.setTitle("Cadastro Cliente");
                
                Scene scene = new Scene(root);
                
                stage.setScene(scene);
                scene.getStylesheets().add(ProjetoVetra.class.getResource("/layout/cadastroClienteController.css").toExternalForm());
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
        
        imgAlterarCliente.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event){
            try {
                Parent root;
                
                root = FXMLLoader.load(getClass().getResource("/telas/AlterarClienteView.fxml"));
                
                
                Stage stage = new Stage();
                stage.setTitle("Alterar Cliente");
                
                Scene scene = new Scene(root);
                
                stage.setScene(scene);
                scene.getStylesheets().add(ProjetoVetra.class.getResource("/layout/cadastroClienteController.css").toExternalForm());
                stage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
        
        imgOrcamento.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event){
            try {
                Parent root;
                
                root = FXMLLoader.load(getClass().getResource("/telas/OrcamentoView.fxml"));
                
                
                Stage stage = new Stage();
                stage.setTitle("Orçamentos");
                
                Scene scene = new Scene(root);
                
                stage.setScene(scene);
                scene.getStylesheets().add(ProjetoVetra.class.getResource("/layout/cadastroClienteController.css").toExternalForm());
                stage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
        
        
        imgServico.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event){
            try {
                Parent root;
                
                root = FXMLLoader.load(getClass().getResource("/telas/ServicoView.fxml"));
                
                
                Stage stage = new Stage();
                stage.setTitle("Serviços");
                
                Scene scene = new Scene(root);
                
                stage.setScene(scene);
                scene.getStylesheets().add(ProjetoVetra.class.getResource("/layout/cadastroClienteController.css").toExternalForm());
                stage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
        
        imgAlterarVeiculo.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event){
            try {
                
                
                Parent root;
                
                root = FXMLLoader.load(getClass().getResource("/telas/AlterarVeiculoView.fxml"));
                
                
                Stage stage = new Stage();
                stage.setTitle("Alterar Veiculo");
                
                Scene scene = new Scene(root);
                
                stage.setScene(scene);
                scene.getStylesheets().add(ProjetoVetra.class.getResource("/layout/cadastroClienteController.css").toExternalForm());
                stage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
        
        imgConta.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event){
            try {
                
                
                Parent root;
                
                root = FXMLLoader.load(getClass().getResource("/telas/ContasView.fxml"));
                
                
                Stage stage = new Stage();
                stage.setTitle("Contas");
                
                Scene scene = new Scene(root);
                
                stage.setScene(scene);
                scene.getStylesheets().add(ProjetoVetra.class.getResource("/layout/cadastroClienteController.css").toExternalForm());
                stage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
        
        
        imgEstoque.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event){
            try {
                
                
                Parent root;
                
                root = FXMLLoader.load(getClass().getResource("/telas/EstoqueView.fxml"));
                
                
                Stage stage = new Stage();
                stage.setTitle("Estoque");
                
                Scene scene = new Scene(root);
                
                stage.setScene(scene);
                scene.getStylesheets().add(ProjetoVetra.class.getResource("/layout/cadastroClienteController.css").toExternalForm());
                stage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
        
    }
    
    
    
    
}
