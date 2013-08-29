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

/**
 * Interface definition for DTO: AdditionalCharge
 * 
 * @author daviddurangiraldo
 * 
 */
public interface IAdditionalCharge {

	/**
	 * Identifier for the tax charge
	 */
	public static final String TAX_CHARGE = "Tax Charge";
	/**
	 * Identifier for the cloud storage charge
	 */
	public static final String CLOUD_STORAGE_CHARGE = "Cloud Storage Charge";

	/**
	 * Gets the additional charge's id
	 * 
	 * @return String containing the additional charge's id
	 */
	public String getId();

	/**
	 * Sets the additional charge's id
	 * 
	 * @param id
	 *            String containing the new additional charge's id
	 */
	public void setId(String id);

	/**
	 * Gets the additional charge's description
	 * 
	 * @return String containing the additional charge's description
	 */
	public String getDescription();

	/**
	 * Sets the additional charge's description
	 * 
	 * @param description
	 *            String containing the new additional charge's description
	 */
	public void setDescription(String description);

	/**
	 * Gets the additional charge's rate percentage
	 * 
	 * @return Double containing the additional charge's rate percentage
	 */
	public Double getRate();

	/**
	 * Sets the additional charge's rate
	 * 
	 * @param rate
	 *            Double containing the new additional charge's rate
	 */
	public void setRate(Double rate);

	/**
	 * Gets the additional charge's value
	 * 
	 * @return double containing the additional charge's value
	 */
	public double getValue();

	/**
	 * Sets the additional charge's value
	 * 
	 * @param value
	 *            double containing the new additional charge's value
	 */
	public void setValue(double value);
}
