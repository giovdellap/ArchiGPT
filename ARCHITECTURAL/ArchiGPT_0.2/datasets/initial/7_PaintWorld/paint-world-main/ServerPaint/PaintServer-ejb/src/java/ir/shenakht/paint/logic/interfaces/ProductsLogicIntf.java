/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.Products;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface ProductsLogicIntf {

    Products createProducts(Products products);

    boolean updateProducts(Products products);

    boolean deleteProducts(Integer id);

    Products findProductsById(Integer id);

    Products findProductsByProductsCode(String productsCode);

    List<Products> findListProducts();

}
