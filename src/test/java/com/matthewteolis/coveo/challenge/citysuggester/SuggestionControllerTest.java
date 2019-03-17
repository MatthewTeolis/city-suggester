package com.matthewteolis.coveo.challenge.citysuggester;

import com.matthewteolis.coveo.challenge.citysuggester.entity.admin1.Admin1;
import com.matthewteolis.coveo.challenge.citysuggester.entity.admin1.Admin1Factory;
import com.matthewteolis.coveo.challenge.citysuggester.entity.city.City;
import com.matthewteolis.coveo.challenge.citysuggester.entity.city.CityFactory;
import com.matthewteolis.coveo.challenge.citysuggester.entity.country.Country;
import com.matthewteolis.coveo.challenge.citysuggester.entity.country.CountryFactory;
import com.matthewteolis.coveo.challenge.citysuggester.repository.CityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CitySuggesterApplication.class})
public class SuggestionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CityRepository cityRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void suggestionsShouldBeEmptyWhenQueryIsRandom() throws Exception {
        mockMvc.perform(get("/suggestions?q=SomeRandomCityInTheMiddleOfNowhere&latitude=43.70011&longitude=-79.4163"))
                .andExpect(jsonPath("$.suggestions", iterableWithSize(0)));
    }

    @Test
    public void suggestionsSuccess() throws Exception {
        final City city1 = mockedCity("London", 10, 11, 1000, 0, "CA", "Canada", "CA.08", "Ontario");
        final City city2 = mockedCity("London", 50, 51, 1001, 1, "US", "United States", "US.OH", "Ohio");
        final City city3 = mockedCity("Londontowne", 43.70011, -79.4163, 1002, 2, "US", "United States", "US.MD", "Maryland");

        final List<City> cityList = new ArrayList<>(Arrays.asList(city1, city2, city3));

        when(cityRepository.findAllByNameStartingWith(anyString())).thenReturn(cityList);

        mockMvc.perform(get("/suggestions?q=Londo&latitude=43.70011&longitude=-79.4163"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("suggestions")))
                .andExpect(jsonPath("$.suggestions", hasSize(3)))

                .andExpect(jsonPath("$.suggestions[0].name", is("London, Ontario, Canada")))
                .andExpect(jsonPath("$.suggestions[0].latitude", is("10.0")))
                .andExpect(jsonPath("$.suggestions[0].longitude", is("11.0")))
                .andExpect(jsonPath("$.suggestions[0].score", any(BigDecimal.class)))


                .andExpect(jsonPath("$.suggestions[1].name", is("London, OH, USA")))
                .andExpect(jsonPath("$.suggestions[1].latitude", is("50.0")))
                .andExpect(jsonPath("$.suggestions[1].longitude", is("51.0")))
                .andExpect(jsonPath("$.suggestions[1].score", any(BigDecimal.class)))


                .andExpect(jsonPath("$.suggestions[2].name", is("Londontowne, MD, USA")))
                .andExpect(jsonPath("$.suggestions[2].latitude", is("43.70011")))
                .andExpect(jsonPath("$.suggestions[2].longitude", is("-79.4163")))
                .andExpect(jsonPath("$.suggestions[2].score", is(1.0)));
    }

    private City mockedCity(String name, double latitude, double longitude, long population, int elevation, String countryISO, String countryName, String admin1Code, String admin1Name) {
        final Country country = new CountryFactory().create(countryISO, countryName);
        final Admin1 admin1 = new Admin1Factory().create(admin1Code, admin1Name);

        return new CityFactory().create(name, latitude, longitude, country, admin1, population, elevation);
    }

}