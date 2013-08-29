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
package payments.dtos.impl;

import java.io.Serializable;
import java.util.Date;

import payments.dtos.ICreditCard;

/**
 * DTO implementation class for DTO: CreditCard
 * 
 * @author daviddurangiraldo
 * 
 */
public class CreditCard implements Serializable, ICreditCard {

	private static final long serialVersionUID = 1L;

	private String number;

	private String billingAddress;

	private String cardHolderName;

	private String city;

	private String country;

	private Date expirationDate;

	private String phoneNumber;

	private String state;

	private String user;

	private String zipCode;

	private int cardVerificationCode;
	
	private String brandName;

	@Override
	public int getCardVerificationCode() {
		return cardVerificationCode;
	}

	@Override
	public void setCardVerificationCode(int cardVerificationCode) {
		this.cardVerificationCode = cardVerificationCode;
	}

	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String getBillingAddress() {
		return billingAddress;
	}

	@Override
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	@Override
	public String getCardHolderName() {
		return cardHolderName;
	}

	@Override
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public Date getExpirationDate() {
		return expirationDate;
	}

	@Override
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String getState() {
		return state;
	}

	@Override
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String getZipCode() {
		return zipCode;
	}

	@Override
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String getBrandName() {
		return this.brandName;
	}

	@Override
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
