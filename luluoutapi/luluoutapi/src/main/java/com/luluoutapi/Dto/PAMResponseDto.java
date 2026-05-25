package com.luluoutapi.Dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class PAMResponseDto {

    private String responseId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Errors> errors;

    @Data
    public static class Errors {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String source;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String description;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String reasonCode;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean recoverable;
    }

}