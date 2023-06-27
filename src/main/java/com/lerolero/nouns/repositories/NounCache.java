package com.lerolero.nouns.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.redisson.api.RedissonClient;
import org.redisson.api.RBucket;

import com.lerolero.nouns.repositories.MongoNounRepository;
import com.lerolero.nouns.models.Noun;

@Repository
public class NounCache {

	@Autowired
	private RedissonClient redis;

	@Autowired
	private MongoNounRepository repo;

	private List<String> ids;

	public Noun next() throws CacheMissException {
		if (ids == null) {
			ids = repo.findAll().stream().map(Noun::getId).collect(Collectors.toList());
		}
		String id = ids.get((int)(Math.random() * ids.size()));
		RBucket<Noun> bucket = redis.getBucket("/noun/" + id);
		if (bucket.get() == null) throw new CacheMissException(id);
		return bucket.get();
	}

	public void add(Noun noun) {
		RBucket<Noun> bucket = redis.getBucket("/noun/" + noun.getId());
		bucket.set(noun);
		ids.add(noun.getId());
	}

	public static class CacheMissException extends Exception {
		private String key;
		public CacheMissException(String key) {
			this.key = key;
		}
		public String getKey() {
			return key;
		}
	}

}
