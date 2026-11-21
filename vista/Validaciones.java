package vista;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Validaciones {

    public static void soloNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
            }
        });
    }

    public static void soloTextoSinNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isDigit(c)) {
                    e.consume();
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
        if (nombre == null || nombre.isBlank()
                || apellido == null || apellido.isBlank()
                || telefono == null || telefono.isBlank()
                || email == null || email.isBlank()
                || direccion == null || direccion.isBlank()) {
            JOptionPane.showMessageDialog(
                    parent,
                    "Todos los campos son obligatorios.",
                    "Validación",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        if (nombre.matches(".*\\d.*") || apellido.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(
                    parent,
                    "El nombre y el apellido no pueden contener números.",
                    "Validación",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        if (!telefono.trim().matches("\\d+")) {
            JOptionPane.showMessageDialog(
                    parent,
                    "El teléfono debe contener solo números.",
                    "Validación",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        if (!email.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(
                    parent,
                    "Ingrese un correo electrónico válido.",
                    "Validación",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        return true;
    }
}