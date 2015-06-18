package com.herokuapp.obscurespire6277.photor;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FooRepository extends CrudRepository<FooModel, Long> {
    List<FooModel> findByName(String name);
}
