/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConnectionPG;
import Modelo.Empleado;
import Modelo.Modeloempleado;
import Vista.VistaEmpleado;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ERIKA
 */
public class Controladorempleado {

    private final Modeloempleado me;
    private final VistaEmpleado ve;

    public Controladorempleado(Modeloempleado me, VistaEmpleado ve) {
        this.me = me;
        this.ve = ve;
        ve.setVisible(true);
    }
    
       public void IniciarControl() {
        Cargaempleado();

       

//        ValidarCampos();
        ve.getBtncrear().addActionListener(l -> AbreDialogoPersona(1));
        ve.getBtncrear().addActionListener(l -> InitClean());
        ve.getBtneditar().addActionListener(l -> AbreDialogoPersona(2));
        ve.getBtneliminar().addActionListener(l -> AbreDialogoPersona(3));

        ve.getBtnaceptar().addActionListener(l -> crearEditarEliminarEmpleado());
        ve.getBtncancelar().addActionListener(l -> ve.getDlgcrudempleados().dispose());
        ve.getBtnimprimir().addActionListener(l -> Reporte());
       
        ve.getTxtbuscarempleado().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BuscarEmpleadosBD();
            }
        });
    }

    private void Cargaempleado() {
        List<Empleado> listaper = me.ListarPersonas();

        DefaultTableModel df;
        df = (DefaultTableModel) ve.getTblemplados().getModel();
        df.setNumRows(0);

        listaper.stream().forEach(pe -> {

            String[] FilaNueva = {
                pe.getCedula(),
                pe.getNombre(),
                pe.getApellido(),
                String.valueOf(pe.getFechacontra()),
                pe.getSalario(),
                pe.getDiscapacidad(),
                pe.getHorario(),};
            df.addRow(FilaNueva);

        });
    }

    private void BuscarEmpleadosBD() {
        String search = ve.getTxtbuscarempleado().getText().trim();
        List<Empleado> listaper = me.BuscaPersonaDB(search);

        DefaultTableModel df;
        df = (DefaultTableModel) ve.getTblemplados().getModel();
        df.setNumRows(0);
        listaper.stream().forEach(pe -> {

            String[] FilaNueva = {
                pe.getCedula(),
                pe.getNombre(),
                pe.getApellido(),
                String.valueOf(pe.getFechacontra()),
                pe.getSalario(),
                pe.getDiscapacidad(),
                pe.getHorario(),};
            df.addRow(FilaNueva);

        });
    }

    private void AbreDialogoPersona(int ce) {
        String title = null;
        boolean RowSelected = true;
        boolean report = false;
        switch (ce) {
            case 1:
                title = "Añadir un nuevo empleado";
                EnableFields(InitClean());

                ve.getDlgcrudempleados().setName("crear");
                break;

            case 2:
                title = "Editar empleado";
                ve.getDlgcrudempleados().setName("editar");
                EnableFields(InitClean());
                ve.getTxtcedula().setEnabled(false);

                RowSelected = MousePressed(ve.getTblemplados());
                break;

            case 3:
                title = "Eliminar empleado";
                ve.getDlgcrudempleados().setName("eliminar");
                DisableFields(InitClean());
                RowSelected = MousePressed(ve.getTblemplados());
                break;
//            case 4:
//                title = "Generar reporte";
//                vp.getDlgreporteemp().setName("reporte");
//                report = true;
//                break;
        }

        if (RowSelected && !report) {
            ve.getDlgcrudempleados().setLocationRelativeTo(ve);
            ve.getDlgcrudempleados().setSize(500, 575);
            ve.getDlgcrudempleados().setTitle(title);
            ve.getDlgcrudempleados().setVisible(true);
        }
    }

    private void DisableFields(Component[] components) {
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setEnabled(false);
                ((JTextField) component).setDisabledTextColor(component.getForeground());
            } else if (component instanceof JDateChooser) {
                ((JDateChooser) component).setEnabled(false);
            } else if (component instanceof JButton) {
                ((JButton) component).setEnabled(false);
            }

        }
    }

    private Component[] InitClean() {
        Component[] com = {
            ve.getTxtcedula(),
            ve.getTxtnombre(),
            ve.getTxtapellido(),
            ve.getFechacontra(),
            ve.getTxtsalario(),
            ve.getTxtdiscapacidad(),
            ve.getTxthorario(),};

        CleanFields(com);
        return com;
    }

    private void CleanFields(Component[] components) {
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText(null);
            } else if (component instanceof JDateChooser) {
                ((JDateChooser) component).setCalendar(null);
            }

        }
    }

    private void EnableFields(Component[] components) {
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setEnabled(true);
            } else if (component instanceof JDateChooser) {
                ((JDateChooser) component).setEnabled(false);
                ((JDateChooser) component).getCalendarButton().setEnabled(true);
            } else if (component instanceof JButton) {
                ((JButton) component).setEnabled(true);
            }

        }
    }

    private boolean MousePressed(JTable table) {
        boolean press = false;
        try {
            if (table.getSelectedRowCount() == 1) {
                press = true;

                ve.getTxtcedula().setText(ve.getTblemplados().getValueAt(ve.getTblemplados().getSelectedRow(), 0).toString());

                ve.getTxtnombre().setText(ve.getTblemplados().getValueAt(ve.getTblemplados().getSelectedRow(), 1).toString());
                ve.getTxtapellido().setText(ve.getTblemplados().getValueAt(ve.getTblemplados().getSelectedRow(), 2).toString());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                ve.getFechacontra().setDate((formatter.parse(ve.getTblemplados().getValueAt(ve.getTblemplados().getSelectedRow(), 3).toString())));

                ve.getTxtsalario().setText(ve.getTblemplados().getValueAt(ve.getTblemplados().getSelectedRow(), 4).toString());
                ve.getTxtdiscapacidad().setText(ve.getTblemplados().getValueAt(ve.getTblemplados().getSelectedRow(), 5).toString());
                ve.getTxthorario().setText(ve.getTblemplados().getValueAt(ve.getTblemplados().getSelectedRow(), 6).toString());

                Modeloempleado mp = new Modeloempleado();

            } else {
                JOptionPane.showMessageDialog(ve, "Seleccione una fila primero");
            }
        } catch (NullPointerException | ParseException e) {
            System.err.print(e);
        }
        return press;
    }

    private void crearEditarEliminarEmpleado() {
        if (ve.getDlgcrudempleados().getName().equals("crear")) {
            try {

                String cedula = ve.getTxtcedula().getText().trim();
                String nombre = ve.getTxtnombre().getText().trim();
                String apellido = ve.getTxtapellido().getText().trim();
                java.sql.Date fechacon = new java.sql.Date(ve.getFechacontra().getDate().getTime());
                String saalrio = ve.getTxtsalario().getText().trim();

                String discapacidad = ve.getTxtdiscapacidad().getText().trim();
                String horario = ve.getTxthorario().getText().trim();

//                if (!ValidData(numeroidentificacion)) {
//                    JOptionPane.showMessageDialog(vp, "El campo de número de documento no puede quedar vacío");
//                    return;
//                }
//
//                if (!ValidData(nombre)) {
//                    JOptionPane.showMessageDialog(vp, "El campo del nombre no puede quedar vacío");
//                    return;
//                }
//
//                if (!ValidData(apellido)) {
//                    JOptionPane.showMessageDialog(vp, "El campo del apellido no puede quedar vacío");
//                    return;
//                }
//                if (!ValidData(telefono)) {
//                    JOptionPane.showMessageDialog(vp, "El campo de teléfono no puede quedar vacío");
//                    return;
//                }
//                if (!ValidData(sexo)) {
//                    JOptionPane.showMessageDialog(vp, "Elija el sexo");
//                    return;
//                }
//                if (!validateDate(fechanac)) {
//                    JOptionPane.showMessageDialog(vp, "Ingrese una fecha de nacimiento válida. \n"
//                            + "Recuerde que la persona debe ser mayor de edad.");
//                    return;
//                }
//                if (!ValidData(direccion)) {
//                    JOptionPane.showMessageDialog(vp, "El campo de la dirección no puede quedar vacío");
//                    return;
//                }
//                if (!ValidData(email)) {
//                    JOptionPane.showMessageDialog(vp, "El campo del email no es válido");
//                    return;
//                }
////                if (!validateCorreo()) {
////                    JOptionPane.showMessageDialog(vp, "Proporcione correo válido\n");
////                    return;
////                }
//                if (!Cedula.validarCedula(numeroidentificacion)) {
//                    JOptionPane.showMessageDialog(vp, "Proporcione un número de identificación válido");
//                    return;
//                }
                Modeloempleado persona = new Modeloempleado();

                persona.setCedula(cedula);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                persona.setFechacontra(fechacon);
                persona.setSalario(saalrio);
                persona.setDiscapacidad(discapacidad);

                persona.setHorario(horario);

                if (persona.InsertaPersonaBD() == null) {
                    JOptionPane.showMessageDialog(ve, " agragado correctamnete");
                    ve.getDlgcrudempleados().dispose();

                } else {
                    JOptionPane.showMessageDialog(ve, "No se pudo añadir el registro");
                }

            } catch (NullPointerException | NumberFormatException e) {
                System.out.println(e);
            }
        } else {

            if (ve.getDlgcrudempleados().getName().equals("editar")) {
                try {

                    String cedula = ve.getTxtcedula().getText();
                    String nombre = ve.getTxtnombre().getText().trim();
                    String apellido = ve.getTxtapellido().getText().trim();
                    java.sql.Date fechacon = new java.sql.Date(ve.getFechacontra().getDate().getTime());
                    String salario = ve.getTxtsalario().getText().trim();

                    String discapacidad = ve.getTxtdiscapacidad().getText().trim();
                    String horario = ve.getTxthorario().getText().trim();

//                    if (!ValidData(numeroidentificacion)) {
//                        JOptionPane.showMessageDialog(vp, "El campo de número de documento no puede quedar vacío");
//                        return;
//                    }
//
//                    if (!ValidData(nombre)) {
//                        JOptionPane.showMessageDialog(vp, "El campo del nombre no puede quedar vacío");
//                        return;
//                    }
//
//                    if (!ValidData(apellido)) {
//                        JOptionPane.showMessageDialog(vp, "El campo del apellido no puede quedar vacío");
//                        return;
//                    }
//                    if (!ValidData(telefono)) {
//                        JOptionPane.showMessageDialog(vp, "El campo de teléfono no puede quedar vacío");
//                        return;
//                    }
//                    if (!ValidData(sexo)) {
//                        JOptionPane.showMessageDialog(vp, "Elija el sexo");
//                        return;
//                    }
//
//                    if (!ValidData(direccion)) {
//                        JOptionPane.showMessageDialog(vp, "El campo de la dirección no puede quedar vacío");
//                        return;
//                    }
//                    if (!ValidData(email)) {
//                        JOptionPane.showMessageDialog(vp, "El campo del email no es válido");
//                        return;
//                    }
////                    if (!validateCorreo()) {
////                        JOptionPane.showMessageDialog(vp, "Proporcione correo válido\n");
////                        return;
////                    }
//                    if (!Cedula.validarCedula(numeroidentificacion)) {
//                        JOptionPane.showMessageDialog(vp, "Proporcione un número de identificación válido");
//                        return;
//                    }
                    Modeloempleado persona = new Modeloempleado();

                    persona.setCedula(cedula);
                    persona.setNombre(nombre);
                    persona.setApellido(apellido);
                    persona.setFechacontra(fechacon);
                    persona.setSalario(salario);
                    persona.setDiscapacidad(discapacidad);

                    persona.setHorario(horario);

                    if (persona.ModficarPersonaDB(cedula) == null) {
                        JOptionPane.showMessageDialog(ve, "Registro editado correctamente ");
                        ve.getDlgcrudempleados().dispose();
                    } else {
                        JOptionPane.showMessageDialog(ve, "No se pudo editar el registro");
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    System.out.println(e);
                }

            } else {
                if (ve.getDlgcrudempleados().getName().equals("eliminar")) {
                    try {
                        
                       

                        Modeloempleado persona = new Modeloempleado();
//                        
//                        if (persona.EliminarPersonaDB(cedula) == null) {
//                            ve.getDlgcrudempleados().setVisible(false);
//                            JOptionPane.showMessageDialog(ve, " eliminado con éxito");
//                        } else {
//                            JOptionPane.showMessageDialog(ve, "No se pudo eliminar al empleado ");
//                        }

                    } catch (NumberFormatException | NullPointerException e) {
                        System.out.print(e);
                    }
                }
            }
        }
        Cargaempleado();
    }
    
       private void Reporte (){
    ConnectionPG con =new ConnectionPG();
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/Vista/Reportes/EmpleadoA4.jasper"));
            JasperPrint  jp = JasperFillManager.fillReport(jr, null,con.getCon());
            JasperViewer jv = new JasperViewer(jp,false);
            jv.setVisible(true);
        } catch (JRException e) {
            Logger.getLogger(Controladorempleado.class.getName()).log(Level.SEVERE,null,e);
        }
    }

}
