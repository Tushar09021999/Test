package com.luluoutapi.Dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpirationTransactionResDto {

    private Items items;
    private ResponseStatus responseStatus;

    @Data
    @NoArgsConstructor
    public static class Items {
        private List<BookTransactionItem> bookTransactionItems;
        private List<FeeTransactionItem> feeTransactionItems;
        private List<AdjustmentTransactionItem> adjustmentTransactionItems;
        private List<CardTransactionItem> cardTransactionItems;
        private List<ExternalTransactionItem> externalTransactionItems;
    }

    // public enum Category {
    //     TRX_CAT_BOOK, TRX_CAT_CARD, TRX_CAT_EXTERNAL, TRX_CAT_FEE, TRX_CAT_ADJUSTMENT
    // }

    // public enum Status {
    //     TRX_STATUS_SUCCESS, TRX_STATUS_DECLINED, TRX_STATUS_EXPIRED
    // }

    // public enum Direction {
    //     TRX_DIR_CREDIT, TRX_DIR_DEBIT
    // }

    // public enum Event {
    //     TRX_EVENT_AUTHORIZATION, TRX_EVENT_SETTLEMENT, TRX_EVENT_REVERSAL
    // }

    // public enum Network {
    //     CARD_NET_VISA, CARD_NET_INTERLINK, CARD_NET_PLUS, CARD_NET_MADA,
    //     CARD_NET_MASTERCARD, CARD_NET_MAESTRO, CARD_NET_BANKNET
    // }

    // public enum PaymentMethod {
    //     CARD_PAY_MTHD_MANUAL, CARD_PAY_MTHD_SWIPE, CARD_PAY_MTHD_CONTACTLESS,
    //     CARD_PAY_MTHD_CHIP_AND_PIN, CARD_PAY_MTHD_STORED, CARD_PAY_MTHD_OTHER
    // }

    // public enum DigitalWallet {
    //     CARD_DIG_WALLET_APPLE_PAY, CARD_DIG_WALLET_MADA_PAY,
    //     CARD_DIG_WALLET_SAMSUNG_PAY, CARD_DIG_WALLET_GOOGLE_PAY, CARD_DIG_WALLET_OTHER
    // }

    @Data
    @NoArgsConstructor
    public static class Account {
        private String depositAccountId;
        private String loanAccountId;
        private String externalAccountId;
    }

    @Data
    @NoArgsConstructor
    public static class ForexDetail {
        private String currency;
        private Long currencyExponent;
        private String amount;
        private Double rate;
    }

    @Data
    @NoArgsConstructor
    public static class AmountDetail {
        private String currency;
        private Long currencyExponent;
        private String amount;
        private ForexDetail forexDetail;
    }

    @Data
    @NoArgsConstructor
    public static class RelatedTransaction {
        private String category;
        private String type;
        private String event;
        private String direction;
        private List<AmountDetail> amounts;
    }

    @Data
    @NoArgsConstructor
    public static class ResponseStatus {
        private String constant;
        private String message;
    }

    @Data
    @NoArgsConstructor
    public static class BookTransactionItem {
        private String transactionId;
        private Account originatorAccount;
        private List<String> originatorCustomerId;
        private String category;
        private String type;
        private String event;
        private String direction;
        private String currency;
        private Long currencyExponent;
        private String amount;
        private ForexDetail forexDetail;
        private String description;
        private Account beneficiaryAccount;
        private List<String> beneficiaryCustomerId;
        private String externalReferenceId;
        private List<RelatedTransaction> relatedTransactions;
        private String status;
        private String responseCode;
        private String responseDescription;
        private Map<String, Object> metadata;
        private OffsetDateTime creationTime;
        private OffsetDateTime modifiedTime;
    }

    @Data
    @NoArgsConstructor
    public static class FeeTransactionItem {
        private String transactionId;
        private Account account;
        private List<String> customerId;
        private String category;
        private String type;
        private String event;
        private String direction;
        private String currency;
        private Long currencyExponent;
        private String amount;
        private String description;
        private String externalReferenceId;
        private List<RelatedTransaction> relatedTransactions;
        private String status;
        private String responseCode;
        private String responseDescription;
        private Map<String, Object> metadata;
        private OffsetDateTime creationTime;
        private OffsetDateTime modifiedTime;
        private String cardId;
        private ForexDetail forexDetail;
    }

    @Data
    @NoArgsConstructor
    public static class AdjustmentTransactionItem {
        private String transactionId;
        private Account account;
        private List<String> customerId;
        private String category;
        private String type;
        private String event;
        private String direction;
        private String currency;
        private Long currencyExponent;
        private String amount;
        private String description;
        private String externalReferenceId;
        private List<RelatedTransaction> relatedTransactions;
        private String status;
        private String responseCode;
        private String responseDescription;
        private Map<String, Object> metadata;
        private OffsetDateTime creationTime;
        private OffsetDateTime modifiedTime;
    }

    @Data
    @NoArgsConstructor
    public static class CardTransactionItem {
        private String transactionId;
        private String customerId;
        private String personId;
        private Account account;
        private String cardId;
        private String network;
        private String networkReferenceId;
        private String category;
        private String type;
        private String event;
        private String direction;
        private String transactionCurrency;
        private Long transactionCurrencyExponent;
        private String transactionAmount;
        private String settlementCurrency;
        private Long settlementCurrencyExponent;
        private String settlementAmount;
        private String issuerCurrency;
        private Long issuerCurrencyExponent;
        private String issuerAmount;
        private AcquirerDetails acquirerDetails;
        private String paymentMethod;
        private String digitalWallet;
        private MerchantDetails merchantDetails;
        private TerminalDetails terminalDetails;
        private String authorizationCode;
        private String externalReferenceId;
        private List<RelatedTransaction> relatedTransactions;
        private String status;
        private String rrn;
        private String responseCode;
        private String responseDescription;
        private OffsetDateTime creationTime;
        private OffsetDateTime modifiedTime;
        private ForexDetail forexDetail;
    }

    @Data
    @NoArgsConstructor
    public static class ExternalTransactionItem {
        private String transactionId;
        private Account account;
        private List<String> customerId;
        private String category;
        private String type;
        private String event;
        private String direction;
        private List<AmountDetail> amounts;
        private OffsetDateTime valueDate;
        private String description;
        private String externalReferenceId;
        private List<RelatedTransaction> relatedTransactions;
        private String status;
        private String responseCode;
        private String responseDescription;
        private Map<String, Object> metadata;
        private OffsetDateTime creationTime;
        private OffsetDateTime modifiedTime;
    }

    @Data
    @NoArgsConstructor
    public static class AcquirerDetails {
        private String acquirerId;
        private String acquirerCountry;
    }

    @Data
    @NoArgsConstructor
    public static class MerchantDetails {
        private String name;
        private String mcc;
        private String mid;
    }

    @Data
    @NoArgsConstructor
    public static class TerminalDetails {
        private String terminalId;
        private String location;
    }
}