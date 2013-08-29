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
package songstock.web.extensions.payments.additionalcharges.tax;

import java.text.DecimalFormat;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import payments.dtos.IAdditionalCharge;

import additionalcharges.tax.logic.beans.ITaxLogicBean;

import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

import songstock.web.AbstractController;
import songstock.web.ContentContributor;
import songstock.web.Registry;
import songstock.web.SongStockUI;
import songstock.web.extensions.payments.PaymentViewController;
import songstock.web.extensions.shoppingcart.ShoppingCartController;
import users.dtos.IUser;

/**
 * @author Andrés Paz
 *
 */
public class TaxController extends AbstractController implements ContentContributor {

	private static TaxController taxController;
	
	private ITaxLogicBean taxLogicBean;
	
	/**
	 * 
	 */
	public TaxController() {
		doLookup();
	}

	private void doLookup() {
		try 
		{
			InitialContext context = new InitialContext();
			taxLogicBean = (ITaxLogicBean) context.lookup("java:global/payments.subsystem/additionalcharges.tax.logic.ejb/TaxLogicBean!additionalcharges.tax.logic.beans.ITaxLogicBean");											
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
	public static TaxController getInstance() {
		if (taxController == null) {
			taxController = new TaxController();
		}
		return taxController;
	}

	/* (non-Javadoc)
	 * @see songstock.web.UIContributor#contributeTo(com.vaadin.ui.Component, java.lang.Object)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void contributeContentTo(Component component, Object data) throws Exception {
		if (component instanceof Table) {
			Table table = (Table) component;
			double subtotal = 0;
			table.removeAllItems();
			if (data instanceof List) {
				List items = (List) data;
				IAdditionalCharge additionalCharge = taxLogicBean.calculateTax(items);
				table.addItem(new Object[] { additionalCharge.getDescription(), additionalCharge.getRate(), additionalCharge.getValue() }, additionalCharge);
				
				DecimalFormat df = new DecimalFormat("#.##");
				subtotal = additionalCharge.getValue();
				table.setColumnFooter("Value", df.format(subtotal));
			} else {
				throw new Exception("Data type not supported");
			}
		} else {
			throw new Exception("Component type not supported");
		}
	}

}
