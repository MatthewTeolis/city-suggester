package com.matthewteolis.coveo.challenge.citysuggester.entity.admin1;

import org.springframework.stereotype.Component;

@Component
public class Admin1Factory {

    /**
     * Create a {@link Admin1} object.
     *
     * @param code The representation of the {@link Admin1} code.<br>
     *             Format: {@code XX.YY}<br>
     *             - XX is the country code.<br>
     *             - YY is the admin code.
     * @param name The name of the {@link Admin1}.
     * @return A new {@link Admin1} object.
     */
    public Admin1 create(String code, String name) {
        return new Admin1(code, name);
    }

}
