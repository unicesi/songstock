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
package additionalcharges.tax.logic.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import payments.dtos.IAdditionalCharge;
import payments.dtos.impl.AdditionalCharge;
import payments.persistence.beans.IPaymentsPersistenceBean;
import sales.dtos.IItem;

/**
 * Session Bean that implements the ITaxLogicBean Services
 * 
 * @author daviddurangiraldo
 */
@Stateless
public class TaxLogicBean implements ITaxLogicBean {

	/**
	 * Bean that contains the basic persistence services
	 */
	@EJB
	private IPaymentsPersistenceBean iTaxPersistenceBean;

	/**
	 * Default constructor.
	 */
	public TaxLogicBean() {

	}

	@Override
	public IAdditionalCharge calculateTax(List<IItem> items) {
		double total = 0;
		for (IItem iItem : items) {
			total += iItem.getPrice();
		}
		IAdditionalCharge additionalCharge = new AdditionalCharge();
		additionalCharge.setId(IAdditionalCharge.TAX_CHARGE);
		additionalCharge.setDescription("16% discount per item");
		additionalCharge.setRate(0.16);
		additionalCharge.setValue(total * 0.16);
		iTaxPersistenceBean.persistAdditionalCharge(additionalCharge);
		return additionalCharge;
	}

}
