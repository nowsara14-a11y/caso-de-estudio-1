package vista;

import dao.PersonaDAO;
import modelo.Persona;

import javax.swing.*;
import java.awt.*;

public class RegistroPersonalFrame extends JFrame {
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtApellido = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextArea txtDireccion = new JTextArea(3, 20);
    private final PersonaDAO personaDAO = new PersonaDAO();

    public RegistroPersonalFrame() {
        setTitle("Registro de Personal");
        setSize(540, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitulo = new JLabel("Registro de Personal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 28));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 18, 12));
        formPanel.add(crearBloqueCampo("Nombre", txtNombre));
        formPanel.add(crearBloqueCampo("Apellido", txtApellido));
        formPanel.add(crearBloqueCampo("Telefono", txtTelefono));
        formPanel.add(crearBloqueCampo("Email", txtEmail));

        Validaciones.soloTextoSinNumeros(txtNombre);
        Validaciones.soloTextoSinNumeros(txtApellido);
        Validaciones.soloNumeros(txtTelefono);

        JPanel dirPanel = new JPanel(new BorderLayout(5, 5));
        dirPanel.add(new JLabel("Dirección"), BorderLayout.NORTH);
        txtDireccion.setLineWrap(true);
        txtDireccion.setWrapStyleWord(true);
        dirPanel.add(new JScrollPane(txtDireccion), BorderLayout.CENTER);

        JPanel centerContainer = new JPanel(new BorderLayout(0, 15));
        centerContainer.add(formPanel, BorderLayout.NORTH);
        centerContainer.add(dirPanel, BorderLayout.CENTER);
        mainPanel.add(centerContainer, BorderLayout.CENTER);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setPreferredSize(new Dimension(180, 36));
        btnRegistrar.addActionListener(e -> registrarPersona());

        JButton btnVerRegistros = new JButton("Ver / Gestionar Registros");
        btnVerRegistros.setPreferredSize(new Dimension(190, 36));
        btnVerRegistros.addActionListener(e -> new GestionPersonalFrame().setVisible(true));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        bottomPanel.add(btnRegistrar);
        bottomPanel.add(btnVerRegistros);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel crearBloqueCampo(String etiqueta, JTextField campo) {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.add(new JLabel(etiqueta), BorderLayout.NORTH);
        campo.setPreferredSize(new Dimension(200, 30));
        panel.add(campo, BorderLayout.CENTER);
        return panel;
    }

    private void registrarPersona() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (!Validaciones.validarFormulario(this, nombre, apellido, telefono, email, direccion)) {
            return;
        }

        Persona nueva = new Persona(nombre, apellido, telefono, email, direccion);
        if (personaDAO.insertar(nueva)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Registro insertado correctamente en la base de datos.",
                    "Notificación",
                    JOptionPane.INFORMATION_MESSAGE
            );
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo insertar el registro.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        txtNombre.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroPersonalFrame().setVisible(true));
    }
}