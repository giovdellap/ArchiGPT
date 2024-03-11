package it.yourstore.gateway.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
	private static final Logger LOGGER = LogManager.getLogger(MainController.class);

	@GetMapping("/home")
	public String getHome() {
		return "home";
	}

	@GetMapping("/cart/{id:.+}")
	public String getCarello(@PathVariable String id) {
		return "cart";
	}

	@GetMapping(value= {"/product/{id:.+}"})
	public String getProdotto(@PathVariable(name = "id") String id) {
		return "product";
	}

	@GetMapping("/shipping")
	public String getShipping() {
		return "shipping";
	}
	
	@GetMapping("/addProduct")
	public String getAddProduct() {
		return "addProduct";
	}
	
	@GetMapping("/analytics")
    public String getAnalytics() {
        return "adminAnalytics";
    }
	@GetMapping("/listOrder")
    public String getListOrder() {
        return "listaOrdini";
    }
	@GetMapping("/order/{id:.+}")
    public String getOrder(@PathVariable String id) {
        return "order";
    }

}
