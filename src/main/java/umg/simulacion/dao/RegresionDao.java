package umg.simulacion.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import umg.simulacion.pojo.ResultsVentas;

public interface RegresionDao {
	public ResultsVentas getRegresionVentas(String desde, String tipo, Integer mes);
	public List<ResultsVentas> generarTabla(String tipo,  String anio, Integer mes);
}

@Repository
@Transactional
class RegresionDaoImpl implements RegresionDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResultsVentas getRegresionVentas(String desde, String tipo, Integer mes) {
		try {
			
		    String nativeQuery = "";
			
			if(tipo.equalsIgnoreCase("M")){
				nativeQuery = "Ventas.NativeQueryMesRegresion";
			}else if(tipo.equalsIgnoreCase("Y")){				
				nativeQuery = "Ventas.NativeQueryRegresion";
			}
			
			TypedQuery<ResultsVentas> query = entityManager
					.createNamedQuery(nativeQuery,ResultsVentas.class);
			query.setParameter(1, desde);
			if(tipo.equalsIgnoreCase("M")){
				query.setParameter(2, mes);
			}
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	@Override
	public List<ResultsVentas> generarTabla(String tipo, String anio, Integer mes) {
		try {
			String nativeQuery = "";
			
			if(tipo.equalsIgnoreCase("M")){
				nativeQuery = "Ventas.NativeQueryCountMes";
			}else if (tipo.equalsIgnoreCase("Y")){
				nativeQuery = "Ventas.NativeQueryCount";
			}
			
			TypedQuery<ResultsVentas> query = entityManager
					.createNamedQuery(nativeQuery,ResultsVentas.class);
			if(tipo.equalsIgnoreCase("M")){
				query.setParameter(1, anio);
				query.setParameter(2, mes);
			}
			
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ResultsVentas>();
		}
	}

}
