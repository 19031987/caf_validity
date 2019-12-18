package com.caf.valididty.web.rest;

import com.caf.valididty.domain.MobileValidation;
import com.caf.valididty.domain.Scancaf;
import com.caf.valididty.repository.MobileValidationRepository;
import com.caf.valididty.repository.search.MobileValidationSearchRepository;
import com.caf.valididty.web.rest.util.HeaderUtil;
import com.caf.valididty.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

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
    private Scancaf scancaf;

    MobileValidationResource(MobileValidationRepository mobileValidationRepository, MobileValidationSearchRepository mobileValidationSearchRepository) {
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
        MobileValidation latestByUser = mobileValidationRepository.findByuserOrderByDsc(getCurrentUserLogin());
        if (latestByUser != null && latestByUser.getMobilenumber() != null && latestByUser.getMobilenumber().equals(mobileValidation.getMobilenumber())) {
            return ResponseEntity.badRequest().headers(
                HeaderUtil.createFailureAlert(ENTITY_NAME, "mobilenumberrepeat", "mobile number is entered"))
                .body(null);
        }
        mobileValidation.setUser(getCurrentUserLogin());
        MobileValidation latestCat1 = getLatestCategory1();
        int userCount = getUserCount();
        mobileValidation.setUserCount(Integer.toString(userCount + 1));
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        mobileValidation.setUserDate(localDate);
        if (mobileValidation.getColorCode() != null
            && mobileValidation.getColorCode().trim().equalsIgnoreCase("pink")) {
            mobileValidation.setFathername("");
            mobileValidation.setActivationDate("");
            mobileValidation.setCustomerName("");
        }

        if (mobileValidation.getColorCode() != null && mobileValidation.getColorCode().trim().equalsIgnoreCase("green")) {

            if (latestCat1 != null && latestCat1.getBarcodeName() != null && !latestCat1.getBarcodeName().isEmpty()
                && latestCat1.getCategory1().equals(mobileValidation.getCategory1())) {
                String previousBarCode = latestCat1.getBarcodeName().substring(7, 11);
                //String presentBarCode = mobileValidation.getCategory1().substring(7, 8)+mobileValidation.getBarcode();
                if (Integer.parseInt(previousBarCode) + 1 == mobileValidation.getBarcode()) {
                    mobileValidation.setBarcodeName(mobileValidation.getCategory1().substring(0, 7) + String.format("%04d", mobileValidation.getBarcode()));
                } else {
                    return ResponseEntity.badRequest().headers(
                        HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "Bar code is is not in sequence"))
                        .body(null);
                }
            } else {
                if (mobileValidation.getBarcode() % 10 == 1) {
                    mobileValidation.setBarcodeName(mobileValidation.getCategory1().substring(0, 7) + String.format("%04d", mobileValidation.getBarcode()));
                } else {
                    return ResponseEntity.badRequest().headers(
                        HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "Bar code should start with 001"))
                        .body(null);
                }
            }
        }


        try {
            MobileValidation result = mobileValidationRepository.save(mobileValidation);
            // mobileValidationSearchRepository.save(result);
            return ResponseEntity.created(new URI("/api/mobile-validations/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception dive) {
            System.out.println(dive);
            return ResponseEntity.badRequest().headers(
                HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "Bar code is already entered"))
                .body(null);
        }
    }

    private MobileValidation getLatestCategory1() {
        return mobileValidationRepository.getCategory1ByUser(getCurrentUserLogin());
    }

    private int getUserCount() {
        return mobileValidationRepository.getCountOfUser(getCurrentUserLogin());
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
     * @param query    the query of the mobileValidation search
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
    public ResponseEntity<List<MobileValidation>> getMobileValidation(@RequestBody MobileValidation mobileValidation) {
        List<MobileValidation> result = mobileValidationRepository.getMobileNumber(mobileValidation.getMobilenumber());
        if (result.isEmpty()) {
            mobileValidation.setColorCode("NA");
            result = new ArrayList<MobileValidation>();
            result.add(mobileValidation);
        }
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    public String getCurrentUserLogin() {
        return getUserName();
    }

    static String getUserName() {
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

    @PostMapping("/mobile-validation/getDetailsByName")
    @Timed
    public ResponseEntity<MobileValidation> getDetailsByName(@RequestBody MobileValidation scancaf) {
        MobileValidation scancafLocal = mobileValidationRepository.findByuserOrderByDsc(scancaf.getUser());
        if (scancafLocal != null) {
            return ResponseEntity.ok().body(scancafLocal);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/mobile-validation/category1")
    @Timed
    public MobileValidation getMyCategory1() {
        String mobileValidationLocal = mobileValidationRepository.getCategory1();
        MobileValidation mobileValidation = new MobileValidation();
        mobileValidation.setCategory1(mobileValidationLocal);
        return (mobileValidation);
    }

    @PostMapping("/mobile-validation/category2")
    @Timed
    public MobileValidation getByCategory2() {
        String MobileValidationLocal = mobileValidationRepository.getCategory2();
        MobileValidation mobileValidation = new MobileValidation();
        mobileValidation.setCategory2(MobileValidationLocal);
        return (mobileValidation);
    }

    @PostMapping("/mobile-validation/category3")
    @Timed
    public MobileValidation getByCategory3() {
        String mobileValidationLocal = mobileValidationRepository.getCategory3();
        MobileValidation mobileValidation = new MobileValidation();
        mobileValidation.setCategory3(mobileValidationLocal);
        return (mobileValidation);
    }

    @PostMapping("/mobile-validation/category4")
    @Timed
    public MobileValidation getByCategory4() {
        String mobileValidationLocal = mobileValidationRepository.getCategory4();
        MobileValidation mobileValidation = new MobileValidation();
        mobileValidation.setCatergory4(mobileValidationLocal);
        return (mobileValidation);
    }

    @PostMapping("/mobile-validation/category5")
    @Timed
    public MobileValidation getByCategory5() {
        String mobileValidationLocal = mobileValidationRepository.getCategory5();
        MobileValidation mobileValidation = new MobileValidation();
        mobileValidation.setCatergory5(mobileValidationLocal);
        return (mobileValidation);
    }

    @PostMapping("/mobile-validation/categoryRv")
    @Timed
    public MobileValidation getByCategoryRv() {
        String mobileValidationLocal = mobileValidationRepository.getCategoryRv();
        MobileValidation mobileValidation = new MobileValidation();
        mobileValidation.setCategoryRv(mobileValidationLocal);
        return (mobileValidation);
    }

    @PostMapping("/mobile-validation/categoryNa")
    @Timed
    public MobileValidation getByCategoryNa() {
        String mobileValidationLocal = mobileValidationRepository.getCategoryNA();
        MobileValidation mobileValidation = new MobileValidation();
        mobileValidation.setCategoryNA(mobileValidationLocal);
        return (mobileValidation);
    }


    @PostMapping("/mobile-validation/getOutBox")
    @Timed
    private ResponseEntity<MobileValidation> getBox(@RequestBody MobileValidation id) {
        log.debug("REST request to get Mobile validation : {}", id);
        MobileValidation mobileValidation = mobileValidationRepository.findByMobileNumber(id.getMobilenumber());
        if (mobileValidation != null) {
            return ResponseEntity.ok().body(mobileValidation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
