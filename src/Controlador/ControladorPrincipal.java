/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista .*;

/**
 *
 * @author ERIKA
 */
public class ControladorPrincipal {

   
   VistaPrincipal vistaprin;

    public ControladorPrincipal() {
    }

    public ControladorPrincipal(VistaPrincipal vistaprin) {
        this.vistaprin = vistaprin;
        this.vistaprin.setVisible(true);
    }

   

    

  
     public void iniciaControl() {
        vistaprin.getBtnempleado().addActionListener(l -> crudempleado());

    }
     
         private void crudempleado() {

        Modeloempleado modelop = new Modeloempleado();
        VistaPrincipal vis = new VistaPrincipal();

        
        VistaEmpleado vista = new VistaEmpleado();

     

        vistaprin.getPanelPrin().add(vista);

        //ControladorEmpleado control = new ControladorEmpleado(modelop, modelo, vista, modelol, modelot);
        Controladorempleado control = new Controladorempleado(modelop, vista);

        control.IniciarControl();
    }
   
   
   

}
