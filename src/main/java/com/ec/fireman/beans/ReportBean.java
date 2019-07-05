package com.ec.fireman.beans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.ec.fireman.data.dao.InspectionFireExtinguisherDao;
import com.ec.fireman.data.dao.InspectionHeaderDao;
import com.ec.fireman.data.entities.InspectionFireExtinguisher;
import com.ec.fireman.data.entities.InspectionHeader;
import com.ec.fireman.util.MessageUtil;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Data
@Log4j2
@Named
@ViewScoped
public class ReportBean implements Serializable {

  private static final long serialVersionUID = 3650632184377124942L;

  @Inject
  private InspectionHeaderDao inspectionHeaderDao;
  @Inject
  private InspectionFireExtinguisherDao inspectionFireExtinguisherDao;

  private List<InspectionHeader> inspectionList;

  @PostConstruct
  public void init() {
  }
  
  public void buildReport(ActionEvent event) {
    //buscar info de la inspeccion
    InspectionHeader inspection = inspectionHeaderDao.findById(1);
    //buscar data de extintores por inspeccion
    List<InspectionFireExtinguisher> extinguishers =  inspectionFireExtinguisherDao.findAll();

    if (inspection != null) {
      String nombreArchivo = "INSPECCION";

      Map<String, Object> params = new HashMap<String, Object>();
      params.put("concrete", inspection.isConcrete());
      params.put("metallicStructure", inspection.isMetallicStructure());
      params.put("mixed", inspection.isMixed());
      params.put("block", inspection.isBlock());
      params.put("brick", inspection.isBrick());
      params.put("adobe", inspection.isAdobe());
      params.put("installationsGood", inspection.isInstallationsGood());
      params.put("installationsBad", inspection.isInstallationsBad());
      params.put("installationsInternal", inspection.isInstallationsInternal());
      params.put("installationsExternal", inspection.isInstallationsExternal());
      params.put("ventilationNatural", inspection.isVentilationNatural());
      params.put("ventilationMechanic", inspection.isVentilationMechanic());
      params.put("ventilationAdequate", inspection.isVentilationAdequate());
      params.put("ventilationScarce", inspection.isVentilationScarce());
      params.put("knowledgeExtinction", inspection.isKnowledgeExtinction());
      params.put("alarms", inspection.isAlarms());
      params.put("fireDetectors", inspection.isFireDetectors());
      params.put("smokeDetectors", inspection.isSmokeDetectors());
      params.put("emergencyLights", inspection.isEmergencyLights());
      params.put("riskFire", inspection.getRiskFire());
      params.put("recommendations", inspection.getRecommendations());
      params.put("observations", inspection.getObservations());

      JRBeanCollectionDataSource data = extinguishers != null && !extinguishers.isEmpty() ? new JRBeanCollectionDataSource(extinguishers) : null;

      try {
        this.generatePDF(nombreArchivo, "inspection.jrxml", data, params);
      } catch (Exception e) {
        log.error(e.getMessage());
        MessageUtil.errorFacesMessage("Reporte", "Error al generar el reporte.");
      }
    } else {
      MessageUtil.warningFacesMessage("Reporte", "No existe información");
    }
  }

  public void generatePDF(String fileName, String templateName, JRBeanCollectionDataSource data,
      Map<String, Object> params) throws JRException, IOException {
    try {
      FacesContext context = FacesContext.getCurrentInstance();
      String path = context.getExternalContext().getRealPath(File.separator) + File.separator + "resources"
          + File.separator + "reportes" + File.separator + templateName;
      HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
      JasperReport jasperReport = JasperCompileManager.compileReport(path);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, data);
      response.addHeader("Content-disposition", "attachment; filename=" + fileName + ".pdf");

      ServletOutputStream servletOutputStream = response.getOutputStream();
      JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

      servletOutputStream.flush();
      servletOutputStream.close();
      context.responseComplete();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

}
