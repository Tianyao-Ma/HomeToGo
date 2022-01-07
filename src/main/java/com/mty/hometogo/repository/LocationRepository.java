package com.mty.hometogo.repository;

import com.mty.hometogo.model.Location;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LocationRepository extends ElasticsearchRepository<Location, Long>, CustomLocationRepository {
}
