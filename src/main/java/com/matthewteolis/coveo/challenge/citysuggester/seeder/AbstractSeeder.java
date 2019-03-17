package com.matthewteolis.coveo.challenge.citysuggester.seeder;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractSeeder<R extends JpaRepository> implements Seedable<R> {

    protected R repository;

    public AbstractSeeder(R repository) {
        this.repository = repository;
    }

    @Override
    public R getRepository() {
        return this.repository;
    }

}
