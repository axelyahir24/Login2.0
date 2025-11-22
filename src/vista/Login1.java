package vista;

import login1.FormLogin;        // IMPORTAMOS FormLogin desde su paquete real
import controlador.ControladorLogin;

public class Login1 {

    public static void main(String[] args) {

        FormLogin vista = new FormLogin();  // Ahora ya la reconoce

        new ControladorLogin(vista);

        vista.setVisible(true);
    }
}

