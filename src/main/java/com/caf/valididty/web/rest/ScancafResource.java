package com.caf.valididty.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.caf.valididty.domain.Boxassign;
import com.caf.valididty.domain.Scancaf;
import com.caf.valididty.repository.BoxassignRepository;
import com.caf.valididty.repository.ScancafRepository;
import com.caf.valididty.repository.search.ScancafSearchRepository;
import com.caf.valididty.web.rest.util.HeaderUtil;
import com.caf.valididty.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Scancaf.
 */
@RestController
@RequestMapping("/api")
public class ScancafResource {

	private final Logger log = LoggerFactory.getLogger(ScancafResource.class);

	private static final String ENTITY_NAME = "scancaf";

	private final ScancafRepository scancafRepository;

	private final BoxassignRepository boxassignRepository;

	private final ScancafSearchRepository scancafSearchRepository;

	public ScancafResource(ScancafRepository scancafRepository, ScancafSearchRepository scancafSearchRepository,
			BoxassignRepository boxassignRepository) {
		this.scancafRepository = scancafRepository;
		this.scancafSearchRepository = scancafSearchRepository;
		this.boxassignRepository = boxassignRepository;
	}

	/**
	 * POST /scancafs : Create a new scancaf.
	 *
	 * @param scancaf
	 *            the scancaf to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         scancaf, or with status 400 (Bad Request) if the scancaf has already
	 *         an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/scancafs")
	@Timed
	public ResponseEntity<Scancaf> createScancaf(@RequestBody Scancaf scancaf) throws URISyntaxException {
		log.debug("REST request to save Scancaf : {}", scancaf);
		if (scancaf.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new scancaf cannot already have an ID"))
					.body(null);
		}
		scancaf.setUser(getCurrentUserLogin());
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDate localDate = localDateTime.toLocalDate();
		scancaf.userdate(localDate);
		scancaf.setBoxstatus("FIRST_LEVEL");
		Scancaf result = scancafRepository.save(scancaf);
		scancafSearchRepository.save(result);
		return ResponseEntity.created(new URI("/api/scancafs/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /scancafs : Updates an existing scancaf.
	 *
	 * @param scancaf
	 *            the scancaf to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         scancaf, or with status 400 (Bad Request) if the scancaf is not
	 *         valid, or with status 500 (Internal Server Error) if the scancaf
	 *         couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/scancafs")
	@Timed
	public ResponseEntity<Scancaf> updateScancaf(@RequestBody Scancaf scancaf) throws URISyntaxException {
		log.debug("REST request to update Scancaf : {}", scancaf);
		if (scancaf.getId() == null) {
			return createScancaf(scancaf);
		}
		scancaf.setSecaudit(getCurrentUserLogin());
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDate localDate = localDateTime.toLocalDate();
		scancaf.setSecauditdate(localDate);
		Scancaf result = scancafRepository.save(scancaf);
		scancafSearchRepository.save(result);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scancaf.getId().toString()))
				.body(result);
	}

	/**
	 * GET /scancafs : get all the scancafs.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of scancafs in
	 *         body
	 */
	@GetMapping("/scancafs")
	@Timed
	public ResponseEntity<List<Scancaf>> getAllScancafs(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of Scancafs");
		Page<Scancaf> page = scancafRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/scancafs");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /scancafs/:id : get the "id" scancaf.
	 *
	 * @param id
	 *            the id of the scancaf to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the scancaf, or
	 *         with status 404 (Not Found)
	 */
	@GetMapping("/scancafs/{id}")
	@Timed
	public ResponseEntity<Scancaf> getScancaf(@PathVariable Long id) {
		log.debug("REST request to get Scancaf : {}", id);
		Scancaf scancaf = scancafRepository.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(scancaf));
	}

	/**
	 * DELETE /scancafs/:id : delete the "id" scancaf.
	 *
	 * @param id
	 *            the id of the scancaf to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/scancafs/{id}")
	@Timed
	public ResponseEntity<Void> deleteScancaf(@PathVariable Long id) {
		log.debug("REST request to delete Scancaf : {}", id);
		scancafRepository.delete(id);
		scancafSearchRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * SEARCH /_search/scancafs?query=:query : search for the scancaf corresponding
	 * to the query.
	 *
	 * @param query
	 *            the query of the scancaf search
	 * @param pageable
	 *            the pagination information
	 * @return the result of the search
	 */
	@GetMapping("/_search/scancafs")
	@Timed
	public ResponseEntity<List<Scancaf>> searchScancafs(@RequestParam String query, @ApiParam Pageable pageable) {
		log.debug("REST request to search for a page of Scancafs for query {}", query);
		Page<Scancaf> page = scancafSearchRepository.search(queryStringQuery(query), pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/scancafs");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /scancafs/:id : get the "id" scancaf.
	 *
	 * @param id
	 *            the id of the scancaf to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the scancaf, or
	 *         with status 404 (Not Found)
	 */
	@PostMapping("/scancafs/getCaf")
	@Timed
	public Scancaf getCaf(@RequestBody Scancaf id) {
		log.debug("REST request to get Scancaf : {}", id);
		Object scancaf = scancafRepository.getCaf(id.getCafbarcode());
		Object[] getdata = (Object[]) scancaf;
		Scancaf scan = new Scancaf();
		if (getdata != null) {
			scan.setCaftype((String) getdata[2]);
			scan.setCafbarcode((String) getdata[1]);
			BigInteger mobno = new BigInteger(getdata[0].toString());
			scan.setMobilenumber(mobno.longValue());
		}
		return scan;
	}

	@PostMapping("/scancafs/getBoxCount")
	@Timed
	public Scancaf getBoxCount(@RequestBody Scancaf id) {
		log.debug("REST request to get Scancaf : {}", id);
		Scancaf scancaf = scancafRepository.findByCategory(id.getSourcebox(), getCurrentUserLogin());

		return scancaf;
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

	@PostMapping("/scancafs/getCatLatest")
	@Timed
	public Boxassign getCatLatest(@RequestBody Boxassign id) {
		log.debug("REST request to get Scancaf : {}", id);
		Boxassign assign = new Boxassign();
		Scancaf scancaf = new Scancaf();
		if (id.getChurntype() == null) {
			scancaf = scancafRepository.getCatALl();
			if (scancaf == null) {
				scancaf = new Scancaf();
				scancaf.setCategory("E1A00000");
				scancaf.setCategory1("EC100000");
				scancaf.setCategory2("EC200000");
				scancaf.setCategory3("EC300000");
				scancaf.setCategory4("EC400000");
				scancaf.setCategory5("EDA00000");
			}

			id.setChurntype("Any");
		} else {
			scancaf = scancafRepository.getCatLatest(id.getChurntype());
		}

		if (id.getChurntype().equalsIgnoreCase("category_1")) {
			assign.setBoxassign(scancaf.getCategory1());
		}
		if (id.getChurntype().equalsIgnoreCase("category_2")) {
			assign.setBoxassign((scancaf.getCategory2()));
		}

		if (id.getChurntype().equalsIgnoreCase("category_3")) {
			assign.setBoxassign((scancaf.getCategory3()));
		}

		if (id.getChurntype().equalsIgnoreCase("category_4")) {
			assign.setBoxassign((scancaf.getCategory4()));
		}

		if (id.getChurntype().equalsIgnoreCase("category_5")) {
			assign.setBoxassign((scancaf.getCategory5()));
		}
		if (id.getChurntype().equalsIgnoreCase("Any")) {
			assign.setBoxassign((scancaf.getCategory() + "," + scancaf.getCategory1() + "," + scancaf.getCategory2()
					+ "," + scancaf.getCategory3() + "," + scancaf.getCategory4() + "," + scancaf.getCategory5()));
		}
		return assign;

	}

	@PostMapping("/scancafs/getBox")
	@Timed
	public ResponseEntity<Scancaf> getBox(@RequestBody Scancaf id) {
		log.debug("REST request to get Scancaf : {}", id);
		Scancaf scancaf = scancafRepository.findByBarcode(id.getBoxstatus());
		if (scancaf != null) {
			scancaf.setCafbarcode("");
			return ResponseEntity.ok().body(scancaf);
		} else {
			Boxassign box = new Boxassign();
			box.boxassign(id.getSourceboxstaus());
			box.setUser(id.getUser());
			box.setBoxassign(id.getBoxstatus());
			boxassignRepository.save(box);
			return ResponseEntity.badRequest()
					.headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "Caf is Not segregated"))
					.body(null);
		}

	}

	@PostMapping("/scancafs/getOutBox")
	@Timed
	public ResponseEntity<Scancaf> getOutBox(@RequestBody Scancaf id) {
		log.debug("REST request to get Scancaf : {}", id);
		String cafs = "";
		Scancaf cafloc = new Scancaf();
		List<Scancaf> scancaf = new ArrayList<Scancaf>();
		if (id.getCaftype().equalsIgnoreCase("category_1")) {
			scancaf = scancafRepository.findByOutBoxCompletionCat5(id.getSourceboxstaus(), "green");
		}
		if (id.getCaftype().equalsIgnoreCase("category_2")) {
			scancaf = scancafRepository.findByOutBoxCompletionCat1(id.getSourceboxstaus(), "white");
		}
		if (id.getCaftype().equalsIgnoreCase("category_3")) {
			scancaf = scancafRepository.findByOutBoxCompletionCat2(id.getSourceboxstaus(), "yellow");
		}
		if (id.getCaftype().equalsIgnoreCase("category_4")) {
			scancaf = scancafRepository.findByOutBoxCompletionCat3(id.getSourceboxstaus(), "blue");
		}
		if (id.getCaftype().equalsIgnoreCase("category_5")) {
			scancaf = scancafRepository.findByOutBoxCompletionCat4(id.getSourceboxstaus(), "red");
		}
		for (Scancaf caf : scancaf) {
			if (id.getCaftype().equalsIgnoreCase("category_1")) {
				cafs = cafs + " ,    " + caf.getCentralbarcode();
			}
			if (id.getCaftype().equalsIgnoreCase("category_2")) {
				cafs = cafs + "  ,   " + caf.getCentralbarcode();
			}
			if (id.getCaftype().equalsIgnoreCase("category_3")) {
				cafs = cafs + "  ,   " + caf.getCentralbarcode();
			}
			if (id.getCaftype().equalsIgnoreCase("category_4")) {
				cafs = cafs + "  ,   " + caf.getCentralbarcode();
			}
			if (id.getCaftype().equalsIgnoreCase("category_5")) {
				cafs = cafs + "  ,  " + caf.getCentralbarcode();
			}
		}

		if ("".equalsIgnoreCase(cafs)) {

			return ResponseEntity.badRequest()
					.headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "Caf is Not segregated"))
					.body(null);
		} else {
			cafloc.setBoxstatus(cafs);
			return ResponseEntity.ok().body(cafloc);
		}

	}

	@PostMapping("/scancafs/getDetailsByName")
	@Timed
	public ResponseEntity<Scancaf> getDetailsByName(@RequestBody Scancaf scancaf) {
		Scancaf scancafLocal = scancafRepository.findByuserOrderByDsc(scancaf.getUser());
		if (scancafLocal != null) {
			return ResponseEntity.ok().body(scancafLocal);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/scancafs/category1")
	@Timed
	public Scancaf getByCategory1() {
		String scancafLocal = scancafRepository.getCategory1();
		Scancaf scancaf = new Scancaf();
            scancaf.setCategory1(scancafLocal);
		return  (scancaf);
	}

	@PostMapping("/scancafs/category2")
	@Timed
	public Scancaf getByCategory2() {
		String scancafLocal = scancafRepository.getCategory2();
        Scancaf scancaf = new Scancaf();
        scancaf.setCategory2(scancafLocal);
		return  (scancaf);
	}

	@PostMapping("/scancafs/category3")
	@Timed
	public Scancaf getByCategory3() {
		String scancafLocal = scancafRepository.getCategory3();
        Scancaf scancaf = new Scancaf();
        scancaf.setCategory3(scancafLocal);
		return  (scancaf);
	}

	@PostMapping("/scancafs/category4")
	@Timed
	public Scancaf getByCategory4() {
		String scancafLocal = scancafRepository.getCategory4();
        Scancaf scancaf = new Scancaf();
        scancaf.setCategory4(scancafLocal);
        return  (scancaf);
	}

	@PostMapping("/scancafs/category5")
	@Timed
	public Scancaf getByCategory5() {
		String scancafLocal = scancafRepository.getCategory5();
        Scancaf scancaf = new Scancaf();
        scancaf.setCategory5(scancafLocal);
        return  (scancaf);
	}

}
