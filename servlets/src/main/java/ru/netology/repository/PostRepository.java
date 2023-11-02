package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

  private static final AtomicLong postCounter = new AtomicLong(0);
  private static final Map<Long, Post> repository = new ConcurrentHashMap<>();

  public List<Post> all() {
    return new ArrayList<>(repository.values());
  }

  public Optional<Post> getById(long id) {

    if (id < 1 || id > postCounter.get()) {
      return Optional.empty();
    }

    if (repository.containsKey(id)) {
      return Optional.of(repository.get(id));
    } else {
      return Optional.empty();
    }
  }

  public Post save(Post post) {
    long id = post.getId();

    if (id < 0 || id > postCounter.get()) {
      return null;
    }

    if (id == 0) {
      id = postCounter.addAndGet(1);
      post.setId(id);
      repository.put(id, post);
    } else {
      repository.replace(id, post);
    }

    return repository.get(id);
  }

  public void removeById(long id) {

    if (id <= 0 || id > postCounter.get() || !repository.containsKey(id)) {
      return;
    }

    repository.remove(id);
  }
}
