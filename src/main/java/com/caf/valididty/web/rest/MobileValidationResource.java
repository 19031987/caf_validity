package com.caf.valididty.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caf.valididty.domain.MobileValidation;
import com.caf.valididty.domain.Scancaf;
import com.caf.valididty.repository.MobileValidationRepository;
import com.caf.valididty.repository.search.MobileValidationSearchRepository;
import com.caf.valididty.web.rest.util.HeaderUtil;
import com.caf.valididty.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private final MobileValidationSearchRepository mobileValidationSearchRepository;
    public MobileValidationResource(MobileValidationRepository mobileValidationRepository, MobileValidationSearchRepository mobileValidationSearchRepository) {
        this.mobileValidationRepository = mobileValidationRepository;
        this.mobileValidationSearchRepository = mobileValidationSearchRepository;
    }

    /**
     * POST  /mobile-validations : Create a new mobileValidation.
     *
     * @param mobileValidation the mobileValidation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mobileValidation, or with status 400 (Bad Request) if the mobileValidation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mobile-validations")
    @Timed
    public ResponseEntity<MobileValidation> createMobileValidation(@RequestBody MobileValidation mobileValidation) throws URISyntaxException {
        log.debug("REST request to save MobileValidation : {}", mobileValidation);
        mobileValidation.setId(null);
        if (mobileValidation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mobileValidation cannot already have an ID")).body(null);
        }
        mobileValidation.setUser(getCurrentUserLogin());
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        mobileValidation.setUserDate(localDate);
        MobileValidation result = mobileValidationRepository.save(mobileValidation);
        mobileValidationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mobile-validations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mobile-validations : Updates an existing mobileValidation.
     *
     * @param mobileValidation the mobileValidation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mobileValidation,
     * or with status 400 (Bad Request) if the mobileValidation is not valid,
     * or with status 500 (Internal Server Error) if the mobileValidation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mobile-validations")
    @Timed
    public ResponseEntity<MobileValidation> updateMobileValidation(@RequestBody MobileValidation mobileValidation) throws URISyntaxException {
        log.debug("REST request to update MobileValidation : {}", mobileValidation);
        if (mobileValidation.getId() == null) {
            return createMobileValidation(mobileValidation);
        }
        MobileValidation result = mobileValidationRepository.save(mobileValidation);
        mobileValidationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mobileValidation.getId().toString()))
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
    public ResponseEntity<List<MobileValidation>> getAllMobileValidations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MobileValidations");
        Page<MobileValidation> page = mobileValidationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mobile-validations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mobile-validations/:id : get the "id" mobileValidation.
     *
     * @param id the id of the mobileValidation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mobileValidation, or with status 404 (Not Found)
     */
    @GetMapping("/mobile-validations/{id}")
    @Timed
    public ResponseEntity<MobileValidation> getMobileValidation(@PathVariable Long id) {
        log.debug("REST request to get MobileValidation : {}", id);
        MobileValidation mobileValidation = mobileValidationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mobileValidation));
    }

    /**
     * DELETE  /mobile-validations/:id : delete the "id" mobileValidation.
     *
     * @param id the id of the mobileValidation to delete
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
    public ResponseEntity<List<MobileValidation>> searchMobileValidations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MobileValidations for query {}", query);
        Page<MobileValidation> page = mobileValidationSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mobile-validations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @PostMapping("/getmobilenumber")
    @Timed
    public ResponseEntity<List<MobileValidation>> getMobileValidation(@RequestBody MobileValidation mobileValidation) throws URISyntaxException {
               List<MobileValidation> result = mobileValidationRepository.getMobileNumber(mobileValidation.getMobilenumber());
       return new ResponseEntity<List<MobileValidation>>(result, HttpStatus.ACCEPTED);
    }

    public String getCurrentUserLogin() {
        org.springframework.security.core.context.SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String login = null;
        if (authentication != null)
            if (authentication.getPrincipal() instanceof UserDetails)
            	login = ((UserDetails) authentication.getPrincipal()).getUsername();
            else if (authentication.getPrincipal() instanceof String)
            	login = (String) authentication.getPrincipal();
        
        return login; 
        

}
    @PostMapping("/scancafs/getDetailsByName")
    @Timed
    public ResponseEntity<MobileValidation> getDetailsByName(@RequestBody MobileValidation scancaf) {
    	
    	 MobileValidation scancafLocal  = mobileValidationRepository.findByuserOrderByDsc(scancaf.getUser());
    	if(scancafLocal!= null) {
		return ResponseEntity.ok().body(scancafLocal);
    	}else {
    		return ResponseEntity.badRequest().body(null);
    	}
    		
    	
    
    }

}
