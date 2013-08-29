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
package songstock.web.extensions.login.basic;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import login.basic.logic.beans.IBasicLoginLogicBean;
import login.basic.logic.exceptions.LoginLogicException;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import songstock.web.Registry;
import songstock.web.SongStockUI;
import songstock.web.client.ContentPanel;
import songstock.web.client.MenuPanel;
import songstock.web.extensions.register.info.RegisterForm;

import users.dtos.IUser;

/**
 * 
 * @author Andrés Paz
 *
 */
public class BasicLoginFormController {

	/**
	 * 
	 */
	private static BasicLoginFormController loginFormController;
	
	/**
	 * 
	 */
	private IBasicLoginLogicBean userLogicBean;
	
	/**
	 * 
	 */
	private BasicLoginFormController() {
		doLookup();
		
		BasicLoginForm loginForm = null;
		if (Registry.get(BasicLoginForm.LOGIN_FORM) != null) {
			loginForm = Registry.get(BasicLoginForm.LOGIN_FORM);
		} else {
			loginForm = new BasicLoginForm();
			Registry.register(BasicLoginForm.LOGIN_FORM, loginForm);
		}
	}
	
	/**
	 * 
	 */
	private void doLookup() 
	{
		try 
		{
			InitialContext context = new InitialContext();
			userLogicBean = (IBasicLoginLogicBean) context.lookup("java:global/users.subsystem/login.basic.logic.ejb/BasicLoginLogicBean!login.basic.logic.beans.IBasicLoginLogicBean");											
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
	public static BasicLoginFormController getInstance() {
		if (loginFormController == null) {
			loginFormController = new BasicLoginFormController();
		}
		return loginFormController;
	}
	
	/**
	 * 
	 */
	public void showLoginForm() {
		BasicLoginForm loginForm = Registry.get(BasicLoginForm.LOGIN_FORM);
		
		ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		contentPanel.removeContent();
		contentPanel.setContent(loginForm);
		
		Registry.register(BasicLoginForm.LOGIN_FORM, loginForm);
	}

	/**
	 * 
	 * @param iUser
	 */
	public void doLogin(IUser iUser) {
		try {
			IUser user = userLogicBean.login(iUser);
			
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", "Welcome " + user.getName());
			
			Registry.register(SongStockUI.USER, user);
			
			BasicLoginForm loginForm = Registry.get(BasicLoginForm.LOGIN_FORM);
			loginForm.clear();
			
			RegisterForm registerForm = Registry.get(RegisterForm.REGISTER_FORM);
			registerForm.setActionUpdate();
			
			MenuPanel menuPanel = Registry.get(SongStockUI.MENU_PANEL);
			try{
				menuPanel.setMenuCaption(RegisterForm.ACTION_REGISTER, "User Info");
			}catch(Exception e){
				menuPanel.setMenuCaption("User Info", RegisterForm.ACTION_REGISTER);
			}
			menuPanel.setMenuCaptionAndCommand("Login", "Logout", getLogoutCommand());
			
			menuPanel.enableMenus();
			
			ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
			contentPanel.removeContent();
			
		} catch (LoginLogicException e) {
			BasicLoginForm loginForm = Registry.get(BasicLoginForm.LOGIN_FORM);
			loginForm.setErrorMessage(e.getMessage());
		}
	}
	
	public void doLogout() {
		Registry.unregister(SongStockUI.USER);
		
		MenuPanel menuPanel = Registry.get(SongStockUI.MENU_PANEL);
		menuPanel.setMenuCaptionAndCommand("Logout", "Login", getLoginCommand());
		
		try {
			menuPanel.setMenuCaption("User Info", RegisterForm.ACTION_REGISTER);
		} catch (Exception e) {
			menuPanel.setMenuCaption(RegisterForm.ACTION_REGISTER, "User Info");
		}
		
		RegisterForm registerForm = Registry.get(RegisterForm.REGISTER_FORM);
		if (registerForm != null) {
			registerForm.setLoginButtonText("Login");
		}
		
		menuPanel.disableMenus();
		
		showLoginForm();
		
		SongStockUI songStockUI = Registry.get(SongStockUI.UI);
		songStockUI.showNotification("SongStock", "Goodbye");
	}

	/**
	 * 
	 * @return
	 */
	public Command getLoginCommand() {
		return new Command() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				showLoginForm();
			}
		};
	}
	
	public Command getLogoutCommand() {
		return new Command() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				doLogout();
			}
		};
	}
}
