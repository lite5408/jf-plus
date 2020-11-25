package com.jf.plus.core.order.entity;

import java.io.Serializable;
import java.util.List;

public class OrderDeliveryExportList implements Serializable{
		private static final long serialVersionUID = 0L;

		public OrderDeliveryExportList() {
			super();
		}
		private List<OrderDeliveryExport> orderDeliveryExports;

		public List<OrderDeliveryExport> getOrderDeliveryExports() {
			return orderDeliveryExports;
		}

		public void setOrderDeliveryExports(List<OrderDeliveryExport> orderDeliveryExports) {
			this.orderDeliveryExports = orderDeliveryExports;
		}
		
	}