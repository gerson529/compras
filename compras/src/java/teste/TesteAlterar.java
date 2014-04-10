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
public class TesteAlterar {
    public static void main(String[] args) {
        //Gravando Valores nas variaveis
        String nome = ("Teste Luiz de Oliveira");
        String email = ("teste@hotmail.com");
        Double cpf = Double.parseDouble("32853159892");
        String data = ("01/01/2014");
        Long telefone = Long.parseLong("23121274");

        //FORMATANDO A DATA
        String auxDt = data;
        int ano = Integer.parseInt(auxDt.substring(6,10))-1900;
        int mes = Integer.parseInt(auxDt.substring(3, 5)) - 1;
        int dia = Integer.parseInt(auxDt.substring(0, 2));
        java.sql.Date dtSQL = new java.sql.Date(ano, mes, dia);

        //MONTANDO O OBJETO
        Material clie = new Material();
        clie.setNome(nome);
        //clie.setDatanascimento(dtSQL);
        
        //id od Material a ser alterado
        clie.setId(1);
        
        
        //gravar nessa conexao
        MaterialDAO dao = new MaterialDAO();
        if (dao.alterar(clie)) {
            System.out.println("Dados atualizados com sucesso!");
        } else {
            System.out.println("Erro ao tentar atualizar Material!");
        }
    }
}
