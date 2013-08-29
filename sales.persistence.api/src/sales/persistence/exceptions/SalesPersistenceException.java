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
package sales.persistence.exceptions;

/**
 * Exception implementation class for the sales persistence package
 * @author daviddurangiraldo
 *
 */
public class SalesPersistenceException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * Default SalesPersistenceException constructor
	 */
	public SalesPersistenceException() {
		super();
	}

	/**
	 * throws a SalesPersistenceException with the specified message
	 * @param message String containing the Exception message
	 */
	public SalesPersistenceException(String message) {
		super(message);
	}

	/**
	 * throws a SalesPersistenceException with the specified cause
	 * @param cause Throwable containing the Exception cause
	 */
	public SalesPersistenceException(Throwable cause) {
		super(cause);
	}

	/**
	 * throws a SalesPersistenceException with the specified message and cause
	 * @param message String containing the Exception message
	 * @param cause Throwable containing the Exception cause
	 */
	public SalesPersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SalesPersistenceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
