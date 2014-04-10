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
import dominio.Cotacao;
import dominio.EntidadeDominio;
/**
 *
 * @author Gerson
 */
public class CotacaoDAO extends AbstractDAO{
      
    public List consultar(EntidadeDominio entidade){
        StringBuilder sb = new StringBuilder();
        if(entidade != null){
            Cotacao vo = (Cotacao) entidade;

            if( vo.getId() != null ){
                 sb.append(" AND id="+vo.getId());             
            }
            
        }
        Transaction t = session.beginTransaction();
        List lista = session.createQuery("FROM Cotacao WHERE 1=1 "+sb.toString()+" ORDER BY id").list();
        t.commit();
        if(lista.isEmpty())
            return null;
        else
            return lista;
    }
    
    public boolean salvar(EntidadeDominio vo){
        Transaction t = session.beginTransaction();
        try{
            session.save((Cotacao)vo);
            session.flush();
            t.commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
                
            }
            e.printStackTrace();
        } 
        return true;
      
        
    }
    
    public boolean alterar(EntidadeDominio vo){
		Transaction t = session.beginTransaction();
        try{
			
        
            // limpa o cache da session para evitar excpetion de redundancia no objeto
            session.clear();
            session.saveOrUpdate((Cotacao)vo);
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
            session.createQuery("delete CotacaoMaterial where cotacao_id="+vo.getId()).executeUpdate();
            session.delete((Cotacao)vo);
            session.flush();
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
 
    public Cotacao consultarPorId(Integer id) {
        Transaction t = session.beginTransaction();
        List lista = session.createQuery("FROM Cotacao WHERE id=" +id.intValue()).list();
        //t.commit();
        
        if (lista.isEmpty()) {
            return null;
        } else {
            return (Cotacao) lista.get(0);
        }
    }
}
