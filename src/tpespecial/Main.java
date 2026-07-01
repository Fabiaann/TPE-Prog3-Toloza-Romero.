package tpespecial;

import java.io.File;
public class Main {

	public static void main(String[] args) {
		
		String archivosCamiones = "datos" + File.separator + "camiones.csv";
		String archivosPaquetes = "datos" + File.separator + "paquetes.csv";
		Servicios s = new Servicios(archivosCamiones, archivosPaquetes);
		
		
		
		
	
		
        //System.out.println(s.Greedy());
		//System.out.println(s.backtracking());
		
		
		//System.out.println(s.servicio1("P002"));
		//System.out.println(s.servicio2(false));
		//System.out.println(s.servicio3(0,100));
		
 

	}

}

