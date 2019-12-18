package com.caf.valididty.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caf.valididty.domain.Adminstats;

import com.caf.valididty.repository.AdminstatsRepository;
import com.caf.valididty.repository.search.AdminstatsSearchRepository;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Adminstats.
 */
@RestController
@RequestMapping("/api")
public class AdminstatsResource {

    private final Logger log = LoggerFactory.getLogger(AdminstatsResource.class);

    private static final String ENTITY_NAME = "adminstats";

    private final AdminstatsRepository adminstatsRepository;

    private final AdminstatsSearchRepository adminstatsSearchRepository;
    public AdminstatsResource(AdminstatsRepository adminstatsRepository, AdminstatsSearchRepository adminstatsSearchRepository) {
        this.adminstatsRepository = adminstatsRepository;
        this.adminstatsSearchRepository = adminstatsSearchRepository;
    }

    /**
     * POST  /adminstats : Create a new adminstats.
     *
     * @param adminstats the adminstats to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adminstats, or with status 400 (Bad Request) if the adminstats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adminstats")
    @Timed
    public ResponseEntity<Adminstats> createAdminstats(@RequestBody Adminstats adminstats) throws URISyntaxException {
        log.debug("REST request to save Adminstats : {}", adminstats);
        if (adminstats.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new adminstats cannot already have an ID")).body(null);
        }
        Adminstats result = adminstatsRepository.save(adminstats);
        adminstatsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/adminstats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adminstats : Updates an existing adminstats.
     *
     * @param adminstats the adminstats to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adminstats,
     * or with status 400 (Bad Request) if the adminstats is not valid,
     * or with status 500 (Internal Server Error) if the adminstats couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adminstats")
    @Timed
    public ResponseEntity<Adminstats> updateAdminstats(@RequestBody Adminstats adminstats) throws URISyntaxException {
        log.debug("REST request to update Adminstats : {}", adminstats);
        if (adminstats.getId() == null) {
            return createAdminstats(adminstats);
        }
        Adminstats result = adminstatsRepository.save(adminstats);
        adminstatsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adminstats.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adminstats : get all the adminstats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of adminstats in body
     */
    @GetMapping("/adminstats")
    @Timed
    public ResponseEntity<List<Adminstats>> getAllAdminstats(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Adminstats");
        Page<Adminstats> page = adminstatsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/adminstats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /adminstats/:id : get the "id" adminstats.
     *
     * @param id the id of the adminstats to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adminstats, or with status 404 (Not Found)
     */
    @GetMapping("/adminstats/{id}")
    @Timed
    public ResponseEntity<Adminstats> getAdminstats(@PathVariable Long id) {
        log.debug("REST request to get Adminstats : {}", id);
        Adminstats adminstats = adminstatsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adminstats));
    }

    /**
     * DELETE  /adminstats/:id : delete the "id" adminstats.
     *
     * @param id the id of the adminstats to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adminstats/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdminstats(@PathVariable Long id) {
        log.debug("REST request to delete Adminstats : {}", id);
        adminstatsRepository.delete(id);
        adminstatsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/adminstats?query=:query : search for the adminstats corresponding
     * to the query.
     *
     * @param query the query of the adminstats search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/adminstats")
    @Timed
    public ResponseEntity<List<Adminstats>> searchAdminstats(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Adminstats for query {}", query);
        Page<Adminstats> page = adminstatsSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/adminstats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
