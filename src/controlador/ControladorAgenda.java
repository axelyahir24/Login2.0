package controlador;

import dao.ContactoDAO;
import modelo.Contacto;
import vista.AgendaFrame;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList; 
import java.util.List; 

public class ControladorAgenda {

    private AgendaFrame vista;
    private ContactoDAO dao = new ContactoDAO();
    private DefaultTableModel modeloTabla = new DefaultTableModel();
    
    // Bandera para controlar el modo de edición
    private boolean modoEdicion = false; 

    public ControladorAgenda(AgendaFrame vista) {
        this.vista = vista;
        
        // Eventos de botones
        vista.getBtnAgregar().addActionListener(e -> agregarOConfirmarEdicion());
        vista.getBtnEditar().addActionListener(e -> habilitarModoEdicion());
        vista.getBtnEliminar().addActionListener(e -> eliminar());
        vista.getBtnCancelar().addActionListener(e -> cancelarEdicion()); 
        
        // **ACTUALIZACIÓN CLAVE:** Al hacer clic, solo carga y DESACTIVA edición
        vista.getTablaContactos().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
                cargarDatos();
                // Bloquea los campos después de cargar los datos de la fila
                actualizarEstadoCampos(false); 
            }
        });
        
        // **INICIO:** Los campos son editables al inicio para el modo "Agregar"
        actualizarEstadoCampos(true); 

        inicializarTabla();
        listar();
        actualizarBotones(false); 
    }
    
    // El resto del código de inicializarTabla(), listar(), eliminar(), y limpiar() permanece igual.

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

    private boolean validarDatos(Contacto c) {
        // Lista para guardar mensajes de error
        List<String> errores = new ArrayList<>(); 
        
        // 1. Validación de Nombre (no vacío)
        if (c.getNombre().trim().isEmpty()) {
            errores.add("- El Nombre no puede estar vacío.");
        }
        
        // 2. Validación de Teléfono: Exactamente 10 dígitos.
        String telefonoLimpio = c.getTelefono().replaceAll("[\\s\\-()]", "");
        String regexTelefono = "^[0-9]{10}$"; 
        Pattern patternTelefono = Pattern.compile(regexTelefono);
        Matcher matcherTelefono = patternTelefono.matcher(telefonoLimpio);

        if (!matcherTelefono.matches()) {
            errores.add("- El Teléfono debe contener exactamente 10 dígitos numéricos.");
        }

        // 3. Validación de Correo: formato básico de email
        String regexCorreo = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern patternCorreo = Pattern.compile(regexCorreo);
        Matcher matcherCorreo = patternCorreo.matcher(c.getCorreo());

        if (!matcherCorreo.matches()) {
            errores.add("- El Correo Electrónico tiene un formato inválido.");
        }
        
        // --- Reporte de Errores ---
        if (!errores.isEmpty()) {
            String mensajeError = "Se encontraron los siguientes errores de validación:\n" + String.join("\n", errores);
            JOptionPane.showMessageDialog(vista, mensajeError, "Error de Validación de Datos", JOptionPane.ERROR_MESSAGE);
            return false; // Retorna falso si hay errores
        }
        
        return true; // Retorna verdadero si no hay errores
    }

    private void agregarOConfirmarEdicion() {
        Contacto c = new Contacto();
        c.setNombre(vista.getTxtNombre().getText());
        c.setTelefono(vista.getTxtTelefono().getText());
        c.setCorreo(vista.getTxtCorreo().getText());
        
        // Se valida SIEMPRE
        if (!validarDatos(c)) {
            return; 
        }

        if (modoEdicion) {
            // Modo Edición
            int fila = vista.getTablaContactos().getSelectedRow();
            if (fila != -1) {
                c.setIdContacto((int) vista.getTablaContactos().getValueAt(fila, 0)); 
                if (dao.editar(c)) {
                    listar();
                    limpiar();
                    actualizarBotones(false); 
                    actualizarEstadoCampos(true); // Vuelve a modo "Agregar" (editable)
                    JOptionPane.showMessageDialog(vista, "Contacto editado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al editar el contacto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            // Modo Agregar Normal
            if (dao.agregar(c)) {
                listar();
                limpiar();
                // Los campos ya están editables, solo limpia
                JOptionPane.showMessageDialog(vista, "Contacto agregado con éxito.");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al agregar el contacto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void habilitarModoEdicion() {
        int fila = vista.getTablaContactos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un contacto de la tabla para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        modoEdicion = true;
        actualizarBotones(true);
        actualizarEstadoCampos(true); // <--- HABILITAR CAMPOS para edición
        cargarDatos(); 
        vista.getTxtNombre().requestFocus();
        JOptionPane.showMessageDialog(vista, "Modo Edición Activado. Modifique los campos y presione 'Confirmar Edición'.");
    }
    
    private void cancelarEdicion() {
        modoEdicion = false;
        limpiar();
        vista.getTablaContactos().clearSelection();
        actualizarBotones(false); 
        actualizarEstadoCampos(true); // <--- Vuelve a modo "Agregar" (editable)
        JOptionPane.showMessageDialog(vista, "Edición cancelada. Volviendo al modo Agregar.");
    }
    
    // **ACTUALIZACIÓN CLAVE:** Método para controlar la propiedad editable
    private void actualizarEstadoCampos(boolean editable) {
        vista.getTxtNombre().setEditable(editable);
        vista.getTxtTelefono().setEditable(editable);
        vista.getTxtCorreo().setEditable(editable);
        
        // Si se llama con 'false' (desde el clic en tabla), los campos se bloquean.
        // Si se llama con 'true' (desde Cancelar/Agregar/Editar), los campos se habilitan.
    }

    private void actualizarBotones(boolean enEdicion) {
        modoEdicion = enEdicion;
        
        if (enEdicion) {
            vista.getBtnAgregar().setText("Confirmar Edición");
            vista.getBtnEditar().setEnabled(false);
            vista.getBtnEliminar().setEnabled(false);
            vista.getBtnCancelar().setVisible(true); 
        } 
        else {
            vista.getBtnAgregar().setText("Agregar Contacto");
            vista.getBtnEditar().setEnabled(true);
            vista.getBtnEliminar().setEnabled(true);
            vista.getBtnCancelar().setVisible(false); 
        }
    }

    private void eliminar() {
        int fila = vista.getTablaContactos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un contacto de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de que quieres eliminar este contacto?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            int id = (int) vista.getTablaContactos().getValueAt(fila, 0);
            if (dao.eliminar(id)) {
                listar();
                limpiar();
                actualizarEstadoCampos(true); // Asegura que vuelva a modo Agregar
                JOptionPane.showMessageDialog(vista, "Contacto eliminado con éxito.");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el contacto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarDatos() {
        int fila = vista.getTablaContactos().getSelectedRow();
        if (fila != -1) {
            vista.getTxtNombre().setText(modeloTabla.getValueAt(fila, 1).toString());
            vista.getTxtTelefono().setText(modeloTabla.getValueAt(fila, 2).toString());
            vista.getTxtCorreo().setText(modeloTabla.getValueAt(fila, 3).toString());
        }
    }

    private void limpiar() {
        vista.getTxtNombre().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTxtCorreo().setText("");
        vista.getTablaContactos().clearSelection();
    }
}