import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Класс загрузки данных из ЦБР
 */
class LoadWeb {
    private Document doc;
    private Thread secThread;
    private Runnable runnable;

    void init() {
        runnable = new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        };
        secThread = new Thread(runnable);
        secThread.start();
    }

    /**
     * метод конектится с ЦБР.
     * забирает на себя таблицу с данными (инфо по всем валютам).
     * затем вытягивает из таблицы нужные элементы
     */
    private void getWeb() {
        try {
            DataBase dataBase = new DataBase();
            doc = Jsoup.connect("http://www.cbr.ru/scripts/XML_daily.asp").get();
            Elements tables = doc.children(); // забираем таблицы с сайта
            Element table = tables.get(0); // берем 1 таблицу (в конкретном случае она одна)
            Elements elementsTable = table.children(); // берем елементы из нужной таблицы

            //TODO таблица каждой валюты (всего их 34)
            for (int i = 0; i < 34; i++) {
                Element elementTable = elementsTable.get(i);
                Elements elementsAud1 = elementTable.children();

                //TODO забираем из каждой таблицы (каждой валюты) элементы
                Element elementNumCode = elementsAud1.get(0); // элемент внутри каждой валюты
                String numCode = elementNumCode.text();
                Element elementCharCode = elementsAud1.get(1);
                String charCode = elementCharCode.text();
                Element elementNominal = elementsAud1.get(2);
                String nominal = elementNominal.text();
                Element elementName = elementsAud1.get(3);
                String name = elementName.text();
                Element elementValue = elementsAud1.get(4);
                String value = elementValue.text();

                // TODO вызываем метод загрузки данных в БД. Передаем необходимые элементы
                dataBase.uploadingDataToDatabase(numCode, charCode, nominal, name, value);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}