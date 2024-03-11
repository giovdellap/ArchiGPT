package it.yourstore.store.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class GenericEntity extends GenericObject {

	
	protected String getStringCheckedAgainstNullContent(String token) {
		return ("null".equals(token)) ? null : token;
	}

	protected String getStringAsString(String value) {
		return value;
	}

	protected Byte getByteCheckedAgainstNullContent(String token) {
		return ("null".equals(token)) ? null : new Byte(token);
	}

	protected String getByteAsString(Byte value) {
		return (value == null) ? "null" : value.toString();
	}

	protected Short getShortCheckedAgainstNullContent(String token) {
		return ("null".equals(token)) ? null : new Short(token);
	}

	protected String getShortAsString(Short value) {
		return (value == null) ? "null" : value.toString();
	}

	protected Integer getIntegerCheckedAgainstNullContent(String token) {
		return ("null".equals(token)) ? null : new Integer(token);
	}

	protected String getIntegerAsString(Integer value) {
		return (value == null) ? "null" : value.toString();
	}

	protected Long getLongCheckedAgainstNullContent(String token) {
		return ("null".equals(token)) ? null : new Long(token);
	}

	protected String getLongAsString(Long value) {
		return (value == null) ? "null" : value.toString();
	}

	protected BigInteger getBigIntegerCheckedAgainstNullContent(String token) {
		return ("null".equals(token)) ? null : new BigInteger(token);
	}

	protected String getBigIntegerAsString(BigInteger value) {
		return (value == null) ? "null" : value.toString();
	}

	protected BigDecimal getBigDecimalCheckedAgainstNullContent(String token) {
		return ("null".equals(token)) ? null : new BigDecimal(token);
	}

	protected String getBigDecimalAsString(BigDecimal value) {
		return (value == null) ? "null" : value.toString();
	}

	protected Boolean equalsPreliminaryCheck(Object o) {
		if (this == o) {
			return Boolean.TRUE;
		}
		if (o == null || getClass() != o.getClass()) {
			return Boolean.FALSE;
		}
		return null;
	}

	protected Boolean equalsAttributeCheck(Object thisAttribute, Object otherAttribute) {
		if (thisAttribute != null ? !thisAttribute.equals(otherAttribute) : otherAttribute != null) {
			return Boolean.FALSE;
		}
		return null;
	}

	protected int hashCodeFor(Object attribute) {
		return (attribute != null ? attribute.hashCode() : 0);
	}

	public java.sql.Timestamp getCurrentTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	protected Object emptyIfNull(Object value) {
		if (value == null) {
			return "";
		}
		return value.toString();
	}

	public boolean isValueChanged(Object currentValue, Object newValue, boolean ignoreCase) {
		if (currentValue == null) {
			return (newValue != null);
		} else {
			if (currentValue instanceof BigDecimal) {
				if (newValue == null) {
					return true;
				}
				return ((BigDecimal) currentValue).compareTo(((BigDecimal) newValue)) != 0;
			} else if (currentValue instanceof Date) {
				if (newValue == null) {
					return true;
				}
				return !DateUtils.isSameInstant((Date) currentValue, (Date) newValue);
			} else {
				if (ignoreCase) {
					return (!((String) currentValue).equalsIgnoreCase((String) newValue));
				} else {
					return (!currentValue.equals(newValue));
				}
			}
		}
	}
	
	public String getRowIdFieldDelimiter() {
		return "~";
	}
}
