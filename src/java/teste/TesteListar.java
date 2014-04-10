/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package teste;

import dao.impl.MaterialDAO;
import java.util.Iterator;
import java.util.List;
import dominio.Material;

/**
 *
 * @author Gerson
 */
public class TesteListar {
    public static void main(String[] args) throws Exception{
        MaterialDAO dao = new MaterialDAO();
        List Materials = dao.consultar(null);
        for (Iterator<Material> it = Materials.iterator(); it.hasNext();) {
            Material Material = it.next();
            System.out.println("Cod: " + Material.getId());
            System.out.println("Nome: " + Material.getNome());
            //System.out.println("DN: "+ Material.getDatanascimento());
            System.out.println("");
        }
        System.out.println("Fim!");
        
    }
}
