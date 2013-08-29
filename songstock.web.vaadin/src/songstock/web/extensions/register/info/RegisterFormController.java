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
package songstock.web.extensions.register.info;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import songstock.web.AbstractController;
import songstock.web.Registry;
import songstock.web.SongStockUI;
import songstock.web.client.ContentPanel;

import register.logic.beans.IRegisterLogicBean;
import register.logic.exceptions.RegisterLogicException;
import users.dtos.IUser;

/**
 * Singleton Controller that links the register form and the database services
 * 
 * @author daviddurangiraldo
 * 
 */
public class RegisterFormController extends AbstractController {
	/**
	 * Singleton instance of this object
	 */
	private static RegisterFormController registerFormController;
	/**
	 * Allows access to the register logic services
	 */
	private IRegisterLogicBean registerLogicBean;

	private RegisterFormController() {
		doLookup();

		RegisterForm registerForm = null;
		if (Registry.get(RegisterForm.REGISTER_FORM) != null) {
			registerForm = Registry.get(RegisterForm.REGISTER_FORM);
		} else {
			registerForm = new RegisterForm();
			Registry.register(RegisterForm.REGISTER_FORM, registerForm);
		}
	}

	/**
	 * Does the EJB lookup that contains the implemented register logic services
	 */
	private void doLookup() {
		try {
			InitialContext context = new InitialContext();
			registerLogicBean = (IRegisterLogicBean) context
					.lookup("java:global/users.subsystem/register.logic.ejb/RegisterLogicBean!register.logic.beans.IRegisterLogicBean");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the singleton instance of the controller
	 * 
	 * @return RegisterFormController containing the unique instance of the
	 *         controller
	 */
	public static RegisterFormController getInstance() {
		if (registerFormController == null) {
			registerFormController = new RegisterFormController();
		}
		return registerFormController;
	}

	public void showRegisterForm() {
		RegisterForm registerForm = Registry.get(RegisterForm.REGISTER_FORM);

		IUser user = Registry.get(SongStockUI.USER);
		if(user != null)
			registerForm.setLoginButtonText("Logout");	
		else
			registerForm = new RegisterForm();
		
		ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		contentPanel.removeContent();
		contentPanel.setContent(registerForm);

		Registry.register(RegisterForm.REGISTER_FORM, registerForm);
		
		try {
			extendUI(registerForm, null);
		} catch (Exception e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
	}

	public void doRegister(IUser iUser) {
		RegisterForm registerForm = Registry.get(RegisterForm.REGISTER_FORM);
		try {
			boolean register = registerLogicBean.registerNewUser(iUser);
			if (register)
				registerForm.setErrorMessage("User Registered Successfully");
		} catch (RegisterLogicException e) {
			registerForm.setErrorMessage(e.getMessage());
		}
	}

	public void doUpdate(IUser iUser, String new_pass, String new_pass_confirm) {
		RegisterForm registerForm = Registry.get(RegisterForm.REGISTER_FORM);
		try {
			IUser userLogged = Registry.get(SongStockUI.USER);
			if (iUser.getPassword().equals(userLogged.getPassword())) {
				if (new_pass.equals(new_pass_confirm)) {
					iUser.setPassword(new_pass);
					registerLogicBean.updateUser(iUser);
					registerForm.clear();
					registerForm.setErrorMessage("User Info Updated Successfully");
				} else {
					registerForm
							.setErrorMessage("The New Password and the New Password Confirm do not Match");
				}
			} else {
				registerForm.setErrorMessage("The Current Password do not Match.");
			}
		} catch (RegisterLogicException e) {
			registerForm.setErrorMessage("The Current Password do not Match.");
		}
	}

	public Command getRegisterCommand() {
		return new Command() {

			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				showRegisterForm();
			}
		};
	}
}
