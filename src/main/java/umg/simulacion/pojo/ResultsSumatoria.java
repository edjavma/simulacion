package umg.simulacion.pojo;

public class ResultsSumatoria {
	
	private Double yPromedio2;
	private Double yPrima2;
	private Integer n;
	
	public ResultsSumatoria() {
		// TODO Auto-generated constructor stub
	}

	public ResultsSumatoria(Double yPromedio2, Double yPrima2, Integer n) {
		super();
		this.yPromedio2 = yPromedio2;
		this.yPrima2 = yPrima2;
		this.n = n;
	}

	public Double getyPromedio2() {
		return yPromedio2;
	}

	public void setyPromedio2(Double yPromedio2) {
		this.yPromedio2 = yPromedio2;
	}

	public Double getyPrima2() {
		return yPrima2;
	}

	public void setyPrima2(Double yPrima2) {
		this.yPrima2 = yPrima2;
	}
	
	public Integer getN() {
		return n;
	}
	
	public void setN(Integer n) {
		this.n = n;
	}

}
