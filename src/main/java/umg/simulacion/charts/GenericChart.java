package umg.simulacion.charts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import umg.simulacion.dao.VentasDao;
import umg.simulacion.enums.Month;
import umg.simulacion.model.Producto;
import umg.simulacion.model.TmpVentas;
import umg.simulacion.model.Ventas;
import umg.simulacion.pojo.ResultsImport;
import umg.simulacion.pojo.ResultsTemporal;
import umg.simulacion.pojo.ResultsVentas;
import umg.simulacion.util.BarChartObject;
import umg.simulacion.util.Column;
import umg.simulacion.util.Data;
import umg.simulacion.util.Options;
import umg.simulacion.util.Row;
import umg.simulacion.util.V;

public interface GenericChart{
	public BarChartObject generarJsonChartTmp(List<TmpVentas> ventas);
	public BarChartObject generarJsonChartVentas(List<ResultsVentas> ventas);
	public BarChartObject generarJsonChartImport(List<ResultsImport> ventas);
	public BarChartObject tempChart(Integer desde, Integer mes);
}

@Service
class GenericChartImpl implements GenericChart {
	
	@Autowired
	private VentasDao ventasDao;
	
	public BarChartObject generarJsonChartTmp(List<TmpVentas> ventas){
		BarChartObject obj = new BarChartObject();
		try {
			obj.setType("LineChart");
			obj.setOptions(new Options("Resultados Proyectados"));
			
			List<Column> cols = new ArrayList<Column>();
			cols.add(new Column("a","Año","string"));
			cols.add(new Column("v","Venta","number"));
			
			
			
			List<Row> rows = new ArrayList<Row>();
			List<V> list = new ArrayList<V>();
			for(TmpVentas venta: ventas){				 
				list.add(new V(venta.getLabel()));
				list.add(new V(venta.getValue()));
				rows.add(new Row(list));
				list = new ArrayList<V>();
				
			}
			
			obj.setData(new Data(cols,rows));
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
	public BarChartObject generarJsonChartVentas(List<ResultsVentas> ventas){
		BarChartObject obj = new BarChartObject();
		try {
			obj.setType("LineChart");
			obj.setOptions(new Options("Resultados Proyectados"));
			
			List<Column> cols = new ArrayList<Column>();
			cols.add(new Column("a","Año","string"));
			cols.add(new Column("v","Venta","number"));
			
			
			
			List<Row> rows = new ArrayList<Row>();
			List<V> list = new ArrayList<V>();
			for(ResultsVentas venta: ventas){				 
				list.add(new V(venta.getAnio()));
				list.add(new V(venta.getY()));
				rows.add(new Row(list));
				list = new ArrayList<V>();
				
			}
			
			obj.setData(new Data(cols,rows));
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public BarChartObject  generarJsonChartImport(List<ResultsImport> ventas){
		BarChartObject obj = new BarChartObject();
		try {
			obj.setType("LineChart");
			obj.setOptions(new Options("Resultados Proyectados"));
			
			List<Column> cols = new ArrayList<Column>();
			cols.add(new Column("a","Año","string"));
			cols.add(new Column("v","Venta","number"));
			
			
			
			List<Row> rows = new ArrayList<Row>();
			List<V> list = new ArrayList<V>();
			for(ResultsImport venta: ventas){				 
				list.add(new V(venta.getAnio()));
				list.add(new V(venta.getY()));
				rows.add(new Row(list));
				list = new ArrayList<V>();
				
			}
			
			obj.setData(new Data(cols,rows));
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public BarChartObject tempChart(Integer desde, Integer mes){
		BarChartObject chart = new BarChartObject();
		try {
			//Integer desde = 2013;
			chart.setType("LineChart");
			chart.setOptions(new Options("Resultados proyectados"));
			List<Producto> productos = ventasDao.listProductosByDesde(desde, mes);
			List<Column> cols = new ArrayList<Column>();
			cols.add(new Column("year","Anio","string"));
			for(Producto producto: productos){
				cols.add(new Column(producto.getIdProducto().toString(), producto.getDescripcion(),"number"));										
			}
			List<Row> rows = new ArrayList<Row>();
			/*do{*/
			 List<ResultsTemporal> tempAnios = ventasDao.findYearByParams(desde, mes);
			 for(ResultsTemporal anio: tempAnios){
				//List<ResultsVentas> ventas = ventasDao.findProductoByAño(desde);
				List<ResultsVentas> ventas = new ArrayList<ResultsVentas>();
				if(mes != null){
					ventas = ventasDao.findProductoByMes(anio.getCantidad(), mes);
				}else {
					ventas = ventasDao.findProductoByAño(anio.getCantidad());
				}
				if(ventas != null && !ventas.isEmpty()){
				
					List<V> list = new ArrayList<V>();
					//list.add(new V(desde));
					if(mes != null){
						list.add(new V(anio.getCantidad()+ " - " +Month.getByValue(mes)));
					}else{
						list.add(new V(anio.getCantidad()));
					}
					for(ResultsVentas venta:ventas){						
						list.add(new V(venta.getY()));
						
					}
					rows.add(new Row(list));
					list = new ArrayList<V>();
					//desde++;
				}
				/*else{
					desde = null;
				}*/
			 }	
			/*}while(desde != null);*/
			chart.setData(new Data(cols,rows));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chart;
	}

}
