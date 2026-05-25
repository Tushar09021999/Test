package com.luluoutapi.scheduler;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.luluoutapi.servcie.CreateTransactionService;
import com.luluoutapi.servcie.ExpireTransactionService;
import com.luluoutapi.servcie.ReverseTransactionService;
import com.luluoutapi.servcie.SettleTransactionService;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@PropertySource("classpath:api-config.properties")

public class ApiScheduler {

    private ExpireTransactionService closeAccountService;
    private ReverseTransactionService deleteAccountService;
    private SettleTransactionService requestStatusService;
    private CreateTransactionService addAccountService;

    public ApiScheduler(  ExpireTransactionService closeAccountService,
            ReverseTransactionService deleteAccountService, SettleTransactionService requestStatusService,
            CreateTransactionService addAccountService) {
        this.closeAccountService = closeAccountService;
        this.deleteAccountService = deleteAccountService;
        this.requestStatusService = requestStatusService;
        this.addAccountService = addAccountService;

    }



    @Value("${scheduler.enable.updateAccount:true}")
    private boolean updateAccountFlag;
    @Value("${scheduler.enable.closeAccount:true}")
    private boolean closeAccountFlag;
    @Value("${scheduler.enable.addAccount:true}")
    private boolean addAccountFlag;
    @Value("${scheduler.enable.deleteAccount:true}")
    private boolean deleteAccountFlag;
    @Value("${scheduler.enable.requestStatus:true}")
    private boolean requestStatusFlag;

    @PostConstruct
    public void init() {
        log.info("Initializing API Poller Scheduler Summary...");
        logApiStatus("UpdateAccountFlag", updateAccountFlag);
    }

    private void logApiStatus(String apiName, boolean isEnabled) {
        if (isEnabled) {
            log.info("{} API polling is enabled for " + apiName);
        } else {
            log.info("{} API polling is disabled for " + apiName);
        }
    }

   

    @Scheduled(cron = "${cron.expression.deleteAccount}")
    public void runDeleteAccount() {
        if (!deleteAccountFlag) {
            return;
        }
        try {
            log.info("Starting processDeleteAccount...");
            deleteAccountService.processReverseTransactionService();
            log.info("Finished processDeleteAccount...");
        } catch (Exception e) {
            log.error("Error while polling processDeleteAccount: {}", e);
        }
    }

    @Scheduled(cron = "${cron.expression.closeAccount}")
    public void runCloseAccount() {
        if (!closeAccountFlag) {
            return;
        }
        try {
            log.info("Starting processCloseAccount...");
            closeAccountService.processExpireTransactionService();
            log.info("Finished processCloseAccount...");
        } catch (Exception e) {
            log.error("Error while polling processCloseAccount: {}", e);
        }
    }

    @Scheduled(cron = "${cron.expression.addAccount}")
    public void runAddAccount() {
        if (!addAccountFlag) {
            return;
        }
        try {
            log.info("Starting processAddAccount...");
            addAccountService.processCreateTransaction();
            log.info("Finished processAddAccount...");
        } catch (Exception e) {
            log.error("Error while polling processAddAccount: {}", e);
        }
    }

    @Scheduled(cron = "${cron.expression.requestStatus}")
    public void runRequestStatus() {
        if (!requestStatusFlag) {
            return;
        }
        try {
            log.info("Starting processRequestStatus...");
            requestStatusService.processSettleTransactionService();
            log.info("Finished processRequestStatus...");
        } catch (Exception e) {
            log.error("Error while polling processRequestStatus: {}", e);
        }
    }
}
