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
 * The primary key class for the AdditionalCharges_Invoices database table.
 * 
 */
@Embeddable
public class AdditionalCharge_InvoicePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String additionalCharge;

	private int invoice;

	public AdditionalCharge_InvoicePK() {
	}
	public String getAdditionalCharge() {
		return this.additionalCharge;
	}
	public void setAdditionalCharge(String additionalCharge) {
		this.additionalCharge = additionalCharge;
	}
	public int getInvoice() {
		return this.invoice;
	}
	public void setInvoice(int invoice) {
		this.invoice = invoice;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdditionalCharge_InvoicePK)) {
			return false;
		}
		AdditionalCharge_InvoicePK castOther = (AdditionalCharge_InvoicePK)other;
		return 
			this.additionalCharge.equals(castOther.additionalCharge)
			&& (this.invoice == castOther.invoice);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.additionalCharge.hashCode();
		hash = hash * prime + this.invoice;
		
		return hash;
	}
}