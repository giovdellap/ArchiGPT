/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.City;
import ir.shenakht.paint.domain.Discount;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.domain.GalleryCost;
import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.domain.Judgment;
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.domain.PaymentIrancel;
import ir.shenakht.paint.domain.Picture;
import ir.shenakht.paint.domain.Products;
import ir.shenakht.paint.domain.Provice;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.domain.ScaleQuestionForRacing;
import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.view.CityView;
import ir.shenakht.paint.view.DiscountView;
import ir.shenakht.paint.view.GalleryCostView;
import ir.shenakht.paint.view.GalleryView;
import ir.shenakht.paint.view.JudgeUserView;
import ir.shenakht.paint.view.JudgmentView;
import ir.shenakht.paint.view.ParticipantsView;
import ir.shenakht.paint.view.PaymentIrancelView;
import ir.shenakht.paint.view.PictureView;
import ir.shenakht.paint.view.ProductsView;
import ir.shenakht.paint.view.ProviceView;
import ir.shenakht.paint.view.QuestionView;
import ir.shenakht.paint.view.RacingView;
import ir.shenakht.paint.view.ScaleQuestionForRacingView;
import ir.shenakht.paint.view.UserView;

/**
 *
 * @author hossien
 */
public class ConfigMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.addMixIn(User.class, UserView.class);
        mapper.addMixIn(Gallery.class, GalleryView.class);
        mapper.addMixIn(Picture.class, PictureView.class);
        mapper.addMixIn(Participants.class, ParticipantsView.class);
        mapper.addMixIn(JudgeUser.class, JudgeUserView.class);
        mapper.addMixIn(Judgment.class, JudgmentView.class);
        mapper.addMixIn(Provice.class, ProviceView.class);
        mapper.addMixIn(City.class, CityView.class);
        mapper.addMixIn(Question.class, QuestionView.class);
        mapper.addMixIn(Racing.class, RacingView.class);
        mapper.addMixIn(ScaleQuestionForRacing.class, ScaleQuestionForRacingView.class);
        mapper.addMixIn(GalleryCost.class, GalleryCostView.class);
        mapper.addMixIn(Discount.class, DiscountView.class);
        mapper.addMixIn(Products.class, ProductsView.class);
        mapper.addMixIn(PaymentIrancel.class, PaymentIrancelView.class);
    }

    public static ObjectMapper getInstance() {
        return mapper;
    }
}
