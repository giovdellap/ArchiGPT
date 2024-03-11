/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.view;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author hossien
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public interface ProductsView {

    public Integer getId();

    public String getProductCode();

    public String getServiceCode();

    public String getType();

    public String getTitle();

    public String getPrice();

    public String getUrl();

}
