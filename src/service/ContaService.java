/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import DAO.ContaDAOImpl;
import model.Conta;

/**
 *
 * @author MarcosVinicius
 */
public class ContaService {
    ContaDAOImpl contaDAO;
   
    public ContaService(){
        contaDAO = new ContaDAOImpl();
    }
    
    public void salvar(Conta conta)throws ServiceException{
        if(conta.getConta().isEmpty()){
            throw new ServiceException("Campo Conta é obrigatorio");
        }
        
        if(conta.getSituacao().isEmpty()){
            throw new ServiceException("Campo Situação é obrigatorio");
        }
        
        if(conta.getValor() == null){
            throw new ServiceException("Campo Valor é obrigatorio");
        }
        
        if(conta.getVencimento() == null){
            throw new ServiceException("Campo Vencimento é obrigatorio");
        }
        
        
        try {
            contaDAO.create(conta);
        } catch (ServiceException ex) {
        }
        
        
    }

    public void editar(Conta conta) throws ServiceException {
        if(conta.getConta().isEmpty()){
            throw new ServiceException("Campo Conta do modulo editar é obrigatorio");
        }
        
        if(conta.getSituacao().isEmpty()){
            throw new ServiceException("Campo Situação do modulo editar é obrigatorio");
        }
        
        if(conta.getValor() == null){
            throw new ServiceException("Campo Valor do modulo editar é obrigatorio");
        }
        
        if(conta.getVencimento() == null){
            throw new ServiceException("Campo Vencimento do modulo editar é obrigatorio");
        }
        
        
        try {
            contaDAO.alterar(conta);
        } catch (ServiceException ex) {
            
        }
        
        
    }
}
