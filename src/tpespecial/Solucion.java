package tpespecial;

import java.util.ArrayList;
import java.util.List;

public class Solucion {
    private List<List<Paquete>> asignaciones; // una lista por camión
    private float pesoNoAsignado;
    private int candidatoConsiderados;
    public Solucion(int numCamiones) {
        asignaciones = new ArrayList<>();
        for (int i = 0; i < numCamiones; i++) asignaciones.add(new ArrayList<>());
        pesoNoAsignado = 0;
    }

	public double getPesoNoAsignado() {
		return pesoNoAsignado;
	}

	public void setPesoNoAsignado(float pesoNoAsignado) {
		this.pesoNoAsignado = pesoNoAsignado;
	}
	public List<List<Paquete>> getAsignaciones() {
		return asignaciones;
	}
	
	public void setCandidatoConsiderados(int candidatoConsiderados) {
		this.candidatoConsiderados = candidatoConsiderados;
	}
    
	@Override
	public String toString() {
		String resultado = "";
	    resultado = "1) Se muestran los camiones con sus respectivos paquetes asignados:\n\n";
	    for (int i = 0; i < asignaciones.size(); i++) {
	        resultado = resultado + "Camion " + (i+1 ) + ": " + asignaciones.get(i) + "\n";
	    }
	    
	    resultado = resultado + "\n2) Peso no asignado: " + pesoNoAsignado + " kg\n\n";
	    resultado = resultado + "3) Candidatos considerados(Total): " + candidatoConsiderados;
	    
	    return resultado;
	}
}