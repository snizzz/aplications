package com.example.weatherui.core;

import com.example.weatherclient.WeatherClient;
import com.example.weatherclient.WeatherDiaryClient;
import com.example.weatherui.view.WeatherUiController;
import com.example.weatherui.viewModel.WeatherUiViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.example.weatherui.core.WeatherUiApplication.StageReadyEvent;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {


    @Value("classpath:/WeatherUiView.fxml")
    private Resource applicationResource;
    private ApplicationContext applicationContext;
    private final String applicationTitle;

    public StageInitializer(ApplicationContext applicationContext, @Value("${spring.application.ui.title}") String applicationTitle) {
        this.applicationContext = applicationContext;
        this.applicationTitle = applicationTitle;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(applicationResource.getURL());
            Parent parent = fxmlLoader.load();

            WeatherUiController ctrl = fxmlLoader.getController();
            ctrl.init(new WeatherUiViewModel(applicationContext.getBean(WeatherClient.class), applicationContext.getBean(WeatherDiaryClient.class)));

            Stage stage = event.getStage();
            stage.setScene(new Scene(parent, 600, 600));
            stage.setTitle(applicationTitle);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
