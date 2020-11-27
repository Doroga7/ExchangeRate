import javax.swing.*;
import java.awt.*;

/**
 * класс для дальнейшей логики по заходу в программу
 * TODO в разработке
 */

class Start {
    public static void enter() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                Frame frame = new Frame();
                frame.setVisible(true); // отображение панели
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // закрытие панели
                frame.setTitle("Конвертер валют"); // название панели
                Toolkit toolkit = Toolkit.getDefaultToolkit(); // набор инструментов для работы с Frame, приложениями
                Dimension dimension = toolkit.getScreenSize(); // получаем размер нашего экрана (размер экрана пользователя)
                frame.setBounds(dimension.width/2 - 250, dimension.height/2 - 150, 500, 300);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
