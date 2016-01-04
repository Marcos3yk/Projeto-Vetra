/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Busca;
import model.Pessoa;


import util.ConexaoUtil;
import util.HibernateUtil;

/**
 *
 * @author Max Willyan
 */
public class BuscaDAO {
    EntityManagerFactory emf;
    EntityManager em;
    
    public BuscaDAO() {
        //Ler o persistence.xml
        emf = Persistence.createEntityManagerFactory("ProjetoVetraPU");
        //Criando EntityManager
        em = emf.createEntityManager();
        
    } 
   
    
    public Pessoa buscarPorId(String cpfCnpj) {

        return em.find(Pessoa.class, cpfCnpj);
    }
    
    public List<Pessoa> buscarPorNome(String nome) {
        //JPQL
        Query consulta = em.createQuery("select p.nomerazaoSocial, p.cpfCnpj, p.email,  from Pessoa p  join Endereco e on p.cpfCnpj = e.cpfCnpj AND p.nomerazaoSocial LIKE =:nome");
        return consulta.getResultList();
    }
    /*
    public List<Busca> buscarPorNome(String nomeRazaoSocial){
       
        
        
        Query hql = em.createQuery("SELECT P.nomeRazaoSocial, P.cpfCnpj, P.email, E.cidade, E.estado FROM Pessoa P JOIN  Endereco E ON P.cpfCnpj = E.cpfCnpj WHERE P.nomeRazaoSocial like=: nomeRazaoSocial");
        return hql.getResultList();
    }*/
    
    /*
    public Busca buscarPorCpfCnpj(String cpfCnpj){
        String sql = "SELECT nomeRazaoSocial, cpfCnpj, email FROM pessoa WHERE cpfCnpj =?";
        try {
            PreparedStatement preparadorSQL = conexao.prepareStatement(sql);
           
            preparadorSQL.setString(1,cpfCnpj); 
            
             
            //Armazenando Resultado da consulta
            ResultSet resultado = preparadorSQL.executeQuery();
            List<Busca> lista = new ArrayList<>();
            while (resultado.next()) {
                //Instancia de busca
                Busca bus = new Busca();

                //Atribuindo dados do resultado no objeto busca
                bus.setNomeRazaoSocial(resultado.getString("nomeRazaoSocial"));
                bus.setCpfCnpj(resultado.getString("cpfCnpj"));
                bus.setEmail(resultado.getString("email"));
                return bus;
                
                
            }
            
            preparadorSQL.close();
            return null;
        } catch (SQLException ex) {

            Logger.getLogger(BuscaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public List<Busca> buscarPorNome(String nome){
        
        String sql = "SELECT P.nomeRazaoSocial, P.cpfCnpj, P.email, E.cidade, E.estado FROM pessoa P JOIN  Endereco E ON P.cpfCnpj = E.cpfCnpj WHERE P.nomeRazaoSocial like ?";
        try {
            PreparedStatement preparadorSQL = conexao.prepareStatement(sql);
           
            preparadorSQL.setString(1,"%"+nome+"%"); 
            
             
            //Armazenando Resultado da consulta
            ResultSet resultado = preparadorSQL.executeQuery();
            List<Busca> lista = new ArrayList<>();
            while (resultado.next()) {
                //Instancia de busca
                Busca bus = new Busca();

                //Atribuindo dados do resultado no objeto busca
                bus.setNomeRazaoSocial(resultado.getString("nomeRazaoSocial"));
                bus.setCpfCnpj(resultado.getString("cpfCnpj"));
                bus.setEmail(resultado.getString("email"));
                bus.setEstado(resultado.getString("estado"));
                bus.setCidade(resultado.getString("cidade"));
                lista.add(bus);
                
                
            }
            
            preparadorSQL.close();
            return lista;
        } catch (SQLException ex) {

            Logger.getLogger(BuscaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
                 
    }
    */
    
    
}
