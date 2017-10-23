package umg.simulacion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Transient;

import umg.simulacion.pojo.ResultsImport;

@Entity
@NamedNativeQueries({
	/*@NamedNativeQuery(name ="Import.NativeQuerySum",
					  query = "SELECT SUM(X) X, SUM(Y) Y, SUM(X2) X2, SUM(Y2) Y2, SUM(XY) XY, count(*) N "
				 			  +"FROM(SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, " 
				 			  +"T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY " 
				 			  +"FROM (SELECT EXTRACT(YEAR FROM FECHA) ANIO,SUM(TOTAL) TOTAL " 
				 			  +"FROM VENTAS "
				 			  +"WHERE EXTRACT(YEAR FROM FECHA) >= ? "
				 			  +"GROUP BY EXTRACT(YEAR FROM FECHA) " 
				 			  +"ORDER BY EXTRACT(YEAR FROM FECHA) ASC ) T )",
				 			  resultSetMapping = "ResultsImport")*/
	@NamedNativeQuery(name = "Import.NativeQuerySum",
		 	  query = "SELECT SUM(X) X, SUM(Y) Y, SUM(X2) X2, SUM(Y2) Y2, SUM(XY) XY, count(*) N, NULL ANIO, NULL MES "+
		 			  " FROM(SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "
		 			  + " T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY "
		 			  + "FROM (SELECT EXTRACT(YEAR FROM FECHA) ANIO,SUM(TOTAL*CANTIDAD) TOTAL "
		 			  + "FROM TMP_IMPORT "
		 			  +" WHERE EXTRACT(YEAR FROM FECHA) >= ? "
		 			  + "GROUP BY EXTRACT(YEAR FROM FECHA) "
		 			  + "ORDER BY EXTRACT(YEAR FROM FECHA) ASC ) T )",
		 	  resultSetMapping = "ResultsImport"
			),
@NamedNativeQuery(name = "Import.NativeQueryMesSum",
		 	  query = "SELECT SUM(X) X, SUM(Y) Y, SUM(X2) X2, SUM(Y2) Y2, SUM(XY) XY, count(*) N, NULL ANIO, NULL MES "+ 
						"FROM(SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "+ 
						"T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY "+
						"FROM (SELECT EXTRACT(YEAR FROM FECHA) ANIO,SUM(TOTAL*CANTIDAD) TOTAL "+ 
						"FROM TMP_IMPORT "+
						" WHERE EXTRACT(YEAR FROM FECHA) >= ? "+
						" AND EXTRACT(MONTH FROM FECHA) = ? "+
						"GROUP BY EXTRACT(YEAR FROM FECHA) ) T )",
		 	  resultSetMapping = "ResultsImport"
			),
			 @NamedNativeQuery(name = "Import.NativeQueryReg",
		 	  query = "SELECT SUM(X) / count(*) X, SUM(Y) / count(*) Y, SUM(X2) X2, SUM(Y2) Y2, SUM(XY) XY, count(*) N, NULL ANIO, NULL MES "+
		 			  " FROM(SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "
	 			  + " T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY "
	 			  + "FROM (SELECT EXTRACT(YEAR FROM FECHA) ANIO,SUM(TOTAL*CANTIDAD) TOTAL "
	 			  + "FROM TMP_IMPORT "
	 			  +" WHERE EXTRACT(YEAR FROM FECHA) >= ? "
	 			  + "GROUP BY EXTRACT(YEAR FROM FECHA) "
	 			  + "ORDER BY EXTRACT(YEAR FROM FECHA) ASC ) T )",
			 	  resultSetMapping = "ResultsImport"
									),
		@NamedNativeQuery(name = "Import.NativeQueryMesReg",
								 	  query = "SELECT SUM(X) / count(*) X, SUM(Y) / count(*) Y, SUM(X2) X2, SUM(Y2) Y2, SUM(XY) XY, count(*) N, NULL ANIO, NULL MES "+
								 			  " FROM(SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "
							 			  + " T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY "
							 			  + "FROM (SELECT EXTRACT(YEAR FROM FECHA) ANIO,SUM(TOTAL*CANTIDAD) TOTAL "
							 			  + "FROM TMP_IMPORT "
							 			  +" WHERE EXTRACT(YEAR FROM FECHA) >= ? "
							 			  + "AND EXTRACT (MONTH FROM FECHA) = ? "
					     	 			  + "GROUP BY EXTRACT(YEAR FROM FECHA) ) T )",
									 	  resultSetMapping = "ResultsImport"
															),
															
			@NamedNativeQuery(name = "Import.NativeQueryCount",
							  query = "SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "
								  +"T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY, null N, T.ANIO , NULL MES  "
								  +"FROM (SELECT EXTRACT(YEAR FROM FECHA) ANIO,SUM(TOTAL*CANTIDAD) TOTAL " 
									  +"FROM TMP_IMPORT  "
								  +"GROUP BY EXTRACT(YEAR FROM FECHA) " 
								  +"ORDER BY EXTRACT(YEAR FROM FECHA) ASC ) T ",
								  resultSetMapping = "ResultsImport"),
						 @NamedNativeQuery(name = "Import.NativeQueryCountMes",
										  query = "SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "+
												"T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY, null N, T.ANIO , T.MES_TEXTO MES "+  
												"FROM (SELECT TO_CHAR(FECHA,'MONTH') MES_TEXTO, EXTRACT(MONTH FROM FECHA) MES, EXTRACT(YEAR FROM FECHA) ANIO, SUM(TOTAL*CANTIDAD) TOTAL "+ 
												"FROM TMP_IMPORT "+ 
													"WHERE EXTRACT(YEAR FROM FECHA) >= ? "+
												"AND EXTRACT(MONTH FROM FECHA) = ? "+
												"GROUP BY EXTRACT(MONTH FROM FECHA),EXTRACT(YEAR FROM FECHA),TO_CHAR(FECHA,'MONTH') "+ 
												"ORDER BY EXTRACT(YEAR FROM FECHA) ASC ) T ",
											  resultSetMapping = "ResultsImport"),
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name = "ResultsImport",
						classes = {@ConstructorResult(targetClass = ResultsImport.class,
								columns = {@ColumnResult(name = "X", type = Double.class),
										   @ColumnResult(name = "Y", type = Double.class),
										   @ColumnResult(name = "X2", type = Double.class),
										   @ColumnResult(name = "Y2", type = Double.class),
										   @ColumnResult(name = "XY", type = Double.class),
										   @ColumnResult(name = "N", type = Integer.class),
										   @ColumnResult(name = "ANIO",  type = Integer.class),
										   @ColumnResult(name = "MES",  type = String.class)
						})
	})
})
@Table(name = "TMP_IMPORT")
public class TmpImport {
	
	@Id
	@SequenceGenerator(name = "bSequence", sequenceName = "SEQ_IMPORT", allocationSize = 1)
    @GeneratedValue(generator = "bSequence",strategy = GenerationType.SEQUENCE)
	@Column(name = "CORRELATIVO")
	private Integer correlativo;
	
	@Column(name = "FECHA")
	private Date fecha;
	
	@Column(name = "TOTAL")
	private Double total;
	
	@Column(name = "CANTIDAD")
	private Integer cantidad;
	
	/*@ManyToOne
	@JoinColumn(name = "ID_PRODUCTO")*/
	@Column(name = "ID_PRODUCTO")
	private Integer producto;
	
	@Transient
	private boolean edit = false;

	public TmpImport() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public TmpImport(Integer correlativo, Date fecha, Double total,
			Integer cantidad, Integer producto) {
		super();
		this.correlativo = correlativo;
		this.fecha = fecha;
		this.total = total;
		this.cantidad = cantidad;
		this.producto = producto;
	}



	public Integer getCorrelativo() {
		return correlativo;
	}

	public void setCorrelativo(Integer correlativo) {
		this.correlativo = correlativo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}



	public Integer getCantidad() {
		return cantidad;
	}



	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}



	public Integer getProducto() {
		return producto;
	}



	public void setProducto(Integer producto) {
		this.producto = producto;
	}



	public boolean isEdit() {
		return edit;
	}



	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	
	
}
