/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import DAO.ServicoDAOImpl;
import model.Servico;

/**
 *
 * @author Max Willyan
 */
public class ServicoService {
    ServicoDAOImpl servDAO;
    
    public ServicoService(){
        servDAO = new ServicoDAOImpl();
    }
    
    public void salvar(Servico serv) throws ServiceException{
        
        if(serv.getDescricaoServico().isEmpty()){
            throw new ServiceException("É necessario informar a descrição do serviço");
        }
        
        if(serv.getDataEntrada() == null){
            throw new ServiceException("É necessario informar a data de entrada do veiculo");
        }
        
        if(serv.getDataTermino()== null){
            throw new ServiceException("É necessario informar a data termino do serviço");
        }
        
        if(serv.getCpfCnpj().isEmpty()){
            throw new ServiceException("Localize um cliente primeiro");
        }
        try {
              servDAO.create(serv);
        } catch (ServiceException e) {
        
        }
      
    }
}
