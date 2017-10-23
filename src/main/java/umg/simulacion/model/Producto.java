package umg.simulacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "Producto.listAll",
				query = "SELECT p FROM Producto p ")
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "Producto.listProductosDesde",
					  query = "SELECT P.* FROM VENTAS V "
								+"INNER JOIN PRODUCTO P ON V.ID_PRODUCTO = P.ID_PRODUCTO "
								+"WHERE EXTRACT(YEAR FROM V.FECHA) >= ?1 "
								+"AND EXTRACT(MONTH FROM V.FECHA) = DECODE(?2 ,0,EXTRACT(MONTH FROM V.FECHA), ?2) "
								+"GROUP BY P.DESCRIPCION,P.ID_PRODUCTO "
								+"ORDER BY P.ID_PRODUCTO ",
					  resultClass = Producto.class) 
})
@Table(name = "PRODUCTO")
public class Producto {
	
	@Id
	@Column(name = "ID_PRODUCTO")
	private Integer idProducto;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	public Producto() {
		// TODO Auto-generated constructor stub
	}

	public Producto(Integer idProducto, String descripcion) {
		super();
		this.idProducto = idProducto;
		this.descripcion = descripcion;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
