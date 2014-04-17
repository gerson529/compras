
package negocio.impl;

import dao.IDAO;
import dao.impl.MaterialDAO;
import dominio.EntidadeDominio;



public class ValidadorExistenciaMaterial extends AbstractValidador {

	
	@Override
	public String validar(EntidadeDominio entidade) {
		IDAO dao = new MaterialDAO();
		
		if(dao.consultar(entidade) != null){
			return "Material ja cadastrado";
		}else{
			
			return null;
		}		
		
	}

}
