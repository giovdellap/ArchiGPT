package it.yourstore.store.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.yourstore.store.domain.GenericEntity;
import it.yourstore.store.domain.OrderItem;
import it.yourstore.store.domain.Ordine;
import it.yourstore.store.domain.Product;
import it.yourstore.store.domain.Utente;
import it.yourstore.store.exception.DisponibilityException;
import it.yourstore.store.jmsClient.ToDatabaseJMSProducer;
import it.yourstore.store.repository.OrdineRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrdineServiceImpl implements OrdineService {

	private final OrdineRepository ordineRepository;
	// CHILD SERVICES
	private final OrderItemService orderItemService;
	@Autowired
//	@Qualifier("jMSProducer")
	private ToDatabaseJMSProducer producer;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(OrdineServiceImpl.class);

	@Override
	public Page<Ordine> findAll(Pageable pageable) {
		return ordineRepository.findAll(pageable);
	}

	@Override
	public List<Ordine> findAll() {
		return ordineRepository.findAll();
	}

	@Override
	public Optional<Ordine> findByObjectKey(String objectKey) {
		Ordine ordine = new Ordine(objectKey);
		return ordineRepository.findByOrdineId(ordine.getOrdineId());
	}

	@Override
	public boolean exists(Integer id) {
		return ordineRepository.existsById(id);
	}

	@Override
	public Ordine insert(@Valid Ordine entity) {
		return ordineRepository.save(entity);
	}

	@Override
	public Ordine update(@Valid Ordine entity) {
		return ordineRepository.save(entity);
	}

	@Override
	public Optional<Ordine> delete(String objectKey) {
		return findByObjectKey(objectKey).map(ordine -> {
			ordineRepository.delete(ordine);
			return Optional.of(ordine);
		}).orElseGet(Optional::empty);
	}

	@Override
	public Page<Ordine> search(Specification<Ordine> specification, Pageable pageable) {
		return ordineRepository.findAll(specification, pageable);
	}

	@Override
	public void deleteById(Integer id) {
		ordineRepository.deleteById(id);
	}

	@Override
	public Ordine bulkUpdate(Ordine ordine) {
		if (ordine.getTheOrderItem() != null) {
			List<OrderItem> updateTheOrderItem = ordine.getTheOrderItem().stream()
					.filter(child -> !child.isDeletedEntityState()).collect(Collectors.toList());
			List<OrderItem> deleteTheOrderItem = ordine.getTheOrderItem().stream()
					.filter(GenericEntity::isDeletedEntityState).collect(Collectors.toList());
			ordine.setTheOrderItem(updateTheOrderItem);
			deleteTheOrderItem.forEach(child -> orderItemService.deleteById(child.getTheOrderItemKey()));
		}
		Ordine update = this.update(ordine);
		return update;
	}

	@Override
	public Page<Ordine> findByTheUtente(Utente parentEntity, Pageable pageable) {
		return ordineRepository.findByTheUtente(parentEntity, pageable);
	}

	public Ordine buy(Ordine entity) {
		entity.setDate(LocalDateTime.now());
		Ordine update = this.bulkUpdate(entity);
		Collection<OrderItem> orderItems = entity.getTheOrderItem();
		for (OrderItem oi : orderItems) {
			producer.sendPurchasedProductNotification(oi.getProductId(), oi.getAmount());
		}
		return update;
	}

	public Ordine findCurrentOrdineByTheUtente(String utenteId) {
		Utente utente = new Utente(utenteId);
		List<Ordine> listOrdini = findByTheUtente(utente, null).getContent();
		Ordine currentOrdine = null;
		for (Ordine o : listOrdini) {
			if (o.getDate() == null)
				currentOrdine = o;
		}
		if (currentOrdine == null) {
			currentOrdine = new Ordine();
			currentOrdine.setTheUtente(utente);
			currentOrdine.setTotalCost(0.00f);
			insert(currentOrdine);
		}
		return currentOrdine;
	}

	@Override
	public void checkDisponibility(Ordine entity) {
		Collection<OrderItem> orderItems = entity.getTheOrderItem();
		for (OrderItem oi : orderItems) {
			try {
				producer.sendCheckProductAvailabilityRequest(oi.getProductId(), oi.getAmount());
			} catch (Exception e) {
				throw new DisponibilityException(oi.getTheProductObjectKey(), oi.getAmount().toString());
			}
		}
	}

	@Override
	public void updateOrdineCost(OrderItem entity) {
		Ordine ordine = entity.getTheOrdine();
		Product product = entity.getTheProduct();
		Integer amount = entity.getAmount();
		Float oldCost=0F;
		if (ordine.getTotalCost()!=null)
			oldCost= ordine.getTotalCost();
		Float cost = product.getCost();
		Float newCost = cost*amount;
		ordine.setTotalCost(oldCost + newCost);
		update(ordine);
	}

}
