package com.caf.valididty.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caf.valididty.domain.Boxassign;
import com.caf.valididty.domain.Scancaf;
import com.caf.valididty.domain.System;
import com.caf.valididty.repository.BoxassignRepository;
import com.caf.valididty.repository.search.BoxassignSearchRepository;
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

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.caf.valididty.web.rest.MobileValidationResource.getUserName;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Boxassign.
 */
@RestController
@RequestMapping("/api")
public class BoxassignResource {

    private final Logger log = LoggerFactory.getLogger(BoxassignResource.class);

    private static final String ENTITY_NAME = "boxassign";

    private final BoxassignRepository boxassignRepository;

    private final BoxassignSearchRepository boxassignSearchRepository;
    public BoxassignResource(BoxassignRepository boxassignRepository, BoxassignSearchRepository boxassignSearchRepository) {
        this.boxassignRepository = boxassignRepository;
        this.boxassignSearchRepository = boxassignSearchRepository;
    }

    /**
     * POST  /boxassigns : Create a new boxassign.
     *
     * @param boxassign the boxassign to create
     * @return the ResponseEntity with status 201 (Created) and with body the new boxassign, or with status 400 (Bad Request) if the boxassign has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/boxassigns")
    @Timed
    public ResponseEntity<Boxassign> createBoxassign(@RequestBody Boxassign boxassign) throws URISyntaxException {
        log.debug("REST request to save Boxassign : {}", boxassign);
        if (boxassign.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new boxassign cannot already have an ID")).body(null);
        }
        Boxassign result = boxassignRepository.save(boxassign);
        boxassignSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/boxassigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /boxassigns : Updates an existing boxassign.
     *
     * @param boxassign the boxassign to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated boxassign,
     * or with status 400 (Bad Request) if the boxassign is not valid,
     * or with status 500 (Internal Server Error) if the boxassign couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/boxassigns")
    @Timed
    public ResponseEntity<Boxassign> updateBoxassign(@RequestBody Boxassign boxassign) throws URISyntaxException {
        log.debug("REST request to update Boxassign : {}", boxassign);
        if (boxassign.getId() == null) {
            return createBoxassign(boxassign);
        }
        Boxassign result = boxassignRepository.save(boxassign);
        boxassignSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, boxassign.getId().toString()))
            .body(result);
    }

    /**
     * GET  /boxassigns : get all the boxassigns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of boxassigns in body
     */
    @GetMapping("/boxassigns")
    @Timed
    public ResponseEntity<List<Boxassign>> getAllBoxassigns(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Boxassigns");
        Page<Boxassign> page = boxassignRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/boxassigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /boxassigns/:id : get the "id" boxassign.
     *
     * @param id the id of the boxassign to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the boxassign, or with status 404 (Not Found)
     */
    @GetMapping("/boxassigns/{id}")
    @Timed
    public ResponseEntity<Boxassign> getBoxassign(@PathVariable Long id) {
        log.debug("REST request to get Boxassign : {}", id);
        Boxassign boxassign = boxassignRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(boxassign));
    }

    /**
     * DELETE  /boxassigns/:id : delete the "id" boxassign.
     *
     * @param id the id of the boxassign to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/boxassigns/{id}")
    @Timed
    public ResponseEntity<Void> deleteBoxassign(@PathVariable Long id) {
        log.debug("REST request to delete Boxassign : {}", id);
        boxassignRepository.delete(id);
        boxassignSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/boxassigns?query=:query : search for the boxassign corresponding
     * to the query.
     *
     * @param query the query of the boxassign search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/boxassigns")
    @Timed
    public ResponseEntity<List<Boxassign>> searchBoxassigns(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Boxassigns for query {}", query);
        Page<Boxassign> page = boxassignSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/boxassigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @PostMapping("/boxassigns/getCatLatest")
    @Timed
    public Boxassign getCatLatest() {
        Boxassign assign = new Boxassign();
        	assign = boxassignRepository.getCatAll();
        	if(assign == null) {
        		assign = new Boxassign();
        		assign.setBoxassign("EKA00000"+","+"EC100000"+","+"EC200000"+","+"EC300000"+","+"EDA00000"+",ERV00000"+",ENA00000");
        	}
 		return assign;
    }

    @GetMapping("/boxassigns/getSystemName")
    @Timed
    public ResponseEntity<Boxassign> getSystemByName() {
    	Boxassign system =boxassignRepository.findByuser(getCurrentUserLogin());
    	return ResponseEntity.ok().body(system);
    }

    public String getCurrentUserLogin() {
        return getUserName();


    }



}
