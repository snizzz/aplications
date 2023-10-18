package com.example.weatherapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class WeatherAppApplication implements CommandLineRunner {
	public static void main(String[] args) {
		new SpringApplicationBuilder(WeatherAppApplication.class).headless(false).run(args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	@Override
	public void run(String... args) {
		JFrame frame = new JFrame("Spring Boot Swing App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		JPanel panel = new JPanel(new BorderLayout());
		JTextField text = new JTextField("Spring Boot can be used with Swing apps");
		panel.add(text, BorderLayout.CENTER);
		frame.setContentPane(panel);
		frame.setVisible(true);
	}

}
