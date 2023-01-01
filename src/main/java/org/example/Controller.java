package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    ComboBox<String> comboBoxFrom = new ComboBox<>();

    @FXML
    ComboBox<String> comboBoxTo = new ComboBox<>();

    @FXML
    Button buttonConvertTo = new Button();

    @FXML
    TextField  textFieldFrom = new TextField();

    @FXML
    TextField  textFieldTo = new TextField();

    private HashMap<String,Double> currencyTable = new HashMap<>();

    private void getCurrency(){
        try {
            Document doc = Jsoup.connect("https://www.x-rates.com/table/?from=USD&amount=1").get();
            Elements newsHeadlines = doc.getElementsByClass("tablesorter ratesTable");
            currencyTable.put("US Dollar", 1.0D);
            for (Element element : newsHeadlines){
                for (Element row : element.select("tr")) {
                    Elements tds = row.select("td");
                    if (tds.size() > 1) {
                        currencyTable.put(tds.get(0).text(), Double.valueOf(tds.get(1).text()));
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void calculateCurrency(){
        String from = comboBoxFrom.getValue();
        String to = comboBoxTo.getValue();
        double d = Double.parseDouble(textFieldFrom.getText());
        double toDollar = d/currencyTable.get(from);
        double toCurrency = toDollar*currencyTable.get(to);

        //textFieldTo.setText(new DecimalFormat("#.####").format(toCurrency));
        textFieldTo.setText(String.format("%.4f", toCurrency));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCurrency();
        comboBoxFrom.getItems().addAll(currencyTable.keySet());
        comboBoxTo.getItems().addAll(currencyTable.keySet());
        comboBoxFrom.getSelectionModel().select("US Dollar");
        comboBoxTo.getSelectionModel().select("US Dollar");
        textFieldTo.setEditable(false);
        textFieldFrom.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("^([0-9]+\\.?[0-9]*|\\.[0-9]+)$")  && newValue.length() > 0 ) {
                if(newValue.equals(".")){
                    textFieldFrom.setText("0.");
                }else{
                    //textFieldFrom.setText(newValue.substring(0, newValue.length()-1));
                    textFieldFrom.setText(oldValue);
                }
            }
        });
        buttonConvertTo.setOnAction(actionEvent -> calculateCurrency());
    }
}
