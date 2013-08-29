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
package creditcard.persistence.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import creditcard.persistence.entities.CreditCard;
import creditcard.persistence.entities.CreditCard_Invoice;

import payments.dtos.ICreditCard;
import payments.dtos.IInvoice;
import users.dtos.IUser;

/**
 * Session Bean that implements the ICreditCardPersistenceBean Services
 * 
 * @author daviddurangiraldo
 * 
 */
@Stateless
public class CreditCardPersistenceBean implements ICreditCardPersistenceBean {

	@PersistenceContext(unitName = "payments.persistence.jpa")
	private EntityManager entityManager;

	public CreditCardPersistenceBean() {

	}

	@Override
	public void logPayment(ICreditCard creditCard, IInvoice invoice) {
		CreditCard_Invoice cCI = new CreditCard_Invoice();
		cCI.setCreditCardBean(entityManager.find(CreditCard.class,
				creditCard.getNumber()));
		cCI.setInvoice(invoice.getId());
		entityManager.persist(cCI);
		entityManager.flush();
	}

	@Override
	public ICreditCard getUserCreditCard(IUser user) {
		TypedQuery<CreditCard> query = entityManager.createNamedQuery(
				"creditcard.getUserCreditCard", CreditCard.class);
		query.setParameter("userId", user.getEmail());
		CreditCard foundCreditCard = null;
		try {
			List<CreditCard> resultList = query.getResultList();
			foundCreditCard = resultList.get(0);
			ICreditCard iCreditCard = new payments.dtos.impl.CreditCard();
			iCreditCard.setBillingAddress(foundCreditCard.getBillingAddress());
			iCreditCard.setCardHolderName(foundCreditCard.getCardHolderName());
			iCreditCard.setCity(foundCreditCard.getCity());
			iCreditCard.setCountry(foundCreditCard.getCountry());
			iCreditCard.setExpirationDate(foundCreditCard.getExpirationDate());
			iCreditCard.setNumber(foundCreditCard.getNumber());
			iCreditCard.setPhoneNumber(foundCreditCard.getPhoneNumber());
			iCreditCard.setState(foundCreditCard.getState());
			iCreditCard.setUser(foundCreditCard.getUser());
			iCreditCard.setZipCode(foundCreditCard.getZipCode());
			iCreditCard.setBrandName(foundCreditCard.getCardBrand());
			return iCreditCard;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void updateUserCreditCard(ICreditCard creditCard, IUser user) {
		CreditCard cC = entityManager.find(CreditCard.class,
				creditCard.getNumber());
		if (cC != null) {
			fillCreditCardInfo(creditCard, user, cC);
			entityManager.merge(cC);
			entityManager.flush();
		} else {
			cC = new CreditCard();
			fillCreditCardInfo(creditCard, user, cC);
			entityManager.persist(cC);
			entityManager.flush();
		}
	}

	/**
	 * Adds the specified info to a credit card
	 * 
	 * @param creditCard
	 *            ICreditCard containing the new info
	 * @param user
	 *            IUser containing the credit card's owner
	 * @param cC
	 *            ICreditCard containing the credit card to be updated
	 */
	private void fillCreditCardInfo(ICreditCard creditCard, IUser user,
			CreditCard cC) {
		cC.setBillingAddress(creditCard.getBillingAddress());
		cC.setCardHolderName(creditCard.getCardHolderName());
		cC.setCity(creditCard.getCity());
		cC.setCountry(creditCard.getCountry());
		cC.setExpirationDate(creditCard.getExpirationDate());
		cC.setNumber(creditCard.getNumber());
		cC.setPhoneNumber(creditCard.getPhoneNumber());
		cC.setState(creditCard.getState());
		cC.setUser(user.getEmail());
		cC.setZipCode(creditCard.getZipCode());
		cC.setCardBrand(creditCard.getBrandName());
	}

	@Override
	public void deleteAllCreditCards() {
		TypedQuery<CreditCard_Invoice> queryOne = entityManager
				.createNamedQuery("creditcard.deleteAllCreditCardInvoice",
						CreditCard_Invoice.class);
		queryOne.executeUpdate();
		entityManager.flush();

		TypedQuery<CreditCard> queryTwo = entityManager.createNamedQuery(
				"creditcard.deleteAllCreditCards", CreditCard.class);
		queryTwo.executeUpdate();
		entityManager.flush();
	}

}
