/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package teste;

import dao.impl.MaterialDAO;
import dominio.Material;

/**
 *
 * @author Gerson
 */
public class TesteConsultarPorId {
    public static void main(String[] args) throws Exception{
        MaterialDAO dao = new MaterialDAO();
        Material clie = dao.consultarPorId(1);
        
        System.out.println(clie.getId());
        System.out.println(clie.getNome());
        
    }
}
