package com.marysql.moneytrack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carregar os gastos do arquivo JSON ao iniciar
        List<Gasto> gastos = ArquivoJSON.carregarGastos();  // Carrega os dados do arquivo JSON

        // Criar o FXMLLoader para carregar o FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/marysql/moneytrack/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 579, 470); // Definir um tamanho inicial adequado

        // Acessar o controlador para passar os dados carregados
        MainController controller = fxmlLoader.getController();
        controller.setGastos(gastos);  // Passar a lista de gastos carregados para o controlador

        // Carregar e aplicar o CSS
        scene.getStylesheets().add(getClass().getResource("/com/marysql/moneytrack/style.css").toExternalForm());

        // Configurar a janela principal
        primaryStage.setTitle("Controle de Gastos");
        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(579);  // Largura maxima
        primaryStage.setMaxHeight(768); // Altura maxima
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
