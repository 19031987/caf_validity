package com.caf.valididty.service.mapper;

import com.caf.valididty.domain.*;
import com.caf.valididty.service.dto.MobileValidationDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity MobileValidation and its DTO MobileValidationDTO.
 */
@Service
@Mapper(componentModel = "spring", uses = {})
public interface MobileValidationMapper extends EntityMapper <MobileValidationDTO, MobileValidation> {
    
    
    default MobileValidation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MobileValidation mobileValidation = new MobileValidation();
        mobileValidation.setId(id);
        return mobileValidation;
    }
}
