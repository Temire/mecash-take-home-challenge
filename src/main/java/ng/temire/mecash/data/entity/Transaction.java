package ng.temire.mecash.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Transaction_SEQ")
    @SequenceGenerator(name = "Transaction_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "Recipient cannot be empty")
    @Column(name = "to_account")
    private String toAccount;

    @NotEmpty(message = "Sender cannot be empty")
    @Column(name = "from_account")
    private String fromAccount;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "converted_amount")
    private BigDecimal convertedAmount;

    @Column(name = "reference")
    private String reference;

    @Column(name = "to_currency")
    private String toCurrency;

    @Column(name = "from_currency")
    private String fromCurrency;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "initiator")
    private String initiator;

    @Length(message = "Narration exceeds allowed length of 255", max = 255)
    @Column(name = "narration")
    private String narration;

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}