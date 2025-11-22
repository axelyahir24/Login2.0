package controlador;

import javax.swing.JOptionPane;
import login1.FormLogin;
import vista.AgendaFrame;

public class ControladorLogin {

    private FormLogin vista;
    private CLogin modelo;

    public ControladorLogin(FormLogin vista) {
        this.vista = vista;
        this.modelo = new CLogin();
        iniciarControl();
    }

    public void iniciarControl() {
        vista.getBtnIngresar().addActionListener(l -> validarLogin());
    }

    private void validarLogin() {
        String usuario = vista.getTxtUsuario().getText();
        String contrasenia = String.valueOf(vista.getTxtContrasenia().getPassword());

        boolean acceso = modelo.entrarSiValido(usuario, contrasenia);

if (acceso) {
    JOptionPane.showMessageDialog(vista, "Bienvenido " + usuario);
    vista.dispose();
    AgendaFrame ag = new AgendaFrame();
    new ControladorAgenda(ag); // <-- importante
    ag.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(vista, "Usuario o contraseña incorrectos");
        }
    }
}


