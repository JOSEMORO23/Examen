/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ERIKA
 */
public class Modeloempleado extends Empleado {

    public Modeloempleado() {
    }

    public Modeloempleado(String cedula, String nombre, String apellido, String salario, String discapacidad, String horario, Date fechacontra) {
        super(cedula, nombre, apellido, salario, discapacidad, horario, fechacontra);
    }

    public List<Empleado> ListarPersonas() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT cedula, nombre, apellido, fechacon, salario, discapacidad, "
                + "horario FROM empleado";
        ConnectionPG conpq = new ConnectionPG();
        ResultSet rs = conpq.Consulta(sql);
        try {
            while (rs.next()) {
                Empleado per = new Empleado();

                per.setCedula(rs.getString(1));
                per.setNombre(rs.getString(2));
                per.setApellido(rs.getString(3));
                per.setFechacontra(rs.getDate(4));
                per.setSalario(rs.getString(5));
                per.setDiscapacidad(rs.getString(6));
                per.setHorario(rs.getString(7));

                lista.add(per);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(Modeloempleado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public SQLException InsertaPersonaBD() {
        String sql = "INSERT INTO empleado (cedula, nombre, apellido, fechacon, salario, discapacidad, "
                + "horario) VALUES ( '" + getCedula() + "','" + getNombre() + "', "
                + "'" + getApellido() + "', '" + getFechacontra() + "', '" + getSalario() + "', '" + getDiscapacidad() + "', "
                + "'" + getHorario() + "')";

        ConnectionPG con = new ConnectionPG();
        SQLException ex = con.Accion(sql);
        return ex;
    }

    public List<Empleado> BuscaPersonaDB(String search) {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT cedula, nombre, apellido, fechacon, salario, "
                + "discapacidad, horario FROM empleado"
                + "WHERE nombre LIKE '%" + search + "%'";
        ConnectionPG conpq = new ConnectionPG();
        ResultSet rs = conpq.Consulta(sql);
        try {
            while (rs.next()) {
                Empleado per = new Empleado();

                per.setCedula(rs.getString(1));
                per.setNombre(rs.getString(2));
                per.setApellido(rs.getString(3));
                per.setFechacontra(rs.getDate(4));
                per.setSalario(rs.getString(5));
                per.setDiscapacidad(rs.getString(6));
                per.setHorario(rs.getString(7));

                lista.add(per);
            }
            rs.close();
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(Modeloempleado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public SQLException ModficarPersonaDB(String bus) {
       String sql = "UPDATE empleado SET nombre = '" + getNombre() + "', apellido = '" + getApellido() + 
             "', fechacon = '" + getFechacontra() + "', salario = '" + getSalario() + "', discapacidad = '" + getDiscapacidad() + "', horario = '" + getHorario() + "' WHERE cedula = " + bus;

        ConnectionPG con = new ConnectionPG();
        SQLException ex = con.Accion(sql);
        return ex;
    }

    public SQLException EliminarPersonaDB(String cedula) {
        String sql = "DELETE FROM empleado WHERE cedula = " + cedula;

        ConnectionPG con = new ConnectionPG();
        SQLException ex = con.Accion(sql);
        return ex;
    }

  

}
