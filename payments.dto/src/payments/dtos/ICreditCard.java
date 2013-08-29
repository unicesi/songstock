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
package payments.dtos;

import java.util.Date;

public interface ICreditCard {

	/**
	 * Gets the credit card's verification code
	 * 
	 * @return int containing the credit card's verification code
	 */
	public int getCardVerificationCode();

	/**
	 * Sets the credit card's verification code
	 * 
	 * @param cardVerificationCode
	 *            int containing the new credit card's verification code
	 */
	public void setCardVerificationCode(int cardVerificationCode);

	/**
	 * Gets the credit card's number
	 * 
	 * @return String containing the credit card's number
	 */
	public String getNumber();

	/**
	 * Sets the credit card's number
	 * 
	 * @param number
	 *            String containing the new credit card's number
	 */
	public void setNumber(String number);

	/**
	 * Gets the credit card's billing address
	 * 
	 * @return String containing the credit card's billing address
	 */
	public String getBillingAddress();

	/**
	 * Sets the credit card's bulling address
	 * 
	 * @param billingAddress
	 *            String containing the new credit card's billing address
	 */
	public void setBillingAddress(String billingAddress);

	/**
	 * Gets the credit card's holder name
	 * 
	 * @return String containing the credit card's holder name
	 */
	public String getCardHolderName();

	/**
	 * Sets the credit card's holder name
	 * 
	 * @param cardHolderName
	 *            String containing the credit card's holder name
	 */
	public void setCardHolderName(String cardHolderName);

	/**
	 * Gets the credit card's city
	 * 
	 * @return String containing the credit card's city
	 */
	public String getCity();

	/**
	 * Sets the credit card's city
	 * 
	 * @param city
	 *            String containing the new credit card's city
	 */
	public void setCity(String city);

	/**
	 * Gets the credit card's country
	 * 
	 * @return String containing the credit card's country
	 */
	public String getCountry();

	/**
	 * Sets the credit card's country
	 * 
	 * @param country
	 *            String containing the new credit card's country
	 */
	public void setCountry(String country);

	/**
	 * Gets the credit card's expiration date
	 * 
	 * @return Date containing the credit card's expiration date
	 */
	public Date getExpirationDate();

	/**
	 * Sets the credit card's expiration date
	 * 
	 * @param expirationDate
	 *            Date containing the new credit card's expiration date
	 */
	public void setExpirationDate(Date expirationDate);

	/**
	 * Gets the credit card's phone number
	 * 
	 * @return String containing the credit card's phone number
	 */
	public String getPhoneNumber();

	/**
	 * Sets the credit card's phone number
	 * 
	 * @param phoneNumber
	 *            String containing the new credit card's number
	 */
	public void setPhoneNumber(String phoneNumber);

	/**
	 * Gets the credit card's state
	 * 
	 * @return String containing the credit card's state
	 */
	public String getState();

	/**
	 * Sets the credit card's state
	 * 
	 * @param state
	 *            String containing the new credit card's state
	 */
	public void setState(String state);

	/**
	 * Gets the credit card's user email
	 * 
	 * @return String containing the credit card's user email
	 */
	public String getUser();

	/**
	 * Sets the credit card's user email
	 * 
	 * @param user
	 *            String containing the new credit card's user email
	 */
	public void setUser(String user);

	/**
	 * Gets the credit card's country zip code
	 * 
	 * @return String containing the credit card's country zip code
	 */
	public String getZipCode();

	/**
	 * Sets the credit card's country zip code
	 * 
	 * @param zipCode
	 *            String containing the new credit card's country zip code
	 */
	public void setZipCode(String zipCode);

	/**
	 * Gets the credit card's brand name
	 * 
	 * @return String containing the credit card's brand name
	 */
	public String getBrandName();

	/**
	 * Sets the credit card's brand name
	 * 
	 * @param brandName
	 *            String containing the new credit card's brand name
	 */
	public void setBrandName(String brandName);
}
