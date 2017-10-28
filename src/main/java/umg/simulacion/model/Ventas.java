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
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

import umg.simulacion.pojo.ResultsData;
import umg.simulacion.pojo.ResultsSumatoria;
import umg.simulacion.pojo.ResultsTemporal;
import umg.simulacion.pojo.ResultsVentas;

@Entity
@NamedQueries({
	@NamedQuery(name = "Ventas.listAll",
				query = "SELECT v FROM Ventas v ORDER BY v.fecha ASC")
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "Ventas.NativeQueryVentas",
				 	  query = "SELECT SUM(X) X, SUM(Y) Y, SUM(X2) X2, SUM(Y2) Y2, SUM(XY) XY, count(*) N, NULL ANIO, NULL MES "+
				 			  " FROM(SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "
				 			  + " T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY "
				 			  + "FROM (SELECT EXTRACT(YEAR FROM FECHA) ANIO,SUM(TOTAL*CANTIDAD) TOTAL "
				 			  + "FROM VENTAS "
				 			  +" WHERE EXTRACT(YEAR FROM FECHA) >= ? "
				 			  + "GROUP BY EXTRACT(YEAR FROM FECHA) "
				 			  + "ORDER BY EXTRACT(YEAR FROM FECHA) ASC ) T )",
				 	  resultSetMapping = "ResultsVentas"
					),
   /*@NamedNativeQuery(name = "Ventas.NativeQueryMes",
	    		 	  query = "SELECT SUM(X) X, SUM(Y) Y, SUM(X2) X2, SUM(Y2) Y2, SUM(XY) XY, count(*) N, NULL ANIO, NULL MES  "+
				 			  " FROM(SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "
				 			  + " T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY "
				 			  + "FROM (SELECT EXTRACT(MONTH FROM FECHA) ANIO,SUM(TOTAL) TOTAL "
				 			  + "FROM VENTAS "
				 			  +" WHERE EXTRACT(YEAR FROM FECHA) = ? "
				 			  + "GROUP BY EXTRACT(MONTH FROM FECHA) "
				 			  + "ORDER BY EXTRACT(MONTH FROM FECHA) ASC ) T )",
				 	  resultSetMapping = "ResultsVentas"
					),					*/
	@NamedNativeQuery(name = "Ventas.NativeQueryMes",
	    		 	  query = "SELECT SUM(X) X, SUM(Y) Y, SUM(X2) X2, SUM(Y2) Y2, SUM(XY) XY, count(*) N, NULL ANIO, NULL MES "+ 
								"FROM(SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "+ 
								"T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY "+
								"FROM (SELECT EXTRACT(YEAR FROM FECHA) ANIO,SUM(TOTAL*CANTIDAD) TOTAL "+ 
								"FROM VENTAS "+
								" WHERE EXTRACT(YEAR FROM FECHA) >= ? "+
								" AND EXTRACT(MONTH FROM FECHA) = ? "+
								"GROUP BY EXTRACT(YEAR FROM FECHA) ) T )",
				 	  resultSetMapping = "ResultsVentas"
					),	
 @NamedNativeQuery(name = "Ventas.NativeQueryRegresion",
			 	  query = "SELECT SUM(X) / count(*) X, SUM(Y) / count(*) Y, SUM(X2) X2, SUM(Y2) Y2, SUM(XY) XY, count(*) N, NULL ANIO, NULL MES "+
			 			  " FROM(SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "
		 			  + " T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY "
		 			  + "FROM (SELECT EXTRACT(YEAR FROM FECHA) ANIO,SUM(TOTAL*CANTIDAD) TOTAL "
		 			  + "FROM VENTAS "
		 			  +" WHERE EXTRACT(YEAR FROM FECHA) >= ? "
     	 			  + "GROUP BY EXTRACT(YEAR FROM FECHA) "
		 			  + "ORDER BY EXTRACT(YEAR FROM FECHA) ASC ) T )",
				 	  resultSetMapping = "ResultsVentas"
										),
			@NamedNativeQuery(name = "Ventas.NativeQueryMesRegresion",
									 	  query = "SELECT SUM(X) / count(*) X, SUM(Y) / count(*) Y, SUM(X2) X2, SUM(Y2) Y2, SUM(XY) XY, count(*) N, NULL ANIO, NULL MES "+
									 			  " FROM(SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "
								 			  + " T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY "
								 			  + "FROM (SELECT EXTRACT(YEAR FROM FECHA) ANIO,SUM(TOTAL*CANTIDAD) TOTAL "
								 			  + "FROM VENTAS "
								 			  +" WHERE EXTRACT(YEAR FROM FECHA) >= ? "
								 			  + "AND EXTRACT (MONTH FROM FECHA) = ? "
						     	 			  + "GROUP BY EXTRACT(YEAR FROM FECHA) ) T )",
										 	  resultSetMapping = "ResultsVentas"
																),
	@NamedNativeQuery(name = "Ventas.NativeQueryCount",
					  query = "SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "
				 			  +"T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY, null N, T.ANIO , NULL MES  "
				 			  +"FROM (SELECT EXTRACT(YEAR FROM FECHA) ANIO,SUM(TOTAL*CANTIDAD) TOTAL " 
				 			  +"FROM VENTAS  "
				 			  +"GROUP BY EXTRACT(YEAR FROM FECHA) " 
				 			  +"ORDER BY EXTRACT(YEAR FROM FECHA) ASC ) T ",
				 			  resultSetMapping = "ResultsVentas"),
	/*@NamedNativeQuery(name = "Ventas.NativeQueryCountMes",
							  query = "SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "
						 			  +"T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY, null N, null ANIO, T.MES MES  "
						 			  +"FROM (SELECT TO_CHAR(FECHA,'MONTH') MES,EXTRACT(MONTH FROM FECHA) ANIO,SUM(TOTAL) TOTAL " 
						 			  +"FROM VENTAS  "
						 			  +"WHERE EXTRACT(YEAR FROM FECHA) = ? "
						 			  +"GROUP BY EXTRACT(MONTH FROM FECHA),TO_CHAR(FECHA,'MONTH') " 
						 			  +"ORDER BY EXTRACT(MONTH FROM FECHA) ASC ) T ",
						 			  resultSetMapping = "ResultsVentas"),*/
				 			 @NamedNativeQuery(name = "Ventas.NativeQueryCountMes",
							  query = "SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "+
										"T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY, null N, T.ANIO , T.MES_TEXTO MES "+  
										"FROM (SELECT TO_CHAR(FECHA,'MONTH') MES_TEXTO, EXTRACT(MONTH FROM FECHA) MES, EXTRACT(YEAR FROM FECHA) ANIO, SUM(TOTAL*CANTIDAD) TOTAL "+ 
										"FROM VENTAS "+ 
										"WHERE EXTRACT(YEAR FROM FECHA) >= ? "+
										"AND EXTRACT(MONTH FROM FECHA) = ? "+
										"GROUP BY EXTRACT(MONTH FROM FECHA),EXTRACT(YEAR FROM FECHA),TO_CHAR(FECHA,'MONTH') "+ 
										"ORDER BY EXTRACT(YEAR FROM FECHA) ASC ) T ",
						 			  resultSetMapping = "ResultsVentas"),
						 	@NamedNativeQuery(name = "Ventas.NativeQueryAllMes",
									  query = "SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "+
												"T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY, null N, T.ANIO , T.MES_TEXTO MES "+  
												"FROM (SELECT TO_CHAR(FECHA,'MONTH') MES_TEXTO, EXTRACT(MONTH FROM FECHA) MES, EXTRACT(YEAR FROM FECHA) ANIO, SUM(TOTAL*CANTIDAD) TOTAL "+ 
												"FROM VENTAS "+ 
												"WHERE EXTRACT(YEAR FROM FECHA) >= ? "+
												//"AND EXTRACT(MONTH FROM FECHA) = ? "+
												"GROUP BY EXTRACT(MONTH FROM FECHA),EXTRACT(YEAR FROM FECHA),TO_CHAR(FECHA,'MONTH') "+ 
												"ORDER BY EXTRACT(YEAR FROM FECHA),EXTRACT(MONTH FROM FECHA) ASC ) T ",
								 			  resultSetMapping = "ResultsVentas"),
	@NamedNativeQuery(name = "Ventas.NativeQueryCantidad",
					  query = "SELECT MAX(ROWNUM) CANTIDAD "
					  		+ "FROM (SELECT COUNT(*) TOTAL "
					  		+ "FROM VENTAS "
					  		+ "GROUP BY EXTRACT(YEAR FROM FECHA))",
					  resultSetMapping = "ResultsTemporal"),
    @NamedNativeQuery(name = "Ventas.NativeQueryAnio",
    				 query = "SELECT MAX(FECHA) ANIO "
    				 		+ "FROM (SELECT EXTRACT(YEAR FROM FECHA) FECHA "
    				 		+ "FROM VENTAS "
    				 		+ "GROUP BY EXTRACT(YEAR FROM FECHA)")	,
    @NamedNativeQuery(name = "Ventas.listProductosByAnio",
    					query = "SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, " 
								+"T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY, null N, T.ANIO , NULL MES "  
								+"FROM (SELECT EXTRACT(YEAR FROM V.FECHA) ANIO,SUM(V.TOTAL*V.CANTIDAD) TOTAL "
								+"FROM VENTAS V "
								+"INNER JOIN PRODUCTO P ON V.ID_PRODUCTO = P.ID_PRODUCTO "
								+"WHERE EXTRACT(YEAR FROM V.FECHA) = ? "
								+"GROUP BY EXTRACT(YEAR FROM V.FECHA),P.ID_PRODUCTO "
								+"ORDER BY EXTRACT(YEAR FROM V.FECHA),P.ID_PRODUCTO ASC ) T ",
								resultSetMapping = "ResultsVentas"),
	@NamedNativeQuery(name = "Ventas.listProductosByMes",
						query = "SELECT ROWNUM X, T.TOTAL Y, ROWNUM * ROWNUM X2, "+
										"T.TOTAL * T.TOTAL Y2, ROWNUM * T.TOTAL XY, null N, T.ANIO , T.MES_TEXTO MES "+
										"FROM (SELECT TO_CHAR(FECHA,'MONTH') MES_TEXTO, EXTRACT(MONTH FROM FECHA) MES, EXTRACT(YEAR FROM FECHA) ANIO, SUM(TOTAL*CANTIDAD) TOTAL "+ 
										"FROM VENTAS "+
                                        "INNER JOIN PRODUCTO P ON VENTAS.ID_PRODUCTO = P.ID_PRODUCTO "+
										"WHERE EXTRACT(YEAR FROM FECHA) = ? "+
										"AND EXTRACT(MONTH FROM FECHA) = ? "+
										"GROUP BY EXTRACT(MONTH FROM FECHA),EXTRACT(YEAR FROM FECHA),TO_CHAR(FECHA,'MONTH'), P.ID_PRODUCTO "+ 
										"ORDER BY EXTRACT(YEAR FROM FECHA), P.ID_PRODUCTO ASC ) T ",
			resultSetMapping = "ResultsVentas"),
			
			@NamedNativeQuery(name = "Ventas.listAniosByData",
			query = "SELECT  EXTRACT(YEAR FROM FECHA) CANTIDAD "+ 
										"FROM VENTAS "+
                                        "INNER JOIN PRODUCTO P ON VENTAS.ID_PRODUCTO = P.ID_PRODUCTO "+
										"WHERE EXTRACT(YEAR FROM FECHA) >= ?1 "+
										"AND EXTRACT(MONTH FROM FECHA) = DECODE(?2 ,0,EXTRACT(MONTH FROM FECHA),?2 ) "+
										"GROUP BY EXTRACT(YEAR FROM FECHA) "+
										"ORDER BY EXTRACT(YEAR FROM FECHA) ASC ",
resultSetMapping = "ResultsTemporal"),
	
	@NamedNativeQuery(name = "Ventas.listData",
	query = "SELECT  * FROM TMP_DATA",
	resultSetMapping = "ResultsData"),
	@NamedNativeQuery(name = "Ventas.sumatorias",
	query = "SELECT SUM(YPROMEDIO2) YPROMEDIO2, SUM(YPRIMA2) YPRIMA2, COUNT(*) N FROM TMP_DATA",
	resultSetMapping = "ResultsSumatoria")
				 	  
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name = "ResultsVentas",
						classes = {@ConstructorResult(targetClass = ResultsVentas.class,
								columns = {@ColumnResult(name = "X", type = Double.class),
										   @ColumnResult(name = "Y", type = Double.class),
										   @ColumnResult(name = "X2", type = Double.class),
										   @ColumnResult(name = "Y2", type = Double.class),
										   @ColumnResult(name = "XY", type = Double.class),
										   @ColumnResult(name = "N",  type = Integer.class),
										   @ColumnResult(name = "ANIO",  type = Integer.class),
										   @ColumnResult(name = "MES",  type = String.class)
						})
	}),
	@SqlResultSetMapping(name = "ResultsTemporal",
						 classes = {@ConstructorResult(targetClass = ResultsTemporal.class,
								 columns = {@ColumnResult(name = "CANTIDAD", type = Integer.class)							 			
						 })
			
	}),
	@SqlResultSetMapping(name = "ResultsData",
	classes = {@ConstructorResult(targetClass = ResultsData.class,
			columns = {@ColumnResult(name = "X", type = String.class),
					   @ColumnResult(name = "Y", type = Double.class),
					   @ColumnResult(name = "PROMEDIO", type = Double.class),
					   @ColumnResult(name = "YPROMEDIO", type = Double.class),
					   @ColumnResult(name = "YPROMEDIO2", type = Double.class),
					   @ColumnResult(name = "YVALOR",  type = Double.class),
					   @ColumnResult(name = "YPRIMA",  type = Double.class),
					   @ColumnResult(name = "YPRIMA2",  type = Double.class)
		})
	}),
	@SqlResultSetMapping(name = "ResultsSumatoria",
	 classes = {@ConstructorResult(targetClass = ResultsSumatoria.class,
			 columns = {@ColumnResult(name = "YPROMEDIO2", type = Double.class),
		 				@ColumnResult(name = "YPRIMA2", type = Double.class),
		 				@ColumnResult(name = "N", type = Integer.class)
	 		})

	})
	
})
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		    name = "Ventas.callData",	    
		    procedureName = "PKG_DATA.INSERT_DATA",
		    parameters = {
		        @StoredProcedureParameter(mode=javax.persistence.ParameterMode.IN, name="P_A", type=Double.class),
		        @StoredProcedureParameter(mode=javax.persistence.ParameterMode.IN, name="P_B", type=Double.class),
		        @StoredProcedureParameter(mode=javax.persistence.ParameterMode.IN, name="P_ANIO", type=Integer.class)
		    }
		)
})
@Table(name = "VENTAS")
public class Ventas {
	
	@Id
	@SequenceGenerator(name = "aSequence", sequenceName = "SEQ_VENTAS", allocationSize = 1)
    @GeneratedValue(generator = "aSequence",strategy = GenerationType.SEQUENCE)
	@Column(name = "CORRELATIVO")
	private Integer correlativo;
	
	@Column(name = "FECHA")
	private Date fecha;
	
	@Column(name = "TOTAL")
	private Double total;
	
	@Column(name = "CANTIDAD")
	private Integer cantidad;
	
	@ManyToOne
	@JoinColumn(name = "ID_PRODUCTO")
	private Producto producto;
	
	@Transient
	private boolean edit = false;
	
	public Ventas() {}
	
	

	public Ventas(Integer correlativo, Date fecha, Double total,
			Integer cantidad, Producto producto) {
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public boolean isEdit() {
		return edit;
	}
	
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	

}
