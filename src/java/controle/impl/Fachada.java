/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle.impl;

import controle.IFachada;
import dao.IDAO;
import dao.impl.CategoriaDAO;
import dao.impl.CompraDAO;
import dao.impl.CotacaoDAO;
import dao.impl.MaterialDAO;
import dominio.Categoria;
import dominio.Compra;
import dominio.Cotacao;
import dominio.EntidadeDominio;
import dominio.Material;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import negocio.ICommand;
import negocio.impl.ValidadorExistenciaCategoria;
import negocio.impl.ValidadorExistenciaMaterial;

/**
 *
 * @author Gerson
 */
public class Fachada implements IFachada {

    private Map<String, IDAO> daos;
    private Map<String, List<ICommand>> rns;

    /**
     * TODO Construtor padrão/alternativo da classe
     */
    public Fachada() {
        daos = new HashMap<String, IDAO>();
        rns = new HashMap<String, List<ICommand>>();
		
		//
		// MATERIAL
		//
        daos.put(Material.class.getName(), new MaterialDAO());

        List<ICommand> rnsMaterial = new ArrayList<ICommand>();
	//rnsFuncionario.add(new ComplementadorFuncionario());
        //rnsFuncionario.add(new ValidadorCpf());
        rnsMaterial.add(new ValidadorExistenciaMaterial());

        rns.put(Material.class.getName(), rnsMaterial);
        
		//
		// CATEGORIA
		//
		daos.put(Categoria.class.getName(), new CategoriaDAO());
        List<ICommand> rnsCategoria = new ArrayList<ICommand>();
	    rnsCategoria.add(new ValidadorExistenciaCategoria());
        rns.put(Categoria.class.getName(), rnsCategoria);
		
		//
		// COMPRA
		//
		daos.put(Compra.class.getName(), new CompraDAO());
        List<ICommand> rnsCompra = new ArrayList<ICommand>();
	    //rnsCompra.add(new ValidadorExistenciaCompra());
        rns.put(Compra.class.getName(), rnsCompra);
		
		//
		// Cotacao
		//
		daos.put(Cotacao.class.getName(), new CotacaoDAO());
        List<ICommand> rnsCotacao = new ArrayList<ICommand>();
	    //rnsCompra.add(new ValidadorExistenciaCompra());
        rns.put(Cotacao.class.getName(), rnsCotacao);

    }

    /**
     * TODO Descrição do Método
     *
     * @param entidade
     * @return
     * @see les.dao.IDAO#salvar(les.dominio.EntidadeDominio)
     */
    @Override
    public String salvar(EntidadeDominio entidade) {
        StringBuilder sb = new StringBuilder();
        String nmClasse = entidade.getClass().getName();
        List<ICommand> cmds = rns.get(nmClasse);

        for (ICommand cmd : cmds) {
            String msg = cmd.execute(entidade);
            if (msg != null) {
                sb.append(msg);
                sb.append("\n");
            }
        }

        if (sb.length() == 0) {
            IDAO dao = daos.get(nmClasse);

            dao.salvar(entidade);
            return null;
        } else {
            return sb.toString();
        }
    }

    /**
     * TODO Descrição do Método
     *
     * @param entidade
     * @return
     * @see les.dao.IDAO#alterar(les.dominio.EntidadeDominio)
     */
    @Override
    public String alterar(EntidadeDominio entidade) {
        StringBuilder sb = new StringBuilder();
        String nmClasse = entidade.getClass().getName();
        List<ICommand> cmds = rns.get(nmClasse);

        for (ICommand cmd : cmds) {
            String msg = cmd.execute(entidade);
            if (msg != null) {
                sb.append(msg);
                sb.append("\n");
            }
        }

        if (sb.length() == 0) {
            IDAO dao = daos.get(nmClasse);

            dao.alterar(entidade);
            return null;
        } else {
            return sb.toString();
        }
    }

    /**
     * TODO Descrição do Método
     *
     * @param entidade
     * @return
     * @see les.dao.IDAO#excluir(les.dominio.EntidadeDominio)
     */
    @Override
    public String excluir(EntidadeDominio entidade) {
        StringBuilder sb = new StringBuilder();
        String nmClasse = entidade.getClass().getName();
        //List<ICommand> cmds = rns.get(nmClasse);

        //for (ICommand cmd : cmds) {
        //    String msg = cmd.execute(entidade);
        //    if (msg != null) {
        //        sb.append(msg);
        //        sb.append("\n");
        //    }
        //}

        if (sb.length() == 0) {
            IDAO dao = daos.get(nmClasse);

            dao.excluir(entidade);
            return null;
        } else {
            return sb.toString();
        }
    }

    /**
     * TODO Descrição do Método
     *
     * @param entidade
     * @return
     * @see les.dao.IDAO#consultar(les.dominio.EntidadeDominio)
     */
    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        List<EntidadeDominio> lista = null;
		String nmClasse = entidade.getClass().getName();
		try {
			IDAO dao = daos.get(nmClasse);
			lista = dao.consultar(entidade);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("erro no salvar fachada");
		}
		return lista;
    }

}
