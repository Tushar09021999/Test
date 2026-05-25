package com.luluoutapi.Dto;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReverseTransactionReqDto {

    private BookTransaction bookTransaction;
    private FeeTransaction feeTransaction;
    private AdjustmentTransaction adjustmentTransaction;
    private ExternalTransaction externalTransaction;

    @Data
    @NoArgsConstructor
    public static class BookTransaction {
        private String amount;              
        private String description;         
        private Map<String, Object> metadata;
    }

    @Data
    @NoArgsConstructor
    public static class FeeTransaction {
        private String amount;              
        private String description;        
        private Map<String, Object> metadata;
    }


    @Data
    @NoArgsConstructor
    public static class AdjustmentTransaction {
        private String amount;              
        private String description;         
        private Map<String, Object> metadata;
    }


    @Data
    @NoArgsConstructor
    public static class ExternalTransaction {

        private List<Amount> amounts;      
        private String description;         
        private Map<String, Object> metadata;
    }


    @Data
    @NoArgsConstructor
    public static class Amount {
        private String currency;
        private String amount;
    }
}