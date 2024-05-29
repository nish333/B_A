package com.MPKA_Group.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails 
{
	private String recipient;
	private String messageBody;
	private String subject;
	private String attachment;
	

	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAttachment(String file) {
		// TODO Auto-generated method stub
		
	}

	public String getAttachment() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRecipient() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSubject() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMessageBody() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRecipient(String email) {
		// TODO Auto-generated method stub	
	}

	public void setSubject(String string) {
		// TODO Auto-generated method stub
	
	}

	public void setMessageBody(String string) {
		// TODO Auto-generated method stub	
	}


}
