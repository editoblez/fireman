package com.ec.fireman.data.representation;

import org.primefaces.model.UploadedFile;

import lombok.Data;

@Data
public class RequirementFileUpload {

  private long requirementId;
  private String requirementName;
  private UploadedFile file;

  public RequirementFileUpload(long requirementId, String requirementName) {
    super();
    this.requirementId = requirementId;
    this.requirementName = requirementName;
  }

}
