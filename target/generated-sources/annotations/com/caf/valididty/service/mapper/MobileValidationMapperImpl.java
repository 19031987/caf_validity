package com.caf.valididty.service.mapper;

import com.caf.valididty.domain.MobileValidation;
import com.caf.valididty.service.dto.MobileValidationDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-02-14T03:08:58+0530",
    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_144 (Oracle Corporation)"
)
@Component
public class MobileValidationMapperImpl implements MobileValidationMapper {

    @Override
    public MobileValidation toEntity(MobileValidationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MobileValidation mobileValidation = new MobileValidation();

        mobileValidation.setId( dto.getId() );
        mobileValidation.setMobilenumber( dto.getMobilenumber() );
        mobileValidation.setActivationDate( dto.getActivationDate() );
        mobileValidation.setCustomerName( dto.getCustomerName() );
        mobileValidation.setColorCode( dto.getColorCode() );
        mobileValidation.setUser( dto.getUser() );
        mobileValidation.setUserDate( dto.getUserDate() );

        return mobileValidation;
    }

    @Override
    public MobileValidationDTO toDto(MobileValidation entity) {
        if ( entity == null ) {
            return null;
        }

        MobileValidationDTO mobileValidationDTO = new MobileValidationDTO();

        mobileValidationDTO.setId( entity.getId() );
        mobileValidationDTO.setMobilenumber( entity.getMobilenumber() );
        mobileValidationDTO.setActivationDate( entity.getActivationDate() );
        mobileValidationDTO.setCustomerName( entity.getCustomerName() );
        mobileValidationDTO.setColorCode( entity.getColorCode() );
        mobileValidationDTO.setUser( entity.getUser() );
        mobileValidationDTO.setUserDate( entity.getUserDate() );

        return mobileValidationDTO;
    }

    @Override
    public List<MobileValidation> toEntity(List<MobileValidationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MobileValidation> list = new ArrayList<MobileValidation>();
        for ( MobileValidationDTO mobileValidationDTO : dtoList ) {
            list.add( toEntity( mobileValidationDTO ) );
        }

        return list;
    }

    @Override
    public List<MobileValidationDTO> toDto(List<MobileValidation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MobileValidationDTO> list = new ArrayList<MobileValidationDTO>();
        for ( MobileValidation mobileValidation : entityList ) {
            list.add( toDto( mobileValidation ) );
        }

        return list;
    }
}
