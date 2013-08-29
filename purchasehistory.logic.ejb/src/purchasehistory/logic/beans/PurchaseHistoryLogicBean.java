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
package purchasehistory.logic.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import purchasehistory.logic.exceptions.PurchaseHistoryLogicException;

import sales.dtos.IItem;
import sales.persistence.beans.ISalesPersistenceBean;
import sales.persistence.exceptions.SalesPersistenceException;
import users.dtos.IUser;

/**
 * Session Bean that implements the IPurchaseHistoryLogicBean Services
 * 
 * @author daviddurangiraldo
 */
@Stateless
public class PurchaseHistoryLogicBean implements IPurchaseHistoryLogicBean {

	/**
	 * Bean that contains the basic persistence services
	 */
	@EJB
	private ISalesPersistenceBean iSalesPersistenceBean;

	/**
	 * Default constructor.
	 */
	public PurchaseHistoryLogicBean() {

	}

	@Override
	public void logUserPurchase(List<IItem> items, IUser iUser)
			throws PurchaseHistoryLogicException {
		boolean error = false;
		for (IItem iItem : items) {
			try {
				iSalesPersistenceBean.logUserPurchase(iItem, iUser);
			} catch (SalesPersistenceException e) {
				error = true;
			}
		}

		if (error) {
			throw new PurchaseHistoryLogicException(
					"One of the selected items has been alredy bought");
		}
	}

	@Override
	public List<IItem> getUserPurchaseHistory(IUser iUser) {
		return iSalesPersistenceBean.getUserPurchaseHistory(iUser);
	}

}
