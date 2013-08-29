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
package payments.dtos;

import java.util.Date;
import java.util.List;

import sales.dtos.IItem;

/**
 * Interface definition for DTO: Invoice
 * 
 * @author daviddurangiraldo
 * 
 */
public interface IInvoice {

	/**
	 * Approved payment sate
	 */
	public static final String APPROVED_STATE = "Approved";
	/**
	 * Pending payment state
	 */
	public static final String PENDING_STATE = "Pending";
	/**
	 * Credit card payment method
	 */
	public static final String CREDIT_CARD_METHOD = "Credit Card";
	/**
	 * Debit card payment method
	 */
	public static final String DEBIT_CARD_METHOD = "Debir Card";
	/**
	 * Gift card payment method
	 */
	public static final String GIFT_CARD_METHOD = "Gift Card";
	/**
	 * Paypal payment method
	 */
	public static final String PAYPAL_METHOD = "Paypal";

	/**
	 * Gets the invoice's id
	 * 
	 * @return Integer containing the Gets the invoice's id
	 */
	public Integer getId();

	/**
	 * Sets the invoice's id
	 * 
	 * @param id
	 *            Integer containing the new invoice's id
	 */
	public void setId(Integer id);

	/**
	 * Gets the invoice's date
	 * 
	 * @return Date containing the invoice's date
	 */
	public Date getDate();

	/**
	 * Sets the invoice's date
	 * 
	 * @param date
	 *            Date containing the new invoice's date
	 */
	public void setDate(Date date);

	/**
	 * Gets the invoice's payment method selected
	 * 
	 * @return String containing the selected invoice's payment method
	 */
	public String getPaymentMethod();

	/**
	 * Sets the invoice's payment method
	 * 
	 * @param paymentMethod
	 *            String containing the new invoice's payment method
	 */
	public void setPaymentMethod(String paymentMethod);

	/**
	 * Gets the invoice's payment state
	 * 
	 * @return String containing the invoice's payment state
	 */
	public String getPaymentState();

	/**
	 * Sets the invoice's payment state
	 * 
	 * @param paymentState
	 *            String containing the new invoice's payment state
	 */
	public void setPaymentState(String paymentState);

	/**
	 * Gets the the invoice's associated user
	 * 
	 * @return String containing the invoice's associated user
	 */
	public String getUser();

	/**
	 * Sets the the invoice's associated user
	 * 
	 * @param user
	 *            String containing the new invoice's associated user
	 */
	public void setUser(String user);

	/**
	 * Gets the invoice's value
	 * 
	 * @return double containing the invoice's value
	 */
	public double getValue();

	/**
	 * Sets the invoice's value
	 * 
	 * @param value
	 *            double containing the new invoice's value
	 */
	public void setValue(double value);

	/**
	 * Gets the list of the additional charges related to the invoice
	 * 
	 * @return List<IAdditionalCharge> containing the additional charges related
	 *         to the invoice
	 */
	public List<IAdditionalCharge> getAdditionalCharges();

	/**
	 * Sets the list of the additional charges related to the invoice
	 * 
	 * @param additionalCharges
	 *            List<IAdditionalCharge> containing the new additional charges
	 *            related to the invoice
	 */
	public void setAdditionalCharges(List<IAdditionalCharge> additionalCharges);

	/**
	 * Gets the list of the items related to the invoice
	 * 
	 * @return List<IItem> containing the list of the items related to the
	 *         invoice
	 */
	public List<IItem> getItems();

	/**
	 * Sets the list of items associated to the invoice
	 * 
	 * @param item
	 *            List<IItem> containing the new list of items
	 */
	public void setItems(List<IItem> items);
}
