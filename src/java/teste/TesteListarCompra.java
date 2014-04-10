/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package teste;

import dao.impl.CompraDAO;
import java.util.Iterator;
import java.util.List;
import dominio.Compra;

/**
 *
 * @author Gerson
 */
public class TesteListarCompra {
    public static void main(String[] args) throws Exception{
        CompraDAO dao = new CompraDAO();
        List Compras = dao.consultar(null);
        for (Iterator<Compra> it = Compras.iterator(); it.hasNext();) {
            Compra Compra = it.next();
            System.out.println("Cod: " + Compra.getId());
            System.out.println("Status: " + Compra.getStatus());
            //System.out.println("DN: "+ Compra.getDatanascimento());
            System.out.println("");
        }
        System.out.println("Fim!");
        
    }
}
