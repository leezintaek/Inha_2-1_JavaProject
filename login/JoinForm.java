package login;

import userdata.User;
import userdata.UserDataSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinForm extends JFrame {
    private JTextField tfId;
    private JPasswordField tfPw;
    private JPasswordField tfPwConfirm;
    private JTextField tfName;
    private JComboBox<String> cbJob;
    private JButton btnJoin;
    private JButton btnCancel;
    private UserDataSet userDataSet;
    private LoginForm loginForm;

    public JoinForm(LoginForm loginForm) {
        this.loginForm = loginForm;
        this.userDataSet = loginForm.getUserDataSet();

        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    private void init() {
        tfId = new JTextField(10);
        tfPw = new JPasswordField(10);
        tfPwConfirm = new JPasswordField(10);
        tfName = new JTextField(10);
        cbJob = new JComboBox<>(new String[]{"Student", "Worker", "Child"});
        btnJoin = new JButton("Join");
        btnCancel = new JButton("Cancel");
    }

    private void setDisplay() {
        JPanel pnlCenter = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlCenter.add(new JLabel("  ID:"));
        pnlCenter.add(tfId);
        pnlCenter.add(new JLabel("  Password:"));
        pnlCenter.add(tfPw);
        pnlCenter.add(new JLabel("  Confirm Password:"));
        pnlCenter.add(tfPwConfirm);
        pnlCenter.add(new JLabel("  Name:"));
        pnlCenter.add(tfName);
        pnlCenter.add(new JLabel("  Job:"));
        pnlCenter.add(cbJob);

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnJoin);
        pnlSouth.add(btnCancel);

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    private void addListeners() {
        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = tfId.getText();
                String pw = new String(tfPw.getPassword());
                String pwConfirm = new String(tfPwConfirm.getPassword());
                String name = tfName.getText();
                String job = (String) cbJob.getSelectedItem();

                if (id.isEmpty() || pw.isEmpty() || pwConfirm.isEmpty() || name.isEmpty() || job.isEmpty()) {
                    JOptionPane.showMessageDialog(JoinForm.this, "All fields are required.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!pw.equals(pwConfirm)) {
                    JOptionPane.showMessageDialog(JoinForm.this, "Passwords do not match.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (userDataSet.containsUser(id)) {
                    JOptionPane.showMessageDialog(JoinForm.this, "ID already exists.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                User newUser = new User(id, pw, name, job);
                userDataSet.addUser(newUser);
                userDataSet.saveUserData();
                JOptionPane.showMessageDialog(JoinForm.this, "Registration successful.");
                dispose();
                loginForm.setVisible(true);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                loginForm.setVisible(true);
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(JoinForm.this,
                        "Are you sure you want to end your join?", "End",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    dispose();
                    loginForm.setVisible(true);
                }
            }
        });
    }

    private void showFrame() {
        setTitle("Join");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
