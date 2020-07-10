package com.teecup.shop.repository;

import com.teecup.shop.model.Tee;
import org.springframework.data.repository.CrudRepository;

public interface TeeRepository extends CrudRepository<Tee, Long> {
}
