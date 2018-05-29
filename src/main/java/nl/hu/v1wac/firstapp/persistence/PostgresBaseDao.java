package nl.hu.v1wac.firstapp.persistence;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class PostgresBaseDao
{

    public Connection getConnection() {
        try {
            Context ctx = new InitialContext();
            if (ctx == null)
                throw new Exception("Boom - No Context");
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PostgresDS");
            if(ds != null) return ds.getConnection();
            return null;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
