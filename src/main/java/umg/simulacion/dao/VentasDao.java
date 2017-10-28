package umg.simulacion.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import umg.simulacion.model.Producto;
import umg.simulacion.model.TmpVentas;
import umg.simulacion.model.Ventas;
import umg.simulacion.pojo.DataResult;
import umg.simulacion.pojo.ResultsData;
import umg.simulacion.pojo.ResultsSumatoria;
import umg.simulacion.pojo.ResultsTemporal;
import umg.simulacion.pojo.ResultsVentas;

public interface VentasDao {
	public void createVenta(Ventas ventas) throws Exception;
	public void update(Ventas ventas) throws Exception;
	public List<Ventas> listAll();
	public ResultsVentas getFinalResult(String desde,String tipo, Integer mes) throws Exception;
	public List<TmpVentas> createTmp(List<TmpVentas> temporal);
	public List<ResultsVentas> generarTabla(String tipo,  String anio, Integer mes);
	public ResultsTemporal conteo() throws Exception;
	public Long getLastYear();
	public Long getLastMonth(String anio,Integer mes);
	public List<BigDecimal> getAllAvailableYear();
	public List<Producto> getProductos();
	public List<ResultsVentas> findProductoByAño(Integer desde);
	public List<Producto> listProductosByDesde(Integer desde,Integer mes);
	public List<ResultsVentas> findProductoByMes(Integer desde, Integer mes);
	
	public List<ResultsTemporal> findYearByParams(Integer desde, Integer mes);
	
	
	public DataResult executeProcedure(Double a, Double b, Integer year);
}

@Repository
@Transactional
class VentasDaoImpl implements VentasDao{
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void createVenta(Ventas ventas) throws Exception{
		try {
			entityManager.persist(ventas);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}		 
	}
	
	@Override
	public List<TmpVentas> createTmp(List<TmpVentas> temporal){
		try {
			for(TmpVentas tmp:temporal){
				entityManager.persist(tmp);
			}
			
			TypedQuery<TmpVentas> query = entityManager
					.createNamedQuery("TmpVentas.listAll",TmpVentas.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<TmpVentas>();
		}
	}

	@Override
	public List<Ventas> listAll() {
		try {
			TypedQuery<Ventas> query = entityManager
					.createNamedQuery("Ventas.listAll", Ventas.class);
			return query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ArrayList<Ventas>();
		}
	}

	@Override
	public ResultsVentas getFinalResult(String desde,String tipo, Integer mes) throws Exception {
		try {
			
			String nativeQuery = "";
			
			if(tipo.equalsIgnoreCase("M")){
				nativeQuery = "Ventas.NativeQueryMes";
			}else if(tipo.equalsIgnoreCase("Y")){				
				nativeQuery = "Ventas.NativeQueryVentas";
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
			throw new Exception(e.getMessage());
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

	@Override
	public ResultsTemporal conteo() throws Exception {
		try {
			TypedQuery<ResultsTemporal> query = entityManager
					.createNamedQuery("Ventas.NativeQueryCantidad",ResultsTemporal.class);
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Long getLastYear() {
		try {
			Query query = entityManager.createNativeQuery("SELECT MAX(FECHA) ANIO FROM (SELECT EXTRACT(YEAR FROM FECHA) FECHA FROM VENTAS GROUP BY EXTRACT(YEAR FROM FECHA))");
			BigDecimal tmp = (BigDecimal)query.getSingleResult();
			return tmp.longValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BigDecimal> getAllAvailableYear() {
		try {
			Query query = entityManager.createNativeQuery("SELECT EXTRACT(YEAR FROM FECHA) ANIO FROM VENTAS GROUP BY EXTRACT(YEAR FROM FECHA) ORDER BY EXTRACT(YEAR FROM FECHA) ASC");
			List<BigDecimal> tmp = query.getResultList();
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<BigDecimal>();
		}
	}

	@Override
	public Long getLastMonth(String anio, Integer mes) {
		try {
			//Query query = entityManager.createNativeQuery("SELECT MAX(FECHA) ANIO FROM (SELECT EXTRACT(MONTH FROM FECHA) FECHA FROM VENTAS WHERE EXTRACT(YEAR FROM FECHA) = ? GROUP BY EXTRACT(MONTH FROM FECHA))");
			Query query = entityManager.createNativeQuery("SELECT MAX(FECHA) ANIO FROM (SELECT EXTRACT(YEAR FROM FECHA) FECHA FROM VENTAS WHERE EXTRACT(YEAR FROM FECHA) >= ? AND EXTRACT(MONTH FROM FECHA) = ? GROUP BY EXTRACT(MONTH FROM FECHA), EXTRACT(YEAR FROM FECHA))");			
			query.setParameter(1, anio);
			query.setParameter(2, mes);
			BigDecimal tmp = (BigDecimal)query.getSingleResult();
			return tmp.longValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Producto> getProductos() {
		try {
			TypedQuery<Producto> query = entityManager
					.createNamedQuery("Producto.listAll",Producto.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Producto>();
		}
	}

	@Override
	public void update(Ventas ventas) throws Exception {
		try {
			entityManager.merge(ventas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ResultsVentas> findProductoByAño(Integer desde) {
		try {
			TypedQuery<ResultsVentas> query = entityManager
					.createNamedQuery("Ventas.listProductosByAnio",ResultsVentas.class);
			query.setParameter(1, desde);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ResultsVentas>();
		}
	}

	@Override
	public List<Producto> listProductosByDesde(Integer desde, Integer mes) {
		try {
			System.out.println(mes);
			TypedQuery<Producto> query = entityManager
					.createNamedQuery("Producto.listProductosDesde",Producto.class);
			query.setParameter(1, desde);
			query.setParameter(2, mes != null ? mes : 0);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Producto>();
		}
	}

	@Override
	public List<ResultsVentas> findProductoByMes(Integer desde, Integer mes) {
		try {
			TypedQuery<ResultsVentas> query = entityManager
					.createNamedQuery("Ventas.listProductosByMes",ResultsVentas.class);
			query.setParameter(1, desde);
			query.setParameter(2, mes);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ResultsVentas>();
		}
	}

	@Override
	public List<ResultsTemporal> findYearByParams(Integer desde, Integer mes) {
		try {
			TypedQuery<ResultsTemporal> query = entityManager
					.createNamedQuery("Ventas.listAniosByData",ResultsTemporal.class);
			query.setParameter(1, desde);
			query.setParameter(2, mes != null ? mes : 0);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ResultsTemporal>();
		}
	}

	@Override
	public DataResult executeProcedure(Double a, Double b, Integer year) {
		DataResult result = new DataResult();
		try {
			StoredProcedureQuery call = entityManager.createNamedStoredProcedureQuery("Ventas.callData");
			call.setParameter("P_A",  a);
			call.setParameter("P_B",  b);
			call.setParameter("P_ANIO", year);
			call.execute();
			
			TypedQuery<ResultsData> query = entityManager
					.createNamedQuery("Ventas.listData",ResultsData.class);
			
			TypedQuery<ResultsSumatoria> query2 = entityManager
					.createNamedQuery("Ventas.sumatorias",ResultsSumatoria.class);
						
			result.setData(query.getResultList());
			result.setSumatoria(query2.getSingleResult());
			

			Double r = Math.sqrt((result.getSumatoria().getyPrima2() / result.getSumatoria().getyPromedio2()));
			Double s = Math.sqrt((result.getSumatoria().getyPromedio2() / (result.getSumatoria().getN() - 2)));
			
			result.setCorrelacion(r);
			result.setErrorEstandar(s);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		
		return result;
	}
	
	
	

}
