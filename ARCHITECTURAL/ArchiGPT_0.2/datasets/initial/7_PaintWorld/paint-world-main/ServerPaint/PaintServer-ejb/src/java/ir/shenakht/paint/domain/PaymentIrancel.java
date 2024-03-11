/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.domain;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hossien
 */
@Entity
@Table(name = "payment_irancel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentIrancel.findAll", query = "SELECT p FROM PaymentIrancel p")
    , @NamedQuery(name = "PaymentIrancel.findByIdpaymentIrancel", query = "SELECT p FROM PaymentIrancel p WHERE p.id = :id")
    , @NamedQuery(name = "PaymentIrancel.findByToken", query = "SELECT p FROM PaymentIrancel p WHERE p.token = :token")
    , @NamedQuery(name = "PaymentIrancel.findByMsisdn", query = "SELECT p FROM PaymentIrancel p  WHERE p.msisdn = :msisdn ORDER BY p.id DESC")})
public class PaymentIrancel implements Serializable {

    @Size(max = 1000)
    @Column(name = "kind")
    private String kind;
    @Column(name = "startTimeMillis")
    private BigInteger startTimeMillis;
    @Size(max = 1000)
    @Column(name = "priceCurrencyCode")
    private String priceCurrencyCode;
    @Column(name = "expiryTimeMillis")
    private BigInteger expiryTimeMillis;
    @Column(name = "autoRenewing")
    private Boolean autoRenewing;
    @Column(name = "priceAmountMircos")
    private BigInteger priceAmountMicros;
    @Size(max = 1000)
    @Column(name = "countryCode")
    private String countryCode;
    @Size(max = 1000)
    @Column(name = "developerPayload")
    private String developerPayload;
    @Column(name = "paymentState")
    private BigInteger paymentState;
    @Column(name = "cancelReason")
    private BigInteger cancelReason;
    @Column(name = "todayPayment")
    private BigInteger todayPayment;
    @Size(max = 1000)
    @Column(name = "cancelChannel")
    private String cancelChannel;
    @Column(name = "totalPayment")
    private BigInteger totalPayment;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 1000)
    @Column(name = "token")
    private String token;
    @Size(max = 45)
    @Column(name = "msisdn")
    private String msisdn;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;

    public PaymentIrancel() {
    }

    public PaymentIrancel(Integer idpaymentIrancel) {
        this.id = idpaymentIrancel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idpaymentIrancel) {
        this.id = idpaymentIrancel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentIrancel)) {
            return false;
        }
        PaymentIrancel other = (PaymentIrancel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ir.shenakht.paint.domain.PaymentIrancel[ idpaymentIrancel=" + id + " ]";
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public BigInteger getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(BigInteger startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public String getPriceCurrencyCode() {
        return priceCurrencyCode;
    }

    public void setPriceCurrencyCode(String priceCurrencyCode) {
        this.priceCurrencyCode = priceCurrencyCode;
    }

    public BigInteger getExpiryTimeMillis() {
        return expiryTimeMillis;
    }

    public void setExpiryTimeMillis(BigInteger expiryTimeMillis) {
        this.expiryTimeMillis = expiryTimeMillis;
    }

    public Boolean getAutoRenewing() {
        return autoRenewing;
    }

    public void setAutoRenewing(Boolean autoRenewing) {
        this.autoRenewing = autoRenewing;
    }

    public BigInteger getPriceAmountMicros() {
        return priceAmountMicros;
    }

    public void setPriceAmountMicros(BigInteger priceAmountMicros) {
        this.priceAmountMicros = priceAmountMicros;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDeveloperPayload() {
        return developerPayload;
    }

    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }

    public BigInteger getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(BigInteger paymentState) {
        this.paymentState = paymentState;
    }

    public BigInteger getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(BigInteger cancelReason) {
        this.cancelReason = cancelReason;
    }

    public BigInteger getTodayPayment() {
        return todayPayment;
    }

    public void setTodayPayment(BigInteger todayPayment) {
        this.todayPayment = todayPayment;
    }

    public String getCancelChannel() {
        return cancelChannel;
    }

    public void setCancelChannel(String cancelChannel) {
        this.cancelChannel = cancelChannel;
    }

    public BigInteger getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigInteger totalPayment) {
        this.totalPayment = totalPayment;
    }

}
