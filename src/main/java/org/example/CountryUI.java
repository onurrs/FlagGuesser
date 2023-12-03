package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class CountryUI extends JFrame implements ActionListener {

    JPanel questionPanel;

    private JLabel flagLabel;
    private JLabel answerLabel;

    JPanel buttonPanel;

    private JButton option1;
    private JButton option2;
    private JButton option3;
    private JButton option4;

    JPanel sidePanel;

    private JButton resetButton;
    private JLabel scoreLabel;
    private int points = 0;
    private int count = 0;

    private JButton categoryAll;
    private JButton categoryEurope;
    private JButton categoryAsia;
    private JButton categoryAfrica;
    private JButton categoryAmerica;
    private JButton categoryOceania;

    Font buttonFont = new Font("Century Schoolbook", Font.BOLD, 20);

    Country country;
    RestAPI api;

    CountryUI() {
        setTitle("Country Guesser");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generateSidePanel();
        generateQuestionPanel();
        generateOptionsPanel();

        add(buttonPanel, BorderLayout.SOUTH);
        add(sidePanel, BorderLayout.EAST);
        add(questionPanel, BorderLayout.CENTER);

        // Pencereyi göster
        setVisible(true);

        api = new RestAPI("all");

        country = api.RandomCountry();

        answerLabel.setText("Please choose one of the following:");
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        option4.setEnabled(true);
        resetButton.setEnabled(true);
        categoryAll.setEnabled(true);
        categoryEurope.setEnabled(true);
        categoryAmerica.setEnabled(true);
        categoryAsia.setEnabled(true);
        categoryAfrica.setEnabled(true);
        categoryOceania.setEnabled(true);
        answerLabel.setFont(new Font("Century Schoolbook", Font.BOLD, 15));

        setInitial();

    }


    void setInitial() {
        determineTrueOption();

    }

    void nextQuestion() {
        country = api.RandomCountry();
        determineTrueOption();
    }

    void determineTrueOption() {
        int randomIndex = (int) (Math.random() * 4) + 1;

        switch (randomIndex) {
            case 1 -> {
                option1.setText(country.getName());
                option2.setText(api.RandomCountryName());
                option3.setText(api.RandomCountryName());
                option4.setText(api.RandomCountryName());
            }
            case 2 -> {
                option2.setText(country.getName());
                option1.setText(api.RandomCountryName());
                option3.setText(api.RandomCountryName());
                option4.setText(api.RandomCountryName());
            }
            case 3 -> {
                option3.setText(country.getName());
                option1.setText(api.RandomCountryName());
                option2.setText(api.RandomCountryName());
                option4.setText(api.RandomCountryName());
            }
            case 4 -> {
                option4.setText(country.getName());
                option1.setText(api.RandomCountryName());
                option2.setText(api.RandomCountryName());
                option3.setText(api.RandomCountryName());
            }
        }

        try {
            flagLabel.setText("");
            flagLabel.setIcon(new ImageIcon(new URL(country.getFlag())));
        } catch (Exception e) {
            flagLabel.setText("ERROR LOADING IMAGE");
            answerLabel.setText("Establish your connection and restart again!");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == option1 || e.getSource() == option2 || e.getSource() == option3 || e.getSource() == option4) {
            if (e.getActionCommand().equals(country.getName())) {
                answerLabel.setForeground(new Color(14, 133, 0));
                answerLabel.setText("CORRECT");
                points++;
            } else {
                answerLabel.setForeground(new Color(183, 0, 0));
                answerLabel.setText("WRONG! : It was " + country.getName());
            }
            count++;
        }
        if (e.getSource() == resetButton) {
            count = 0;
            points = 0;
        }

        if (e.getSource() == categoryAll) {
            api = new RestAPI("all");
            answerLabel.setForeground(Color.BLACK);
            answerLabel.setText("Category Changed to all.");
        } else if (e.getSource() == categoryAfrica) {
            api = new RestAPI("region/africa");
            answerLabel.setForeground(Color.BLACK);
            answerLabel.setText("Category Changed to Africa.");
        } else if (e.getSource() == categoryAmerica) {
            api = new RestAPI("region/america");
            answerLabel.setForeground(Color.BLACK);
            answerLabel.setText("Category Changed to America.");
        } else if (e.getSource() == categoryAsia) {
            api = new RestAPI("region/asia");
            answerLabel.setForeground(Color.BLACK);
            answerLabel.setText("Category Changed to Asia.");
        } else if (e.getSource() == categoryEurope) {
            api = new RestAPI("region/europe");
            answerLabel.setForeground(Color.BLACK);
            answerLabel.setText("Category Changed to Europe.");
        } else if (e.getSource() == categoryOceania) {
            api = new RestAPI("region/oceania");
            answerLabel.setForeground(Color.BLACK);
            answerLabel.setText("Category Changed to Oceania.");
        }


        nextQuestion();
        scoreLabel.setText("Score: " + points + "/" + count);
        System.out.println(country.getFlag());
        System.out.println(country.getName());
    }

    private void generateQuestionPanel() {
        flagLabel = new JLabel();
        flagLabel.setHorizontalAlignment(JLabel.CENTER);
        flagLabel.setVerticalAlignment(JLabel.CENTER);


        answerLabel = new JLabel("Connecting... Please wait.");
        answerLabel.setFont(new Font("Century Schoolbook", Font.PLAIN, 15));
        answerLabel.setHorizontalAlignment(JLabel.CENTER);
        answerLabel.setBorder(new EmptyBorder(0, 0, 40, 0));


        questionPanel = new JPanel(new BorderLayout(0, 0));
        questionPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        questionPanel.add(flagLabel, BorderLayout.CENTER);
        questionPanel.add(answerLabel, BorderLayout.SOUTH);
        questionPanel.setBackground(Color.green);
    }

    private void generateSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(10, 1, 0, 10));
        sidePanel.setBorder(new EmptyBorder(10, 30, 0, 30));
        sidePanel.setBackground(new Color(54, 194, 54));

        scoreLabel = new JLabel("Score: " + points + "/" + count);
        scoreLabel.setFont(new Font("Century Schoolbook", Font.BOLD, 20));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);

        resetButton = new JButton("Reset Score");
        resetButton.setFocusable(false);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.addActionListener(this);
        resetButton.setEnabled(false);
        resetButton.setMaximumSize(new Dimension(150, 10));

        JLabel categoryHeader = new JLabel("CATEGORIES:");
        categoryHeader.setFont(new Font("Century Schoolbook", Font.BOLD, 15));
        categoryHeader.setHorizontalAlignment(JLabel.CENTER);

        sidePanel.add(scoreLabel);
        sidePanel.add(resetButton);
        sidePanel.add(categoryHeader);

        Dimension categoryDimension = new Dimension(150, 30);
        categoryAll = new JButton("All");
        categoryAfrica = new JButton("Africa");
        categoryAmerica = new JButton("America");
        categoryAsia = new JButton("Asia");
        categoryEurope = new JButton("Europe");
        categoryOceania = new JButton("Oceania");
        JButton[] categories = {categoryAll, categoryEurope, categoryAmerica, categoryAsia, categoryAfrica, categoryOceania};
        for (JButton category : categories) {
            category.setEnabled(false);
            category.setAlignmentX(Component.CENTER_ALIGNMENT);
            category.setMaximumSize(categoryDimension);
            category.addActionListener(this);
            category.setFont(new Font("Century Schoolbook", Font.BOLD, 15));
            category.setFocusable(false);
            category.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            sidePanel.add(category);
        }

    }

    private void generateOptionsPanel() {
        buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(Color.green);

        option1 = new JButton("Option 1");
        option2 = new JButton("Option 2");
        option3 = new JButton("Option 3");
        option4 = new JButton("Option 4");

        Dimension buttonDimension = new Dimension(90, 70);

        JButton[] options = {option1, option2, option3, option4};

        for (JButton option : options) {
            option.setFocusable(false);
            option.setEnabled(false);
            option.setFont(buttonFont);
            option.setPreferredSize(buttonDimension);
            buttonPanel.add(option);
            option.addActionListener(this);
            option.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

    }
}
