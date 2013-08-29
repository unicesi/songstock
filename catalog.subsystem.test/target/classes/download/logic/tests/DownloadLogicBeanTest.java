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
package download.logic.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
import browsing.logic.beans.BrowsingLogicBean;
import browsing.logic.beans.IBrowsingLogicBean;
import browsing.logic.exceptions.BrowsingLogicException;
import catalog.dtos.IAlbum;
import catalog.dtos.ISong;
import catalog.dtos.impl.Album;
import catalog.dtos.impl.Song;
import catalog.persistence.beans.CatalogPersistenceBean;
import catalog.persistence.beans.ICatalogPersistenceBean;
import catalog.persistence.exceptions.CatalogPersistenceException;

import download.logic.beans.DownloadLogicBean;
import download.logic.beans.IDownloadLogicBean;
import download.logic.exceptions.DownloadLogicException;

/**
 * DownloadLogicBean test class
 * 
 * @author Andrés Paz
 *
 */
@RunWith(Arquillian.class)
public class DownloadLogicBeanTest {

	/**
	 * DownloadLogicBean instance
	 */
	@EJB
	private IDownloadLogicBean downloadLogicBean;
	
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
						IDownloadLogicBean.class,
						DownloadLogicException.class,
						DownloadLogicBean.class,
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
	 * {@link download.logic.beans.DownloadLogicBean#downloadItem()} In
	 * order for this test to work, the information contained in the scripts in the
	 * data folder must be contained previously in the database.
	 */
	@Test
	public void testDownloadItem() {
		System.out.println("Test: download song item »");
		
		try {
			IItem item = new Item();
			item.setArtist("Judas Priest");
			item.setName("Painkiller");
			item.setSong(16);
			item.setAlbum(7);
			item.setType(IItem.SONG_TYPE);
			
			String resultURL = downloadLogicBean.downloadItem(item);
			
			String expectedURL = "http://200.3.193.23/songstock/files/Judas Priest/Painkiller/Painkiller.mp3";
			
			assertEquals(expectedURL, resultURL);
			
		} catch (DownloadLogicException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for
	 * {@link download.logic.beans.DownloadLogicBean#downloadItemsIn()} In
	 * order for this test to work, the information contained in the scripts in the
	 * data folder must be contained previously in the database.
	 */
	@Test
	public void testDownloadItemsIn() {
		System.out.println("Test: download album item »");
		
		IItem item = new Item();
		item.setArtist("Judas Priest");
		item.setName("Painkiller");
		item.setAlbum(7);
		item.setType(IItem.ALBUM_TYPE);
		
		List<String> resultURLs = downloadLogicBean.downloadItemsIn(item);
		
		List<String> expectedURLs = new ArrayList<>();
		expectedURLs.add("http://200.3.193.23/songstock/files/Judas Priest/Painkiller/Hell Patrol.mp3");
		expectedURLs.add("http://200.3.193.23/songstock/files/Judas Priest/Painkiller/Night Crawler.mp3");
		expectedURLs.add("http://200.3.193.23/songstock/files/Judas Priest/Painkiller/Painkiller.mp3");
		
		for (int i = 0; i < resultURLs.size(); i++) {
			assertEquals(expectedURLs.get(i), resultURLs.get(i));
		}
	}

}
