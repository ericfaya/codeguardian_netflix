package com.nttdata.indhub.controller.impl;

import com.nttdata.indhub.controller.model.rest.SeasonRest;
import com.nttdata.indhub.controller.model.rest.restSeason.PostSeasonRest;
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
import com.nttdata.indhub.controller.SeasonControllerRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.D4iPaginationInfo;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.service.SeasonService;
import com.nttdata.indhub.util.constant.CommonConstantsUtils;
import com.nttdata.indhub.util.constant.RestConstantsUtils;

@RestController
@Tag(name = "Season", description = "Season Controller")
@RequiredArgsConstructor
public class SeasonControllerRestImpl implements SeasonControllerRest {

  @Override
  public NetflixResponse<D4iPageRest<PostSeasonRest>> getAllSeasons(int page, int size, Pageable pageable) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<PostSeasonRest> getSeasonById(Long id) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<PostSeasonRest> createSeason(PostSeasonRest season) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<PostSeasonRest> updateSeason(PostSeasonRest season) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<Object> deleteSeason(Long id) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<SeasonRest> addChapterToSeason(Long seasonId, Long chapterId) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<SeasonRest> deleteChapterOfSeason(Long seasonId, Long chapterId) throws NetflixException {
    return null;
  }
}
