

package negocio;

import dominio.EntidadeDominio;



public interface ICommand {

	public String execute(EntidadeDominio entidade);	
	
}
