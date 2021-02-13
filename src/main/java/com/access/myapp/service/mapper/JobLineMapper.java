package com.access.myapp.service.mapper;


import com.access.myapp.domain.*;
import com.access.myapp.service.dto.JobLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobLine} and its DTO {@link JobLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {JobMapper.class})
public interface JobLineMapper extends EntityMapper<JobLineDTO, JobLine> {

    @Mapping(source = "job.id", target = "jobId")
    JobLineDTO toDto(JobLine jobLine);

    @Mapping(source = "jobId", target = "job")
    JobLine toEntity(JobLineDTO jobLineDTO);

    default JobLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobLine jobLine = new JobLine();
        jobLine.setId(id);
        return jobLine;
    }
}
