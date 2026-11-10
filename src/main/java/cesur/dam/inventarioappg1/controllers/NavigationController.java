package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.AppG1;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador de navegación: es el único responsable de cambiar la vista activa.
 * <p>
 * Guarda una referencia al {@link Stage} principal (se la pasa {@link AppG1#start})
 * y, cuando se le pide ir a una vista, carga su FXML y lo pone como escena.
 * Así los controladores de cada vista no tienen que saber nada de Stage ni de FXMLLoader.
 * </p>
 */
public class NavigationController {

    /** Vistas disponibles en la aplicación y su fichero FXML. */
    public enum View {
        LOGIN("login-view.fxml", "Inventario App G1 - Login"),
        MAIN("main-view.fxml", "Inventario App G1 - Equipos"),
        CREATE("create-view.fxml", "Inventario App G1 - Nuevo equipo");

        private final String fxml;
        private final String title;

        View(String fxml, String title) {
            this.fxml = fxml;
            this.title = title;
        }
    }

    private static Stage stage;

    private NavigationController() {
        // Clase de utilidad: no se instancia
    }

    /**
     * Registra la ventana principal. Debe llamarse una sola vez al arrancar.
     *
     * @param mainStage ventana principal de la aplicación
     */
    public static void setStage(Stage mainStage) {
        stage = mainStage;
    }

    /**
     * Cambia la vista activa por la indicada.
     *
     * @param view vista a la que se quiere navegar
     */
    public static void goTo(View view) {
        try {
            FXMLLoader loader = new FXMLLoader(AppG1.class.getResource("views/" + view.fxml));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle(view.title);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la vista " + view.fxml, e);
        }
    }
}
