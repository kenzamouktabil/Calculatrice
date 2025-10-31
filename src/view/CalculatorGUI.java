package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;
import controller.CalculatorController;

// Vue de la calculatrice (interface graphique JavaFX)
// Elle affiche la calculatrice et gère les clics de l'utilisateur.
// Règle : la vue ne contient aucune logique de calcul, elle affiche seulement les données
// et transmet les actions utilisateur au contrôleur.
public class CalculatorGUI extends Application implements CalculatorGUIInterface {
    
    // Référence vers le contrôleur
    private CalculatorController controller;
    
    // Composants graphiques
    private Label accuDisplay;      // Affichage de l'accumulateur
    private VBox stackDisplay;      // Zone de la pile
    private Label[] stackLabels;    // 4 labels pour les 4 éléments de la pile
    private static final int STACK_SIZE = 4;

    // Constructeur vide requis par JavaFX
    public CalculatorGUI() {
        // Le contrôleur sera créé dans start()
    }

    // Point d'entrée JavaFX : création de l'interface graphique
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculatrice RPN");

        // 1. Création de la zone d'affichage
        VBox displayArea = createDisplayArea();

        // 2. Création du clavier
        GridPane keyboard = createKeyboard();

        // 3. Création du contrôleur (après l'interface)
        controller = new CalculatorController();
        controller.setView(this);

        // 4. Assemblage de l'interface
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(displayArea, keyboard);

        Scene scene = new Scene(root, 400, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Crée la zone d'affichage (pile + accumulateur)
    private VBox createDisplayArea() {
        VBox display = new VBox(5);
        display.setPadding(new Insets(10));
        display.setStyle("-fx-background-color: #E0E0E0; -fx-border-radius: 5;");

        // Zone de la pile (4 lignes du haut)
        stackDisplay = new VBox(2);
        stackLabels = new Label[STACK_SIZE];

        for (int i = 0; i < STACK_SIZE; i++) {
            stackLabels[i] = new Label("0.0");
            stackLabels[i].setMaxWidth(Double.MAX_VALUE);
            stackLabels[i].setAlignment(Pos.CENTER_RIGHT);
            stackLabels[i].setStyle(
                "-fx-background-color: white; " +
                "-fx-border-color: #CCCCCC; " +
                "-fx-border-width: 1; " +
                "-fx-padding: 8; " +
                "-fx-font-size: 16px; " +
                "-fx-font-family: 'Courier New';"
            );
            stackDisplay.getChildren().add(stackLabels[i]);
        }

        // Ligne du bas : accumulateur
        accuDisplay = new Label("0.0");
        accuDisplay.setMaxWidth(Double.MAX_VALUE);
        accuDisplay.setAlignment(Pos.CENTER_RIGHT);
        accuDisplay.setStyle(
            "-fx-background-color: white; " +
            "-fx-border-color: #2196F3; " +
            "-fx-border-width: 2; " +
            "-fx-padding: 15; " +
            "-fx-font-size: 28px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: 'Courier New';"
        );

        display.getChildren().addAll(stackDisplay, accuDisplay);
        return display;
    }

    // Crée le clavier de la calculatrice
    private GridPane createKeyboard() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));

        // Colonnes du clavier (4 colonnes de taille égale)
        for (int i = 0; i < 4; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25);
            grid.getColumnConstraints().add(col);
        }

        // Boutons numériques (0-9)
        String[][] numberLayout = {
            {"7", "8", "9"},
            {"4", "5", "6"},
            {"1", "2", "3"},
            {"0", ".", "+/-"}
        };

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 3; col++) {
                String text = numberLayout[row][col];
                Button btn = createButton(text, "number");

                // Gestion des clics
                if (text.equals(".")) {
                    btn.setOnAction(e -> controller.onDotPressed());
                } else if (text.equals("+/-")) {
                    btn.setOnAction(e -> controller.onPlusMinusPressed());
                } else {
                    btn.setOnAction(e -> controller.onDigitPressed(text));
                }

                grid.add(btn, col, row);
            }
        }

        // Opérations (+, -, *, /)
        String[] operations = {"+", "-", "*", "/"};
        for (int i = 0; i < operations.length; i++) {
            String op = operations[i];
            Button btn = createButton(op, "operation");
            btn.setOnAction(e -> controller.onOperationPressed(op));
            grid.add(btn, 3, i);
        }

        // Bouton SWAP (échange les deux derniers éléments)
        Button swapBtn = createButton("⇄", "special");
        swapBtn.setOnAction(e -> controller.onSwapPressed());
        grid.add(swapBtn, 3, 4);

        // Bouton PUSH (empile la valeur saisie)
        Button pushBtn = createButton("PUSH", "special");
        pushBtn.setStyle(pushBtn.getStyle() + 
            "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        pushBtn.setOnAction(e -> controller.onPushPressed());
        grid.add(pushBtn, 0, 4, 3, 1);

        // Bouton CLEAR (réinitialise)
        Button clearBtn = createButton("C", "special");
        clearBtn.setStyle(clearBtn.getStyle() + 
            "-fx-background-color: #FF5252; -fx-text-fill: white;");
        clearBtn.setOnAction(e -> controller.onClearPressed());
        grid.add(clearBtn, 3, 5);

        // Bouton DROP (supprime le sommet de la pile)
        Button dropBtn = createButton("DROP", "special");
        dropBtn.setOnAction(e -> controller.getModel().drop());
        grid.add(dropBtn, 0, 5, 3, 1);

        return grid;
    }

    // Crée un bouton stylisé
    private Button createButton(String text, String type) {
        Button btn = new Button(text);
        btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btn.setPrefHeight(60);

        String baseStyle = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-cursor: hand;";

        switch (type) {
            case "number":
                btn.setStyle(baseStyle + "-fx-background-color: #F5F5F5;");
                break;
            case "operation":
                btn.setStyle(baseStyle + "-fx-background-color: #FFB74D; -fx-text-fill: white;");
                break;
            case "special":
                btn.setStyle(baseStyle + "-fx-background-color: #90CAF9;");
                break;
        }

        // Effet visuel au survol
        btn.setOnMouseEntered(e -> btn.setOpacity(0.8));
        btn.setOnMouseExited(e -> btn.setOpacity(1.0));

        return btn;
    }

    // Méthode appelée par le contrôleur pour mettre à jour l'affichage de l'accumulateur
    // Conforme au cahier des charges : première méthode change
    @Override
    public void change(String accu) {
        accuDisplay.setText(accu);
    }

    // Méthode appelée par le contrôleur pour mettre à jour l'affichage de la pile
    // Conforme au cahier des charges : deuxième méthode change (surcharge)
    @Override
    public void change(List<Double> stackData) {
        // Affiche les 4 derniers éléments de la pile
        int stackSize = stackData.size();
        for (int i = 0; i < STACK_SIZE; i++) {
            int index = stackSize - STACK_SIZE + i;
            if (index >= 0) {
                stackLabels[i].setText(String.format("%.2f", stackData.get(index)));
            } else {
                stackLabels[i].setText("0.0");
            }
        }
    }

    // Lance l'application
    @Override
    public void affiche() {
        launch();
    }

    // Point d'entrée pour exécuter la vue seule
    public static void main(String[] args) {
        launch(args);
    }
}