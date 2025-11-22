package login1;

public class Menuprincipal extends javax.swing.JFrame {// Esta clase crea la ventana principal del sistema (Menu)
    // Logger: una herramienta que NetBeans usa para registrar errores/mensajes
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Menuprincipal.class.getName());

    
    public Menuprincipal() {// Este es el constructor de la ventana
        initComponents();// Llama a la funcion que dibuja todos los elementos (botones, menus, etc.)
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // --- Metodo principal (main) para probar esta ventana individualmente ---
    public static void main(String args[]) {
    // Pone la ventana Menuprincipal en la pantalla y la hace visible
        java.awt.EventQueue.invokeLater(() -> new Menuprincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
