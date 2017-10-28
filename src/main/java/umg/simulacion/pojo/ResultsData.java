package umg.simulacion.pojo;

public class ResultsData {
	
	private String x;
	private Double y;
	private Double promedio;
	private Double yPromedio;
	private Double yPromedio2;
	private Double yValor;
	private Double yPrima;
	private Double yPrima2;

	public ResultsData() {
		// TODO Auto-generated constructor stub
	}

	public ResultsData(String x, Double y, Double promedio, Double yPromedio,
			Double yPromedio2, Double yValor, Double yPrima, Double yPrima2) {
		super();
		this.x = x;
		this.y = y;
		this.promedio = promedio;
		this.yPromedio = yPromedio;
		this.yPromedio2 = yPromedio2;
		this.yValor = yValor;
		this.yPrima = yPrima;
		this.yPrima2 = yPrima2;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getPromedio() {
		return promedio;
	}

	public void setPromedio(Double promedio) {
		this.promedio = promedio;
	}

	public Double getyPromedio() {
		return yPromedio;
	}

	public void setyPromedio(Double yPromedio) {
		this.yPromedio = yPromedio;
	}

	public Double getyPromedio2() {
		return yPromedio2;
	}

	public void setyPromedio2(Double yPromedio2) {
		this.yPromedio2 = yPromedio2;
	}

	public Double getyValor() {
		return yValor;
	}

	public void setyValor(Double yValor) {
		this.yValor = yValor;
	}

	public Double getyPrima() {
		return yPrima;
	}

	public void setyPrima(Double yPrima) {
		this.yPrima = yPrima;
	}

	public Double getyPrima2() {
		return yPrima2;
	}

	public void setyPrima2(Double yPrima2) {
		this.yPrima2 = yPrima2;
	}
	
	
	
}

