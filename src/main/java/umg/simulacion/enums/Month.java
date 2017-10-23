package umg.simulacion.enums;

public enum Month {
	ENERO("Enero",1),
	FEBRERO("Febrero",2),
	MARZO("Marzo",3),
	ABRIL("Abril",4),
	MAYO("Mayo",5),
	JUNIO("Junio",6),
	JULIO("Julio",7),
	AGOSTO("Agosto",8),
	SEPTIEMBRE("Septiembre",9),
	OCTUBRE("Octubre",10),
	NOVIEMBRE("Noviembre",11),
	DICIEMBRE("Diciembre",12),
	DEFAULT("Error",0);

	private final String nombre;
	private  final Integer value;
	
	private Month(String nombre,Integer value) {
		this.nombre = nombre;
		this.value = value;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Integer getValue() {
		return value;
	}
	
	
	public static Month getByValue(Integer value){
		for(Month tmp: Month.values()){
			if(tmp.getValue().equals(value)){
				return tmp;
			}
		}
		return Month.DEFAULT;
	}
	
	public static String getNameByValue(Integer value){
		for(Month tmp: Month.values()){
			if(tmp.getValue().equals(value)){
				return tmp.getNombre();
			}
		}
		return Month.DEFAULT.getNombre();
	}
	
}
