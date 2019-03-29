package com.danielsaraiva.sharebroker.api.v1.mapper;

import org.springframework.stereotype.Component;

import com.danielsaraiva.sharebroker.api.v1.model.ShareDTO;
import com.danielsaraiva.sharebroker.domain.Share;

@Component
public class ShareMapper {
	
	public ShareDTO shareToShareDTO(Share share) {
		final ShareDTO shareDTO = new ShareDTO();
		
		shareDTO.setId(share.getId());
		shareDTO.setCompanyId(share.getCompany().getId());
		
		if (share.getBuyer() != null) {
			shareDTO.setBuyerId(share.getBuyer().getId());
		}
		
		shareDTO.setCurrentValue(share.getCurrentValue());
		shareDTO.setOriginalValue(share.getOriginalValue());
		shareDTO.setIsForSale(share.getIsForSale());
		shareDTO.setSaleValue(share.getSaleValue());
		
		return shareDTO;
	}

}
