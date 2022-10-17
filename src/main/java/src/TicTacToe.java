package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe implements ActionListener {

    public static final String PLAYER_O = "O";
    public static final String PLAYER_X = "X";
    private Random random = new Random();
    private JFrame frame = new JFrame();
    private JPanel titlePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel refreshButtonPanel = new JPanel();
    private JButton refreshButton = new JButton();
    private JLabel textField = new JLabel();
    private JButton[] buttons = new JButton[9];
    private boolean playerFirstTurn;

    private int attemptCount = 0;

    public void setUpGame() throws InterruptedException {
        attemptCount = 0;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(25, 255, 0));
        textField.setFont(new Font("Ink Free", Font.BOLD, 75));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));

        refreshButton.setBackground(new Color(25, 25, 25));
        refreshButton.setForeground(new Color(25, 255, 0));
        refreshButton.setFont(new Font("Ink Free", Font.BOLD, 50));
        refreshButton.setHorizontalAlignment(JLabel.CENTER);
        refreshButton.setOpaque(true);
        refreshButton.setFocusable(false);
        refreshButton.addActionListener(this);
        refreshButton.setText("Reset");

        refreshButtonPanel.setLayout(new BorderLayout());
        refreshButtonPanel.setBounds(0, 0, 800, 50);
        refreshButtonPanel.add(refreshButton);

        buttonPanel.setBackground(new Color(150, 150, 150));


        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setBackground(new Color(50, 50, 50));
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        titlePanel.add(textField);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.add(refreshButtonPanel, BorderLayout.SOUTH);

        textField.setText("Tic-Tac-Toe");
        Thread.sleep(500);
        firstTurn();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("Action triggered!! Attempt : " + attemptCount);
        if( e.getSource() == refreshButton ){
             resetGame();
        }
        for (JButton button : buttons) {
            if (e.getSource() == button) {
                attemptCount++;
                if (playerFirstTurn) {
                    if (button.getText().equals("")) {
                        button.setForeground(Color.RED);
                        button.setText(PLAYER_X);
                        playerFirstTurn = false;
                        textField.setText(PLAYER_O + " turn");
                        if (attemptCount > 4) {
                            checkGameStatus(PLAYER_X);
                        }
                    }
                } else {
                    if (button.getText().equals("")) {
                        button.setForeground(Color.BLUE);
                        button.setText(PLAYER_O);
                        playerFirstTurn = true;
                        textField.setText(PLAYER_X + " turn");
                        if (attemptCount > 4) {
                            checkGameStatus(PLAYER_O);
                        }
                    }
                }
            }
        }
    }

    private void resetGame() {
        System.out.println("Reset Game!");
        attemptCount=0;
        firstTurn();

        for (int i = 0; i < 9; i++) {
            buttons[i].setBackground(new Color(50, 50, 50));
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
    }

    public void firstTurn() {
        if (random.nextInt(2) == 0) {
            playerFirstTurn = true;
            textField.setText(PLAYER_X + " turn");
        } else {
            playerFirstTurn = false;
            textField.setText(PLAYER_O + " turn");
        }
    }

    private void checkGameStatus(String player) {
        System.out.println("Checking if player : " + player + " won! Attempt : " + attemptCount);

        // 0 1 2
        // 3 4 5
        // 6 7 8

        //Rows
        checkWinningIndexes(0, 1, 2, player);
        checkWinningIndexes(3, 4, 5, player);
        checkWinningIndexes(6, 7, 8, player);

        //Columns
        checkWinningIndexes(0, 3, 6, player);
        checkWinningIndexes(1, 4, 7, player);
        checkWinningIndexes(2, 5, 8, player);

        //Diagonals
        checkWinningIndexes(0, 4, 8, player);
        checkWinningIndexes(2, 4, 6, player);
    }

    private void checkWinningIndexes(int x1, int x2, int x3, String player) {
        if ((buttons[x1].getText() == player) && (buttons[x2].getText() == player) && (buttons[x3].getText() == player)) {
            playerWinsMessage(x1, x2, x3, player);
        }
    }

    public void playerWinsMessage(int a, int b, int c, String player) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }

        textField.setText(player + " Wins");
    }

}
