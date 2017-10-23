package umg.simulacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umg.simulacion.dao.PruebaDao;
import umg.simulacion.model.DocWord;

public interface PruebaService {
	public void insertDoc(DocWord word) throws Exception;
	public DocWord getWordById(Integer id) throws Exception;
}

@Service
class PruebaServiceImpl implements PruebaService{
	
	@Autowired
	PruebaDao pruebaDao;

	@Override
	public void insertDoc(DocWord word) throws Exception {
		try {
			pruebaDao.insertDoc(word);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public DocWord getWordById(Integer id) throws Exception {
		try {
			return pruebaDao.getWordById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
