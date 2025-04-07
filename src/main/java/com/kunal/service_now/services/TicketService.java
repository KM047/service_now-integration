package com.kunal.service_now.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kunal.service_now.dto.TicketDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
@Slf4j
public class TicketService {

    @Value("${service.now.instance}")
    private String servicenowUrl;

    private RestTemplate restTemplate;

    public TicketService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createHeaders(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


    public ResponseEntity<?> createTicket(
            TicketDTO newTicket
    ) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            if (
                    newTicket.getShort_description() == null || newTicket.getShort_description().isEmpty() ||
                            newTicket.getDescription() == null || newTicket.getDescription().isEmpty() ||
                            newTicket.getState() == null || newTicket.getState().isEmpty() ||
                            newTicket.getPriority() == null || newTicket.getPriority().isEmpty() ||
                            newTicket.getU_type() == null
            ) {
                return ResponseEntity.badRequest().build();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(newTicket);
            HttpEntity<String> entity = new HttpEntity<>(jsonPayload, createHeaders(username, password));
            return restTemplate.exchange(servicenowUrl + "/api/now/table/ticket", HttpMethod.POST, entity, String.class);
        } catch (JsonProcessingException e) {
            log.error("Error creating ticket TicketService::createTicket => {}", e.getMessage());
            throw new RuntimeException("Error creating ticket TicketService::createTicket => ", e);
        }
    }


    public ResponseEntity<?> getAllTickets(String ticketType) {
        // Logic to get a ticket by ID



        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            HttpEntity<String> entity = new HttpEntity<>(createHeaders(username, password));

            if (ticketType != null && !ticketType.isEmpty()) {
                return restTemplate.exchange(servicenowUrl + "/api/now/table/ticket?u_type=" + ticketType, HttpMethod.GET, entity, String.class);
            } else {
                return restTemplate.exchange(servicenowUrl + "/api/now/table/ticket", HttpMethod.GET, entity, String.class);
            }

        } catch (RestClientException e) {
            log.error("Error creating ticket TicketService::getTicketById => {}", e.getMessage());
            throw new RuntimeException("Error creating ticket TicketService::getTicketById => ", e);
        }
    }

    public ResponseEntity<?> getTicketById(String ticketID) {
        // Logic to get a ticket by ID

        ResponseEntity<String> responseEntity = null;

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            HttpEntity<String> entity = new HttpEntity<>(createHeaders(username, password));
            responseEntity = restTemplate.exchange(servicenowUrl + "/api/now/table/ticket/" + ticketID, HttpMethod.GET, entity, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity;
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body("Ticket not found");
            }

        } catch (RestClientException e) {

            log.error("Error while getting ticket TicketService::getTicketById => {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found or already deleted.");
        }
    }


    public ResponseEntity<?> updateTicket(String ticketID, TicketDTO updatedTicket) {
        // Logic to update a ticket

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(updatedTicket);
            HttpEntity<String> entity = new HttpEntity<>(jsonPayload, createHeaders(username, password));
            return restTemplate.exchange(servicenowUrl + "/api/now/table/ticket/" + ticketID, HttpMethod.PUT, entity, String.class);
        } catch (JsonProcessingException e) {
            log.error("Error while updating ticket TicketService::updateTicket => {}", e.getMessage());
            throw new RuntimeException("Error while updating ticket TicketService::updateTicket => ", e);
        }

    }


    public ResponseEntity<?> deleteTicket(String ticketID) {
        // Logic to delete a ticket

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            HttpEntity<String> entity = new HttpEntity<>(createHeaders(username, password));
            return restTemplate.exchange(servicenowUrl + "/api/now/table/ticket/" + ticketID, HttpMethod.DELETE, entity, String.class);
        } catch (RestClientException e) {
            log.error("Error while deleting ticket TicketService::deleteTicket => {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found or already deleted.");
        }
    }

}
