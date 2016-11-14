package server.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.dao.ToDoDAO;
import server.domain.ToDo;
import org.springframework.stereotype.Repository;
import server.exception.ToDoException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaToDoDAO implements ToDoDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaToDoDAO.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<ToDo> getToDoList() {
        LOGGER.debug("DAO call getCurrencyList");
        return em.createQuery("select t from ToDo t order by t.id").getResultList();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<ToDo> getToDoListFiltering(String filter) {
        Query query = em.createQuery("select t from ToDo t" +
                " where lower(t.name) like :filter or lower(t.description) like :filter order by t.id");
        query.setParameter("filter", "%" + filter.trim().toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public ToDo getToDo(Integer id) {
        LOGGER.debug("пробуем получить задачу");
        ToDo toDo = em.find(ToDo.class,id);
        if (toDo != null) {
            return toDo;
        } else {
            LOGGER.debug("Попытка получить несуществующую задачу ID={}",id);
            throw new ToDoException("Попытка получить несуществующую задачу");
        }
    }

    @Override
    public void deleteToDo(Integer id) {
        ToDo toDo = em.find(ToDo.class,id);
        if (toDo != null) {
            LOGGER.debug("Пробуем удалить задачу ID={}, NAME={}", id, toDo.getName());
            try {
                em.remove(toDo);
                em.flush();
            }
            catch (PersistenceException e) {
                LOGGER.error("Ошибка удаления задачи ID={}", id);
                throw new ToDoException("PersistenceException - Ошибка удаления задачи");
            }
        }
        else {
            LOGGER.debug("Попытка удалить несуществующую задачу ID={}", id);
            throw new ToDoException("Попытка удалить несуществующую задачу");
        }
    }

    @Override
    public ToDo addToDo(ToDo toDo) {
        LOGGER.debug("Пробуем добавить задачу");
        try {
            em.persist(toDo);
            em.flush();
            LOGGER.debug("Задача {} добавлена c ID={}", toDo.getName(), toDo.getId());
            return toDo;
        } catch (PersistenceException e) {
            LOGGER.error("Ошибка добавления задачи");
            throw new ToDoException("PersistenceException - Ошибка добавления задачи");
        }
    }

    @Override
    public ToDo updateToDo(ToDo toDo) {
        if (em.find(ToDo.class, toDo.getId()) == null) {
            LOGGER.debug("Попытка изменить несуществующую задачу");
            throw new ToDoException("Попытка изменить несуществующую задачу");
        }
        LOGGER.debug("Пытаемся изменить задачу - ID={}", toDo.getId());
        try {
            toDo = em.merge(toDo);
            em.flush();
            return toDo;
        } catch (PersistenceException e) {
            LOGGER.error("Ошибка добавления задачи");
            throw new ToDoException("PersistenceException - Ошибка изменения задачи");
        }
    }
}
