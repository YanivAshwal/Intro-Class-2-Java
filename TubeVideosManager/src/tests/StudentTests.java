package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import tubeVideosManager.Genre;
import tubeVideosManager.Playlist;
import tubeVideosManager.TubeVideosManager;
import tubeVideosManager.Video;

/**
 * 
 * You need student tests if you are asking for help during
 * office hours about bugs in your code. Feel free to use
 * tools available in TestingSupport.java
 * 
 * @author UMCP CS Department
 *
 */
public class StudentTests {
	
	private Playlist playlist;
	private Video video;
	private ArrayList<Video> videoDatabase;
	private ArrayList<Playlist> playlists;
	private TubeVideosManager videosManager;
	
	@Before
	public void setUp() {
		playlist = new Playlist("Shana Forever");
		video = new Video("Pashut - Zusha", "www.Zusha.com",3,Genre.Music);
		videoDatabase = new ArrayList<Video>();
		playlists = new ArrayList<Playlist>();
		videosManager = new TubeVideosManager();
	}
	
	@After
	public void tearDown() { 
		playlist = null;
		video = null;
		videoDatabase = null;
		playlists = null; 
		videosManager = null;
	}
	
	//PLAYLIST CLASS TESTS
	@Test
	public void playlistDefConstructorTest() { //Playlist(Name) constructor
		Playlist playlist = new Playlist("Shana Forever");
		playlist.addToPlaylist("Pashut");
		String actual = playlist.toString();
		
		String expected = "Playlist Name: Shana Forever\n"
				+ "VideoTitles: [Pashut]";

		assertEquals(actual, expected);	
	}
	
	@Test
	public void playlistTypeConstructorTest() { //Playlist(Playlist) constructor
		Playlist playlist = new Playlist("Circles - Mac Miller");
		playlist.addToPlaylist("Circles");
		playlist.addToPlaylist("Complicated"); 
		
		Playlist actualPlaylist = new Playlist(playlist); 
		String actual = actualPlaylist.toString();
		String expected = "Playlist Name: Circles - Mac Miller\n"
				+ "VideoTitles: [Circles, Complicated]";
		assertEquals(actual, expected);
		
	}
	
	@Test
	public void addToPlayListTest() { //tests .addToPlaylist() method
		playlist.addToPlaylist("Pashut");
		String actual = playlist.getPlaylistVideosTitles().toString();
		String expected = "[Pashut]";
		assertEquals(actual, expected);
	}
	
	@Test
	public void removeFromPlaylistTest() { //Tests .removeFromPlaylist() method
		playlist.addToPlaylist("Pashut"); 
		playlist.addToPlaylist("Brighter");
		
		playlist.removeFromPlaylistAll("Pashut");
		
		String actual = playlist.getPlaylistVideosTitles().toString();
		String expected = "[Brighter]";
		
		assertEquals(actual, expected);
	}
	
	@Test  							  // NEEDS REFINEMENT
	public void shuffleVideosTest() { //Tests shuffleVideoTitle() method
		playlist.addToPlaylist("Pashut");
		playlist.addToPlaylist("Brighter");
		playlist.addToPlaylist("Shining Faces"); 
		
		Random random = new Random();
		Playlist original = playlist;
		playlist.shuffleVideoTitles(random);
		
		assertTrue(playlist.getPlaylistVideosTitles().size() == 3);
		assertTrue(playlist.getPlaylistVideosTitles()
				.containsAll(original.getPlaylistVideosTitles()));
	}
	
	//VIDEO CLASS TESTS
	@Test
	public void videoConstructorTest() { //tests Video def constructor
		Video video = new Video("Pashut - Zusha","www.Zusha.com",3,Genre.Music);
		String actual = video.toString();
		String expected = "Title: \"Pashut - Zusha\"\nUrl: www.Zusha.com\n"
				+ "Duration (minutes): 3\nGenre: Music\n";
		assertEquals(actual, expected);
	}
	
	@Test
	public void videoTypeContructorTest() { 
		Video video = new Video("Pashut - Zusha", "www.Zusha.com",3,Genre.Music);
		Video actualVideo = new Video(video); 
		
		String actual = actualVideo.toString();
		String expected = "Title: \"Pashut - Zusha\"\nUrl: www.Zusha.com\n"
				+ "Duration (minutes): 3\nGenre: Music\n";
		assertEquals(actual, expected);
	}
	
	@Test
	public void addCommentsTest() { 
		video.addComments("Great Song!"); 
		video.addComments("100/10!!!");
		
		String actual = video.getComments().toString();
		String expected = "[Great Song!, 100/10!!!]";
		assertEquals(actual, expected);
	}
	
	@Test
	public void compareToTest() {
		Video video1 = new Video("Come Back - Moshav","WWW.Moshav.com",4,
				Genre.Music);
		Video vidCopy = new Video(video);
		
		assertTrue(video.compareTo(video1) != 0);
		assertTrue(video.compareTo(vidCopy) == 0);
	}
	
	@Test
	public void equalsTest() { 
		Video video1 = new Video("Come Back - Moshav","WWW.Moshav.com",4,
				Genre.Music);
		Video vidCopy = new Video(video);
		
		assertTrue(!video.equals(video1));
		assertTrue(video.equals(vidCopy));
	}
	
	//TUBE VIDEOS MANAGER TESTS
	@Test
	public void addVidToDBTest() {
		assertTrue(playlists.add(playlist));
	}
	
	@Test
	public void getVidsinDBTest() {
		videosManager.addVideoToDB("Pashut - Zusha", "www.Zusha.com", 3, Genre.Music);
		ArrayList<Video> actual = videosManager.getAllVideosInDB();
		
		ArrayList<Video> expected = new ArrayList<Video>();
		expected.add(video);
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void managerAddCommentsTest() { 
		videosManager.addVideoToDB("Pashut - Zusha", "www.Zusha.com", 3, Genre.Music);
		assertTrue(videosManager.addComments("Pashut - Zusha", "Great Song!"));
	}
	
	@Test
	public void findVideoTest() {
		videosManager.addVideoToDB("Pashut - Zusha", "www.Zusha.com", 3, Genre.Music);
		String actual = videosManager.findVideo("Pashut - Zusha").toString();
		String expected = video.toString();
		assertEquals(actual, expected);
	}
	
	@Test
	public void addPlaylistTest() {
		assertTrue(videosManager.addPlaylist("Shana Forever"));
	}
	
	@Test
	public void getPlaylistNamesTest() {
		playlists.add(playlist);
		videosManager.addPlaylist("Shana Forever");
		String actual = Arrays.toString(videosManager.getPlaylistsNames());
		String expected = "[Shana Forever]";
		
		assertEquals(actual, expected);
	}
	
	@Test
	public void addVideoToPlaylistTest() { 
		videosManager.addPlaylist("Shana Forever");
		videosManager.addVideoToDB("Come Back - Moshav","WWW.Moshav.com",4,
				Genre.Music);
		assertTrue(videosManager.addVideoToPlaylist("Come Back - Moshav","Shana Forever"));
	}
}
