// package com.mipamapi.scheduler;

// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;

// import com.mipamapi.servcie.AddAccountService;
// import com.mipamapi.servcie.CloseAccountService;
// import com.mipamapi.servcie.DeleteAccountService;
// import com.mipamapi.servcie.RequestStatusService;
// import com.mipamapi.servcie.UpdateAccountService;

// import lombok.extern.log4j.Log4j2;

// @Component
// @Log4j2
// public class ApiScheduler {

//     private UpdateAccountService updateAccountService;
//     private CloseAccountService closeAccountService;
//     private DeleteAccountService deleteAccountService;
//     private RequestStatusService requestStatusService;
//     private AddAccountService addAccountService;

//     public ApiScheduler(UpdateAccountService updateAccountService, CloseAccountService closeAccountService,
//             DeleteAccountService deleteAccountService, RequestStatusService requestStatusService,
//             AddAccountService addAccountService) {
//         this.updateAccountService = updateAccountService;
//         this.closeAccountService = closeAccountService;
//         this.deleteAccountService = deleteAccountService;
//         this.requestStatusService = requestStatusService;
//         this.addAccountService = addAccountService;

//     }

//     @Scheduled(fixedRate = 1000)
//     public void runUpdateAccount() {
//         log.info("Update Account poller running.......");
//         updateAccountService.processUpdateAccount();
//     }

//     @Scheduled(fixedRate = 1000)
//     public void runCloseAccount() {
//         log.info("Close Account poller running.......");
//         closeAccountService.processCloseAccount();
//     }

//     @Scheduled(fixedRate = 1000)
//     public void runAddAccount() {
//         log.info("Add Account poller running.......");
//         addAccountService.processAddAccount();
//     }

//     @Scheduled(fixedRate = 1000)
//     public void runDeleteAccount() {
//         log.info("Delete Account poller running.......");
//         deleteAccountService.processDeleteAccount();
//     }

//     @Scheduled(fixedRate = 1000)
//     public void runRequestStatus() {
//         log.info("Request Status poller running.......");
//         requestStatusService.processRequestStatus();
//     }
// }
