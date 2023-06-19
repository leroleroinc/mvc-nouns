package com.lerolero.nouns.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;

import com.lerolero.nouns.models.Noun;

public interface MongoNounRepository extends MongoRepository<Noun,String> {

	@Aggregation("{ $sample: { size: 1 } }")
	public Optional<Noun> pullRandom();

}
