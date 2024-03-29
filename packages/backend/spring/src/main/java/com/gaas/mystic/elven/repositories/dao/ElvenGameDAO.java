package com.gaas.mystic.elven.repositories.dao;

import com.gaas.mystic.elven.repositories.data.ElvenGameData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElvenGameDAO extends MongoRepository<ElvenGameData, String> {
}
