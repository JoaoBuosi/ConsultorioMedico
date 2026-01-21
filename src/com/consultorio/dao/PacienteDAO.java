package com.consultorio.dao;

import com.consultorio.model.Paciente;
import com.consultorio.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // Salvar paciente
    public void salvar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO paciente (nome, cpf, idade) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setInt(3, paciente.getIdade());
            stmt.executeUpdate();
        }
    }

    // Atualizar paciente
    public void atualizar(Paciente paciente) throws SQLException {
        String sql = "UPDATE paciente SET nome = ?, cpf = ?, idade = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setInt(3, paciente.getIdade());
            stmt.setInt(4, paciente.getId());
            stmt.executeUpdate();
        }
    }

    // Deletar paciente
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM paciente WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Listar todos os pacientes
    public List<Paciente> listar() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM paciente";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setIdade(rs.getInt("idade"));
                pacientes.add(p);
            }
        }
        return pacientes;
    }

    // Buscar paciente por ID (este método é essencial para ConsultaDAO)
    public Paciente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM paciente WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Paciente p = new Paciente();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setCpf(rs.getString("cpf"));
                    p.setIdade(rs.getInt("idade"));
                    return p;
                }
            }
        }
        return null;
    }
}
