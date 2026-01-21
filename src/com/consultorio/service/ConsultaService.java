package com.consultorio.service;

import com.consultorio.dao.ConsultaDAO;
import com.consultorio.model.Consulta;

import java.sql.SQLException;
import java.util.List;

public class ConsultaService {

    private ConsultaDAO consultaDAO = new ConsultaDAO();

    public void agendarConsulta(Consulta consulta) throws SQLException {
        // Aqui você pode adicionar validações de conflito de horário
        consultaDAO.salvar(consulta);
    }

    public List<Consulta> listarConsultas() throws SQLException {
        return consultaDAO.listar();
    }
}
