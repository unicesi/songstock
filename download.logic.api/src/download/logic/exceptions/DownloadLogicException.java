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
package download.logic.exceptions;

/**
 * @author Andrés Paz
 *
 */
public class DownloadLogicException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public DownloadLogicException() {
	}

	/**
	 * Creates a DownloadLogicException with the specified message.
	 * @param message describing the exception
	 */
	public DownloadLogicException(String message) {
		super(message);
	}

	/**
	 * Creates a DownloadLogicException with the specified cause.
	 * @param cause of the exception
	 */
	public DownloadLogicException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a DownloadLogicException with the specified message and cause.
	 * @param message describing the exception
	 * @param cause of the exception
	 */
	public DownloadLogicException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a DownloadLogicException with the specified message, cause and options.
	 * 
	 * @param message describing the exception
	 * @param cause of the exception
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DownloadLogicException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
