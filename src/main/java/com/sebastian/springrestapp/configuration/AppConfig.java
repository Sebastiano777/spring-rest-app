package com.sebastian.springrestapp.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration()
//                .setFieldMatchingEnabled(true)
//                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.getConfiguration()
                .setDestinationNameTransformer(NameTransformers.builder())
                .setDestinationNamingConvention(NamingConventions.builder());
        return modelMapper;
    }
}
