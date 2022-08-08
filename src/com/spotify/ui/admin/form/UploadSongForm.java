package com.spotify.ui.admin.form;

import com.spotify.app.Admin;
import com.spotify.app.Genre;
import com.spotify.app.Song;
import com.spotify.ui.FFmpeg;
import com.spotify.ui.admin.ASSET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UploadSongForm extends JPanel{
    private JLabel ChooseArtist;
    private JLabel ChooseTitle;
    private JComboBox Artist;
    private JTextField Song;
    private JTextField Duration;
    private JLabel Durationlabel;
    private JButton Upload;
    private JLabel Blank;
    private JLabel ChooseLabel;
    private JButton ChooseFile;

    public UploadSongForm(Admin admin) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        String Artis [] = new String[admin.getSongs().size()];
        if(admin.getArtists().size() >0 && admin.getSongs().size()>0){
            for (int i = 0; i < admin.getArtists().size(); i++) {
                Artist = new JComboBox(admin.getArtists().toArray());
                Artist.setSelectedIndex(i);
            }
        }
        else{
            Artist = new JComboBox();
        }
        ChooseArtist = new JLabel("CHOOSE ARTIST");
        ChooseTitle = new JLabel("SONG TITLE");
        ChooseLabel = new JLabel("CHOOSE FILE");
        ChooseFile = new JButton("BROWSE FILE");
        Durationlabel = new JLabel("DURATION (SECONDS)");
        Duration = new JTextField();
        Song = new JTextField();
        this.setBackground(new Color(18, 18, 18));
        Upload = new JButton("UPLOAD");
        Upload.setBackground(Color.green);
        Blank = new JLabel("");
        this.add(ChooseArtist);
        this.add(Artist);
        this.add(Box.createRigidArea(new Dimension((int) ASSET.content.getWidth()-100 , 0)));
        this.add(ChooseTitle);
        this.add(Song);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(Durationlabel);
        this.add(Duration);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(ChooseLabel);
        this.add(ChooseFile);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(Blank);
        this.add(Upload);
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        ChooseArtist.setPreferredSize(new Dimension(150,25));
        ChooseLabel.setPreferredSize(new Dimension(150,25));
        ChooseTitle.setPreferredSize(new Dimension(150,25));
        Durationlabel.setPreferredSize(new Dimension(150,25));
        Durationlabel.setFont(f1);
        Durationlabel.setForeground(new Color(226,226,226));
        Artist.setPreferredSize(new Dimension(300,25));
        Song.setPreferredSize(new Dimension(300,25));
        Duration.setPreferredSize(new Dimension(300,25));
        Blank.setPreferredSize(new Dimension(150,25));
        ChooseArtist.setFont(f1);
        ChooseLabel.setFont(f1);
        ChooseArtist.setForeground(new Color(226,226,226));
        ChooseLabel.setForeground(new Color(226,226,226));
        ChooseTitle.setFont(f1);
        ChooseTitle.setForeground(new Color(226,226,226));
        Upload.setPreferredSize(new Dimension(125,25));
        ChooseFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    loadAudioFile();
                }catch (Exception b){

                }
            }
        });
        Upload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int durasi = Integer.parseInt(Duration.getText());
                admin.getSongs().add(new Song(ChooseTitle.getText(),"",formatter.format(date),admin.getArtists().get(Artist.getSelectedIndex()), null,durasi,0,0,null,null));
                JOptionPane.showMessageDialog(null,"Upload Successful \n" + formatter.format(date));
            }
        });
    }

    public void loadAudioFile() throws IOException {
        JFileChooser fileChooser = new JFileChooser();

        Action detailView = fileChooser.getActionMap().get("viewTypeDetails");
        detailView.actionPerformed(null);

        fileChooser.setPreferredSize(new Dimension(640, 480));
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);

        int returnVal = fileChooser.showOpenDialog(this.Song);

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String origpath = selectedFile.getParent(), origname = selectedFile.getName();
            String filepath = "./db/<NAMA ARTIS>/<NAMA ALBUM>/", filename = "";
            String[] splitter = origname.split("\\.");
            for(int i = 0; i < splitter.length - 1; i++) {
                filename += splitter[i] + ".";
            } filename += "wav";

            File comparedFile = new File(filepath + "\\" + filename);

            if(comparedFile.exists() == false) {
                FFmpeg converter = new FFmpeg(origpath, origname, filepath, filename);
            }
        }

        // Update audioFile di file song pake      filepath + "\\" + filename
    }

    public JLabel getChooseArtist() {
        return ChooseArtist;
    }

    public void setChooseArtist(JLabel chooseArtist) {
        ChooseArtist = chooseArtist;
    }

    public JLabel getChooseTitle() {
        return ChooseTitle;
    }

    public void setChooseTitle(JLabel chooseTitle) {
        ChooseTitle = chooseTitle;
    }

    public JComboBox getArtist() {
        return Artist;
    }

    public void setArtist(JComboBox artist) {
        Artist = artist;
    }

    public JTextField getSong() {
        return Song;
    }

    public void setSong(JTextField song) {
        Song = song;
    }

    public JButton getUpload() {
        return Upload;
    }

    public void setUpload(JButton upload) {
        Upload = upload;
    }

    public JLabel getBlank() {
        return Blank;
    }

    public void setBlank(JLabel blank) {
        Blank = blank;
    }

    public JLabel getChooseLabel() {
        return ChooseLabel;
    }

    public void setChooseLabel(JLabel chooseLabel) {
        ChooseLabel = chooseLabel;
    }

    public JButton getChooseFile() {
        return ChooseFile;
    }

    public void setChooseFile(JButton chooseFile) {
        ChooseFile = chooseFile;
    }
}
