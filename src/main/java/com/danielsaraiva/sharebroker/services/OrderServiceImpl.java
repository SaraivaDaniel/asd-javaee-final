package com.danielsaraiva.sharebroker.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.danielsaraiva.sharebroker.api.v1.model.BuyOrderDTO;
import com.danielsaraiva.sharebroker.api.v1.model.SellOrderDTO;
import com.danielsaraiva.sharebroker.domain.Buyer;
import com.danielsaraiva.sharebroker.domain.Share;
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
	public BuyOrderDTO executeBuyOrderDTO(BuyOrderDTO buyOrderDTO) {
		// carrega o buyer
		Buyer buyer = getBuyerById(buyOrderDTO.getBuyerId());
				
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
		
		filteredShares.subList(0, toIndex)
			.forEach(share -> {
				share.setIsForSale(false);
				share.setCurrentValue(buyOrderDTO.getValue());
				share.setBuyer(buyer);
				shareRepository.save(share);
			});
		
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

}
