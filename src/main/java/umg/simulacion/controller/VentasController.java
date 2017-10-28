package umg.simulacion.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import umg.simulacion.charts.GenericChart;
import umg.simulacion.enums.Month;
import umg.simulacion.model.Producto;
import umg.simulacion.model.TmpImport;
import umg.simulacion.model.TmpVentas;
import umg.simulacion.model.Ventas;
import umg.simulacion.pojo.FinalResultTemp;
import umg.simulacion.pojo.ObjectResult;
import umg.simulacion.pojo.ResultsTemporal;
import umg.simulacion.pojo.ResultsVentas;
import umg.simulacion.service.ImportService;
import umg.simulacion.service.VentasService;
import umg.simulacion.util.BarChartObject;
import umg.simulacion.util.Column;
import umg.simulacion.util.Data;
import umg.simulacion.util.Options;
import umg.simulacion.util.V;

@RestController
public class VentasController {
	
	@Autowired
	VentasService ventasService;
	
	@Autowired
	ImportService importService;
	
	@Autowired
	GenericChart genericChart;
	
	@RequestMapping(value = "/listYear", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BigDecimal> listAllYear(){
		return ventasService.getAllAvailableYear();
	}

	@RequestMapping(value = "/crear", method = RequestMethod.POST)
	public ResponseEntity<String> crearVenta(@RequestBody Ventas ventas){
		try {	
			String response = "{\"message\":\"Datos Invalidos.\"}";
			
			if(ventas != null){
				if(ventas.getFecha() != null && ventas.getTotal() != null && ventas.getCantidad() != null && ventas.getProducto() != null){
					ventasService.createVenta(ventas);
					response = "{\"message\":\"Creado con exito.\"}";
				}					
			}
			
			return new ResponseEntity<String>(response,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@RequestMapping(value = "/modificar", method = RequestMethod.POST)
	public ResponseEntity<String> modificarVenta(@RequestBody Ventas ventas){
		try {	
			String response = "{\"message\":\"Datos Invalidos.\"}";
			
			if(ventas != null){
				if(ventas.getCorrelativo() != null && ventas.getFecha() != null && ventas.getTotal() != null && ventas.getCantidad() != null && ventas.getProducto() != null){
					ventasService.update(ventas);
					response = "{\"message\":\"Creado con exito.\"}";
				}					
			}
			
			return new ResponseEntity<String>(response,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@RequestMapping(value = "/productos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Producto> obtenerProductos(){
		return ventasService.getProductos();
	}
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Ventas> obtenerVentas(){
		return ventasService.listAll();
	}
	
	@RequestMapping(value = "/calcular", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ObjectResult> verificar(@RequestParam("anios") Integer anios, 
			@RequestParam("desde") String desde, @RequestParam("tipo") String tipo, @RequestParam(value = "mes", required = false) Integer mes){
		try {
			List<TmpVentas> temporal = new ArrayList<TmpVentas>(); 
			ResultsVentas ventas = ventasService.getFinalResult(desde,tipo,mes);
			//ResultsTemporal cantidad = ventasService.conteo();
			
			System.out.println("N: "+ventas.getN());
			System.out.println("X: "+ventas.getX());
			System.out.println("Y: "+ventas.getY());
			System.out.println("x2: "+ventas.getX2());
			System.out.println("XY: "+ventas.getXy());
			//Integer N = cantidad.getCantidad();
			Integer N = ventas.getN();
			
			/*Calcular formula b =  N*(Sumatoria XY) -  (sumatoria X)*(sumatoria Y) / N*(Sumatoria X2) - (Sumatoria X)2 */
			Double b =  ((N * ventas.getXy()) - (ventas.getX() * ventas.getY())) / ((N * ventas.getX2()) - (Math.pow(ventas.getX(), 2)));
			
			
			/*Calcular formula a = (Sumatoria Y ) - b*(Sumatoria x) / N */
			//Double a = (ventas.getY() - (b * ventas.getX()) / 5 );
			//Double a = (ventas.getY() - (b * ventas.getX())) / N;
			Double a = ((ventas.getY() * ventas.getX2()) - (ventas.getX() * ventas.getXy())) / ((N *  ventas.getX2()) - ((Math.pow(ventas.getX(), 2))));
			//Double a = (ventas.getY() / N) -  ((b * ventas.getX()) / N);
			
			
			System.out.println("B: "+b);
			System.out.println("A: "+a);
			/*Crecimiento para 3 años  c = b(n) / (sumatoria y)*/
			Double c = (b * N) / ventas.getY();
			
			c = c*100;
			
			/*Calcular y = a + bx donde x es la cantidad de años*/
			Long lastYear = ventasService.getLastYear();
			Long val = null;
			if(tipo.equalsIgnoreCase("M")){
				val = ventasService.getLastMonth(desde,mes);
			}
			
			for(int i = N+1; i<=N+anios; i++){
				Double y = a + (b*i);
				if(tipo.equalsIgnoreCase("M")){
					val = val + 1;
					temporal.add(new TmpVentas(val + " - " + Month.getNameByValue(mes),y));
				}else if (tipo.equalsIgnoreCase("Y")){
					lastYear = lastYear + 1;
					temporal.add(new TmpVentas(String.valueOf(lastYear),y));
				}
								
			}
			
		
			
			
			List<TmpVentas> ventasTemporal = ventasService.createTmp(temporal);
			List<ResultsVentas> totalVentas = ventasService.generarTabla(tipo,desde, mes);
			
			ObjectResult ob = new ObjectResult(ventasTemporal, totalVentas,null, genericChart.generarJsonChartTmp(ventasTemporal),genericChart.generarJsonChartVentas(totalVentas),genericChart.tempChart(Integer.valueOf(desde), mes),null);
			return new ResponseEntity<ObjectResult>(ob,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ObjectResult>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ObjectResult uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("desde") String desde, @RequestParam("anios") Integer anios, @RequestParam("tipo") String tipo,
			@RequestParam(value = "mes", required = false) Integer mes){
		//String response = "{\"message\" : \"error al importar archivo\"}";
		 ObjectResult resultadoFinal = new ObjectResult();
		try {
			if(file != null){
				byte [] byteArr=file.getBytes();
				InputStream inputStream = new ByteArrayInputStream(byteArr);
				
				List<TmpImport> ventas = new ArrayList<TmpImport>();
		        Workbook workbook = new XSSFWorkbook(inputStream);
		        Sheet firstSheet = workbook.getSheetAt(0);
		        Iterator<Row> iterator = firstSheet.iterator();
		        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
		        
		        while (iterator.hasNext()) {
		            Row nextRow = iterator.next();
		            Iterator<Cell> cellIterator = nextRow.cellIterator();
		            TmpImport venta = new TmpImport(); 
		            while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                switch (cell.getCellType()) {		                
		                	case Cell.CELL_TYPE_STRING:
		                		if(cell.getStringCellValue().contains("/")){
		                			System.out.println("entro: ");
		                			venta.setFecha(format.parse(cell.getStringCellValue()));
		                		}	                     
		                		
		                		break;
		                    case Cell.CELL_TYPE_NUMERIC:                    			                    	
		                        if(HSSFDateUtil.isCellDateFormatted(cell)){
		                        	venta.setFecha(cell.getDateCellValue());
		                        }else{
		                        	switch(cell.getColumnIndex()){
		                        	case 1: venta.setTotal(cell.getNumericCellValue()); break;
		                        	case 2: venta.setCantidad(Double.valueOf(cell.getNumericCellValue()).intValue());break;
		                        	case 3: venta.setProducto(Double.valueOf(cell.getNumericCellValue()).intValue());break;
		                        		
		                        	}		                        	
		                        }
		                        break;
		                }
		                
		            }
		            if(venta.getFecha() != null && venta.getTotal() != null){
		            	ventas.add(venta);
		            }		            
		        }
		        
		        FinalResultTemp result = importService.generarValores(ventas, desde, tipo,mes);
		        
		        resultadoFinal = this.executeProcess(result, desde, anios,tipo,mes); 
		      //  response = "{\"message\" : \"archivo importado correctamente\"}";
		        workbook.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultadoFinal;
		
	}
	
	
	private ObjectResult executeProcess(FinalResultTemp finalResult,String desde,Integer anios, String tipo, Integer mes){
		ObjectResult results = new ObjectResult();
		List<TmpVentas> temporal = new ArrayList<TmpVentas>(); 
		try {
			
			Integer N = finalResult.getResultsImport().getN();
			Double XY = finalResult.getResultsImport().getXy();
			Double X = finalResult.getResultsImport().getX();
			Double Y = finalResult.getResultsImport().getY();
			Double x2 = finalResult.getResultsImport().getX2();
			Double y2 = finalResult.getResultsImport().getY2();
			
			/*Calcular formula b =  N*(Sumatoria XY) -  (sumatoria X)*(sumatoria Y) / N*(Sumatoria X2) - (Sumatoria X)2 */
			Double b =  ((N * XY) - (X * Y)) / ((N * x2) - (Math.pow(X, 2)));
			
			
			/*Calcular formula a = (Sumatoria Y ) - b*(Sumatoria x) / N */
			//Double a = (ventas.getY() - (b * ventas.getX()) / 5 );
			Double a = (Y - (b * X)) / N;
			
			/*Crecimiento para 3 años  c = b(n) / (sumatoria y)*/
			Double c = (b * N) / Y;
			
			c = c*100;
			
			/*Calcular y = a + bx donde x es la cantidad de años*/
			Long lastYear = finalResult.getLastYear().longValue();
			Long val = null;
			if(tipo.equalsIgnoreCase("M")){
				val = finalResult.getMonth().longValue();
			}
			for(int i = N+1; i<=N+anios; i++){
				Double y = a + (b*i);
				if(tipo.equalsIgnoreCase("M")){
					val = val + 1;
					temporal.add(new TmpVentas(val + " - " + Month.getNameByValue(mes),y));
				}else if (tipo.equalsIgnoreCase("Y")){
					lastYear = lastYear + 1;
					temporal.add(new TmpVentas(String.valueOf(lastYear),y));
				}				
			}
			
			List<TmpVentas> ventasTemporal = ventasService.createTmp(temporal);
			results.setResultados(ventasTemporal);
			results.setResultsValues(finalResult.getValues());
			results.setBarChart(genericChart.generarJsonChartTmp(ventasTemporal));
			results.setChartFirstResult(genericChart.generarJsonChartImport(finalResult.getValues()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	@RequestMapping(value = "/prueba", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BarChartObject obtenerChart(){
		return genericChart.tempChart(2013,2);
	}
	
}
