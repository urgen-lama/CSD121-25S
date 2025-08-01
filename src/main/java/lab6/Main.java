package lab6;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private class MenuItem {
        private String name;
        private double price;

        public MenuItem(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return name + " ($" + String.format("%.2f", price) + ")";
        }
    }

    private class OrderItem {
        private MenuItem item;
        private int quantity;

        public OrderItem(MenuItem item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public MenuItem getItem() {
            return item;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getTotalPrice() {
            return item.getPrice() * quantity;
        }

        @Override
        public String toString() {
            return item.getName() + " x " + quantity + " ($" + String.format("%.2f", getTotalPrice()) + ")";
        }
    }

    private ObservableList<OrderItem> currentOrder = FXCollections.observableArrayList();
    private Label subtotalLabel;
    private Label totalLabel;
    private TextField amountPaidField;
    private Label changeDueLabel;
    private ListView<OrderItem> orderListView;
    private List<MenuItem> menuItemsList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cafe Payment System (Sault Ste. Marie)");

        setupMenuItems();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        root.setLeft(createMenuSection());
        BorderPane.setMargin(root.getLeft(), new Insets(0, 15, 0, 0));

        root.setCenter(createOrderSection());
        BorderPane.setMargin(root.getCenter(), new Insets(0, 15, 0, 0));

        root.setRight(createPaymentSection());

        updateOrderSummary();

        Scene scene = new Scene(root, 1100, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupMenuItems() {
        menuItemsList.add(new MenuItem("Americano", 3.75));
        menuItemsList.add(new MenuItem("Cappuccino", 4.50));
        menuItemsList.add(new MenuItem("Coffee", 3.00));
        menuItemsList.add(new MenuItem("Bread", 2.00));
    }

    private VBox createStyledVBox(String titleText) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(15));
        vbox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 15; -fx-background-color: #f5f5f5; -fx-border-radius: 8; -fx-background-radius: 8;");
        vbox.setAlignment(Pos.TOP_CENTER);

        Label title = new Label(titleText);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
        title.setStyle("-fx-text-fill: #333333;");
        vbox.getChildren().add(title);

        Separator separator = new Separator();
        separator.setPadding(new Insets(5, 0, 10, 0));
        vbox.getChildren().add(separator);
        return vbox;
    }

    private void applyButtonStyle(Button button, String bgColor, String textColor, int borderRadius) {
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s; -fx-border-radius: %d; -fx-background-radius: %d;", bgColor, textColor, borderRadius, borderRadius));
    }

    private VBox createMenuSection() {
        VBox menuVBox = createStyledVBox("Cafe Menu");
        menuVBox.setPrefWidth(250);

        GridPane menuGrid = new GridPane();
        menuGrid.setHgap(15);
        menuGrid.setVgap(12);
        menuGrid.setPadding(new Insets(10, 0, 0, 0));

        int row = 0;
        for (MenuItem item : menuItemsList) {
            Label nameLabel = new Label(item.getName());
            nameLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
            nameLabel.setStyle("-fx-text-fill: #555555;");

            Label priceLabel = new Label("$" + String.format("%.2f", item.getPrice()));
            priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            priceLabel.setStyle("-fx-text-fill: #333333;");

            Button addButton = new Button("Add");
            applyButtonStyle(addButton, "#007bff", "white", 4);
            addButton.setPrefWidth(80);
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    addToOrder(item);
                }
            });

            menuGrid.addRow(row, nameLabel, priceLabel, addButton);
            row++;
        }
        menuVBox.getChildren().add(menuGrid);
        return menuVBox;
    }

    private VBox createOrderSection() {
        VBox orderVBox = createStyledVBox("Current Order");
        orderVBox.setPrefWidth(350);

        orderListView = new ListView<>(currentOrder);
        orderListView.setPrefHeight(350);
        orderVBox.getChildren().add(orderListView);

        HBox orderButtons = new HBox(10);
        orderButtons.setAlignment(Pos.CENTER);

        Button increaseQtyButton = new Button("Add Qty");
        applyButtonStyle(increaseQtyButton, "#007bff", "white", 4);
        increaseQtyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                adjustQuantity(1);
            }
        });

        Button decreaseQtyButton = new Button("Sub Qty");
        applyButtonStyle(decreaseQtyButton, "#ffc107", "black", 4);
        decreaseQtyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                adjustQuantity(-1);
            }
        });

        Button removeItemButton = new Button("Remove Item");
        applyButtonStyle(removeItemButton, "#dc3545", "white", 4);
        removeItemButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                removeItemFromOrder();
            }
        });
        orderButtons.getChildren().addAll(increaseQtyButton, decreaseQtyButton, removeItemButton);
        orderVBox.getChildren().add(orderButtons);

        VBox summaryVBox = new VBox(8);
        summaryVBox.setPadding(new Insets(15, 0, 0, 0));
        summaryVBox.setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: lightgray; -fx-padding: 10 0 0 0;");

        subtotalLabel = new Label("Subtotal: $0.00");
        subtotalLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        totalLabel = new Label("Total: $0.00");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        totalLabel.setStyle("-fx-text-fill: #333333;");

        summaryVBox.getChildren().addAll(subtotalLabel, totalLabel);
        orderVBox.getChildren().add(summaryVBox);

        return orderVBox;
    }

    private VBox createPaymentSection() {
        VBox paymentVBox = createStyledVBox("Payment");
        paymentVBox.setPrefWidth(350);

        GridPane paymentGrid = new GridPane();
        paymentGrid.setHgap(10);
        paymentGrid.setVgap(10);
        paymentGrid.setPadding(new Insets(10, 0, 0, 0));
        paymentGrid.setAlignment(Pos.CENTER);

        Label amountPaidPrompt = new Label("Amount Received:");
        amountPaidPrompt.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        amountPaidField = new TextField();
        amountPaidField.setPromptText("Enter amount");
        amountPaidField.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        amountPaidField.setMaxWidth(180);
        amountPaidField.setAlignment(Pos.CENTER_RIGHT);

        amountPaidField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                    amountPaidField.setText(oldValue);
                }
            }
        });

        paymentGrid.addRow(0, amountPaidPrompt, amountPaidField);
        paymentVBox.getChildren().add(paymentGrid);

        Button payButton = new Button("Process Payment");
        payButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        payButton.setPrefWidth(250);
        payButton.setPadding(new Insets(10, 20, 10, 20));
        applyButtonStyle(payButton, "#28a745", "white", 6);
        payButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processPayment();
            }
        });
        paymentVBox.getChildren().add(payButton);

        changeDueLabel = new Label("Change Due: $0.00");
        changeDueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        changeDueLabel.setStyle("-fx-text-fill: #333333;");
        paymentVBox.getChildren().add(changeDueLabel);

        Button clearOrderButton = new Button("New Transaction");
        clearOrderButton.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        clearOrderButton.setPrefWidth(250);
        clearOrderButton.setPadding(new Insets(8, 15, 8, 15));
        applyButtonStyle(clearOrderButton, "#6c757d", "white", 4);
        clearOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearOrder();
            }
        });
        paymentVBox.getChildren().add(clearOrderButton);

        return paymentVBox;
    }

    private void addToOrder(MenuItem item) {
        boolean itemExists = false;
        for (OrderItem orderItem : currentOrder) {
            if (orderItem.getItem() == item) {
                orderItem.setQuantity(orderItem.getQuantity() + 1);
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            currentOrder.add(new OrderItem(item, 1));
        }
        orderListView.refresh();
        updateOrderSummary();
    }

    private void adjustQuantity(int change) {
        OrderItem selectedItem = orderListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "No Item Selected", "Select an item.");
            return;
        }

        int newQuantity = selectedItem.getQuantity() + change;
        if (newQuantity > 0) {
            selectedItem.setQuantity(newQuantity);
        } else {
            currentOrder.remove(selectedItem);
        }
        orderListView.refresh();
        updateOrderSummary();
    }

    /**
     *Removes the selected item from the order
     * If no item is selected, a warning is displayed
     */
    private void removeItemFromOrder() {
        OrderItem selectedItem = orderListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "No Item Selected", "Select an item to remove.");
            return;
        }
        currentOrder.remove(selectedItem);
        updateOrderSummary();
    }

    private void updateOrderSummary() {
        double total = 0.0;
        for (OrderItem item : currentOrder) {
            total += item.getTotalPrice();
        }

        subtotalLabel.setText(String.format("Subtotal: $%.2f", total));
        totalLabel.setText(String.format("Total: $%.2f", total));

        changeDueLabel.setText("Change Due: $0.00");
        amountPaidField.clear();
    }

    private void processPayment() {
        if (currentOrder.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Order", "Add items to order first.");
            return;
        }

        double total = Double.parseDouble(totalLabel.getText().replace("Total: $", ""));
        double amountPaid;
        try {
            amountPaid = Double.parseDouble(amountPaidField.getText());
            if (amountPaid < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Amount", "Enter a valid positive number.");
            return;
        }

        double change = amountPaid - total;

        StringBuilder receiptBuilder = new StringBuilder();
        receiptBuilder.append("--- Receipt ---\n");
        receiptBuilder.append(String.format("Total: $%.2f\n", total)); //Insert the string "Total: $" and format total as a float type with two decimal places
        receiptBuilder.append(String.format("Paid: $%.2f\n", amountPaid));

        if (change >= 0) {
            receiptBuilder.append(String.format("Change: $%.2f\n", change));
            changeDueLabel.setText(String.format("Change Due: $%.2f", change));
            showAlert(Alert.AlertType.INFORMATION, "Payment Successful!", receiptBuilder.toString());
        } else {
            receiptBuilder.append(String.format("Balance Due: $%.2f\n", Math.abs(change)));
            changeDueLabel.setText(String.format("Remaining: $%.2f", Math.abs(change)));
            showAlert(Alert.AlertType.WARNING, "Payment Needed", receiptBuilder.toString());
        }
    }

    private void clearOrder() {
        currentOrder.clear();
        orderListView.refresh();
        updateOrderSummary();
        showAlert(Alert.AlertType.INFORMATION, "Order Cleared", "Ready for new transaction.");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        if ((type == Alert.AlertType.INFORMATION && title.equals("Payment Successful!")) ||
                (type == Alert.AlertType.WARNING && title.equals("Payment Needed"))) {
            TextArea textArea = new TextArea(message);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setFont(Font.font("Monospaced", 12));
            VBox dialogContent = new VBox(textArea);
            dialogContent.setPadding(new Insets(10));
            alert.getDialogPane().setContent(dialogContent);
            alert.getDialogPane().setPrefWidth(350);
            alert.getDialogPane().setPrefHeight(250);
        }

        alert.showAndWait();    }

    public static void main(String[] args) {
        launch(args);
    }
}