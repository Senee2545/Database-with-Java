import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainFrame {
    private JTextField firstNamef;
    private JTextField phonef;
    private JTextField birthDatef;
    private JTextField streetf;
    private JTextField mif;
    private JTextField lastNamef;
    private JTextField zipCodef;
    private JTextField deptIDf;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JLabel ST_TABLE;
    private JLabel TABLE;
    private JPanel Main;
    private JTextField ssnf;
    private JTable table1;
    private JScrollPane table_load;
    private JButton searchButton;
    private JButton chartButton;

    public static void main(String[] args){
        JFrame frame = new JFrame("STUDENT TABLE");
        frame.setContentPane(new MainFrame().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/courseproject", "root","15599006/11/2002");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void table_load(){
        try
        {
            pst = con.prepareStatement("select * from studenttable");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public MainFrame(){
        connect();
        table_load();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String  firstName, mi, lastName, phone, birthDate , street, zipCode, deptID;

                firstName = firstNamef.getText();
                mi = mif.getText();
                lastName = lastNamef.getText();
                phone = phonef.getText();
                birthDate = birthDatef.getText();
                street = streetf.getText();
                zipCode = zipCodef.getText();
                deptID = deptIDf.getText();

                try {
                    pst = con.prepareStatement("insert into studenttable(firstName,mi,lastName,phone,birthDate,street,zipCode,deptID)values(?,?,?,?,?,?,?,?,?,?)");
                    pst.setString(1, firstName);
                    if (firstNamef.getText().length() < 2) {
                        JOptionPane.showMessageDialog(null, "Your firstName is too short!");
                    } else if (firstNamef.getText().length() > 20) {
                        JOptionPane.showMessageDialog(null, "Your firstName under the limited!");
                    }
                    pst.setString(2, mi);
                    if (mif.getText().length() > 1) {
                        JOptionPane.showMessageDialog(null, "Your Mi is not correct!");
                    }
                    pst.setString(3, lastName);
                    if (lastNamef.getText().length() < 2) {
                        JOptionPane.showMessageDialog(null, "Your lastName is too short!");
                    } else if (lastNamef.getText().length() > 20) {
                        JOptionPane.showMessageDialog(null, "Your lastName under the limited!");
                    }

                    pst.setString(4, phone);
                    if (phonef.getText().length() > 10) {
                        JOptionPane.showMessageDialog(null, "Your phone number must be not more than ten!");
                    } else if (phonef.getText().length() < 10) {
                        JOptionPane.showMessageDialog(null, "Your phone number must be not less than ten!");
                    }
                    pst.setString(5, birthDate);
                    pst.setString(6, street);
                    if(streetf.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "You must enter the street!");
                    }
                    pst.setString(7, zipCode);
                    if(zipCodef.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "You must enter the zipCode!");
                    }
                    pst.setString(8, deptID);
                    if(deptIDf.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "You must enter the deptID!");
                    }


                    pst.execute();

                    JOptionPane.showMessageDialog(null, "Record Added");
                    table_load();

                    firstNamef.setText("");
                    mif.setText("");
                    lastNamef.setText("");
                    phonef.setText("");
                    birthDatef.setText("");
                    streetf.setText("");
                    zipCodef.setText("");
                    deptIDf.setText("");

                }

                catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }

        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    int ssn;
                    ssn = Integer.parseInt(ssnf.getText());

                    pst = con.prepareStatement("select firstName,mi,lastName,phone,birthDate,street,zipCode,deptID from studenttable where ssn = ?");
                    pst.setInt(1, ssn);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String firstName = rs.getString(1);
                        String mi = rs.getString(2);
                        String lastName = rs.getString(3);
                        String phone = rs.getString(4);
                        String birthDate = rs.getString(5);
                        String street = rs.getString(6);
                        String zipCode = rs.getString(7);
                        String deptID = rs.getString(8);


                        firstNamef.setText(firstName);
                        mif.setText(mi);
                        lastNamef.setText(lastName);
                        phonef.setText(phone);
                        birthDatef.setText(birthDate);
                        streetf.setText(street);
                        zipCodef.setText(zipCode);
                        deptIDf.setText(deptID);

                    }
                    else
                    {
                        firstNamef.setText("");
                        mif.setText("");
                        lastNamef.setText("");
                        phonef.setText("");
                        birthDatef.setText("");
                        streetf.setText("");
                        zipCodef.setText("");
                        deptIDf.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Student ssn");

                    }

                } catch (SQLException e2) {
                    e2.printStackTrace();
                }

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int ssn;
                String  firstName, mi, lastName, phone, birthDate , street, zipCode, deptID;

                firstName = firstNamef.getText();
                mi = mif.getText();
                lastName = lastNamef.getText();
                phone = phonef.getText();
                birthDate = birthDatef.getText();
                street = streetf.getText();
                zipCode = zipCodef.getText();
                deptID = deptIDf.getText();
                ssn = Integer.parseInt(ssnf.getText());

                try{
                    pst = con.prepareStatement("update studenttable set firstName= ?,mi = ?,lastName = ?, phone = ? , birthDate = ?" +
                            ", street = ?, zipCode = ?, deptID= ? where ssn = ?");
                    pst.setString(1, firstName);
                    pst.setString(2, mi);
                    pst.setString(3, lastName);
                    pst.setString(4, phone);
                    pst.setString(5, birthDate);
                    pst.setString(6, street);
                    pst.setString(7, zipCode);
                    pst.setString(8, deptID);
                    pst.setInt(9, ssn);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update!");
                    table_load();
                    firstNamef.setText("");
                    mif.setText("");
                    lastNamef.setText("");
                    phonef.setText("");
                    birthDatef.setText("");
                    streetf.setText("");
                    zipCodef.setText("");
                    deptIDf.setText("");
                    firstNamef.requestFocus();

                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                int ssn;
                ssn = Integer.parseInt(ssnf.getText());

                try{
                    pst = con.prepareStatement("delete from studenttable where ssn = ?");
                    pst.setInt(1, ssn);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Delete!");
                    table_load();
                    firstNamef.setText("");
                    mif.setText("");
                    lastNamef.setText("");
                    phonef.setText("");
                    birthDatef.setText("");
                    streetf.setText("");
                    zipCodef.setText("");
                    deptIDf.setText("");
                    firstNamef.requestFocus();

                }catch (SQLException e4) {
                    e4.printStackTrace();
                }
            }
        });


    }

}
