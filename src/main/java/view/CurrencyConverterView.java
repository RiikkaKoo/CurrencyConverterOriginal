package view;

import application.CurrencyConverterController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CurrencyConverterView extends Application {

    private CurrencyConverterController controller;
    private ChoiceBox<String> currencySelector1 = new ChoiceBox<>();
    private ChoiceBox<String> currencySelector2 = new ChoiceBox<>();
    private TextField field2 = new TextField();
    private TextField field1 = new TextField();
    private Label errorMessages = new Label();

    public void init() {
        this.controller = new CurrencyConverterController(this);
    }

    public void start(Stage stage) {

        BorderPane mainLayout = new BorderPane();
        HBox from = new HBox();
        HBox to = new HBox();
        VBox fromSelector = new VBox();
        VBox toSelector = new VBox();
        HBox selectors = new HBox();
        VBox instructions = new VBox();
        VBox buttonBox = new VBox();

        Label text = new Label("From the left selector, select the the currency you want to convert from." +
                "\nInsert the amount to convert into the field next to the selector." +
                "\nFrom the right selector, select the currency you want to convert to." +
                "\nClick the \"Convert\" button." +
                "\nThe converted amount is shown on the field next to the right selector.");
        Label instLabel = new Label("HOW TO CONVERT: ");
        Label fromLabel = new Label("Convert from: ");
        Label toLabel = new Label("Convert to: ");

        errorMessages.setId("error");
        instLabel.setId("instructions");

        instructions.getChildren().add(instLabel);
        instructions.getChildren().add(text);
        text.setTextAlignment(TextAlignment.CENTER);
        instructions.setAlignment(Pos.CENTER);

        field1.setAlignment(Pos.CENTER);
        field2.setAlignment(Pos.CENTER);

        from.getChildren().add(currencySelector1);
        from.getChildren().add(field1);
        fromSelector.getChildren().add(fromLabel);
        fromSelector.getChildren().add(from);
        fromSelector.setAlignment(Pos.CENTER_LEFT);

        to.getChildren().add(field2);
        to.getChildren().add(currencySelector2);
        toSelector.getChildren().add(toLabel);
        toSelector.getChildren().add(to);
        toSelector.setAlignment(Pos.CENTER_RIGHT);

        selectors.getChildren().add(fromSelector);
        selectors.getChildren().add(toSelector);
        selectors.setAlignment(Pos.CENTER);

        Button convertButton = new Button("Convert");
        buttonBox.getChildren().add(convertButton);
        buttonBox.getChildren().add(errorMessages);
        buttonBox.setAlignment(Pos.CENTER);
        convertButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                    displayMessage("");
                    controller.doTheConversion();
            }
        });

        mainLayout.setTop(instructions);
        mainLayout.setCenter(selectors);
        mainLayout.setBottom(buttonBox);

        HBox.setMargin(fromSelector, new Insets(0, 2,0,0));
        HBox.setMargin(toSelector, new Insets(0, 0,0,2));

        BorderPane.setMargin(instructions, new Insets(20, 20, 20,20));
        BorderPane.setMargin(selectors, new Insets(10, 30, 20,30));
        BorderPane.setMargin(buttonBox, new Insets(5, 0, 20,0));

        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add("style.css");
        stage.setTitle("Currency converter");
        stage.setScene(scene);
        stage.show();
    }

    public void displayMessage(String text) {
        errorMessages.setText(text);
    }

    public void setTextInField2(String text) {
        this.field2.setText(text);
    }

    public void updateChoiceBoxes(ArrayList<String> items) {
        currencySelector1.getItems().addAll(items);
        currencySelector2.getItems().addAll(items);
    }

    public String getFromCurrency() {
        return this.currencySelector1.getValue();
    }

    public String getToCurrency() {
        return this.currencySelector2.getValue();
    }

    public String getAmount() {
            return this.field1.getText();
    }
}
