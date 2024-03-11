/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.view;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ir.shenakht.paint.domain.City;
import ir.shenakht.paint.domain.Judgment;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.view.serializer.RacingSerializer;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface ParticipantsView {

    public Integer getId();

    @JsonProperty("first_name")
    public String getFirstName();

    @JsonProperty("last_name")
    public String getLastName();

    public Integer getAge();

    public String getUrl();

    public String getAddress();

    @JsonProperty("total_score")
    public Integer getTotalScore();

    @JsonProperty("is_selective")
    public boolean getIsSelective();

    @JsonProperty("create_at")
    public Date getCreateAt();

    @JsonProperty("update_at")
    public Date getUpdateAt();

    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("judgment_list")
    public List<Judgment> getJudgmentList();

    @JsonProperty("city_id")
    public City getCityId();

    @JsonSerialize(using = RacingSerializer.class)
    @JsonProperty("racing")
    public Racing getRacingId();

//    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("user")
    public User getUserId();

    @JsonProperty("birth_year")
    public Integer getBirthYear();

    @JsonProperty("birth_month")
    public Integer getBirthMonth();

    @JsonProperty("birth_day")
    public Integer getBirthDay();

    public String getGender();

    public String getCity();

    public String getTag();

    public String getDesciption();

    public String getStatus();

    @JsonProperty("display_time")
    public Date getDisplayTime();

    public Integer getConditionNum();
}
