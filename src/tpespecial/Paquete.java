package tpespecial;

public class Paquete {

	int idUnico;
	String codigoIndentificador;
	Float pesoEnKg;
	boolean contieneAlimento;
	int nivelDeUrgencia;
	
	
	
	public Paquete(String[] row) {
		
		

		this.idUnico= Integer.parseInt(row[0]);
		this.codigoIndentificador=row[1];
		this.pesoEnKg=Float.parseFloat(row[2]);
		
		if(Integer.parseInt(row[3])==0) {
			this.contieneAlimento=false;
		}else
		{this.contieneAlimento=true;}
		
		this.nivelDeUrgencia= Integer.parseInt(row[4]);
		
	}



	public int getIdUnico() {
		return idUnico;
	}



	public String getCodigoIndentificador() {
		return codigoIndentificador;
	}



	public Float getPesoEnKg() {
		return pesoEnKg;
	}



	public boolean isContieneAlimento() {
		return contieneAlimento;
	}



	public int getNivelDeUrgencia() {
		return nivelDeUrgencia;
	}
	
	@Override
	public String toString() {
		return "Paquete [idUnico=" + idUnico + ", codigoIndentificador=" + codigoIndentificador + ", pesoEnKg="
				+ pesoEnKg + ", contieneAlimento=" + contieneAlimento + ", nivelDeUrgencia=" + nivelDeUrgencia
				+"]" ;
	}
	

}
