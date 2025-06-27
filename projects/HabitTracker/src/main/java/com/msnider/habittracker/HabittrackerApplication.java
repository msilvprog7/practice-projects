package com.msnider.habittracker;

import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api/habits")
public class HabittrackerApplication {
  private static final String filename = "habits.txt";
	private static final HabitList habitList = new HabitList();
	
	public static void main(String[] args) {
    HabittrackerApplication.habitList.readHabits(filename);
		SpringApplication.run(HabittrackerApplication.class, args);
	}

	@GetMapping
	public HabitList getHabits() {
		return HabittrackerApplication.habitList;
	}

	@GetMapping("/summary")
	public HabitSummaries getSummary() {
		// Sort by most recent desc, a future improvement could add sort options in the request
		return new HabitSummaries(HabittrackerApplication
			.habitList
			.getHabits()
			.stream()
			.map((habit) -> new HabitSummary(habit))
			.sorted((a, b) -> -a.getMostRecent().compareTo(b.getMostRecent()))
			.collect(Collectors.toList()));
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Habit> postHabit(@RequestBody HabitEntry habit) {
		Habit updatedHabit = HabittrackerApplication.habitList.addHabit(habit);

		HabittrackerApplication.habitList.writeHabits(filename);

		HttpStatusCode statusCode = updatedHabit.getDateTimes().size() == 1 ? HttpStatus.CREATED : HttpStatus.OK;
		return new ResponseEntity<>(updatedHabit, statusCode);
	}
}
