/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.ProductsJpaControllerIntf;
import ir.shenakht.paint.domain.Products;
import ir.shenakht.paint.logic.interfaces.ProductsLogicIntf;
import ir.shenakht.paint.util.FileCodecBase64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hossien
 */
@Stateless
public class ProductsLogic implements ProductsLogicIntf {
    
    @EJB
    private ProductsJpaControllerIntf pj;
    
    @Override
    public Products createProducts(Products products) {
        try {
            if (findProductsByProductsCode(products.getProductCode()) == null) {
                if (products.getUrl() != null) {
                    products.setUrl(FileCodecBase64.uploadFile(products.getUrl(),"zip"));
                }
                products = pj.create(products);
                return products;
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductsLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public boolean updateProducts(Products products) {
        try {
            Products productsOld = findProductsById(products.getId());
            if (products.getPrice() != null) {
                productsOld.setPrice(products.getPrice());
            }
            if (products.getProductCode() != null) {
                productsOld.setProductCode(products.getProductCode());
            }
            if (products.getServiceCode() != null) {
                productsOld.setServiceCode(products.getServiceCode());
            }
            if (products.getTitle() != null) {
                productsOld.setTitle(products.getTitle());
            }
            if (products.getType() != null) {
                productsOld.setType(products.getType());
            }
            if (products.getUrl() != null) {
                productsOld.setUrl(products.getUrl());
            }
            pj.edit(productsOld);
            return true;
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ProductsLogic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProductsLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean deleteProducts(Integer id) {
        Products products = findProductsById(id);
        if (products != null) {
            try {
                pj.destroy(id);
                return true;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(ProductsLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ProductsLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    @Override
    public Products findProductsById(Integer id) {
        return pj.findProducts(id);
    }
    
    @Override
    public List<Products> findListProducts() {
        List<Products> productses = pj.getEntityManager()
                .createNamedQuery("Products.findAll")
                .getResultList();
        return productses.isEmpty() ? null : productses;
    }
    
    @Override
    public Products findProductsByProductsCode(String productCode) {
        List<Products> productses = pj.getEntityManager()
                .createNamedQuery("Products.findByProductCode")
                .setParameter("productCode", productCode)
                .getResultList();
        return productses.isEmpty() ? null : productses.get(0);
    }
    
}
