package web.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class UserDAO {

//    private final SessionFactory sessionFactory;
//
//    @Autowired
//    public UserDAO(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional(readOnly = true)
    public List<User> index() {
//        Session session = sessionFactory.getCurrentSession();
        List<User> users = entityManager.createQuery("select u from User u", User.class).getResultList();

        return users;
    }

    @Transactional(readOnly = true)
    public User show(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.get(User.class, id);
        return entityManager.find(User.class, id);
    }

    @Transactional
    public void save(User user) {
//        Session session = sessionFactory.getCurrentSession();
//        session.save(user);
        entityManager.persist(user);
    }

    @Transactional
    public void update(int id, User updatedUser) {
//        Session session = sessionFactory.getCurrentSession();
//        User userToBeUpdated = session.get(User.class,id);

        User userToBeUpdated = show(id);

        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setAge(updatedUser.getAge());
        userToBeUpdated.setEmail(updatedUser.getEmail());
        entityManager.merge(userToBeUpdated);
    }

    @Transactional
    public void delete(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        session.remove(session.get(User.class,id));
        User user = show(id);
        entityManager.remove(user);
    }
}