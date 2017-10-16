package com.badenblog.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class AbstractBadenblogController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  protected <T> BadenblogResource<T> handleSuccessResponse(final T response) {
    log.info("Fin de llamada a la api, enviando repuesta del servicio: {} ",
            JSONObject.wrap(response));
    final BadenblogResource<T> resource = new BadenblogResource<T>();
    resource.setContent(response);
    resource.setTimestamp(new Date().getTime());
    resource.setStatus(HttpStatus.OK.value());
    resource.setMessage(HttpStatus.OK.getReasonPhrase());
    resource.setManaged(true);
    return resource;
  }

  protected BadenblogResource<String> handleSuccessResponse() {
    log.info("Fin de llamada a la api, enviando repuesta del servicio: {} ",
            HttpStatus.OK.getReasonPhrase());
    final BadenblogResource<String> resource = new BadenblogResource<String>();
    resource.setContent(HttpStatus.OK.getReasonPhrase());
    resource.setTimestamp(new Date().getTime());
    resource.setStatus(HttpStatus.OK.value());
    resource.setMessage(HttpStatus.OK.getReasonPhrase());
    resource.setManaged(true);
    return resource;
  }

  protected BadenblogResource<String> handleSuccessResponse(final String message) {
    log.info("Fin de llamada a la api, enviando repuesta del servicio: {} ",
            HttpStatus.OK.getReasonPhrase());
    final BadenblogResource<String> resource = new BadenblogResource<String>();
    resource.setContent(HttpStatus.OK.getReasonPhrase());
    resource.setTimestamp(new Date().getTime());
    resource.setStatus(HttpStatus.OK.value());
    resource.setMessage(message);
    resource.setManaged(true);
    return resource;
  }

  protected ResponseEntity<InputStreamResource> handleSuccesResponseExportar(final byte[] data,
          final String mediaType) {
    final InputStream in = new ByteArrayInputStream(data);
    return ResponseEntity
            .ok()
            .contentLength(data.length)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(new InputStreamResource(in));
  }
}
