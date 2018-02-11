package com.caf.valididty.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caf.valididty.domain.MobileValidation;

import com.caf.valididty.repository.MobileValidationRepository;
import com.caf.valididty.repository.search.MobileValidationSearchRepository;
import com.caf.valididty.web.rest.util.HeaderUtil;
import com.caf.valididty.web.rest.util.PaginationUtil;
import com.caf.valididty.service.dto.MobileValidationDTO;
import com.caf.valididty.service.mapper.MobileValidationMapper;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MobileValidation.
 */
@RestController
@RequestMapping("/api")
public class MobileValidationResource {

    private final Logger log = LoggerFactory.getLogger(MobileValidationResource.class);

    private static final String ENTITY_NAME = "mobileValidation";

    private final MobileValidationRepository mobileValidationRepository;

    private final MobileValidationMapper mobileValidationMapper;

    private final MobileValidationSearchRepository mobileValidationSearchRepository;
    public MobileValidationResource(MobileValidationRepository mobileValidationRepository, MobileValidationMapper mobileValidationMapper, MobileValidationSearchRepository mobileValidationSearchRepository) {
        this.mobileValidationRepository = mobileValidationRepository;
        this.mobileValidationMapper = mobileValidationMapper;
        this.mobileValidationSearchRepository = mobileValidationSearchRepository;
    }

    /**
     * POST  /mobile-validations : Create a new mobileValidation.
     *
     * @param mobileValidationDTO the mobileValidationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mobileValidationDTO, or with status 400 (Bad Request) if the mobileValidation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mobile-validations")
    @Timed
    public ResponseEntity<MobileValidationDTO> createMobileValidation(@RequestBody MobileValidationDTO mobileValidationDTO) throws URISyntaxException {
        log.debug("REST request to save MobileValidation : {}", mobileValidationDTO);
        if (mobileValidationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mobileValidation cannot already have an ID")).body(null);
        }
        MobileValidation mobileValidation = mobileValidationMapper.toEntity(mobileValidationDTO);
        mobileValidation = mobileValidationRepository.save(mobileValidation);
        MobileValidationDTO result = mobileValidationMapper.toDto(mobileValidation);
        mobileValidationSearchRepository.save(mobileValidation);
        return ResponseEntity.created(new URI("/api/mobile-validations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mobile-validations : Updates an existing mobileValidation.
     *
     * @param mobileValidationDTO the mobileValidationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mobileValidationDTO,
     * or with status 400 (Bad Request) if the mobileValidationDTO is not valid,
     * or with status 500 (Internal Server Error) if the mobileValidationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mobile-validations")
    @Timed
    public ResponseEntity<MobileValidationDTO> updateMobileValidation(@RequestBody MobileValidationDTO mobileValidationDTO) throws URISyntaxException {
        log.debug("REST request to update MobileValidation : {}", mobileValidationDTO);
        if (mobileValidationDTO.getId() == null) {
            return createMobileValidation(mobileValidationDTO);
        }
        MobileValidation mobileValidation = mobileValidationMapper.toEntity(mobileValidationDTO);
        mobileValidation = mobileValidationRepository.save(mobileValidation);
        MobileValidationDTO result = mobileValidationMapper.toDto(mobileValidation);
        mobileValidationSearchRepository.save(mobileValidation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mobileValidationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mobile-validations : get all the mobileValidations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mobileValidations in body
     */
    @GetMapping("/mobile-validations")
    @Timed
    public ResponseEntity<List<MobileValidationDTO>> getAllMobileValidations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MobileValidations");
        Page<MobileValidation> page = mobileValidationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mobile-validations");
        return new ResponseEntity<>(mobileValidationMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /mobile-validations/:id : get the "id" mobileValidation.
     *
     * @param id the id of the mobileValidationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mobileValidationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mobile-validations/{id}")
    @Timed
    public ResponseEntity<MobileValidationDTO> getMobileValidation(@PathVariable Long id) {
        log.debug("REST request to get MobileValidation : {}", id);
        MobileValidation mobileValidation = mobileValidationRepository.findOne(id);
        MobileValidationDTO mobileValidationDTO = mobileValidationMapper.toDto(mobileValidation);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mobileValidationDTO));
    }

    /**
     * DELETE  /mobile-validations/:id : delete the "id" mobileValidation.
     *
     * @param id the id of the mobileValidationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mobile-validations/{id}")
    @Timed
    public ResponseEntity<Void> deleteMobileValidation(@PathVariable Long id) {
        log.debug("REST request to delete MobileValidation : {}", id);
        mobileValidationRepository.delete(id);
        mobileValidationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mobile-validations?query=:query : search for the mobileValidation corresponding
     * to the query.
     *
     * @param query the query of the mobileValidation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mobile-validations")
    @Timed
    public ResponseEntity<List<MobileValidationDTO>> searchMobileValidations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MobileValidations for query {}", query);
        Page<MobileValidation> page = mobileValidationSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mobile-validations");
        return new ResponseEntity<>(mobileValidationMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

}
