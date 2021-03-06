/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.forit.blog.dao;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.forit.blog.dto.CategoriaDTO;
import org.forit.blog.entity.CategoriaEntity;
import org.forit.blog.exceptions.BlogException;

/**
 *
 * @author Casper
 */
public class CategoriaDAO {

  public CategoriaDTO CategoriaEntityToCategoriaDTO(CategoriaEntity cEntity) {
    return new CategoriaDTO(
            cEntity.getId(),
            cEntity.getNome(),
            cEntity.getDescrizione(),
            cEntity.getImmagine());
  }

  public CategoriaEntity CategoriaDtoToCategoriaEntity(CategoriaDTO cdto) {
    return new CategoriaEntity(
            cdto.getId(),
            cdto.getNome(),
            cdto.getDescrizione(),
            cdto.getImmagine());
  }


  public List<CategoriaDTO> getListaCategorie() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog_pu"); // nome dato in persistence.xml
    EntityManager em = emf.createEntityManager();
    TypedQuery<CategoriaEntity> query = em.createNamedQuery("categoria.selectAll", CategoriaEntity.class);
    List<CategoriaEntity> list = query.getResultList();
    List<CategoriaDTO> cDTO = list.stream().map(cEnt -> {
      return this.CategoriaEntityToCategoriaDTO(cEnt);
    }).collect(Collectors.toList());
    em.close();
    emf.close();

    return cDTO;
  }

  public CategoriaDTO loadCategoria(long id) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog_pu"); // nome dato in persistence.xml
    EntityManager em = emf.createEntityManager();
    CategoriaEntity cEntity = em.find(CategoriaEntity.class, id);
    CategoriaDTO cDTO = CategoriaEntityToCategoriaDTO(cEntity);
    PostDAO pDAO = new PostDAO();
    cDTO.setPosts(cEntity.getPosts().stream().map(pEnt -> {
      return pDAO.postEntityToPostDTO(pEnt);
    }).collect(Collectors.toList()));
    em.close();
    emf.close();

    return cDTO;
  }

    public void insertCategoria(CategoriaDTO cDTO) throws BlogException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog_pu"); // nome dato in persistence.xml
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            CategoriaEntity cEntity = new CategoriaEntity();
            cEntity.setNome(cDTO.getNome());
            cEntity.setDescrizione(cDTO.getDescrizione());
            cEntity.setImmagine(cDTO.getImmagine());

            em.persist(cEntity);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new BlogException(ex);
        } finally {
            em.close();
            emf.close();
        }
    }

    public void deleteCategoria(long id) throws BlogException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog_pu"); // nome dato in persistence.xml
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            CategoriaEntity categoria = em.find(CategoriaEntity.class, id);
            em.remove(categoria);

            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new BlogException(ex);
        } finally {
            em.close();
            emf.close();
        }
    }
}
