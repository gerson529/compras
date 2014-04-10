
package dao;

import java.util.List;

import dominio.EntidadeDominio;



public interface IDAO {

	public boolean salvar(EntidadeDominio entidade);
	public boolean alterar(EntidadeDominio entidade);
	public boolean excluir(EntidadeDominio entidade);
	public List<EntidadeDominio> consultar(EntidadeDominio entidade);
	
	
}
