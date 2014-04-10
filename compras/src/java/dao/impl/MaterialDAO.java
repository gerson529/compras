/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao.impl;

import dominio.EntidadeDominio;
import java.util.List;
import org.hibernate.HibernateException;

import org.hibernate.Transaction;
import dominio.Material;
/**
 *
 * @author Gerson
 */
public class MaterialDAO extends AbstractDAO {
    
     
    @Override
    public List consultar(EntidadeDominio entidade){
        StringBuilder sb = new StringBuilder();
        if(entidade != null){
            Material vo = (Material) entidade;

            if( vo.getId() != null ){
                 sb.append(" AND id="+vo.getId());             
            }
            if( vo.getNome()!= null ){
                 sb.append(" AND nome = '"+vo.getNome()+"'");             
            }
        }
        Transaction t = session.beginTransaction();
       
            
        List lista = session.createQuery("FROM Material WHERE 1=1 "+sb.toString()+" ORDER BY id").list();
        //t.commit();
        if(lista.isEmpty())
            return null;
        else
            return lista;
    }
    
    @Override
    public boolean salvar(EntidadeDominio vo){
         Transaction t = session.beginTransaction();
        try{
            session.save((Material)vo);
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
    
    @Override
    public boolean alterar(EntidadeDominio vo){
        Transaction t = session.beginTransaction();
        try{
            // limpa o cache da session para evitar excpetion de redundancia no objeto
            session.clear();
            session.update((Material)vo);
            t.commit();
            return true;
        }catch(Exception e){
           e.printStackTrace();
        }
        return false;
    }
    
   
    public boolean excluir(EntidadeDominio vo) {
        Transaction t = session.beginTransaction();
        try{
            session.delete((Material)vo);
            t.commit();
            return true;
        }catch(Exception e){
           e.printStackTrace();
        }
        return false;
    }
 
    public Material consultarPorId(Integer id) {
        Transaction t =session.beginTransaction();
        List lista = session.createQuery("FROM Material WHERE id=" +id.intValue()).list();
        
        if (lista.isEmpty()) {
            return null;
        } else {
            return (Material) lista.get(0);
        }
    }
}
