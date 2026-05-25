package com.luluoutapi.Dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RequestStatusReqDto {

    private List<String> requestId = new ArrayList<>();

}
