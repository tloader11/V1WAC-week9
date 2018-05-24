package nl.hu.v1wac.firstapp.servlets;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = "/DynamicServlet.do")
public class DynamicServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));
        String soort = req.getParameter("soort");

        int output = 0;
        switch(soort)
        {
            case "*":
                output=a*b;
                break;
            case "/":
                output=a/b;
                break;
            case "+":
                output=a+b;
                break;
            case "-":
                output=a-b;
                break;
             default:
                 output=-1;
                 break;
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println(" <title>Calculator</title>");
        out.println(" <body>");
        out.println(" <h2>My Basic Calculator</h2>");
        out.println(" <h2>Output: "+a +" "+ soort + " " + b + " = "+ output + "!</h2>");
        out.println(" </body>");
        out.println("</html>");
    }
}