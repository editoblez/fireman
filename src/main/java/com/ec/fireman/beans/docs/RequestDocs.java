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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.faces.context.FacesContext.getCurrentInstance;

@Data
@Log4j2
@Named
@ViewScoped
public class RequestDocs implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Inject
    private RequirementDao requirementDao;
    @Inject
    private PermissionRequestDao permissionRequestDao;
    @Inject
    private PermissionRequestFilesDao permissionRequestFilesDao;


    private List<RequirementFileUpload> files;
    private PermissionRequest selectedRequest;
    private Long id;

    public void init() {
        if (Assert.has(id) && id != null) {
            selectedRequest = permissionRequestDao.findById(id);
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

    public StreamedContent download(PermissionRequestFiles prf) {
        InputStream stream = new ByteArrayInputStream(prf.getData());
        String suffix = prf.getFileName().substring(prf.getFileName().lastIndexOf("."));
        return new DefaultStreamedContent(stream, MimeTypes.findBySuffix(suffix).getMimeType(), prf.getFileName());
    }


    public List<PermissionRequestFiles> listFiles() {
        List<PermissionRequestFiles> list = permissionRequestFilesDao
                .findPermissionRequestFilesByRequest(selectedRequest.getId());
        log.info("Files length: " + (list != null ? list.size() : 0));
        return list;
    }
}
