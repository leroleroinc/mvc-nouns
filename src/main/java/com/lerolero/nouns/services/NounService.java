package com.lerolero.nouns.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.lerolero.nouns.repositories.MongoNounRepository;
import com.lerolero.nouns.repositories.NounCache;
import com.lerolero.nouns.models.Noun;

@Service
public class NounService {

	@Autowired
	private MongoNounRepository repo;

	@Autowired
	private NounCache cache;

	private String next() {
		Noun noun;
		try {
			noun = cache.next();
		} catch (NounCache.CacheMissException e) {
			noun = repo.findById(e.getKey())
				.orElseThrow(() -> new RuntimeException("No noun available"));
			cache.add(noun);
		}
		return noun.getPlural();
	}

	public String randomNoun() {
		return next();
	}

	public List<String> randomNounList(Integer size) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < size; i++) list.add(next());
		return list;
	}

}
