package it.yourstore.store.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import it.yourstore.store.domain.Utente;
import it.yourstore.store.repository.UtenteRepository;
import it.yourstore.store.service.UtenteService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LoginController {
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private UtenteService utenteService;

	@GetMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		if (principal != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", principal.getAttribute("name"));
			map.put("id", principal.getName());
			return map;
		} else {
			return null;
		}
	}

	@GetMapping("/utenteId")
	public Map<String, Object> getUtenteId(@RequestParam String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", utenteRepository.findByOauthId(id).get().getUtenteId());
		map.put("isAdmin", utenteRepository.findByOauthId(id).get().getIsAdmin());
		return map;
	}

	@RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
	public RedirectView loginSuccess(Model model, OAuth2AuthenticationToken authentication) {
		OAuth2AuthorizedClient client = authorizedClientService
				.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		String oauthId = client.getPrincipalName();
		String name = authentication.getPrincipal().getAttribute("given_name");
		String surname = authentication.getPrincipal().getAttribute("family_name");
		String email = authentication.getPrincipal().getAttribute("email");
		Utente utente = null;
		if (!utenteRepository.findByOauthId(oauthId).isPresent()) {
			utente = new Utente();
			utente.setEmail(email);
			utente.setOauthId(oauthId);
			utente.setName(name);
			utente.setSurname(surname);
			utente.setIsAdmin(false);
			utenteService.insert(utente);
		} else {
			utente = utenteRepository.findByOauthId(oauthId).get();
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://localhost:8080/home");
		System.out.println("SUCCESS");
		return redirectView;
	}
	
	@RequestMapping(value = "/loginFailure", method = RequestMethod.GET)
	public RedirectView loginFailure(Model model, OAuth2AuthenticationToken authentication) {
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://localhost:8080/home");
		System.out.println("FAILURE");
		return redirectView;
	}

}
