package tpespecial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Servicios {
	
	List<Paquete> paquetes = new ArrayList<>();
	List<Camion> camiones = new ArrayList<>();
	HashMap<String, Paquete> paquetesPorCodigo = new HashMap<>();
	HashMap<Boolean, List<Paquete>> paquetesAlimento = new HashMap<>();
	TreeMap<Integer, List<Paquete>> arbolNivelDeUrgencia = new TreeMap<>();
	
	
	   //Completar con las estructuras y métodos privados que se  requieran. 
	 
	    /* 
	     * Expresar la complejidad temporal del constructor. 
	     */ 
	
	
	
	
	    public Servicios(String pathCamiones, String pathPaquetes) 	{ 
		char P= 'p'; //paquetes
		char C= 'c'; //camiones 
		cargaDeDatos( pathCamiones,C);
		cargaDeDatos( pathPaquetes, P);
	 
	    } 
	 
		private void cargaDeDatos(String informacion, char tipo) {
			
	    	
	    	BufferedReader  reader= null; 
			
			String line ="";
			try{
				
				reader = new BufferedReader(new FileReader(informacion));
			
			    reader.readLine();
			    
				while((line= reader.readLine()) != null) {
					
					String [] row = line.split(";");
				
					if(tipo =='p') { //Si el tipo es de paquetes, joya cargo los paquetes
					 Paquete p = new Paquete(row);
						      
						paquetes.add(p);
						//cargamos los paquetes a los maps.
						paquetesPorCodigo.put(p.getCodigoIndentificador(), p);
						paquetesAlimento.get(p.isContieneAlimento()).add(p);
						
					}
					if(tipo=='c')  //si tipo es camiones, cargo los camiones 
					{
						Camion ca= new Camion(row);
						camiones.add(ca);
						
					}
				}
				
			}
			catch(Exception e){
				
				e.printStackTrace();
			}
					
			finally {
				
				
				try{
					
					reader.close();
					}
					
				
				catch(Exception e){
					
					e.printStackTrace();
				}
					
				
				
				
			}
			
		}

		/*  
	     * Su complejidad computacional es O(1) 
	     */ 
		public Paquete servicio1(String codigoPaquete) {
	        if (codigoPaquete == null) {
	            return null;
	        }
	        return buscarPaquetePorCodigo(codigoPaquete);
	    }

	   
	    private Paquete buscarPaquetePorCodigo(String codigoPaquete) {
	        return paquetesPorCodigo.get(codigoPaquete);
	    }
	
	    /* 
	     * O(Log n) donde n es la cantidad de paquetes de alimentos. 
	     */ 
	    public List<Paquete> servicio2(boolean contieneAlimentos) {
	        return buscarPaquetesPorAlimentos(contieneAlimentos);
	    }

	   
	    private List<Paquete> buscarPaquetesPorAlimentos(boolean contieneAlimentos) {
	        return paquetesAlimento.getOrDefault(contieneAlimentos, new ArrayList<>());
	    }
	    /* 
	     * con complejidad  O(log n + k) la k representa la cantidad de paquetes que hay en el rango determinado.
	     */ 
	    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) { 
	    	cargarArbolPorUrgencia();
	    	return paquetesPorUrgencia(urgenciaMinima, urgenciaMaxima);
	    	
	    } 
	    private void cargarArbolPorUrgencia() {
	    	
			for(Paquete p : paquetes) {
			
				
					
				if (!arbolNivelDeUrgencia.containsKey(p.nivelDeUrgencia)) {
			        arbolNivelDeUrgencia.put(  p.nivelDeUrgencia,new ArrayList<>() );
			    }
			    arbolNivelDeUrgencia.get(p.nivelDeUrgencia).add(p);
					
			}

		}
	    

	    private List<Paquete> paquetesPorUrgencia(int urgenciaMinima, int urgenciaMaxima) {
	    			
	    			
	    	
	    			  List<Paquete> paquetesEnUrgencia = new ArrayList<>();

	    			  NavigableMap<Integer, List<Paquete>> rango = arbolNivelDeUrgencia.subMap(urgenciaMinima, true, urgenciaMaxima, true);

	    			    for (List<Paquete> lista : rango.values()) {
	    			        paquetesEnUrgencia.addAll(lista);
	    			    }
	    	
	    				return paquetesEnUrgencia;
	    	
	    		}

	    /*
	     * Estrategia Backtracking: Exploramos recursivamente la asignación de cada paquete.
	     * Usamos poda por factibilidad (restricciones) y poda por optimalidad (mejor solución encontrada).
	     */
	    public Solucion backtracking() {
	        // Copia para no modificar originales
	        List<Paquete> paqs = new ArrayList<>(paquetes);
	        List<Camion> cams = new ArrayList<>(camiones);

	        Float[] capacidadesRestantes = new Float[cams.size()];
	        for (int i = 0; i < cams.size(); i++) {
	            capacidadesRestantes[i] = cams.get(i).getCapacidadMaxima();
	        }

	        Solucion mejor = new Solucion(cams.size());
	        mejor.setPesoNoAsignado(Float.MAX_VALUE);

	        int[] asignacionActual = new int[paqs.size()]; // -1 = no asignado
	        Arrays.fill(asignacionActual, -1);

	        backtrack(0, paqs, cams, capacidadesRestantes, 0, mejor, asignacionActual);
	        return mejor;
	    }

	    private void backtrack(int index, List<Paquete> paqs, List<Camion> cams, Float[] capRest, float pesoNoAsignadoActual, Solucion mejor, int[] asignacionActual) {

	        if (index == paqs.size()) {
	            if (pesoNoAsignadoActual < mejor.getPesoNoAsignado()) {
	                mejor.setPesoNoAsignado(pesoNoAsignadoActual);
	                mejor.getAsignaciones().clear();
	                for (int c = 0; c < cams.size(); c++) mejor.getAsignaciones().add(new ArrayList<>());
	                for (int i = 0; i < asignacionActual.length; i++) {
	                    int c = asignacionActual[i];
	                    if (c != -1) {
	                        mejor.getAsignaciones().get(c).add(paqs.get(i));
	                    }
	                }
	            }
	            return;
	        }

	        Paquete p = paqs.get(index);
	        double pesoRestanteMaximo = 0;
	        for (int i = index; i < paqs.size(); i++) pesoRestanteMaximo += paqs.get(i).getPesoEnKg();

	        // Poda por optimalidad
	        if (pesoNoAsignadoActual + pesoRestanteMaximo >= mejor.getPesoNoAsignado()) {
	            return;
	        }

	        // Probar asignar a cada camión
	        for (int c = 0; c < cams.size(); c++) {
	            Camion cam = cams.get(c);
	            if (p.isContieneAlimento() && !cam.isEstaRefrigerado()) continue;
	            if (capRest[c] >= p.getPesoEnKg()) {
	                // Asignar
	                capRest[c] -= p.getPesoEnKg();
	                asignacionActual[index] = c;

	                backtrack(index + 1, paqs, cams, capRest, pesoNoAsignadoActual, mejor, asignacionActual);

	                // Backtrack
	                capRest[c] += p.getPesoEnKg();
	                asignacionActual[index] = -1;
	            }
	        }
	    }
}
