package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa tworząca okno startowe aplikacji
 */
public class StartFrame extends JFrame implements ActionListener {
    private JFrame frame = new JFrame();
    private static final Color VERY_LIGHT_BLUE = new Color(51, 204, 255);
    private JButton exitButton = new JButton("Wyłącz");
    private JButton visitButton = new JButton("Pokaż wizyty");
    private ImageIcon logo= new ImageIcon("logo.png");
    private JLabel logoLabel = new JLabel();
    public StartFrame(){

        visitButton.addActionListener(this);
        exitButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,1));
        visitButton.setMinimumSize(new Dimension(200,40));
        buttonPanel.add(visitButton);
        buttonPanel.add(exitButton);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setPreferredSize(new Dimension(200,100));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        frame.setLayout(new BorderLayout(0, 0));
        frame.getContentPane().setBackground(Color.WHITE);
        logoLabel.setIcon(logo);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(logoLabel,BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.PAGE_END);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setMinimumSize(new Dimension(350, 300));
        frame.setBackground(VERY_LIGHT_BLUE);
        frame.setTitle("Gabinet FizjoGab");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (exitButton == e.getSource()) {
            System.exit(0);
        }
        if (visitButton== e.getSource()) {
            frame.setVisible(false);
            try{
                new VisitFrame();
            }
            catch (Exception e1){
                e1.printStackTrace();
            }
        }

    }
}
