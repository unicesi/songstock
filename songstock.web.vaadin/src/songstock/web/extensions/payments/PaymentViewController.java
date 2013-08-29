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
package songstock.web.extensions.payments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import payments.dtos.IAdditionalCharge;
import payments.dtos.IInvoice;
import payments.logic.beans.IPaymentsLogicBean;

import sales.dtos.IItem;
import songstock.web.AbstractController;
import songstock.web.ProcessContributor;
import songstock.web.Registry;
import songstock.web.SongStockUI;
import songstock.web.client.ContentPanel;
import users.dtos.IUser;

/**
 * @author Andrés Paz
 * 
 */
public class PaymentViewController extends AbstractController implements
		ProcessContributor {

	private static PaymentViewController paymentViewController;

	private IPaymentsLogicBean paymentsLogicBean;

	/**
	 * 
	 */
	private PaymentViewController() {
		doLookup();
	}

	/**
	 * 
	 */
	private void doLookup() {
		try {
			InitialContext context = new InitialContext();
			paymentsLogicBean = (IPaymentsLogicBean) context
					.lookup("java:global/payments.subsystem/payments.logic.ejb/PaymentsLogicBean!payments.logic.beans.IPaymentsLogicBean");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public static PaymentViewController getInstance() {
		if (paymentViewController == null) {
			paymentViewController = new PaymentViewController();
		}
		return paymentViewController;
	}

	protected void showPaymentView(List<IItem> items) {
		PaymentView paymentView = new PaymentView();

		ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		contentPanel.removeContent();
		contentPanel.setContent(paymentView);

		Registry.register(PaymentView.PAYMENT_VIEW, paymentView);

		try {
			extendUI(paymentView.getPaymentMethodsPanel(), null);
		} catch (Exception e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}

		// Load items
		paymentView.loadItems(items);

		// Calculate additional charges
		try {
			extendContent(paymentView.getTableAdditionalCharges(), items);
		} catch (Exception e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}

		// Calculate invoice total value
		paymentView.addToTotalValue(Double.parseDouble(paymentView
				.getTableAdditionalCharges().getColumnFooter("Value")));

		// Generate invoice
		IUser user = Registry.get(SongStockUI.USER);
		Collection<?> collection = paymentView.getTableAdditionalCharges()
				.getItemIds();
		List<IAdditionalCharge> additionalCharges = new ArrayList<>();
		for (Object object : collection) {
			if (object instanceof IAdditionalCharge)
				additionalCharges.add((IAdditionalCharge) object);
		}

		IInvoice invoice = paymentsLogicBean.generateInvoice(items,
				additionalCharges, user);
		Registry.register(SongStockUI.INVOICE, invoice);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contributeProcess(Object data) throws Exception {
		if (data instanceof List) {
			List<IItem> items = (List<IItem>) data;
			showPaymentView(items);
		}
	}

}
