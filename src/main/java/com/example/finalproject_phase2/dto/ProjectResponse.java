package com.example.finalproject_phase2.dto;

import com.example.finalproject_phase2.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectResponse {
    String code;
    String message;

    public static ResponseEntity<ProjectResponse> getResponseEntity(ProjectResponse object){
        ProjectResponse projectResponse = object;
        if (projectResponse.getCode().equals("202")) {
            return new ResponseEntity<>(projectResponse, HttpStatus.ACCEPTED);
        }
        if (projectResponse.getCode().equals("500")) {
            return new ResponseEntity<>(projectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(projectResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
