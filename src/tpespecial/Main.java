package tpespecial;

public class Main {

	public static void main(String[] args) {
		
		String archivosCamiones = "C:\\Users\\s7\\Desktop\\TPE(Prog3) Romero-Toloza\\TPE_Prog3\\datos\\camiones.csv";
		String archivosPaquetes = "C:\\Users\\s7\\Desktop\\TPE(Prog3) Romero-Toloza\\TPE_Prog3\\datos\\paquetes.csv";
		Servicios s = new Servicios(archivosCamiones, archivosPaquetes);
		
		
		
		
	
		
		//PRUEBA DE QUE FUNCIONA EL "LECTOR DE CSV"
		System.out.println(s.getCamiones());
	     System.out.println(s.getPaquetes());		
		//BORRAR LOS GETERS DE LA CLASS SERVICIO.....!!!!!!!!!
		
 

	}

}
