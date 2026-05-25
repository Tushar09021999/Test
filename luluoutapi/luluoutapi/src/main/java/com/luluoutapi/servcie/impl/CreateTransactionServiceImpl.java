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
import com.luluoutapi.Dto.AddAccountReqDto;
import com.luluoutapi.constants.AppConstants;
import com.luluoutapi.servcie.CreateTransactionService;
import com.luluoutapi.webClient.WebClient;

import lombok.extern.log4j.Log4j2;

@Service
@PropertySource("classpath:pamcommon.properties")
@Log4j2
public class CreateTransactionServiceImpl implements CreateTransactionService {

    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    private CreateTransactionServiceImpl(ObjectMapper objectMapper, WebClient webClient) {
        this.objectMapper = objectMapper;
        this.webClient = webClient;
    }

    @Override
    public void processCreateTransaction() {
        String apiKey = AppConstants.ADACC;
        log.info("Inside processAddAccount {}", apiKey);

        AddAccountReqDto AddAccountReqDto = new AddAccountReqDto();
        AddAccountReqDto.EncryptedPayload encryptedPayload = new AddAccountReqDto.EncryptedPayload();
        try (Connection connection = DBUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        AppConstants.ADD_ACC_QUERY)) {
            preparedStatement.setString(1, "P");
            preparedStatement.setString(2, apiKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.isBeforeFirst()) {
                    log.info("request found for Add Account Api {}", apiKey);

                    while (resultSet.next()) {
                        Date RequestDate = Date.valueOf(LocalDate.now());
                        AddAccountReqDto.setRequestId(resultSet.getString(AppConstants.REQUEST_ID));
                        encryptedPayload.getCardInfo().setAccountNumber(resultSet.getString(AppConstants.FLD001));
                        encryptedPayload.getCardInfo().setExpiryMonth(resultSet.getString(AppConstants.FLD002));
                        encryptedPayload.getCardInfo().setExpiryYear(resultSet.getString(AppConstants.FLD003));
                        encryptedPayload.getCardInfo().setPanSequenceNumber(resultSet.getString(AppConstants.FLD004));
                        AddAccountReqDto.setEncryptedPayload(encryptedPayload);
                        String ass = objectMapper.writeValueAsString(AddAccountReqDto);
                        webClient.constructRequest(ass, apiKey,RequestDate);
                    }
                } else {
                    log.info(" Entry not found for  {}", apiKey);

                }
            }

        } catch (Exception e) {
            log.error("DBERR  Exceptin while getting data from DB" + e + " apiKey::" + apiKey);
        }

    }


}
