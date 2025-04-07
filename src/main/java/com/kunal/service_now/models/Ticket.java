package com.kunal.service_now.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    private String short_description;
    private String description;
    private String state;
    private String priority;
    private String u_type;
}

