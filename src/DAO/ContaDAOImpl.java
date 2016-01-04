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
import model.Conta;
import model.Estoque;
import model.Pessoa;
import service.ServiceException;

/**
 *
 * @author MarcosVinicius
 */
public class ContaDAOImpl {
    private final EntityManager em;
    private final EntityManagerFactory emf;

    public ContaDAOImpl() {
        emf = Persistence.createEntityManagerFactory("ProjetoVetraPU");

        em = emf.createEntityManager();
    }
    
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Conta conta) throws ServiceException{
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (conta.getIdConta()== null) {
                em.persist(conta);
            } else {
                em.merge(conta);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public List<Conta> buscaPorSituacao(String situacao) {
        Query consulta = em.createQuery("select c FROM Conta c WHERE c.situacao like :situacao");
        consulta.setParameter("situacao", situacao);
        return (List<Conta>) consulta.getResultList();
    }
    
     public List<Conta> findAll() {
        try {
            Query consulta = em.createQuery("select c FROM Conta c");        
            return (List<Conta>) consulta.getResultList();
        } catch (NoResultException e) {
            return null;
        }
        
    }

    public void alterar(Conta conta) throws ServiceException{
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(conta);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
}
