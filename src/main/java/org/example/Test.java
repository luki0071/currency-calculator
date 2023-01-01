package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.logging.Logger;

public class Test {

    private static final Logger log = Logger.getGlobal();

    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://www.x-rates.com/table/?from=USD&amount=1").get();
            log.info(doc.title());
            //log.info("cos");
            Elements newsHeadlines = doc.getElementsByClass("tablesorter ratesTable");
            log.info(newsHeadlines.size()+"");
            for (Element element : newsHeadlines){
                for (Element row : element.select("tr")) {
                    //log.info(row.text());
                    Elements tds = row.select("td");
                    if (tds.size() > 1) {
                        System.out.println(tds.get(0).text());
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
