package com.luluoutapi.Dto;

import lombok.Data;

@Data

public class AddAccountReqDto {

    private String requestId;
    private EncryptedPayload encryptedPayload;

    @Data
    static public class EncryptedPayload {
        private CardInfo cardInfo;

        @Data
        static public class CardInfo {
            private String accountNumber;
            private String expiryMonth;
            private String expiryYear;
            private String panSequenceNumber;

        }

    }
}
