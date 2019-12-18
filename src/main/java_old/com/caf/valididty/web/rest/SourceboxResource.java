package com.caf.valididty.web.rest;

import static com.caf.valididty.web.rest.MobileValidationResource.getUserName;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caf.valididty.domain.Sourcebox;
import com.caf.valididty.repository.SourceboxRepository;
import com.caf.valididty.repository.search.SourceboxSearchRepository;
import com.caf.valididty.web.rest.util.HeaderUtil;
import com.caf.valididty.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Sourcebox.
 */
@RestController
@RequestMapping("/api")
public class SourceboxResource {

    private final Logger log = LoggerFactory.getLogger(SourceboxResource.class);

    private static final String ENTITY_NAME = "sourcebox";

    private final SourceboxRepository sourceboxRepository;

    private final SourceboxSearchRepository sourceboxSearchRepository;
    public SourceboxResource(SourceboxRepository sourceboxRepository, SourceboxSearchRepository sourceboxSearchRepository) {
        this.sourceboxRepository = sourceboxRepository;
        this.sourceboxSearchRepository = sourceboxSearchRepository;
    }

    /**
     * POST  /sourceboxes : Create a new sourcebox.
     *
     * @param sourcebox the sourcebox to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sourcebox, or with status 400 (Bad Request) if the sourcebox has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sourceboxes")
    @Timed
    public ResponseEntity<Sourcebox> createSourcebox(@RequestBody Sourcebox sourcebox) throws URISyntaxException {
        log.debug("REST request to save Sourcebox : {}", sourcebox);
        if (sourcebox.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sourcebox cannot already have an ID")).body(null);
        }
        sourcebox.setCreateduser(getCurrentUserLogin());
        //sourcebox.setCreateddate(LocalDate.now() );
        Sourcebox result = sourceboxRepository.save(sourcebox);
        sourceboxSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/sourceboxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sourceboxes : Updates an existing sourcebox.
     *
     * @param sourcebox the sourcebox to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sourcebox,
     * or with status 400 (Bad Request) if the sourcebox is not valid,
     * or with status 500 (Internal Server Error) if the sourcebox couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sourceboxes")
    @Timed
    public ResponseEntity<Sourcebox> updateSourcebox(@RequestBody Sourcebox sourcebox) throws URISyntaxException {
        log.debug("REST request to update Sourcebox : {}", sourcebox);
        if (sourcebox.getId() == null) {
            return createSourcebox(sourcebox);
        }
        Sourcebox result = sourceboxRepository.save(sourcebox);
        sourceboxSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sourcebox.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sourceboxes : get all the sourceboxes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sourceboxes in body
     */
    @GetMapping("/sourceboxes")
    @Timed
    public ResponseEntity<List<Sourcebox>> getAllSourceboxes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Sourceboxes");
        Page<Sourcebox> page = sourceboxRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sourceboxes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sourceboxes/:id : get the "id" sourcebox.
     *
     * @param id the id of the sourcebox to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sourcebox, or with status 404 (Not Found)
     */
    @GetMapping("/sourceboxes/{id}")
    @Timed
    public ResponseEntity<Sourcebox> getSourcebox(@PathVariable Long id) {
        log.debug("REST request to get Sourcebox : {}", id);
        Sourcebox sourcebox = sourceboxRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sourcebox));
    }

    /**
     * DELETE  /sourceboxes/:id : delete the "id" sourcebox.
     *
     * @param id the id of the sourcebox to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sourceboxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteSourcebox(@PathVariable Long id) {
        log.debug("REST request to delete Sourcebox : {}", id);
        sourceboxRepository.delete(id);
        sourceboxSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sourceboxes?query=:query : search for the sourcebox corresponding
     * to the query.
     *
     * @param query the query of the sourcebox search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sourceboxes")
    @Timed
    public ResponseEntity<List<Sourcebox>> searchSourceboxes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Sourceboxes for query {}", query);
        Page<Sourcebox> page = sourceboxSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sourceboxes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /sourceboxes/:id : get the "id" sourcebox.
     *
     * @param id the id of the sourcebox to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sourcebox, or with status 404 (Not Found)
     */
    @PostMapping("/sourceboxes/getSource")
    @Timed
    public ResponseEntity<Sourcebox> getSource(@RequestBody  String id) {
        log.debug("REST request to get Sourcebox : {}", id);
        Sourcebox sourcebox = sourceboxRepository.findBySourceboxname(id);
        if (sourcebox == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "This box is not there in the database, please check")).body(null);
        }else {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sourcebox));
        }
    }

    public String getCurrentUserLogin() {
        return getUserName();


    }

}
