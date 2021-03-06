/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.forit.blog.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.forit.blog.dto.CategoriaDTO;
import org.forit.blog.dto.CommentoDTO;
import org.forit.blog.dto.PostDTO;
import org.forit.blog.dto.RuoloDTO;
import org.forit.blog.dto.TagDTO;
import org.forit.blog.dto.UtenteDTO;
import org.forit.blog.entity.CategoriaEntity;
import org.forit.blog.entity.PostEntity;
import org.forit.blog.entity.RuoloEntity;
import org.forit.blog.entity.TagEntity;
import org.forit.blog.entity.UtenteEntity;
import org.forit.blog.exceptions.BlogException;

/**
 *
 * @author Utente
 */
public class PostDAO {

    public PostDTO postEntityToPostDTO(PostEntity pEntity) {

        CategoriaDTO cDTO = null;
        UtenteDTO uDTO = null;

        if (pEntity.getCategoria() != null) {
            cDTO = new CategoriaDTO(
                    pEntity.getCategoria().getId(),
                    pEntity.getCategoria().getNome(),
                    pEntity.getCategoria().getDescrizione(),
                    pEntity.getCategoria().getImmagine());
        }

        if (pEntity.getUtente() != null) {

            RuoloDTO rDTO = null;

            if (pEntity.getUtente().getRuolo() != null) {
                rDTO = new RuoloDTO(
                        pEntity.getUtente().getRuolo().getId(),
                        pEntity.getUtente().getRuolo().getNome()
                );
            }

            uDTO = new UtenteDTO(
                    pEntity.getUtente().getId(),
                    pEntity.getUtente().getEmail(),
                    pEntity.getUtente().getIsActive(),
                    pEntity.getUtente().getFailed_access_attempts(),
                    pEntity.getUtente().getIsBanned(),
                    pEntity.getUtente().getImage(),
                    pEntity.getUtente().getDateCreation(),
                    pEntity.getUtente().getDateLastAccess(),
                    rDTO);
        }

        PostDTO pDTO = new PostDTO(
                pEntity.getId(),
                pEntity.getTitolo(),
                pEntity.getDescrizione(),
                pEntity.getDataPost(),
                pEntity.getVisibile(),
                pEntity.getVisite(),
                pEntity.getImage(),
                cDTO,
                uDTO);

        return pDTO;
    }

    public PostEntity postDTOToPostEntity(PostDTO pDTO) {

        CategoriaEntity cEntity = null;
        UtenteEntity uEntity = null;

        if (pDTO.getCategoria() != null) {
            CategoriaDAO cDAO = new CategoriaDAO();
            cEntity = cDAO.CategoriaDtoToCategoriaEntity(pDTO.getCategoria());
        }

        if (pDTO.getUtente() != null) {

            RuoloEntity rEntity = null;

            if (pDTO.getUtente().getRuolo() != null) {
                rEntity = new RuoloEntity(
                        pDTO.getUtente().getRuolo().getId(),
                        pDTO.getUtente().getRuolo().getNome()
                );
            }

            uEntity = new UtenteEntity(
                    pDTO.getUtente().getId(),
                    pDTO.getUtente().getEmail(),
                    pDTO.getUtente().getIsActive(),
                    pDTO.getUtente().getFailedAccessAttempts(),
                    pDTO.getUtente().getIsBanned(),
                    pDTO.getUtente().getImage(),
                    pDTO.getUtente().getDateCreation(),
                    pDTO.getUtente().getDateLastAccess(),
                    rEntity);
        }

        PostEntity postEntity = new PostEntity(
                pDTO.getId(),
                pDTO.getTitolo(),
                pDTO.getDescrizione(),
                pDTO.getDataPost(),
                pDTO.getVisibile(),
                pDTO.getVisite(),
                pDTO.getImage(),
                cEntity,
                uEntity);

        return postEntity;
    }

    public List<PostDTO> getListaPost() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog_pu"); // nome dato in persistence.xml
        EntityManager em = emf.createEntityManager();
        TypedQuery<PostEntity> query = em.createNamedQuery("post.selectAll", PostEntity.class);
        List<PostEntity> list = query.getResultList();
        List<PostDTO> pDTO = list.stream().map(pEnt -> {
            PostDAO pDAO = new PostDAO();
            PostDTO postDTO = pDAO.postEntityToPostDTO(pEnt);
//            CommentoDAO cDAO = new CommentoDAO();
//
//            List<CommentoDTO> cDTO = pEnt.getCommenti().stream().map(cEnt -> {
//                return cDAO.CommentoEntityToCommentoDTO(cEnt);
//            }).collect(Collectors.toList());
//            postDTO.setCommenti(cDTO);
            return postDTO;
        }).collect(Collectors.toList());
        em.close();
        emf.close();

        // Sorting
        Collections.sort(pDTO, new Comparator<PostDTO>() {
            @Override
            public int compare(PostDTO post1, PostDTO post2) {
                return post1.getDataPost().isBefore(post2.getDataPost()) ? 1 : -1;
            }
        });

        return pDTO;
    }

    public PostDTO loadPost(long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog_pu"); // nome dato in persistence.xml
        EntityManager em = emf.createEntityManager();
        PostEntity post = em.find(PostEntity.class, id);
        PostDTO pDTO = this.postEntityToPostDTO(post);
        CommentoDAO cDAO = new CommentoDAO();
        List<CommentoDTO> cDTO = post.getCommenti().stream().map(cEnt -> {
            return cDAO.CommentoEntityToCommentoDTO(cEnt);
        }).collect(Collectors.toList());
        pDTO.setCommenti(cDTO);

        TagDAO tDAO = new TagDAO();
        List<TagDTO> tDTO = post.getTags().stream().map(tEnt -> {
            return tDAO.TagEntityToTagDTO(tEnt);
        }).collect(Collectors.toList());
        pDTO.setTags(tDTO);
        em.close();
        emf.close();

        return pDTO;
    }

    public void deletePost(long id) throws BlogException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog_pu"); // nome dato in persistence.xml
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            PostEntity post = em.find(PostEntity.class, id);
            em.remove(post);

            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new BlogException(ex);
        } finally {
            em.close();
            emf.close();
        }
    }

    public long insertPost(PostDTO pDTO) throws BlogException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog_pu"); // nome dato in persistence.xml
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            CategoriaDAO cDAO = new CategoriaDAO();
            UtenteDAO uDAO = new UtenteDAO();
            PostEntity pEntity = new PostEntity();
            pEntity.setId(-1);
            pEntity.setCategoria(cDAO.CategoriaDtoToCategoriaEntity(pDTO.getCategoria()));
            pEntity.setCommenti(null);
            pEntity.setVisite(pDTO.getVisite());
            pEntity.setVisibile(pDTO.getVisibile());
            pEntity.setImage(pDTO.getImage());
            pEntity.setDataPost(pDTO.getDataPost());
            pEntity.setDescrizione(pDTO.getDescrizione());
            pEntity.setTitolo(pDTO.getTitolo());
            pEntity.setUtente(uDAO.utenteDTOToUtenteEntity(pDTO.getUtente()));

            TagDAO tDAO = new TagDAO();
            pDTO.setTags(tagsFromText(pEntity.getDescrizione()));
            // removing duplicates
            if (pDTO.getTags() != null) {
                Set<TagDTO> tagSet = new HashSet<>(pDTO.getTags());
                pDTO.setTags(new ArrayList(tagSet));

                pDTO.getTags().stream().forEach(tDTO -> {

                    if (tDAO.loadTagByName(tDTO.getNome()) == null) {
                        try {
                            tDAO.insertTag(new TagDTO(-1, tDTO.getNome()));
                        } catch (BlogException ex) {
                            System.out.println("Insert Tag Failed: " + ex.getLocalizedMessage());
                        }
                    }
                    TagDTO tagDTO = tDAO.loadTagByName(tDTO.getNome());
                    pEntity.addTag(new TagEntity(tagDTO.getId(), tagDTO.getNome()));
                });
            }

            em.persist(pEntity);
            em.flush();
            transaction.commit();
            return pEntity.getId();
        } catch (Exception ex) {
            transaction.rollback();
            throw new BlogException(ex);
        } finally {
            em.close();
            emf.close();
        }
    }

    private List<TagDTO> tagsFromText(String text) {
        List<TagDTO> tags = new ArrayList<>();
        TagDTO tag = null;
        String[] names;
        if (text != null && text != "") {
            names = text.split("#");
            for (int i = 0; i < names.length; i++) {
                if (i > 0) {
                    if (names[i].indexOf(' ') > 0) {
                        names[i] = names[i].substring(0, names[i].indexOf(' '));
                    } else {
                        names[i] = names[i].substring(0);
                    }
                    tag = new TagDTO(-1, names[i]);
                    tags.add(tag);
                }
            }
        }

        return tags;
    }

    public void increaseViewCount(long id) throws BlogException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog_pu"); // nome dato in persistence.xml
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            PostEntity pEntity = em.find(PostEntity.class, id);
            pEntity.setVisite(pEntity.getVisite() + 1);
            em.merge(pEntity);

            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new BlogException(ex);
        } finally {
            em.close();
        }
    }

    public void updateImage(String imagePath, long id) throws BlogException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blog_pu"); // nome dato in persistence.xml
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            PostEntity post = postDTOToPostEntity(loadPost(id));
            post.setImage(imagePath);
            em.merge(post);

            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new BlogException(ex);
        } finally {
            em.close();
        }
    }

}
