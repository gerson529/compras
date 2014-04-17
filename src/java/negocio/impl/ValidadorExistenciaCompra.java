/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.impl;

import dao.IDAO;
import dao.impl.CompraDAO;
import dominio.EntidadeDominio;

/**
 *
 * @author Eddy
 */
public class ValidadorExistenciaCompra extends AbstractValidador {

    @Override
    public String validar(EntidadeDominio entidade) {
                IDAO dao = new CompraDAO();
		
		if(dao.consultar(entidade) != null){
			return "Compra Cadastrada";
		}else{
			
			return null;
		}
    }    
    
    
}
