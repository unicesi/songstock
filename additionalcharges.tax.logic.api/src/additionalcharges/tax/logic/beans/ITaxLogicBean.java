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

import javax.ejb.Remote;

import payments.dtos.IAdditionalCharge;
import sales.dtos.IItem;

/***
 * Interface definition that contains the TaxLogicBean services
 * 
 * @author daviddurangiraldo
 * 
 */
@Remote
public interface ITaxLogicBean {

	/**
	 * Calculates the tax value depending on the prices of the given list of
	 * items
	 * 
	 * @param items
	 *            List<IItem> containing the items to be bought
	 * @return IAdditionalCharge containing the calculated tax value
	 */
	public IAdditionalCharge calculateTax(List<IItem> items);
}
