package tpespecial;

public class Main {

	public static void main(String[] args) {
		
		String archivosCamiones = "C:\\Users\\s7\\Desktop\\TPE(Prog3) Romero-Toloza\\TPE_Prog3\\datos\\camiones.csv";
		String archivosPaquetes = "C:\\Users\\s7\\Desktop\\TPE(Prog3) Romero-Toloza\\TPE_Prog3\\datos\\paquetes.csv";
		Servicios s = new Servicios(archivosCamiones, archivosPaquetes);
		
		
		
		
	
		
		System.out.println(s.Greddy());
	
		
 

	}

}
