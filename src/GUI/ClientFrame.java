package GUI;

import Others.FileUtils;
import PhysiotherapyCabinet.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 * Klasa odpowiedzialna za wyświetlanie listy klientów i uzupłenienie ich karty pacjetna
 */
public class ClientFrame extends JFrame implements ActionListener {
    private static final Color VERY_LIGHT_BLUE = new Color(51, 204, 255);
    private JFrame frame = new JFrame();
    private JButton backButton = new JButton("Cofnij do okna wizyt");
    private JButton saveButton = new JButton("Zapisz kartę pacjenta");
    private JTextArea visitsHistory = new JTextArea();
    private JList customerList;
    private JTextField visitInformation = new JTextField();
    private int selectedClientNumber;
    private Vector<String> customerMedicalHistory;
    private JLabel visitHistoryLabel = new JLabel("Karta pacjenta:");
    private JLabel addToInformationLabel = new JLabel("Dodaj informację do karty pacjenta:");

    /**
     * Konstruktor
     *
     * @param customerTable - tablica klientów
     */
    public ClientFrame(Customer[] customerTable) {
        saveButton.setEnabled(false);
        saveButton.addActionListener(this);
        backButton.addActionListener(this);
        customerList = new JList(customerTable);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(saveButton);
        buttonsPanel.add(backButton);

        JPanel visitInformationPanel = new JPanel();
        visitInformationPanel.setLayout(new GridLayout(2, 1));
        JScrollPane sc1 = new JScrollPane(visitInformation);
        visitInformationPanel.add(addToInformationLabel);
        visitInformationPanel.add(sc1);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(0, 0));
        visitsHistory.setEnabled(false);
        JScrollPane sc2 = new JScrollPane(visitsHistory);
        bottomPanel.add(visitHistoryLabel, BorderLayout.PAGE_START);
        bottomPanel.add(sc2, BorderLayout.CENTER);
        bottomPanel.add(visitInformationPanel, BorderLayout.PAGE_END);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(1300, 600);
        frame.setBackground(VERY_LIGHT_BLUE);
        frame.setTitle("Gabinet FizjoGab - Edycja Karty Pacjenta");
        frame.setMinimumSize(new Dimension(800, 300));
        frame.add(customerList, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        customerList.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                showVisitHistory(mouseEvent, customerTable);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (saveButton == e.getSource()) {
            String str = visitInformation.getText();
            customerMedicalHistory.addElement(str);
            visitsHistory.append(str + "\n");
            visitInformation.setText("");
            Customer.getCustomerByNumber(selectedClientNumber).setMedicalHisotry(customerMedicalHistory);
            try {
                FileUtils.saveToFile();
            } catch (Exception e1) {

            }

        }
        if (backButton == e.getSource()) {
            frame.setVisible(false);
        }
    }

    /**
     * Reguje na klikniecie w table pacjentów i wyświela informacje z karty pacjenta
     *
     * @param mouseEvent
     * @param customerTable
     */
    public void showVisitHistory(MouseEvent mouseEvent, Customer[] customerTable) {
        JList list = (JList) mouseEvent.getSource();
        saveButton.setEnabled(true);
        int index = list.locationToIndex(mouseEvent.getPoint());
        customerMedicalHistory = (customerTable[index]).getMedicalHisotry();
        String tmp = "";
        for (int i = 0; i < customerMedicalHistory.size(); i++) {
            tmp = tmp + customerMedicalHistory.get(i) + "\n";
        }
        visitsHistory.setText(tmp);
        selectedClientNumber = customerTable[index].getCustomerNumber();

    }
}
