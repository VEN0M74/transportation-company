import javax.swing.*;
public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private registration registrationPage;
    private LoginPage loginPage;
    public MainFrame() {
        setTitle("Registration and Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        tabbedPane = new JTabbedPane();
        registrationPage = new registration();
        loginPage = new LoginPage();
        tabbedPane.addTab("Registration", registrationPage.getContentPane());
        tabbedPane.addTab("Login", loginPage.getContentPane());
        add(tabbedPane);
        setVisible(true);
    }
}