package tpespecial;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Servicios {

	List<Paquete> paquetes = new ArrayList<>();
	List<Camion> camiones = new ArrayList<>();
	HashMap<String, Paquete> paquetesPorCodigo = new HashMap<>();
	HashMap<Boolean, List<Paquete>> paquetesAlimento = new HashMap<>();
	TreeMap<Integer, List<Paquete>> arbolNivelDeUrgencia = new TreeMap<>();

	//

	/*
	 * Expresar la complejidad temporal del constructor.
	 * 
	 * * Complejidad: O(n) : n representa la cantidad total de registros de los
	 * camiones y los paquetes.
	 */

	public Servicios(String pathCamiones, String pathPaquetes) {
		char P = 'p'; // paquetes
		char C = 'c'; // camiones
		paquetesAlimento.put(true, new ArrayList<>());
		paquetesAlimento.put(false, new ArrayList<>());
		cargaDeDatos(pathCamiones, C);
		cargaDeDatos(pathPaquetes, P);

	}

	/*
	 * Complejidad Computacional De carga De Datos:
	 * 
	 * O(k) : para la carga de camiones, k seria la cantidad de lineas que tiene le
	 * archivo csv.
	 * 
	 * O(k log p) : Para la carga de paquetes, k es la cantidad de lineas que tiene
	 * el archivo csv.P es la cantidad de paquetes que ya fueron cargados en el
	 * arbol.
	 * 
	 * 
	 */

	private void cargaDeDatos(String informacion, char tipo) {

		BufferedReader reader = null;

		String line = "";
		try {

			reader = new BufferedReader(new FileReader(informacion));

			reader.readLine();

			while ((line = reader.readLine()) != null) {

				String[] row = line.split(";");

				if (tipo == 'p') { // Si el tipo es de paquetes, cargo los paquetes
					Paquete p = new Paquete(row);

					paquetes.add(p);
					if (!arbolNivelDeUrgencia.containsKey(p.nivelDeUrgencia)) {
						arbolNivelDeUrgencia.put(p.nivelDeUrgencia, new ArrayList<>());
					}
					arbolNivelDeUrgencia.get(p.nivelDeUrgencia).add(p);

					// cargamos los paquetes a los maps.
					paquetesPorCodigo.put(p.getCodigoIndentificador(), p);
					paquetesAlimento.get(p.isContieneAlimento()).add(p);

				}
				if (tipo == 'c') // si tipo es camiones, cargo los camiones
				{
					Camion ca = new Camion(row);
					camiones.add(ca);

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {

			try {

				reader.close();
			}

			catch (Exception e) {

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
	 * con complejidad O(log n + k) la k representa la cantidad de paquetes que hay
	 * en el rango determinado. log n representa la búsqueda del rango en el
	 * TreeMap.
	 */
	public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {

		List<Paquete> paquetesEnUrgencia = new ArrayList<>();

		NavigableMap<Integer, List<Paquete>> rango = arbolNivelDeUrgencia.subMap(urgenciaMinima, true, urgenciaMaxima,true);

		for (List<Paquete> lista : rango.values()) {
			paquetesEnUrgencia.addAll(lista);
		}

		return paquetesEnUrgencia;

	}

	/*
	 * Estrategia Backtracking:
	 * 
	 *  <<Breve explicación de la estrategia de resolución>>
	 * 
	 *
	 * 
	 * Por cada paquete se aplica un proceso recursiva de backtracking, donde en cada nivel
	 * de la recursión se exploran todas las posibles asignaciones de un paquete dado a los camiones disponibles.
	 * Tambien se prueba no asignar el paquete a un camion.
	 * 
	 * Se utilizan restricciones de capacidad y refrigeración (los paquetes con alimentos
	 * solo pueden asignarse a camiones refrigerados), además de considerar la opción
	 * de no asignar el paquete.
	 *
	 * El objetivo es minimizar el peso total de paquetes no asignados.
	 * 
	 * 
	 * 
	 *  Complejidad computacional:
	 *  
	 *    		 O(m^n), donde n es la cantidad de paquetes y m la cantidad de camiones,
	 *     		 por que se exploran todas las combinaciones posibles.
	 * 
	 *
	 */
	public Solucion backtracking() {

		Solucion mejorSolucion = new Solucion(camiones.size());
		float[] pesoCamiones = new float[camiones.size()];
		int[] candidatosConsiderados = new int[1];
		int[] asignacionActual = new int[paquetes.size()]; // -1 = no asignado
		Arrays.fill(asignacionActual, -1);
		mejorSolucion.setPesoNoAsignado(Float.MAX_VALUE);

		backtracking(0, candidatosConsiderados, mejorSolucion, 0, asignacionActual, pesoCamiones);
		mejorSolucion.setCandidatoConsiderados(candidatosConsiderados[0]);
		return mejorSolucion;

	}

	private void backtracking(int index, int[] candidatosConsiderados, Solucion mejorSolucion,
			float pesoNoAsignadoActual, int[] asignacionActual, float[] pesoCamiones) {

		if (index == paquetes.size()) {

			if (pesoNoAsignadoActual < mejorSolucion.getPesoNoAsignado()) {

				mejorSolucion.setPesoNoAsignado(pesoNoAsignadoActual);

				mejorSolucion.getAsignaciones().clear();

				for (int camion = 0; camion < camiones.size(); camion++) {
					mejorSolucion.getAsignaciones().add(new ArrayList<>());
				}

				for (int i = 0; i < asignacionActual.length; i++) {

					int camion = asignacionActual[i];

					if (camion != -1) {
						mejorSolucion.getAsignaciones().get(camion).add(paquetes.get(i));

					}

				}
			}

			return;
		}

		Paquete paquete = paquetes.get(index);

		/* Poda : Si el pesoNoAsignadoActual es mayor o igual  al pesoNoAsignado que ya tenia hago poda,
		 *  por que ya se que por este camino no va a ser la mejor solucion.
		 */
		if (pesoNoAsignadoActual >= mejorSolucion.getPesoNoAsignado()) {
			return;
		}

		for (int c = 0; c < camiones.size(); c++) {
			candidatosConsiderados[0]++;
			Camion camion = camiones.get(c);

			boolean restriccionPeso = pesoCamiones[c] + paquete.getPesoEnKg() <= camion.getCapacidadMaxima();
			boolean restriccionAlimento = !paquete.isContieneAlimento() || camion.isEstaRefrigerado();

			if (restriccionPeso && restriccionAlimento) {

				asignacionActual[index] = c;

				pesoCamiones[c] += paquete.getPesoEnKg();

				backtracking(index + 1, candidatosConsiderados, mejorSolucion, pesoNoAsignadoActual, asignacionActual,
						pesoCamiones);

				pesoCamiones[c] -= paquete.getPesoEnKg();

				asignacionActual[index] = -1;
			}
		}

		backtracking(index + 1, candidatosConsiderados, mejorSolucion, pesoNoAsignadoActual + paquete.getPesoEnKg(),asignacionActual, pesoCamiones);

	}

	/*
	 * <<Breve explicación de la estrategia de resolución>>
	 * 
	 * Nuestra estrategia fue ordenar los paquetes de mayor a menor peso. Para cada
	 * paqutet, buscamos el camion donde entra (capacidad y refrigeracion) y que le
	 * deje menos espacio libre. Si no entra en ningun camion, queda sin asignar.
	 * 
	 * 
	 * COMPLEJIDAD DEL GREEDY:
	 * 
	 * O(n log n + n·m) n: es la cantidad de paquetes. m: es la cantidad de
	 * camiones. n*m: Es por el doble for.
	 * 
	 */

	public Solucion Greedy() {

		Solucion solucion = new Solucion(camiones.size());

		int candidatosConsiderados = 0;

		paquetes.sort((a, b) -> Double.compare(b.getPesoEnKg(), a.getPesoEnKg()));

		for (Paquete p : paquetes) {

			int mejorIndex = -1;

			double mejorEspacio = Double.MAX_VALUE;

			for (int i = 0; i < camiones.size(); i++) {

				Camion c = camiones.get(i);

				candidatosConsiderados++;

				if (esFactible(c, p)) {

					double espacioDisponible = c.getCapacidadMaxima() - (c.getPesoNeto() + p.getPesoEnKg());

					if (espacioDisponible < mejorEspacio) {
						mejorEspacio = espacioDisponible;

						mejorIndex = i;
					}
				}
			}

			if (mejorIndex != -1) {
				Camion c = camiones.get(mejorIndex);
				solucion.getAsignaciones().get(mejorIndex).add(p);
				c.setPesoNeto(c.getPesoNeto() + p.getPesoEnKg());

			} else {

				solucion.setPesoNoAsignado(solucion.getPesoNoAsignado() + p.getPesoEnKg());

			}

		}

		solucion.setCandidatoConsiderados(candidatosConsiderados);
		return solucion;
	}

	/*
	 * Complejidad Computacional de Esfactible:
	 * 
	 * O(1), por que solo compara cosas simpless.
	 * 
	 */

	private boolean esFactible(Camion c, Paquete p) {

		if ((c.getPesoNeto() + p.getPesoEnKg()) >= c.getCapacidadMaxima()) {
			return false;
		}

		if (p.contieneAlimento && !c.estaRefrigerado) {
			return false;
		}

		return true;
	}

}
