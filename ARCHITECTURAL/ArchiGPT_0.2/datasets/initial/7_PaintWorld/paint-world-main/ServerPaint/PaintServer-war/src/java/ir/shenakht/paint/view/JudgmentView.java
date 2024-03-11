/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.view;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.view.serializer.RacingSerializer;

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface JudgmentView {

    public Integer getId();

    public float getScore();

    @JsonProperty("judge_user")
    public JudgeUser getJudgeUserId();

    @JsonProperty("paricipants")
    public Participants getParticipants();

    @JsonProperty("question")
    public Question getQuestionId();

    @JsonSerialize(using = RacingSerializer.class)
    @JsonProperty("racing")
    public Racing getRacingId();

}
