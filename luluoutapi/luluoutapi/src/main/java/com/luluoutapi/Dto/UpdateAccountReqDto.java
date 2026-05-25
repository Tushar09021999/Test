package com.luluoutapi.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class UpdateAccountReqDto {

    private String requestId;
    private String reasonCode;
    private EncryptedPayload encryptedPayload;

    @Data
    static public class EncryptedPayload {
        private OldCardInfo oldCardInfo;
        private NewCardInfo newCardInfo;

        @Data
        static public class OldCardInfo {
            private String accountNumber;
            private String expiryMonth;
            private String expiryYear;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String panSequenceNumber;

        }

        @Data
        static public class NewCardInfo {
            private String accountNumber;
            private String expiryMonth;
            private String expiryYear;
            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String panSequenceNumber;

        }
    }
}
