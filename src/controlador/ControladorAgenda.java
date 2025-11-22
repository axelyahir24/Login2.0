package controlador;

import dao.ContactoDAO;
import modelo.Contacto;
import vista.AgendaFrame;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class ControladorAgenda {

    private AgendaFrame vista;
    private ContactoDAO dao = new ContactoDAO();
    private DefaultTableModel modeloTabla = new DefaultTableModel();

    public ControladorAgenda(AgendaFrame vista) {
        this.vista = vista;

        // Eventos
        vista.getBtnAgregar().addActionListener(e -> agregar());
        vista.getBtnEditar().addActionListener(e -> editar());
        vista.getBtnEliminar().addActionListener(e -> eliminar());
        vista.getTablaContactos().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { cargarDatos(); }
        });

        inicializarTabla();
        listar();
    }

    private void inicializarTabla() {
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Correo");
        vista.getTablaContactos().setModel(modeloTabla);
    }

    private void listar() {
        modeloTabla.setRowCount(0);
        for (Contacto c : dao.listar()) {
            modeloTabla.addRow(new Object[]{c.getIdContacto(), c.getNombre(), c.getTelefono(), c.getCorreo()});
        }
    }

    private void agregar() {
        Contacto c = new Contacto();
        c.setNombre(vista.getTxtNombre().getText());
        c.setTelefono(vista.getTxtTelefono().getText());
        c.setCorreo(vista.getTxtCorreo().getText());
        if (dao.agregar(c)) {
            listar();
            limpiar();
        }
    }

    private void editar() {
        int fila = vista.getTablaContactos().getSelectedRow();
        if (fila == -1) return;

        Contacto c = new Contacto();
        c.setIdContacto((int) vista.getTablaContactos().getValueAt(fila, 0));
        c.setNombre(vista.getTxtNombre().getText());
        c.setTelefono(vista.getTxtTelefono().getText());
        c.setCorreo(vista.getTxtCorreo().getText());

        if (dao.editar(c)) {
            listar();
            limpiar();
        }
    }

    private void eliminar() {
        int fila = vista.getTablaContactos().getSelectedRow();
        if (fila == -1) return;

        int id = (int) vista.getTablaContactos().getValueAt(fila, 0);
        if (dao.eliminar(id)) listar();
    }

    private void cargarDatos() {
        int fila = vista.getTablaContactos().getSelectedRow();
        vista.getTxtNombre().setText(modeloTabla.getValueAt(fila, 1).toString());
        vista.getTxtTelefono().setText(modeloTabla.getValueAt(fila, 2).toString());
        vista.getTxtCorreo().setText(modeloTabla.getValueAt(fila, 3).toString());
    }

    private void limpiar() {
        vista.getTxtNombre().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTxtCorreo().setText("");
    }
}


