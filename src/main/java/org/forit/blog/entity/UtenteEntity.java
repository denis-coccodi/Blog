/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.forit.blog.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Utente
 */
@Entity
@Table(name = "utente")
@NamedQueries({
  @NamedQuery(name = "utente.selectAll",
          query = "SELECT u from UtenteEntity u Order by u.email")
})
public class UtenteEntity implements Serializable{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // id e' generato automaticamente. Identity = campo autoincrementante
  @Column(name = "ID", unique = true, nullable = false)
  private long id;

  @Column(name = "EMAIL", unique = true, nullable = false)
  private String email;

  @Column(name = "PASSWORD", unique = false, nullable = false)
  private String password;

  @Column(name = "is_active", unique = false, nullable = false)
  private Boolean isActive;

  @Column(name = "failed_access_attempts", unique = false, nullable = true)
  private Integer failed_access_attempts;	

  @Column(name = "is_banned", unique = false, nullable = false)
  private Boolean isBanned;

  @Column(name = "date_creation", unique = false, nullable = false)
  private LocalDate dateCreation;

  @Column(name = "date_last_access", unique = false, nullable = true)
  private LocalDate dateLastAccess;
  
  @OneToOne
  @JoinColumn(name="ID_RUOLO")
  private RuoloEntity ruolo;

  public UtenteEntity() {
  }

  public UtenteEntity(long id, String email, String password, Boolean isActive, Integer failed_access_attempts, Boolean isBanned, LocalDate dateCreation, LocalDate dateLastAccess, RuoloEntity ruolo) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.isActive = isActive;
    this.failed_access_attempts = failed_access_attempts;
    this.isBanned = isBanned;
    this.dateCreation = dateCreation;
    this.dateLastAccess = dateLastAccess;
    this.ruolo = ruolo;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public Integer getFailed_access_attempts() {
    return failed_access_attempts;
  }

  public void setFailed_access_attempts(Integer failed_access_attempts) {
    this.failed_access_attempts = failed_access_attempts;
  }

  public Boolean getIsBanned() {
    return isBanned;
  }

  public void setIsBanned(Boolean isBanned) {
    this.isBanned = isBanned;
  }

  public LocalDate getDateCreation() {
    return dateCreation;
  }

  public void setDateCreation(LocalDate dateCreation) {
    this.dateCreation = dateCreation;
  }

  public LocalDate getDateLastAccess() {
    return dateLastAccess;
  }

  public void setDateLastAccess(LocalDate dateLastAccess) {
    this.dateLastAccess = dateLastAccess;
  }

  public RuoloEntity getRuolo() {
    return ruolo;
  }

  public void setRuolo(RuoloEntity ruolo) {
    this.ruolo = ruolo;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
    hash = 53 * hash + Objects.hashCode(this.email);
    hash = 53 * hash + Objects.hashCode(this.password);
    hash = 53 * hash + Objects.hashCode(this.isActive);
    hash = 53 * hash + Objects.hashCode(this.failed_access_attempts);
    hash = 53 * hash + Objects.hashCode(this.isBanned);
    hash = 53 * hash + Objects.hashCode(this.dateCreation);
    hash = 53 * hash + Objects.hashCode(this.dateLastAccess);
    hash = 53 * hash + Objects.hashCode(this.ruolo);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final UtenteEntity other = (UtenteEntity) obj;
    if (this.id != other.id) {
      return false;
    }
    if (!Objects.equals(this.email, other.email)) {
      return false;
    }
    if (!Objects.equals(this.password, other.password)) {
      return false;
    }
    if (!Objects.equals(this.isActive, other.isActive)) {
      return false;
    }
    if (!Objects.equals(this.failed_access_attempts, other.failed_access_attempts)) {
      return false;
    }
    if (!Objects.equals(this.isBanned, other.isBanned)) {
      return false;
    }
    if (!Objects.equals(this.dateCreation, other.dateCreation)) {
      return false;
    }
    if (!Objects.equals(this.dateLastAccess, other.dateLastAccess)) {
      return false;
    }
    if (!Objects.equals(this.ruolo, other.ruolo)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "UtenteEntity{" + "id=" + id + ", email=" + email + ", password=" + password + ", isActive=" + isActive + ", failed_access_attempts=" + failed_access_attempts + ", isBanned=" + isBanned + ", dateCreation=" + dateCreation + ", dateLastAccess=" + dateLastAccess + ", ruolo=" + ruolo + '}';
  }
  
}