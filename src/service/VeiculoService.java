/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import DAO.VeiculoDAOImpl;
import model.Servico;
import model.Veiculo;


public class VeiculoService {
    VeiculoDAOImpl veicDAO;

    public VeiculoService() {
        veicDAO = new VeiculoDAOImpl();
    }
    
    public void salvar(Veiculo veic) throws ServiceException{
        if(veic.getNomeVeiculo().isEmpty()){
            throw new ServiceException("O campo Modelo é obrigatorio!");
        } 
        if(veic.getMarca().equals("")){
            throw new ServiceException("O campo Marca é obrigatorio!");
        }
        if(veic.getPlaca().isEmpty()){
            throw new ServiceException("O campo Placa é obrigatorio!");
        }
        if(veic.getRenavam() == null){
            throw new ServiceException("O campo Nº RENAVAM é obrigatorio!");
        }
        
        if(veic.getChassi() == null){
            throw new ServiceException("O campo Nº Chassi é obrigatorio!");
        }
        
        if(veic.getNomeProprietario().isEmpty()){
            throw new ServiceException("O campo Nome do Proprietario é obrigatorio!");
        }      
        
        
        if(veic.getPessoa().getCpfCnpj().isEmpty()){
            throw new ServiceException("Selecione uma Pessoa primeiro!");
        }
        
        try {
            veicDAO.create(veic);
        } catch (ServiceException ex) {
            
        }
    }


}
