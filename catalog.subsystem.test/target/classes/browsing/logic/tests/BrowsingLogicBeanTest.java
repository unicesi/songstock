/**
* Copyright © 2013 Universidad Icesi
* 
* This file is part of SongStock.
* 
* SongStock is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* SongStock is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with SongStock.  If not, see <http://www.gnu.org/licenses/>.
**/
package browsing.logic.tests;

import static org.junit.Assert.*;

import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import sales.dtos.IItem;
import sales.dtos.impl.Item;

import catalog.dtos.IAlbum;
import catalog.dtos.ISong;
import catalog.dtos.impl.Album;
import catalog.dtos.impl.Song;
import catalog.persistence.beans.CatalogPersistenceBean;
import catalog.persistence.beans.ICatalogPersistenceBean;
import catalog.persistence.exceptions.CatalogPersistenceException;

import browsing.logic.beans.BrowsingLogicBean;
import browsing.logic.beans.IBrowsingLogicBean;
import browsing.logic.exceptions.BrowsingLogicException;

/**
 * BrowsingLogicBean test class
 * @author David Durán
 *
 */
@RunWith(Arquillian.class)
public class BrowsingLogicBeanTest {

	/**
	 * BrowsingLogicBean instance
	 */
	@EJB
	private IBrowsingLogicBean iBrowsingLogicBean;
	
	/**
	 * Creates a test archive with the classes and configuration files needed to run the test cases.
	 * 
	 * @return .jar file containing the classes and configuration files needed to run the test cases 
	 */
	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "BrowsingLogicBeanTest.jar")
				.addClasses(  
						IAlbum.class,
						ISong.class,
						Album.class,
						Song.class,
						IBrowsingLogicBean.class,
						BrowsingLogicException.class,
						BrowsingLogicBean.class,
						IItem.class,
						Item.class,
						ICatalogPersistenceBean.class,
						CatalogPersistenceException.class,
						CatalogPersistenceBean.class,
						catalog.persistence.entities.Album.class,
						catalog.persistence.entities.Song.class)
				.addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
	}
	
	/**
	 * Test method for
	 * {@link browsing.logic.beans.BrowsingLogicBean#getAllAlbums()} In
	 * order for this test to work, the information contained in the script
	 * data/albums.sql must be contained previously in the database
	 */
	@Test
	public void testGetAllAlbums() {
		System.out.println("Test: obtaining all albums stored in database");
		List<IAlbum> foundAlbums = iBrowsingLogicBean.getAllAlbums();
		int totalAlbums = 7;
		assertEquals(
				"The query didn't find all the albums stored in the database that contain the keyword: ",
				totalAlbums, foundAlbums.size());

		int validFoundAlbums = 0, nFoundAlbums = 0;
		for (IAlbum iAlbum : foundAlbums) {
			if (isValidAlbum(iAlbum.getName(), iAlbum.getArtist()))
				validFoundAlbums++;
			nFoundAlbums++;
		}
		assertEquals("The query found different albums from the expected ones",
				totalAlbums, nFoundAlbums);
		assertEquals("The query found different albums from the expected ones",
				totalAlbums, validFoundAlbums);
	}

	/**
	 * Compares the name and the artist name of a given album against the
	 * data/albums.sql list, to determine if the album belongs to this list
	 * 
	 * @param name
	 *            String containing the album's name
	 * @param artist
	 *            String containing the album's artist name
	 * @return true if the album is contained in the list, false otherwise
	 */
	private boolean isValidAlbum(String name, String artist) {
		if (name.equals("Too Hot To Sleep") && artist.equals("Survivor"))
			return true;
		if (name.equals("Vital Signs") && artist.equals("Survivor"))
			return true;
		if (name.equals("When Seconds Count") && artist.equals("Survivor"))
			return true;
		if (name.equals("How to Dismantle an Atomic Bomb")
				&& artist.equals("U2"))
			return true;
		if (name.equals("The Joshua Tree") && artist.equals("U2"))
			return true;
		if (name.equals("Synchronicity") && artist.equals("The Police"))
			return true;
		if (name.equals("Painkiller") && artist.equals("Judas Priest"))
			return true;
		return false;
	}

	/**
	 * Test method for
	 * {@link browsing.logic.beans.BrowsingLogicBean#getAlbumDetails()}
	 * In order for this test to work, the information contained in the script
	 * data/albums.sql must be contained previously in the database
	 */
	@Test
	public void testGetAlbumDetails() {
		int albumId = -1;
		IAlbum iAlbum = new Album();
		// Album with id = -1
		System.out.println("Test: obtaining album details. AlbumId = "
				+ albumId);
		iAlbum.setId(albumId);
		IAlbum foundAlbum = iBrowsingLogicBean.getAlbumDetails(iAlbum);
		assertNull("The query is finding an album that doesn't exist",
				foundAlbum);

		// Album with id = 1
		getAlbumDetailsConfig(iAlbum, 1, "Too Hot To Sleep", "Survivor", 1988,
				"Rock");
		// Album with id = 2
		getAlbumDetailsConfig(iAlbum, 2, "Vital Signs", "Survivor", 1984,
				"Rock");
		// Album with id = 3
		getAlbumDetailsConfig(iAlbum, 3, "When Seconds Count", "Survivor",
				1986, "Rock");
		// Album with id = 4
		getAlbumDetailsConfig(iAlbum, 4, "How to Dismantle an Atomic Bomb",
				"U2", 2004, "Rock");
		// Album with id = 5
		getAlbumDetailsConfig(iAlbum, 5, "The Joshua Tree", "U2", 1987, "Rock");
		// Album with id = 6
		getAlbumDetailsConfig(iAlbum, 6, "Synchronicity", "The Police", 1983,
				"Rock");
		// Album with id = 7
		getAlbumDetailsConfig(iAlbum, 7, "Painkiller", "Judas Priest", 1990,
				"Heavy Metal");
	}

	/**
	 * Sets all the parameters needed to test the getAlbumDetails method
	 * 
	 * @param iAlbum
	 *            IAlbum containing album to search for
	 * @param albumId
	 *            int containing the id of the album to search for
	 * @param albumName
	 *            String containing the name of the album to search for
	 * @param albumArtist
	 *            String containing the artist of the album to search for
	 * @param albumGenre
	 *            String containing the genre of the album to search for
	 */
	private void getAlbumDetailsConfig(IAlbum iAlbum, int albumId,
			String albumName, String albumArtist, int releaseYear,
			String albumGenre) {
		IAlbum foundAlbum;
		System.out.println("Test: obtaining album details. AlbumId = "
				+ albumId);
		iAlbum.setId(albumId);
		foundAlbum = iBrowsingLogicBean.getAlbumDetails(iAlbum);
		assertNotNull("The query is NOT finding an album that exist",
				foundAlbum);

		assertEquals(
				"The name of the album found is different from the expected one",
				albumName, foundAlbum.getName());
		assertEquals(
				"The artist name of the album found is different from the expected one",
				albumArtist, foundAlbum.getArtist());
		assertEquals(
				"The release year of the album found is different from the expected one",
				releaseYear, foundAlbum.getReleaseYear().intValue());
		assertEquals(
				"The genre of the album found is different from the expected one",
				albumGenre, foundAlbum.getGenre());
	}

	/**
	 * Test method for
	 * {@link browsing.logic.beans.BrowsingLogicBean#getAlbumSongs()}
	 * In order for this test to work, the information contained in the scripts
	 * data/albums.sql and data/songs.sql must be contained previously in the
	 * database
	 */
	@Test
	public void testGetAlbumSongs() {
		// Album with id = -1
		int albumId = -1;
		System.out
				.println("Test: obtaining songs of a specified album. AlbumId = "
						+ albumId);
		IAlbum iAlbum = new Album();
		iAlbum.setId(albumId);
		List<ISong> foundSongs = iBrowsingLogicBean.getAlbumSongs(iAlbum);
		assertNull(
				"The query is finding songs from an album that doesn't exist",
				foundSongs);

		// Album with id = 6. Synchronicity By The Police
		albumId = 6;
		System.out
				.println("Test: obtaining songs of a specified album. AlbumId = "
						+ albumId);
		iAlbum.setId(albumId);
		foundSongs = iBrowsingLogicBean.getAlbumSongs(iAlbum);
		// Id = 12. Every Breath You Take By The Police
		assertEquals("The query didn't find the expected song", true,
				checkSong(foundSongs, 12));
		// Id = 13. Wrapped Around Your Finger By The Police
		assertEquals("The query didn't find the expected song", true,
				checkSong(foundSongs, 13));

		// Album with id = 7. Painkiller By Judas Priest
		albumId = 7;
		System.out
				.println("Test: obtaining songs of a specified album. AlbumId = "
						+ albumId);
		iAlbum.setId(albumId);
		foundSongs = iBrowsingLogicBean.getAlbumSongs(iAlbum);
		// Id = 14. Hell Patroll By Judas Priest
		assertEquals("The query didn't find the expected song", true,
				checkSong(foundSongs, 14));
		// Id = 15. Night Crawler By Judas Priest
		assertEquals("The query didn't find the expected song", true,
				checkSong(foundSongs, 15));
		// Id = 16. Painkiller By Judas Priest
		assertEquals("The query didn't find the expected song", true,
				checkSong(foundSongs, 16));
	}

	/**
	 * Validates if a song belongs to the specified list
	 * 
	 * @param list
	 *            List<ISong> containing the list where the song is going to be
	 *            looked up
	 * @param songId
	 *            Integer representing the song's id
	 * @return true if the song is in the list, false otherwise
	 */
	private boolean checkSong(List<ISong> list, Integer songId) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId().equals(songId))
				return true;
		}
		return false;
	}

	/**
	 * Test method for
	 * {@link browsing.logic.beans.BrowsingLogicBean#getSongDetails()}
	 * In order for this test to work, the information contained in the scripts
	 * data/albums.sql and data/songs.sql must be contained previously in the
	 * database
	 */
	@Test
	public void testGetSongDetails() {
		int songId = -1;
		ISong iSong = new Song();
		// Album with id = -1
		System.out.println("Test: obtaining song details. SongId = " + songId);
		iSong.setId(songId);
		ISong foundAlbum = iBrowsingLogicBean.getSongDetails(iSong);
		assertNull("The query is finding a song that doesn't exist", foundAlbum);

		// Song with id = 1
		getSongDetailsConfig(iSong, 1, 1, "Desperate Dreams", "Survivor");
		// Song with id = 5
		getSongDetailsConfig(iSong, 5, 3, "Man Against The World", "Survivor");
		// Song with id = 9
		getSongDetailsConfig(iSong, 9, 4, "Sometimes You Can't Make it on Your Own", "U2");
		// Song with id = 13
		getSongDetailsConfig(iSong, 13, 6, "Wrapped Around Your Finger", "The Police");
		// Song with id = 16
		getSongDetailsConfig(iSong, 16, 7, "Painkiller", "Judas Priest");
	}

	/**
	 * Sets all the parameters needed to test the getSongDetails method
	 * 
	 * @param iSong
	 *            ISong containing album to search for
	 * @param songId
	 *            int that represents the song's id
	 * @param albumId
	 *            int that represents the song's album id
	 * @param songName
	 *            String that contains the song's name
	 * @param songArtist
	 *            String that contains the song's artist name
	 */
	private void getSongDetailsConfig(ISong iSong, int songId, int albumId,
			String songName, String songArtist) {
		ISong foundSong;
		System.out.println("Test: obtaining song details. SongId = " + songId);
		iSong.setId(songId);
		foundSong = iBrowsingLogicBean.getSongDetails(iSong);
		assertNotNull("The query is NOT finding a song that exist", foundSong);

		assertEquals(
				"The name of the song found is different from the expected one",
				songName, foundSong.getName());
		assertEquals(
				"The artist name of the song found is different from the expected one",
				songArtist, foundSong.getArtist());
		assertEquals(
				"The album's id of the song found is different from the expected one",
				albumId, foundSong.getAlbumId().intValue());
	}

}
