package login1;

import controlador.ControladorLogin;

public class Login1 {

    public static void main(String[] args) {

        FormLogin vista = new FormLogin();
        new ControladorLogin(vista);
        vista.setVisible(true);
    }
}
