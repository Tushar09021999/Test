package com.luluoutapi.Dto;

import lombok.Data;

@Data
public class DeleteAccountReqDto {

    private String requestId;
    private EncryptedPayload encryptedPayload;

    @Data
    static public class EncryptedPayload {
        private String accountNumber;

    }

}
