/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao.impl;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import dominio.Categoria;
import dominio.EntidadeDominio;
import util.HibernateUtil;
/**
 *
 * @author Gerson
 */
public class CategoriaDAO extends AbstractDAO{
    
       
    public List consultar(EntidadeDominio entidade){
        StringBuilder sb = new StringBuilder();
        if(entidade != null){
            Categoria vo = (Categoria) entidade;

            if( vo.getId() != null ){
                 sb.append(" AND id="+vo.getId());             
            }
            if( vo.getNome()!= null ){
                 sb.append(" AND nome='"+vo.getNome()+"'");             
            }
        }
        
        Transaction t = session.beginTransaction();
        List lista = session.createQuery("FROM Categoria WHERE 1=1 "+sb.toString()+" ORDER BY nome").list();
        //t.commit();
        
        if(lista.isEmpty())
            return null;
        else
            return lista;
    }
    
    public boolean salvar(EntidadeDominio vo){
         Transaction t = session.beginTransaction();
        try{
            session.save((Categoria)vo);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
                
            }
            e.printStackTrace();
        } 
        return false;
    }
    
    public boolean alterar(EntidadeDominio vo){
        Transaction t = session.beginTransaction();
        try{
            // limpa o cache da session para evitar excpetion de redundancia no objeto
            session.clear();
            session.update((Categoria)vo);
            t.commit();
            return true;
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return false;
    }
    
    public boolean excluir(EntidadeDominio vo) {
        Transaction t = session.beginTransaction();
        try{
            session.delete((Categoria)vo);
            t.commit();
            return true;
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return false;
    }
 
    public Categoria consultarPorId(Integer id) {
        Transaction t =session.beginTransaction();
        List lista = session.createQuery("FROM Categoria WHERE id=" +id.intValue()).list();
        
        if (lista.isEmpty()) {
            return null;
        } else {
            return (Categoria) lista.get(0);
        }
    }
}
