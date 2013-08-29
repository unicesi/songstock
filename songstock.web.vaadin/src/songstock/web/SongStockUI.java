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
package songstock.web;

import songstock.web.client.ContentPanel;
import songstock.web.client.MenuPanel;
import songstock.web.extensions.browsing.BrowsingController;
import songstock.web.extensions.download.DownloadController;
import songstock.web.extensions.login.basic.BasicLoginFormController;
import songstock.web.extensions.payments.PaymentViewController;
import songstock.web.extensions.payments.additionalcharges.tax.TaxController;
import songstock.web.extensions.payments.creditcard.CreditCardFormController;
import songstock.web.extensions.purchasehistory.PurchaseHistoryController;
import songstock.web.extensions.register.addresses.AddressesController;
import songstock.web.extensions.register.info.RegisterForm;
import songstock.web.extensions.register.info.RegisterFormController;
import songstock.web.extensions.searching.basic.SearchController;
import songstock.web.extensions.shoppingcart.ShoppingCartController;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * Main UI class
 * 
 * @author Andrés Paz
 */
@SuppressWarnings("serial")
public class SongStockUI extends UI {

	// Constants used in the registry
	public static final String UI = "ui";
	public static final String MENU_PANEL = "menuPanel";
	public static final String CONTENT_PANEL = "contentPanel";
	public static final String USER = "user";
	public static final String SHOPPING_CART = "ShoppingCart";
	public static final String INVOICE = "invoice";
	public static final String PAYMENT_STAGE = "Payment";
	public static final String DOWNLOAD_STAGE = "Download";
	public static final String PURCHASE_STAGE = "Purchase";
	public static String STAGE = PAYMENT_STAGE;
	
	/** 
	 * Popup window
	 */
	private Window popupWindow;

	/**
	 * Initializes the SongStock UI elements.
	 * @param request 
	 */
	@Override
	protected void init(VaadinRequest request) {

		// Set the browser window title
		getPage().setTitle("SongStock");

		final AbsoluteLayout layout = new AbsoluteLayout();
		layout.setSizeFull();

		// Menu Panel
		MenuPanel menuPanel = new MenuPanel();
		menuPanel.setImmediate(true);
		layout.addComponent(menuPanel, "top:0.0px;left:0.0px;");
		Registry.register(MENU_PANEL, menuPanel);

		// Content Panel
		ContentPanel contentPanel = new ContentPanel();
		contentPanel.setImmediate(true);
		layout.addComponent(contentPanel, "top:23.0px;left:0.0px;");
		Registry.register(CONTENT_PANEL, contentPanel);

		setContent(layout);

		Registry.register(UI, this);
		
		// Set the application menus
		
		// USERS SUBSYSTEM:

		// Login Module
		menuPanel.addMenu("Login", BasicLoginFormController.getInstance().getLoginCommand(), true);		
		BasicLoginFormController.getInstance().showLoginForm();
		
		// Register Module
		menuPanel.addMenu("Register", RegisterFormController.getInstance().getRegisterCommand(), true);
		
		// Multiple Addresses (UI contribution to RegisterForm)
		RegisterFormController.getInstance().addContributor(AddressesController.getInstance());
		
		// CATALOG SUBSYSTEM:

		// Browsing Module
		menuPanel.addMenu("Browse Catalog", BrowsingController.getInstance().getBrowsingCommand(), true);
		
		// Basic Search Module
		menuPanel.addMenu("Basic Search", SearchController.getInstance().getBasicSearchCommand(), true);
		
		// PAYMENTS SUBSYSTEM:
		
		// Payments Core Module (Process contribution)
		ShoppingCartController.getInstance().addContributor(PAYMENT_STAGE, PaymentViewController.getInstance());
		
		// Payment Methods (UI contributions)
		PaymentViewController.getInstance().addContributor(CreditCardFormController.getInstance());
		
		// Additional Charges (Content contributions)
		PaymentViewController.getInstance().addContributor(TaxController.getInstance());
				
		// SALES SUBSYSTEM:
		
		// Shopping Cart Module
		menuPanel.addMenu("Shopping Cart", ShoppingCartController.getInstance().getShoppingCartCommand(), false);
		// UI contributions
		BrowsingController.getInstance().addContributor(ShoppingCartController.getInstance());
		SearchController.getInstance().addContributor(ShoppingCartController.getInstance());
		
		// Download Module (Process contribution)
		ShoppingCartController.getInstance().addContributor(DOWNLOAD_STAGE, DownloadController.getInstance());
		
		// Purchase History Module
		menuPanel.addMenu("Purchase History", PurchaseHistoryController.getInstance().getPurchaseHistoryCommand(), false);
		// Process contributions

		ShoppingCartController.getInstance().addContributor(PURCHASE_STAGE, PurchaseHistoryController.getInstance());		
	}

	/**
	 * Shows a tray notification with the given title and message.
	 * @param title of the notification
	 * @param message for the notification
	 */
	public void showNotification(String title, String message) {
		Notification.show(title, message, Type.TRAY_NOTIFICATION);
	}
	
	/**
	 * Shows a warning notification with the given title and message.
	 * @param title of the notification
	 * @param message for the notification
	 */
	public void showWarningNotification(String title, String message) {
		new Notification(title, message, Type.WARNING_MESSAGE, true).show(Page.getCurrent());
	}
	
	/**
	 * Shows a modal popup window with the given title and content.
	 * @param title of the popup window
	 * @param content of the popup window
	 */
	public void showPopupWindow(String title, Component content) {
		// Create a new popup window
		popupWindow = new Window(title);
		popupWindow.setModal(true);
		// Add content to the popup window
		popupWindow.setContent(content);
		// Set the height of the popup window
		popupWindow.setHeight(content.getHeight() + 50, content.getHeightUnits());
		// Set the width of the popup window
		popupWindow.setWidth(content.getWidth(), content.getWidthUnits());
		
		// Add popup window to the app's main window
		addWindow(popupWindow);
	}

}