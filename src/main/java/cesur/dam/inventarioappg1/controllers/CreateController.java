package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import cesur.dam.inventarioappg1.servicios.EquipoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Vista de creación: formulario que guarda un equipo nuevo en la base de datos.
 * El id no se pide porque lo genera la base de datos al insertar.
 */
public class CreateController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private ChoiceBox<String> choiceCategory;
    @FXML
    private CheckBox chkAvailable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceCategory.getItems().addAll("Keyboard", "Laptop", "Gamepad", "Monitor", "Computer", "Printer");
        choiceCategory.getSelectionModel().selectFirst();
    }

    /**
     * Guarda el equipo en la base de datos si el formulario es correcto
     * y vuelve a la vista principal.
     */
    @FXML
    public void save(ActionEvent actionEvent) {
        // 1. Validar
        if (txtName.getText() == null || txtName.getText().isBlank()) {
            showAlert(Alert.AlertType.WARNING, "Datos incompletos", "El nombre es obligatorio.");
            return;
        }
        if (choiceCategory.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Datos incompletos", "Selecciona una categoría.");
            return;
        }

        // 2. Crear el objeto con los datos del formulario
        Equipo equipment = new Equipo();
        equipment.setNombre(txtName.getText().trim());
        equipment.setCategoria(choiceCategory.getValue());
        equipment.setDisponible(chkAvailable.isSelected());

        // 3. Guardar en la base de datos
        try {
            EquipoService service = new EquipoService(new EquipoJdbcDAO(DataSourceFactory.getDataSource()));
            equipment = service.save(equipment);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "No se pudo guardar", e.getMessage());
            return;
        }

        // 4. Avisar y volver a la tabla
        showAlert(Alert.AlertType.INFORMATION, "Guardado",
                "Id " + equipment.getId() + " | " + equipment.getNombre() + " | "
                        + equipment.getCategoria() + " | "
                        + (equipment.getDisponible() ? "disponible" : "no disponible"));

        NavigationController.goTo(NavigationController.View.MAIN);
    }

    /** Botón "Volver": regresa a la vista principal sin guardar. */
    @FXML
    public void goBack(ActionEvent actionEvent) {
        NavigationController.goTo(NavigationController.View.MAIN);
    }

    private void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle("Inventario App G1");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
