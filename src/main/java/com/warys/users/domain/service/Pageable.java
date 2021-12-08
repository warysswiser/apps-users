package com.warys.users.domain.service;

import com.warys.users.domain.model.user.Session;
import com.warys.users.infrastructure.exception.ApiException;

import java.util.List;

public interface Pageable<I, O> {

    List<O> getPagedData(Session me, I from, I to) throws ApiException;
}
