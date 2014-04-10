/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle.web.util;

/**
 *
 * @author Gerson
 */
public enum OPERACAO {

    // novo registro
    ADICIONAR,
    // salvar no banco
    SALVAR,
    // selecionar um registro para alteração
    EDITAR,
    // atualizar registro no banco
    ATUALIZAR,
    // selecionar registro(s) 
    LISTAR,
    // excluir registro
    EXCLUIR;
}
