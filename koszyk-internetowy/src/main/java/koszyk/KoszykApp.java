package koszyk;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import promocje.Promocja;
import promocje.Promocja5Procent;
import promocje.PromocjaDarmowyKubek;
import promocje.PromocjaTrzyZaDwa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KoszykApp extends Application {

    private Koszyk koszyk = new Koszyk();

    @Override
    public void start(Stage primaryStage) {
        Scene scene = showProductList(primaryStage);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setTitle("Koszyk z pliku - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Scene showProductList(Stage primaryStage) {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));

        VBox productListBox = new VBox(10);
        productListBox.setPadding(new Insets(10));

        List<Product> produkty = loadProductsFromResources("produkty.txt");

        for (Product p : produkty) {
            Button addButton = new Button("Dodaj do koszyka");
            addButton.getStyleClass().add("add-button");
            HBox itemBox = new HBox(10, new Label(p.toString()), addButton);
            itemBox.getStyleClass().add("product-box");
            itemBox.setPadding(new Insets(5));
            productListBox.getChildren().add(itemBox);

            addButton.setOnAction(e -> {
                Koszyk.dodajProdukt(new Product(p));
            });
        }

        ScrollPane scrollPane = new ScrollPane(productListBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(700);

        Button goToCart = new Button("Przejdź do koszyka");
        goToCart.getStyleClass().add("cart-button");
        goToCart.setOnAction(e -> {
            Scene cartScene = showCartScene(primaryStage);
            cartScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            primaryStage.setScene(cartScene);
        });

        mainLayout.getChildren().addAll(scrollPane, goToCart);

        Scene scene = new Scene(mainLayout, 1000, 900);
        return scene;
    }

    private Scene showCartScene(Stage primaryStage) {
        VBox layout = new VBox(40);
        layout.setPadding(new Insets(40));

        TextArea cartOutput = new TextArea();
        cartOutput.setEditable(false);
        cartOutput.getStyleClass().add("cart-output");
        cartOutput.setPrefHeight(400);
        cartOutput.setWrapText(true);

        cartOutput.clear();
        cartOutput.appendText("Produkty w koszyku:\n");
        for (Product p : koszyk.getProdukty()) {
            cartOutput.appendText(p.toString() + "\n");
        }
        cartOutput.appendText("\nCena: " + Koszyk.calculateTotalPrice(koszyk.getProdukty()) + " zł\n");

        Button applyPromotions = new Button("Zastosuj promocje");
        applyPromotions.getStyleClass().add("apply-button");
        applyPromotions.setOnAction(e -> {
            koszyk.clearPromocje();

            koszyk.dodajPromocje(new Promocja5Procent());
            koszyk.dodajPromocje(new PromocjaTrzyZaDwa());
            koszyk.dodajPromocje(new PromocjaDarmowyKubek());

            koszyk.zastosujNajlepszaKolejnoscPromocjiZFiltracją();

            cartOutput.clear();
            cartOutput.appendText("Produkty po promocji:\n");
            for (Product p : koszyk.getProdukty()) {
                cartOutput.appendText(p.toString() + "\n");
            }
            cartOutput.appendText("\nCena końcowa: " + Koszyk.calculateTotalPrice(koszyk.getProdukty()) + " zł\n");
            cartOutput.appendText("\nPromocje:\n");
            for (var promo : koszyk.getNajlepszaKolejnosc()) {
                cartOutput.appendText("- " + promo.getClass().getSimpleName() + "\n");
            }
        });

        Button backButton = new Button("Wróć do listy produktów");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> {
            Scene productScene = showProductList(primaryStage);
            productScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            primaryStage.setScene(productScene);
        });

        layout.getChildren().addAll(new Label("Twój koszyk:"), cartOutput, applyPromotions, backButton);

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        Scene scene = new Scene(scrollPane, 1000, 900);
        return scene;
    }

    private List<Product> loadProductsFromResources(String filename) {
        List<Product> produkty = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(filename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    produkty.add(new Product("ID" + System.nanoTime(), parts[0], Double.parseDouble(parts[1]), null));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return produkty;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
