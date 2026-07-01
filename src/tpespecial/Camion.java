package tpespecial;

public class Camion {

	int idUnico;
	String patente;
	boolean estaRefrigerado;
	Float capacidadMaxima;
	Float pesoNeto;
	
	public Camion(String[] row) {
		
		
		this.idUnico= Integer.parseInt(row[0]);
		this.patente= row[1];
		if(Integer.parseInt(row[2]) ==0) {
			this.estaRefrigerado=false;
		}else {
			this.estaRefrigerado=true;
		}
		
		this.capacidadMaxima = Float.parseFloat(row[3]);
		
		this.pesoNeto = (float) 0.0;
	}
	
	
	public int getIdUnico() {
		return idUnico;
	}


	public String getPatente() {
		return patente;
	}


	public boolean isEstaRefrigerado() {
		return estaRefrigerado;
	}


	public Float getCapacidadMaxima() {
		return capacidadMaxima;
	}
	public Float getPesoNeto() {
		return pesoNeto;
	}


	@Override
	public String toString() {
		return "Camiones [idUnico=" + idUnico + ", patente=" + patente + ", estaRefrigerado=" + estaRefrigerado
				+ ", capacidadMaxima=" + capacidadMaxima+ "]";
	}


	public void setPesoNeto(float f) {
		this.pesoNeto=f;
		
	}
	

}