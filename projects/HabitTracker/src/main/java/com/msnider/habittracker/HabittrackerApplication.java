package com.msnider.habittracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

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
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
	private static final HabitList habitList = new HabitList(formatter);
	
	public static void main(String[] args) {
    HabittrackerApplication.habitList.readHabits(filename);
		SpringApplication.run(HabittrackerApplication.class, args);
	}

	// todo: update json schema to include habits field with list of habits
	// todo: additional summary api
	@GetMapping
	public List<Habit> getHabits() {
		return new ArrayList<>(HabittrackerApplication.habitList.getHabits());
	}

	// todo: update schema to support post
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Habit> postHabit(@RequestBody Habit habit) {
		String name = habit.getName();
		LocalDateTime dt = habit.getDates().getFirst();
		Habit updatedHabit = HabittrackerApplication.habitList.addHabit(new AbstractMap.SimpleEntry<>(name, dt));

		HabittrackerApplication.habitList.writeHabits(filename);

		HttpStatusCode statusCode = habit.getDates().size() == 1 ? HttpStatus.CREATED : HttpStatus.OK;
		return new ResponseEntity<>(updatedHabit, statusCode);
	}
}
