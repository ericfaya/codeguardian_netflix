package com.nttdata.indhub.controller.impl;

import com.nttdata.indhub.controller.model.rest.CategoryRest;
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
import com.nttdata.indhub.controller.CategoryControllerRest;
import com.nttdata.indhub.controller.model.rest.D4iPageRest;
import com.nttdata.indhub.controller.model.rest.D4iPaginationInfo;
import com.nttdata.indhub.controller.model.rest.NetflixResponse;
import com.nttdata.indhub.exception.NetflixException;
import com.nttdata.indhub.service.CategoryService;
import com.nttdata.indhub.util.constant.CommonConstantsUtils;
import com.nttdata.indhub.util.constant.RestConstantsUtils;

@RestController
@Tag(name = "Category", description = "Category Controller")
@RequiredArgsConstructor
public class CategoryControllerRestImpl implements CategoryControllerRest {

  @Override
  public NetflixResponse<D4iPageRest<CategoryRest>> getAllCategories(int page, int size, Pageable pageable) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<CategoryRest> getCategoryById(Long id) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<CategoryRest> createCategory(CategoryRest category) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<CategoryRest> updateCategory(CategoryRest category) throws NetflixException {
    return null;
  }

  @Override
  public NetflixResponse<Object> deleteCategory(Long id) throws NetflixException {
    return null;
  }
}
