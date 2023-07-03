package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Connection connection = null;
    private static String senha;

    private Conexao(){}

    public static void setSenha(String password){
        senha = "1620";
    }

    public static Connection getConexao() throws ClassNotFoundException, SQLException{

        if(connection == null){
            String url = "jdbc:postgresql://localhost:5432/PostgreSQL15";
            String usuario = "postgres";
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, usuario, senha);
        }

        return connection;
    }

    
}
