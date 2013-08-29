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
package songstock.web.extensions.payments.creditcard;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import payments.dtos.ICreditCard;
import payments.dtos.IInvoice;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import creditcard.logic.beans.ICreditCardLogicBean;
import creditcard.logic.exceptions.CreditCardLogicException;

import songstock.web.AbstractController;
import songstock.web.Registry;
import songstock.web.SongStockUI;
import songstock.web.UIContributor;
import songstock.web.client.ContentPanel;
import songstock.web.extensions.payments.PaymentMethodsPanel;
import songstock.web.extensions.shoppingcart.ShoppingCartController;
import users.dtos.IUser;

/**
 * @author Andrés Paz
 *
 */
public class CreditCardFormController extends AbstractController implements UIContributor {

	private static final String UI_CONTRIBUTION = "Credit Card";
	
	private static CreditCardFormController creditCardFormController;
	
	private ICreditCardLogicBean creditCardLogicBean;
	
	/**
	 * 
	 */
	private CreditCardFormController() {
		doLookup();
	}

	private void doLookup() {
		try 
		{
			InitialContext context = new InitialContext();
			creditCardLogicBean = (ICreditCardLogicBean) context.lookup("java:global/payments.subsystem/creditcard.logic.ejb/CreditCardLogicBean!creditcard.logic.beans.ICreditCardLogicBean");											
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public static CreditCardFormController getInstance() {
		if (creditCardFormController == null) {
			creditCardFormController = new CreditCardFormController();
		}
		return creditCardFormController;
	}

	/* (non-Javadoc)
	 * @see songstock.web.UIContributor#contributeUITo(com.vaadin.ui.Component, java.lang.Object)
	 */
	@Override
	public void contributeUITo(Component component, Object data) throws Exception {
		if (component instanceof PaymentMethodsPanel) {
			PaymentMethodsPanel paymentMethodsPanel = (PaymentMethodsPanel) component;

			Button buttonCreditCard = new Button(UI_CONTRIBUTION);
			buttonCreditCard.addClickListener(new ClickListener() {
						
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					showCreditCardForm();
				}
			});
			paymentMethodsPanel.addPaymentMethod(buttonCreditCard);
		} else {
			throw new Exception("Component type not supported");
		}
	}
	
	protected void showCreditCardForm() {
		CreditCardForm creditCardForm = new CreditCardForm();
		
		ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		contentPanel.removeContent();
		contentPanel.setContent(creditCardForm);
		
		Registry.register(CreditCardForm.PAYMENT_METHOD, creditCardForm);
		
		// Load the user's credit card
		try {
			IUser user = Registry.get(SongStockUI.USER);
			ICreditCard creditCard = creditCardLogicBean.getUserCreditCard(user);
			creditCardForm.loadCreditCard(creditCard);
		} catch (CreditCardLogicException e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
	}
	
	public void doPay(ICreditCard creditCard) {
		IUser user = Registry.get(SongStockUI.USER);
		creditCardLogicBean.updateUserCreditCard(creditCard, user);

		IInvoice invoice = Registry.get(SongStockUI.INVOICE);
		creditCardLogicBean.authorizeAndCapture(creditCard, invoice);
		
		ShoppingCartController.getInstance().checkoutItems();
	}
}
