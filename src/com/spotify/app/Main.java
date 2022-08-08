package com.spotify.app;

import com.spotify.ui.LoginRegister.Flogin;
import com.spotify.ui.Window;
import java.awt.Color;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Admin());

        if(accounts.get(accounts.size() - 1) instanceof Admin) {
            Admin admin = (Admin)accounts.get(accounts.size() - 1);
            accounts.add(new User("SamGun", "hahaha", "Samuel", "Gunawan", "Gatau", "Gatau", "samgun@gmail.com"));
//            admin.getArtists().add(new Artist("IU", true, ""));
//            admin.getGenres().add(new Genre("K-pop"));
//            admin.getSongs().add(new Song(
//                "LILAC", "LILAC", "03/25/2021", admin.getArtists().get(admin.getArtists().size() - 1),
//                admin.getGenres().get(admin.getGenres().size() - 1), 214, 25189629, 25189629,
//                "./db/IU/LILAC/Cover.jpg", "./db/IU/LILAC/01. LILAC.wav"
//            ));
//            admin.getSongs().add(new Song(
//                "Coin", "LILAC", "03/25/2021", admin.getArtists().get(admin.getArtists().size() - 1),
//                admin.getGenres().get(admin.getGenres().size() - 1), 193, 9104017, 9104017,
//                "./db/IU/LILAC/Cover.jpg", "./db/IU/LILAC/03. Coin.wav"
//            ));
//            admin.getPlaylists().add(new Playlist(
//                new Color(160, 72, 72), admin.getSongs(), "ALBUM", "LILAC", "-",
//                admin.getArtists().get(admin.getArtists().size() - 1).getName(),
//                "03/25/2021", "./db/IU/LILAC/Cover.jpg", 0, 2
//            ));
//            admin.getArtists().get(admin.getArtists().size() - 1).getAlbum().add(
//                admin.getPlaylists().get(admin.getPlaylists().size() - 1)
//            );

            admin.getArtists().add(new Artist("WagakkiBand", true, "Japanese traditional rock band"));
            admin.getGenres().add(new Genre("J-pop"));
            admin.getSongs().add(new Song(
                "Calling", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 270, 95214, 95214,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/01. Calling.wav"
            ));
            admin.getSongs().add(new Song(
                "Ignite", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 212, 526197, 526197,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/02. Ignite.wav"
            ));
            admin.getSongs().add(new Song(
                "reload dead", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 305, 92373, 92373,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/03. reload dead.wav"
            ));
            admin.getSongs().add(new Song(
                "生きとしいける花", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 245, 107950, 107950,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/04. 生きとしいける花.wav"
            ));
            admin.getSongs().add(new Song(
                "月下美人", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 280, 290298, 290298,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/05. 月下美人.wav"
            ));
            admin.getSongs().add(new Song(
                "Sakura Rising", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 229, 590335, 590335,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/06. Sakura Rising.wav"
            ));
            admin.getSongs().add(new Song(
                "ゲルニカ", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 251, 80919, 80919,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/07. ゲルニカ.wav"
            ));
            admin.getSongs().add(new Song(
                "Tokyo Sensation", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 221, 75390, 75390,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/08. Tokyo Sensation.wav"
            ));
            admin.getSongs().add(new Song(
                "オリガミイズム", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 246, 75630, 75630,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/09. オリガミイズム.wav"
            ));
            admin.getSongs().add(new Song(
                "宛名のない手紙", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 305, 105657, 105657,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/10. 宛名のない手紙.wav"
            ));
            admin.getSongs().add(new Song(
                "日輪", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 266, 150527, 150527,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/11. 日輪.wav"
            ));
            admin.getSongs().add(new Song(
                "Eclipse", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 303, 61093, 61093,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/12. Eclipse.wav"
            ));
            admin.getSongs().add(new Song(
                "Singin' for...", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 223, 245094, 245094,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/13. Singin' for....wav"
            ));
            admin.getSongs().add(new Song(
                "ロキ", "TOKYO SINGING", "10/05/2020", admin.getArtists().get(admin.getArtists().size() - 1),
                admin.getGenres().get(admin.getGenres().size() - 1), 232, 487277, 487277,
                "./db/WagakkiBand/Tokyo Singing/Cover.jpg", "./db/WagakkiBand/Tokyo Singing/14. ロキ.wav"
            ));
            admin.getPlaylists().add(new Playlist(
                new Color(40, 56, 96), admin.getSongs(), "ALBUM", "TOKYO SINGING", "-",
                admin.getArtists().get(admin.getArtists().size() - 1).getName(),
                "10/05/2020", "./db/WagakkiBand/Tokyo Singing/Cover.jpg", 0, 0, 14
            ));
            admin.getArtists().get(admin.getArtists().size() - 1).getAlbum().add(
                admin.getPlaylists().get(admin.getPlaylists().size() - 1)
            );

            User user = null;

            if(accounts.get(accounts.size() - 1) instanceof User) {
                user = (User)accounts.get(accounts.size() - 1);
            }

//            WindowAdmin windowAdmin = new WindowAdmin(admin, accounts);
            Window window = new Window(user, accounts);
            System.gc();
//            Flogin frameLogin = new Flogin(accounts);
//            frameLogin.setVisible(true);
        }
    }
}
