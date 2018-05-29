package nl.hu.v1wac.firstapp.webservices;

import nl.hu.v1wac.firstapp.persistence.CountryPostgresDaoImpl;
import nl.hu.v1wac.firstapp.model.Country;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/restservices/countries/*")
public class CountryResource extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        CountryPostgresDaoImpl service = new CountryPostgresDaoImpl();
        String[] uri = req.getRequestURI().split("/");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        if(uri.length > 0 && uri[uri.length-1].length() == 2) {
            String country_code = uri[uri.length - 1];
            try {
                boolean success = false;
                Country c = service.findByCode(country_code);
                if(c!= null)
                {
                    //AH OH!! Country does already exist..!
                    success = false;
                }
                else
                {
                    //new country added !! (add based on CountryCode)
                    String code = req.getParameter("code");
                    String iso3 = req.getParameter("iso3");
                    String nm = req.getParameter("name");
                    String cap = req.getParameter("capital");
                    String ct = req.getParameter("continent");
                    String reg = req.getParameter("region");
                    Double sur = Double.parseDouble(req.getParameter("surfacearea"));
                    int pop = Integer.parseInt(req.getParameter("population"));
                    String gov = req.getParameter("governmentform");
                    Double lat = Double.parseDouble(req.getParameter("latitude"));
                    Double lng = Double.parseDouble(req.getParameter("longitude"));
                    Country newCountry = new Country(code, iso3, nm, cap, ct, reg, sur, pop, gov, lat, lng);
                    success = service.save(newCountry);
                    System.out.println("Got a new country to add.");
                }
                if(success)out.print("{\"status\":true}");
                else out.print("{\"status\":false}");
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                out.print("{\"status\":false}");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        CountryPostgresDaoImpl service = new CountryPostgresDaoImpl();
        String[] uri = req.getRequestURI().split("/");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        if(uri.length > 0 && uri[uri.length-1].length() == 2)
        {
            String country_code = uri[uri.length-1];
            try {
                Country c = service.findByCode(country_code);
                if(c!= null)
                {
                    //found country!
                    service.delete(c);
                    System.out.println("Got delete request");
                }
                else
                {
                    throw new IllegalArgumentException(); //force catch so status false is returned.
                }
                out.print("{status:true}");
            }
            catch(Exception ex)
            {
                out.print("{status:false}");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        CountryPostgresDaoImpl service = new CountryPostgresDaoImpl();
        String[] uri = req.getRequestURI().split("/");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        if(uri.length > 0 && uri[uri.length-1].length() == 2) {
            String country_code = uri[uri.length - 1];
            try {
                boolean success = false;
                Country c = service.findByCode(country_code);
                if(c!= null)
                {
                    //found country!
                    String code = req.getParameter("code");
                    String iso3 = req.getParameter("iso3");
                    String nm = req.getParameter("name");
                    String cap = req.getParameter("capital");
                    String ct = req.getParameter("continent");
                    String reg = req.getParameter("region");
                    Double sur = Double.parseDouble(req.getParameter("surfacearea"));
                    int pop = Integer.parseInt(req.getParameter("population"));
                    String gov = req.getParameter("governmentform");
                    Double lat = Double.parseDouble(req.getParameter("latitude"));
                    Double lng = Double.parseDouble(req.getParameter("longitude"));
                    Country newVersion = new Country(code, iso3, nm, cap, ct, reg, sur, pop, gov, lat, lng);
                    success = service.update(newVersion);
                    System.out.println("Got existing country update request.");
                }
                else
                {
                   success=false;
                }
                if(success)out.print("{\"status\":true}");
                else out.print("{\"status\":false}");
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                out.print("{\"status\":false}");
            }
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        CountryPostgresDaoImpl service = new CountryPostgresDaoImpl();

        //WorldService service = new WorldService();
        String[] uri = req.getRequestURI().split("/");
        JSONObject obj = new JSONObject();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        if(uri.length > 0 && uri[uri.length-1].length() == 2)
        {
            String country_code = uri[uri.length-1];
            try {
                Country c = service.findByCode(country_code);
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
                obj.put("surfaces", service.find10LargestSurfaces());
                out.print(obj.toString());
            }
            else if(request.toLowerCase().equals("largestpopulations"))
            {
                obj.put("populations", service.find10LargestPopulations());
                out.print(obj.toString());
            }
        }
        else
        {
            out.print("{}");
        }
    }
}