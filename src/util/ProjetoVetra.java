/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Max Willyan
 */
public class ProjetoVetra extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/telas/PrincipalView.fxml"));
        
        Scene scene = new Scene(root,1000, 600);
        stage.setTitle("Tela Principal");
        stage.setScene(scene);
        scene.getStylesheets().add(ProjetoVetra.class.getResource("/layout/TelaPrincipalLayout.css").toExternalForm());
        stage.show();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
        
    }
    
}
