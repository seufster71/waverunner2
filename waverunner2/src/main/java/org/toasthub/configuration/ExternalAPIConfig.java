package org.toasthub.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.properties.DataAPIType;
import net.jacobpeterson.alpaca.model.properties.EndpointAPIType;

@Configuration
public class ExternalAPIConfig {

	@Bean
	public AlpacaAPI alpacaAPI() {
        return new AlpacaAPI("PK94RB6C0KXUIOLJT470", "bmhK6AhO3lD9DEeLvN8UdkEXq3Doq7wXZ3Ub8gTg", EndpointAPIType.PAPER, DataAPIType.IEX);
    }
}
