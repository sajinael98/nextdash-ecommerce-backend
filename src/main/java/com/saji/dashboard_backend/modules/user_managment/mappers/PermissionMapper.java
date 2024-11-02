package com.saji.dashboard_backend.modules.user_managment.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.user_managment.dtos.PermissionResponse;
import com.saji.dashboard_backend.modules.user_managment.entities.Permission;
import com.saji.dashboard_backend.shared.dtos.BaseResponse;
import com.saji.dashboard_backend.shared.mappers.BaseMapper;

@Service
public class PermissionMapper implements BaseMapper<Permission> {

   @Override
   public BaseResponse convertEntityToResponse(Permission entity) {
      PermissionResponse response = new PermissionResponse();
      BeanUtils.copyProperties(entity, response);
      return response;
   }
}
