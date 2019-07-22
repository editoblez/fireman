package com.ec.fireman.beans;

import com.ec.fireman.data.dao.InspectionFireExtinguisherDao;
import com.ec.fireman.data.dao.InspectionHeaderDao;
import com.ec.fireman.data.entities.InspectionFireExtinguisher;
import com.ec.fireman.data.entities.InspectionHeader;
import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.util.DateUtil;
import com.ec.fireman.util.MessageUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  public void buildReport(PermissionRequest pr) {
    InspectionHeader inspection = null;
    List<InspectionFireExtinguisher> extinguishers = null;
    try {
      log.info(pr.toString());
      inspection = inspectionHeaderDao.findInspectionHeaderByRequest(pr.getId());
      log.info(inspection.toString());
      extinguishers = inspectionFireExtinguisherDao.findInspectionFireExtinguisherByHeader(inspection.getId());
    } catch (Exception e1) {
      log.info(e1.getMessage());
    }

    if (inspection == null) {
      MessageUtil.warningFacesMessage("Reporte", "No existe información");
      return;
    }

    String nombreArchivo = "INSPECCION";

    Map<String, Object> params = new HashMap<>();
    params.put("local", inspection.getPermissionRequest().getLocal().getName());
    params.put("category", inspection.getPermissionRequest().getLocal().getService().getName());
    params.put("client", inspection.getPermissionRequest().getLocal().getUserAccount().getFullName().toUpperCase());
    params.put("phone", inspection.getPermissionRequest().getLocal().getUserAccount().getPhoneNumber());
    params.put("address", inspection.getPermissionRequest().getLocal().getAddress());
    params.put("date", DateUtil.formatDateToString(inspection.getLastUpdate()));

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

    //TODO: LLENAR OTROS params DEL REPORTE COMO LOCAL, NUM INSPECC, NOMBRE DEL INSPECTOR, ETC

    JRBeanCollectionDataSource data = CollectionUtils.isEmpty(extinguishers)
        ? null : new JRBeanCollectionDataSource(extinguishers);

    try {
      this.generatePDF(nombreArchivo, "inspection.jrxml", data, params);
    } catch (Exception e) {
      log.error(e.getMessage());
      MessageUtil.errorFacesMessage("Reporte", "Error al generar el reporte.");
    }

  }

  public void generatePDF(String fileName, String templateName, JRBeanCollectionDataSource data,
                          Map<String, Object> params) {
    try {
      FacesContext context = FacesContext.getCurrentInstance();
      params.put("logo", context.getExternalContext().getRealPath(File.separator) + File.separator + "resources"
          + File.separator + "images" + File.separator + "fireman-logo.png");
      String path = context.getExternalContext().getRealPath(File.separator) + File.separator + "resources"
          + File.separator + "reports" + File.separator + templateName;
      HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
      JasperReport jasperReport = JasperCompileManager.compileReport(path);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,
          data != null ? data : new JREmptyDataSource());
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
