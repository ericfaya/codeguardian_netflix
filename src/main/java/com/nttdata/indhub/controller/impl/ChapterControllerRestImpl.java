package com.nttdata.indhub.controller.impl;

import com.nttdata.indhub.controller.model.rest.ChapterRest;
import com.nttdata.indhub.controller.model.rest.restChapter.PostChapterRest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.nttdata.indhub.controller.ChapterControllerRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.D4iPaginationInfo;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.service.ChapterService;
import com.nttdata.indhub.util.constant.CommonConstantsUtils;
import com.nttdata.indhub.util.constant.RestConstantsUtils;

@RestController
@Tag(name = "Chapter", description = "Chapter Controller")
@RequiredArgsConstructor
public class ChapterControllerRestImpl implements ChapterControllerRest {

  @Override
  public NetflixResponse<D4iPageRest<PostChapterRest>> getAllChapters(int page, int size, Pageable pageable) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<PostChapterRest> getChapterById(Long id) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<PostChapterRest> createChapter(PostChapterRest chapter) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<PostChapterRest> updateChapter(PostChapterRest chapter) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<Object> deleteChapter(Long id) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<ChapterRest> addActorToChapter(Long actorId, Long chapterId) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<ChapterRest> deleteActorOfChapter(Long actorId, Long chapterId) throws NetflixException {
    return null;
  }
}
