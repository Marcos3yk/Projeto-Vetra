/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package telascontroller;

import DAO.PessoaDAOImpl;
import DAO.ServicoDAOImpl;
import DAO.VeiculoDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.Pessoa;
import model.Servico;
import model.Veiculo;
import org.controlsfx.control.Notifications;
import service.ServiceException;

/**
 * FXML Controller class
 *
 * @author MarcosVinicius
 */
public class ServicoController implements Initializable {
    @FXML private TextField tfCpfCnpjbusca, tfNomeRazaoSocial, tfPlaca, tfRenavam, tfChassi, tfBuscaPlaca, tfVeiculo, tfMarca;
    @FXML private TextArea taDescricao;
    @FXML private TextField tfValor, tfCliente, tfCpfCnpj;
    @FXML private DatePicker dpEntrada, dpTermino, dpBuscaInicio, dpBuscaFim;
    @FXML private RadioButton rbBuscaAndamento, rbBuscaConcluido, rbBuscaCancelado, rbAndamento, rbConcluido, rbCancelado;
    @FXML private ImageView imgBuscaCliente, imgBuscaVeiculo, imgBuscaServico;
    @FXML private Label lbAviso;
    @FXML private ComboBox cbSituacao;
    public List<Servico> resultado;
    @FXML 
    private TableView<Servico> tabelaBusca;
    @FXML
    private TableColumn  colunaCpfCnpj, colunaVeiculo, colunaMarca, colunaPlaca, colunaDataEntrada;
    
    @FXML
    private TableColumn<Servico, String> colunaNome;
    
    @FXML
    private TableColumn colunaDataTermino, colunaStatus, colunaValor;
    @FXML
    private Button btnFechar;
    @FXML
    private AnchorPane anchorTabela;
    int busca;
    Servico linhaBuscada = new Servico();
    ServicoDAOImpl bus = new ServicoDAOImpl();
    Servico resultadoVeiculo = new Servico();
    Integer  idServico;
    Veiculo veiculo = new Veiculo();
    
    
    public static LocalDate toLocalDate(Date d) {
        Instant instant = Instant.ofEpochMilli(d.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //cbSituacao.setValue("Selecione");  
        
        //Busca por Cliente
        tratamentoDosRadiosButtons();
        imgBuscaCliente.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event){
            if(!tfCpfCnpjbusca.getText().isEmpty() && tfNomeRazaoSocial.getText().isEmpty()){
                try {
                    bus = new ServicoDAOImpl();
                    resultado = bus.buscarPorCpfCnpj(tfCpfCnpjbusca.getText());
                    populaTabela(resultado); 
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Nenhum Serviço encontrado para o CPF/CNPJ informado!");
                }
                   

            }else if(!tfNomeRazaoSocial.getText().isEmpty() && tfCpfCnpjbusca.getText().isEmpty()){
                bus = new ServicoDAOImpl();
                resultado = bus.buscarPorNome(tfNomeRazaoSocial.getText());
                populaTabela(resultado);
            }else{
                JOptionPane.showMessageDialog(null,"Informe apenas Um campo!");
            }
        
        }
        });
        
        //Busca por Veiculo
        
        imgBuscaVeiculo.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
               if(!tfBuscaPlaca.getText().isEmpty() && tfRenavam.getText().isEmpty() && tfChassi.getText().isEmpty()){
                   try {
                        bus = new ServicoDAOImpl();
                        resultado = bus.buscarPorPlaca(tfBuscaPlaca.getText());
                        populaTabela(resultado);
                   } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,"Nenhum Serviço encontrado para o veiculo com a placa informada!");

                   }                
             
               }else if(!tfRenavam.getText().isEmpty() && tfBuscaPlaca.getText().isEmpty() && tfChassi.getText().isEmpty()){
                   try {
                        bus = new ServicoDAOImpl();
                        resultado = bus.buscarPorRenavam(Long.parseLong(tfRenavam.getText()));
                        populaTabela(resultado);
                   } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,"Nenhum Serviço encontrado para o veiculo com o numero RENAVAM informado!"+e.getMessage());

                   } 
               }else if(!tfChassi.getText().isEmpty() && tfBuscaPlaca.getText().isEmpty() && tfRenavam.getText().isEmpty()){
                    try {
                        bus = new ServicoDAOImpl();
                        resultado = bus.buscarPorChassi(Long.parseLong(tfChassi.getText()));
                        populaTabela(resultado);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,"Nenhum Serviço encontrado para o veiculo com o Chassi informado!");

                   }  
               }else{
                   JOptionPane.showMessageDialog(null,"Informe apenas Um campo!");
               } 
            }

            
            
        });
        
        //Buscar por serviço
        
        imgBuscaServico.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if(rbBuscaAndamento.isSelected() == true && rbBuscaCancelado.isSelected()== false && rbBuscaConcluido.isSelected()== false && dpBuscaFim.getValue() == null && dpBuscaInicio.getValue() == null){
                   try {
                       bus = new ServicoDAOImpl();
                       resultado = bus.buscarPorStatus(rbBuscaAndamento.getText());
                       
                       populaTabela(resultado);
                   } catch (Exception e) {
                      JOptionPane.showMessageDialog(null,"Nenhum Serviço com status Em andamento encontrado!");
 
                   }
               }else if(rbBuscaConcluido.isSelected() == true && rbBuscaCancelado.isSelected()== false && rbBuscaAndamento.isSelected()== false && dpBuscaFim.getValue() == null && dpBuscaInicio.getValue() == null){
                   try {
                       bus = new ServicoDAOImpl();
                       resultado = bus.buscarPorStatus(rbBuscaConcluido.getText());
                       populaTabela(resultado);
                   } catch (Exception e) {
                       JOptionPane.showMessageDialog(null,"Nenhum Serviço com status Em andamento encontrado!");
 
                   }
               }else if(rbBuscaCancelado.isSelected() == true && rbBuscaAndamento.isSelected()== false && rbBuscaConcluido.isSelected()== false && dpBuscaFim.getValue() == null && dpBuscaInicio.getValue() == null){
                   try {
                       bus = new ServicoDAOImpl();
                       resultado = bus.buscarPorStatus(rbBuscaCancelado.getText());
                       populaTabela(resultado);
                   } catch (Exception e) {
                       JOptionPane.showMessageDialog(null,"Nenhum Serviço com status Em andamento encontrado!");
 
                   }
               }else if(dpBuscaInicio.getValue()!= null && dpBuscaFim.getValue() != null && rbBuscaAndamento.isSelected() == false && rbBuscaCancelado.isSelected() == false && rbBuscaConcluido.isSelected() == false){
                    try {
                        bus = new ServicoDAOImpl();
                        Date dataInicio = Date.from(dpBuscaInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Date dataFim = Date.from(dpBuscaFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        resultado = bus.buscarPorIntervalo(dataInicio, dataFim);
                        populaTabela(resultado);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,e.getMessage());
                    }
               }else if(dpBuscaInicio.getValue()!= null && dpBuscaFim.getValue() != null && rbBuscaAndamento.isSelected()){
                    try {
                        bus = new ServicoDAOImpl();
                        Date dataInicio = Date.from(dpBuscaInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Date dataFim = Date.from(dpBuscaFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        resultado = bus.buscarPorStatusData(dataInicio, dataFim, rbBuscaAndamento.getText());
                        populaTabela(resultado);
                    } catch (Exception e) {
                    }
               }else if(dpBuscaInicio.getValue()!= null && dpBuscaFim.getValue() != null && rbBuscaCancelado.isSelected()){
                    try {
                        bus = new ServicoDAOImpl();
                        Date dataInicio = Date.from(dpBuscaInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Date dataFim = Date.from(dpBuscaFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        resultado = bus.buscarPorStatusData(dataInicio, dataFim, rbBuscaCancelado.getText());
                        populaTabela(resultado);
                    } catch (Exception e) {
                    }
               }else if(dpBuscaInicio.getValue()!= null && dpBuscaFim.getValue() != null && rbBuscaConcluido.isSelected()){
                    try {
                        bus = new ServicoDAOImpl();
                        Date dataInicio = Date.from(dpBuscaInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Date dataFim = Date.from(dpBuscaFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        resultado = bus.buscarPorStatusData(dataInicio, dataFim, rbBuscaConcluido.getText());
                        populaTabela(resultado);
                    } catch (Exception e) {
                        
                    }
               }
            
            }
        });
            
        
    }
    
    
    
    
    private void tratamentoDosRadiosButtons() {
        rbBuscaAndamento.selectedProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(rbBuscaAndamento.isSelected()){
                    rbBuscaAndamento.setSelected(true);
                    rbBuscaCancelado.setSelected(false);
                    rbBuscaConcluido.setSelected(false);
                }
            }
        });
        
        rbBuscaConcluido.selectedProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(rbBuscaConcluido.isSelected()){
                    rbBuscaConcluido.setSelected(true);
                    rbBuscaAndamento.setSelected(false);
                    rbBuscaCancelado.setSelected(false);
                }
            }
        });
        
        rbBuscaCancelado.selectedProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(rbBuscaCancelado.isSelected()){
                    rbBuscaCancelado.setSelected(true);
                    rbBuscaAndamento.setSelected(false);
                    rbBuscaConcluido.setSelected(false);
                }
            }
        });
        
        //Lidando com os RadioButtons do resultado da busca
        rbAndamento.selectedProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(rbAndamento.isSelected()){
                    rbCancelado.setSelected(false);
                    rbConcluido.setSelected(false);
                }
            }
        });
        
        rbConcluido.selectedProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(rbConcluido.isSelected()){
                    rbAndamento.setSelected(false);
                    rbCancelado.setSelected(false);
                }
            }
        });
        
        rbCancelado.selectedProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(rbCancelado.isSelected()){
                    rbAndamento.setSelected(false);
                    rbConcluido.setSelected(false);
                }
            }
        });
    }
    
    @FXML
    private void cancelar(){
        tfPlaca.setText("");
        tfVeiculo.setText("");
        tfMarca.setText("");
        taDescricao.setText("");
        tfValor.setText("");
        tfCliente.setText("");
        tfCpfCnpj.setText("");
        dpEntrada.setValue(null);
        dpTermino.setValue(null);
        rbAndamento.setSelected(false);
        rbCancelado.setSelected(false);
        rbConcluido.setSelected(false);
    }

    private void populaTabela(List<Servico> resultado) {
       		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeRazaoSocial"));            
                colunaCpfCnpj.setCellValueFactory(new PropertyValueFactory<>("cpfCnpj"));
                colunaVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));                
                colunaPlaca.setCellValueFactory(new PropertyValueFactory<>("placa")); 
                colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));                
                colunaDataEntrada.setCellValueFactory(new PropertyValueFactory<>("dataEntrada"));
                colunaDataTermino.setCellValueFactory(new PropertyValueFactory<>("dataTermino"));                
                tabelaBusca.getItems().setAll(resultado);
                anchorTabela.setVisible(true);
                tabelaBusca.setVisible(true);            
                btnFechar.setVisible(true);
                
                tabelaBusca.setOnMousePressed(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent event){
                            linhaBuscada = tabelaBusca.getSelectionModel().getSelectedItem();                            
                                tfPlaca.setText(linhaBuscada.getVeiculo().getPlaca());
                                tfMarca.setText(linhaBuscada.getVeiculo().getMarca());
                                tfVeiculo.setText(linhaBuscada.getVeiculo().getNomeVeiculo());
                                taDescricao.setText(linhaBuscada.getDescricaoServico());
                                tfValor.setText(Double.toString(linhaBuscada.getValor()));
                                tfCliente.setText(linhaBuscada.getVeiculo().getPessoa().getNomerazaoSocial());
                                tfCpfCnpj.setText(linhaBuscada.getCpfCnpj());
                                veiculo = linhaBuscada.getVeiculo();
                                idServico = linhaBuscada.getIdServico();
                                LocalDate de = toLocalDate(linhaBuscada.getDataEntrada());           
                                dpEntrada.setValue(de);

                                LocalDate dt = toLocalDate(linhaBuscada.getDataTermino());           
                                dpTermino.setValue(dt);

                                if(linhaBuscada.getStatus().equals("Em andamento")){
                                    rbAndamento.setSelected(true);
                                    rbConcluido.setSelected(false);
                                    rbCancelado.setSelected(false);
                                }else if(linhaBuscada.getStatus().equals("Concluido")){
                                    rbConcluido.setSelected(true);
                                    rbCancelado.setSelected(false);
                                    rbAndamento.setSelected(false);
                                }else if(linhaBuscada.getStatus().equals("Cancelado")){
                                    rbCancelado.setSelected(true);
                                    rbConcluido.setSelected(false);
                                    rbAndamento.setSelected(false);
                                } 
                            }
                        
                         });
    }
    
    
    private void populaTabela2(int busca) {
       		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeRazaoSocial"));            
                colunaCpfCnpj.setCellValueFactory(new PropertyValueFactory<>("cpfCnpj"));
                colunaVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));                
                colunaPlaca.setCellValueFactory(new PropertyValueFactory<>("placa")); 
                colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));                
                colunaDataEntrada.setCellValueFactory(new PropertyValueFactory<>("dataEntrada"));
                colunaDataTermino.setCellValueFactory(new PropertyValueFactory<>("dataTermino"));
                if(busca == 1){
                    tabelaBusca.getItems().setAll(bus.buscarPorStatus(rbBuscaAndamento.getText()));
                    anchorTabela.setVisible(true);
                    tabelaBusca.setVisible(true);            
                    btnFechar.setVisible(true);
                }
                                
                tabelaBusca.setOnMousePressed(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent event){
                            linhaBuscada = tabelaBusca.getSelectionModel().getSelectedItem();                            
                                tfPlaca.setText(linhaBuscada.getVeiculo().getPlaca());
                                tfMarca.setText(linhaBuscada.getVeiculo().getMarca());
                                tfVeiculo.setText(linhaBuscada.getVeiculo().getNomeVeiculo());
                                taDescricao.setText(linhaBuscada.getDescricaoServico());
                                tfValor.setText(Double.toString(linhaBuscada.getValor()));
                                tfCliente.setText(linhaBuscada.getVeiculo().getPessoa().getNomerazaoSocial());
                                tfCpfCnpj.setText(linhaBuscada.getCpfCnpj());
                                veiculo = linhaBuscada.getVeiculo();
                                idServico = linhaBuscada.getIdServico();
                                LocalDate de = toLocalDate(linhaBuscada.getDataEntrada());           
                                dpEntrada.setValue(de);

                                LocalDate dt = toLocalDate(linhaBuscada.getDataTermino());           
                                dpTermino.setValue(dt);

                                if(linhaBuscada.getStatus().equals("Em andamento")){
                                    rbAndamento.setSelected(true);
                                    rbConcluido.setSelected(false);
                                    rbCancelado.setSelected(false);
                                }else if(linhaBuscada.getStatus().equals("Concluido")){
                                    rbConcluido.setSelected(true);
                                    rbCancelado.setSelected(false);
                                    rbAndamento.setSelected(false);
                                }else if(linhaBuscada.getStatus().equals("Cancelado")){
                                    rbCancelado.setSelected(true);
                                    rbConcluido.setSelected(false);
                                    rbAndamento.setSelected(false);
                                } 
                            }
                        
                         });
    }
    
    @FXML
    private void fecharTabela(ActionEvent event){
        anchorTabela.setVisible(false);
        tabelaBusca.getItems().clear();
        tabelaBusca.setVisible(false);
        btnFechar.setVisible(false);
    }
    @FXML
    public void salvar(ActionEvent event){
        Date dataEntrada = Date.from(dpEntrada.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataSaida = Date.from(dpTermino.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        Servico serv = new Servico();
        ServicoDAOImpl serDao = new ServicoDAOImpl();
        
        serv.setIdServico(idServico);
        serv.setVeiculo(veiculo);
        serv.setCpfCnpj(tfCpfCnpj.getText());
        serv.setPlaca(tfPlaca.getText());
        serv.setDataEntrada(dataEntrada);
        serv.setDataTermino(dataSaida);
        serv.setDescricaoServico(taDescricao.getText());
        serv.setValor(Double.parseDouble(tfValor.getText()));
        serv.setNomeRazaoSocial(tfCliente.getText());
        if(rbConcluido.isSelected()){
           serv.setStatus("Concluido");
        }else if(rbAndamento.isSelected()){
            serv.setStatus("Em andamento");
        }else if(rbCancelado.isSelected())
            serv.setStatus("Cancelado");
        try {
           serDao.alterar(serv);
           Notifications.create().title("Sucesso").position(Pos.CENTER).text("Salvo com sucesso!").showInformation();
           
           
        } catch (ServiceException ex) {
           Notifications.create().title("Erro").position(Pos.CENTER).text(ex.getMessage()).showError();
           
        }
       
    }
    
    
    
}
