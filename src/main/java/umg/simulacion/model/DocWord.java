package umg.simulacion.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = "DocWord.findById",
				query = "SELECT u FROM DocWord u WHERE u.correlativo = :id ")
})
@Table(name = "DOC_WORD")
public class DocWord {
	
	@Id
	@SequenceGenerator(name = "cSequence", sequenceName = "SEQ_DOC", allocationSize = 1)
    @GeneratedValue(generator = "cSequence",strategy = GenerationType.SEQUENCE)
	@Column(name = "CORRELATIVO")
	private Integer correlativo;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ARCHIVO")
	private byte[] archivo;
	
	public DocWord() {}

	public DocWord(Integer correlativo, byte[] archivo) {
		super();
		this.correlativo = correlativo;
		this.archivo = archivo;
	}


	public Integer getCorrelativo() {
		return correlativo;
	}

	public void setCorrelativo(Integer correlativo) {
		this.correlativo = correlativo;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
	

}
