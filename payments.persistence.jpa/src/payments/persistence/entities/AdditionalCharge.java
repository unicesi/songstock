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

import payments.dtos.IAdditionalCharge;

import java.util.List;


/**
 * The persistent class for the AdditionalCharges database table.
 * 
 */
@Entity
@Table(name="AdditionalCharges")
@NamedQueries({ 
	@NamedQuery(name = "payments.deleteAllAdditionalCharges", query = "DELETE FROM AdditionalCharge aC"),
	@NamedQuery(name = "payments.getAllAdditionalCharges", query = "SELECT aC FROM AdditionalCharge aC")
	})
public class AdditionalCharge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String description;

	private double rate;

	//bi-directional many-to-many association to Invoice
	@ManyToMany(mappedBy="additionalCharges")
	private List<Invoice> invoices;

	//bi-directional many-to-one association to AdditionalCharge_Invoice
	@OneToMany(mappedBy="additionalChargeBean")
	private List<AdditionalCharge_Invoice> additionalChargesInvoices;

	public AdditionalCharge() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRate() {
		return this.rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public List<Invoice> getInvoices() {
		return this.invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public List<AdditionalCharge_Invoice> getAdditionalChargesInvoices() {
		return this.additionalChargesInvoices;
	}

	public void setAdditionalChargesInvoices(List<AdditionalCharge_Invoice> additionalChargesInvoices) {
		this.additionalChargesInvoices = additionalChargesInvoices;
	}

	/**
	 * Wraps this Object into its DTO representation (IAdditionalCharge)
	 * 
	 * @return ISong containing the information attached to this object
	 */
	public IAdditionalCharge toBO(){
		IAdditionalCharge aC = new payments.dtos.impl.AdditionalCharge();
		aC.setDescription(description);
		aC.setId(id);
		aC.setRate(rate);
		
		return aC;
	}
}