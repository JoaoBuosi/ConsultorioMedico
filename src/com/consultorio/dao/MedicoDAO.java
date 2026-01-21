package com.consultorio.dao;

import com.consultorio.model.Medico;
import com.consultorio.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    // Salvar médico
    public void salvar(Medico medico) throws SQLException {
        String sql = "INSERT INTO medico (nome, especialidade) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getEspecialidade());
            stmt.executeUpdate();
        }
    }

    // Atualizar médico
    public void atualizar(Medico medico) throws SQLException {
        String sql = "UPDATE medico SET nome = ?, especialidade = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getEspecialidade());
            stmt.setInt(3, medico.getId());
            stmt.executeUpdate();
        }
    }

    // Deletar médico
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM medico WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Listar todos os médicos
    public List<Medico> listar() throws SQLException {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medico";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setEspecialidade(rs.getString("especialidade"));
                medicos.add(m);
            }
        }
        return medicos;
    }

    // Buscar médico por ID
    public Medico buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM medico WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setEspecialidade(rs.getString("especialidade"));
                return m;
            }
        }
        return null;
    }
}
