package com.huncho.tripubank.customer.services;

import com.huncho.tripubank.customer.dtos.EmailDetails;

public interface EmailService {
    void sendEmailAlerts(EmailDetails emailDetails);
}
