package com.access.myapp.service.mapper;


import com.access.myapp.domain.*;
import com.access.myapp.service.dto.JobDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Job} and its DTO {@link JobDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface JobMapper extends EntityMapper<JobDTO, Job> {

    @Mapping(source = "client.id", target = "clientId")
    JobDTO toDto(Job job);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComments", ignore = true)
    @Mapping(target = "lines", ignore = true)
    @Mapping(target = "removeLines", ignore = true)
    @Mapping(target = "visits", ignore = true)
    @Mapping(target = "removeVisits", ignore = true)
    @Mapping(source = "clientId", target = "client")
    Job toEntity(JobDTO jobDTO);

    default Job fromId(Long id) {
        if (id == null) {
            return null;
        }
        Job job = new Job();
        job.setId(id);
        return job;
    }
}
