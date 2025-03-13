package com.nttdata.indhub.controller.model.rest.restChapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostChapterRest {

  private Long id;

  private Integer number;

  private String name;

  private Double duration;
}
