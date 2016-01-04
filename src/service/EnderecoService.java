/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import DAO.EnderecoDAOImpl;
import model.Endereco;

/**
 *
 * @author Max Willyan
 */
public class EnderecoService {
    EnderecoDAOImpl endDAO;
   
    public EnderecoService(){
        endDAO = new EnderecoDAOImpl();
    }
    
    public void salvar(Endereco endereco)throws ServiceException{
        if(endereco.getRua().isEmpty()){
            throw new ServiceException("Campo Rua é obrigatorio");
        }
        
        if(endereco.getCidade().isEmpty()){
            throw new ServiceException("Campo Cidade é obrigatorio");
        }
        
        if(endereco.getBairro().isEmpty()){
            throw new ServiceException("Campo Bairro é obrigatorio");
        }
        /*
        if(endereco.getNumero() == null){
            throw new ServiceException("Campo N é obrigatorio");
        }*/
        
        if(endereco.getEstado().equals("Selecione")){
            throw new ServiceException("Escolha um Estado");
        }
       
        try {
            endDAO.create(endereco);
        } catch (ServiceException ex) {
        }
        
        
    }
    
    public void alterar(Endereco endereco)throws ServiceException{
        if(endereco.getRua().isEmpty()){
            throw new ServiceException("Campo Rua é obrigatorio");
        }
        
        if(endereco.getCidade().isEmpty()){
            throw new ServiceException("Campo Cidade é obrigatorio");
        }
        
        if(endereco.getBairro().isEmpty()){
            throw new ServiceException("Campo Bairro é obrigatorio");
        }
        
        if(endereco.getNumero() == null){
            throw new ServiceException("Campo Numero é obrigatorio");
        }
        
        if(endereco.getEstado().equals("Selecione")){
            throw new ServiceException("Escolha um Estado");
        }
        
        try {
            endDAO.alterar(endereco);
        } catch (ServiceException ex) {
        }
        
        
    }
    
 
    public void excluir(Integer codEndereco)throws ServiceException{
       
           //endDAO.excluir(codEndereco);
      
   }
}
