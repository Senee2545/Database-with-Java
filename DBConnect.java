import java.sql.*;

public class DBConnect {
    public static void main(String[] args){
        try {
            String host = "jdbc:mysql://localhost:3306/courseproject";
            String uName = "root";
            String uPass = "15599006/11/2002";
            Connection con = DriverManager.getConnection(host, uName, uPass);

            Statement stat = con.createStatement();
            String sql = "select * from studenttable";
            ResultSet rs = stat.executeQuery(sql);

            while ( rs.next()) {
                int ssn = rs.getInt("ssn");
                String firstName = rs.getString("firstName");
                String mi = rs.getString("mi");
                String lastName = rs.getString("lastName");
                long phone = rs.getLong("phone");
                String birthDate = rs.getString("birthDate");
                String street = rs.getString("street");
                String zipCode = rs.getString("zipCode");
                String deptID = rs.getString("deptID");


                String p = ssn + "\t" + firstName + "\t" + mi + "\t" + lastName + "\t"
                        + phone + "\t"  + birthDate + "\t" + street + "\t" + zipCode
                        + "\t" + deptID ;
                System.out.println(p);
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }
}


