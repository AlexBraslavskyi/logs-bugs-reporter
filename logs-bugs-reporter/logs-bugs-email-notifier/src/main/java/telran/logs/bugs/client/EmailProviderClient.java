package telran.logs.bugs.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Component
public class EmailProviderClient {
	static Logger LOG = LoggerFactory.getLogger(EmailProviderClient.class);
	RestTemplate restTemplate = new RestTemplate();
	@Value("${app-url-assigner-mail:xxx}")
	String urlAssignerMail;
	@Value("${app-url-artifact-mail:xxx}")
	String urlArtifactMail;
	
public String getEmailByArtifact(String artifact) {
	String res;
	try {
	ResponseEntity<String> respone = restTemplate.exchange(urlArtifactMail+"/email/"+artifact, HttpMethod.GET, null, String.class);
	res = respone.getBody();
	} catch (RestClientException e) {
		res = "";
	}
	LOG.debug("recived email: {} by artifact: {}", res, artifact);
	return res;
}
public String getAssignerMail() {
	String res;
	try {
		ResponseEntity<String> response =
				restTemplate.exchange(urlAssignerMail+"/mail/assigner", HttpMethod.GET, null, String.class);
		res = response.getBody();
	} catch (RestClientException e) {
		res = "";
	}
	LOG.debug("assigner email is {}", res);
	return res;
}

}
