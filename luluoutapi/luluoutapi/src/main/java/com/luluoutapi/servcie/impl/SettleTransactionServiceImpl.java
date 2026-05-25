package com.luluoutapi.servcie.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luluoutapi.Dao.DBUtils;
import com.luluoutapi.Dto.RequestStatusReqDto;
import com.luluoutapi.constants.AppConstants;
import com.luluoutapi.servcie.SettleTransactionService;
import com.luluoutapi.webClient.WebClient;

import lombok.extern.log4j.Log4j2;

@Service
@PropertySource("classpath:pamcommon.properties")
@Log4j2

public class SettleTransactionServiceImpl implements SettleTransactionService {

    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    private SettleTransactionServiceImpl(ObjectMapper objectMapper, WebClient webClient) {
        this.objectMapper = objectMapper;
        this.webClient = webClient;
    }

    @Override
    public void processSettleTransactionService() {
        String apiKey = AppConstants.REQSTS;
        RequestStatusReqDto requestStatusReqDto = new RequestStatusReqDto();
        log.info("Inside processRequestStatus {}", apiKey);

        try (Connection connection = DBUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        AppConstants.RQST_STATUS_QUERY)) {
            preparedStatement.setString(1, "P");
            preparedStatement.setString(2, apiKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.isBeforeFirst()) {
                    log.info("request found for request Account Api {}", apiKey);
                    Date RequestDate = null;

                    while (resultSet.next()) {
                        RequestDate = Date.valueOf(LocalDate.now());
                        ;

                        requestStatusReqDto.getRequestId().add(resultSet.getString(AppConstants.REQUEST_ID));

                    }
                    String ass = objectMapper.writeValueAsString(requestStatusReqDto);
                    webClient.constructRequest(ass, apiKey, RequestDate);
                } else {
                    log.info(" Entry not found for {}", apiKey);
                }
            }

        } catch (Exception e) {
            log.error("DBERR Exceptin while getting data from DB" + e + " apiKey::" + apiKey);

        }
    }

}
