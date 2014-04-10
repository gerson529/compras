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
import dominio.Material;

/**
 *
 * @author Gerson
 */
public class TesteAlterarCompra {
    public static void main(String[] args) {
        //Gravando Valores nas variaveis
        String nome = ("Teste Luiz de Oliveira");
        String email = ("teste@hotmail.com");
        Double cpf = Double.parseDouble("32853159892");
        String data = ("21/08/2014");
        Long telefone = Long.parseLong("23121274");

        //FORMATANDO A DATA
        String auxDt = data;
        int ano = Integer.parseInt(auxDt.substring(6,10))-1900;
        int mes = Integer.parseInt(auxDt.substring(3, 5)) - 1;
        int dia = Integer.parseInt(auxDt.substring(0, 2));
        java.sql.Date dtSQL = new java.sql.Date(ano, mes, dia);

        //MONTANDO O OBJETO
        CompraDAO dao = new CompraDAO();
        
        Compra compra = dao.consultarPorId(16);
        
        System.out.println(compra.getId());
        System.out.println(compra.getStatus());
        
        compra.setDataPrazo(dtSQL);
        Set tmp_materiais = compra.getCompraMaterials();
        
        
        
       
        for (Iterator iterator = tmp_materiais.iterator(); iterator.hasNext();){
              CompraMaterial compra_material = (CompraMaterial) iterator.next(); 
              if(compra_material.getMaterial().getId()==2){
                  compra_material.setCompra(null);
              }else{
                  compra_material.setCompra(compra);
              }
        }
        compra.setCompraMaterials(tmp_materiais);
//        
//        for (Iterator iterator2 = compra.getCompraMaterials().iterator(); iterator2.hasNext();){
//              CompraMaterial certName = (CompraMaterial) iterator2.next(); 
//              System.out.println("Material Id: " + certName.getMaterial().getId()); 
//              System.out.println("Material Nome: " + certName.getMaterial().getNome()); 
//        }
        
        if (dao.alterar(compra)) {
            System.out.println("Dados atualizados com sucesso!");
        } else {
            System.out.println("Erro ao tentar atualizar Material!");
        }
    }
}
