package com.jota.hub.challenge.infra.springdoc.schemas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "CreateAnswerSchema", description = "Object used for creating an answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnswerSchema {
    private String message;

    private Long topicId;
}
