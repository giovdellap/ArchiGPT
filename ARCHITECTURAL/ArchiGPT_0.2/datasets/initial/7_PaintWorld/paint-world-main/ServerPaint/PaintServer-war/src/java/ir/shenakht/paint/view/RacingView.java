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
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.domain.ScaleQuestionForRacing;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface RacingView {

    public Integer getId();

    public Date getStart();

    public Date getEnd();

    public String getTitle();

    public String getDescription();

    @JsonProperty("extra_field")
    public String getExtraField();

    @JsonProperty("create_at")
    public Date getCreateAt();

    @JsonProperty("update_at")
    public Date getUpdateAt();

    @JsonIgnore
    public List<Judgment> getJudgmentList();

    @JsonIgnore
    @JsonProperty("sacle_question_for_racing_list")
    public List<ScaleQuestionForRacing> getScaleQuestionForRacingList();

    @JsonIgnore
    public List<Participants> getParticipantsList();

    public String getUrl();
}
