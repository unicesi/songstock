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
package payments.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

import payments.dtos.IInvoice;
import sales.dtos.IItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Invoices database table.
 * 
 */
@Entity
@Table(name="Invoices")
@NamedQueries({ 
	@NamedQuery(name = "payments.getAllPendingInvoices", query = "SELECT i FROM Invoice i WHERE i.paymentState='"+IInvoice.PENDING_STATE+"'"),
	@NamedQuery(name = "payments.deleteAllInvoices", query = "DELETE FROM Invoice i"),
	@NamedQuery(name = "payments.getAllInvoices", query = "SELECT i FROM Invoice i")
	})
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String paymentMethod;

	private String paymentState;

	private String user;

	private double value;

	//bi-directional many-to-many association to AdditionalCharge
	@ManyToMany
	@JoinTable(
			name="AdditionalCharges_Invoices"
			, joinColumns={
				@JoinColumn(name="invoice", referencedColumnName = "id")
				}
			, inverseJoinColumns={
				@JoinColumn(name="additionalCharge", referencedColumnName = "id")
				}
			)
	private List<AdditionalCharge> additionalCharges;

	//bi-directional many-to-one association to AdditionalCharge_Invoice
	@OneToMany(mappedBy="invoiceBean")
	private List<AdditionalCharge_Invoice> additionalChargesInvoices;

	//bi-directional many-to-many association to Item
	@ManyToMany
	@JoinTable(
		name="Invoices_Items"
		, joinColumns={
			@JoinColumn(name="invoice", referencedColumnName = "id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="item", referencedColumnName = "id")
			}
		)
	private List<Item> items;

	public Invoice() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentState() {
		return this.paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public List<AdditionalCharge> getAdditionalCharges() {
		return this.additionalCharges;
	}

	public void setAdditionalCharges(List<AdditionalCharge> additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

	public List<AdditionalCharge_Invoice> getAdditionalChargesInvoices() {
		return this.additionalChargesInvoices;
	}

	public void setAdditionalChargesInvoices(List<AdditionalCharge_Invoice> additionalChargesInvoices) {
		this.additionalChargesInvoices = additionalChargesInvoices;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	/**
	 * Wraps this Object into its DTO representation (IInvoice)
	 * 
	 * @return IItem containing the information attached to this object
	 */
	public IInvoice toBO(){
		IInvoice invoice = new payments.dtos.impl.Invoice();
		invoice.setDate(date);
		invoice.setId(id);
		invoice.setPaymentMethod(paymentMethod);
		invoice.setPaymentState(paymentState);
		invoice.setUser(user);
		invoice.setValue(value);
		List<IItem> items = new ArrayList<>();
		for (Item item : this.items) {
			items.add(item.toBO());
		}
		invoice.setItems(items);
		return invoice;
	}

}