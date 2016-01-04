/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Pessoa;
import model.Servico;
import model.Veiculo;
import service.ServiceException;

/**
 *
 * @author MarcosVinicius
 */
public class ServicoDAOImpl {
    private final EntityManager em;
    private final EntityManagerFactory emf;

    public ServicoDAOImpl() {
        emf = Persistence.createEntityManagerFactory("ProjetoVetraPU");

        em = emf.createEntityManager();
    }
    
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servico servico) throws ServiceException{
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (servico.getIdServico()== null) {
                em.persist(servico);
            } else {
                em.merge(servico);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
    
    public void alterar(Servico servico) throws ServiceException{
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
           
                em.merge(servico);
           
               

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
    
     public List<Servico> buscarPorCpfCnpj(String cpfCnpj) {
        Query consulta = em.createQuery("select s FROM Servico s  where s.cpfCnpj =:cpfCnpj");
        consulta.setParameter("cpfCnpj", cpfCnpj);
        return (List<Servico>) consulta.getResultList();
    }
    
    public List<Servico> buscarPorNome(String nomerazaoSocial) {
         nomerazaoSocial = "%"+nomerazaoSocial+"%";
         
        //JPQL
        
         Query consulta = em.createQuery("select s From Servico s  WHERE  LOWER(s.veiculo.pessoa.nomerazaoSocial) LIKE :nomerazaoSocial OR s.veiculo.pessoa.nomerazaoSocial like :nomerazaoSocial");
         consulta.setParameter("nomerazaoSocial", nomerazaoSocial);
         return (List<Servico>) consulta.getResultList();
    }

    public List<Servico> buscarPorPlaca(String placa) {
        Query consulta = em.createQuery("select s FROM Servico s  where LOWER (s.veiculo.placa) LIKE :placa OR s.veiculo.placa like :placa");
        consulta.setParameter("placa", placa);
        return (List<Servico>) consulta.getResultList();
    }

    public List<Servico> buscarPorRenavam(Long nrenavam) {
        Query consulta = em.createQuery("select s FROM Servico s WHERE s.veiculo.renavam =:nrenavam");
        consulta.setParameter("nrenavam", nrenavam);
        return (List<Servico>) consulta.getResultList();
    }

    public List<Servico> buscarPorChassi(Long nchassi) {
        Query consulta = em.createQuery("select s FROM Servico s WHERE s.veiculo.chassi=:nchassi");
        consulta.setParameter("nchassi", nchassi);
        return (List<Servico>) consulta.getResultList();
    }

    public List<Servico> buscarPorStatus(String status) {
       Query consulta = em.createQuery("select s From Servico s  WHERE s.status =:status");
        consulta.setParameter("status", status);
        return (List<Servico>) consulta.getResultList();
    }

    public List<Servico> buscarPorIntervalo(Date dtInicio, Date dtFim) {
       Query consulta = em.createQuery("select s From Servico s  WHERE s.dataEntrada >= :dtInicio AND s.dataEntrada <= :dtFim");
       consulta.setParameter("dtInicio", dtInicio).setParameter("dtFim", dtFim);
       return (List<Servico>) consulta.getResultList();
    }

    public List<Servico> buscarPorStatusData(Date dtInicio, Date dtFim, String status) {
       Query consulta = em.createQuery("select s From Servico s  WHERE s.dataEntrada >= :dtInicio AND s.dataEntrada <= :dtFim AND s.status like :status");
       consulta.setParameter("dtInicio", dtInicio).setParameter("dtFim", dtFim).setParameter("status", status);
       return (List<Servico>) consulta.getResultList();
    }
}
