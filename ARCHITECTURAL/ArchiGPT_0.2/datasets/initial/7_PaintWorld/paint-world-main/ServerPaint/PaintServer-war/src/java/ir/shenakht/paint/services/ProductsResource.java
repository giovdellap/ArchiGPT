/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.Products;
import ir.shenakht.paint.logic.interfaces.ProductsLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author hossien
 */
@Path("products")
public class ProductsResource {

    @EJB
    ProductsLogicIntf pl;

    protected ObjectMapper mapper;

    public ProductsResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProducts(@HeaderParam("user_code") String userCode,
            String data) throws IOException {
        Products products = mapper.readValue(data, Products.class);
        products = pl.createProducts(products);
        if (products != null) {
            String json = mapper.writeValueAsString(products);
            return Response.status(Response.Status.CREATED).entity(json).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProducts(@HeaderParam("user_code") String userCode,
            @PathParam("id") Integer id,
            String data) throws IOException {
        Products products = mapper.readValue(data, Products.class);
        products.setId(id);
        if (pl.updateProducts(products)) {
            String json = mapper.writeValueAsString(products);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProducts(@HeaderParam("user_code") String userCode,
            @PathParam("id") Integer id) {
        if (pl.deleteProducts(id)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(@HeaderParam("user_code") String userCode) throws JsonProcessingException {
        List<Products> productses = pl.findListProducts();
        if (productses != null) {
            String json = mapper.writeValueAsString(productses);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
