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

@WebServlet(urlPatterns = "/restservices/countries/*")
public class CountryResource extends HttpServlet
{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        WorldService service = new WorldService();
        String[] uri = req.getRequestURI().split("/");
        JSONObject obj = new JSONObject();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        if(uri.length > 0 && uri[uri.length-1].length() == 2)
        {
            String country_code = uri[uri.length-1];
            try {
                Country c = service.getCountryByCode(country_code);
                obj.put("code", c.getCode());
                obj.put("name", c.getName());
                obj.put("capital", c.getCapital());
                obj.put("surface", c.getSurface());
                obj.put("government", c.getGovernment());
                obj.put("lat", c.getLatitude());
                obj.put("iso3", c.getIso3());
                obj.put("continent", c.getContinent());
                obj.put("region", c.getRegion());
                obj.put("population", c.getPopulation());
                obj.put("lng", c.getLongitude());
                out.print(obj.toString());
            }
            catch(Exception ex)
            {
                out.print("{}");
            }
        }
        else if(uri.length> 0 && uri[uri.length-1].length() > 2)
        {
            String request = uri[uri.length-1];
            if(request.toLowerCase().equals("largestsurfaces"))
            {
                obj.put("surfaces", service.get10LargestSurfaces());
                out.print(obj.toString());
            }
            else if(request.toLowerCase().equals("largestpopulations"))
            {
                obj.put("populations", service.get10LargestPopulations());
                out.print(obj.toString());
            }
        }
        else
        {
            out.print("{}");
        }
    }
}