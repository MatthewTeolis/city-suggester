package com.matthewteolis.coveo.challenge.citysuggester.seeder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @param <R> The repository type.
 */
public interface Seedable<R extends JpaRepository> {

    /**
     * @return The repository used by the instance.
     */
    R getRepository();

    /**
     * @return The {@link List} of objects that were saved in the database table.
     */
    List<?> seed();

}
