package com.application.view.utilities;

import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@Getter
public class FileLocationSelector {
    private JFileChooser chooser;

    public FileLocationSelector() {
        initializeComponents();
        setProperties();
    }

    private void initializeComponents() {
        chooser = new JFileChooser();
    }

    private void setProperties() {
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    public String getFilePath(Component parent, String fileName) {
        if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getPath() + "\\" + fileName;
        } else {
            return null;
        }
    }

    public void openFile(String filePath, Component parent, String title) {
        Desktop desktop = Desktop.getDesktop();
        File file = new File(filePath);
        if (file.exists()) {
            try {
                desktop.open(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error when opening document!", title, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
