package umg.simulacion.util;

import java.util.List;

public class Data {
	
	private List<Column> cols;
	private List<Row> rows;
	
	public Data() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Data(List<Column> cols, List<Row> rows) {
		super();
		this.cols = cols;
		this.rows = rows;
	}



	public List<Column> getCols() {
		return cols;
	}

	public void setCols(List<Column> cols) {
		this.cols = cols;
	}

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	
	

}
