/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet3con.Action;

/**
 *
 * @author Gerson
 */
public class Action {

    private String header;
    private String footer;
    
    public Action() {
        header = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "  <head>\n"
                + "    <meta charset=\"utf-8\">\n"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                + "    <title>Compras</title>\n"
                + "\n"
                + "    <!-- Bootstrap -->\n"
                + "    <link href=\"../css/jquery-ui.css\" rel=\"stylesheet\">\n"
                + "    <link href=\"../css/bootstrap.min.css\" rel=\"stylesheet\">\n"
                + "\n"
                + "    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->\n"
                + "    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->\n"
                 + "<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->\n"
                + "    <script src=\"../js/jquery.js\"></script>\n"
                + "    <script src=\"../js/jquery-ui.js\"></script>\n"
                + "    <!-- Include all compiled plugins (below), or include individual files as needed -->\n"
                + "    <script src=\"../js/bootstrap.min.js\"></script>\n"
                + "  </head>\n"
                + "  <body>"
                + "     <div class=\"container\">";
        footer = "      </div>"
               
                + "  </body>\n"
                + "</html>";
    }

    public String getHeader() {
        return header;
    }

    

    public String getFooter() {
        return footer;
    }

    
}
