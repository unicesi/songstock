package creditcard.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the CreditCards database table.
 * @author daviddurangiraldo
 *
 */
@Entity
@Table(name="CreditCards")
@NamedQueries({ 
	@NamedQuery(name = "creditcard.deleteAllCreditCards", query = "DELETE FROM CreditCard cC"),
	@NamedQuery(name = "creditcard.getUserCreditCard", query = "SELECT cC FROM CreditCard cC WHERE cC.user=:userId")
	})
public class CreditCard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String number;

	private String billingAddress;

	private String cardBrand;

	private String cardHolderName;

	private String city;

	private String country;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;

	private String phoneNumber;

	private String state;

	private String user;

	private String zipCode;

	//bi-directional many-to-one association to CreditCard_Invoice
	@OneToMany(mappedBy="creditCardBean")
	private List<CreditCard_Invoice> creditCardsInvoices;

	public CreditCard() {
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getBillingAddress() {
		return this.billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getCardBrand() {
		return this.cardBrand;
	}

	public void setCardBrand(String cardBrand) {
		this.cardBrand = cardBrand;
	}

	public String getCardHolderName() {
		return this.cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<CreditCard_Invoice> getCreditCardsInvoices() {
		return this.creditCardsInvoices;
	}

	public void setCreditCardsInvoices(List<CreditCard_Invoice> creditCardsInvoices) {
		this.creditCardsInvoices = creditCardsInvoices;
	}

}