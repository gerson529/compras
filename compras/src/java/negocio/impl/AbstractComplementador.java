
package negocio.impl;

import dominio.EntidadeDominio;
import negocio.ICommand;



public abstract class AbstractComplementador implements ICommand{

	public String execute(EntidadeDominio entidade){
		return complementar(entidade);
	}
	
	public abstract String complementar(EntidadeDominio entidade);
}

