package com.luluoutapi.Dto;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTransactionResDto {

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
    //     TRX_CAT_BOOK,
    //     TRX_CAT_CARD,
    //     TRX_CAT_EXTERNAL,
    //     TRX_CAT_FEE,
    //     TRX_CAT_ADJUSTMENT
    // }

    // public enum Status {
    //     TRX_STATUS_SUCCESS,
    //     TRX_STATUS_DECLINED,
    //     TRX_STATUS_EXPIRED
    // }

    // public enum Direction {
    //     TRX_DIR_CREDIT,
    //     TRX_DIR_DEBIT
    // }

    // public enum Event {
    //     TRX_EVENT_AUTHORIZATION,
    //     TRX_EVENT_SETTLEMENT,
    //     TRX_EVENT_REVERSAL
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

        private Category category;
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

        private Category category;
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
    public static class ExternalTransactionItem {
        private String transactionId;
        private Account account;
        private List<String> customerId;

        private Category category;
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
    public static class CardTransactionItem {
        private String transactionId;
        private String customerId;
        private String personId;

        private Account account;
        private String cardId;

        private String network;
        private String networkReferenceId;

        private Category category;
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

        private Object acquirerDetails;
        private String paymentMethod;
        private String digitalWallet;

        private Object merchantDetails;
        private Object terminalDetails;

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
    public static class ResponseStatus {
        private String constant;
        private String message;
    }


    public static void main(String[] args) throws Exception {

        CreateTransactionResDto response = new CreateTransactionResDto();

       
        CreateTransactionResDto.Items items = new CreateTransactionResDto.Items();

       
        CreateTransactionResDto.BookTransactionItem book =
                new CreateTransactionResDto.BookTransactionItem();

        CreateTransactionResDto.Account acc =
                new CreateTransactionResDto.Account();
        acc.setDepositAccountId("dep_acc_001");

        book.setTransactionId("TXN-001");
        book.setOriginatorAccount(acc);
        book.setCurrency("SAR");
        book.setAmount("1000");
        // book.setCategory(CreateTransactionResDto.Category.TRX_CAT_BOOK);
        // book.setEvent(CreateTransactionResDto.Event.TRX_EVENT_SETTLEMENT);
        // book.setDirection(CreateTransactionResDto.Direction.TRX_DIR_DEBIT);
        // book.setStatus(CreateTransactionResDto.Status.TRX_STATUS_SUCCESS);
        book.setCreationTime(OffsetDateTime.now());
        book.setModifiedTime(OffsetDateTime.now());

        items.setBookTransactionItems(Arrays.asList(book));

        response.setItems(items);

      
        CreateTransactionResDto.ResponseStatus status =
                new CreateTransactionResDto.ResponseStatus();

        status.setConstant("200");
        status.setMessage("Transaction created successfully");

        response.setResponseStatus(status);

        
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String json = mapper.writeValueAsString(response);

        System.out.println(json);
    }
}