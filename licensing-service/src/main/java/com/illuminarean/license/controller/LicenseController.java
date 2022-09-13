package com.illuminarean.license.controller;

import com.illuminarean.license.model.License;
import com.illuminarean.license.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value="v1/organization/{organizationId}/license")
public class LicenseController {

	@Autowired
	private LicenseService licenseService;

	@RequestMapping(value="/{licenseId}",method = RequestMethod.GET)
	public ResponseEntity<License> getLicense(@PathVariable("organizationId") String organizationId,
											  @PathVariable("licenseId") String licenseId) {

		License license = licenseService.getLicense(licenseId, organizationId, "");
		license.add( 
				linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
				linkTo(methodOn(LicenseController.class).createLicense(license)).withRel("createLicense"),
				linkTo(methodOn(LicenseController.class).updateLicense(license)).withRel("updateLicense"),
				linkTo(methodOn(LicenseController.class).deleteLicense(license.getLicenseId())).withRel("deleteLicense")
				);

		return ResponseEntity.ok(license);
	}

	@RequestMapping(value="/{licenseId}/{clientType}",method = RequestMethod.GET)
	public License getLicensesWithClient( @PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId,
			@PathVariable("clientType") String clientType) {

		return licenseService.getLicense(licenseId, organizationId, clientType);
	}

	@PutMapping
	public ResponseEntity<License> updateLicense(@RequestBody License request) {
		return ResponseEntity.ok(licenseService.updateLicense(request));
	}

	@PostMapping
	public ResponseEntity<License> createLicense(@RequestBody License request) {
		return ResponseEntity.ok(licenseService.createLicense(request));
	}

	@DeleteMapping(value="/{licenseId}")
	public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") String licenseId) {
		return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
	}

	@RequestMapping(value="/",method = RequestMethod.GET)
	public List<License> getLicenses( @PathVariable("organizationId") String organizationId) {
		return licenseService.getLicensesByOrganization(organizationId);
	}

}
