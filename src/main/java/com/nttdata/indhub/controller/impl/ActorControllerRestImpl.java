package com.nttdata.indhub.controller.impl;

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
import com.nttdata.indhub.controller.ActorControllerRest;
import com.nttdata.indhub.controller.model.rest.ActorRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.D4iPaginationInfo;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.controller.model.rest.restActor.PostActorRest;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.service.ActorService;
import com.nttdata.indhub.util.constant.CommonConstantsUtils;
import com.nttdata.indhub.util.constant.RestConstantsUtils;

@RestController
@Tag(name = "Actor", description = "Actor Controller")
@RequiredArgsConstructor
public class ActorControllerRestImpl implements ActorControllerRest {

    @Override
    public NetflixResponse<D4iPageRest<PostActorRest>> getAllActors(int page, int size, Pageable pageable) throws NetflixException {
        return null;
    }

    @Override
    public NetflixResponse<PostActorRest> getActorById(Long id) throws NetflixException {
        return null;
    }

    @Override
    public NetflixResponse<PostActorRest> createActor(PostActorRest actor) throws NetflixException {
        return null;
    }

    @Override
    public NetflixResponse<PostActorRest> updateActor(PostActorRest actor) throws NetflixException {
        return null;
    }

    @Override
    public NetflixResponse<Object> deleteActor(Long id) throws NetflixException {
        return null;
    }

    @Override
    public NetflixResponse<ActorRest> getActorTVShowsChapters(Long id) throws NetflixException {
        return null;
    }
}
