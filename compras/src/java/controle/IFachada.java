/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

/**
 *
 * @author Gerson
 */
import java.util.List;
import dominio.EntidadeDominio;

public interface IFachada {

    public String salvar(EntidadeDominio ed);

    public String alterar(EntidadeDominio ed);

    public String excluir(EntidadeDominio ed);

    public List<EntidadeDominio> consultar(EntidadeDominio ed);
}
