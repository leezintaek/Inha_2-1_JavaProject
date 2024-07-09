package accountbook;

import userdata.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ChangeInfoForm extends JFrame {
    private Node node;
    private Management management;
    private JTextField tfName;
    private JTextField tfAmount;
    private JTextField tfDate;
    private JCheckBox cbFixed;
    private JButton btnSave;
    private JButton btnCancel;

    public ChangeInfoForm(Node node, Management management) {
        this.node = node;
        this.management = management;
        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    private void init() {
        tfName = new JTextField(node.getName(), 10);
        tfAmount = new JTextField(String.valueOf(node.getAmount()), 10);
        tfDate = new JTextField(node.getDate().toString(), 10);
        cbFixed = new JCheckBox("Fixed", node.isFixed());
        btnSave = new JButton("Save");
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
        pnlSouth.add(btnSave);
        pnlSouth.add(btnCancel);

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    private void addListeners() {
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();
                int amount = Integer.parseInt(tfAmount.getText());
                LocalDate date = LocalDate.parse(tfDate.getText());
                boolean isFixed = cbFixed.isSelected();

                node.setName(name);
                node.setAmount(amount);
                node.setDate(date);
                node.setFixed(isFixed);

                management.refreshLists();
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
        setTitle("Change Info");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
