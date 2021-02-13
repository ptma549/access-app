package com.access.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.access.myapp.domain.JobComment} entity.
 */
public class JobCommentDTO implements Serializable {
    
    private Long id;

    private String comment;


    private Long jobId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobCommentDTO)) {
            return false;
        }

        return id != null && id.equals(((JobCommentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobCommentDTO{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", jobId=" + getJobId() +
            "}";
    }
}
