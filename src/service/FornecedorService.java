/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import DAO.FornecedorDAOImpl;
import model.Fornecedor;

/**
 *
 * @author MarcosVinicius
 */
public class FornecedorService {
    FornecedorDAOImpl fornecedorDAO;
   
    public FornecedorService(){
        fornecedorDAO = new FornecedorDAOImpl();
    }
    
    public void salvar(Fornecedor fornecedor)throws ServiceException{
        if(fornecedor.getNome().isEmpty()){
            throw new ServiceException("Campo Nome é obrigatorio");
        }
        
        if(fornecedor.getEmail().isEmpty()){
            throw new ServiceException("Campo Email é obrigatorio");
        }
        
         
        if(fornecedor.getCnpj().isEmpty()){
            throw new ServiceException("Campo CNPJ é obrigatorio");
        }
        
        
        try {
            fornecedorDAO.create(fornecedor);
        } catch (ServiceException ex) {
        }    
    }
    
    public void alterar(Fornecedor fornecedor)throws ServiceException{
        if(fornecedor.getNome().isEmpty()){
            throw new ServiceException("Campo Nome é obrigatorio");
        }
        
        if(fornecedor.getEmail().isEmpty()){
            throw new ServiceException("Campo Email é obrigatorio");
        }
        
         
        if(fornecedor.getCnpj().isEmpty()){
            throw new ServiceException("Campo CNPJ é obrigatorio");
        }
        
        
        try {
            fornecedorDAO.alterar(fornecedor);
        } catch (ServiceException ex) { 
        }    
    }
}
