package com.spotify.ui.admin.form;


import com.spotify.app.Admin;
import com.spotify.app.Song;
import com.spotify.ui.Content;
import com.spotify.ui.FFmpeg;
import com.spotify.ui.admin.ASSET;
import com.spotify.ui.admin.ContentAdmin;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class CreateAdsForm extends JPanel {
    private JLabel ChooseLabel;
    private JButton ChooseFile;
    private JLabel Blank;
    private JButton Create;

    public CreateAdsForm(Admin admin){
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        ChooseFile = new JButton("BROWSE FILE");
        Blank = new JLabel("");
        Create = new JButton("CREATE");
        ChooseLabel = new JLabel("CHOOSE FILE");
        Create.setBackground(Color.green);
        this.add(ChooseLabel);
        this.add(ChooseFile);
        this.add(Box.createRigidArea(new Dimension((int) ASSET.content.getWidth()-100 , 0)));
        this.add(Blank);
        this.add(Create);
        Blank.setPreferredSize(new Dimension(150,25));
        ChooseLabel.setPreferredSize(new Dimension(150,25));
        Create.setPreferredSize(new Dimension(125,25));
        ChooseLabel.setFont(f1);
        ChooseLabel.setForeground(new Color(226,226,226));
        ChooseFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    loadAudioFile();
                }catch (Exception b){

                }
            }
        });
        Create.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null,"Create Ads Successful");
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

        int returnVal = fileChooser.showOpenDialog(this.ChooseFile);

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
}
