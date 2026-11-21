package vista;

import dao.PersonaDAO;
import modelo.Persona;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionPersonalFrame extends JFrame {
    private final PersonaDAO personaDAO = new PersonaDAO();
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private int idSeleccionado = -1;
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtApellido = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextArea txtDireccion = new JTextArea(2, 20);

    public GestionPersonalFrame() {
        setTitle("Listado y Gestión de Personal");
        setSize(820, 560);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnas = {"ID", "Nombre", "Apellido", "Teléfono", "Email", "Dirección"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);

        initUI();
        cargarTabla();
    }

    private void initUI() {
        setLayout(new BorderLayout(12, 12));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Registros Almacenados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(lblTitulo, BorderLayout.NORTH);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                cargarFilaSeleccionada();
            }
        });
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        Validaciones.soloTextoSinNumeros(txtNombre);
        Validaciones.soloTextoSinNumeros(txtApellido);
        Validaciones.soloNumeros(txtTelefono);

        JPanel editPanel = new JPanel(new GridLayout(2, 3, 10, 8));
        editPanel.setBorder(BorderFactory.createTitledBorder("Editar Registro Seleccionado"));
        editPanel.add(crearCampo("Nombre", txtNombre));
        editPanel.add(crearCampo("Apellido", txtApellido));
        editPanel.add(crearCampo("Teléfono", txtTelefono));
        editPanel.add(crearCampo("Email", txtEmail));

        JPanel dirBox = new JPanel(new BorderLayout(3, 3));
        dirBox.add(new JLabel("Dirección"), BorderLayout.NORTH);
        dirBox.add(new JScrollPane(txtDireccion), BorderLayout.CENTER);
        editPanel.add(dirBox);

        JButton btnActualizar = new JButton("Actualizar / Modificar");
        btnActualizar.addActionListener(e -> modificarRegistro());

        JButton btnEliminar = new JButton("Eliminar Registro");
        btnEliminar.addActionListener(e -> eliminarRegistro());

        JButton btnRefrescar = new JButton("Refrescar Lista");
        btnRefrescar.addActionListener(e -> cargarTabla());

        JPanel accionesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        accionesPanel.add(btnRefrescar);
        accionesPanel.add(btnActualizar);
        accionesPanel.add(btnEliminar);

        JPanel southContainer = new JPanel(new BorderLayout(0, 8));
        southContainer.add(editPanel, BorderLayout.CENTER);
        southContainer.add(accionesPanel, BorderLayout.SOUTH);

        add(southContainer, BorderLayout.SOUTH);
    }

    private JPanel crearCampo(String label, JTextField field) {
        JPanel p = new JPanel(new BorderLayout(3, 3));
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Persona> lista = personaDAO.listar();
        for (Persona p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getApellido(), p.getTelefono(), p.getEmail(), p.getDireccion()
            });
        }
        limpiarEdicion();
    }

    private void cargarFilaSeleccionada() {
        int fila = tabla.getSelectedRow();
        idSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtApellido.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtEmail.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtDireccion.setText(modeloTabla.getValueAt(fila, 5).toString());
    }

    private void modificarRegistro() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();
        String direccion = txtDireccion.getText();

        if (!Validaciones.validarFormulario(this, nombre, apellido, telefono, email, direccion)) {
            return;
        }

        Persona actualizada = new Persona(idSeleccionado, nombre, apellido, telefono, email, direccion);
        if (personaDAO.actualizar(actualizada)) {
            JOptionPane.showMessageDialog(this, "Registro modificado exitosamente en la base de datos.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarRegistro() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar el registro con ID " + idSeleccionado + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (personaDAO.eliminar(idSeleccionado)) {
                JOptionPane.showMessageDialog(this, "Registro eliminado correctamente de la base de datos.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarEdicion() {
        idSeleccionado = -1;
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        tabla.clearSelection();
    }
}