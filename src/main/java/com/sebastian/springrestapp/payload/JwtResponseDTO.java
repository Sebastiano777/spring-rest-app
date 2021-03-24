package com.sebastian.springrestapp.payload;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class JwtResponseDTO {
    String token;
}
