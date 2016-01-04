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
import model.Veiculo;
import service.ServiceException;

/**
 *
 * @author MarcosVinicius
 */
public class EstoqueDAOImpl {
    private final EntityManager em;
    private final EntityManagerFactory emf;

    public EstoqueDAOImpl() {
        emf = Persistence.createEntityManagerFactory("ProjetoVetraPU");

        em = emf.createEntityManager();
    }
    
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estoque estoque) throws ServiceException{
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (estoque.getIdEstoque()== null) {
                em.persist(estoque);
            } else {
                em.merge(estoque);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
    
    public void alterar(Estoque estoque) throws ServiceException{
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(estoque);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public List<Estoque> findAll() {
        try {
            Query consulta = em.createQuery("select e FROM Estoque e ORDER BY e.peca");        
            return (List<Estoque>) consulta.getResultList();
        } catch (NoResultException e) {
            return null;
        }
        
    }

    public List<Estoque> buscarPorPeca(String peca) {
        try {
            peca = "%"+peca+"%";
        
            Query consulta = em.createQuery("select e FROM Estoque e WHERE LOWER (e.peca) like :peca OR e.peca like :peca"); 
            consulta.setParameter("peca", peca);
            return (List<Estoque>) consulta.getResultList();
        } catch (NoResultException e) {
            return null;
        } 
    }
    
    public List<Estoque> buscarPorQtde(Integer qtdeIni, Integer qtdeFim) {
        try {
            
        
            Query consulta = em.createQuery("select e FROM Estoque e WHERE e.qtde >= :qtdeIni AND e.qtde <= :qtdeFim"); 
            consulta.setParameter("qtdeIni", qtdeIni).setParameter("qtdeFim", qtdeFim);
            return (List<Estoque>) consulta.getResultList();
        } catch (NoResultException e) {
            return null;
        } 
    }
    
    
     public List<Estoque> buscarPorFornecedor(Integer fornecedor) {
        try {
            
        
            Query consulta = em.createQuery("select e FROM Estoque e WHERE e.fornecedor.idFornecedor = :fornecedor"); 
            consulta.setParameter("fornecedor", fornecedor);
            return (List<Estoque>) consulta.getResultList();
        } catch (NoResultException e) {
            return null;
        } 
    }
}
