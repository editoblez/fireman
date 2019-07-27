package com.ec.fireman.beans.docs;

import com.ec.fireman.beans.PageRedirectConstants;
import com.ec.fireman.data.dao.*;
import com.ec.fireman.data.entities.*;
import com.ec.fireman.data.representation.RequirementFileUpload;
import com.ec.fireman.util.MessageUtil;
import com.ec.fireman.util.SessionUtils;
import com.github.adminfaces.template.util.Assert;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.faces.context.FacesContext.getCurrentInstance;

@Data
@Log4j2
@Named
@ViewScoped
public class LocalDocs implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Inject
    private LocalDao localDao;
    @Inject
    private ServiceDao serviceDao;
    @Inject
    private UserAccountDao userAccountDao;
    @Inject
    private RequirementDao requirementDao;
    @Inject
    private PermissionRequestDao permissionRequestDao;
    @Inject
    private PermissionRequestFilesDao permissionRequestFilesDao;


    private List<RequirementFileUpload> files;
    private Local local;
    private Long id;

    @PostConstruct
    public void init() {
        if (Assert.has(id) && id != null) {
            local = localDao.findById(id);
        }
        if (Faces.getSessionAttribute(PageRedirectConstants.REFERER) == null ||
                Faces.getSessionAttribute(PageRedirectConstants.REFERER).toString().isEmpty() ||
                (getCurrentInstance().
                        getExternalContext().getRequestHeaderMap()
                        .get(PageRedirectConstants.REFERER).lastIndexOf("documents.xhtml") < 0)) {
            Faces.setSessionAttribute(PageRedirectConstants.REFERER, getCurrentInstance().
                    getExternalContext().getRequestHeaderMap().get(PageRedirectConstants.REFERER));
        }
    }

    public String redirectTo() {
        String completeUrl = Faces.getSessionAttribute(PageRedirectConstants.REFERER).toString();
        StringBuilder path = new StringBuilder(
                getCurrentInstance().getExternalContext().isSecure() ? "https://" : "http://"
        );
        path.append(getCurrentInstance().getExternalContext().getRequestServerName());
        int port = getCurrentInstance().getExternalContext().getRequestServerPort();
        path.append(port != 80 && port != 443 ? ":" + port : "");
        path.append(getCurrentInstance().getExternalContext().getRequestContextPath());

        String[] urls = completeUrl.split(path.toString());
        String redirectUrl = null;
        for (String item: urls) {
            if (item != null && !item.isEmpty()) {
                redirectUrl = item;
                break;
            }
        }
        return redirectUrl + "?faces-redirect=true";
    }

    public void uploadAll() {
        try {
            for (RequirementFileUpload requirementFileUpload : files) {
                log.info(requirementFileUpload.toString());
                if (requirementFileUpload.getFile() != null) {
                    String suffix = requirementFileUpload.getFile().getFileName()
                            .substring(requirementFileUpload.getFile().getFileName().lastIndexOf("."));
                    if (MimeTypes.findBySuffix(suffix) == null) {
                        MessageUtil.addDetailMessage("La extensión del archivo " +
                                        requirementFileUpload.getFile().getFileName() + " no es permitida.",
                                FacesMessage.SEVERITY_WARN);
                        continue;
                    }
                    byte[] bytes = null;
                    try {
                        bytes = IOUtils.toByteArray(requirementFileUpload.getFile().getInputstream());
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                    PermissionRequest permissionRequest = permissionRequestDao.findPermissionRequestByLocal(local.getId());
                    PermissionRequestFiles prf = new PermissionRequestFiles();
                    prf.setPermissionRequest(permissionRequest);
                    prf.setRequirement(requirementDao.findById(requirementFileUpload.getRequirementId()));
                    prf.setState(State.ACTIVE);
                    prf.setData(bytes);
                    prf.setFileName(requirementFileUpload.getFile().getFileName());
                    permissionRequestFilesDao.save(prf);
                    MessageUtil.addDetailMessage(requirementFileUpload.getFile().getFileName() +
                            " subido correctamente.");
                } else {
                    MessageUtil.addDetailMessage("La extensión del fichero no es permitida o no existe.", FacesMessage.SEVERITY_ERROR);
                }
            }
        } catch (Exception ex) {
            MessageUtil.addDetailMessage(ex.getMessage(), FacesMessage.SEVERITY_WARN);
        }
    }

    public List<RequirementFileUpload> listRequirements() {
        // TODO: LIST ACTIVE REQUIREMENTS BY ROLE (DAO)
        List<Requirement> requirements = requirementDao.findAll().stream()
                .filter(it -> it.getRole().getRoleName().getValue() ==
                        Faces.getSessionAttribute(SessionUtils.ROLE).toString()).collect(Collectors.toList());
        files = new ArrayList<RequirementFileUpload>();
        if (!requirements.isEmpty()) {
            for (Requirement req : requirements) {
                files.add(new RequirementFileUpload(req.getId(), req.getName()));
            }
        }
        return files;
    }

    public StreamedContent download(PermissionRequestFiles prf) {
        InputStream stream = new ByteArrayInputStream(prf.getData());
        String suffix = prf.getFileName().substring(prf.getFileName().lastIndexOf("."));
        return new DefaultStreamedContent(stream, MimeTypes.findBySuffix(suffix).getMimeType(), prf.getFileName());
    }

    public List<PermissionRequestFiles> listFiles(Long reqId) {
        PermissionRequest permissionRequest = permissionRequestDao.findPermissionRequestByLocal(id);
        List<PermissionRequestFiles> list = permissionRequestFilesDao
                .findFilesByRequestAndRequirement(permissionRequest.getId(), reqId);
        log.info("Files length: " + (list != null ? list.size() : 0) +
                " for Requirement: " + reqId + " and Permission Requirement: " + permissionRequest.getId());
        return list;
    }

    @Transactional
    public void eliminarDocumento(Long documentId) {
        PermissionRequestFiles item = permissionRequestFilesDao.findById(documentId);
        log.info("File delete: " + (item.getFileName()) +
                " for Requirement: " + item.getRequirement().getId() + " and Permission Requirement: " +
                item.getPermissionRequest().getId());
        permissionRequestFilesDao.remove(item);
        MessageUtil.addDetailMessage("File "+ item.getFileName() + " is delete.");
    }

    public void handleFileUpload(FileUploadEvent event) {
        MessageUtil.addDetailMessage(event.getFile().getFileName() + " is uploaded.");
    }
}
