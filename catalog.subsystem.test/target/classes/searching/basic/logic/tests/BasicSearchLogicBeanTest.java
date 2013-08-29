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
package searching.basic.logic.tests;

import static org.junit.Assert.*;

import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import catalog.dtos.IAlbum;
import catalog.dtos.ISong;
import catalog.dtos.impl.Album;
import catalog.dtos.impl.Song;
import catalog.persistence.beans.CatalogPersistenceBean;
import catalog.persistence.beans.ICatalogPersistenceBean;
import catalog.persistence.exceptions.CatalogPersistenceException;

import sales.dtos.IItem;
import sales.dtos.impl.Item;
import searching.basic.logic.beans.BasicSearchLogicBean;
import searching.basic.logic.beans.IBasicSearchLogicBean;
import searching.basic.logic.exceptions.BasicSearchLogicException;

/**
 * Test case for the BasicSearchLogicBean class
 * 
 * @author daviddurangiraldo
 * 
 */
@RunWith(Arquillian.class)
public class BasicSearchLogicBeanTest {

	/**
	 * BasicSearchLogicBean instance
	 */
	@EJB
	private IBasicSearchLogicBean iBasicSearchLogicBean;

	/**
	 * Creates a test archive with the classes and configuration files needed to run the test cases.
	 * 
	 * @return .jar file containing the classes and configuration files needed to run the test cases 
	 */
	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "BasicSearchLogicBeanTest.jar")
				.addClasses(  
						IAlbum.class,
						ISong.class,
						Album.class,
						Song.class,
						IBasicSearchLogicBean.class,
						BasicSearchLogicException.class,
						BasicSearchLogicBean.class,
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
	 * {@link searching.basic.logic.beans.BasicSearchLogicBean#findAlbumsByKeywords()}
	 * In order for this test to work, the information contained in the script
	 * data/albums.sql must be contained previously in the database
	 */
	@Test
	public void testFindAlbumsByKeywords() {
		// Albums that contain the keyword: s
		String keyword = "s";
		int totalAlbums = 7;
		System.out.println("Test: finding albums by keyword: " + keyword);

		List<IAlbum> foundAlbums = iBasicSearchLogicBean
				.findAlbumsByKeywords(keyword);
		assertEquals(
				"The query didn't find all the albums in the database that contain the keyword: "
						+ keyword, totalAlbums, foundAlbums.size());

		int validFoundAlbums = 0, nFoundAlbums = 0;
		for (IAlbum iAlbum : foundAlbums) {
			if (isValidAlbumByS(iAlbum.getName(), iAlbum.getArtist()))
				validFoundAlbums++;
			nFoundAlbums++;
		}
		assertEquals("The query found different albums from the expected ones",
				totalAlbums, nFoundAlbums);
		assertEquals("The query found different albums from the expected ones",
				totalAlbums, validFoundAlbums);

		// Albums that contain the keyword: Survivor
		keyword = "Survivor";
		totalAlbums = 3;
		System.out.println("Test: finding albums by keyword: " + keyword);

		foundAlbums = iBasicSearchLogicBean.findAlbumsByKeywords(keyword);
		assertEquals(
				"The query didn't find all the albums in the database that contain the keyword: "
						+ keyword, totalAlbums, foundAlbums.size());

		validFoundAlbums = 0;
		nFoundAlbums = 0;
		for (IAlbum iAlbum : foundAlbums) {
			if (isValidAlbumBySurvivor(iAlbum.getName(), iAlbum.getArtist()))
				validFoundAlbums++;
			nFoundAlbums++;
		}
		assertEquals("The query found different albums from the expected ones",
				totalAlbums, nFoundAlbums);
		assertEquals("The query found different albums from the expected ones",
				totalAlbums, validFoundAlbums);

		// Albums that contain the keyword: xyz!
		keyword = "xyz!";
		System.out.println("Test: finding albums by keyword: " + keyword);

		foundAlbums = iBasicSearchLogicBean.findAlbumsByKeywords(keyword);
		assertNull("The query found results with an invalid keyword",
				foundAlbums);
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
	private boolean isValidAlbumByS(String name, String artist) {
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
	 * Compares the name and the artist name of a given album against the
	 * data/albums.sql list filtered by "Survivor" keyword, to determine if the
	 * album belongs to this list
	 * 
	 * @param name
	 *            String containing the album's name
	 * @param artist
	 *            String containing the album's artist name
	 * @return true if the album is contained in the list, false otherwise
	 */
	private boolean isValidAlbumBySurvivor(String name, String artist) {
		if (name.equals("Too Hot To Sleep") && artist.equals("Survivor"))
			return true;
		if (name.equals("Vital Signs") && artist.equals("Survivor"))
			return true;
		if (name.equals("When Seconds Count") && artist.equals("Survivor"))
			return true;
		return false;
	}

	/**
	 * Test method for
	 * {@link searching.basic.logic.beans.BasicSearchLogicBean#findSongsByKeywords()}
	 * In order for this test to work, the information contained in the script
	 * data/songs.sql must be contained previously in the database
	 */
	@Test
	public void testFindSongsByKeywords() {
		// Songs that contain the keyword: h
		String keyword = "h";
		int totalAlbums = 11;
		System.out.println("Test: finding albums by keyword: " + keyword);

		List<ISong> foundSongs = iBasicSearchLogicBean
				.findSongsByKeywords(keyword);
		assertEquals(
				"The query didn't find all the songs in the database that contain the keyword: "
						+ keyword, totalAlbums, foundSongs.size());

		int validFoundSongs = 0, nFoundSongs = 0;
		for (ISong iSong : foundSongs) {
			if (isValidSongByH(iSong.getName(), iSong.getArtist()))
				validFoundSongs++;
			nFoundSongs++;
		}
		assertEquals("The query found different songs from the expected ones",
				totalAlbums, nFoundSongs);
		assertEquals("The query found different songs from the expected ones",
				totalAlbums, validFoundSongs);

		// Songs that contain the keyword: The Police
		keyword = "The Police";
		totalAlbums = 2;
		System.out.println("Test: finding albums by keyword: " + keyword);

		foundSongs = iBasicSearchLogicBean.findSongsByKeywords(keyword);
		assertEquals(
				"The query didn't find all the songs in the database that contain the keyword: "
						+ keyword, totalAlbums, foundSongs.size());

		validFoundSongs = 0;
		nFoundSongs = 0;
		for (ISong iSong : foundSongs) {
			if (isValidSongByThePolice(iSong.getName(), iSong.getArtist()))
				validFoundSongs++;
			nFoundSongs++;
		}
		assertEquals("The query found different songs from the expected ones",
				totalAlbums, nFoundSongs);
		assertEquals("The query found different songs from the expected ones",
				totalAlbums, validFoundSongs);

		// Songs that contain the keyword: xyz!
		keyword = "xyz!";
		System.out.println("Test: finding albums by keyword: " + keyword);

		foundSongs = iBasicSearchLogicBean.findSongsByKeywords(keyword);
		assertNull("The query found results with an invalid keyword",
				foundSongs);
	}

	/**
	 * Compares the name and the artist name of a given song against the
	 * data/songs.sql list filtered by "h" keyword, to determine if the song
	 * belongs to this list
	 * 
	 * @param name
	 *            String containing the song's name
	 * @param artist
	 *            String containing the song's artist name
	 * @return true if the song is contained in the list, false otherwise
	 */
	private boolean isValidSongByH(String name, String artist) {
		if (name.equals("High On You") && artist.equals("Survivor"))
			return true;
		if (name.equals("I Can't Hold Back") && artist.equals("Survivor"))
			return true;
		if (name.equals("Is This Love") && artist.equals("Survivor"))
			return true;
		if (name.equals("Man Against The World") && artist.equals("Survivor"))
			return true;
		if (name.equals("City Of Blinding Lights") && artist.equals("U2"))
			return true;
		if (name.equals("Still Haven't Found What I'm Looking For")
				&& artist.equals("U2"))
			return true;
		if (name.equals("Where the Streets Have No Name")
				&& artist.equals("U2"))
			return true;
		if (name.equals("Every Breath You Take") && artist.equals("The Police"))
			return true;
		if (name.equals("Wrapped Around Your Finger")
				&& artist.equals("The Police"))
			return true;
		if (name.equals("Hell Patrol") && artist.equals("Judas Priest"))
			return true;
		if (name.equals("Night Crawler") && artist.equals("Judas Priest"))
			return true;
		return false;
	}

	/**
	 * Compares the name and the artist name of a given song against the
	 * data/songs.sql list filtered by "The Police" keyword, to determine if the
	 * song belongs to this list
	 * 
	 * @param name
	 *            String containing the song's name
	 * @param artist
	 *            String containing the song's artist name
	 * @return true if the song is contained in the list, false otherwise
	 */
	private boolean isValidSongByThePolice(String name, String artist) {
		if (name.equals("Every Breath You Take") && artist.equals("The Police"))
			return true;
		if (name.equals("Wrapped Around Your Finger")
				&& artist.equals("The Police"))
			return true;
		return false;
	}
}
