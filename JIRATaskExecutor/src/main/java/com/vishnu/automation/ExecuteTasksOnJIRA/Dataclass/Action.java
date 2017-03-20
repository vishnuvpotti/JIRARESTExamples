package com.vishnu.automation.ExecuteTasksOnJIRA.Dataclass;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Action")
public class Action {

	private Parameter parameterList;

	@XmlElement(name = "Parameter")
	public Parameter getParameterList() {
		return parameterList;
	}

	public void setParameterList(Parameter parameterList) {
		this.parameterList = parameterList;
	}

}
