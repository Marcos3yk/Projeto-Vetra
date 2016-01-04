/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Endereco;
import model.Pessoa;
import model.Servico;
import service.ServiceException;

/**
 *
 * @author MarcosVinicius
 */
public class EnderecoDAOImpl {
    private final EntityManager em;
    private final EntityManagerFactory emf;
    
    public  EnderecoDAOImpl() {
        //Ler o persistence.xml
        emf = Persistence.createEntityManagerFactory("ProjetoVetraPU");
        //Criando EntityManager
        em = emf.createEntityManager();
        
    } 
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Endereco buscarCpfCnpj(String cpfCnpj) {
         
         //JPQL
        
         Query consulta = em.createQuery("select e From Endereco e  WHERE e.pessoa.cpfCnpj  = :cpfCnpj");
         consulta.setParameter("pessoa.cpfCnpj", cpfCnpj);
         return (Endereco) consulta.getSingleResult();
    }
    public Endereco buscarPorId(Integer id) {

        return em.find(Endereco.class, id);
    }
    

    public void create(Endereco endereco) throws ServiceException{
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (endereco.getIdEndereco()== null) {
                em.persist(endereco);
            } else {
                em.merge(endereco);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
    
    public void alterar(Endereco endereco) throws ServiceException{
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
           
                em.merge(endereco);
           
               

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
}
