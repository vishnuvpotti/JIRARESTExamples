package com.vishnu.automation.ReportsFromJIRA.Dataclass;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="EmailReports")
public class EmailReports {

	private List<Report> reports;

	@XmlElement(name="Report")
	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	
}
