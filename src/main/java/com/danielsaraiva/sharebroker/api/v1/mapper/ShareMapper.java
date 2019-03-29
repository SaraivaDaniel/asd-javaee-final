package com.danielsaraiva.sharebroker.api.v1.mapper;

import org.springframework.stereotype.Component;

import com.danielsaraiva.sharebroker.api.v1.model.ShareDTO;
import com.danielsaraiva.sharebroker.domain.Share;

@Component
public class ShareMapper {
	
	public ShareDTO shareToShareDTO(Share share) {
		final ShareDTO shareDTO = new ShareDTO();
		shareDTO.setId(share.getId());
		shareDTO.setCompany(share.getCompany());
		shareDTO.setBuyer(share.getBuyer());
		shareDTO.setCurrentValue(share.getCurrentValue());
		shareDTO.setOriginalValue(share.getOriginalValue());
		shareDTO.setIsForSale(share.getIsForSale());
		
		if (share.getIsForSale()) {
			shareDTO.setSaleValue(share.getSaleValue());
		}
		
		return shareDTO;
	}
	
	public Share shareDTOToShare(ShareDTO shareDTO) {
		final Share share = new Share();
		share.setId(shareDTO.getId());
		share.setCompany(shareDTO.getCompany());
		share.setBuyer(shareDTO.getBuyer());
		share.setCurrentValue(shareDTO.getCurrentValue());
		share.setOriginalValue(shareDTO.getOriginalValue());
		share.setIsForSale(shareDTO.getIsForSale());
		
		if (shareDTO.getIsForSale()) {
			share.setSaleValue(shareDTO.getSaleValue());
		}
		
		return share;
	}

}
