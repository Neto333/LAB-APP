/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.school.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author neto
 */
@Entity
@Table(catalog = "school_db", schema = "public")
@XmlRootElement
@ApiModel(description = "Detalhes sobre a entidade School")
@NamedQueries(
        {
            @NamedQuery(name = "School.findAll", query = "SELECT s FROM School s"),
            @NamedQuery(name = "School.findBySchoolPk", query = "SELECT s FROM School s WHERE s.schoolPk = :schoolPk"),
            @NamedQuery(name = "School.findByNome", query = "SELECT s FROM School s WHERE s.nome = :nome"),
            @NamedQuery(name = "School.findByEmail", query = "SELECT s FROM School s WHERE s.email = :email"),
            @NamedQuery(name = "School.findByNSalas", query = "SELECT s FROM School s WHERE s.nSalas = :nSalas"),
            @NamedQuery(name = "School.findByProvincia", query = "SELECT s FROM School s WHERE s.provincia = :provincia")
        })
public class School implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "school_pk", nullable = false)
    private Integer schoolPk;

    @ApiModelProperty(notes = "Nome da escola")
    @Basic(optional = false)
    @NotBlank(message = "O nome da escola é obrigatório")
    @Size(max = 50, message = "O nome da escola deve ter no máximo 50 caracteres")
    @Column(nullable = false, length = 50, name= "nome")
    private String nome;

    @ApiModelProperty(notes = "Email da escola")
    @Basic(optional = false)
    @NotBlank(message = "O email da escola é obrigatório")
    @Email(regexp=".*@.*\\..*",message = "Digite um email válido")
    @Column(nullable = false, length = 50, name="email")
    private String email;

    @ApiModelProperty(notes = "Número de sala que a escola possui")
    @Basic(optional = false)
    @Positive(message = "O número de salas deve ser um valor positivo")
    @Column(name = "n_salas", nullable = false)
    private int nSalas;

    @ApiModelProperty(notes = "Provincia na qual a escola está localizada")
    @Basic(optional = false)
    @NotBlank(message = "A província da escola é obrigatória")
    @Size(max = 70, message = "A província da escola deve ter no máximo 70 caracteres")
    @Column(nullable = false, length = 70, name="provincia")
    private String provincia;

    public School()
    {
    }

    public School(Integer schoolPk)
    {
        this.schoolPk = schoolPk;
    }

    public School(Integer schoolPk, String nome, String email, int nSalas, String provincia)
    {
        this.schoolPk = schoolPk;
        this.nome = nome;
        this.email = email;
        this.nSalas = nSalas;
        this.provincia = provincia;
    }

    public Integer getSchoolPk()
    {
        return schoolPk;
    }

    public void setSchoolPk(Integer schoolPk)
    {
        this.schoolPk = schoolPk;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getNSalas()
    {
        return nSalas;
    }

    public void setNSalas(int nSalas)
    {
        this.nSalas = nSalas;
    }

    public String getProvincia()
    {
        return provincia;
    }

    public void setProvincia(String provincia)
    {
        this.provincia = provincia;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (schoolPk != null ? schoolPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof School))
        {
            return false;
        }
        School other = (School) object;
        if ((this.schoolPk == null && other.schoolPk != null) || (this.schoolPk != null && !this.schoolPk.equals(other.schoolPk)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.example.school.entities.School[ schoolPk=" + schoolPk + " ]";
    }

}
