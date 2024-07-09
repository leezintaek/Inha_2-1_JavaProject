package accountbook;

import login.LoginForm;
import userdata.Node;
import userdata.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

public class AccountBook extends JFrame {
    private JButton settingButton;
    private JButton earningsButton;
    private JButton expenseButton;
    private JButton manageButton;
    private JLabel assetsLabel;
    private JPanel transactionsPanel;
    private JPanel fixedExpensesPanel;
    private JPanel fixedEarningsPanel;
    private User currentUser;
    private NumberFormat numberFormat;

    public AccountBook(User currentUser) {
        this.currentUser = currentUser;
        this.numberFormat = NumberFormat.getNumberInstance();
        init();
        setDisplay();
        addListeners();
        loadUserData();
        showFrame();
    }

    private void init() {
        settingButton = new JButton("⚙");
        earningsButton = new JButton("Earnings");
        expenseButton = new JButton("Expense");
        manageButton = new JButton("Manage");
        assetsLabel = new JLabel("Assets   " + formatNumber(currentUser.getAssets()), SwingConstants.LEFT);
        transactionsPanel = new JPanel();
        fixedExpensesPanel = new JPanel();
        fixedEarningsPanel = new JPanel();
    }

    private void setDisplay() {
        // 메인 프레임 설정
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.DARK_GRAY);

        // 왼쪽 패널
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(400, getHeight()));
        leftPanel.setBackground(Color.DARK_GRAY);

        // 자산 라벨 설정
        assetsLabel.setFont(new Font("Serif", Font.BOLD, 28));
        assetsLabel.setForeground(Color.WHITE);
        assetsLabel.setBorder(new EmptyBorder(20, 20, 0, 20));
        leftPanel.add(assetsLabel, BorderLayout.NORTH);

        // 트랜잭션 패널 설정
        transactionsPanel.setLayout(new BoxLayout(transactionsPanel, BoxLayout.Y_AXIS));
        transactionsPanel.setBackground(Color.DARK_GRAY);
        transactionsPanel.setBorder(new MatteBorder(0, 0, 0, 1, Color.GRAY));

        // 트랜잭션 컨테이너 설정
        JScrollPane scrollPane = new JScrollPane(transactionsPanel);
        scrollPane.setBorder(null);

        JPanel transactionsContainer = new JPanel(new BorderLayout());
        transactionsContainer.setBorder(new EmptyBorder(10, 20, 20, 20));
        transactionsContainer.setBackground(Color.DARK_GRAY);
        transactionsContainer.add(scrollPane, BorderLayout.CENTER);
        leftPanel.add(transactionsContainer, BorderLayout.CENTER);

        // 설정 버튼
        JPanel settingPanel = new JPanel();
        settingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        settingPanel.add(settingButton);
        settingPanel.setBackground(Color.DARK_GRAY);
        leftPanel.add(settingPanel, BorderLayout.SOUTH);

        // 오른쪽 패널
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(400, getHeight()));
        rightPanel.setBackground(Color.DARK_GRAY);

        // 상단 오른쪽 패널
        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setBackground(Color.DARK_GRAY);
        topRightPanel.add(earningsButton);
        topRightPanel.add(expenseButton);
        topRightPanel.add(manageButton);

        // 중단 오른쪽 패널 (고정 지출과 고정 수입을 반반 차지하도록 설정)
        JPanel middleRightPanel = new JPanel();
        middleRightPanel.setLayout(new GridLayout(2, 1));
        middleRightPanel.setBackground(Color.DARK_GRAY);
        middleRightPanel.setBorder(new MatteBorder(1, 0, 1, 0, Color.GRAY));

        // 고정 지출 패널
        JPanel fixedExpensesContainer = new JPanel();
        fixedExpensesContainer.setLayout(new BoxLayout(fixedExpensesContainer, BoxLayout.Y_AXIS));
        fixedExpensesContainer.setBackground(Color.DARK_GRAY);

        JLabel fixedExpensesLabel = new JLabel("Fixed Expenses");
        fixedExpensesLabel.setFont(new Font("Serif", Font.BOLD, 20));
        fixedExpensesLabel.setForeground(Color.WHITE);
        fixedExpensesLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        fixedExpensesPanel.setLayout(new BoxLayout(fixedExpensesPanel, BoxLayout.Y_AXIS));
        fixedExpensesPanel.setBackground(Color.DARK_GRAY);

        fixedExpensesContainer.add(fixedExpensesLabel);
        fixedExpensesContainer.add(fixedExpensesPanel);

        // 고정 수입 패널
        JPanel fixedEarningsContainer = new JPanel();
        fixedEarningsContainer.setLayout(new BoxLayout(fixedEarningsContainer, BoxLayout.Y_AXIS));
        fixedEarningsContainer.setBackground(Color.DARK_GRAY);

        JLabel fixedEarningsLabel = new JLabel("Fixed Earnings");
        fixedEarningsLabel.setFont(new Font("Serif", Font.BOLD, 20));
        fixedEarningsLabel.setForeground(Color.WHITE);
        fixedEarningsLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        fixedEarningsPanel.setLayout(new BoxLayout(fixedEarningsPanel, BoxLayout.Y_AXIS));
        fixedEarningsPanel.setBackground(Color.DARK_GRAY);

        fixedEarningsContainer.add(fixedEarningsLabel);
        fixedEarningsContainer.add(fixedEarningsPanel);

        middleRightPanel.add(fixedExpensesContainer);
        middleRightPanel.add(fixedEarningsContainer);

        rightPanel.add(topRightPanel, BorderLayout.NORTH);
        rightPanel.add(middleRightPanel, BorderLayout.CENTER);

        // 메인 프레임에 패널 추가
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    private void addListeners() {
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        AccountBook.this,
                        "로그아웃 하시겠습니까?",
                        "로그아웃",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    saveUserData();
                    dispose();
                    new LoginForm();
                }
            }
        });

        earningsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EarningsForm(AccountBook.this);
            }
        });

        expenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ExpenseForm(AccountBook.this);
            }
        });

        manageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Management(currentUser, AccountBook.this);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(AccountBook.this,
                        "Are you sure you want to exit the program?", "End",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    saveUserData();
                    System.exit(0);
                }
            }
        });
    }

    private void showFrame() {
        setTitle("Account Book");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateAssetsLabel(int newAsset) {
        assetsLabel.setText("Assets   " + formatNumber(newAsset));
    }

    public void refreshTransactions() {
        transactionsPanel.removeAll();
        fixedExpensesPanel.removeAll();
        fixedEarningsPanel.removeAll();

        for (Node earning : currentUser.getEarnings()) {
            addTransaction(earning, true);
        }
        for (Node expense : currentUser.getExpenses()) {
            addTransaction(expense, false);
        }

        // 고정 지출과 고정 수입을 패널에 추가
        List<Node> fixedExpenses = currentUser.getExpenses().stream()
                .filter(Node::isFixed)
                .collect(Collectors.toList());

        List<Node> fixedEarnings = currentUser.getEarnings().stream()
                .filter(Node::isFixed)
                .collect(Collectors.toList());

        for (Node fixedExpense : fixedExpenses) {
            JLabel fixedExpenseLabel = new JLabel(formatTransactionSp(fixedExpense, false));
            fixedExpenseLabel.setForeground(Color.WHITE);
            fixedExpensesPanel.add(fixedExpenseLabel);
        }

        for (Node fixedEarning : fixedEarnings) {
            JLabel fixedEarningLabel = new JLabel(formatTransactionSp(fixedEarning, true));
            fixedEarningLabel.setForeground(Color.WHITE);
            fixedEarningsPanel.add(fixedEarningLabel);
        }

        updateAssetsLabel(currentUser.getAssets());
        transactionsPanel.revalidate();
        transactionsPanel.repaint();
        fixedExpensesPanel.revalidate();
        fixedExpensesPanel.repaint();
        fixedEarningsPanel.revalidate();
        fixedEarningsPanel.repaint();
    }

    public void addTransaction(Node node, boolean isIncome) {
        JLabel transactionLabel = new JLabel(formatTransaction(node, isIncome));
        transactionLabel.setForeground(Color.WHITE); // 수입과 지출을 흰색으로 설정
        transactionLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        transactionsPanel.add(transactionLabel);
        transactionsPanel.revalidate();
        transactionsPanel.repaint();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void loadUserData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(currentUser.getId() + "_data.dat"))) {
            User savedUser = (User) ois.readObject();
            currentUser.setAssets(savedUser.getAssets());
            currentUser.setEarnings(savedUser.getEarnings());
            currentUser.setExpenses(savedUser.getExpenses());

            refreshTransactions();
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found. A new file will be created.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveUserData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(currentUser.getId() + "_data.dat"))) {
            oos.writeObject(currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatNumber(int number) {
        return numberFormat.format(number);
    }

    private String formatTransaction(Node node, boolean isIncome) {
        String sign = isIncome ? "+" : "-";
        return node.getName() + " | " + sign + formatNumber(node.getAmount()) + " | " + node.getDate();
    }

    private String formatTransactionSp(Node node, boolean isIncome) {
        String sign = isIncome ? "+" : "-";
        String day = node.getDate().getDayOfMonth() + " day";
        return node.getName() + " | " + sign + formatNumber(node.getAmount()) + " | " + day;
    }
}
