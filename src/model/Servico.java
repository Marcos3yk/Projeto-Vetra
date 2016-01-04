/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.CascadeType;
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
    @NamedQuery(name = "Servico.findAll", query = "SELECT s FROM Servico s"),
    @NamedQuery(name = "Servico.buscarPorCpfCnpj", query = "select s FROM Servico s  where s.cpfCnpj =:cpfCnpj"),
    @NamedQuery(name = "Servico.buscarPorNome", query = "select s From Servico s  WHERE s.veiculo.pessoa.nomerazaoSocial like :nomerazaoSocial"),
    @NamedQuery(name = "Servico.buscarPorPlaca", query = "select s FROM Servico s  where s.veiculo.placa =:placa"),
    @NamedQuery(name = "Servico.buscarPorRenavam", query = "select s FROM Servico s WHERE s.veiculo.renavam =:nrenavam"),
    @NamedQuery(name = "Servico.buscarPorChassi", query = "select s FROM Servico s WHERE s.veiculo.chassi =:nchassi"),
    @NamedQuery(name = "Servico.buscarPorIntervalo", query = "select s From Servico s  WHERE s.dataEntrada >= :dtInicio AND s.dataEntrada <= :dtFim"),
    @NamedQuery(name = "Servico.buscarPorStatus", query = "select s From Servico s  WHERE s.status =:status")
    
})
public class Servico implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idServico;
    
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEntrada, dataTermino;
    @Column
    private String descricaoServico, cpfCnpj;
    @Column
    private double valor;
    @Column
    private String nomeRazaoSocial;
    
    @Column
    private String status;
    
    @Column
    private String placa;
    
    @OneToOne
    private Veiculo veiculo;

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getNomeRazaoSocial() {
        return nomeRazaoSocial;
    }

    public void setNomeRazaoSocial(String nomeRazaoSocial) {
        this.nomeRazaoSocial = nomeRazaoSocial;
    }
   
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
      
}
