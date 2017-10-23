package umg.simulacion.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import umg.simulacion.model.DocWord;

public interface PruebaDao {
	public void insertDoc(DocWord word) throws Exception;
	public DocWord getWordById(Integer id) throws Exception;
}

@Repository
@Transactional
class PruebaDaoImpl  implements PruebaDao{
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void insertDoc(DocWord word) throws Exception{
		try {
			entityManager.persist(word);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public DocWord getWordById(Integer id) throws Exception{
		try {
			TypedQuery<DocWord> query = entityManager
					.createNamedQuery("DocWord.findById",DocWord.class);
			query.setParameter("id", id);
			query.setMaxResults(1);
			
			return query.getSingleResult();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
