package com.luluoutapi.Dto;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luluoutapi.Dto.CreateTransactionReqDto.BookTransaction.OriginatorAccount;

import lombok.Data;

@Data
public class CreateTransactionReqDto {

    private BookTransaction bookTransaction;
    private FeeTransaction feeTransaction;
    private AdjustmentTransaction adjustmentTransaction;
    private ExternalTransaction externalTransaction;

    public CreateTransactionReqDto() {
    }

    @Data
    public static class Amount {

        private String currency;
        private String amount;
        private Double forexRate;

        public Amount() {
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class BookTransaction {

        private OriginatorAccount originatorAccount;
        private String type;
        private String currency;
        private String amount;
        private ForexOverride forexOverride;
        private String description;
        private OriginatorAccount beneficiaryAccount;
        private String externalReferenceId;
        private String relatedTransactionId;
        private Map<String, Object> metadata;

        public BookTransaction() {
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class OriginatorAccount {

            private String depositAccountId;
            private String loanAccountId;
            private String externalAccountId;

            public OriginatorAccount() {
            }

        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        public static class ForexOverride {

            private Double forexRate;
            private String beneficiaryAccountAmount;

            public ForexOverride() {
            }

        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class FeeTransaction {

        private OriginatorAccount account;
        private String type;
        private String currency;
        private String amount;
        private String description;
        private String externalReferenceId;
        private String relatedTransactionId;
        private Map<String, Object> metadata;

        public FeeTransaction() {
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class AdjustmentTransaction {

        private OriginatorAccount account;
        private String type;
        private String direction;
        private String currency;
        private String amount;
        private String description;
        private String externalReferenceId;
        private String relatedTransactionId;
        private Map<String, Object> metadata;

        public AdjustmentTransaction() {
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class ExternalTransaction {

        private OriginatorAccount account;
        private String type;
        private String event;
        private String direction;
        private List<Amount> amounts;
        private OffsetDateTime valueDate;
        private String description;
        private String externalReferenceId;
        private String relatedTransactionId;
        private Map<String, Object> metadata;

        public ExternalTransaction() {
        }

    }

    // public enum Direction {
    //     TRX_DIR_CREDIT,
    //     TRX_DIR_DEBIT
    // }

    // public enum Event {
    //     TRX_EVENT_AUTHORIZATION,
    //     TRX_EVENT_SETTLEMENT,
    //     TRX_EVENT_REVERSAL
    // }

    public static void main(String[] args) throws Exception {

        CreateTransactionReqDto req = new CreateTransactionReqDto();
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        CreateTransactionReqDto.BookTransaction book = new CreateTransactionReqDto.BookTransaction();

        BookTransaction.OriginatorAccount origin = new BookTransaction.OriginatorAccount();
        origin.setDepositAccountId("dep_acc_001");

        BookTransaction.OriginatorAccount beneficiary = new BookTransaction.OriginatorAccount();
        beneficiary.setDepositAccountId("dep_acc_002");

        BookTransaction.ForexOverride forex = new BookTransaction.ForexOverride();
        forex.setForexRate(3.75);
        forex.setBeneficiaryAccountAmount("1000");

        book.setOriginatorAccount(origin);
        book.setBeneficiaryAccount(beneficiary);
        book.setType("TXN_TYPE_BOOK_DEPACC_TO_DEPACC");
        book.setCurrency("SAR");
        book.setAmount("1000");
        book.setDescription("Internal fund transfer");
        book.setForexOverride(forex);
        book.setExternalReferenceId("EXT-REF-001");

        HashMap<String, Object> meta = new HashMap<>();
        meta.put("orderId", "ORD-123");
        book.setMetadata(meta);

        req.setBookTransaction(book);

        CreateTransactionReqDto.FeeTransaction fee = new CreateTransactionReqDto.FeeTransaction();

        BookTransaction.OriginatorAccount feeAcc = new BookTransaction.OriginatorAccount();
        feeAcc.setDepositAccountId("dep_acc_001");

        fee.setAccount(feeAcc);
        fee.setType("FEE_INTERNAL");
        fee.setCurrency("SAR");
        fee.setAmount("10");
        fee.setDescription("Transfer fee");

        req.setFeeTransaction(fee);

        CreateTransactionReqDto.AdjustmentTransaction adj = new CreateTransactionReqDto.AdjustmentTransaction();

        BookTransaction.OriginatorAccount adjAcc = new BookTransaction.OriginatorAccount();
        adjAcc.setDepositAccountId("dep_acc_001");

        adj.setAccount(adjAcc);
        adj.setType("ADJUSTMENT_INTERNAL");
        // adj.setDirection(CreateTransactionReqDto.Direction.TRX_DIR_DEBIT);
        adj.setCurrency("SAR");
        adj.setAmount("0");
        adj.setDescription("Adjustment entry");

        req.setAdjustmentTransaction(adj);

        CreateTransactionReqDto.ExternalTransaction ext = new CreateTransactionReqDto.ExternalTransaction();

        BookTransaction.OriginatorAccount extAcc = new BookTransaction.OriginatorAccount();
        extAcc.setDepositAccountId("dep_acc_001");

        CreateTransactionReqDto.Amount amount = new CreateTransactionReqDto.Amount();
        amount.setCurrency("SAR");
        amount.setAmount("1000");
        amount.setForexRate(3.75);
        List<CreateTransactionReqDto.Amount> amounts = new ArrayList<>();
        amounts.add(amount);
        ext.setAccount(extAcc);
        ext.setType("EXT_SETTLEMENT");
        // ext.setEvent(CreateTransactionReqDto.Event.TRX_EVENT_SETTLEMENT);
        // ext.setDirection(CreateTransactionReqDto.Direction.TRX_DIR_DEBIT);
        ext.setAmounts(amounts);
        ext.setValueDate(OffsetDateTime.now());
        ext.setDescription("External settlement");
        ext.setExternalReferenceId("EXT-EXT-001");

        req.setExternalTransaction(ext);

        String json = mapper.writeValueAsString(req);
        System.out.println(json);
    }
}