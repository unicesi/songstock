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
import java.util.List;

import payments.dtos.IAdditionalCharge;
import payments.dtos.IInvoice;
import sales.dtos.IItem;

/**
 * DTO implementation class for DTO: Invoice
 * 
 * @author daviddurangiraldo
 * 
 */
public class Invoice implements Serializable, IInvoice {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date date;

	private String paymentMethod;

	private String paymentState;

	private String user;

	private double value;

	private List<IAdditionalCharge> additionalCharges;
	
	private List<IItem> items;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String getPaymentMethod() {
		return paymentMethod;
	}

	@Override
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String getPaymentState() {
		return paymentState;
	}

	@Override
	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
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
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public List<IAdditionalCharge> getAdditionalCharges() {
		return additionalCharges;
	}

	@Override
	public void setAdditionalCharges(List<IAdditionalCharge> additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

	@Override
	public List<IItem> getItems() {
		return items;
	}

	@Override
	public void setItems(List<IItem> items) {
		this.items = items;
	}

}
