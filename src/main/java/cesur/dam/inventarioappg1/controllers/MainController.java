package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import cesur.dam.inventarioappg1.servicios.EquipoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Vista principal: muestra en la tabla los equipos guardados en la base de datos.
 */
public class MainController implements Initializable {

    @FXML
    private TableView<Equipo> table;
    @FXML
    private TableColumn<Equipo, String> cId;
    @FXML
    private TableColumn<Equipo, String> cName;
    @FXML
    private TableColumn<Equipo, String> cCategory;
    @FXML
    private TableColumn<Equipo, String> cAvailability;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureColumns();
        loadEquipment();
    }

    /** Define qué muestra cada columna de la tabla. */
    private void configureColumns() {
        cId.setCellValueFactory(row ->
                new SimpleStringProperty(String.valueOf(row.getValue().getId())));

        cName.setCellValueFactory(row -> {
            String name = row.getValue().getNombre();
            return new SimpleStringProperty(name == null || name.isBlank() ? "--" : name);
        });

        cCategory.setCellValueFactory(row ->
                new SimpleStringProperty(String.valueOf(row.getValue().getCategoria()).toUpperCase()));

        cAvailability.setCellValueFactory(row -> {
            Boolean available = row.getValue().getDisponible();
            return new SimpleStringProperty(Boolean.TRUE.equals(available) ? "disponible" : "no disponible");
        });
    }

    /** Lee todos los equipos de la base de datos y los pone en la tabla. */
    private void loadEquipment() {
        try {
            EquipoService service = new EquipoService(new EquipoJdbcDAO(DataSourceFactory.getDataSource()));
            table.getItems().setAll(service.findAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudieron cargar los equipos");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /** Botón "Nuevo equipo": abre la vista de creación. */
    @FXML
    public void goToCreate(ActionEvent actionEvent) {
        NavigationController.goTo(NavigationController.View.CREATE);
    }

    /** Botón "Recargar": vuelve a leer la base de datos. */
   /* @FXML
    public void refresh(ActionEvent actionEvent) {
        loadEquipment();
    }
    */

    /** Botón "Cerrar sesión": vuelve al login. */
    @FXML
    public void logout(ActionEvent actionEvent) {
        NavigationController.goTo(NavigationController.View.LOGIN);
    }
}
