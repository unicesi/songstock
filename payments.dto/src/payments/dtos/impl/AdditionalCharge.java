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
package payments.dtos.impl;

import java.io.Serializable;

import payments.dtos.IAdditionalCharge;

/**
 * DTO implementation class for DTO: AdditionalCharge
 * 
 * @author daviddurangiraldo
 * 
 */
public class AdditionalCharge implements Serializable, IAdditionalCharge {

	private static final long serialVersionUID = 1L;

	private String id;

	private String description;

	private Double rate;

	private double value;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Double getRate() {
		return rate;
	}

	@Override
	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}

}
