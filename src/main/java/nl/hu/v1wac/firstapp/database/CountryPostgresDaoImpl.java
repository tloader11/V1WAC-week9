package nl.hu.v1wac.firstapp.database;

import nl.hu.v1wac.firstapp.model.Country;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CountryPostgresDaoImpl extends PostgresBaseDao implements CountryDao {
    @Override
    public boolean save(Country country) {
        return false;
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
        String sql = "UPDATE country SET code='{0}', iso3='{1}', name='{2}', continent='{3}', region='{4}', surfacearea='{5}', " +
                "indepyear='{6}', population='{7}', lifeexpectancy='{8}', gnp='{9}', gnpold='{10}', " +
                "localname='{11}', governmentform='{12}', headofstate='{13}', latitude={14}, longitude={15}, " +
                "capital='{16}' WHERE iso3='{17}';";
    }

    @Override
    public boolean delete(Country country) {
        return false;
    }
}
