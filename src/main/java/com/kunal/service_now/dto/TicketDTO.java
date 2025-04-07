package com.kunal.service_now.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    @NonNull
    private String short_description;
    @NonNull
    private String description;
    @NonNull
    private String state;
    @NonNull
    private String priority;
    @NonNull
    private String u_type;
    private String close_notes;
}
