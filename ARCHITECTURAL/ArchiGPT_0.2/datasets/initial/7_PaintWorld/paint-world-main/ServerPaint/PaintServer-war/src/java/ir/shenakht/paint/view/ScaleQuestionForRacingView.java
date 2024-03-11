/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.view;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.domain.Racing;

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface ScaleQuestionForRacingView {

    public Integer getId();

    public float getScale();

    @JsonIdentityReference(alwaysAsId = true)
    public Question getQuestionId();

    @JsonIdentityReference(alwaysAsId = true)
    public Racing getRacingId();
}
