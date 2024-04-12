package org.example;

import org.example.InterfazUsuario.PlaylistGUI;

import javax.swing.*;

public class PlaylistApp{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PlaylistGUI playlistGUI = new PlaylistGUI();
                playlistGUI.setVisible(true);
            }
        });
    }
}
