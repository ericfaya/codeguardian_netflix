package com.nttdata.indhub.controller.impl;

import com.nttdata.indhub.controller.model.rest.TVShowRest;
import com.nttdata.indhub.controller.model.rest.restTVShow.PostTVShowRest;
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
import com.nttdata.indhub.controller.TVShowControllerRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.D4iPaginationInfo;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.service.TVShowService;
import com.nttdata.indhub.util.constant.CommonConstantsUtils;
import com.nttdata.indhub.util.constant.RestConstantsUtils;

@RestController
@Tag(name = "TVShow", description = "TVShow Controller")
@RequiredArgsConstructor
public class TVShowControllerRestImpl implements TVShowControllerRest {

  @Override
  public NetflixResponse<D4iPageRest<PostTVShowRest>> getAllTVShows(int page, int size, Pageable pageable) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<PostTVShowRest> getTVShowById(Long id) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<PostTVShowRest> createTVShow(PostTVShowRest tvShow) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<PostTVShowRest> updateTVShow(PostTVShowRest tvShow) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<Object> deleteTVShow(Long id) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<TVShowRest> addSeasonToTVShow(Long seasonId, Long tvShowId) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<TVShowRest> deleteSeasonOfTVShow(Long seasonId, Long tvShowId) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<TVShowRest> addCategoryToTVShow(Long categoryId, Long tvShowId) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<TVShowRest> deleteCategoryOfTVShow(Long categoryId, Long tvShowId) throws NetflixException {
    return null;
  }
}
