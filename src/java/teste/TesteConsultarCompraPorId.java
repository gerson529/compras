/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package teste;

import dao.impl.CompraDAO;
import java.util.Iterator;
import java.util.Set;
import dominio.Compra;
import dominio.CompraMaterial;

/**
 *
 * @author Gerson
 */
public class TesteConsultarCompraPorId {
    public static void main(String[] args) throws Exception{
        CompraDAO dao = new CompraDAO();
        Compra compra = dao.consultarPorId(8);
        
        System.out.println(compra.getId());
        System.out.println(compra.getStatus());
        Set tmp_materiais = compra.getCompraMaterials();
        for (Iterator iterator2 = 
                     tmp_materiais.iterator(); iterator2.hasNext();){
              CompraMaterial certName = (CompraMaterial) iterator2.next(); 
              System.out.println("Material Id: " + certName.getMaterial().getId()); 
              System.out.println("Material Nome: " + certName.getMaterial().getNome()); 
        }
    }
}
