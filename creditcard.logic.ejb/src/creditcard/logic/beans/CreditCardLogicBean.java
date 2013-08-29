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
package creditcard.logic.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import creditcard.logic.exceptions.CreditCardLogicException;
import creditcard.persistence.beans.ICreditCardPersistenceBean;

import payments.dtos.ICreditCard;
import payments.dtos.IInvoice;
import payments.persistence.beans.IPaymentsPersistenceBean;
import users.dtos.IUser;

/**
 * Session Bean that implements the ICreditCardLogicBean Services
 * 
 * @author daviddurangiraldo
 */
@Stateless
public class CreditCardLogicBean implements Serializable, ICreditCardLogicBean {

	private static final long serialVersionUID = 1L;

	/**
	 * Bean that contains the basic persistence services
	 */
	@EJB
	private ICreditCardPersistenceBean iCreditCardPersistenceBean;
	
	@EJB
	private IPaymentsPersistenceBean iPaymentsPresistenceBean;

	/**
	 * Default constructor.
	 */
	public CreditCardLogicBean() {

	}

	@Override
	public void authorizeAndCapture(ICreditCard creditCard, IInvoice invoice) {
		// Web service execution and validation is missing!!!
		invoice.setPaymentState(IInvoice.APPROVED_STATE);
		invoice.setPaymentMethod(IInvoice.CREDIT_CARD_METHOD);
		iCreditCardPersistenceBean.logPayment(creditCard, invoice);
		iPaymentsPresistenceBean.updateInvoice(invoice);
	}

	@Override
	public ICreditCard getUserCreditCard(IUser user)
			throws CreditCardLogicException {
		ICreditCard creditCard = iCreditCardPersistenceBean
				.getUserCreditCard(user);
		if (creditCard == null)
			throw new CreditCardLogicException(
					"The User does not have a credit card");
		else
			return creditCard;
	}

	@Override
	public void updateUserCreditCard(ICreditCard creditCard, IUser user) {
		iCreditCardPersistenceBean.updateUserCreditCard(creditCard, user);
	}

}
