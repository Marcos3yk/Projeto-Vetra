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
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Busca;
import model.Pessoa;
import service.ServiceException;

/**
 *
 * @author MarcosVinicius
 */
public class PessoaDAOImpl {
    private final EntityManager em;
    private final EntityManagerFactory emf;

    public PessoaDAOImpl() {
        emf = Persistence.createEntityManagerFactory("ProjetoVetraPU");

        em = emf.createEntityManager();
    }
    
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pessoa pessoa) throws ServiceException{
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (pessoa.getIdPessoa() == null) {
                em.persist(pessoa);
            } else {
                em.merge(pessoa);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
    
    public void alterar(Pessoa pessoa) throws ServiceException{
        try {
            em.getTransaction().begin();
            em.merge(pessoa);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        
        
        
        /*
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
           
            em.merge(pessoa);
           
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        */
    }
   /* 
   public Pessoa buscarPorId(String cpfCnpj) {

        return em.find(Pessoa.class, cpfCnpj);
    }*/
    
    public List<Pessoa> buscarPorNome(String nomerazaoSocial) {
         nomerazaoSocial = "%"+nomerazaoSocial+"%";
         System.out.println("Nome: "+nomerazaoSocial);
        //JPQL
        
         Query consulta = em.createQuery("select p From Pessoa p  WHERE LOWER(p.nomerazaoSocial) like :nomerazaoSocial OR p.nomerazaoSocial like :nomerazaoSocial");
         consulta.setParameter("nomerazaoSocial", nomerazaoSocial);
         return (List<Pessoa>) consulta.getResultList();
    }
    
    public Pessoa buscarPorId(String cpfCnpj) {
         
        //JPQL
         try {
            Query consulta = em.createQuery("select p From Pessoa p  WHERE p.cpfCnpj  like :cpfCnpj");
            consulta.setParameter("cpfCnpj", cpfCnpj);
            return (Pessoa) consulta.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
         
    }

    public Pessoa verificaPessoa(String cpfCnpj) {
        try {
            Query consulta = em.createQuery("select p From Pessoa p  WHERE p.cpfCnpj  =:cpfCnpj");
            consulta.setParameter("cpfCnpj", cpfCnpj);
            return (Pessoa) consulta.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
 
        
    }

   
}
