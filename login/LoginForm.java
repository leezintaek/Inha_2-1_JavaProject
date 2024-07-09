package login;

import accountbook.AccountBook;
import userdata.User;
import userdata.UserDataSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField tfId;
    private JPasswordField tfPw;
    private JButton btnLogin;
    private JButton btnJoin;
    private UserDataSet userDataSet;

    public LoginForm() {
        userDataSet = new UserDataSet();
        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    private void init() {
        tfId = new JTextField(10);
        tfPw = new JPasswordField(10);
        btnLogin = new JButton("Login");
        btnJoin = new JButton("Join");
    }

    private void setDisplay() {
        JPanel pnlCenter = new JPanel(new GridLayout(2, 2, 5, 5));
        pnlCenter.add(new JLabel("  ID:"));
        pnlCenter.add(tfId);
        pnlCenter.add(new JLabel("  Password:"));
        pnlCenter.add(tfPw);

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnLogin);
        pnlSouth.add(btnJoin);

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    private void addListeners() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = tfId.getText();
                String pw = new String(tfPw.getPassword());

                if (id.isEmpty() || pw.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            LoginForm.this, "Please enter your ID and password", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                User user = userDataSet.getUser(id);
                if (user != null && user.getPassword().equals(pw)) {
                    JOptionPane.showMessageDialog(
                            LoginForm.this, "Login successful!");
                    dispose();
                    new AccountBook(user);
                } else {
                    JOptionPane.showMessageDialog(
                            LoginForm.this, "ID or password does not match.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JoinForm(LoginForm.this);
                setVisible(false); //회원가입동안 로그인 창 뜨지 않는다
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(LoginForm.this,
                        "Are you sure you want to end your login?", "End",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }

    private void showFrame() { //JFrame 을 상속받는 것만으로 화면 출력이 가능
        setTitle("Login");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    public UserDataSet getUserDataSet() {
        return userDataSet;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm();
            }
        });
    }
}
