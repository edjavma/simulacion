package umg.simulacion.pojo;

public class ResultsImport {
	
	private Double x;
	private Double y;
	private Double x2;
	private Double y2;
	private Double xy;
	private Integer n;
	private Integer anio;
	private String mes;
	
	public ResultsImport() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ResultsImport(Double x, Double y, Double x2, Double y2, Double xy,
			Integer n, Integer anio, String mes) {
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.xy = xy;
		this.n = n;
		this.anio = anio;
		this.mes = mes;
	}



	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getX2() {
		return x2;
	}

	public void setX2(Double x2) {
		this.x2 = x2;
	}

	public Double getY2() {
		return y2;
	}

	public void setY2(Double y2) {
		this.y2 = y2;
	}

	public Double getXy() {
		return xy;
	}

	public void setXy(Double xy) {
		this.xy = xy;
	}



	public Integer getN() {
		return n;
	}



	public void setN(Integer n) {
		this.n = n;
	}



	public Integer getAnio() {
		return anio;
	}



	public void setAnio(Integer anio) {
		this.anio = anio;
	}



	public String getMes() {
		return mes;
	}



	public void setMes(String mes) {
		this.mes = mes;
	}
	
	

}
