package com.matthewteolis.coveo.challenge.citysuggester.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
public class CitySuggesterResourceUtils {

    /**
     * Helper method to get a {@link Reader} for a file in the resources directory.
     *
     * @param file path to the file.
     * @return {@link InputStreamReader} for the file.
     * @throws IOException If the file cannot be read.
     */
    public Reader getFile(String file) throws IOException {
        final ClassPathResource classPathResource = new ClassPathResource(file);
        return new InputStreamReader(classPathResource.getInputStream());
    }

}
