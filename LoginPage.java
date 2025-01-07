import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
        private JLabel usernameLabel, passwordLabel;
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton loginButton;
        private JButton resetButton;
        private JPanel panel;

        public LoginPage() {
                panel = new JPanel();
                panel.setLayout(null);

                usernameLabel = new JLabel("Username:");
                usernameLabel.setBounds(50, 50, 100, 30);
                panel.add(usernameLabel);

                usernameField = new JTextField(20);
                usernameField.setBounds(150, 50, 100, 30);
                panel.add(usernameField);

                passwordLabel = new JLabel("Password:");
                passwordLabel.setBounds(50, 100, 100, 30);
                panel.add(passwordLabel);

                passwordField = new JPasswordField(20);
                passwordField.setBounds(150, 100, 100, 30);
                panel.add(passwordField);

                loginButton = new JButton("Login");
                loginButton.setBounds(50, 200, 100, 30);
                panel.add(loginButton);
                resetButton = new JButton("Reset");
                resetButton.setBounds(150, 200, 100, 30);
                panel.add(resetButton);
                loginButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                String username = usernameField.getText();
                                String password = new String(passwordField.getPassword());
                                System.out.println("Username: " + username);
                               System.out.println("Password: " + password);
                                        new WelcomePage(username);
                        }
                });

                resetButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                usernameField.setText("");
                                passwordField.setText("");
                        }
                });
                add(panel);
                setVisible(true);
        }
}
