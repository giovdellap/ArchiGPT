/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.view;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ir.shenakht.paint.domain.DiscountGalleryForUser;
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.domain.PaymentIrancel;
import ir.shenakht.paint.domain.UserHasGallery;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface UserView {

    public Integer getId();

    @JsonProperty("user_code")
    public String getUserCode();

    public String getPhone();

    @JsonProperty("create_at")
    public Date getCreateAt();

    @JsonProperty("update_at")
    public Date getUpdateAt();

    public String getEmail();

    @JsonProperty("extra_fields")
    public String getExtraField();

    @JsonIgnore
    @JsonProperty("confirm_code")
    public String getConfirmCode();

    @JsonProperty("last_login")
    public Date getLastLogin();

    @JsonIgnore
    @JsonProperty("time_create_code_confirm")
    public Date getTimeCreateCodeConfrim();

    public boolean getActive();

    @JsonIgnore
    public boolean getDeleteUser();

    public Integer getStatus();

    @JsonProperty("first_name")
    public String getFirstName();

    @JsonProperty("last_name")
    public String getLastName();

    @JsonIgnore
    public List<UserHasGallery> getUserHasGalleryList();

    @JsonIgnore
    public List<DiscountGalleryForUser> getDiscountGalleryForUserList();

    @JsonIgnore
    @JsonProperty("participant_list")
    public List<Participants> getParticipantsList();

    @JsonProperty("payment_irancel_list")
    public List<PaymentIrancel> getPaymentIrancelList();

}
