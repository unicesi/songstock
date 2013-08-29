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
package sales.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the PurchaseLogs database table.
 * 
 * @author daviddurangiraldo
 */
@Entity
@Table(name = "PurchaseLogs")
@NamedQueries({ 
	@NamedQuery(name = "sales.getUserPurchaseHistory", query = "SELECT ph FROM PurchaseLog ph WHERE ph.id.user=:userId"),
	@NamedQuery(name = "sales.deleteUserPurchaseHistory", query = "DELETE FROM PurchaseLog ph WHERE ph.id.user=:userId")
	})
public class PurchaseLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PurchaseLogPK id;

	//bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name="item", insertable=false, updatable=false)
	private Item itemBean;

	public PurchaseLog() {
	}

	public PurchaseLogPK getId() {
		return this.id;
	}

	public void setId(PurchaseLogPK id) {
		this.id = id;
	}

	public Item getItemBean() {
		return this.itemBean;
	}

	public void setItemBean(Item itemBean) {
		this.itemBean = itemBean;
	}

}