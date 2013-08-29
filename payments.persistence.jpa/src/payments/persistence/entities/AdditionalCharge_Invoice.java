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


/**
 * The persistent class for the AdditionalCharges_Invoices database table.
 * 
 */
@Entity
@Table(name="AdditionalCharges_Invoices")
@NamedQueries({ 
	@NamedQuery(name = "payments.deleteAllAdditionalChargeInvoice", query = "DELETE FROM AdditionalCharge_Invoice aCI")
	})
public class AdditionalCharge_Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdditionalCharge_InvoicePK id;

	private double value;

	//bi-directional many-to-one association to AdditionalCharge
	@ManyToOne
	@JoinColumn(name="additionalCharge", insertable=false, updatable=false)
	private AdditionalCharge additionalChargeBean;

	//bi-directional many-to-one association to Invoice
	@ManyToOne
	@JoinColumn(name="invoice", insertable=false, updatable=false)
	private Invoice invoiceBean;

	public AdditionalCharge_Invoice() {
	}

	public AdditionalCharge_InvoicePK getId() {
		return this.id;
	}

	public void setId(AdditionalCharge_InvoicePK id) {
		this.id = id;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public AdditionalCharge getAdditionalChargeBean() {
		return this.additionalChargeBean;
	}

	public void setAdditionalChargeBean(AdditionalCharge additionalChargeBean) {
		this.additionalChargeBean = additionalChargeBean;
	}

	public Invoice getInvoiceBean() {
		return this.invoiceBean;
	}

	public void setInvoiceBean(Invoice invoiceBean) {
		this.invoiceBean = invoiceBean;
	}

}