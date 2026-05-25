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
import com.luluoutapi.Dto.DeleteAccountReqDto;
import com.luluoutapi.constants.AppConstants;
import com.luluoutapi.servcie.ReverseTransactionService;
import com.luluoutapi.webClient.WebClient;

import lombok.extern.log4j.Log4j2;

@Service
@PropertySource("classpath:pamcommon.properties")
@Log4j2

public class ReverseTransactionServiceImpl implements ReverseTransactionService {

    private final ObjectMapper objectMapper;

    private final WebClient webClient;

    private ReverseTransactionServiceImpl(ObjectMapper objectMapper, WebClient webClient) {
        this.objectMapper = objectMapper;
        this.webClient = webClient;
    }

    @Override
    public void processReverseTransactionService() {
        String apiKey = AppConstants.DLTACC;

        DeleteAccountReqDto deleteAccountReqDto = new DeleteAccountReqDto();
        DeleteAccountReqDto.EncryptedPayload encryptedPayload = new DeleteAccountReqDto.EncryptedPayload();

        log.info("Inside processDeleteAccount {}",apiKey);

        try (Connection connection = DBUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        AppConstants.DELETE_ACC_QUERY)) {
            preparedStatement.setString(1, "P");
            preparedStatement.setString(2, apiKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.isBeforeFirst()) {
                    log.info("request found for delete Account Api {} ",apiKey );

                    while (resultSet.next()) {
                        log.info("Inside resultSet");
                        Date RequestDate = Date.valueOf(LocalDate.now());
                        deleteAccountReqDto.setRequestId(resultSet.getString(AppConstants.REQUEST_ID));
                        deleteAccountReqDto.getEncryptedPayload().setAccountNumber(resultSet.getString(AppConstants.FLD001));
                        deleteAccountReqDto.setEncryptedPayload(encryptedPayload);
                        String ass = objectMapper.writeValueAsString(deleteAccountReqDto);
                        webClient.constructRequest(ass, apiKey,RequestDate);
                    }
                } else {
                    log.info(" Entry not found for {} ",apiKey);
                }

            }
        } catch (Exception e) {
            log.error("DBERR Exceptin while getting data from DB" + e+ " apiKey::"+ apiKey);
        }
    }
}
