package umg.simulacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umg.simulacion.dao.ImportDao;
import umg.simulacion.model.TmpImport;
import umg.simulacion.pojo.FinalResultTemp;

public interface ImportService {
	public void crearVentaTemporal(TmpImport venta) throws Exception;
	public FinalResultTemp generarValores(List<TmpImport> lista,String anio, String tipo, Integer mes) throws Exception;
	public FinalResultTemp generarValoresRegresionLineal(List<TmpImport> lista,String anio, String tipo, Integer mes) throws Exception;
}

@Service
class ImportServiceImpl implements ImportService {
	
	@Autowired
	ImportDao importDao;

	@Override
	public void crearVentaTemporal(TmpImport venta) throws Exception{
		importDao.crearVentaTemporal(venta);
	}

	@Override
	public FinalResultTemp generarValores(List<TmpImport> lista, String anio, String tipo, Integer mes)
			throws Exception {
		return importDao.generarValores(lista, anio, tipo, mes);
	}
	
	@Override
	public FinalResultTemp generarValoresRegresionLineal(List<TmpImport> lista, String anio, String tipo, Integer mes)
			throws Exception {
		return importDao.generarValoresRegresionLineal(lista, anio, tipo, mes);
	}
	
}
