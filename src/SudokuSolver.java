import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolver extends JFrame {
    private JTextField[][] sudokuBoard;

    public SudokuSolver() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        sudokuBoard = new JTextField[9][9];

        // Create a panel for the Sudoku board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(9, 9));

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuBoard[row][col] = new JTextField(1);
                sudokuBoard[row][col].setHorizontalAlignment(JTextField.CENTER);
                boardPanel.add(sudokuBoard[row][col]);
            }
        }

        // Create a solve button
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });

        // Create a panel for the solve button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);

        // Add the panels to the frame
        add(boardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to solve the Sudoku puzzle
    private boolean solveSudoku() {
        int[][] board = new int[9][9];

        // Initialize the board with the values entered by the user
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String value = sudokuBoard[row][col].getText();
                if (!value.isEmpty()) {
                    try {
                        board[row][col] = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid input!");
                        return false;
                    }
                } else {
                    board[row][col] = 0;
                }
            }
        }

        if (solveSudoku(board)) {
            // Update the Sudoku board with the solution
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    sudokuBoard[row][col].setText(String.valueOf(board[row][col]));
                }
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "No solution exists!");
            return false;
        }
    }

    // Recursive backtracking algorithm to solve
    private boolean solveSudoku(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValidMove(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidMove(int[][] board, int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        // Check 3x3 grid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuSolver().setVisible(true);
            }
        });
    }
}
