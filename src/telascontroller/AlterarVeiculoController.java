/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package telascontroller;

import DAO.VeiculoDAOImpl;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.Pessoa;
import model.Veiculo;
import org.controlsfx.control.Notifications;
import static telascontroller.ServicoController.toLocalDate;

/**
 * FXML Controller class
 *
 * @author MarcosVinicius
 */
public class AlterarVeiculoController implements Initializable {
    @FXML private TextField  tfModelo, tfPlaca, tfNrenavam, tfNchassi, tfProprietario;
    @FXML private TextField tfBuscaModelo, tfBuscaPlaca, tfBuscaRenavam, tfBuscaChassi;
    @FXML private DatePicker dpEntrada;
    @FXML private ComboBox cbMarca, cbBuscaMarca;
    @FXML private TableView<Veiculo> tabelaBusca;
    public List<Veiculo> resultado;
    @FXML TableColumn colunaCliente, colunaProprietario, colunaVeiculo, colunaPlaca, colunaMarca;
    @FXML TableColumn colunaChassi,colunaRenavam;
    ObservableValue<Veiculo> p;
    @FXML Button btnSelecionar;
    @FXML ImageView imgBuscar;
    Veiculo veiculoResultado = new Veiculo();
    Veiculo linhaBuscada = new Veiculo();
    VeiculoDAOImpl veic = new VeiculoDAOImpl();
    Pessoa pessoa = new Pessoa();
    Integer idVeiculo;
    
    public static LocalDate toLocalDate(Date d) {
        Instant instant = Instant.ofEpochMilli(d.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbBuscaMarca.setValue("Selecione");
        
        imgBuscar.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event){
            if(!tfBuscaPlaca.getText().isEmpty() && tfBuscaChassi.getText().isEmpty() && tfBuscaModelo.getText().isEmpty() && tfBuscaRenavam.getText().isEmpty() && cbBuscaMarca.getValue().equals("Selecione")){
                try {
                    
                    veiculoResultado = veic.buscarPorPlaca(tfBuscaPlaca.getText());
                    populaTela(veiculoResultado);
                    
                } catch (Exception e) {
                    Notifications.create().title("Não encontrado").position(Pos.CENTER).text("Nenhum veiculo encontrado com a placa "+tfBuscaPlaca.getText()).showInformation();                
                }
            }else if (!tfBuscaChassi.getText().isEmpty() && tfBuscaPlaca.getText().isEmpty()  && tfBuscaModelo.getText().isEmpty() && tfBuscaRenavam.getText().isEmpty() && cbBuscaMarca.getValue().equals("Selecione")){
                try {
                    veiculoResultado = veic.buscarPorChassi(Long.parseLong(tfBuscaChassi.getText()));
                    populaTela(veiculoResultado);

                } catch (Exception e) {
                    Notifications.create().title("Não encontrado").position(Pos.CENTER).text("Nenhum veiculo encontrado com o chassi "+tfBuscaChassi.getText()).showInformation();
                }
            }else if(!tfBuscaRenavam.getText().isEmpty() && tfBuscaChassi.getText().isEmpty() && tfBuscaPlaca.getText().isEmpty()  && tfBuscaModelo.getText().isEmpty() && cbBuscaMarca.getValue().equals("Selecione")){
                 try {
                    veiculoResultado = veic.buscarPorRenavam(Long.parseLong(tfBuscaRenavam.getText()));
                    populaTela(veiculoResultado);
                } catch (Exception e) {
                    Notifications.create().title("Não encontrado").position(Pos.CENTER).text("Nenhum veiculo encontrado com o Renavam "+tfBuscaRenavam.getText()).showInformation();
                }
            }else if(!tfBuscaModelo.getText().isEmpty() && tfBuscaRenavam.getText().isEmpty() && tfBuscaChassi.getText().isEmpty() && tfBuscaPlaca.getText().isEmpty()  &&  cbBuscaMarca.getValue().equals("Selecione")){
                
                try {
                    resultado = veic.buscarPorModelo(tfBuscaModelo.getText());
                    populaTabela(resultado); //popular a tabela busca
                } catch (Exception e) {
                    Notifications.create().title("Não encontrado").position(Pos.CENTER).text("Nenhum veiculo encontrado "+e.getMessage()).showInformation();
                }
                
                              
            }else if(!cbBuscaMarca.getValue().equals("Selecione") && tfBuscaModelo.getText().isEmpty() && tfBuscaRenavam.getText().isEmpty() && tfBuscaChassi.getText().isEmpty() && tfBuscaPlaca.getText().isEmpty()){
                try {
                   resultado = veic.buscarPorMarca(cbBuscaMarca.getValue().toString());
                   
                   populaTabela(resultado); //popular a tabela busca 
                } catch (Exception e) {
                    Notifications.create().title("Não encontrado").position(Pos.CENTER).text("Nenhum veiculo encontrado com a marca "+e.getMessage()).showInformation();
                }
                cbBuscaMarca.setValue("Selecione");
                     
                
            }
        }

            
        

        });
        
    }
    
    @FXML
    private void salvar(ActionEvent event){
        
        Date dataEntrada = Date.from(dpEntrada.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        veiculoResultado.setDataEntrada(dataEntrada);
        veiculoResultado.setPessoa(pessoa);
        veiculoResultado.setPlaca(tfPlaca.getText());
        veiculoResultado.setMarca(cbMarca.getValue().toString());
        veiculoResultado.setIdVeiculo(idVeiculo);
        veiculoResultado.setNomeVeiculo(tfModelo.getText());
        veiculoResultado.setChassi(Long.parseLong(tfNchassi.getText()));
        veiculoResultado.setRenavam(Long.parseLong(tfNrenavam.getText()));
        veiculoResultado.setNomeProprietario(tfProprietario.getText());
        try {
            veic.alterar(veiculoResultado);
            Notifications.create().title("Sucesso").position(Pos.CENTER).text("Salvo com sucesso!").showInformation();
           
        } catch (Exception e) {
            
        }
        
        
        
    }
    
    @FXML
    private void fecharTabela(ActionEvent event){
        tabelaBusca.setVisible(false);
        btnSelecionar.setVisible(false);
    }
    
    private void populaTela(Veiculo veiculoResultado) {
        pessoa = veiculoResultado.getPessoa();
        idVeiculo = veiculoResultado.getIdVeiculo();
        tfPlaca.setText(veiculoResultado.getPlaca());
        tfModelo.setText(veiculoResultado.getNomeVeiculo());
        tfNchassi.setText(Long.toString(veiculoResultado.getChassi()));
        tfNrenavam.setText(Long.toString(veiculoResultado.getRenavam()));
        tfProprietario.setText(veiculoResultado.getNomeProprietario());
        cbMarca.setValue(veiculoResultado.getMarca());
        LocalDate de = toLocalDate(veiculoResultado.getDataEntrada());           
        dpEntrada.setValue(de);
        
    }
    
    private void populaTabela(List<Veiculo> resultado){
        colunaCliente.setCellValueFactory(new PropertyValueFactory<>("pessoa"));           
        colunaProprietario.setCellValueFactory(new PropertyValueFactory<>("nomeProprietario"));
        colunaVeiculo.setCellValueFactory(new PropertyValueFactory<>("nomeVeiculo"));                
        colunaPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));                
        colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        
        tabelaBusca.getItems().setAll(resultado);
                
        tabelaBusca.setVisible(true);            
        btnSelecionar.setVisible(true);
                
        tabelaBusca.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
            linhaBuscada =  tabelaBusca.getSelectionModel().getSelectedItem();                            
            tfPlaca.setText(linhaBuscada.getPlaca());
            pessoa = linhaBuscada.getPessoa();
            idVeiculo = linhaBuscada.getIdVeiculo();
            tfModelo.setText(linhaBuscada.getNomeVeiculo());
            tfNchassi.setText(Long.toString(linhaBuscada.getChassi()));
            tfNrenavam.setText(Long.toString(linhaBuscada.getRenavam()));
            tfProprietario.setText(linhaBuscada.getNomeProprietario());
            cbMarca.setValue(linhaBuscada.getMarca());
            LocalDate de = toLocalDate(linhaBuscada.getDataEntrada());           
            dpEntrada.setValue(de);

                                
            }
                        
        });
    }
    
    @FXML
    private void cancelar(ActionEvent event){
        
        tfBuscaPlaca.setText("");
        tfBuscaRenavam.setText("");
        tfBuscaModelo.setText("");
        tfModelo.setText("");
        tfPlaca.setText("");
        dpEntrada.setValue(null);
        tfProprietario.setText("");
        tfNchassi.setText("");
        tfNrenavam.setText("");
        cbBuscaMarca.setValue("Selecione");
        cbMarca.setValue("");
        tfBuscaChassi.setText("");
                
    }
    
}
