package com.marysql.moneytrack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Optional;

public class MainController {

    @FXML private TableView<Gasto> tableGastos;
    @FXML private TableColumn<Gasto, String> colDescricao;
    @FXML private TableColumn<Gasto, Double> colValor;
    @FXML private TableColumn<Gasto, String> colData;
    @FXML private Button btnAdicionar;
    @FXML private Button btnRemover;
    @FXML private Button btnEditar;

    private ObservableList<Gasto> listaGastos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar as colunas da tabela no controlador
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        // Associar a lista de gastos à tabela
        tableGastos.setItems(listaGastos);

        // Ações dos botões
        btnAdicionar.setOnAction(e -> adicionarGasto());
        btnRemover.setOnAction(e -> removerGasto());
        btnEditar.setOnAction(e -> editarGasto());
    }

    // Configura a lista de gastos ao carregar do JSON
    public void setGastos(List<Gasto> gastos) {
        if (gastos != null) {
            listaGastos.setAll(gastos);
        }
    }

    // Adiciona um gasto com valores específicos
    private void adicionarGasto() {
        // Criar um diálogo para coletar os dados do gasto
        Dialog<Gasto> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Novo Gasto");
        dialog.setHeaderText("Digite os detalhes do novo gasto");

        // Definir os botões de ação
        ButtonType buttonTypeOk = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        // Campos para os dados
        TextField descricaoField = new TextField();
        descricaoField.setPromptText("Descrição");

        TextField valorField = new TextField();
        valorField.setPromptText("Valor");

        TextField dataField = new TextField();
        dataField.setPromptText("Data");

        // Adicionar os campos no conteúdo do diálogo
        dialog.getDialogPane().setContent(new GridPane() {{
            add(new Label("Descrição:"), 0, 0);
            add(descricaoField, 1, 0);
            add(new Label("Valor:"), 0, 1);
            add(valorField, 1, 1);
            add(new Label("Data:"), 0, 2);
            add(dataField, 1, 2);
        }});

        // Configurar o valor do gasto a ser retornado
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                String descricao = descricaoField.getText();
                String valorText = valorField.getText();
                String data = dataField.getText();

                // Verificar se o valor é um número válido
                double valor = 0.0;
                try {
                    valor = Double.parseDouble(valorText);
                } catch (NumberFormatException e) {
                    mostrarAlerta("Erro", "Valor inválido. Por favor, insira um número válido.");
                    return null;
                }

                return new Gasto(descricao, valor, data);
            }
            return null;
        });

        Optional<Gasto> resultado = dialog.showAndWait();
        resultado.ifPresent(gasto -> {
            listaGastos.add(gasto);
            ArquivoJSON.salvarGastos(listaGastos);
        });
    }


    // Remove o gasto selecionado
    private void removerGasto() {
        Gasto gastoSelecionado = tableGastos.getSelectionModel().getSelectedItem();
        if (gastoSelecionado != null) {
            listaGastos.remove(gastoSelecionado);
            ArquivoJSON.salvarGastos(listaGastos);
        } else {
            mostrarAlerta("Erro", "Nenhum gasto selecionado para remoção.");
        }
    }

    // Edita o gasto selecionado
    private void editarGasto() {
        Gasto gastoSelecionado = tableGastos.getSelectionModel().getSelectedItem();
        if (gastoSelecionado != null) {
            // Criar um diálogo para editar os dados do gasto
            Dialog<Gasto> dialog = new Dialog<>();
            dialog.setTitle("Editar Gasto");
            dialog.setHeaderText("Edite os detalhes do gasto");

            // Definir os botões de ação
            ButtonType buttonTypeOk = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

            // Campos para os dados
            TextField descricaoField = new TextField(gastoSelecionado.getDescricao());
            TextField valorField = new TextField(String.valueOf(gastoSelecionado.getValor()));
            TextField dataField = new TextField(gastoSelecionado.getData());

            // Adicionar os campos no conteúdo do diálogo
            dialog.getDialogPane().setContent(new GridPane() {{
                add(new Label("Descrição:"), 0, 0);
                add(descricaoField, 1, 0);
                add(new Label("Valor:"), 0, 1);
                add(valorField, 1, 1);
                add(new Label("Data:"), 0, 2);
                add(dataField, 1, 2);
            }});

            // Configurar o valor do gasto a ser retornado
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == buttonTypeOk) {
                    String descricao = descricaoField.getText();
                    double valor = Double.parseDouble(valorField.getText());
                    String data = dataField.getText();
                    gastoSelecionado.setDescricao(descricao);
                    gastoSelecionado.setValor(valor);
                    gastoSelecionado.setData(data);
                    return gastoSelecionado;
                }
                return null;
            });

            Optional<Gasto> resultado = dialog.showAndWait();
            resultado.ifPresent(gasto -> {
                tableGastos.refresh(); // Atualiza a tabela
                ArquivoJSON.salvarGastos(listaGastos);
            });
        } else {
            mostrarAlerta("Erro", "Nenhum gasto selecionado para edição.");
        }
    }

    // Exibir alertas
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
