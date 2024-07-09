package accountbook;

import userdata.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class EarningsForm extends JFrame {
    private AccountBook accountBook;
    private JTextField tfName;
    private JTextField tfAmount;
    private JTextField tfDate;
    private JCheckBox cbFixed;
    private JButton btnAdd;
    private JButton btnCancel;

    public EarningsForm(AccountBook accountBook) {
        this.accountBook = accountBook;
        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    private void init() {
        tfName = new JTextField(10);
        tfAmount = new JTextField(10);
        tfDate = new JTextField(10);
        cbFixed = new JCheckBox("Fixed");
        btnAdd = new JButton("Add");
        btnCancel = new JButton("Cancel");
    }

    private void setDisplay() {
        JPanel pnlCenter = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlCenter.add(new JLabel("Name:"));
        pnlCenter.add(tfName);
        pnlCenter.add(new JLabel("Amount:"));
        pnlCenter.add(tfAmount);
        pnlCenter.add(new JLabel("Date (YYYY-MM-DD):"));
        pnlCenter.add(tfDate);
        pnlCenter.add(cbFixed);

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnAdd);
        pnlSouth.add(btnCancel);

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    private void addListeners(){
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name =tfName.getText();
                int amount = Integer.parseInt(tfAmount.getText());
                LocalDate date = LocalDate.parse(tfDate.getText());
                boolean isFixed = cbFixed.isSelected();

                Node node = new Node(name, amount, date, isFixed);
                accountBook.getCurrentUser().addEarning(node);
                accountBook.refreshTransactions();
                dispose();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void showFrame() {
        setTitle("Add Earnings");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
