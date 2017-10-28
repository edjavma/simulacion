package umg.simulacion.pojo;

import java.util.List;

import umg.simulacion.model.TmpVentas;
import umg.simulacion.util.BarChartObject;

public class ObjectResult {
	
	private List<TmpVentas> resultados;
	private List<ResultsVentas> values;
	private List<ResultsImport> resultsValues;
	private BarChartObject barChart;
	private BarChartObject chartFirstResult;
	private BarChartObject chartProducto;
	private DataResult dataResult;
	
	public ObjectResult() {
		// TODO Auto-generated constructor stub
	}

	public ObjectResult(List<TmpVentas> resultados, List<ResultsVentas> values,
			List<ResultsImport> resultsValues, BarChartObject barChart,
			BarChartObject chartFirstResult, BarChartObject chartProducto,DataResult dataResult) {
		super();
		this.resultados = resultados;
		this.values = values;
		this.resultsValues = resultsValues;
		this.barChart = barChart;
		this.chartFirstResult = chartFirstResult;
		this.chartProducto = chartProducto;
		this.dataResult = dataResult;
	}

	public List<TmpVentas> getResultados() {
		return resultados;
	}

	public void setResultados(List<TmpVentas> resultados) {
		this.resultados = resultados;
	}

	public List<ResultsVentas> getValues() {
		return values;
	}

	public void setValues(List<ResultsVentas> values) {
		this.values = values;
	}
	
	public List<ResultsImport> getResultsValues() {
		return resultsValues;
	}
	
	public void setResultsValues(List<ResultsImport> resultsValues) {
		this.resultsValues = resultsValues;
	}

	public BarChartObject getBarChart() {
		return barChart;
	}
	
	public void setBarChart(BarChartObject barChart) {
		this.barChart = barChart;
	}

	public BarChartObject getChartFirstResult() {
		return chartFirstResult;
	}

	public void setChartFirstResult(BarChartObject chartFirstResult) {
		this.chartFirstResult = chartFirstResult;
	}
	
	public BarChartObject getChartProducto() {
		return chartProducto;
	}
	
	public void setChartProducto(BarChartObject chartProducto) {
		this.chartProducto = chartProducto;
	}
	
	public DataResult getDataResult() {
		return dataResult;
	}
	
	public void setDataResult(DataResult dataResult) {
		this.dataResult = dataResult;
	}
}
