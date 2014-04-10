
package negocio.impl;

import dominio.EntidadeDominio;
import negocio.ICommand;



public abstract class AbstractValidador implements ICommand{

	public String execute(EntidadeDominio entidade){
		return validar(entidade);
	}
	
	public abstract String validar(EntidadeDominio entidade);
}
