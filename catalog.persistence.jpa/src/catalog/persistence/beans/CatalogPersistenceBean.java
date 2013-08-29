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
package catalog.persistence.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import catalog.dtos.IAlbum;
import catalog.dtos.ISong;
import catalog.persistence.entities.Album;
import catalog.persistence.entities.Song;

/**
 * Session Bean that implements the ICatalogPersistenceBean Services
 * 
 * @author daviddurangiraldo
 * 
 */
@Stateless
public class CatalogPersistenceBean implements ICatalogPersistenceBean {

	@PersistenceContext(unitName = "catalog.persistence.jpa")
	private EntityManager entityManager;

	public CatalogPersistenceBean() {

	}

	@Override
	public List<IAlbum> getAllAlbums() {
		TypedQuery<Album> query = entityManager.createNamedQuery(
				"catalog.getAllAlbums", Album.class);
		List<Album> foundAlbums = query.getResultList();
		List<IAlbum> albums = new ArrayList<>();
		for (Album album : foundAlbums) {
			albums.add(album.toBO());
		}
		return albums.isEmpty() ? null : albums;
	}

	@Override
	public IAlbum getAlbumDetails(IAlbum iAlbum) {
		TypedQuery<Album> query = entityManager.createNamedQuery(
				"catalog.getAlbumDetails", Album.class);
		query.setParameter("albumId", iAlbum.getId());
		Album foundAlbum = null;
		try{
			foundAlbum = query.getSingleResult();
		}catch(Exception e){
			
		}
		IAlbum iFoundAlbum = null;
		if (foundAlbum != null)
			iFoundAlbum = foundAlbum.toBO();
		return iFoundAlbum;
	}

	@Override
	public List<ISong> getAlbumSongs(IAlbum iAlbum) {
		TypedQuery<Song> query = entityManager.createNamedQuery(
				"catalog.getAlbumSongs", Song.class);
		query.setParameter("albumId", iAlbum.getId());
		List<Song> foundSongs = query.getResultList();
		List<ISong> songs = new ArrayList<>();
		for (Song album : foundSongs) {
			songs.add(album.toBO());
		}
		return songs.isEmpty() ? null : songs;
	}

	@Override
	public ISong getSongDetails(ISong iSong) {
		TypedQuery<Song> query = entityManager.createNamedQuery(
				"catalog.getSongDetails", Song.class);
		query.setParameter("songId", iSong.getId());
		Song foundSong = null;
		try{
			foundSong = query.getSingleResult();
		}catch(Exception e){
			
		}
		ISong iFoundSong = null;
		if (foundSong != null)
			iFoundSong = foundSong.toBO();
		return iFoundSong;
	}

	@Override
	public List<IAlbum> getAlbumsWithNameContaining(String keyWord) {
		TypedQuery<Album> query = entityManager.createNamedQuery(
				"catalog.getAlbumsWithNameContaining", Album.class);
		query.setParameter("keyword", "%" + keyWord + "%");
		List<Album> filteredAlbums = query.getResultList();
		List<IAlbum> albums = new ArrayList<>();
		for (Album album : filteredAlbums) {
			albums.add(album.toBO());
		}
		return albums.isEmpty() ? null : albums;
	}

	@Override
	public List<IAlbum> getAlbumsFromArtist(String artist) {
		TypedQuery<Album> query = entityManager.createNamedQuery(
				"catalog.getAlbumsFromArtist", Album.class);
		query.setParameter("artist", "%" + artist + "%");
		List<Album> filteredAlbums = query.getResultList();
		List<IAlbum> albums = new ArrayList<>();
		for (Album album : filteredAlbums) {
			albums.add(album.toBO());
		}
		return albums.isEmpty() ? null : albums;
	}

	@Override
	public List<ISong> getSongsWithNameContaining(String keyWord) {
		TypedQuery<Song> query = entityManager.createNamedQuery(
				"catalog.getSongsWithNameContaining", Song.class);
		query.setParameter("keyword", "%" + keyWord + "%");
		List<Song> filteredSongs = query.getResultList();
		List<ISong> songs = new ArrayList<>();
		for (Song song : filteredSongs) {
			songs.add(song.toBO());
		}
		return songs.isEmpty() ? null : songs;
	}

	@Override
	public List<ISong> getSongsFromArtist(String artist) {
		TypedQuery<Song> query = entityManager.createNamedQuery(
				"catalog.getSongsFromArtist", Song.class);
		query.setParameter("artist", "%" + artist + "%");
		List<Song> filteredSongs = query.getResultList();
		List<ISong> songs = new ArrayList<>();
		for (Song song : filteredSongs) {
			songs.add(song.toBO());
		}
		return songs.isEmpty() ? null : songs;
	}

}
