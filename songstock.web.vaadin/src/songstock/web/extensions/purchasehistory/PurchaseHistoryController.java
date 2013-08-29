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
package songstock.web.extensions.purchasehistory;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import purchasehistory.logic.beans.IPurchaseHistoryLogicBean;
import purchasehistory.logic.exceptions.PurchaseHistoryLogicException;

import sales.dtos.IItem;
import songstock.web.AbstractController;
import songstock.web.ProcessContributor;
import songstock.web.Registry;
import songstock.web.SongStockUI;
import songstock.web.client.ContentPanel;
import users.dtos.IUser;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * @author Andrés Paz
 *
 */
public class PurchaseHistoryController extends AbstractController implements ProcessContributor {

	/**
	 * 
	 */
	private static PurchaseHistoryController purchaseHistoryController;
	
	/**
	 * 
	 */
	private IPurchaseHistoryLogicBean purchaseHistoryLogicBean;
	
	public PurchaseHistoryController() {
		doLookup();
	}

	private void doLookup() {
		try {
			InitialContext context = new InitialContext();
			purchaseHistoryLogicBean = (IPurchaseHistoryLogicBean) context.lookup("java:global/sales.subsystem/purchasehistory.logic.ejb/PurchaseHistoryLogicBean!purchasehistory.logic.beans.IPurchaseHistoryLogicBean");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static PurchaseHistoryController getInstance() {
		if (purchaseHistoryController == null) {
			purchaseHistoryController = new PurchaseHistoryController();
		}
		return purchaseHistoryController;
	}
	
	protected void showPurchaseHistoryView() {
		PurchaseHistoryView purchaseHistoryView = new PurchaseHistoryView();
		
		ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		contentPanel.removeContent();
		contentPanel.setContent(purchaseHistoryView);
		
		Registry.register(PurchaseHistoryView.PURCHASE_HISTORY_VIEW, purchaseHistoryView);
		
		IUser user = Registry.get(SongStockUI.USER);
		List<IItem> items = purchaseHistoryLogicBean.getUserPurchaseHistory(user);
		purchaseHistoryView.loadItems(items);
	}
	
	/**
	 * 
	 * @return
	 */
	public Command getPurchaseHistoryCommand() {
		return new Command() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				showPurchaseHistoryView();
			}
		};
	}
	
	protected void logPurchase(List<IItem> items) {
		IUser user = Registry.get(SongStockUI.USER);
		try {
			purchaseHistoryLogicBean.logUserPurchase(items, user);
		} catch (PurchaseHistoryLogicException e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contributeProcess(Object data) throws Exception {
		if (data instanceof List) {
			List<IItem> items = (List<IItem>) data;
			logPurchase(items);
		}
	}
	
}
