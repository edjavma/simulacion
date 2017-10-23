package umg.simulacion.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import umg.simulacion.model.TmpImport;
import umg.simulacion.pojo.FinalResultTemp;
import umg.simulacion.pojo.ResultsImport;
import umg.simulacion.pojo.ResultsVentas;

public interface ImportDao {
	public void crearVentaTemporal(TmpImport venta) throws Exception;
	public FinalResultTemp generarValores(List<TmpImport> lista,String anio, String tipo, Integer mes) throws Exception;
	public FinalResultTemp generarValoresRegresionLineal(List<TmpImport> lista,String anio, String tipo, Integer mes) throws Exception;
}

@Repository
@Transactional
class ImportDaoImpl implements ImportDao {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void crearVentaTemporal(TmpImport venta) throws Exception{
		try {
			entityManager.persist(venta);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public FinalResultTemp generarValores(List<TmpImport> lista,String anio, String tipo, Integer mes) throws Exception{
		FinalResultTemp temp = new FinalResultTemp();
		try {
			for(TmpImport imp:lista){
				entityManager.persist(imp);
			}
			
			String nativeQuery = "";
			
			if(tipo.equalsIgnoreCase("M")){
				nativeQuery = "Import.NativeQueryMesSum";
			}else if(tipo.equalsIgnoreCase("Y")){				
				nativeQuery = "Import.NativeQuerySum";
			}
			
			TypedQuery<ResultsImport> query = entityManager
					.createNamedQuery(nativeQuery,ResultsImport.class);
			query.setParameter(1, anio);
			if(tipo.equalsIgnoreCase("M")){
				query.setParameter(2, mes);
			}
			

			Query queryLastY = entityManager.createNativeQuery("SELECT MAX(FECHA) ANIO FROM (SELECT EXTRACT(YEAR FROM FECHA) FECHA FROM TMP_IMPORT GROUP BY EXTRACT(YEAR FROM FECHA))");
			BigDecimal lastYear = (BigDecimal)queryLastY.getSingleResult();
			
			if(tipo.equalsIgnoreCase("M")){
				Query queryMonth = entityManager.createNativeQuery("SELECT MAX(FECHA) ANIO FROM (SELECT EXTRACT(YEAR FROM FECHA) FECHA FROM TMP_IMPORT WHERE EXTRACT(YEAR FROM FECHA) >= ? AND EXTRACT(MONTH FROM FECHA) = ? GROUP BY EXTRACT(MONTH FROM FECHA), EXTRACT(YEAR FROM FECHA))");			
				queryMonth.setParameter(1, anio);
				queryMonth.setParameter(2, mes);
				BigDecimal tmp = (BigDecimal)queryMonth.getSingleResult();	
				
				temp.setMonth(tmp);
			}
			
		    String nativeQuery2 = "";
			
			if(tipo.equalsIgnoreCase("M")){
				nativeQuery2 = "Import.NativeQueryCountMes";
			}else if (tipo.equalsIgnoreCase("Y")){
				nativeQuery2 = "Import.NativeQueryCount";
			}
			
			TypedQuery<ResultsImport> query2 = entityManager
					.createNamedQuery(nativeQuery2,ResultsImport.class);
			if(tipo.equalsIgnoreCase("M")){
				query2.setParameter(1, anio);
				query2.setParameter(2, mes);
			}
			
			temp.setValues(query2.getResultList());
			temp.setResultsImport(query.getSingleResult());
			temp.setLastYear(lastYear);
			
			
			return temp;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public FinalResultTemp generarValoresRegresionLineal(List<TmpImport> lista,String anio, String tipo, Integer mes) throws Exception{
		FinalResultTemp temp = new FinalResultTemp();
		try {
			for(TmpImport imp:lista){
				entityManager.persist(imp);
			}
			
			String nativeQuery = "";
			
			if(tipo.equalsIgnoreCase("M")){
				nativeQuery = "Import.NativeQueryMesReg";
			}else if(tipo.equalsIgnoreCase("Y")){				
				nativeQuery = "Import.NativeQueryReg";
			}
			
			TypedQuery<ResultsImport> query = entityManager
					.createNamedQuery(nativeQuery,ResultsImport.class);
			query.setParameter(1, anio);
			if(tipo.equalsIgnoreCase("M")){
				query.setParameter(2, mes);
			}
			

			Query queryLastY = entityManager.createNativeQuery("SELECT MAX(FECHA) ANIO FROM (SELECT EXTRACT(YEAR FROM FECHA) FECHA FROM TMP_IMPORT GROUP BY EXTRACT(YEAR FROM FECHA))");
			BigDecimal lastYear = (BigDecimal)queryLastY.getSingleResult();
			
			if(tipo.equalsIgnoreCase("M")){
				Query queryMonth = entityManager.createNativeQuery("SELECT MAX(FECHA) ANIO FROM (SELECT EXTRACT(YEAR FROM FECHA) FECHA FROM TMP_IMPORT WHERE EXTRACT(YEAR FROM FECHA) >= ? AND EXTRACT(MONTH FROM FECHA) = ? GROUP BY EXTRACT(MONTH FROM FECHA), EXTRACT(YEAR FROM FECHA))");			
				queryMonth.setParameter(1, anio);
				queryMonth.setParameter(2, mes);
				BigDecimal tmp = (BigDecimal)queryMonth.getSingleResult();	
				
				temp.setMonth(tmp);
			}
			
			 String nativeQuery2 = "";
				
				if(tipo.equalsIgnoreCase("M")){
					nativeQuery2 = "Import.NativeQueryCountMes";
				}else if (tipo.equalsIgnoreCase("Y")){
					nativeQuery2 = "Import.NativeQueryCount";
				}
				
				TypedQuery<ResultsImport> query2 = entityManager
						.createNamedQuery(nativeQuery2,ResultsImport.class);
				if(tipo.equalsIgnoreCase("M")){
					query2.setParameter(1, anio);
					query2.setParameter(2, mes);
				}
				
				temp.setValues(query2.getResultList());
			
			temp.setResultsImport(query.getSingleResult());
			temp.setLastYear(lastYear);
			
			
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
}
