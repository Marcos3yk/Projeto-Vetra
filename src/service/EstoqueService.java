/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import DAO.EstoqueDAOImpl;
import model.Estoque;

/**
 *
 * @author MarcosVinicius
 */
public class EstoqueService {
    EstoqueDAOImpl estoqueDAO;
   
    public EstoqueService(){
        estoqueDAO = new EstoqueDAOImpl();
    }
    
    public void salvar(Estoque estoque)throws ServiceException{
        if(estoque.getPeca().isEmpty()){
            throw new ServiceException("Campo Peça é obrigatorio");
        }
        
        if(estoque.getQtde() == null){
            throw new ServiceException("Campo Situação é obrigatorio");
        }
        
         
        if(estoque.getFornecedor()== null){
            throw new ServiceException("Campo Fornecedor é obrigatorio");
        }
        
        
        try {
            estoqueDAO.create(estoque);
        } catch (ServiceException ex) {
        }    
    }
    
    public void alterar(Estoque estoque)throws ServiceException{
        if(estoque.getPeca().isEmpty()){
            throw new ServiceException("Campo Peça é obrigatorio");
        }
        
        if(estoque.getQtde() == null){
            throw new ServiceException("Campo Situação é obrigatorio");
        }        
               
        if(estoque.getFornecedor() == null){
            throw new ServiceException("Campo Fornecedor é obrigatorio");
        }
        
        
        try {
            estoqueDAO.alterar(estoque);
        } catch (ServiceException ex) {
        }    
    }
    
}
