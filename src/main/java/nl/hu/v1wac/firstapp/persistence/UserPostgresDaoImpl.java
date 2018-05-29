package nl.hu.v1wac.firstapp.persistence;

import java.sql.ResultSet;

public class UserPostgresDaoImpl extends PostgresBaseDao implements UserDao
{

    @Override
    public String findRoleForUser(String name, String pass) {
        String sql = String.format("SELECT role FROM useraccount WHERE name='%s' AND password='%s'",name,pass);
        try
        {
            ResultSet result = super.getConnection().createStatement().executeQuery(sql);
            if(result.isBeforeFirst())
            {
                result.next();
                String role = result.getString("role");
                result.close();
                return role;
            }
            return null;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
