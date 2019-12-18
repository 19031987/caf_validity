package com.caf.valididty.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caf.valididty.domain.Suspendedusers;

import com.caf.valididty.repository.SuspendedusersRepository;
import com.caf.valididty.repository.search.SuspendedusersSearchRepository;
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
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.caf.valididty.web.rest.MobileValidationResource.getUserName;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Suspendedusers.
 */
@RestController
@RequestMapping("/api")
public class SuspendedusersResource {

    private final Logger log = LoggerFactory.getLogger(SuspendedusersResource.class);

    private static final String ENTITY_NAME = "suspendedusers";

    private final SuspendedusersRepository suspendedusersRepository;

    private final SuspendedusersSearchRepository suspendedusersSearchRepository;
    public SuspendedusersResource(SuspendedusersRepository suspendedusersRepository, SuspendedusersSearchRepository suspendedusersSearchRepository) {
        this.suspendedusersRepository = suspendedusersRepository;
        this.suspendedusersSearchRepository = suspendedusersSearchRepository;
    }

    /**
     * POST  /suspendedusers : Create a new suspendedusers.
     *
     * @param suspendedusers the suspendedusers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new suspendedusers, or with status 400 (Bad Request) if the suspendedusers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/suspendedusers")
    @Timed
    public ResponseEntity<Suspendedusers> createSuspendedusers(@RequestBody Suspendedusers suspendedusers) throws URISyntaxException {
        log.debug("REST request to save Suspendedusers : {}", suspendedusers);
        if (suspendedusers.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new suspendedusers cannot already have an ID")).body(null);
        }
        suspendedusers.setUser(getCurrentUserLogin());
        ZonedDateTime localDateTime = ZonedDateTime.now();
        suspendedusers.setUserdate(localDateTime);
        Suspendedusers result = suspendedusersRepository.save(suspendedusers);
        suspendedusersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/suspendedusers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /suspendedusers : Updates an existing suspendedusers.
     *
     * @param suspendedusers the suspendedusers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated suspendedusers,
     * or with status 400 (Bad Request) if the suspendedusers is not valid,
     * or with status 500 (Internal Server Error) if the suspendedusers couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/suspendedusers")
    @Timed
    public ResponseEntity<Suspendedusers> updateSuspendedusers(@RequestBody Suspendedusers suspendedusers) throws URISyntaxException {
        log.debug("REST request to update Suspendedusers : {}", suspendedusers);
        if (suspendedusers.getId() == null) {
            return createSuspendedusers(suspendedusers);
        }
        Suspendedusers result = suspendedusersRepository.save(suspendedusers);
        suspendedusersSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, suspendedusers.getId().toString()))
            .body(result);
    }

    /**
     * GET  /suspendedusers : get all the suspendedusers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of suspendedusers in body
     */
    @GetMapping("/suspendedusers")
    @Timed
    public ResponseEntity<List<Suspendedusers>> getAllSuspendedusers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Suspendedusers");
        Page<Suspendedusers> page = suspendedusersRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/suspendedusers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /suspendedusers/:id : get the "id" suspendedusers.
     *
     * @param id the id of the suspendedusers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the suspendedusers, or with status 404 (Not Found)
     */
    @GetMapping("/suspendedusers/{id}")
    @Timed
    public ResponseEntity<Suspendedusers> getSuspendedusers(@PathVariable Long id) {
        log.debug("REST request to get Suspendedusers : {}", id);
        Suspendedusers suspendedusers = suspendedusersRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(suspendedusers));
    }

    /**
     * DELETE  /suspendedusers/:id : delete the "id" suspendedusers.
     *
     * @param id the id of the suspendedusers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/suspendedusers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSuspendedusers(@PathVariable Long id) {
        log.debug("REST request to delete Suspendedusers : {}", id);
        suspendedusersRepository.delete(id);
        suspendedusersSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/suspendedusers?query=:query : search for the suspendedusers corresponding
     * to the query.
     *
     * @param query the query of the suspendedusers search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/suspendedusers")
    @Timed
    public ResponseEntity<List<Suspendedusers>> searchSuspendedusers(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Suspendedusers for query {}", query);
        Page<Suspendedusers> page = suspendedusersSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/suspendedusers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    public String getCurrentUserLogin() {
        return getUserName();


    }

}
