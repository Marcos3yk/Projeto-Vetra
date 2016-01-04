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
import model.Pessoa;
import model.Servico;
import model.Veiculo;
import service.ServiceException;

/**
 *
 * @author MarcosVinicius
 */
public class VeiculoDAOImpl {
   
    private final EntityManager em;
    private final EntityManagerFactory emf;

    public VeiculoDAOImpl() {
        emf = Persistence.createEntityManagerFactory("ProjetoVetraPU");

        em = emf.createEntityManager();
    }
    
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Veiculo veiculo) throws ServiceException{
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (veiculo.getIdVeiculo()== null) {
                em.persist(veiculo);
            } else {
                em.merge(veiculo);
            }

            tx.commit();
        } catch (Exception e) { 
            e.printStackTrace();
            tx.rollback();
        }
    }
    
    public void alterar(Veiculo veiculo) throws ServiceException{
        
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
           
                em.merge(veiculo);
           
               

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
    
     public Veiculo buscarServicoPorCpfCnpj(String cpfCnpj) {
        Query consulta = em.createQuery("select v.marca, v.dataEntrada, v.nChassi, v.nRenavam, v.placa, v.nomeVeiculo,  s.dataTermino, s.descricaoServico, s.valor FROM Veiculo v left join Servico s on v.idVeiculo = s.idVeiculo WHERE  v.pessoa.cpfCnpj =:cpfCnpj");
        consulta.setParameter("pessoa.cpfCnpj", cpfCnpj);
        return (Veiculo) consulta.getSingleResult();
    }

    public Veiculo buscarPorPlaca(String placa) {
        Query consulta = em.createQuery("select v FROM Veiculo v  where LOWER(v.placa)  =:placa OR v.placa =:placa");
        consulta.setParameter("placa", placa);
        return (Veiculo) consulta.getSingleResult();
    }

    public Veiculo buscarPorRenavam(Long renavam) {
        Query consulta = em.createQuery("select v FROM Veiculo v WHERE v.renavam =:renavam");
        consulta.setParameter("renavam", renavam);
        return (Veiculo) consulta.getSingleResult();
    }

    public Veiculo buscarPorChassi(Long nchassi) {
        Query consulta = em.createQuery("select v FROM Veiculo v WHERE v.chassi =:nchassi");
        consulta.setParameter("nchassi", nchassi);
        return (Veiculo) consulta.getSingleResult();
    }

    public List<Veiculo> buscarPorModelo(String modelo) {
        modelo = "%"+modelo+"%";
        
        Query consulta = em.createQuery("select v FROM Veiculo v WHERE LOWER (v.nomeVeiculo) like :modelo OR v.nomeVeiculo like :modelo");
        consulta.setParameter("modelo", modelo);
        return (List<Veiculo>) consulta.getResultList();
    }

    public List<Veiculo> buscarPorMarca(String marca) {
        Query consulta = em.createQuery("select v FROM Veiculo v WHERE v.marca = :marca");
        consulta.setParameter("marca", marca);
        return (List<Veiculo>) consulta.getResultList();
    }

    public Veiculo validaVeiculo(String placa) {
        try {
            Query consulta = em.createQuery("select v From Veiculo v  WHERE v.placa =:placa");
            consulta.setParameter("placa", placa);
            return (Veiculo) consulta.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
