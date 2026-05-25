package com.luluoutapi.Dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class CreateTransactionReqDtoBKP {

    private BookTransaction bookTransaction;
    private String feeTransaction;
    private String adjustmentTransaction;
    private String externalTransaction;

    @Data
    public static class BookTransaction {
        private OriginatorAccount originatorAccount;
        private String type;
        private String currency;
        private String amount;
        private String description;
        private String beneficiaryAccount;
        private String externalReferenceId;
        private String relatedTransactionId;
        private String metadata;

        @Data
        public static class OriginatorAccount {
            private String depositAccountId;
            private String loanAccountId;
            private String externalAccountId;

        }

        @Data
        public static class ForexOverride {
            private String forexRate;
            private String beneficiaryAccountAmount;

        }
    }

    @Data
    public static class FeeTransaction {
        private String account;
        private String type;
        private String currency;
        private String amount;
        private String description;
        private String externalReferenceId;
        private String relatedTransactionId;
        private String metadata;

    }

    @Data
    public static class adjustmentTransaction {
        private String account;
        private String type;
        private String direction;
        private String currency;
        private String amount;
        private String description;
        private String externalReferenceId;
        private String relatedTransactionId;
        private String metadata;

    }

    public static void main(String[] args) throws JsonProcessingException {
        CreateTransactionReqDto s  = new CreateTransactionReqDto();
        ObjectMapper map= new ObjectMapper();
        String writeValueAsString = map.writeValueAsString(s);
        System.out.println(writeValueAsString);
    }
}
