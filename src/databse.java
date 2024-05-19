import java.sql.*;
public class databse {
    Connection con;
    Statement stm;
    databse(){
        try{
           con=DriverManager.getConnection("jdbc:mysql://localhost:3310/opalpay","root", "");
           stm=con.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
