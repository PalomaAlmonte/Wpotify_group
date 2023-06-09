package view.Library;

import model.Album;
import model.Playlist;
import model.Song;
import view.Center.ShowPanel;
import view.Player.PlayerPart;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.EditorKit;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * This JPanel is placed at the left side of MainPaige which includes library and playlists
 * this class also manages reading and writing song for program
 */

public class LibraryPart extends JPanel {

    private JLabel options;
    private JLabel icon;
    private JLabel Jpotify;
    private static final String JPOTIFY_LABEL = "Jpotify";
    private JSeparator jSeparator;
    private JLabel libraryLabel;
    private JTextField fileChooserBtn;
    private JTextField songsBtn;
    private JTextField albumsBtn;
    private JTextField EditBtn;
    private JLabel playlistLabel;
    private JTextField playlistBtn;
    private JTextField newPlaylistBtn;
    private JTextField sharedPlaylistBtn;
    private JTextField favouriteBtn;
    private Song song;
    private ShowPanel showPanel;
    private Color foreground;
    private Color pressedBackground;
    private Album album;
    private String username;
    private PlayerPart playerPart;
    private NewPlaylist newPlaylist;
    private RemoveSong removeSong;


    private ArrayList<Song> songs = new ArrayList<>();
    /**
     * playlist doesnt have arraylist in library and they are shown in showpanel exactly like favourite songs.
     */
    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<Song> favouriteSongs = new ArrayList<>();
    private ArrayList<Song> sharedSongs = new ArrayList<>();
    private ArrayList<String> playlistNames = new ArrayList<>();


    public LibraryPart(String user, PlayerPart playerPart) throws IOException {

        super();
        this.setPreferredSize(new Dimension(150, 750));
        setSize(400, 400);
        this.setBackground(new Color(0, 0, 0));
        setLayout(new GridLayout(19, 1));
        foreground = new Color(255, 255, 255);
        pressedBackground = new Color(0, 0, 0);
        this.playerPart = playerPart;
        //trying to fix the border issue


        username = user;
        if (new File(username + "/songs/").list().length > 0) {
            songs = loadSongs(username);
        }


        options = new JLabel("      ● ● ●");
        options.setForeground(Color.WHITE);
        options.setFont(new Font("Arial", Font.BOLD, 6));
        options.setToolTipText("options");
        options.setBackground(new Color(24, 24, 24));
        options.setForeground(Color.white);
        options.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.add(options);

        icon = new JLabel();
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon JpotifyIcon = new ImageIcon("JpotifyIcon.png");
        Image image = JpotifyIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        JpotifyIcon = new ImageIcon(newimg);
        icon.setIcon(JpotifyIcon);
        add(icon);

        Jpotify = new JLabel(JPOTIFY_LABEL);
        Jpotify.setFont(new Font("San Francisco", Font.BOLD, 15));
        Jpotify.setForeground(foreground);
        Jpotify.setBackground(this.getBackground());
        Jpotify.setHorizontalAlignment(SwingConstants.CENTER);
        add(Jpotify);


        jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        jSeparator.setForeground(new Color(0, 0,0));
        add(jSeparator);


        libraryLabel = new JLabel("   LIBRARY      ");
        libraryLabel.setFont(new Font("San Francisco", Font.BOLD, 17));
        libraryLabel.setForeground(foreground);
        libraryLabel.setBackground(this.getBackground());
        add(libraryLabel);

/**
 * This button is for adding a new song to the program
 */
        fileChooserBtn = new JTextField("   Add To Library");
        fileChooserBtn.setFont(new Font("San Francisco", Font.BOLD, 15));
        fileChooserBtn.setEditable(false);
        fileChooserBtn.setBackground(this.getBackground());
        fileChooserBtn.setForeground(foreground);
        fileChooserBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                fileChooserBtn.setBackground(pressedBackground);
            }

            /**
             * here user choose a song from computer and it adds to the program songs for user and it saves in a specific folder
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                fileChooserBtn.setBackground(getBackground());
                // added part
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                /*try {
                    for (UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //set Look and Feel to Windows
                }*/
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter(".mp3 files", "mp3"));
                fileChooser.setCurrentDirectory(new File("E:/"));
                int result = fileChooser.showOpenDialog(fileChooser);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    saveSong(addSong(selectedFile.getAbsolutePath()));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        fileChooserBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        add(fileChooserBtn);


/**
 * This button shows all existed songs according to last time played
 */
        songsBtn = new JTextField("   Songs");
        songsBtn.setFont(new Font("San Francisco", Font.BOLD, 15));
        songsBtn.setEditable(false);
        songsBtn.setBackground(this.getBackground());
        songsBtn.setForeground(foreground);
        songsBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                songsBtn.setBackground(pressedBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                songsBtn.setBackground(getBackground());

                showPanel.removeAll();
                showPanel.repaint();
                showPanel.setSongs(songs);
                showPanel.revalidate();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        songsBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        add(songsBtn);


/**
 * This button shows all albums according to last time played
 */
        albumsBtn = new JTextField("   Albums");
        albumsBtn.setFont(new Font("San Francisco", Font.BOLD, 15));
        albumsBtn.setBackground(this.getBackground());
        albumsBtn.setForeground(foreground);
        albumsBtn.setEditable(false);
        albumsBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                albumsBtn.setBackground(pressedBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                albumsBtn.setBackground(getBackground());
                showPanel.removeAll();
                showPanel.repaint();

                showPanel.setAlbums(albums, songs);
                showPanel.revalidate();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        albumsBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        add(albumsBtn);

/**
 * by clicking this button a new panel will be opened and user and choose what songs to be deleted from the program
 */
        EditBtn = new JTextField("   Edit");
        EditBtn.setFont(new Font("San Francisco", Font.BOLD, 15));
        EditBtn.setBackground(this.getBackground());
        EditBtn.setForeground(foreground);
        EditBtn.setEditable(false);
        EditBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                EditBtn.setBackground(pressedBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                EditBtn.setBackground(getBackground());

                removeSong = new RemoveSong(songs, getLibrarypartItself(), playerPart);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        EditBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        add(EditBtn);


        add(Box.createRigidArea(new Dimension(0, 5)));

        playlistLabel = new JLabel("    PLAYLISTS");
        songsBtn.setFont(new Font("San Francisco", Font.BOLD, 17));
        playlistLabel.setForeground(foreground);
        playlistLabel.setBackground(this.getBackground());
        add(playlistLabel);


        /**
         * This button creates and adds a new playlist
         */
        newPlaylistBtn = new JTextField("   + New Playlist");
        newPlaylistBtn.setFont(new Font("San Francisco", Font.BOLD, 15));
        newPlaylistBtn.setEditable(false);
        newPlaylistBtn.setBackground(this.getBackground());
        newPlaylistBtn.setForeground(foreground);
        newPlaylistBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                newPlaylistBtn.setBackground(pressedBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                newPlaylistBtn.setBackground(getBackground());

                newPlaylist = new NewPlaylist(songs, getLibrarypartItself());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        newPlaylistBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        add(newPlaylistBtn);


/**
 * this button shows all existed playlists for user
 */

        playlistBtn = new JTextField("   Playlists");
        playlistBtn.setFont(new Font("San Francisco", Font.BOLD, 15));
        playlistBtn.setEditable(false);
        playlistBtn.setBackground(this.getBackground());
        playlistBtn.setForeground(foreground);
        playlistBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                playlistBtn.setBackground(pressedBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                playlistBtn.setBackground(getBackground());
                showPanel.removeAll();
                showPanel.repaint();
                showPanel.setPlaylists(playlistNames, songs, getLibrarypartItself());
                showPanel.revalidate();

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        playlistBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        add(playlistBtn);


/**
 * This buttons shows user's shared playlist on network
 */
        sharedPlaylistBtn = new JTextField("   Shared Playlist");
        sharedPlaylistBtn.setFont(new Font("San Francisco", Font.BOLD, 15));
        sharedPlaylistBtn.setEditable(false);
        sharedPlaylistBtn.setBackground(this.getBackground());
        sharedPlaylistBtn.setForeground(foreground);
        sharedPlaylistBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                sharedPlaylistBtn.setBackground(pressedBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                sharedPlaylistBtn.setBackground(getBackground());

                showPanel.removeAll();
                showPanel.repaint();
                sharedSongs = null;
                sharedSongs = new ArrayList<>();

                for (Song song : songs) {
                    if (song.isSharable()) {
                        sharedSongs.add(song);
                    }
                }
                for (Song song : songs)
                    saveSong(song);
                showPanel.setSongs(sharedSongs);
                showPanel.revalidate();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        sharedPlaylistBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        add(sharedPlaylistBtn);


/**
 * Every user has some favourite songs which is shown by this button
 */
        favouriteBtn = new JTextField("   Favorites");
        favouriteBtn.setFont(new Font("San Francisco", Font.BOLD, 15));
        favouriteBtn.setEditable(false);
        favouriteBtn.setBackground(this.getBackground());
        favouriteBtn.setForeground(foreground);
        favouriteBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                favouriteBtn.setBackground(pressedBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                favouriteBtn.setBackground(getBackground());

                showPanel.removeAll();
                showPanel.repaint();
                favouriteSongs = null;
                favouriteSongs = new ArrayList<>();

                for (Song song : songs) {
                    if (song.isFavourite()) {
                        favouriteSongs.add(song);
                    }

                }
                for (Song song : songs) {
                    saveSong(song);
                }
                showPanel.setSongs(favouriteSongs);
                showPanel.revalidate();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        favouriteBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        add(favouriteBtn);


        setVisible(true);
    } // end of constructor


    /**
     * Adds a song to songs arraylist
     * and also adds song to related album
     *
     * @param path
     */
    public Song addSong(String path) {

        int exist = 0;
        song = new Song(path);

        for (Song song : songs) {
            if (song.getPath().equals(path))
                exist = 1;
        }
        if (exist == 0) {
            songs.add(song);
            addToAlbum(song);
        }

        return song;
    }

    /**
     * Adds a song to user's favourites songs
     *
     * @param
     */
    public void addFavourite(Song song) {
        favouriteSongs.add(song);
    }

    /**
     * this method add each song to it's album class
     *
     * @param song
     */
    public void addToAlbum(Song song) throws NullPointerException {


        int exist = 0;
        for (Album album : albums) {
            if (album.getAlbumName().equals(song.getAlbumName())) {
                album.addSong(song);
                exist = 1;
                break;
            }
        }
        if (exist == 0) {
            album = new Album(song.getAlbumName());
            albums.add(album);
            album.addSong(song);
        }

        playerPart.setAlbums(albums);


    }

    /**
     * this method save added song to the program(create a file for it and write it)
     *
     * @param
     */
    public void saveSong(Song song) {

        try {
            FileOutputStream f = new FileOutputStream(new File(username + "/songs/" + song.getTitle()));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(song);

            o.close();
            f.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * this method is for using library's showpanel on other classes
     *
     * @return
     */

    public void setShowPanel(ShowPanel showPanel) {
        this.showPanel = showPanel;
    }

    public ShowPanel getShowPanel() {
        return showPanel;
    }

    /**
     * this method reads and loads all users songs and albums and add them to the program
     *
     * @param username
     * @return
     * @throws IOException
     */
    public ArrayList<Song> loadSongs(String username) throws IOException {

        ArrayList<Song> loadedSongs = new ArrayList<Song>();
        boolean cont = true;

        try (Stream<Path> filePathStream = Files.walk(Paths.get(username + "/songs/"))) {
            filePathStream.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {

                    try {
                        FileInputStream fis = new FileInputStream(String.valueOf(filePath));
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        Object obj = null;

                        obj = ois.readObject();
                        loadedSongs.add((Song) obj);
                        addToAlbum((Song) obj);

                        for (String playlistNames : ((Song) obj).getPlaylists())
                            addPlaylistName(playlistNames);

                        if (((Song) obj).isFavourite() == true)
                            favouriteSongs.add((Song) obj);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }


            });
        }

        return loadedSongs;
    }


    /**
     * it returns hte library itself
     *
     * @return
     */
    LibraryPart getLibrarypartItself() {
        return this;
    }

    /**
     * it returns library playlist name only which is an arraylist of string
     *
     * @return
     */
    public ArrayList<String> getPlaylistName() {
        return this.playlistNames;
    }

    /**
     * adds a name to playlist names arraylist for library
     *
     * @param name
     */
    public void addPlaylistName(String name) {
        this.playlistNames.add(name);
    }

    /**
     * this method removes a song from library with its file
     *
     * @param path
     */
    public void removeSpecificSong(String path) {
        new File(path).delete();
    }

    public String getUsername() {
        return username;
    }

    /**
     * sets library songs
     *
     * @param songs
     */
    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;

    }

    /**
     * sets library albums
     *
     * @param albums
     */
    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
}
