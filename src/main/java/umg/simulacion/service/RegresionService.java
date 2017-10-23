package umg.simulacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umg.simulacion.dao.RegresionDao;
import umg.simulacion.pojo.ResultsVentas;

public interface RegresionService {
	public ResultsVentas getRegresionVentas(String desde, String tipo, Integer mes);
	public List<ResultsVentas> generarTabla(String tipo,  String anio, Integer mes);
}

@Service
class RegresionServiceImple implements RegresionService {
	
	@Autowired
	RegresionDao regresionDao;

	@Override
	public ResultsVentas getRegresionVentas(String desde, String tipo, Integer mes) {
		return regresionDao.getRegresionVentas(desde, tipo, mes);
	}
	
	@Override
	public List<ResultsVentas> generarTabla(String tipo,String anio, Integer mes) {
		return regresionDao.generarTabla(tipo,anio, mes);
	}

}
