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
package songstock.web.extensions.shoppingcart;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import catalog.dtos.impl.Album;
import catalog.dtos.impl.Song;

import com.vaadin.data.Property;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

import sales.dtos.IItem;
import shoppingcart.logic.beans.IShoppingCartLogicBean;
import shoppingcart.logic.exceptions.ShoppingCartLogicException;
import songstock.web.AbstractController;
import songstock.web.Registry;
import songstock.web.SongStockUI;
import songstock.web.UIContributor;
import songstock.web.client.ContentPanel;
import users.dtos.IUser;

/**
 * @author Andrés Paz
 *
 */
public class ShoppingCartController extends AbstractController implements UIContributor {

	private static final String UI_CONTRIBUTION = "Buy";
	
	/**
	 * 
	 */
	private static ShoppingCartController shoppingCartController;
	
	/**
	 * 
	 */
	private IShoppingCartLogicBean shoppingCartLogicBean;
	
	/**
	 * 
	 */
	public ShoppingCartController() {
		doLookup();
	}

	/**
	 * 
	 */
	private void doLookup() {
		try {
			InitialContext context = new InitialContext();
			shoppingCartLogicBean = (IShoppingCartLogicBean) context.lookup("java:global/sales.subsystem/shoppingcart.logic.ejb/ShoppingCartLogicBean!shoppingcart.logic.beans.IShoppingCartLogicBean");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static ShoppingCartController getInstance() {
		if (shoppingCartController == null) {
			shoppingCartController = new ShoppingCartController();
		}
		return shoppingCartController;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void contributeUITo(Component component, Object data) throws Exception {
		if (component instanceof Table) {
			Table table = (Table) component;
			table.addContainerProperty(UI_CONTRIBUTION, Button.class, null);
			if (data instanceof List) {
				List itemIds = (List) data;
				for (Object itemId : itemIds) {
					Button buttonAddToCart = new Button("Add to Cart");
					buttonAddToCart.setData(itemId);
					buttonAddToCart.addClickListener(new ClickListener() {
						
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(ClickEvent event) {
							ShoppingCartController.getInstance().addItem(event.getButton().getData());
						}
					});
					Property p = table.getItem(itemId).getItemProperty(UI_CONTRIBUTION);
					IUser user = Registry.get(SongStockUI.USER);
					if(user == null)
						buttonAddToCart.setEnabled(false);
					else
						buttonAddToCart.setEnabled(true);
					p.setValue(buttonAddToCart);
				}
			} else {
				throw new Exception("Data type not supported");
			}
		} else {
			throw new Exception("Component type not supported");
		}
		
	}
	
	protected void showShoppingCartView() {
		ShoppingCartView shoppingCartView = new ShoppingCartView();
		
		ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		contentPanel.removeContent();
		contentPanel.setContent(shoppingCartView);
		
		Registry.register(ShoppingCartView.SHOPPING_CART_VIEW, shoppingCartView);
		
		try {
			IUser user = Registry.get(SongStockUI.USER);
			List<IItem> items = shoppingCartLogicBean.getAllItems(user);
			shoppingCartView.loadItems(items);
		} catch (ShoppingCartLogicException e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Command getShoppingCartCommand() {
		return new Command() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				showShoppingCartView();
			}
		};
	}
	
	public void addItem(Object item) {
		IUser user = Registry.get(SongStockUI.USER);
		try {
			IItem iItem = item instanceof Album ? ((Album) item).toItem() : ((Song) item).toItem();
			shoppingCartLogicBean.addItem(iItem, user);
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", "Item added to your cart.");
		} catch (ShoppingCartLogicException e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
	}
	
	public void removeItem(IItem item){
		IUser user = Registry.get(SongStockUI.USER);
		try {
			shoppingCartLogicBean.removeItem(item, user);
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", "Item removed from your cart.");
		} catch (ShoppingCartLogicException e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void emptyShoppingCart() {
		IUser user = Registry.get(SongStockUI.USER);
		
		try {
			shoppingCartLogicBean.empty(user);
		
			ShoppingCartView shoppingCartView = Registry.get(ShoppingCartView.SHOPPING_CART_VIEW);
			shoppingCartView.clear();
		} catch (ShoppingCartLogicException e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
	}

	public void checkoutItems() {
		IUser user = Registry.get(SongStockUI.USER);
		
		try {
			List<IItem> items = shoppingCartLogicBean.checkoutAllItems(user);
			
			// Checkout process contributions
			
			try {
				extendProcess(SongStockUI.DOWNLOAD_STAGE, items);
				extendProcess(SongStockUI.PURCHASE_STAGE, items);
			} catch (Exception e) {
				e.printStackTrace();
				SongStockUI songStockUI = Registry.get(SongStockUI.UI);
				songStockUI.showNotification("SongStock", e.getMessage());
			}
			
			ShoppingCartView shoppingCartView = Registry.get(ShoppingCartView.SHOPPING_CART_VIEW);
			shoppingCartView.clear();
		} catch (ShoppingCartLogicException e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
	}

	public List<IItem> getShoppingCartItems(){
		IUser user = Registry.get(SongStockUI.USER);
		List<IItem> items = null;
		try {
			items = shoppingCartLogicBean.getUserShoppingCart(user).getItems();
			
			try {
				if(items.size() > 0){
					extendProcess(SongStockUI.PAYMENT_STAGE, items);
				}else{
					SongStockUI songStockUI = Registry.get(SongStockUI.UI);
					songStockUI.showNotification("SongStock", "There are no items to checkout");
				}
			} catch (Exception e) {
				e.printStackTrace();
				SongStockUI songStockUI = Registry.get(SongStockUI.UI);
				songStockUI.showNotification("SongStock", e.getMessage());
			}
		} catch (ShoppingCartLogicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
}
