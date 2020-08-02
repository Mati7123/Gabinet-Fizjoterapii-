package GUI;

import Others.FileUtils;
import Others.ObjectPlusPlus;
import Others.RoleName;
import PhysiotherapyCabinet.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Vector;
import java.util.stream.Stream;

/**
 * Klasa odpowiedzialna za wyświetlenie okna do zarządzania wizytami
 */
public class VisitFrame extends JFrame implements ActionListener, RoleName {

    private JFrame frame = new JFrame();
    private JPanel selectedFilterPanel = new JPanel();
    private static final Color VERY_LIGHT_BLUE = new Color(51, 204, 255);
    private JRadioButton dataFilterRadioButton = new JRadioButton("Data");
    private JRadioButton statusFilterRadioButton = new JRadioButton(("Status"));
    private JButton dateFilterButton = new JButton("Filtruj");
    private JButton statusFilterButton = new JButton("Filtruj");
    private JButton deleteFilterButton = new JButton("Usuń filtry");
    private JButton endVisitButton = new JButton("Zakończ wizytę");
    private JButton cancelVisitButton = new JButton("Anuluj wizytę");
    private JButton startVisitButton = new JButton("Rozpocznij wizytę");
    private JButton buttonBack = new JButton("Powrót do menu");
    private JLabel titleLabel = new JLabel("Wszystkie wizyty");
    private JLabel labelDay = new JLabel("Dzień");
    private JLabel labelMonth = new JLabel("Miesiąc");
    private JLabel labelYear = new JLabel("Rok");
    private JTable table = new JTable();
    private JComboBox<Integer> dayComboBox;
    private JComboBox<Integer> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<VisitStatus> statusComboBox;
    private JScrollPane scrollPane;
    private boolean visitDateFilterSelected = false;
    private boolean visitStatusFilterSelected = false;
    private LocalDate date;
    private VisitStatus status;

    private Comparator<Visit> compareByDate = Visit.getCompareByDate();

    public VisitFrame() throws Exception {
        createComboBoxes();
        setButtonsEnabled(false);
        createTable(Visit.showAllVisits());
        endVisitButton.addActionListener(this);
        dateFilterButton.addActionListener(this);
        statusFilterButton.addActionListener(this);
        deleteFilterButton.addActionListener(this);
        startVisitButton.addActionListener(this);
        cancelVisitButton.addActionListener(this);
        buttonBack.addActionListener(this);

        JPanel filterPanel = new JPanel();
        ButtonGroup bg = new ButtonGroup();
        bg.add(statusFilterRadioButton);
        bg.add(dataFilterRadioButton);
        filterPanel.add(statusFilterRadioButton);
        filterPanel.add(dataFilterRadioButton);
        dateFilterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterSelection();

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(0, 0));
        leftPanel.add(filterPanel, BorderLayout.NORTH);
        leftPanel.add(selectedFilterPanel, BorderLayout.CENTER);
        leftPanel.add(deleteFilterButton, BorderLayout.PAGE_END);
        leftPanel.setPreferredSize(new Dimension(180, 200));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        endVisitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(startVisitButton);
        buttonPanel.add(endVisitButton);
        buttonPanel.add(cancelVisitButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(0, 0));
        bottomPanel.add(buttonPanel, BorderLayout.LINE_START);
        bottomPanel.add(buttonBack, BorderLayout.LINE_END);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bottomPanel.setPreferredSize(new Dimension(1200, 40));

        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 180, 10, 10));
        titleLabel.setFont(new Font("Serif", Font.BOLD, 16));

        scrollPane = new JScrollPane(table);

        frame.setBackground(VERY_LIGHT_BLUE);
        frame.setTitle("Gabinet FizjoGab - Wizyty");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 600);
        frame.add(titleLabel, BorderLayout.PAGE_START);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.PAGE_END);
        frame.add(leftPanel, BorderLayout.LINE_START);
        frame.setMinimumSize(new Dimension(800, 300));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    /**
     * Metoda uzupełniająca tablcę JTable i obsługująca podwójne kliknięcie na dany wiersz
     *
     * @param visits - dwuwymiarowy wektor opisujący wizyty
     * @throws Exception
     */
    private void createTable(Vector<Vector> visits) throws Exception {
        table.removeAll();
        Vector<String> columnNames = new Vector<>();
        columnNames.addElement("Data");
        columnNames.addElement("Godzina Rozpoczęcia");
        columnNames.addElement("Godzina Zakończenia");
        columnNames.addElement("Grupowa");
        columnNames.addElement("Typ");
        columnNames.addElement("Fizjoterapeuta");
        columnNames.addElement("Klient");
        columnNames.addElement("Status");
        columnNames.addElement("Cena");

        table = new JTable(visits, columnNames) {
            private static final long serialVersionUID = 2L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(3).setPreferredWidth(5);
        table.getColumnModel().getColumn(8).setPreferredWidth(15);
        table.getColumnModel().getColumn(7).setPreferredWidth(20);

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                showCustomerTable(mouseEvent);
            }
        });
    }

    /**
     * Metoda obsługjąca kliknięcia w przyciski
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (endVisitButton == e.getSource() || startVisitButton == e.getSource() || cancelVisitButton == e.getSource()) {
            changeVisitStatus(e);
        }
        if (dateFilterButton == e.getSource()) {
            setButtonsEnabled(false);
            visitDateFilterSelected = true;
            visitStatusFilterSelected = false;
            date = LocalDate.of((Integer) yearComboBox.getSelectedItem(), (Integer) monthComboBox.getSelectedItem(), (Integer) dayComboBox.getSelectedItem());
            titleLabel.setText("Wizyty z dnia: " + date);
            try {
                createTable(Visit.showVisits(date));
                refreshTable();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (statusFilterButton == e.getSource()) {
            setButtonsEnabled(false);
            visitStatusFilterSelected = true;
            visitDateFilterSelected = false;
            status = (VisitStatus) statusComboBox.getSelectedItem();
            titleLabel.setText("Wizyty o statusie: " + status);
            try {
                createTable(Visit.showVisits(status));
                refreshTable();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (deleteFilterButton == e.getSource()) {
            setButtonsEnabled(false);
            visitDateFilterSelected = false;
            visitStatusFilterSelected = false;
            titleLabel.setText("Wszystkie wizyty");
            try {
                createTable(Visit.showAllVisits());
                refreshTable();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (buttonBack == e.getSource()) {
            frame.setVisible(false);
            new StartFrame();
        }
    }

    /**
     * Metoda tworząca comoboBoxes
     */
    private void createComboBoxes() {
        Integer[] days = new Integer[31];
        Integer[] months = new Integer[12];
        Integer[] years = new Integer[20];
        for (int i = 0; i < days.length; i++) days[i] = i + 1;
        for (int i = 0; i < months.length; i++) months[i] = i + 1;
        for (int i = 0; i < years.length; i++) years[i] = 2020 + i;
        dayComboBox = new JComboBox<>(days);
        monthComboBox = new JComboBox<>(months);
        yearComboBox = new JComboBox<>(years);

        VisitStatus[] statuses = {VisitStatus.Zakończona, VisitStatus.Zarezerwowana, VisitStatus.Rozpoczęta, VisitStatus.Anulowana};
        statusComboBox = new JComboBox<>(statuses);

    }

    /**
     * Metoda odświżająca wartości w wyświetlanej tablicy JTable
     */
    private void refreshTable() {
        frame.remove(scrollPane);
        scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);
        frame.add(scrollPane, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    /**
     * Metoda obsługjąca wybór opcji filtrowania
     */
    private void filterSelection() {
        JPanel statusFilterPanel = new JPanel();
        JPanel dateFilterPanel = new JPanel();
        JPanel dayPanel = new JPanel();
        dayPanel.add(labelDay);
        dayPanel.add(dayComboBox);

        JPanel monthPanel = new JPanel();
        monthPanel.add(labelMonth);
        monthPanel.add(monthComboBox);

        JPanel yearPanel = new JPanel();
        yearPanel.add(labelYear);
        yearPanel.add(yearComboBox);

        statusFilterRadioButton.addChangeListener(e -> {
            selectedFilterPanel.removeAll();
            statusFilterPanel.setLayout(new GridLayout(2, 1));
            statusFilterPanel.add(statusComboBox);
            statusFilterPanel.add(statusFilterButton);
            selectedFilterPanel.add(statusFilterPanel, BorderLayout.PAGE_END);
            SwingUtilities.updateComponentTreeUI(frame);

        });

        dataFilterRadioButton.addChangeListener(e -> {
            selectedFilterPanel.removeAll();
            dateFilterPanel.setLayout(new GridLayout(4, 1));
            dateFilterPanel.add(dayPanel);
            dateFilterPanel.add(monthPanel);
            dateFilterPanel.add(yearPanel);
            dateFilterPanel.add(dateFilterButton);
            selectedFilterPanel.add(dateFilterPanel, BorderLayout.PAGE_END);
            SwingUtilities.updateComponentTreeUI(frame);
        });

    }

    /**
     * Metoda włączająca/ wyłączająca możlwiość użycia przycisków odpowiedzialnych za zmiane statusu wizyty
     *
     * @param enabled
     */
    private void setButtonsEnabled(boolean enabled) {
        startVisitButton.setEnabled(enabled);
        cancelVisitButton.setEnabled(enabled);
        endVisitButton.setEnabled(enabled);
    }

    /**
     * Metoda zmieniająca status wizyty
     *
     * @param v     - dana wizyta
     * @param e     - kliknięcie na dany przycisk
     * @param index - wiersz tablicy
     */
    private void setVisitStatus(Visit v, ActionEvent e, int index) {
        if (endVisitButton == e.getSource()) {
            v.endVisit();
            table.setValueAt(VisitStatus.Zakończona, index, 7);
        } else if (startVisitButton == e.getSource()) {
            v.startVisit();
            table.setValueAt(VisitStatus.Rozpoczęta, index, 7);
        } else {
            v.cancelVisit();
            table.setValueAt(VisitStatus.Anulowana, index, 7);
        }
        table.revalidate();
    }


    /**
     * Zmienia status wizyty dla konkretnie wybranej wizyty
     *
     * @param e
     */
    public void changeVisitStatus(ActionEvent e) {
        try {
            int index = table.getSelectedRow();
            Stream<Visit> visitStream = Visit.createVisitStream();
            if (visitDateFilterSelected) {
                visitStream.filter(v -> v.getVisitDate().equals(date)).sorted(compareByDate).skip(index).findFirst().ifPresent(v -> setVisitStatus(v, e, index));
            } else if (visitStatusFilterSelected) {
                visitStream.filter(v -> v.getStatus().equals(status)).sorted(compareByDate).skip(index).findFirst().ifPresent(v -> setVisitStatus(v, e, index));
            } else {
                visitStream.sorted(compareByDate).skip(index).findFirst().ifPresent(v -> setVisitStatus(v, e, index));
            }
            FileUtils.saveToFile();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Metoda tworząca tablice klientów i urchumiająca klasę ClientList
     *
     * @param o - tablica klientów
     * @see ClientFrame
     */
    private void createCustomerTable(ObjectPlusPlus[] o) {
        Customer[] customerTable = new Customer[o.length];
        for (int i = 0; i < o.length; i++) {
            customerTable[i] = (Customer) o[i];
        }
        ClientFrame clientList = new ClientFrame(customerTable);
    }

    /**
     * Metoda uruchumiająca właściwą table klientów dla wybranej wizyty po dwukrotnym kliknięciu
     *
     * @param mouseEvent
     */
    public void showCustomerTable(MouseEvent mouseEvent) {
        setButtonsEnabled(true);
        JTable table = (JTable) mouseEvent.getSource();
        int index = table.getSelectedRow();
        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
            try {
                Stream<Visit> visitStream = Visit.createVisitStream();
                if (visitDateFilterSelected) {
                    visitStream.filter(v -> v.getVisitDate().equals(date)).sorted(compareByDate).skip(index).findFirst().ifPresent(v -> {
                        try {
                            createCustomerTable(v.getLinks(customerRole));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    });
                } else if (visitStatusFilterSelected) {
                    visitStream.filter(v -> v.getStatus().equals(status)).sorted(compareByDate).skip(index).findFirst().ifPresent(v -> {
                        try {
                            createCustomerTable(v.getLinks(customerRole));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    });
                } else {
                    visitStream.sorted(compareByDate).skip(index).findFirst().ifPresent(v -> {
                        try {
                            createCustomerTable(v.getLinks(customerRole));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    });
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

}
