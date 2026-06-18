package tpespecial;

import java.util.ArrayList;
import java.util.List;

public class Solucion {
    private List<List<Paquete>> asignaciones; // una lista por camión
    private float pesoNoAsignado;

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
    
	@Override
	public String toString() {
	    return "Asignaciones: " + asignaciones +  "\nPeso no  asignado: " + pesoNoAsignado;
	}
}