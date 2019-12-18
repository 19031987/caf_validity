package com.caf.valididty.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.caf.valididty.domain.MobileValidation;
import com.caf.valididty.repository.MobileValidationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caf.valididty.domain.Boxassign;
import com.caf.valididty.domain.Scancaf;
import com.caf.valididty.domain.Userchange;
import com.caf.valididty.repository.BoxassignRepository;
import com.caf.valididty.repository.ScancafRepository;
import com.caf.valididty.repository.UserchangeRepository;
import com.caf.valididty.repository.search.UserchangeSearchRepository;
import com.caf.valididty.web.rest.util.HeaderUtil;
import com.caf.valididty.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Userchange.
 */
@RestController
@RequestMapping("/api")
public class UserchangeResource {

	private final Logger log = LoggerFactory.getLogger(UserchangeResource.class);

	private static final String ENTITY_NAME = "userchange";

	private final UserchangeRepository userchangeRepository;

	private final ScancafRepository scancafRepository;

	private final BoxassignRepository boxassignRepository;

	private final UserchangeSearchRepository userchangeSearchRepository;
    private final MobileValidationRepository mobileValidationRepository;

	public UserchangeResource(UserchangeRepository userchangeRepository,
                              UserchangeSearchRepository userchangeSearchRepository, ScancafRepository scancafRepository,
                              BoxassignRepository boxassignRepository, MobileValidationRepository mobileValidationRepository) {
		this.userchangeRepository = userchangeRepository;
		this.userchangeSearchRepository = userchangeSearchRepository;
		this.boxassignRepository = boxassignRepository;
		this.scancafRepository = scancafRepository;
        this.mobileValidationRepository = mobileValidationRepository;
    }

	/**
	 * POST /userchanges : Create a new userchange.
	 *
	 * @param userchange
	 *            the userchange to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         userchange, or with status 400 (Bad Request) if the userchange has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/userchanges")
	@Timed
	public ResponseEntity<Userchange> createUserchange(@RequestBody Userchange userchange) throws URISyntaxException {
		log.debug("REST request to save Userchange : {}", userchange);
		if (userchange.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
					"A new userchange cannot already have an ID")).body(null);
		}
		Userchange result = userchangeRepository.save(userchange);
		userchangeSearchRepository.save(result);
		MobileValidation mobileValidation = mobileValidationRepository.findByuserOrderByDsc(userchange.getUser());
        mobileValidation.setUser(userchange.getChangeuser());
        mobileValidationRepository.save(mobileValidation);
		Boxassign boxassign = boxassignRepository.findByuser(userchange.getUser());
		boxassign.setUser(userchange.getChangeuser());
		boxassignRepository.save(boxassign);

		return ResponseEntity.created(new URI("/api/userchanges/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /userchanges : Updates an existing userchange.
	 *
	 * @param userchange
	 *            the userchange to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         userchange, or with status 400 (Bad Request) if the userchange is not
	 *         valid, or with status 500 (Internal Server Error) if the userchange
	 *         couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/userchanges")
	@Timed
	public ResponseEntity<Userchange> updateUserchange(@RequestBody Userchange userchange) throws URISyntaxException {
		log.debug("REST request to update Userchange : {}", userchange);
		if (userchange.getId() == null) {
			return createUserchange(userchange);
		}
		Userchange result = userchangeRepository.save(userchange);
		userchangeSearchRepository.save(result);

		Scancaf scancaf = scancafRepository.findByuserOrderByDsc(userchange.getUser());
		if(scancaf!=null) {
		scancaf.setUser(userchange.getChangeuser());
		scancafRepository.save(scancaf);
		}
		Boxassign boxassign = boxassignRepository.findByuser(userchange.getUser());
		if(boxassign!=null) {
		boxassign.setUser(userchange.getChangeuser());
		boxassignRepository.save(boxassign);
		}
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userchange.getId().toString())).body(result);
	}

	/**
	 * GET /userchanges : get all the userchanges.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of userchanges
	 *         in body
	 */
	@GetMapping("/userchanges")
	@Timed
	public ResponseEntity<List<Userchange>> getAllUserchanges(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of Userchanges");
		Page<Userchange> page = userchangeRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userchanges");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /userchanges/:id : get the "id" userchange.
	 *
	 * @param id
	 *            the id of the userchange to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the userchange,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/userchanges/{id}")
	@Timed
	public ResponseEntity<Userchange> getUserchange(@PathVariable Long id) {
		log.debug("REST request to get Userchange : {}", id);
		Userchange userchange = userchangeRepository.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userchange));
	}

	/**
	 * DELETE /userchanges/:id : delete the "id" userchange.
	 *
	 * @param id
	 *            the id of the userchange to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/userchanges/{id}")
	@Timed
	public ResponseEntity<Void> deleteUserchange(@PathVariable Long id) {
		log.debug("REST request to delete Userchange : {}", id);
		userchangeRepository.delete(id);
		userchangeSearchRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * SEARCH /_search/userchanges?query=:query : search for the userchange
	 * corresponding to the query.
	 *
	 * @param query
	 *            the query of the userchange search
	 * @param pageable
	 *            the pagination information
	 * @return the result of the search
	 */
	@GetMapping("/_search/userchanges")
	@Timed
	public ResponseEntity<List<Userchange>> searchUserchanges(@RequestParam String query, @ApiParam Pageable pageable) {
		log.debug("REST request to search for a page of Userchanges for query {}", query);
		Page<Userchange> page = userchangeSearchRepository.search(queryStringQuery(query), pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
				"/api/_search/userchanges");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

}
