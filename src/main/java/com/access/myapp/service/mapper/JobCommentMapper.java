package com.access.myapp.service.mapper;


import com.access.myapp.domain.*;
import com.access.myapp.service.dto.JobCommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobComment} and its DTO {@link JobCommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {JobMapper.class})
public interface JobCommentMapper extends EntityMapper<JobCommentDTO, JobComment> {

    @Mapping(source = "job.id", target = "jobId")
    JobCommentDTO toDto(JobComment jobComment);

    @Mapping(source = "jobId", target = "job")
    JobComment toEntity(JobCommentDTO jobCommentDTO);

    default JobComment fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobComment jobComment = new JobComment();
        jobComment.setId(id);
        return jobComment;
    }
}
