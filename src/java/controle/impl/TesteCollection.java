
package controle.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class TesteCollection {

	/** 
	 * TODO Descri��o do M�todo
	 * @param args
	 */
	public static void main(String[] args) {
		String[] nomeArray = new String[2];
		nomeArray[0] = "Rodrigo";
		nomeArray[1] = "Alice";
		
		System.out.println(nomeArray[0]);
		
		List<String> nomeList = new ArrayList<String>();
		nomeList.add("Maria");
		nomeList.add("Jo�o");
		nomeList.add("Jos�");
		
		System.out.println(nomeList.get(1));
		
		
		Map<String, List<String>> nomeMap = new HashMap<String, List<String>>();
		
		nomeMap.put("Rodrigo", nomeList);
		
		List<String> nomes = new ArrayList<String>();
		nomes.add("Carlos");
		nomes.add("Mario");
		nomes.add("Fernando");
		
		nomeMap.put("Fernanda", nomes);

		for(String l: nomeMap.get("Rodrigo"))
			System.out.println("\n"+l);
		
	}

}
