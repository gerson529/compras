
package dao.impl;

import util.HibernateUtil;
import java.sql.Connection;
import java.sql.SQLException;

import dao.IDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public abstract class AbstractDAO implements IDAO{

	protected Connection connection;
	protected String table;
	
        protected Session session;
        
        protected AbstractDAO(){
            try{
                session = HibernateUtil.getSessionFactory().getCurrentSession();
            }catch(HibernateException e){
                e=e;
            }
        }
//	protected AbstractDAO(String table){
//            
//            this.table = table;
//	}
//	
//	protected void openConnection(){
//		try {
//			connection = Conexao.conectar();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
}
