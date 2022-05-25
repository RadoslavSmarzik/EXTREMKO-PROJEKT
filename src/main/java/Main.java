import dbConnect.DbContext;
import login.Login;
import menu.DoctorMenu;
import menu.PatientMenu;
import menu.Menu;
import org.postgresql.ds.PGSimpleDataSource;
import queries.Query;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static Menu selectMenu(Login login, Query query){
        if(login.isDoctor()){
            return new DoctorMenu(query, login.getLoggedUser());
        }
        return new PatientMenu(query, login.getLoggedUser());

    }

    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setServerName("ec2-63-35-156-160.eu-west-1.compute.amazonaws.com");
        dataSource.setPortNumber(5432);
        dataSource.setDatabaseName("d15gln61q6orq3");
        dataSource.setUser("pivtfthinzygfw");

        dataSource.setPassword("d768a80621f7bdec33fe1975e02b259a5cdd7a1978fd8986a4c12e3cd455e034");

        try (Connection connection = dataSource.getConnection()) {
            DbContext.setConnection(connection);
            Query query = new Query();
            while (true) {
                Login login = new Login(query);
                login.start();
                Menu menu = selectMenu(login, query);
                menu.run();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbContext.clear();
        }

    }




}
