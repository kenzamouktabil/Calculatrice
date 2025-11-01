package view;

import java.util.List;

import controller.CalculatorController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class CalculatorFXMLController implements CalculatorGUIInterface {

    @FXML private Label accuLabel;
    @FXML private Label stack0, stack1, stack2, stack3;

    private final CalculatorController controller = new CalculatorController();

    @FXML
    private void initialize() {
        controller.setView(this);
    }

    @FXML
    private void onDigit(javafx.event.ActionEvent e) {
        controller.onDigitPressed(((Button) e.getSource()).getText());
    }

    @FXML private void onDot()       { controller.onDotPressed(); }
    @FXML private void onPlusMinus() { controller.onPlusMinusPressed(); }
    @FXML private void onPush()      { controller.onPushPressed(); }
    @FXML private void onDrop()      { controller.getModel().drop(); }
    @FXML private void onSwap()      { controller.onSwapPressed(); }
    @FXML private void onClear()     { controller.onClearPressed(); }

    @FXML
    private void onOp(javafx.event.ActionEvent e) {
        controller.onOperationPressed(((Button) e.getSource()).getText());
    }

    @Override
    public void change(String accu) {
        Platform.runLater(() -> accuLabel.setText(accu));
    }

    @Override
    public void change(List<Double> stackData) {
        Platform.runLater(() -> {
            Label[] lines = { stack0, stack1, stack2, stack3 };
            int n = stackData.size();
            for (int i = 0; i < 4; i++) {
                int idx = n - 4 + i;
                lines[i].setText(idx >= 0 ? String.format("%.2f", stackData.get(idx)) : "0.00");
            }
        });
    }

    @Override
    public void showError(String message) {
        Platform.runLater(() ->
            new Alert(Alert.AlertType.ERROR, message, ButtonType.OK).showAndWait()
        );
    }
    
    @Override
    public void affiche() {  /* non utilisé en FXML */ }
}
