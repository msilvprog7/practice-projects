package com.msnider.shortidgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msnider.shortidgenerator.entity.IdsResponse;
import com.msnider.shortidgenerator.repository.AvailableRepository;
import com.msnider.shortidgenerator.repository.UnavailableRepository;

@RestController
@RequestMapping("/ids/v1")
public class ShortIdController {

	@Autowired
	private UnavailableRepository unavailableRepository;

	@Autowired
	private AvailableRepository availableRepository;

	@GetMapping
	public ResponseEntity<IdsResponse> getIds() {
		return ResponseEntity.ok(new IdsResponse(
      this.unavailableRepository.findAll(),
      this.availableRepository.findAll()));
	}
	
}
