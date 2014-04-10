/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao.impl;

import util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import dominio.Compra;
import dominio.EntidadeDominio;
/**
 *
 * @author Gerson
 */
public class CompraDAO extends AbstractDAO{
    
    public List consultar(EntidadeDominio entidade){
        StringBuilder sb = new StringBuilder();
        
        if(entidade != null){
            Compra vo = (Compra) entidade;

            if( vo.getId() != null ){
                 sb.append(" AND id="+vo.getId());             
            }
            
        }
        Transaction t = session.beginTransaction();
        List lista = session.createQuery("FROM Compra WHERE 1=1 "+sb.toString()+" ORDER BY id").list();
        //t.commit();
        if(lista.isEmpty())
            return null;
        else
            return lista;
    }
    
    public boolean salvar(EntidadeDominio vo){
        Transaction t = session.beginTransaction();
        try{
            session.save((Compra)vo);
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
            session.saveOrUpdate((Compra)vo);
            //session.createQuery("delete CompraMaterial where compra_id is null ").executeUpdate();
//            session.createQuery("delete Cotacao where compra_id is null ").executeUpdate();
            t.commit();
            return true;
        }catch (HibernateException e) {
            if (t != null) {
                t.rollback();                
            }
            e.printStackTrace();
        } 
        return false;
    }
//    
    public boolean excluir(EntidadeDominio vo) {
        Transaction t = session.beginTransaction();
        try{
			session.clear();
            session.delete((Compra)vo);
            t.commit();
            return true;
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return false;
    }
 
    public Compra consultarPorId(Integer id) {
        Transaction t = session.beginTransaction();
        List lista = session.createQuery("FROM Compra WHERE id=" +id.intValue()).list();
        //t.commit();
        
        if (lista.isEmpty()) {
            return null;
        } else {
            return (Compra) lista.get(0);
        }
    }
}
