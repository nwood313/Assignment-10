package com.coderscampus.model;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
package com.coderscampus.JSON.alphaadvantage;

import com.coderscampus.JSON.alphaadvantage.dto.AlphaAdvantageResponse;
import org.junit.Test;


public class SpoonacularIntegration {

        @Test
        public void callApiExample(){
            RestTemplate rt = new RestTemplate();

            //?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=demo
            URI uri = UriComponentsBuilder.fromHttpUrl("https://www.alphavantage.co/query")
                    .queryParam("function", "TIME_SERIES_INTRADAY")
                    .queryParam("symbol", "TM")
                    .queryParam("interval", "5min")
                    .queryParam("apikey", "7714e04bdfe945429e3e5f6ca1ace8d9")
                    .build()
                    .toUri();

            ResponseEntity<SpoonacularIntegrationResponse> response = rt.getForEntity(uri, SpoonacularIntegrationResponse.class);
            System.out.println(response.getBody());
        }

    }


