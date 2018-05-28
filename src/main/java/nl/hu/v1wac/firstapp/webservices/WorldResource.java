package nl.hu.v1wac.firstapp.webservices;

import nl.hu.v1wac.firstapp.model.Country;
import nl.hu.v1wac.firstapp.model.WorldService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/restservices/countries")
public class WorldResource extends HttpServlet
{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        WorldService service = new WorldService();
        List<Country> countries = service.getAllCountries();
        JSONObject obj = new JSONObject();
        obj.put("countries", countries);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        out.print(obj.toString());
        /*
        for(int i =0; i < countries.size(); i++)
        {
            Map<String, String> country = new HashMap<>();
            country.put("code",countries.get(i).getCode());
            country.put("name",countries.get(i).getCode());
            country.put("capital",countries.get(i).getCode());
            country.put("iso3",countries.get(i).getCode());
        }
        */
    }
}
