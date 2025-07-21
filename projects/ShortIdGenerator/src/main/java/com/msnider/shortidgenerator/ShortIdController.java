package com.msnider.shortidgenerator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msnider.shortidgenerator.entity.Available;
import com.msnider.shortidgenerator.entity.IdRequest;
import com.msnider.shortidgenerator.entity.IdsResponse;
import com.msnider.shortidgenerator.entity.Unavailable;
import com.msnider.shortidgenerator.service.AvailableService;
import com.msnider.shortidgenerator.service.UnavailableService;


@RestController
@RequestMapping("/ids/v1")
public class ShortIdController {

	@Autowired
	private UnavailableService unavailableService;

	@Autowired
	private AvailableService availableRepository;

	@GetMapping
	public ResponseEntity<IdsResponse> getIds() {
		return ResponseEntity.ok(new IdsResponse(
      this.unavailableService.findAll(),
      this.availableRepository.findAll()));
	}
	
	@PostMapping
	public ResponseEntity<Unavailable> postId(@RequestBody IdRequest request) {
		// when not available
		Optional<Available> availableResponse = this.availableRepository.findAndRemoveById(request.getId());
		if (availableResponse.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		// store the value for unavailable
		Available available = availableResponse.get();
		Unavailable unavailable = this.unavailableService.save(new Unavailable(
			available.getId(),
			request.getText(),
			available.getGeneratedAt(),
			System.currentTimeMillis()));
		return ResponseEntity.ok(unavailable);
	}
	
}
