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
import model.Estoque;
import model.Fornecedor;
import service.ServiceException;

/**
 *
 * @author MarcosVinicius
 */
public class FornecedorDAOImpl {
    private final EntityManager em;
    private final EntityManagerFactory emf;

    public FornecedorDAOImpl() {
        emf = Persistence.createEntityManagerFactory("ProjetoVetraPU");

        em = emf.createEntityManager();
    }
    
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fornecedor fornecedor) throws ServiceException{
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (fornecedor.getIdFornecedor()== null) {
                em.persist(fornecedor);
            } else {
                em.merge(fornecedor);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public List<Fornecedor> findAll() {
         try {
            Query consulta = em.createQuery("select f FROM Fornecedor f ORDER BY f.nome");        
            return (List<Fornecedor>) consulta.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void alterar(Fornecedor fornecedor) throws ServiceException{
        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin();
            
            em.merge(fornecedor);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public List<Fornecedor> buscarPorNome(String nome) {
        try {
            nome = "%"+nome+"%";
        
            Query consulta = em.createQuery("select f FROM Fornecedor f WHERE LOWER(f.nome) like :nome OR f.nome like :nome"); 
            consulta.setParameter("nome", nome);
            return (List<Fornecedor>) consulta.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Fornecedor> buscarPorCnpj(String cnpj) {
        try {
        
            Query consulta = em.createQuery("select f FROM Fornecedor f WHERE f.cnpj like :cnpj"); 
            consulta.setParameter("cnpj", cnpj);
            return (List<Fornecedor>) consulta.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
