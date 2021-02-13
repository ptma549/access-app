package com.access.myapp.service.mapper;


import com.access.myapp.domain.*;
import com.access.myapp.service.dto.JobVisitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobVisit} and its DTO {@link JobVisitDTO}.
 */
@Mapper(componentModel = "spring", uses = {JobMapper.class})
public interface JobVisitMapper extends EntityMapper<JobVisitDTO, JobVisit> {

    @Mapping(source = "job.id", target = "jobId")
    JobVisitDTO toDto(JobVisit jobVisit);

    @Mapping(source = "jobId", target = "job")
    JobVisit toEntity(JobVisitDTO jobVisitDTO);

    default JobVisit fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobVisit jobVisit = new JobVisit();
        jobVisit.setId(id);
        return jobVisit;
    }
}
