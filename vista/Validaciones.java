package vista;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Validaciones {
    
    public static void soloNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignorar el evento si no es un número
                }
            }
        });
    }

    public static void soloLetras(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignorar el evento si no es una letra o espacio
                }
            }
        });
    }

    public static boolean validarFormulario(
        Component parent,
        String nombre,
        String apellido,
        String telefono,
        String email,
        String direccion
    ) {
        if (nombre.isBlank() || apellido.isBlank() || telefono.isBlank() || email.isBlank() || direccion.isBlank()) {
      JOptionPane.showMessageDialog(parent, "Todos los campos son obligatorios      .", "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (nombre.matches(".*\\d.*") || apellido.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(parent, "El nombre no puede contener números.", "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!telefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(parent, "El teléfono debe contener solo números.", "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(parent, "Ingrese un correo electrónico válido.", "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
