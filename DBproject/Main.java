import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main
{
    private static Scanner sc = new Scanner(System.in);
    private static Random r = new Random();
    private static Statement stmt;
    private static String userID;

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //Class.forName("com.mariadb.jdbc.Driver").newInstance();
        //String url = "jdbc:mariadb://localhost:3306/music_app";
        String url = "jdbc:mariadb://127.0.0.1:3306/music_app";
        String username = "root";
        String password = "inuyasha00^^";
        Connection con = DriverManager.getConnection(url, username, password);
        stmt = con.createStatement();

        if(whoAreYou() == 1){
            menuForAdmin();
        } else{
            menuForMember();
        }

        System.out.println("Log out succeeded.");
    }

    public static int whoAreYou()
    {
        System.out.println("----------------------");
        System.out.println("     Who Are You?     ");
        System.out.println("----------------------");
        System.out.println("1. administrator");
        System.out.println("2. listener");
        System.out.println("3. not a member - sign up");
        System.out.println("----------------------");

        int cmd;
        input:
        while(true)
        {
            System.out.print(">>> ");
            cmd = sc.nextInt();

            switch (cmd)
            {
                case 1:
                case 2:
                    Login(cmd);
                    break input;
                case 3:
                    signUp();
                    break input;

                default:
                    System.out.println("Wrong command!");
            }
        }

        return cmd;
    }

    public static void Login(int who)
    {
        try {
            System.out.print("input your ID : ");
            String id = sc.next();
            System.out.print("input password : ");
            String password = sc.next();

            ResultSet rs;
            if(who == 1) {
                rs = stmt.executeQuery("SELECT adminIdx, password FROM administrator WHERE adminIdx='" + id
                        + "' AND password='" + password +"'");
            }else if(who == 2){
                rs =  stmt.executeQuery("SELECT ID, password FROM members WHERE ID='" + id
                        + "' AND password='" + password +"'");
            }else return;

            if(rs.next()) {
                userID = id;
                System.out.println("Login succeeded. Hello " + userID + "!");
            }
            else {
                System.out.println("Wrong information!");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void signUp()
    {
        while(true) {
            sc.nextLine();
            System.out.println("-----Sign up Page-----");
            System.out.print("Input name : ");
            String name = sc.nextLine();
            System.out.print("Input social security number : ");
            String ssn = sc.next();
            System.out.print("Input your age : ");
            int age = sc.nextInt();
            System.out.print("Input your sex : ");
            char sex = sc.next().charAt(0);
            System.out.print("Input ID : ");
            String id = sc.next();
            System.out.print("Input password : ");
            String pw = sc.next();
            sc.nextLine();
            System.out.print("Input comment : ");
            String comment = sc.nextLine();

            try {
                if (ssn.length() == 13) {
                    int memberIdx = r.nextInt(89999999) + 10000000;
                    String memIdx = Integer.toString(memberIdx);

                    int result = stmt.executeUpdate(String.format("INSERT INTO members" +
                                    "(name, ssn, age, ID, comment, password, memberIdx, sex) " +
                                    "VALUES ('%s', '%s', %d, '%s', '%s', '%s', '%s', '%c')",
                            name, ssn, age, id, comment, pw, memIdx, sex));

                    if (result != 0) {
                        userID = id;
                        System.out.println("Welcome " + userID + "!");
                        break;
                    }
                    else System.out.println("duplicate ID or already a member");

                } else {
                    System.out.println("Wrong ssn!\n");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void menuForAdmin() throws SQLException
    {
        while(true) {
            System.out.println("----------------------");
            System.out.println("    Menu For Admin    ");
            System.out.println("----------------------");
            System.out.println("1. add artist");
            System.out.println("2. add album");
            System.out.println("3. delete album");
            System.out.println("4. user list");
            System.out.println("5. delete user");
            System.out.println("0. quit");
            System.out.println("----------------------");
            System.out.print(">>> ");

            int cmd = sc.nextInt();
            switch (cmd) {
                case 1:
                    addArtist();
                    break;
                case 2:
                    addAlbum();
                    break;
                case 3:
                    deleteAlbum();
                    break;
                case 4:
                    userList();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    public static void addArtist() throws SQLException
    {
        System.out.print("Insert the artist's name : ");

        String name = sc.next();
        int Idx = r.nextInt(89999999) + 10000000;
        String idx = Integer.toString(Idx);

        stmt.executeUpdate(String.format("INSERT INTO artist VALUES ('%s', '%s')", name, idx));

        System.out.print("Does he/she belong to a group? (y or n) : ");
        char c = sc.next().charAt(0);
        if(c=='y'){
            sc.nextLine();
            System.out.print("Group name : ");
            String groupname = sc.nextLine();
            ResultSet rs = stmt.executeQuery("SELECT artistIdx FROM artist WHERE name='" + groupname + "'");
            String groupIdx;
            if(rs.next()){ // the group exists
             groupIdx = rs.getString(1);
             stmt.executeUpdate(String.format("INSERT INTO memberof VALUES ('%s', '%s')", groupIdx, idx));
            }
            else{ // no such group
                System.out.println("No such group.");
            }
        }

        System.out.println("addArtist() done.");
    }

    public static void addAlbum() throws SQLException
    {
        sc.nextLine();
        //required album information
        System.out.print("Insert the album's name : ");
        String name = sc.nextLine(); //album name
        System.out.print("Insert artist : ");
        String artist = sc.nextLine(); //artist

        int Idx = r.nextInt(89999999) + 10000000;
        String idx = Integer.toString(Idx); //albumIdx

        ResultSet rs = stmt.executeQuery("SELECT artistIdx FROM artist WHERE name='" + artist + "'");
        rs.next();
        String artistIdx = rs.getString(1); //artistNum

        //additional album information
        System.out.print("Do you want to insert additional info? (y or n) : ");
        char c = sc.next().charAt(0);
        if(c == 'y'){
            sc.nextLine();
            System.out.print("Insert publisher : ");
            String publisher = sc.nextLine();
            System.out.print("Insert release date : ");
            String reldate = sc.next();
            stmt.executeUpdate(String.format("INSERT INTO album " +
                            "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                    name, publisher, idx, reldate, artistIdx, userID));
        }
        else{
            stmt.executeUpdate(String.format("INSERT INTO album(name,albumIdx,artistNum) " +
                            "VALUES ('%s', '%s', '%s')", name, idx, artistIdx));
        }
        System.out.println("New album added.");
        addSong(idx);
    }

    public static void addSong(String albumIdx) throws SQLException
    {
        System.out.print("The number of songs in this album : ");
        int N = sc.nextInt();
        sc.nextLine();
        for(int i = 1; i<=N; i++){
            //required song information
            System.out.print("Insert song title " + i + " : ");
            String name = sc.nextLine(); //name
            int Idx = r.nextInt(89999999) + 10000000; //songIdx
            String idx = Integer.toString(Idx);

            //additional song information
            System.out.print("Do you want to insert additional info? (y or n) : ");
            char c = sc.next().charAt(0);
            if(c == 'y'){
                System.out.print("Insert age limit : ");
                int ageLimit = sc.nextInt();;
                sc.nextLine();
                System.out.print("lyrics : ");
                String lyrics = sc.nextLine();
                System.out.print("song writer : ");
                String writer = sc.nextLine();
                System.out.print("Number of genre : ");
                int n = sc.nextInt();
                sc.nextLine();
                ArrayList<String> genres = new ArrayList<>();
                for(int j = 1; j<=n; j++){
                    System.out.print("Input genre " + j + " : ");
                    String genre = sc.nextLine();
                    genres.add(genre);
                }
                stmt.executeUpdate(String.format("INSERT INTO song VALUES ('%s', '%s', %d, '%s', '%s', '%s')",
                        name, lyrics, ageLimit, idx, writer, albumIdx));
                for(String g : genres) {
                    stmt.executeUpdate("INSERT INTO genre VALUES ('" + g + "', '" + idx + "')");
                }
            }
            else{
                stmt.executeUpdate(String.format("INSERT INTO song(name, songIdx, albumNum) VALUES ('%s', '%s', '%s')",
                        name, idx, albumIdx));
                sc.nextLine();
            }
        }

        System.out.println("addSong() done.");
    }

    public static void deleteAlbum() throws SQLException
    {
        sc.nextLine();
        System.out.print("Insert album name : ");
        String album = sc.nextLine();
        System.out.print("Insert the artist : ");
        String artist = sc.nextLine();

        //check permission and delete album
        ResultSet rs = stmt.executeQuery(String.format("SELECT albumIdx, admNum " +
                "FROM album JOIN artist ON artistNum=artistIdx " +
                "WHERE album.name='%s' AND artist.name='%s'", album, artist));
        rs.last();
        if(rs.getRow() == 1){ //if we found the album
            rs.first();
            if(rs.getString("admNum").equals(userID)){ //check the permission
                stmt.executeUpdate(String.format("DELETE FROM album WHERE albumIdx='%s'", rs.getString("albumIdx")));
                System.out.println("Delete album " + album + " succeeded.");
            }else{ //no permission
                System.out.println("You have no permission to delete this album.");
            }
        }else{ //no such album
            System.out.println("There is no such album.");
        }
    }

    public static void userList() throws SQLException
    {
        ResultSet users = stmt.executeQuery("SELECT ID, memberIdx " +
                "FROM members WHERE admNum = '" + userID + "'");
        System.out.println("----------------------");
        while(users.next()){
            System.out.println(users.getString(1));
        }
        System.out.println("----------------------");
    }

    public static void deleteUser() throws SQLException
    {
        System.out.print("Insert the user ID : ");
        String user = sc.next();

        //check permission and delete user
        ResultSet rs = stmt.executeQuery("SELECT admNum FROM members WHERE ID='" + user + "'");

        if(rs.next()){ //if the user exists
            if(rs.getString("admNum").equals(userID)){ //check the permission
                stmt.executeUpdate("DELETE FROM members WHERE ID='" + user + "'");
                System.out.println("Delete user " + user + " succeeded.");
            }else{ //no permission
                System.out.println("You have no permission to delete this user.");
            }
        }else{ // no such user
            System.out.println("No such user.");
        }

        System.out.println("----------------------");
    }

    public static void menuForMember() throws SQLException
    {
        while(true) {
            System.out.println("----------------------");
            System.out.println("    Menu For Member   ");
            System.out.println("----------------------");
            System.out.println("1. search song");
            System.out.println("2. search album");
            System.out.println("3. search artist");
            System.out.println("4. create playlist");
            System.out.println("5. see my playlists");
            System.out.println("6. play playlist");
            System.out.println("7. search other users");
            System.out.println("8. see my bookmarks");
            System.out.println("9. statistics");
            System.out.println("0. quit");
            System.out.println("----------------------");
            System.out.print(">>> ");


            int cmd = sc.nextInt();
            switch (cmd) {
                case 1:
                    searchSong();
                    break;
                case 2:
                    searchAlbum();
                    break;
                case 3:
                    searchArtist();
                    break;
                case 4:
                    createPlaylist();
                    break;
                case 5:
                    seeMyPlaylists();
                    break;
                case 6:
                    playPlaylist();
                    break;
                case 7:
                    searchMember();
                    break;
                case 8:
                    seeMyBookmarks();
                    break;
                case 9:
                    statistics();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Wrong command!");
                }
        }
    }

    public static void searchSong() throws SQLException
    {
        System.out.println("----------------------");
        System.out.println("      Search Song     ");
        System.out.println("----------------------");
        System.out.println("1. song ");
        System.out.println("2. song + artist");
        System.out.println("----------------------");
        System.out.print(">>> ");

        ResultSet rs;
        int cmd = sc.nextInt();
        if(cmd == 1)
        {
            sc.nextLine();
            System.out.print("Input the song title : ");
            String song = sc.nextLine();

            rs = stmt.executeQuery("SELECT songName, albumName, artistName " +
                    "FROM artist_album_songs " +
                    "WHERE songName LIKE '%" + song + "%'");
        }
        else if (cmd == 2)
        {
            sc.nextLine();
            System.out.print("Input the song title : ");
            String song = sc.nextLine();
            System.out.print("Input the artist : ");
            String artist = sc.nextLine();

            rs = stmt.executeQuery("SELECT songName, albumName, artistName " +
                    "FROM artist_album_songs " +
                    "WHERE  songName LIKE '%" + song + "%' AND artistName LIKE '%" + artist + "%'");
        }
        else{
            System.out.println("Wrong command.");
            return;
        }

        // print search result
        System.out.println("----------------------");
        if(rs == null){
            System.out.println("No such song.");
            System.out.println("----------------------");
            return;
        }
        while(rs.next()){
            System.out.println("song : " + rs.getString(1) +
                    " / album : " + rs.getString(2) + " / artist : " + rs.getString(3));
        }
        System.out.println("----------------------");

        //additional menu
        System.out.println("----------------------");
        System.out.println("   additional menu    ");
        System.out.println("----------------------");
        System.out.println("1. listen to the song ");
        System.out.println("2. see the song info  ");
        System.out.println("0. exit");
        System.out.println("----------------------");
        System.out.print(">>> ");

        cmd = sc.nextInt();
        if(cmd == 1 || cmd == 2){
            sc.nextLine();
            System.out.print("Input the song title : ");
            String song = sc.nextLine();
            System.out.print("Input the artist : ");
            String artist = sc.nextLine();

            //get songIdx
            rs = stmt.executeQuery("SELECT songIdx " +
                    "FROM artist_album_songs " +
                    "WHERE songName = '" + song + "' AND artistName = '" + artist + "'");
            rs.next();
            String songIdx = rs.getString(1);

            if(cmd == 1) {
                // listen
                System.out.println("----------------------");
                playSong(songIdx);
                System.out.println("----------------------");
            }else {
                // see info
                System.out.println("----------------------");
                songInfo(songIdx);
                System.out.println("----------------------");
            }
        }
    }

    public static void playSong(String songidx) throws SQLException
    {   // who listen to music?
        ResultSet memberidx = stmt.executeQuery("SELECT memberIdx FROM members WHERE ID='" + userID + "'");
        ResultSet songname = stmt.executeQuery("SELECT name FROM song WHERE songIdx='"+songidx+"'");
        songname.next();
        memberidx.next();
        String memidx = memberidx.getString(1);

        //check ageLimit
        ResultSet ageLimit = stmt.executeQuery("SELECT ageLimit FROM song WHERE songIdx='" + songidx + "'");
        ResultSet userage = stmt.executeQuery("SELECT age FROM members WHERE ID='" + userID + "'");
        ageLimit.next();
        userage.next();
        if(ageLimit.getInt(1) > userage.getInt(1)){
            System.out.println("You cannot play " + songname.getString(1) + " until you are 19.");
            return;
        }

        // get play record
        ResultSet play = stmt.executeQuery("SELECT playtime " +
                "FROM playsong " +
                "WHERE listenerNum='" + memidx + "' AND songNum='" + songidx + "'");

        if(!play.next()){ //first play
            stmt.executeUpdate(String.format("INSERT INTO playsong " +
                    "VALUES('%s', '%s', 1)", memidx, songidx));
            System.out.println("Listening : " + songname.getString(1) + " for the first time.");
        } else{ //second play
            stmt.executeUpdate("UPDATE playsong SET playtime = playtime + 1 " +
                    "WHERE listenerNum='" + memidx + "' AND songNum='" + songidx + "'");
            System.out.println("Listening : " + songname.getString(1) +
                    " for the " + (play.getInt("playtime")+1) + "th time.");
        }
    }

    public static void songInfo(String songidx) throws SQLException
    {
        ResultSet song = stmt.executeQuery("SELECT *" +
                "FROM song, album, artist " +
                "WHERE albumNum=albumIdx AND artistNum=artistIdx AND songIdx='"+songidx + "'");
        ResultSet genre = stmt.executeQuery("SELECT * FROM genre, song WHERE songIdx=songNum");

        while(song.next())
        {
            String title = song.getString("name");
            String lyrics = song.getString("lyrics");
            int agelimit = song.getInt("ageLimit");
            String writer = song.getString("songwriter");
            String album = song.getString("album.name");
            String artist = song.getString("artist.name");


            System.out.println("Info : " + title);
            System.out.println("----------------------");
            System.out.println("Belonging album : " + album);
            System.out.println("Sung by : " + artist);
            System.out.println("ageLimit : " + agelimit);
            if(genre != null){
                System.out.print("genre : ");
                while(genre.next()){
                    System.out.print(genre.getString(1) + " ");
                }
                System.out.println();
            }
            if(writer != null) {
                System.out.println("[song writer info]");
                System.out.println(writer);
            }
            if(lyrics != null) {
                System.out.println("[lyrics]");
                System.out.println(lyrics);
            }
            System.out.println("----------------------");
        }
    }

    public static void searchAlbum() throws SQLException
    {
        System.out.println("----------------------");
        System.out.println("     Search Album     ");
        System.out.println("----------------------");
        System.out.println("1. album ");
        System.out.println("2. album + artist");
        System.out.println("----------------------");
        System.out.print(">>> ");

        ResultSet albums;
        int cmd = sc.nextInt();
        if(cmd == 1)
        {
            sc.nextLine();
            System.out.print("Input the album title : ");
            String albumname = sc.nextLine();

            albums = stmt.executeQuery("SELECT song.name, album.name, artist.name " +
                    "FROM song, album, artist " +
                    "WHERE albumNum=albumIdx AND artistNum=artistIdx AND album.name LIKE '%" + albumname + "%' " +
                    "ORDER BY album.name");
        }
        else if (cmd == 2)
        {
            sc.nextLine();
            System.out.print("Input the album title : ");
            String albumname = sc.nextLine();
            System.out.print("Input the artist : ");
            String artist = sc.nextLine();

            albums = stmt.executeQuery("SELECT songName, albumName, artistName " +
                    "FROM artist_album_songs " +
                    "WHERE albumName LIKE '%" + albumname + "%' AND artistName LIKE '%" + artist + "%' " +
                    "ORDER BY albumName, artistName");
        }
        else{
            System.out.println("Wrong command.");
            return;
        }

        // print search result
        System.out.println("----------------------");
        if(albums == null){
            System.out.println("No such song.");
            System.out.println("----------------------");
            return;
        }
        String albumname = null;
        String artist = null;
        while(albums.next()){
            if(!albums.getString("album.name").equals(albumname) && !albums.getString("artist.name").equals(artist))
            {
                albumname = albums.getString("album.name");
                artist = albums.getString("artist.name");
                System.out.println("----------------------");
                System.out.println(albumname + " sung by " + artist);
                System.out.println("----------------------");
            }
            System.out.println(albums.getString("song.name"));
        }
        System.out.println("----------------------");

        //additional menu
        System.out.println("----------------------");
        System.out.println("   additional menu    ");
        System.out.println("----------------------");
        System.out.println("1. listen to this album ");
        System.out.println("2. see the album info");
        System.out.println("0. exit");
        System.out.println("----------------------");
        System.out.print(">>> ");

        cmd = sc.nextInt();
        if(cmd == 1 || cmd == 2){
            sc.nextLine();
            System.out.print("Input the album title : ");
            albumname = sc.nextLine();
            System.out.print("Input the artist : ");
            artist = sc.nextLine();

            ResultSet album = stmt.executeQuery("SELECT albumIdx " +
                    "FROM artist_album_songs " +
                    "WHERE  albumName = '" + albumname + "' AND artistName = '" + artist + "'");

            String albumIdx;
            if(album.next())
                albumIdx = album.getString(1);
            else{
                System.out.println("No such album");
                return;
            }

            if(cmd == 1) {
                // listen
                System.out.println("----------------------");
                playAlbum(albumIdx);
                System.out.println("----------------------");
            }else {
                // see info
                System.out.println("----------------------");
                albumInfo(albumIdx);
                System.out.println("----------------------");
            }
        }
    }

    public static void playAlbum(String albumidx) throws SQLException
    {
        ResultSet songs = stmt.executeQuery("SELECT songIdx FROM song, album " +
                "WHERE albumIdx = albumNum AND albumIdx='" + albumidx + "'");
        while(songs.next()){
            playSong(songs.getString(1));
        }
        System.out.println("----------------------");
    }

    public static void albumInfo(String albumidx) throws SQLException
    {
        ResultSet album = stmt.executeQuery("SELECT * " +
                "FROM artist_album_songs " +
                "WHERE albumIdx='" + albumidx + "'");
        ResultSet count = stmt.executeQuery("SELECT count(*) " +
                "FROM artist_album_songs " +
                "WHERE albumIdx = '" + albumidx + "'");
        count.next();
        album.next();
        System.out.println(album.getString("album.name") + " sung by " + album.getString("artist.name")
                + " (" + album.getString("album.releaseDate") + ")"); //album, artist, release
        System.out.println("published by " + album.getString("album.publisher")); //publisher
        if(count.getInt("count(*)") >= 10)
            System.out.println("Classification : original album");
        else if(count.getInt("count(*)") > 3)
            System.out.println("Classification : EP");
        else System.out.println("Classification : single"); //classification
        System.out.println("----------------------");

        album.beforeFirst();
        while(album.next()){
            System.out.println(album.getString("song.name"));
        }
        System.out.println("----------------------");
    }

    public static void searchArtist() throws SQLException
    {
        sc.nextLine();
        System.out.println("----------------------");
        System.out.print("Input artist : ");
        String artistname = sc.nextLine();

        ResultSet artists = stmt.executeQuery("SELECT * FROM artist JOIN album ON artistNum=artistIdx " +
                "WHERE artist.name LIKE '%" + artistname + "%' ORDER BY artist.name");

        artistname = null;
        while(artists.next()){
            if(!artists.getString("artist.name").equals(artistname))
            {
                artistname = artists.getString("artist.name");
                System.out.println("----------------------");
                System.out.println(" < "+ artistname + " > ");
                System.out.println("----------------------");
            }
            System.out.println("album : " + artists.getString("album.name"));
        }
        System.out.println("----------------------");

        //additional menu
        System.out.println("----------------------");
        System.out.println("   additional menu    ");
        System.out.println("----------------------");
        System.out.println("1. see group members");
        System.out.println("2. listen to his/her album ");
        System.out.println("3. see his/her album info");
        System.out.println("0. exit");
        System.out.println("----------------------");
        System.out.print(">>> ");

        int cmd = sc.nextInt();
        if(cmd == 2 || cmd == 3)
        {
            sc.nextLine();
            System.out.print("Input the album title : ");
            String albumname = sc.nextLine();
            System.out.print("Input the artist : ");
            String artist = sc.nextLine();

            ResultSet album = stmt.executeQuery("SELECT albumIdx " +
                    "FROM artist_album_songs " +
                    "WHERE songName = '" + albumname + "' AND artistName = '" + artist + "'");

            String albumIdx;
            if(album.next())
                albumIdx = album.getString(1);
            else{
                System.out.println("No such album or artist.");
                return;
            }

            if(cmd == 2) {
                // listen
                System.out.println("----------------------");
                playAlbum(albumIdx);
                System.out.println("----------------------");
            }else {
                // see info
                System.out.println("----------------------");
                albumInfo(albumIdx);
                System.out.println("----------------------");
            }
        }else if(cmd == 1)
        {
            System.out.println("----------------------");
            memOfGroup();
            System.out.println("----------------------");
        }
    }

    public static void memOfGroup() throws SQLException
    {
        sc.nextLine();
        System.out.print("Input artist : ");
        String artistname = sc.nextLine();

        ResultSet members = stmt.executeQuery("SELECT g.name, m.name " +
                "FROM artist AS g, artist AS m, memberof " +
                "WHERE g.artistIdx = memberof.groupNum AND m.artistIdx = memberof.memberNum AND g.name='" + artistname + "'");

        System.out.println("Members of Group <" + artistname + ">");
        System.out.println("----------------------");
        while(members.next()){
            System.out.println(members.getString("m.name"));
        }
    }

    public static void createPlaylist() throws SQLException
    {
        sc.nextLine();
        // create playlist
        // playlist name
        System.out.println("----------------------");
        System.out.print("Input name of a new playlist : ");
        String plName = sc.nextLine();
        // public or private
        System.out.print("public or private : ");
        String tmp = sc.next();
        int Public;
        if(tmp.equals("public")) Public = 1;
        else Public = 0;
        // creatorIdx
        ResultSet userIdx = stmt.executeQuery("SELECT memberIdx FROM members WHERE ID='" + userID + "'");
        userIdx.next();
        String createrIdx = userIdx.getString(1);

        //make a new playlist
        if(stmt.executeUpdate(String.format("INSERT INTO playlist VALUES('%s', %d, '%s')",
                plName, Public, createrIdx)) == 0){
            System.out.println(plName + " is already exist.");
            return;
        }

        //input songs to the new playlist
        System.out.print("How many songs will you put into the playlist? : ");
        int n = sc.nextInt();
        sc.nextLine();
        for(int i = 1; i<=n; i++)
        {
            System.out.print("Input the song title "+ i + " : ");
            String song = sc.nextLine();
            System.out.print("Input the artist : ");
            String artist = sc.nextLine();

            ResultSet songs = stmt.executeQuery("SELECT songIdx " +
                    "FROM song, album, artist " +
                    "WHERE albumNum=albumIdx AND artistNum=artistIdx " +
                    "AND song.name = '" + song + "' AND artist.name = '" + artist + "'");

            if(!songs.next()){
                System.out.println("No such song. Try again.");
                n++; continue;
            }

            stmt.executeUpdate(String.format("INSERT INTO included VALUES('%s', '%s', '%s')",
                    songs.getString(1), createrIdx, plName));
        }
    }

    public static void playPlaylist() throws SQLException
    {
        sc.nextLine();
        System.out.print("Input playlist name : ");
        String plName = sc.nextLine();
        System.out.print("Input creator ID : ");
        String creatorID = sc.nextLine();

        // userIdx
        ResultSet userIdx = stmt.executeQuery("SELECT memberIdx FROM members WHERE ID='" + creatorID + "'");
        userIdx.next();
        String useridx = userIdx.getString(1);

        // play my playlist or other's public playlist
        ResultSet songs;
        if(creatorID.equals(userID)){
            songs = stmt.executeQuery("SELECT i.songNum FROM included AS i " +
                    "WHERE PLName='" + plName + "' AND creatorNum='" + useridx + "'");
        }else{
            songs = stmt.executeQuery("SELECT i.songNum FROM included AS i, playlist AS p " +
                    "WHERE p.name=i.PLName AND p.creatorNum=i.creatorNum AND p.public=1 AND p.name='" + plName + "'");
        }

        if(!songs.next()){
            System.out.println("No such playlist or there is no song to play.");
            return;
        }
        songs.beforeFirst();
        while(songs.next()){
            playSong(songs.getString(1));
        }
    }

    public static void seeMyPlaylists() throws SQLException
    {
        // userIdx
        ResultSet userIdx = stmt.executeQuery("SELECT memberIdx FROM members WHERE ID='" + userID + "'");
        userIdx.next();
        String useridx = userIdx.getString(1);
        // playlist list
        ResultSet pls = stmt.executeQuery("SELECT name FROM playlist WHERE creatorNum='" + useridx + "'");

        System.out.println("----------------------");
        while(pls.next())
        {
            System.out.println(pls.getString(1));
            System.out.println("----------------------");

            ResultSet songs = stmt.executeQuery("SELECT song.name, songIdx FROM included, song " +
                    "WHERE songIdx = songNum AND PLname = '" + pls.getString(1) + "'");

            while(songs.next())
            {
                ResultSet artist = stmt.executeQuery("SELECT artistName FROM artist_album_songs " +
                        "WHERE songIdx='" + songs.getString(2) + "'");
                artist.next();
                System.out.println(songs.getString("song.name") + " - " + artist.getString(1));
            }
            System.out.println("----------------------");
        }
        System.out.println("----------------------");
    }

    public static void searchMember() throws SQLException
    {
        System.out.print("Input member ID for search : ");
        String otherID = sc.next();
        ResultSet other = stmt.executeQuery("SELECT p.name, m.memberIdx , m.comment " +
                "FROM members m JOIN playlist p ON m.memberIdx=p.creatorNum " +
                "WHERE m.ID='" + otherID + "' AND public = 1");
        other.next();

        // other user info
        System.out.println("----------------------");
        System.out.println("   ID   : " + otherID);
        System.out.println("comment : " + other.getString("m.comment"));
        System.out.println("[open playlists]");

        do{ System.out.println(other.getString(1));
        }while(other.next());

        System.out.println("----------------------");

        // additional menu
        System.out.println("----------------------");
        System.out.println("   additional menu    ");
        System.out.println("----------------------");
        System.out.println("1. bookmark this user ");
        System.out.println("2. play his/her playlist");
        System.out.println("0. exit");
        System.out.println("----------------------");
        System.out.print(">>> ");

        int cmd = sc.nextInt();
        if(cmd == 1)
        {
            other.first();
            String otherIdx = other.getString("m.memberIdx");
            ResultSet rs = stmt.executeQuery("SELECT memberIdx FROM members WHERE ID='" + userID + "'");
            rs.first();
            String userIdx = rs.getString("memberIdx");

            stmt.executeUpdate(String.format("INSERT INTO bookmark VALUES('%s', '%s')", otherIdx, userIdx));
            System.out.println("Bookmark done.");
            System.out.println("----------------------");
        }
        else if(cmd == 2)
        {
            playPlaylist();
        }
    }

    public static void seeMyBookmarks() throws SQLException
    {
        ResultSet myfollower = stmt.executeQuery("SELECT theyID " +
                "FROM follow WHERE themID='" + userID + "'");
        ResultSet Ifollow = stmt.executeQuery("SELECT themID " +
                "FROM follow WHERE theyID='" + userID + "'");

        System.out.println("----------------------");
        System.out.println("     my following     ");
        System.out.println("----------------------");
        while(Ifollow.next()){
            System.out.println(Ifollow.getString(1));
        }

        System.out.println("----------------------");
        System.out.println("     my followers     ");
        System.out.println("----------------------");
        while(myfollower.next()){
            System.out.println(myfollower.getString(1));
        }
        System.out.println("----------------------");
    }

    public static void statistics() throws SQLException
    {
        System.out.println("----------------------");
        System.out.println("      statistics      ");
        System.out.println("----------------------");
        System.out.println("1. total ranking");
        System.out.println("2. ranking by category");
        System.out.println("----------------------");
        System.out.print(">>> ");

        int cmd = sc.nextInt();
        if(cmd==1)
        {
            System.out.println("----------------------");
            System.out.println("   total ranking");
            System.out.println("----------------------");

            ResultSet rank = stmt.executeQuery("SELECT songNum " +
                    "FROM playsong GROUP BY songNum ORDER BY SUM(playtime) DESC");
            while(rank.next()){
                ResultSet song = stmt.executeQuery("SELECT artistName, albumName, songName " +
                        "FROM artist_album_songs " +
                        "WHERE songIdx='" + rank.getString("songNum") + "'");
                song.next();
                String artist = song.getString(1);
                String album = song.getString(2);
                String songName = song.getString(3);

                // print ranking
                System.out.println(rank.getRow() + ") " + songName + " in " + album + " sung by " + artist);
            }
            System.out.println("----------------------");
        }
        else if(cmd==2)
        {
            System.out.println("----------------------");
            System.out.println("       category ");
            System.out.println("----------------------");
            System.out.println("1. by age");
            System.out.println("2. by gender");
            System.out.println("3. by age & gender");
            System.out.println("----------------------");
            System.out.print(">>> ");
            cmd = sc.nextInt();

            if(cmd==1){
                System.out.println("----------------------");
                System.out.println("    ranking by age    ");
                System.out.println("----------------------");

                System.out.print("Input age (ex 20) : ");
                int age = sc.nextInt();

                ResultSet rank = stmt.executeQuery("SELECT songNum " +
                        "FROM playsong JOIN members ON listenerNum=memberIdx " +
                        "WHERE (age - age % 10) = " + (age-age%10) +
                        " GROUP BY songNum ORDER BY sum(playtime) DESC");

                while(rank.next()){
                    ResultSet song = stmt.executeQuery("SELECT artistName, albumName, songName " +
                            "FROM artist_album_songs " +
                            "WHERE songIdx='" + rank.getString("songNum") + "'");
                    song.next();
                    String artist = song.getString(1);
                    String album = song.getString(2);
                    String songName = song.getString(3);

                    // print ranking
                    System.out.println(rank.getRow() + ") " + songName + " in " + album + " sung by " + artist);
                }
                System.out.println("----------------------");
            }
            else if(cmd == 2)
            {
                System.out.println("----------------------");
                System.out.println("   ranking by gender  ");
                System.out.println("----------------------");

                System.out.print("Input gender (M/F) : ");
                char sex = sc.next().charAt(0);

                ResultSet rank = stmt.executeQuery("SELECT songNum " +
                        "FROM playsong JOIN members ON listenerNum=memberIdx " +
                        "WHERE sex = '" + sex +
                        "' GROUP BY songNum ORDER BY sum(playtime) DESC");

                while(rank.next()){
                    ResultSet song = stmt.executeQuery("SELECT artistName, albumName, songName " +
                            "FROM artist_album_songs " +
                            "WHERE songIdx='" + rank.getString("songNum") + "'");
                    song.next();
                    String artist = song.getString(1);
                    String album = song.getString(2);
                    String songName = song.getString(3);

                    // print ranking
                    System.out.println(rank.getRow() + ") " + songName + " in " + album + " sung by " + artist);
                }
                System.out.println("----------------------");
            }
            else if(cmd==3)
            {
                System.out.println("----------------------");
                System.out.println("ranking by age & gender");
                System.out.println("----------------------");

                System.out.print("Input age (ex 20) : ");
                int age = sc.nextInt();
                System.out.print("Input gender (M/F) : ");
                char sex = sc.next().charAt(0);

                ResultSet rank = stmt.executeQuery("SELECT songNum " +
                        "FROM playsong JOIN members ON listenerNum=memberIdx " +
                        "WHERE sex = '" + sex + "' AND (age - age % 10) = " + (age-age%10) +
                        " GROUP BY songNum ORDER BY sum(playtime) DESC");

                while(rank.next()){
                    ResultSet song = stmt.executeQuery("SELECT artistName, albumName, songName " +
                            "FROM artist_album_songs " +
                            "WHERE songIdx='" + rank.getString("songNum") + "'");
                    song.next();
                    String artist = song.getString(1);
                    String album = song.getString(2);
                    String songName = song.getString(3);

                    // print ranking
                    System.out.println(rank.getRow() + ") " + songName + " in " + album + " sung by " + artist);
                }
                System.out.println("----------------------");
            }
            else{
                System.out.println("Wrong command.");
            }
        }else{
            System.out.println("Wrong command.");
        }
    }
}
