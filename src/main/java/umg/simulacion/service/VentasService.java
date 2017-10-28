package umg.simulacion.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umg.simulacion.dao.VentasDao;
import umg.simulacion.model.Producto;
import umg.simulacion.model.TmpVentas;
import umg.simulacion.model.Ventas;
import umg.simulacion.pojo.DataResult;
import umg.simulacion.pojo.ResultsData;
import umg.simulacion.pojo.ResultsTemporal;
import umg.simulacion.pojo.ResultsVentas;

public interface VentasService {
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
	
	
	public DataResult executeProcedure(Double a, Double b, Integer year);
}

@Service
class VentasServiceImpl implements VentasService{
	
	@Autowired
	VentasDao ventasDao;

	@Override
	public void createVenta(Ventas ventas) throws Exception {
		try {
			ventasDao.createVenta(ventas);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Ventas> listAll() {
		return ventasDao.listAll();
	}

	@Override
	public ResultsVentas getFinalResult(String desde,String tipo, Integer mes) throws Exception {
		return ventasDao.getFinalResult(desde, tipo, mes);
	}

	@Override
	public List<TmpVentas> createTmp(List<TmpVentas> temporal) {
		return ventasDao.createTmp(temporal);
	}

	@Override
	public List<ResultsVentas> generarTabla(String tipo,String anio, Integer mes) {
		return ventasDao.generarTabla(tipo,anio, mes);
	}

	@Override
	public ResultsTemporal conteo() throws Exception {
		return ventasDao.conteo();
	}

	@Override
	public Long getLastYear() {
		return ventasDao.getLastYear();
	}

	@Override
	public List<BigDecimal> getAllAvailableYear() {
		return ventasDao.getAllAvailableYear();
	}

	@Override
	public Long getLastMonth(String anio,Integer mes) {
		return ventasDao.getLastMonth(anio,mes);
	}

	@Override
	public List<Producto> getProductos() {
		return ventasDao.getProductos();
	}

	@Override
	public void update(Ventas ventas) throws Exception {
		ventasDao.update(ventas);
	}

	@Override
	public DataResult executeProcedure(Double a, Double b, Integer year) {
		return ventasDao.executeProcedure(a, b, year);
	}
	
	

}
