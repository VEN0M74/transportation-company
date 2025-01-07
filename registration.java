import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
public class registration extends JFrame {
    HashMap<String,String> logininfo = new HashMap<String,String>();
        private JLabel FirstNameLabel,LastNameLabel,emailLabel,ConfirmPassword,passwordLabel,EMAILLabel;
        private JTextField emailField,FirstName,LastName,confirm,phone;
        private JPasswordField passwordField;
        private JButton RegisterButton;
        private JPanel panel;
         public HashMap getLoginInfo() {
        return logininfo;
    }
        public registration() {
            panel=new JPanel();
            panel.setLayout(null);

            FirstNameLabel=new JLabel("First name");
            FirstNameLabel.setBounds(50,50,100,30);
            panel.add(FirstNameLabel);
            FirstName=new JTextField(20);
            FirstName.setBounds(120,50,100,30);
            panel.add(FirstName);

            LastNameLabel=new JLabel("Last name:");
            LastNameLabel.setBounds(50,100,100,30);
            panel.add(LastNameLabel);
            LastName=new JTextField(20);
            LastName.setBounds(120,100,100,30);
            panel.add(LastName);

            emailLabel=new JLabel("user name:");
            emailLabel.setBounds(50,150,100,30);
            panel.add(emailLabel);
            emailField=new JTextField(20);
            emailField.setBounds(120,150,100,30);
            panel.add(emailField);

//            EMAILLabel=new JLabel("Email:");
//            EMAILLabel.setBounds(50,200,100,30);
//            panel.add(EMAILLabel);
//            phone=new JTextField(20);
//            phone.setBounds(120,200,100,30);
//            panel.add(phone);

            passwordLabel=new JLabel("Password:");
            passwordLabel.setBounds(50,250,100,30);
            panel.add(passwordLabel);
            passwordField=new JPasswordField(20);
            passwordField.setBounds(120,250,100,30);
            panel.add(passwordField);

            ConfirmPassword=new JLabel("Confirm password:");
            ConfirmPassword.setBounds(50,300,140,30);
            panel.add(ConfirmPassword);
            confirm=new JPasswordField(20);
            confirm.setBounds(160,300,100,30);
            panel.add(confirm);

            RegisterButton =new JButton("Register");
            RegisterButton.setBounds(50,350,150,30);
            panel.add(RegisterButton);
            RegisterButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        String name1=FirstName.getText();
                        String name2=LastName.getText();
                        String user=emailField.getText();
                        //String email=EMAILLabel.getText();
                        String password=new String(passwordField.getPassword());
                        System.out.println("First name:"+name1);
                        System.out.println("Last name:"+name2);
                        System.out.println("user name:"+user);
                        //System.out.println("email"+email);
                        System.out.println("Password:"+password);
                        if (password.equals(ConfirmPassword)){
                            System.out.println("password correct");
                        }else {
                            System.out.println("password is wrong!");
                        }
               }
            });
                add(RegisterButton);
                setSize(200, 100);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setVisible(true);panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JLabel userTypeLabel = new JLabel("User Type:");
            JComboBox<String> userTypeComboBox = new JComboBox<>(new String[]{"Passenger", "Employee"});
            panel.add(userTypeLabel);
            panel.add(userTypeComboBox);
                String userType = (String) userTypeComboBox.getSelectedItem();
                String password = new String(passwordField.getPassword());
                // Handle registration based on user type
                if (userType.equals("Passenger")) {
                    // Register passenger
                } else if (userType.equals("Employee")) {
                    System.out.println("1. Manager");
                    System.out.println("2. Driver");
                    System.out.print("Choose employee type: ");
                    Scanner scanner = new Scanner(System.in);
                    int employeeTypeChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    String employeeType = (employeeTypeChoice == 1) ? "Manager" : "Driver";
                }
                RegisterButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        String name1=FirstName.getText();
                        String name2=LastName.getText();
                        String user=emailField.getText();
                        String email=EMAILLabel.getText();
                        String password=new String(passwordField.getPassword());
                        System.out.println("First name:"+name1);
                        System.out.println("Last name:"+name2);
                        System.out.println("user name:"+user);
                        System.out.println("email"+email);
                        System.out.println("Password:"+password);
                        if (!password.equals(ConfirmPassword)){
                            System.out.println("password isn't the same please try again");
                        }
                    }
                });
                panel.add(RegisterButton);
                add(panel);
                setVisible(true);
            }
        }

