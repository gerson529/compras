/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet3con.ServletController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gerson
 */
public interface EncaminharDados {
    void executa(HttpServletRequest request,HttpServletResponse response) throws Exception;
}
