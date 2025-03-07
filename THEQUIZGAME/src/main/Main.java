package main;

import javax.swing.*;
import java.awt.*;

public class Main {
	JLabel dialogueLabel;
    private JFrame frame;
    private JLabel backgroundLabel, characterLabel;
    private JButton[] choiceButtons;
    private int currentQuestion = 0, lives = 5, score = 0;
    
    class Question {
        private final String questionText;
        private final String[] options;
        private final int correctAnswerIndex;
        private final String character;

        public Question(String questionText, String[] options, int correctAnswerIndex, String character) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
            this.character = character;
        }

        public String getQuestionText() {
            return questionText;
        }

        public String[] getOptions() {
            return options;
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }

        public String getCharacter() {
            return character;
        }
    }

    private final Question[] questions = {
    	    new Question("She is the first female computer programmer who writes programs for the Analytical Engine.",
    	                 new String[]{"Charles Cabbage", "Merlin Bryon", "Augusta Ada Byron", "Agusta Babbage"},
    	                 2, "rin"),

    	    new Question("Computers of this generation used vacuum tubes as the basic components for memory and circuitry for the CPU.",
    	                 new String[]{"First Generation", "Second Generation", "Third Generation", "Fourth Generation"},
    	                 0, "Haidee"),

    	    new Question("It is an automatic, mechanical calculator designed to tabulate polynomial functions.",
    	                 new String[]{"Analytic Engine", "Difference Engine", "Jam Engine", "Arithmetic Engine"},
    	                 1, "tan"),

    	    new Question("What does CPU stand for?",
    	                 new String[]{"Control Processing Unit", "Central Providing Unit", "Control Providing Unit", "Central Processing Unit"},
    	                 3, "rin"),

    	    new Question("It is also known as a \"tower\" or \"chassis\", which is the main part of a desktop computer.",
    	                 new String[]{"System Unit", "House System", "Casing", "Casing System"},
    	                 0, "Haidee"),

    	    new Question("These computers are capable of handling and processing very large amounts of data quickly.",
    	                 new String[]{"Largeframe computer", "Mainframe computer", "Dataframe computer", "Minframe computer"},
    	                 1, "tan"),

    	    new Question("It serves as a single platform to connect all the parts of a computer together.",
    	                 new String[]{"System Unit", "Casing", "Breadboard", "Motherboard"},
    	                 3, "rin"),

    	    new Question("It is the first portable computer released in 1981 by the Osborne Computer Corporation.",
    	                 new String[]{"Osborne 1", "Osborne Computer", "Osborne Portable", "Osborne Co."},
    	                 0, "Haidee"),

    	    new Question("He is the father of the computer.",
    	                 new String[]{"Douglas Engelbert", "Alan Tuning", "Charles Babbage", "George Boole"},
    	                 2, "tan"),

    	    new Question("It is an electronic device that manipulates information or data according to a list of instructions.",
    	                 new String[]{"Computer", "Calculator", "System", "Program"},
    	                 0, "rin")
    	};
    
    public Main() {
        setupUI();
        loadQuestion();
    }

    @SuppressWarnings("unused")
	private void setupUI() {
        frame = new JFrame("Quiz Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 620);
        frame.setLayout(null);

        backgroundLabel = new JLabel(new ImageIcon("images/background.png"));
        backgroundLabel.setBounds(0, 0, 800, 600);
        frame.add(backgroundLabel);
        
        dialogueLabel = new JLabel();
        dialogueLabel.setFont(new Font("Pixelify Sans", Font.BOLD, 18));
        dialogueLabel.setVerticalAlignment(SwingConstants.CENTER); 
        dialogueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dialogueLabel.setOpaque(true);
        dialogueLabel.setBackground(Color.black);
        dialogueLabel.setForeground(Color.WHITE);
        dialogueLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        dialogueLabel.setBounds(50, 450, 700, 110); 
        backgroundLabel.add(dialogueLabel);

        characterLabel = new JLabel();
        characterLabel.setBounds(250, 150, 300, 300);
        backgroundLabel.add(characterLabel);

        choiceButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            choiceButtons[i] = new JButton();
            choiceButtons[i].setBounds(200 + (i % 2) * 200, 350 + (i / 2) * 50, 180, 40);
            choiceButtons[i].setFont(new Font("Pixelify Sans", Font.BOLD, 14));
            choiceButtons[i].setBackground(Color.LIGHT_GRAY);
            choiceButtons[i].setForeground(Color.black);
            choiceButtons[i].setBorder(BorderFactory.createLineBorder(Color.white, 1));
            final int index = i;
            choiceButtons[i].addActionListener(e -> checkAnswer(index));
            backgroundLabel.add(choiceButtons[i]);
            
        }

        frame.setVisible(true);
    }

    private void loadQuestion() {
        if (currentQuestion >= questions.length) {
            dialogueLabel.setText("Game Over! You answered " + score + " correctly! Lives left: " + lives);
            for (JButton button : choiceButtons) button.setEnabled(false);
            return;
        }
        
        Question q = questions[currentQuestion];

        dialogueLabel.setText("<html><div style='text-align: center;'>" + q.getQuestionText() + "<br>(Lives: " + lives + ")</div></html>");
        String[] options = q.getOptions();
        for (int i = 0; i < choiceButtons.length; i++) {
            choiceButtons[i].setText(options[i]);
        }
        characterLabel.setIcon(new ImageIcon("images/" + q.getCharacter() + "_normal.png"));
        backgroundLabel.setComponentZOrder(characterLabel, backgroundLabel.getComponentCount() - 1);
    }
    
    private void checkAnswer(int selected) {
        Question q = questions[currentQuestion];

        if (selected == q.getCorrectAnswerIndex()) {
            dialogueLabel.setText("NICE ONE!");
            characterLabel.setIcon(new ImageIcon("images/" + q.getCharacter() + "_happy.png"));
            score++;
        } else {
            String characterName = null;
            if (q.getCharacter().equals("rin")) {
                characterName = "Rin";
            } else if (q.getCharacter().equals("Haidee")) {
                characterName = "Haidee";
            } else if (q.getCharacter().equals("tan")) {
                characterName = "Tan";
        } 

            dialogueLabel.setText(characterName + " got killed.");
            characterLabel.setIcon(new ImageIcon("images/" + q.getCharacter() + "_uh.png"));
            lives--;
        }

        if (lives == 0) {
            dialogueLabel.setText("Game Over! You ran out of lives. Correct Answers: " + score + " / " + questions.length);
            for (JButton button : choiceButtons) button.setEnabled(false);
            return;
        }

        if (currentQuestion + 1 >= questions.length) {
            dialogueLabel.setText("You completed the quiz! Correct Answers: " + score + " / " + questions.length + ". Lives Remaining: " + lives);
            for (JButton button : choiceButtons) button.setEnabled(false);
            return;
        }
        
		Timer timer = new Timer(1500, e -> {
            currentQuestion++;
            loadQuestion();
        });
        timer.setRepeats(false);
        timer.start();
    }


    public static void main(String[] args) {
        new Main();
    }
}