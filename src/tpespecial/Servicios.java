package tpespecial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Servicios {
	
	List<Paquete> paquetes = new ArrayList<>();
	List<Camion> camiones = new ArrayList<>();
	HashMap<String, Paquete> paquetesPorCodigo = new HashMap<>();
	HashMap<Boolean, List<Paquete>> paquetesAlimento = new HashMap<>();
	TreeMap<Integer, Paquete> arbolNivelDeUrgencia = new TreeMap<>();
	
	
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
	     * Expresar la complejidad temporal del servicio 2. 
	     */ 
	    public List<Paquete> servicio2(boolean contieneAlimentos) {
	        return buscarPaquetesPorAlimentos(contieneAlimentos);
	    }

	   
	    private List<Paquete> buscarPaquetesPorAlimentos(boolean contieneAlimentos) {
	        return paquetesAlimento.getOrDefault(contieneAlimentos, new ArrayList<>());
	    }
	 
	    /* 
	     * Expresar la complejidad temporal del servicio 3. 
	     */ 
	    public void /*List<Paquete>*/ servicio3(int urgenciaMinima, int urgenciaMaxima) { 
	    	
	    	
	    } 
	

}
