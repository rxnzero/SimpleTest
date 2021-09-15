package com.eactive.eai.batch.util;

public class LayoiutItemRow {
	String itemName;
	String itemDescr;
	int itemDepth;
	String itemType = "";
	String itemDataType = "";
	int itemLength;
	String encYn = "N";
	
	public LayoiutItemRow() {
		super();
	}

	public LayoiutItemRow(String itemName, String itemDescr, int itemDepth, String itemType, String itemDataType,
			int itemLength, String encYn) {
		super();
		this.itemName = itemName;
		this.itemDescr = itemDescr;
		this.itemDepth = itemDepth;
		this.itemType = itemType;
		this.itemDataType = itemDataType;
		this.itemLength = itemLength;
		this.encYn = encYn;
	}
	
	public LayoiutItemRow(String itemName, String itemDescr, int itemDepth, String itemType) {
		super();
		this.itemName = itemName;
		this.itemDescr = itemDescr;
		this.itemDepth = itemDepth;
		this.itemType = itemType;
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public int getItemDepth() {
		return itemDepth;
	}

	public void setItemDepth(int itemDepth) {
		this.itemDepth = itemDepth;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemDataType() {
		return itemDataType;
	}

	public void setItemDataType(String itemDataType) {
		this.itemDataType = itemDataType;
	}

	public int getItemLength() {
		return itemLength;
	}

	public void setItemLength(int itemLength) {
		this.itemLength = itemLength;
	}

	public String getEncYn() {
		return encYn;
	}

	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}
	
	public void setContent(String content) {
		this.encYn = content.substring(0, 1);
		this.itemDataType = content.substring(1, 2);
		this.itemLength = Integer.parseInt(content.substring(2));
	}
	
	@Override
	public String toString() {
		return "LayoiutItemRow [itemName=" + itemName + ", itemDescr=" + itemDescr + ", itemDepth=" + itemDepth
				+ ", itemType=" + itemType + ", itemDataType=" + itemDataType + ", itemLength=" + itemLength
				+ ", encYn=" + encYn + "]";
	}

	public String toCsvString() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%2d", itemDepth)).append(",");
		sb.append(String.format("%10s", itemType)).append(",");
		sb.append(String.format("%10s", itemDataType)).append(",");
		sb.append(String.format("%5d", itemLength)).append(",");
		sb.append(String.format("%1s", encYn)).append(",");
		sb.append(itemName);
		
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + itemDepth;
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((itemType == null) ? 0 : itemType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LayoiutItemRow other = (LayoiutItemRow) obj;
		if (itemDepth != other.itemDepth)
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
//		if (itemType == null) {
//			if (other.itemType != null)
//				return false;
//		} else if (!itemType.equals(other.itemType))
//			return false;
		return true;
	}
	
	
}
