package org.example.InterfazUsuario;
import org.example.Nodo.Nodo;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class PlaylistGUI extends JFrame {
    private DefaultListModel<Nodo> playlistModel;
    private JList<Nodo> playlistList;
    private JTextArea textArea;

    public PlaylistGUI() {
        setTitle("Gestor de Playlist");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el modelo de la lista de reproducción
        playlistModel = new DefaultListModel<>();
        playlistList = new JList<>(playlistModel);
        JScrollPane scrollPane = new JScrollPane(playlistList);

        // Área de texto para mostrar información detallada de la canción seleccionada
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane textScrollPane = new JScrollPane(textArea);

        // Botones
        JButton addButton = new JButton("Agregar Canción");
        JButton removeButton = new JButton("Eliminar Canción");
        JButton sortButton = new JButton("Ordenar Playlist");
        JButton searchButton = new JButton("Buscar");
        JButton exportButton = new JButton("Exportar Playlist");

        // Campos de texto para búsqueda
        JTextField searchField = new JTextField();

        // Layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(exportButton);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Buscar: "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        controlPanel.add(searchPanel, BorderLayout.SOUTH);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        leftPanel.add(controlPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("Información de la Canción"), BorderLayout.NORTH);
        rightPanel.add(textScrollPane, BorderLayout.CENTER);

        setLayout(new GridLayout(1, 2));
        add(leftPanel);
        add(rightPanel);

        // Agregar acción al botón de agregar canción
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Ingrese el nombre de la canción:");
                String artist = JOptionPane.showInputDialog("Ingrese el nombre del artista:");
                String genre = JOptionPane.showInputDialog("Ingrese el género:");
                int duration = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la duración (segundos):"));

                Nodo song = new Nodo(name, artist, genre, duration);
                playlistModel.addElement(song);
            }
        });

        // Agregar acción al botón de eliminar canción
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = playlistList.getSelectedIndex();
                if (selectedIndex != -1) {
                    playlistModel.remove(selectedIndex);
                }
            }
        });

        // Agregar acción al botón de ordenar lista de reproducción
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortPlaylist();
            }
        });

        // Agregar acción al botón de búsqueda
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                searchSongs(searchTerm);
            }
        });

        // Agregar acción al botón de exportar lista de reproducción
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportPlaylist();
            }
        });

        // Agregar listener para mostrar información detallada de la canción seleccionada
        playlistList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Nodo selectedSong = playlistList.getSelectedValue();
                if (selectedSong != null) {
                    String info = "Nombre: " + selectedSong.getNombreCancion() + "\n" +
                            "Artista: " + selectedSong.getNombreArtistaEnEspanol() + "\n" +
                            "Género: " + selectedSong.getNombreGeneroEnEspanol() + "\n" +
                            "Duración: " + selectedSong.getDuracion() + " segundos";
                    textArea.setText(info);
                }
            }
        });
    }

    private void sortPlaylist() {
        List<Nodo> songs = new LinkedList<>();
        for (int i = 0; i < playlistModel.size(); i++) {
            songs.add(playlistModel.getElementAt(i));
        }

        // Ordenar la lista de reproducción por nombre de canción
        songs.sort(Comparator.comparing(Nodo::getNombreCancion));

        // Actualizar el modelo de la lista de reproducción
        playlistModel.removeAllElements();
        for (Nodo song : songs) {
            playlistModel.addElement(song);
        }
    }

    private void searchSongs(String searchTerm) {
        playlistList.clearSelection();
        for (int i = 0; i < playlistModel.size(); i++) {
            Nodo song = playlistModel.getElementAt(i);
            if (song.getNombreCancion().equalsIgnoreCase(searchTerm)
                    || song.getNombreArtistaEnEspanol().equalsIgnoreCase(searchTerm)
                    || song.getNombreGeneroEnEspanol().equalsIgnoreCase(searchTerm)) {
                playlistList.setSelectedIndex(i);
                playlistList.ensureIndexIsVisible(i);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Canción no encontrada: " + searchTerm);
    }

    private void exportPlaylist() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar Playlist");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(fileToSave);
                for (int i = 0; i < playlistModel.size(); i++) {
                    Nodo song = playlistModel.getElementAt(i);
                    writer.write("Nombre: " + song.getNombreCancion() + "\n");
                    writer.write("Artista: " + song.getNombreArtistaEnEspanol() + "\n");
                    writer.write("Género: " + song.getNombreGeneroEnEspanol() + "\n");
                    writer.write("Duración: " + song.getDuracion() + " segundos\n\n");
                }
                writer.close();
                JOptionPane.showMessageDialog(this, "Playlist exportada exitosamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al exportar la playlist: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
}
}






//public class PlaylistGUI extends JFrame {
//    private DefaultListModel<Nodo> playlistModel;
//    private JList<Nodo> playlistList;
//
//    public PlaylistGUI() {
//        setTitle("Playlist Manager");
//        setSize(600, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        // Crear el modelo de la lista de reproducción
//        playlistModel = new DefaultListModel<>();
//        playlistList = new JList<>(playlistModel);
//        JScrollPane scrollPane = new JScrollPane(playlistList);
//
//        // Botones
//        JButton addButton = new JButton("Add Song");
//        JButton removeButton = new JButton("Remove Song");
//        JButton sortButton = new JButton("Sort Playlist");
//        JButton searchButton = new JButton("Search");
//        JButton exportButton = new JButton("Export Playlist");
//
//        // Campos de texto para búsqueda
//        JTextField searchField = new JTextField();
//
//        // Layout
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new GridLayout(1, 5));
//        buttonPanel.add(addButton);
//        buttonPanel.add(removeButton);
//        buttonPanel.add(sortButton);
//        buttonPanel.add(searchButton);
//        buttonPanel.add(exportButton);
//
//        JPanel searchPanel = new JPanel(new BorderLayout());
//        searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
//        searchPanel.add(searchField, BorderLayout.CENTER);
//
//        setLayout(new BorderLayout());
//        add(scrollPane, BorderLayout.CENTER);
//        add(buttonPanel, BorderLayout.SOUTH);
//        add(searchPanel, BorderLayout.NORTH);
//
//        // Agregar acción al botón de agregar canción
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String name = JOptionPane.showInputDialog("Enter song name:");
//                String artist = JOptionPane.showInputDialog("Enter artist:");
//                String genre = JOptionPane.showInputDialog("Enter genre:");
//                int duration = Integer.parseInt(JOptionPane.showInputDialog("Enter duration (seconds):"));
//
//                Nodo song = new Nodo(name, artist, genre, duration);
//                playlistModel.addElement(song);
//            }
//        });
//
//        // Agregar acción al botón de eliminar canción
//        removeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedIndex = playlistList.getSelectedIndex();
//                if (selectedIndex != -1) {
//                    playlistModel.remove(selectedIndex);
//                }
//            }
//        });
//
//        // Agregar acción al botón de ordenar lista de reproducción
//        sortButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sortPlaylist();
//            }
//        });
//
//        // Agregar acción al botón de búsqueda
//        searchButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String searchTerm = searchField.getText();
//                searchSongs(searchTerm);
//            }
//        });
//
//        // Agregar acción al botón de exportar lista de reproducción
//        exportButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                exportPlaylist();
//            }
//        });
//    }
//
//    private void sortPlaylist() {
//        List<Nodo> songs = new LinkedList<>();
//        for (int i = 0; i < playlistModel.size(); i++) {
//            songs.add(playlistModel.getElementAt(i));
//        }
//
//        // Ordenar la lista de reproducción por nombre de canción
//        songs.sort(Comparator.comparing(Nodo::getNombreCancion));
//
//        // Actualizar el modelo de la lista de reproducción
//        playlistModel.removeAllElements();
//        for (Nodo song : songs) {
//            playlistModel.addElement(song);
//        }
//    }
//
//    private void searchSongs(String searchTerm) {
//        playlistList.clearSelection();
//        for (int i = 0; i < playlistModel.size(); i++) {
//            Nodo song = playlistModel.getElementAt(i);
//            if (song.getNombreCancion().equalsIgnoreCase(searchTerm)
//                    || song.getNombreArtistaEnEspanol().equalsIgnoreCase(searchTerm)
//                    || song.getNombreGeneroEnEspanol().equalsIgnoreCase(searchTerm)) {
//                playlistList.setSelectedIndex(i);
//                playlistList.ensureIndexIsVisible(i);
//                return;
//            }
//        }
//        JOptionPane.showMessageDialog(this, "Song not found: " + searchTerm);
//    }
//
//    private void exportPlaylist() {
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setDialogTitle("Export Playlist");
//        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
//        int userSelection = fileChooser.showSaveDialog(this);
//        if (userSelection == JFileChooser.APPROVE_OPTION) {
//            File fileToSave = fileChooser.getSelectedFile();
//            try {
//                FileWriter writer = new FileWriter(fileToSave);
//                for (int i = 0; i < playlistModel.size(); i++) {
//                    Nodo song = playlistModel.getElementAt(i);
//                    writer.write("Song: " + song.getNombreCancion() + "\n");
//                    writer.write("Artist: " + song.getNombreArtistaEnEspanol() + "\n");
//                    writer.write("Genre: " + song.getNombreGeneroEnEspanol() + "\n");
//                    writer.write("Duration: " + song.getDuracion() + " seconds\n\n");
//                }
//                writer.close();
//                JOptionPane.showMessageDialog(this, "Playlist exported successfully.");
//            } catch (IOException e) {
//                JOptionPane.showMessageDialog(this, "Error exporting playlist: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                PlaylistGUI playlistGUI = new PlaylistGUI();
//                playlistGUI.setVisible(true);
//            }
//        });
//    }
//}

//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.Comparator;
//import java.util.LinkedList;
//import java.util.List;
//
//public class PlaylistGUI extends JFrame {
//    private DefaultListModel<Nodo> playlistModel;
//    private JList<Nodo> playlistList;
//
//    public PlaylistGUI() {
//        setTitle("Playlist Manager");
//        setSize(600, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        // Crear el modelo de la lista de reproducción
//        playlistModel = new DefaultListModel<>();
//        playlistList = new JList<>(playlistModel);
//        JScrollPane scrollPane = new JScrollPane(playlistList);
//
//        // Botones
//        JButton addButton = new JButton("Add Song");
//        JButton removeButton = new JButton("Remove Song");
//        JButton sortButton = new JButton("Sort Playlist");
//
//        // Layout
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new GridLayout(1, 3));
//        buttonPanel.add(addButton);
//        buttonPanel.add(removeButton);
//        buttonPanel.add(sortButton);
//
//        setLayout(new BorderLayout());
//        add(scrollPane, BorderLayout.CENTER);
//        add(buttonPanel, BorderLayout.SOUTH);
//
//        // Agregar acción al botón de agregar canción
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String name = JOptionPane.showInputDialog("Enter song name:");
//                String artist = JOptionPane.showInputDialog("Enter artist:");
//                String genre = JOptionPane.showInputDialog("Enter genre:");
//                int duration = Integer.parseInt(JOptionPane.showInputDialog("Enter duration (seconds):"));
//
//                Nodo song = new Nodo(name, artist, genre, duration);
//                playlistModel.addElement(song);
//            }
//        });
//
//        // Agregar acción al botón de eliminar canción
//        removeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedIndex = playlistList.getSelectedIndex();
//                if (selectedIndex != -1) {
//                    playlistModel.remove(selectedIndex);
//                }
//            }
//        });
//
//        // Agregar acción al botón de ordenar lista de reproducción
//        sortButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sortPlaylist();
//            }
//        });
//    }
//
//    private void sortPlaylist() {
//        List<Nodo> songs = new LinkedList<>();
//        for (int i = 0; i < playlistModel.size(); i++) {
//            songs.add(playlistModel.getElementAt(i));
//        }
//
//        // Ordenar la lista de reproducción por nombre de canción
//        songs.sort(Comparator.comparing(Nodo::getNombreCancion));
//
//        // Actualizar el modelo de la lista de reproducción
//        playlistModel.removeAllElements();
//        for (Nodo song : songs) {
//            playlistModel.addElement(song);
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                PlaylistGUI playlistGUI = new PlaylistGUI();
//                playlistGUI.setVisible(true);
//            }
//        });
//    }
//}
