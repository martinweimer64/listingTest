package com.weimer.listingTest;

import org.hsqldb.util.DatabaseManagerSwing;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application.properties")
class ListingTestApplicationTests {

    private static final Logger LOG = LoggerFactory.getLogger(ListingTestApplicationTests.class);

    @Test
    void newTest() {
        System.out.println("Hola");
    }

}
