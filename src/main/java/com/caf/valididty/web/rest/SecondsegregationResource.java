package com.caf.valididty.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caf.valididty.domain.Scancaf;
import com.caf.valididty.domain.Secondsegregation;

import com.caf.valididty.repository.SecondsegregationRepository;
import com.caf.valididty.repository.search.SecondsegregationSearchRepository;
import com.caf.valididty.web.rest.util.HeaderUtil;
import com.caf.valididty.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.caf.valididty.web.rest.MobileValidationResource.getUserName;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Secondsegregation.
 */
@RestController
@RequestMapping("/api")
public class SecondsegregationResource {

    private final Logger log = LoggerFactory.getLogger(SecondsegregationResource.class);

    private static final String ENTITY_NAME = "secondsegregation";

    private final SecondsegregationRepository secondsegregationRepository;

    private final SecondsegregationSearchRepository secondsegregationSearchRepository;
    public SecondsegregationResource(SecondsegregationRepository secondsegregationRepository, SecondsegregationSearchRepository secondsegregationSearchRepository) {
        this.secondsegregationRepository = secondsegregationRepository;
        this.secondsegregationSearchRepository = secondsegregationSearchRepository;
    }

    /**
     * POST  /secondsegregations : Create a new secondsegregation.
     *
     * @param secondsegregation the secondsegregation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new secondsegregation, or with status 400 (Bad Request) if the secondsegregation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/secondsegregations")
    @Timed
    public ResponseEntity<Secondsegregation> createSecondsegregation(@RequestBody Secondsegregation secondsegregation) throws URISyntaxException {
        log.debug("REST request to save Secondsegregation : {}", secondsegregation);
        secondsegregation.setId(null);
        if (secondsegregation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new secondsegregation cannot already have an ID")).body(null);
        }
        try {
            secondsegregation.setUser(getCurrentUserLogin());
            if(secondsegregation.getStatus()!=null&&secondsegregation.getStatus().equalsIgnoreCase("WRONG")) {
                secondsegregation.setCafcode(secondsegregation.getCafcode()+"_WRONG");
            }
            if(secondsegregation.getStatus()!=null&&secondsegregation.getStatus().equalsIgnoreCase("NA")) {
                secondsegregation.setCafcode(secondsegregation.getCafcode()+"_NA");
            }
            secondsegregation.setUserdate(Instant.now());
            Secondsegregation result = secondsegregationRepository.save(secondsegregation);
            secondsegregationSearchRepository.save(result);
            return ResponseEntity.created(new URI("/api/secondsegregations/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }catch(DataIntegrityViolationException dive) {
            return ResponseEntity.badRequest().headers(
                HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "You have already entered "))
                .body(null);
        }
    }

    /**
     * PUT  /secondsegregations : Updates an existing secondsegregation.
     *
     * @param secondsegregation the secondsegregation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated secondsegregation,
     * or with status 400 (Bad Request) if the secondsegregation is not valid,
     * or with status 500 (Internal Server Error) if the secondsegregation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/secondsegregations")
    @Timed
    public ResponseEntity<Secondsegregation> updateSecondsegregation(@RequestBody Secondsegregation secondsegregation) throws URISyntaxException {
        log.debug("REST request to update Secondsegregation : {}", secondsegregation);
        if (secondsegregation.getId() == null) {
            secondsegregation.setUser(getCurrentUserLogin());
            return createSecondsegregation(secondsegregation);
        }
        Secondsegregation result = secondsegregationRepository.save(secondsegregation);
        secondsegregationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, secondsegregation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /secondsegregations : get all the secondsegregations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of secondsegregations in body
     */
    @GetMapping("/secondsegregations")
    @Timed
    public ResponseEntity<List<Secondsegregation>> getAllSecondsegregations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Secondsegregations");
        Page<Secondsegregation> page = secondsegregationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/secondsegregations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /secondsegregations/:id : get the "id" secondsegregation.
     *
     * @param id the id of the secondsegregation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the secondsegregation, or with status 404 (Not Found)
     */
    @GetMapping("/secondsegregations/{id}")
    @Timed
    public ResponseEntity<Secondsegregation> getSecondsegregation(@PathVariable Long id) {
        log.debug("REST request to get Secondsegregation : {}", id);
        Secondsegregation secondsegregation = secondsegregationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(secondsegregation));
    }

    /**
     * DELETE  /secondsegregations/:id : delete the "id" secondsegregation.
     *
     * @param id the id of the secondsegregation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/secondsegregations/{id}")
    @Timed
    public ResponseEntity<Void> deleteSecondsegregation(@PathVariable Long id) {
        log.debug("REST request to delete Secondsegregation : {}", id);
        secondsegregationRepository.delete(id);
        secondsegregationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/secondsegregations?query=:query : search for the secondsegregation corresponding
     * to the query.
     *
     * @param query the query of the secondsegregation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/secondsegregations")
    @Timed
    public ResponseEntity<List<Secondsegregation>> searchSecondsegregations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Secondsegregations for query {}", query);
        Page<Secondsegregation> page = secondsegregationSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/secondsegregations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/secondsegregations/getOutbox")
    @Timed
    public ResponseEntity<Secondsegregation> getOutbox(@RequestBody Secondsegregation secondsegregation) {
        Secondsegregation seg =  secondsegregationRepository.findTop1ByboxnameOrderByIdDesc(secondsegregation.getBoxname());
        if(seg!= null) {
            return ResponseEntity.ok().body(seg);
        }else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"BoxNameNotCorrect",String.format("Please enter Correct Box Name", secondsegregation.getBoxname()))).body(secondsegregation);
        }

    }

    @PostMapping("/secondsegregations/getBox")
    @Timed
    public ResponseEntity<Secondsegregation> getbox(@RequestBody Secondsegregation secondsegregation) {
        log.debug("Request to search application {}", secondsegregation);
        Scancaf scancaf = secondsegregationRepository.findByBoxName(secondsegregation.getBoxname(), secondsegregation.getCafcode());
        if(scancaf!= null) {
            log.debug("Scan caf **************** {}", scancaf);
            Secondsegregation secondSegregation = secondsegregationRepository.findBycafcode(secondsegregation.getCafcode());
            if(secondSegregation == null) {
                if(scancaf.getCategory1().equalsIgnoreCase(secondsegregation.getBoxname())&& secondsegregation.getBoxname().contains("E1A")) {
                    secondsegregation.setSegregatedcount(secondsegregation.getSegregatedcount()+1);
                }else if(scancaf.getCategory2().equalsIgnoreCase(secondsegregation.getBoxname())&& secondsegregation.getBoxname().contains("EC1")) {
                    secondsegregation.setSegregatedcount(secondsegregation.getSegregatedcount()+1);
                }
                else if(scancaf.getCategory3().equalsIgnoreCase(secondsegregation.getBoxname())&& secondsegregation.getBoxname().contains("EC2")) {
                    secondsegregation.setSegregatedcount(secondsegregation.getSegregatedcount()+1);
                }
                else if(scancaf.getCategory4().equalsIgnoreCase(secondsegregation.getBoxname())&& secondsegregation.getBoxname().contains("EC3")) {
                    secondsegregation.setSegregatedcount(secondsegregation.getSegregatedcount()+1);
                }
                else if(scancaf.getCategory5().equalsIgnoreCase(secondsegregation.getBoxname())&& secondsegregation.getBoxname().contains("EDA")) {

                    secondsegregation.setSegregatedcount(secondsegregation.getSegregatedcount()+1);
                }
                else if(scancaf.getCategoryRv().equalsIgnoreCase(secondsegregation.getBoxname())&& secondsegregation.getBoxname().contains("ERV")) {
                    secondsegregation.setSegregatedcount(secondsegregation.getSegregatedcount()+1);
                }
                else {
                    secondsegregation.setWrongsegregatedcount(secondsegregation.getWrongsegregatedcount()+1);
                    secondsegregation.setStatus("WRONG");
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"BoxNameNotCorrect",String.format("Wrongly Enter Caf  %s – Count & Caf not from these box – Count : %s",
                        secondsegregation.getBoxname(),secondsegregation.getWrongsegregatedcount()))).body(secondsegregation);

                }
                secondsegregation.setFirstleveluser(scancaf.getUser());
                secondsegregation.setColorcode(scancaf.getColorcode());
                secondsegregation.setLastcafentered(secondsegregation.getCafcode());
            }
            else {
                secondsegregation.setNotsegregatedcount(secondsegregation.getNotsegregatedcount()+1);
                secondsegregation.setStatus("NA");
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"CafBarCodeNotFound",String.format("Data not found for the caf bar code", secondsegregation.getCafcode()))).body(secondsegregation);
            }
        }else {
            secondsegregation.setNotsegregatedcount(secondsegregation.getNotsegregatedcount()+1);
            secondsegregation.setStatus("NA");
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"CafBarCodeNotFound",String.format("Data not found for the caf bar code", secondsegregation.getCafcode()))).body(secondsegregation);
        }


        return ResponseEntity.ok(secondsegregation);
    }
    public String getCurrentUserLogin() {
        return getUserName();

    }

    @PostMapping("/secondsegregations/getLatestBox")
    @Timed
    public ResponseEntity<Secondsegregation> getLatestBox(@RequestBody Secondsegregation secondsegregation) {
        log.debug("Request to search application {}", secondsegregation);
        return ResponseEntity.ok().body(secondsegregationRepository.findTop1ByuserOrderByIdDesc(getCurrentUserLogin()));
    }
}
