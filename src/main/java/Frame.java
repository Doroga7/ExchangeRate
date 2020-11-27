import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * Класс создания интерфейса и работы с данными (отображение) в интерфейсе
 */
public class Frame extends JFrame {
    JTextField jTextField1; // окно ввода суммы
    JButton button1; // кнопка выполнения конвертации
    JComboBox comboBox1; // выпадающий список первой валюты
    JComboBox comboBox2; // выпадающий список второй валюты
    JTextArea jTextArea; // окно для ввода суммы для конвертации
    DataBase dataBase = new DataBase();
    String[][] wa = dataBase.loadingDataToDatabase();

    public Frame() {
        super();
        createGUI();
    }

    /**
     * метод отображения рабочей панели
     */
    public void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        button1 = new JButton("Конвертировать");
        button1.setBounds(175, 205, 140, 30);
        panel.add(button1);
        ActionListener actionListener = new TestActionListener();
        button1.addActionListener(actionListener);

        String[] items = {
                "Выбор валюты",
                "Австралийский доллар", "Азербайджанский манат", "Фунт стерлингов Соединенного королевства", "Армянских драмов",
                "Белорусский рубль", "Болгарский лев", "Бразильский реал", "Венгерских форинтов", "Гонконгских долларов",
                "Датская крона", "Доллар США", "Евро", "Индийских рупий", "Казахстанских тенге",
                "Канадский доллар", "Киргизских сомов", "Китайский юань", "Молдавских леев", "Норвежских крон",
                "Польский злотый", "Румынский лей", "СДР (специальные права заимствования)", "Сингапурский доллар", "Таджикских сомони",
                "Турецких лир", "Новый туркменский манат", "Узбекских сумов", "Украинских гривен", "Чешских крон",
                "Шведских крон", " Швейцарский франк", "Южноафриканских рэндов", "Вон Республики Корея", "Японских иен"
        };
        comboBox1 = new JComboBox(items); // выбор валюты
        comboBox1.setBounds(10, 50, 230, 20);
        panel.add(comboBox1);

        comboBox2 = new JComboBox(items); // выбор валюты
        comboBox2.setBounds(250, 50, 230, 20);
        panel.add(comboBox2);

        jTextField1 = new JTextField(25); // dsdjl ntrcnf
        jTextField1.setBounds(160, 100, 150, 25);
        panel.add(jTextField1);

        jTextArea = new JTextArea(); // выводит результат
        jTextArea.setBounds(160, 150, 150, 25);
        panel.add(jTextArea);

        panel.add(new NewPanel()).setBounds(5, 75, 140, 50);
        getContentPane().add(panel);
        setPreferredSize(new Dimension(285, 145));
    }

    /**
     * класс для работы после нажатия на кнопку конвертировать
     */
    public class TestActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Double rateCurrencyOne = Double.parseDouble(wa[comboBox1.getSelectedIndex() - 1][2]);
            Double rateCurrencyTwo = Double.parseDouble(wa[comboBox2.getSelectedIndex() - 1][2]);
            Double nominalCurrencyOne = Double.parseDouble(wa[comboBox1.getSelectedIndex() - 1][1]);
            Double nominalCurrencyTwo = Double.parseDouble(wa[comboBox2.getSelectedIndex() - 1][1]);
            Double amount = Double.parseDouble(jTextField1.getText());
            Double calculation = (((rateCurrencyOne / rateCurrencyTwo) / nominalCurrencyOne) * nominalCurrencyTwo) * amount;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String result = decimalFormat.format(calculation);
            jTextArea.setText(result);
        }
    }
}

/**
 * дополнительный класс для работы с Frame
 */
class NewPanel extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("Arial", Font.BOLD, 14);
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(font);
        g.drawString("Введите сумму:", 10, 40);
        g.drawString("Результат:", 48, 75);
    }
}


