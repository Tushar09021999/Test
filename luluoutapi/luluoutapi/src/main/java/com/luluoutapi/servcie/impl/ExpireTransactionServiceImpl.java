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
import com.luluoutapi.Dto.CloseAccountReqDto;
import com.luluoutapi.constants.AppConstants;
import com.luluoutapi.servcie.ExpireTransactionService;
import com.luluoutapi.webClient.WebClient;

import lombok.extern.log4j.Log4j2;

@Service
@PropertySource("classpath:pamcommon.properties")
@Log4j2

public class ExpireTransactionServiceImpl implements ExpireTransactionService {

    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    private ExpireTransactionServiceImpl(ObjectMapper objectMapper, WebClient webClient) {
        this.objectMapper = objectMapper;
        this.webClient = webClient;
    }

    @Override
    public void processExpireTransactionService() {
        
        String apiKey = AppConstants.CLSACC;
        log.info("Inside processCloseAccount {}",apiKey);

        CloseAccountReqDto closeAccountReqDto = new CloseAccountReqDto();
        CloseAccountReqDto.EncryptedPayload encryptedPayload = new CloseAccountReqDto.EncryptedPayload();
        try (Connection connection = DBUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        AppConstants.CLOSE_ACC_QUERY)) {
            preparedStatement.setString(1, "P");
            preparedStatement.setString(2, apiKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.isBeforeFirst()) {
                    log.info("request found for close Account Api {}", apiKey);

                    while (resultSet.next()) {
                        Date RequestDate = Date.valueOf(LocalDate.now());
                        closeAccountReqDto.setRequestId(resultSet.getString(AppConstants.REQUEST_ID));
                        encryptedPayload.setAccountNumber(resultSet.getString(AppConstants.FLD001));
                        closeAccountReqDto.setEncryptedPayload(encryptedPayload);
                        String ass = objectMapper.writeValueAsString(closeAccountReqDto);
                        webClient.constructRequest(ass, apiKey,RequestDate);
                    }
                } else {
                    log.info(" Entry not found for {}", apiKey);
                }
            }

        } catch (

        Exception e) {
            log.error("DBERR Exceptin while getting data from DB" + e +" apiKey::"+ apiKey);
        }

    }

}
