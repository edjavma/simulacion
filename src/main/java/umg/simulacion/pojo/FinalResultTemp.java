package umg.simulacion.pojo;

import java.math.BigDecimal;
import java.util.List;

public class FinalResultTemp {
	
	private ResultsImport resultsImport;
	private List<ResultsImport> values;
	private BigDecimal lastYear;
	private BigDecimal month;
	
	public FinalResultTemp() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public FinalResultTemp(ResultsImport resultsImport,List<ResultsImport> values, BigDecimal lastYear,  BigDecimal month) {
		this.resultsImport = resultsImport;
		this.values = values;
		this.lastYear = lastYear;
		this.month = month;
	}



	public BigDecimal getLastYear() {
		return lastYear;
	}
	
	public void setLastYear(BigDecimal lastYear) {
		this.lastYear = lastYear;
	}
	
	public ResultsImport getResultsImport() {
		return resultsImport;
	}
	
	public void setResultsImport(ResultsImport resultsImport) {
		this.resultsImport = resultsImport;
	}
	
	public BigDecimal getMonth() {
		return month;
	}
	
	public void setMonth(BigDecimal month) {
		this.month = month;
	}
	
	public List<ResultsImport> getValues() {
		return values;
	}
	
	public void setValues(List<ResultsImport> values) {
		this.values = values;
	}

}
