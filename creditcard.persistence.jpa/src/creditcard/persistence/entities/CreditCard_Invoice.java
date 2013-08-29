package creditcard.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CreditCards_Invoices database table.
 * @author daviddurangiraldo
 *
 */
@Entity
@Table(name="CreditCards_Invoices")
@NamedQueries({ 
	@NamedQuery(name = "creditcard.deleteAllCreditCardInvoice", query = "DELETE FROM CreditCard_Invoice cCI")
	})
public class CreditCard_Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int invoice;

	//bi-directional many-to-one association to CreditCard
	@ManyToOne
	@JoinColumn(name="creditCard")
	private CreditCard creditCardBean;

	public CreditCard_Invoice() {
	}

	public int getInvoice() {
		return this.invoice;
	}

	public void setInvoice(int invoice) {
		this.invoice = invoice;
	}

	public CreditCard getCreditCardBean() {
		return this.creditCardBean;
	}

	public void setCreditCardBean(CreditCard creditCardBean) {
		this.creditCardBean = creditCardBean;
	}

}