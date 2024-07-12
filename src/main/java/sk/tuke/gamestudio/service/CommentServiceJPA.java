package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws CommentException {
        entityManager.persist(comment);
    }

    @Override
    public void updateComment(int id, String comment) throws CommentException {
        Comment commentEntity = entityManager.find(Comment.class, id);
        if (commentEntity == null) {
            throw new CommentException("comment does not exist");
        }
        commentEntity.setComment(comment);
        commentEntity.setCommentedOn(new Date());
        entityManager.persist(commentEntity);
        entityManager.flush();
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return entityManager.createNamedQuery("Comment.getComments")
                .setParameter("game", game).setMaxResults(10).getResultList();
    }

    @Override
    public void reset() throws CommentException {
        entityManager.createNamedQuery("Comment.resetComment").executeUpdate();
    }
}
