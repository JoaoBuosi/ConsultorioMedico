package com.consultorio.dao;

import com.consultorio.model.Consulta;
import com.consultorio.model.Medico;
import com.consultorio.model.Paciente;
import com.consultorio.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    private PacienteDAO pacienteDAO = new PacienteDAO();
    private MedicoDAO medicoDAO = new MedicoDAO();

    // Salvar uma nova consulta
    public void salvar(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO consulta (paciente_id, medico_id, data_hora, observacao) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consulta.getPaciente().getId());
            stmt.setInt(2, consulta.getMedico().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setString(4, consulta.getObservacao());
            stmt.executeUpdate();
        }
    }

    // Atualizar uma consulta existente
    public void atualizar(Consulta consulta) throws SQLException {
        String sql = "UPDATE consulta SET paciente_id = ?, medico_id = ?, data_hora = ?, observacao = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consulta.getPaciente().getId());
            stmt.setInt(2, consulta.getMedico().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setString(4, consulta.getObservacao());
            stmt.setInt(5, consulta.getId());
            stmt.executeUpdate();
        }
    }

    // Deletar uma consulta pelo ID
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Listar todas as consultas
    public List<Consulta> listar() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));

                // Carregar paciente e médico pelo ID
                int pacienteId = rs.getInt("paciente_id");
                int medicoId = rs.getInt("medico_id");
                Paciente paciente = pacienteDAO.buscarPorId(pacienteId);
                Medico medico = medicoDAO.buscarPorId(medicoId);

                c.setPaciente(paciente);
                c.setMedico(medico);
                c.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                c.setObservacao(rs.getString("observacao"));

                consultas.add(c);
            }
        }

        return consultas;
    }

    // Buscar uma consulta pelo ID
    public Consulta buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM consulta WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Consulta c = new Consulta();
                    c.setId(rs.getInt("id"));

                    int pacienteId = rs.getInt("paciente_id");
                    int medicoId = rs.getInt("medico_id");
                    c.setPaciente(pacienteDAO.buscarPorId(pacienteId));
                    c.setMedico(medicoDAO.buscarPorId(medicoId));
                    c.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    c.setObservacao(rs.getString("observacao"));
                    return c;
                }
            }
        }

        return null;
    }
}
