package com.luluoutapi.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UpdateStatusDao {

    static String queryUpdate = "update out_pam_api set status=? ,RESPONSECODE=? , RESPONSETEXT=? , REQUEST_DTTM =?,RESPONSE_DTTM=sysdate where REQUEST_ID=?  and status=? and api_key=?";

    public void checkandUpdateStatus(Connection connection, String responseCode, String txnRefNo, String apiKey,
            String responseText, String flag, String status,Date date) throws SQLException {
        log.info("[UpdateApiStatus][checkandUpdateStatus] Inside checkandUpdateStatus method ");
        log.info("[UpdateApiStatus][checkandUpdateStatus]  responseCode is -" + responseCode);
        log.info("[UpdateApiStatus][checkandUpdateStatus] responseText is -" + responseText);
        PreparedStatement preparedStatement = null;
        try {
            if (responseCode.equalsIgnoreCase("200")) {
                log.info("[UpdateApiStatus][checkandUpdateStatus] Inside responseCode sucsess -" + responseCode);
                log.info("[UpdateApiStatus][checkandUpdateStatus] sql update query -" + queryUpdate);
                log.info("[UpdateApiStatus][checkandUpdateStatus]flag before update -" + flag);
                preparedStatement = connection.prepareStatement(queryUpdate);
                preparedStatement.setString(1, status);
                preparedStatement.setString(2, responseCode);
                preparedStatement.setString(3, responseText);
                preparedStatement.setDate(4, date);
                preparedStatement.setString(5, txnRefNo);
                preparedStatement.setString(6, flag);
                preparedStatement.setString(7, apiKey);
                log.info("[UpdateApiStatus][checkandUpdateStatus] preparedStatement set values as param [1.txnRefNo]-"
                        + txnRefNo + " ,[2.apiKey]-" + apiKey);
                preparedStatement.executeUpdate();

            } else {
                log.info("[UpdateApiStatus][checkandUpdateStatus] Inside responseCode Fail -" + responseCode);
                log.info("[UpdateApiStatus][checkandUpdateStatus] sql update query -" + queryUpdate);
                log.info("[UpdateApiStatus][checkandUpdateStatus]pawan -" + flag);
                preparedStatement = connection.prepareStatement(queryUpdate);
                preparedStatement.setString(1, status);
                preparedStatement.setString(2, responseCode);
                preparedStatement.setString(3, responseText);
                preparedStatement.setDate(4, date);
                preparedStatement.setString(5, txnRefNo);
                preparedStatement.setString(6, flag);
                preparedStatement.setString(7, apiKey);
                log.info("[UpdateApiStatus][checkandUpdateStatus] preparedStatement set values as param [1.txnRefNo]-"
                        + txnRefNo + " ,[2.apiKey]-" + apiKey);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            log.error("while checkandUpdateStatus exception-" + e.getMessage());
            throw e;
        } finally {
            DBUtils.closePrepareStatement(preparedStatement);
        }

    }

    public void checkandUpdateStatus11(Connection connection, String responseCode, String txnRefNo, String apiKey,
            String responseText, String flag, String status) throws SQLException {
        log.info("[UpdateApiStatus][checkandUpdateStatus] Inside checkandUpdateStatus method ");
        log.info("[UpdateApiStatus][checkandUpdateStatus]  responseCode is -" + responseCode);
        log.info("[UpdateApiStatus][checkandUpdateStatus] responseText is -" + responseText);
        PreparedStatement preparedStatement = null;
        try {
            log.info("[UpdateApiStatus][checkandUpdateStatus] Inside responseCode sucsess -" + responseCode);
            log.info("[UpdateApiStatus][checkandUpdateStatus] sql update query -" + queryUpdate);
            log.info("[UpdateApiStatus][checkandUpdateStatus]flag before update -" + flag);
            preparedStatement = connection.prepareStatement(queryUpdate);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, responseCode);
            preparedStatement.setString(3, responseText);
            preparedStatement.setString(4, txnRefNo);
            preparedStatement.setString(5, flag);
            preparedStatement.setString(6, apiKey);
            log.info("[UpdateApiStatus][checkandUpdateStatus] preparedStatement set values as param [1.txnRefNo]-"
                    + txnRefNo + " ,[2.apiKey]-" + apiKey);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("while checkandUpdateStatus exception-" + e.getMessage());
            throw e;
        } finally {
            DBUtils.closePrepareStatement(preparedStatement);
        }

    }

}
