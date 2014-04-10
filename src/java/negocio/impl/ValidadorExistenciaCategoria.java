
package negocio.impl;

import dao.IDAO;
import dao.impl.CategoriaDAO;
import dominio.EntidadeDominio;



public class ValidadorExistenciaCategoria extends AbstractValidador {

	
	@Override
	public String validar(EntidadeDominio entidade) {
		IDAO dao = new CategoriaDAO();
		
		if(dao.consultar(entidade) != null){
			return "Categoria ja cadastrado";
		}else{
			
			return null;
		}
			
		
	}

}
