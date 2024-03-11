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
import ir.shenakht.paint.domain.Judgment;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface JudgeUserView {

    public Integer getId();

    @JsonProperty("first_name")
    public String getFirstName();

    @JsonProperty("last_name")
    public String getLastName();

    @JsonProperty("username")
    public String getUsername();

    public String getPhone();

    public String getEmail();

    @JsonProperty("create_at")
    public Date getCreateAt();

    @JsonProperty("update_at")
    public Date getUpdateAt();

    @JsonProperty("last_login")
    public Date getLastLogin();

    @JsonProperty("user_code")
    public String getUserCode();

    public String getPassword();

    public boolean getActive();

    @JsonIgnore
    public boolean getDeleteUser();

    public Integer getGender();

    @JsonProperty("type_user")
    public Integer getTypeUser();

    @JsonIgnore
    public List<Judgment> getJudgmentList();

}
