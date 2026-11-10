package cesur.dam.inventarioappg1;

import cesur.dam.inventarioappg1.controllers.NavigationController;
import cesur.dam.inventarioappg1.data.DataBaseInitializer;
import cesur.dam.inventarioappg1.data.DataSourceFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppG1 extends Application {
    @Override
    public void start(Stage stage) {
        // Crea la tabla y carga los datos de ejemplo (igual que Main2 del proyecto JDBC)
        try {
            DataBaseInitializer.initialize(DataSourceFactory.getDataSource()); // crea la tabla (init.sql)
            DataBaseInitializer.loadData(DataSourceFactory.getDataSource());   // mete los datos si está vacía (data.sql)
        } catch (Exception e) {
            System.out.println("Aviso al inicializar la base de datos: " + e.getMessage());
        }

        // Le damos la ventana al controlador de navegación y arrancamos en el login
        NavigationController.setStage(stage);
        NavigationController.goTo(NavigationController.View.LOGIN);
    }
}

