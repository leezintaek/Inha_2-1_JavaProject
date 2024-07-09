package accountbook;

import userdata.Node;
import userdata.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class Management extends JFrame {
    private User currentUser;
    private AccountBook accountBook;
    private JLabel totalAssetsLabel;
    private DefaultListModel<String> earningsListModel;
    private DefaultListModel<String> expensesListModel;
    private JList<String> earningsList;
    private JList<String> expensesList;
    private JButton btnDeleteEarning;
    private JButton btnDeleteExpense;
    private JButton btnChangeEarning;
    private JButton btnChangeExpense;

    public Management(User currentUser, AccountBook accountBook) {
        this.currentUser = currentUser;
        this.accountBook = accountBook;
        init();
        setDisplay();
        addListeners();
        showFrame();
    }

    private void init() {
        totalAssetsLabel = new JLabel("Total Assets: " + formatNumber(currentUser.getAssets()));
        earningsListModel = new DefaultListModel<>();
        expensesListModel = new DefaultListModel<>();
        earningsList = new JList<>(earningsListModel);
        expensesList = new JList<>(expensesListModel);
        btnDeleteEarning = new JButton("Delete Earning");
        btnDeleteExpense = new JButton("Delete Expense");
        btnChangeEarning = new JButton("Change Earning");
        btnChangeExpense = new JButton("Change Expense");


        for (Node earning : currentUser.getEarnings()) {
            earningsListModel.addElement(earning.toString());
        }

        for (Node expense : currentUser.getExpenses()) {
            expensesListModel.addElement(expense.toString());
        }
    }

private void setDisplay() {
    JPanel pnlNorth = new JPanel();
    pnlNorth.add(totalAssetsLabel);

    JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 10, 10));
    pnlCenter.add(new JScrollPane(earningsList));
    pnlCenter.add(new JScrollPane(expensesList));

    JPanel pnlSouth = new JPanel(new GridLayout(1, 2));

    JPanel pnlSouthLeftTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
    pnlSouthLeftTop.add(btnDeleteEarning);

    JPanel pnlSouthLeftBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
    pnlSouthLeftBottom.add(btnChangeEarning);

    JPanel pnlSouthRightTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
    pnlSouthRightTop.add(btnDeleteExpense);

    JPanel pnlSouthRightBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
    pnlSouthRightBottom.add(btnChangeExpense);

    pnlSouth.add(pnlSouthLeftTop);
    pnlSouth.add(pnlSouthLeftBottom);
    pnlSouth.add(pnlSouthRightTop);
    pnlSouth.add(pnlSouthRightBottom);

    add(pnlNorth, BorderLayout.NORTH);
    add(pnlCenter, BorderLayout.CENTER);
    add(pnlSouth, BorderLayout.SOUTH);
}


    private void addListeners() {
        btnDeleteEarning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = earningsList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Node removed = currentUser.getEarnings().remove(selectedIndex);
                    currentUser.setAssets(currentUser.getAssets() - removed.getAmount());
                    earningsListModel.remove(selectedIndex);
                    updateTotalAssetsLabel();
                    accountBook.refreshTransactions();
                }
            }
        });

        btnDeleteExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = expensesList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Node removed = currentUser.getExpenses().remove(selectedIndex);
                    currentUser.setAssets(currentUser.getAssets() + removed.getAmount());
                    expensesListModel.remove(selectedIndex);
                    updateTotalAssetsLabel();
                    accountBook.refreshTransactions();
                }
            }
        });

        btnChangeEarning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = earningsList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Node selectedNode = currentUser.getEarnings().get(selectedIndex);
                    new ChangeInfoForm(selectedNode, Management.this);
                }
            }
        });

        btnChangeExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = expensesList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Node selectedNode = currentUser.getExpenses().get(selectedIndex);
                    new ChangeInfoForm(selectedNode, Management.this);
                }
            }
        });
    }

    private void showFrame() {
        setTitle("Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateTotalAssetsLabel() {
        totalAssetsLabel.setText("Total Assets: " + formatNumber(currentUser.getAssets()));
    }

    private String formatNumber(int number) {
        return NumberFormat.getNumberInstance().format(number);
    }

    public void refreshLists() {
        earningsListModel.clear();
        expensesListModel.clear();
        for (Node earning : currentUser.getEarnings()) {
            earningsListModel.addElement(earning.toString());
        }
        for (Node expense : currentUser.getExpenses()) {
            expensesListModel.addElement(expense.toString());
        }
        updateTotalAssetsLabel();
        accountBook.refreshTransactions();
    }
}
