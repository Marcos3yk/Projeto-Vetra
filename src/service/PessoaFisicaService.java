/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import DAO.PessoaDAOImpl;
import model.Pessoa;

/**
 *
 * @author Max Willyan
 */
public class PessoaFisicaService {
    PessoaDAOImpl pessoaDAO;
    
    public PessoaFisicaService(){
        pessoaDAO = new PessoaDAOImpl();
    }
    
    public void salvar(Pessoa pessoa) throws ServiceException{
        if(pessoa.getNomerazaoSocial().isEmpty()){
            throw new ServiceException("O campo nome é obrigatório");
        }
        
        if(pessoa.getCpfCnpj().isEmpty()){
            throw new ServiceException("O campo CPF/CNPJ é obrigatório");
        }
        if(pessoa.getEmail().isEmpty()){
            throw new ServiceException("O campo Email é obrigatório");
        }
        if(pessoa.getRg().isEmpty()){
            throw new ServiceException("O campo RG é obrigatório");
        }
        
        if(pessoa.getTelefone().isEmpty() && pessoa.getCelular().isEmpty()){
            throw new ServiceException("Informe pelo menos um numero de contato");
        }
        
        if(pessoa.getTipoPessoa().equals("Selecione")){
            throw new ServiceException("Selecione o Tipo de pessoa");
        }
        try {
            pessoaDAO.create(pessoa);
        } catch (ServiceException ex) {
            
        }
        
    }

    public void alterar(Pessoa pessoa) throws ServiceException{
        if(pessoa.getNomerazaoSocial().isEmpty()){
            throw new ServiceException("O campo nome é obrigatório");
        }
        
        if(pessoa.getCpfCnpj().isEmpty()){
            throw new ServiceException("O campo CPF/CNPJ é obrigatório");
        }
        if(pessoa.getEmail().isEmpty()){
            throw new ServiceException("O campo Email é obrigatório");
        }
        if(pessoa.getRg().isEmpty()){
            throw new ServiceException("O campo RG é obrigatório");
        }
        if(pessoa.getTelefone().isEmpty() && pessoa.getCelular().isEmpty()){
            throw new ServiceException("Informe pelo menos um numero de contato");
        }
        
        if(pessoa.getTipoPessoa().equals("Selecione")){
            throw new ServiceException("Selecione o Tipo de pessoa");
        }
        try {
            pessoaDAO.alterar(pessoa);
        } catch (ServiceException ex) {
            
        }
    }
}
