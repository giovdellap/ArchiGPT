package com.lap.springbootdocker.Controller;

import com.lap.springbootdocker.Security.services.UserDetailsServiceImpl;
import com.lap.springbootdocker.Service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lap.springbootdocker.Model.User;
import com.lap.springbootdocker.Repository.UserRepository;
import com.lap.springbootdocker.Security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MainController {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private MainServiceConsumer consumer;

	@Autowired
	private UserDetailsServiceImpl userDetails;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public RedirectView redirectIndex(RedirectAttributes attributes) {
		return new RedirectView("/index.html");
	}

	@GetMapping("/crypto")
	public RedirectView redirectCrypto(RedirectAttributes attributes) {
		return new RedirectView("/crypto.html");
	}

	@PostMapping(value = "/search")
	public ResponseEntity<?> QuerySearch(@RequestBody String q) throws Exception {
		String data = consumer.GetSearchResult(q); // per avere l'oggetto JSON in output
		return new ResponseEntity<String>(data, HttpStatus.OK);
	}

	@GetMapping("/signin")
	public RedirectView redirectSignin(RedirectAttributes attributes) {
		return new RedirectView("/signin.html");
	}

	@GetMapping("/signup")
	public RedirectView redirectSignup(RedirectAttributes attributes) {
		return new RedirectView("/signup.html");
	}

	@GetMapping("/isAuthenticated")
	public ResponseEntity<String> isAuthenticated(@RequestParam("jwt") String jwt) {
		try {
			if (!jwtUtils.validateJwtToken(jwt)) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			String username = jwtUtils.getUserNameFromJwtToken(jwt);
			UserDetails user = userDetails.loadUserByUsername(username);
			String json = new ObjectMapper().writeValueAsString(user);
			System.out.println(json);
			return ResponseEntity.ok(json);
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping(value = "/data")
	public ResponseEntity<?> getData() throws Exception {
		String data = consumer.GetData(); // per avere l'oggetto JSON
		return new ResponseEntity<String>(data, HttpStatus.OK);
	}

	@GetMapping(value = "/news")
	public ResponseEntity<?> getNews() throws Exception {
		String news = consumer.GetNews(); // per avere l'oggetto JSON
		return new ResponseEntity<String>(news, HttpStatus.OK);
	}

	@GetMapping(value = "/boxes")
	public ResponseEntity<?> getBoxes() throws Exception {
		String result = consumer.GetBoxes(); // per avere l'oggetto JSON
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/crypto_data")
	public ResponseEntity<?> getCryptoData() throws Exception {
		String data = consumer.GetCryptoData(); // per avere l'oggetto JSON
		return new ResponseEntity<String>(data, HttpStatus.OK);
	}

	@GetMapping(value = "/graph")
	public ResponseEntity<Void> redirectGraphPage(HttpServletRequest request, @RequestParam String symbol)
			throws Exception {
		String queryString = request.getQueryString();
		String redirectUrl = "/stock.html";
		if (queryString != null) {
			redirectUrl += "?" + queryString;
		}
		return ResponseEntity.status(302).header("Location", redirectUrl).build();
	}

	@GetMapping(value = "/graph_data")
	public ResponseEntity<?> getGraphData(@RequestParam String symbol) throws Exception {
		String data = consumer.GetGraph(symbol);
		return new ResponseEntity<String>(data, HttpStatus.OK);
	}

	@GetMapping(value = "/stock_data")
	public ResponseEntity<?> getStockData(@RequestParam String symbol) throws Exception {
		String data = consumer.GetStockData(symbol);
		return new ResponseEntity<String>(data, HttpStatus.OK);
	}

	@GetMapping(value = "/profile")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public RedirectView redirectProfile(RedirectAttributes attributes) {
		return new RedirectView("/profile.html");
	}


	// INSERT METHOD
	@GetMapping("/add_favorite")
	@PreAuthorize("hasRole('USER')")
	public String addFavorite(@RequestParam String symbol, @RequestParam("jwt") String jwt) throws Exception {
		String username;
		try {
			if (!jwtUtils.validateJwtToken(jwt)) {
				return "false";
			}
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		} catch (Exception e) {
			System.out.println(e);
			return "false";
		}
		try {
			User u = userRepository.findUserByUsername(username);
			Set<String> favorites = u.getFavorites();
			favorites.add(symbol);
			u.setFavorites(favorites);
			userRepository.save(u);
		}
		catch (Exception e) {
			System.out.println(e);
			return "false";
		}
		return "true";
	}

	@GetMapping("/delete_favorite")
	@PreAuthorize("hasRole('USER')")
	public boolean removeFavorite(@RequestParam String symbol, @RequestParam("jwt") String jwt) throws Exception {
		String username;
		try {
			if (!jwtUtils.validateJwtToken(jwt)) {
				return false;
			}
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		User u = userRepository.findUserByUsername(username);
		Set<String> favorites = u.getFavorites();
		favorites.remove(symbol);
		u.setFavorites(favorites);
		userRepository.save(u);

		return true;
	}

	// SHOW METHOD: THIS FUNCTION CAN BE CALLED FROM SCRIPT AFTER favorite.html LOAD
	@GetMapping(value = "/favorite_data")
	public ResponseEntity<String> getUserFavorites(@RequestParam("jwt") String jwt) throws Exception {
		String username;
		
		try {
			if (!jwtUtils.validateJwtToken(jwt)) {
				return new ResponseEntity<String>(null, null, 0);
			}
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<String>(null, null, 0);
		}

		User u=userRepository.findUserByUsername(username);
		Set<String> favorites= u.getFavorites();
		ObjectMapper objectMapper = new ObjectMapper();
		String symbols = objectMapper.writeValueAsString(favorites);
		return new ResponseEntity<String>(symbols, HttpStatus.OK);
	}

	@GetMapping(value = "/is_in_favorites")
	public String isInFavorites(@RequestParam("symbol") String symbol, @RequestParam("jwt") String jwt) throws Exception {
		String username;

		try {
			if (!jwtUtils.validateJwtToken(jwt)) {
				return "false";
			}
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		} catch (Exception e) {
			System.out.println(e);
			return "false";
		}

		User u=userRepository.findUserByUsername(username);
		Set<String> favorites = u.getFavorites();
		if (favorites.contains(symbol)) {
			return "true";
		}
		return "false";
	}
	// ***********************************************************************************************************
}
