/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import dao.impl.CompraDAO;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import dominio.Compra;
import dominio.CompraMaterial;
import dominio.Cotacao;
import dominio.Material;

/**
 *
 * @author Gerson
 */
public class TesteAdicionarCompra {

    public static void main(String[] args) {
        //Gravando Valores nas variaveis
        String nome = ("Teste de Oliveira");
        Short status = 1;
        String data = ("01/03/2014");
        Long telefone = Long.parseLong("23121274");

        
        
        //FORMATANDO A DATA
        String auxDt = data;
        int ano = Integer.parseInt(auxDt.substring(6,10))-1900;
        int mes = Integer.parseInt(auxDt.substring(3, 5)) - 1;
        int dia = Integer.parseInt(auxDt.substring(0, 2));
        java.sql.Date dtSQL = new java.sql.Date(ano, mes, dia);

        //MONTANDO O OBJETO
        Compra compra = new Compra();
        compra.setStatus(status);
        compra.setDataPrazo(dtSQL);
        
        Set materiais = new HashSet(0);
        
        materiais.add(new CompraMaterial(new Material(2), compra,10f ));
        materiais.add(new CompraMaterial(new Material(5), compra,13.4f ));
        compra.setCompraMaterials(materiais);
        
        
        Set cotacao = new HashSet(0);
        
        cotacao.add(new Cotacao(compra, 1,null ));
        cotacao.add(new Cotacao(compra, 3,null ));
        compra.setCotacaos(cotacao);
        //compra.setDatanascimento(dtSQL);
        
        
//        Set tmp_materiais = compra.getCompraMaterials();
//        for (Iterator iterator2 = 
//                     tmp_materiais.iterator(); iterator2.hasNext();){
//              CompraMaterial certName = (CompraMaterial) iterator2.next(); 
//              System.out.println("Certificate: " + certName.getMaterial().getId()); 
//        }
        
        //gravar nessa conexao
        CompraDAO dao = new CompraDAO();
        if (dao.salvar(compra)) {
            System.out.println("Compra cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao tentar cadastrado Compra!");
        }
    }
}
