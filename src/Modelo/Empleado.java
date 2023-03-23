/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import java.util.Date;
/**
 *
 * @author ERIKA
 */
public class Empleado {
    private String cedula ,nombre,apellido,salario,discapacidad,horario;
    
    private Date fechacontra;

    public Empleado(String cedula, String nombre, String apellido, String salario, String discapacidad, String horario, Date fechacontra) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.salario = salario;
        this.discapacidad = discapacidad;
        this.horario = horario;
        this.fechacontra = fechacontra;
    }

    public Empleado() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Date getFechacontra() {
        return fechacontra;
    }

    public void setFechacontra(Date fechacontra) {
        this.fechacontra = fechacontra;
    }
    
    
}
