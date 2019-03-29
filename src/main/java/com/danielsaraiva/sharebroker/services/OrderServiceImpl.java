package com.danielsaraiva.sharebroker.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.danielsaraiva.sharebroker.api.v1.model.BuyOrderDTO;
import com.danielsaraiva.sharebroker.api.v1.model.SellOrderDTO;
import com.danielsaraiva.sharebroker.domain.Buyer;
import com.danielsaraiva.sharebroker.domain.Company;
import com.danielsaraiva.sharebroker.domain.Share;
import com.danielsaraiva.sharebroker.emailsender.EmailSender;
import com.danielsaraiva.sharebroker.repositories.BuyerRepository;
import com.danielsaraiva.sharebroker.repositories.ShareRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	private ShareRepository shareRepository;
	private BuyerRepository buyerRepository;
	
	public OrderServiceImpl(ShareRepository shareRepository,
			BuyerRepository buyerRepository) {
		this.shareRepository = shareRepository;
		this.buyerRepository = buyerRepository;
	}
	
	@Override
	public SellOrderDTO newSellOrder(SellOrderDTO sellOrderDTO) {
		if (sellOrderDTO.getQuantity() == null || sellOrderDTO.getQuantity() <= 0) {
			throw new IllegalArgumentException("Quantidade a venda não pode ser menor ou igual a zero");
		}
		
		return executeSellOrderDTO(sellOrderDTO);
	}

	@Override
	public BuyOrderDTO newBuyOrder(BuyOrderDTO buyOrderDTO) {
		if (buyOrderDTO.getQuantity() == null || buyOrderDTO.getQuantity() <= 0) {
			throw new IllegalArgumentException("Quantidade a comprar não pode ser menor ou igual a zero");
		}
		
		return executeBuyOrderDTO(buyOrderDTO);
	}

	@Override
	public SellOrderDTO executeSellOrderDTO(SellOrderDTO sellOrderDTO) {
		List<Share> availableShares = shareRepository.
				findByBuyerIdAndCompanyId(sellOrderDTO.getBuyerId(), 
										  sellOrderDTO.getCompanyId());
		
		if (availableShares.size() == 0) {
			throw new IllegalArgumentException("Não existem ações disponíveis para o comprador/empresa");
		}
		
		int toIndex = sellOrderDTO.getQuantity();
		if (toIndex > availableShares.size()) {
			toIndex = availableShares.size();
		}

		availableShares.subList(0, toIndex)
			.forEach(share -> {
				share.setIsForSale(true);
				share.setSaleValue(sellOrderDTO.getValue());
				shareRepository.save(share);
			});
		
		sellOrderDTO.setQuantity(toIndex);
		return sellOrderDTO;
	}

	@Override
	@Transactional
	public BuyOrderDTO executeBuyOrderDTO(BuyOrderDTO buyOrderDTO) {
		// carrega o buyer
		Buyer newBuyer = getBuyerById(buyOrderDTO.getBuyerId());
				
		List<Share> availableShares = shareRepository
				.findByCompanyId(buyOrderDTO.getCompanyId());
		
		if (availableShares.size() == 0) {
			throw new IllegalArgumentException("Não existem ações disponíveis para compra");
		}
		
		// filtra acoes a venda com valor igual ou menor ao de compra
		List<Share> filteredShares = availableShares.stream()
				.filter(share -> {
					return share.getIsForSale() && share.getSaleValue() <= buyOrderDTO.getValue();
				})
				.collect(Collectors.toList());
		
		int toIndex = buyOrderDTO.getQuantity();
		if (toIndex > filteredShares.size()) {
			toIndex = filteredShares.size();
		}
		
		// guarda quantidades por buyer antigo
		Map<Buyer, LongAdder> mapBuyerQuantidade = new ConcurrentHashMap<Buyer, LongAdder>();
		
		filteredShares.subList(0, toIndex)
			.forEach(share -> {
				mapBuyerQuantidade
					.computeIfAbsent(share.getBuyer(), k -> new LongAdder())
					.increment();
				
				share.setIsForSale(false);
				share.setCurrentValue(buyOrderDTO.getValue());
				share.setBuyer(newBuyer);
				shareRepository.save(share);
			});
		
		// notifica os buyers
		Company company = filteredShares.get(0).getCompany();
		notificaBuyer(newBuyer, company, toIndex, buyOrderDTO.getValue(), "compra");
		mapBuyerQuantidade.forEach((oldBuyer, quantity) -> notificaBuyer(oldBuyer, company, quantity.intValue(), buyOrderDTO.getValue(), "venda"));
		
		buyOrderDTO.setQuantity(toIndex);
		return buyOrderDTO;
	}
	
	
	private Buyer getBuyerById(Long id) {
		Optional<Buyer> optionalBuyer = buyerRepository.findById(id);
		
		if (!optionalBuyer.isPresent()) {
			throw new IllegalArgumentException("Comprador não localizado");
		}
		
		return optionalBuyer.get();
	}
	
	private void notificaBuyer(Buyer buyer, Company company, Integer quantity, Double value, String action) {
		EmailSender emailSender = new EmailSender();
		System.out.println("Envia email para: " + buyer.getEmail());
		
		String subject = "NOTIFICACAO DE " + action.toUpperCase();
		String body = String.format("<h3>Ordem de %s executada com suceso!</h3>" + 
				"<div>Empresa: %s</div><div>Quantidade: %s</div><div>Valor Unitario: %s</div>", 
				action, company.getName(), quantity.toString(), value.toString());
		
		emailSender.sendEmail(buyer.getEmail(), subject, body);
	}

}
