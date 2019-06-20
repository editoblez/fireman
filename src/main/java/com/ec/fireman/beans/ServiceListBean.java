package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ec.fireman.data.entities.Service;

@Named
@SessionScoped
public class ServiceListBean implements Serializable {

	private static final long serialVersionUID = -5468228478359216158L;

	private static final Logger LOG = LogManager.getLogger(ServiceListBean.class);

	private List<Service> services;
	private Service selectedService;

	@PostConstruct
	public void init() {
		this.refreshServices();
		setSelectedService(new Service());
	}

	public void refreshServices() {
		// TODO:
		setServices(new ArrayList<>());
		Service item = new Service();
		item.setId(1);
		item.setName("Servicio de auditoría.");
		getServices().add(item);
	}

	public void createService() {
		// TODO: SAVE USER
		//this.refreshServices();
		services.add(selectedService);
		setSelectedService(new Service());
	}

	public void editService() {
		// TODO: SAVE USER
		//this.refreshServices();
		setSelectedService(new Service());
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public Service getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(Service selectedService) {
		this.selectedService = selectedService;
	}

}
