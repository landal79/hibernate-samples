package com.sourceallies.payroll.domain;

import java.util.Date;

public interface Auditable {

	String getCreatedBy();
	void setCreatedBy(String username);

    String getUpdatedBy();
	void setUpdatedBy(String username);

    Date getCreatedOn();
	void setCreatedOn(Date creationDate);

    Date getUpdatedOn();
	void setUpdatedOn(Date updateDate);
}
