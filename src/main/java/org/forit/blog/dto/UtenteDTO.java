/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.forit.blog.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.Objects;
import org.forit.blog.serializer.LocalDateTimeDeserializer;
import org.forit.blog.serializer.LocalDateTimeSerializer;

/**
 *
 * @author Utente
 */
public class UtenteDTO {

    private long id;
    private String email;
    private String password;
    private Boolean isActive;
    private int failedAccessAttempts;
    private Boolean isBanned;
    private String image;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateCreation;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateLastAccess;
    private RuoloDTO ruolo;

    public UtenteDTO() {
    }

    public UtenteDTO(long id, String email, Boolean isActive, int failedAccessAttempts, Boolean isBanned, String image, LocalDateTime dateCreation, LocalDateTime dateLastAccess, RuoloDTO ruolo) {
        this.id = id;
        this.email = email;
        this.isActive = isActive;
        this.failedAccessAttempts = failedAccessAttempts;
        this.isBanned = isBanned;
        this.image = image;
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

    public int getFailedAccessAttempts() {
        return failedAccessAttempts;
    }

    public void setFailedAccessAttempts(int failedAccessAttempts) {
        this.failedAccessAttempts = failedAccessAttempts;
    }

    public Boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(Boolean isBanned) {
        this.isBanned = isBanned;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateCreationAsString() {
        return dateCreation.toString();
    }

    public void setDateCreationAsString(String creationDate) {
        this.dateCreation = LocalDateTime.parse(creationDate);
    }

    public LocalDateTime getDateLastAccess() {
        return dateLastAccess;
    }

    public void setDateLastAccess(LocalDateTime dateLastAccess) {
        this.dateLastAccess = dateLastAccess;
    }

    public String getDateLastAccessAsString() {
        return dateLastAccess.toString();
    }

    public void setDateLastAccessAsString(String lastAccess) {
        this.dateLastAccess = LocalDateTime.parse(lastAccess);
    }

    public RuoloDTO getRuolo() {
        return ruolo;
    }

    public void setRuolo(RuoloDTO ruolo) {
        this.ruolo = ruolo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 53 * hash + Objects.hashCode(this.email);
        hash = 53 * hash + Objects.hashCode(this.password);
        hash = 53 * hash + Objects.hashCode(this.isActive);
        hash = 53 * hash + this.failedAccessAttempts;
        hash = 53 * hash + Objects.hashCode(this.isBanned);
        hash = 53 * hash + Objects.hashCode(this.image);
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
        final UtenteDTO other = (UtenteDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.failedAccessAttempts != other.failedAccessAttempts) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.isActive, other.isActive)) {
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
        return "UtenteDTO{" + "id=" + id + ", email=" + email + ", password=" + password + ", isActive=" + isActive + ", failedAccessAttempts=" + failedAccessAttempts + ", isBanned=" + isBanned + ", image=" + image + ", dateCreation=" + dateCreation + ", dateLastAccess=" + dateLastAccess + ", ruolo=" + ruolo + '}';
    }

}
