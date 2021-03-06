package umg.simulacion.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import umg.simulacion.charts.GenericChart;
import umg.simulacion.enums.Month;
import umg.simulacion.mapper.ObjectJson;
import umg.simulacion.mapper.Resultado;
import umg.simulacion.model.TmpImport;
import umg.simulacion.model.TmpVentas;
import umg.simulacion.pojo.DataResult;
import umg.simulacion.pojo.FinalResultTemp;
import umg.simulacion.pojo.ObjectResult;
import umg.simulacion.pojo.ResultsVentas;
import umg.simulacion.service.ImportService;
import umg.simulacion.service.RegresionService;
import umg.simulacion.service.VentasService;
import umg.simulacion.util.BarChartObject;
import umg.simulacion.util.Column;
import umg.simulacion.util.Data;
import umg.simulacion.util.Options;
import umg.simulacion.util.V;

@RestController
@RequestMapping(value = "/regresion")
public class RegresionController {
	
	@Autowired
	VentasService ventasService;
	
	@Autowired
	RegresionService regresionService;
	
	@Autowired
	ImportService importService;
	
	@Autowired
	GenericChart genericChart;
	

	@RequestMapping(value = "/calcular", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ObjectResult> verificar(@RequestParam("anios") Integer anios, 
			@RequestParam("desde") String desde, @RequestParam("tipo") String tipo, @RequestParam(value = "mes", required = false) Integer mes){
		try {
			List<TmpVentas> temporal = new ArrayList<TmpVentas>(); 
			ResultsVentas ventas = regresionService.getRegresionVentas(desde,tipo,mes);
			
			System.out.println("N: "+ventas.getN());
			System.out.println("X: "+ventas.getX());
			System.out.println("Y: "+ventas.getY());
			System.out.println("x2: "+ventas.getX2());
			System.out.println("XY: "+ventas.getXy());
			
			Integer N = ventas.getN();
			
			Double B1 = (ventas.getXy() - (N  * ventas.getX() * ventas.getY())) / (ventas.getX2() - (N * (Math.pow(ventas.getX(), 2))));   
			
			
			Double B0 = (ventas.getY() - (B1 * ventas.getX()));
			
			System.out.println("B1: "+B1);
			System.out.println("B0: "+B0);
			
			Long lastYear = ventasService.getLastYear();
			Long val = null;
			if(tipo.equalsIgnoreCase("M")){
				val = ventasService.getLastMonth(desde,mes);
			}
			for(int i = N+1; i<=N+anios; i++){
				Double y = B0 + (B1*i);
				if(tipo.equalsIgnoreCase("M")){
					val = val + 1;
					temporal.add(new TmpVentas(val + " - " + Month.getNameByValue(mes),y));
				}else if (tipo.equalsIgnoreCase("Y")){
					lastYear = lastYear + 1;
					temporal.add(new TmpVentas(String.valueOf(lastYear),y));
				}
								
			}
			
			DataResult res =  ventasService.executeProcedure(B0, B1, Integer.valueOf(desde));
			
			
			List<TmpVentas> ventasTemporal = ventasService.createTmp(temporal);
			List<ResultsVentas> totalVentas = regresionService.generarTabla(tipo,desde,mes);
			
			//ObjectResult ob = new ObjectResult(ventasTemporal, totalVentas,null,this.generarJsonChart(ventasTemporal));
			ObjectResult ob = new ObjectResult(ventasTemporal, totalVentas,null,genericChart.generarJsonChartTmp(ventasTemporal),genericChart.generarJsonChartVentas(totalVentas),genericChart.tempChart(Integer.valueOf(desde), mes), res);
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
		      /*  Workbook workbook = new XSSFWorkbook(inputStream);
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
		        }*/
				
			     
			     String prefix = FilenameUtils.getExtension(file.getOriginalFilename());
			     
			     if(prefix.equalsIgnoreCase("XLSX")){
			    	 ventas = this.getExcelFile(inputStream);
			     }else if (prefix.equalsIgnoreCase("XML")){
			    	ventas = this.getXmlFile(inputStream); 
			     }
				
				
				
		        
		        FinalResultTemp result = importService.generarValoresRegresionLineal(ventas, desde, tipo,mes);
		        
		        resultadoFinal = this.executeProcess(result, desde, anios,tipo,mes); 
		      //  response = "{\"message\" : \"archivo importado correctamente\"}";
		     
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultadoFinal;
		
	}
	
	
	private List<TmpImport> getExcelFile(InputStream inputStream){
		List<TmpImport> results = new ArrayList<TmpImport>();
		try {			
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
	            	results.add(venta);
	            }	
	        }
	        
	        workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	private List<TmpImport> getXmlFile(InputStream inputStream){
		List<TmpImport> results = new ArrayList<TmpImport>();
		try {
			
			 String line="";
			 String str="";
			 BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		        while ((line = br.readLine()) != null) 
		        {   
		            str+=line;  
		        }
		        
		        JSONObject jsondata = XML.toJSONObject(str);
		        ObjectMapper mapper = new ObjectMapper();
				ObjectJson data = mapper.readValue(jsondata.toString(), ObjectJson.class);
				
				results = data.getData().getResultado();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/*
	@RequestMapping(value = "/uploadxml", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ObjectResult uploadXml(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "desde", required = false) String desde, @RequestParam(value = "anios", required= false) Integer anios, @RequestParam(value = "tipo", required= false) String tipo,
			@RequestParam(value = "mes", required = false) Integer mes){
		//String response = "{\"message\" : \"error al importar archivo\"}";
		 ObjectResult resultadoFinal = new ObjectResult();
		 try {
			 String line="";
			 String str="";
			 byte [] byteArr=file.getBytes();
		     InputStream inputStream = new ByteArrayInputStream(byteArr);
		     BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		        while ((line = br.readLine()) != null) 
		        {   
		            str+=line;  
		        }
		        
		        JSONObject jsondata = XML.toJSONObject(str);
		        ObjectMapper mapper = new ObjectMapper();
				ObjectJson data = mapper.readValue(jsondata.toString(), ObjectJson.class);
				for(Resultado resultado: data.getData().getResultado()){
					System.out.println("Nombre: "+resultado.getNombre()+" Edad: "+resultado.getEdad()+"\n");
				}
		     
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return resultadoFinal;
	}*/
	
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
			
			Double B1 = (XY - (N  * X * Y)) / (x2 - (N * (Math.pow(X, 2))));   
			
			
			Double B0 = (Y - (B1 * X));
			
			Long lastYear = finalResult.getLastYear().longValue();
			Long val = null;
			if(tipo.equalsIgnoreCase("M")){
				val = finalResult.getMonth().longValue();
			}
			for(int i = N+1; i<=N+anios; i++){
				Double y = B0 + (B1*i);
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
			//results.setBarChart(this.generarJsonChart(ventasTemporal));
			results.setBarChart(genericChart.generarJsonChartTmp(ventasTemporal));
			results.setChartFirstResult(genericChart.generarJsonChartImport(finalResult.getValues()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/*private BarChartObject generarJsonChart(List<TmpVentas> ventas){
		BarChartObject obj = new BarChartObject();
		try {
			obj.setType("LineChart");
			obj.setOptions(new Options("Resultados Proyectados"));
			
			List<Column> cols = new ArrayList<Column>();
			cols.add(new Column("a","Año","string"));
			cols.add(new Column("v","Venta","number"));
			
			
			
			List<umg.simulacion.util.Row> rows = new ArrayList<umg.simulacion.util.Row>();
			List<V> list = new ArrayList<V>();
			for(TmpVentas venta: ventas){				 
				list.add(new V(venta.getLabel()));
				list.add(new V(venta.getValue()));
				rows.add(new umg.simulacion.util.Row(list));
				list = new ArrayList<V>();
				
			}
			
			obj.setData(new Data(cols,rows));
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}*/

}
