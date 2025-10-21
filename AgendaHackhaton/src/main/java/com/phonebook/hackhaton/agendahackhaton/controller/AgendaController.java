package com.phonebook.hackhaton.agendahackhaton.controller;

import com.phonebook.hackhaton.agendahackhaton.model.Contact;
import com.phonebook.hackhaton.agendahackhaton.service.AgendaService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AgendaController implements Initializable {


    private AgendaService agenda = new AgendaService(10);

    @FXML
    private TextField nameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField spacesField;
    @FXML
    private TableView<Contact> contactTableView;
    @FXML
    private TableColumn<Contact, String> nameColumn;
    @FXML
    private TableColumn<Contact, String> lastNameColumn;
    @FXML
    private TableColumn<Contact, String> phoneColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        contactTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        populateFields(newSelection);
                    }
                }
        );

        refreshContactList();
    }

    @FXML
    private void handleAnadirContacto() {
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        String phone = phoneField.getText();

        String resultado = agenda.anadirContacto(name, lastName, phone);
        showFeedback(resultado);

        if (resultado.contains("éxito")) {
            refreshContactList();
            handleLimpiarCampos();
        }
    }

    @FXML
    private void handleModificarTelefono() {
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        String phone = phoneField.getText();

        String resultado = agenda.modificarTelefono(name, lastName, phone);
        showFeedback(resultado);

        if (resultado.contains("modificado")) {
            refreshContactList();
            handleLimpiarCampos();
        }
    }

    @FXML
    private void handleEliminarContacto() {
        String name = nameField.getText();
        String lastName = lastNameField.getText();

        if (name.isEmpty() || lastName.isEmpty()) {
            showFeedback("Error: Introduce nombre y apellido para eliminar.");
            return;
        }

        String resultado = agenda.eliminarContacto(name, lastName);
        showFeedback(resultado);

        if (resultado.contains("éxito")) {
            refreshContactList();
            handleLimpiarCampos();
        }
    }

    @FXML
    private void handleBuscarContacto() {
        String name = nameField.getText();
        String lastName = lastNameField.getText();

        if (name.isEmpty() || lastName.isEmpty()) {
            showAlert("Error", "Introduce nombre y apellido para buscar.");
            return;
        }

        Optional<Contact> contacto = agenda.buscarContacto(name, lastName);

        contacto.ifPresentOrElse(
                c -> showAlert("Contacto Encontrado", "El teléfono de " + c.getName() + " es: " + c.getPhone()),
                () -> showAlert("No Encontrado", "El contacto " + name + " " + lastName + " no existe.")
        );
    }

    @FXML
    private void handleExisteContacto() {
        String name = nameField.getText();
        String lastName = lastNameField.getText();

        if (name.isEmpty() || lastName.isEmpty()) {
            showAlert("Error", "Introduce nombre y apellido para verificar.");
            return;
        }

        if (agenda.existeContacto(name, lastName)) {
            showAlert("Verificación", "✅ SÍ, el contacto " + name + " " + lastName + " existe.");
        } else {
            showAlert("Verificación", "❌ NO, el contacto " + name + " " + lastName + " no existe.");
        }
    }

    @FXML
    private void handleAgendaLlena() {
        if (agenda.agendaLlena()) {
            showAlert("Estado Agenda", "La agenda está LLENA.");
        } else {
            showAlert("Estado Agenda", "La agenda AÚN tiene espacio.");
        }
    }

    @FXML
    private void handleEspaciosLibres() {
        int libres = agenda.espaciosLibres();
        showAlert("Espacios Libres", "Quedan " + libres + " espacios disponibles en la agenda.");
    }

    @FXML
    private void handleLimpiarCampos() {
        nameField.clear();
        lastNameField.clear();
        phoneField.clear();
        statusLabel.setText("");
        spacesField.clear(); // También limpia el nuevo campo
        contactTableView.getSelectionModel().clearSelection();
    }


    @FXML
    private void handleAumentarEspacios() {
        String input = spacesField.getText();
        int espacios;

        try {
            espacios = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            showFeedback("Error: Introduce un número válido.");
            return;
        }

        String resultado = agenda.aumentarLimite(espacios);
        showFeedback(resultado);

        if (resultado.startsWith("Éxito")) {
            spacesField.clear(); // Limpia el campo si tuvo éxito
        }
    }


    private void refreshContactList() {
        contactTableView.getItems().setAll(agenda.listarContactos());
    }

    private void showFeedback(String message) {
        statusLabel.setText(message);
        if (message.startsWith("Error:")) {
            statusLabel.getStyleClass().remove("status-success");
            statusLabel.getStyleClass().add("status-error");
        } else {
            statusLabel.getStyleClass().remove("status-error");
            statusLabel.getStyleClass().add("status-success");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void populateFields(Contact contact) {
        nameField.setText(contact.getName());
        lastNameField.setText(contact.getLastName());
        phoneField.setText(contact.getPhone());
    }
}