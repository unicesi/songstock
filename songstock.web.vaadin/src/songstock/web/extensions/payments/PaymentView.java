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
package songstock.web.extensions.payments;

import java.text.DecimalFormat;
import java.util.List;

import sales.dtos.IItem;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

/**
 * 
 * @author Andrés Paz
 *
 */
public class PaymentView extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private PaymentMethodsPanel paymentMethodsPanel;

	@AutoGenerated
	private Table tableAdditionalCharges;

	@AutoGenerated
	private Label labelAdditionalCharges;

	@AutoGenerated
	private Label labelItems;

	@AutoGenerated
	private Label labelTotalValue;

	@AutoGenerated
	private Label labelTotal;

	@AutoGenerated
	private Table tableItems;

	@AutoGenerated
	private Label labelTitle;

	private double totalValue;
	
	private DecimalFormat df;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String PAYMENT_VIEW = "paymentView";
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public PaymentView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// User code
		labelTotalValue.setValue("");
		
		tableItems.addContainerProperty("Name", String.class, null);
		tableItems.addContainerProperty("Artist", String.class, null);
		tableItems.addContainerProperty("Type", String.class, null);
		tableItems.addContainerProperty("Price", Double.class, null);

		// Allow selecting items from the table.
		tableItems.setSelectable(true);

		// Send changes in selection immediately to server.
		tableItems.setImmediate(true);
		
		// Set the footers
		tableItems.setFooterVisible(true);
		tableItems.setColumnFooter("Name", "Subtotal");
		
		tableAdditionalCharges.addContainerProperty("Description", String.class, null);
		tableAdditionalCharges.addContainerProperty("Rate", Double.class, null);
		tableAdditionalCharges.addContainerProperty("Value", Double.class, null);

		// Allow selecting items from the table.
		tableAdditionalCharges.setSelectable(true);

		// Send changes in selection immediately to server.
		tableAdditionalCharges.setImmediate(true);
		
		// Set the footers
		tableAdditionalCharges.setFooterVisible(true);
		tableAdditionalCharges.setColumnFooter("Description", "Subtotal");
		
		df = new DecimalFormat("#.##");
	}
	
	public PaymentMethodsPanel getPaymentMethodsPanel() {
		return paymentMethodsPanel;
	}
	
	public Table getTableAdditionalCharges() {
		return tableAdditionalCharges;
	}
	
	public void loadItems(List<IItem> items) {
		totalValue = 0;
		double subtotal = 0;
		if (items != null) {
			for (IItem item : items) {
				addToTotalValue(item.getPrice());
				subtotal += item.getPrice();
				tableItems.addItem(new Object[] { item.getName(), item.getArtist(), item.getType(), item.getPrice() }, item);
			}
		}
		
		tableItems.setColumnFooter("Price", df.format(subtotal));
	}
	
	protected void addToTotalValue(double value) {
		totalValue += value;
		labelTotalValue.setValue(df.format(totalValue));
	}
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// labelTitle
		labelTitle = new Label();
		labelTitle.setImmediate(false);
		labelTitle.setWidth("-1px");
		labelTitle.setHeight("-1px");
		labelTitle.setValue("<b>Payment</b>");
		labelTitle.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
		mainLayout.addComponent(labelTitle, "top:20.0px;left:20.0px;");
		
		// tableItems
		tableItems = new Table();
		tableItems.setImmediate(false);
		tableItems.setWidth("100.0%");
		tableItems.setHeight("180px");
		mainLayout.addComponent(tableItems,
				"top:140.0px;right:20.0px;left:20.0px;");
		
		// labelTotal
		labelTotal = new Label();
		labelTotal.setImmediate(false);
		labelTotal.setWidth("-1px");
		labelTotal.setHeight("-1px");
		labelTotal.setValue("Total: $");
		mainLayout.addComponent(labelTotal, "top:60.0px;left:20.0px;");
		
		// labelTotalValue
		labelTotalValue = new Label();
		labelTotalValue.setImmediate(false);
		labelTotalValue.setWidth("-1px");
		labelTotalValue.setHeight("-1px");
		labelTotalValue.setValue("Label");
		mainLayout.addComponent(labelTotalValue, "top:60.0px;left:68.0px;");
		
		// labelItems
		labelItems = new Label();
		labelItems.setImmediate(false);
		labelItems.setWidth("-1px");
		labelItems.setHeight("-1px");
		labelItems.setValue("<b>Items</b>");
		labelItems.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
		mainLayout.addComponent(labelItems, "top:100.0px;left:20.0px;");
		
		// labelAdditionalCharges
		labelAdditionalCharges = new Label();
		labelAdditionalCharges.setImmediate(false);
		labelAdditionalCharges.setWidth("-1px");
		labelAdditionalCharges.setHeight("-1px");
		labelAdditionalCharges.setValue("<b>Additional Charges</b>");
		labelAdditionalCharges.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
		mainLayout.addComponent(labelAdditionalCharges,
				"top:360.0px;left:20.0px;");
		
		// tableAdditionalCharges
		tableAdditionalCharges = new Table();
		tableAdditionalCharges.setImmediate(false);
		tableAdditionalCharges.setWidth("100.0%");
		tableAdditionalCharges.setHeight("60px");
		mainLayout.addComponent(tableAdditionalCharges,
				"top:400.0px;right:20.0px;left:20.0px;");
		
		// paymentMethodsPanel
		paymentMethodsPanel = new PaymentMethodsPanel();
		paymentMethodsPanel.setImmediate(false);
		mainLayout
				.addComponent(paymentMethodsPanel, "top:60.0px;left:200.0px;");
		
		return mainLayout;
	}

}
