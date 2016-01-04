/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package telascontroller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class MenuController implements Initializable {
    @FXML
    private Button cadastrarCliente, alterarCadastroCliente, excluirCadastroCliente;
    
    
    //Menu Cliente
    @FXML
    private void cadastrarCliente(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/telas/CadastroCliente.fxml"));        
        
        Stage stage = new Stage();
        stage.setTitle("Cadastro Cliente");
        
        Scene scene = new Scene(root, 1000, 600);
        
        stage.setScene(scene);
        //scene.getStylesheets().add(ProjetoVetra1.class.getResource("/layout/cadastroClienteController.css").toExternalForm());
        stage.show();
    }
    
    @FXML
    private void alterarCadastroCliente(ActionEvent event) {
    }

    @FXML
    private void excluirCadastroCliente(ActionEvent event) {
    }
    
    
    //Menu Veiculo
    @FXML
    private void cadastrarVeiculo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/telas/CadastroVeiculo.fxml"));        
        
        Stage stage = new Stage();
        stage.setTitle("Cadastro Veiculo");
        
        Scene scene = new Scene(root, 1000, 600);
        
        stage.setScene(scene);
        //scene.getStylesheets().add(ProjetoVetra.class.getResource("/layout/cadastroClienteController.css").toExternalForm());
        stage.show();
    }

    @FXML
    private void alterarCadastroVeiculo(ActionEvent event) {
    }

    @FXML
    private void excluirCadastroVeiculo(ActionEvent event) {
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
