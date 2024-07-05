package com.jota.hub.challenge.infra.springdoc.schemas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "DefaultErrorResponseSchema", description = "Default response object.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultErrorResponseSchema {

    private String message;
    private boolean status;
    private Object data;
}
