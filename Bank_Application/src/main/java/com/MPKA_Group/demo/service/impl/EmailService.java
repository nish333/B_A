package com.MPKA_Group.demo.service.impl;

import com.MPKA_Group.demo.dto.EmailDetails;

public interface EmailService
{
	void sendEmailAlert(EmailDetails emailDetails);
	
	void sendEmailWithAttachment(EmailDetails emailDetails);
}
