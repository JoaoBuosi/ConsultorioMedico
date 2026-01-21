package com.consultorio;

import com.consultorio.dao.ConsultaDAO;
import com.consultorio.dao.MedicoDAO;
import com.consultorio.dao.PacienteDAO;
import com.consultorio.model.Consulta;
import com.consultorio.model.Medico;
import com.consultorio.model.Paciente;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Main extends Application {

    private PacienteDAO pacienteDAO = new PacienteDAO();
    private MedicoDAO medicoDAO = new MedicoDAO();
    private ConsultaDAO consultaDAO = new ConsultaDAO();

    private ListView<String> pacientesList = new ListView<>();
    private ListView<String> medicosList = new ListView<>();
    private ListView<String> consultasList = new ListView<>();

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();

        Tab tabPacientes = new Tab("Pacientes", criarPacientePane());
        Tab tabMedicos = new Tab("Médicos", criarMedicoPane());
        Tab tabConsultas = new Tab("Consultas", criarConsultaPane());

        tabPane.getTabs().addAll(tabPacientes, tabMedicos, tabConsultas);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Scene scene = new Scene(tabPane, 800, 500);
        stage.setScene(scene);
        stage.setTitle("Sistema de Consultório");
        stage.show();

        atualizarPacientes();
        atualizarMedicos();
        atualizarConsultas();
    }

    private VBox criarPacientePane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");
        TextField cpfField = new TextField();
        cpfField.setPromptText("CPF");
        TextField idadeField = new TextField();
        idadeField.setPromptText("Idade");

        Button salvarBtn = new Button("Salvar Paciente");
        salvarBtn.setOnAction(e -> {
            try {
                Paciente p = new Paciente();
                p.setNome(nomeField.getText());
                p.setCpf(cpfField.getText());
                p.setIdade(Integer.parseInt(idadeField.getText()));
                pacienteDAO.salvar(p);
                atualizarPacientes();
                nomeField.clear();
                cpfField.clear();
                idadeField.clear();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (NumberFormatException ex) {
                showAlert("Erro", "Idade inválida!");
            }
        });

        vbox.getChildren().addAll(nomeField, cpfField, idadeField, salvarBtn, pacientesList);
        return vbox;
    }

    private VBox criarMedicoPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");
        TextField especialidadeField = new TextField();
        especialidadeField.setPromptText("Especialidade");

        Button salvarBtn = new Button("Salvar Médico");
        salvarBtn.setOnAction(e -> {
            try {
                Medico m = new Medico();
                m.setNome(nomeField.getText());
                m.setEspecialidade(especialidadeField.getText());
                medicoDAO.salvar(m);
                atualizarMedicos();
                nomeField.clear();
                especialidadeField.clear();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        vbox.getChildren().addAll(nomeField, especialidadeField, salvarBtn, medicosList);
        return vbox;
    }

    private VBox criarConsultaPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        ComboBox<Paciente> pacienteCombo = new ComboBox<>();
        ComboBox<Medico> medicoCombo = new ComboBox<>();
        DatePicker dataPicker = new DatePicker(LocalDate.now());
        Spinner<Integer> horaSpinner = new Spinner<>(0, 23, 9);
        Spinner<Integer> minutoSpinner = new Spinner<>(0, 59, 0);
        TextField observacaoField = new TextField();
        observacaoField.setPromptText("Observação");

        Button agendarBtn = new Button("Agendar Consulta");
        agendarBtn.setOnAction(e -> {
            Paciente p = pacienteCombo.getSelectionModel().getSelectedItem();
            Medico m = medicoCombo.getSelectionModel().getSelectedItem();
            LocalDate data = dataPicker.getValue();
            LocalTime hora = LocalTime.of(horaSpinner.getValue(), minutoSpinner.getValue());

            if (p == null || m == null || data == null) {
                showAlert("Erro", "Selecione Paciente, Médico e Data!");
                return;
            }

            try {
                Consulta c = new Consulta();
                c.setPaciente(p);
                c.setMedico(m);
                c.setDataHora(LocalDateTime.of(data, hora));
                c.setObservacao(observacaoField.getText());
                consultaDAO.salvar(c);
                atualizarConsultas();
                observacaoField.clear();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        HBox timeBox = new HBox(5, new Label("Hora:"), horaSpinner, new Label("Minuto:"), minutoSpinner);

        vbox.getChildren().addAll(new Label("Paciente:"), pacienteCombo,
                new Label("Médico:"), medicoCombo,
                new Label("Data:"), dataPicker, timeBox,
                observacaoField, agendarBtn, consultasList);

        // Atualiza ComboBoxes quando a aba é mostrada
        vbox.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                try {
                    pacienteCombo.getItems().clear();
                    pacienteCombo.getItems().addAll(pacienteDAO.listar());
                    medicoCombo.getItems().clear();
                    medicoCombo.getItems().addAll(medicoDAO.listar());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        return vbox;
    }

    private void atualizarPacientes() {
        try {
            List<Paciente> pacientes = pacienteDAO.listar();
            pacientesList.getItems().clear();
            for (Paciente p : pacientes) {
                pacientesList.getItems().add(p.getId() + " - " + p.getNome() + " - " + p.getCpf() + " - " + p.getIdade());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void atualizarMedicos() {
        try {
            List<Medico> medicos = medicoDAO.listar();
            medicosList.getItems().clear();
            for (Medico m : medicos) {
                medicosList.getItems().add(m.getId() + " - " + m.getNome() + " - " + m.getEspecialidade());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void atualizarConsultas() {
        try {
            List<Consulta> consultas = consultaDAO.listar();
            consultasList.getItems().clear();
            for (Consulta c : consultas) {
                consultasList.getItems().add(c.getId() + " - Paciente: " + c.getPaciente().getNome()
                        + " - Médico: " + c.getMedico().getNome()
                        + " - Data: " + c.getDataHora()
                        + " - Obs: " + c.getObservacao());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
