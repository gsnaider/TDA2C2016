package estadisticoDeOrdenK;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public abstract class BuscadorEstadisticoDeOrdenKTest {

	protected BuscadorEstadisticoDeOrdenK buscador;
	private long start;

	private static final int N = 1000;
	private static final int MAXINT = 1000;

	@Rule
	public TestName name = new TestName();

	@Before
	public void setup(){
		buscador = buscador();
		start = System.nanoTime();
	}

	@After
	public void end() {
		System.out.println("Test " + name.getMethodName() + " tardo " + (System.nanoTime() - start) + " ns");
	}

	protected abstract BuscadorEstadisticoDeOrdenK buscador();
	

	protected Integer[] setUpConjunto(int n){
		Random random = new Random();
		Integer[] conjunto = new Integer[n];
		for (int i = 0; i < n ; i++){
			conjunto[i] = random.nextInt(MAXINT);
		}
		return conjunto;
	}
	
	@Test
	public void testRandom() {
		Integer[] conjunto = this.setUpConjunto(N);
		//Uso el ordenamiento comun para tener todos los k
		Integer[] conjuntoOrdenado = conjunto.clone();
		Arrays.sort(conjuntoOrdenado);

		for(int k = 0; k < conjunto.length; k++){
			assertEquals(buscador.buscarEstadisticoDeOrdenK(conjunto, k), conjuntoOrdenado[k]);
		}
		
	}

//TODO: Algun dia actulizar tal vez, la verdad fiaca porque en si solo sirve para el de Fuerza bruta pero bue lo dejo
//	private Map<Integer,List<Integer>> getKPosibles(Integer[] conjunto){
//		Arrays.sort(conjunto);
//		Map<Integer,List<Integer>> map = new HashMap<Integer,List<Integer>>();
//		for (int i = 0; i < conjunto.length; i++){
//			Integer actual = conjunto[i];
//			List<Integer> listActual;
//			
//			if (map.containsKey(actual)){
//				listActual = map.get(actual);
//			}else {
//				listActual = new ArrayList<Integer>();
//			}
//			
//			listActual.add(i);
//			map.put(actual,listActual);
//		}
//		return map;
//	}
//	
//	@Test
//	public void testDeprecado() {
//		Integer[] conjunto = this.setUpConjunto();
//		//Mapa para los posibles valores de k por numero del conjunto
//		Map<Integer,List<Integer>> kPosibles = getKPosibles(conjunto.clone());
//		BuscadorEstadisticoDeOrdenK fuerzaBruta = new FuerzaBruta();
//		for(int k = 0; k < conjunto.length; k++){
//			for (int c = 0; c < conjunto.length; c++){
//				if (kPosibles.get(conjunto[c]).contains(k)){
//					assertEquals(fuerzaBruta.buscarEstadisticoDeOrdenK(conjunto, k), conjunto[c]);
//				}else {
//					assertNotEquals(fuerzaBruta.buscarEstadisticoDeOrdenK(conjunto, k), conjunto[c]);
//				}
//			}
//		}
//	}
	
	@Test
	public void buscarEnArrayVacioDevuelveNull(){
		Integer[] conjunto = new Integer[0];
		assertNull(buscador.buscarEstadisticoDeOrdenK(conjunto, 1));
	}
	
	@Test
	public void buscarConKMayorALongitudDevuelveNull(){
		int longitud = 3;
		Integer[] conjunto = new Integer[longitud];
		conjunto[0] = 3;
		conjunto[1] = 5;
		conjunto[2] = 2;

		assertNull(buscador.buscarEstadisticoDeOrdenK(conjunto, 3));
	}
	

	@Test
	public void buscarConKNegativoDevuelveNull(){
		int longitud = 3;
		Integer[] conjunto = new Integer[longitud];
		conjunto[0] = 3;
		conjunto[1] = 5;
		conjunto[2] = 2;
		int k = -1;
		assertNull(buscador.buscarEstadisticoDeOrdenK(conjunto, k));
	}
	
	@Test
	public void buscarConKEntreCeroYUltimaPosicionDevuelveEstadisticoDeOrdenK(){
		Integer[] conjunto = new Integer[3];
		conjunto[0] = 3;
		conjunto[1] = 5;
		conjunto[2] = 2;
		int k = 1;
		assertEquals(buscador.buscarEstadisticoDeOrdenK(conjunto, k), new Integer(3));
	}
	
	@Test
	public void buscarEstadisticoDeOrdenKConDuplicados(){
		Integer[] conjunto = new Integer[4];
		conjunto[0] = 3;
		conjunto[1] = 5;
		conjunto[2] = 2;
		conjunto[3] = 3;
		int k = 1;
		assertEquals(buscador.buscarEstadisticoDeOrdenK(conjunto, k), new Integer(3));
		k = 2;
		assertEquals(buscador.buscarEstadisticoDeOrdenK(conjunto, k), new Integer(3));
		k = 0;
		assertEquals(buscador.buscarEstadisticoDeOrdenK(conjunto, k), new Integer(2));
		k = 3;
		assertEquals(buscador.buscarEstadisticoDeOrdenK(conjunto, k), new Integer(5));
	}
	
	@Test
	public void buscarConKIgualCeroDevuelveElMenor(){
		Integer[] conjunto = new Integer[3];
		conjunto[0] = 3;
		conjunto[1] = 5;
		conjunto[2] = 2;
		int k = 0;
		assertEquals(buscador.buscarEstadisticoDeOrdenK(conjunto, k), new Integer(2));
	}
	
	
	@Test
	public void buscarConKIgualUltimaPosicionDevuelveElMayor(){
		int longitud = 3;
		Integer[] conjunto = new Integer[longitud];
		conjunto[0] = 3;
		conjunto[1] = 5;
		conjunto[2] = 2;
		int k = longitud - 1;
		assertEquals(buscador.buscarEstadisticoDeOrdenK(conjunto, k), new Integer(5));
	}
	
}
