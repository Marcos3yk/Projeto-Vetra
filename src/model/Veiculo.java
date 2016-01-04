/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Max Willyan
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Veiculo.findAll", query = "SELECT v FROM Veiculo v"),
    @NamedQuery(name = "Veiculo.buscarPorChassi", query = "select v FROM Veiculo v WHERE v.chassi =:nchassi"),
    @NamedQuery(name = "Veiculo.buscarPorPlaca", query = "select v FROM Veiculo v  where v.placa =:placa"),
    @NamedQuery(name = "Veiculo.buscarPorRenavam", query = "select v FROM Veiculo v  where v.renavam =:nrenavam"),
    @NamedQuery(name = "Veiculo.buscarPorModelo", query = "select v FROM Veiculo v  where v.nomeVeiculo like :nomeVeiculo"),
    @NamedQuery(name = "Veiculo.buscarPorMarca", query = "select v FROM Veiculo v  where v.marca like :marca")
   
   
})
public class Veiculo implements Serializable {
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idVeiculo;

    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Integer idVeiculo) {
        this.idVeiculo = idVeiculo;
    }
    
    
    @Column
    private String placa;
    @Column
    private String nomeVeiculo;
    @Column
    private String  marca;     
    @Column
    private String nomeProprietario;
    @Column
    private Long  renavam;      
    @Column
    private Long  chassi;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEntrada;
    
    @ManyToOne
    @JoinColumn(name = "idPessoa_pessoa", nullable = true)
    private Pessoa pessoa;
    
    
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    

    public String getNomeVeiculo() {
        return nomeVeiculo;
    }

    public void setNomeVeiculo(String nomeVeiculo) {
        this.nomeVeiculo = nomeVeiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public Long getRenavam() {
        return renavam;
    }

    public void setRenavam(Long renavam) {
        this.renavam = renavam;
    }

    public Long getChassi() {
        return chassi;
    }

    public void setChassi(Long chassi) {
        this.chassi = chassi;
    }  
    

   

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
    
    @Override
    public String toString() {
        return this.nomeVeiculo;
    }
        
    
}
