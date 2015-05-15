package com.avisit.vijayam.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.GcmContent;
import com.avisit.vijayam.service.GcmSenderService;

@Component
@Path("/gcm")
public class GcmRestService {
	
	@Autowired
	private GcmSenderService sender;
	
	@GET
	@Path("/send")
	public Response sendToGcm(){
		String apiKey = "AIzaSyA2BCAz71E5BSJXjHm5SAe5MdJXF99g53A";
        GcmContent content = createContent();
        sender.post(apiKey, content);
        return Response.status(200).entity("siva").build();
	}

	public static GcmContent createContent(){
		GcmContent c = new GcmContent();
		/*c.addRegId("APA91bFyUMakdADWaUMdE_cZVMyhyujzioxcoCNdFVt-wDVttB4c0NlN41mBcVAmI7EM-gMFLcIHy88MJvHzhZjXvRY4hGN_jEawblXfiSCxFTZbms5mSOjmaDTp4FJglliH9i6VcTneOXXJ7ww06tiX0Euk5RqKcA");*/
        c.addRegId("APA91bEhzS3rBZ3B46KsRNXeDizVt4EuQMxnbj0m1EUlwoFeZEIzE1XW6YaIHTIHmTWrRWmuv_vRllfy6hJptuDAsBKH7hwG2m156auXUytrty2zfXB2QYG65kLGpFHASL-MsFddqIEte6D5n0ZagAgC6QKpNMyPgg");
        c.createData("Test Title", "Test Message");
        return c;
    }
}
