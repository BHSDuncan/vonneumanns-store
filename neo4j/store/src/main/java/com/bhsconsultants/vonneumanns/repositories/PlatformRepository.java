package com.bhsconsultants.vonneumanns.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.bhsconsultants.vonneumanns.entities.Platform;

public interface PlatformRepository extends GraphRepository<Platform> {
    Platform findByPlatformId(String platformId);
}
