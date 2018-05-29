package nl.hu.v1wac.firstapp.persistence;

import nl.hu.v1wac.firstapp.model.Country;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CountryPostgresDaoImpl extends PostgresBaseDao implements CountryDao {
    @Override
    public boolean save(Country country) {
        String sql = String.format("INSERT INTO public.country(" +
                "code, iso3, name, continent, region, surfacearea, " +
                "population, governmentform, " +
                "latitude, longitude, capital) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', %f, %d, '%s', %f, %f, '%s')",country.getCode(), country.getIso3(),country.getName(),country.getContinent(),country.getRegion(),
                country.getSurface(),country.getPopulation(),country.getGovernment(), country.getLatitude(),country.getLongitude(), country.getCapital());
        try {
            super.getConnection().createStatement().executeUpdate(sql);
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Country> findAll() {
        String sql = "SELECT * FROM country";
        try {
            ResultSet rst = super.getConnection().createStatement().executeQuery(sql);
            if(rst.isBeforeFirst())
            {
                List<Country> countries = new ArrayList<>();
                while(rst.next())
                {
                    countries.add(getCountryFromResultSet(rst));
                }
                return countries;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private List<Country> findAll(String whatMax, String asc_desc, int limit)
    {
        String sql = "SELECT * FROM country ORDER BY "+whatMax+" "+asc_desc+" LIMIT "+limit;
        try {
            ResultSet rst = super.getConnection().createStatement().executeQuery(sql);
            if(rst.isBeforeFirst())
            {
                List<Country> countries = new ArrayList<>();
                while(rst.next())
                {
                    countries.add(getCountryFromResultSet(rst));
                }
                return countries;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private Country getCountryFromResultSet(ResultSet rst)
    {
        try {
            Country c = new Country(rst.getString("code"), rst.getString("iso3"),
                    rst.getString("name"), rst.getString("capital"), rst.getString("continent"),
                    rst.getString("region"), rst.getDouble("surfacearea"), rst.getInt("population"),
                    rst.getString("governmentform"), rst.getDouble("latitude"),rst.getDouble("longitude") );
            return c;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Country findByCode(String code) {
        String sql = "SELECT * FROM country WHERE code='"+code+"'";
        try
        {
            ResultSet rst = super.getConnection().createStatement().executeQuery(sql);
            if(rst.isBeforeFirst())
            {
                rst.next();
                Country c = new Country(rst.getString("code"), rst.getString("iso3"),
                        rst.getString("name"), rst.getString("capital"), rst.getString("continent"),
                        rst.getString("region"), rst.getDouble("surfacearea"), rst.getInt("population"),
                        rst.getString("governmentform"), rst.getDouble("latitude"),rst.getDouble("longitude") );
                return c;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Country> find10LargestPopulations() {
        return findAll("population", "DESC", 10);
    }

    @Override
    public List<Country> find10LargestSurfaces() {
        return findAll("surfacearea", "DESC", 10);
    }

    @Override
    public boolean update(Country country) {
        if(country.getIso3().length() > 3 || country.getName().length()>52 || country.getContinent().length()>20)
        {
            return false;
        }
        String sql = String.format("UPDATE country SET iso3='%s', name='%s', continent='%s', region='%s', surfacearea=%f, " +
                "population=%d, governmentform='%s', latitude=%f, longitude=%f, capital='%s' WHERE code='%s'",
                country.getIso3(),country.getName(),country.getContinent(),country.getRegion(),
                country.getSurface(),country.getPopulation(),country.getGovernment(), country.getLatitude(),country.getLongitude(), country.getCapital(), country.getCode());
        //System.out.println(sql);
        try {
            super.getConnection().createStatement().executeUpdate(sql);
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Country country) {
        String sql = "DELETE FROM country WHERE code='"+country.getCode()+"'";
        try {
            super.getConnection().createStatement().executeUpdate(sql);
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return false;
    }
}
