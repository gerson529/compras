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
public class TesteDeletar {
    public static void main(String[] args) throws Exception{
        // id do Material  a ser deletado
        Integer id = Integer.parseInt("1");
        
        MaterialDAO dao = new MaterialDAO();
        Material Material = dao.consultarPorId(id);
        
        if(dao.excluir(Material)){
            System.out.println("Material deletado com sucesso!");
        } else{
            System.out.println("Erro ao tentar deletar Material!");
        }
    }
}
