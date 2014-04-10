/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet3con.ServletController;

import java.io.IOException;
import java.rmi.ServerException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gerson
 */
public class ServletController extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        
        String parametros = request.getParameter("encaminhar");
        String nomeDaClasse = "servlet3con.Action." + parametros;
        
        try{
            Class classe = Class.forName(nomeDaClasse);
            EncaminharDados servlet = (EncaminharDados) classe.newInstance();
            servlet.executa(request, response);
        }catch(Exception e){
            throw new ServerException("A logica de negócio causou uma exceção",e);
        }
        
    }
}
