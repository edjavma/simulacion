package umg.simulacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "TmpVentas.listAll",
				query = "SELECT t FROM TmpVentas t ")
})
@Table(name = "TMP_VENTAS")
public class TmpVentas {
	
	@Id
	@Column(name = "ANIO")
	private String label;
	
	@Column(name = "VENTAS")
	private Double value;
	
	public TmpVentas() {
		// TODO Auto-generated constructor stub
	}

	public TmpVentas(String label, Double value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}



	
	
}
