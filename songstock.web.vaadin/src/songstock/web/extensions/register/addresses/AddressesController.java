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
package songstock.web.extensions.register.addresses;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.vaadin.ui.Component;

import songstock.web.AbstractController;
import songstock.web.Registry;
import songstock.web.SongStockUI;
import songstock.web.UIContributor;
import songstock.web.extensions.register.info.RegisterForm;
import users.dtos.IUser;

/**
 * @author Andrés Paz
 *
 */
public class AddressesController extends AbstractController implements UIContributor {

	private static AddressesController addressesController;
	
//	private IAddressesLogicBean addressesLogicBean;
	
	private AddressesController() {
		doLookup();
	}

	private void doLookup() {
		try 
		{
			InitialContext context = new InitialContext();
//			addressesLogicBean = (IAddressesLogicBean) context.lookup("java:global/users.subsystem/addresses.logic.ejb/AddressesLogicBean!addresses.logic.beans.IAddressesLogicBean");											
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
	public static AddressesController getInstance() {
		if (addressesController == null) {
			addressesController = new AddressesController();
		}
		return addressesController;
	}

	/* (non-Javadoc)
	 * @see songstock.web.UIContributor#contributeUITo(com.vaadin.ui.Component, java.lang.Object)
	 */
	@Override
	public void contributeUITo(Component component, Object data) throws Exception {
		// TODO contribute UI to RegisterForm on ACTION_UPDATE
		if (component instanceof RegisterForm) {
			RegisterForm registerForm = (RegisterForm) component;
			AddressesListView addressesListView = new AddressesListView();
			registerForm.addUIComponent(addressesListView, null);
		} else {
			throw new Exception("Component type not supported");
		}
	}
	
	public void showAddressForm(/*IAddress address*/) {
		AddressForm addressForm = new AddressForm();
		
		String title = "Add Address";
		
//		if (address != null) {
//			addressForm.loadAddress(/*address*/);
//			title = "Update Address";
//		}
		
		SongStockUI songStockUI = Registry.get(SongStockUI.UI);
		songStockUI.showPopupWindow(title, addressForm);
	}
	
	public void doSave(/*IAddress address*/) {
		IUser user = Registry.get(SongStockUI.USER);
		// TODO save address
	}
	
	public void doRemove(/*IAddress address*/) {
		IUser user = Registry.get(SongStockUI.USER);
		// TODO remove address
	}
}
