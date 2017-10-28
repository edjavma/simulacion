package umg.simulacion.pojo;

import java.util.List;

public class DataResult {
	
	private List<ResultsData> data;
	private ResultsSumatoria sumatoria;
	private Double correlacion;
	private Double errorEstandar;
	
	public DataResult() {
		// TODO Auto-generated constructor stub
	}

	public DataResult(List<ResultsData> data, ResultsSumatoria sumatoria, Double correlacion, Double errorEstandar) {
		super();
		this.data = data;
		this.sumatoria = sumatoria;
		this.correlacion = correlacion;
		this.errorEstandar = errorEstandar;
	}

	public List<ResultsData> getData() {
		return data;
	}

	public void setData(List<ResultsData> data) {
		this.data = data;
	}

	public ResultsSumatoria getSumatoria() {
		return sumatoria;
	}

	public void setSumatoria(ResultsSumatoria sumatoria) {
		this.sumatoria = sumatoria;
	}
	
	public Double getCorrelacion() {
		return correlacion;
	}
	
	public void setCorrelacion(Double correlacion) {
		this.correlacion = correlacion;
	}

	public Double getErrorEstandar() {
		return errorEstandar;
	}
	
	public void setErrorEstandar(Double errorEstandar) {
		this.errorEstandar = errorEstandar;
	}
}
